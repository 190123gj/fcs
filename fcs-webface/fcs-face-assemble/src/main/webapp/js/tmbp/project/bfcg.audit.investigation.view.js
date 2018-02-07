define(function(require, exports, module) {

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