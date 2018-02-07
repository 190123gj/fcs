define(function(require, exports, module) {

	// 项目管理 > 理财项目管理 > 理财项目合同申请
	require('input.limit');
	require('zyw/upAttachModify');
	require('validate');

	var util = require('util');
	var getList = require('zyw/getList');

	var $fnInput = $('.fnInput'), // 填写项目
	$form = $('#form');

	function doApplySubmit(boole) {

		if (!!!document.getElementById('productName').value) {
			Y.alert('提示', '请选择一个产品');
			return;
		}

		var _state = boole ? '1' : '0';

		$('[name=checkStatus]').val(_state);

		util.ajax({
			url : $form.attr('action'),
			data : $form.serializeJCK(),
			success : function(res) {
				if (res.success) {

					// 提交表单
					if (boole) {
						Y.confirm('提示', '确认提交当前表单？', function(opn) {
							if (opn == 'yes') {
								util.postAudit({
									formId : res.form.formId
								}, function() {
									window.location.href = '/projectMg/financialProject/contract/list.htm';
								})
							}
						});
					} else {
						Y.alert('提示 ', '操作成功', function() {
							window.location = '/projectMg/financialProject/contract/list.htm';
						});
					}

				} else {
					Y.alert('操作失败', res.message);
				}
			}
		});

	}

	var requiredRules = {
		rules : {},
		messages : {}
	};

	util.setValidateRequired($('.fnInput'), requiredRules)

	$form.validate($.extend(true, util.validateDefault, {
		rules : requiredRules.rules,
		messages : requiredRules.messages,
		submitHandler : function() {
			doApplySubmit(true);
		}
	}));

	// ------ 选择项目 start
	var _getList = new getList();
	_getList.init({
		width : '90%',
		title : '理财项目列表',
		ajaxUrl : '/baseDataLoad/financialReviewOrContractProject.json?from=contract',
		btn : '#chooseBtn',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td>{{item.projectCode}}</td>', '        <td title="{{item.productName}}">{{(item.productName && item.productName.length > 6)?item.productName.substr(0,6)+\'\.\.\':item.productName}}</td>', '        <td title="{{item.issueInstitution}}">{{(item.issueInstitution && item.issueInstitution.length > 6)?item.issueInstitution.substr(0,6)+\'\.\.\':item.issueInstitution}}</td>', '        <td>{{item.expectBuyNum}}</td>', '        <td>{{item.expectBuyDate}}</td>', '        <td>{{item.rateRangeStart}} - {{item.rateRangeEnd}}%</td>', '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>', '    </tr>', '{{/each}}' ].join(''),
			form : [ '产品名称：', '<input class="ui-text fn-w160" type="text" name="productName">', '&nbsp;&nbsp;', '发行机构：', '<input class="ui-text fn-w160" type="text" name="issueInstitution">', '&nbsp;&nbsp;', '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ].join(''),
			thead : [ '<th width="100">项目编号</th>', '<th width="120">产品名称</th>', '<th width="120">发行机构</th>', '<th width="200">拟申购份数</th>', '<th width="120">拟申购日</th>', '<th width="100">年化收益率</th>', '<th width="50">操作</th>' ].join(''),
			item : 7
		},
		callback : function($a) {
			window.location.href = '/projectMg/financialProject/contract/form.htm?projectCode=' + $a.attr('projectCode');
		}
	});

	// ------ 审核通用部分 start
	var auditProject = require('zyw/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

	// ------ 审核通用部分 end
	//
	//
	// ------ 侧边栏 start
	var publicOPN = new (require('zyw/publicOPN'))();

	var productId = $("#productId").val();
	if (productId && productId > 0) {
		publicOPN.addOPN([ {
			name : '查看产品信息',
			alias : 'pdinfo',
			event : function() {
				util.openDirect('/projectMg/index.htm', '/projectMg/basicmaintain/financialProduct/view.htm?productId=' + productId)
			}
		} ]);
	}
	
	if ($form.length) {
		publicOPN.addOPN([ {
			name : '暂存',
			alias : 'doSave',
			event : function() {
				doApplySubmit()
			}
		} ]);
	}

	publicOPN.init().doRender();
	// ------ 侧边栏 end
	
    
	// BPM弹窗
	var BPMiframe = require('BPMiframe');
	var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
	var BPMiframeConf = {
		'width' : 850,
		'height' : 460,
		'scope' : '{type:\"system\",value:\"all\"}',
		'selectUsers' : {
			selectUserIds : '',
			selectUserNames : ''
		},
		'bpmIsloginUrl' : '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl' : '/JumpTrust/makeLoginUrl.htm'
	};	
	
	// ---法务经理
        var _chooselegalManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
            title : '法务经理'
        }));

        var $legalManagerName = $('#legalManagerName'), $legalManagerId = $('#legalManagerId'), $legalManagerAccount = $('#legalManagerAccount');

        $('#legalManagerBtn').on('click', function() {

            if ($('[name="chooseLegalManager"]').attr('value') == 'YES') {

				_chooselegalManager.init(function(relObj) {
					$legalManagerId.val(relObj.userIds);
					$legalManagerName.val(relObj.fullnames);
					$legalManagerAccount.val(relObj.accounts);

				});
            }
        });

});