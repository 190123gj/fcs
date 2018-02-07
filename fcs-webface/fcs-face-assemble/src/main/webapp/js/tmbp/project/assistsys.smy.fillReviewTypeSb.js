define(function(require, exports, module) {
    //Nav选中样式添加
    //require('zyw/project/bfcg.itn.toIndex');
    //选择授信用途
    require('zyw/chooseLoanPurpose');
    //必填集合
    require('zyw/requiredRules');
    //页面共用
    require('zyw/project/assistsys.smy.Common');
    $.smyCommon(true);
    //验证共用
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'belongToNc,beforeDay,depositAccount,chargeRemark,remark,id,tabId,spId,summaryId,projectCode,cancel,isChargeEveryBeginning,checkIndex,checkStatus,formId,formCode,initiatorId,initiatorAccount,isMaximumAmount',
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
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',overview,projectOverview,chargeRemark,remark,assureObject', _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        digitsRules = _form.requiredRulesSharp('timeLimit,beforeDay', false, {}, function(rules, name) {
            rules[name] = {
                digits: true,
                maxlength: 9,
                messages: {
                    digits: '请输入9位以内的整数',
                    maxlength: '请输入9位以内的整数'
                }
            };
        }),
        isMoneyRules = _form.requiredRulesSharp('amountStr,guaranteeAmountStr', false, {}, function(rules, name) {
            rules[name] = {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数位14位以内，小数位2位以内的数字'
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
        rulesAllBefore = {
            amount: {
                isMoneyMillion: true,
                messages: {
                    isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
                }
            }
        },
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, digitsRules, isMoneyRules, maxlengthRules, rulesAllBefore);

    var submitValidataCommon = require('zyw/submitValidataCommon'),
        isNotShowSidebar = document.getElementById('fnChangeApplyPost') ? true : false;
    submitValidataCommon.submitValidataCommon({
        notShowSidebar: isNotShowSidebar,
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

    //单位改变验证
    require('zyw/project/assistsys.smy.unitChange');

    //根据状态初始
    require('zyw/project/assistsys.smy.init')({
        form: _form
    });

    // 公共签报
    require('./assistsys.smy.fillReviewChange')

});