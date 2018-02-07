define(function(require, exports, module) {
	//项目管理 > 授信后管理  > 到期项目 新增 打印

	require('zyw/upAttachModify');

	require('input.limit');

	var util = require('util');

	//------ 选择时间 start
	//选择时间 默认时间为当前时间
	var $fnInputDate = $('.fnInputDate'),
		nowTime = util.getNowTime();
	$fnInputDate.find('.fnInputYear').val(nowTime.YY).attr('max', nowTime.YY).attr('old', nowTime.YY);
	$fnInputDate.find('.fnInputMonth').val(nowTime.MM).attr('max', nowTime.MM).attr('old', nowTime.MM);
	$fnInputDate.find('.fnInputDay').val(nowTime.DD).attr('max', nowTime.DD).attr('old', nowTime.DD);

	$('#fnYearText').html(nowTime.YY);

	$fnInputDate.on('blur', '.fnInputYear', function() {

		var _thisVal = +this.value,
			_oldVal = +this.getAttribute('old');

		if (_thisVal > +this.getAttribute('max')) {
			this.value = +this.getAttribute('max');
			_thisVal = +this.getAttribute('max');
		}

		if (_thisVal != _oldVal) {
			this.value = _thisVal;
			var _$fnInputDate = $(this).parents('.fnInputDate');
			_$fnInputDate.find('.fnInputMonth').val('01');
			_$fnInputDate.find('.fnInputDay').val('01');
		}

	}).on('blur', '.fnInputMonth', function() {

		var _$fnInputDate = $(this).parents('.fnInputDate'),
			_$year = _$fnInputDate.find('.fnInputYear'),
			_isNowYear = (+_$year.val() >= +_$year.attr('max')) ? true : false;

		var _thisVal = +this.value,
			_oldVal = +this.getAttribute('old');

		if (_isNowYear) {
			if (_thisVal > +this.getAttribute('max')) {
				this.value = util.getTwoIntegers(+this.getAttribute('max'));
				_thisVal = +this.getAttribute('max');
			}
		} else {
			if (_thisVal > 12) {
				_thisVal = 12;
			}
		}
		if (_thisVal < 1) {
			_thisVal = 1;
		}

		if ((_thisVal + '').length < 2) {
			_thisVal = util.getTwoIntegers(_thisVal);
		}
		if (_thisVal != _oldVal) {
			this.value = _thisVal;
			_$fnInputDate.find('.fnInputDay').val('01');
		}

	}).on('blur', '.fnInputDay', function() {

		var _$fnInputDate = $(this).parents('.fnInputDate'),
			_year = +_$fnInputDate.find('.fnInputYear').val(),
			_month = +_$fnInputDate.find('.fnInputMonth').val(),
			_isNowMonth = (+_$fnInputDate.find('.fnInputMonth').attr('max') == _month) ? true : false;

		var _thisVal = +this.value,
			_dayOfMonth = util.getDayOfMonth(_year, _month);

		if (_isNowMonth) {
			if (_thisVal > +this.getAttribute('max')) {
				this.value = util.getTwoIntegers(+this.getAttribute('max'));
				_thisVal = +this.getAttribute('max');
			}
		} else {
			if (_thisVal > _dayOfMonth) {
				_thisVal = _dayOfMonth
			}
		}

		if (_thisVal < 1) {
			_thisVal = 1;
		}

		if ((_thisVal + '').length < 2) {
			_thisVal = util.getTwoIntegers(_thisVal);
		}
		this.value = _thisVal;

	}).on('focus', '.fnInputYear,.fnInputMonth,.fnInputDay', function() {
		this.setAttribute('old', this.value);
	});

	//------ 选择时间 end

	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
		'title': '人员',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '', //已选id,多用户用,隔开
			selectUserNames: '' //已选Name,多用户用,隔开
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层

	$('#fnJBRBtn').on('click', function() {

		singleSelectUserDialog.init(function(relObj) {

			document.getElementById('fnJBR').value = relObj.fullnames;

		});

	});


	var $form = $('#form'),
		nullText = '';
	for (var i = 0; i < 40; i++) {
		nullText += '&nbsp;'
	}
	$('.submit').on('click', function() {

		var _this = $(this);
		if (_this.hasClass('img')) {
			return;
		}
		_this.hasClass('ing');

		var $input = $('.fn-text-input input[type="text"]'),
			_isPass = true,
			_isPassEq;
		$input.each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '') && !util.hasClass(el, 'ignore')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}
		});

		if (!_isPass) {
			Y.alert('提示', '内容不完整，请再次确认', function() {
				$input.eq(_isPassEq).focus();
			})
			_this.removeClass('ing');
			return;
		}

		// 转换html
		var $div = $('<div></div>').html($form.html()),
			$_fnTextInput = $div.find('.fn-text-input'),
			$_fnListInput = $div.find('#list input');

		$form.find('.fn-text-input').each(function(index, el) {
			var _input = this.getElementsByTagName('input')[0],
				_hasVal = this.innerHTML.replace(/\s/g, '') ? true : false;
			$_fnTextInput.eq(index)[0].innerHTML = _input ? _input.value : (_hasVal ? this.innerHTML : nullText);
		});

		// 欠 款 清 单
		$form.find('#list input').each(function(index, el) {
			$_fnListInput.eq(index).parent().html(el.value || nullText);
		});

		// 按钮
		$div.find('.ui-btn').remove();

		document.getElementById('fnPrintHtml').value = $div.html().replace(/(^\s*|\s*$)/g, '');

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {
				if (res.success) {
					if (_this.hasClass('fnPrint')) {
						//doPrint($div, res.year, res.sequence);
						window.location.href = '/projectMg/stampapply/addStampApply.htm?templateId=' + res.id;
					} else {
						Y.alert('提示', '操作成功', function() {
							window.location.href = '/projectMg/expireProject/list.htm';
						});
					}
				} else {
					Y.alert('提示', res.message)
					_this.removeClass('ing');
				}
			}
		});

	});

	$('.submit2').on('click', function() {

		var _this = $(this);
		if (_this.hasClass('img')) {
			return;
		}
		_this.hasClass('ing');

		if (_this.hasClass('fnPrint')) {
			doPrint($('#fnPrint'), $('#fnYear').val(), $('#fnSequence').val());
		} else {

			var $f = $('#files');

			util.ajax({
				url: $f.attr('action'),
				data: $f.serialize(),
				success: function(res) {
					if (res.success) {
						Y.alert('提示', '操作成功', function() {
							window.location.reload(true);
						});
					} else {
						Y.alert('提示', res.message);
						_this.removeClass('ing');
					}
				}
			});

		}

	});

	if ($('#fnYear').val()) {
		document.getElementById('fnYearText').innerHTML = document.getElementById('fnYear').value;
		document.getElementById('fnSequenceText').innerHTML = document.getElementById('fnSequence').value;
	}

	// 打印页面需要替换值

	function doPrint($html, year, sequence) {
		$html.find('#fnYearText').html(year);
		$html.find('#fnSequenceText').html(sequence);

		util.print($html.html());

		// document.getElementsByTagName('body')[0].innerHTML = $html.html();
		// setTimeout(function() {
		// 	window.print();
		// 	setTimeout(function() {
		// 		window.location.reload(true);
		// 	}, 1000)
		// }, 500);
	}


});