define(function(require, exports, module) {

	require('Y-imageplayer');

	$('body').on('click', '.fnAttachView', function() {
		//查看所有上传的附件
		var _imgs = [];

		$('.fnAttachView').each(function(index, el) {
			var _this = $(this);
			_imgs.push({
				src: _this.parent().attr('val'),
				desc: _this.text()
			});
		});

		Y.create('ImagePlayer', {
			imgs: _imgs,
			index: $(this).index('.fnAttachView'),
			scaleLowerLimit: 0.1,
			loop: false,
			fullAble: false,
			firstTip: '这是第一张了',
			firstTip: '这是最后一张了'
		}).show();

	});

	// 审核通用部分 start
	var auditProject = require('/js/tmbp/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit({
		// 初始化审核
		// 自定义 确定函数
		// doPass: function(self) {
		// 	alert('1');
		// 	self.audit.$box.find('.close').eq(0).trigger('click');
		// },
		// doNoPass: function(self) {
		// 	alert('3');
		// 	self.audit.$box.find('.close').eq(0).trigger('click');
		// },
		// doBack: function(self) {
		// 	alert('2');
		// 	self.audit.$box.find('.close').eq(0).trigger('click');
		// }
	}).initPrint('打印的url');

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.init().doRender();
	//------ 侧边栏 end

});