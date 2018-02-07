define(function(require, exports, module) {
	// 项目管理 > 项目列表
	require('Y-msg');

	var getList = require('zyw/getList');
	var util = require('util');

	// ------ 选项目 start
	var projectList = new getList();
	projectList.init({
		width: '90%',
		title: '项目列表',
		ajaxUrl: '/baseDataLoad/queryProjects.json?setAuthCondition=YES',
		btn: '#choose',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td class="item">{{item.projectCode}}</td>',
				'        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6) + \'\.\.\':item.customerName}}</td>',
				'        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6) + \'\.\.\':item.projectName}}</td>',
				'        <td>{{item.busiTypeName}}</td>',
				'        <td>{{item.amount}}</td>',
				'        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
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
			window.location.href = '/projectMg/managerbchange/form.htm?projectCode=' + $a.attr('projectCode');
		}
	});
	// ------ 选项目 end

	// ------ 选择B角 start
	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
		'title': '人员',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	var fnBManName = document.getElementById('fnBManName'),
		fnBManId = document.getElementById('fnBManId'),
		fnBManAccount = document.getElementById('fnBManAccount');
	// 添加选择后的回调，以及显示弹出层
	$('#fnChooseB').on('click', function() {

		//这里也可以更新已选择用户
		singleSelectUserDialog.obj.selectUsers = {
			selectUserIds: fnBManId.value, //已选id,多用户用,隔开
			selectUserNames: fnBManName.value, //已选Name,多用户用,隔开
			selectUserAccounts: fnBManAccount.value
		}

		singleSelectUserDialog.init(function(relObj) {

			fnBManId.value = relObj.userIds;
			fnBManName.value = relObj.fullnames;
			fnBManAccount.value = relObj.accounts;

		});

	});
	// ------ 选择B角 end


	// ------ 选择机制 start
	$('.fnReplaceM').on('click', function() {
		changeM(this.value)
	});
	changeM($('.fnReplaceM:checked').val());

	function changeM(m) {

		if (m == 'DIRECT' || !!!m) {
			$('#fnfnReplaceMTime, #fnfnReplaceMPhase').addClass('fn-hide').find('input').prop('disabled', 'disabled');
		}

		if (m == 'PERIOD') {
			$('#fnfnReplaceMTime').removeClass('fn-hide').find('input').removeProp('disabled');
			$('#fnfnReplaceMPhase').addClass('fn-hide').find('input').prop('disabled', 'disabled');
		}

		if (m == 'PHASES') {
			$('#fnfnReplaceMTime').addClass('fn-hide').find('input').prop('disabled', 'disabled');
			$('#fnfnReplaceMPhase').removeClass('fn-hide').find('input').removeProp('disabled');
		}

	}
	// ------ 选择机制 end

	// ------ 提交 start
	function doSubmit(isSave) {

		if (!!!document.getElementById('projectCode').value) {
			Y.alert('提示', '请选择项目');
			return;
		}

		document.getElementById('checkStatus').value = isSave ? '1' : '0';

		if (isSave) {

			if (!!!fnBManName.value) {
				Y.alert('提示', '请选择B角');
				return;
			}

			var fnReplaceM = $('.fnReplaceM:checked').val();

			if (!!!fnReplaceM) {
				Y.alert('提示', '请选择更换机制');
				return;
			}

			if (fnReplaceM == 'PERIOD') {

				if (!!!$fnStartTime.val() || !!!$fnEndTime.val()) {
					Y.alert('提示', '请选择更换时间');
					return;
				}

			}

			if (fnReplaceM == 'PHASES') {

				var mhaseLength = $('.fnfnReplaceMPhase:checked').length;

				if (mhaseLength == 0) {
					Y.alert('提示', '请选择阶段');
					return;
				}

			}

		}


		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {

				if (res.success) {

					if (!isSave || document.getElementById('fnIsDirector').value == "IS") {
						
						Y.alert('提示', '保存成功', function() {
							window.location.href = '/projectMg/managerbchange/list.htm';
						})

					} else {

//						util.ajax({
//							url: '/projectMg/form/submit.htm',
//							data: {
//								formId: res.form.formId
//							},
//							success: function(xhr) {
//
//								Y.alert('提示', xhr.success ? '提交成功' : '提交失败', function() {
//									if(xhr.success){
//										window.location.href = '/projectMg/managerbchange/list.htm';
//									}
//								});
//
//							}
//						});
						util.postAudit({
                            formId: res.form.formId
                        }, function () {
                        	window.location.href = '/projectMg/managerbchange/list.htm';
                        })

					}

				} else {
					Y.alert('保存失败', res.message);
				}

			}
		});


	}
	// ------ 提交 end


	var $form = $('#form'),
		$fnStartTime = $('#fnStartTime'),
		$fnEndTime = $('#fnEndTime');

	$fnStartTime.on('blur', function() {
		$fnEndTime.attr('onclick', 'laydate({"min": "' + this.value + '"})');
	});
	$fnEndTime.on('blur', function() {
		$fnStartTime.attr('onclick', 'laydate({"max": "' + this.value + '"})');
	});

	$form.on('click', '#submit', function() {
		doSubmit(true);
	});

	//------ 侧边栏 start
	//
	var publicOPN = new(require('zyw/publicOPN'))();

	if (!!$('#projectCode').val()) {
		publicOPN.addOPN([{
			name: '暂存',
			alias: 'save',
			event: function() {
				doSubmit(false);
			}
		}]);
	}

	publicOPN.init().doRender();

	//------ 侧边栏 end

	// ------ 审核 start

	var auditProject = require('zyw/auditProject');

	if (document.getElementById('auditForm')) {

		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

	}

	// ------ 审核 end

});