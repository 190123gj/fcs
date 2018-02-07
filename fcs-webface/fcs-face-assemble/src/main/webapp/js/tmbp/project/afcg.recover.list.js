define(function (require, exports, module) {

	//项目管理 > 追偿管理 > 通知函、列表
	require('input.limit');
	require('zyw/publicPage');
	require('Y-msg');

	var util = require('util');

	var NULLTEXT = '',
		NOTICEID = $('#fnNoticeLetterId').val(),
		ISPRIENT = false;

	$.each((new Array(50)), function (index, el) {
		NULLTEXT += '&nbsp;';
	});

	//定位到对应的地方
	if (NOTICEID) {

		setTimeout(function () {

			$('.fnTab li[noticeid="' + NOTICEID + '"]').click();
			ISPRIENT = true;
		}, 0);

	}

	// ------ 通知函 切换 start
	$('.fnTab').on('click', 'li:not(.active)', function () {

		if (ISPRIENT) {
			return;
		}

		var $this = $(this);

		$this.addClass('active').siblings().removeClass('active');

		$('.fnTabItem').addClass('fn-hide').eq($this.index()).removeClass('fn-hide');

	});

	// ------ 通知函 切换 end

	// ------ 保存通知单 start
	var $fnNoticeForm = $('#fnNoticeForm');

	$fnNoticeForm.on('click', '.fnSave', function () {

		var $this = $(this),
			$fnNoticeHide = $('.fnNoticeHide');

		// 遍历 `.fnTabItem` 组合html
		$('.fnTabItem').each(function (index, el) {

			var $div = $('<div></div>'),
				_$this = $(this),
				_$input = $fnNoticeHide.eq(index).find('input');

			var _$content = _$input.eq(0),
				_$contentMessage = _$input.eq(1);

			if (_$content[0].name.indexOf('.') == -1) {

				_$input.each(function (i, e) {

					e.name = 'noticeLetters[' + index + '].' + e.name;

				});

			}

			$div.html(_$this.html());

			var _$fnTextInput = $div.find('.fn-text-input input');

			// 遍历 `.fn-text-input` 添加value值
			_$this.find('.fn-text-input input').each(function (i, e) {

				if (e.value.replace(/\s/g, '')) {

					_$fnTextInput.eq(i).attr('value', e.value);

				}

			});

			// 保存值
			_$content.val(encodeURIComponent(util.trimHtml($div.html())));

			// 遍历 `.fn-text-input` 文字替换
			_$this.find('.fn-text-input input').each(function (i, e) {

				if (e.value.replace(/\s/g, '')) {

					_$fnTextInput.eq(i).after(e.value);

				} else {
					_$fnTextInput.eq(i).after(NULLTEXT);
				}

				_$fnTextInput.eq(i).remove();

			});

			// 保存值
			_$contentMessage.val(encodeURIComponent(util.trimHtml($div.html())));

		});

		// 提交数据
		util.ajax({
			url: $fnNoticeForm.attr('action'),
			data: $fnNoticeForm.serializeJCK(),
			success: function (res) {

				if (res.success) {

					// 是否申请用印
					Y.alert('保存成功', function () {

						if ($this.hasClass('fnToApply')) {

							// 申请用印

							window.location.href = '/projectMg/stampapply/addStampApply.htm?noticeLetterId=' + res.formIds[$('.fnTab li.active').index()] + '&revoveryId=' + res.id;

						} else {

							window.location.href = '/projectMg/recovery/projectRecoveryList.htm';

						}

					})

				} else {
					Y.alert('操作失败', res.message);
				}

			}
		});

	});

	// ------ 保存通知单 end



	// ------ 打印通知单 start
	$('.fnPrint').on('click', function () {

		// 只是打印当前激活的那页
		util.print($('.fnTabItem').eq($('.fnTab li.active').index()).html());

	});
	// ------ 打印通知单 end

	// ------ 关闭项目 start
	var $fnCloseProject = $('#fnCloseProject');
	$fnCloseProject.on('click', '#submit', function () {

		var $fnInput = $('.fnInput'),
			_isPass = true,
			_isPassEq;

		$fnInput.each(function (index, el) {

			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = index;
				return false;
			}

		});


		if (!_isPass) {
			Y.alert('提示', '请填写完整', function () {
				$fnInput.eq(_isPassEq).focus();
			});
			return;
		}

		util.ajax({
			url: $fnCloseProject.attr('action'),
			data: $fnCloseProject.serializeJCK(),
			success: function (res) {

				if (res.success) {
					Y.alert('提示', '操作成功', function () {
						window.location.href = '/projectMg/recovery/projectRecoveryList.htm';
					});
				} else {
					Y.alert('操作失败', res.message);
				}

			}
		});


	});

	// ------ 关闭项目 end

	// // ------ 列表 start
	// $('#list').on('click', '.fnDoAjax', function(e) {

	// 	var _url = this.href;

	// 	e.preventDefault();
	// 	util.ajax({
	// 		url: _url,
	// 		success: function(res) {

	// 			if (res.success) {

	// 				Y.alert('提示', '操作成功', function() {

	// 					window.location.reload(true);

	// 				});

	// 			} else {

	// 				Y.alert('提示', res.message);

	// 			}

	// 		}
	// 	});

	// });
	// // ------ 列表 end


	if (document.getElementById('fnNeedOPN')) {

		var publicOPN = new(require('zyw/publicOPN'))();

		var $customerId = $('#customerId');

		if (!!$customerId.val()) {

			publicOPN.addOPN([{
				name: '查看客户资料',
				alias: 'lookCusInfo',
				event: function () {

					var _s = (document.getElementById('customerType').value == 'PERSIONAL') ? 'personalCustomer' : 'companyCustomer',
						_url = '/customerMg/' + _s + '/info.htm?userId=' + $customerId.val(),
						_list = '/customerMg/' + _s + '/list.htm';

					util.openDirect('/customerMg/index.htm', _url, _list);

				}
			}]);

		}

		publicOPN.init().doRender();


	}


});