define(function (require, exports, module) {
    // 项目管理 > 风险管控 > 上会申报记录
    var util = require('util');
    var getList = require('zyw/getList');
    var template = require('arttemplate');

    require('Y-msg');
    require('input.limit');
    require('lib/lodash');
    require('validate');
    //上传
    require('zyw/upAttachModify');

    //代偿和非代偿的区分
    var ISREPAY = (util.getParam('isRepay') == 'Y') ? true : false,
        customerName = util.getParam('customerName') || ''; // 客户名称
    //todo 根据 ISREPAY 搜索参数不同
    var _url = '/projectMg/riskHandle/queryProjects.json?phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES&isAdd=YES';
    if (ISREPAY) { //代偿
        _url = _url + '&isRepay=YES';
    }
    $('body').on('click', '.m-table-list .checkbox', function () {
        var isChecked = $(this).is(':checked');
        var oldNum = $('#checkBoxItem').val() || 0;
        oldNum = isChecked ? ++oldNum : --oldNum;
        oldNum = (oldNum == 0) ? '' : oldNum;
        $('#checkBoxItem').val(oldNum).trigger('blur');
    })
    console.log('1')
    var _getList = new getList();
    _getList.init({
        ajaxUrl: _url,
        btn: '#choose',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectcode="{{item.customerName}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目名称：',
                '<input class="ui-text fn-w160" type="text" name="projectName">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" type="text" name="customerName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">客户名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            var formId = "";
            if ($("#formId").val() != undefined) {
                formId = '&formId=' + $("#formId").val();
            }
            window.location.href = '/projectMg/riskHandle/edit.htm?customerName=' + encodeURIComponent($a.attr('projectcode')) + formId + "&date=" + new Date().getTime();
            //window.location.href = '/projectMg/riskHandle/edit.htm?' + ((ISREPAY) ? 'isRepay=Y&' : '') + 'customerName=' + $a.attr('projectcode')+"&date="+new Date().getTime();
        }
    });
    $("input[name=isRepay]").click(function () {

        _getList.resetAjaxUrl(_url + "&isRepay=" + $("input[name=isRepay]:checked").val());
    });
    var obj = {}; //缓存数据
    $('body').on('click', '.checkboxPro .checkbox', function () {

        var _this = $(this)
        if (_this.hasClass('disabledBtn')) return;

        rePayItem(_this);
    }).on('click', '#compensatoryDtl .optBtn', function () {
        var _this = $(this);
        var _thisTr = _this.parents('tr');
        var isAddItem = _this.hasClass('toAdd');
        var projectCode = _thisTr.find('[name*=projectCode]').val();
        var $sameCodeTr = _thisTr.siblings('tr[projectcode=' + projectCode + ']');

        if (isAddItem) {
            obj.projectCode = projectCode;
            var html = template('PROJECT_DAICHANG', obj);
            var $newItem = $(html);
            $newItem.find('.optBtn').removeAttr('style');
            if ($sameCodeTr.length == 0) _this.siblings('a').show();
            _this.hide();
            _thisTr.after($newItem);
        } else {
            if ($sameCodeTr.length <= 1) $sameCodeTr.find('.toDelete').hide();
            $sameCodeTr.last().find('.toAdd').show();
            _thisTr.remove();
        };
        orderNameList();
    }).on('click', '#toRepayRadio .radio', function () {
        var _this = $(this);
        var isRepay = _this.hasClass('isClear');
        var $checkBox = $('.checkboxPro .checkbox');
        if (isRepay) {
            $('#compensatoryDtl').parent().show();
            if (!$checkBox.hasClass('disabledBtn')) return;
            $checkBox.removeClass('disabledBtn');
            $.each($checkBox, function (i, e) {
                rePayItem($(this))
            })
        } else {
            $('#compensatoryDtl').parent().hide().end()
                .find('tbody').html('');
            if ($checkBox.hasClass('disabledBtn')) return;
            $checkBox.addClass('disabledBtn');
        }
    });

    function rePayItem(ele) {
        var $tableBox = $('#compensatoryDtl tbody');
        var isAddItem = ele.prop('checked');
        var _thisTr = ele.parents('tr');
        var projectCode = _thisTr.find('[name*=projectCode]').val();

        if (isAddItem) {
            obj.projectCode = projectCode;
            var html = template('PROJECT_DAICHANG', obj);
            $tableBox.append(html);
        } else {
            $tableBox.find('tr[projectcode=' + projectCode + ']').remove();
        }
        orderNameList();
    }

    function orderNameList() {
        var $oderNameList = $('#compensatoryDtl tr[orderName]');
        $.each($oderNameList, function (index1, el1) {
            // console.log($oderNameList)
            var _this = $(this);
            var orderName = _this.attr('orderName');
            $.each(_this.find('[name]'), function (index2, el2) {
                var $this = $(this);
                var thisName = $this.attr('name');
                if (thisName.indexOf('.') > 0) {
                    thisName = thisName.split('.')[1];
                };
                var newName = orderName + '[' + index1 + '].' + thisName;
                $this.attr('name', newName);
            })
        });
        dynamicRules($('#compensatoryDtl'));

    }

    var $form = $('#form');

    //------ 公共 验证规则 start
    require('validate');
    var validateRules = {
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
        submitHandler: function (form) {},
        rules: {
            customerName: {
                required: true
            },
            isRepay: {
                required: true
            },
            basicInfo: {
                required: true
            },
            riskInfo: {
                required: true
            },
            thisCouncilScheme: {
                required: true
            }
        },
        messages: {
            customerName: {
                required: '必填'
            },
            isRepay: {
                required: '必填'
            },
            basicInfo: {
                required: '必填'
            },
            riskInfo: {
                required: '必填'
            },
            thisCouncilScheme: {
                required: '必填'
            }
        }
    };

    function dynamicRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput');

        $nameList.each(function (i, e) {
            var _this = $(this);
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })
    }
    $('#form').validate(validateRules);
    dynamicRules($('#compensatoryDtl'))
    $form.on('click', '#submit', function () {

        // console.log(!$('#form').valid())
        if (!$('#form').valid()) return;
        var $fnInput = $('.fnInput');
        var _this = $(this);

        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        if ($("input[name=isRepay]:checked").val() == undefined) //验证是否选择代偿
        {
            Y.alert('提示', '请选择是否代偿');
            _this.removeClass('ing');
            return;
        };

        var _isPass = true,
            _isPassEq;

        $fnInput.each(function (index, el) {
            if (!!!$(this).val().replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {
            Y.alert('提醒', '请把表单填写完整', function () {
                $('.fnInput').eq(_isPassEq).focus();
            });
            _this.removeClass('ing');
            return;
        }

        doSubmit('SUBMIT');
    });

    function doSubmit(type) {
        if (!!!document.getElementById('customerName').value) {
            Y.alert('提示', '请选择用户', function () {
                document.getElementById('customerName').focus();
            });
            $('#submit').removeClass('ing');
            return;
        }
        var _isSava = type ? false : true;
        //暂存0 提交1
        document.getElementById('checkStatus').value = _isSava ? '0' : '1';
        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {
                if (res.success) {
                    if (!_isSava) {

                        //                      util.ajax({
                        //                          url: '/projectMg/form/submit.htm',
                        //                          data: {
                        //                              formId: res.form.formId
                        //                          },
                        //                          success: function(res2) {
                        //                              Y.alert('提示', res2.message, function() {
                        //                                  if (res2.success) {
                        //                                      window.location = '/projectMg/riskHandle/list.htm';
                        //                                  }else{
                        //                                        $('#submit').removeClass('ing');
                        //                                    }
                        //                              });
                        //                          }
                        //                      });
                        util.postAudit({
                            formId: res.form.formId
                        }, function () {
                            window.location.href = '/projectMg/riskHandle/list.htm';
                        })

                    } else {
                        Y.alert('提示', '已保存', function () {
                            window.location.href = '/projectMg/riskHandle/list.htm';
                        });
                    }
                } else {

                    Y.alert('提示', res.message, function () {
                        $('#submit').removeClass('ing');
                    });

                }
            }
        });

    };



    //------ 侧边工具栏 start
    var publicOPN = new(require('zyw/publicOPN'))()
    publicOPN.addOPN([{
        name: '暂存',
        alias: 'save',
        event: function () {
            doSubmit();
        }
    }]);
    var fnRiskWarningFormId = document.getElementById('fnRiskWarningFormId');
    if (fnRiskWarningFormId && fnRiskWarningFormId.value != 0) {
        publicOPN.addOPN([{
            name: '查看风险预警',
            alias: 'riskWarning',
            event: function () {
                // 新开页面跳转到指定url，带参数的url
                util.openDirect('/projectMg/index.htm', '/projectMg/riskWarning/view.htm?formId=' + fnRiskWarningFormId.value)
            }
        }]);
    }
    publicOPN.init().doRender();
    //------ 侧边工具栏 end

});