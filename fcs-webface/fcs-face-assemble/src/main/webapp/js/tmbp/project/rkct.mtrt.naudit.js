define(function (require, exports, module) {

	require('Y-imageplayer');

	var util = require('util')

	var pako = require('pako')

	$('body').on('click', '.fnAttachView', function () {
		//查看所有上传的附件
		var _imgs = [];

		$('.fnAttachView').each(function (index, el) {
			var _this = $(this);
			_imgs.push({
				src: _this.parent().attr('val'),
				desc: _this.text()
			});
		});

		Y.create('ImagePlayer', {
			imgs: _imgs,
			index: $(this).index('.fnAttachView'),
			scaleLowerLimit: 0.1,
			loop: false,
			fullAble: false,
			firstTip: '这是第一张了',
			firstTip: '这是最后一张了'
		}).show();

	});

	// 审核通用部分 start
	var auditProject = require('/js/tmbp/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();

	publicOPN.init().doRender();
	//------ 侧边栏 end

	$('#fnDoPrint').on('click', function () {

		var $div = $('#form')

		$div.find('.m-blank, .ui-btn-submit').remove()

		util.print('<div class="viewNotInsteadPay">' + $div.html() + '</div>')

	})

	$('#fnDoExport').on('click', function () {
		var $ex = $('#form')

		$ex.find('.ui-btn-submit').remove()

		$ex.find('.printshow').removeClass('fn-hide')

		util.css2style($ex)

		var __body = document.getElementsByTagName('body')[0]

		__body.innerHTML = ''
		__body.appendChild($ex[0])

		$('head link, script, .ui-tool').remove()

		//document.documentElement.outerHTML
		var deflate = pako.gzip(document.documentElement.outerHTML, {
			to: 'string'
		});
		//console.log(pako.ungzip(deflate,{ to: 'string' }));
		$.ajax({
			type: 'post',
			url: '/baseDataLoad/createDoc.json',
			data: {
				formId: util.getParam('formId'),
				type: 'RISK_HANDLE',
				decode: 'yes',
				htmlData: encodeURIComponent(deflate)
			},
			success: function (res) {
				if (res.success) {
					window.location.href = res.url;

					setTimeout(function() {
						window.location.reload(true)
					}, 1000)

				} else {
					alert(res.message)
					window.location.reload()
				}
			}
		})

	})

});