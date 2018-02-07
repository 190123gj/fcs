define(function(require, exports, module) {
	//项目管理 > 理财项目管理 > 理财申请列表
	require('zyw/publicPage');
	require('Y-msg');
	var util = require('util');

	//操作相似的ajax请求，合并数据
	var ajaxObj = {
		discontinued: {
			url: '/projectMg/basicmaintain/financialProduct/changeStatus.htm',
			message: '已停售',
			opn: '停售',
			status: 'STOP'
		},
		sale: {
			url: '/projectMg/basicmaintain/financialProduct/changeStatus.htm',
			message: '已开售',
			opn: '开售',
			status: 'SELLING'
		}
	};

	$('#list').on('click', '.discontinued,.sale', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');
		var _thisType = util.getJqHasClass(_this, ajaxObj);
		Y.confirm('信息提醒', '您确定要' + ajaxObj[_thisType].opn + _this.parents('tr').attr('itemname') + '？', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url: ajaxObj[_thisType].url,
					data: {
						productId: _this.parents('tr').attr('itemid'),
						status: ajaxObj[_thisType].status
					},
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
	})

});