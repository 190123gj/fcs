define(function(require, exports, module) {

	require('zyw/publicPage');

	$('.screen a').click(function(event) {

		var $this, NewDate, Time, toTime, val, Day, $toTime, $DayTime;

		$this = $(this);
		val = $this.attr('code');
		NewDate = new Date();
		DayTime = NewDate.getFullYear() + '-' + (NewDate.getMonth() + 1) + '-' + NewDate.getDate();
		$toTime = $('.toTime');
		$DayTime = $('.DayTime');

		if (val == 'day') {

			Time = NewDate.getTime() + (7 - 1) * 24 * 3600 * 1000;
			toTime = new Date(Time).toLocaleString().substring(0, 10).replace(/\//g, '-');

		} else if (val == 'week') {

			Time = NewDate.getTime() + (28 - 1) * 24 * 3600 * 1000;
			toTime = new Date(Time).toLocaleString().substring(0, 10).replace(/\//g, '-');

		} else if (val == 'month') {

			var getMonthVal, getFullYearVal;

			if (NewDate.getMonth() + 7 > 12) {

				getMonthVal = NewDate.getMonth() - 5;
				getFullYearVal = NewDate.getFullYear() + 1;

			} else {

				getMonthVal = NewDate.getMonth() + 7;
				getFullYearVal = NewDate.getFullYear();

			}

			toTime = getFullYearVal + '-' + getMonthVal + '-' + NewDate.getDate();

		} else if (val == 'quarter') {
			// 闰年怎么办
			toTime = (NewDate.getFullYear() + 1) + '-' + NewDate.getMonth() + '-' + NewDate.getDate();

		}

		$toTime.val(toTime);
		$DayTime.val(DayTime);

		$this.nextAll('input').val($this.attr('code'));

		$('form').submit();

	});

	var applyAddPopupCommom = require('zyw/fund/fme.applyAddPopupCommom')

	applyAddPopupCommom({
		formSource : '/baseDataLoad/receiptForm.json ',
		htmlSource : '/fundMg/receipt/apply/form.htm'
	});

	$('.showChangeDetail').on('click', function() {// 展示变化明细
		$this = $(this), dataId = $this.attr("dataId");
		$detailTr = $(".changeDetail[dataId=" + dataId + "]");
		if ($detailTr && $detailTr.length) {
			if ($detailTr.is(":visible")) {
				$detailTr.hide();
			} else {
				$detailTr.show();
			}
		}
	})
})