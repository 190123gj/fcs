define(function(require, exports, module) {
	// 项目管理>授信前管理> 立项申请 列表
	require('zyw/publicPage');

	require('Y-imageplayer');
	require('zyw/upAttachModify');

	$(".fnShowEndFile").on("click", function() {
		var _this = $(this);
		$(".fnShowEndFileBox").addClass("fn-hide");
		_this.siblings(".fnShowEndFileBox").removeClass("fn-hide");
	});

	$('.close').on('click', function() {
		$(this).parents(".fnShowEndFileBox").addClass("fn-hide");
	})
});