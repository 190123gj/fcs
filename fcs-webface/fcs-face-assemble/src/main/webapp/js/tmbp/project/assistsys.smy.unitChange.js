define(function(require, exports, module) {

	//单位改变验证
	var _unitAll = {
		PERCENT: {
			isPercentTwoDigits: true,
			messages: {
				isPercentTwoDigits: '请输入100.00以内的数字'
			}
		},
		AMOUNT: {
			isMoney: true,
			messages: {
				isMoney: '请输入整数位14位以内，小数位2位以内的数字'
			}
		},
		rest: {
			isMoneyMillion: true,
			messages: {
				isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
			}
		}
	}
	$('body').on('change','.changePrev',function() {

		var _this = $(this),
			_val = _this.val(),
			_prev = _this.parent().prev('td').find('input').length ? _this.parent().prev('td').find('input') : _this.prevAll('input.changePrevTarget');

		for (i in _unitAll) {
			for (j in _unitAll[i]) {
				if (j != 'messages') {
					_prev.rules('remove', j);
				}
			}
		}

		_prev.rules('add', _unitAll[_val]);
		_prev.blur();

	}).trigger('change');

});