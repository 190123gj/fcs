define(function (require, exports, module) {

	//弹窗
	require('Y-window');
	//弹窗提示
	var hintPopup = require('zyw/hintPopup');
    var getList = require('zyw/getList');

	//入库
	var searchForDetails = require('zyw/searchForDetails');
	//项目编号
	if (document.getElementById('chooseBtn')) {
		searchForDetails({
			ajaxListUrl: '/baseDataLoad/loadFileProjects.json',
			btn: '#chooseBtn',
			codeInput: 'projectCode',
			tpl: {
				tbody: ['{{each pageList as item i}}',
					'    <tr class="fn-tac m-table-overflow onbreaks">',
					'        <td class="item onbreak" id="{{item.name}}">{{item.projectCode}}</td>',
					'        <td class="onbreak" title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
					'        <td class="onbreak" title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
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

				$('#customerName').val(_data.customerName);
				$('#projectName').val(_data.projectName);
				$('#projectCode').val(_data.projectCode);
				$('#customerId').val(_data.customerId);
				//$('.projectName').text(_data.projectName);
				//$('.customerName').text(_data.customerName);
				//$('.contractNo').text(_data.contractNo);

			}
		});
	}
    var _getList = new getList();
    //选择历史单据

	$("#chooseHisBtn").click(function () {
        _getList.init({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            width: '90%',
            title: '选择需要复制的历史已归档授信后管理卷',
            ajaxUrl: '/projectMg/file/copyHisFileList.json?fileType=CREDIT_BEFORE_MANAGEMENT&applyType=%E5%85%A5%E5%BA%93%E7%94%B3%E8%AF%B7&formStatus=APPROVAL',
            btn: '#chooseHisBtn',
            tpl: {
                tbody: ['{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow onbreaks">',
                    '        <td class="item onbreak" id="{{item.fileCode}}">{{item.fileCode}}</td>',
                    //  '        <td class="onbreak" title="{{item.oldFileCode}}">{{item.oldFileCode}}</td>',
                    '        <td class="onbreak" title="{{item.projectCode}}">{{item.projectCode}}</td>',
                    '        <td class="onbreak" title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
                    '        <td class="onbreak" id="{{item.rawAddTime}}">{{item.rawAddTime}}</td>',
                    '        <td class="onbreak">' +
                    '<a href="/projectMg/file/inputView.htm?formId={{item.formId}}&type=CREDIT_BEFORE_MANAGEMENT" target="_bank">查看详情</a>&nbsp;&nbsp;<a class="choose"  formId="{{item.formId}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '档案编号：',
                    '<input class="ui-text fn-w160" type="text" name="fileCode">',
                    //  '&nbsp;&nbsp;',
                    //  '原档案编号：',
                    //  '<input class="ui-text fn-w160" type="text" name="oldFileCode">',
                    // '</br>',
                    '项目编号：',
                    '<input class="ui-text fn-w160" type="text" name="projectCode">',
                    //   '&nbsp;&nbsp;',
                    //   '客&nbsp;户&nbsp;名&nbsp;称：',
                    //   '<input class="ui-text fn-w160" type="text" name="customerName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: ['<th width="100">档案编号</th>',
                    // '<th width="100">原档案编号</th>',
                    '<th width="120">项目编号</th>',
                    '<th width="120">客户名称</th>',
                    '<th width="50">操作时间</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            },
            callback: function ($a) {
                var copyFormId=$a.attr('formId');
                var type=$('.apply-step .item.active').attr('codetype');
                var doing=$('[name="doing"]').val();
                var formId=$('[name="formId"]').val();
                if(formId==undefined){
                    formId=0;
                }
                var projectCode=$('#projectCode').val();
                window.location.href = '/projectMg/file/inputApply.htm?type='+type+'&doing='+doing+'&formId='+formId+'&projectCode='+projectCode+'&copyFormId='+copyFormId;
            }
        });
    })

    //历史档案追加
    //111
	$("#addToHis").click(function () {
        _getList.init({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            width: '90%',
            title: '选择需要追加的历史档案',
            ajaxUrl: '/projectMg/file/copyHisFileList.json?fileType='+$("#currType").val()+'&applyType=%E5%85%A5%E5%BA%93%E7%94%B3%E8%AF%B7&formStatus=APPROVAL&projectCode='+$("#projectCode").val(),
            btn: '#addToHis',
            tpl: {
                tbody: ['{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow onbreaks">',
                    '        <td class="item onbreak" id="{{item.fileCode}}">{{item.fileCode}}</td>',
                    //  '        <td class="onbreak" title="{{item.oldFileCode}}">{{item.oldFileCode}}</td>',
                    '        <td class="onbreak" title="{{item.projectCode}}">{{item.projectCode}}</td>',
                    '        <td class="onbreak" title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
                    '        <td class="onbreak" id="{{item.rawAddTime}}">{{item.rawAddTime}}</td>',
                    '        <td class="onbreak">' +
                    '<a href="/projectMg/file/inputView.htm?formId={{item.formId}}&type=CREDIT_BEFORE_MANAGEMENT"'+$("#currType").val()+' target="_bank">查看详情</a>&nbsp;&nbsp;<a class="choose"  formId="{{item.formId}}" fileCode="{{item.fileCode}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '档案编号：',
                    '<input class="ui-text fn-w160" type="text" name="fileCode">',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: ['<th width="100">档案编号</th>',
                    // '<th width="100">原档案编号</th>',
                    '<th width="120">项目编号</th>',
                    '<th width="120">客户名称</th>',
                    '<th width="50">操作时间</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            },
            callback: function ($a) {
                var fileCode=$a.attr('fileCode');
                $("#fileCode").val(fileCode);
                $("#showFileCode").html(fileCode);
            }
        });
    })


	$('#screenSubmit').click(function (event) { //入库

		if ($('#projectCode').val() == '' || $('#customerName').val() == '') {

			hintPopup('请选择项目编号');
			return false;

		}

		$('#screenForm').submit();

	});


	//验证开始---------------------------------------------------------华丽的分割线-------------------------------------------------------------------------

	function eduTainMent() {

		$('.filePage').each(function (index, el) {

			var $el;

			$el = $(el);

			if ($el.val() == '') $el.val(0);

		});

	}

	//必填集合
	require('zyw/requiredRules');
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'fileName,fileType,archiveFileName,filePath,filePage,originalCopy,filePage,attachType',
			boll: false
		},
		requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function (rules, name) {
			rules[name] = {
				required: true,
				messages: {
					required: '必填'
				}
			};
		}),
		rulesAllBefore = { //所有格式验证的基
			fileName: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			fileType: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			archiveFileName: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			filePath: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			originalCopy: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			attachType: {
				required: true,
				messages: {
					required: '必填'
				}
			},
			filePage: {
				required: true,
				digits: true,
				min: 1,
				// max: 1000,
				messages: {
					digits: '请输入大于0的整数',
					min: '请输入大于0的整数',
					// max: '请输入1000以内的数字',
					required: '必填'
				}
			},
			no: {
				//required: true,
				digits: true,
				// onRepetition: {
				// 	obj: '.repetition'
				// },
				// remote: function (This) {

				// 	return {
				// 		url: '/baseDataLoad/validataNo.json', //远程验证的接口url
				// 		data: {
				// 			no: $(This).val(),
				// 			projectCode: $('[name="projectCode"]').val(),
				// 			type: $('#step .active').attr('codeType'),
				// 			id: $(This).parents('tr').find('.inputListId').val()
				// 		},
				// 		type: "post", //数据发送方式
				// 		dataType: "json"
				// 	}

				// },
				maxlength: 3,
				messages: {
					//required: '必填',
					digits: '请输入3位有效数字',
					maxlength: '请输入3位有效数字'
					// onRepetition: '件号重复',
					// remote: '与已存在的件号重复'
				}
			}
		},
		maxlength50Rules = _form.requiredRulesSharp('archiveFileName,filePage,firstLoanTime,filingTime,handOverDept,handOverMan,handOverTime,principalMan,viceManager,receiveDept,receiveMan,receiveTime,inputRemark', _allWhetherNull['boll'], {}, function (rules, name) {
			rules[name] = {
				maxlength: 50,
				messages: {
					maxlength: '已超出50字'
				}
			};
		}),
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
		submitValidataCommonUp = require('zyw/submitValidataCommonUp'),
		mergeTable = require('zyw/mergeTable');


	//重复验证
	jQuery.validator.addMethod('onRepetition', function (value, element, param) {

		var $el, $obj, val, jut;

		$el = $(element);
		$obj = $(param.obj);
		val = $el.val();
		jut = true;

		$obj.each(function (index, el) {

			var elVal = $(el).val();

			if ($(el)[0] === $el[0]) return true;

			if (elVal == val) {

				jut = false;

				return false;

			}

		});

		return this.optional(element) || jut;

	}, $.validator.format("请输入{0}"));

	var $repetition = $('.repetition');

	$repetition.change(function () {

		$repetition.blur();

	})

	//三选一失焦验证规则
	//特殊判断的类型
	//RISK_COMMON  常规风险卷  RISK_LAWSUIT  风险诉讼卷
	var currFormType = $('[name*=currType]').val();
	var isXinhui = $('#isXinhui').val();
	var isTochecked = ((currFormType != 'RISK_COMMON' && currFormType != 'RISK_LAWSUIT') && isXinhui != 'true') ? false : true;
	//console.log(((currFormType != 'RISK_COMMON' && currFormType != 'RISK_LAWSUIT')||isXinhui!='true'),isXinhui!='true',isTochecked);
	var isSpecailRequried = !isTochecked; //用于判断页面是否做出改变
	if (isTochecked) {
		$('.oneInThree').each(function () {
			if (!!$(this).val().replace(/\s/g, '')) isSpecailRequried = true;
		})
	}
	$('body').on('blur', '.oneInThree', function () {

		var _this = $(this);

		if (!isTochecked) return;
		if (!!_this.attr('isRequired')) return;

		var _allItem = _this.parents('tr').find('[name].oneInThree');
		var _thisVal = _this.val();
		var _others = _allItem.not(_this);

		if (!_thisVal.replace(/\s/g, '')) { //为空
			isSpecailRequried = false;
			_allItem.each(function (i, e) { //当前行选项不验证
				$(this).addClass('cancel').removeAttr('isRequired');
			});
			// _others.each(function (i, e) {
			//     $(this).trigger('blur')
			// })
			_this.parents('table').find('.oneInThree').each(function (i, e) { //遍历所有内容是否全为空，全为空不能提交
				if (!!$(this).val().replace(/\s/g, '')) isSpecailRequried = true;
			});

			return;
		};

		isSpecailRequried = true;
		_others.each(function (i, e) {
			$(this).removeClass('cancel').attr('isRequired', true).trigger('blur');
		})

	}).find('.oneInThree').blur();

	function maskingChange(_this) {

		$('body').find('.headmanCover').each(function (index, el) { //遮罩高度

			var _this = $(el),
				_parent = parseFloat(_this.parent().css('height'));

			_this.css({

                //height: _parent + 22.000182
				height: '100%'

			});

		});

	}

	new mergeTable({

		//jq对象、jq selector
		obj: '.demandMerge',

		//默认为false。true为td下有input或者select
		objVal: '.demandMergeVal',

		//每次遍历合并会调用的callback
		mergeCallback: function (Dom) { //Dom为每次遍历的合并对象和被合并对象
			// var Obj, text, index;

			// Obj = Dom['mergeObj'];
			// text = Obj.text();
			// index = Obj.index();

			// if (!index) {

			//     Obj.html(text + '<div class="headmanCover"><i class = "xlsTop fn-p-abs"></i><i class = "xlsLeft fn-p-abs"></i><i class = "xlsRight fn-p-abs"></i><i class = "xlsBottom fn-p-abs"></i></div>');

			// }

		},
		callback: function () { //表格合并完毕后

			maskingChange();

			submitValidataCommonUp.submitValidataCommonUp({

				form: _form, //form
				allWhetherNull: _allWhetherNull, //必填集合与是否反向判断
				rulesAll: _rulesAll, //验证全集
				extendFun: function (extendJson) {

					var _checkStatus = $('[name="checkStatus"]').val().split(''),
						sum = '';

					$('#t-test').find('input').each(function (index, el) {

						var _this = $(el),
							_name = $.fn.nameOperationFun(_this);

						if (_name != 'id') sum += _this.val();

					});

					(sum == '') ? _checkStatus[extendJson['index']] = 2: _checkStatus[extendJson['index']] = extendJson['presentCheckStatus'];

					return _checkStatus.join('').replace(/0/g, '3');

				},
				allEvent: {

					// replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
					// replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
					contentBtn: [{
						clickObj: '#step ul li',
						eventFun: function (jsonObj) {

							if ($(jsonObj['self']).hasClass('active')) return false;

							var _checkStatus = jsonObj.objList['extendFun']({

								form: jsonObj.objList['form'],
								index: $('#step ul li.active').attr('index'),
								presentCheckStatus: jsonObj.setInitVal['checkStatus']

							}); //拼接checkStatus

							$.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //否必填

							jsonObj.portInitVal['submitHandlerContent'] = {

								validateData: {

									type: $(jsonObj['self']).attr('codeType'),
									submitStatus: 'N'

								},

								checkStatus: _checkStatus,
								fm5: jsonObj.setInitVal['fm5']

							}; //向validate文件里的submitHandler暴露数据接口

							jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

							jsonObj.objList['form'].submit(); //提交

						}
					}, {
						clickObj: '#TS',
						eventFun: function (jsonObj) {

							var _checkStatus = jsonObj.objList['extendFun']({

								form: jsonObj.objList['form'],
								index: $('#step ul li.active').attr('index'),
								presentCheckStatus: jsonObj.setInitVal['checkStatus']

							}); //拼接checkStatus


							$.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //否必填

							jsonObj.portInitVal['submitHandlerContent'] = {

								validateData: {

									type: $('.apply-step .item.active').attr('codeType'),
									submitStatus: 'N'

								},

								checkStatus: _checkStatus,
								fm5: jsonObj.setInitVal['fm5']

							}; //向validate文件里的submitHandler暴露数据接口

							jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

							jsonObj.objList['form'].submit(); //提交

						}
					}, {
						clickObj: '#submit',
						eventFun: function (jsonObj) {

							if (!$('#t-test').find('tr').length || !isSpecailRequried) {

								hintPopup('无内容添加！');
								return false;

							} //无内容不能提交

							if ($('#projectCode').val() == '' || $('#customerName').val() == '') {

								hintPopup('请选择正确的项目编号');
								return false;

							}

							var _checkStatus = jsonObj.objList['extendFun']({

								form: jsonObj.objList['form'],
								index: $('#step ul li.active').attr('index'),
								presentCheckStatus: jsonObj.setInitVal['checkStatus']

							}); //拼接checkStatus

							$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

							jsonObj.portInitVal['submitHandlerContent'] = {

								validateData: {

									type: $('.apply-step .item.active').attr('codeType'),
									submitStatus: 'Y',
									doing: 'has'

								},

								checkStatus: _checkStatus,
								fm5: jsonObj.setInitVal['fm5']

							}; //向validate文件里的submitHandler暴露数据接口

							jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

							jsonObj.objList['form'].submit(); //提交

						}
					}], //内容区提交组
					broadsideBtn: [{
							name: '保存并提交',
							alias: 'temporarStorage',
							events: function (jsonObj) {

								// if (!$('#t-test').find('tr').length) return false; //无内容不能提交
								if (!$('#t-test').find('tr').length || !isSpecailRequried) {

									hintPopup('无内容添加！');
									return false;

								} //无内容不能提交

								if ($('#projectCode').val() == '' || $('#customerName').val() == '') {

									hintPopup('请选择正确的项目编号');
									return false;

								}

								var _checkStatus = jsonObj.objList['extendFun']({

									form: jsonObj.objList['form'],
									index: $('#step ul li.active').attr('index'),
									presentCheckStatus: jsonObj.setInitVal['checkStatus']

								}); //拼接checkStatus

								$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

								jsonObj.portInitVal['submitHandlerContent'] = {

									validateData: {

										type: $('.apply-step .item.active').attr('codeType'),
										submitStatus: 'Y',
										doing: 'has'

									},
									checkStatus: _checkStatus,
									fm5: jsonObj.setInitVal['fm5']

								}; //向validate文件里的submitHandler暴露数据接口

								jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

								jsonObj.objList['form'].submit(); //提交

							}
						}, {
							name: '暂存',
							alias: 'temporarStorage2',
							events: function (jsonObj) {

								var _checkStatus = jsonObj.objList['extendFun']({

									form: jsonObj.objList['form'],
									index: $('#step ul li.active').attr('index'),
									presentCheckStatus: jsonObj.setInitVal['checkStatus']

								}); //拼接checkStatus

								$.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //否必填

								jsonObj.portInitVal['submitHandlerContent'] = {

									validateData: {

										type: $('.apply-step .item.active').attr('codeType'),
										submitStatus: 'N'

									},
									checkStatus: _checkStatus,
									fm5: jsonObj.setInitVal['fm5']

								}; //向validate文件里的submitHandler暴露数据接口

								jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

								jsonObj.objList['form'].submit(); //提交

							}
						}] //侧边栏提交组

				},

				ValidataInit: {

					successBeforeFun: function (res) { //请求前操作

						if (!res) return false;

						var util = require('util'),
							loading = new util.loading();

						loading.open();
						$.fn.orderName();
						$('[name="checkStatus"]').val(res['checkStatus']);

						if (res.validateData['submitStatus'] == 'Y') { //保存

							if (/3/.test(res['checkStatus'])) { //某页未填完整

								var navSubmited = require('zyw/navSubmited');

								navSubmited.navSubmited3(res['checkStatus']);
								$('[name="doing"]').val('has');
								$('body,html').animate({

									scrollTop: 0

								});

								$('.m-modal-box').remove();

								return false;

							} else {

								eduTainMent();
								return true;

							}

						} else { //切换and暂存

							if (!res['fm5']) {

								if (res.validateData['type'] == $('.apply-step .item.active').attr('codetype')) {

									var hintPopup = require('zyw/hintPopup'); //弹窗提示

									$('.m-modal-box').remove();
									hintPopup('数据未改变，保持原有存储');

								} else {

									window.location.href = '/projectMg/file/inputApply.htm?formId=' + $('[name="formId"]').val() + '&type=' + res.validateData['type'] + '&doing=' + $('[name="doing"]').val() + '&projectCode=' + $('[name="projectCode"]').val();

								}

								return false;

							} else {

								eduTainMent();
								return true;

							}

						}


					},

					successFun: function (res) { //响应成功操作

						//弹窗提示
						var hintPopup = require('zyw/hintPopup');

						if (res['success']) {

							if (res['submitStatus'] == 'Y') {

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

												window.location.href = '/projectMg/file/list.htm';

											}

										});

										$('.m-modal-box').remove();

									}

								})

							} else if (res['submitStatus'] == 'N') {

								window.location.href = '/projectMg/file/inputApply.htm?formId=' + res['formId'] + '&type=' + res['type'] + '&doing=' + res['doing'] + '&projectCode=' + res['projectCode'];

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
					//         element.after(error)
					//     }

					// }

				},

				callback: function (objList) { //加载时回调
					//验证方法集初始化
					$('.fnAddLine').click(function () {
						setTimeout(function () {
                            loopTR(function () {
                                //console.log($(this))
                            })
                        },300)
                    }).addValidataCommon(objList['rulesAll'], true)
						.initAllOrderValidata().assignGroupAddValidataUpUp();


					
					//提交过

					// if ($('[name="doing"]').val() != 'null' || $('[name="doing"]').val() != '') {
					//
					// 	var navSubmited = require('zyw/navSubmited');
					//
					// 	navSubmited.navSubmited($('[name="checkStatus"]').val());
					// 	objList['form'].submit();
					//
					// }

					//双击填写
					$('body').on('dblclick', '.addDemand .headmanCover', function (event) {

						var $this, $siblings;

						$this = $(this);
						$siblings = $this.siblings('input[type="text"]');

						if (!$siblings.length) return false;

						$this.hide();
						$siblings.focus();

					}).click(function (event) {

						var $target;

						$target = $(event.target);

						if (!$target.closest('.addDemand').length) $('.addDemand.active .headmanCover').show();

					}).on('click', '.addDemand .headmanCover', function (event) {

						var $this, $siblings;

						$this = $(this);
						$siblings = $this.parents('tbody').find('.headmanCover');

						$siblings.show();

					}).on('change', '.Alter', function (event) {

						var $this, $tr, $index, val;

						$this = $(this);
						$index = $this.parent('td').index();
						$tr = $this.parents('tr');
						val = $this.val();

						$tr.nextAll('tr').each(function (index, el) {

							var $el, $target, $td;

							$el = $(el);
							$td = $el.find('td').eq($index);
							$target = $td.find('.Alter');

							if ($td.is(':visible')) return false;

							$target.val(val);

						});


					})
					.on('change', '.Alter2', function (event) {

						var $this, $tr, $index, val;

						$this = $(this);
						$index = $this.parent('td').index();
						$tr = $this.parents('tr');
						val = $this.val();

						$tr.nextAll('tr').each(function (index, el) {

							var $el, $target, $td;

							$el = $(el);
							$td = $el.find('td').eq($index);
							$target = $td.find('.Alter2');

							if ($td.is(':visible')) return false;

							$target.val(val);

						});


					});


				}

			})
		},
		transform: {

			open: true, //开启增删模式
			active: '.addDemand', //允许增加项对象（支持任何JQselect）
			objVal: '.demandMergeVal',
			on: '.active', //选中样式（支持任何JQselect）
			addBtn: '.fnAddLineSubitem', //选中增加项后单击增加的按钮（单个）
			addHandleEachExcept: function (callObj) { //选中对象active、新增tr对象newTr

				if (!callObj.active.length) {

					hintPopup('请选择要增加的类别项或档案名称项');
					return false;

				}

				var $activeTr, $activeTd;

				$activeTr = callObj.active.parent();
				$activeTd = $activeTr.find('td');

				$.fn.orderName();

				callObj.newTr.find('td').each(function (index, el) {

					var $el, $index;

					$el = $(el);
					$index = $el.index();

					if ($el.is(':hidden')) {

						$el.find('input').each(function (index, el) {

							var $elInput;

							$elInput = $(el);

							$elInput.val($activeTd.eq($index).find('input').eq(index).val().trim());

						}).eq(0).after($activeTd.eq($index).text().trim());

					}

					if ($el.find('input').length) $.fn.addValidataFun($el.find('input'), _rulesAll);

				});

				//if ($activeTr.attr('parentsClass')) callObj.newTr.find('.removeDemand').addClass('fnDelLine');

			},
			// addHandleEachTd: function(callObj) { //添加时参与循环改变rowspan的对象

			//     console.log(2);

			// },
			addCallback: function () { //添加完毕后回调

				maskingChange();
                loopTR(function () {
                    //console.log('在添加回调中执行')
                })

			},
			addContent: $('#addList').html(), //Dom或拼接字符串

			removeBtn: '.removeDemand', //对应tr内td或td内元素
			removeBeforeHandle: function (callObj) { //当前删除按钮对象,删除前操作

				var obj = callObj.element.parents('tr').find('td').eq(1),
					jus = parseInt(obj.attr('rowspan'));

				//if ((jus == 1 || (obj.is(':visible') && !jus)) && !callObj.element.hasClass('fnDelLine')) return false;

			},
			// removeHandleEachExcept: function(callObj) { //重新显示的TD

			//     console.log(3);

			// },
			// removeHandleEachTd: function(callObj) { //移除时参与循环改变rowspan的对象

			//     console.log(4);

			// },
			removeCallback: function () { //删除完毕后回调

				maskingChange();
                loopTR(function () {
					//console.log('在删除回调中执行')
                })
			},

		}

	});

	$('#cleanTbody').on('click', '', function () {
		$("#t-test").html("");
	})


    /**
     * author zhuri 2017-5-22
     * 上移tr功能
     * @param callback 回调
     */

	function loopTR(callback) {

		var obj = $('#t-test tr');
        var index = 0;
        var long = 0;
		obj.each(function () {
			var $this = $(this);
            $this.removeAttr('index');
			if($this.find('td').eq(0).css('display') != 'none'){
				//进入一大类
                var this_nextAll = $this.nextAll();
                index = 0;
                long = 0;
                long = $this.find('td').eq(0).attr('rowspan') > 0 ? $this.find('td').eq(0).attr('rowspan') : 0;
                $this.attr('index',0).find('.moveUp').hide();
			}
            if($this.find('td').eq(0).css('display') == 'none'){
                $this.attr('index',++index);
                $this.find('.moveUp').show();
			}
            $this.attr('long',long);
        });

		$("table input[type='radio']").each(function () {
            var $this = $(this);
            if($this.attr('checked') !== undefined  || $this.attr('checked') == 'checked'){
                $this.attr('checked','checked')
                $this.attr('zr','checked')
                $this.prop('checked',true)
            }
            if($this.attr('zr')=='checked') {
                $this.attr('checked','checked');
                $this.prop('checked','checked')
            }

        });

		callback && callback();


    };

    loopTR();


    $('body').on('click','#t-test input[type="radio"]', function () {

    	$(this).attr('zr','checked');
    	$(this).siblings('input[type="radio"]').each(function () {
			$(this).removeAttr('zr')
        })

    })

    $('body').on('click','.moveUp',function () {

		var $this = $(this);
		var this_tr = $this.parents('tr');
		var long = this_tr.attr('long');
		var index = this_tr.attr('index');

		if(this_tr.attr('index') != 0) {
            var tr_p = this_tr.prev();
            var allInput = this_tr.find('input[type="text"],input[type="radio"]');
            var allPInput= tr_p.find('input[type="text"],input[type="radio"]');
            //收集上下两个tr的value，并对应替换
			var obj =[];
			var obj2=[];
            allPInput.each(function (index) {
                var $this = $(this);
                if(index){
                    if($this.prop('type') == 'radio'){
                        if($this.attr('checked')=='checked'){
                            $this.removeAttr('checked');
                            obj2.push(true);
                        }else{
                            obj2.push(false);
                        }
                    }else{
                        obj2.push($this.val());
                    }
				}
            });
            allInput.each(function (index) {
                var $this = $(this);
                if(index) {
                    if ($this.prop('type') == 'radio') {
                        if ($this.attr('checked') == 'checked') {
                            $this.removeAttr('checked');
                            obj.push(true);
                        } else {
                            obj.push(false);
                        }
                    } else {
                        obj.push($this.val());
                    }
                }
            });
			allInput.each(function (index) {
				var $this = $(this);
                if(index) {
                    if($this.prop('type') == 'radio'){
                        if(obj2[index-1]){
                            $this.attr('checked','checked');
                            $this.prop('checked','checked');
                        }
                    }else{
                        $this.val(obj2[index-1]);
                        $this.prop('value',obj2[index-1])
                    }
                }

            })
            allPInput.each(function (index) {
				var $this = $(this);
                if(index) {
                    if ($this.prop('type') == 'radio') {
                        if (obj[index-1]) {
                            $this.attr('checked', 'checked');
                            $this.prop('checked', 'checked');
                        }
                    } else {
                        $this.val(obj[index-1]);
                        $this.attr('value', obj[index-1])
                    }
                }
            });
		}

    })

})