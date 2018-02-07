define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('input.limit');
	var util = require('util');
	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
	var getList = require('zyw/getList');

	//上传
	require('zyw/upAttachModify');

	//// ------- 企业
	//(new getList()).init({
	//	title: '选择客户',
	//	ajaxUrl: '/baseDataLoad/customer.json',
	//	btn: '#fnCBtnCustomer',
	//	tpl: {
	//		tbody: ['{{each pageList as item i}}',
	//			'    <tr class="fn-tac m-table-overflow">',
	//			'        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>',
	//			'        <td title="{{item.busiLicenseNo}}">{{(item.busiLicenseNo && item.busiLicenseNo.length > 18)?item.busiLicenseNo.substr(0,18)+\'\.\.\':item.busiLicenseNo}}</td>',
	//			'        <td title="{{item.enterpriseType}}">{{(item.enterpriseType && item.enterpriseType.length > 10)?item.enterpriseType.substr(0,10)+\'\.\.\':item.enterpriseType}}</td>',
	//			'        <td title="{{item.industry}}">{{(item.industry && item.industry.length > 10)?item.industry.substr(0,10)+\'\.\.\':item.industry}}</td>',
	//			'        <td><a class="choose" customerid="{{item.customerId}}" customername="{{item.customerName}}" num="{{item.busiLicenseNo}}" href="javascript:void(0);">选择</a></td>',
	//			'    </tr>',
	//			'{{/each}}'
	//		].join(''),
	//		form: ['客户名称：',
	//			'<input class="ui-text fn-w100" type="text" name="customerName">',
	//			'&nbsp;&nbsp;',
	//			'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
	//		].join(''),
	//		thead: ['<th>客户名称</th>',
	//			'<th>营业执照号码</th>',
	//			'<th>企业性质</th>',
	//			'<th>所属行业</th>',
	//			'<th width="50">操作</th>'
	//		].join(''),
	//		item: 5
	//	},
	//	callback: function($a) {
	//	$("#customerId").val($a.attr('customerid'));
	//	$("#customerName").val($a.attr('customername'));
	//	   projectList.resetAjaxUrl('/baseDataLoad/queryProjects.json?customerType=ENTERPRISE&customerId=' + $a.attr('customerid'));
	//		$('#projectName').val('');
	//		$('#projectCode').val('');
	//	}
	//});
	var projectList = new getList()

	projectList.init({
		title: '项目列表',
		ajaxUrl: '/baseDataLoad/queryProjects.json?fromRiskCouncil=YES',
		btn: '#fnToChooseProjectCode',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td class="item">{{item.projectCode}}</td>',
				'        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
				'        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
				'        <td>{{item.busiTypeName}}</td>',
				'        <td>{{item.amount}}</td>',
				'        <td><a class="choose"  customerId="{{item.customerId}}" customerName="{{item.customerName}}"  projectCode="{{item.projectCode}}"  projectName="{{item.projectName}}"  busiManagerId="{{item.busiManagerId}}" deptCode="{{item.deptCode}}"  busiManagerName="{{item.busiManagerName}}"    href="javascript:void(0);">选择</a></td>',
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
				'<th width="100">授信金额</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 6
		},
		callback: function($a) {
			$('#projectName').val($a.attr('projectName')).trigger('blur');
			$('#projectCode').val($a.attr('projectCode')).trigger('blur');
			$('#busiManagerId').val($a.attr('busiManagerId'));
			$('#busiManagerName').val($a.attr('busiManagerName'));
			$("#customerId").val($a.attr('customerId'));
			$("#customerName").val($a.attr('customerName')).trigger('blur');
		}
	});



	// 必备参数
	var scope = '{type:\"system\",value:\"all\"}';
	var selectUsers = {
		selectUserIds: '', //已选id,多用户用,隔开
		selectUserNames: '' //已选Name,多用户用,隔开
	}



	var singleSelectMemberNamesDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
		'title': '人员',
		'width': 850,
		'height': 460,
		'scope': scope,
		'selectUsers': selectUsers,
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

// 添加选择后的回调，以及显示弹出层
	var $body = $('body');
    $body.on("click","#fnToChooseMemberNames",function(){
        var $names = $("#participantNames"),
            $ids = $("#participantIds");
        singleSelectMemberNamesDialog.obj.selectUsers={
            selectUserNames:$names.val(),
            selectUserIds:$ids.val()
        }
        singleSelectMemberNamesDialog.init(function(relObj) {
            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    });
	// $body.on("click","#fnToChooseMemberNames",function(){
	// 	singleSelectMemberNamesDialog.init(function(relObj) {
	// 		$("#participantNames").val(relObj.fullnames).trigger('blur');
	// 		$("#participantIds").val(relObj.userIds).trigger('blur');
	// 	});
	// });


	var $form = $("#form");
	$form.on("click","#fnToClearCustomer",function(){
		//$("#customerId").val("");
		//$("#customerName").val("");
		//$('#projectName').val("");
		//$('#projectCode').val("");
	}).on("click","#fnToChooseProjectCode",function(){
		//if ($("#customerId").val() =="0" || !$("#customerId").val()) {
		//	Y.alert('提示', '请先选择客户');
		//	return false;
		//}
	}).on("click","#fnToClearProjectCode",function(){
		$('#projectName').val("").trigger('blur');
		$('#projectCode').val("").trigger('blur');
	})


	// 必备参数
	var scope = '{type:\"system\",value:\"all\"}';
	var selectUsers = {
		selectUserIds: '', //已选id,多用户用,隔开
		selectUserNames: '' //已选Name,多用户用,隔开
	}


// 初始化弹出层
// 	var singleSelectChiefLeaderDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
// 		'title': '人员',
// 		'width': 850,
// 		'height': 460,
// 		'scope': scope,
// 		'selectUsers': selectUsers,
// 		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
// 		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
// 	});
//
// 	var singleSelectMemberNamesDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
// 		'title': '人员',
// 		'width': 850,
// 		'height': 460,
// 		'scope': scope,
// 		'selectUsers': selectUsers,
// 		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
// 		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
// 	});

// 添加选择后的回调，以及显示弹出层
	var $body = $('body');
	// $body.on('click', '#fnChooseChiefLeaderBtn', function() {
    //
	// 	singleSelectChiefLeaderDialog.init(function(relObj) {
	// 		$("#chiefLeaderId").val(relObj.userIds);
	// 		$("#chiefLeaderName").val(relObj.fullnames);
	// 	});
	// })
     //    .on("click","#fnChooseViceLeaderNameBtn",function(){
	// 	singleSelectChiefLeaderDialog.init(function(relObj) {
	// 		$("#viceLeaderName").val(relObj.fullnames);
	// 		$("#viceLeaderId").val(relObj.userIds);
	// 	});
	// })
     //    .on("click","#fnToChooseMemberNames",function(){
	// 	singleSelectMemberNamesDialog.init(function(relObj) {
	// 		$("#memberNames").val(relObj.fullnames);
	// 		$("#memberIds").val(relObj.userIds);
	// 	});
	// });

    //------ 公共 验证规则 start
    require('validate');
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            // element.parent().append(error);
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else {
                element.parent().append(error);
            }
        },
        submitHandler: function(form) {
        },
        rules: {
            projectCode1: {
                required: true
            },
            // customerName: {
            //     required: true
            // },
            // occurTime: {
            //     required: true
            // },
            // eventTitle: {
            //     required: true
            // },
            // eventDetail: {
            //     required: true
            // },
            // needAnnounce: {
            //     required: true
            // }
        },
        messages: {
            projectCode1: {
                required: '必填'
            },
            // customerName: {
            //     required: '必填'
            // },
            // occurTime: {
            //     required: '必填'
            // },
            // eventTitle: {
            //     required: '必填'
            // },
            // eventDetail: {
            //     required: '必填'
            // },
            // needAnnounce: {
            //     required: '必填'
            // }
        }
    };
    $form.validate(validateRules);
    function dynamAddRules(ele) {
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput');
        $nameList.each(function (i,e) {
            var _this = $(this);
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })
    };
    function dynamicRemoveRules(ele) {
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput');
        $nameList.each(function (i,e) {
            $(this).rules("remove");
        })
    };
    dynamAddRules();

	var $addForm = $('#form'),
		$fnInput = $('.fnInput');
    $('.toCanleOpt').click(function () {
        var n = 0;
        var hrefVal = '/projectMg/projectRiskLog/list.htm';
        if($("#logStatus").hasClass('hasSaved')) {
            window.location.href = hrefVal;
            return;
        };

        $.each($fnInput, function (index,el) {
            if(!!!el.value.replace(/\s/g, '')) {
                n++;
            };
        });

        if(n == $fnInput.length){
            window.location.href = hrefVal;
            return;
        }else{
            Y.confirm('提示', '有尚未保存数据，是否继续取消？', function(opt) {
                if(opt == 'yes'){
                    window.location.href = hrefVal;
                    return;
                }
            })
        }

    });
    //提交
	$('.submitBtn').on('click', function() {
        var url = "/projectMg/projectRiskLog/list.htm";
        $fnInput = $('.fnInput');
        if($(this).val() =="暂存"){
            $("#logStatus").val("DRAFT");
            dynamicRemoveRules($addForm)
        }else{
            $("#logStatus").val("APPROVAL");
            dynamAddRules($addForm)
        };
        if(!$('#form').valid()) return;

		if($(this).val() =="保存并新增"){
			url = "/projectMg/projectRiskLog/toAdd.htm"
		}

		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		var _isPass = true,
			_isPassEq;
		$fnInput.each(function(index, el) {

			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}

		});
		if (!_isPass || ($("#logStatus").val() == 'DRAFT' && $("#customerName").val() == '')) {
			if($("#logStatus").val() == 'DRAFT'){//如果是暂存则只验证客户名称和项目编号
				if($("#customerName").val() == '' || $("#customerName").val() == ''){
					Y.alert('提示', '暂存时，客户名称和项目编号为必填！');
					_this.removeClass('ing');
					return;
				}

			}else {
				Y.alert('提示', '请填写完整表单', function() {
					$fnInput.eq(_isPassEq).focus();
				});
				_this.removeClass('ing');
				return;
			}


		}

		util.ajax({
			url: $addForm.attr('action'),
			data: $addForm.serialize(),
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '已提交', function() {
                            util.direct(url);
					});
				} else {
					Y.alert('提示', res.message);
					_this.removeClass('ing');
				}
			}
		});
	});
});