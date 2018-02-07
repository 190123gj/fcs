define(function(require, exports, module) {
	
	require('zyw/publicPage');
	$('#printBtn').click(function(event) {
		window.print();
	});

	$('#selectChange').change(function(event) {

		$('form').submit();

	});



	$("#fnListExport").click(function() {
		var url = $(this).attr("exportUrl");
		url = url + "?" + $("#fnListSearchForm").serialize();
		window.location = url;
	});

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.addOPN([]).init().doRender();
	//------ 侧边栏 end

})