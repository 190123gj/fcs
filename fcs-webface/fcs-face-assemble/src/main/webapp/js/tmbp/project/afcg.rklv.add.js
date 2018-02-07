define(function (require, exports, module) {
    //保后项目汇总表 新增
    require('input.limit');
    require('validate');
    require('Y-msg');

    var util = require('util');
    var getList = require('zyw/getList');

    //------ 选择项目 start
    var _getList = new getList();
    _getList.init({
        title: '项目列表',
        ajaxUrl: '/projectMg/riskLevel/queryProjects.json?select=my&phasesList=LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES&busiTypes=1,221,231,3,4,6,7',
        btn: '#choose',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目编号：',
                '<input class="ui-text fn-w160" name="projectCode" type="text">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" name="customerName" type="text">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">企业名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            window.location.href = '/projectMg/riskLevel/edit.htm?projectCode=' + $a.attr('projectCode')
        }
    });
    //------ 选择项目 end

    var $form = $('#form')
    $form.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {

            if (element.hasClass('fnErrorAfter')) {

                element.after(error);

            } else {

                element.parent().append(error);

            }
        },
        rules: {
            enterpriseType: {
                required: true
            },
            projectType: {
                required: true
            }
        },
        messages: {
            enterpriseType: {
                required: '必填'
            },
            projectType: {
                required: '必填'
            }
        },
        submitHandler: function (form) {

            //得分
            var _score = +document.getElementById('totalInput').value,
                _level;

            if (_score < 30) {
                _level = '损失类'
            } else if (_score >= 30 && _score < 40) {
                _level = '可疑类'
            } else if (_score >= 40 && _score < 60) {
                _level = '次级类'
            } else if (_score >= 60 && _score < 75) {
                _level = '关注类'
            } else {
                _level = '正常类'
            }

            var _content = '<p>合计得分：' + _score + '分</p><p class="fn-mt20">该项目风险等级为<span class="fn-fs20 fn-f30 fn-fwb">' + _level + '</span></p>'

            if ($fnTBCheckbox.prop('checked')) {

                var $select = $fnTBCheckbox.parents('.fnTBCheckbox').find('.fnTBInput').eq(0)

                _content = '<p>该项目风险等级为<span class="fn-fs20 fn-f30 fn-fwb">' + $select.find('option[value="' + $select.val() + '"]').text() + '</span></p>'

            }

            // 显示计算结果
            $('body').Y('Msg', {
                type: 'confirm',
                title: '提示',
                content: _content,
                yesText: '确认提交',
                noText: '返回修改',
                callback: function (opn) {
                    if (opn == 'yes') {
                        doSubmit('SUBMIT');
                    }
                }
            });

        }
    });

    $form.on('change', 'input.fnTotal', function () {

        var _total = 0

        $('input.fnTotal:visible').each(function (index, el) {
            _total = util.accAdd(+el.value, _total)
        });

        document.getElementById('totalInput').value = _total

    })

    $('#fnFDBBody').find('input.fnInput').each(function (index, el) {
            $(this).rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })
        })
        .end().find('input.fnInput').eq(0).trigger('blur');

    function doSubmit(type) {

        if (!!!document.getElementById('projectNumber').value) {
            Y.alert('提示', '请先选择一个项目', function () {
                document.getElementById('projectNumber').focus();
            });
            return;
        }

        document.getElementById('checkStatus').value = type ? 1 : 0;

        util.ajax({
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    // Y.alert('提示', type ? res.message : '已保存', function () {
                    //     window.location.href = '/projectMg/riskLevel/list.htm'
                    // });
                    util.postAudit({
                        formId: res.form.formId
                    }, function () {
                        window.location.href = '/projectMg/riskLevel/list.htm'
                    })
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });

    }

    // ------ 特别事项 starr
    var $fnTBCheckbox = $('#fnTBCheckbox');

    function toggleTBInput() {

        var $box = $fnTBCheckbox.parents('.fnTBCheckbox')

        if ($fnTBCheckbox.prop('checked')) {

            $box.find('.fnTBInputBox').removeClass('fn-hide')
            $box.find('.fnTBInput').each(function (index, el) {
                $(this).rules('add', {
                    required: true,
                    maxlength: false,
                    messages: {
                        required: '必填'
                    }
                })
            })

            $('.fnRadioBox .fnInput').each(function (index, el) {
                var $this = $(this)
                $this.rules('remove', 'required')
                $this.valid()
            })

            $('#fnNotTBCheckbox').addClass('fn-hide')

        } else {

            $box.find('.fnTBInputBox').addClass('fn-hide')
            $box.find('.fnTBInput').each(function (index, el) {
                var $this = $(this)
                $this.rules('remove', 'required')
                $this.valid()
            })

            $('.fnRadioBox .fnInput:visible').each(function (index, el) {
                $(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })
            })

            $('#fnNotTBCheckbox').removeClass('fn-hide')

        }
    }

    $fnTBCheckbox.on('click', toggleTBInput)

    setTimeout(function () {
        toggleTBInput()
    }, 10)


    // ------ 特别事项 end

    // ------ 二选一 start
    $('.fnRadioBox').on('click', '.fnRadioItem', function () {

            // 特别事项说明 无须再加必填

            var _type = this.getAttribute('ctype'),
                $box = $(this).parents('.fnRadioBox')

            $box.find('table').addClass('fn-hide').find('.fnInput').each(function (index, el) {
                    var $this = $(this)
                    $this.rules('remove', 'required')
                    $this.attr('name', '');
                })
                .end().find('input').prop('disabled', 'disabled');

            $box.find('table[ctype="' + _type + '"]').removeClass('fn-hide').find('.fnInput').each(function (index, el) {
                    var $this = $(this)
                    $this.attr('name', $this.attr('sname'))
                    if (!$fnTBCheckbox.prop('checked')) {
                        $this.rules('add', {
                            required: true,
                            messages: {
                                required: '必填'
                            }
                        })
                    }
                    $this.trigger('blur')
                })
                .end().find('input.fnInput').trigger('blur')
                .end().find('input').removeProp('disabled');

        }).on('blur', 'input.fnInput', function () {

            // 合计分数
            var $this = $(this),
                _max = +$this.parent().prev().html().replace(/(\( |\)|\s)/g, '')

            setTimeout(function () {

                if (+$this.val() > _max) {
                    $this.val(_max)
                }

                totalInput($this.parents('table'))

                //如果需要小的求和
                if ($this[0].className.indexOf('subth_') >= 0) {
                    getCombined($this[0])
                }

            }, 10);

        })
        .find('.fnRadioItem:checked').trigger('click');

    function totalInput($table) {

        var _total = 0

        $table.find('input.fnInput').each(function (index, el) {
            _total = util.accAdd(+el.value, _total)
        });

        $table.find('.fnTotal').val(_total).trigger('change')

    }

    // //小分段求和
    function getCombined(self) {

        var _classArr = self.className.split(' '),
            _class,
            _n = 0;
        //找到可能的那个class
        for (var i = 0; i < _classArr.length; i++) {
            if (_classArr[i].indexOf('subth_') == 0) {
                _class = _classArr[i];
            }
        }
        $('.' + _class).each(function (index, el) {
            _n = util.accAdd(_n, +el.value)
        });
        document.getElementById(_class).innerHTML = _n;

    }

    // ------ 二选一 start

    // var $fnInput = $('.fnInput'), //所有得分必要输入新
    //     // 企业综合评价
    //     // $fnEnterpriseType = $('.fnEnterpriseType'),
    //     hasEnterpriseType = $('.fnEnterpriseType:checked').val(),
    //     $comprehensive = $('#comprehensive'),
    //     $fnComprehensive = $('.fnComprehensive'),
    //     // 项目指标情况及项目能力评价
    //     hasProjectType = $('.fnProjectType:checked').val(),
    //     $situation = $('#situation'),
    //     $fnSituation = $('.fnSituation'),
    //     // 担保
    //     $guarantee = $('#guarantee'),
    //     $fnGuarantee = $('.fnGuarantee'),
    //     //
    //     $totalInput = $('#totalInput'),
    //     $form = $('#form'),
    //     formRules = {
    //         rules: {},
    //         messages: {}
    //     };
    // // 还原选择 综合评价
    // if (hasEnterpriseType) {
    //     $fnComprehensive.removeClass('disabled').removeProp('disabled');
    //     $fnComprehensive.trigger('blur');
    // }
    // // 还原选择 项目指标
    // if (hasProjectType) {
    //     $fnSituation.removeClass('disabled').removeProp('disabled');
    //     //填充默认值
    //     var _isLD = (hasProjectType.indexOf('流动') >= 0) ? true : false;
    //     $fnSituation.each(function (index, el) {
    //         var _this = $(this),
    //             _score = _this.attr('score');
    //         if (_score && _isLD) {
    //             _this[0].value = _score;
    //             _this.prop('readonly', 'readonly');
    //         } else {
    //             _this.removeProp('readonly');
    //         }
    //     });
    //     //记录选择了那个
    //     $fnSituation.trigger('blur');
    // }
    // // 企业综合评价
    // $form.on('click', '.fnEnterpriseType', function () {
    //     if ($fnComprehensive.eq(0).hasClass('disabled')) {
    //         $fnComprehensive.removeClass('disabled').removeProp('disabled');
    //     }
    //     //记录选择了那个
    //     hasEnterpriseType = this.value;
    //     $fnComprehensive.trigger('blur');
    // });
    // // 项目指标情况及项目能力评价
    // $form.on('click', '.fnProjectType', function () {
    //     if ($fnSituation.eq(0).hasClass('disabled')) {
    //         $fnSituation.removeClass('disabled').removeProp('disabled');
    //     }
    //     //填充默认值
    //     var _isLD = (this.value.indexOf('流动') >= 0) ? true : false;
    //     $fnSituation.each(function (index, el) {
    //         var _this = $(this),
    //             _score = _this.attr('score');
    //         if (_score && _isLD) {
    //             _this[0].value = _score;
    //             _this.prop('readonly', 'readonly');
    //         } else {
    //             _this.removeProp('readonly');
    //         }
    //     });
    //     //记录选择了那个
    //     hasProjectType = this.value;
    //     $fnSituation.trigger('blur');
    // });

    // // 小计得分
    // $form.on('blur', '.fnComprehensive,.fnSituation', function () {

    //     var _this = $(this),
    //         _tdEq;
    //     if (_this.hasClass('fnComprehensive')) {
    //         _tdEq = (hasEnterpriseType.indexOf('一般') > -1) ? 2 : 4
    //     } else {
    //         _tdEq = (hasProjectType.indexOf('项目') > -1) ? 2 : 4
    //     }

    //     var _max = +_this.parents('tr').find('td').eq(_tdEq).text().replace(/[^\d]/g, '');

    //     if (this.value > _max) {
    //         this.value = _max
    //     }

    //     if (_this.hasClass('fnComprehensive')) {
    //         getTotal($fnComprehensive, $comprehensive)
    //     } else {
    //         getTotal($fnSituation, $situation)
    //     }

    // }).on('blur', '.fnGuarantee', function () {

    //     var _this = $(this),
    //         _max = +_this.parents('tr').find('td').eq(2).text().replace(/[^\d]/g, '');

    //     if (this.value > _max) {
    //         this.value = _max
    //     }

    //     getTotal($fnGuarantee, $guarantee)

    //     //如果需要小的求和
    //     if (this.className.indexOf('subth_') >= 0) {
    //         getCombined(this)
    //     }

    // }).on('change', '#comprehensive,#situation,#guarantee', function () {

    //     getScore()

    // });

    // //求和
    // function getTotal($els, $totalEl) {

    //     var _n = 0;
    //     $els.each(function (index, el) {
    //         _n = util.accAdd(_n, +el.value)
    //     });
    //     $totalEl.val(_n).trigger('change');
    // }

    // //小分段求和
    // function getCombined(self) {

    //     var _classArr = self.className.split(' '),
    //         _class,
    //         _n = 0;
    //     //找到可能的那个class
    //     for (var i = 0; i < _classArr.length; i++) {
    //         if (_classArr[i].indexOf('subth_') == 0) {
    //             _class = _classArr[i];
    //         }
    //     }
    //     $('.' + _class).each(function (index, el) {
    //         _n = util.accAdd(_n, +el.value)
    //     });
    //     document.getElementById(_class).innerHTML = _n;

    // }

    // //总分
    // function getScore() {
    //     // $totalInput.val((+$comprehensive.val() || 0) + (+$situation.val() || 0) + (+$guarantee.val() || 0))
    //     $totalInput.val(util.accAdd(+$comprehensive.val(), util.accAdd(+$situation.val(), +$guarantee.val())));
    // }

    // if ($('#projectNumber').val()) {
    //     getScore();
    // }


    // // 提交
    // function doSubmit(type) {

    //     if (!!!document.getElementById('projectNumber').value) {
    //         Y.alert('提示', '请先选择一个项目', function () {
    //             document.getElementById('projectNumber').focus();
    //         });
    //         return;
    //     }

    //     document.getElementById('checkStatus').value = type ? 1 : 0;

    //     util.ajax({
    //         url: $form.attr('action'),
    //         data: $form.serialize(),
    //         success: function (res) {
    //             if (res.success) {
    //                 Y.alert('提示', type ? '已提交' : '已保存', function () {
    //                     window.location.href = '/projectMg/riskLevel/list.htm'
    //                 });
    //             } else {
    //                 Y.alert('提示', res.message);
    //             }
    //         }
    //     });

    // }

    // $fnInput.each(function (index, el) {
    //     //必填规则
    //     var name = $(this).attr('name');
    //     if (!name) {
    //         return true;
    //     }
    //     formRules.rules[name] = {
    //         required: true
    //     };
    //     formRules.messages[name] = {
    //         required: '必填'
    //     };
    // });

    // $form.validate($.extend(true, {}, formRules, {
    //     errorClass: 'error-tip',
    //     errorElement: 'b',
    //     ignore: '.ignore',
    //     onkeyup: false,
    //     errorPlacement: function (error, element) {

    //         if (element.hasClass('fnErrorAfter')) {

    //             element.after(error);

    //         } else {

    //             element.parent().append(error);

    //         }
    //     },
    //     rules: {
    //         enterpriseType: {
    //             required: true
    //         },
    //         projectType: {
    //             required: true
    //         }
    //     },
    //     messages: {
    //         enterpriseType: {
    //             required: '必填'
    //         },
    //         projectType: {
    //             required: '必填'
    //         }
    //     },
    //     submitHandler: function (form) {

    //         if (!!!hasEnterpriseType) {
    //             Y.alert('提示', '还未选择企业类型', function () {
    //                 $('.fnEnterpriseType').focus();
    //             });
    //             return;
    //         }

    //         if (!!!hasProjectType) {
    //             Y.alert('提示', '还未选择项目类型', function () {
    //                 $('.fnProjectType').focus();
    //             });
    //             return;
    //         }

    //         //得分
    //         var _score = +$totalInput.val(),
    //             _level;

    //         if (_score < 30) {
    //             _level = '损失类'
    //         } else if (_score >= 30 && _score < 40) {
    //             _level = '可疑类'
    //         } else if (_score >= 40 && _score < 60) {
    //             _level = '次级类'
    //         } else if (_score >= 60 && _score < 75) {
    //             _level = '关注类'
    //         } else {
    //             _level = '正常类'
    //         }

    //         // 显示计算结果
    //         $('body').Y('Msg', {
    //             type: 'confirm',
    //             title: '提示',
    //             content: '<p>合计得分：' + _score + '分</p><p class="fn-mt20">该项目风险等级为<span class="fn-fs20 fn-f30 fn-fwb">' + _level + '</span></p>',
    //             yesText: '确认提交',
    //             noText: '返回修改',
    //             callback: function (opn) {
    //                 if (opn == 'yes') {
    //                     doSubmit('SUBMIT');
    //                 }
    //             }
    //         });

    //     }
    // }));

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    // publicOPN.addOPN([{
    //  name: '暂存',
    //  event: function() {
    //      doSubmit();
    //  }
    // }]);
    publicOPN.init().doRender();
    //------ 侧边栏 end

});