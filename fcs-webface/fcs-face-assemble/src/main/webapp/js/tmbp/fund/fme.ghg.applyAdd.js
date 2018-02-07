define(function(require, exports, module) {

	//必填集合
	require('zyw/requiredRules');
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'receiptDate,feeType,amountStr,account,fnAtCodeName,applyUserName,backTime,receiptDate,remark,receiptDateStr',
			boll: false
		},
		requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				required: true,
				messages: {
					required: '必填'
				}
			};
		}),
		maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				maxlength: 50,
				messages: {
					maxlength: '已超出50字'
				}
			};
		}),
		// changeRules = _form.requiredRulesSharp('applyUserName,receiptDate', _allWhetherNull['boll'], {}, function(rules, name) {
		// 	rules[name] = {
		// 		required: true,
		// 		messages: {
		// 			required: function() {
		// 				return $('.choiceSelect').find(':selected').index() ? '必填' : '必填';
		// 			}
		// 		}
		// 	};
		// }),
		// relevanceRules = _form.requiredRulesSharp('projectName,applyDeptName,customerName', _allWhetherNull['boll'], {}, function(rules, name) {
		// 	rules[name] = {
		// 		required: true,
		// 		messages: {
		// 			required: '必填'
		// 		}
		// 	};
		// }),
		rulesAllBefore = {
			relevance: {
				required: true,
				messages: {
					required: '请选择关联单据'
				}
			},
			remark: {
				maxlength: 1000,
				messages: {
					maxlength: '已超出1000字'
				}
			},
			amountStr: {
				summationUp: {
					max: parseFloat($('#totalAmount').text().replace(/\,/g, '')),
					box: 'table',
					obj: '.amountStrVali'
				},
				isMoney: true,
				messages: {
					summationUp: '收款金额总和超出合计收款金额',
					isMoney: '请输入整数位14位，小数位两位的数字'
				}
			}

		},
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
		validataCommon = require('zyw/fund/fme.ibw.validataCommon');

	validataCommon({
		form: _form,
		allWhetherNull: _allWhetherNull,
		rulesAll: _rulesAll,
		// hintPopup: '请选择增加的二级指标选择项',
		href: '/fundMg/receipt/apply/list.htm',
		// previewHref: 'cityEnterprise.htm?level=3&type=CTCW&view=true'
	});


	var applyAddPopupCommom = require('zyw/fund/fme.applyAddPopupCommom')

	applyAddPopupCommom({
		formSource: '/baseDataLoad/receiptForm.json',
		htmlSource: '/fundMg/receipt/apply/form.htm'
	});

})