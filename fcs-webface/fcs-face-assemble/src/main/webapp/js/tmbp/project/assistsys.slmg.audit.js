define(function(require, exports, module) {
	// 项目管理 > 辅助系统  > 用印申请审核

	require('zyw/upAttachModify');

	//------ 审核通用部分 start
	var auditProject = require('zyw/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');
	//------ 审核通用部分 end
	//
	//
	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.init().doRender();
	//------ 侧边栏 end

	$('.fnNewWindowOpen').on('click', function(e) {
		e.preventDefault();
		window.open(this.href);
	});

});