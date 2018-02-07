define(function(require, exports, module) {

	//上传
	require('zyw/upAttachModify');

	//弹窗
	var popupWindow = require('zyw/popupWindow');

	$('#queryUserBtn').click(function(event) {

		var $thisBtn = $(this),
			_projectCode = $('[name="projectCode"]').val();

		// if (!_projectCode) {

		//     hintPopup('请先选择项目编号')
		//     return false;

		// }

		fundDitch = new popupWindow({

			YwindowObj: {
				content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
				closeEle: '.close', //find关闭弹窗对象
				dragEle: '.newPopup dl dt' //find拖拽对象
			},

			ajaxObj: {
				url: '/customerMg/evaluting/selectCustomer.json',
				type: 'post',
				dataType: 'json',
				data: {
					// isChargeNotification: "IS",
					// projectCode: _projectCode,
					pageSize: 6,
					pageNumber: 1
				}
			},

			pageObj: { //翻页
				clickObj: '.pageBox a.btn', //find翻页对象
				attrObj: 'page', //翻页对象获取值得属性
				jsonName: 'pageNumber', //请求翻页页数的dataName
				callback: function($Y) {

					//console.log($Y)

				}
			},

			callback: function($Y) {

				$Y.wnd.on('click', 'a.choose', function(event) {

					var $this = $(this),
						customerName = $this.parents('tr').find('td:eq(1)').text(),
						customerId = $this.parents('tr').find('td:eq(1)').attr('customerId');

					window.location.href = '/customerMg/evaluting/add.htm?customerName=' + encodeURIComponent(customerName) + "&showType=KHGK&customerId=" + customerId;

					$Y.close();

				});

			},

			console: false //打印返回数据

		});

	});

	addClassFun = function(index, el) {

		var $this, name;

		$this = $(el);
		name = $this.attr('name');

		$this.attr('name', name ? name + '_destroyName' : name)
			.addClass('cancel checkStatusCancel');

		//	        if (name != undefined && name.indexOf("].") > 0) {
		//                name = name.substring(name.indexOf("].") + 2);
		//            }
		//
		//            if ($.inArray(name, allWhetherNullString.split(',')) != -1) {
		//
		//                $this.addClass('fnAuditRequired');
		//
		//            }

	}

	removeClassFun = function(index, el) {

		var $this, name;

		$this = $(el);
		name = $this.attr('name');

		$this.attr('name', name ? name.replace(/_destroyName/g, '') : name)
			.removeClass('cancel checkStatusCancel');
		//$this.removeClass('fnAuditRequired')

	}


	var $gradeSelect = $('#gradeSelect');
	//页面dom操作
	$('body').on('change', '#aboutRadio [type="radio"]', function(event) {

		var index = $(this).index(),
			$step = $('.step'),
			$about = $('#about').children();

		$about.eq(index).show().find('input,select').each(removeClassFun).end()
			.siblings().hide().find('input,select').each(addClassFun);

		index ? $step.hide() : $step.show();

		$('.cancels').addClass('cancel checkStatusCancel');
		if ($('.cancels').length && $('.cancels').val()) $gradeSelect.val($('.cancels').val());

	}).find('#aboutRadio [type="radio"]:checked').trigger('change');

	$('body').on('change', '.qualifiedCheckbox', function(event) {

		var judge = $(this).attr('checked')

		if (judge) {

			$('.qualified').hide().find('input,select').each(addClassFun);
			$('.onQualified').show().find('input,select').each(removeClassFun);
			$('.steps').hide();

		} else {

			$('.qualified').show().find('input,select').each(removeClassFun);
			$('.onQualified').hide().find('input,select').each(addClassFun);
			$('body').find('#aboutRadio [type="radio"]:checked').trigger('change');
			//$('.step').show();
		}

		$('.cancels').addClass('cancel checkStatusCancel');
		if ($('.cancels').length && $('.cancels').val()) $gradeSelect.val($('.cancels').val());


	}).find('.qualifiedCheckbox').trigger('change');



	//按钮展示情况控制
	var active, length;

	active = parseInt($('#step li.active').index());
	length = $('#step li').length;

	if (active == 0) {

		$('[stepatt="prev"]').remove();

	} else if (active == length - 1) {

		$('[stepatt="next"]').remove();

	}

	//计算总分

	$('body').on('keyup', '.countScore', function(event) {

		var $countScore, sum;

		$countScore = $('.countScore');
		sum = 0;

		$countScore.each(function(index, el) {

			sum += parseFloat($(el).val() || 0);

		});

		$('#sumScore').val(sum.toFixed(1));

	});

	$('body').on('keydown', '#customerName', function(event) {

		if (event.keyCode == 13) return false;

	});



})