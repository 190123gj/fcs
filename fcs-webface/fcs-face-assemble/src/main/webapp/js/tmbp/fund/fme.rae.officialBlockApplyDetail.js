define(function(require, exports, module) {
	require('zyw/publicPage');
	//搜索框时间限制
	$('body').on('blur', '.fnListSearchDateS', function() {

		var $p = $(this).parents('.fnListSearchDateItem'),
			$input = $p.find('.fnListSearchDateE');

		$input.attr('onclick', 'laydate({min: "' + this.value + '"})');

	}).on('blur', '.fnListSearchDateE', function() {

		var $p = $(this).parents('.fnListSearchDateItem'),
			$input = $p.find('.fnListSearchDateS');

		$input.attr('onclick', 'laydate({max: "' + this.value + '"})');

	}).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');
	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.addOPN([]).init().doRender();
	//------ 侧边栏 end

	$("#fnListExport").click(function() {
		var url = $(this).attr("exportUrl");
		url = url + "?" + $("#fnListSearchForm").serialize();
		window.location = url;
	});

})