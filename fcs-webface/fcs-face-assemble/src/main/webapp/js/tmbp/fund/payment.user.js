define(function(require, exports, module) {
	// 资金管理 > 用户信息维护

	require('zyw/publicPage')

	require('Y-msg');

	require('validate');

	var BPMiframe = require('BPMiframe');

	var util = require('util'),
		$form = $('#form');

	// ------ 查看模式 start
	if (!!$('#fnIsView').val()) {

		$form.find('.ui-btn:not(.ui-btn-back)').remove();
		$form.find('input.ui-text').each(function(index, el) {

			var _span = document.createElement('span'),
				_parent = this.parentNode;
			_span.innerHTML = this.value;

			_parent.insertBefore(_span, this);
			_parent.removeChild(this);

		});

	}
	// ------ 查看模式 end

	var validObj = {
		rules: {},
		messages: {}
	};

	$('.fnInput').each(function(index, el) {

		validObj.rules[el.name] = {
			required: true
		};
		validObj.messages[el.name] = {
			required: '必填'
		};

	});

	$form.validate($.extend({}, {
		errorClass: 'error-tip',
		errorElement: 'b',
		ignore: '.ignore',
		onkeyup: false,
		errorPlacement: function(error, element) {

			if (element.hasClass('fnErrorAfter')) {

				element.after(error);

			} else {

				element.parent().append(error);

			}

		},
		submitHandler: function(form) {

			util.ajax({
				url: $form.attr('action'),
				data: $form.serialize(),
				success: function(res) {

					if (res.success) {
						Y.alert('提示', '操作成功', function() {
							window.location.href = '/';
						});
					} else {
						Y.alert('操作失败', res.message);
					}

				}
			});

		}
	}, validObj));


	// ------ 选择用户 star
	var selectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
		'title': '人员',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '', //已选id,多用户用,隔开
			selectUserNames: '' //已选Name,多用户用,隔开
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$('#fnChooseBtn').on('click', function() {

		selectUserDialog.init(function(relObj) {

			// _$id[0].value = relObj.userIds;
			// _$name[0].value = relObj.fullnames;
			// _$account[0].value = relObj.accounts;
			window.location.href = '/';

		});

	});
	// ------ 选择用户 end


});