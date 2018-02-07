define(function(require, exports, module) {
	// 项目管理 > 内审管理 
	require('zyw/publicPage');
	require('Y-msg');
	require('input.limit');

	require('zyw/upAttachModify');

	var util = require('util');

	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
	// 选择 部门 单选
	// 初始化弹出层
	var singleSelectOrgDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=false', {
		'title': '部门',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [],
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	$('#fnChooseDepartment').on('click', function() {

		var $this = $(this),
			_$parent = $this.parent(),
			_$id = _$parent.find('.fnDepartmentId'),
			_$name = _$parent.find('.fnDepartmentName');

		singleSelectOrgDialog.obj.arrys = [{
			id: _$id[0].value,
			name: _$name[0].value
		}];

		singleSelectOrgDialog.init(function(relObj) {

			_$id[0].value = relObj.orgId;
			_$name[0].value = relObj.orgName;
			_$id.trigger('change');

		});

	});


	// 选择人员
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=false', {
		'title': '人员',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '', //已选id,多用户用,隔开
			selectUserNames: '' //已选Name,多用户用,隔开
				//,selectUserAccounts: '' //已选Account,多用户用,隔开
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	$('#fnChooseMan').on('click', function() {

		var $this = $(this),
			_$parent = $this.parent(),
			_$id = _$parent.find('.fnManId'),
			_$name = _$parent.find('.fnManName');
		//,_$account = _$parent.find('.fnManAccount');

		singleSelectUserDialog.obj.selectUsers = {
			selectUserIds: _$id[0].value, //已选id,多用户用,隔开
			selectUserNames: _$name[0].value //已选Name,多用户用,隔开
				//,selectUserAccounts: _$account[0].value //已选Account,多用户用,隔开
		}

		singleSelectUserDialog.init(function(relObj) {

			_$id[0].value = relObj.userIds;
			_$name[0].value = relObj.fullnames;
			//_$account[0].value = relObj.accounts;
			_$id.trigger('change');

		});

	});

	// 清除
	$('.fnClearBtn').on('click', function() {
		$(this).parent().find('input').val('')
	});

	// 是否在一个部门
	var isInDepartment = false;

	// 监听部门、人员
	$('#fnDepartmentId, #fnManId').on('change', function() {

		var _dId = document.getElementById('fnDepartmentId').value,
			_dName = document.getElementById('fnDepartmentName').value,
			_mId = document.getElementById('fnManId').value;

		if (!!!_dId || !!!_mId) {
			return;
		}

		$.ajax({
			url: '/projectMg/internalOpinionExchange/checkDeptUser.htm',
			type: 'POST',
			dataType: 'json',
			data: {
				deptIds: _dId,
				deptNames: _dName,
				userIds: _mId
			},
			success: function(res) {
				if (res.success) {
					isInDepartment = true;
				} else {
					Y.alert('提示', res.message);
					isInDepartment = false;
				}
			}
		});
	});

	$('#fnManId').trigger('change');

	// 是否需要整改
	$('.fnRectifyRadio').on('click', function() {

		var $d = $('#fnFeedBackTime');
		if (this.value == 'YES') {
			$d.removeClass('fn-hide').find('input').addClass('fnInput');
		} else {
			$d.addClass('fn-hide').find('input').removeClass('fnInput');
		}

	});

	// 时间必须大于今天
	if (document.getElementById('fnFeedBackTimes')) {
		$('#fnFeedBackTimes').attr('onclick', 'laydate({"min": "' + laydate.now(+1) + '"})');
	}

	var $form = $('#form');

	$form.on('click', '#submit', function() {

		var _isPass = true,
			_isPassEq;

		var $fnInput = $('.fnInput');

		$fnInput.each(function(index, el) {

			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = index;
				return false;
			}

		});

		if (!_isPass) {
			Y.alert('提示', '请填写完整表单', function() {
				$fnInput.eq(_isPassEq).focus();
			})
			return;
		}

		if (!isInDepartment) {
			Y.alert('提示', '审计人员与审计部门不匹配，请重新选择');
			return;
		}

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {
				if (res.success) {
//					util.ajax({
//						url: '/projectMg/form/submit.htm',
//						data: {
//							formId: res.form.formId
//						},
//						success: function(ress) {
//							Y.alert('提示', ress.message, function() {
//								window.location.href = '/projectMg/internalOpinionExchange/list.htm';
//							});
//						}
//					});
					util.postAudit({
                        formId: res.form.formId
                    }, function () {
                        window.location.href = '/projectMg/internalOpinionExchange/list.htm';
                    })
				} else {
					Y.alert('操作失败', res.message);
				}
			}
		});

	});

	// ------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();

	publicOPN.init().doRender();
	// ------ 侧边栏 end


	// ------ 审核 start

	var auditProject = require('zyw/auditProject');

	if (document.getElementById('auditForm')) {

		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

	}

	// ------ 审核 end

	// ------ 打印 start
	$('#fnPrint').on('click', function() {
		util.print('<h1 class="fn-tac fn-fs24">' + document.getElementById('printTitle').innerHTML + '</h1>' + document.getElementById('printHtml').innerHTML);
	});
	// ------ 打印 end

});