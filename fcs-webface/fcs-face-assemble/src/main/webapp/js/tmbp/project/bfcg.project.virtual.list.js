define(function(require, exports, module) {
	// 项目管理 > 项目列表
	require('Y-msg');

	require('zyw/publicPage');

	require('validate');

	var util = require('util');

	$(".delVirtual").on('click', function() {
		var $this = $(this), $tr = $this.parent().parent();
		var virtualId = $tr.attr('virtualId'), projectName = $tr.attr('projectName');
		var message = '';
		if (projectName) {
			message = '确定删除项目【' + projectName + '】？';
		} else {
			message = '确定删除项目？';
		}

		Y.confirm('提示', message, function(opn) {

			if (opn == 'yes') {
				util.ajax({
					url : '/projectMg/virtual/delete.json',
					data : {
						'virtualId' : virtualId
					},
					success : function(res) {
						Y.alert(res.success ? '操作成功' : '操作失败', res.message, function() {
							window.location.reload(true);
						});
					}
				});
			}
		});
	})
});