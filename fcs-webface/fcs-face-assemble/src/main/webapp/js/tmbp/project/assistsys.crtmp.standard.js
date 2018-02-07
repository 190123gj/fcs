define(function(require, exports, module) {
	// 辅助系统 > 合同管理  > 制式合同
	require('zyw/upAttachModify');
	require('Y-msg');

	var util = require('util');

	var $form = $('#form'),
		$contract = $('#contract'),
		$fnContractInput = $('.fnContractInput'),
		nullText = '';
	for (var i = 0; i < 5; i++) {
		nullText += '&nbsp;'
	}

	$form.submit(function(event) {

		doSubmit('SUBMIT');

		return false;

	});

	// 去掉之都属性
	$fnContractInput.removeProp('readonly');

	// 去掉table宽
	$contract.find('table').each(function(index, el) {
		el.width = '100%';
		el.style.width = '100%';
	});

	// 针对table的一些
	$fnContractInput.each(function(index, el) {

		var _$this = $(this),
			_len = 1,
			_width = 0;
		if (_$this.parents('td').length) {



			_len = _$this.parents('tr').find('td').length;
			_width = ($contract.width() - 200) / _len - 10;

			var _l = _$this.parents('td').find('.fnContractInput').length;

			if (_l > 1) {
				_width = _width / (_l + 1);
			}

			// _width = (_width > 50) ? 50 : _width;

			_$this.outerWidth(Math.floor(_width));

			// console.log(_width);

		}

	});

	// 返回
	$('#fnGoBack').on('click', function() {
		// close 只能关闭脚本打开的页面
		window.close();
	});

	// 保存数据
	function doSubmit(type) {

		document.getElementById('checkStatus').value = type ? '1' : '0';

		var _contract = '';

		// 序列化合同填写的内容

		$fnContractInput.each(function(index, el) {
			// 序列化
			_contract += '&projectContractExtraValue[' + index + '].documentName=' + el.name;
			_contract += '&projectContractExtraValue[' + index + '].documentValue=' + el.value;
			// 填充值
			$fnContractInput.eq(index).attr('value', el.value);
		});

		// 合同填充值、变化了后的html（带有input控件），合同模板从此写死
		$('input[name=content]').val(escape($contract.html()));
		//_contract += '&content=' + escape($contract.html());
		// 合同填充值后展示的html（不带有input控件）
		$fnContractInput.each(function(index, el) {

			// 有值就填充，无值就是短横线
			//是否被套了span 转换下 border 为 text-decoration
			var _$this = $(this),
				_$parents = _$this.parent().parent();

			if (_$parents[0].nodeName == 'SPAN' && _$parents.css('text-decoration') == 'underline') {
				_$parents.css('text-decoration', 'none');
			}

			var _obj = {
				border: 0
			}

			if (!_$this.hasClass('fnNoBottomLine') && !_$this.parents('tr').length) {
				_obj['text-decoration'] = 'underline';
			}

			// table里面的input 去掉
			_$this.parents('tr').find('span').each(function(index, el) {
				$(this).css('border-bottom-width', '0');
			});

			_$this.parent().css(_obj);

			if (el.value.replace(/\s/g, '')) {
				_$this.after(el.value).remove();
			} else {
				_$this.after(nullText).remove();
			}

		});
		$('input[name=contentMessage]').val(escape($contract.html()));
		//_contract += '&contentMessage=' + escape($contract.html());

		//添加表单其他默认、需要的值
		_contract += '&' + $form.serialize();

		util.ajax({
			url: $form.attr('action'),
			data: _contract,
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '已保存', function() {
						// window.location.href = '/projectMg/contract/templateList.htm';
						// 2016.10.10 刷新申请页
						window.opener.top.location.hash = 'refresh';
						// 2016.06.06 页面直接关闭
						window.close();
					});
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

	}

	//------ 侧边工具栏 start
	var publicOPN = new(require('zyw/publicOPN'))();

	if (document.getElementById('submit')) {
		publicOPN.addOPN([{
			name: '暂存',
			alias: 'save',
			event: function() {

				doSubmit();

			}
		}]);
	}

	publicOPN.init().doRender();
	// (new(require('zyw/publicOPN'))()).addOPN([{
	// 	name: '暂存',
	// 	alias: 'save',
	// 	event: function() {

	// 		doSubmit();

	// 	}
	// }]).init().doRender();
	//------ 侧边工具栏 end

});