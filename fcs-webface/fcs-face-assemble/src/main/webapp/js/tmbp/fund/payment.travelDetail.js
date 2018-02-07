define(function(require, exports, module) {
    // 资金管理 > 差旅费报销单新增
    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.addOPN([]).init().doRender();
    //------ 侧边栏 end
    require('./payment.common')

    require('Y-msg');
    require('validate');

    $("#fnListExport").click(function() {
        var url = $(this).attr("exportUrl");
        url = url + "?" + $("#fnListSearchForm").serialize();
        window.location = url;
    });


    var BPMiframe = require('BPMiframe');

    var getList = require('zyw/getList');

    var util = require('util'),
        $form = $('#form');

    var validObj = {
        rules: {},
        messages: {}
    };

    util.resetName();

    $('.fnInput').each(function(index, el) {

        validObj.rules[el.name] = {
            required: true
        };
        validObj.messages[el.name] = {
            required: '必填',
            maxlength: '输入的长度超了范围'
        };

    });

    $form.validate($.extend({}, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {

            if (element.hasClass('fnErrorAfter')) {

                element.after(error);

            } else {

                element.parent().append(error);

            }

        }
    }, validObj));

    $form.on('click', '.doSubmit', function() {

        if (!!!document.getElementById('fnApplyDeptName').value) {
            Y.alert('提示', '请选择部门');
            return;
        }

        var _isSave = util.hasClass(this, 'save');

        document.getElementById('status').value = _isSave ? 'DRAFT' : 'SUBMIT';

        if (!_isSave && !$form.valid()) {
            Y.alert('提示', '表单未填写完整', function() {
                $('input.error-tip').eq(0).focus();
            });
            return;
        }

        if (!_isSave) {

            // 表格必填项
            var $fnListInput = $('.fnListInput'),
                _isListPass = true,
                _isListPassEq;

            $fnListInput.each(function(i, el) {

                if (!!!el.value.replace(/\s/g, '')) {
                    _isListPass = false;
                    _isListPassEq = i;
                    return false;
                }

            });

            if (!_isListPass) {
                Y.alert('提示', '费用清单中填写不完整', function() {
                    $fnListInput.eq(_isListPassEq).focus();
                });
                return;
            }

            // 金额
            var $fnListTotal = $('.fnListTotal'),
                _isListTotal = true,
                _isListTotalEq;

            $fnListTotal.each(function(i, el) {

                if (+el.value == 0) {
                    _isListTotal = false;
                    _isListTotalEq = i;
                    return false;
                }

            });

            if (!_isListTotal) {
                Y.alert('提示', '第' + (_isListTotalEq + 1) + '行并没有费用');
                return;
            }

        }

        // 还原原来的 name
        $('[diyname="self"] [name]').each(function(index, el) {

            var _name = el.name,
                _nameArr = _name.split('.');

            _name = _nameArr[_nameArr.length - 1];

            el.name = _name;

        });

        util.ajax({
            url: '/baseDataLoad/deptBalanceCheckLock.json',
            data: {
                acquire: true
            },
            success: function(res) {

                util.ajax({
                    url: $form.attr('action'),
                    data: $form.serialize(),
                    success: function(res) {
                        if (res.success) {

                            // 审核走流程
                            if (_isSave) {
                                Y.alert('提示', '操作成功', function() {
                                    window.location = '/fundMg/travelExpense/travellist.htm';
                                });
                                releaseLock();
                            } else {

                                //                              util.ajax({
                                //                                  url: '/projectMg/form/submit.htm',
                                //                                  data: {
                                //                                      formId: res.form.formId,
                                //                                      sysname: 'FM',
                                //                                      _SYSNAME: 'FM'
                                //                                  },
                                //                                  success: function(xhr) {
                                //
                                //                                      if (xhr.success) {
                                //                                          Y.alert('提示', '操作成功', function() {
                                //                                              window.location = '/fundMg/travelExpense/travellist.htm';
                                //                                          });
                                //                                      } else {
                                //                                          Y.alert('操作失败', xhr.message);
                                //                                      }
                                //                                      releaseLock();
                                //                                  }
                                //                              });
                                util.postAudit({
                                    formId: res.form.formId,
                                    _SYSNAME: 'FM'
                                }, function() {
                                    window.location.href = '/fundMg/travelExpense/travellist.htm';
                                })

                            }

                        } else {
                            releaseLock();
                            Y.alert('操作失败', res.message);
                            // 更新 预算余额
                            $('.fnListSearchOrgName').trigger('change');
                        }

                    }
                });
            }
        });
    });

    function releaseLock() {
        util.ajax({
            url: '/baseDataLoad/deptBalanceCheckLock.json',
            data: {
                acquire: false
            },
            success: function(res) {}
        });
    };

    if (document.getElementById('showAmount')) {
        var fnAmount = document.getElementById('fnAmount').value;
        setTimeout(function() {
            $('#fnAmountChine').text(Y.Number(fnAmount).digitUppercase());
        }, 0);
    };

    // ------ 是否是 公务卡支付 start

    var $fnIsCard = $('#fnIsCard');

    $('#fnOfficialCard').on('click', function() {
        setIsCard(!!$('#fnOfficialCard:checked').val());
    });

    setIsCard(!!$('#fnOfficialCard:checked').val());

    function setIsCard(is) {

        if (is) {

            $fnIsCard.addClass('fn-hide').find('input.ui-text').each(function(index, el) {

                $(this).prop('disabled', 'disabled');

            });

        } else {

            $fnIsCard.removeClass('fn-hide').find('input.ui-text').each(function(index, el) {

                $(this).removeProp('disabled');

            });

        }

    }

    // ------ 是否是 公务卡支付 end

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
                '        <td title="{{item.accountNo}}">{{(item.accountNo && item.accountNo.length > 6)?item.accountNo.substr(0,6)+\'\.\.\':item.accountNo}}</td>',
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
        callback: function($a) {
            $('#bankName').val($a.attr('bankName'));
            $('#bankId').val($a.attr('bankId'));
            $('#bankAccount').val($a.attr('bankAccount'));
            $('#accountName').val($a.attr('accountName'));
        }
    });
    // ------ 选择银行 end

    $('#bankSubmit').on('click', function() {
        util.ajax({
            url: '/baseDataLoad/proPayBank.json',
            data: {
                formId: $('#formId').val(),
                payBank: $('#bankName').val(),
                payBankAccount: $('#bankAccount').val()
            },
            success: function(res) {
                Y.alert('提示', res.message);
            }
        });
    });
    var yearsTime = require('zyw/yearsTime');
    $('body').on('focus', '.birth', function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: this,
            format: 'YYYY-MM',
            callback: function(_this, _time) {
                $('.fnListSearchDateS,.fnListSearchDateE').val('');
            }
        });
    });
    //搜索框时间限制
    $('body').on('blur', '.fnListSearchDateS', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateE');

        $input.attr('onclick', 'laydate({min: "' + this.value + '",choose:function(){$(".birth").val("")}})');

    }).on('blur', '.fnListSearchDateE', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateS');

        $input.attr('onclick', 'laydate({max: "' + this.value + '",choose:function(){$(".birth").val("")}})');

    }).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');


});