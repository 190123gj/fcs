define(function(require, exports, module) {
	// 项目管理 > 风险管控 > 上会申报记录
	require('zyw/publicPage');
	require('Y-msg');
	var util = require('util');

	$('#list').on('click', '.end', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');
		var councilId = _this.attr("councilId");
		Y.confirm('信息提醒', '针对本次会议，您操作了会议结束，是否确认？', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url: "/projectMg/councilRisk/end.json",
					data: {
						councilId: councilId
					},
					success: function(res) {
						if (res.success) {
							Y.alert('消息提醒', res.message, function() {
								window.location.reload(true);
							});
						} else {
							_this.removeClass('ing');
							Y.alert('消息提醒', res.message);
						}
					}
				});
			} else {
				_this.removeClass('ing');
			}
		});
	})

});