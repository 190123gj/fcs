define(function(require, exports, module) {
	// 复议项目申请表新增
	require('Y-msg');
	require('validate')
	var util = require('util');
	// ------ 新增 复议项目申请表新增 start
	//
	if (!!util.browserType() && util.browserType() < 10) { // IE9及以下不支持多选，加载单选上传
		require.async('tmbp/upAttachModify'); // 上传
	} else {
		require.async('tmbp/upAttachModifyNew'); // 上传
	}
	var formId = util.getParam('formId');
	var $addForm = $('#addForm');

	$addForm.validate($.extend({}, util.validateDefault, {
		rules : {
			projectCode : {
				required : true
			},
			contentReason : {
				required : true,
			},
			overview : {
				required : true,
			}
		},
		messages : {
			projectCode : {
				required : '必填'
			},
			contentReason : {
				required : '必填',
                maxlength : '最多输入50000字'
			},
			overview : {
				required : '必填',
                maxlength : '最多输入50000字'
			}
		},
		submitHandler : function() {
			$.ajax({
				url : '/projectMg/recouncil/save.htm',
				data : $addForm.serialize(),
				dataType : 'json',
				type : 'POST',
				success : function(res) {
					if (res.success) {
						submitForm(res.form.formId);
					} else {
						Y.alert('提示', res.message);
					}
				}
			});
		}
	}))

	var getList = require('zyw/getList');

	// ------ 选择项目 end
	var _getList = new getList();
	_getList.init({
		title : '项目列表',
		ajaxUrl : '/baseDataLoad/loanRecouncliProject.json',
		btn : '#chooseBtn',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td>{{item.projectCode}}</td>', '        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>', '        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>', '        <td>{{item.timeLimit}}</td>', '        <td>{{item.amount}}</td>', '        <td>{{item.chargeFee}}</td>', '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>', '    </tr>', '{{/each}}' ].join(''),
			form : [ '项目编号：', '<input class="ui-text fn-w160" name="projectCode" type="text">', '&nbsp;&nbsp;', '客户名称：', '<input class="ui-text fn-w160" name="customerName" type="text">', '&nbsp;&nbsp;', '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ].join(''),
			thead : [ '<th>项目编号</th>', '<th>客户名称</th>', '<th>项目名称</th>', '<th width="80">授信期限</th>', '<th width="80">授信金额<br>(万元)</th>', '<th width="50">授信费用</th>', '<th width="50">操作</th>' ].join(''),
			item : 8
		},
		callback : function($a) {
			window.location = "/projectMg/recouncil/form.htm?projectCode=" + $a.attr('projectCode');
		}
	});

	function submitForm(formId) {

		util.postAudit({
			formId : formId
		}, function() {
			window.location.href = '/projectMg/recouncil/list.htm';
		})

	}

	// 点击增加
	// $addForm.on('click', '#add', function () {
	// var _this = $(this);
	// if (_this.hasClass('ing')) {
	// return;
	// }
	// _this.addClass('ing');

	// if (!document.getElementById('projectNumber').value) {
	// Y.alert('提示', '请选择项目');
	// _this.removeClass('ing');
	// return;
	// }

	// if (!document.getElementById('reason').value.replace(/\s/g, '')) {
	// Y.alert('提示', '请填写复议内容及理由');
	// _this.removeClass('ing');
	// return;
	// }

	// if (!document.getElementById('advice').value.replace(/\s/g, '')) {
	// Y.alert('提示', '请填写综合意见');
	// _this.removeClass('ing');
	// return;
	// }

	// });
	// 计算风控委纪要iframe高度
	// (function reinitIframe() {
	// var iframe = document.getElementById('summaryFrame');
	// try {
	// var bHeight = iframe.contentWindow.document.body.scrollHeight;
	// var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	// var height = Math.max(bHeight, dHeight);
	// iframe.height = height;
	// } catch (ex) {}
	// })();

	// var summaryFrame = $("#summaryFrame");
	// summaryFrame.load(function() {
	// var mainheight = $(this).contents().find("body").height();
	// $(this).height(mainheight);
	// });

	// ------ 审核通用部分 start
	var auditProject = require('zyw/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
	// ------ 审核通用部分 end

	// ------ 侧边栏 start
	var publicOPN = new (require('zyw/publicOPN'))();

	var projectCode = $("#projectNumber").val();
	if (projectCode) {
		publicOPN.addOPN([ {
			name : '项目详情',
			alias : 'projectDetail',
			event : function() {
				//util.openDirect('/projectMg/index.htm', '/projectMg/viewProjectAllMessage.htm?projectCode=' + projectCode)
				window.open('/projectMg/viewProjectAllMessage.htm?projectCode=' + projectCode)
			}
		}])
		if($("#reason").length) {
            publicOPN.addOPN([{
                name: '暂存',
                alias: 'save',
                event: function () {
                    $.ajax({
                        url : '/projectMg/recouncil/save.htm',
                        data : $addForm.serialize(),
                        dataType : 'json',
                        type : 'POST',
                        success: function () {
                            Y.alert('提示', '已保存');
                        }
                    })
                }
            }])
		}
	}

	//屏蔽侧边按钮 2017-5-9 zhurui

	publicOPN.init().doRender();

	$('.ui-opn ul li').eq(0).attr('target','_blank');

	// ------ 侧边栏 end

	function printdiv(printpage) {
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}

	var summaryFrame = $("#summaryFrame");
	$("#summaryDiv").html(summaryFrame.contents().find("#div_print").html());

	// $('#fnPrint').click(function(event) {
	// 	printdiv('div_print');
	// });

    $('#fnPrint').click(function (event) {

        var $fnPrintBox = $('#div_print')
        // console.log($fnPrintBox.html())
        $fnPrintBox.find('.ui-btn-submit').remove()

        $fnPrintBox.find('.printshow').removeClass('fn-hide')

        util.print($fnPrintBox.html())
    });

    $('#auditForm').css('width', '100%')
});