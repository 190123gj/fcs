define(function(require, exports, module) {
	//上传图片
	require('Y-htmluploadify');
	});

	//------ 上传附件 start
	$('.fnUpFile').click(function() {
		var _this = $(this);
		var htmlupload = Y.create('HtmlUploadify', {
			key: 'up1',
			uploader: '/upload.json1',
			multi: false,
			auto: true,
			addAble: false,
			fileTypeExts: '*.jpg; *.jpeg',
			fileObjName: 'UploadFile',
			onQueueComplete: function(a, b) {},
			onUploadSuccess: function($this, data, resfile) {
				_this.attr('src', data.xxx).parent().find('input.fnUpFileInput').val(data.xxx);
			},
			renderTo: 'body'
		});
		htmlupload.show();
	});
	//------ 上传附件 end