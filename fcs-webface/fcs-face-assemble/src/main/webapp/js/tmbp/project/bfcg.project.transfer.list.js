define(function(require, exports, module) {
	// 项目管理 > 项目列表
	require('Y-msg');
	
	require('zyw/publicPage');

	require('validate');

	var util = require('util');

	var getTypesOfCredit = require('zyw/getTypesOfCredit')

	// 授信类型
	var _getTypesOfCredit = new getTypesOfCredit();
	_getTypesOfCredit.init({
		chooseAll : true,
		btn : '#businessTypeBtn',
		name : '#businessTypeName',
		code : '#businessTypeCode'
	});
});