define(function(require, exports, module) {
    //Y对话框
    require('Y-msg');
    //上传图片
    require('Y-htmluploadify');//------ 上传 start
	//通用方法
	var util = require('util');
	require('Y-window');

	//------ 上传 start
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
	//------ 上传 end

	//------ 通知函及回执 start
	$('.fn-notice').click(function() {
        $('body').Y('Window', {
            content: ['<div class="m-modal apply-org-new">',
                '    <div class="apply-org-hd">',
                '        <span class="fn-right fn-usn fn-csp apply-org-x fn-close" id="boxClose" href="javascript:void(0);">×</span>',
                '        <span id="boxTitle">请选择</span>',
                '    </div>',
                '    <div class="apply-org-body">',
				'		<ul class="fn-clear notice-ul">',
				'	        <li class="notice-li">',
				'	            <div class="notice-name">承担担保责任确认函</div>',
				'	            <a href="javascript:void(0)" class="lh32 green">已上传回执</a>',
				'	        </li>',
				'	        <li class="notice-li">',
				'	            <div class="notice-name">债务责任转移通知函</div>',
				'	            <a href="javascript:void(0)" class="lh32 green fnUpFile2"><input type="file" name="" id="">上传回执</a>',
				'	        </li>',
				'	        <li class="notice-li">',
				'	            <div class="notice-name">追偿通知函</div>',
                '               <a href="javascript:void(0)" class="lh32 green fnUpFile2"><input type="file" name="" id="">上传回执</a>',
				'	        </li>',
				'	        <li class="notice-li">',
				'	            <div class="notice-name">承担担保责任确认函</div>',
                '               <a href="javascript:void(0)" class="lh32 green fnUpFile2"><input type="file" name="" id="">上传回执</a>',
				'	        </li>',
				'	    </ul>',
                '        <div class="button fn-mt20 fn-tac">',
                '            <a class="ui-btn ui-btn-fill ui-btn-green fn-mr30 fn-close">打印</a>',
                '            <a class="ui-btn ui-btn-fill ui-btn-green fn-close">关闭</a>',
                '        </div>',
                '    </div>',
                '</div>'
            ].join(""),
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '#boxClose'
        });
        var wnd = Y.getCmp('modalwnd');
        wnd.bind(wnd.wnd.find('.fn-close'), 'click', function() {
            wnd.close();
        });
    });
	//------ 通知函及回执 end
});