define(function(require, exports, module) {
	// 项目管理 > 辅助系统 > 会议类型
	require('zyw/publicPage');
	require('Y-msg');

	var util = require('util');

	//操作相似的ajax请求，合并数据
	var ajaxObj = {
		del: {
			url: '/projectMg/meetingMg/delete.htm',
			message: '已删除',
			opn: '删除'
		}
	};

	$('#list').on('click', '.del', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');
		var _thisType = util.getJqHasClass(_this, ajaxObj);
		Y.confirm('信息提醒', '您确定要' + ajaxObj[_thisType].opn + _this.parents('tr').attr('formname') + '？', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url: ajaxObj[_thisType].url,
					data: {
						typeId: _this.parents('tr').attr('formid')
					},
					dataType: 'json',
					type: 'POST',
					success: function(res) {
						if (res.success) {
							Y.alert('消息提醒', ajaxObj[_thisType].message);
							window.location.reload(true);
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
	});
});