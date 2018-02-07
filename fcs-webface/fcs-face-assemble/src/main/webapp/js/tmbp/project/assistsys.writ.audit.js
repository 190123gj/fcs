define(function(require, exports, module) {
	// 辅助系统 > 文书审核 > 文书审核 审核
	var util = require('util');
	require('zyw/upAttachModify');

    //------ 2017.09.30 授信条件 翻页 start
    var $body = $('body');
    // 显示隐藏未落实
    $body.on('click', '#fnHideIsConfirmIsNO', function () {

        getCreditConditions(2, 1)

    })

    var $creditPageList = $('#creditPageList')
    var $window = $(window)

    $creditPageList.find('.m-pages a').removeAttr('attribute name')

    window.getCreditConditions = function (totalPage, pageNo) {

        var PROJECT_CODE = document.getElementById('projectCode').value

        if (!!!PROJECT_CODE) {
            return
        }

        if (totalPage < pageNo) {
            return false;
        }

        var fnHideIsConfirmIsNO = document.getElementById('fnHideIsConfirmIsNO')
        var _hasToCarry = ''

        if (fnHideIsConfirmIsNO.checked) {
            _hasToCarry = fnHideIsConfirmIsNO.value
        }

        $creditPageList.load("/projectMg/contract/creditPageList.json", {
            'pageNumber': pageNo,
            'projectCode': PROJECT_CODE,
            'isConfirm': _hasToCarry
        }, function () {
            $window.scrollTop($creditPageList.offset().top)
        })


    }
    //------ 2017.09.30 授信条件 翻页 end

    /**
     * 授信落实 上传资产附件/查看附件
     *
     * 主要分为 用户上传的资源（授信id关联） 资产数据资源（资产id关联）
     *
     * 用户上传的资源（授信id关联） value 附件信息 txt 文字
     *
     *
     */

    require('./bfcg.ctcd.attach');

	//侧边栏
	var publicOPN = require('zyw/publicOPN'),
		_publicOPN = new publicOPN(),
		projectCode = document.getElementById('projectCode');

	if (projectCode && projectCode.value) {

		_publicOPN.addOPN([{
			name: '查看项目批复',
			alias: 'look',
			event: function() {
				util.openDirect('/projectMg/index.htm', '/projectMg/meetingMg/summary/approval.htm?projectCode=' + document.getElementById('projectCode').value);
			}

		}])

	}
	_publicOPN.init().doRender();

	//BPM弹窗
	var BPMiframe = require('BPMiframe');
	var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
	var BPMiframeConf = {
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '',
			selectUserNames: ''
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	};

	var _chooseLegalManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
		title: '法务人员'
	}));

	var $legalManagerName = $('#legalManagerName'),
		$legalManagerId = $('#legalManagerId'),
		$legalManagerAccount = $('#legalManagerAccount');

	$('#legalManagerBtn').on('click', function() {

		_chooseLegalManager.init(function(relObj) {

			$legalManagerId.val(relObj.userIds);
			$legalManagerName.val(relObj.fullnames);
			$legalManagerAccount.val(relObj.accounts);

		});
	});

	//查看
	$('.fnWindowOpen').on('click', function(e) {
		e.preventDefault();
		window.open(this.href);
	});
	//新窗口打开
	$('.fnNewWindowOpen').on('click', function(e) {
		e.preventDefault();
		util.openDirect('/projectMg/index.htm', this.href);
	});

	////合同审查意见不必填
	//$('[name="workflowVoteContent"]').removeClass('fnAuditInput').parent().find('.m-required').html('');


	////------ 审核通用部分 start
	//var auditProject = require('zyw/auditProject');
	//var _auditProject = new auditProject();
	//if (document.getElementById('auditForm')) {
	//	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
	//}
	////------ 审核通用部分 end


	// 审核部分分支选择后处理
	var $auditForm = $("#auditForm"),
		$leaderTaskNodeId = $auditForm.find("[name=leaderTaskNodeId]").val(),
		$deptTaskNodeId = $auditForm.find("[name=deptTaskNodeId]").val(),
		$wyhTaskNodeId = $auditForm.find("[name=wyhTaskNodeId]").val();
	$("body").on("click", "[name=radNextNodeId]", function () {
		var nodeId = $(this).val();
		if (nodeId == $leaderTaskNodeId) {
			$auditForm.find(".leaderTaskNodeId").show().find('[name]').removeAttr("disabled");
			$auditForm.find(".deptTaskNodeId").hide().find('[name]').attr("disabled", true);
			$auditForm.find(".wyhTaskNodeId").hide().find('[name]').attr("disabled", true);
		} else if (nodeId == $deptTaskNodeId) {
			$auditForm.find(".deptTaskNodeId").show().find('[name]').removeAttr("disabled");
			$auditForm.find(".leaderTaskNodeId").hide().find('[name]').attr("disabled", true);
			$auditForm.find(".wyhTaskNodeId").hide().find('[name]').attr("disabled", true);
		}  else if (nodeId == $wyhTaskNodeId) {
			$auditForm.find(".wyhTaskNodeId").show().find('[name]').removeAttr("disabled");
			$auditForm.find(".leaderTaskNodeId").hide().find('[name]').attr("disabled", true);
			$auditForm.find(".deptTaskNodeId").hide().find('[name]').attr("disabled", true);
		}else {
			$auditForm.find(".leaderTaskNodeId").hide().find('[name]').attr("disabled", true);
			$auditForm.find(".deptTaskNodeId").hide().find('[name]').attr("disabled", true);
			$auditForm.find(".wyhTaskNodeId").hide().find('[name]').attr("disabled", true);
		}
	}).on("click", "#wyhSel", function () {
		var _this = $(this),
			wyh = _this.val();
		_this.siblings("[wyh]").hide();
		_this.siblings("[wyh="+wyh+"]").show();
	})

	if (document.getElementById('auditForm')) {

		var auditProject = require('zyw/auditProject');
		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

		var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选
		// 尽量在url后面加上参数
		// 初始化弹出层
		var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
			'title': '人员',
			'width': 850,
			'height': 460,
			'scope': '{type:\"system\",value:\"all\"}',
			'selectUsers': {
				selectUserIds: '', // 已选id,多用户用,隔开
				selectUserNames: '' // 已选Name,多用户用,隔开
			},
			'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
			'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
		});
		// 添加选择后的回调，以及显示弹出层
		$('#fnChooseMan').on('click', function () {

			var $p = $(this).parent(),
				$userName = $p.find('.userName'),
				$userId = $p.find('.userId');

			singleSelectUserDialog.init(function (relObj) {

				$userId.val(relObj.userIds);
				$userName.val(relObj.fullnames);

			});

		});

	}

});