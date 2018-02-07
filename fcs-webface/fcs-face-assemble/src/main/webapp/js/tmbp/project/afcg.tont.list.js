define(function(require, exports, module) {
	//项目管理 > 授信后管理  > 到期项目列表
	require('zyw/publicPage');
	require('Y-msg');
	require('zyw/upAttachModify');

	var util = require('util');

	var $fnUpFileBox = $('#fnUpFileBox'),
		$fnUpFileForm = $fnUpFileBox.find('form'),
		$fnUpFileCode = $fnUpFileBox.find('.fnUpFileCode'),
		$fnUpFileItem = $fnUpFileBox.find('.fnUpFileItem'),
		$fnUpAttachUl = $fnUpFileBox.find('.fnUpAttachUl'),
		$fnUpAttachVal = $fnUpFileBox.find('.fnUpAttachVal'),
		$fnUpFileTitle = $fnUpFileBox.find('.fnUpFileTitle'),
		$fnUpFileProjectCode = $fnUpFileBox.find('.fnUpFileProjectCode');

	$('#list').on('click', '.fnUpReceipt', function() {

		var _this = $(this),
			_itemId = _this.parents('tr').attr('itemId'),
			_projectCode = _this.parents('tr').attr('projectCode'),
			_projectName = _this.parents('tr').attr('projectName'),
			_val = this.getAttribute('val').replace(/\s/g, ''),
			_isfinished = (this.getAttribute('isfinished').replace(/\s/g, '') == 'IS') ? true : false;
		// 根据 _isfinished 是否有值，判断上传还是查看
		if (_isfinished) {
			$fnUpFileItem.addClass('fn-hide');
		} else {
			$fnUpFileItem.removeClass('fn-hide');
			$fnUpFileCode.val(_itemId);
			$fnUpFileProjectCode.val(_projectCode);
			$fnUpAttachUl.html('');
		}

		// 根据 _val显示对应的名称
		if (_val) {
			var _html = '';
			$.each(_val.split(';'), function(index, el) {

				var _elArr = el.split(','),
					isImg = util.isImg(_elArr[0]),
					res = {
						fileName: _elArr[0],
						serverPath: _elArr[1],
						fileUrl: _elArr[2]
					};

				_html += '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '">' + (isImg ? ('<i class="icon icon-img"></i><span class="fnAttachView fn-csp">' + res.fileName + '</span>') : ('<i class="icon icon-file"></i><a href="" target="_blank">' + res.fileName + '</a>')) + (_isfinished ? '' : '<span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span>') + '</span>';

			});
			$fnUpAttachVal.val(_val);
			$fnUpFileTitle[0].innerHTML = '';
			// $fnUpFileTitle[0].innerHTML = '项目编号“' + _projectCode + '”，项目名称“' + _projectName + '”还未结束，请上传还款凭证';
		} else {
			$fnUpFileTitle[0].innerHTML = '您还未上传项目编号“' + _projectCode + '”，项目名称“' + _projectName + '”的还款凭证';
		}

		$fnUpAttachUl.html(_html);

		$fnUpFileBox.removeClass('fn-hide');

	}).on('click', '.fnGoOther', function(e) {
		e.preventDefault();
		util.direct2param(this.href, this.getAttribute('navurl'))
	});

	$('#fnGoXZJB').on('click', function(e) {
		e.preventDefault();
		util.direct2param(this.href, '/projectMg/counterGuarantee/list.htm')
	});


	$fnUpFileBox.on('click', '.close', function() {
		$fnUpFileBox.addClass('fn-hide');
	}).on('click', '.sure', function() {

		// 是否上传附件
		if (!!!$fnUpAttachUl.html().replace(/\s/g, '')) {
			Y.alert('提示', '请上传还款凭证')
			return;
		}

		util.ajax({
			url: $fnUpFileForm.attr('action'),
			data: $fnUpFileForm.serialize(),
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '操作成功', function() {
						window.location.reload(true);
					});
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

	});

	// $('#list').on('click', '.fnUpReceipt', function() {
	// 	/**
	// 	 * 上传回执 先上传附件，附件上传成功后再ajax
	// 	 */
	// 	var _this = $(this);
	// 	// 上传附件
	// 	var htmlupload = Y.create('HtmlUploadify', {
	// 		key: 'up1',
	// 		uploader: '/upload/imagesUpload.htm',
	// 		multi: false,
	// 		auto: true,
	// 		addAble: false,
	// 		fileTypeExts: util.fileType.all,
	// 		fileObjName: 'UploadFile',
	// 		onQueueComplete: function(a, b) {},
	// 		onUploadSuccess: function($this, res, resfile) {

	// 			var _res = $.parseJSON(res);

	// 			if (_res.success) {

	// 				ajaxPostReceipt(_this, _res.data.url);

	// 			} else {
	// 				Y.alert('提示', _res.message);
	// 			}
	// 		},
	// 		renderTo: 'body'
	// 	});
	// });

	/**
	 * Ajax更新项目回执信息
	 * @param  {[type]} $this     [当前'上传回执'的jQuery对象]
	 * @param  {[string]} fileUrl [附件地址]
	 */
	function ajaxPostReceipt($this, fileUrl) {

		util.ajax({
			url: '/projectMg/expireProject/uploandReceipt.json',
			data: {
				id: $this.parents('tr').attr('itemId'),
				repayCertificate: fileUrl
			},
			success: function(res) {
				if (res.success) {
					window.location.reload(true);
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

	}

});