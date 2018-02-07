define(function(require, exports, module) {
	//Nav选中样式添加
	//require('zyw/project/bfcg.itn.toIndex');
	//必填集合
	require('zyw/requiredRules');
	//页面共用
	require('zyw/project/assistsys.smy.Common');
    //选择授信用途
    var loanPurpose = require('zyw/chooseLoanPurpose');
    loanPurpose.init('LOAN_PURPOSE')
	$.smyCommon(true);

	require('zyw/upAttachModify');//上传

    // var publicOPN = require('zyw/publicOPN');
    // var _publicOPN = new publicOPN();
    // _publicOPN.init();
	//验证共用
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'pathName_SUMMARY_INNOVATIVE_PRODUCT,overview,timeRemark,assetRemark,belongToNc,channelId,channelCode,channelType,beforeDay,depositAccount,debtedAmountStr,interestRate,totalCost,chargeRemark,assetType,ownershipName,evaluationPrice,pledgeRate,remark,guarantorType,itype,other,pathName_COUNTER_GUARANTEE,id,guarantor,guaranteeAmountStr,guaranteeWay,tabId,spId,summaryId,projectCode,cancel,checkIndex,checkStatus,isChargeEveryBeginning,formId,formCode,initiatorId,initiatorAccount,processFlag,isMaximumAmount',
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
		maxlength50Rules = _form.requiredRulesSharp('pathName_INVESTIGATION_INNOVATIVE_PRODUCT,assetRemark,chargeRemark,overview,projectOverview,chargeRemark,remark,other,content,assetType,ownershipName,evaluationPrice,pledgeRate,pathName_COUNTER_GUARANTEE,id,guaranteeAmountStr,tabId,spId,summaryId,projectCode,cancel,checkIndex,checkStatus,isChargeEveryBeginning,formId,formCode,initiatorId,initiatorAccount,processFlag,isMaximumAmount,upBp,downBp,assetLiabilityRatio,upRate,downRate', _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				maxlength: 100000,
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

	//资金渠道
	var popupWindow = require('zyw/popupWindow');

	$('#fundDitchBtn').click(function(event) {

		var $this = $(this);

		fundDitch = new popupWindow({

			YwindowObj: {
				content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
				closeEle: '.close', //find关闭弹窗对象
				dragEle: '.newPopup dl dt' //find拖拽对象
			},

			ajaxObj: {
				url: '/projectMg/investigation/findFInvestigationFinancialReviewData.htm',
				type: 'post',
				dataType: 'json',
				data: {
					id: 46677
				}
			},

			pageObj: { //翻页
				clickObj: '.pageBox a', //find翻页对象
				attrObj: 'page', //翻页对象获取值得属性
				jsonName: 'pagesss', //请求翻页页数的dataName
				callback: function($Y) {

					console.log($Y)

				}
			},

			formAll: { //搜索
				submitBtn: '#PopupSubmit', //find搜索按钮
				formObj: '#PopupFrom', //find from
				callback: function($Y) {

					console.log($Y)

				}
			},

			navObj: { //选卡
				clickObj: '.name li', //find 选卡对象
				attrObj: 'code', //选卡对象获取值得属性
				jsonName: 'navCode', //请求选卡code的dataName
				activeObj: 'active', //find 选中样式
				defaultObj: '555554', //默认选中项index、attrObj val
				callback: function($Y) {

					console.log($Y)

				}
			},

			callback: function($Y) {

				$Y.wnd.on('click', 'a.choose', function(event) {

					// var _this = $(this),
					//     _parents = _this.parents('.tableAll'),
					//     _name = _parents.find('.name').text(),
					//     _marketPrice = _parents.find('.marketPrice').text();

					var _this = $(this),
						_index = _this.index(),
						_parents = _this.parents('.tableAll'),
						//_name = _parents.find('.name li').eq(_index).text(),
						_marketPrice = _parents.find('.marketPrice li').eq(_index).text();

					$this.siblings('[name="capitalChannelName"]').val(_marketPrice);

					$Y.close();

				});

			},

			console: false //打印返回数据

		});

	});

	// 公共签报
	require('./assistsys.smy.fillReviewChange')


});