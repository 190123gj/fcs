define(function (require, exports, module) {
    // 资金收付
    require('Y-msg')

    var util = require('util')

    var getList = require('zyw/getList')

    var BANK_LIST;

    var $fnTotalMoney = $('#fnTotalMoney')
    var $fnTotalMoneyVal = $('#fnTotalMoneyVal')

    var $fnMaintenanceBox = $('#fnMaintenanceBox')

    $fnMaintenanceBox.on('click', '.close', function () {
        $fnMaintenanceBox.addClass('fn-hide')
    }).on('click', '.sure', function () {

        var _obj = {}

        $fnMaintenanceBox.find('[c]').each(function (index, el) {
            _obj[el.getAttribute('c')] = el.value
        })

        if (!!!$fnMaintenanceBox.find('.fnMaintenanceMust').val()) {
            Y.alert('提示', '请填写必填项', function () {
                $fnMaintenanceBox.find('.fnMaintenanceMust').focus()
            })
            return
        }

        var $backp = $('.fnMaintenance.back').removeClass('back').parent()

        $.each(_obj, function (key, val) {
            $backp.find('[c="' + key + '"]').val(val)
        });

        $fnMaintenanceBox.addClass('fn-hide')
    })

    var $fnFinanceTbody = $('#fnFinanceTbody')

    $fnFinanceTbody.on('click', '.fnMaintenance', function () {

        var _obj = {}

        $(this).addClass('back').parent().find('[c]').each(function (index, el) {
            _obj[el.getAttribute('c')] = el.value
        })

        $.each(_obj, function (key, val) {

            var $input = $fnMaintenanceBox.find('[c="' + key + '"]')

            if ($input.attr('type') == 'text') {
                $input.val(val)
            } else {
                $fnMaintenanceBox.find('option').removeProp('selected')
                $fnMaintenanceBox.find('option[value="' + val + '"]').prop('selected', 'selected')
            }

        });

        $fnMaintenanceBox.removeClass('fn-hide')

    }).on('click', '.fnChooseBank', function () {


        if (!!!BANK_LIST) {

            BANK_LIST = new getList()
            BANK_LIST.init({
                title: '选择开户银行',
                ajaxUrl: '/baseDataLoad/bankMessage.json',
                btn: '.fnChooseDetailBank',
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
                    $('.fnChooseBank.back').removeClass('back').parents('td').find('.fnChooseBankCard').val($a.attr('bankAccount'))
                    BANK_LIST.$box.find('.ui-text').val('')
                }
            })

        }

        $(this).addClass('back')

        BANK_LIST.getDate(1)
        BANK_LIST.show()

    }).on('blur', '.fnNeedToAdd', function () {

        var _total = 0

        setTimeout(function () {

            $fnFinanceTbody.find('.fnNeedToAdd').each(function (index, el) {

                // _total += +el.value
                _total = util.accAdd(_total, (el.value || '').replace(/\,/g, ''))

            })

            $fnTotalMoney.html(util.num2k(_total.toFixed(2)))
            $fnTotalMoneyVal.val(_total)

        }, 50)

    })

});