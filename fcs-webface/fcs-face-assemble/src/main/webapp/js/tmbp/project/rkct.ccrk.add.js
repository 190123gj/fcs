define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('input.limit');
	var util = require('util');
	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
	var getList = require('zyw/getList');


	// ------- 企业
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
	//	   projectList.resetAjaxUrl('/baseDataLoad/queryProjects.json?customerType=ENTERPRISE&customerId=' + $a.attr('customerid'));
	//		$('#projectName').val('');
	//		$('#projectCode').val('');
	//	}
	//});
	var projectList = new getList();

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
				'        <td><a class="choose"  customerId="{{item.customerId}}" customerName="{{item.customerName}}"  deptCode="{{item.deptCode}}"  projectCode="{{item.projectCode}}"  projectName="{{item.projectName}}"  busiManagerId="{{item.busiManagerId}}"  busiManagerName="{{item.busiManagerName}}"    href="javascript:void(0);">选择</a></td>',
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
			$('#deptCode').val($a.attr('deptCode'));
			$("#customerId").val($a.attr('customerId'));
			$("#customerName").val($a.attr('customerName')).trigger('blur');
			util.ajax({
				url:"/projectMg/councilRisk/queryRiskTeam.json",
				data: {projectCode:$("#projectCode").val()},
				success: function(res) {
					if (res.success) {
						$("#participantIds").val(res.memberIds);
						$("#participantNames").val(res.memberNames).trigger('blur');

					}
				}
			});
		}
	});


	var $form = $("#form");
	$form.on("click","#fnToClearCustomer",function(){
		//$("#customerId").val("");
		//$("#customerName").val("");
		//$('#projectName').val("");
		//$('#projectCode').val("");
	}).on("click","#fnToChooseProjectCode",function(){
		//if ($("#customerId").val() == 0 || !$("#customerId").val()) {
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
            councilType: {
                required: true
            },
            beginTime: {
                required: true
            },
            councilPlace: {
                required: true
            },
            councilSubject:{
                required: true
            },
            participantNames:{
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
            councilType: {
                required: '必填'
            },
            beginTime: {
                required: '必填'
            },
            councilPlace: {
                required: '必填'
            },
            councilSubject:{
                required: '必填'
            },
            participantNames:{
                required: '必填'
            }
        }
    };
    $('#form').validate(validateRules);

    function dynamicRules(ele) {
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
    }
    //提交
    var $addForm = $('#form'),
        $fnInput = $('.fnInput');

    $('#submit').on('click', function() {
        if(!$('#form').valid()) return;
        $fnInput = $('.fnInput');
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

		if (!_isPass) {
			Y.alert('提示', '请填写完整表单', function() {
				$fnInput.eq(_isPassEq).focus();
			});
			_this.removeClass('ing');
			return;
		}

		util.ajax({
			url: $addForm.attr('action'),
			data: $addForm.serialize(),
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '已提交', function() {
						util.direct('/projectMg/councilRisk/list.htm');
					});
				} else {
					Y.alert('提示', res.message);
					_this.removeClass('ing');
				}
			}
		});
	});
});