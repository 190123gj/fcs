define(function(require, exports, module) {

	// 项目签报 > 表单签报 > 项目批复签报 查看
	
	require('zyw/upAttachModify');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();
    $('#fnFooterGoBack').parents('.ui-tool').css('zIndex','99');

	$('#fnOldReport').append(decodeURIComponent($('[name="originalPageContent"]').html()));
	$('#fnNewReport').append(decodeURIComponent($('[name="pageContent"]').html()));

	$('body').on('click', '.m-change-view', function() {

		$(this).addClass('active').siblings().removeClass('active')

	});

});