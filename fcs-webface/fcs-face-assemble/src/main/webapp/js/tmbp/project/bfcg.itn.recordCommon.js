define(function(require, exports, module) {

	var hintPopup = require('zyw/hintPopup');
	require('Y-htmluploadify');
	//js引擎
	var template = require('arttemplate');

	function recordCommon(rulesAll) {

		$('.fnUpFile').click(function() {
			var htmlupload = Y.create('HtmlUploadify', {
				key: 'up1',
				uploader: '/projectMg/investigation/uploadExcel.htm?type=leaders',
				multi: false,
				auto: true,
				addAble: false,
				fileTypeExts: '*.xls',
				fileObjName: 'UploadFile',
				onQueueComplete: function(a, b) {},
				onUploadSuccess: function($this, data, resfile) {

					var JSONdata = JSON.parse(data),
						test = $('#test');

					if (JSONdata.success) {

						var Html = template('testDataList', JSONdata);

						test.html(Html).find('[name]').each(function(index, el) {

							var $el = $(el);

							$.fn.addValidataFun($el, rulesAll);

						});



					} else {

						hintPopup(JSONdata.message);

					}


				}

			});

		});

		function recordCommonFun($target, orderName, orderNameIndex) {

			$target.find('tr').each(function(index, el) { //添加orderNameChildren

				var $el, orderNameChildren, $index;

				$el = $(el);
				$index = index
				orderNameChildren = $el.attr('orderNameChildren');

				$el.find('[name]').each(function(index, el) {

					var $nameEl, name;

					$nameEl = $(el);
					name = $nameEl.attr('name');

					if (name != undefined && name.indexOf("].") > 0) {
						name = name.match(/.*\.(.*)/)[1];
					}

					$nameEl.attr('name', orderName + '[' + orderNameIndex + '].' + orderNameChildren + '[' + $index + '].' + name);
					$.fn.addValidataFun($nameEl, rulesAll);

				});


			});

		}

		//高管子项增加
		$('body').on('click', '.recordAdd', function(event) {

			var $this, $target, Html, orderName, orderNameIndex;

			$this = $(this);
			$target = $this.parents('thead').next();
			Html = $('#t-test-children').html();
			orderName = $this.parents('[orderName]').attr('orderName');
			orderNameIndex = $this.parents('[orderName]').index();

			$target.append(Html); //添加Dom

			recordCommonFun($target, orderName, orderNameIndex);

		}).on('click', '.recordDelete', function(event) { //删除

			var $this, $target, $tr, orderName, orderNameIndex;

			$this = $(this);
			$tr = $this.parents('tr');
			$target = $this.parents('tbody');
			orderName = $this.parents('[orderName]').attr('orderName');
			orderNameIndex = $this.parents('[orderName]').index();

			$tr.remove();

			recordCommonFun($target, orderName, orderNameIndex);

		});

		//搜索框时间限制
		// $('body').on('blur', '.fnListSearchDateS', function() {

		// 	var $p = $(this).parents('.fnListSearchDateItem'),
		// 		$input = $p.find('.fnListSearchDateE');

		// 	$input.attr('onclick', 'laydate({min: "' + this.value + '",format: "YYYY"})');

		// }).on('blur', '.fnListSearchDateE', function() {

		// 	var $p = $(this).parents('.fnListSearchDateItem'),
		// 		$input = $p.find('.fnListSearchDateS');

		// 	$input.attr('onclick', 'laydate({max: "' + this.value + '",format: "YYYY"})');

		// }).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');

		var yearsTime = require('zyw/yearsTime');
		$('body').on('focus', '.birth', function(event) {
			var yearsTimeFirst = new yearsTime({
				elem: this,
				format: 'YYYY-MM',
				// soFar: true,
				callback: function(_this, _time) {
					_this.val(_time[0] + '-' + ((_time[1].length == 1) ? '0' + _time[1] : _time[1]));
				}
			});
		});


		$('body').on('focus', '.birthStart', function(event) {
			var yearsTimeFirst = new yearsTime({
				elem: this,
				format: 'YYYY',
				max: function(This) {
					return $(This).parents('tr').find('.birthStot').val();
				}
			});
		});

		$('body').on('focus', '.birthStot', function(event) {
			var yearsTimeFirst = new yearsTime({
				elem: this,
				format: 'YYYY',
				soFar: true,
				min: function(This) {
					return $(This).parents('tr').find('.birthStart').val();
				}
			});
		});

	}

	module.exports = recordCommon;


})