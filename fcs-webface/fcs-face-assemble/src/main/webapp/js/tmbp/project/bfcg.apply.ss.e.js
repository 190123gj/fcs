define(function(require, exports, module) {
	//表单验证
	require('validate');
	require('validate.extend');
	//选择合作机构
	var getList = require('zyw/getList');
	//Y对话框
	require('Y-msg');
	//模板引擎
	var template = require('arttemplate');
	//所属行业 相关
	require('zyw/chooseIndustry');
	//所属地区 相关
	require('zyw/chooseRegion');
	//上传图片等公共代码
	var applyFN = require('./bfcg.apply.common');

	//项目管理>授信前管理> 立项申请
	//------ 合作机构选择 start
	// var _getList = new getList();
	// _getList.init({
	// 	title: '合作机构选择',
	// 	ajaxUrl: '/ajaxUrl.json',
	// 	btn: '#openApplyOrg',
	// 	input: ['coInstitutionName', 'coInstitutionId'],
	// 	tpl: {
	// 		tbody: ['{{each list as item i}}',
	// 			'    <tr class="fn-tac">',
	// 			'        <td class="item" id="{{item.name}}">{{item.name}}</td>',
	// 			'        <td>{{item.name}}</td>',
	// 			'        <td>{{item.name}}</td>',
	// 			'        <td><a class="choose" coInstitutionId="{{item.id}}" coInstitutionName="{{item.name}}" href="javascript:void(0);">选择</a></td>',
	// 			'    </tr>',
	// 			'{{/each}}'
	// 		].join(''),
	// 		form: ['剩余可用授信额度&nbsp&nbsp;&nbsp;',
	// 			'<select class="ui-select" name="text1">',
	// 			'    <option value="">&nbsp;</option>',
	// 			'    <option value="1">&ge;</option>',
	// 			'    <option value="2">&lt;</option>',
	// 			'</select>&nbsp;&nbsp;',
	// 			'<input class="ui-text fn-w90 fnMakeMoney" type="text" name="text2">&nbsp;&nbsp;',
	// 			'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
	// 		].join(''),
	// 		thead: ['<th width="200">合作机构名称</th>',
	// 			'<th width="170">机构授信额度（万元）</th>',
	// 			'<th width="170">剩余可用额度（万元）</th>',
	// 			'<th>操作</th>'
	// 		].join(''),
	// 		item: 4
	// 	}
	// });
	//------ 合作机构选择 end

	//--------------------企业基本情况 start

	//企业性质 

	//----数据验证 基本信息 start
	$('#qyjbqk').validate($.extend(true, applyFN.validateRules, {
		rules: {
			legalPersionAddress: {
				required: true
			},
			contactMan: {
				required: true
			},
			contactNo: {
				required: true
			},
			actualControlManCertNo: {
				checkID: true
			}
		},
		messages: {
			legalPersionAddress: {
				required: '必填项'
			},
			contactMan: {
				required: '必填项'
			},
			contactNo: {
				required: '必填项'
			},
			actualControlManCertNo: {
				checkID: '请输入正确的号码'
			}
		}
	}));
	//----数据验证 基本信息 end

	//--------------------企业基本情况 end

	//--------------------申请贷款/担保情况 start

	//----数据验证 申请贷款/担保情况 start
	$('#sqdkdbqk').validate({
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
			applyFN.submitForm();
		},
		rules: {
			projectName: {
				required: true
			},
			amount: {
				required: true
			},
			timeLimit: {
				required: true
			},
			coInstitutionName: {
				required: true
			},
			loanType: {
				required: true
			},
			loanPurpose: {
				required: true
			},
			assureObject: {
				required: true
			},
			amountStr: {
				required: true,
				min: 0.01
			}
		},
		messages: {
			projectName: {
				required: '必填'
			},
			amount: {
				required: '请输入金额'
			},
			timeLimit: {
				required: '请输入时间'
			},
			coInstitutionName: {
				required: '请输入合作机构'
			},
			loanType: {
				required: '请选择类型'
			},
			loanPurpose: {
				required: '请选用途'
			},
			assureObject: {
				required: '必填'
			},
			amountStr: {
				required: '必填',
				min: '必须大于0'
			}
		}
	});
	//----数据验证 申请贷款/担保情况 end

	//--------------------申请贷款/担保情况 end

});