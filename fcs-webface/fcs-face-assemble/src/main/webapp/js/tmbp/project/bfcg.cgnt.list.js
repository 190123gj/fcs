define(function(require, exports, module) {
	//收费通知
	require('zyw/publicPage');

	//授信类型
	$("#fnListExport").click(function() {
		var url = $(this).attr("exportUrl");
		url = url + "?" + $("#fnListSearchForm").serialize();
		window.location = url;
	});
	var getTypesOfCredit = require('zyw/getTypesOfCredit');
	var _getTypesOfCredit = new getTypesOfCredit();
	_getTypesOfCredit.init({
		chooseAll: true,
		btn: '#businessTypeBtn',
		name: '#businessTypeName',
		code: '#businessTypeCode'
	});

});