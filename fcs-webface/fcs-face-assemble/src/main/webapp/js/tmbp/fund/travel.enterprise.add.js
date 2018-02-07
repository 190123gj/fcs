define(function(require, exports, module) {

	require('input.limit');
	require('zyw/opsLine');

	require('zyw/chooseRegion');

	require('Y-entertip');

	var util = require('util');

	util.resetName();

	$('body').on('click', '.fnAddLine', function() {
		setTimeout(function() {
			util.resetName();
		}, 0);
	});

	// tab
	var $step = $('#step'),
		$window = $(window),
		$div = $('<div></div>').height($step.outerHeight()).hide(),
		HALFWINDOWH = $window.height() / 2,
		TITLEH = [];

	$step.after($div);

	$step.outerWidth($step.outerWidth());

	// 缓存高度
	$('.fnStep').each(function(index, el) {

		TITLEH.push({
			key: el.id,
			top: $(el).offset().top
		});
	});

	function whichNavActive(scrollTop) {

		scrollTop = scrollTop + HALFWINDOWH / 2 * 1;

		$.each(TITLEH, function(i, o) {

			if (i == 0) {
				if (scrollTop < TITLEH[1].top) {
					activeItem(o.key);
					return false;
				}
			} else if (i == TITLEH.length - 1) {
				if (scrollTop > o.top) {
					activeItem(o.key);
					return false;
				}
			} else {
				if (scrollTop < TITLEH[(i + 1)].top && scrollTop > o.top) {
					activeItem(o.key);
					return false;
				}
			}

		});

		function activeItem(key) {
			var $keyItem = $step.find('li.item[ftf="' + key + '"]');
			if (!$keyItem.hasClass('active')) {
				$step.find('li.item').removeClass('active');
				$keyItem.addClass('active');
			}
		}

	}

	if ($window.scrollTop() > 100) {
		$step.addClass('step-fiexd');
		$div.show();
		whichNavActive($window.scrollTop());
	}

	$window.scroll(function(event) {

		if ($window.scrollTop() > 100) {
			if (!$step.hasClass('step-fiexd')) {
				$step.addClass('step-fiexd');
				$div.show();
			}
		} else {
			if ($step.hasClass('step-fiexd')) {
				$step.removeClass('step-fiexd');
				$div.hide();
			}
		}

		whichNavActive($window.scrollTop());

	});

	$step.on('click', 'li.item', function() {

		$(this).addClass('active').siblings().removeClass('active');
		$window.scrollTop($('#' + this.getAttribute('ftf')).offset().top - $step.outerHeight());

	});

	// 输入银行卡的提示
	$('.fnInputBankCard').each(function(i, el) {

		Y.create('EnterTip', {
			target: '.fnInputBankCard' + i,
			mode: 'bankCard'
		});

	});

	var REQUIRERULES = { // 验证规则
		rules: {},
		messages: {}
	};

	// 必填
	$('.fnRequired').each(function(index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			required: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			required: '必填'
		});

	});


	// 字母
	$('.fnLetter').each(function(index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			checkAZ: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			checkAZ: '请输入英文字母'
		});

	});

	// 电话号码
	$('.fnIsPhoneOrMobile').each(function(index, el) {
		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			isPhoneOrMobile: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			isPhoneOrMobile: '请输入正确的电话号码'
		});
	});

	// email
	$('.fnEmail').each(function(index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			email: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			email: '请输入正确的邮箱地址'
		});

	});

	// 中文
	$('.fnIsZh').each(function(index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			checkZh: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			checkZh: '请输入中文'
		});

	});

	// 邮编
	$('.fnIsZipCode').each(function(index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			checkZhZipCode: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			checkZhZipCode: '请输入6位数字格式'
		});

	});

	// 银行卡
	$('.fnIsBankCard').each(function(index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			rangelength: [18, 21],
			number: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			rangelength: '请输入{0}到{1}位数字',
			number: '请输入数字'
		});

	});

	module.exports = REQUIRERULES;

});