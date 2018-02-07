define(function(require, exports, module) {
    //弹窗
    require('Y-window');
    require('input.limit');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    // //输入限制
    // require('input.limit');
    //异步提交
    require('form')();
    //表单验证
    require('validate');
    require('validate.extend');
    //必填集合
    require('zyw/requiredRules');
    //上传
    require('zyw/upAttachModify');
    //验证方法集
    require('zyw/project/bfcg.itn.addValidataCommon');
    //字数提示
    require('zyw/hintFont');
    var util = require('util'),
        loading = new util.loading();

    var _form = $('#form'),
        _allWhetherNull = 'intro,pathName_INVESTIGATION_INNOVATIVE_PRODUCT,MReviewId,schemeId,submited,loanCardNo,preUrl,nextUrl,token,pathName_UNDERWRITING_PROJECT,clubRate,lawOfficeRate,otherRate,formId,projectCode,projectName,customerId,customerName,underwritingId,canle,token,preUrl,nextUrl,editorValue',
        requiredRules = _form.requiredRulesSharp(_allWhetherNull, true, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }

            };
        }),
        requiredRulesIsPercentTwoDigits = _form.requiredRulesSharp('totalCost,issueRate', false, {}, function(rules, name) {
            rules[name] = {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字'
                }

            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('pathName_UNDERWRITING_PROJECT,customerName', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 100000,
                messages: {
                    maxlength: '已超出50字'
                }

            };
        }),
        rulesAllBefore = { //所有格式验证的基
            financingAmountStr: {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数14位以内,小数2位以内的数字'
                }
            },
            collectScaleStr: {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数14位以内,小数2位以内的数字'
                }
            },
            // balanceStr: {
            //     isMoneyMillion: true,
            //     messages: {
            //         isMoneyMillion: '请输入整数12位以内,小数2位以内的数字'
            //     }
            // },
            timeLimit: {
                digits: true,
                maxlength: 10,
                messages: {
                    digits: '请输入整数',
                    maxlength: '超出10字'
                }
            },
            projectSource: {
                maxlength: 20,
                messages: {
                    maxlength: '超出20字'
                }
            },
            projectGist: {
                maxlength: 20,
                messages: {
                    maxlength: '超出20字'
                }
            }
        },
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRules, requiredRulesIsPercentTwoDigits, rulesAllBefore);
    //console.log(requiredRulesIsPercentTwoDigits)
    _form.validate({
        errorClass: 'error-tip',
        errorElement: 'p',
        errorPlacement: function(error, element) {
            element.after(error);
        },
        onkeyup: true,
        ignore: '.cancel',
        submitHandler: function(form) {
            loading.open();
            var fnMakeMoneyfnMakeMicrometer = $('.fnMakeMoney.fnMakeMicrometer');

            fnMakeMoneyfnMakeMicrometer.each(function(index, el) {
                var $el = $(el);
                $el.val($el.val().replace(/\,/g, ''))
            });
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
                            hintPopup(res.message, window.location.href);
                        } else {
                            $.ajax({
                                url: '/projectMg/form/submit.htm',
                                type: 'post',
                                dataType: 'json',
                                data: {
                                    formId: $('input[name=formId]').val()
                                },
                                success: function(data) {
                                    hintPopup(data.message, function() {
                                        if (data.success) {
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
        toIndex = null;

        // 不限制字数
        _form.find('.notlimit').each(function(index, el) {
            var $this = $(this)
            $this.rules('remove', 'maxlength')
            $this.rules('remove', 'required')
        });

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
            // },
            // {
            //     name: '提交',
            //     alias: 'fulfilSubmit',
            //     event: function() {
            //         //console.log(1111)
            //     }
    }]).init().doRender();

    //计算发行人扣除费用后实际到账金额
    var unitFun = {
            AMOUNT: function(_financingAmountStr, _amount) {
                return _amount;
            },
            PERCENT: function(_financingAmountStr, _rate) {
                return _financingAmountStr * _rate / 100;
            }
        },
        timeUnitFun = {
            Y: function(_timeLimit) {
                return _timeLimit;
            },
            M: function(_timeLimit) {
                return _timeLimit / 12;
            },
            D: function(_timeLimit) {
                return _timeLimit / 365;
            }
        }
    $('body').on('keyup change', '.calculateFun', function() {

        var _this = $(this),
            _parents = _this.parents('tbody'),
            _all = _parents.find('.calculateFun'),
            _sum = _financingAmountStr = parseFloat($('[name="financingAmountStr"]').val().replace(/,/g,"")) || 0,
            _timeUnit = $('[name="timeUnit"]').val(),
            _timeLimit = timeUnitFun[_timeUnit](parseFloat($('[name="timeLimit"]').val())) || 0;
        //console.log()
        _all.each(function(index, el) {
            var _el = $(el),
                _val = _el.val() || 0,
                _itNext = _el.parent().next().find('select'),
                _unit = _itNext.val(),
                _return = unitFun[_unit](_financingAmountStr, _val, _timeLimit);

            _sum -= _return;

        });

        $('[name="balanceStr"]').val(_sum.toFixed(2)).blur();
    })
    $('body').on('change', '[name="timeUnit"],[name="chargeUnit"],[name="lawOfficeUnit"],[name="clubUnit"],[name="otherUnit"],[name="underwritingUnit"]', function(event) {
        $('.calculateFun').trigger('change');
    })
    $('body').on('keyup', '[name="financingAmountStr"],[name="timeLimit"]', function(event) {
        $('.calculateFun').trigger('change');
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