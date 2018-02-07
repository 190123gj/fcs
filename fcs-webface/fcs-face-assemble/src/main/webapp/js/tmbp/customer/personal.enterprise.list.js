/*
 * 关于移交分配 只需要给对应接口正确的数据就好
 * 角色不同，操作也不同
 * 分配 -> 总监
 * 移交 -> 业务经理
 *
 * 
 */

define(function(require, exports, module) {
	// 客户管理 > 个人客户 > 客户列表
	require('Y-msg');
	require('zyw/publicPage');

	var util = require('util');

	var isTransfer; // 是否是移交状态，每次点击的时候，在tr上找状态
	var NEEDCHECKDEPID, NEEDCHECKDEPIDPASS = true;

	$('#list').on('click', '.fnDelCustomer', function() {

		// 删除客户

		var _this = $(this), _candel = _this.attr('candel') ? true : false;

		if (_candel) {

			Y.confirm('提示', '是否确认删除所选中的客户信息？', function(opn) {

				if (opn == 'yes') {
					util.ajax({
						url : _this.parents('tbody').attr('delaction'),
						data : {
							userId : _this.parents('tr').attr('formid')
						},
						success : function(res) {

							if (res.success) {

								Y.alert('提示', '操作成功', function() {
									window.location.reload(true);
								});

							} else {

								Y.alert('操作失败', res.message);

							}

						}
					});

				}

			});

		} else {

			Y.alert('提示', '该客户有关联的项目，不允许删除！')

		}

	}).on('click', '.fnChangeMG', function() {

		// 移交、分配客户经理
		var _this = $(this);
		document.getElementById('fnTransferFormId').value = _this.parents('tr').attr('formid');
		document.getElementById('fnTransferRelationId').value = _this.attr('relationId') || '';
		document.getElementById('fnTransferProvinceCode').value = _this.attr('provinceCode') || '';
		$fnTransferForm.attr('action', (_this.attr('istransfer') == 'IS') ? '/customerMg/customer/transfer.json' : '/customerMg/customer/distribution.json');
		$fnTransferBox.removeClass('fn-hide');
		isTransfer = (_this.attr('istransfer') == 'IS') ? true : false;
		NEEDCHECKDEPID = _this.attr('depId');

	});

	// ------ 客户经理、总监 start
	var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选 尽量在url后面加上参数
	// 初始化弹出层
	var selUrl = "/bornbpm/platform/system/sysUser/dialog.do?isSingle=true&onlyBizDept=true";
	if ($("#isAdmin").val() != 'true') {
		selUrl = selUrl + '&onlyViewChild=true';
	}
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
		});

	});

	var $fnTransferBox = $('#fnTransferBox'), $fnTransferForm = $fnTransferBox.find('form');

	$fnTransferBox.on('click', '.close', function() {

		window.location.reload(true);

	}).on('click', '.sure', function() {

		if (!!!acceptUserId.value) {
			Y.alert('提示', document.getElementById('fnTransferTitle').innerHTML.replace(/\：/g, ''));
			return;
		}

		// 验证是否选对了用户

		// 选择的角色验证

		$.when(getUserRole(acceptUserId.value, NEEDCHECKDEPID)).then(function(res) {
			return $.when(getUserDep(acceptUserAccount.value));
		}).then(function(xhr) {
			// 设置部门信息
			document.getElementById('acceptUserDepId').value = xhr.data.primaryOrg.id;
			document.getElementById('acceptUserDepName').value = xhr.data.primaryOrg.name;

			util.ajax({
				url : $fnTransferForm.attr('action'),
				data : $fnTransferForm.serialize(),
				success : function(res) {
					if (res.success) {
						Y.alert('提示', '操作成功', function() {
							window.location.reload(true);
						})
					} else {
						Y.alert('操作失败', res.message)
					}
				}
			});
		}).fail(function(res) {
			Y.alert('提示', res.message);
		});

	});

	function getUserRole(userId, depId) {

		var dtd = $.Deferred();

		util.ajax({
			url : '/customerMg/customer/userRole.json?userId=' + userId + '&depId=' + (!!depId ? depId : '') + '&provinceCode=' + document.getElementById('fnTransferProvinceCode').value,
			success : function(res) {
				if (res.success) {
					if (getRightMan(isTransfer, res.role)) {
						// 总监和经理的 name 值不同
						setRightName(res.role);
						document.getElementById('fnTransferType').value = res.role;

						dtd.resolve({
							role : res.role
						});

					} else {

						dtd.reject({
							message : '选择的人不合符要求，请重新选择'
						});

					}
				} else {
					dtd.reject({
						message : res.message
					});
				}
			}
		});

		return dtd.promise();

	}

	function getUserDep(UserAccount) {

		var dtd = $.Deferred();

		util.ajax({
			url : '/baseDataLoad/userDetail.json?userAccount=' + UserAccount,
			success : function(res) {
				if (res.success) {
					dtd.resolve(res);
				} else {
					dtd.reject({
						message : res.message
					});
				}
			}
		});

		return dtd.promise();

	}

	function getRightMan(isTransfer, role) {

		// 移交接口只能是业务经理
		// 分配接口两者都可以
		if (isTransfer) {

			if (role != 'director') {
				return true;
			} else {
				return false;
			}

		} else {
			return true;
		}

	}

	function setRightName(role) {

		if (role == 'director') {
			// 总监
			acceptUserId.name = 'directorId';
			acceptUserName.name = 'director';
		} else {
			acceptUserId.name = 'customerManagerId';
			acceptUserName.name = 'customerManager';
		}

	}

	$('.fnTransfer').on('click', function() {

		var _thisTr = $(this).parents('tr');

		$fnTransferBox.find('.code').eq(0).val(_thisTr.attr('projectCode'));
		$fnTransferBox.find('.code').eq(1).html(_thisTr.attr('projectCode'));
		$fnTransferBox.find('.customer').html(_thisTr.attr('customer'));
		$fnTransferBox.find('.manager').html(_thisTr.attr('manager'));

		$fnTransferBox.removeClass('fn-hide');
	});

	// ------ 客户经理、总监 end
});