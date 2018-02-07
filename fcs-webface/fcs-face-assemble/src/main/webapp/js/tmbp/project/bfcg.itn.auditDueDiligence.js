define(function(require, exports, module) {

	//Nav选中样式添加
	require('zyw/project/bfcg.itn.toIndex');
	//表单验证
	require('validate');
	require('validate.extend');
	//异步提交
	require('form')();
	//我们熟悉的。。。。
	require('Y-window');
	//弹窗提示
	var hintPopup = require('zyw/hintPopup'),
		modalwnd;
	//js模板引擎
	//var template = require('arttemplate');
	$('.operationBtn li a').click(function(event) {
		var $this = $(this),
			_title = $this.text(),
			_content = $this.attr('Popup');
		$('body').Y('Window', {
			content: $('#' + _content).html(),
			modal: true,
			key: 'modalwnd',
			simple: true
		});
		var modalwnd = Y.getCmp('modalwnd');
		modalwnd.wnd.on('click', '.close', function() {
				//(_content!='save')?modalwnd.close():hintPopup('单据保存成功');
				modalwnd.close();
			})
			.find('b.sign').text(_title).end()
			.validate({
				errorClass: 'error-tip',
				errorElement: 'b',
				errorPlacement: function(error, element) {
					element.after(error);
				},
				onkeyup: false, //不在按键时进行验证
				ignore: '.cancel',
				rules: {
					sss: {
						required: true
					}
				},
				messages: {
					sss: {
						required: '必填'
					}
				},
				submitHandler: function(form) {
					$(form).ajaxSubmit({
						type: 'post',
						dataType: 'json',
						success: function(res) {
							alert(1111111111111)
						}
					});
				}
			})
	});
	$('.auditCommon dt').click(function(event) {
		$('.auditCommon dd').eq(0).slideToggle();
	});
	
		
		// 审核通用部分 start
		// var auditProject = require('/js/tmbp/auditProject');
		// var _auditProject = new auditProject();
		// _auditProject.initFlowChart().initSaveForm().initAssign().initAudit({
		// 	//初始化审核
		// 	//自定义 确定函数
		// 	doPass: function(self) {
		// 		//alert('1');
		// 		self.audit.$box.find('.close').eq(0).trigger('click');
		// 	},
		// 	doNoPass: function(self) {
		// 		//alert('3');
		// 		self.audit.$box.find('.close').eq(0).trigger('click');
		// 	},
		// 	doBack: function(self) {
		// 		//alert('2');
		// 		self.audit.$box.find('.close').eq(0).trigger('click');
		// 	}

	// }).initPrint('打印的url');

});