define(function(require, exports, module) {
	// 资金收付
	require('Y-msg');
	require('input.limit');
	require('zyw/opsLine');
	require('validate');

	var util = require('util');

	var getList = require('zyw/getList');
	// ------ 选择项目 start
	var _getList = new getList();
	_getList.init({
		width : '90%',
		title : '项目列表',
		ajaxUrl : '/baseDataLoad/queryProjects.json?select=my&phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,RE_COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES,FINISH_PHASES&statusList=NORMAL,RECOVERY,SELL_FINISH,FINISH,EXPIRE,OVERDUE',
		btn : '#chooseBtn',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td>{{item.projectCode}}</td>', '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>', '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>', '        <td>{{item.timeLimit}}</td>', '        <td>{{item.amount}}</td>', '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>', '    </tr>', '{{/each}}' ].join(''),
			form : [ '项目编号：', '<input class="ui-text fn-w160" name="projectCode" type="text">', '&nbsp;&nbsp;', '客户名称：', '<input class="ui-text fn-w160" name="customerName" type="text">', '&nbsp;&nbsp;', '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ].join(''),
			thead : [ '<th>项目编号</th>', '<th>客户名称</th>', '<th>项目名称</th>', '<th width="80px">授信期限</th>', '<th width="140px">授信金额</th>', '<th width="50px">操作</th>' ].join(''),
			item : 6
		},
		callback : function($a) {
			window.location = "/projectMg/chargeRepayPlan/form.htm?projectCode=" + $a.attr('projectCode');
		}
	});

	util.resetName();

	var $form = $('#form'), requiredRules = {
		rules : {},
		messages : {}
	};

	util.setValidateRequired($('.fnInput:visible'), requiredRules)

	$form.validate($.extend(true, util.validateDefault, {
		rules : requiredRules.rules,
		messages : requiredRules.messages,
		submitHandler : function() {

			if (!!!$('.fnWhichPlan:checked').length) {
				Y.alert('提示', '请选择本次上报计划内容')
				return;
			}

			util.resetName();

			util.ajax({
				url : $form.attr('action'),
				data : $form.serializeJCK(),
				success : function(res) {

					if (res.success) {
						Y.alert('提示', '操作成功', function() {
							window.location.href = '/projectMg/chargeRepayPlan/list.htm';
						});
					} else {
						Y.alert('操作失败', res.message);
					}

				}
			});
		}
	}));

	$('.fnMustMin0').each(function(index, el) {
		$(this).rules('add', {
			min : 1,
			messages : {
				min : '必须大于0'
			}
		})
	});

	$form.on('click', '.fnDoSubmit', function() {

		$('#isAffirm').val(this.getAttribute('affirm') || '')

	})

	// var $fnPlanBox = $('.fnPlanBox')

	$('body').on('click', '.fnWhichPlan', function() {

		$('.fnPlanBox').addClass('fn-hide').find('.fnInput').each(function(index, el) {
			$(this).rules('remove', 'required')
		});

		$('.fnWhichPlan:checked').each(function(i, e) {
			$('.fnPlanBox[boxname="' + e.name + '"]').removeClass('fn-hide').find('.fnInput').each(function(index, el) {
				$(this).rules('add', {
					required : true,
					messages : {
						required : '必填'
					}
				})
			});
		});

	});

	$('body').on('click', '.fnAddLine', function() {

		var $this = $(this)

		setTimeout(function() {

			util.resetName();

			$('#' + $this.attr('cttid')).find('tr').last().find('.fnInput').each(function(index, el) {
				$(this).rules('add', {
					required : true,
					messages : {
						required : '必填'
					}
				})
			});

		}, 500)

	})

	$form.on('blur', '.fnInputDateS', function() {

		var $this = $(this), $end = $this.parent().next().find('.fnInputDateE')

		$end.attr('onclick', 'laydate({min: "' + $this.val() + '", max: "' + ($end.attr('limitmax') || '') + '"})')

	}).on('blur', '.fnInputDateE', function() {

		var _thisVal = this.value;
		$(this).parent().prev().find('.fnInputDateS').attr('onclick', 'laydate({max: "' + _thisVal + '"})');

	});

	var popupWindow = require('zyw/popupWindow');
	// ------ 侧边栏 start
	var publicOPN = new (require('zyw/publicOPN'))();

	var projectCode = document.getElementById('projectCode');

	if (projectCode && projectCode.value) {

		publicOPN.addOPN([ {
			name : '查看全部计划',
			alias : 'lookAllPlan',
			event : function() {
				util.openDirect('/projectMg/index.htm', '/projectMg/chargeRepayPlan/view.htm?projectCode=' + projectCode.value);
				// url: '/projectMg/chargeRepayPlan/view.htm?projectCode=' +
				// projectCode.value
			}
		}, {
			name : '查看历史收费',
			alias : 'lookHistory',
			event : function() {

				// 参考 project/bfcg.cgnt.add.audit.js

				chargePopupWindow = new popupWindow({

					YwindowObj : {
						content : 'historyCharge', // 弹窗对象，支持拼接dom、template、template.compile
						closeEle : '.close', // find关闭弹窗对象
						dragEle : '.newPopup dl dt' // find拖拽对象
					},

					ajaxObj : {
						url : '/projectMg/chargeNotification/showChargeHistory.htm',
						type : 'post',
						dataType : 'json',
						data : {
							projectCode : projectCode.value
						}
					},

					console : false
				// 打印返回数据

				});

				// url:
				// '/projectMg/chargeNotification/showChargeHistory.htm?projectCode'
				// + projectCode.value
			}
		} ]);

	}

	publicOPN.init().doRender();

	// ------ 侧边栏 end

	// 公共签报
	require('./assistsys.smy.fillReviewChange')
});