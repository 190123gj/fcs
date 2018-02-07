define(function(require, exports, module) {
	require('input.limit');
	var hintPopup = require('zyw/hintPopup');
	require("Y-msg");
	//必填集合
	require('zyw/requiredRules');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    //收费总金额
    var MAX = ($('#incomeConfirmInfo').html().replace('元','')).replace(/\,/g, '')

    var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'incomeConfirmedAmount',
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
		rulesAllBefore = {
			incomeConfirmedAmount: {
				isMoneyMillion: true,
				summationConfirmed: {
					max: MAX,
					box: '#incomeConfirmInfoTable',
					obj: '.money'
				},
				messages: {
					isMoneyMillion: '请输入整数位14位小数点2位以内的数字',
					summationConfirmed: '收入确认金额总和超出收费总金额'
				}
			}
		},
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
		submitValidataCommonUps = require('zyw/submitValidataCommonUp');

	//summationUp
	jQuery.validator.addMethod('summationConfirmed', function(value, element, param) {

		var $This, max, sum;

		$This = $(element);
		max = typeof param.max == 'function' ? param.max($This) : param.max;
		min = typeof param.min == 'function' ? param.min($This) : param.min;
		sum = 0;

		$This.parents(param.box).find(param.obj).each(function(index, el) {

			var _val = parseFloat($(el).val() || 0);

			if (!_val || !$(el).parent().next().find('input').is(':checked')) return true;

			sum += _val

		});

		return this.optional(element) || (max ? (sum <= max) : true) && (min ? (sum >= min) : true);

	}, $.validator.format("请输入{0}"));

	submitValidataCommonUps.submitValidataCommonUp({

		form: _form, //form
		allWhetherNull: _allWhetherNull, //必填集合与是否反向判断
		rulesAll: _rulesAll, //验证全集
		allEvent: {

			// replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
			// replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
			contentBtn: [{
					clickObj: '#submit',
					eventFun: function(jsonObj) {

						var $checkbox = $('[type="checkbox"]:not(:checked)');

						$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

						if ($checkbox.length) {

							if (jsonObj.objList['form'].valid()) {

								Y.confirm('选择框', '已确认的金额有修改，是否重新确认', function(opn) {

									if (opn == 'yes') {


									} else {

										jsonObj.portInitVal['submitHandlerContent'] = {

											// validateData: {

											//     checkStatus: jsonObj.setInitVal['checkStatus']

											// },
											submitStatus: 'submit',
											fm5: jsonObj.setInitVal['fm5']

										}; //向validate文件里的submitHandler暴露数据接口

										jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

										jsonObj.objList['form'].submit(); //提交
									}

								});

							}

						} else {

							jsonObj.portInitVal['submitHandlerContent'] = {

								// validateData: {

								//     checkStatus: jsonObj.setInitVal['checkStatus']

								// },
								submitStatus: 'submit',
								fm5: jsonObj.setInitVal['fm5']

							}; //向validate文件里的submitHandler暴露数据接口

							jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

							jsonObj.objList['form'].submit(); //提交

						}


					}

				},
				// {
				// 	clickObj: '[sortOrder]',
				// 	eventFun: function(jsonObj) {

				// 		$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

				// 		jsonObj.portInitVal['submitHandlerContent'] = {

				// 			// validateData: {

				// 			//     checkStatus: jsonObj.setInitVal['checkStatus']

				// 			// },
				// 			submitStatus: 'sortOrder',
				// 			fm5: jsonObj.setInitVal['fm5']

				// 		}; //向validate文件里的submitHandler暴露数据接口

				// 		jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

				// 		jsonObj.objList['form'].submit(); //提交

				// 	}
				// }
			], //内容区提交组

		},
		ValidataInit: {

			successBeforeFun: function(res) {

				var util = require('util'),
					loading = new util.loading();

				var fnMakeMoneyfnMakeMicrometer = $('.fnMakeMoney.fnMakeMicrometer');

				fnMakeMoneyfnMakeMicrometer.each(function(index, el) {
					var $el = $(el);
					$el.val($el.val().replace(/\,/g, ''))
				});

				loading.open();
				$.fn.orderName();

				if (res['submitStatus'] == 'submit') { //二级页面

					if (res['fm5']) { //有改变

						$('.submitStatus').attr('submitStatus', res['submitStatus']);

						$('[type="checkbox"]:not(:checked)').each(function(index, el) {

							var $this, $target;

							$this = $(this);
							$target = $this.parent().prev().find('input');

							$target.val($target.siblings('.replaceSpan').text());

						});

						$('[type="checkbox"]:disabled').removeAttr('disabled');

						return true;

					} else { //无改变

						hintPopup('数据未改变，保持原有储存', window.location.href);
						$('.m-modal-box').remove();
						return false;

					}

				} else if (res['submitStatus'] == 'sortOrder') {

					$('.submitStatus').attr('submitStatus', res['submitStatus']);
					return true;

				}

			},

			successFun: function(res) {

				// //弹窗提示
				// var hintPopup = require('zyw/hintPopup');

				if (res['success']) {

					// $.ajax({

					// 	url: '/projectMg/form/submit.htm',
					// 	type: 'post',
					// 	dataType: 'json',
					// 	data: {
					// 		formId: res['form']['formId'],
					// 		_SYSNAME: 'FM'
					// 	},
					// 	success: function(data) {

					// 		hintPopup(data.message, function() {

					// 			if (data.success) {
					// 				window.location.href = window.location.href;
					// 			}

					// 			$('.m-modal-box').remove();

					// 		});

					// 	}

					// });
					if ($('.submitStatus').attr('submitStatus') == 'submit') {

						$('.m-modal-box').remove();
						hintPopup(res.message, window.location.href);

					}
					// else if ($('.submitStatus').attr('submitStatus') == 'sortOrder') {

					// 	var sortOrder = window.location.href.match(/\&sortOrder\=(((.*?)(?=\&))|(.*))/);

					// 	$('.m-modal-box').remove();
					// 	window.location.href = '/fundMg/incomeConfirm/detailSorts.htm?sortCol=income_period&incomeId=' + $('#incomeConfirmInfoTable').attr('incomeId') + '&sortOrder=' + (sortOrder ? (sortOrder[1] == 'asc') ? 'desc' : 'asc' : 'asc');

					// }

				} else {

					$('.m-modal-box').remove();
					hintPopup(res['message']);

				}

			},
			// errorAll: { //validata error属性集

			//     errorClass: 'errorClassConfirm',
			//     errorElement: 'em',
			//     errorPlacement: function(error, element) {
			//         if(element.hasClass('radioSpan')){

			//         }
			//         element.after(error)
			//     }

			// }
		},
		callback: function(objList) { //加载时回调

			//验证方法集初始化
			$.fn.addValidataCommon(objList['rulesAll'], true)
				.initAllOrderValidata();

			// $('body').on('change', '.score', function(event) {

			// 	$('.score').blur();

			// });

		}

	})

	//收起
	$('.packBtn').click(function(event) {
		var _this = $(this);
		_this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
		_this.parents('dt').next().slideToggle();
	});

	var $modification, $submit;

	$modification = $('#modification');
	$submit = $('#submit');

	$modification.click(function(event) {

		var $this;

		$this = $(this);

		$this.hide().nextAll().show();

		$('#table').find('.replaceSpan').hide().prev().show();

	});

	//修改变化
	$('body').on('change', '.money', function(event) {

		var $this, val, standardVal, $targer;

		$this = $(this);
		val = $this.val();
		standardVal = $this.siblings('.replaceSpan').text();
		$targer = $this.parent().next().find('input');

		if ($targer.attr('affirm') == 'Y') { //初始确认过的

			if (val != standardVal) {

				$this.addClass('signBackground');
				$targer.removeAttr('disabled').removeAttr('checked');

			} else {

				$this.removeClass('signBackground');
				$targer.attr({
					'checked': true,
					'disabled': true
				});

			}

		} else { //初始未确认过的

			if (val != standardVal) {

				$this.addClass('signBackground');

			} else {

				$this.removeClass('signBackground');

			}

			$targer.removeAttr('checked');

		}

	});

	//var sortOrder = window.location.href.match(/\&sortOrder\=(((.*?)(?=(\&|\#)))|(.*))/);
	//js引擎
	var template = require('arttemplate');
	//排序
	$('[sortOrder]').click(function(event) {

		//window.location.href = '/fundMg/incomeConfirm/detailSorts.htm?sortCol=income_period&incomeId=' + $('#incomeConfirmInfoTable').attr('incomeId') + '&sortOrder=' + (sortOrder ? (sortOrder[1] == 'asc') ? 'desc' : 'asc' : 'asc') + '#incomeId';

		var $this, sortOrder;

		$this = $(this);
		sortOrder = $this.attr('sortOrder');

		$.ajax({
				url: '/fundMg/incomeConfirm/detailSorts.htm?sortCol=income_period&incomeId=' + $('#incomeConfirmInfoTable').attr('incomeId') + '&sortOrder=' + sortOrder,
				type: 'POST',
				dataType: 'json',
				// data: {
				// 	param1: 'value1'
				// },
			})
			.done(function(res) {

				if (res.incomeConfirmDetailInfo.length) {

					var Html = template('HtmlTemplate', res);
					$('#incomeConfirmInfoTable').html(Html);

				}

			})
			.fail(function() {
				console.log("error");
			})

		if (sortOrder == 'asc') {

			$this.attr('sortOrder', 'desc');
			$this.find('b').remove().end().append('<b style="font-size:20px">↑</b>');

		} else {

			$this.attr('sortOrder', 'asc');
			$this.find('b').remove().end().append('<b style="font-size:20px">↓</b>');

		}

	});



})