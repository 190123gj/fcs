define(function(require, exports, module) {

	var hintPopup = require('zyw/hintPopup');
	require('zyw/publicPage');

	$('.pageSelect').change(function(event) {
		$("#fnPageSize").val($(this).val())
		$('#fnListSearchBtn').click();
	});

	// // 分页公共操作
	// var util = require('util');

	// //删等操作
	// util.listOPN({
	// 	start: {
	// 		url: '/fundMg/bankMessage/list.htm?bankId=$!item.bankId&status=NORMAL',
	// 		message: '已启用',
	// 		opn: '启用'
	// 	},
	// 	stop: {
	// 		url: '/fundMg/bankMessage/list.htm?bankId=$!item.bankId&status=BLOCK_UP',
	// 		message: '已停用',
	// 		opn: '停用'
	// 	}
	// }, 'bankId');

	$('#list').on('click', '.fnOPNnew', function() {

		var $this, opn, code;

		$this = $(this);
		opn = $this.attr('opn');
		code = $this.attr('code');
		message = $this.attr('message');

		Y.confirm('信息提醒', '您确定要' + opn + '该银行账户' + '？', function(opn) {
			if (opn == 'yes') {
				//var _data = {};
				$.ajax({
					url: '/fundMg/bankMessage/changeStatus.htm?bankId=' + code,
					// data: {
					// 	_SYSNAME: _this.attr('sysname') || '',
					// },
					success: function(res) {
						if (res.success) {
							Y.alert('消息提醒', message);
							window.location.reload(true);
						} else {
							Y.alert('消息提醒', res.message);
						}
					}
				});
			}
		});
	});

	//导出

	$('#dataExport').click(function(event) {

		window.location.href = '/fundMg/bankMessage/excelDownLoad.htm?' + $('#fnListSearchForm').serialize();

	});


	//上传
	require('Y-htmluploadify');
	//导入
	$('#dataImport').click(function(event) {

		var htmlupload = Y.create('HtmlUploadify', {
			key: 'up1',
			uploader: '/fundMg/bankMessage/excelParse.htm',
			multi: false,
			auto: true,
			addAble: false,
			fileTypeExts: '*.xls',
			fileObjName: 'UploadFile',
			onQueueComplete: function(a, b) {},
			onUploadSuccess: function($this, data, resfile) {

				var jsonData = JSON.parse(data);


				hintPopup(jsonData.message, function() {

					if (jsonData.success) {

						// window.location.href = window.location.href;
						$('#fnListSearchBtn').click();

					}

				});

			}

		});

	});


})