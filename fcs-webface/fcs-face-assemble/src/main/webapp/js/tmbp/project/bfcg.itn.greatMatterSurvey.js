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
        _allWhetherNull = 'financialCondition,investment,otherEvents',
        requiredRules1 = _form.requiredRulesSharp('financialCondition,investment,otherEvents',false,{},function(rules,name){
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRules2 = _form.requiredRulesSharp('financialCondition,investment,otherEvents',false,{},function(rules,name){
            rules[name] = {
                messages:{
                    maxlength:'已超过1000字'
                },
                maxlength:1000

            };
        }),
        rulesAllBefore = {//所有格式验证的基

        },
        rulesAll = $.extend(true,requiredRules1,requiredRules2,rulesAllBefore);

        ValidataCommon(rulesAll,_form,_allWhetherNull,false,function(res) {
        	showResult(res, hintPopup);
        });


});