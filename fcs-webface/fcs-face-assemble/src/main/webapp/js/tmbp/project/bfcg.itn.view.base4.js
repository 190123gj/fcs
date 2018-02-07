define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //js引擎
    var template = require('arttemplate');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    $('.popup').click(function() {
        var _this = $(this),
            _parents = _this.parents('tr'),
            dataid = _parents.attr('dataid');
        $.ajax({
                url: '/projectMg/investigation/findFInvestigationFinancialReviewData.htm',
                type: 'post',
                dataType: 'json',
                async: false,
                data: {
                    id: dataid
                },
                success: function(res) {
                    _post = res;
                }
            })
            //弹窗
        $('body').Y('Window', {
            content: template('newPopupScript', _post),
            modal: true,
            key: 'modalwnd',
            simple: true
        });
        var modalwnd = Y.getCmp('modalwnd');
        modalwnd.bind(modalwnd.wnd.find('.close'), 'click', function() {
            modalwnd.close();
        });
    });

    //收起
    $('.packBtn').click(function(event) {
        var _this = $(this);
        _this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
        _this.parent().next().slideToggle();
    });


});