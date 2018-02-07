define(function(require, exports, module) {
	require('input.limit');
	require('zyw/publicPage');
	var util = require('util');

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

			toTime = (NewDate.getFullYear() + 1) + '-' + NewDate.getMonth() + '-' + NewDate.getDate();

		}

		$toTime.val(toTime);
		$DayTime.val(DayTime);

		$this.nextAll('input').val($this.attr('code'));

		$('#fnListSearchBtn').click();

	});

	$('body').on('click', '.changeMoney', function(event) {

		if (event.stopPropagation)
			event.stopPropagation();

		var $this, $target;

		$this = $(this);
		$target = $this.parents('tr').find('.replaceSpan');

		$target.each(function(index, el) {

			var $this;

			$this = $(this);

			$this.siblings('input').val($this.text().replace(/\,/g, ''))

		});

		$target.hide().siblings('input').removeClass('cancel');
		$target.hide().siblings('input').show();
		$this.hide().nextAll().show();

		return false;
	}).on('click', '.showChangeDetail', function() {// 展示变化明细
		$this = $(this), dataId = $this.attr("dataId");
		$detailTr = $(".changeDetail[dataId=" + dataId + "]");
		if ($detailTr && $detailTr.length) {
			if ($detailTr.is(":visible")) {
				$detailTr.hide();
			} else {
				$detailTr.show();
			}
		}
	});

	$("#list .del").click(function(event) {

		if (event.stopPropagation)
			event.stopPropagation();

		var $this = $(this);
		$this.hide().siblings().hide().eq(0).show();
		$this.parents('tr').find('input').hide().nextAll().show();

		return false;
	});

	$("#list .submit").click(function() {
		var $this = $(this), $tr = $this.parents('tr');
		var id = $tr.attr("dataId"), time = $tr.find('input[name=uselessTime]').val(), amount = $tr.find('input[name=uselessAmount]').val();

		var _isPass = true, _isPassEq;

		$tr.find('input.text').each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false
				_isPassEq = index
				return false;
			}
		});

		if (!_isPass) {
			Y.alert('提示', '请填写完整修改数据', function() {
				$tr.find('input.text').eq(_isPassEq).focus();
			})
			return;
		}

		var fnMakeMoneyfnMakeMicrometer = $('.fnMakeMoney.fnMakeMicrometer');

		fnMakeMoneyfnMakeMicrometer.each(function(index, el) {
			var $el = $(el);
			$el.val($el.val().replace(/\,/g, ''))
		});
		// TODO 验证 time amount
		util.ajax({
			url : "/fundMg/forecast/forecastAccountModify.htm",
			data : {
				"forecastId" : id,
				"forecastAmount" : amount,
				"forecastTime" : time
			},
			success : function(res) {
				if (res.success) {
					window.location.reload(true);
				} else {
					Y.alert('操作失败', res.message);
				}
			}
		});
	});

	/***************************************************************************
	 * var hintPopup = require('zyw/hintPopup'); //必填集合
	 * require('zyw/requiredRules'); var _form = $('#fnListSearchForm'),
	 * _allWhetherNull = { stringObj: 'uselessTime,uselessAmount', boll: false },
	 * requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'],
	 * _allWhetherNull['boll'], {}, function(rules, name) { rules[name] = {
	 * required: true, messages: { required: '必填' } }; }), maxlength50Rules =
	 * _form.requiredRulesSharp(_allWhetherNull['stringObj'],
	 * _allWhetherNull['boll'], {}, function(rules, name) { rules[name] = {
	 * maxlength: 50, messages: { maxlength: '已超出50字' } }; }), rulesAllBefore = {
	 * ssss: { isMoneyMillion: true, messages: { isMoneyMillion:
	 * '请输入整数位12位以内，小数位2位以内的数字' } } }, _rulesAll = $.extend(true,
	 * maxlength50Rules, requiredRules, rulesAllBefore), submitValidataCommonUps =
	 * require('zyw/submitValidataCommonUp');
	 * 
	 * submitValidataCommonUps.submitValidataCommonUp({
	 * 
	 * form: _form, //form allWhetherNull: _allWhetherNull, //必填集合与是否反向判断
	 * rulesAll: _rulesAll, //验证全集 allEvent: { // replaceContentBtn: true,
	 * //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置 // replaceBroadsideBtn:
	 * true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置 contentBtn: [{ clickObj:
	 * '.submit', eventFun: function(jsonObj) {
	 * 
	 * console.log(jsonObj);
	 * 
	 * $.fn.whetherMust(jsonObj.objList['rulesAll'],
	 * true).allAddValidata(jsonObj.objList['rulesAll']); //是必填
	 * 
	 * jsonObj.portInitVal['submitHandlerContent'] = { // validateData: { //
	 * checkStatus: jsonObj.setInitVal['checkStatus'] // }, submitStatus:
	 * 'submit', fm5: jsonObj.setInitVal['fm5'] };
	 * //向validate文件里的submitHandler暴露数据接口
	 * 
	 * jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据
	 * 
	 * //jsonObj.objList['form'].submit(); //提交 } } // , { // clickObj: '.screen
	 * a', // eventFun: function(jsonObj) { // var $this, NewDate, Time, toTime,
	 * val, Day, $toTime, $DayTime; // $this = $(jsonObj.self); // val =
	 * $this.attr('code'); // NewDate = new Date(); // DayTime =
	 * NewDate.getFullYear() + '-' + NewDate.getMonth() + '-' +
	 * NewDate.getDate(); // $toTime = $('.toTime'); // $DayTime =
	 * $('.DayTime'); // if (val == 'day') { // Time = NewDate.getTime() + 7 *
	 * 24 * 3600 * 1000; // toTime = new
	 * Date(Time).toLocaleString().substring(0, 10).replace(/\//g, '-'); // }
	 * else if (val == 'week') { // Time = NewDate.getTime() + 28 * 24 * 3600 *
	 * 1000; // toTime = new Date(Time).toLocaleString().substring(0,
	 * 10).replace(/\//g, '-'); // } else if (val == 'month') { // var
	 * getMonthVal, getFullYearVal; // if (NewDate.getMonth() + 7 > 12) { //
	 * getMonthVal = NewDate.getMonth() - 5; // getFullYearVal =
	 * NewDate.getFullYear() + 1; // } else { // getMonthVal =
	 * NewDate.getMonth() + 7; // getFullYearVal = NewDate.getFullYear(); // } //
	 * toTime = getFullYearVal + '-' + getMonthVal + '-' + NewDate.getDate(); // }
	 * else if (val == 'quarter') { // toTime = (NewDate.getFullYear() + 1) +
	 * '-' + NewDate.getMonth() + '-' + NewDate.getDate(); // } //
	 * $toTime.val(toTime); // $DayTime.val(DayTime); //
	 * $.fn.whetherMust(jsonObj.objList['rulesAll'],
	 * true).allAddValidata(jsonObj.objList['rulesAll']); //是必填 //
	 * jsonObj.portInitVal['submitHandlerContent'] = { // // validateData: { // //
	 * checkStatus: jsonObj.setInitVal['checkStatus'] // // }, // submitStatus:
	 * 'screen', // fm5: jsonObj.setInitVal['fm5'] // };
	 * //向validate文件里的submitHandler暴露数据接口 //
	 * jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据 //
	 * jsonObj.objList['form'].submit(); //提交 // } // } ], //内容区提交组 },
	 * ValidataInit: {
	 * 
	 * successBeforeFun: function(res) {
	 * 
	 * var util = require('util'), loading = new util.loading();
	 * 
	 * loading.open(); $.fn.orderName();
	 * 
	 * if (res['submitStatus'] == 'submit') { //二级页面
	 * 
	 * 
	 * if (res['fm5']) { //有改变
	 * 
	 * $('.submitStatus').attr('submitStatus', res['submitStatus']); return
	 * true; } else { //无改变
	 * 
	 * hintPopup('数据未改变，保持原有储存', window.location.href);
	 * $('.m-modal-box').remove(); return false; } } else {
	 * 
	 * $('.submitStatus').attr('submitStatus', res['submitStatus']); return
	 * true; } },
	 * 
	 * successFun: function(res) { // //弹窗提示 // var hintPopup =
	 * require('zyw/hintPopup');
	 * 
	 * if (res['success']) { // $.ajax({ // url: '/projectMg/form/submit.htm', //
	 * type: 'post', // dataType: 'json', // data: { // formId:
	 * res['form']['formId'], // _SYSNAME: 'FM' // }, // success: function(data) { //
	 * hintPopup(data.message, function() { // if (data.success) { //
	 * window.location.href = window.location.href; // } //
	 * $('.m-modal-box').remove(); // }); // } // });
	 * $('.m-modal-box').remove(); hintPopup(res.message, window.location.href); }
	 * else {
	 * 
	 * $('.m-modal-box').remove(); hintPopup(res['message']); } }, // errorAll: {
	 * //validata error属性集 // errorClass: 'errorClassConfirm', // errorElement:
	 * 'em', // errorPlacement: function(error, element) { //
	 * if(element.hasClass('radioSpan')){ // } // element.after(error) // } // } },
	 * 
	 * callback: function(objList) { //加载时回调
	 * 
	 * //验证方法集初始化 $('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
	 * .initAllOrderValidata() .assignGroupAddValidataUpUp(); //
	 * $('body').on('change', '.score', function(event) { // $('.score').blur(); //
	 * }); } })
	 **************************************************************************/
	$('.minTime').click(function(event) {

		var NewDate, Time, maxTime;

		NewDate = new Date();
		Time = NewDate.getTime() + 7 * 24 * 3600 * 1000;
		maxTime = new Date(Time).toLocaleString().substring(0, 10).replace(/\//g, '-');

		laydate({
			// elem: '.minTime',
			min : maxTime
		});

	});

})