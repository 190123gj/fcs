/*
 * @Author: erYue
 * @Date:   2016-07-14 14:29:18
 * @Last Modified by:   erYue
 * @Last Modified time: 2016-07-14 14:48:38
 */

define(function(require, exports, module) {
	require('Y-msg');
	require('zyw/publicPage')
	var util = require('util');
	
	//操作相似的ajax请求，合并数据
	var ajaxObj = {
		withdraw: {
			url: '/projectMg/form/cancel.htm',
			message: '已撤销',
			opn: '撤回'
		},
		del: {
			url: '/projectMg/form/delete.htm',
			message: '已删除',
			opn: '删除'
		}
	};
	
	$('#fnListSearchForm').on('click', '.del', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');
		var _thisType = util.getJqHasClass(_this, ajaxObj);
		Y.confirm('信息提醒', '您确定要' + ajaxObj[_thisType].opn + '？', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url: ajaxObj[_thisType].url,
					data: {
						formId: _this.parents('tr').attr('itemid'),
						_SYSNAME: "FM"
					},
					dataType: 'json',
					type: 'POST',
					success: function(res) {
						if (res.success) {
							Y.alert('消息提醒', ajaxObj[_thisType].message);
							window.location.reload(true);
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
	
//	var getList = require('zyw/getList');
//	// 选择项目
//	var _getList = new getList();
//	_getList
//			.init({
//				title : '项目列表',
//				ajaxUrl : '/baseDataLoad/queryProjects.json?phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,RE_COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES',
//				btn : '.chooseBtn',
//				tpl : {
//					tbody : [
//							'{{each pageList as item i}}',
//							'    <tr class="fn-tac m-table-overflow">',
//							'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
//							'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
//							'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
//							'        <td>{{item.busiTypeName}}</td>',
//							'        <td>{{item.amount}}</td>',
//							'        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>', // 跳转地址需要的一些参数
//							'    </tr>', '{{/each}}' ].join(''),
//					form : [
//							'项目名称：',
//							'<input class="ui-text fn-w160" type="text" name="projectName">',
//							'&nbsp;&nbsp;',
//							'客户名称：',
//							'<input class="ui-text fn-w160" type="text" name="customerName">',
//							'&nbsp;&nbsp;',
//							'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ]
//							.join(''),
//					thead : [ '<th width="100">项目编号</th>',
//							'<th width="120">客户名称</th>',
//							'<th width="120">项目名称</th>',
//							'<th width="100">授信类型</th>',
//							'<th width="100">授信金额(万元)</th>',
//							'<th width="50">操作</th>' ].join(''),
//					item : 6
//				},
//				callback : function($a) {
//					// 跳转地址
//					document.getElementById('projectCode').value = $a
//							.attr('projectCode');
//				}
//			});
//
//	// 清除输入框
//	$('#businessTypeClear').on('click', function() {
//		$('#projectCode').val('');
//	});
})