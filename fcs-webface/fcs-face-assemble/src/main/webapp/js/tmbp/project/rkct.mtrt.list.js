define(function(require, exports, module) {
	// 项目管理 > 风险管控 > 上会申报记录
	require('zyw/publicPage');
	require('Y-msg');

	$('#fnNewBtn').on('click', function() {
		window.location.href = '/projectMg/riskHandle/edit.htm?date='+new Date().getTime();
	});

});