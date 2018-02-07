define(function(require, exports, module) {
	// 项目管理 > 辅助系统 > 决策机构维护

	var util = require('util');
	require('Y-msg');
	require('input.limit');

	require('validate');


	var $form = $('#form'),
		$body = $('body'),
		$institutionName = $('#institutionName'),
		institutionNameVal = $institutionName.val();

	$form.validate({
		errorClass: 'error-tip',
		errorElement: 'b',
		ignore: '.ignore',
		onkeyup: false,
		errorPlacement: function(error, element) {

			if (element.hasClass('fnErrorAfter')) {

				element.after(error);

			} else {

				element.parents('.m-item').append(error);

			}

		},
		rules: {
			institutionName: {
				required: true
			},
			userName: {
				required: true
			}
		},
		messages: {
			institutionName: {
				required: '必填'
			},
			userName: {
				required: '必填'
			}
		}
	});


	$form.on('click', '.submit', function() {

		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		if (!$form.valid()) {
			_this.removeClass('ing');
			return;
		}

		//同步验证名称是否唯一
		//如果名称不同或没有
		var _canUse = true;
		if (!institutionNameVal || institutionNameVal != $institutionName.val()) {
			util.ajax({
				url: '/projectMg/assistSys/basicDataMg/checkInstitutionName.htm?institutionName=' + encodeURIComponent($institutionName.val()),
				async: false,
				success: function(res) {
					if (res.success) {

						_canUse = false;

					}
				}
			});
		}

		if (!_canUse) {
			//已重复
			Y.alert('提醒', '决策机构名称已存在');
			_this.removeClass('ing');
			return;
		}

		//resetName();

		var isExit = _this.hasClass('fnExit');
		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {

				if (res.success) {
					Y.alert('提示', '已保存~', function() {
						if (isExit) {
							window.location.href = '/projectMg/assistSys/basicDataMg/decisionList.htm';
						} else {
							window.location.reload(true);
						}
					});
				} else {
					Y.alert('提示', res.message);
					_this.removeClass('ing');
				}

			}
		});


	});

	function resetName() {

		$('.fnChooseMan').each(function(index, el) {

			var $this = $(this),
				_diyname = $this.attr('diyname');

			$this.find('[name]').each(function() {

				var _this = $(this),
					name = _this.attr('name');

				if (name.indexOf('.') > 0) {
					name = name.substring(name.indexOf('.') + 1);
				}

				name = _diyname + '[' + index + '].' + name;

				_this.attr('name', name);

			});

		});

	}

	//------- 选人 start
	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
		'title': '决策机构人员',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '',
			selectUserNames: ''
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	var userId = document.getElementById('userId');
	var userName = document.getElementById('userName');

	$body.on('click', '.fnChooseManBtn', function() {

		// 更新已选
		singleSelectUserDialog.obj.selectUsers.selectUserIds = userId.value;
		singleSelectUserDialog.obj.selectUsers.selectUserNames = userName.value;

		singleSelectUserDialog.init(function(relObj) {

			userId.value = relObj.userIds;
			userName.value = relObj.fullnames;

		});

	});

	//------- 选人 end



});