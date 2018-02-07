define(function (require, exports, module) {

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
    //上传
    require('zyw/upAttachModify');
    //页面提交后跳转处理
    var util = require('util'),
        loading = new util.loading();
    var getList = require('zyw/getList');
    var showResult = require('zyw/process.result');

    var _allWhetherNull,
        _enterpriseType = $('[name="enterpriseType"]');

    var ONLY_VALUE = 0
    $('#saveSubmit').click(function () {
        ONLY_VALUE = 1;
    })




    var _form = $('#form'),
        toIndex, _formSerializeMd5New, fm5, checkStatus,
        requiredRules2 = _form.requiredRulesSharp('netAsset', false, {}, function (rules, name) {
            rules[name] = {
                checkAZ09: true, //必填.数字或字母
                messages: {
                    checkAZ09: '请输入数字或字母'
                }

            };
        }),
        requiredRules3 = _form.requiredRulesSharp('wu', false, {}, function (rules, name) {
            rules[name] = {
                messages: {
                    digits: '请输入数字',
                    maxlength: '已超过20字'
                },
                digits: true, //必填.正整数
                maxlength: 20
            };
        }),
        requiredRules4 = _form.requiredRulesSharp('wu', false, {}, function (rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过10000字'
                },
                maxlength: 10000

            };
        }),
        requiredRules5 = _form.requiredRulesSharp('holderScale', false, {}, function (rules, name) {
            rules[name] = {
                messages: {
                    number: '请输入浮点数',
                    maxlength: '已超过20字'
                },
                number: true,
                maxlength: 20

            };
        }),
        requiredRulesMoneyMillion = _form.requiredRulesSharp('loanCostStr,consumerLoanAmount', false, {}, function (rules, name) {
            rules[name] = {
                isMoneyMillion: true,
                messages: {
                    isMoneyMillion: '请输入整数为12位以内，小数位为两位的数字'
                }

            };
        }),
        requiredRulesMoney = _form.requiredRulesSharp('actualInvestmentStr,busiLicenseNoamountStr,incomeScaleStr,capitalScaleStr', false, {}, function (rules, name) {
            rules[name] = {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }

            };
        }),
        requiredRulesPercentTwoDigits = _form.requiredRulesSharp('capitalRatio', false, {}, function (rules, name) {
            rules[name] = {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字'
                }

            };
        }),
        ueValidate = _form.requiredRulesSharp('loanRepaySituationCustomer,customizeFieldMap_zxCustomerjson', false, {}, function (rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过9999999字'
                },
                maxlength: 9999999

            };
        }),
        rulesAllBefore = { //所有格式验证的基
            remark: {
                maxlength: 10000,
                messages: {
                    maxlength: '已超过10000字'
                },
            },
            busiScope: {
                messages: {
                    maxlength: '已超过1000字'
                },
                maxlength: 1000
            },
            certificateName: {
                messages: {
                    maxlength: '已超过50字',
                    required: '必填'
                },
                maxlength: 50,
                required: true
            },
            certificateCode: {
                messages: {
                    maxlength: '已超过50字',
                    //required: '必填'
                },
                maxlength: 50,
                //required: true
            },
            validDateStr: {
                messages: {
                    maxlength: '已超过50字',
                    //required: '必填'
                },
                maxlength: 50,
                //required: true
            },
            warrantee: {
                messages: {
                    maxlength: '已超过50字',
                    required: '必填'
                },
                maxlength: 50,
                required: true
            },
            amountStr: {
                messages: {
                    maxlength: '已超过50字',
                    required: '必填',
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                },
                maxlength: 50,
                required: true,
                isMoney: true
            },
            guarranteeWay: {
                messages: {
                    maxlength: '已超过50字',
                    required: '必填'
                },
                maxlength: 50,
                required: true
            },
            timeLimit: {
                messages: {
                    maxlength: '已超过50字',
                    //required: '必填'
                },
                maxlength: 50,
                //required: true
            },
            consideration: {
                messages: {
                    maxlength: '已超过500字',
                    //required: '必填'
                },
                maxlength: 500,
                //required: true
            },
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
                ID_CARD: false,
                messages: {
                    ID_CARD: '请输入正确身份证号'
                }
            },
            assetLiabilityRatio: {
                //required: true,
                isSpecialRate: true,
                messages: {
                    //required: '必填',
                    isSpecialRate: '请输入正确整数位为8位以内，小数位为2位以内的数字'
                }
            },
            //capitalRatio:{},
            busiLicenseNo: {
                maxlength: 50,
                //isBusinessLicense: true,
                messages: {
                    //isBusinessLicense: '请输入正确的营业执照号'
                    maxlength: '已超过50字'
                }
            },
            orgCode: {
                messages: {
                    //isOrganization: '请输入正确的组织机构码',
                    maxlength: '已超过50字',
                    required: '必填'
                },
                //isOrganization: true,
                maxlength: 50,
                required: true
            },
            taxCertificateNo: {
                maxlength: 50,
                messages: {
                    maxlength: '已超过50字',
                    //isAdministration: '请输入正确的税务登记证号',
                },
                ///isAdministration: true,
            },
            // loanCardNo: {
            // // messages: {
            // //     isCardNo: '请输入正确的贷款卡号',
            // // },
            // // isCardNo: true
            // },
            accountNo: {
                messages: {
                    isBankSegmentation: '请输入正确的银行卡号'
                },
                isBankSegmentation: true
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
                maxlength: 1000,
                messages: {
                    //required: '必填',
                    maxlength: '已超出1000字'
                },
                //required: true,
            },
            livingAddress: {
                maxlength: 1000,
                messages: {
                    maxlength: '已超出1000字'
                },
            },
            workAddress: {
                maxlength: 1000,
                messages: {
                    maxlength: '已超出1000字'
                },
            },
            capitalScaleStr: {
                isMoney: true,
                //required: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字',
                    //required: '必填'
                }
            },
            incomeScaleStr: {
                isMoney: true,
                //required: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字',
                    //required: '必填'
                }
            },
            actualInvestmentStr: {
                isMoney: true,
                required: true,
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字',
                    required: '必填'
                }
            },
            // holderCertType: {
            //     messages: {
            //         required: '必填'
            //     },
            //     required: true
            // },
            holderContactNo: {
                //手机号码
                messages: {
                    //required: '必填', //
                    isMobile: '请输入正确的手机号码'
                },
                //required: true,
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
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                },
                isMoney: true
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
            // holderCertNo: {
            //     required: true,
            //     messages: {
            //         required: '必填'
            //     }
            // },
            legalCertno: {
                //required: true,
                // isBusinessLicense: true,
                messages: {
                    //required: '必填',
                    // isBusinessLicense: '请输入正确的营业执照号'
                }
            },
            operatingTerm: {
                maxlength: 20,
                messages: {
                    maxlength: '超出20字'
                }
            },
            loanStartDateStr: {
                // max: function (This) {
                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().next().find('input');

                //     return $target.val() ? $target.val() : '9999-99-99';
                // },
                messages: {
                    // max: '大于贷款止日'
                }
            },
            loanEndDateStr: {
                // min: function (This) {

                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().prev().find('input');

                //     return $target.val();

                // },
                messages: {
                    // min: '小于贷款起日'
                }
            },
            netProfitThisYearStr: {
                //required: true,
                isMoney: true,
                messages: {
                    //required: '必填',
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }
            },
            assetScale: {
                 //required: true,
                maxlength: 50,
                 messages: {
                     maxlength: '超出50字'
                 }
             },
            majorBusi: {
                //required: true,
                maxlength: 1000,
                messages: {
                    //required: '必填',
                    maxlength: '超出1000字'
                }
            },
            // capitalRatio: {
            //     required: true,
            //     messages: {
            //         required: '必填'
            //     }
            // },
            capitalRatioStr: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            registerCapitalStr: {
                isMoney: true,
                required: true,
                messages: {
                    required: '必填',
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }
            },
            registerCapital: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            relationDesc: {
                //required: false,
                maxlength: 50,
                messages: {
                    maxlength: '超出50字',
                    //required: '必填'
                }
            },
            companyName: {
                maxlength: 50,
                required: true,
                messages: {
                    maxlength: '超出50字',
                    required: '必填'
                }
            },
            persionalName: {
                maxlength: 50,
                required: true,
                messages: {
                    maxlength: '超出50字',
                    required: '必填'
                }
            },
            persionalDesc: {
                maxlength: 50,
                required: true,
                messages: {
                    maxlength: '超出50字',
                    required: '必填'
                }
            },
            loanInstitution: {
                maxlength: 50,
                //required: true,
                messages: {
                    maxlength: '超出50字',
                    //required: '必填'
                }
            },
            loanBalanceStr: {
                maxlength: 50,
                isMoney: true,
                required: true,
                messages: {
                    maxlength: '超出50字',
                    required: '必填',
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }
            },
            loanStartDateStr: {
                maxlength: 50,
                required: true,
                messages: {
                    maxlength: '超出50字',
                    required: '必填'
                }
            },
            loanEndDateStr: {
                maxlength: 50,
                required: true,
                messages: {
                    maxlength: '超出50字',
                    required: '必填'
                }
            },
            loanCost: {
                maxlength: 50,
                //required: true,
                //isPercentTwoDigits: true,
                messages: {
                    maxlength: '超出50字',
                    //required: '必填',
                    //isPercentTwoDigits: '请输入0.01-100之间的数字'
                }
            },
            guaranteePledge: {
                maxlength: 50,
                required: true,
                messages: {
                    maxlength: '超出50字',
                    required: '必填'
                }
            }

        },
        rulesVariation = _form.requiredRulesSharp('spouseName,spouseCertType,spouseCertNo,spouseContactNo', false, {}, function (rules, name) {
            rules[name] = {
                required: false,
                messages: {
                    required: '必填'
                }
            };
        }),
        rulesVariation2 = _form.requiredRulesSharp('spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty', false, {}, function (rules, name) {
            rules[name] = {
                required: false,
                messages: {
                    required: '必填'
                }
            };
        });
    _enterpriseType.addClass('cancel'); //是否必填婚姻状态 netProfitThisYearStr,assetLiabilityRatio,assetScale,majorBusi,capitalRatio,registerCapitalStr,relationDesc,companyName,
    // _allWhetherNull = 'holderMajorBusi,loanCost,loanInstitution,other,queryTimeStr,holderContactNo,incomeScaleStr,capitalScaleStr,pathName_FORM_ATTACH,localTaxNo,taxCertificateNo,consideration,holderCertType,holderCertNo,legalCertno,certificateName,certificateCode,validDateStr,lastCheckYear,isOneCert,MReviewId,consumerLoanBankBusinesLoanBankMortgageLoanBank,hasFolkLoanHasBankLoan,schemeId,submited,loanCardNo,preUrl,nextUrl,token,csrReviewId,mortgageLoanBankCheckbox,consumerLoanBankCheckbox,businesLoanBankCheckbox,bankName,accountNo,formId,projectCode,projectName,customerId,customerName,siteName,site,hasFolkLoan,hasBankLoan,shishi2,shishi3,cancel,name,certNo,houseNum,carNum,investAmountStr,depositAmountStr,maritalStatus,spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty,folkLoanAmountStr,businesLoanBank,consumerLoanEndDateStr,consumerLoanBank,mortgageLoanBank,businesLoanAmountStr,mortgageLoanAmountStr,consumerLoanStartDateStr,businesLoanStartDateStr,mortgageLoanStartDateStr,businesLoanEndDateStr,mortgageLoanEndDateStr,consumerLoanAmountStr';
    // rulesAllBeforechange = {}
    //民企是否必填
    if (_enterpriseType.val() == 'PRIVATE') { //民企
        _allWhetherNull = 'customize_zxFormId,customize_zxProjectCode,customize_zxCustomerName,customize_zxCustomerName,customize_zxProjectName,pathName_CREDIT_REPORT,customizeFieldMap_zxCustomerjson,workAddress,relationDesc,subsidiaryRemark,participationRemark,correlationRemark,timeLimit,relatedCapitalTieup,relatedGuarantee,relatedTrade,netProfitThisYearStr,assetLiabilityRatio,assetScale,majorBusi,capitalRatio,holderMajorBusi,loanCost,loanInstitution,other,queryTimeStr,holderContactNo,incomeScaleStr,capitalScaleStr,pathName_FORM_ATTACH,localTaxNo,taxCertificateNo,consideration,holderCertType,holderCertNo,legalCertno,certificateName,certificateCode,validDateStr,lastCheckYear,isOneCert,MReviewId,consumerLoanBankBusinesLoanBankMortgageLoanBank,hasFolkLoanHasBankLoan,schemeId,submited,loanCardNo,preUrl,nextUrl,token,csrReviewId,mortgageLoanBankCheckbox,consumerLoanBankCheckbox,businesLoanBankCheckbox,bankName,accountNo,formId,projectCode,projectName,customerId,customerName,siteName,site,,hasFolkLoan,hasBankLoan,shishi2,shishi3,cancel,spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty';
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
                // max: function (This) {
                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().next().next().find('input');

                //     return $target.val() ? $target.val() : '9999-99-99';
                // },
                messages: {
                    required: '必填'
                    // max: '大于贷款止日'
                }
            },
            consumerLoanEndDateStr: {
                required: true,
                // min: function (This) {

                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().prev().prev().find('input');

                //     return $target.val();

                // },
                messages: {
                    required: '必填'
                    // min: '小于贷款起日'
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
                // max: function (This) {
                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().next().next().find('input');

                //     return $target.val() ? $target.val() : '9999-99-99';
                // },
                messages: {
                    required: '必填'
                    // max: '大于贷款止日'
                }
            },
            businesLoanEndDateStr: {
                required: true,
                // min: function (This) {

                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().prev().prev().find('input');

                //     return $target.val();

                // },
                messages: {
                    required: '必填'
                    // min: '小于贷款起日'
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
                // max: function (This) {
                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().next().next().find('input');

                //     return $target.val() ? $target.val() : '9999-99-99';
                // },
                messages: {
                    required: '必填'
                    // max: '大于贷款止日'
                }
            },
            mortgageLoanEndDateStr: {
                required: true,
                // min: function (This) {

                //     var $this, $target;

                //     $this = $(This);
                //     $target = $this.parent().prev().prev().find('input');

                //     return $target.val();

                // },
                messages: {
                    required: '必填'
                    // min: '小于贷款起日'
                }
            },
            folkLoanAmountStr: {
                messages: {
                    required: '必填'
                },
                required: true
            },
            // hasFolkLoanHasBankLoan: {
            //     messages: {
            //         required: '必填'
            //     },
            //     required: true
            //         // },
            //         // consumerLoanBankBusinesLoanBankMortgageLoanBank: {
            //         //     messages: {
            //         //         required: '该项三选一必填'
            //         //     },
            //         //     required: true
            // }
        }
    } else {
        _enterpriseType.addClass('cancel'); //是否必填婚姻状态 netProfitThisYearStr,assetLiabilityRatio,assetScale,majorBusi,capitalRatio,registerCapitalStr,relationDesc,companyName,
        _allWhetherNull = 'customize_zxFormId,customize_zxProjectCode,customize_zxCustomerName,customize_zxCustomerName,customize_zxProjectName,pathName_CREDIT_REPORT,customizeFieldMap_zxCustomerjson,workAddress,relationDesc,subsidiaryRemark,participationRemark,correlationRemark,timeLimit,relatedCapitalTieup,relatedGuarantee,relatedTrade,netProfitThisYearStr,assetLiabilityRatio,assetScale,majorBusi,capitalRatio,holderMajorBusi,loanCost,loanInstitution,other,queryTimeStr,holderContactNo,incomeScaleStr,capitalScaleStr,pathName_FORM_ATTACH,localTaxNo,taxCertificateNo,consideration,holderCertType,holderCertNo,legalCertno,certificateName,certificateCode,validDateStr,lastCheckYear,isOneCert,MReviewId,consumerLoanBankBusinesLoanBankMortgageLoanBank,hasFolkLoanHasBankLoan,schemeId,submited,loanCardNo,preUrl,nextUrl,token,csrReviewId,mortgageLoanBankCheckbox,consumerLoanBankCheckbox,businesLoanBankCheckbox,bankName,accountNo,formId,projectCode,projectName,customerId,customerName,siteName,site,hasFolkLoan,hasBankLoan,shishi2,shishi3,cancel,name,certNo,houseNum,carNum,investAmountStr,depositAmountStr,maritalStatus,spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty,folkLoanAmountStr,businesLoanBank,consumerLoanEndDateStr,consumerLoanBank,mortgageLoanBank,businesLoanAmountStr,mortgageLoanAmountStr,consumerLoanStartDateStr,businesLoanStartDateStr,mortgageLoanStartDateStr,businesLoanEndDateStr,mortgageLoanEndDateStr,consumerLoanAmountStr';
        rulesAllBeforechange = {}
    }
    var requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, true, {}, function (rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('correlationRemark,participationRemark,subsidiaryRemark,loanRepaySituationPersional,operatingTerm,token,preUrl,nextUrl,pathName_FORM_ATTACH,pathName_CREDIT_REPORT,other,loanRepaySituationPersional,customerDevEvolution,relatedTrade,relatedGuarantee,relatedCapitalTieup,busiQualification,busiPlace', true, {}, function (rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRulesPercentTwoDigits, requiredRules1, requiredRules2, requiredRules3, requiredRules4, requiredRules5, requiredRulesMoneyMillion, requiredRulesMoney, rulesAllBefore, rulesAllBeforechange, ueValidate);
    _form.validate({
        errorClass: 'error-tip',
        errorElement: 'p',
        // errorLabelContainer: $('#signupForm'),
        // showErrors: function(errorMap, errorList) {
        //     for (i in errorList) {
        //         $(errorList[i]['element']).after('<p class="error-tip">' + errorList[i]['message'] + '</p>');
        //     }
        //     // element.after(error);
        //     // console.log(errorMap);
        //     // console.log(errorList);
        // },
        errorPlacement: function (error, element) {

            element.after(error);

            var globalHint = $('#globalHint');
            var errorHtml = '<span class="fn-lh26">' + element.attr('name') + ' ' + error.text() + '</span>'

            if (!globalHint.length) {
                _form.after('<div id="globalHint" style="font-size:12px"><p class="fn-hide"></p><div class="fn-lh26" style="color: #f00">数据录入不完整(请认真检查)</div></div>');
            }

            // if (!globalHint.html() || globalHint.html().length < 350) {
            globalHint.find('p').append(errorHtml);
            // }

            if (console && console.log) {
                console.log(element.attr('name') + ' ' + error.text());
            }


        },

        // onkeyup: true,
        ignore: '.cancel',
        submitHandler: function (form) {
            $.fn.orderName();
            loading.open();
            $(form).ajaxSubmit({
                type: 'post',
                dataType: 'json',
                data: {
                    toIndex: toIndex,
                    fm5: fm5,
                    checkStatus: checkStatus
                },
                success: function (res) {
                    if(ONLY_VALUE === 1){
                        showResult(res, hintPopup(res.message,$("#backUrl").attr('data-url')));
                    } else {
                        showResult(res, hintPopup);
                    }
                },
                error: function (a, b, c) {
                    console.log(a)
                    console.log(b)
                    console.log(c)
                }
            });
        }
    });


    $('body').on('change','select.marriage',function () {
        if($(this).val() === '已婚'){
            addRemind('show',$(this))
        }else {
            addRemind('hide',$(this))
        }
    })
    
    function addRemind(way, el) {
        var remind = el.parents('.marriageTbody').find('.remind')
        if (remind.length) {
            if(way === 'hide') {
                remind.hide()
            } else if(way === 'show') {
                remind.show()
            }
            remind.eq(0).show()
        }
    }

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

    function rulesAllFalse() { //不必填
        var rulesAllBig = $.extend(true, requiredRules1, rulesAllBefore, rulesVariation, rulesVariation2, rulesAllBeforechange);
        var _allWhetherNull = [];
        for (i in rulesAllBig) {
            if (rulesAllBig[i].required == true) _allWhetherNull.push(i);
        }
        checkStatus = _form.allWhetherNull(_allWhetherNull, false); //是否填写完整

        if (!$('#version').val()) {
            $('[name]').each(function (index, el) {
                if($(el).attr('aria-required')) {
                    $(el).rules('remove')
                }
            })
        }
        else {
            $.fn.whetherMust(rulesAllBig, false).allAddValidata(rulesAllBig); //否必填
        }

        $('[name="banks[0].bankName"],[name="banks[0].accountNo"],[name="taxCertificateNo"],[name="localTaxNo"]').each(function (index, el) {
            $(el).rules('add', {
                    required: false
                }) //特殊对象对待
        });
    }

    var _initSerializeMd5 = formSerializeMd5(_form);

    //提交

    $('#submits').click(function (event) {
        fm5WhetherChange();
        if($('#version').val()) {
            $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        } else {
            if(($('#zxCustomerjson').val()!='[]' && $('#zxCustomerjson').val()!='') || $('#zxck').length) {
                $('[name="pathName_CREDIT_REPORT"]').rules('remove');
            }
            else {
                $('[name="pathName_CREDIT_REPORT"]').rules('add',{
                    required: true,
                    messages:{
                        required: '征信查询情况，至少填写其中一项'
                    }
                });
            }
        }
        checkStatus = 1;
        toIndex = parseInt($('#step').attr('toIndex')) + 1;
        $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function (index, el) {
            $(el).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                }) //特殊对象对待
        });
        _form.submit();
    });
    $('#step li').click(function (event) {
        toIndex = $(this).index(); //跳到哪
        var _activeIindex = $('#step li.active').index();
        if (_activeIindex == toIndex) return false; //原页面上不请求
        fm5WhetherChange();
        rulesAllFalse();
        _form.submit();

        $('.marriageChange,.marriage').change();

    });

    var cp=$('#checkPoint').val()
    var ar=[]
    if(cp=='' || cp==undefined){
        ar=[{
            name: '暂存',
            alias: 'temporarStorage',
            event: function() {
                fm5WhetherChange();
                rulesAllFalse();
                toIndex = -1;
                _form.submit();
            }},{
            name: '获取最新客户资料',
            alias: 'temporarStorage2b',
            event: function() {
                fm5WhetherChange();
                rulesAllFalse();
                toIndex = -1;
                $('#formId').attr('forCustomer', 1);
                _form.submit();
            }
        },{name: '提交',
            alias: 'fulfilSubmit',
            event: function () {
                fm5WhetherChange();
                if($('#version').val()) {
                    $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
                } else {
                    if(($('#zxCustomerjson').val()!='[]' && $('#zxCustomerjson').val()!='') || $('#zxck').length) {
                        $('[name="pathName_CREDIT_REPORT"]').rules('remove');
                    } else {
                        $('[name="pathName_CREDIT_REPORT"]').rules('add',{
                            required: true,
                            messages:{
                                required: '征信查询情况，至少填写其中一项'
                            }
                        });
                    }
                }
                checkStatus = 111;
                toIndex = -2;
                $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function (index, el) {
                    $(el).rules('add', {
                        required: true,
                        messages: {
                            required: '必填'
                        }
                    }) //特殊对象对待
                });
                _form.submit();
            }}]

        $('#tempSave,#saveSubmit').hide();
    } else {

        $('#tempSave,#saveSubmit').show()
        $('#tempSave,#saveSubmit').click(function () {
            fm5WhetherChange();
            rulesAllFalse();
            toIndex = -1;
            _form.submit();
        })
    }
    (new(require('zyw/publicOPN'))()).addOPN(ar).init().doRender();

    // (new(require('zyw/publicOPN'))()).addOPN([{
    //     name: '暂存',
    //     alias: 'temporarStorage',
    //     event: function () {
    //         fm5WhetherChange();
    //         rulesAllFalse();
    //         toIndex = -1;
    //         _form.submit();
    //     }
    // }, {
    //     name: '获取最新客户资料',
    //     alias: 'temporarStorage2b',
    //     event: function () {
    //         fm5WhetherChange();
    //         rulesAllFalse();
    //         toIndex = -1;
    //         $('#formId').attr('forCustomer', 1);
    //         _form.submit();
    //     }
    // }, {
    //     name: '提交',
    //     alias: 'fulfilSubmit',
    //     event: function () {
    //         fm5WhetherChange();
    //         $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
    //         checkStatus = 1;
    //         toIndex = -2;
    //         $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function (index, el) {
    //             $(el).rules('add', {
    //                     required: true,
    //                     messages: {
    //                         required: '必填'
    //                     }
    //                 }) //特殊对象对待
    //         });
    //         _form.submit();
    //     }
    // }]).init().doRender();
    // $('#tempSave').click(function () {
    //     fm5WhetherChange();
    //     rulesAllFalse();
    //     toIndex = -1;
    //     _form.submit();
    // })
    //下拉选择改变hidden值
    $('body').on('change', 'select', function (event) {
        $(this).next('input').val($(this).find(':selected').val()).change();
        if ($(this).find(':selected').val() != null) $(this).next('input').next('p.error-tip').hide();
    });


    //弹出添加网站，链接
    var _fnAddLinePopup = $('.fnAddLinePopup');
    _fnAddLinePopup.click(function (event) {
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
            errorPlacement: function (error, element) {
                element.after(error);
            },
            onkeyup: true,
            ignore: '.cancel',
            submitHandler: function (form) {
                $.fn.clickAddValidata(rulesAll, modalwnd1.wnd.find('.fnAddLineAssign'), _assign)
                modalwnd1.wnd.find('input').each(function (index, el) {
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
        modalwnd1.wnd.find('.close').on('click', function () {
            modalwnd1.close();
        });
    });

    //股东性质
    $('body').on('change', '.shareholderNatureSelect', function (event) {

        var _this = $(this),
            _val = _this.val(),
            _index = _this.find('option:selected').index(),
            _parents = _this.parents('.shareholderNature'),
            _next = _parents.next('.shareholderNatureObj');

        if (_index == '2') {
            _next.html('');
        } else {
            _next.html($('.shareholderNatureAlter' + _index).html());
        }

        _parents.find('p.error-tip').hide();

        $.fn.eachFunaddValidata(_this.parents('#test'), rulesAll);
        $('.marriageChange').change();
        $('.credentialsType').change();

    });


    function eachFun(obj, _rulesVariation, bool) {
        obj.each(function (index, el) {
            var _this = $(el);
            $.fn.whetherMust(_rulesVariation, bool).addValidataFun(_this, _rulesVariation);
            _this.next('.error-tip').hide();
            bool ? _this.removeClass('checkStatusCancel') : _this.addClass('checkStatusCancel');
        });
    }

    // 婚姻判断后面几项是否必填
    // $('body').on('change', '.marriageChange', function() {
    //     var _this = $(this),
    //         _nextAll = _this.parents('tr').nextAll().find('input,select'),
    //         _val = _this.find('option:selected').val();
    //     //if($('[name="enterpriseType"]').val()=='民企') return false;
    //     (_val == '已婚' && _enterpriseType.val() == 'PRIVATE') ? eachFun(_nextAll, rulesVariation, true): eachFun(_nextAll, rulesVariation, false);
    // }).find('.marriageChange').trigger('change');

    //婚姻情况

    function marriageText(_val, tbody, remindhiht) {

        if (_val == '已婚') {

            tbody.each(function (index, el) {

                var _el = $(el);

                if (_el.hasClass('marriage')) return true;

                if (_el.val() == "") {

                    remindhiht.text('已婚但配偶信息为空');

                    return true;

                } else {

                    remindhiht.text('');

                    return false;

                }

            });


        } else {
            remindhiht.text('');
        }

    }
    //坑壁一直来回改
    $('body').on('change', '.marriage', function () {

        var _this = $(this),
            _parents = _this.parents('tr'),
            _pparents = _this.parents('.marriageTbody')
            _parentsObj = _this.parents('tr').find('input,select'),
            _nextAll = _parents.nextAll().find('input,select'),
            _val = _this.val(),
            remindhiht = _this.parents('table').siblings('em'),
            tbody = _this.parents('tbody').find('input,select');

        marriageText(_val, tbody, remindhiht);

        if (_val == '已婚') {
            eachFun(_nextAll, rulesVariation2, true)
            eachFun(_parentsObj, rulesVariation2, true)
            _pparents.find('.fn-font-b2').each(function () {
                $(this).html('<em class="remind">*</em>' + $(this).text())
            })
        } else {
            eachFun(_nextAll, rulesVariation2, false)
            eachFun(_parentsObj, rulesVariation2, false)
            _pparents.find('.fn-font-b2 .remind').remove()
        }

    }).find('.marriage').trigger('change');

    $('body').on('change', '.marriageTbody input,.marriageTbody select', function (event) {

        var $this = $(this),
            _val = $this.parents('tbody').find('.marriage').val(),
            remindhiht = $this.parents('table').siblings('em'),
            tbody = $this.parents('tbody').find('input,select');

        marriageText(_val, tbody, remindhiht);

    });


    var credentialsNo = {
        ID_CARD: {
            ID_CARD: false,
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

    $('body').on('change', '.credentialsType', function () {
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
    $('body').on('change', '.hasFolkLoanCheckbox', function (event) {
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
    $('body').on('change', '.hasBankLoanCheckbox', function (event) {
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
    $('body').on('change', '.hasBankLoanSpan input[type="checkbox"]', function (event) {
        var _this = $(this),
            _index = _this.parent().index(),
            _checked = _this.attr('checked'),
            _obj = _this.parents('.hasBankLoan');
        //console.log($('#t-hasBankLoan'+_index).html())
        _checked ? _obj.append($('#t-hasBankLoan' + _index).html()) : _obj.find('.t-hasBankLoan' + _index).remove();
        $.fn.eachFunaddValidata(_this.parents('#test33'), rulesAll);
    })

    //负债情况多选验证状态改变
    // $('body').on('change', '.hasFolkLoanCheckbox,.hasBankLoanCheckbox', function() {

    //     var _this = $(this),
    //         _all = _this.parents('td').find('.hasFolkLoanCheckbox,.hasBankLoanCheckbox'),
    //         _error = _this.parents('td').find('.hasFolkLoanHasBankLoan'),
    //         _sum = '';

    //     _all.each(function(index, el) {

    //         var _this = $(el);
    //         if (_this.is(':checked')) _sum += _this.val()

    //     });

    //     _error.focus().val(_sum).blur();
    //     (_sum == '') ? _error.next().show(): _error.next().hide();


    // });


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
    $('body').on('change', '.statusStrChange', function (event) {

        var _this = $(this),
            _next = _this.parent().next().find('textarea'),
            _val = _this.val();

        (_val == 'NOMAL') ? _next.addClass('cancel').next().hide(): _next.removeClass('cancel').next().show();

    }).find('.statusStrChange').trigger('change');

    //是否有过提交动作
    var submitedFun = require('zyw/submited'),
        _submited = $('[name="submited"]').val(),
        _currentBoll = submitedFun(_submited);
    if (_currentBoll == '0' && _submited) {
        //$.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        $('[name="banks[0].bankName"],[name="banks[0].accountNo"]').each(function (index, el) {
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

    // $('body').on('change', '.startEnd', function(event) {

    //     $(this).parents('tr').find('.startEnd').blur();

    // });

    //搜索框时间限制
    $('body').on('blur', '.fnListSearchDateS', function () {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateE');

        $input.attr('onclick', 'laydate({min: "' + this.value + '"})');

    }).on('blur', '.fnListSearchDateE', function () {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateS');

        $input.attr('onclick', 'laydate({max: "' + this.value + '"})');

    }).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');

    $('.administrationNo').change(function (event) { //税务登记

        var $this, $siblings, val;

        $this = $(this);
        $siblings = $this.parent().siblings().find('.administrationNo');
        val = $this.val();

        val ? $siblings.rules('remove', 'required') : $siblings.rules('add', {
            required: true,
            messages: {
                required: '税务登记必填一项'
            }
        })

        $('.administrationNo').blur();

    }).change();

    //
    // setTimeout(function() {
    //     $('.fnGRZCXXMust').each(function(index, el) {
    //         var $this = $(this);
    //         $this.rules('add', {
    //             required: true,
    //             messages: {
    //                 required: '必填'
    //             }
    //         });
    //     });
    // }, 1000);

    var util = require('util');

    $('body').on('click', '.information', function (event) {

        var $this, $user, $No;

        $this = $(this);
        $user = $this.parents('table').find('.informationUser').val();
        $No = $this.parents('table').find('.informationNo').val();

        if ($user && $No) {

            util.risk2retrieve($user, $No);

        } else if (!$No && !$user) {

            hintPopup('请输入主要股东名称和股东证件号码');

        } else if (!$user && $No) {

            hintPopup('请输入主要股东名称');

        } else if (!$No && $user) {

            hintPopup('请输入股东证件号码');

        }

    });

    $('body').on('change', '.credentialsType', function (event) {

        var $this, $target, val, $a;

        $this = $(this);
        $target = $this.parents('tbody').find('.verify');
        $a = $this.parents('tbody').find('.verifyCertNo1').siblings('.checkImg,.fn-green');
        val = $this.val();

        if (val == 'ID_CARD') {

            $target.show();
            $a.show();

        } else {

            $target.hide();
            $a.hide();

        }

    }).find('.credentialsType').change();

    $('body').on('change', '.credentialsType1', function (event) {

        var $this, $target, val, $a;

        $this = $(this);
        $target = $this.parents('table').find('.verifyNew1');
        $a = $this.parents('table').find('.verifyCertNo1').siblings('.checkImg,.fn-green');
        val = $this.val();

        if (val == 'ID_CARD') {

            $target.show();
            $a.show();

        } else {

            $target.hide();
            $a.hide();

        }

    }).find('.credentialsType1').change();

    $('body').on('change', '.credentialsType2', function (event) {

        var $this, $target, val, $a;

        $this = $(this);
        $target = $this.parents('table').find('.verifyNew2');
        $a = $this.parents('table').find('.verifyCertNo1').siblings('.checkImg,.fn-green');
        val = $this.val();

        if (val == 'ID_CARD') {

            $target.show();
            $a.show();

        } else {

            $target.hide();
            $a.hide();

        }

    }).find('.credentialsType2').change();

    $('body').on('click', '.verify', function (event) {

        var $this, $tbody, $verifyName, $verifyCertNo, $verifySpouseContactNo, verifyName, verifyCertNo, verifySpouseContactNo, ajaxUrl;

        $this = $(this);
        $tbody = $this.parents('tbody');
        $verifyName = $tbody.find('.verifyName');
        $verifyCertNo = $tbody.find('.verifyCertNo');
        $verifySpouseContactNo = $tbody.find('.verifySpouseContactNo');
        verifyName = $verifyName.val();
        verifyCertNo = $verifyCertNo.val();
        verifySpouseContactNo = $verifySpouseContactNo.val();

        if ($verifySpouseContactNo.length && !$this.hasClass('two')) {

            ajaxUrl = '/baseDataLoad/validateMobile.json';
            alertText = '请输入名称、证件号码、电话号码';

        } else {

            ajaxUrl = '/baseDataLoad/validateIdCard.json';
            alertText = '请输入名称、证件号码';
            verifySpouseContactNo = true;

        }

        if (!!!verifyName || !!!verifyCertNo || !!!verifySpouseContactNo) {
            hintPopup(alertText);
            return
        }

        // 是否是身份证
        // if (util.checkIdcard(verifyCertNo) != '验证通过!') {
        //     return
        // }

        $.ajax({
                url: ajaxUrl,
                type: 'POST',
                dataType: 'json',
                data: {
                    certNo: verifyCertNo,
                    name: verifyName,
                    mobile: verifySpouseContactNo
                },
            })
            .done(function (res) {
                // 两个接口返回数据不一致
                var _html;
                if ($verifySpouseContactNo.length) {
                    if (res.success) {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>'
                    } else {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>'
                    }
                } else {

                    if (res.success) {
                        if (res.data.checkResult) {
                            var imgUrl = res.data.idcardphoto;
                            _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + '</em>' + '<a class="ui-btn ui-btn-fill ui-btn-green checkImg" verifyCertNo="' + verifyCertNo + '" verifyName="' + verifyName + '" hrefUrl="' + imgUrl + '" style="right: 60px;">查看图片</a>';
                        } else {
                            _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.data.checkMessage + '</em>';
                        }
                    } else {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>';
                    }

                }
                //_html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + '</em>' + '<a class="ui-btn ui-btn-fill ui-btn-green checkImg" verifyCertNo="' + verifyCertNo + '" verifyName="' + verifyName + '" hrefUrl="' + imgUrl + '" style="right: 60px;">查看图片</a>';
                if ($this.nextAll('em.fn-green').length) {
                    $this.nextAll('em.fn-green').remove();
                    $this.nextAll('.checkImg').remove();
                }

                $this.after(_html)


            })
            .fail(function () {
                console.log("error");
            })
            .always(function () {
                console.log("complete");
            });


    });

    $('body').on('click', '.verifyNew1', function (event) {

        var $this, $tbody, $verifyName, $verifyCertNo, $verifySpouseContactNo, verifyName, verifyCertNo, verifySpouseContactNo, ajaxUrl;

        $this = $(this);
        $tbody = $this.parents('table');
        $verifyName = $tbody.find('.verifyName1');
        $verifyCertNo = $tbody.find('.verifyCertNo1');
        $verifySpouseContactNo = $tbody.find('.verifySpouseContactNo1');
        verifyName = $verifyName.val();
        verifyCertNo = $verifyCertNo.val();
        verifySpouseContactNo = $verifySpouseContactNo.val();

        if ($verifySpouseContactNo.length && !$this.hasClass('two')) {

            ajaxUrl = '/baseDataLoad/validateMobile.json';
            alertText = '请输入名称、证件号码、电话号码';

        } else {

            ajaxUrl = '/baseDataLoad/validateIdCard.json';
            alertText = '请输入名称、证件号码';
            verifySpouseContactNo = true;

        }

        if (!!!verifyName || !!!verifyCertNo || !!!verifySpouseContactNo) {
            hintPopup(alertText);
            return
        }

        // 是否是身份证
        // if (util.checkIdcard(verifyCertNo) != '验证通过!') {
        //     return
        // }

        $.ajax({
                url: ajaxUrl,
                type: 'POST',
                dataType: 'json',
                data: {
                    certNo: verifyCertNo,
                    name: verifyName,
                    mobile: verifySpouseContactNo
                },
            })
            .done(function (res) {
                // 两个接口返回数据不一致
                var _html;
                if ($verifySpouseContactNo.length) {
                    if (res.success) {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>'
                    } else {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>'
                    }
                } else {

                    if (res.success) {
                        if (res.data.checkResult) {
                            var imgUrl = res.data.idcardphoto;
                            _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + '</em>' + '<a class="ui-btn ui-btn-fill ui-btn-green checkImg" verifyCertNo="' + verifyCertNo + '" verifyName="' + verifyName + '" hrefUrl="' + imgUrl + '" style="right: 60px;">查看图片</a>';
                        } else {
                            _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.data.checkMessage + '</em>';
                        }
                    } else {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>';
                    }

                }
                //_html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + '</em>' + '<a class="ui-btn ui-btn-fill ui-btn-green checkImg" verifyCertNo="' + verifyCertNo + '" verifyName="' + verifyName + '" hrefUrl="' + imgUrl + '" style="right: 60px;">查看图片</a>';
                if ($this.nextAll('em.fn-green').length) {
                    $this.nextAll('em.fn-green').remove();
                    $this.nextAll('.checkImg').remove();
                }

                $this.after(_html)


            })
            .fail(function () {
                console.log("error");
            })
            .always(function () {
                console.log("complete");
            });


    });

    $('body').on('click', '.verifyNew2', function (event) {

        var $this, $tbody, $verifyName, $verifyCertNo, $verifySpouseContactNo, verifyName, verifyCertNo, verifySpouseContactNo, ajaxUrl;

        $this = $(this);
        $tbody = $this.parents('table');
        $verifyName = $tbody.find('.verifyName2');
        $verifyCertNo = $tbody.find('.verifyCertNo2');
        $verifySpouseContactNo = $tbody.find('.verifySpouseContactNo2');
        verifyName = $verifyName.val();
        verifyCertNo = $verifyCertNo.val();
        verifySpouseContactNo = $verifySpouseContactNo.val();

        if ($verifySpouseContactNo.length && !$this.hasClass('two')) {

            ajaxUrl = '/baseDataLoad/validateMobile.json';
            alertText = '请输入名称、证件号码、电话号码';

        } else {

            ajaxUrl = '/baseDataLoad/validateIdCard.json';
            alertText = '请输入名称、证件号码';
            verifySpouseContactNo = true;

        }

        if (!!!verifyName || !!!verifyCertNo || !!!verifySpouseContactNo) {
            hintPopup(alertText);
            return
        }

        // 是否是身份证
        // if (util.checkIdcard(verifyCertNo) != '验证通过!') {
        //     return
        // }

        $.ajax({
                url: ajaxUrl,
                type: 'POST',
                dataType: 'json',
                data: {
                    certNo: verifyCertNo,
                    name: verifyName,
                    mobile: verifySpouseContactNo
                },
            })
            .done(function (res) {
                // 两个接口返回数据不一致
                var _html;
                if ($verifySpouseContactNo.length) {
                    if (res.success) {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>'
                    } else {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>'
                    }
                } else {

                    if (res.success) {
                        if (res.data.checkResult) {
                            var imgUrl = res.data.idcardphoto;
                            _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + '</em>' + '<a class="ui-btn ui-btn-fill ui-btn-green checkImg" verifyCertNo="' + verifyCertNo + '" verifyName="' + verifyName + '" hrefUrl="' + imgUrl + '" style="right: 60px;">查看图片</a>';
                        } else {
                            _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.data.checkMessage + '</em>';
                        }
                    } else {
                        _html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + res.message + '</em>';
                    }

                }
                //_html = '<em class="fn-green" style="position: absolute;right: 60px;top: 30px;word-break: keep-all !important; white-space:nowrap;">' + '</em>' + '<a class="ui-btn ui-btn-fill ui-btn-green checkImg" verifyCertNo="' + verifyCertNo + '" verifyName="' + verifyName + '" hrefUrl="' + imgUrl + '" style="right: 60px;">查看图片</a>';
                if ($this.nextAll('em.fn-green').length) {
                    $this.nextAll('em.fn-green').remove();
                    $this.nextAll('.checkImg').remove();
                }

                $this.after(_html)


            })
            .fail(function () {
                console.log("error");
            })
            .always(function () {
                console.log("complete");
            });


    });

    require("Y-msg");
    //展示证件号
    $('body').on('click', '.checkImg', function (event) {

        var $this, url;

        $this = $(this);
        url = $this.attr('hrefurl');

        if (url) {

            var _img = new Image();
            _img.src = 'data:image/png;base64,' + url
            _img.onload = function () {
                var _div = document.createElement('div')
                _div.appendChild(_img)
                Y.alert('图像信息', _div);
            };

        }

    });

    //名称、证件号change控制查看图片按钮展示情况
    $('body').on('change', '.verifyName,.verifyCertNo', function (event) {

        var $this, $tbody, $verifyName, $verifyCertNo, $checkImg;

        $this = $(this);
        $tbody = $this.parents('tbody');
        $verifyName = $tbody.find('.verifyName');
        $verifyCertNo = $tbody.find('.verifyCertNo');
        $checkImg = $tbody.find('.checkImg');

        if ($verifyName.val() != $checkImg.attr('verifyName') || $verifyCertNo.val() != $checkImg.attr('verifyCertNo')) {

            $checkImg.hide();

        } else {

            $checkImg.show();

        }


    });

    //名称、证件号change控制查看图片按钮展示情况
    $('body').on('change', '.verifyName2,.verifyCertNo2', function (event) {

        var $this, $tbody, $verifyName, $verifyCertNo, $checkImg;

        $this = $(this);
        $tbody = $this.parents('table');
        $verifyName = $tbody.find('.verifyName2');
        $verifyCertNo = $tbody.find('.verifyCertNo2');
        $checkImg = $tbody.find('.verifyCertNo2').siblings('.checkImg');

        if ($verifyName.val() != $checkImg.attr('verifyName') || $verifyCertNo.val() != $checkImg.attr('verifyCertNo')) {

            $checkImg.hide();

        } else {

            $checkImg.show();

        }


    });

    //名称、证件号change控制查看图片按钮展示情况
    $('body').on('change', '.verifyName1,.verifyCertNo1', function (event) {

        var $this, $tbody, $verifyName, $verifyCertNo, $checkImg;

        $this = $(this);
        $tbody = $this.parents('table');
        $verifyName = $tbody.find('.verifyName1');
        $verifyCertNo = $tbody.find('.verifyCertNo1');
        $checkImg = $tbody.find('.verifyCertNo1').siblings('.checkImg');

        if ($verifyName.val() != $checkImg.attr('verifyName') || $verifyCertNo.val() != $checkImg.attr('verifyCertNo')) {

            $checkImg.hide();

        } else {

            $checkImg.show();

        }


    });

    $('.tooltip').hover(function () {

        var $this, text;

        $this = $(this);
        text = $this.attr('hover');

        $this.append('<span class="hoverTooltip">' + text + '</span>');

    }, function () {

        var $this, text;

        $this = $(this);

        $this.find('.hoverTooltip').remove();

    });

    //计算合计
    $('body').on('blur', '.testex6 tr td.endingBalanceStr,.t-import5 .endingBalanceStr', function (event) {
        if($(this).find('input').length){
            var _this = $(this),
                _parents = _this.parents('tbody'),
                _next = _parents.next(),
                _siblings = _parents.find('td.endingBalanceStr input'),
                _sum = 0;
            _parents.next('.fn-hide').show();
            _siblings.each(function (index, el) {
                // zhurui  replace',' 逗号bug
                var _val = parseFloat($(el).val().replace(/\,/g, ''));
                if (!_val) return false;
                _sum += parseFloat($(el).val().replace(/\,/g, ''));
            });
            _next.find('tr td.endingBalanceStr').text(_sum.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));
            // _next.find('input').val(_sum.toFixed(2));
            //$(this).parents('').find('tbody.fn-hide').show();
        }
    });


    $('body').click(function (event) {
        setTimeout(function () {
            var $parent = $('.testex6');
            if($parent.length){
                $parent.each(function () {
                    var $target = $(this).find('td.endingBalanceStr input'),
                        _next = $(this).next(),
                        _sum = 0;
                    $target.each(function (index, el) {
                        var _val = parseFloat($(el).val().replace(/\,/g, ''));
                        if (!_val) return false;
                        _sum += parseFloat($(el).val().replace(/\,/g, ''));
                    });

                    _next.find('tr td.endingBalanceStr').text(_sum.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));
                    // _next.find('input').val(_sum.toFixed(2));
                    //$(this).find('.testex6 tr').length ? _next.show() : _next.hide();
                })
            }
        }.bind(this), 10);

    });


    $('.fnAddLine').on('click',function () {
        $(this).prev().find('tbody.fn-hide').show();
    });



    $('.fnAddLineStater').click(function (event) {

        $(this).prev().find('.testex6').next().show();

    });

    //信用评价导入
    var template = require('arttemplate');
    require('Y-htmluploadify');

    $('.fnUpFile').click(function () {
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/investigation/uploadExcel.htm?type=e_i_c_4_i_1',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, data, resfile) {

                var Jdata = JSON.parse(data).datas;

                //console.log(Jdata[0].data1)

                var tImport1 = template('h-import1', Jdata[4]);
                var tImport2 = template('h-import2', Jdata[5]);
                var tImport3 = template('h-import3', Jdata[6]);
                var tImport4 = template('h-import4', Jdata[7]);
                var tImport5 = template('h-import5', Jdata[0]);

                var tImport6 = template('h-import6', Jdata[1]);
                var tImport7 = template('h-import7', Jdata[2]);
                var tImport8 = template('h-import8', Jdata[3]);
                var tImportObj1 = $('.t-import1');
                var tImportObj2 = $('.t-import2');
                var tImportObj3 = $('.t-import3');
                var tImportObj4 = $('.t-import4');
                var tImportObj5 = $('.t-import5');
                var tImportObj6 = $('.t-import6');
                var tImportObj7 = $('.t-import7');
                var tImportObj8 = $('.t-import8');

/*
                var dataSum = 0;
                for(var i in Jdata[0].data1){

                    if(i>0){

                        dataSum += parseFloat(Jdata[0].data1[i][1]);

                    }

                }

                dataSum.toFixed(2)


                var dom ='<tbody><tr>' +
                    '<td class="fn-text-c">合计</td>' +
                    '<td class="fn-text-c endingBalanceStr">'+dataSum+'</td>' +
                    '<td class="fn-text-c">-</td>' +
                    '<td class="fn-text-c">-</td>' +
                    '<td class="fn-text-c">-</td>' +
                    '<td class="fn-text-c">-</td>' +
                    '<td class="fn-text-c">-</td>' +
                    '<td class="fn-text-c">-</td>' +
                    '</tr></tbody>'


*/

                tImportObj1.html(tImport1).find('[name]').focus().blur();
                tImportObj2.html(tImport2).find('[name]').focus().blur();
                tImportObj3.html(tImport3).find('[name]').focus().blur();
                tImportObj4.html(tImport4).find('[name]').focus().blur();
                tImportObj5.html(tImport5).find('[name]').focus().blur();
                tImportObj6.html(tImport6).find('[name]').focus().blur().click();
                tImportObj7.html(tImport7).find('[name]').focus().blur();
                tImportObj8.html(tImport8).find('[name]').focus().blur().click();
/*
                $(dom).appendTo($('.t-import5').parent())
*/
                $.fn.eachFunaddValidata(tImportObj1, rulesAll);
                $.fn.eachFunaddValidata(tImportObj2, rulesAll);
                $.fn.eachFunaddValidata(tImportObj3, rulesAll);
                $.fn.eachFunaddValidata(tImportObj4, rulesAll);
                $.fn.eachFunaddValidata(tImportObj5, rulesAll);
                $.fn.eachFunaddValidata(tImportObj6, rulesAll);
                $.fn.eachFunaddValidata(tImportObj7, rulesAll);
                $.fn.eachFunaddValidata(tImportObj8, rulesAll);

            }

        });

    });

    /**
     * 转换小数
     * @param  {[number]} limit [限制多少位小数]
     * @param  {[string]} str   [被转换的字符串]
     * @return {[string]}       [转换后的字符串]
     */
    function makeFloat(limit, str) {

        var _str = str;
        var IS_NEGATIVE = (_str.substr(0, 1) === '-') ? true : false;

        //除了数字和小数点都不要
        _str = _str.replace(/[^\d\.]/g, '');

        //去除多个小数点的可能性
        var _arr = _str.split('.');

        if (_arr.length > 2) {
            _str = _arr[0] + '.' + _arr[1];
        }

        //获取第一个小数点的位置
        var _dotIndex = _str.indexOf('.');

        if (_dotIndex < 0) {
            return (IS_NEGATIVE ? '-' : '') + _str;
        }

        return (IS_NEGATIVE ? '-' : '') + _str.substr(0, (_dotIndex + 1 + limit));

    }

    function makeDB(_thisVal) {

        var _thisValArrs = _thisVal.split('.');

        if (_thisValArrs.length == 1 && _thisValArrs[0]) {
            _thisVal = +_thisValArrs[0] + '.00';
        }

        if (_thisValArrs.length == 2 && !!!_thisValArrs[1].replace(/\s/g, '')) {
            // ie中有个问题
            _thisVal = +_thisValArrs[0] + '.00';
        }

        if (_thisValArrs.length == 2 && _thisValArrs[1].length == 1) {
            _thisVal = _thisValArrs.join('.') + '0';
        }

        return _thisVal;

    }

    // 千分位，什么鬼
    function setInputK(el) {

        return;

        var $this = $(el)

        var $tip = $this.parent().find('.fnMakeMoneyTip')

        if (!!!$tip.length) {
            $tip = $('<span class="fnMakeMoneyTip"></span>')
            $this.after($tip)
        }

        setTimeout(function () {
            var _offset = $this.offset(),
                _left = _offset.left + 1 + +$this.css('padding-left').replace(/px$/g, '')

            $tip.html(util.num2k($this.val())).css({
                left: _left + 'px',
                top: _offset.top + 1 + 'px'
            }).removeClass('fn-hide')
        }, 20)

    }

    var $body = $('body');

    //新加验证方法集（money zhurui ）
    $body.on('focus', '.fnMakeMoney', function () {

        // 2016.11.03 某些地方做了自定义的maxlength 为了不影响，所以咯

        console.log('limitinputmoney:'+this.getAttribute('limitinputmoney'))

        if (!!!this.getAttribute('limitinputmoney')) {


            var _old = +(this.getAttribute('maxlength') || 16)

            // 兼容以前 maxlength="15"
            if (_old === 15) {
                _old = 16
            }

            if (this.className.indexOf('fnMakeMicrometer') >= 0) {
                // 去掉千分位
                _old = _old - this.value.split(',').length + 1
            }

            //this.setAttribute('limitinputmoney', _old)

            if (util.isIE()) {
                _old += 2
            }

            if (this.className.indexOf('fnMakeMicrometer') >= 0) {
                _old = _old + this.value.split(',').length - 1
            } else {
                this.setAttribute('maxlength', _old)
            }
            //zhurui modi
            this.setAttribute('limitinputmoney', _old)

        }

        if (this.value) {
            if (this.value == '0.00') {
                this.value = ''
            } else {
                this.value = +(this.value || '').replace(/\,/g, '')
            }
        }



        // if (util.isIE()) {
        //     this.setAttribute('maxlength', '18')
        // } else {
        //     this.setAttribute('maxlength', '16')
        // }

    }).on('blur', '.fnMakeMoney', function (e) {

        if (this.value == '') {
            return
        }

        var _thisVal = makeFloat(2, this.value);

        // 整数最大位数
        // var _max = +(this.getAttribute('limitinputmoney') || this.getAttribute('maxlength')) - 3;

        var _max = +this.getAttribute('maxlength') - 3

        if (this.className.indexOf('fnMakeMicrometer') >= 0) {

            if (this.getAttribute('limitinputmoney')) {
                _max = +this.getAttribute('limitinputmoney') - 3
            } else {
                _max = _max - this.value.split(',').length + 1
            }

        }


        var _thisValArr = _thisVal.split('.');

        _thisValArr[0] = (_thisValArr[0].length > _max) ? _thisValArr[0].substr(0, _max) : _thisValArr[0];

        _thisVal = _thisValArr.join('.');

        _thisVal = makeDB(_thisVal)

        if (this.className.indexOf('fnMakeMicrometer') >= 0) {
            // 如果需要千分位
            _thisVal = util.num2k(_thisVal)

            // maxlength 动态起来
            _max += _thisVal.split(',').length - 1
        }

        if (util.isIE()) {
            _max += 2
        }

        _max += 3

        this.setAttribute('maxlength', _max)

        if (_thisVal == this.value) {
            return;
        }

        this.value = _thisVal;
        try {
            $(this).valid()
        } catch (err) {}

    }).on('blur', '.fnMakeNumber', function (e) {

        this.value = this.value.replace(/[^\d]/gi, '');

    }).on('blur', '.fnMakeTel', function (e) {

        this.value = this.value.replace(/[^\d-]/gi, '');

    }).on('blur', '.fnMakeLF', function (e) {
        //class="fnMakeLF fnMakeLV4"
        var _thisVal = this.value,
            _classNameArr = this.className.split(' '),
            _classIndex = 0;
        //获取限制等级
        for (var i = _classNameArr.length - 1; i >= 0; i--) {
            if (_classNameArr[i].indexOf('fnMakeLV') >= 0) {
                _classIndex = _classNameArr[i].replace(/fnMakeLV/, '');
            }
        }
        _thisVal = makeFloat(parseInt(_classIndex), _thisVal);

        // 业务需要新增一个大于1的标志
        if (parseFloat(_thisVal) > 1 && !(this.className.indexOf('fnMakeLFS') >= 0)) {
            _thisVal = 0;
        }

        this.value = _thisVal;

    }).on('blur', '.fnMakePercent100', function (e) {

        var _thisVal = this.value;
        _thisVal = makeFloat(2, _thisVal);

        if (parseFloat(_thisVal) > 100) {
            _thisVal = '100';
        }

        _thisVal = makeDB(_thisVal)

        this.value = _thisVal;

    }).on('blur', 'input:not([type="file"],.laydate-icon, .fnInputIgnore)', function (e) {

        // 注释，避免日期中间的空格被删了
        this.value = this.value.replace(/\s*|\s$/g, '');

    }).on('blur', '.fnMakePercent100', function () {

        // if (!!!this.value) {
        //  return;
        // }

        // this.value = +this.value;

    }).on('blur', '.fnMakePercent100,.fnMakeMoney,.fnMakeNumber', function () {

        if (this.value == '.') {
            this.value = '0'
        }

    }).on('click', 'input:not(.fnIgnoreInput),textarea:not(.fnIgnoreInput)', function () {

        // if (util.isIE()) {
        //  this.blur();
        //  this.focus();
        // }

    }).on('keyup', '.fnFloatNumber,.fn-moneyNumberThree', function () { //正数：支持整数和小数

        var thisVal = $(this).val();
        thisVal = thisVal.indexOf(',') ? thisVal.replace(/,/g, '') : thisVal;
        var tempVal;

        if ((thisVal.indexOf('.') != thisVal.lastIndexOf('.')) && (thisVal.indexOf('.') > 0 || thisVal.lastIndexOf('.') > 0)) { //判断当前输入的是否为第二个小数点
            // if (thisVal.indexOf('.') > 0 && thisVal.replace('.', '').indexOf('.') > 0) { //判断当前输入的是否为第二个小数点

            tempVal = thisVal.substring(0, thisVal.lastIndexOf('.')); //去除第二个小数点及其后面的数字，保证只有一个小数点

        } else {

            tempVal = thisVal;

        };

        if (isNaN(tempVal)) tempVal = tempVal.replace(/[^\d]/gi, '');
        // if(!!tempVal && $(this).hasClass("fn-moneyNumberThree")) tempVal = tempVal.split('').reverse().join('').replace(/(\d{3}(?=\d)(?!\d+\.|$))/g, '$1,').split('').reverse().join('');//逗号分割
        $(this).val(tempVal);

    }).on('blur', '.fnFloatNumber,.fn-moneyNumberThree', function () { //正数：支持整数和小数

        var _this = $(this);
        var thisVal = !!_this.val() ? _this.val() : '0';
        thisVal = thisVal.indexOf(',') ? thisVal.replace(/,/g, '') : thisVal;
        var nMaxLength = parseInt($(this).attr('nMaxlength'));
        var intNum = 0,
            floatNum = '00',
            reslt = intNum + '.' + floatNum;
        if (thisVal.indexOf('.') > 0) {
            var ttt = (parseInt(thisVal * 10000) / 10000).toFixed(2);
            intNum = ttt.split('.')[0];
            floatNum = ttt.split('.')[1];
        } else {
            intNum = thisVal || 0;
            floatNum = '00';
        };
        reslt = intNum + "." + floatNum;
        if (reslt.length > (nMaxLength + 1)) {
            reslt = '0.00';
            Y.alert('提示', '超出数值长度限制，包括小数位在内支持最大长度' + nMaxLength, function () {
                _this.val(reslt);
            });
            return;
        };
        //0是否清空（默认清空）
        if (parseFloat(reslt) == 0) reslt = _this.hasClass('toZero') ? '0.00' : '';
        //0是否转换成浮点数（默认转换）
        if (parseFloat(reslt) == 0) reslt = _this.hasClass('notToFloatZero') ? '0' : reslt;
        if (!!reslt && $(this).hasClass("fn-moneyNumberThree")) reslt = reslt.split('').reverse().join('').replace(/(\d{3}(?=\d)(?!\d+\.|$))/g, '$1,').split('').reverse().join(''); //逗号分割
        _this.val(reslt);

    }).on('focus', '.fnFloatNumber,.fn-moneyNumberThree', function () { //正数：支持整数和小数

        var _this = $(this);
        var thisVal = !!_this.val() ? _this.val() : '0';
        thisVal = thisVal.indexOf(',') ? thisVal.replace(/,/g, '') : thisVal;
        var numArry = thisVal.split('.');
        var isZore = parseInt(numArry[1]) == 0;
        if (isZore) _this.val(numArry[0]);

    }).on('blur', 'input[maxnumber]', function () { //不能超过某数值
        var $this = $(this);
        var thisVal = ($this.val() || '').replace(/\,/g, '');
        var maxNum = parseFloat($this.attr('maxnumber'));
        var tempVal;

        if (thisVal > maxNum) {

            Y.alert('提醒', '不得超过当前阈值' + maxNum, function () {
                $this.val(maxNum).focus();
            });
            return;
        }

    }).on('blur', 'input[minnumber]', function () { //不能超过某数值
        var $this = $(this);
        var thisVal = ($this.val() || '').replace(/\,/g, '');
        var minNum = parseFloat($this.attr('minnumber'));
        var tempVal;

        if (thisVal < minNum) {

            Y.alert('提醒', '不得小于当前阈值' + minNum, function () {
                $this.val(minNum).focus();
            });
            return;
        }

    }).on('blur', '.fnMakeMoney', function () {

        setInputK(this)

    }).on('focus', '.fnMakeMoney', function () {
        $(this).parent().find('.fnMakeMoneyTip').addClass('fn-hide')
    }).on('click', '.fnMakeMoneyTip', function () {
        $(this).addClass('fn-hide').parent().find('.fnMakeMoney').focus()
    }).find('.fnMakeMoney').each(function (index, el) {
        setInputK(this)
    });

    //是否还存在错误字段
    /*setInterval(function () {
        var len = $('input.error-tip,textarea.error-tip').length;
        //如果没有错误字段
        if(len==0){
            $('#globalHint').hide();
        }
    },500);*/
    
    //------ 选择项目等 start
    var _getList = new getList();

    if (!$('#version').val()) {
        _getList.init({
            title: '征信查询报告',
            ajaxUrl: '/baseDataLoad/queryCredit.json',
            btn: '.choosef',
            multiple: true,
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow" formId="{{item.formId}}">',
                    '        <td>{{item.projectCode}}</td>',
                    '        <td title="{{item.companyName}}">{{(item.companyName && item.companyName.length > 6)?item.companyName.substr(0,6)+\'\.\.\':item.companyName}}</td>',
                    '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                    '        <td><input type="checkbox" /></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '项目名称：',
                    '<input class="ui-text fn-w160" type="text" name="projectName">',
                    '&nbsp;&nbsp;',
                    '企业名称：',
                    '<input class="ui-text fn-w160" type="text" name="companyName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: [
                    '<th width="100">项目编号</th>',
                    '<th width="120">企业名称</th>',
                    '<th width="120">项目名称</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            },
            callback: function (btn) {
                $('#added_zx').remove()
                var tr = $('#boxList').find('input[type="checkbox"]:checked').closest('tr')
                    dd = $('<dd id="added_zx" class="fn-pl30 fn-lh60 fn-mt20"><div class="fn-w700">' +
                        '<table class="m-table m-table-list"><thead><th>项目编号</th><th>客户名称</th><th>项目名称</th><th>操作</th></thead><tbody></tbody></table>' +
                        '</div></dd>')
                if(tr.length) {
                    tr.each(function (index, el) {
                        $(el).find('input[type="checkbox"]').closest('td').empty().append('<a style="margin-right: 10px" href="/projectMg/creditRefrerenceApply/uploadReport.htm?isView=IS&&formId='+$(el).attr("formId")+'">查看</a><a class="fnDelLine" href="javscript:;">删除</a>');
                        $(el).find('td').eq(0).append('<input name="customize_zxProjectCode" type="hidden" value="'+$(el).find('td').eq(0).text()+'"><input name="customize_zxFormId" type="hidden" value="'+$(el).attr("formId")+'">')
                        $(el).find('td').eq(1).append('<input name="customize_zxCustomerName" type="hidden" value="'+$(el).find('td').eq(1).text()+'">')
                        $(el).find('td').eq(2).append('<input name="customize_zxProjectName" type="hidden" value="'+$(el).find('td').eq(2).text()+'">')
                    })
                    tr.appendTo(dd.find('table tbody'))
                    dd.insertAfter($('.choosef').closest('dt'))
                    toJASON();
                }
            }
        });
        function toJASON() {
            var jsonArray=new Array();
            $('#added_zx tbody tr').each(function (index, el) {
                var obj = {};
                $(el).find('input').each(function (index, _el) {
                    obj[$(_el).attr('name')] = $(_el).val()
                })
                jsonArray.push(obj)
            })
            $('#pathName_CREDIT_REPORT-error').remove()
            $('#zxCustomerjson').val(JSON.stringify(jsonArray))
        }
        $('body').on('click','.fnDelLine', function () {
            if(!$(this).closest('tr').siblings('tr').length) {
                $('#added_zx').remove();
            } else {
                $(this).closest('tr').remove();
            }
            toJASON();
        })
        
    } else {
        _getList.init({
            title: '征信查询报告',
            ajaxUrl: '/baseDataLoad/queryCredit.json',
            btn: '.choosef',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.projectCode}}</td>',
                    '        <td title="{{item.companyName}}">{{(item.companyName && item.companyName.length > 6)?item.companyName.substr(0,6)+\'\.\.\':item.companyName}}</td>',
                    '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                    '        <td><input type="checkbox" /></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '项目名称：',
                    '<input class="ui-text fn-w160" type="text" name="projectName">',
                    '&nbsp;&nbsp;',
                    '企业名称：',
                    '<input class="ui-text fn-w160" type="text" name="companyName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: [
                    '<th width="100">项目编号</th>',
                    '<th width="120">企业名称</th>',
                    '<th width="120">项目名称</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            },
            callback: function ($a) {
                window.location.href = '/projectMg/creditRefrerenceApply/addCreditRefrerenceApply.htm?projectCode=' + $a.attr('projectCode')
            }
        });

    }



    //移除validate
    setTimeout(function () {
        var removeObj = 'workAddress'.split(',')
        removeObj.forEach(function (item) {
            //移除规则，顺便移除红色星星
            $('[name="'+item+'"]').closest('td').prev().find('.remind').remove();
        })
    },200)

    setInterval(function () {
        if($('[name="pathName_CREDIT_REPORT"]').val()) {
            $('#pathName_CREDIT_REPORT-error').remove()
            if($('#form').valid()) {
                $('#globalHint').remove()
            }
        }
        if($('#zxCustomerjson').val()!='[]' && $('#zxCustomerjson').val()!='') {
            $('[name="pathName_CREDIT_REPORT"]').rules('remove','required')
            if($('#form').valid()) {
                $('#globalHint').remove()
            }
        }
    },500)

});