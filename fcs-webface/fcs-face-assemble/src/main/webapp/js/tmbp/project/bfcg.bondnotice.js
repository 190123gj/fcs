define(function(require, exports, module) {
	// 项目管理 > 授信前管理 > 承销/发债信息维护
	require('Y-msg');

	var util = require('util');
	var getList = require('zyw/getList');

	//选择客户
	var _getList = new getList();

	if ($('.fnChooseP').length) {
		var _getList = new getList();
		_getList.init({
			title: '项目列表',
			ajaxUrl: '/baseDataLoad/loadBondProjectDataByNotice.json',
			btn: '.fnChooseP',
			tpl: {
				tbody: ['{{each pageList as item i}}',
					'    <tr class="fn-tac m-table-overflow">',
					'        <td>{{item.projectCode}}</td>',
					'        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
					'        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
					'        <td>{{item.busiTypeName}}</td>',
					'        <td>{{item.amount}}</td>',
					'        <td><a class="choose" customerId="{{item.customerId}}" customerName="{{item.customerName}}" projectCode="{{item.projectCode}}" projectName="{{item.projectName}}" href="javascript:void(0);">选择</a></td>',
					'    </tr>',
					'{{/each}}'
				].join(''),
				form: ['项目名称：',
					'<input class="ui-text fn-w160" type="text" name="projectName">',
					'&nbsp;&nbsp;',
					'客户名称：',
					'<input class="ui-text fn-w160" type="text" name="customerName">',
					'&nbsp;&nbsp;',
					'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
				].join(''),
				thead: ['<th width="100">项目编号</th>',
					'<th width="120">客户名称</th>',
					'<th width="120">项目名称</th>',
					'<th width="100">授信类型</th>',
					'<th width="100">授信金额(元)</th>',
					'<th width="50">操作</th>'
				].join(''),
				item: 6
			},
			callback: function($a) {
				window.location.href = '/projectMg/consentIssueNotice/addNotice.htm?projectCode=' + $a.attr('projectCode')
			}
		});
	}

	//------ 保存通知单 start
	var $form = $('#form'),
		$fnPrint = $form.find('#fnPrint'),
		$fnInput = $form.find('input[type="text"]');
	$form.on('click', '.submit', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		var _isPass = true,
			_isPassEq;

		$fnInput.each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}
		});
		if (!_isPass) {
			Y.alert('提示', '表单还未填写完整', function() {
				$fnInput.eq(_isPassEq).focus();
			});
			_this.removeClass('ing');
			return;
		}

		//序列化表单、格式化内容
		//		var _formSerialize = $form.serialize();
		//		$fnPrint.find('.fnUserInput').each(function(index, el) {
		//			el.innerHTML = el.getElementsByTagName('input')[0].value;
		//		});

		var $div = $('<div></div>').html($fnPrint.html());
		$div.find('.fnUserInput').each(function(index, el) {
			el.innerHTML = $fnPrint.find('.fnUserInput').eq(index)[0].getElementsByTagName('input')[0].value;
		});

		$('#fnPrintHtml').val(escape($div.html().replace(/(^\s*|\s*$)/g, '')));

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			//			data: _formSerialize + '&html=' + escape($fnPrint.html().replace(/(^\s*|\s*$)/g, '')),
			success: function(res) {
				if (res.success) {
					if (_this.hasClass('fnApply')) {
						// browserPrint($div.html());
						util.direct('/projectMg/stampapply/addStampApply.htm?consentId=' + res.id, '/projectMg/stampapply/addStampApply.htm');
					} else {
						Y.alert('提示', '操作成功', function() {
							window.location.href = '/projectMg/consentIssueNotice/noticeList.htm';
						});
						//window.location.href = '/projectMg/consentIssueNotice/noticeList.htm';
					}
				} else {
					Y.alert('提示', res.message);
					_this.removeClass('ing');
				}
			}
		});
	}).on('click', '.fnPrint', function() {
		browserPrint($fnPrint.html());
	});

	//---打印
	function browserPrint(html) {
		window.document.body.innerHTML = html;
		window.print();
		setTimeout(function() {
			window.print();
			setTimeout(function() {
				window.location.reload(true);
			}, 2000)
		}, 500);
	}
	//------ 保存通知单 end

});