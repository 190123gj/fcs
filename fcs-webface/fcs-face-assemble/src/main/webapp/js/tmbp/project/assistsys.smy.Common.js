define(function(require, exports, module) {
	$.extend({
		smyCommon: function(_reverse) {
			//页面结构变化
			$('body').on('change', '.selectTime', function() {

				var _this = $(this),
					_index = _this.find(':selected').index(),
					_obj = _this.parents('.selectTimeParent').next('ul').find('li'),
					_addition = _this.parent().nextAll('.addition');

				_obj.eq(_index).find('.fnSelectTimeUlLi').show().end().find('input,select').each($.repairName).end()
					.siblings().find('.fnSelectTimeUlLi').hide().end().find('input,select').each($.destroyName);
				if (_addition.hasClass('addition'))(_index == 1) ? _addition.show() : _addition.hide();
				$('.checkboxTime').change();

			}).find('.selectTime').trigger('change').end()


			.on('change', '.checkboxTime', function() {

				var _this = $(this),
					_next = _this.parent().next(),
					_checkboxTimeObj = $('.checkboxTimeObj'),
					_judge = _reverse ? (!(_this.attr('checked') == 'checked')) : (_this.attr('checked') == 'checked');

				if ($('.selectTime').val() == 'ONE') return false;

				_judge ?
					_checkboxTimeObj.show().find('input,select').each($.repairName).end()
					.siblings().hide().find('input,select').each($.destroyName) :
					_checkboxTimeObj.hide().find('input,select').each($.destroyName).end()
					.siblings().show().find('input,select').each($.repairName);

				_judge ? _next.show() : _next.hide();

			}).find('.checkboxTime').trigger('change');
		},
		destroyName: function(index, el) {
			var _this = $(el),
				_name = _this.attr('name');
			_this.attr('name', _name + '_destroyName')
				.addClass('cancel');
		},
		repairName: function(index, el) {
			var _this = $(el),
				_name = _this.attr('name');
			_this.attr('name', _name.replace(/_destroyName/g, ''))
				.removeClass('cancel');;
		},
		destroyOrderName: function(index, el) {
			var _this = $(el),
				_orderName = _this.attr('orderName');
			_this.attr('orderName', _orderName + '_destroyName');
		},
		repairOrderName: function(index, el) {
			var _this = $(el),
				_orderName = _this.attr('orderName');
			_this.attr('orderName', _orderName.replace(/_destroyName/g, ''));
		},
	});
	
	//只编辑授信条件
	var $editCredit = $("#editCredit");
	if($editCredit && $editCredit.length && $editCredit.val() == "true"){
		$creditDiv = $("#creditDiv");
		$("#creditDiv").find("[name]").addClass("editCredit");
		$("#auditForm").find("[name]").addClass("editCredit");
		$("#creditDiv").find(".ui-btn").addClass("editCredit");
		$("#auditForm").find(".ui-btn").addClass("editCredit");
		$("[name]").not(".editCredit").attr("disabled",true).addClass("disabled");
		$(".ui-btn").not(".editCredit").remove();
		setTimeout(function () {
			$('textarea').each(function () {
				if($(this).attr('name') != 'other') {
                    $(this).prev().find('iframe').contents().find('body[contenteditable="true"]').attr('contenteditable',false).css('background-color','#ebebe4');
				}
            })
            $('[trigger="fulfilSubmit"]').hide();
            $('#loanPurpose select').attr('disabled', true).addClass("disabled");
            if($creditDiv && $creditDiv.length)
            	$('#auditSubmitBtn .fn-pt10.fn-pb20').append('<input id="newSave" class="ui-btn ui-btn-fill ui-btn-green fn-ml15 fnAuditBtn editCredit" type="button" value="保存">')
            $('#newSave').click(function () {
                $("[name]").not(".editCredit").removeAttr("disabled").removeClass("disabled");
                $('#loanPurpose select').removeAttr('disabled', true).addClass("disabled");
				$('[trigger="temporarStorage"]').trigger('click')
            })
		},500);
	}
	
	 require.async('tmbp/upAttachModify');
	 // var util = require('util');
  //   if (!!util.browserType() && util.browserType() < 10) { //IE9及以下不支持多选，加载单选上传
  //       require.async('tmbp/upAttachModify'); //上传
  //       console.log(1)
  //   } else {
  //       require.async('tmbp/upAttachModifyNew'); //上传
  //       console.log(2)
  //   }
});