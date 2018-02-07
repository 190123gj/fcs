define(function (require, exports, module) {
	// 项目管理 > 授信后管理  > 保后项目汇总表
	require('zyw/publicPage');
	require('input.limit');
	require('Y-msg');

	var util = require('util');

	//------ 列表页 start

	//---选择部门 start
	var $fnOrgName = $('#fnOrgName'),
		$fnOrgId = $('#fnOrgId');

	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=false', {
		'title': '部门组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$('#fnOrgBtn').on('click', function () {

		//这里也可以更新已选择机构
		var _arr = [],
			_nameArr = $fnOrgName.val().split(','),
			_idArr = $fnOrgId.val().split(',');

		for (var i = 0; i < _nameArr.length; i++) {
			if (_nameArr[i]) {
				_arr.push({
					id: _idArr[i],
					name: _nameArr[i]
				});
			}
		}

		singleSelectUserDialog.obj.arrys = _arr;

		singleSelectUserDialog.init(function (relObj) {

			$fnOrgId.val(relObj.orgId);
			$fnOrgName.val(relObj.orgName);

		});

	});
	//---选择部门 end
	//
	//
	//---新增还是编辑
	$('#fnAddBtn').on('click', function (e) {

		e.preventDefault();
		var _formId = document.getElementById('formId').value;
		if (_formId) {
			Y.alert('提示', '本期已填写汇总报告，点击“确定”去查看', function (opn) {
				if (opn == 'yes') {
					window.location.href = '/projectMg/afterwardsSummary/viewSummary.htm?formId=' + _formId
				}
			})
		} else {
			window.location.href = this.href;
		}

	});

	//------ 列表页 emd

	//------ 新增 start
	var $form = $('#form'),
		$fnInput = $('.fnInput');
	$form.on('click', '#submit', function () {

		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		// var _isPass = true,
		// 	_isPassEq;

		// $fnInput.each(function (index, el) {
		// 	if (!!!el.value.replace(/\s/g, '')) {
		// 		_isPass = false;
		// 		_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
		// 	}
		// });

		// if (!_isPass) {
		// 	Y.alert('提示', '请填写完整项目详情', function () {
		// 		$fnInput.eq(_isPassEq).focus();
		// 	})
		// 	_this.removeClass('ing');
		// 	return;
		// }

		document.getElementById('checkStatus').value = '1'

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function (res) {
				if (res.success) {
					util.postAudit({
							formId: res.formId
						}, function () {
							window.location.href = '/projectMg/afterwardsSummary/list.htm';
						})
						//					Y.alert('提示', res.message, function () {
						//						// window.location.href = '/projectMg/afterwardsCheck/summaryList.htm';
						//						util.postAudit({
						//							formId: res.form.formId
						//						}, function () {
						//							window.location.href = '/projectMg/afterwardsCheck/summaryList.htm';
						//						})
						//					});
				} else {
					Y.alert('提示', res.message);
					_this.removeClass('ing');
				}
			}
		});

	});
	//------ 新增 end


	// ------ 审核 start
	if (document.getElementById('auditForm')) {
		var auditProject = require('zyw/auditProject');
		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
	}
	// ------ 审核 end

	//------ 侧边工具栏 start
	(new(require('zyw/publicOPN'))()).init().doRender();
	//------ 侧边工具栏 end

});