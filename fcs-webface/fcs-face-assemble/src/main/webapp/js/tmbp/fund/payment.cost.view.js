define(function (require, exports, module) {
    // 资金管理 > 费用支付申请单查看 审核

    require('./payment.common')
    require('Y-number');
    require('Y-msg');
    require('input.limit');

    // var template = require('arttemplate');

    var getList = require('zyw/getList');

    var util = require('util');

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    //------ 侧边栏 end

    // ------ 设置奋勇类型 来源新增页面  start

    var OFFICIAL_CARD_DISABLED = '借款、还款、预付款、退预付款、冲预付款、工资、养老保险、失业保险、医疗保险、工伤保险、生育保险、住房公积金、补充医疗保险、企业年金、补充养老保险、外派劳务费、福利费（其他）'.split('、') // 公务卡需要屏蔽的
    var NOT_OFFICIAL_CARD_DISABLED = '还款、退预付款、冲预付款'.split('、') // 非公务卡需要屏蔽的
    var ALL_COST_TYPE = [] // 所有可选的费用类型
    var $fnOfficialCard = $('#fnOfficialCard') //是否是公务卡
    var PUBLIC_COST_TYPE_DISABLED = '借款、还款、退预付款'.split('、') // 对公类型需要屏蔽的
    var PRIVATE_COST_TYPE_ABLED = '还款'.split('、') //　冲销时，对私类型可以选择的
    var $fnPublicCharge = $('#fnPublicCharge') // 对公冲销
    var $fnPublicChargeTotall = $('#fnPublicChargeTotall')
    var $fnPrivateCharge = $('#fnPrivateCharge') // 对私冲销
    var $fnPrivateChargeTotall = $('#fnPrivateChargeTotall')

    var $accountName = $('#accountName') // 对公银行账户用户名
    var $payee = $('#payee') // 对私银行账户用户名

    // 获取所有费用类型
    $('#fnAllCostType').find('option').each(function (index, el) {
        ALL_COST_TYPE.push({
            value: el.value,
            text: el.innerHTML.replace(/\s/g, '')
        })
    })

    /**
     *  2016.11.14
     *  费用类型 的限制
     *
     *  公务卡、非公务卡 来一个限制
     *
     *  选择冲销 再来一个限制
     * 
     */

    function setCostType() {

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

            // 退预付款 放出来    预付款 屏蔽
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

    // ------ 设置奋勇类型 来源新增页面  end

    // 系统带出来的不能选择费用类型、修改金额
    // 自己新增的，金额总额等于 发票冲销金额
    var FPCXJE = 0;
    $('.fnFPCXJE').each(function (index, el) {
        FPCXJE += +el.innerHTML.replace(/\s/g, '')
    });

    if (FPCXJE == 0) {
        $('#fnTbodyAdd').remove()
    }

    var $tbody = $('#tbody');

    // 新增
    $('#fnTbodyAdd').on('click', function () {
        $tbody.append(document.getElementById('t-money').innerHTML).find('.expenseType').last().trigger('change')
    });
    // 删除
    $tbody.on('click', '.del', function () {
        $(this).parents('tr').remove()
    });
    // ------ 选择银行 start
    var $fnBank;
    $tbody.on('click', '.fnChooseDetailBank', function () {
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
            $fnBank.find('.detailbankId').val($a.attr('bankId'));
            $fnBank.find('.detailbankName').val($a.attr('bankName'));
            $fnBank.find('.detaibankAccount').val($a.attr('bankAccount'));
        }
    });
    // ------ 选择银行 end

    // ------ 选择费用类型 带 科目 start
    $tbody.on('change', '.expenseType', function () {

        var $this = $(this),
            _id = this.value;

        util.ajax({
            url: '/baseDataLoad/costCategoryById.json',
            data: {
                categoryId: _id
            },
            success: function (res) {
                if (res.success) {
                    $this.parents('tr').find('.account').val(res.accountCode + " " + res.accountName);
                }
            }
        })

    });
    // ------ 选择费用类型 带 科目 end

    // ------ 付款账户信息 选择开户行 start
    var bankList = new getList();
    bankList.init({
        title: '选择开户银行',
        ajaxUrl: '/baseDataLoad/bankMessage.json',
        btn: '#fnChooseBank',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 12)?item.bankName.substr(0,12)+\'\.\.\':item.bankName}}</td>',
                '        <td title="{{item.accountNo}}">{{(item.accountNo && item.accountNo.length > 25)?item.accountNo.substr(0,25)+\'\.\.\':item.accountNo}}</td>',
                '        <td title="{{item.accountName}}">{{(item.accountName && item.accountName.length > 12)?item.accountName.substr(0,12)+\'\.\.\':item.accountName}}</td>',
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
    // ------ 付款账户信息 选择开户行 end


    // ------ 还原金额 start

    function getPublicChargeTotall() {

        var _total = 0

        $('#fnPublicCharge').find('td input:checked').each(function (index, el) {
            _total += +(el.getAttribute('money') || '').replace(/\,/g, '')
        })

        $('#fnPublicChargeTotall').val(_total)

    }

    // 还原已选择的金额
    getPublicChargeTotall()

    // ------ 还原金额  end



    // $('.fnInput').each(function(index, el) {

    //     validObj.rules[el.name] = {
    //         required: true
    //     };
    //     validObj.messages[el.name] = {
    //         required: '必填'
    //     };

    // });

    // $form.on('click', '.doSubmit', function() {

    //  var _isSave = util.hasClass(this, 'save');

    //  document.getElementById('status').value = _isSave ? 'DRAFT' : 'SUBMIT';

    //  console.log($form.valid())

    //  if (!_isSave && !$form.valid()) {
    //      Y.alert('提示', '表单未填写完整');
    //      return;
    //  }

    //  util.resetName();

    //  if (!_isSave) {
    //      Y.confirm('提示', '提交后，费用类型将不可更改为其它流程的费用类型，是否继续？', function(opt) {
    //             if(opt == 'yes'){
    //              doSave(_isSave);
    //             }
    //         })
    //  }else{
    //      doSave(_isSave);
    //  }

    // });

    // function doSave(isSave) {
    //  util.ajax({
    //      url: $form.attr('action'),
    //      data: $form.serialize(),
    //      success: function(res) {

    //          if (res.success) {

    //              // 审核走流程
    //              if (isSave) {
    //                  Y.alert('提示', '操作成功', function() {
    //                      window.location.href = '/fundMg/expenseApplication/ealist.htm';
    //                  });
    //              } else {

    //                  util.ajax({
    //                      url: '/projectMg/form/submit.htm',
    //                      data: {
    //                          formId: res.form.formId,
    //                          sysname: 'FM',
    //                          _SYSNAME: 'FM'
    //                      },
    //                      success: function(xhr) {

    //                          if (xhr.success) {
    //                              Y.alert('提示', '操作成功', function() {
    //                                  window.location.href = '/fundMg/expenseApplication/ealist.htm';
    //                              });
    //                          } else {
    //                              Y.alert('操作失败', xhr.message);
    //                          }

    //                      }
    //                  });

    //              }

    //          } else {
    //              Y.alert('操作失败', res.message);
    //          }

    //      }
    //  });
    // }

    // var $fnListTbody0 = $('#fnListTbody0');
    // $('#fnListAdd0').on('click', function () {
    //     var $lastTr = $fnListTbody0.find('tr').last();

    //     var $tr = $('<tr></tr>').html($lastTr.html()).attr('diyname', $lastTr.attr('diyname'));

    //     $tr.find('input').each(function (index, el) {
    //         el.value = '';
    //     });

    //     $tr.find('.fnDateS, .fnDateE').attr('onclick', 'laydate()');

    //     $tr.find('.fnListDel0').addClass('del');

    //     $tr.find('.fnListDel0').on('click', function () {
    //         $(this).parents('tr').remove();
    //     });

    //     $tr.find('.expenseType0').on('change', function () {
    //         var categoryId = $(this).val();
    //         var $etype = $(this);
    //         util.ajax({
    //             url: '/baseDataLoad/costCategoryById.json',
    //             data: {
    //                 categoryId: categoryId
    //             },
    //             success: function (res) {
    //                 if (res.success) {
    //                     $etype.parent().parent().find('.account').val(res.accountCode + " " + res.accountName);
    //                 }
    //             }
    //         })
    //     });

    //     $fnListTbody0.append($tr);

    // });

    // $('.fnListDel0.del').on('click', function () {
    //     $(this).parents('tr').remove();
    // });

    // ------ 选择银行 start



    // ------ 收款人 预付款信息 start
    // $('#fnUserId').on('change', function() {

    //     util.ajax({
    //         url: '/baseDataLoad/expenseApplication.json',
    //         data: {
    //             expenseType: 1
    //         },
    //         success: function(res) {

    //             if (res.success) {
    //                 document.getElementById('fnAdvance').innerHTML = template('t-advance', {
    //                     list: res.data
    //                 });
    //             }

    //         }
    //     });

    // });
    // ------ 收款人 预付款信息 end

    // ------ 预付款相关 操作 start
    // var $fnAdvance = $('#fnAdvance');

    // $fnAdvance.on('blur', '.fnMakeMoney', function() {

    //     var self = this;
    //     setTimeout(function() {

    //         if (+self.value > +self.getAttribute('maxmoney')) {
    //             self.value = self.getAttribute('maxmoney');
    //         }

    //     }, 0);

    // }).on('click', '.fnIsCX', function() {

    //     var _isCX = ($fnAdvance.find('.fnIsCX:checked').val() == 'IS') ? true : false;

    //     if (_isCX) {
    //         $fnAdvance.find('#fnCXList').removeClass('fn-hide');
    //     } else {
    //         $fnAdvance.find('#fnCXList').addClass('fn-hide');
    //     }

    // });

    // ------ 预付款相关 操作 end

    //  var signalArr;
    //  $.ajax({
    //      url: '/projectMg/riskWarning/querySignal.json',
    //      type: 'get',
    //      dataType: 'json',
    //      data: {},
    //      success: function(res) {
    //          if (res.success) {
    //              signalArr = res.data.signals;
    //          }
    //      }
    //  });

    // $('.expenseType0').on('change', function() {
    //     //       alert($(this).val());
    //     var categoryId = $(this).val();
    //     var $etype = $(this);
    //     util.ajax({
    //         url: '/baseDataLoad/costCategoryById.json',
    //         data: {
    //             categoryId: categoryId
    //         },
    //         success: function(res) {
    //             if (res.success) {
    //                 $etype.parent().parent().find('.account').val(res.accountCode + " " + res.accountName);
    //             }
    //         }
    //     })
    // });

    // $('.expenseType').on('change', function() {
    //     $('.fnListSearchOrgName').change();
    //     showCX();
    // });

    // function showCX() {
    //     var expenseType = "";
    //     $("*[name='expenseType']").each(function() {
    //         expenseType = expenseType + "," + $(this).val();
    //     });

    //     document.getElementById('fnAdvance').innerHTML = "";
    //     util.ajax({
    //         url: '/baseDataLoad/expenseApplication.json',
    //         data: {
    //             formId: $('#formId').val(),
    //             expenseType: expenseType
    //         },
    //         success: function(res) {

    //             if (res.success) {
    //                 document.getElementById('fnAdvance').innerHTML = template('t-advance', {
    //                     list: res.data.list,
    //                     allcx: res.data.allcx
    //                 });
    //             }

    //         }
    //     });

    // }
    // showCX();
});