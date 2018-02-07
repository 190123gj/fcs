define(function(require, exports, module) {
	//通用方法
	var util = require('util');
    //模板引擎
    var template = require('arttemplate');

	//------ 确认用印 start
	$('.fn-confirm').click(function() {
        $('body').Y('Window', {
            content: ['<div class="m-modal apply-org-new">',
                '    <div class="apply-org-hd">',
                '        <span class="fn-right fn-usn fn-csp apply-org-x fn-close" id="boxClose" href="javascript:void(0);">×</span>',
                '        <span id="boxTitle">确认提醒</span>',
                '    </div>',
                '    <div class="apply-org-body fn-mt20">',
                '        <p>请确认对文件：XXXXXXXXXX 用印完成再来点击确认！</p>',
                '        <p>是否确认用印？</p>',
                '        <div class="button fn-mt20 fn-tac">',
                '            <a class="ui-btn ui-btn-fill ui-btn-green fn-mr30 fn-close">确认</a>',
                '            <a class="ui-btn ui-btn-fill ui-btn-green fn-close">取消</a>',
                '        </div>',
                '    </div>',
                '</div>'
            ].join(""),
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.fn-close'
        });
        var wnd = Y.getCmp('modalwnd');
        wnd.bind(wnd.wnd.find('.fn-close'), 'click', function() {
            wnd.close();
        });
    });
	//------ 确认用印 end
});