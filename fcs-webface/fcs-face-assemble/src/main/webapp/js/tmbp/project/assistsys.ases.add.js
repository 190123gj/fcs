define(function(require, exports, module) {
	// 项目管理 > 辅助系统 > 决策机构维护
	var util = require('util');
	require('Y-msg');
	require('input.limit');

	//所属地区 相关
	require('zyw/chooseRegion');

	//表单验证
	require('validate');
	require('validate.extend');

	var $form = $('#form'),
		$companyName = $('#companyName'),
		companyNameVal = $companyName.val();

	var $addressResult = $('#addressResult'); //最后一个地区

	$('body').on('blur', '#addressResult', function() {
		$addressResult.valid();
	});

	//异步验证名称唯一性规则
	var nameIsNotRepeated = {
		url: '/projectMg/basicmaintain/assessCompany/checkCompanyName.htm', //后台处理程序 远程地址只能输出 "true" 或 "false"，不能有其他输出。
		type: 'POST', //数据发送方式
		dataType: 'json', //接受数据格式   
		data: { //要传递的数据
			companyName: function() {
				return $companyName.val();
			}
		}
	};

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
			companyName: {
				required: true,
				remote: nameIsNotRepeated,
			},
			region: {
				required: true
			},
			contactName: {
				required: true
			},
			contactNumber: {
				required: true,
				isPhoneOrMobile: true
			}
		},
		messages: {
			companyName: {
				required: '必填',
				remote: '已有该公司'
			},
			region: {
				required: '必填'
			},
			contactName: {
				required: '必填'
			},
			contactNumber: {
				required: '必填',
				isPhoneOrMobile: '请输入正确的号码'
			}
		}
	});

	//提交
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

		if (!!!document.getElementById('cityName').value) {
			Y.alert('提示', '请选择所在城市');
			_this.removeClass('ing');
			return;
		}

		var isExit = _this.hasClass('fnExit');

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {

				if (res.success) {
					Y.alert('提示', '已保存~', function() {
						if (isExit) {
							window.location.href = '/projectMg/basicmaintain/assessCompany/assessCompanyList.htm';
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

	//是否是编辑状体
	if (!!companyNameVal) {
		//移除唯一性校验
		$companyName.rules('remove', 'remote');

		// 编辑状体监听值得变化
		$companyName.on('change', function() {

			if (this.value != companyNameVal) {

				$companyName.rules('add', {
					remote: nameIsNotRepeated,
					messages: {
						remote: '已有该公司'
					}
				});

			} else {

				$companyName.rules('remove', 'remote');

			}

			$companyName.valid();

		});

	}

	// 地域属性 国内
	setTimeout(function() {
		$('#addressBox').find('select').eq(0).find('option').each(function(index, el) {

			var _this = $(this);
			if (_this.val() !== 'China' && _this.val()) {
				_this.remove();
			}

		});
	}, 1000);



});