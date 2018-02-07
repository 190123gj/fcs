define(function(require, exports, module) {


    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');

    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
  //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        _allWhetherNull = 'MReviewId,schemeId,submited,preUrl,token,nextUrl,csrReviewId,formId,projectCode,projectName,customerId,customerName,cancel,guarantor,abilityLevel,totalCapitalStr,intangibleAssetsStr,contingentLiabilityStr,guaranteeAmountStr',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, true, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('wu,token,preUrl,nextUrl', true, {}, function(rules, name) {
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
        requiredRules2 = _form.requiredRulesSharp('certificateCode', false, {}, function(rules, name) {
            rules[name] = {
                checkAZ09: true, //必填.数字或字母
                messages: {
                    checkAZ09: '请输入数字或字母'
                }

            };
        }),
        requiredRules3 = _form.requiredRulesSharp('loanCardNo', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    digits: '请输入整数',
                    maxlength: '已超过20字'
                },
                digits: true, //必填.正整数
                maxlength: 20

            };
        }),
        requiredRules4 = _form.requiredRulesSharp('leadersAppraise,mainlyReview.customerDevEvolution,amountLimitReview,timeLimitReview,loanPurposeReview,repaySourceReview,guarantorInfo,pledgeValue,reviewSummary,otherRepaySource', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过1000字'
                },
                maxlength: 1000

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
        requiredRulesMoneyMillion = _form.requiredRulesSharp('value,value1,value2,value3', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    isMoneyMillion: '请输入整数为12位以内，小数位为两位的数字'
                },
                isMoneyMillion: true

            };
        }),
        requiredRulesMoneyMillion = _form.requiredRulesSharp('totalCapitalStr,intangibleAssetsStr,contingentLiabilityStr,guaranteeAmountStr', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                },
                isMoney: true

            };
        }),
        rulesAllBefore = { //所有格式验证的基
            guarantor:{
                maxlength:20,
                messages:{
                    maxlength:'超出20字'
                }
            },
            busiLicenseNo: {
                messages: {
                    isBusinessLicense: '请输入正确的营业执照号'
                },
                isBusinessLicense: true
            },
            taxCertificateNo: {
                messages: {
                    isBusinessLicense: '请输入正确的税务登记证号'
                },
                isBusinessLicense: true
            },
            orgCode: {
                messages: {
                    isOrganizationBusinessLicense: '请输入正确的组织机构码'
                },
                isOrganizationBusinessLicense: true
            },
            loanCardNo: {
                messages: {
                    isCardNo: '请输入正确的贷款卡号'
                },
                isCardNo: true
            },
            degree: {
                maxlength: 10,
                messages: {
                    maxlength: '已超出10字'
                }
            },
            title: {
                maxlength: 20,
                messages: {
                    maxlength: '已超出20字'
                }
            },
            age: {
                digits: true,
                maxlength: 3,
                messages: {
                    digits: '有效整数',
                    maxlength: '已超出3字'
                }
            },
            resume: {
                maxlength: 500,
                messages: {
                    maxlength: '已超出500字'
                }
            }
        },
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRulesMaxlength20, requiredRules1, requiredRules2, requiredRules3, requiredRules4, requiredRules5, requiredRulesMoneyMillion, rulesAllBefore);

    ValidataCommon(rulesAll, _form, _allWhetherNull, true, function(res) {
    	showResult(res, hintPopup);
    });

    //上传
    require('Y-htmluploadify')

    $('.fnUpFile2').click(function() {
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
                        _toLead = $('#toLead');

                    for (var i = 0; i < _data.length; i++) {
                        for (var j = 0; j < _data[i].length; j++) {
                            if (i == 0) {
                                _toLead.find('tr').eq(i).find('th').eq(j).text(_data[i][j]).next('input').val(_data[i][j]).blur();
                            } else {
                                if (j == 0) {
                                    _toLead.find('tr').eq(i).find('td').eq(j).text(_data[i][j]).next('input').val(_data[i][j]).blur();
                                } else {
                                    _toLead.find('tr').eq(i).find('td').eq(j).find('input').val(_data[i][j]).blur();
                                }
                            }
                        }
                    }
                }
                //renderTo: 'body'
        });
        // htmlupload.show();
    });

    $('.fnUpFile1').click(function() {
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
                        _obj = $('#test2'),
                        _orderName = _obj.find('tr').attr('orderName');
                    _obj.html('');
                    for (var i = 1; i < _data.length; i++) {
                        var _string = '<tr class="fnNewLine" orderName="leadingTeams">' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].name"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].sex"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].age"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].degree"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].title"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].resume"></td>'
                        var _or;
                        (i!=1)?_or = '<td class="fn-text-c"><a class="ui-btn ui-btn-fill ui-btn-green fnDelLine" parentsClass="fnNewLine"><i class="icon icon-del"></i>删除一行</a></td></tr>':_or = '<td></td>';
                        _obj.append(_string+_or)
                        for (var j = 0; j < _data[i].length; j++) {
                            _obj.find('tr').eq(i - 1).find('td').eq(j).find('input').val(_data[i][j])
                        };
                    };
                }
                //renderTo: 'body'
        });
        // htmlupload.show();
    });


});