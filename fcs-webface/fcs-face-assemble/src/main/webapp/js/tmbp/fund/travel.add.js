/**
 * 页面涉及到新增和签报
 *
 * 签报和新增验证规则一致，某些交互不同
 *
 * 验证规则通过控件上的class命添加
 * 
 */

define(function(require, exports, module) {
	// 客户管理 > 个人客户 > 新增、编辑
	var REQUIRERULES = require('./travel.enterprise.add');

	require('zyw/upAttachModify');

	require('Y-msg');

	require('validate');
	require('validate.extend');

	var util = require('util');
	var getList = require('zyw/getList');
	var formStatus = $('#formStatus').val();
	$form = $('#form');

	// 提交
	$('#fnDoSubmit').on('click', function() {
		fnSubmit(true);
	});
	
	// 暂存
	function fnSubmit(boole) {
		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {
				if (res.success) {
					if (boole) {
						//提交表单
						Y.confirm('提示', '确认提交当前表单？', function(opn) {

							if (opn == 'yes') {
//								util.ajax({
//									url: '/projectMg/form/submit.htm',
//									data: {
//										formId: res.form.formId,
//										_SYSNAME:'FM'
//									},
//									success: function(res2) {
//										Y.alert('提示', res2.message, function() {
//											if (res2.success) {
//												window.location = '/fundMg/travelExpense/travellist.htm';
//											}
//										});
//
//									}
//								});
								util.postAudit({
		                            formId: res.form.formId,
		                            _SYSNAME: 'FM'
		                        }, function () {
		                            window.location.href = '/fundMg/travelExpense/travellist.htm';
		                        })
							}
						});

					} else {
						Y.alert('提示', '操作成功', function() {
							window.location = '/fundMg/travelExpense/travellist.htm';
						});
					}
				} else {
					Y.alert('操作失败', res.message)
				}
			}
		});

	}
	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	
	if (formStatus=="" || formStatus=="DRAFT" || formStatus=="BACK") {
		publicOPN.addOPN([{
			name: '暂存',
			alias: 'doSave',
			event: function() {
				fnSubmit(false);
			}
		}]);
	}
	
	publicOPN.init().doRender();
	//------ 侧边栏 end
	
	//------ 选择项目 start
	var _getList = new getList();
	_getList.init({
		title: '理财项目列表',
		ajaxUrl: '/baseDataLoad/financialTransferOrRedeemProject.json?hasHoldNum=IS',
		btn: '#chooseBtn',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td>{{item.projectCode}}</td>',
				'        <td>{{item.productName}}</td>',
				'        <td title="{{item.issueInstitution}}">{{(item.issueInstitution && item.issueInstitution.length > 6)?item.issueInstitution.substr(0,6)+\'\.\.\':item.issueInstitution}}</td>',
				'        <td>{{item.holdNum}}</td>',
				'        <td>{{item.buyDate}}</td>',
				'        <td>{{item.interestRate}}%</td>',
				'        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['产品名称：',
				'<input class="ui-text fn-w160" type="text" name="productName">',
				'&nbsp;&nbsp;',
				'发行机构：',
				'<input class="ui-text fn-w160" type="text" name="issueInstitution">',
				'&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th width="100">理财项目编号</th>',
				'<th width="120">产品名称</th>',
				'<th width="120">发行机构</th>',
				'<th width="200">持有份数</th>',
				'<th width="120">申购日</th>',
				'<th width="100">年化收益率</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 7
		},
		callback: function($a) {
			window.location.href = '/projectMg/financialProject/redeem/form.htm?projectCode=' + $a.attr('projectCode');
		}
	});
	
	var BPMiframe = require('BPMiframe');
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true&isExternal=true', {
		'title': '组织选择器',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	$('#applyDeptBtn').on('click', function() {
		singleSelectUserDialog.init(function(relObj) {
			$('#expenseDeptId').val(relObj.orgId); //多选用逗号隔开
			$('#applyDeptName').val(relObj.orgName); //多选用逗号隔开	
		});

	});
	
//	//------ 列席人员 start
//	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
//	// 初始化弹出层
//	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=false', {
//		'title': '人员',
//		'width': 850,
//		'height': 460,
//		'scope': '{type:\"system\",value:\"all\"}',
//		'selectUsers': {
//			selectUserIds: '', //已选id,多用户用,隔开
//			selectUserNames: '' //已选Name,多用户用,隔开
//		},
//		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
//		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
//	});

//	var participantsName = document.getElementById('participantsName'),
//		participantsId = document.getElementById('participantsId'),
//		participantsKey = document.getElementById('participantsKey');

	// 添加选择后的回调，以及显示弹出层
	$('#membersBtn').on('click', function() {

//		singleSelectUserDialog.obj.selectUsers.selectUserIds = participantsId.value;
//		singleSelectUserDialog.obj.selectUsers.selectUserNames = participantsName.value;

//		singleSelectUserDialog.init(function(relObj) {

//			participantsId.value = relObj.userIds;
//			participantsName.value = relObj.fullnames;

//			var _arr = relObj.userIds ? relObj.userIds.split(',') : [],
//				_arrs = [];
//
//			for (var i = 0; i < _arr.length; i++) {
//				_arrs.push('0');
//			}
//
//			participantsKey.value = _arrs.length ? _arrs.toString() : '';

//		});

	});

	//------ 列席人员 end
});