define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //字数提示
    require('zyw/hintFont');
    //必填集合
    require('zyw/requiredRules');
    //验证共用
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //异步提交
    require('form')();
    //表单验证
    require('validate');
    require('validate.extend');
    //验证方法集
    require('zyw/project/bfcg.itn.addValidataCommon');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');

    var _allWhetherNull,
        _enterpriseType = $('[name="enterpriseType"]');

    var _form = $('#form'),
        toIndex, _formSerializeMd5New, fm5, checkStatus,
        requiredRules2 = _form.requiredRulesSharp('netAsset,taxCertificateNo,loanCardNo', false, {}, function(rules, name) {
            rules[name] = {
                checkAZ09: true, //必填.数字或字母
                messages: {
                    checkAZ09: '请输入数字或字母'
                }

            };
        }),
        requiredRules3 = _form.requiredRulesSharp('houseNum,carNum', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    digits: '请输入数字',
                    maxlength: '已超过20字'
                },
                digits: true, //必填.正整数
                maxlength: 20
            };
        }),
        requiredRules4 = _form.requiredRulesSharp('other,loanRepaySituationPersional,customerDevEvolution,relatedTrade,relatedGuarantee,relatedCapitalTieup,busiQualification,busiPlace,loanRepaySituationCustomer', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过1000字'
                },
                maxlength: 1000

            };
        }),
        requiredRules5 = _form.requiredRulesSharp('actualInvestmentStr,holderScale,loanBalance.loanCost,consideration,amount,consideration', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    number: '请输入浮点数',
                    maxlength: '已超过20字'
                },
                number: true,
                maxlength: 20

            };
        }),
        requiredRulesMoneyMillion = _form.requiredRulesSharp('loanCostStr,registerCapitalStr,netProfitThisYear,netProfitThisYear,loanBalanceStr,loanCost,folkLoanAmountStr,consumerLoanAmount', false, {}, function(rules, name) {
            rules[name] = {
                isMoneyMillion: true,
                messages: {
                    isMoneyMillion: '请输入整数为12位以内，小数位为两位的数字'
                }

            };
        }),
        requiredRulesMoney = _form.requiredRulesSharp('busiLicenseNoamountStr,investAmountStr,incomeScaleStr,capitalScaleStr,depositAmountStr', false, {}, function(rules, name) {
            rules[name] = {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }

            };
        }),
        requiredRulesPercentTwoDigits = _form.requiredRulesSharp('capitalRatio', false, {}, function(rules, name) {
            rules[name] = {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字'
                }

            };
        }),
        rulesAllBefore = { //所有格式验证的基
            consumerLoanBank: {
                maxlength: 50,
                messages: {
                    maxlength: '超出50字'
                }
            },
            mortgageLoanBank: {
                maxlength: 50,
                messages: {
                    maxlength: '超出50字'
                }
            },
            certNo: {
                ID_CARD: true,
                messages: {
                    ID_CARD: '请输入正确身份证号'
                }
            },
            assetLiabilityRatio: {
                isSpecialRate: true,
                messages: {
                    isSpecialRate: '请输入正确整数位为8位以内，小数位为2位以内的数字'
                }
            },
            busiLicenseNo: {
                isBusinessLicense: true,
                messages: {
                    isBusinessLicense: '请输入正确的营业执照号'
                }
            },
            orgCode: {
                messages: {
                    isOrganization: '请输入正确的组织机构码',
                    required: '必填'
                },
                isOrganization: true,
                required: true
            },
            taxCertificateNo: {
                messages: {
                    isAdministration: '请输入正确的税务登记证号',
                },
                isAdministration: true,
            },
            loanCardNo: {
                messages: {
                    isCardNo: '请输入正确的贷款卡号',
                },
                isCardNo: true
            },
            accountNo: {
                messages: {
                    isBankNo: '请输入正确的银行卡号'
                },
                isBankNo: true
            },
            actualInvestmentStr: {
                messages: {
                    isMoneyMillion: '请输入整数为12位以内，小数位为两位的数字',
                    required: '必填'
                },
                isMoneyMillion: true,
                required: true
            },
            paidinCapitalRatio: {
                messages: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字',
                    required: '必填'
                },
                isPercentTwoDigits: true,
                required: true
            },
            holderMajorBusi: {
                messages: {
                    required: '必填'
                },
                required: true,
            },
            capitalScaleStr: {
                isMoney: true,
                required: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字',
                    required: '必填'
                }
            },
            incomeScaleStr: {
                isMoney: true,
                required: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字',
                    required: '必填'
                }
            },
            holderCertType: {
                messages: {
                    required: '必填'
                },
                required: true
            },
            holderContactNo: {
                //手机号码
                messages: {
                    required: '必填',
                    isMobile: '请输入正确的手机号码'
                },
                required: true,
                isMobile: true
            },
            spouseContactNo: {
                //手机号码
                messages: {
                    isMobile: '请输入正确的手机号码'
                },
                isMobile: true
            },
            folkLoanAmountStr: {
                messages: {
                    isMoneyMillion: '请输入整数为12位以内，小数位为两位的数字'
                },
                isMoneyMillion: true
            },
            businesLoanAmountStr: {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }
            },
            consumerLoanAmountStr: {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }
            },
            mortgageLoanAmountStr: {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }
            },
            actualControlPerson: {
                maxlength: 20,
                messages: {
                    maxlength: '已超出20字'
                }
            },
            produceScaleStr: {
                isMoney: true,
                required: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字',
                    required: '必填'
                }
            },
            holderCertNo: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            legalCertno: {
                required: true,
                isBusinessLicense: true,
                messages: {
                    required: '必填',
                    isBusinessLicense: '请输入正确的营业执照号'
                }
            },
            operatingTerm: {
                maxlength: 20,
                messages: {
                    maxlength: '超出20字'
                }
            }

        },
        rulesVariation = _form.requiredRulesSharp('spouseName,spouseCertType,spouseCertNo,spouseContactNo', false, {}, function(rules, name) {
            rules[name] = {
                required: false,
                messages: {
                    required: '必填'
                }
            };
        }),
        rulesVariation2 = _form.requiredRulesSharp('spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty', false, {}, function(rules, name) {
            rules[name] = {
                required: false,
                messages: {
                    required: '必填'
                }
            };
        });
    //民企是否必填
    if (_enterpriseType.val() != 'PRIVATE') {
        _enterpriseType.addClass('cancel'); //是否必填婚姻状态
        _allWhetherNull = 'MReviewId,consumerLoanBankBusinesLoanBankMortgageLoanBank,hasFolkLoanHasBankLoan,schemeId,submited,loanCardNo,preUrl,nextUrl,token,csrReviewId,mortgageLoanBankCheckbox,consumerLoanBankCheckbox,businesLoanBankCheckbox,bankName,accountNo,formId,projectCode,projectName,customerId,customerName,siteName,site,hasFolkLoan,hasBankLoan,shishi2,shishi3,cancel,name,certNo,houseNum,carNum,investAmountStr,depositAmountStr,maritalStatus,spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty,folkLoanAmountStr,businesLoanBank,consumerLoanEndDateStr,consumerLoanBank,mortgageLoanBank,businesLoanAmountStr,mortgageLoanAmountStr,consumerLoanStartDateStr,businesLoanStartDateStr,mortgageLoanStartDateStr,businesLoanEndDateStr,mortgageLoanEndDateStr,consumerLoanAmountStr';
        rulesAllBeforechange = {}
    } else { //民企
        _allWhetherNull = 'MReviewId,consumerLoanBankBusinesLoanBankMortgageLoanBank,hasFolkLoanHasBankLoan,schemeId,submited,loanCardNo,preUrl,nextUrl,token,csrReviewId,mortgageLoanBankCheckbox,consumerLoanBankCheckbox,businesLoanBankCheckbox,bankName,accountNo,formId,projectCode,projectName,customerId,customerName,siteName,site,,hasFolkLoan,hasBankLoan,shishi2,shishi3,cancel,spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty';
        rulesAllBeforechange = {
            consumerLoanBank: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            consumerLoanAmountStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            consumerLoanStartDateStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            consumerLoanEndDateStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            businesLoanBank: {
                messages: {
                    required: '必填'
                },
                required: true
            },
            businesLoanAmountStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            businesLoanStartDateStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            businesLoanEndDateStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            mortgageLoanBank: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            mortgageLoanAmountStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            mortgageLoanStartDateStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            mortgageLoanEndDateStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            folkLoanAmountStr: {
                messages: {
                    required: '必填'
                },
                required: true
            },
            hasFolkLoanHasBankLoan: {
                messages: {
                    required: '必填'
                },
                required: true
                    // },
                    // consumerLoanBankBusinesLoanBankMortgageLoanBank: {
                    //     messages: {
                    //         required: '该项三选一必填'
                    //     },
                    //     required: true
            }
        }
    }
    var requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, true, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('loanRepaySituationPersional,operatingTerm,token,preUrl,nextUrl', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRulesPercentTwoDigits, requiredRules1, requiredRules2, requiredRules3, requiredRules4, requiredRules5, requiredRulesMoneyMillion, requiredRulesMoney, rulesAllBefore, rulesAllBeforechange);
    _form.validate({
        errorClass: 'error-tip',
        errorElement: 'p',
        errorPlacement: function(error, element) {
            element.after(error);
        },
        onkeyup: true,
        ignore: '.cancel',
        submitHandler: function(form) {
            $.fn.orderName();
            $(form).ajaxSubmit({
                type: 'post',
                dataType: 'json',
                data: {
                    toIndex: toIndex,
                    fm5: fm5,
                    checkStatus: checkStatus
                },
                success: function(res) {
                    showResult(res, hintPopup);
                },
                error: function(a, b, c) {
                    console.log(a)
                    console.log(b)
                    console.log(c)
                }
            });
        }
    });
    //验证方法集初始化
    $('.fnAddLine').addValidataCommon(rulesAll, true)
        .initAllOrderValidata()
        .groupAddValidata();

    var md5 = require('md5'); //md5加密
    function formSerializeMd5(_form) {
        var _formSerialize = _form.serialize();
        return md5(_formSerialize);
    }

    function fm5WhetherChange() {
        var _newSerializeMd5 = formSerializeMd5(_form);
        fm5 = (_newSerializeMd5 != _initSerializeMd5) ? 1 : 0; //数据是否有改变
    }

    function rulesAllFalse() { //否必填共用
        var rulesAllBig = $.extend(true, requiredRules1, rulesAllBefore, rulesVariation, rulesVariation2, rulesAllBeforechange);
        var _allWhetherNull = [];
        for (i in rulesAllBig) {
            if (rulesAllBig[i].required == true) _allWhetherNull.push(i);
        }
        //console.log()
        checkStatus = _form.allWhetherNull(_allWhetherNull, false); //是否填写完整
        $.fn.whetherMust(rulesAllBig, false).allAddValidata(rulesAllBig); //否必填
        $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function(index, el) {
            $(el).rules('add', {
                    required: false
                }) //特殊对象对待
        });
    }

    var _initSerializeMd5 = formSerializeMd5(_form);

    //提交
    $('#submit').click(function(event) {
        fm5WhetherChange();
        $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        checkStatus = 1;
        //toIndex = null;
        $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function(index, el) {
            $(el).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                }) //特殊对象对待
        });
        _form.submit();
    });
    $('#step li').click(function(event) {
        toIndex = $(this).index(); //跳到哪
        var _activeIindex = $('#step li.active').index();
        if (_activeIindex == toIndex) return false; //原页面上不请求
        fm5WhetherChange();
        rulesAllFalse();
        _form.submit();

        $('.marriageChange,.marriage').change();

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
        name: '提交',
        alias: 'fulfilSubmit',
        event: function() {
            fm5WhetherChange();
            $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
            checkStatus = 1;
            toIndex = -2;
            $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function(index, el) {
                $(el).rules('add', {
                        required: true,
                        messages: {
                            required: '必填'
                        }
                    }) //特殊对象对待
            });
            _form.submit();
        }
    }]).init().doRender();

    //下拉选择改变hidden值
    $('body').on('change', 'select', function(event) {
        $(this).next('input').val($(this).find(':selected').val()).change();
        if ($(this).find(':selected').val() != null) $(this).next('input').next('p.error-tip').hide();
    });


    //弹出添加网站，链接
    var _fnAddLinePopup = $('.fnAddLinePopup');
    _fnAddLinePopup.click(function(event) {
        $('body').Y('Window', {
            content: $('#fnAddLinePopup').html(),
            modal: true,
            key: 'modalwnd1',
            simple: true,
            closeEle: '#boxClose'
        });
        var modalwnd1 = Y.getCmp('modalwnd1'),
            _arr = [],
            _assign = $('.assign');

        //$('.fnAddLineAssign').assignGroupAddValidata(rulesAll, _assign);
        $('#fnAddLinePopupForm').validate({
            errorClass: 'error-tip',
            errorElement: 'p',
            errorPlacement: function(error, element) {
                element.after(error);
            },
            onkeyup: true,
            ignore: '.cancel',
            submitHandler: function(form) {
                $.fn.clickAddValidata(rulesAll, modalwnd1.wnd.find('.fnAddLineAssign'), _assign)
                modalwnd1.wnd.find('input').each(function(index, el) {
                    _arr.push(modalwnd1.wnd.find('input').eq(index).val());
                });
                var _obj = _assign.find('tr:last').find('a').eq(0)
                _obj.text(_arr[0]).attr('href', _arr[1]).next().val(_arr[0]).next().val(_arr[1])
                modalwnd1.close();
            },
            rules: {
                addSiteName: {
                    required: true,
                    maxlength: 20
                },
                addSite: {
                    required: true,
                    maxlength: 50
                }
            },
            messages: {
                addSiteName: {
                    required: '必填',
                    maxlength: '已超过20字'
                },
                addSite: {
                    required: '必填',
                    maxlength: '已超过50字'
                }
            }
        });
        modalwnd1.wnd.find('.close').on('click', function() {
            modalwnd1.close();
        });
    });

    //股东性质
    $('body').on('change', '.shareholderNatureSelect', function(event) {
        var _this = $(this),
            _val = _this.val(),
            _index = _this.find('option:selected').index(),
            _parents = _this.parents('.shareholderNature'),
            _next = _parents.next('.shareholderNatureObj');
        _next.html($('.shareholderNatureAlter' + _index).html());
        $.fn.eachFunaddValidata(_this.parents('#test'), rulesAll);
        $('.marriageChange').change();
        $('.credentialsType').change();

    });



    function eachFun(obj, _rulesVariation, bool) {
        obj.each(function(index, el) {
            var _this = $(el);
            $.fn.whetherMust(_rulesVariation, bool).addValidataFun(_this, _rulesVariation);
            _this.next().hide();
        });
    }

    //婚姻判断后面几项是否必填
    $('body').on('change', '.marriageChange', function() {
        var _this = $(this),
            _nextAll = _this.parents('tr').nextAll().find('input,select'),
            _val = _this.find('option:selected').val();
        //if($('[name="enterpriseType"]').val()=='民企') return false;
        (_val == '已婚' && _enterpriseType.val() == 'PRIVATE') ? eachFun(_nextAll, rulesVariation, true): eachFun(_nextAll, rulesVariation, false);
    }).find('.marriageChange').trigger('change');

    //婚姻情况

    $('body').on('change', '.marriage', function() {

        var _this = $(this),
            _parents = _this.parents('tr'),
            _parentsObj = _this.parents('tr').find('input,select'),
            _nextAll = _parents.nextAll().find('input,select'),
            _val = _this.val();

        if (_val == '已婚' && _enterpriseType.val() == 'PRIVATE') {
            eachFun(_nextAll, rulesVariation2, true)
            eachFun(_parentsObj, rulesVariation2, true)
        } else {
            eachFun(_nextAll, rulesVariation2, false)
            eachFun(_parentsObj, rulesVariation2, false)
        }

    }).find('.marriage').trigger('change');

    var credentialsNo = {
        ID_CARD: {
            ID_CARD: true,
            messages: {
                ID_CARD: '请输入正确的身份证号'
            }
        },
        SOLDIERS: {
            SOLDIERS: true,
            messages: {
                SOLDIERS: '请输入正确的士兵证号'
            }
        },
        OFFICER: {
            OFFICER: true,
            messages: {
                OFFICER: '请输入正确的军官证号'
            }
        },
        POLICE_OFFICER: {
            POLICE_OFFICER: true,
            messages: {
                POLICE_OFFICER: '请输入正确的警官证号'
            }
        },
        PASSPORT: {
            PASSPORT: true,
            messages: {
                PASSPORT: '请输入正确的护照号'
            }
        },
        OTHER: {
            OTHER: true,
            messages: {
                OTHER: ''
            }
        },
        isAdministration: {
            isAdministration: true,
            messages: {
                isAdministration: '请输入正确的营业执照号'
            }
        }
    }

    $('body').on('change', '.credentialsType', function() {
        var _this = $(this),
            _val = _this.val(),
            _next = _this.parent().nextAll().find('input');
        for (i in credentialsNo) {
            for (j in credentialsNo[i]) {
                if (j != 'messages') _next.rules('remove', j);
            }
        }
        for (i in credentialsNo) {
            if (i == _val && i == 'ID_CARD') {
                _next.rules('add', credentialsNo[i])
            } else {
                _next.rules('add', {
                    maxlength: 20,
                    messages: {
                        maxlength: '超出20字'
                    }
                })
            };
        }
        _next.blur();
    }).find('.credentialsType').change();

    //实际控制人或自然人股东个人资产、负债状况（非国有必填）checkbox
    //民间借贷
    $('body').on('change', '.hasFolkLoanCheckbox', function(event) {
        var _this = $(this),
            _FolkClass = _this.parents('.hasFolkLoan'),
            _Folk = $('#t-hasFolkLoan').html();
        _FolkClass.find('.target').remove();
        _this.attr('checked') ?
            _FolkClass.append(_Folk) :
            _FolkClass.append('<td class="fn-font-b2 target">' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + '</td>' + '<td class="target" colspan="3"></td>');
        $.fn.eachFunaddValidata(_this.parents('#test33'), rulesAll);
    });

    //银行负债
    $('body').on('change', '.hasBankLoanCheckbox', function(event) {
        var _this = $(this),
            _BankClass = _this.parents('.hasBankLoan'),
            _hasBankLoanSpan = _this.parent().siblings('.hasBankLoanSpan'),
            _Bank = $('#t-hasBankLoan').html(),
            _BankOption = $('#t-hasBankLoan-option').html();
        _this.attr('checked') ?
            _hasBankLoanSpan.html(_BankOption) :
            _hasBankLoanSpan.html('');
        _this.attr('checked') ?
            _BankClass.append(_Bank) :
            _BankClass.find('tr.target').remove();
    });
    $('body').on('change', '.hasBankLoanSpan input[type="checkbox"]', function(event) {
        var _this = $(this),
            _index = _this.parent().index(),
            _checked = _this.attr('checked'),
            _obj = _this.parents('.hasBankLoan');
        //console.log($('#t-hasBankLoan'+_index).html())
        _checked ? _obj.append($('#t-hasBankLoan' + _index).html()) : _obj.find('.t-hasBankLoan' + _index).remove();
        $.fn.eachFunaddValidata(_this.parents('#test33'), rulesAll);
    })

    //负债情况多选验证状态改变
    $('body').on('change', '.hasFolkLoanCheckbox,.hasBankLoanCheckbox', function() {

        var _this = $(this),
            _all = _this.parents('td').find('.hasFolkLoanCheckbox,.hasBankLoanCheckbox'),
            _error = _this.parents('td').find('.hasFolkLoanHasBankLoan'),
            _sum = '';

        _all.each(function(index, el) {

            var _this = $(el);
            if (_this.is(':checked')) _sum += _this.val()

        });

        _error.focus().val(_sum).blur();
        (_sum == '') ? _error.next().show(): _error.next().hide();


    });


    // //银行负责多选验证状态改变
    // $('body').on('change', '.consumerLoanBank,.businesLoanBank,.mortgageLoanBank', function() {

    //     var _this = $(this),
    //         _all = _this.parents('.hasBankLoanSpan').find('.consumerLoanBank,.businesLoanBank,.mortgageLoanBank'),
    //         _error = _this.parents('.hasBankLoanSpan').find('.consumerLoanBankBusinesLoanBankMortgageLoanBank'),
    //         _sum = '';

    //     _all.each(function(index, el) {

    //         var _this = $(el);
    //         if (_this.is(':checked')) _sum += _this.val()

    //     });

    //     _error.val(_sum).change();
    //     (_sum == '') ? _error.next().show(): _error.next().hide();

    // }).find('.consumerLoanBank,.businesLoanBank,.mortgageLoanBank').trigger('change');

    //异常备注
    $('body').on('change', '.statusStrChange', function(event) {

        var _this = $(this),
            _next = _this.parent().next().find('input'),
            _val = _this.val();

        (_val == 'NOMAL') ? _next.addClass('cancel').next().hide(): _next.removeClass('cancel').next().show();

    }).find('.statusStrChange').trigger('change');

    //是否有过提交动作
    var submitedFun = require('zyw/submited'),
        _submited = $('[name="submited"]').val(),
        _currentBoll = submitedFun(_submited);
    if (_currentBoll == '0' && _submited) {
        //$.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function(index, el) {
            $(el).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                }) //特殊对象对待
        });
        //console.log(_submited)
        _form.submit();
    }


});