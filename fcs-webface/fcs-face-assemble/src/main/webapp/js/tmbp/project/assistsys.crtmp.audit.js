define(function(require, exports, module) {
	// 辅助系统 > 合同管理  > 制式合同
	require('zyw/upAttachModify');
	require('Y-window');
	require('Y-msg');

	var util = require('util');

	var $audit = $('#audit'),
		$contract = $('#contract'),
		$fnContractInput = $('.fnContractInput'),
		nullText = '';
	for (var i = 0; i < 5; i++) {
		nullText += '&nbsp;'
	}

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
			_width = ($contract.width() - 100) / _len - 10;

			var _l = _$this.parents('td').find('.fnContractInput').length;

			if (_l > 1) {
				_width = _width / (_l + 1);
			}

			// _width = (_width > 50) ? 50 : _width;

			_$this.outerWidth(Math.floor(_width));

			// console.log(_width);

		}

	});

	$audit.submit(function() {

		var _idea = document.getElementById('idea');

		if (!!!_idea.value.replace(/\s/g, '')) {
			Y.alert('提示', '请填写审核意见', function() {
				_idea.focus();
			});
			return false;
		}

		//序列化
		var _contract = '';

		$fnContractInput.each(function(index, el) {
			// 序列化
			_contract += '&projectContractExtraValue[' + index + '].documentName=' + el.name;
			_contract += '&projectContractExtraValue[' + index + '].documentValue=' + el.value;
			// 填充值
			$fnContractInput.eq(index).attr('value', el.value);
		});

		// 合同填充值、变化了后的html（带有input控件），合同模板从此写死
		$('input[name=content]').val(escape($contract.html()));
		// 合同填充值后展示的html（不带有input控件）
		$fnContractInput.each(function(index, el) {
			// 有值就填充，无值就是短横线
			// if (el.value.replace(/\s/g, '')) {
			// 	$(el).after(el.value).remove();
			// }

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

		//添加表单其他默认、需要的值
		_contract += '&' + $audit.serialize();

		util.ajax({
			url: $audit.attr('action'),
			data: _contract,
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '已保存', function() {
						window.close();
					});
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

		return false;
	});

	// 返回
	$('#fnGoBack').on('click', function() {
		// close 只能关闭脚本打开的页面
		window.close();
	});

	// $('#fnPrint').on('click', function() {
	// 	document.getElementsByTagName('body')[0].innerHTML = document.getElementById('contract').innerHTML;
	// 	setTimeout(function(){
	// 		window.print();
	// 	},500);

	// });

	// 查看修改记录
	var publicOPN = new(require('zyw/publicOPN'))();
	//通过页面不同的值，判断是否添加到侧边栏
	if (document.getElementById('fnModifyBox')) {
		publicOPN.addOPN([{
			name: '查看修改记录',
			alias: 'modificationRecord',
			event: function() {
				$('body').Y('Window', {
					content: '#fnModifyBox',
					simple: true,
					modal: false,
					closeEle: '.close'
				});
			}
		}]);
	}

	publicOPN.init().doRender();

});