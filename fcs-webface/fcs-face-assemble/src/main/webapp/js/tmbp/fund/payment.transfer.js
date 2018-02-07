define(function (require, exports, module) {
    // 资金管理 > 费用支付申请单新增

    require('./payment.common')

    require('Y-msg');
    require('validate');

    var template = require('arttemplate');

    var getList = require('zyw/getList');

    var util = require('util'),
        $form = $('#form');

    var validObj = {
        rules: {},
        messages: {}
    };

    // 新打开窗口
    var $fnBank;
    var $fnTbody = $('#fnListTbody');

    // 申请明细中选择银行 start
    $fnTbody.on('click', '.fnChooseDetailBank', function () {
        $fnBank = $(this).parent().parent()

        var _id = document.getElementById('fnDetailDeptId').value;
        if (!!!_id) {
            Y.alert('提示', '请先选择开户银行')
            return
        }

        DBANK_LIST.resetAjaxUrl('/baseDataLoad/bankMessage.json?deptId=' + _id)

        DBANK_LIST.getData(1)
        DBANK_LIST.show()
    });
    var DBANK_LIST = new getList();
    DBANK_LIST.init({
        title: '选择开户银行',
        ajaxUrl: '/baseDataLoad/bankMessage.json',
        btn: '#fnChooseDetailBank',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 12)?item.bankName.substr(0,12)+\'\.\.\':item.bankName}}</td>',
                '        <td title="{{item.accountType}}">{{(item.accountType && item.accountType.length > 12)?item.accountType.substr(0,12)+\'\.\.\':item.accountType}}</td>',
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
            $fnBank.find('.detailbankId').val($a.attr('bankId')).trigger('blur');
            $fnBank.find('.detailbankName').val($a.attr('bankName')).trigger('blur');
            $fnBank.find('.detaibankAccount').val($a.attr('bankAccount')).trigger('blur');
        }
    });
    // 申请明细中选择银行 end

    // 申请部门更换，深圳明细 银行清空
    $('#fnDetailDeptId').on('change', function () {
        $fnTbody.find('.detailbankName, .detailbankId, .detaibankAccount').val('')
    })

    util.resetName();

    $('.fnInput').each(function (index, el) {

        util.ObjAddkey(validObj.rules, el.name, {
            required: true
        });
        util.ObjAddkey(validObj.messages, el.name, {
            required: '必填',
            maxlength: '输入的长度超了范围'
        });

        if (el.className.indexOf('fnMakeMoney') >= 0) {

            util.ObjAddkey(validObj.rules, el.name, {
                min: 0.01
            });
            util.ObjAddkey(validObj.messages, el.name, {
                min: '必填大于0'
            });

        }

    });

    $form.validate($.extend({}, {
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

        }
    }, validObj));

    $form.on('click', '.doSubmit', function () {

        var _isSave = util.hasClass(this, 'save');

        document.getElementById('status').value = _isSave ? 'DRAFT' : 'SUBMIT';

        if (!_isSave && !$form.valid()) {
            Y.alert('提示', '表单未填写完整');
            return;
        }

        // 还原原来的 name
        $('[diyname="self"] [name]').each(function (index, el) {

            var _name = el.name,
                _nameArr = _name.split('.');

            _name = _nameArr[_nameArr.length - 1];

            el.name = _name;

        });

        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {

                if (res.success) {

                    // 审核走流程
                    if (_isSave) {
                        Y.alert('提示', '操作成功', function () {
                            window.location.href = '/fundMg/transfer/list.htm';
                        });
                    } else {

                        util.postAudit({
                            formId: res.form.formId,
                            _SYSNAME: 'FM'
                        }, function () {
                            window.location.href = '/fundMg/transfer/list.htm';
                        })

                    }

                } else {
                    Y.alert('操作失败', res.message);
                    //可能余额同时被别人用了
                    $('.fnListSearchOrgName').trigger('change');
                }

            }
        });

    });

    // ------ 申请日期 start
    var TODAYDATE = util.getNowTime();
    $('#fnApplicationTime').val(TODAYDATE.YY + '-' + TODAYDATE.MM + '-' + TODAYDATE.DD);
    // ------ 申请日期 end



    // ------ 选择银行 start
    var bankList = new getList();
    bankList.init({
        title: '选择开户银行',
        ajaxUrl: '/baseDataLoad/bankMessage.json',
        btn: '#fnChooseBank',
        input: ['bankName', 'bankId', 'bankAccount', 'payee', 'fnDetailDeptId'],
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 15)?item.bankName.substr(0,15)+\'\.\.\':item.bankName}}</td>',
                '        <td title="{{item.accountNo}}">{{(item.accountNo && item.accountNo.length > 25)?item.accountNo.substr(0,25)+\'\.\.\':item.accountNo}}</td>',
                '        <td title="{{item.accountName}}">{{(item.accountName && item.accountName.length > 15)?item.accountName.substr(0,15)+\'\.\.\':item.accountName}}</td>',
                '        <td><a class="choose" bankId="{{item.bankId}}" bankName="{{item.bankName}}" bankAccount="{{item.accountNo}}" payee="{{item.accountName}}" fnDetailDeptId="{{item.deptId}}" href="javascript:void(0);">选择</a></td>',
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
        }
        // callback: function ($a) {
        //     $('#bankName').val($a.attr('bankName'));
        //     $('#bankId').val($a.attr('bankId'));
        //     $('#bankAccount').val($a.attr('bankAccount'));
        // }
    });

    // ------ 选择银行 end

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

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();

    $('#fnPrint').click(function (event) {
        var $fnPrintBox = $('#div_print')
        $fnPrintBox.find('.ui-btn-submit').remove()
        $fnPrintBox.find('.printshow').removeClass('fn-hide')
        util.print($fnPrintBox.html())
    })
});