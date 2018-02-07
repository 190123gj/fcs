define(function(require, exports, module) {
	$('.fndetail').on('click', function() {
		$('.audit-detail').slideDown();
	});
	$('.close-btn').on('click', function() {
		var self = $(this);
		self.parent('.audit-detail').slideUp();
	});
});