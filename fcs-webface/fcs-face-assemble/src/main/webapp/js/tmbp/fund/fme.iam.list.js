define(function(require, exports, module) {

	require('zyw/publicPage');

	$('.pageSelect').change(function(event) {
		$("#fnPageSize").val($(this).val())
		$('#fnListSearchBtn').click();
	});

	var util = require('util');

	require('validate');

	var yearsTime = require('zyw/yearsTime');

	$('#time').click(function(event) {
		var yearsTimeFirst = new yearsTime({
			elem : '#time',
		// callback: function(_this, _time) {
		// _this.val('报告期(' + _time[0] + '年' + _time[1] + '月)');
		// $('[name="productStructures[0].reportPeriod2"]').val('上一年（' +
		// (_time[0] - 1) + '年）');
		// $('[name="productStructures[0].reportPeriod3"]').val('上二年（' +
		// (_time[0] - 2) + '年）');
		// }
		});
	});
	// 授信类型

	var getTypesOfCredit = require('zyw/getTypesOfCredit');
	var _getTypesOfCredit = new getTypesOfCredit();
	_getTypesOfCredit.init({
		chooseAll : true,
		btn : '#businessTypeBtn',
		name : '#businessTypeName',
		code : '#businessTypeCode'
	});

	// 批量确认 /fundMg/incomeConfirm/batchConfirm.json

	(function() {
		var $list = $('#list'), $checkAll = $('#fnListSearchTh input[type="checkbox"]'), $checkbox = $list.find('[ordername="confirmOrder"] input[type="checkbox"]:not("[disabled]")'), $modi = $list.find('.modify')

		$checkbox.attr('data-checked', 'false')
		// 单个绑定
		$checkbox.on('change', function() {
			triggerChecked($(this))
			var $this = $(this), checked = $this.prop('checked'), bro = $this.parents('tr').siblings('[ordername="confirmOrder"]').find('input[data-checked ="false"]');
			$checkAll.prop('checked', checked && !bro.length)
		})

		$checkAll.on('change', function() {
			var checked = $(this).prop('checked')
			$checkbox.prop('checked', checked)
			triggerChecked($checkbox)
		})

		// 修改按钮
		$modi.on('click', function() {
			triggerChecked($(this).parents('tr').find('[type="checkbox"]'), $(this))
		})

		function triggerChecked(obj, trigger) {

			obj.each(function() {
				var $this = $(this), checked = $this.prop('checked'), $input = $this.parents('tr').find('.fnInput'), $text = $input.siblings('span'), money = parseFloat(($this.parents('tr').find('.money').text().replace('元', '')).replace(/\,/g, ''))

				if (trigger && trigger.hasClass('onModi')) {
					$input.addClass('fn-hide')
					$text.removeClass('fn-hide')
					$input.siblings('.error-tip').hide()
					$input.val($input.attr('value'))
					trigger.removeClass('onModi').text('[ 修改 ]')
					$this.removeAttr('data-checked')
					return

				}
				$this.attr('data-checked', checked)
				if (checked) {
					$input.removeClass('fn-hide')
					$text.addClass('fn-hide')
					if ($input.siblings('.error-tip').length > 0) {
						$input.siblings('.error-tip').show()
					}
					(money > 0 && !trigger) ? $input.val(money).trigger('blur') : ''
					trigger ? trigger.addClass('onModi').text('[ 取消 ]') : ''
				} else {
					$input.addClass('fn-hide')
					$text.removeClass('fn-hide')
					$input.siblings('.error-tip').hide()
				}
			})

		}

		// fnBatchConfirm

		var requiredRules = {
			rules : {},
			messages : {}
		};

		util.setValidateRequired($('.fnInput'), requiredRules)
		var hintPopup = require('zyw/hintPopup');
		Y.Msg.defaults = $.extend({}, Y.Window.defaults, {
			type : 'alert',
			title : '',
			simple : false,
			closeAble : true,
			yesClass : 'base-btn base-btn-green',
			noClass : 'base-btn base-btn-gray',
			msgClass : 'wnd-msg-msg',
			width : 560,
			autoSize : true,
			bottomClass : 'wnd-msg-bottom',
			bodyStyle : {
				'text-align' : 'center'
			}
		});
		$('#fnListSearchForm').validate({
			rules : requiredRules.rules,
			messages : requiredRules.messages,
			submitHandler : function() {
				util.ajax({
					url : '/fundMg/incomeConfirm/batchConfirm.json',
					data : $('#fnListSearchForm').serializeJCK(),
					success : function(data) {
						$('.wnd-wnd,.wnd-body,.wnd-header').css('width', '450px')
						hintPopup(data.message, function() {

							if (data.success) {
								window.location.href = window.location.href;
							}
							$('.m-modal-box').remove();
						});
					}
				})
			},
			errorClass : 'error-tip',
			errorElement : 'p'
		})

		$('.fnBatchConfirm').on('click', function() {
			var bro = $list.find('[ordername="confirmOrder"] input[data-checked ="true"]')
			if (!bro.length) {
				Y.alert('提示', '请至少选择一项进行确认')
			} else {
				$('#fnListSearchForm').submit()
			}
		})

	})()

	// 合并相同列
	var $_tr, $_rowspan = 1, $_sameArr = [];
	$("#list").find("tr[orderName=confirmOrder]").on("mouseover", function() {
		$(this).addClass("active");
	}).on("mouseout", function() {
		$(this).removeClass("active");
	}).each(function(index, el) {
		$this = $(this), $_next = $this.next();
		if (!$_tr)
			$_tr = $this;
		if ($_tr.attr("incomeId") == $_next.attr("incomeId")) {// 相同项目
			$_rowspan++;
			$_sameArr.push($_next);
		} else {
			$_tr.find("td[incomeId]").attr("rowspan", $_rowspan);
			for (index in $_sameArr) {
				$_sameArr[index].find("td[incomeId]").hide();
			}
			$_sameArr = [];
			$_tr = false;
			$_rowspan = 1;
		}
	})

})