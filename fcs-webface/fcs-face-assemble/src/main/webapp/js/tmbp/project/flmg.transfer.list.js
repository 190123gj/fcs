define(function(require, exports, module) {
	// 项目管理 > 理财项目管理 > 理财转让列表
	require('zyw/publicPage');
	require('Y-msg');
	var util = require('util');

	//操作相似的ajax请求，合并数据
	var ajaxObj = {
		confirm: {
			url: '/projectMg/financialProject/transfer/buyBack.htm',
			message: '已确认',
			opn: '回购'
		}
	};

	$('#list').on('click', '.confirm', function() {
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
						formId: _this.parents('tr').attr('formid'),
						applyId: _this.parents('tr').attr('itemId')
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
	})

});