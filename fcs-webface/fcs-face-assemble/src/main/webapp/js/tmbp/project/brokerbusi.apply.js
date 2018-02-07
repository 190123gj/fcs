define(function(require, exports, module) {
	// 项目管理 > 经纪业务 
	require('zyw/publicPage');
	require('Y-msg');
	require('input.limit');

	require('zyw/upAttachModify');

	var util = require('util');


	var $form = $('#form'),
		$fnInput = $('.fnInput');

	$form.on('click', '#submit', function() {

		var _isPass = true,
			_isPassEq;

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
//								window.location.href = '/projectMg/brokerBusiness/list.htm';
//							});
//						}
//					});
					util.postAudit({
                        formId: res.form.formId
                    }, function () {
                        window.location.href = '/projectMg/brokerBusiness/list.htm';
                    })
				} else {
					Y.alert('操作失败', res.message);
				}
			}
		});

	});



	//BPM弹窗
	var BPMiframe = require('BPMiframe');
	var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
	var BPMiframeConf = {
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '',
			selectUserNames: ''
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	};

	//---法务经理
	if ($('#chooseLegalManager').val() == 'YES') {
		var _chooseLegalManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
			title: '法务经理'
		}));

		var $legalManagerName = $('#legalManagerName'),
			$legalManagerId = $('#legalManagerId'),
			$legalManagerAccount = $('#legalManagerAccount');

		$('#legalManagerBtn').on('click', function() {

			_chooseLegalManager.init(function(relObj) {

				$legalManagerId.val(relObj.userIds);
				$legalManagerName.val(relObj.fullnames);
				$legalManagerAccount.val(relObj.accounts);

			});
		});
	}

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

});