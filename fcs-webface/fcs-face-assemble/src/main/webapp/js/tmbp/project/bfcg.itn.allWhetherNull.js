define(function(require) {
	$.fn.extend({
		allWhetherNull:function(exp,_reverse){
			var _rules = true,
				_reverses = _reverse?_reverse:false;
			if (exp && !$.isArray(exp)) exp = exp.split(',');
			this.find('input,textarea').each(function() {
				var _this = $(this),
					name = _this.attr('name'),
					_WhetherToReverse = _reverse?($.inArray(name, exp) != -1):!($.inArray(name, exp) != -1);
				if (exp) {
					if (_WhetherToReverse) return;
				};
				_rules = _rules&&_this.val()?true:false
				console.log(_this.attr('name')+"------"+_this.attr('class')+"---------"+_this.val()+"-------"+_rules)
			});
			//console.log(_rules)
			return _rules;
		}
	});

});