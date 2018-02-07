define(function(require, exports, module) {

	var setUE = require('zyw/setUE');
	setTimeout(function() {
		setUE();
	}, 1000);

	// 审核通用部分 start
	var auditProject = require('/js/tmbp/auditProject');
	var _auditProject = new auditProject('auditSubmitBtn');
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
	publicOPN.addOPN([{
		name: '查看尽职调查',
		url: '/projectMg/investigation/viewDeclare.htm?formId=' + $('#hddFormId').val(),
	}]).init().doRender();
	//------ 侧边栏 end
	//上传
	require('zyw/upAttachModify');

});