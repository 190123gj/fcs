define(function (require, exports, module) {

	//弹窗提示
	var hintPopup = require('zyw/hintPopup');
	//上传
	require('zyw/upAttachModify');
	//过程选择
    $('body').off('change','#courseSelect dt input[type="checkbox"]');
	$('body').on('change','#courseSelect dt input[type="checkbox"]',function () {
        var _this = $(this),
            _index = _this.index('#courseSelect dt input'),
            _checked = _this.attr('checked'),
            _courseSelectDd = $('#courseSelect .fnCourseSelectitem');
        _checked ? _courseSelectDd.eq(_index).show().find('input,select,textarea').each(function (index, el) {
            $(this).removeClass('cancel');
        }) : _courseSelectDd.eq(_index).hide().find('input,select,textarea').each(function (index, el) {
            $(this).addClass('cancel');
        });
    })
	$('#courseSelect dt input[type="checkbox"]').trigger('change');


	var unitNo = {
			LAND: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数位12位以内，小数2位以内的数字'
				}
			},
			INVENTORY: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数位12位以内，小数2位以内的数字'
				}
			},
			FUNDS: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数位12位以内，小数2位以内的数字'
				}
			},
			HOUSE: {
				digitsMaxlength: true,
				messages: {
					digitsMaxlength: '请输入20位以内的整数'
				}
			},
			EQUIPMENT: {
				digitsMaxlength: true,
				messages: {
					digitsMaxlength: '请输入20位以内的整数'
				}
			},
			STOCK: {
				isPercentTwoDigits: true,
				messages: {
					isPercentTwoDigits: '请输入0.01-100之间的数字'
				}
			}
		},
		_selectChangeGainData;

	//请求押品类型与性质关系Json
	$.ajax({
		url: '/baseDataLoad/queryPledgeProperties.json',
		type: 'POST',
		dataType: 'json',
		async: false,
		success: function (res) {

			if (res.success) {
				_selectChangeGainData = res.data;
			}

		}
	})

	$('body').on('change', '.selectChangeGain', function (event) {
		var _this = $(this),
			_val = _this.val(),
			_unit = _this.find('option:selected').attr('unit') || '',
			_prev = _this.parents('.selectChangeBox').find('.selectChangeInput'),
			_next = _this.parent().nextAll().find('select'),
			_code = _next.attr('code'),
			_optionHtml = '<option value="">请选择押品性质</option>',
			_unitNo;
		_this.parents('.selectChangeBox').find('.selectChange').text(_unit);

		//对应项改变验证规则
		for (i in unitNo) {
			for (j in unitNo[i]) {
				if (j != 'messages') _prev.rules('remove', j);
			}
		}
		for (i in unitNo) {
			if (i == _val) _prev.rules('add', unitNo[i]);
		}
		_prev.blur();

		//改变性质
		if (_val != '') {
			for (var i = 0; i < _selectChangeGainData[_val].length; i++) {
				_optionHtml += '<option value="' + _selectChangeGainData[_val][i]['code'] + '">' + _selectChangeGainData[_val][i]['name'] + '</option>'
			}
		}

		//选中保存的性质
		_next.html(_optionHtml).val(_code);

	}).find('.selectChangeGain').trigger('change');



	//反担保子项有填其他子项必填
	$('body').on('change', '[ordername="pledgeOrders"] input,[ordername="mortgageOrders"] input,[ordername="pledgeOrders"] select,[ordername="mortgageOrders"] select', function (event) {
		var _this = $(this),
			//_val = _this.val(),
			_parents = _this.parents('[ordername]'),
			_find = _parents.find('input,select'),
			_fill = true;
		//_val = _find.val();
		_find.each(function (index, el) {
			var _this = $(this),
				_val = _this.val();

			//console.log(_val)
			_val ? _fill &= false : _fill &= true;
		});
		//console.log(_fill)
		_fill ? _find.each(function (index, el) {
			var _this = $(el);
			_this.addClass('cancel').next('.error-tip').hide();
		}) : _find.each(function (index, el) {
			var _this = $(el);
			_this.removeClass('cancel').next('.error-tip').show();
		});
	}).find('[ordername="pledgeOrders"] input,[ordername="mortgageOrders"] input,[ordername="pledgeOrders"] select,[ordername="mortgageOrders"] select').trigger('change');



	// //计算合计
    $('body').off('keyup click', '.amountStr,.ratio,.debtedAmount ')
	$('body').on('keyup click', '.amountStr,.ratio,.debtedAmount ', function (event) {

		var $this, $tbody, $tr, $assessPrice, $pledgePrice, assessPrice, pledgePrice;
		$this = $(this);
		$tbody = $this.closest('tbody');
		$tr = $tbody.find('tr');
		$assessPrice = $this.closest('dd').prev().find('.assessPrice');
		$pledgePrice = $this.closest('dd').prev().find('.pledgePrice');
		assessPrice = 0;
		pledgePrice = 0;

		$tr.each(function (index, el) {
			var $el, $amountStr, $ratio, $debtedAmount;

			$el = $(el);
			$amountStr = $el.find('.amountStr');
			$ratio = $el.find('.ratio');
			$debtedAmount = $el.find('.debtedAmount');
			if ($amountStr.length) {
                assessPrice += parseFloat($amountStr.val().replace(/\,/g, '') || 0);
                pledgePrice += (parseFloat($amountStr.val().replace(/\,/g, '') || 0) * parseFloat(($ratio.val() || '').replace(/\,/g, '') || 0) / 100) - (parseFloat(($debtedAmount.val() || '').replace(/\,/g, '')) || 0);

            }


		});
		$assessPrice.text(assessPrice.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));
		$pledgePrice.text(pledgePrice.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));

	}).on('keyup', '.guaranteeFun', function (event) {
		var _this = $(this);
		_parents = _this.parents('dl'),
			_guaranteeFun = 0;
		_parents.find('.guaranteeFun').each(function (index, el) {
			var _this = $(el),
				_val = _this.val() || 0;
			_guaranteeFun += parseFloat(_val.replace(/\,/g, '')) || 0;
		});
		var _creditAmountStr = parseFloat($('input[name="creditAmountStr"]').val().replace(/\,/g, ''));
		//qichunhai 2017-7-7
		if(_guaranteeFun>_creditAmountStr)
		{
			_guaranteeFun=_creditAmountStr;
		}
		_parents.prev('dl').find('.guaranteeTotal').text('合计保证额度：' + _guaranteeFun.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ',') + '元');
	});

	require('input.limit');

	//目录
	$('body').on('click', '.catalogue dt', function (event) {

		event.stopPropagation();

		var $this, $next;

		$this = $(this);
		$next = $this.next();

		$next.is(':visible') ? $this.css({
			'background-position': '0 -13px'
		}) : $this.css({
			'background-position': '0 -63px'
		});

		$next.slideToggle();

	}).on('click', '.catalogueSelect', function (event) {

		var $this, $target;

		$this = $(this);
		$target = $this.find('.catalogue');

		$target.is(':visible') ? $this.css({
			'background-position': '98% -112px'
		}) : $this.css({
			'background-position': '98% -62px'
		});

		$target.toggle();

	}).on('click', '.catalogue p', function (event) {

		var $this, $target, val, typeId;

		$this = $(this);
		$typeId = $this.parents('.catalogue').siblings('.typeId');
		$pledgeType = $this.parents('.catalogue').siblings('.pledgeType');
		val = $this.text();
		typeId = $this.attr('typeId');

		$typeId.val(typeId);
		$pledgeType.val(val);

	}).on('click', '.fnDelLineBox', function (event) {

		var $target, $this, $assessPrice, $pledgePrice;

		$this = $(this);
		$target = $this.find('.amountStr');
		$assessPrice = $this.parents('dl').find('.assessPrice');
		$pledgePrice = $this.parents('dl').find('.pledgePrice');

		if (!$target.length) {

			$assessPrice.text(0.00);
			$pledgePrice.text(0.00);

		}

		$('body').find('.amountStr').trigger('keyup');

	}).on('click', '.batchBtn', function (event) { //批量操作

		var $this, $target;

		$this = $(this);
		$target = $this.parents('dt').next().find('.batchCheckbox:checked');

		if (!$target.length) {

			hintPopup('请选择要批量处理的项')
			return false;

		}

		$('body').Y('Window', {
			content: $('#batchPopu').html(),
			modal: true,
			key: 'modalwnd',
			simple: true
		});

		var modalwnd = Y.getCmp('modalwnd');

		modalwnd.wnd.on('click', '.close', function () {
			modalwnd.close();
		});

		modalwnd.wnd.on('click', '.okBtn', function () {

			var synPosition, postposition, debtedAmountStr;

			synPosition = modalwnd.wnd.find('.synPosition').val();
			postposition = modalwnd.wnd.find('.postposition').val();
			debtedAmountStr = modalwnd.wnd.find('.debtedAmountStr').val();

			$target.each(function (index, el) {

				var $el, $tr;

				$el = $(el);
				$tr = $el.parents('tr');

				$tr.find('.synPosition').val(synPosition);
				$tr.find('.postposition').val(postposition);
				$tr.find('.debtedAmountStr').val(debtedAmountStr).keyup();

			});

			modalwnd.close();

		});

	}).on('click', '.allCheckboxBtn', function (event) {

		var $this, $text, $target;

		$this = $(this);
		$text = $this.text();
		$target = $this.parents('dt').next().find('.batchCheckbox');

		if (!$target.length) {

			hintPopup('没有项可选择');

		}

		if ($text == '全选') {

			$this.text('取消全选');
			$target.attr('checked', true);

		} else {

			$this.text('全选');
			$target.attr('checked', false);

		}

	}).on('change', '.batchCheckbox', function (event) {

		var $this, $target, $all;

		$this = $(this);
		$target = $this.parents('dd').prev().find('.allCheckboxBtn');
		$all = $this.parents('tbody').find('.batchCheckbox').not(':checked');

		if ($all.length) {

			$target.text('全选');

		} else {

			$target.text('取消全选');

		}

	});

	// if (!Object.keys) Object.keys = function(o) {

	// 	if (o !== Object(o))
	// 		throw new TypeError('Object.keys called on a non-object');

	// 	var k = [],
	// 		p;

	// 	for (p in o)

	// 		if (Object.prototype.hasOwnProperty.call(o, p)) k.push(p);

	// 	return k;

	// }

	var popupWindow = require('zyw/popupWindow');

	module.exports = function (rulesAll, form, _allWhetherNull, _boll) {

		var $itype = $('[name="itype"]');

		if ($itype.length && $itype.val()) {

			var itypeTop = parseFloat($('[itype="' + $itype.val() + '"]')[0].offsetTop);

			$('body,html').animate({
				scrollTop: itypeTop + 'px'
			}, 1)

		}

		$('.addNewPopup').click(function (event) {

			var obj = new Object(),
				amountStrObj = new Object(),
				ratioObj = new Object(),
				PopupAllObj = new Object(),
				$thisBtn = $(this),
				$target = $thisBtn.parent().next().find('tbody'),
				thisBtnText = $thisBtn.text().substring(2, 4),
				thisBtnOrderName = $thisBtn.attr('orderName'),
				thisBtnItype = $thisBtn.attr('itype'),
				$number = 0,
				calculate = function ($Y, Fun) { //翻页或搜索保持页面数据

					var amountStrRateSum = 0,
						amountStrSum = 0,
						i;

					for (i in amountStrObj) {

						amountStrSum += amountStrObj[i];
						amountStrRateSum += amountStrObj[i] * ratioObj[i] / 100;

						if (Fun) Fun($Y, i);

					}
					// console.log(123);
					// $Y.wnd.find('.PopupAll').text(PopupAllObj[$numPopupAll]);
					$Y.wnd.find('b.number').text($number);
					$Y.wnd.find('.amountStrRateSum').text(amountStrRateSum.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));
					$Y.wnd.find('.amountStrSum').text(amountStrSum.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));

				},
				commonCallback = function ($Y) { //翻页或搜索保持选项

					var $numPopupAll = $Y.wnd.find('.pageBox').text().match(/第(\d+?)页/)[1];

					$Y.wnd.find('tr').attr('orderName', thisBtnOrderName);
					$Y.wnd.find('em.replaceText').text(thisBtnText);

					if (PopupAllObj[$numPopupAll]) {

						$Y.wnd.find('.PopupAll').text(PopupAllObj[$numPopupAll]);

					}

					calculate($Y, function ($Y, i) {

						var $this = $Y.wnd.find('tbody tr[ids="' + i + '"]');

						if ($this.length) $this.find('[type="checkbox"]').attr('checked', true);

					});

				}

			fundDitch2 = new popupWindow({

				YwindowObj: {
					content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
					closeEle: '.close', //find关闭弹窗对象
					dragEle: '.newPopup dl dt.dt' //find拖拽对象
				},

				ajaxObj: {
					url: '/baseDataLoad/queryAssetSimple.json',
					type: 'post',
					dataType: 'json',
					data: {
						// isChargeNotification: "IS",
						ids: function () {

							var arr = new Array();

							$target.find('tr').each(function (index, el) {

								arr.push($(el).attr('ids'))

							});

							return arr.join(',');

						},
						pageSize: 6,
						pageNumber: 1,
						projectCode: $("#coucilSummaryProjectCode").val()
					}
				},

				pageObj: { //翻页
					clickObj: '.pageBox a.btn', //find翻页对象
					attrObj: 'page', //翻页对象获取值得属性
					jsonName: 'pageNumber', //请求翻页页数的dataName
					callback: commonCallback
				},

				formAll: { //搜索
					submitBtn: '.PopupSubmit', //find搜索按钮
					formObj: '#PopupFrom', //find from
					callback: commonCallback
				},

				callback: function ($Y) {

					$Y.wnd.find('tr').attr('orderName', thisBtnOrderName);
					$Y.wnd.find('em.replaceText').text(thisBtnText);

					$Y.wnd.on('change', '[type="checkbox"]', function (event) { //选择后操作

						var $this, ids, $tr, checked, notCheckedLength, $numPopupAll;

						$this = $(this);
						$tr = $this.parents('tr');
						ids = $tr.attr('ids');
						checked = $this.attr('checked');
						notCheckedLength = $this.parents('tbody').find('input[type="checkbox"]').not(':checked').length;
						$numPopupAll = $Y.wnd.find('.pageBox').text().match(/第(\d+?)页/)[1];

						if (checked) {

							obj[ids] = $tr[0];
							amountStrObj[ids] = parseFloat($tr.find('.amountStr').val().replace(/\,/g, '') || 0);
							ratioObj[ids] = parseFloat($tr.find('.ratio').val().replace(/\,/g, '') || 0);
							$number++;

						} else {

							delete obj[ids];
							delete amountStrObj[ids];
							delete ratioObj[ids];
							$number--;

						}

						calculate($Y);

						if (notCheckedLength) {

							$Y.wnd.find('.PopupAll').text('全选');
							PopupAllObj[$numPopupAll] = '全选'

						} else {

							$Y.wnd.find('.PopupAll').text('取消全选');
							PopupAllObj[$numPopupAll] = '取消全选'

						}



					});

					$Y.wnd.on('click', '.okBtn', function (event) { //确定后操作

						var string = '',
							embellish;

						for (i in obj) {

							$(obj[i]).find('td:eq(5)').hide().end().find('td:eq(4)').hide().end().find('td:eq(0)').hide().end()
								.append(['<td>',
									'<select class="text fnChangeInput synPosition" name="synPosition">',
									'<option value="">请选择</option>',
									'<option value="FIRST">第一顺位</option>',
									'<option value="SECOND">第二顺位</option>',
									'</select>',
									'</td>',
									'<td>',
									'<select class="text fnChangeInput postposition" name="postposition">',
									'<option value="">请选择</option>',
									'<option value="YES">是</option>',
									'<option value="NO">否</option>',
									'</select>',
									'</td>',
									'<td><input class="text fnChangeInput debtedAmount debtedAmountStr fnMakeMoney fnMakeMicrometer" type="text" name="debtedAmountStr"></td>',
									'<td class="fn-text-c fn-tac">',
									'<a class="fn-mr10" href="/assetMg/toAdd.htm?id=' + $(obj[i]).attr('ids') + '&isView=true&disReturn" target="_blank">查看详情</a>',
									'<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-green fnDelLine" parentsClass="fnNewLine">删除</a>',
									'</td>'
								].join(''))
								.prepend('<td class="fn-text-c"><input type="checkbox" class="batchCheckbox"></td><td class="testNum fn-text-c"></td>');

							embellish = $(obj[i])[0].outerHTML;

							string += embellish;

						}

						$target.append(string);
						$.fn.eachFunaddValidata($target, rulesAll).numOrder($target.find('tr'), '.testNum');

						$Y.close();
						setTimeout(function () {
                            $('body').find('.amountStr').trigger('keyup');
                        },200)



					})

					$Y.wnd.on('click', '.PopupAll', function (event) { //全选

						var $this, $target, $numPopupAll, $text;

						$this = $(this);
						$text = $this.text();
						$numPopupAll = $this.parents('dd').find('.pageBox').text().match(/第(\d+?)页/)[1];
						$target = $this.parents('dd').find('input[type="checkbox"]');

						if ($text == '全选') {

							$this.text('取消全选');
							$target.not(':checked').attr('checked', true).change();
							PopupAllObj[$numPopupAll] = '取消全选'

						} else {

							$this.text('全选');
							$target.attr('checked', false).change();
							PopupAllObj[$numPopupAll] = '全选'

						}



					});

					$Y.wnd.on('click', '#newAddType', function (event) {

						$.fn.whetherMust(rulesAll, false).allAddValidata(rulesAll); //否必填

						if (!form.valid()) {

							hintPopup('页面填写有错！无法保存');

							return false;

						}

						form.ajaxSubmit({
							type: 'post',
							dataType: 'json',
							data: {
								toIndex: -1,
								fm5: fm5WhetherChange(),
								checkStatus: form.allWhetherNull(_allWhetherNull, _boll)
							},
							success: function (res) {

								if (res.success) {

									window.open('/assetMg/toAdd.htm?isInvestigation=YES&disReturn&itype=' + thisBtnItype + '&iFormId=' + $('[name="formId"]').val(), '_blank');

								} else {

									hintPopup(res.success);

								}

							},
							error: function (a, b, c) {
								//loading.close();
								hintPopup('请求失败')
							}
						});
					})

				},

				console: false //打印返回数据

			});

		});

		var md5 = require('md5'); //md5加密
		function formSerializeMd5(_form) {
			var _formSerialize = _form.serialize();
			return md5(_formSerialize);
		}

		function fm5WhetherChange() {
			var _newSerializeMd5 = formSerializeMd5(form);
			fm5 = (_newSerializeMd5 != _initSerializeMd5) ? 1 : 0; //数据是否有改变
			return fm5
		}

		var _initSerializeMd5 = formSerializeMd5(form);

	}



	//初始页面数据

	// $('body').on('keyup', '.assessFun,.pledgeFun', function(event) {
	// 	var _this = $(this),
	// 		_parents = _this.parents('dl'),
	// 		_text = _parents.prev().find('dt').text().substring(2, 4),
	// 		_assessTotal = 0,
	// 		_pledgeTotal = 0;
	// 	_parents.find('.assessFun').each(function(index, el) {
	// 		var _this = $(el),
	// 			_val = _this.val() || 0,
	// 			_next = _this.parent('td').siblings().find('input').val();
	// 		_assessTotal += parseFloat(_val) || 0;
	// 		_pledgeTotal += parseFloat(parseFloat(_next) * parseFloat(_val / 100) || 0);
	// 	});
	// 	// console.log(_pledgeTotal);
	// 	_parents.nextAll('.assessTotal:first').text('评估' + '价格：' + _assessTotal.toFixed(2) + '元');
	// 	_parents.nextAll('.pledgeTotal:first').text(_text + '价格：' + _pledgeTotal.toFixed(2) + '元');
	// }).on('keyup', '.guaranteeFun', function(event) {
	// 	var _this = $(this);
	// 	_parents = _this.parents('dl'),
	// 		_guaranteeFun = 0;
	// 	_parents.find('.guaranteeFun').each(function(index, el) {
	// 		var _this = $(el),
	// 			_val = _this.val() || 0;
	// 		_guaranteeFun += parseFloat(_val) || 0;
	// 	});
	// 	_parents.prev('dl').find('.guaranteeTotal').text('合计保证额度：' + _guaranteeFun + '元');
	// });

	//点击增加内置滚动条滑到底
	$('.fnAddLine').click(function (event) {
		var _this = $(this),
			// _overflowY1 = _this.parent('.overflowY1') || _this.parent().find('.overflowY1');
			_overflowY1 = _this.parent('.overflowY1').length ? _this.parent('.overflowY1') : _this.prev('.overflowY1');

		if (!_this.parent('.overflowY1').length && !_this.prev('.overflowY1').length) return false;

		_overflowY1.animate({
			scrollTop: _overflowY1[0].scrollHeight
		});
	});



});