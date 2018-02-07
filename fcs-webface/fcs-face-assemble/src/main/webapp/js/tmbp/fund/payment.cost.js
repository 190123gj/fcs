/**
 *
 * 费用支付 规则
 *
 * 申请部门 ajax 查询出 部门负责人 新增表服务器带出来该数据
 *
 * 是否是 公务卡 影响 费用方向（对公、对私） 是 不需要填写对公、对私的账号，等同于没有冲销；费用类型也被限制 ·*
 * 隐藏费用方向的选择、银行账户等信息，清空冲销内 否 需要填写对公、对私的账号
 *
 * 费用方向 影响冲销类型
 *
 * 是否 确认冲销 影响 费用类型、报销总金额 是 预付款（对公） 报销总金额 大于等于 勾选的金额 借款（对私） 报销总金额 小宇等于 未冲销金额
 *
 */

define(function (require, exports, module) {
    // 资金管理 > 费用支付申请单新增

    require('./payment.common')

    require('Y-msg');
    require('validate');

    var template = require('arttemplate');

    var getList = require('zyw/getList');
    // fnOfficialCard
    var OFFICIAL_CARD_DISABLED = '借款2、还款、预付款、退预付款、冲预付款、工资、养老保险、失业保险、医疗保险、工伤保险、生育保险、住房公积金、补充医疗保险、企业年金、补充养老保险、劳务费、福利费（其他）'.split('、') // 公务卡需要屏蔽的
    var NOT_OFFICIAL_CARD_DISABLED = '还款、退预付款、冲预付款'.split('、') // 非公务卡需要屏蔽的
    var ALL_COST_TYPE = [] // 所有可选的费用类型
    var $fnOfficialCard = $('#fnOfficialCard') // 是否是公务卡
    var PUBLIC_COST_TYPE_DISABLED = '借款、还款、退预付款'.split('、') // 对公类型需要屏蔽的
    var PRIVATE_COST_TYPE_ABLED = '还款'.split('、') // 冲销时，对私类型可以选择的
    var $fnPublicCharge = $('#fnPublicCharge') // 对公冲销
    var $fnPublicChargeTotall = $('#fnPublicChargeTotall')
    var $fnPrivateCharge = $('#fnPrivateCharge') // 对私冲销
    var $fnPrivateChargeTotall = $('#fnPrivateChargeTotall')

    var $accountName = $('#accountName') // 对公银行账户用户名
    var $payee = $('#payee') // 对私银行账户用户名

    // 对公冲销 选择金额 计算总金额

    function getPublicChargeTotall() {

        var _total = 0

        $fnPublicCharge.find('td input:checked').each(function (index, el) {
            _total += +(el.getAttribute('money') || '').replace(/\,/g, '')
        })

        $fnPublicChargeTotall.val(_total)

    }

    // 还原已选择的金额
    getPublicChargeTotall()

    $fnPublicCharge.on('click', 'td input', function () {
        if (!!!$fnPublicCharge.find('.fnToCharge:checked').length) {
            this.checked = false
            return
        }
        getPublicChargeTotall()
    }).on('click', '.fnToCharge', setCostType)

    // 对私冲销
    $fnPrivateCharge.on('click', '.fnToCharge', setCostType)

    // 获取所有费用类型
    $('#fnAllCostType').find('option').each(function (index, el) {
        ALL_COST_TYPE.push({
            value: el.value,
            text: el.innerHTML.replace(/\s/g, '')
        })
    })

    /**
	 * 2016.11.14 费用类型 的限制
	 *
	 * 公务卡、非公务卡 来一个限制
	 *
	 * 选择冲销 再来一个限制
	 *
	 */

    function setCostType(callback , bool) {

        var _list = [],
            _noArr = [],
            _canArr = []

        if ($fnOfficialCard.prop('checked')) {
            _noArr = _noArr.concat(OFFICIAL_CARD_DISABLED)
        } else {
            _noArr = _noArr.concat(NOT_OFFICIAL_CARD_DISABLED)
            if ($('.fnIsPP:checked').val() == 'PUBLIC') {
                // 对公
                _noArr.push('借款')
            } else {
                _noArr.push('预付款')
            }
        }

        if (!!$fnPublicCharge.find('.fnToCharge:checked').length) {
            _noArr = _noArr.concat(PUBLIC_COST_TYPE_DISABLED)

            // 退预付款 放出来 预付款 屏蔽
            _noArr = _noArr.join('、').replace(/退预付款、?/g, '').split('、')
            _noArr.push('预付款')
        }

        if (!!$fnPrivateCharge.find('.fnToCharge:checked').length) {
            _canArr = _canArr.concat(PRIVATE_COST_TYPE_ABLED)
        }

        _list = filterCostType(_noArr, _canArr)

        $('.expenseType').each(function (index, el) {

            var $select = $(this)

            $select.html(creatCostType(_list, $select.val())).trigger('change')



        });
        setOptionType();
    }

    setCostType()

    function filterCostType(noArr, canArr) {

        var _arr = []

        if (!!canArr.length) {

            $.each(ALL_COST_TYPE, function (index, obj) {

                if ($.inArray(obj.text, canArr) >= 0) {

                    _arr.push(obj)

                }

            })

        } else {

            $.each(ALL_COST_TYPE, function (index, obj) {

                if ($.inArray(obj.text, noArr) < 0) {

                    _arr.push(obj)

                }

            })

        }

        return _arr

    }

    function creatCostType(list, select) {

        var _html = ''

        $.each(list, function (index, obj) {

            _html += '<option value="' + obj.value + '" ' + ((obj.value == select) ? 'selected' : '') + '>' + obj.text + '</option>'

        })

        return _html

    }

    var util = require('util'),
        $form = $('#form')

    var validObj = {
        rules: {},
        messages: {}
    };

    util.resetName();

    if (document.getElementById('showAmount')) {
        var fnAmount = document.getElementById('fnAmount').value;
        setTimeout(function () {
            $('#fnAmountChine').text(Y.Number(fnAmount).digitUppercase());
        }, 0)
    }

    // ------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender(); // 初始化侧边栏

    $('.fnInput:visible').each(function (index, el) {

        util.ObjAddkey(validObj.rules, el.name, {
            required: true
        });
        util.ObjAddkey(validObj.messages, el.name, {
            required: '必填'
        });

        if (el.className.indexOf('fnMakeMoney') >= 0) {

            util.ObjAddkey(validObj.rules, el.name, {
                min: 0.01
            });
            util.ObjAddkey(validObj.messages, el.name, {
                min: '必填大于0'
            });

        }

    })

    $form.validate($.extend({}, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {

            if (element.hasClass('fnErrorAfter')) {

                element.after(error)

            } else {

                element.parent().append(error)

            }

        }
    }, validObj))

    $form.on('click', '.doSubmit', function () {

        var _isSave = util.hasClass(this, 'save')

        document.getElementById('status').value = _isSave ? 'DRAFT' : 'SUBMIT'

        // 避免误触发
        setTimeout(function () {

            if (!_isSave && !$form.valid()) {
                Y.alert('提示', '表单未填写完整', function () {
                    $('input.error-tip').eq(0).focus()
                })
                return
            }

            // 还原原来的 name
            $('[diyname="self"] [name]').each(function (index, el) {

                var _name = el.name,
                    _nameArr = _name.split('.')

                _name = _nameArr[_nameArr.length - 1]

                el.name = _name

            });


            if (!_isSave) {

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

                Y.confirm('提示', '提交后，费用类型将不可更改为其它流程的费用类型，是否继续？', function (opt) {
                    if (opt == 'yes') {
                        doSave(_isSave)
                    }
                })
            } else {
                doSave(_isSave)
            }

        }, 500)

    }).on('change', '.expenseType', function () {

        $(this).parents('tr').find('.fnListSearchOrgName').trigger('change')
        showCX()

    }).on('blur', '#accountName, #payee', function () {

        if (!!!this.value) {
            $fnPrivateCharge.html('')
            $fnPublicCharge.html('')
            return
        }

        getChargeInfo(this.value)

    }).on('click', '.fnMoreChargeInfo', function () {
        getChargeInfo(document.getElementById(this.getAttribute('toid')).value, true)
    })

    setTimeout(function () {
        $('.expenseType').each(function (index, el) {
            $(this).trigger('change')
        });
    }, 0)

    function getChargeInfo(name, isMore) {

        $fnPublicChargeTotall.val('')

        var _direction = $('.fnIsPP:checked').val()

        $.get('/baseDataLoad/expenseApplicationReverse.json', {
            formId: document.getElementById('formId').value || 0,
            payee: name,
            direction: _direction, // PRIVATE PUBLIC
            getLoginUser: !!isMore,
            expenseApplicationId: document.getElementById('expenseApplicationId').value
        })
            .then(function (res) {

                if (!res.success) {
                    return
                }

                res.data.isMore = !!isMore

                if (_direction == 'PRIVATE') {
                    if (res.data.waitReverseAmount.amount > 0) {
                        $fnPrivateCharge.html(template('fnPrivateChargeT', res.data))
                    } else {
                        $fnPrivateCharge.html('')
                    }
                    $fnPrivateChargeTotall.val(res.data.waitReverseAmount.amount)
                } else {
                    if (res.data.waitReverseAmount.amount > 0) {
                        $fnPublicCharge.html(template('fnPublicChargeT', res.data))
                    } else {
                        $fnPublicCharge.html('')
                    }
                }

                setCostType()

            })

    }

    function doSave(isSave) {

        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {

                if (res.success) {
                    //本地存储
                    _setItem()
                    // 审核走流程
                    if (isSave) {
                        Y.alert('提示', '操作成功', function () {
                            window.location.href = '/fundMg/expenseApplication/ealist.htm';
                        });
                    } else {

                        util.ajax({
                            url: '/projectMg/form/submit.htm',
                            data: {
                                formId: res.form.formId,
                                _SYSNAME: 'FM'
                            },
                            success: function (xhr) {

                                Y.alert('提示', xhr.success ? (xhr.message + '<br>单据号：' + res.billNo) : xhr.message, function () {
                                    window.location.href = '/fundMg/expenseApplication/ealist.htm';
                                })

                            }
                        })

                    }

                } else {
                    Y.alert('操作失败', res.message);
                    // 更新 预算余额
                    $('.fnListSearchOrgName').trigger('change');
                }

            }
        });
    }

    // 公务卡支付
    $fnOfficialCard.on('click', function () {

        if (this.checked) {

            $('#fnIsPublic, #fnIsPrivate, #fnIsDirection').addClass('fn-hide').find('.fnIsPP').eq(0).trigger('click')
            hidePPDom($fnIsPublic)
            hidePPDom($fnIsPrivate)

            setCostType()

        } else {
            $('#fnIsDirection').removeClass('fn-hide').find('.fnIsPP').eq(0).trigger('click')
            showPPDom($fnIsPublic)

            setCostType()

        }

    })

    // ------ 对公对私 start
    var $fnIsPublic = $('#fnIsPublic'),
        $fnIsPrivate = $("#fnIsPrivate")

    $('.fnIsPP').on('click', function () {

        $fnPublicCharge.html('')
        $fnPrivateCharge.html('')

        var _val = this.value
        if (_val == 'PUBLIC') {

            showPPDom($fnIsPublic)
            hidePPDom($fnIsPrivate)
            if ($accountName.val() && !$fnOfficialCard.prop('checked')) {
                $accountName.trigger('blur')
            }

        } else {

            showPPDom($fnIsPrivate)
            hidePPDom($fnIsPublic)
            if ($payee.val() && !$fnOfficialCard.prop('checked')) {
                $payee.trigger('blur')
            }

        }
        setCostType();
    });


    //显示和隐藏，添加隐藏校验
    function showPPDom($div) {

        $div.removeClass('fn-hide').find('input.ui-text').removeProp('disabled').each(function (index, el) {
            $(this).rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })
        })

    }

    function hidePPDom($div) {

        $div.addClass('fn-hide').find('input.ui-text').prop('disabled', 'disabled').each(function (index, el) {
            $(this).rules('remove', 'disabled')
        });

    }
    // ------ 对公对私 end

    // ------ 选择银行 start
    var bankList = new getList();
    bankList.init({
        title: '选择开户银行',
        ajaxUrl: '/baseDataLoad/bankMessage.json',
        btn: '#fnChooseBank',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 6)?item.bankName.substr(0,6)+\'\.\.\':item.bankName}}</td>',
                '        <td title="{{item.accountNo}}">{{(item.accountNo && item.accountNo.length > 25)?item.accountNo.substr(0,25)+\'\.\.\':item.accountNo}}</td>',
                '        <td title="{{item.accountName}}">{{(item.accountName && item.accountName.length > 6)?item.accountName.substr(0,6)+\'\.\.\':item.accountName}}</td>',
                '        <td><a class="choose" bankId="{{item.bankId}}" bankName="{{item.bankName}}" bankAccount="{{item.accountNo}}" accountName="{{item.accountName}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['开户银行：',
                '<input class="ui-text fn-w100" type="text" name="bankName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>开户银行</th>',
                '<th>账户号码</th>',
                '<th>户名</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 4
        },
        callback: function ($a) {
            $('#bankName').val($a.attr('bankName'));
            $('#bankId').val($a.attr('bankId'));
            $('#bankAccount').val($a.attr('bankAccount'));
            $('#accountName').val($a.attr('accountName'));
        }
    });

    var $fnBank;
    var $fnTbody = $('#fnListTbody0');
    $fnTbody.on('click', '.fnChooseDetailBank', function () {
        $fnBank = $(this).parent().parent();
    });
    var _dBankList = new getList();
    _dBankList.init({
        title: '选择开户银行',
        ajaxUrl: '/baseDataLoad/bankMessage.json',
        btn: '.fnChooseDetailBank',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 6)?item.bankName.substr(0,6)+\'\.\.\':item.bankName}}</td>',
                '        <td title="{{item.accountType}}">{{(item.accountType && item.accountType.length > 6)?item.accountType.substr(0,6)+\'\.\.\':item.accountType}}</td>',
                '        <td title="{{item.accountNo}}">{{(item.accountNo && item.accountNo.length > 25)?item.accountNo.substr(0,25)+\'\.\.\':item.accountNo}}</td>',
                '        <td><a class="choose" bankId="{{item.bankId}}" bankName="{{item.bankName}}" bankAccount="{{item.accountNo}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['开户银行：',
                '<input class="ui-text fn-w100" type="text" name="bankName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>开户银行</th>',
                '<th>资金类型</th>',
                '<th>账户号码</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 4
        },
        callback: function ($a) {
            $fnBank.find('.detailbankId').val($a.attr('bankId'));
            $fnBank.find('.detailbankName').val($a.attr('bankName'));
            $fnBank.find('.detaibankAccount').val($a.attr('bankAccount'));
        }
    });
    // ------ 选择银行 end

    // ------ 收款人 预付款信息 start
    $('#fnUserId').on('change', function () {

        util.ajax({
            url: '/baseDataLoad/expenseApplication.json',
            data: {
                expenseType: 1
            },
            success: function (res) {

                if (res.success) {
                    document.getElementById('fnAdvance').innerHTML = template('t-advance', {
                        list: res.data
                    });
                }

            }
        });

    });
    // ------ 收款人 预付款信息 end

    // ------ 预付款相关 操作 start
    var $fnAdvance = $('#fnAdvance');

    $fnAdvance.on('blur', '.fnMakeMoney', function () {

        var self = this;
        setTimeout(function () {

            if (+self.value > +self.getAttribute('maxmoney')) {
                self.value = self.getAttribute('maxmoney');
            }

        }, 0);

    }).on('click', '.fnIsCX', function () {

        var _isCX = ($fnAdvance.find('.fnIsCX:checked').val() == 'IS') ? true : false;

        if (_isCX) {
            $fnAdvance.find('#fnCXList').removeClass('fn-hide');
        } else {
            $fnAdvance.find('#fnCXList').addClass('fn-hide');
        }

    });

    // ------ 预付款相关 操作 end

    // var signalArr;
    // $.ajax({
    // url: '/projectMg/riskWarning/querySignal.json',
    // type: 'get',
    // dataType: 'json',
    // data: {},
    // success: function(res) {
    // if (res.success) {
    // signalArr = res.data.signals;
    // }
    // }
    // });

    // $('.expenseType0').on('change', function() {
    // // alert($(this).val());
    // var categoryId = $(this).val();
    // var $etype = $(this);
    // util.ajax({
    // url: '/baseDataLoad/costCategoryById.json',
    // data: {
    // categoryId: categoryId
    // },
    // success: function(res) {
    // if (res.success) {
    // alert(res.accountCode);
    // $etype.parent().parent().find('.account').val(res.accountCode + " " +
    // res.accountName);
    // }
    // }
    // })
    // });

    function showCX() {
        // 2016.11.14 冲销的方式换了
        return;

        var expenseType = [];
        $('[name*="expenseType"]').each(function () {
            // expenseType = expenseType + "," + $(this).val();
            expenseType.push(this.value)
        });

        document.getElementById('fnAdvance').innerHTML = ''; // 先清空

        $.get('/baseDataLoad/expenseApplication.json', {
            formId: $('#formId').val(),
            expenseType: expenseType.join(',')
        })
            .then(function (res) {
                if (res.success) {
                    document.getElementById('fnAdvance').innerHTML = template('t-advance', {
                        list: res.data.list,
                        allcx: res.data.allcx
                    })
                }
            })

        // util.ajax({
        // url: '/baseDataLoad/expenseApplication.json',
        // data: {
        // formId: $('#formId').val(),
        // expenseType: expenseType.join(',')
        // },
        // success: function (res) {

        // if (res.success) {
        // document.getElementById('fnAdvance').innerHTML =
        // template('t-advance', {
        // list: res.data.list,
        // allcx: res.data.allcx
        // });
        // }

        // }
        // });

    }
    // showCX();
    // //js引擎
    var template = require('arttemplate');
    // 弹窗
    require('Y-window');
    var data = eval($('#costCategorysJson').val());

    $('.expenseTypeBtn').click(function(event) {

        var $this = $(this);

        $('body').Y('Window', {
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.close',
            content: template('Script', {
                success: 'true',
                list: data
            })
        });

        var Box = Y.getCmp('modalwnd'),
            Arr = new Array(),
            ArrName = new Array();

        Box.wnd.on('click', '.confirm', function() {

            Box.wnd.find(':checked').each(function(index, el) {

                var $el = $(el);
                Arr.push($el.val());
                ArrName.push($el.attr('valName'))

            });

            $this.siblings('.expenseName').val(ArrName.join(','));
            $this.siblings('.expenseType').val(Arr.join(','));

            Box.close();

        })

    });


    $(".confirmBranchPay").click(function(){

        var billNo = $(this).attr("billNo");

        Y.confirm('提示', '确认付款['+billNo+']？', function(opn) {
            if (opn == 'yes') {
                util.ajax({
                    url: '/fundMg/expenseApplication/conformBranchPay.json',
                    data : {'billNo' : billNo},
                    success: function(res) {
                        if (res.success) {
                            window.location.reload(true);
                        } else {
                            Y.alert('提示', res.message);
                        }
                    }
                });
            }
        });
    });

    /**
     * 大小类
     * 隐藏option
     * @param index zhurui 2017-5-11
     */

    var isSetTwo = false;
    var global_temp = '';
    function tabOption(index){
        //劳资及税金
        var indexToHide = index;
        var $obj = $('.my_original'), $option = $obj.find('option');
        var domFirst = '<select class="my_1step" name="" id="">' +
            '<option value="">请选择类别</option> ' +
            '<option value="_0">报销及支付</option> ' +
            '<option value="_1">劳资及税金</option> ' +
            '</select>';

        if(isSetTwo){
            $('.my_1step').remove();
            $(domFirst).insertBefore($obj.hide());
        }
        else if(isSetTwo == false && $('.my_1step').length){
            $('.my_1step').remove();
            $obj.show();
        }
    }

    function setOptionType() {
        isSetTwo = false;
        // var array = [50,56,57,58,59,60,61,62,63,64,65,66,67,68,109,117,118];
//        var string = ['工资','补充医疗保险','企业年金','补充养老保险','房产税','车船使用税','土地使用税','印花税','养老保险', '失业保险','医疗保险','工伤保险','生育保险','住房公积金','劳务费']
        var string = ['工资','补充医疗保险','企业年金','补充养老保险','税金','养老保险', '失业保险','医疗保险','工伤保险','生育保险','住房公积金','劳务费']
        var $obj = $('.my_original'), $option = $obj.find('option');
        $option.each(function (index) {
            $(this).attr('selected',false);
            //1 劳资及税金
            for(var i in string){
                if(string[i] == $(this).text()){
                    isSetTwo = true;
                    $(this).attr('_type','1')
                }
            }
        });
        $option.each(function (index) {
            if($(this).attr('_type')!=1){
                $(this).attr('_type','0')
            }
        })

        tabOption();
    }

    function showHide(_value ,obj) {

        var showIndex = _value;
        var setFirst = true;
        var $option = obj.parent().find('.my_original option');

        $option.each(function(index){

            $(this).prop('selected',false);

            if($(this).attr('_type') == showIndex){
                if(setFirst ==true){
                    $(this).prop('selected',true);
                    setFirst=false;
                }
                $(this).show();

            }else{
                $(this).hide();
            }
        })

    }


    setOptionType();


    $('body').on('change','.my_1step',function (e) {
        var _index = $(this).val().replace('_','');
        var $target =  $(this).parent().find('.my_expenseType');

        if($(this).val()){
            $target.show();
            _index == 1 ? showHide(1,$(this)) : showHide(0,$(this)) ;
        }else{
            $target.hide();
        }
    })


    //autocomplete
    require('../../lib/jqUi.js')

    var availableTags = [];
    $('[name]').each(function () {
        var name = $(this).attr('name')
        if (name) {
            var val = window.localStorage.getItem(name)
            if (val) {
                val = val.split(',')
                $(this).autocomplete({
                    source: val,
                    autoFill: true,
                    minChars: 0,
                    search: true
                })
            }
        }
    })

    function _setItem() {
        var input = $('[name="bank"],[name="payee"],[name="bankAccount"]')
        input.each(function () {
            var _this = $(this)
            var name = _this.attr('name')
            var val = _this.val()
            if(name) {
                var results = window.localStorage.getItem(name)
                if (results){
                    if (results.length > 1) {
                        results = results.split(',')
                        var sameFlag = false
                        for (var i in results) {
                            if (results[i] == val) {
                                sameFlag = true
                            }
                        }
                        if (!sameFlag) {
                            results.push(val)
                            window.localStorage.setItem(name, results)
                        }
                    } else {
                        if (results) {
                            var arr =[]
                            arr.push(results)
                            window.localStorage.setItem(name, val)
                        }
                    }

                } else {
                    window.localStorage.setItem(name, val)
                }
            }
        })
    }
    
    
    //20171122导出
	$("#fnListExport").click(function() {
		var url = $(this).attr("exportUrl");
		url = url + "?" + $("#fnListSearchForm").serialize();
		window.location = url;
	});
});

