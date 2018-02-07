define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('input.limit');
	var util = require('util');
	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
	var getList = require('zyw/getList');

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
    //
	//
	//		projectList.resetAjaxUrl('/baseDataLoad/queryProjects.json?customerType=ENTERPRISE&customerId=' + $a.attr('customerid'));
	//		// $('#projectName').val('');
	//		// $('#projectCode').val('');
	//	}
	//});
	var projectList = new getList()

	projectList.init({
		title: '项目列表',
		ajaxUrl: '/baseDataLoad/queryProjects.json',
		btn: '#fnToChooseProjectCode',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td class="item" title="{{item.projectCode}}">{{item.projectCode}}</td>',
				'        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
				'        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
				'        <td title="{{item.busiTypeName}}">{{(item.busiTypeName && item.busiTypeName.length > 6)?item.busiTypeName.substr(0,6)+\'\.\.\':item.busiTypeName}}</td>',
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
			$('#projectName').val($a.attr('projectName'));
			$('#projectCode').val($a.attr('projectCode')).trigger('blur');
			$('#busiManagerId').val($a.attr('busiManagerId'));
			$('#busiManagerName').val($a.attr('busiManagerName'));
			$("#customerId").val($a.attr('customerId'));
			$("#customerName").val($a.attr('customerName')).trigger('blur');
			util.ajax({
				url: "/projectMg/riskHandleTeam/queryTeamInfo.json",
				data: {projectCode:$a.attr('projectCode'),"deptCode":$a.attr('deptCode')},
				success: function(res) {
					if (res.success) {
						$("#memberIds").val(res.memberIds);
						$("#memberNames").val(res.memberNames).trigger('blur');
						$("#viceLeaderId").val(res.viceLeaderId);
						$("#viceLeaderName").val(res.viceLeaderName).trigger('blur');
					}
				}});
		}
	});


	var $form = $("#form");
	$form.on("click","#fnToClearCustomer",function(){
		//$("#customerId").val("");
		//$("#customerName").val("");
		//$('#projectName').val("");
		//$('#projectCode').val("");
	}).on("click","#fnToChooseProjectCode",function(){
		//if ($("#customerId").val() ==0 || !$("#customerId").val()) {
		//	Y.alert('提示', '请先选择客户');
		//	return false;
		//}
	}).on("click","#fnToClearProjectCode",function(){
		$('#projectName').val("");
		$('#projectCode').val("");
	})


	// 必备参数
	var scope = '{type:\"system\",value:\"all\"}';
	var selectUsers = {
		selectUserIds: '', //已选id,多用户用,隔开
		selectUserNames: '' //已选Name,多用户用,隔开
	}


// 初始化弹出层
	var singleSelectChiefLeaderDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
		'title': '人员',
		'width': 850,
		'height': 460,
		'scope': scope,
		'selectUsers': selectUsers,
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

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
    $body.on('click', '#fnChooseChiefLeaderBtn', function() {
        // var $names = $("#chiefLeaderName"),
        //     $ids = $("#chiefLeaderId");
        // singleSelectChiefLeaderDialog.obj.selectUsers={
        //     selectUserNames:$names.val(),
        //     selectUserIds:$ids.val()
        // }
        // singleSelectChiefLeaderDialog.init(function(relObj) {
        //     $names.val(relObj.fullnames).trigger('blur');
        //     $ids.val(relObj.userIds).trigger('blur');
        // });
        choosePeople('chiefLeaderName','chiefLeaderId',true);
    }).on("click","#fnChooseViceLeaderNameBtn",function(){
        // var $names = $("#viceLeaderName"),
        //     $ids = $("#viceLeaderId");
        // singleSelectChiefLeaderDialog.obj.selectUsers={
        //     selectUserNames:$names.val(),
        //     selectUserIds:$ids.val()
        // }
        // singleSelectChiefLeaderDialog.init(function(relObj) {
        //     $names.val(relObj.fullnames).trigger('blur');
        //     $ids.val(relObj.userIds).trigger('blur');
        // });
        choosePeople('viceLeaderName','viceLeaderId',true);
    }).on("click","#fnToChooseMemberNames",function(){
        // var $names = $("#memberNames"),
        //     $ids = $("#memberIds");
        // singleSelectChiefLeaderDialog.obj.selectUsers={
        //     selectUserNames:$names.val(),
        //     selectUserIds:$ids.val()
        // }
        // singleSelectChiefLeaderDialog.init(function(relObj) {
        //     $names.val(relObj.fullnames).trigger('blur');
        //     $ids.val(relObj.userIds).trigger('blur');
        // });
        choosePeople('memberNames','memberIds',false);
    });

    function choosePeople(namesID,idsID,isSingle) {
        var $names = $("#" + namesID),
            $ids = $("#" + idsID),
            funs = !!isSingle ? singleSelectChiefLeaderDialog : singleSelectMemberNamesDialog;
        funs.obj.selectUsers={
            selectUserNames:$names.val(),
            selectUserIds:$ids.val()
        }
        funs.init(function(relObj) {
            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    }

    //------ 公共 验证规则 start
    require('validate');
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else {
                element.parent().append(error);
            }
        },
        submitHandler: function(form) {
        },
        rules: {
            projectCode: {
                required: true
            },
            customerName: {
                required: true
            },
            chiefLeaderName: {
                required: true
            },
            viceLeaderName: {
                required: true
            },
            memberNames: {
                required: true
            }
        },
        messages: {
            projectCode: {
                required: '必填'
            },
            customerName: {
                required: '必填'
            },
            chiefLeaderName: {
                required: '必填'
            },
            viceLeaderName: {
                required: '必填'
            },
            memberNames: {
                required: '必填'
            }
        }
    };
    $('#form').validate(validateRules);
    function dynamAddRules(ele) {
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput').not(':hidden');
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
        var $nameList = ele.find('[name].fnInput').not(':hidden');
        $nameList.each(function (i,e) {
            $(this).rules("remove");
        })
    };

	//提交
	var $addForm = $('#form');


	$('.toSubmit').on('click', function() {

		var _this = $(this);
        var $fnInput = $('.fnInput');
        var _isPass = true,
            _isPassEq;
        var opt = _this.attr('opt');//暂存DRAFT、保存并退出SAVE_QUIT、保存并新增状态判断SAVE_NEW
        var linkUrl = '/projectMg/riskHandleTeam/list.htm'; //提交成功后的跳转链接，默认为新增成功后退出

        if(opt == 'SAVE_NEW') linkUrl = '/projectMg/riskHandleTeam/toAdd.htm';

        if(opt == 'DRAFT'){//如果是暂存只验证客户名称
            if (!!!$('[name*="customerName"]').val().replace(/\s/g, '')) {
                Y.alert('提示', '暂存必须选择客户名称！', function() {
                    $('[name*="customerName"]').focus();
                });
                return;
            }
            dynamicRemoveRules($('.tableList'))
        }else {
            dynamAddRules($('.tableList'));
            if(!$('#form').valid()) return;
            $fnInput.each(function(index, el) {

                if (!!!el.value.replace(/\s/g, '')) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                }

            });
        }

		if (!_isPass) {
			Y.alert('提示', '请填写完整表单', function() {
				$fnInput.eq(_isPassEq).focus();
			});
			return;
		}

		util.ajax({
			url: $addForm.attr('action'),
			data: $addForm.serialize(),
			success: function(res) {
			    // console.log(res)
				if (res.success) {
					Y.alert('提示', '已提交', function() {
                        util.direct(linkUrl);
					});
				} else {
					Y.alert('提示', res.message);
				}
			}
		});
	});
});