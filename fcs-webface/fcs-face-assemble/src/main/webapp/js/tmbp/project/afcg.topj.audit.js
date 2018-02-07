define(function(require, exports, module) {

	require('Y-imageplayer');
	require('zyw/upAttachModify');
	var util = require('util');

	if (document.getElementById('auditForm')) {
		// 审核通用部分 start
		var auditProject = require('/js/tmbp/auditProject');
		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');
	}

    // ------ 侧边栏 start
    var publicOPN = new (require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    // ------ 侧边栏 end.

    $('#fnPrint').click(function (event) {
        var $fnPrintBox = $('#div_print')
        $fnPrintBox.find('.ui-btn-submit').remove()
        $fnPrintBox.find('.printshow').removeClass('fn-hide')
        util.print($fnPrintBox.html())
    })
	//
	// ------ 查看图片 start
	$('.fnAttachView1').on('click', function() {
		// 查看所有上传的附件
		var _imgs = [];

		$('.fnAttachView1').each(function(index, el) {
			var _this = $(this);
			_imgs.push({
				src : _this.parent().attr('val'),
				desc : _this.text()
			});
		});

		Y.create('ImagePlayer', {
			imgs : _imgs,
			index : $(this).index('.fnAttachView1'),
			scaleLowerLimit : 0.1,
			loop : false,
			fullAble : false,
			firstTip : '这是第一张了',
			firstTip : '这是最后一张了'
		}).show();
	});
	// ------ 查看图片 end

	// 上传文件后
	$(".fnUpAttachVal").on("blur", function() {
		var _this = $(this), attach = _this.val(), bizNo = _this.attr("bizNo"), childId = _this.attr("childId"), moduleType = _this.attr("moduleType");

		util.ajax({
			url : '/baseDataLoad/saveAttach.json',
			data : {
				"attach" : attach,
				"bizNo" : bizNo,
				"childId" : childId,
				"moduleType" : moduleType
			},
			success : function(res) {
				if (res.success) {
					// window.location.reload(true);
				} else {
					Y.alert('提醒', res.message, function() {
						window.location.reload(true);
					});
				}
			}
		});
	});
});