define(function(require, exports, module) {
	function toIndex() {
		var _step = $('#step'),
			_toIndex = _step.attr('toIndex');
		_step.find('li').eq(_toIndex).addClass('active')
		if (_step.attr('version') == "false" && _toIndex == 4) {
			_step.find('li').eq(2).addClass('active')
		}
	}
	module.exports = toIndex()
})