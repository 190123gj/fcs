define(function(require, exports, module) {

	var popupWindow = require('zyw/popupWindow');
	var hintPopup = require('zyw/hintPopup');

	module.exports = function(objJson) {

		// 切换
		var $choice = $('.choice'), $choiceList = $('.choiceList'), $relevanceBtn = $('.relevanceBtn'), $choiceSelect = $('.choiceSelect');

		$('body').on('change', '.choiceSelect', function(event) {

			var $this, $index;

			$this = $(this);
			$index = $this.find(':selected').index();
			$choice.find('dd').eq($index).show().find('[name]').removeClass('cancel').attr('disabled', false).end().siblings().hide().find('[name]').addClass('cancel').attr('disabled', true); // 显示块
			$choiceList.find('dl').eq($index).show().find('[name]').removeClass('cancel').attr('disabled', false).end().siblings().hide().find('[name]').addClass('cancel').attr('disabled', true);

			$index ? $relevanceBtn.hide() : $relevanceBtn.show();

		}).find('.choiceSelect').trigger('change');

		$choiceList.find('dl').eq($choiceSelect.find('option').not(':selected').index()).find('input,select').val('');
		$choice.find('dd').eq($choiceSelect.find('option').not(':selected').index()).find('input,select').val('');
		// 关联数据项目
		// $('body').on('change', '#projectName,#relevanceData', function(event)
		// {

		// var $Project, $Data;

		// $Project = $('#projectName');
		// $Data = $('#relevanceData');

		// if (!$Project.val() || !$Data().val()) return false;

		// $.ajax({
		// url: '/path/to/file',
		// type: 'POST',
		// dataType: 'json',
		// data: {
		// param1: 'value1'
		// },
		// })
		// .done(function() {
		// console.log("success");
		// })
		// .fail(function() {
		// console.log("error");
		// })
		// .always(function() {
		// console.log("complete");
		// });

		// });

		// 选人
		require('zyw/chooseLoanPurpose');

		// 现场调查人员弹窗
		var scope = "{type:\"system\",value:\"all\"}";
		var selectUsers = {
			selectUserIds : $('#handleNameID').val(),
			selectUserNames : $('#handleName').val(),
			selectUserAccs : $('#handleAcc').val()
		}
		var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选
		var singleSelectUserDialog1 = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
			'title' : '单选选用户',
			'width' : 850,
			'height' : 460,
			'scope' : scope,
			'selectUsers' : selectUsers,
			'bpmIsloginUrl' : '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
			'makeLoginUrl' : '/JumpTrust/makeLoginUrl.htm'
		});

		$("#handleNameID").change(function() {
			var userId = $(this).val();
			if (userId) {
				$.ajax({
					url : '/baseDataLoad/userDetail.json',
					type : 'POST',
					dataType : 'json',
					data : {
						userId : userId
					},
				}).done(function(res) {
					// console.log(res);
					if (res.success) {
						var orgList, string, i, hasMatch = false, selDeptId = $("input[name=applyDeptId]").val();
						orgList = res.data.orgList;
						if (orgList && orgList.length > 0) {
							string = '';
							for (i in orgList) {
								var isSel = false;
								if ((selDeptId && selDeptId == orgList[i].id)) {
									isSel = true;
									hasMatch = true;
								} else if (!hasMatch && orgList[i].primary) {
									isSel = true;
								}
								string += '<option ' + (isSel ? 'selected' : '') + ' value="' + orgList[i].id + '" code="' + orgList[i].code + '">' + orgList[i].name + '</option>';
							}
							$('.applyDept').html(string).change();
						}
					} else {
						hintPopup(res.message);
					}
				}).fail(function() {
					hintPopup('error');
				})
			}
		}).change();
		// window.frameElement.dialog=singleSelectUserDialog;
		$('#handleNameBtn').on('click', function() {

			selectUsers.selectUserIds = $('#handleNameID').val();
			selectUsers.selectUserNames = $('#handleName').val();
			selectUsers.selectUserAccs = $('#handleAcc').val()

			singleSelectUserDialog1.init(function(relObj) {

				for (var i = 0; i < relObj.userIds.length; i++) {
					$('#handleNameID').val(relObj.userIds).blur();
					$('#handleName').val(relObj.fullnames).blur();
					$('#handleAcc').val(relObj.accounts).blur();
				}

				$("#handleNameID").change();

			});
		});

		$('.applyDept').change(function() {
			var $selOption = $(this).find(":selected");
			if ($selOption && $selOption.size() > 0) {
				$("input[name=applyDeptId]").val($selOption.val());
				$("input[name=applyDeptCode]").val($selOption.attr('code'));
				$("input[name=applyDeptName]").val($selOption.text());
			} else {
				$("input[name=applyDeptId]").val('');
				$("input[name=applyDeptCode]").val('');
				$("input[name=applyDeptName]").val('');
			}
		});

		// /baseDataLoad/receiptForm.json 待处理收款单据
		// /baseDataLoad/paymentForm.json 待处理付款单据
		// /baseDataLoad/bankMessage.json 银行账户
		// /baseDataLoad/accountTitle.json 会计科目
		// ---选择部门 start
		// var $fnOrgName = $('#fnOrgName'),
		// $fnOrgId = $('#fnOrgId');

		var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选
		// 初始化弹出层
		var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true', {
			'title' : '部门组织',
			'width' : 850,
			'height' : 460,
			'scope' : '{type:\"system\",value:\"all\"}',
			'arrys' : [], // [{id:'id',name='name'}];
			'bpmIsloginUrl' : '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
			'makeLoginUrl' : '/JumpTrust/makeLoginUrl.htm'
		});
		// 添加选择后的回调，以及显示弹出层
		$('body').on('click', '.creditorBtn', function() {

			// 这里也可以更新已选择机构
			var _arr = [], _this = $(this), $fnOrgName = _this.siblings('.fnOrgName'), $fnOrgId = _this.siblings('.fnOrgId'), _nameArr = $fnOrgName.val().split(','), _idArr = $fnOrgId.val().split(',');

			for (var i = 0; i < _nameArr.length; i++) {
				if (_nameArr[i]) {
					_arr.push({
						id : _idArr[i],
						name : _nameArr[i]
					});
				}
			}

			singleSelectUserDialog.obj.arrys = _arr;

			singleSelectUserDialog.init(function(relObj) {

				$fnOrgId.val(relObj.orgId);
				$fnOrgName.val(relObj.orgName);

			});

		});
		// ---选择部门 end

		// var searchForDetails = require('zyw/searchForDetails');
		// //关联项目
		// if (document.getElementById('chooseBtn')) {
		// searchForDetails({
		// ajaxListUrl: '/baseDataLoad/queryProjects.json',
		// // ajaxListUrlFun: function() {
		// // return 'busiManagerId=' + document.getElementById('xxxID').value
		// // },
		// btn: '#chooseBtn',
		// codeInput: 'projectCode',
		// tpl: {
		// tbody: ['{{each pageList as item i}}',
		// ' <tr class="fn-tac m-table-overflow onbreaks">',
		// ' <td class="item onbreak"
		// id="{{item.name}}">{{item.projectCode}}</td>',
		// ' <td class="onbreak"
		// title="{{item.customerName}}">{{(item.customerName &&
		// item.customerName.length >
		// 6)?item.customerName.substr(0,6)+"..":item.customerName}}</td>',
		// ' <td class="onbreak"
		// title="{{item.projectName}}">{{(item.projectName &&
		// item.projectName.length >
		// 6)?item.projectName.substr(0,6)+"..":item.projectName}}</td>',
		// ' <td class="onbreak">{{item.busiTypeName}}</td>',
		// ' <td class="onbreak">{{item.amount}}</td>',
		// ' <td class="onbreak"><a class="choose"
		// projectCode="{{item.projectCode}}"
		// href="javascript:void(0);">选择</a></td>',
		// ' </tr>',
		// '{{/each}}'
		// ].join(''),
		// form: ['项目名称：',
		// '<input class="ui-text fn-w160" type="text" name="projectName">',
		// '&nbsp;&nbsp;',
		// '客户名称：',
		// '<input class="ui-text fn-w160" type="text" name="customerName">',
		// '&nbsp;&nbsp;',
		// '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit"
		// value="筛选">'
		// ].join(''),
		// thead: ['<th width="100">项目编号</th>',
		// '<th width="120">客户名称</th>',
		// '<th width="120">项目名称</th>',
		// '<th width="100">授信类型</th>',
		// '<th width="100">授信金额(万元)</th>',
		// '<th width="50">操作</th>'
		// ].join(''),
		// item: 6
		// },
		// ajaxDetailsUrl: '/baseDataLoad/loadProjectData.json',
		// fills: [{
		// type: 'html',
		// key: 'customerName',
		// select: '#customerName'
		// }, {
		// type: 'html',
		// key: 'projectName',
		// select: '#projectName'
		// }, {
		// type: 'html',
		// key: 'contractNumber',
		// select: '#contractNumber'
		// }, {
		// type: 'html',
		// key: 'creditType',
		// select: '#creditType'
		// }, {
		// type: 'html',
		// key: 'guarantyPeriodStart',
		// select: '#guarantyPeriodStart'
		// }, {
		// type: 'html',
		// key: 'guarantyPeriodEnd',
		// select: '#guarantyPeriodEnd'
		// }, {
		// type: 'html',
		// key: 'agencies',
		// select: '#agencies'
		// }, {
		// type: 'html',
		// key: 'amount',
		// select: '#amount'
		// }],
		// callback: function(_data) {

		// $('#customerName').val(_data.customerName);
		// $('#projectName').val(_data.projectName);
		// $('#customerName').text(_data.customerName);

		// }
		// });
		// }

		// 关联单据

		$('body').on('click', '.relevanceBtn', function(event) {

			var isSimple = $("#isSimple");
			if (isSimple.length > 0 && isSimple.val() == "IS") {
				isSimple = "IS";
			} else {
				isSimple = "NO";
			}
			fundDitch = new popupWindow({ // 关联单据

				YwindowObj : {
					content : 'relevanceScript', // 弹窗对象，支持拼接dom、template、template.compile
					closeEle : '.close', // find关闭弹窗对象
					dragEle : '.newPopup dl dt' // find拖拽对象
				},
				ajaxObj : {
					url : objJson.formSource,
					type : 'post',
					dataType : 'json',
					data : {
						// isChargeNotification: "IS",
						// projectCode: _projectCode,
						pageSize : 6,
						pageNumber : 1,
						isSimple : isSimple
					}
				},
				formAll : { // 搜索
					submitBtn : '#PopupSubmit', // find搜索按钮
					formObj : '#PopupFrom', // find from
					callback : function($Y) {

					}
				},
				pageObj : { // 翻页
					clickObj : '.pageBox a.btn', // find翻页对象
					attrObj : 'page', // 翻页对象获取值得属性
					jsonName : 'pageNumber', // 请求翻页页数的dataName
					callback : function($Y) {

						// console.log($Y)

					}
				},

				callback : function($Y) {

					$Y.wnd.on('click', 'a.choose', function(event) {

						var _this = $(this);

						window.location.href = objJson.htmlSource + '?sourceId=' + _this.parents('tr').attr('sourceId');

					});

				},

				console : false
			// 打印返回数据

			});

		});
		// 收款账户
		$('body').on('click', '.paymentBtn', function(event) {

			var $this = $(this);

			fundDitch = new popupWindow({

				YwindowObj : {
					content : 'paymentScript', // 弹窗对象，支持拼接dom、template、template.compile
					closeEle : '.close', // find关闭弹窗对象
					dragEle : '.newPopup dl dt' // find拖拽对象
				},

				ajaxObj : {
					url : '/baseDataLoad/bankMessage.json',
					type : 'post',
					dataType : 'json',
					data : {
						// isChargeNotification: "IS",
						// projectCode: _projectCode,
						pageSize : 6,
						pageNumber : 1
					}
				},
				formAll : { // 搜索
					submitBtn : '#PopupSubmit', // find搜索按钮
					formObj : '#PopupFrom', // find from
					callback : function($Y) {

					}
				},
				pageObj : { // 翻页
					clickObj : '.pageBox a.btn', // find翻页对象
					attrObj : 'page', // 翻页对象获取值得属性
					jsonName : 'pageNumber', // 请求翻页页数的dataName
					callback : function($Y) {

						// console.log($Y)

					}
				},

				callback : function($Y) {

					$Y.wnd.on('click', 'a.choose', function(event) {
						var _this = $(this), _account = _this.parents('tr').attr("account"), _amountStr = _this.parents('tr').find('td').eq(5).text().replace(/\,/g, '');
						// console.log(_this.parents('tr').html())
						$this.siblings('.fnBankAccount').val(_account).blur();

						if ($this.siblings('.feeTypePrev ').length) {

							$this.parents('tr').find('[amountStr]').attr('amountStr', _amountStr).blur();
							$this.parents('tr').attr('paymentAccount', _account);

						}

						$Y.close();

					});

				},

				console : false
			// 打印返回数据

			});

		});
		// 付款账户
		$('body').on('click', '.paymentBtn2', function(event) {

			var $this = $(this);

			fundDitch = new popupWindow({

				YwindowObj : {
					content : 'paymentScript', // 弹窗对象，支持拼接dom、template、template.compile
					closeEle : '.close', // find关闭弹窗对象
					dragEle : '.newPopup dl dt' // find拖拽对象
				},

				ajaxObj : {
					url : '/baseDataLoad/bankMessage.json',
					type : 'post',
					dataType : 'json',
					data : {
						// isChargeNotification: "IS",
						// projectCode: _projectCode,
						pageSize : 6,
						pageNumber : 1
					}
				},
				formAll : { // 搜索
					submitBtn : '#PopupSubmit', // find搜索按钮
					formObj : '#PopupFrom', // find from
					callback : function($Y) {

					}
				},
				pageObj : { // 翻页
					clickObj : '.pageBox a.btn', // find翻页对象
					attrObj : 'page', // 翻页对象获取值得属性
					jsonName : 'pageNumber', // 请求翻页页数的dataName
					callback : function($Y) {

						// console.log($Y)

					}
				},

				callback : function($Y) {

					$Y.wnd.on('click', 'a.choose', function(event) {
						var _this = $(this), _account = _this.parents('tr').attr("account"), _name = _this.parents('tr').attr("name"), _bank = _this.parents('tr').attr("bank"), _amountStr = _this.parents('tr').find('td').eq(5).text().replace(/\,/g, '');
						// console.log(_this.parents('tr').html())
						$this.siblings('.fnBankAccount').val(_account).blur();
						$this.parents('tr').find('.fnReceiptName').val(_name).blur();
						$this.parents('tr').find('.fnBankName').val(_bank).blur();

						if ($this.siblings('.feeTypePrev ').length) {

							$this.parents('tr').find('[amountStr]').attr('amountStr', _amountStr).blur();
							$this.parents('tr').attr('paymentAccount', _account);

						}

						$Y.close();

					});

				},

				console : false
			// 打印返回数据

			});

		});

		// 节流阀
		var timer, recordName, place;
		var Ddebounce = function(fn, delay) {

			clearTimeout(timer);
			timer = setTimeout(fn, delay);

		};

		function getCursortPosition(ctrl) {
			var CaretPos = 0;
			if (document.selection) {
				ctrl.focus();
				var Sel = document.selection.createRange();
				Sel.moveStart('character', -ctrl.value.length);
				CaretPos = Sel.text.length;
			} else if (ctrl.selectionStart || ctrl.selectionStart == '0') {
				CaretPos = ctrl.selectionStart
			}
			;
			return (CaretPos);
		}

		function setCaretPosition(ctrl, pos) {
			if (ctrl.setSelectionRange) {
				ctrl.focus();
				ctrl.setSelectionRange(pos, pos);
			} else if (ctrl.createTextRange) {
				var range = ctrl.createTextRange();
				ctrl.focus();
				range.collapse(true);
				range.moveEnd('character', pos);
				range.moveStart('character', pos);
				range.select();
			}
		}

		// 会计科目
		$('body').on('click', '.subjectBtn', function(event) {

			var choiceSelect = $('.choiceSelect');
			var limitHandleName = $('.limitHandleName');

			if (choiceSelect.val() == 'OTHER' && limitHandleName.length && !limitHandleName.val()) {

				hintPopup('请选择经办人');
				return false;

			}

			var limitApplyDept = $('.limitApplyDept');
			if (choiceSelect.val() == 'OTHER' && limitApplyDept.length && !limitApplyDept.val()) {

				hintPopup('请选择经办人部门');
				return false;

			}

			var limitRelevance = $('[name="relevance"]');
			if (choiceSelect.val() == 'FORM' && limitRelevance.length && !limitRelevance.val()) {

				hintPopup('请选择关联单据');
				return false;

			}

			var limitAtName = $('#limitAtName');
			if (limitAtName.length && !limitAtName.val()) {

				hintPopup('请选择所属机构');
				return false;
			}

			var $this = $(this);
			fundDitch = new popupWindow({

				YwindowObj : {
					content : 'subjectScript', // 弹窗对象，支持拼接dom、template、template.compile
					closeEle : '.close', // find关闭弹窗对象
					dragEle : '.newPopup dl dt' // find拖拽对象
				},

				ajaxObj : {
					url : '/baseDataLoad/accountTitle.json',
					type : 'post',
					dataType : 'json',
					data : {
						deptId : $('input[name=deptId]').val(),
						deptCode : $('input[name=applyDeptCode]').val(),
						pageSize : 50,
						pageNumber : 1
					}
				},
				formAll : { // 搜索
					submitBtn : '#PopupSubmit', // find搜索按钮
					formObj : '#PopupFrom', // find from
					callback : function($Y) {

						var recordFocus, val;

						if (recordName) {

							recordFocus = $Y.wnd.find('[name="' + recordName + '"]');
							setCaretPosition(recordFocus[0], place);

						}
						// recordFocus.focus();
						// val = recordFocus.val();
						// recordFocus.val('').focus().val(val);

					}
				},
				pageObj : { // 翻页
					clickObj : '.pageBox a.btn', // find翻页对象
					attrObj : 'page', // 翻页对象获取值得属性
					jsonName : 'pageNumber', // 请求翻页页数的dataName
					callback : function($Y) {

						// console.log($Y)

					}
				},

				callback : function($Y) {

					$Y.wnd.on('click', 'a.choose', function(event) {

						var _this = $(this), _tr = _this.parents('tr'), _atCode = _tr.attr("atCode"), _atName = _tr.attr("atName");

						$this.siblings('.fnAtCode').val(_atCode).blur();
						$this.siblings('.fnAtName').val(_atName).blur();
						$this.siblings('.fnAtCodeName').val(_atCode + "-" + _atName).blur();
						$Y.close();

					});

					$Y.wnd.on('keyup', '[name="atCode"],[name="atName"]', function() {

						var record = $(this);
						recordName = record.attr('name');

						place = getCursortPosition(record[0]);

						Ddebounce(function() {

							$Y.wnd.find('#PopupSubmit').trigger('click');

						}, 300);

						if (recordName == 'atCode') {

							$Y.wnd.find('[name="UEDselect"]').val(record.val());

						}

					})

					$Y.wnd.on('change', '[name="UEDselect"]', function() {

						var $this = $(this);

						$Y.wnd.find('[name="atCode"]').val($this.find(':checked').val());

						Ddebounce(function() {

							$Y.wnd.find('#PopupSubmit').trigger('click');

						}, 300);

					})

				},

				console : false
			// 打印返回数据

			});

		});

		$('body').on('click', '.clearContent', function(event) {

			var $this, $target;

			$this = $(this);
			$target = $this.siblings('.clearContentTarget');
			$target.val('');
			$this.parents('tr').attr('paymentAccount', '').find('[amountStr]').attr('amountStr', '')

		});

	}

})