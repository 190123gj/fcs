define(function(require, exports, module) {
    //Nav选中样式添加
    //require('zyw/project/bfcg.itn.toIndex');
    //必填集合
    require('zyw/requiredRules');
    require('Y-window');
	//页面共用
	require('zyw/project/assistsys.smy.Common');
    $.smyCommon(true);
    //验证共用
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'remark,id,tabId,spId,summaryId,projectCode,cancel,isChargeEveryBeginning,checkIndex,checkStatus,formId,formCode,initiatorId,initiatorAccount,processFlag',
            boll: true
        },
        requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        maxlengthRules = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
            rules[name] = {
                maxlength: 1000,
                messages: {
                    maxlength: '已超出1000字'
                }
            };
        }),
        _rulesAll = $.extend(true, requiredRules, maxlengthRules);

    var submitValidataCommon = require('zyw/submitValidataCommon');
    submitValidataCommon.submitValidataCommon({
        rulesAll: _rulesAll,
        form: _form,
        allWhetherNull: _allWhetherNull
            // ValidataInit:{
            //     form: _form,
            //     successFun: function(res) {

        //             //响应成功操作
        //             //响应成功操作ssssssss

        //     }
        // } //有特殊响应,如无则配置zyw/submitValidataCommon公用响应
    });

    //字数提示
    require('zyw/hintFont');

    //根据状态初始、bpm选人
    require('zyw/project/assistsys.smy.init')({
        form: _form
    });

});