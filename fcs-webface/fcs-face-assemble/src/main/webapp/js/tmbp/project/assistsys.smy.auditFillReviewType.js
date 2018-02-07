define(function(require, exports, module) {
	// require('zyw/upAttachModify')

	var util = require('util');
	require('zyw/chooseLoanPurpose');
	var loanPurpose = require('zyw/chooseLoanPurpose');
    loanPurpose.init('LOAN_PURPOSE')

	require('zyw/project/assistsys.smy.Common');
	$.smyCommon(true);
	var publicOPN = new(require('zyw/publicOPN'))();
	
	if (!$('[isApproval]').attr('isApproval')) {
		// 异步提交
		require('form')();
		// 表单验证
		require('validate');
		require('validate.extend');

		// 审核通用部分 start
		var auditProject = require('/js/tmbp/auditProject');
		var _auditProject = new auditProject('auditSubmitBtn');
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit({}).initPrint('打印的url');

		var $councilId = $("#councilId"), $summaryId = $("#summaryId");
		// ------ 侧边栏 start
		
		// 董事长才有查看投票详情的
		if ($councilId && $councilId.length > 0) {

			publicOPN.addOPN([ {
				name : '投票详情',
				url : '/projectMg/meetingMg/viewVote.htm?councilId=' + $councilId.val() + '&projectCode=' + $councilId.attr("projectCode")
			} ]);


			// 董事长点击通过按钮前给提示
			_auditProject.addClickBefore(function(dtd, isPass) {
				if (eval(isPass)) {
					$("#extraHtml").load("/projectMg/meetingMg/summary/presidentComment.json", {
						'summaryId' : $summaryId.val()
					}, function() {
						//
					});
				} else {
					$("#extraHtml").html("");
				}
				return dtd.resolve();
			});

			// 董事长流程审批的时候需要验证是否全部发表意见
			_auditProject.addAuditBefore(function(dtd) {

				// 董事长审核特殊处理接口，全部通过的就走流程没全部通过的单独出批复流程继续挂着
				_auditProject.ajaxUrl.fnAuditBtnPass = "/projectMg/meetingMg/summary/submit.json";

				// var after = false, message = false;
				// if (_isPass) {
				//
				// // 查看是否已全部发表
				// util.ajax({
				// url : '/baseDataLoad/checkOneVote.json',
				// async : false,
				// data : {
				// 'summaryId' : $summaryId.val()
				// },
				// success : function(res) {
				// if (res.success) {
				// // do nothing
				// } else {
				// message = res.message;
				// }
				// after = true;
				// }
				// });
				//
				// if (after) {
				// if (message) {
				// return dtd.reject({
				// message : message
				// });
				// } else {
				// return dtd.resolve();
				// }
				// }
				// } else {
				// // 非通过状态不验证是否发表意见
				// return dtd.resolve();
				// }
				return dtd.resolve();
			});
		}
		

		// ------ 侧边栏 end

		// 一票否决
		var fillReviewTypeCommon = require('zyw/project/assistsys.smy.fillReviewTypeCommon'), popupWindow = require('zyw/popupWindow'), hintPopup = require('zyw/hintPopup');

		$('.vipBtn').click(function(event) {

			var $this = $(this);
			var voteResult = $this.attr("voteResult"), voteResultDesc = $this.val();

			fundDitch = new popupWindow({

				YwindowObj : {

					content : '#newPopupScript',
					closeEle : '.close'

				},

				callback : function($Y) {

					$Y.wnd.find("#oneVoteResult").html(voteResultDesc);
					$Y.wnd.find('[name="oneVoteResult"]').val(voteResult);

					fillReviewTypeCommon.ValidataInit({

						form : $Y.wnd.find('form'),

						// successBeforeFun: function(successBeforeJson) {

						// exports.submitHandlerContent = {
						// validateData: {
						// spId: $('.apply-step .item.active').attr('tabId')
						// }
						// };

						// fillReviewTypeCommon.submitHandlerContent('zyw/project/assistsys.smy.auditFillReviewType');

						// return true;

						// },

						successFun : function(res) {

							if (res.success) {

								hintPopup(res['message'], window.location.href);

							} else {

								hintPopup(res['message']);

							}

						},

						RulesInit : {

							rules : {

								reason : {
									required : true,
									maxlength : 1000
								}

							},

							messages : {

								reason : {
									required : '必填',
									maxlength : '已超出1000字'
								}

							}

						}

					});

				}
			});

		});

	}

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

	$('#fnPrint').click(function(event) {
		// printdiv('div_print');
		util.print(document.all.item('div_print').innerHTML)
	})

    //加下划线
	$('#div_print a[title="点击查看项目详情"] b').css('border-bottom','2px solid #000');
	// var istrue = true;
	// $('#fnMOPN').on('click','.fn-usn',function(){
	// 	if(istrue){
	// 		$('#fnMOPN').css({
	// 		right:0
	// 		})
	// 		istrue = false;
	// 	}else {
	// 		$('#fnMOPN').css({
	// 		right:-122+'px'
	// 		})
	// 		istrue = true;
	// 	}
	// })
	 if($('.fn-tac.apply-h2.fn-mt30').text().trim() == '查看会议纪要'){
	 publicOPN.init().doRender();
	 }

});