define(function(require, exports, module) {

	//必填集合
	require('zyw/requiredRules');
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'applyUserName,paymentDate,remark,feeType,amountStr,account,fnAtCodeName',
			boll: false
		},
		requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				required: true,
				required: true,
				messages: {
					required: '必填'
				}
			};
		}),
		maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',remark,compareStandardValue', _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				maxlength: 1000,
				messages: {
					maxlength: '不超过1000字符'
				}
			};
		}),
		rulesAllBefore = {
			amountStr: {
				summationAnother: {
					dsd: true,
					obj: '[amountStr]',
					max: $('#totalAmount').text().replace(/\,/g, ''),
					conditionVali: function() {

						var _return = $('.choiceSelect ').val() == 'FORM' ? true : false;

						return _return;

					}
				},
				isMoney: true,
				max: function(This) {

					return parseFloat($(This).attr('amountStr')) || 999999999999999999999999999999;

				},
				accountBalance: true,
				messages: {
					isMoney: '请输入整数14位以内,小数2位以内的数字',
					max: function(a, This) {

						return '账户余额不足'; //'超出余额：' + (parseFloat($(This).attr('amountStr')) || 0) + '元';

					},
					summationAnother: '超出合计付款金额', //'超出合计付款金额' + $('#totalAmount').text() + '元',
					accountBalance: function(a, This) {

						return '账户余额不足'; //'该表该账户超出余额：' + (parseFloat($(This).attr('amountStr')) || 0) + '元';

					},
				}
			}
		},
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
		validataCommon = require('zyw/fund/fme.ibw.validataCommon');

	//accountBalance
	jQuery.validator.addMethod('accountBalance', function(value, element, param) {

		var $el, max, paymentAccount, $tbody, sum;

		$el = $(element);
		max = parseFloat($el.attr('amountStr')) || 0;
		paymentAccount = $el.parents('tr').attr('paymentAccount');
		$tbody = $el.parents('tbody');
		sum = 0

		$tbody.find('tr[paymentAccount=' + paymentAccount + ']').each(function(index, el) {

			var $this, $target;

			$this = $(this);
			$target = $this.find('[amountstr]');

			sum += (parseFloat($target.val()) || 0)

		});

		return this.optional(element) || paymentAccount ? sum <= max : true;

	}, $.validator.format("请输入正确的{0}"));


	var isSimple = $("#isSimple").val();
	validataCommon({
		form: _form,
		allWhetherNull: _allWhetherNull,
		rulesAll: _rulesAll,
		// hintPopup: '请选择增加的二级指标选择项',
		href: '/fundMg/payment/apply/list.htm?isSimple=' + isSimple,
		isSimple : isSimple
		// previewHref: 'cityEnterprise.htm?level=3&type=CTCW&view=true'
	});

	var applyAddPopupCommom = require('zyw/fund/fme.applyAddPopupCommom')

	applyAddPopupCommom({
		formSource: '/baseDataLoad/receiptForm.json?type=payment',
		htmlSource: '/fundMg/payment/apply/form.htm'
	})

	$('body').on('change', '.feeType', function(event) {

		var $this, $prev, val;

		$this = $(this);
		val = $this.val();
		$prev = $this.parents('tr').find('.feeTypePrev'); //付款账户
		$prev2 = $this.parents('tr').find('.feeTypePrev2'); //会计科目
		$prev3 = $this.parents('tr').find('.feeTypePrev3'); //收款账户

		if (val == 'INTERNAL_FUND_LENDING') {

			// $prev.rules('add', {
			// 	required: true,
			// 	messages: {
			// 		required: '必填'
			// 	}
			// });

			$prev3.attr('readonly', true).rules('add', {
				required: true,
				messages: {
					required: '必填'
				}
			});

		} else {

			// $prev.rules('remove', 'required');
			$prev3.removeAttr('readonly').rules('remove', 'required');

		}

		// if (val == 'DEPOSIT_PAID') {

		// 	$prev2.rules('add', {
		// 		required: true,
		// 		messages: {
		// 			required: '必填'
		// 		}
		// 	});

		// } else {

		// 	$prev2.rules('remove', 'required');

		// }

		$prev.blur();
		// $prev2.blur();
		$prev3.blur();

	}).find('.feeType').not('[disabled]').trigger('change');


})