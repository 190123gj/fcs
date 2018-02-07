define(function(require, exports, module) {
	var util = require('util');
	// 审核通用部分 start
	var auditProject = require('/js/tmbp/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');

	// ------ 侧边栏 start
	var publicOPN = new (require('zyw/publicOPN'))();
	publicOPN.init().doRender();
	// ------ 侧边栏 end

	$('#fnPrint').click(function(event) {

		var $fnPrintBox = $('#div_print')
		$fnPrintBox.find('.ui-btn-submit').remove()
		$fnPrintBox.find('.printshow').removeClass('fn-hide')
		util.print($fnPrintBox.html())
	})
});