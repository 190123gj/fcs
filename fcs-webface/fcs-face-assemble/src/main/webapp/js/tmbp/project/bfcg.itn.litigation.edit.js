define(function(require, exports, module) {
    require('input.limit');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //异步提交
    require('form')();
    //表单验证
    require('validate');
    require('validate.extend');
    //必填集合
    require('zyw/requiredRules');
    //验证方法集
    require('zyw/project/bfcg.itn.addValidataCommon');
    //字数提示
    require('zyw/hintFont');
    var util = require('util'),
        loading = new util.loading();
    var _form = $('#form'),
        _allWhetherNull = 'depositAccount,xxAmount,coInstitutionId,customerCoverRate,riskCoverRate,MReviewId,schemeId,submited,preUrl,token,nextUrl,formId,projectCode,projectName,customerId,customerName,litigationId,canle,token,customizeFieldMap_formRemark',
        requiredRules = _form.requiredRulesSharp(_allWhetherNull, true, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }

            };
        }),
        requiredRulesIsPercentTwoDigits = _form.requiredRulesSharp('guaranteeRate', false, {}, function(rules, name) {
            rules[name] = {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入正确0.01-100之间的数字'
                }

            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('content,caseIntroduce', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }

            };
        }),
        rulesAllBefore = {
            // content: {
            //     maxlength: 10000,
            //     messages: {
            //         maxlength: '已超过10000字'
            //     }
            // },
            // caseIntroduce: {
            //     maxlength: 10000,
            //     messages: {
            //         maxlength: '已超过10000字'
            //     }
            // },
            guaranteeAmountStr: {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数位14位以内，小数位2位以内的数字'
                }
            },
            contactNo: {
                isPhoneOrMobile: true,
                messages: {
                    isPhoneOrMobile: '请输入正确的号码'
                }

            }
        },
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRules, requiredRulesIsPercentTwoDigits, rulesAllBefore),
        fm5, checkStatus, toIndex;
    _form.validate({
        errorClass: 'error-tip',
        errorElement: 'p',
        errorPlacement: function(error, element) {
            element.after(error);
        },
        onkeyup: true,
        ignore: '.cancel',
        submitHandler: function(form) {
            var fnMakeMoneyfnMakeMicrometer = $('.fnMakeMoney.fnMakeMicrometer');

            fnMakeMoneyfnMakeMicrometer.each(function(index, el) {
                var $el = $(el);
                $el.val($el.val().replace(/\,/g, ''))
            });
            loading.open();
            $(form).ajaxSubmit({
                type: 'post',
                dataType: 'json',
                data: {
                    toIndex: toIndex,
                    fm5: fm5,
                    checkStatus: checkStatus
                },
                success: function(res) {
                    loading.close();
                    if (res.success) {
                        if (res.url == '#') {

                            if ($('#formId').attr('forCustomer')) {
                                window.location.href = window.location.href.replace(/(\&|\?)((forCustomer.+(?=\&))|(forCustomer.+))/g, '') + (/\?/.test(window.location.href) ? '&' : '?') + 'forCustomer=1';
                            } else {
                                hintPopup(res.message, window.location.href.replace(/(\&|\?)((forCustomer.+(?=\&))|(forCustomer.+))/g, ''));
                            }

                        } else {
                            $.ajax({
                                url: '/projectMg/form/submit.htm',
                                type: 'post',
                                dataType: 'json',
                                data: {
                                    formId: $('input[name=formId]').val()
                                },
                                success: function(resf) {
                                    hintPopup(resf.message, function() {
                                        if (resf.success) {
                                            window.location.href = '/projectMg/investigation/list.htm';
                                        }
                                    });
                                }
                            })
                        }
                    } else {
                        hintPopup(res.message);
                    }
                }
            });
        }
    });
    //验证方法集初始化
    $.fn.initAllOrderValidata(rulesAll, true)
    var md5 = require('md5'); //md5加密
    function formSerializeMd5(_form) {
        var _formSerialize = _form.serialize();
        return md5(_formSerialize);
    }

    function fm5WhetherChange() {
        var _newSerializeMd5 = formSerializeMd5(_form);
        fm5 = (_newSerializeMd5 != _initSerializeMd5) ? 1 : 0; //数据是否有改变
    }

    function rulesAllFalse() {
        $.fn.whetherMust(rulesAll, false).allAddValidata(rulesAll); //否必填
        checkStatus = _form.allWhetherNull(_allWhetherNull, true); //是否填写完整
    }

    var _initSerializeMd5 = formSerializeMd5(_form); //初始页面数据

    $('#submits').click(function(event) {
        fm5WhetherChange();
        $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        checkStatus = 1;

        // 不限制字数
        _form.find('.notlimit').each(function(index, el) {
            var $this = $(this)
            $this.rules('remove', 'maxlength')
            $this.rules('remove', 'required')
        });

        //toIndex = undefined;
        _form.submit();
    });

    (new(require('zyw/publicOPN'))()).addOPN([{
            name: '暂存',
            alias: 'temporarStorage',
            event: function() {
                fm5WhetherChange();
                rulesAllFalse();
                toIndex = -1;
                _form.submit();
            }
        }, {
            name: '获取最新客户资料',
            alias: 'temporarStorage2b',
            event: function() {
                fm5WhetherChange();
                rulesAllFalse();
                toIndex = -1;
                $('#formId').attr('forCustomer', 1);
                _form.submit();
            }
        }
        // },
        // {
        //     name: '提交',
        //     alias: 'fulfilSubmit',
        //     event: function() {
        //         //console.log(1111)
        //     }
    ]).init().doRender();

    $('body').on('keyup', '.guaranteeRate', function(event) {

        $('.guaranteeAmountStr').val(parseFloat($(this).val() || 0) * parseFloat($('[name="xxAmount"]').val() || 0));

    });

    //单位改变验证
    require('zyw/project/assistsys.smy.unitChange');

    // 不限制字数
    _form.find('.notlimit').each(function(index, el) {
        var $this = $(this)
        $this.rules('remove', 'maxlength')
        $this.rules('remove', 'required')
    });

});