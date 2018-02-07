define(function(require, exports, module) {
	//风险审查报告
	require('Y-msg');
	var util = require('util');
	//------ 新增 风险审查报告 start
	//
	//var getList = require('zyw/getList');
	var formId = util.getParam('formId');
	var $addFormDBWD = $('#addFormDBWD');
	var $addFormSS = $('#addFormSS');

	var searchForDetails = require('zyw/searchForDetails');

	//新增才加载
	if (!!!formId) {
		searchForDetails({
			ajaxListUrl: '/baseDataLoad/waitRiskReviewProject.json',
			btn: '#choose',
			codeInput: 'projectNumber',
			tpl: {
				tbody: ['{{each pageList as item i}}',
					'    <tr class="fn-tac m-table-overflow">',
					'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
					'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
					'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
					'        <td>{{item.busiTypeName}}</td>',
					'        <td>{{item.amount}}</td>',
					'        <td><a class="choose" projectNumber="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
					'    </tr>',
					'{{/each}}'
				].join(''),
				form: ['项目名称：',
					'<input class="ui-text fn-w160" type="text">',
					'&nbsp;&nbsp;',
					'客户名称：',
					'<input class="ui-text fn-w160" type="text">',
					'&nbsp;&nbsp;',
					'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
				].join(''),
				thead: ['<th width="100">项目编号</th>',
					'<th width="120">客户名称</th>',
					'<th width="120">项目名称</th>',
					'<th width="100">授信类型</th>',
					'<th width="100">授信金额</th>',
					'<th width="50">操作</th>'
				].join(''),
				item: 6
			},
			ajaxDetailsUrl: '/baseDataLoad/loadFProjectData.json',
			callback: function(_data) {
				$addFormDBWD.find('#creditAmount').val(_data.amount.amount);
				$addFormDBWD.find('#timeLimitCode').val(_data.timeLimit);
				$addFormDBWD.find('#timeLimitName').text(_data.timeUnit);
				$addFormDBWD.find('#creditRate').val(0);
				$addFormDBWD.find('#projectName').val(_data.projectName);
				$addFormDBWD.find('#creditType').val(_data.busiTypeName);
			}
		});
		// var _getList = new getList();
		// _getList.init({
		// 	title: '选择',
		// 	ajaxUrl: '/baseDataLoad/waitRiskReviewProject.json',
		// 	btn: '#choose',
		// 	input: ['projectNumber'],
		// 	tpl: {
		// 		tbody: ['{{each pageList as item i}}',
		// 			'    <tr class="fn-tac m-table-overflow">',
		// 			'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
		// 			'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
		// 			'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
		// 			'        <td>{{item.busiTypeName}}</td>',
		// 			'        <td>{{item.amount}}</td>',
		// 			'        <td><a class="choose" projectNumber="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
		// 			'    </tr>',
		// 			'{{/each}}'
		// 		].join(''),
		// 		form: ['项目名称：',
		// 			'<input class="ui-text fn-w160" type="text">',
		// 			'&nbsp;&nbsp;',
		// 			'客户名称：',
		// 			'<input class="ui-text fn-w160" type="text">',
		// 			'&nbsp;&nbsp;',
		// 			'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
		// 		].join(''),
		// 		thead: ['<th width="100">项目编号</th>',
		// 			'<th width="120">客户名称</th>',
		// 			'<th width="120">项目名称</th>',
		// 			'<th width="100">授信类型</th>',
		// 			'<th width="100">授信金额</th>',
		// 			'<th width="50">操作</th>'
		// 		].join(''),
		// 		item: 6
		// 	}
		// });
	}
	//------ 新增 DBWD start
	$('#fnLook').on('click', function(e) {
		e.preventDefault();
		var _idCode = document.getElementById('projectNumber').value;
		if (_idCode) {
			util.direct(this.href + document.getElementById('projectNumber').value, '/projectMg/investigation/list.htm');
			// window.location.href = this.href + document.getElementById('projectNumber').value;
		} else {
			Y.alert('提示', '请选择项目');
		}
	});
	//点击增加
	$addFormDBWD.on('change', '#projectNumber', function() {

		// var self = this;

		// util.ajax({
		// 	url: '/baseDataLoad/loadFProjectData.json',
		// 	data: {
		// 		projectCode: self.value
		// 	},
		// 	success: function(res) {
		// 		if (res.success) {
		// 			var _data = res.data;
		// 			$addFormDBWD.find('#creditAmount').val(_data.amount.amount);
		// 			$addFormDBWD.find('#timeLimitCode').val(_data.timeLimit);
		// 			$addFormDBWD.find('#timeLimitName').text(_data.timeUnit);
		// 			$addFormDBWD.find('#creditRate').val(0);
		// 			$addFormDBWD.find('#projectName').val(_data.projectName);
		// 			$addFormDBWD.find('#creditType').val(_data.busiTypeName);
		// 		} else {
		// 			Y.alert('提示', res.message);
		// 		}
		// 	}
		// });

	}).on('click', '#add', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		if (!document.getElementById('projectNumber').value) {
			Y.alert('提示', '请填写项目编号');
			_this.removeClass('ing');
			return;
		}

		if (!document.getElementById('report').value) {
			Y.alert('提示', '请填写风险审查报告');
			_this.removeClass('ing');
			return;
		}
		$.ajax({
			url: '/projectMg/riskreview/addRiskReview.json',
			data: $addFormDBWD.serialize(),
			dataType: 'json',
			type: 'POST',
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '提交成功', function() {
						location.href = '/projectMg/riskreview/riskReviewList.htm?size=5&page=1';
					});
				} else {
					_this.removeClass('ing');
					Y.alert('提示', res.message);
				}
			}
		});

	});
	//------ 新增 DBWD end
	//
	//------ 新增 SS start
	var $fnMustInput = $('.fnMustInput');
	$addFormSS.on('change', '#projectNumber', function() {

		var self = this;

		util.ajax({
			url: '/baseDataLoad/loadFProjectData.json',
			data: {
				projectCode: self.value
			},
			success: function(res) {
				if (res.success) {
					var _data = res.data;
					$addFormSS.find('#applyName').val(_data.amount.amount);
					$addFormSS.find('#preserveAmount').val(_data.timeLimit);
					$addFormSS.find('#guaranteeRate').val(_data.timeUnit);
					$addFormSS.find('#agency').val('');
					$addFormSS.find('#consultFees').val(_data.projectName);
					$addFormSS.find('#verifier').val(_data.busiTypeName);
					$addFormSS.find('#reviewTime').val(_data.busiTypeName);
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

	}).on('click', '#add', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		var _isPass = true;
		$fnMustInput.each(function(index, el) {
			if (!!!el.value) {
				_isPass = false;
			}
		});
		if (!_isPass) {
			Y.alert('提示', '请填写风险审查报告');
			_this.removeClass('ing');
			return;
		}

		//ajax
	});
	//------ 新增 SS end

	//------ 新增 风险审查报告 end


	//------更新 风险审查报告 start

	$('#update').on('click', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		if (!document.getElementById('number').value) {
			Y.alert('提示', '请填写项目编号');
			_this.removeClass('ing');
			return;
		}

		if (!document.getElementById('report').value) {
			Y.alert('提示', '请填写风险审查报告');
			_this.removeClass('ing');
			return;
		}

		$.ajax({
			url: '/projectMg/riskreview/updateRiskReview.json',
			data: $('#updateForm').serialize(),
			dataType: 'json',
			type: 'POST',
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '提交成功', function() {
						//	location.href = '/list';
					});
				} else {
					_this.removeClass('ing');
					Y.alert('提示', res.message);
				}
			}
		});

	});

	//------ 新增 风险审查报告 end
	//
	//------ 审核 风险审查报告 start

	var ajaxObj = {
		end: {
			url: '/url',
			message: '已终止',
			opn: '终止'
		},
		back: {
			url: '/url',
			message: '已返回',
			opn: '返回'
		}
	}

	$('#auditForm').on('click', 'input.pass', function() {
		//通过
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		if (!document.getElementById('idea').value) {
			Y.alert('提示', '请填写风险审查意见');
			_this.removeClass('ing');
			return;
		}
		$.ajax({
			url: '/url',
			data: $('#addForm').serialize(),
			dataType: 'json',
			type: 'POST',
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '提交成功', function() {
						location.href = '/list';
					});
				} else {
					_this.removeClass('ing');
					Y.alert('提示', res.message);
				}
			}
		});

	}).on('click', 'input.end,input.back', function() {
		//终止 返回
		var _this = $(this),
			_thisType = util.getJqHasClass(_this, ajaxObj);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');
		Y.confirm('提示', '您确定要' + ajaxObj[_thisType].opn + '该报告？', function(opn) {
			if (opn == 'yes') {
				$.ajax({
					url: ajaxObj[_thisType].url,
					data: {
						id: _this.attr('itemID')
					},
					dataType: 'json',
					type: 'POST',
					success: function(res) {
						if (res.success) {
							Y.alert('消息提醒', ajaxObj[_thisType].message);
						} else {
							_this.removeClass('ing');
							Y.alert('消息提醒', res.message);
						}
					}
				});
			} else {
				_this.removeClass('ing');
			}
		});

	});


	//------ 审核 风险审查报告 end


});