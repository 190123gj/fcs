define(function(require, exports, module) {
	// 项目管理 > 风险管控 > 上会申报记录
	var util = require('util');
	var getList = require('zyw/getList');

	require('Y-msg');
	require('input.limit');
	require('zyw/upAttachModify');

	var _getList = new getList();
	_getList.init({
		ajaxUrl: '/baseDataLoad/queryProjects.json?phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES&isAdd=YES&isRepay='+$("input[name=isRepay]:checked").val(),
		btn: '#choose',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td>{{item.projectCode}}</td>',
				'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
				'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
				'        <td>{{item.busiTypeName}}</td>',
				'        <td>{{item.amount}}</td>',
				'        <td><a class="choose" projectcode="{{item.customerName}}" href="javascript:void(0);">选择</a></td>',
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
			window.location.href = '/projectMg/riskHandle/edit.htm?customerName=' + $a.attr('projectcode')+"&date="+new Date().getTime();
		}
	});

	var $fnInput = $('.fnInput'),
		$form = $('#form');

	$form.on('click', '#submit', function() {

		var _this = $(this);

		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		var _isPass = true,
			_isPassEq;

		$('.fnInput').each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}
		});

		if (!_isPass) {

			Y.alert('提醒', '请把表单填写完整', function() {
				$('.fnInput').eq(_isPassEq).focus();
			});
			_this.removeClass('ing');
			return;
		}

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {
				if (res.success) {
					window.location.href = '/projectMg/riskHandle/list.htm';
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

	}).on('click', '#pass,#noPass', function() {

		var isPass = false;

		if (this.id == 'pass') {
			isPass = true;
		}

		document.getElementById('isPass').value = isPass;

		if (!!!document.getElementById('idea').value) {
			Y.alert('提示', '请输入审批意见');
			return;
		}

		doSubmit('SUBMIT');

	});


	function doSubmit(type) {

		//暂存0 提交1
		var _isSava = type ? false : true;
		document.getElementById('checkStatus').value = _isSava ? '0' : '1';

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {
				if (res.success) {
					if (!_isSava) {

//						util.ajax({
//							url: '/projectMg/form/submit.htm',
//							data: {
//								formId: res.form.formId
//							},
//							success: function(res2) {
//								Y.alert('提示', res2.message, function() {
//									if (res2.success) {
//										window.location = '/projectMg/riskHandle/list.htm';
//									}
//								});
//							}
//						});
						
						util.postAudit({
                            formId: res.form.formId
                        }, function () {
                            window.location.href = '/projectMg/riskHandle/list.htm';
                        })
						
					} else {
						Y.alert('提示', '已保存', function() {
							window.location.href = '/projectMg/riskHandle/list.htm';
						});
					}
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

	}

	//------ 侧边工具栏 start
	(new(require('zyw/publicOPN'))()).addOPN([{
		name: '暂存',
		alias: 'save',
		event: function() {
			doSubmit();
		}
	}]).init().doRender();
	//------ 侧边工具栏 end


});