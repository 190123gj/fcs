define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    var template = require('arttemplate');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //上传
    require('zyw/upAttachModify');
    //验证共用
    var _form = $('#form'),
        hasGuarantor = false, //($('[name="hasGuarantor"]').val() == 'YES'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        _allWhetherNull = hasGuarantor ? 'eventDesc,eventDesc2,establishedTime,birth,hasGuarantor,establishedTimeStr,operatingTerm,legalPersion,livingAddress,actualControlPerson,busiLicenseNo,enterpriseType,workAddress,orgCode,loanCardNo,lastCheckYear,busiScope,pathName_FORM_ATTACH,startDate,endDate,companyName,title,taxCertificateNo,localTaxNo,name,sex,age,degree,MReviewId,schemeId,submited,preUrl,token,nextUrl,csrReviewId,formId,projectCode,projectName,customerId,customerName,cancel,guarantor,abilityLevel,totalCapitalStr,intangibleAssetsStr,contingentLiabilityStr,guaranteeAmountStr,kpiValue1,kpiValue2,kpiValue3,kpiValue4,leaderReview,certNo,houseNum,carNum,investAmount,depositAmount,marriage,spouseName,spouseCertType,spouseCertNo,spouseContactNo,spouseAddress,spouseImmovableProperty,spouseMovableProperty,hasFolkLoan,hasBankLoan,folkLoanAmountStr,hasBankLoan,hasBankLoan,hasBankLoan,consumerLoanBank,businesLoanBank,mortgageLoanBank,consumerLoanAmountStr,businesLoanAmountStr,mortgageLoanAmountStr,consumerLoanStartDate,businesLoanStartDate,mortgageLoanStartDate,consumerLoanEndDate,businesLoanEndDate,mortgageLoanEndDate,mateName,mateContactNo,mateCertType,mateCertNo' : 'eventDesc,eventDesc2,mateCertNo,mateCertType,mateContactNo,mateName,mortgageLoanEndDate,businesLoanEndDate,consumerLoanEndDate,mortgageLoanStartDate,businesLoanStartDate,consumerLoanStartDate,mortgageLoanAmountStr,businesLoanAmountStr,consumerLoanAmountStr,mortgageLoanBank,businesLoanBank,consumerLoanBank,hasBankLoan,hasBankLoan,hasBankLoan,folkLoanAmountStr,hasBankLoan,hasFolkLoan,spouseMovableProperty,spouseImmovableProperty,spouseAddress,spouseContactNo,spouseCertNo,spouseCertType,spouseName,marriage,depositAmount,investAmount,carNum,houseNum,certNo,leaderReview,kpiValue1,kpiValue2,kpiValue3,kpiValue4,establishedTime,birth,hasGuarantor,leadersAppraise,establishedTimeStr,operatingTerm,legalPersion,livingAddress,actualControlPerson,busiLicenseNo,enterpriseType,workAddress,orgCode,loanCardNo,lastCheckYear,busiScope,pathName_FORM_ATTACH,startDate,endDate,companyName,title,taxCertificateNo,localTaxNo,name,sex,age,degree,MReviewId,schemeId,submited,preUrl,token,nextUrl,csrReviewId,formId,projectCode,projectName,customerId,customerName,cancel,guarantor,abilityLevel,totalCapitalStr,intangibleAssetsStr,contingentLiabilityStr,guaranteeAmountStr,value1,value2,value3,value',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, true, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('eventDesc,eventDesc2,leaderReview,wu,token,preUrl,nextUrl,pathName_FORM_ATTACH,leadersAppraise,mainlyReview.customerDevEvolution,amountLimitReview,timeLimitReview,loanPurposeReview,repaySourceReview,guarantorInfo,pledgeValue,reviewSummary,otherRepaySource', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRulesMaxlength20 = _form.requiredRulesSharp('legalPersion,actualControlPerson,operatingTerm', false, {}, function(rules, name) {
            rules[name] = {
                maxlength: 20,
                messages: {
                    maxlength: '已超出20字'
                }
            };
        }),
        // requiredRules2 = _form.requiredRulesSharp('certificateCode', false, {}, function(rules, name) {
        //     rules[name] = {
        //         checkAZ09: true, //必填.数字或字母
        //         messages: {
        //             checkAZ09: '请输入数字或字母'
        //         }

        //     };
        // }),
        requiredRules3 = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    digits: '请输入整数',
                    maxlength: '已超过20字'
                },
                digits: true, //必填.正整数
                maxlength: 20

            };
        }),
        requiredRules4 = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过10000字'
                },
                maxlength: 10000

            };
        }),
        requiredRules5 = _form.requiredRulesSharp('sex', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    checkZh: '请输入汉字'
                },
                checkZh: true
            };
        }),
        // requiredRulesMoneyMillion = _form.requiredRulesSharp('value,value1,value2,value3', false, {}, function(rules, name) {
        //     rules[name] = {
        //         messages: {
        //             isMoneyMillion: '请输入整数为12位以内，小数位为两位的数字'
        //         },
        //         isMoneyMillion: true

        //     };
        // }),
        requiredRulesMoneyMillion = _form.requiredRulesSharp('totalCapitalStr,intangibleAssetsStr,contingentLiabilityStr,investAmount,depositAmount,consumerLoanAmountStr,businesLoanAmountStr,mortgageLoanAmountStr,folkLoanAmountStr', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                },
                isMoney: true

            };
        }),
        rulesAllBefore = { //所有格式验证的基
            mateContactNo: {
                //手机号码
                messages: {
                    isMobile: '请输入正确的手机号码'
                },
                isMobile: true
            },
            guarantorContactNo: {
                //手机号码
                messages: {
                    isMobile: '请输入正确的手机号码'
                },
                isMobile: true
            },
            spouseContactNo: {
                //手机号码
                messages: {
                    isMobile: '请输入正确的手机号码'
                },
                isMobile: true
            },
            guarantor: {
                maxlength: 20,
                messages: {
                    maxlength: '超出20字'
                }
            },
            busiScope: {
                maxlength: 1000,
                messages: {
                    maxlength: '超出1000字'
                }
            },
            certificateName: {
                required: true,
                maxlength: 50,
                messages: {
                    required: '必填',
                    maxlength: '超出50字'
                }
            },
            certificateCode: {
                required: true,
                maxlength: 50,
                messages: {
                    required: '必填',
                    maxlength: '超出50字'
                }
            },
            validDateStr: {
                required: true,
                maxlength: 50,
                messages: {
                    required: '必填',
                    maxlength: '超出50字'
                }
            },
            // busiLicenseNo: {
            //     messages: {
            //         isBusinessLicense: '请输入正确的营业执照号'
            //     },
            //     isBusinessLicense: true
            // },
            // taxCertificateNo: {
            //     messages: {
            //         isBusinessLicense: '请输入正确的税务登记证号'
            //     },
            //     isBusinessLicense: true
            // },
            // orgCode: {
            //     messages: {
            //         isOrganizationBusinessLicense: '请输入正确的组织机构码'
            //     },
            //     isOrganizationBusinessLicense: true
            // },
            // loanCardNo: {
            //     messages: {
            //         isCardNo: '请输入正确的贷款卡号'
            //     },
            //     isCardNo: true
            // },
            degree: {
                maxlength: 10,
                messages: {
                    maxlength: '已超出10字'
                }
            },
            // title: {
            //     maxlength: 20,
            //     messages: {
            //         maxlength: '已超出20字'
            //     }
            // },
            // age: {
            //     digits: true,
            //     maxlength: 3,
            //     messages: {
            //         digits: '请输入正整数',
            //         maxlength: '已超出3字'
            //     }
            // },
            resume: {
                maxlength: 500,
                messages: {
                    maxlength: '已超出500字'
                }
            }
        },
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRulesMaxlength20, requiredRules1, requiredRules3, requiredRules4, requiredRules5, requiredRulesMoneyMillion, rulesAllBefore);

    // 保存并返回
    var ONLY_VALUE = 0

    $('#saveSubmit').click(function () {
        ONLY_VALUE = 1;
    })
    ValidataCommon(rulesAll, _form, _allWhetherNull, true, function(res) {
        if(ONLY_VALUE === 1){
            showResult(res, hintPopup(res.message,$("#backUrl").attr('data-url')));
        } else {
            showResult(res, hintPopup);
        }
    });

    //对外可提供担保额度
    $('body').on('change', '.abilityLevel', function(event) {

        var _this = $(this),
            _val = _this.val(),
            _intangibleAssetsStr = parseFloat(_this.parent().siblings('td').find('.intangibleAssetsStr').val().replace(/\,/g, '')) || 0,
            _contingentLiabilityStr = parseFloat(_this.parent().siblings('td').find('.contingentLiabilityStr').val().replace(/\,/g, '')) || 0,
            _totalCapitalStr = parseFloat(_this.parent().siblings('td').find('.totalCapitalStr').val().replace(/\,/g, '')) || 0,
            _guaranteeAmountStr = _this.parent().siblings('td').find('.guaranteeAmountStr'),
            //_minusSum = _intangibleAssetsStr + _contingentLiabilityStr,
            _countVal;
        console.log(_intangibleAssetsStr, _contingentLiabilityStr, _totalCapitalStr);
        if (_val == 'AA') {

            _countVal = (_totalCapitalStr - _intangibleAssetsStr) * 0.8 - _contingentLiabilityStr;

        } else if (_val == 'AAA') {

            _countVal = _totalCapitalStr - _intangibleAssetsStr - _contingentLiabilityStr;

        } else {

            _countVal = 0;

        }

        _guaranteeAmountStr.val(_countVal.toFixed(2));

    });

    $('body').on('change', '.intangibleAssetsStr,.contingentLiabilityStr', function(event) {

        $(this).parent().siblings('td').find('.abilityLevel').change();

    });

    //上传
    require('Y-htmluploadify')

    $('body').on('click', '.fnUpFile2', function(event) {

        var _this = $(this);

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/investigation/uploadExcel.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function(a, b) {},
            onUploadSuccess: function($this, data, resfile) {
                var _data = JSON.parse(data).datas,
                    _toLead = _this.parent().next().find('.toLead');
                // console.log(_data);
                // _totalAssets = parseFloat(_data[1][2].match(/\(?-?\d+\.?/g).join('').replace(/\(/, '-')),
                //     _grossLiability = parseFloat(_data[5][2].match(/\(?-?\d+\.?/g).join('').replace(/\(/, '-'));
                // _tTest3 = $('#t-test3').html();


                for (var i = 0; i < _data.length; i++) {
                    for (var j = 0; j < _data[i].length; j++) {

                        //var ij = _data[i][j].match(/\(?-?\d+\.?/g) ? _data[i][j].match(/\(?-?\d+\.?/g).join('').replace(/\(/, '-') : _data[i][j];
                        var ij = /(?!\().+(?=\))/.test(_data[i][j]) ? _data[i][j].replace(/\((.+)\)/, function(a) {
                            return '-' + arguments[1];
                        }) : _data[i][j];

                        if (i == 0) {
                            _toLead.find('tr').eq(i).find('th').eq(j).text(ij).next('input').val(ij).attr('maxlength',ij.length).blur();
                        } else {
                            if (j == 0) {
                                _toLead.find('tr').eq(i).find('td').eq(j).text(ij).next('input').val(ij).attr('maxlength',ij.length).blur();
                            } else {
                                _toLead.find('tr').eq(i).find('td').eq(j).find('input').val(ij).attr('maxlength',ij.length).blur();
                            }
                        }
                    }
                }

                // $('#test3').find('tr').each(function(index, el) {

                //     $(this).find('td:eq(3) input').val(_totalAssets - _grossLiability);

                // });

                // $('#t-test3').text(_tTest3.replace('name="totalCapitalStr"', 'name="totalCapitalStr" value="' + (_totalAssets - _grossLiability) + '"'));

                // $('body').find('.abilityLevel').change();

            }

        });

    });

    //require('zyw/project/bfcg.itn.recordCommon')(rulesAll) //高管公用


    // $('body').on('change keyup', '.administrationNo', function(event) { //税务登记

    //     var $this, $siblings, val, siblingsVal;

    //     $this = $(this);
    //     $siblings = $this.parent().siblings().find('.administrationNo');
    //     val = $this.val();
    //     siblingsVal = $siblings.val();

    //     if (val) {

    //         $this.removeClass('cancel');

    //         if (siblingsVal) {

    //             $siblings.removeClass('cancel').blur();

    //         } else {

    //             $siblings.addClass('cancel').next('.error-tip').hide();

    //         }

    //     } else {

    //         if (siblingsVal) {

    //             $this.addClass('cancel');
    //             $siblings.removeClass('cancel');

    //         } else {

    //             $this.removeClass('cancel');
    //             $siblings.removeClass('cancel');

    //         }

    //     }


    // }).change();

    var util = require('util');

    $('body').on('click', '.information', function(event) {

        var $this, $user, $No;

        $this = $(this);
        $user = $this.parents('table').find('.informationUser').val();
        $No = $this.parents('table').find('.informationNo').val();

        if ($user && $No) {

            util.risk2retrieve($user, $No);

        } else if (!$No && !$user) {

            hintPopup('请输入保证人名称和营业执照号');

        } else if (!$user && $No) {

            hintPopup('请输入保证人名称');

        } else if (!$No && $user) {

            hintPopup('请输入营业执照号');

        }

    });

    var openDirect = util.openDirect;

    $('body').on('click', '#customerId', function(event) {

        openDirect('/assetMg/index.htm', '/assetMg/list.htm?customerId=' + $(this).attr('customerId'), '/assetMg/list.htm');

    });


    //////////////////////////改版部分///////////////////////////////////////////////////////////////////

    $('body').on('change', '.maxtermChange', function(event) { //显示隐藏

        var $this, $index, $targetTbody, $targetMaxterm;

        $this = $(this);
        $index = $this.find('option:checked').index();
        $targetTbody = $this.parents('table').find('tbody').eq($index);
        $targetMaxterm = $this.parents('.maxterm').find('ul.maxtermType li').eq($index);

        $targetTbody.show().find('[name]').removeAttr('disabled').removeClass('cancel').end().siblings('tbody').hide().find('[name]').attr('disabled', true).addClass('cancel');
        $targetMaxterm.show().find('[name]').removeAttr('disabled').removeClass('cancel').end().siblings('li').hide().find('[name]').attr('disabled', true).addClass('cancel');

        $('body').find('.marriageCondition').trigger('change');
        $('body').find('.hasFolkLoanCheckbox').trigger('change');

    }).find('.maxtermChange').trigger('change');

    $('body').on('change', '.marriageCondition', function(event) {

        var $this, $val, $target, $targetNew;

        $this = $(this);
        $val = $this.val();
        $target = $this.parents('tr').nextAll('tr');
        $targetNew = $this.parents('.maxterm').find('.marriageTbodyNew');

        if ($val == '已婚') {

            $target.show().find('[name]').removeAttr('disabled').removeClass('cancel');
            $targetNew.show().find('[name]').removeAttr('disabled').removeClass('cancel');

        } else {

            $target.show().hide().find('[name]').attr('disabled', true).addClass('cancel');
            $targetNew.show().hide().find('[name]').attr('disabled', true).addClass('cancel');

        }

    }).find('.marriageCondition').trigger('change');


    function orderNameValidate(Box) {

        Box.find('[name]').each(function(index, el) {

            var $el, name, $orderNameTwo, orderNameTwo, orderNameTwoIndex, $orderNameThree, orderNameThree, orderNameThreeIndex;

            $el = $(el);
            name = $.fn.realNameOperationFun($el);
            $orderNameOne = $el.parents('[orderNameOne]');
            orderNameOne = $orderNameOne.attr('orderNameOne');
            orderNameOneIndex = $orderNameOne.index();
            $orderNameTwo = $el.parents('[orderNameTwo]');
            orderNameTwo = $orderNameTwo.attr('orderNameTwo');
            orderNameTwoIndex = $orderNameTwo.index();
            $orderNameThree = $el.parents('[orderNameThree]');
            orderNameThree = $orderNameThree.attr('orderNameThree');
            orderNameThreeIndex = $orderNameThree.index();

            if ($orderNameThree.length) {

                $el.attr('name', orderNameOne + '[' + orderNameOneIndex + '].' + orderNameTwo + '[' + orderNameTwoIndex + '].' + orderNameThree + '[' + orderNameThreeIndex + '].' + name);

            } else if ($orderNameTwo.length && !$orderNameThree.length) {

                $el.attr('name', orderNameOne + '[' + orderNameOneIndex + '].' + orderNameTwo + '[' + orderNameTwoIndex + '].' + name);

            } else {

                $el.attr('name', orderNameOne + '[' + orderNameOneIndex + '].' + name);

            }

            $.fn.addValidataFun($el, rulesAll);

        });

    }

    var $maxtermBox = $('.maxtermBox');

    orderNameValidate($maxtermBox); //初始化
    $_GLOBAL.setUE();


    $('body').on('click', '.fnAddLineMultistage', function(event) { //增加

        var $this, stage, Html, Box;

        $this = $(this);
        stage = $this.attr('stage');
        Html = $('.' + $this.attr('tplid')).html();

        if (stage == 1) {

            Box = $('.' + $this.attr('cttid'));

        } else if (stage == 2) {

            Box = $this.parents('[orderNameOne]').find('.' + $this.attr('cttid'));

        } else if (stage == 3) {

            Box = $this.parents('[orderNameTwo]').find('.' + $this.attr('cttid'));

        }

        Box.append(Html);
        orderNameValidate(Box);

        if (stage == 1) {

            $_GLOBAL.setUE(Box.find('.maxterm').last());
            $('body').find('.maxtermChange').trigger('change');
            $('body').find('.marriageCondition').trigger('change');

        }

    }).on('click', '.maxtermFnDelLine', function(event) { //减少

        var $this, stage, Box;

        $this = $(this);
        stage = $this.attr('stage');

        if (stage == 1) {

            Box = $this.parents('[orderNameOne]').parent();
            $this.parents('[orderNameOne]').remove();

        } else if (stage == 2) {

            Box = $this.parents('[orderNameTwo]').parent();
            $this.parents('[orderNameTwo]').remove();

        } else if (stage == 3) {

            Box = $this.parents('[orderNameThree]').parent();
            $this.parents('[orderNameThree]').remove();

        }

        orderNameValidate(Box);

    });

    //////////////////上传高管//////////////////
    $('body').on('click', '.fnUpFile', function(event) {

        var $thisFnUpFile = $(this);

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/investigation/uploadExcel.htm?type=leaders',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function(a, b) {},
            onUploadSuccess: function($this, data, resfile) {

                var JSONdata = JSON.parse(data),
                    test = $thisFnUpFile.parents('dl').next();

                if (JSONdata.success) {

                    var Html = template('testDataList', JSONdata);

                    test.html(Html);

                    orderNameValidate(test);

                } else {

                    hintPopup(JSONdata.message);

                }


            }

        });

    });

    $('body').on('change', '.hasFolkLoanCheckbox', function(event) { //借贷显示

        var $this, $target;

        $this = $(this);
        $target = $this.parents('tr').find('td:eq(3) input');

        $this.is(':checked') ? $target.show().removeClass('cancel').removeAttr('disabled') : $target.hide().addClass('cancel').attr('disabled', true);

    }).find('.hasFolkLoanCheckbox').trigger('change');

    $('body').on('change', '.hasBankLoanCheckbox', function(event) {

        var $this, $target, $targetTr;

        $this = $(this);
        $target = $this.parent().siblings('.hasBankLoanSpan');
        $targetTr = $this.parents('tr').siblings('.target');

        $this.is(':checked') ? $target.show().find('[name]').removeClass('cancel').removeAttr('disabled') : $target.hide().find('[name]').addClass('cancel').attr('disabled', true);

        $this.is(':checked') ? $target.find('[name]').change() : $targetTr.hide().find('[name]').addClass('cancel').attr('disabled', true);

    }).find('.hasBankLoanCheckbox').trigger('change');

    $('body').on('change', '.t-hasBankLoanNew', function(event) {

        var $this, $index, $target;

        $this = $(this);
        $index = $this.parent().index();
        $target = $this.parents('tr').siblings('.t-hasBankLoan' + $index);

        $this.is(':checked') ? $target.show().find('[name]').removeClass('cancel').removeAttr('disabled') : $target.hide().find('[name]').addClass('cancel').attr('disabled', true);

    }).find('.t-hasBankLoanNew').trigger('change');

    $('body').on('change', '.credentialsType', function(event) { //校验

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


    $('body').on('click', '.verify', function(event) {

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
        if (util.checkIdcard(verifyCertNo) != '验证通过!') {
            return
        }

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
            .done(function(res) {
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
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
            });


    });


    require("Y-msg");
    //展示证件号
    $('body').on('click', '.checkImg', function(event) {

        var $this, url;

        $this = $(this);
        url = $this.attr('hrefurl');

        if (url) {

            var _img = new Image();
            _img.src = 'data:image/png;base64,' + url
            _img.onload = function() {
                var _div = document.createElement('div')
                _div.appendChild(_img)
                Y.alert('图像信息', _div);
            };

        }

    });

    //名称、证件号change控制查看图片按钮展示情况
    $('body').on('change', '.verifyName,.verifyCertNo', function(event) {

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


    $('body').on('change', '.credentialsType1', function(event) {

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

    $('body').on('change', '.credentialsType2', function(event) {

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

    $('body').on('click', '.verifyNew1', function(event) {

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
        //     hintPopup('请输入正确的身份证');
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
            .done(function(res) {
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
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
            });


    });

    $('body').on('click', '.verifyNew2', function(event) {

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
            .done(function(res) {
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
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
            });


    });
    require("Y-msg");
    //展示证件号
    $('body').on('click', '.checkImg', function(event) {

        var $this, url;

        $this = $(this);
        url = $this.attr('hrefurl');

        if (url) {

            var _img = new Image();
            _img.src = 'data:image/png;base64,' + url
            _img.onload = function() {
                var _div = document.createElement('div')
                _div.appendChild(_img)
                Y.alert('图像信息', _div);
            };

        }

    });
    //名称、证件号change控制查看图片按钮展示情况
    $('body').on('change', '.verifyName2,.verifyCertNo2', function(event) {

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
    $('body').on('change', '.verifyName1,.verifyCertNo1', function(event) {

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
    ////////////////////时间
    var yearsTime = require('zyw/yearsTime');
    $('body').on('focus', '.birth', function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: this,
            format: 'YYYY-MM',
            callback: function(_this, _time) {
                _this.val(_time[0] + '-' + ((_time[1].length == 1) ? '0' + _time[1] : _time[1]));
            }
        });
    });


    $('body').on('focus', '.birthStart', function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: this,
            format: 'YYYY',
            max: function(This) {
                return $(This).parents('tr').find('.birthStot').val();
            }
        });
    });

    $('body').on('focus', '.birthStot', function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: this,
            format: 'YYYY',
            soFar: true,
            min: function(This) {
                return $(This).parents('tr').find('.birthStart').val();
            }
        });
    });

    //搜索框时间限制
    $('body').on('blur', '.fnListSearchDateS', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateE');

        $input.attr('onclick', 'laydate({min: "' + this.value + '"})');

    }).on('blur', '.fnListSearchDateE', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateS');

        $input.attr('onclick', 'laydate({max: "' + this.value + '"})');

    }).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');



});