define(function(require, exports, module) {

	// 弹窗
	require('Y-window');
	// 弹窗提示
	var hintPopup = require('zyw/hintPopup');
	//上传
	require('zyw/upAttachModify')
	// 异步提交
	require('form')();
	// 表单验证
	require('validate');
	require('validate.extend');
	// 必填集合
	require('zyw/requiredRules');
	require("Y-msg");
	// 菊花
	var util = require('util'), loading = new util.loading();

	// 验证共用
	var _form = $('#form'), _allWhetherNull = {
		stringObj : 'isOther,isRedo,isExtend,isComp,cancel,formId,otherCombisRedo,redoTimeRemark,formCode,summaryId,councilId,checkStatus,tabId,extendData,compRemark,overview,pathName_COUNTER_GUARANTEE,pathName_RISK_HANDLE_ATTACH',
		boll : true
	}, requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
		rules[name] = {
			required : true,
			messages : {
				required : '必填'
			}
		}
	}), maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj']+',other,extendRemark,otherComb,redo', _allWhetherNull['boll'], {}, function(rules, name) {
		rules[name] = {
			maxlength : 50,
			messages : {
				maxlength : '已超出50字'
			}
		}
	}), isMoneyRules = _form.requiredRulesSharp('compPrincipalStr,compInterestStr,compPenaltyStr,compPenaltyInterestStr,compOtherStr,extendPrincipalStr1', false, {}, function(rules, name) {
		rules[name] = {
			isMoney : true,
			messages : {
				isMoney : '请输入整数位14位以内，小数位2位以内的数字'
			}
		}
	}), maxlengthRules = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
		rules[name] = {
			maxlength : 1000,
			messages : {
				maxlength : '已超出1000字'
			}
		};
	}), rulesAllBefore = {
		extendTimeLimit1 : {
			digits : true,
			maxlength : 8,
			messages : {
				digits : '请输入整数',
				maxlength : '已超出最大长度8字'
			}
		// 必填.正整数
		},
		principalAmount : {
			max : function(This) {

				var $This, Max;

				$This = $(this);
				Max = $This.attr('proceed');

				return Max;

			},
			messages : {

				max : '不能大于在保余额'

			}
		},
		other:{
            required : true,
            messages : {
                required : '必填'
            }
		},
		isComp : {
			requiredScheme : true,
			messages : {
				requiredScheme : '至少选一项'
			}
		},
		isRedo : {
			requiredScheme : true,
			messages : {
				requiredScheme : '至少选一项'
			}
		},
		isOther : {
			requiredScheme : true,
			messages : {
				requiredScheme : '至少选一项'
			}
		},
        overview:{
            required : true,
            messages : {
                required : '必填'
            }
		},
        guarantor:{
            required : true,
            messages : {
                required : '必填'
            }
		},
        guarantorType:{
            required : true,
            messages : {
                required : '必填'
            }
		},
        synPosition:{
            required : true,
            messages : {
                required : '必填'
            }
		},
        postposition:{
            required : true,
            messages : {
                required : '必填'
            }
		},
        guaranteeAmountStr:{
            required : true,
            messages : {
                required : '必填'
            }
		},
		isExtend : {
			requiredScheme : true,
			messages : {
				requiredScheme : '至少选一项'
			}
		}
	}, rulesAll = $.extend(true, maxlength50Rules, requiredRules, isMoneyRules, maxlengthRules, rulesAllBefore), tabId, fm5;

	jQuery.validator.addMethod('requiredScheme', function(value, element, param) {

		var $el, $siblings;
		$el = $(element);
		$siblings = $el.parent().siblings().find('input[type="checkbox"]:checked');

		if ($siblings.length ? true : $el.is(':checked')) {

			$el.parent().siblings().find('em').hide();

		}

		return $siblings.length ? true : $el.is(':checked');

	}, $.validator.format("请输入正确的{0}"));

    var tabId;
    $('#step li').click(function () {
        tabId = $(this).attr('tabId');
    })

	var isSave = false,
		isSubmit = false;
	$('body').on('click','#fnMOPN ul li a',function () {
	    if($(this).attr('trigger') =="temporarStorage") {
	        isSave = true
	        isSubmit = false
	        console.log(isSubmit)
		} else if($(this).attr('trigger') =="fulfilSubmit") {
	    	isSave =false
	    	isSubmit = true
	    	console.log(isSubmit)
		}

    })
	$('body').on('click','#step ul li,.submit',function () {
        if ($(this).attr('branch') == 'submit') {
            isSubmit = true
		}

    })


	$('.submit').click(function () {
		var branch = $(this).attr('branch')
		tabId = branch =='submitNext' ? (parseInt($('.item.icon-s1.active').attr('tabid'))+1) : branch =='submitPrev' ? (parseInt($('.item.icon-s1.active').attr('tabid')) -1) : ''
        if ($(this).attr('branch') == 'submit') {
            isSubmit = true
		}
    })

	_form.validate({
		errorClass : 'error-tip',
		errorElement : 'em',
		errorPlacement : function(error, element) {
			element.after(error);
		},
		onkeyup : false, // 不在按键时进行验证
		ignore : '.cancel',
		submitHandler : function(form) {

			loading.open();
			$.fn.orderName();
			if (fm5 == 0 && tabId == 0) {

				hintPopup('数据未改变，保留原有存储');
				return false;

			}


			if($('#step li').length > 1){

                $('[name="checkStatus"]').val(validateDataReturnGather.rulesAllFalse());
            } else {

                $('[name="checkStatus"]').val(_form.valid() ? '1':'0');
            }
			$('[name="tabId"]').val(tabId);



			// 拼接json
			$('[schemeCode="DELAY"]').each(function(index, el) {

				var $this, $delayListDate, $list, arr;

				$this = $(el);
				$delayListDate = $this.find('.delayListDate');
				$list = $this.find('.listDELAY');
				arr = []

				$list.each(function(index, el) {

					var $el;

					$el = $(el);

					arr.push({
						extendPrincipal : $el.find('.extendPrincipal').val(),
						extendTimeLimit : $el.find('.extendTimeLimit').val(),
						extendTimeUnit : $el.find('.extendTimeUnit').val()
					});

				});

				$delayListDate.val(JSON.stringify(arr));

			});
			$(form).ajaxSubmit({
				type : 'post',
				dataType : 'json',
				success : function(res) {
					loading.close();
					if (res.success) {

						if(isSubmit && res.form.checkStatus.indexOf('0') > -1) {
							var empty = res.form.checkStatus.split('')
							empty.forEach(function (index) {
								if(empty[index] == 0) {
                                    if (!$('#step li').eq(index).find('.remind').length) {
                                        $('#step li').eq(index).append('<em class="remind"><b>请完整该页必填项</b></em>');
                                    }
								}
                            })
                            $('body').animate({'scrollTop': $('#step').offset().top - 80 + 'px'},500)
							return
						}
						console.log(isSubmit)
                        if (res.tabId != '0' && res.form.checkStatus.indexOf('0') < 0 && isSubmit) { // 提交

							loading.open();

							$.ajax({

								url : '/projectMg/form/submit.htm',
								type : 'post',
								dataType : 'json',
								data : {
									formId : res['form']['formId']
								},
								success : function(data) {

									if (data.success) {

										$.ajax({

											url : '/projectMg/meetingMg/summary/checkNoRiskTeamProject.json',
											type : 'post',
											dataType : 'json',
											data : {
												formId : res['form']['formId']
											},
											success : function(dataPopup) {

												loading.close();

												if (dataPopup.success) {

													$('body').Y('Msg', {
														type : 'confirm',
														content : '  请及时对项目' + dataPopup.noRiskTeamProject + '成立风险处置小组',
														icon : 'warn',
														yesText : '前往',
														noText : '取消',
														callback : function(opn) {
															if (opn == 'yes') {

																window.location.href = '/projectMg/riskHandleTeam/list.htm';

															} else {

																hintPopup(data.message, function() {

																	window.location.href = '/projectMg/meetingMg/councilList.htm';

																});

															}
														}
													});

												} else {

													hintPopup(data.message, function() {

														if (data.success) {
															window.location.href = '/projectMg/meetingMg/councilList.htm';
														}

													});

												}

											}

										});

									} else {

										hintPopup(data.message)

									}

								}

							});

						} else { // 暂存
							if (isSave) {
                                hintPopup(res.message, window.location.href);
							} else {
								window.location.href = window.location.pathname+'?formId='+$('[name="formId"]').val()+'&spId='+ $('[name="tabId"]').val()
							}

						}

					} else { // 报错

						hintPopup(res.message);

					}
				}
			});
		}
	});

	// 添加

	$('body').on('click', '.recordAdd', function(event) {

		var $this, $target, $ultimateTarget, Html;

		$this = $(this);
		$target = $this.parents('tr').nextAll('.listDELAY');
		$ultimateTarge = $target.eq($target.length - 1);
		Html = $('#t-listDELAY').html();

		$ultimateTarge.after(Html);

		$this.parents('tbody').find('.listDELAY').each(function(index, el) {

			var $el, $index;

			$el = $(el);
			$index = index;

			$el.find('[name]').each(function(index, el) {

				var $thisEl, name;

				$thisEl = $(el);
				name = $thisEl.attr('name');

				if (name) {

					$thisEl.attr('name', 'orderNameTest[' + $index + '].' + name.replace(/.*\]\./, ''));
					$.fn.addValidataFun($thisEl, rulesAll);

				}
			});

		});

	}).on('click', '.recordDelete', function(event) {
		$(this).parents('tr').remove();
	});
	;

	// 字数提示
	require('zyw/hintFont');
	// 验证方法集
	require('zyw/project/bfcg.itn.addValidataCommon');

	// 验证方法集初始化
	$.fn.initAllOrderValidata(rulesAll, true);

	// 数据是否改变
	var md5 = require('md5'), // md5加密
	validateDataReturnGather = { // 数据是否改变、否必填

		formSerializeMd5 : function(_form) { // 返回表单序列值

			var _formSerialize = _form.serialize();

			return md5(_formSerialize);

		},

		fm5WhetherChange : function() { // 数据是否有改变

			var _newSerializeMd5 = validateDataReturnGather['formSerializeMd5'](_form), fm5 = (_newSerializeMd5 != _initSerializeMd5) ? 1 : 0; // 数据是否有改变

			return fm5;

		},

		rulesAllFalse : function() { // 数据是否完整
			var checkStatus = _form.allWhetherNull(_allWhetherNull['stringObj'], _allWhetherNull['boll']); // 是否填写完整

			return checkStatus

		},
	}, _initSerializeMd5 = validateDataReturnGather['formSerializeMd5'](_form); // 初始页面数据

	// (new (require('zyw/publicOPN'))()).init().doRender();

	// $('#submit').click(function(event) {
    //
	// tabId = 1
	// fm5 = validateDataReturnGather.fm5WhetherChange();
    //
	// $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll);
	// _form.submit();
    //
	// });

	// 页面共用
	require('zyw/project/assistsys.smy.Common');
	// 处置切换
	// $('body').on('change', '.schemeSelect', function() {

	// var _this = $(this),
	// _val = _this.val(),
	// _parents = _this.parents('.m-table');

	// _parents.find('[schemeCode="' + _val +
	// '"]').show().find('input,select').each($.repairName).end()
	// .siblings('[schemeCode]').hide().find('input,select').each($.destroyName);

	// }).find('.schemeSelect').trigger('change');

	$('body').on('change', '.checkboxScheme', function(event) {

		var $this, valueCode, checked;

		$this = $(this);
		valueCode = $this.attr('valueCode');
		checked = $this.is(':checked');

		if (checked) {

			$this.parents('table').find('[schemeCode="' + valueCode + '"]').show().find('input,select,textarea').each($.repairName);

		} else {

			$this.parents('table').find('[schemeCode="' + valueCode + '"]').hide().find('input,select,textarea').each($.destroyName);

		}

		$this.siblings().blur();

	}).find('.checkboxScheme').change();

	// 现场调查人员弹窗
	var scope = "{type:\"system\",value:\"all\"}";
	var selectUsers = {
		selectUserIds : "",
		selectUserNames : ""
	}
	var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
		'title' : '多选用户',
		'width' : 850,
		'height' : 460,
		'scope' : scope,
		'selectUsers' : selectUsers,
		'bpmIsloginUrl' : '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl' : '/JumpTrust/makeLoginUrl.htm'
	});

	// window.frameElement.dialog=singleSelectUserDialog;
	$('#xxxBtn').on('click', function() {
		singleSelectUserDialog.init(function(relObj) {

			for (var i = 0; i < relObj.userIds.length; i++) {
				$('#xxxID').val(relObj.userIds);
				$('#xxx').val(relObj.fullnames);
			}
		});

	});

	var getTypesOfCredit = require('zyw/getTypesOfCredit')
	var _getTypesOfCredit = new getTypesOfCredit();
	_getTypesOfCredit.init({
		chooseAll : true,
		btn : '.businessTypeBtn',
		name : '.businessTypeName',
		code : '.businessTypeCode',
		dual : true
	});

	$("body").on('click', '.fnListSearchClear', function() {
		var inputs = $(this).siblings('input');
		inputs.each(function() {
			$(this).removeAttr('value').prop('value', '');
		})
	}).on('change', '[name=redoBusiType]', function() {
		var busiType = $(this).val(), 
			$pageCredit = $("#pageCredit"),
			$feeText = $("#feeText"),
			handleId = $("[name=handleId]").val();
		if (busiType) {
			$("[name=feeType]").find("option[value=AMOUNT]").attr("disabled",false);
			// 费用名称改变
			if (busiType.startsWith("5")) {
				$feeText.text("承销费")
			} else if (busiType.startsWith("4")) {
				$feeText.text("利率")
				$("[name=feeType]").val("PERCENT").find("option[value=AMOUNT]").attr("disabled",true);
			} else {
				$feeText.text("担保费用")
			}

			// 承销诉保没有授信前提条件
			if (busiType.startsWith("211") || busiType.startsWith("5")) {
				$pageCredit.html("").parent().addClass("fn-hide");
			} else if(!$pageCredit.html()){
				// 加载反担保数据
				util.ajax({
					url : '/projectMg/meetingMg/summary/pageCredit.json',
					data : {
						'handleId' : handleId
					},
					dataType : 'html',
					success : function(res) {
						$pageCredit.html(res).parent().removeClass("fn-hide");
						// 反担保共用
						require('zyw/project/bfcg.itn.counterGuaranteeCommon')(_rulesAll, _form, _allWhetherNull.stringObj, true);
					}
				});
			}
		} else {
			$pageCredit.html("").parent().addClass("fn-hide");
		}
	})
	
	$("[name=redoBusiType]").trigger("change");
	
	var _rulesAll = {};
	var submitValidataCommon = require('zyw/submitValidataCommon'),
		isNotShowSidebar = document.getElementById('fnChangeApplyPost') ? true : false;
	submitValidataCommon.submitValidataCommon({
		notShowSidebar : isNotShowSidebar,
		rulesAll : rulesAll,
		form : _form,
		allWhetherNull : _allWhetherNull,
		uni: true
	});

    // 单位改变验证
	require('zyw/project/assistsys.smy.unitChange');

	// 根据状态初始
	require('zyw/project/assistsys.smy.init')({
		form : _form
	});

	$('.fnAddLineNew').addValidataCommon(rulesAll, true).initAllOrderValidata().fillGroupAddValidata();

	$('.fnAddLine').addValidataCommon(rulesAll, true).assignGroupAddValidataUpUp();
});