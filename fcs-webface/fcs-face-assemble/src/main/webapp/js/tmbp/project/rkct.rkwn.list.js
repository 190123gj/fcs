define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请 列表
	require('zyw/publicPage');
	require('Y-msg');

	var util = require('util');

	var customerName;

	$('.fnNewBtn').on('click', function() {

		var customerName = $(this).parents('tr').attr('customer');
		window.location.href = '/projectMg/riskHandle/edit.htm?customerName=' + customerName+"&date="+new Date().getTime();
	});
	$('.fnClearBtn').on('click', function() {

		formid = $(this).parents('tr').attr('formid');
		window.location.href='clearEdit.htm?formId='+formid+"&date="+new Date().getTime();
		
	});
	$('.fnToMeeting').on('click', function(e) {
		e.preventDefault();
		var _param;
		if (this.href.indexOf('?') >= 0) {
			_param = '&'
		} else {
			_param = '?'
		}

		util.direct2param(this.href + _param + 'customerName=' + customerName,'/projectMg/riskHandle/list.htm');
		// window.location.href = this.href + _param + 'customerName=' + customerName;

	});
});