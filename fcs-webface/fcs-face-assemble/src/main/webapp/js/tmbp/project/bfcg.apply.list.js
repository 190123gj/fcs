define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请 列表
	require('Y-msg');
	require('zyw/publicPage');

	var util = require('util');

	//授信类型

	var getTypesOfCredit = require('zyw/getTypesOfCredit');
	var _getTypesOfCredit = new getTypesOfCredit();
	_getTypesOfCredit.init({
		chooseAll: true,
		btn: '#businessTypeBtn',
		name: '#businessTypeName',
		code: '#businessTypeCode'
	});

	//尽职调查 跳转
	$('.fnGoJZDC').on('click', function(e) {
		e.preventDefault();
		util.direct(this.href, '/projectMg/investigation/list.htm')
	});

});