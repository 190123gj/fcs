define(function(require, exports, module) {


    $('.contrastTab span').css({
        'cursor':'pointer'
    })
    $('.contrastTab span').eq(0).css({
        'padding':'4px 40px',
        'font-size':'16px'
    })
    $('.contrastTab').on('click','span',function(){
        $('.contrastTabCon').hide()
        $('#contrastTab'+$(this).attr('id')).show();

        $(this).css({
            'padding':'4px 40px',
            'transition-duration':'0.3s',
            'font-size':'16px'
        }).siblings().css({
            'padding':'2px 30px',
            'transition-duration':'0.3s',
            'font-size':'14px'
        });



        var iframeid=$("#iframe"); //iframe id

        var _height = document.body.offsetHeight;

        if(window.parent.SetCwinHeight)
            window.parent.SetCwinHeight($('.m-main').outerHeight())



    })



});