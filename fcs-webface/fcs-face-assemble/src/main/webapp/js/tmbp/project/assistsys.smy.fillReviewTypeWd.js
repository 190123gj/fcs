define(function(require, exports, module) {
	//Nav选中样式添加
	//require('zyw/project/bfcg.itn.toIndex');
	//选择授信用途
	require('zyw/chooseLoanPurpose');
	//必填集合
	require('zyw/requiredRules');
	//页面共用
	require('zyw/project/assistsys.smy.Common');
    //选择授信用途
    var loanPurpose = require('zyw/chooseLoanPurpose');
    loanPurpose.init('LOAN_PURPOSE')
    var publicOPN = require('zyw/publicOPN');
    var _publicOPN = new publicOPN();
    _publicOPN.init();
	$.smyCommon(true);
	//验证共用
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'timeRemark,assetRemark,belongToNc,beforeDay,depositAccount,debtedAmountStr,interestRate,totalCost,chargeRemark,assetType,ownershipName,evaluationPrice,pledgeRate,remark,guarantorType,itype,other,pathName_COUNTER_GUARANTEE,id,guarantor,guaranteeAmountStr,guaranteeWay,tabId,spId,summaryId,projectCode,cancel,isChargeEveryBeginning,checkIndex,checkStatus,formId,formCode,initiatorId,initiatorAccount,processFlag,isMaximumAmount',
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
		maxlength50Rules = _form.requiredRulesSharp('assetRemark,content,overview,projectOverview,chargeRemark,remark,assetType,ownershipName,evaluationPrice,pledgeRate,other,pathName_COUNTER_GUARANTEE,id,guaranteeAmountStr,tabId,spId,summaryId,projectCode,cancel,isChargeEveryBeginning,checkIndex,checkStatus,formId,formCode,initiatorId,initiatorAccount,processFlag,isMaximumAmount,upBp,downBp,assetLiabilityRatio,upRate,downRate', _allWhetherNull['boll'], {}, function(rules, name) {
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
		isPercentTwoDigitsRules = _form.requiredRulesSharp('interestRate,ratio,assetLiabilityRatio,upRate,downRate', false, {}, function(rules, name) {
			rules[name] = {
				isPercentTwoDigits: true,
				messages: {
					isPercentTwoDigits: '请输入100.00以内的数字'
				}
			};
		}),
		isSpecialRateRules = _form.requiredRulesSharp('upDownRate,assetLiabilityRatio,thresholdRatio,adjustRatio', false, {}, function(rules, name) {
			rules[name] = {
				isSpecialRate: true,
				messages: {
					isSpecialRate: '请输入整数8位以内,小数2位以内的数字'
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
			content: {
				maxlength: 20000,
				messages: {
					maxlength: '超出20000字'
				}
			},
			debtedAmountStr: {
				isMoneyCommon: true,
				messages: {
					isMoneyCommon: '请输入整数13位以内,小数2位以内的数字'
				}
			},
			synPosition: {
				required: true,
				messages: {
					required: '必填',
				}
			},
			postposition: {
				required: true,
				messages: {
					required: '必填',
				}
			},
			debtedAmount: {
				isMoney: true,
				messages: {
					isMoney: '请输入整数14位以内,小数2位以内的数字'
				}
			},
			assetLiabilityRatio: {
				isSpecialRate: true,
				messages: {
					isSpecialRate: '请输入8位以内，小数点2位的数字'
				}
			},
			amount: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
				}
			},
			upBp: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
				}
			},
			downBp: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
				}
			},
			guarantorType: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			guarantor: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			guaranteeAmountStr: {
				required: true,
				isMoney: true,
				messages: {
					required: '必填',
					isMoney: '请输入整数位14位以内，小数位2位以内的数字'
				}
			},
			// guaranteeWay: {
			// 	required: true,
			// 	messages: {
			// 		required: '必填'
			// 	}
			// }
		},
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, digitsRules, isMoneyRules, isPercentTwoDigitsRules, isSpecialRateRules, maxlengthRules, rulesAllBefore);

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

	//反担保共用
	require('zyw/project/bfcg.itn.counterGuaranteeCommon')(_rulesAll, _form, _allWhetherNull.stringObj, true);

	//根据状态初始
	require('zyw/project/assistsys.smy.init')({
		form: _form
	});

	// 公共签报
	require('./assistsys.smy.fillReviewChange')

});