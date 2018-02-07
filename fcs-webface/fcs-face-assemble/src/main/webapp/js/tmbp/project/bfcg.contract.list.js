define(function (require, exports, module) {
    // 项目管理 > 授信前管理 > 合同申请
    require('Y-msg');
    require('zyw/publicPage');

    var util = require('util');

    var getList = require('zyw/getList');

    // 合同编号
    var _projectCodeList = new getList();

    // 2017-8-2 新增弹框批量用印
    var IDS = [],
        NAMES = [],
        CODE = ''

    _projectCodeList.init({
        title: '项目列表',
        ajaxUrl: '/baseDataLoad/contractProjects.json',
        btn: '#chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item">{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6) + \'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6) + \'\.\.\':item.projectName}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
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
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            document.getElementById('choose').value = $a.attr('projectCode');
        }
    });

    //授信类型

    var getTypesOfCredit = require('zyw/getTypesOfCredit');
    var _getTypesOfCredit = new getTypesOfCredit();
    _getTypesOfCredit.init({
        chooseAll: true,
        btn: '#businessTypeBtn',
        name: '#businessTypeName',
        code: '#businessTypeCode'
    });

    //清除
    $('#businessTypeClear').on('click', function () {
        var d = document;
        d.getElementById('businessTypeName').value = '';
        d.getElementById('businessTypeCode').value = '';
    });

    function creatInvalidHtml(customerName, contractNo, contractName) {

        return [
            '<p class="fn-tal fn-pl20 fn-mb10">客户名称：' + customerName + '</p>',
            '<p class="fn-tal fn-pl20 fn-mb10">合同编号：' + contractNo + '</p>',
            '<p class="fn-tal fn-pl20 fn-mb10">合同名称：' + contractName + '</p>',
            '<p class="fn-tal fn-pl20"><span class="fn-f30 fn-mr5">*</span>报废原因：<textarea class="ui-textarea fn-w200 fn-vat" id="invalidReson" maxlength="1000"></textarea></p>'
        ].join('');

    }

    //其他操作
    ///projectMg/contract/contractInvalid.htm
    ///projectMg/contract/contractConfirm.htm?id
    $('#list').on('click', '.invalid', function () {

        var _thisTr = $(this).parents('tr');

        Y.confirm('合同作废', creatInvalidHtml(_thisTr.attr('customername'), _thisTr.attr('contractno'), _thisTr.attr('contractname')), function (opn) {

            if (opn == 'yes') {

                var _invalidReason = document.getElementById('invalidReson').value;

                if (!!!_invalidReason.replace(/\s/g, '')) {
                    Y.alert('提示', '报废原因未填写，请重新操作', function () {
                        window.location.reload(true);
                    });
                    return;
                }

                util.ajax({
                    url: '/projectMg/contract/contractInvalid.htm',
                    data: {
                        id: _thisTr.attr('id'),
                        invalidReason: _invalidReason
                    },
                    success: function (res) {
                        if (res.success) {
                            Y.alert('提示', '处理成功', function () {
                                window.location.reload(true);
                            });
                        } else {
                            Y.alert('提示', res.message);
                        }
                    }
                });

            }

        });

    }).on('click', '.sure', function () {

        NAMES = []
        IDS = []

        var _thisTr = $(this).parents('tr');
        IDS.push(_thisTr.attr('id'))
        NAMES.push(_thisTr.attr('contractname'))
        CODE = _thisTr.attr('projectcode')
        $newSeal.removeClass('fn-hide')
        addTo$newSealList()

        // Y.confirm('合同确认', '是否确认执行”合同确认“操作？', function (opn) {
        //
        //     if (opn == 'yes') {
        //         Y.confirm('提示','是否需要要用印',function (_opn) {
        //             if (_opn == 'yes') {
        //                 util.ajax({
        //                     url: '/projectMg/contract/contractConfirm.htm',
        //                     data: {
        //                         ids: _thisTr.attr('id'),
        //                         IS: _thisTr.attr('id'),
        //                         projectCode: _thisTr.attr('projectcode'),
        //                         isContract: 'IS',
        //                     },
        //                     success: function (res) {
        //                         if (res.success) {
        //                             Y.alert('提示', '成功处理', function () {
        //                                 window.location.reload(true);
        //                             });
        //                         } else {
        //                             Y.alert('提示', res.message);
        //                         }
        //                     }
        //                 });
        //             } else {
        //                 util.ajax({
        //                     url: '/projectMg/contract/contractConfirm123.htm',
        //                     data: {
        //                         ids: _thisTr.attr('id'),
        //                         projectCode: _thisTr.attr('projectcode'),
        //                         isContract: 'IS',
        //                     },
        //                     success: function (res) {
        //                         if (res.success) {
        //
        //                             Y.alert('提示', '成功处理', function () {
        //                                 window.location.reload(true);
        //                             });
        //                         } else {
        //                             Y.alert('提示', res.message);
        //                         }
        //                     }
        //                 });
        //             }
        //         })
        //     }
        // });

    }).on('click', '.fnDelAll', function () {

        var $tr = $(this).parents('tr')

        $.when(getAllContract($tr.attr('formid')))
            .then(function (titles) {

                Y.confirm('信息提醒', '您确定要删除以下合同？<div class="fn-tal" style="max-height: 300px; overflow-y: auto">' + titles.join('<br>') + '</div>', function (opn) {
                    if (opn == 'yes') {
                        util.ajax({
                            url: '/projectMg/form/delete.htm',
                            data: {
                                formId: $tr.attr('formid')
                            },
                            success: function (res) {
                                if (res.success) {
                                    Y.alert('消息提醒', res.message, function () {
                                        window.location.reload(true);
                                    });
                                } else {
                                    Y.alert('消息提醒', res.message);
                                }
                            }
                        });
                    }
                });

            })

    }).on('click', '.fnWithdrawAll', function () {

        var $tr = $(this).parents('tr')

        $.when(getAllContract($tr.attr('formid')))
            .then(function (titles) {

                Y.confirm('信息提醒', '您确定要撤回以下合同？<div class="fn-tal" style="max-height: 300px; overflow-y: auto">' + titles.join('<br>') + '</div>', function (opn) {
                    if (opn == 'yes') {
                        util.ajax({
                            url: '/projectMg/form/cancel.htm',
                            data: {
                                formId: $tr.attr('formid')
                            },
                            success: function (res) {
                                if (res.success) {
                                    Y.alert('消息提醒', res.message, function () {
                                        window.location.reload(true);
                                    });
                                } else {
                                    Y.alert('消息提醒', res.message);
                                }
                            }
                        });
                    }
                });

            })

    })

    function getAllContract(formId) {

        var dtd = $.Deferred()

        util.ajax({
            url: '/baseDataLoad/getContractItem.json?formId=' + formId,
            success: function (res) {

                if (!res.success) {
                    Y.alert('提示', '查询合同详情失败')
                    return
                }

                var titleArr = []

                $.each(res.data.pageList, function (index, obj) {

                    titleArr.push(obj.contractName + '(' + obj.contractCode + ')')

                })

                return dtd.resolve(titleArr)

            }
        })

        return dtd.promise()

    }

    function creatList(arr) {

        if (!!!arr.length) {
            return;
        }

        var _s = '<div class="m-item"><label class="fn-csp"><input class="fn-mr5 all" type="checkbox">全选</label></div>';

        for (var i = 0; i < arr.length; i++) {

            _s += '<div class="m-item"><label class="fn-csp"><input class="fn-mr5 check" type="checkbox" value="' + arr[i].id + '">' + arr[i].contractName + '</label></div>';

        }
        $('#fnSealList').append(_s);

    }


    // 2016.10.21 新增批量确认合同
    // 2017.01.09 新增批量用印

    $('.fnBatchOPN2').on('click', function () {

        var _code
        var _type = this.className.split(' ')
        var _isPass = true
        var _typePass = true

        _type = _type[_type.length - 1]
        IDS = []
        NAMES = []
        $('input.checkbox:checked').each(function (index, el) {

            if (!!!index) {
                _code = el.getAttribute('code')
            } else {

                // projectCode 不一致
                if (_code != el.getAttribute('code')) {
                    _isPass = false
                    return false
                }

            }

            // 当前类型与按钮操作不一致
            if (!!!el.getAttribute(_type)) {
                _isPass = false
                _typePass = false
                return false
            }

            IDS.push(el.value)
            NAMES.push($(el).parent().next().next().next().text())

        })

        if (!!!IDS.length) {
            Y.alert('提示', '请选择可以操作的合同')
            return
        }

        if (!_isPass) {

            Y.alert('提示', _typePass ? '请选择同一个项目的合同进行操作' : '请选择对应操作的合同')
            return

        }

        CODE = _code
        $newSeal.removeClass('fn-hide')
        addTo$newSealList()


        // if (_type === 'confirm') {
        //
        //
        //         Y.confirm('合同确认', '是否确认执行”合同确认“操作？', function (opn) {
        //
        //         if (opn == 'yes') {
        //             Y.confirm('提示','是否需要要用印',function (_opn) {
        //                 if (_opn == 'yes') {
        //                     util.ajax({
        //                         url: '/projectMg/contract/contractConfirm.htm',
        //                         data: {
        //                             ids: IDS.join(','),
        //                             IS:IDS.join(','),
        //                             projectCode: _code,
        //                             isContract: 'IS'
        //                         },
        //                         success: function (res) {
        //                             if (res.success) {
        //                                 Y.alert('提示', '成功处理', function () {
        //                                     window.location.reload(true);
        //                                 });
        //                             } else {
        //                                 Y.alert('提示', res.message);
        //                             }
        //                         }
        //                     });
        //                 } else {
        //                     util.ajax({
        //                         url: '/projectMg/contract/contractConfirm.htm',
        //                         data: {
        //                             ids: IDS.join(','),
        //                             projectCode: _code,
        //                             isContract: 'IS'
        //                         },
        //                         success: function (res) {
        //                             if (res.success) {
        //                                 Y.alert('提示', '成功处理', function () {
        //                                     window.location.reload(true);
        //                                 });
        //                             } else {
        //                                 Y.alert('提示', res.message);
        //                             }
        //                         }
        //                     });
        //                 }
        //
        //             })
        //
        //         }
        //
        //     })
        //
        //
        // }

        if (_type === 'seal') {

            util.direct('/projectMg/stampapply/addStampApply.htm?contractId=' + IDS.join(',') + '&backurl=1')

        }


    })


    // $('#fnConfirmContract').on('click', function () {

    //     var _ids = [],
    //         _code,
    //         _isPass = true;

    //     $('input.checkbox:checked').each(function (index, el) {

    //         if (!!!index) {
    //             _code = el.getAttribute('code')
    //         } else {

    //             if (_code != el.getAttribute('code')) {
    //                 _isPass = false
    //                 return false
    //             }

    //         }

    //         _ids.push(el.value)

    //     });

    //     if (!!!_ids.length) {
    //         Y.alert('提示', '请选择可以确认的合同')
    //         return
    //     }

    //     if (!_isPass) {
    //         Y.alert('提示', '请选择同一个项目的合同再确认')
    //         return
    //     }

    //     Y.confirm('合同确认', '是否确认执行”合同确认“操作？', function (opn) {

    //         if (opn == 'yes') {

    //             util.ajax({
    //                 url: '/projectMg/contract/contractConfirm.htm',
    //                 data: {
    //                     ids: _ids.join(','),
    //                     projectCode: _code
    //                 },
    //                 success: function (res) {
    //                     if (res.success) {
    //                         Y.alert('提示', '成功处理', function () {
    //                             window.location.reload(true);
    //                         });
    //                     } else {
    //                         Y.alert('提示', res.message);
    //                     }
    //                 }
    //             });

    //         }

    //     });

    // });


    var $seal = $('#fnSealBox'),
        $sealList = $seal.find('#fnSealList');

    $seal.on('click', '.close', function () {

        $seal.addClass('fn-hide');
        $sealList.empty();
        window.location.reload(true);

    })
        .on('click', '.sure', function () {

        var _checked = $sealList.find('.check:checked');
        if (!!!_checked.length) {
            Y.alert('提示', '请选择需要用印的文件');
            return;
        }

        var _arr = [];

        _checked.each(function (index, el) {
            _arr.push(el.value);
        });
        var _contractIds = _arr.join(",");
        util.direct('/projectMg/stampapply/addStampApply.htm?contractId=' + _contractIds + '&backurl=1');

    })
        .on('click', '.all', function () {

        var _this = $(this);
        if (_this.prop('checked')) {
            $sealList.find('input').prop('checked', 'checked');
        } else {
            $sealList.find('input').removeProp('checked');
        }

    })
        .on('click', '.check', function () {
            var _this = $(this);
            if (!_this.prop('checked') && $sealList.find('.all').prop('checked')) {
                $sealList.find('.all').removeProp('checked');
            }
        });

    var $newSeal = $('#fnNewSealBox'),
        $newSealList = $('#fnNewSealList')

    
    function addTo$newSealList() {
        $newSealList.empty()
        var dom =  '<table class="m-table m-table-list fn-tac" style="text-align: center; width: 100%; margin: 20px auto 0"><tr><th width="25%">是否用印</th><th width="25%">合同名称</th></tr>'
        NAMES.forEach(function (el, index) {
            dom += '<tr class="sealTr" data-id="'+ IDS[index]+'" data-seal="IS"><td width="25%"><input type="checkbox" checked="checked"></td><td width="50%">' + el + '</td></tr>'
        })
        $newSealList.append(dom)
    }
    $newSealList.on('change', '[type="checkbox"]', function () {
        $(this).attr('checked') === 'checked' ? $(this).parents('tr').attr('data-seal','IS') : $(this).parents('tr').attr('data-seal','NO')
    })
    $newSeal.on('click', '.close', function (){
        $newSeal.addClass('fn-hide');
    }).on('click', '.sure', function (){
        var str = []
        $newSealList.find('.sealTr').each(function () {
            str.push($(this).attr('data-id') + ',' + $(this).attr('data-seal'))
        })
        submitInSeal(str)
    })

    
    function submitInSeal(str) {
        util.ajax({
            url: '/projectMg/contract/contractConfirm.htm',
            data: {
                ids: str.join(';'),
                projectCode: CODE,
                isContract: 'IS'
            },
            success: function (res) {
                $newSeal.addClass('fn-hide')
                if (res.success) {
                    Y.alert('提示', res.message, function () {
                        window.location.reload(true);
                    });
                } else {
                    Y.alert('提示', res.message);
                }
            },
            error: function () {
                $newSeal.addClass('fn-hide')
                Y.alert('提示', '网络错误');
            }
        });
    }

    var $selPerson = $('.selPerson'), $fnSelPerson = $('#fnSelPerson');
    $selPerson.click(function() {
        var _thisTr = $(this).parents('tr');
        var projectCode = _thisTr.attr('projectcode');
        var SignPostId = _thisTr.attr('signPosUserId');
        var OffsetPostId = _thisTr.attr('pledgePosUserId');
        var SignPostAccount = _thisTr.attr('signPosUserAccount');
        var OffsetPostAccount = _thisTr.attr('pledgePosUserAccount');
        var OffsetPostName = _thisTr.attr('pledgePosUserName');
        var SignPostName = _thisTr.attr('signPosUserName');

        $fnSelPerson.removeClass('fn-hide')
        $fnSelPerson.find('input').val('')
        $('#SignPostId').val(SignPostId);
        $('#OffsetPostId').val(OffsetPostId);
        $('#SignPostAccount').val(SignPostAccount);
        $('#OffsetPostAccount').val(OffsetPostAccount);
        $('#OffsetPostName').val(OffsetPostName);
        $('#SignPostName').val(SignPostName);
        $fnSelPerson.on('click', '.close', function (){
            $fnSelPerson.addClass('fn-hide');
        }).on('click', '.sure', function (){



            if($('#SignPostName').val() == '' || $('#OffsetPostName').val() == ''){
                Y.alert('提示', '请填写完整');
                return
            }
            util.ajax({
                url: '/projectMg/contract/saveRelevantPersonnel.json',
                data: {
                    projectCode: projectCode,
                    SignPostId:document.getElementById('SignPostId').value,
                    SignPostAccount:document.getElementById('SignPostAccount').value,
                    SignPostName:document.getElementById('SignPostName').value,
                    OffsetPostId:document.getElementById('OffsetPostId').value,
                    OffsetPostAccount:document.getElementById('OffsetPostAccount').value,
                    OffsetPostName:document.getElementById('OffsetPostName').value
                },
                success: function (res) {
                    $fnSelPerson.addClass('fn-hide');
                    if (res.success) {
                        Y.alert('提示', '成功处理', function () {
                            window.location.reload(true);
                        });
                    } else {
                        Y.alert('提示', res.message);
                    }
                },
                error: function () {
                    $newSeal.addClass('fn-hide')
                    Y.alert('提示', '网络错误');
                }
            });
        })
    })


    //BPM弹窗
    var BPMiframe = require('BPMiframe');
    var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
    var BPMiframeConf = {
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'selectUsers': {
            selectUserIds: '',
            selectUserNames: ''
        },
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    };

    var _chooseSignPost = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
        title: '面签岗人员'
    }));
    var $SignPostName = $('#SignPostName'),
        $SignPostId = $('#SignPostId'),
        $SignPostAccount = $('#SignPostAccount');

    $('#SignPostBtn').on('click', function () {
        _chooseSignPost.init(function (relObj) {
            $SignPostName.val(relObj.fullnames);
            $SignPostId.val(relObj.userIds);
            $SignPostAccount.val(relObj.accounts);
        });
    });

    var _chooseOffsetPost = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
        title: '抵质押岗人员'
    }));
    var $OffsetPostName = $('#OffsetPostName'),
        $OffsetPostId = $('#OffsetPostId'),
        $OffsetPostAccount = $('#OffsetPostAccount');

    $('#OffsetPostBtn').on('click', function () {
        _chooseOffsetPost.init(function (relObj) {
            $OffsetPostName.val(relObj.fullnames);
            $OffsetPostId.val(relObj.userIds);
            $OffsetPostAccount.val(relObj.accounts);
        });
    });

});