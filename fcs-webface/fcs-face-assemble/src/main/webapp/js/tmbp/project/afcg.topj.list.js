define(function(require, exports, module) {
	//项目管理 > 授信后管理  > 到期解保阶段项目列表
	require('zyw/publicPage');

	var util = require('util');

	//权利凭证出库 跳转
	$('.fnGoDAMX').on('click', function(e) {
		e.preventDefault();
		util.direct(this.href, '/projectMg/file/detailFileList.htm')
	});

});