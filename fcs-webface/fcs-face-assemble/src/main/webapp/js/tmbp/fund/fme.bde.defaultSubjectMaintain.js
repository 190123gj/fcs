define(function(require, exports, module) {

	var hintPopup = require('zyw/hintPopup');
	//必填集合
	require('zyw/requiredRules');
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'wu',
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
		maxlength50Rules = _form.requiredRulesSharp('subject', _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				maxlength: 50,
				messages: {
					maxlength: '已超出50字'
				}
			};
		}),
		rulesAllBefore = {

		},
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
		submitValidataCommonUps = require('zyw/submitValidataCommonUp');

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

					$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

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
			}], //内容区提交组

		},
		ValidataInit: {

			successBeforeFun: function(res) {

				var util = require('util'),
					loading = new util.loading();

				loading.open();
				$.fn.orderName();

				if (res['submitStatus'] == 'submit') { //二级页面


					if (res['fm5']) { //有改变

						$('.submitStatus').attr('submitStatus', res['submitStatus']);
						return true;

					} else { //无改变

						hintPopup('数据未改变，保持原有储存', window.location.href);
						$('.m-modal-box').remove();
						return false;

					}

				} else {

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
					$('.m-modal-box').remove();
					hintPopup(res.message, '/fundMg/sysSubject/list.htm?subjectType=' + $('[name="subjectType"]').val());

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
			$('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
				.initAllOrderValidata()
				.assignGroupAddValidataUpUp();

			// $('body').on('change', '.score', function(event) {

			// 	$('.score').blur();

			// });

		}

	})

	//页面操作

	var $modification, $submit, $defaultSubject;

	$modification = $('#modification');
	$submit = $('#submit');
	$defaultSubject = $('.subjectBtn');

	$modification.click(function(event) {

		var $this;

		$this = $(this);

		$this.hide().nextAll().show();

		$defaultSubject.show().siblings('input').show().siblings('p').hide();

	});

	var applyAddPopupCommom = require('zyw/fund/fme.applyAddPopupCommom')

	applyAddPopupCommom({
		formSource: '/baseDataLoad/receiptForm.json?type=payment',
		htmlSource: '/fundMg/payment/apply/form.htm'
	})

	$('[name="subjectType"]').change(function(event) {

		var val = $(this).val();

		window.location.href = '/fundMg/sysSubject/list.htm?subjectType=' + val;

	});

})