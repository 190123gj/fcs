define(function(require, exports, module) {
	// 项目管理 > 授信前管理 > 承销/发债信息维护
	require('zyw/publicPage');
	require('Y-msg');

	var util = require('util');
	
	//操作相似的ajax请求，合并数据
	var ajaxObj = {
		stop: {
			url: '/projectMg/projectIssueInformation/stopSell.htm',
			message: '已停止发售',
			opn: '停止发售'
		}
	};

	$('#list').on('click', '.stop', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');
		var _thisType = util.getJqHasClass(_this, ajaxObj);
		Y.confirm('信息提醒', '您确定要' + ajaxObj[_thisType].opn + '该项目？', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url: ajaxObj[_thisType].url,
					data: {
						projectCode: _this.parents('tr').attr('itemid')
					},
					dataType: 'json',
					type: 'POST',
					success: function(res) {
						if (res.success) {
							Y.alert('消息提醒', ajaxObj[_thisType].message, function() {
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
	});
});