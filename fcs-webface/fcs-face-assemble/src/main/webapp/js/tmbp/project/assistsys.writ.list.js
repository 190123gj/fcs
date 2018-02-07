define(function(require, exports, module) {
	//辅助系统 > 文书审核 > 文书申请审核列表

	require('Y-htmluploadify');
	require('zyw/publicPage');
	require('Y-msg');

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

	// 申请用印
	$('#fnApplySeal').on('click', function() {

		var $checked = $('.fnCheck:checked'),
			_checkIds = [],
			_checkIdMark,
			_checkIdPass = true;

		$checked.each(function(index, el) {

			if (index == 0) {
				_checkIdMark = el.getAttribute('projectcode');
			} else {

				if (_checkIdMark != el.getAttribute('projectcode')) {
					_checkIdPass = false;
					return false;
				}

			}

			_checkIds.push(el.value);

		});

		if (!_checkIdPass) {
			Y.alert('提示', '请选择同一个项目的文书');
			return;
		}

		if (_checkIds.length == 0) {
			Y.alert('提示', '请选择文书');
			return;
		}

		util.direct('/projectMg/stampapply/addStampApply.htm?writIds=' + _checkIds.join(','), '/projectMg/stampapply/addStampApply.htm');

	});

	// 回传文书
	$('.fnPassBack').on('click', function() {

		var self = this;

		var htmlupload = Y.create('HtmlUploadify', {
			key: 'up1',
			uploader: '/upload/imagesUpload.htm',
			multi: false,
			auto: true,
			addAble: false,
			fileTypeExts: util.fileType.all,
			fileObjName: 'UploadFile',
			onQueueComplete: function(a, b) {},
			onUploadSuccess: function($this, data, resfile) {

				var _res = $.parseJSON(data);
				if (_res.success) {

					var _file = [_res.data.oldFileName, _res.data.serverPath, _res.data.url].join(',');

					util.ajax({
						url: '/projectMg/contract/saveContractBack.htm',
						data: {
							contractScanUrl: _file,
							id: self.getAttribute('backid')
						},
						success: function(res) {
							if (res.success) {

								Y.alert('提示', '已保存', function() {
									window.location.reload(true);
								});

							} else {
								Y.alert('提示', res.message);
							}
						}
					});

				} else {
					Y.alert('提示', '上传失败');
				}

			},
			renderTo: 'body'
		});

	});


});