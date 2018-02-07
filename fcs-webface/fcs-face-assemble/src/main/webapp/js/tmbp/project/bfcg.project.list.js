define(function(require, exports, module) {
	// 项目管理 > 项目列表
	require('Y-msg');
	require('zyw/publicPage');

	require('../workflowTemplate');
	require('validate');

	var util = require('util');

	var getTypesOfCredit = require('zyw/getTypesOfCredit')

	// 授信类型
	$("#fnListExport").click(function() {
		var url = $(this).attr("exportUrl");
		url = url + "?" + $("#fnListSearchForm").serialize();
		window.location = url;
	});

	// 授信类型
	var _getTypesOfCredit = new getTypesOfCredit();
	_getTypesOfCredit.init({
		chooseAll : true,
		btn : '#businessTypeBtn',
		name : '#businessTypeName',
		code : '#businessTypeCode'
	});

	// 暂缓
	$('.fnPause').click(function() {
		var projectCode = $(this).parents("tr").attr("projectCode");
		Y.confirm('信息提醒', '您确定要暂缓项目：' + projectCode + '？', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url : '/projectMg/pause.htm',
					data : {
						projectCode : projectCode
					},
					success : function(res) {
						Y.alert('提示', res.message, function() {
							if (res.success) {
								document.getElementById('fnListSearchForm').submit();
							}
						});
					}
				});
			}
		});
	});

	// 重启
	$('.fnRestart').click(function() {
		var projectCode = $(this).parents("tr").attr("projectCode");
		Y.confirm('信息提醒', '您确定要重启项目：' + projectCode + '？', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url : '/projectMg/restart.htm',
					data : {
						projectCode : projectCode
					},
					success : function(res) {
						Y.alert('提示', res.message, function() {
							if (res.success) {
								document.getElementById('fnListSearchForm').submit();
							}
						});
					}
				});
			}
		});

	});

	// ------ 客户移交 start
	var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选 尽量在url后面加上参数
	var selUrl = "/bornbpm/platform/system/sysUser/dialog.do?isSingle=true";
	if ($("#isAdmin").val() != 'true') {
		selUrl = selUrl + '&onlyViewChild=true';
	}
	if ($("#isBusiDirector").val() == 'true') {
		selUrl = selUrl + '&onlyBizDept=true';
	}
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe(selUrl, {
		'title' : '人员',
		'width' : 850,
		'height' : 460,
		'scope' : '{type:\"system\",value:\"all\"}',
		'selectUsers' : {
			selectUserIds : '', // 已选id,多用户用,隔开
			selectUserNames : '' // 已选Name,多用户用,隔开
		},
		'bpmIsloginUrl' : '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl' : '/JumpTrust/makeLoginUrl.htm'
	});
	var acceptUserName = document.getElementById('acceptUserName'), acceptUserAccount = document.getElementById('acceptUserAccount'), acceptUserId = document.getElementById('acceptUserId');
	// 添加选择后的回调，以及显示弹出层
	$('#personnelBtn').on('click', function() {

		// 这里也可以更新已选择用户
		singleSelectUserDialog.obj.selectUsers = {
			selectUserIds : acceptUserId.value,
			selectUserNames : acceptUserName.value,
			selectUserAccounts : acceptUserAccount.value
		}

		singleSelectUserDialog.init(function(relObj) {
			acceptUserId.value = relObj.userIds;
			acceptUserName.value = relObj.fullnames;
			acceptUserAccount.value = relObj.accounts;

			var $acceptDeptId = $('#acceptDeptId').html('')

			// 查询部门
			util.ajax({
				url : '/baseDataLoad/userDetail.json',
				data : {
					userId : relObj.userIds
				},
				success : function(res) {

					if (res.success) {

						var _html = ''

						$.each(res.data.deptList, function(index, obj) {

							_html += '<option value="' + obj.id + '">' + obj.name + '</option>'

						});

						$acceptDeptId.html(_html)

					}

				}
			})

		});

	});

	var $fnTransferBox = $('#fnTransferBox'), $fnTransferForm = $fnTransferBox.find('form'), isBusiDirector = $("#isBusiDirector").val(), isLegalDirector = $("#isLegalDirector").val(), isAdmin = $("#isAdmin").val();

	$fnTransferBox.on('click', '.close', function() {
		window.location.reload(true);
	}).on('click', '.sure', function() {

		if (!!!$fnTransferForm.find('#acceptUserId').val()) {
			Y.alert('提示', '请选择移交人员');
			return;
		}

		if (!!!$fnTransferForm.find('#acceptDeptId').val()) {
			Y.alert('提示', '请选择移交人员所属的部门');
			return;
		}

		util.ajax({
			url : $fnTransferForm.attr('action'),
			data : $fnTransferForm.serialize(),
			success : function(res) {
				if (res.success) {
					Y.alert('提示', '移交成功', function() {
						window.location.reload(true);
					})
				} else {
					Y.alert('移交失败', res.message)
				}
			}
		});

	});

	$('.fnTransfer').on('click', function() {

		var _thisTr = $(this).parents('tr');

		$fnTransferBox.find('.code').eq(0).val(_thisTr.attr('projectCode'));
		$fnTransferBox.find('.code').eq(1).html(_thisTr.attr('projectCode'));
		$fnTransferBox.find('.customer').html(_thisTr.attr('customer'));
		$fnTransferBox.find('.manager').html(_thisTr.attr('manager'));
		if (!_thisTr.attr('riskManager')) {
			$fnTransferBox.find('.riskManager').parent().hide();
			$("[name=userType][value=RISK_MANAGER]").attr("disabled", true).parent().hide();
		} else {
			$fnTransferBox.find('.riskManager').html(_thisTr.attr('riskManager')).parent().show();
			$("[name=userType][value=RISK_MANAGER]").attr("disabled", false).parent().show();
		}

		if (!_thisTr.attr('legalManager')) {
			$fnTransferBox.find('.legalManager').parent().hide();
			$("[name=userType][value=LEGAL_MANAGER]").attr("disabled", true).parent().hide();
		} else {
			$fnTransferBox.find('.legalManager').html(_thisTr.attr('legalManager')).parent().show();
			$("[name=userType][value=LEGAL_MANAGER]").attr("disabled", false).parent().show();
		}
		var belong2LegalDept = _thisTr.attr("belong2LegalDept");
		if (belong2LegalDept == 'true' && isLegalDirector == 'true') {// 属于法务部
			$(".manager").parent().show();
			$("[name=userType][value=BUSI_MANAGER]").attr("disabled", false).parent().show();
		} else if (isBusiDirector != 'true' && isAdmin != 'true') {
			$(".manager").parent().hide();
			$("[name=userType][value=BUSI_MANAGER]").attr("disabled", true).parent().hide();
		}

		$fnTransferBox.removeClass('fn-hide');
	});

	// ------ 客户移交 end

	// ------ 项目总结 start
	var $summaryForm = $('#summaryForm'), requiredRules = {
		rules : {},
		messages : {}
	};

	util.setValidateRequired($('.fnInput'), requiredRules)

	$summaryForm.validate($.extend(true, util.validateDefault, {
		rules : requiredRules.rules,
		messages : requiredRules.messages,
		submitHandler : function() {
			util.ajax({
				url : $summaryForm.attr('action'),
				data : $summaryForm.serialize(),
				success : function(res) {

					if (res.success) {

						util.postAudit({
							formId : res.form.formId
						}, function() {
							window.location.href = '/projectMg/list.htm';
						})

					} else {

						Y.alert('操作失败', res.message)

					}

				}
			});
		}
	}));
	// ------ 项目总结 end

	// 复议
	$(".fn-reconsider").on("click", function() {

		var projectCode = $(this).attr("projectCode"), isApproval = $(this).attr("isApproval"), isApprovalDel = $(this).attr("isApprovalDel"), isRedoProject = $(this).attr("isRedoProject"), isInnovativeProduct =$(this).attr("isInnovativeProduct");

		if (isRedoProject == "IS" && isInnovativeProduct != "true") {
			window.location = '/projectMg/recouncil/form.htm?projectCode=' + projectCode;
		}
		// 2017-5-5 完善复议流程（先查询接口）
		util.ajax({
			url : '/baseDataLoad/queryInvestigationProjects.json?fstatus=APPROVAL&projectCode=' + projectCode,
			success : function(res) {
				// console.log(res);
				if (res.success) {
					if (res.data.totalCount > 0) {
						//过会并且批复未作废
						if (isApproval == "IS" && isApprovalDel != "IS") { // 走复议申请
							window.location = '/projectMg/recouncil/form.htm?projectCode=' + projectCode;
						} else {
							Y.confirm('提示', '确认复议么', function(opn) { // 走尽调
								if (opn == 'yes') {
									// console.log("/projectMg/investigation/editDeclare.htm?projectCode="
									// + projectCode);
									// window.location.href =
									// "/projectMg/investigation/editDeclare.htm?review=YES&projectCode="
									// + projectCode;
									util.direct('/projectMg/investigation/editDeclare.htm?review=YES&projectCode=' + projectCode, '/projectMg/investigation/list.htm');
								}
							});
						}
					} else {
						Y.alert('提示', '项目正在审核中，不能申请复议！')
					}
				} else {
					Y.alert('提示', res.message)
				}
			}
		});

	});

	// ------ 审核 start

	var auditProject = require('zyw/auditProject');

	if (document.getElementById('auditForm')) {

		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

	}

	// ------ 审核 end

	// ------ 侧边栏 start
	if (document.getElementsByName('form')) {
		var publicOPN = new (require('zyw/publicOPN'))();
		publicOPN.init().doRender();
	}
	// ------ 侧边栏 end

	// 上传
	require('Y-htmluploadify');
	// 导入
	$('#dataImport').click(function(event) {
		var update = $(this).attr("update");
		if (!update)
			update = 'no';
		var htmlupload = Y.create('HtmlUploadify', {
			key : 'up1',
			uploader : '/projectMg/excelParse.json?update=' + update,
			multi : false,
			auto : true,
			addAble : false,
			fileTypeExts : '*.xls',
			fileObjName : 'UploadFile',
			onQueueComplete : function(a, b) {
			},
			onUploadSuccess : function($this, data, resfile) {

				var jsonData = JSON.parse(data);

				if (jsonData.success) {

					var _head = '<thead><tr><th>客户名称</th><th>项目编号</th><th>项目名称</th><th>导入结果</th></tr></thead>', _tbody = '', _result = jsonData.message

					$.each(_result, function(index, obj) {

						_tbody += '<tr><td>' + obj.customerName + '</td><td>' + (obj.projectCode || '') + '</td><td>' + obj.projectName + '</td><td>' + obj.importResult + '</td></tr>'

					})

					var _html = '<div style="height: auto; max-height: 400px; overflow-y: auto;"><table class="m-table m-table-list">' + _head + '<tbody>' + _tbody + '</tbody></table></div>'

					$('body').Y('Msg', {
						type : 'alert',
						width : '750px',
						content : _html,
						icon : '',
						yesText : '确定',
						callback : function(opn) {

							window.location.reload(true)

						}
					})

				} else {

					Y.alert('提示', jsonData.message)

				}

			}

		});

	});
});