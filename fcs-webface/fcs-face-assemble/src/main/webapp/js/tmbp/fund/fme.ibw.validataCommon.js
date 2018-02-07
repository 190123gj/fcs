define(function(require, exports, module) {

	require('input.limit');
	// 弹窗提示
	var hintPopup = require('zyw/hintPopup'), submitValidataCommonUps = require('zyw/submitValidataCommonUp');

	function ibwValidataCommon(objJson) {

		submitValidataCommonUps.submitValidataCommonUp({

			form : objJson['form'], // form
			allWhetherNull : objJson['allWhetherNull'], // 必填集合与是否反向判断
			rulesAll : objJson['rulesAll'], // 验证全集
			allEvent : {

				// replaceContentBtn: true,
				// //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
				// replaceBroadsideBtn:
				// true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
				contentBtn : [ {
					clickObj : '#submit',
					eventFun : function(jsonObj) {

						if (objJson['condition']) {

							if (objJson['condition'](jsonObj) == false)
								return false;

						}

						$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); // 是必填

						jsonObj.portInitVal['submitHandlerContent'] = {

							// validateData: {

							// checkStatus: jsonObj.setInitVal['checkStatus']

							// },
							submitStatus : 'submit',
							fm5 : jsonObj.setInitVal['fm5']

						}; // 向validate文件里的submitHandler暴露数据接口

						jsonObj.getInitVal('zyw/submitValidataCommonUp'); // validate文件获取数据

						jsonObj.objList['form'].submit(); // 提交

					}
				} ], // 内容区提交组
				broadsideBtn : [ {
					name : '提交',
					alias : 'temporarStorage',
					events : function(jsonObj) {

						if (objJson['condition']) {

							if (objJson['condition'](jsonObj) == false)
								return false;

						}

						$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); // 是必填

						jsonObj.portInitVal['submitHandlerContent'] = {

							// validateData: {

							// checkStatus: jsonObj.setInitVal['checkStatus']

							// },
							submitStatus : 'submit',
							fm5 : jsonObj.setInitVal['fm5']

						}; // 向validate文件里的submitHandler暴露数据接口

						jsonObj.getInitVal('zyw/submitValidataCommonUp'); // validate文件获取数据

						jsonObj.objList['form'].submit(); // 提交

					}
				} ]
			// 侧边栏提交组

			},
			ValidataInit : {

				successBeforeFun : function(res) {

					var util = require('util'), loading = new util.loading();

					loading.open();
					$.fn.orderName();

					var fnMakeMoneyfnMakeMicrometer = $('.fnMakeMoney.fnMakeMicrometer');

					fnMakeMoneyfnMakeMicrometer.each(function(index, el) {
						var $el = $(el);
						$el.val($el.val().replace(/\,/g, ''))
					});

					if (res['submitStatus'] == 'submit') { // 二级页面

						// if (res['fm5']) { //有改变

						$('.submitStatus').attr('submitStatus', res['submitStatus']);
						return true;

						// } else { //无改变

						// hintPopup('数据未改变，保持原有储存');
						// $('.m-modal-box').remove();
						// return false;

						// }

					} else {

						$('.submitStatus').attr('submitStatus', res['submitStatus']);
						return true;

					}

				},

				successFun : function(res) {

					// //弹窗提示
					// var hintPopup = require('zyw/hintPopup');

					if (res['success']) {

						if (objJson['isSimple'] == "IS") {
							window.location.href = objJson['href'];
						} else {
							$.ajax({

								url : '/projectMg/form/submit.htm',
								type : 'post',
								dataType : 'json',
								data : {
									formId : res['form']['formId'],
									_SYSNAME : 'FM'
								},
								success : function(data) {

									hintPopup(data.message, function() {

										if (data.success) {
											window.location.href = objJson['href'];
										}

										$('.m-modal-box').remove();

									});

								}

							});
							// hintPopup(res.message, window.location.href);
						}
					} else {

						$('.m-modal-box').remove();
						hintPopup(res['message']);

					}

				},
			// errorAll: { //validata error属性集

			// errorClass: 'errorClassConfirm',
			// errorElement: 'em',
			// errorPlacement: function(error, element) {
			// if(element.hasClass('radioSpan')){

			// }
			// element.after(error)
			// }

			// }
			},

			callback : function(objList) { // 加载时回调

				// 验证方法集初始化
				$('.fnAddLine').addValidataCommon(objList['rulesAll'], true).initAllOrderValidata().assignGroupAddValidataUpUp();

				// $('body').on('change', '.score', function(event) {

				// $('.score').blur();

				// });

			}

		})

	}

	module.exports = ibwValidataCommon;

})