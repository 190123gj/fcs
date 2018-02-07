define(function(require, exports, module) {
	// 客户管理 > 渠道合同 > 渠道合同维护
	require('Y-msg');
	require('zyw/publicPage');
	require('Y-htmluploadify');

	var util = require('util');

	$('.fnUpFile').on('click', function() {

		var _formid = this.getAttribute('formid');

		Y.create('HtmlUploadify', {
			key: 'up1',
			uploader: '/upload/imagesUpload.htm',
			multi: false,
			auto: true,
			addAble: false,
			fileTypeExts: util.fileType.all,
			fileObjName: 'UploadFile',
			onQueueComplete: function(a, b) {},
			onUploadSuccess: function($this, res, resfile) {
				var _res = $.parseJSON(res);
				if (_res.success) {

					var _url = _res.data.oldFileName + ',' + _res.data.serverPath + ',' + _res.data.url;

					util.ajax({
						url: '/customerMg/channalContract/returnContract.json',
						data: {
							formId: _formid,
							contractReturn: _url
						},
						success: function(res) {

							Y.alert('提示', res.success ? '操作成功' : res.message, function() {
								window.location.reload(true);
							});

						}
					});

				}
			},
			onUploadError: function($this, file) {
				Y.alert('提示', '上传图片失败', function() {
					window.location.reload(true);
				});
			},
			renderTo: 'body'
		});

	});

});