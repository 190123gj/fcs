define(function (require, exports, module) {
	//决策依据
	var COMMON = require('./bfcg.contract.common');
	//合同编号弹窗
	var popupWindow = require('zyw/popupWindow');
	//必填集合
	require('zyw/requiredRules');
	//字数提示
	require('zyw/hintFont');
	//弹窗
	require('Y-window');
	//弹窗提示
	var hintPopup = require('zyw/hintPopup');
	var _autoProjectCode = $('#projectCode');

	function refundApplicationAdd() {

		$('input,textarea,select').each(function (index, el) {

			$(this).removeAttr('disabled');

		});

		$('[hasnopf]').show();

	}

	// 2017.01.10 修复新增一行 批复问题
	var IS_HAVE_APPROVAL = false

	$('.fnAddLine').on('click', function () {

		setTimeout(function () {

			if (!IS_HAVE_APPROVAL) {
				$('#test .fnChooseGist').last().attr('hasnopf', 'IS')
			}

		}, 50)

	})

	//验证
	var _form = $('#form'),
		addJson;

	if (_autoProjectCode.val()) {
		refundApplicationAdd();
	}

	if ($('#refundResult').val()) {

		addJson = JSON.parse($('#refundResult').val())

	} else {

		if (_autoProjectCode.val()) {

			$.ajax({
					url: '/projectMg/refundApplication/add.htm?time=' + new Date(),
					type: 'POST',
					dataType: 'json',
					async: false,
					data: {
						projectCode: _autoProjectCode.val()
					},
				})
				.done(function (res) {

					if (res.refundResult.success) {

						addJson = res.refundResult

					}
					console.log(addJson);

				})
				.fail(function () {

					console.log("error");

				});

		}

	}


	var _allWhetherNull = {
			stringObj: 'refundReason,refundAmount,remark,projectCode',
			boll: false
		},
		requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function (rules, name, This) {
			rules[name] = {
				required: true,
				messages: {
					required: '必填'
				}
			};
		}),
		rulesAllBefore = {
			remark1: {
				maxlength: 1000,
				messages: {
					maxlength: '已超出1000字'
				}
			},
			remark: {
				maxlength: 20,
				messages: {
					maxlength: '已超出20字'
				}
			},
			refundAmount: {
				isMoneyCommon: true,
				max: function (This) {

					var _this, _selectVal;

					_this = $(This);
					_selectVal = _this.parent().siblings('td').find('select').val().toLowerCase().replace(/[_]([a-z])/gi, function (s, m) {

						return m.toUpperCase();

					});
					//console.log(addJson[_selectVal]['amount']);

					if (_selectVal) {


						return addJson[_selectVal]['amount'];

					} else {

						return 0;

					}

				},
				min: 0.01,
				messages: {
					isMoneyCommon: '请输入整数位13位以内，小数位2位以内的数字',
					max: '超过本项目相关收费的的总额',
					min: '请输入大于0的数字'
				}
			}
		},
		_rulesAll = $.extend(true, requiredRules, rulesAllBefore),
		submitValidataCommonUp = require('zyw/submitValidataCommonUp');


	submitValidataCommonUp.submitValidataCommonUp({

		form: _form,
		rulesAll: _rulesAll,
		allWhetherNull: _allWhetherNull, //必填集合与是否反向判断
		allEvent: {

			// replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
			// replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
			contentBtn: [{
				clickObj: '#submit',
				eventFun: function (jsonObj) {

					$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

					jsonObj.portInitVal['submitHandlerContent'] = {

						validateData: {

							submitStatus: 'Y',
							checkStatus: jsonObj.setInitVal['checkStatus']

						},
						fm5: jsonObj.setInitVal['fm5']

					}; //向validate文件里的submitHandler暴露数据接口

					jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

					jsonObj.objList['form'].submit(); //提交

				}
			}], //内容区提交组
			broadsideBtn: [{
					name: '暂存',
					alias: 'temporarStorage',
					events: function (jsonObj) {

						$.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //是必填

						jsonObj.portInitVal['submitHandlerContent'] = {

							validateData: {

								submitStatus: 'N',
								checkStatus: 0

							},
							fm5: jsonObj.setInitVal['fm5']

						}; //向validate文件里的submitHandler暴露数据接口

						jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

						jsonObj.objList['form'].submit(); //提交

					}
				}, {
					name: '查看合同',
					alias: 'temporarStorage2',
					events: function (jsonObj) {

						var _projectCode = $('[name="projectCode"]').val();

						if (!_projectCode) {

							hintPopup('请先选择项目编号')
							return false;

						}

						fundDitch = new popupWindow({

							YwindowObj: {
								content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
								closeEle: '.close', //find关闭弹窗对象
								dragEle: '.newPopup dl dt' //find拖拽对象
							},

							ajaxObj: {
								url: '/projectMg/contract/contractChoose.htm',
								type: 'post',
								dataType: 'json',
								data: {
									projectCode: _projectCode,
									pageSize: 6,
									pageNumber: 1
								}
							},

							pageObj: { //翻页
								clickObj: '.pageBox a.btn', //find翻页对象
								attrObj: 'page', //翻页对象获取值得属性
								jsonName: 'pageNumber', //请求翻页页数的dataName
								callback: function ($Y) {

									//console.log($Y)

								}
							},

							callback: function ($Y) {

								$Y.wnd.on('click', 'a.choose', function (event) {

									$Y.close();

								});

							},

							console: false //打印返回数据

						});

					}
				}] //侧边栏提交组

		},
		ValidataInit: {

			successBeforeFun: function (res) {

				//				var _checkBasis = COMMON.checkBasis();

				//				if (!_checkBasis.success) {
				//
				//					hintPopup(_checkBasis.message)
				//
				//					return
				//				}

				var util = require('util'),
					loading = new util.loading();

				loading.open();
				$.fn.orderName();

				if (res.validateData['submitStatus'] == 'Y') { //提交

					$('.submitStatus').val(res.validateData['submitStatus']);
					return true;

				} else {

					if (res['fm5']) { //有改变

						$('.submitStatus').val(res.validateData['submitStatus']);
						return true;

					} else { //无改变

						hintPopup('数据未改变，保持原有储存');
						loading.close();
						return false;

					}

				}

			},

			successFun: function (res) {

				//弹窗提示
				var hintPopup = require('zyw/hintPopup');

				if (res['success']) {

					if ($('.submitStatus').val() == 'Y') {

						//hintPopup(res['message'], '/projectMg/file/fileCatalogMg.htm?type=' + res['type'] + '&doing=' + res['doing']);
						$.ajax({

							url: '/projectMg/form/submit.htm',
							type: 'post',
							dataType: 'json',
							data: {
								formId: res['formId']
							},

							success: function (data) {

								hintPopup(data.message, function () {

									if (data.success) {

										window.location.href = '/projectMg/refundApplication/list.htm';

									}

								});

								$('.m-modal-box').remove();

							}

						})

					} else if ($('.submitStatus').val() == 'N') {

						$('.m-modal-box').remove();
						hintPopup(res.message, '/projectMg/refundApplication/list.htm');

					}

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

		callback: function (objList) {

			//验证方法集初始化
			$('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
				.initAllOrderValidata()
				.assignGroupAddValidataUp(objList['rulesAll'], function (hintPopup) {

					if ($('#projectCode').val() == '') {

						hintPopup('请选关联项目');
						return false;

					} else {

						return true;

					}

				});

		}

	}); //validate初始化


	var searchForDetails = require('zyw/searchForDetails');
	//项目编号
	if (document.getElementById('chooseBtn')) {
		searchForDetails({
			ajaxListUrl: '/baseDataLoad/loadRefundApplicationByCharge.json?time=' + new Date(),
			btn: '#chooseBtn',
			codeInput: 'projectCode',
			tpl: {
				tbody: ['{{each pageList as item i}}',
					'    <tr class="fn-tac m-table-overflow onbreaks">',
					'        <td class="item onbreak" id="{{item.name}}">{{item.projectCode}}</td>',
					'        <td class="onbreak" title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+"..":item.customerName}}</td>',
					'        <td class="onbreak" title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+"..":item.projectName}}</td>',
					'        <td class="onbreak">{{item.busiTypeName}}</td>',
					'        <td class="onbreak">{{item.amount}}</td>',
					'        <td class="onbreak"><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
					'    </tr>',
					'{{/each}}'
				].join(''),
				form: ['项目名称：',
					'<input class="ui-text fn-w160" type="text" name="projectName">',
					'&nbsp;&nbsp;',
					'客户名称：',
					'<input class="ui-text fn-w160" type="text" name="customerName">',
					'&nbsp;&nbsp;',
					'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
				].join(''),
				thead: ['<th width="100">项目编号</th>',
					'<th width="120">客户名称</th>',
					'<th width="120">项目名称</th>',
					'<th width="100">授信类型</th>',
					'<th width="100">授信金额(元)</th>',
					'<th width="50">操作</th>'
				].join(''),
				item: 6
			},
			ajaxDetailsUrl: '/baseDataLoad/loadProjectData.json',
			fills: [{
				type: 'html',
				key: 'customerName',
				select: '#customerName'
			}, {
				type: 'html',
				key: 'projectName',
				select: '#projectName'
			}, {
				type: 'html',
				key: 'contractNumber',
				select: '#contractNumber'
			}, {
				type: 'html',
				key: 'creditType',
				select: '#creditType'
			}, {
				type: 'html',
				key: 'guarantyPeriodStart',
				select: '#guarantyPeriodStart'
			}, {
				type: 'html',
				key: 'guarantyPeriodEnd',
				select: '#guarantyPeriodEnd'
			}, {
				type: 'html',
				key: 'agencies',
				select: '#agencies'
			}, {
				type: 'html',
				key: 'amount',
				select: '#amount'
			}],
			callback: function (_data) {

				//$('#projectCode').val(_data.projectCode);
				$('#customerName').val(_data.customerName);
				$('.customerName').text(_data.customerName);
				$('.busiTypeName').text(_data.busiTypeName);
				$('#projectName').val(_data.projectName);
				$('.projectName').text(_data.projectName);
				$('.amount').text(_data.amount);

				refundApplicationAdd();

				$.ajax({
						url: '/projectMg/refundApplication/add.htm?time=' + new Date(),
						type: 'POST',
						dataType: 'json',
						async: false,
						data: {
							projectCode: _autoProjectCode.val()
						},
					})
					.done(function (res) {

						if (res.refundResult.success) {

							addJson = res.refundResult

							var data, html, htmlObj, add, newAdd, i;

							data = res.feeType;
							htmlObj = $('#t-test');
							add = htmlObj.text();
							html = '<option value="">请选择付款种类</option>';

							for (i = 0; i < data.length; i++) {

								html += '<option value="' + data[i].code + '">' + data[i].message + '</option>';

							};

							newAdd = add.replace(/\<option[\s\S]+\/option\>/, html);

							htmlObj.text(newAdd);

							$('.selectRefundReason').each(function (index, el) { //存在项添加

								var This;

								This = $(el);

								This.html(html);

							});

							$('.selectRefundReason').change();

							//决策依据
							var $ishaveApproval = $('.ishaveApproval'),
								contentIshaveApproval;

							$('.fnChooseGist').show();
							$('.ishaveApproval ul').text('');
							$('.ishaveApproval input').val('');

							if (res.ishaveApproval == 'IS') {
								IS_HAVE_APPROVAL = true
								$('.fnChooseGist').removeAttr('hasnopf');
								//$('.tableList').find('thead th:eq(4)').html('<em class="remind">*</em>决策依据');
								//								$ishaveApproval.html(['<div class="fnWhich fn-left">',
								//									'<a class="fn-f30" href="/projectMg/meetingMg/summary/approval.htm?projectCode=' + _data.projectCode + '" target="_banlk">批复</a>',
								//									'<input type="hidden" name="basisOfDecision">',
								//									'<input type="hidden" name="decisionType" value="PROJECT_APPROVAL">',
								//									'</div>',
								//									'<a class="fn-green fnChooseGist fn-left" href="javascript:void(0);">重新选择</a>',
								//								].join(''));
								//
								//								contentIshaveApproval = [
								//									'<td class="ishaveApproval fn-clear">',
								//									'<div class="fnWhich fn-left">',
								//									'<a class="fn-f30" href="/projectMg/meetingMg/summary/approval.htm?projectCode=' + _data.projectCode + '" target="_banlk">批复</a>',
								//									'<input type="hidden" name="basisOfDecision">',
								//									'<input type="hidden" name="decisionType" value="PROJECT_APPROVAL">',
								//									'</div>',
								//									'<a class="fn-green fnChooseGist fn-left" href="javascript:void(0);">重新选择</a>',
								//									'</td>'
								//								].join('');



							} else {

								//								$ishaveApproval.html(['<div class="fnWhich fn-left"></div>',
								//									'<a class="fn-green fnChooseGist fn-left" href="javascript:void(0);" hasnopf="IS">请选择</a>',
								//								].join(''));
								//
								//								contentIshaveApproval = [
								//									'<td class="ishaveApproval fn-clear">',
								//									'<div class="fnWhich fn-left"></div>',
								//									'<a class="fn-green fnChooseGist fn-left" href="javascript:void(0);" hasnopf="IS">请选择</a>',
								//									'</td>'
								//								].join('');
								IS_HAVE_APPROVAL = false
								$('.fnChooseGist').attr('hasnopf', 'IS');
								//$('.tableList').find('thead th:eq(4)').html('决策依据')

							}

							//htmlObj.text(htmlObj.text().replace(/\<td\sclass=\"ishaveApproval fn-clear\"\>[\s\S]+?\<\/td\>/, contentIshaveApproval));

						}


					})
					.fail(function () {

						console.log("error");

					});

			}
		});
	}

	$('body').on('change', '.selectRefundReason', function (event) {

		var _this, _val, _next;

		_this = $(this);
		_val = _this.val();
		_next = _this.parent().next().find('input');

		_val ? _next.removeAttr('readonly') : _next.attr('readonly', true);
		_next.blur();

	}).find('.selectRefundReason').change();


	//上传
	require('zyw/upAttachModify');

	$('body').on('change', '.fundCanPay', function (event) {

		var $this, val;

		$this = $(this);
		val = $this.val();

		$this.parents('tr').siblings().find('.fundCanPay').each(function (index, el) { //防重复

			if (val && ($(el).val() == val)) {

				hintPopup('不能选择重复的退费种类');
				$this.val('');
				$this.change();

				return false;

			}

		});

	})

})