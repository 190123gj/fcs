define(function(require, exports, module) {

	require('zyw/publicPage')

	$('.pageSelect').change(function(event) {
		$("#fnPageSize").val($(this).val())
		$('#fnListSearchBtn').click();
	});

})