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
    //上传
    require('zyw/upAttachModify');
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        _allWhetherNull = 'financialCondition,investment,otherEvents',
        requiredRules1 = _form.requiredRulesSharp('financialCondition,investment,otherEvents', false, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRules2 = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过10000字'
                },
                maxlength: 10000

            };
        }),
        rulesAllBefore = { //所有格式验证的基

        },
        rulesAll = $.extend(true, requiredRules1, requiredRules2, rulesAllBefore);

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


});