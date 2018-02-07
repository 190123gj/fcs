define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //必填集合
    require('zyw/requiredRules');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //上传
    require('zyw/upAttachModify');
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        _allWhetherNull = 'resume,leaderReview,staffReview',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, false, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('token,preUrl,nextUrl,pathName_FORM_ATTACH,leaderReview,staffReview', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRules2 = _form.requiredRulesSharp('sex', false, {}, function(rules, name) {
            rules[name] = {
                checkZh: true,
                messages: {
                    checkZh: '请输入汉字'
                }
            };
        }),
        requiredRules3 = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
            rules[name] = {
                maxlength: 10000,
                messages: {
                    maxlength: '已超出10000字'
                }
            };
        }),
        rulesAllBefore = { //所有格式验证的基
            // age: {
            //     digits: true,
            //     maxlength: 3,
            //     messages: {
            //         digits: '请输入正整数',
            //         maxlength: '已超出3字'
            //     }
            // },
            degree: {
                maxlength: 10,
                messages: {
                    maxlength: '已超出10字'
                }
            },
            resume: {
                maxlength: 500,
                messages: {
                    maxlength: '已超出500字'
                }
            },
            // startDate: {
            //     isTime: true,
            //     messages: {
            //         isTime: '请输入正确的时间格式'
            //     }
            // },
            // endDate: {
            //     isTime: true,
            //     messages: {
            //         isTime: '请输入正确的时间格式'
            //     }
            // }
        },
        rulesAll = $.extend(true, requiredRulesMaxlength, rulesAllBefore, requiredRules1, requiredRules2, requiredRules3);

    // 保存并返回
    var ONLY_VALUE = 0

    $('#saveSubmit').click(function () {
        ONLY_VALUE = 1;
    })
    ValidataCommon(rulesAll, _form, _allWhetherNull, false, function(res) {
        if(ONLY_VALUE === 1){
            showResult(res, hintPopup(res.message,$("#backUrl").attr('data-url')));
        } else {
            showResult(res, hintPopup);
        }
    });

    require('zyw/project/bfcg.itn.recordCommon')(rulesAll) //高管公用



});