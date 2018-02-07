define(function (require, exports, module) {
    // 项目管理 > 辅助系统  > 用印申请
    require('zyw/publicPage');
    require('Y-msg');
    require('input.limit');

    var util = require('util');
    var getList = require('zyw/getList');

    //------ 侧边栏 start

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();

    //------ 侧边栏 end

    //------ 用印申请列表 start

    $('#list').on('click', '.fnCheckAll', function (e) {

        // 列表中全选中的checkbox

        e ? e.stopPropagation() : window.event.cancelBubble = true;

        var _checked = this.checked
        $list.find('.fnCheck:not([disabled])').each(function (index, el) {
            this.checked = _checked
        })

    }).on('click', '.confirm', function () {

        var _thisTr = $(this).parents('tr');
        Y.confirm('信息提醒', '请确认文件：' + _thisTr.attr('formname') + ' 用印完成再来点击<br>是否确认用印？', function (opn) {
            if (opn == 'yes') {
                util.ajax({
                    url: '/projectMg/stampapply/confirmStampApply.htm?formId=' + _thisTr.attr('formid'),
                    success: function (res) {
                        if (res.success) {
                            Y.alert('消息提醒', '已确认打印', function () {
                                window.location.reload(true);
                            });
                        } else {
                            Y.alert('消息提醒', res.message);
                        }
                    }
                });
            }
        });
    });


    //------ 用印申请列表 end

    //------ 多个页面集合 start
    var $form = $('#form');

    $('.fnNewWindowOpen').on('click', function (e) {
        e.preventDefault();
        window.open(this.href);
    });


    //--- 提交用印申请表

    // 新增一行
    $('#fnNewLine').on('click', function () {
        $form.find('tbody').append(document.getElementById('t-file').innerHTML);
    });

    // 上传附件
    require('zyw/upAttachModify');

    // 对 name 排序
    function resetApplyName() {

        var i = 0;
        $('.fnResetName').each(function (index, el) {

            var $this = $(this),
                _diyname = $this.attr('diyname'),
                i = Math.floor(index / 2);

            $this.find('input').each(function (index, el) {

                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.indexOf('.') + 1);
                }

                name = _diyname + '[' + i + '].' + name;

                _this.attr('name', name);

            });

        });
    }

    $form.on('click', '.fnApplyBtn', function () {

        var _this = $(this),
            _isSave = _this.hasClass('save'),
            Dstatus = document.getElementById('status');

        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        //必填项
        var _isPass = true,
            _isPassEq;

        if (!_isSave) {
            $('.fnInput').each(function (index, el) {
                if (!!!el.value.replace(/\s/g, '') || (util.hasClass(el, 'fnMakeNumber') && el.value == 0)) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                    return false;
                }
            });
        } else {
            $('.fnRequired').each(function (index, el) {
                if (!!!el.value.replace(/\s/g, '')) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                    return false;
                }
            });
        }

        if (!_isPass) {
            Y.alert('提醒', '请填写必填内容', function () {

                _isSave ? $('.fnRequired').eq(_isPassEq).focus() : $('.fnInput').eq(_isPassEq).focus()

            });
            _this.removeClass('ing');
            return;
        }

        // 要勾选一个印章类型
        // 2016.07.25 无论新增、默认数据，有了文件名，都要选择一个
        var _isChecked = true;
        $('.fnResetName').each(function (index, el) {

            if ((index % 2) != 0) {
                return true;
            }

            var _$this = $(this);

            if (_$this.find('td').eq(1).find('input').eq(0).val().replace(/\s/g, '')) {

                if (!_$this.next().find('input:checked').length) {
                    _isChecked = false;
                }

            }

        });

        if (!_isChecked) {
            Y.alert('提醒', '每个文件至少勾选一个印章类型');
            _this.removeClass('ing');
            return false;
        }

        // if (!_isSave && !$('input:checked').length) {
        //  Y.alert('提醒', '至少选择一个用印类型');
        //  _this.removeClass('ing');
        //  return;
        // }

        //是否是保存
        if (!_isSave) {
            Dstatus.value = 'SUBMIT';
        } else {
            Dstatus.value = 'TEMP';
        }
        //部分内容填充0
        $('.fnMakeNumber').each(function (index, el) {
            if (!!!el.value) {
                el.value = '0';
            }
        });
        // 文件名未写，不保存
        $('tbody .fnNewTr').each(function (index, el) {
            if (_isSave) {
                return false;
            }
            $(this).find('input').each(function (i, e) {
                if (!!!e.value && e.name == 'fileName') {
                    var _$thisTr = $(this).parents('tr');
                    _$thisTr.find('input').prop('disabled', 'disabled');
                    _$thisTr.next().find('input').prop('disabled', 'disabled');
                }
            });
        });

        document.getElementById('checkStatus').value = _isSave ? '0' : '1';

        //重命名
        resetApplyName();

        //提交数据
        util.ajax({
            url: '/projectMg/stampapply/saveStampApply.htm',
            data: $form.serialize(),
            success: function (res) {
                _this.removeClass('ing');
                if (res.success) {
                    if (res.status == 'SUBMIT') {
                        util.postAudit({
                            formId: res.formId
                        }, function () {

                            if (util.getParam('backurl')) {
                                util.doBack();
                            } else {
                                util.direct('/projectMg/stampapply/list.htm');
                            }

                        }, function () {
                            util.direct('/projectMg/stampapply/list.htm');
                        })
                    } else {
                        Y.alert('提醒', '保存成功', function () {
                            util.direct('/projectMg/stampapply/list.htm');
                        });
                    }

                } else {
                    Y.alert('提醒', res.message);
                }
            }
        });

    }).on('click', '.fnDelLine', function () {
        var _thisTr = $(this).parents('tr'),
            _thisTrNext = _thisTr.next();
        _thisTr.remove();
        _thisTrNext.remove();
    }).on('click', '.fnCheckbox', function () {
        //份数是否必填
        var _this = $(this),
            _thisP = _this.parent(),
            _thisInput = _thisP.next();
        if (_this.prop('checked')) {
            _thisP.append('<span class="fn-f30 fn-ml20 fn-fwb">*</span>');
            _thisInput.addClass('fnInput');
        } else {
            _thisP.find('span').remove();
            _thisInput.removeClass('fnInput');
        }
    }).on('blur', '.fnMakeNumber', function () {
        //从合同列表申请用印
        //不能大于申请份数
        var _this = $(this),
            _maxitem = +_this.attr('maxitem');

        setTimeout(function () {
            if (!_maxitem) {
                return;
            }

            if (_this.val() > _maxitem) {
                _this.val(_maxitem)
            }
        }, 0);

    });

    //选项目
    if ($('.fnChooseP').length) {
        var _getPr = new getList();
        _getPr.init({
            width: '90%',
            title: '项目列表',
            ajaxUrl: '/baseDataLoad/queryProjects.json?phasesList=CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES&fromStamp=IS',
            btn: '.fnChooseP',
            input: ['customerId', 'customerName', 'projectCode', 'projectName'],
            tpl: {
                tbody: ['{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.projectCode}}</td>',
                    '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>',
                    '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 10)?item.projectName.substr(0,10)+\'\.\.\':item.projectName}}</td>',
                    '        <td>{{item.busiTypeName}}</td>',
                    '        <td>{{item.amount}}</td>',
                    '        <td><a class="choose" customerId="{{item.customerId}}" customerName="{{item.customerName}}" projectCode="{{item.projectCode}}" projectName="{{item.projectName}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: ['项目名称：',
                    '<input class="ui-text fn-w160" type="text" name="projectName">',
                    '&nbsp;&nbsp;',
                    '客户名称：',
                    '<input class="ui-text fn-w160" type="text" name="customerName">',
                    '<input type="hidden" name="" value="' + $('#fnMaintainType').val() + '">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: ['<th width="100">项目编号</th>',
                    '<th width="120">客户名称</th>',
                    '<th width="120">项目名称</th>',
                    '<th width="100">授信类型</th>',
                    '<th width="100">授信金额(元)</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            }
        });
    }

    // 用印申请 选公司 start

    if (document.getElementById('fnChooseC')) {

        var _getC = new getList();
        _getC.init({
            width: '90%',
            title: '印章配置列表',
            ajaxUrl: '/baseDataLoad/stampConfig.json',
            btn: '#fnChooseC',
            multiple: true,
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.orgName}}</td>',
                    '        <td>{{item.gzRoleCode}}</td>',
                    '        <td>{{item.frzRoleCode}}</td>',
                    // '        <td><a class="choose" companyName="{{item.orgId}}" href="javascript:void(0);">选择</a></td>',
                    '        <td><input class="checkbox" type="checkbox" value="{{item.orgName}}" val="{{item.orgName}}"></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [].join(''),
                thead: [
                    '<th width="100">公司名称</th>',
                    '<th width="120">公章</th>',
                    '<th width="120">法人章</th>',
                    '<th width="50">操作</th>'
                ].join('')
            },
            renderCallBack: function (res, self) {

                var _ids = (document.getElementById('companyNames').value || '').split(',')

                $.each(_ids, function (index, str) {

                    self.$box.find('tbody .checkbox[value="' + str + '"]').prop('checked', 'checked')

                })

            },
            callback: function (self) {

                var _ids = []
                var _names = []

                self.$box.find('tbody .checkbox:checked').each(function (index, el) {

                    _ids.push(el.value)
                    _names.push(el.value)

                })

               // document.getElementById('companyIds').value = _ids.join(',')
                document.getElementById('companyNames').value = _names.join(',')

            }
        });

    }

    // 用印申请 选公司 end


    //--- 文件替换申请表
    $('.fnBackOldFile').on('blur', function () {
        var _max = +$(this).attr('maxitem'),
            self = this;

        setTimeout(function () {
            if (+self.value > _max) {
                self.value = _max;
            }
        }, 0);
    });
    $form.on('click', '.fnReplaceBtn', function () {

        var _this = $(this),
            _isSave = _this.hasClass('save'),
            Dstatus = document.getElementById('status');

        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');
        //是否是保存
        if (!_isSave) {
            Dstatus.value = 'TEMP';
        } else {
            Dstatus.value = 'SUBMIT';
        }

        document.getElementById('checkStatus').value = _isSave ? '0' : '1';

        //提交数据
        util.ajax({
            url: '/projectMg/stampapply/saveStampApply.htm',
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    if (res.status == 'SUBMIT') {
                        util.postAudit({
                            formId: res.formId
                        }, function () {
                            util.direct('/projectMg/stampapply/list.htm');
                        })
                    } else {
                        Y.alert('提醒', '保存成功', function () {
                            util.direct('/projectMg/stampapply/list.htm');
                        });
                    }

                } else {
                    Y.alert('提醒', res.message);
                    _this.removeClass('ing');
                }
            }
        });

    });

    //--- 审核页面
    $form.on('click', '.fnAuditBtn', function () {

        var _this = $(this),
            _isPass = _this.hasClass('pass') ? 'IS' : 'NO';

        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        //是否填写审核意见
        if (!!!document.getElementById('idea').value) {
            Y.alert('提示', '请填写审核意见');
            _this.removeAttr('ing');
            return;
        }

        //提交数据
        util.ajax({
            url: 'dfsd?isPass=' + _isPass,
            success: function (res) {
                if (res.success) {
                    Y.alert('提醒', '保存成功', function () {
                        if (!_isSave) {
                            util.direct('/projectMg/stampapply/list.htm');
                        }
                    });
                } else {
                    Y.alert('提醒', res.message);
                    _this.removeClass('ing');
                }
            }
        });

    });

    //------ 多个页面集合 start
    //
    //
    //
    //
    //------ 用印明细 statr
    // var $detailList = $('#detailList'),
    //  $fnCheckAll = $('#fnCheckAll'),
    //  $fnCheckItem = $('.fnCheckItem');
    // $detailList.on('click', '#fnCheckAll', function() {

    //  //全选
    //  var _this = $(this);
    //  if (_this.prop('checked')) {
    //      $fnCheckItem.prop('checked', 'checked');
    //  } else {
    //      $fnCheckItem.removeProp('checked');
    //  }

    // }).on('click', '.fnCheckItem', function() {

    //  //全选，当某个不是的时候取消
    //  var _this = $(this);
    //  if (!_this.prop('checked') && $fnCheckAll.prop('checked')) {
    //      $fnCheckAll.removeProp('checked');
    //  }

    // });

    $('#fnReplace').on('click', function () {

        //替换跳转
        var _projectCode, _arr = [],
            _applyid,
            _$fnCheckItem = $('.fnCheckItem:checked'),
            _isPass = true;
        //未选择
        if (_$fnCheckItem.length == 0) {
            Y.alert('提醒', '请选择替换文件');
            return;
        }
        //是否一致的projectCode
        _$fnCheckItem.each(function (index, el) {
            var _this = _$fnCheckItem.eq(index);
            if (index == 0) {
                _projectCode = _this.attr('projectCode');
                _applyid = _this.attr('applyid');
            }
            _arr.push(_this.attr('itemid'));

            if (_projectCode != _this.attr('projectCode')) {
                _isPass = false;
            }
        });

        if (!_isPass || !_$fnCheckItem.length) {
            Y.alert('提醒', '请选择同一个项目内的文件');
            return;
        }

        window.location.href = '/projectMg/stampapply/viewStampApply.htm?id=' + _applyid + '&fileIds=' + _arr + '&toPage=replace'

    });
    //------ 用印明细 end

$(function(){
    if (!!window.ActiveXObject || "ActiveXObject" in window){
        $('input[maxlength]').each(function(i,el){
            $(el).attr('maxlength',parseInt($(el).attr('maxlength')+2))
        })
    }
})
});