define(function (require, exports, module) {
    // 资金管理 > 差旅费报销单新增

    require('zyw/publicPage');

    require('Y-number');

    require('validate');

    var getList = require('zyw/getList');

    var BPMiframe = require('BPMiframe');

    var util = require('util');
    
    if (!!util.browserType() && util.browserType() < 10) { //IE9及以下不支持多选，加载单选上传
        require.async('tmbp/upAttachModify'); //上传
    } else {
        require.async('tmbp/upAttachModifyNew'); //上传
    }


    var fnDefaultDeptId = document.getElementById('fnDefaultDeptId'); // 当前用户部门Id

    (function () {

        var $fnFotal = $('#fnFotal'),
            $li = $fnFotal.find('li');
        $li.width(Math.floor(($fnFotal.width() - 20) / $li.length));

    })();

    $.validator.addMethod('moneycanbenull', function (value, element, param) {
        if (value === '') {
            return true
        }
        return (+value.replace(/\,/g, '') >= 0) ? true : false;
    }, '金额必须大于0');

    // ------ 费用计算 start

    var $fnListTbody = $('#fnListTbody');

    $fnListTbody.on('click', '.fnListDel.del', function () {

        $(this).parents('tr').remove();

        doCount();

    }).on('change', '.fnMakeMoney', function () {

        var self = this,
            _max = self.getAttribute('maxval')

        setTimeout(function () {
            if (!!_max && +_max < +self.value) {
                self.value = _max
            }
            doCount();
        }, 50);

    }).on('blur', '.fnDateS', function () {

        var $parents = $(this).parents('tr'),
            $dataEnd = $parents.find('.fnDateE');

        $dataEnd.attr('onclick', 'laydate({"min": "' + this.value + '"})');

    }).on('blur', '.fnDateE', function () {

        var $parents = $(this).parents('tr'),
            $dataStart = $parents.find('.fnDateS');

        $dataStart.attr('onclick', 'laydate({"max": "' + this.value + '"})');

    }).on('blur', '.fnDateS, .fnDateE', function () {

        var $parents = $(this).parents('tr'),
            _s = $parents.find('.fnDateS').val(),
            _e = $parents.find('.fnDateE').val(),
            $day = $parents.find('.fnNeedCountDay')

        if (!!!$day.length || !!!_s || !!!_e) {
            return
        }

        countDay(_s, _e, $day)

    }).on('blur', '.taxAmountMax', function () {

        var $this = $(this)

        setTimeout(function () {

            var $input = $this.parents('tr').find('.taxAmount')
            $input.rules('add', {
                required: true,
                max: +($this.val() || '').replace(/\,/g, ''),
                min: 0,
                messages: {
                    required: '必填',
                    max: '不能大于' + $this.attr('maxname'),
                    min: '不能为负值'
                }
            })
            if (!!$input.val()) {
                $input.valid()
            }

        }, 100)

    });

    setTimeout(function () {
        $fnListTbody.find('.taxAmountMax').each(function (index, el) {
            $(el).trigger('blur')
        });
    }, 500)

    function countDay(start, end, $day) {

        var _start = (new Date(start || null)).getTime(),
            _end = (new Date(end || null)).getTime();

        var _d = (_end - _start) / (1000 * 60 * 60 * 24)

        $day.val(_d + 1).trigger('blur')

    }

    $('#fnListAdd').on('click', function () {

        $fnListTbody.find('input').each(function (index, el) {
            var $this = $(this)
            $this.rules('remove', 'required')
            $this.rules('remove', 'min')
            $this.rules('remove', 'max')
            $this.removeAttr('aria-describedby aria-required').removeClass('error-tip')
                .next('b.error-tip').remove();

        });


        var $lastTr = $fnListTbody.find('tr').last();

        var $tr = $('<tr></tr>').html($lastTr.html()).attr('diyname', $lastTr.attr('diyname'));;

        $tr.find('input').each(function (index, el) {
            el.value = '';
        });

        $tr.find('.fnDateS, .fnDateE').attr('onclick', 'laydate()');

        $tr.find('.fnListDel').addClass('del');


        if($(this).attr('isMy') == 'true'){
            if($('.my_expenseType').prev('.my_1step').length)
                $tr.find('.my_expenseType').hide();
        }

        // $tr.find('b.error-tip').remove();

        // $tr.find('.fnInput').each(function (index, el) {
        //     $(this).removeAttr('aria-describedby aria-required').removeClass('error-tip')
        // });

        // 2016.12.16 默认值？
        var fnDefaultListOtgName = document.getElementById('fnDefaultListOtgName')
        if (!!fnDefaultListOtgName) {
            $tr.find('.fnListSearchOrgName').val(fnDefaultListOtgName.value)
            $tr.find('.fnListSearchOrgId').val(document.getElementById('fnDefaultListOtgId').value)
        }

        $fnListTbody.append($tr);

        util.resetName();

        $fnListTbody.find('tr').find('.fnInput').each(function (index, el) {

            var $this = $(this)

            $this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })

        });

        $fnListTbody.find('tr').find('.fnMakeMoney').each(function (index, el) {

            var $this = $(this)

            if (!$this.hasClass('canbenull')) {
                $this.rules('add', {
                    min: 0.01,
                    messages: {
                        min: '必填大于0'
                    }
                })
            } else {
                $this.rules('add', {
                    moneycanbenull: true
                })
            }

        });

        $fnListTbody.find('.taxAmount').each(function (index, el) {

            var $this = $(this),
                $max = $this.parents('tr').find('.taxAmountMax')
                // 先移除规则，再绑定规则
                // $this.rules('remove', 'max')
            $this.rules('add', {
                required: true,
                max: +($max.val() || '').replace(/\,/g, ''),
                min: 0,
                messages: {
                    required: '必填',
                    max: '不能大于' + $max.attr('maxname'),
                    min: '不能为负值'
                }
            })
            if (!!$this.val()) {
                $this.valid()
            }

        })

        $tr.find('.fnListSearchOrgName').trigger('change')

    });

    function setDOMHtml(id, html) {

        var _dom = document.getElementById(id);
        if (_dom) {
            _dom.innerHTML = html;
        }

    }

    function doCount() {

        var total = {},
            totalNum = 0,
            trTotal = 0;

        $fnListTbody.find('tr').each(function (index, el) {

            trTotal = 0;

            var $this = $(this)

            $this.find('.fnMakeMoney:not(.not,.total)').each(function (i, e) {

                //是否被setName了
                var _name = e.name;

                if (_name.indexOf('.') > -1) {
                    _name = _name.substring(_name.indexOf('.') + 1);
                }

                if (e.className.indexOf('nottotal') == -1) {
                    trTotal = util.accAdd(trTotal, +e.value.replace(/\,/g, ''))
                }

                if (total[_name]) {

                    total[_name] = util.accAdd(total[_name], +((e.value || '0').replace(/\,/g, '')))
                        // total[_name] += +(e.value || 0);

                } else {
                    total[_name] = +((e.value || '0').replace(/\,/g, ''))
                        // total[_name] = +(e.value || 0);

                }

            }).end().find('.fnMakeMoney.total').val(util.num2k(trTotal))

            if ($this.find('.fnMakeMoney.total').length) {
                $this.find('.fnMakeMoney.total').valid()
            }

            totalNum += trTotal;

        });

        $.each(total, function (key, val) {

            setDOMHtml(key, util.num2k(val.toFixed(2)));

        });

        var _sNum = util.num2k(totalNum.toFixed(2)),
            _uNum = Y.Number(totalNum).digitUppercase()

        $('#fnAmount').val(_sNum).html(_sNum);
        $('#fnAmountChine').val(_uNum).html(_uNum);
        $('#fnAmounts').val(_sNum).html(_sNum);
        $('#fnAmountChines').val(_uNum).html(_uNum);

    }

    // 查看
    if (document.getElementById('fnIsView')) {
        var LOOKTOTALOBJ = {};
        $fnListTbody.find('tr').each(function (index, el) {

            $(this).find('td.fnDoCount').each(function (index, el) {

                var _name = this.className.split(' ')[1];
                if (LOOKTOTALOBJ[_name]) {

                    LOOKTOTALOBJ[_name] += +this.innerHTML.replace(/\,/g, '');

                } else {
                    LOOKTOTALOBJ[_name] = +this.innerHTML.replace(/\,/g, '');
                }

            });

        });

        $.each(LOOKTOTALOBJ, function (key, val) {
            setDOMHtml(key, util.num2k(val.toFixed(2)));
        });

    }

    if (document.getElementById('showAmount') && !!!$('#fnListTbody .fnMakeMoney').length) {
        var fnAmount = +(document.getElementById('fnAmount').innerHTML.replace(/\,/g, '') || '')
        setTimeout(function () {
            var _uNum = Y.Number(+fnAmount).digitUppercase();
            $('#fnAmountChine').val(_uNum).html(_uNum);
        }, 0);
    } else {
        setTimeout(function () {
            doCount()
        }, 0);
    }

    // ------ 费用计算 end

    // ------ 选择部门 start
    // 初始化弹出层
    var singleSelectOrgDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true', {
        'title': '组织',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'arrys': [], //[{id:'id',name:'name'}];
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });

    if (!!fnDefaultDeptId) {
        singleSelectOrgDialog.resetSrc('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true&selectTop=Y')
    }

    // 添加选择后的回调，以及显示弹出层
    $('#fnChooseOrg').on('click', function () {

        singleSelectOrgDialog.init(function (relObj) {

            // 2016.12.08 差旅、费用支付 不能能选到公司领导
            // 转账怎么办呢？
            if (relObj.orgName === '公司领导') {
                Y.alert('提示', '并不能选择“公司领导”')
                return
            }

            if ((relObj.orgName === '公司' || relObj.orgName === '重庆进出口信用担保有限公司') && !!fnDefaultDeptId) {
                chooseOrgCallBack(fnDefaultDeptId.value);
            } else {
                chooseOrgCallBack(relObj.orgId);
            }

            $('#fnApplyDeptId').val(relObj.orgId);
            $('#fnApplyDeptName').val(relObj.orgName).trigger('blur');
            // orgId.value = relObj.orgId;
            // orgName.value = relObj.orgName;

        });

    });

    var DOM_APPLY_DEPTID = document.getElementById('fnApplyDeptId'),
        DOM_DEPTHEAD = document.getElementById('deptHead')

    if (DOM_APPLY_DEPTID && DOM_DEPTHEAD && !!!DOM_DEPTHEAD.value) {
        chooseOrgCallBack(DOM_APPLY_DEPTID.value)
    }

    // chooseOrgCallBack($('#fnApplyDeptId').val())

    function chooseOrgCallBack(id) {

        if (!!!id) {
            return
        }

        $.ajax({
            url: '/baseDataLoad/deptFzr.json?deptId=' + id,
            success: function (res) {

                if (res.success) {

                    document.getElementById('deptHead').value = res.data.name;

                } else {
                    Y.alert('提示', res.message);
                }

            }
        });

    }

    // ------ 选择部门 end


    // ------ 带出余额 start

    $('body').on('blur', '#applicationTime', function () {
        $('.fnListSearchOrgName').trigger('change');
    }).on('change', '.fnListSearchOrgName', function () {

        if (!!!this.value) {
            return
        }

        var $this = $(this),
            _deptId = $this.parent().find('.fnListSearchOrgId').val(),
            $category = $this.parent().parent().parent().find('.expenseType'),
            categoryId = 1;

        if (!!$category.val()) {
            categoryId = $category.val();
        }
        $.ajax({
            url: '/baseDataLoad/deptBudgetBalance.json',
            data: {
                budgetDeptId: _deptId,
                applicationTime: document.getElementById('applicationTime').value,
                categoryId: categoryId
            },
            success: function (res) {
                if (res.success) {
                    $this.parents('tr').find('.balance').val(res.data.balance);
                }
            }
        })

    })

    // 默认数据
    setTimeout(function () {
        $('.fnListSearchOrgName').each(function (index, el) {
            $(this).trigger('change')
        });
    }, 0);
    // ------ 带出余额 end

    // ------ 收款人 start

    var _payeeList = new getList();
    _payeeList.init({
        title: '选择收款人',
        ajaxUrl: '/baseDataLoad/payee.json',
        btn: '#fnChoosePayee',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.userName}}">{{(item.userName && item.userName.length > 6)?item.userName.substr(0,6)+\'\.\.\':item.userName}}</td>',
                '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 10)?item.bankName.substr(0,10)+\'\.\.\':item.bankName}}</td>',
                '        <td title="{{item.bankAccountNo}}">{{(item.bankAccountNo && item.bankAccountNo.length > 25)?item.bankAccountNo.substr(0,25)+\'\.\.\':item.bankAccountNo}}</td>',
                '        <td><a class="choose" userId="{{item.userId}}" userName="{{item.userName}}" bankName="{{item.bankName}}" bankAccountNo="{{item.bankAccountNo}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['收款人：',
                '<input class="ui-text fn-w100" type="text" name="userName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>收款人</th>',
                '<th>开户银行</th>',
                '<th>银行账号</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 4
        },
        callback: function ($a) {

            setPayeeValue('payeeId', $a.attr('userId'))
            setPayeeValue('payee', $a.attr('userName'))
            setPayeeValue('fnChoosePayeeBank', $a.attr('bankName'))
            setPayeeValue('fnChoosePayeeBankAccount', $a.attr('bankAccountNo'))

        }
    });

    function setPayeeValue(id, val) {

        var $dom = $('#' + id)

        if (!!!$dom.length) {
            return
        }

        $dom.val(val).trigger('blur')

    }

    // ------ 收款人 end

    if (document.getElementById('auditForm')) {

        var auditProject = require('/js/tmbp/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');
        // 财务审核，驳回意见非必填
        // 审核时可以改变数据
        var $fnAuditChange = $('#fnAuditChange')

        if (!!$fnAuditChange.length) {

            util.resetName();

            $fnAuditChange.validate(util.validateDefault)

            $fnAuditChange.find('.fnMakeMoney.fnInput').each(function (index, el) {

                var $this = $(this)

                $this.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })

            });

            $fnAuditChange.find('.fnMakeMoney').each(function (index, el) {

                var $this = $(this)

                if (!$this.hasClass('canbenull')) {
                    $this.rules('add', {
                        min: 0.01,
                        messages: {
                            min: '必填大于0'
                        }
                    })
                } else {
                    $this.rules('add', {
                        moneycanbenull: true
                    })
                }

            });

            $('#fnReimburseReasonInput').on('change', function () {
                document.getElementById('fnReimburseReason').value = this.value
            })

            if (!!$fnAuditChange.find('.fnMakeMoney').length) {

                _auditProject.addAuditBefore(function (dtd) {

                    if ($fnAuditChange.valid()) {

                        // ------ 费用支付的冲销验证 start 

                        var $fnPublicCharge = $('#fnPublicCharge') // 对公
                        var $fnPublicChargeTotall = $('#fnPublicChargeTotall') // 对公总冲销
                        var $fnPrivateCharge = $('#fnPrivateCharge') // 对私
                        var $fnPrivateChargeTotall = $('#fnPrivateChargeTotall') // 对私总冲销

                        // 对公冲销
                        if ($fnPublicCharge.find('.fnToCharge').prop('checked')) {

                            var __public = 0
                            $('#fnListTbody').find('.BXamount').each(function (index, el) {
                                __public += +el.value
                            })

                            // 必须选择

                            if (+$fnPublicChargeTotall.val() == 0) {
                                Y.alert('提示', '请选择要冲销的金额')
                                return;
                            }

                            // 当报销金额明细里选择了退预付款/冲预付款 的时候 报销金额合计要和冲销金额相等

                            var __publicDep = ['退预付款', '冲预付款'],
                                __publicDepIs = false

                            $('#fnListTbody option:selected').each(function (i, e) {

                                var _text = e.innerHTML.replace(/\s/g, '')
                                if ($.inArray(_text, __publicDep) >= 0) {
                                    __publicDepIs = true
                                    return false
                                }

                            })

                            if (__publicDepIs) {
                                if (__public != +$fnPublicChargeTotall.val()) {
                                    Y.alert('提示', '报销总金额必须等于未冲销金额（勾选的金额）')
                                    return;
                                }
                            } else {

                                if (__public < +$fnPublicChargeTotall.val()) {
                                    Y.alert('提示', '报销总金额不能小于未冲销金额（勾选的金额）')
                                    return;
                                }

                            }

                        }

                        // 对私冲销
                        if ($fnPrivateCharge.find('.fnToCharge').prop('checked')) {

                            var __private = 0
                            $('#fnListTbody').find('.BXamount').each(function (index, el) {
                                __private += +el.value
                            })

                            if (__private > +$fnPrivateChargeTotall.val()) {
                                Y.alert('提示', '报销总金额不能大于未冲销金额')
                                return;
                            }

                        }

                        // ------ 费用支付的冲销验证 end

                        // 还原原来的 name
                        $('[diyname="self"] [name]').each(function (index, el) {

                            var _name = el.name,
                                _nameArr = _name.split('.');

                            _name = _nameArr[_nameArr.length - 1];

                            el.name = _name;

                        })

                        // 暂存数据
                        util.ajax({
                            url: $fnAuditChange.attr('action'),
                            data: $fnAuditChange.serializeJCK(),
                            success: function (res) {
                                if (res.success) {
                                    dtd.resolve()
                                } else {
                                    dtd.reject(res)
                                }
                            }
                        })

                    } else {
                        dtd.reject({
                            message: '申请明细填写错误'
                        })
                    }

                })

            }

        }

    }

    // 批量操作显示提示
    $('#list').on('click', '.fnCheckAll, .fnCheck', setCheckTip)

    function setCheckTip() {

        var CHECKED_MONEY = 0,
            CHECKED_SIZE = 0

        $('#list tbody .fnCheck:checked').each(function (index, el) {
            CHECKED_MONEY = util.accAdd(CHECKED_MONEY, +(el.getAttribute('money') || ''))
                // CHECKED_MONEY += +(el.getAttribute('money') || '')
            CHECKED_SIZE++
        });

        $('#fnCheckTip').html(!!CHECKED_SIZE ? '共选了<span class="fn-f30 fn-pl5 fn-pr5 fn-fwb">' + CHECKED_SIZE + '</span>个单据，总金额为<span class="fn-f30 fn-pl5 fn-pr5 fn-fwb">' + util.num2k(CHECKED_MONEY.toFixed(2)) + '</span>元' : '')

    }

    var BPMiframe = require('BPMiframe');

    var selectOrgDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true' + (!!fnDefaultDeptId ? '&selectTop=Y' : ''), {
        'title': '组织',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'arrys': [], //[{id:'id',name:'name'}];
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });
    // 添加选择后的回调，以及显示弹出层
    $('body').on('click', '.fnListSearchOrgChoose2', function () {
        var $this = $(this),
            _isSingle = $this.attr('single') ? true : false,
            _$parent = $this.parent(),
            _$id = _$parent.find('.fnListSearchOrgId'),
            _$name = _$parent.find('.fnListSearchOrgName');

        //这里也可以更新已选择机构
        selectOrgDialog.obj.arrys = [{
            id: _$id.val(),
            name: _$name.val()
        }];

        selectOrgDialog.init(function (relObj) {

            // 2016.12.08 差旅、费用支付 不能能选到公司领导
            if (relObj.orgName === '公司领导') {
                Y.alert('提示', '并不能选择“公司领导”')
                return
            }

            _$id.val(relObj.orgId).trigger('change').trigger('blur');
            _$name.val(relObj.orgName).trigger('change').trigger('blur');

        });

    });

});