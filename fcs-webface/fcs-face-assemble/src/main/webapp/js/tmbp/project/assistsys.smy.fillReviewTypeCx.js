define(function(require, exports, module) {
	//Nav选中样式添加
	//require('zyw/project/bfcg.itn.toIndex');
	//必填集合
	require('zyw/requiredRules');
	//页面共用
	require('zyw/project/assistsys.smy.Common');
	$.smyCommon(true);

	//验证共用
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'beforeDay,chargeRemark,remark,id,tabId,spId,summaryId,projectCode,cancel,checkIndex,checkStatus,isChargeEveryBeginning,formId,formCode,initiatorId,initiatorAccount',
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
		maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',overview,projectOverview,chargeRemark,remark', _allWhetherNull['boll'], {}, function(rules, name) {
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
		rulesAllBefore = { //所有格式验证的基

			// remark: {
			// 	maxlength: 1000,
			// 	messages: {
			// 		maxlength: '已超出1000字'
			// 	}
			// },
			// chargeRemark: {
			// 	maxlength: 1000,
			// 	messages: {
			// 		maxlength: '已超出1000字'
			// 	}
			// },
			// overview: {
			// 	maxlength: 1000,
			// 	messages: {
			// 		maxlength: '已超出1000字'
			// 	}
			// },
			amount: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
				}
			},
			releaseRate: {
				isPercentTwoDigits: true,
				messages: {
					isPercentTwoDigits: '请输入100.00以内的数字'
				}
			}
		},
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, digitsRules, rulesAllBefore);

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

	//单位改变验证
	require('zyw/project/assistsys.smy.unitChange');

	//字数提示
	require('zyw/hintFont');

	//根据状态初始
	require('zyw/project/assistsys.smy.init')({
		form: _form
	});

	// 公共签报
	require('./assistsys.smy.fillReviewChange')

});