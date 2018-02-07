define(function(require, exports, module) {
	require('Y-window');
    $('body').on('click','.newPopupBtn',function(){
        $('body').Y('Window', {
            content: $('#newPopupScript').html(),
            modal: true,
            key: 'modalwnd',
            simple: true
        });
        var modalwnd = Y.getCmp('modalwnd'),
            callbacks = $.Callbacks();
        modalwnd.bind(modalwnd.wnd.find('.close'),'click',function(){
            modalwnd.close();
        })
    })
});