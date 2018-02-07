define(function(require, exports, module) {
	//表单验证
	require('validate');
	require('validate.extend');
	//增删行
	require('zyw/opsLine');
	//选择合作机构
	var getList = require('zyw/getList');
	//模板引擎
	var template = require('arttemplate');

	//Y对话框
	require('Y-msg');
	//md5加密
	var md5 = require('md5');

	//所属行业 相关
	require('zyw/chooseIndustry');
	//所属地区 相关
	require('zyw/chooseRegion');
	//上传图片等公共代码
	var applyFN = require('./bfcg.apply.common');

	//------ 担保函出具机构 start
	var _guaranteeOrg = new getList();
	_guaranteeOrg.init({
		title: '担保函出具机构',
		ajaxUrl: '/ajaxUrl.json',
		btn: '#guaranteeOrgBtn',
		input: ['guaranteeOrgName', 'guaranteeOrgCode'],
		tpl: {
			tbody: ['{{each list as item i}}',
				'    <tr class="fn-tac">',
				'        <td class="item" id="{{item.name}}">{{item.name}}</td>',
				'        <td>{{item.name}}</td>',
				'        <td>{{item.name}}</td>',
				'        <td><a class="choose" guaranteeOrgCode="{{item.id}}" guaranteeOrgName="{{item.name}}" href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['剩余可用授信额度&nbsp&nbsp;&nbsp;',
				'<select class="ui-select" name="text1">',
				'    <option value="">&nbsp;</option>',
				'    <option value="1">&ge;</option>',
				'    <option value="2">&lt;</option>',
				'</select>&nbsp;&nbsp;',
				'<input class="ui-text fn-w90 fnMakeMoney" type="text" name="text2">&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th width="200">合作机构名称</th>',
				'<th width="170">机构授信额度（万元）</th>',
				'<th width="170">剩余可用额度（万元）</th>',
				'<th>操作</th>'
			].join(''),
			item: 4
		}
	});
	//------ 担保函出具机构 end

	//项目管理>授信前管理> 立项申请

	//--------------------企业基本情况 start

	//企业性质 
	//----数据验证 基本信息 start
	$('#qyjbqk').validate($.extend(true, applyFN.validateRules));
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
			element.after(error);
		},
		submitHandler: function(form) {
			applyFN.submitForm();
		},
		rules: {
			projectName: {
				required: true
			},
			amount: {
				required: true,
				min: 0.01
			},
			timeLimit: {
				required: true,
				min: 1
			},
			coInstitutionName: {
				required: true
			},
			loanType: {
				required: true
			},
			loanPurpose: {
				required: true
			}
		},
		messages: {
			projectName: {
				required: '必填'
			},
			amount: {
				required: '请输入金额',
				min: '必须大于0'
			},
			timeLimit: {
				required: '请输入时间',
				min: '必须大于0'
			},
			coInstitutionName: {
				required: '请输入合作机构'
			},
			loanType: {
				required: '请选择类型'
			},
			loanPurpose: {
				required: '请选用途'
			}
		}
	});
	//----数据验证 申请贷款/担保情况 end

	//----授信用途
	var $loanPurpose = $('#loanPurpose');
	var loanPurposeObj = {
		main: [{
			type: '项目投资',
			value: '项目投资',
			sub: ''
		}, {
			type: '流动资金',
			value: '流动资金',
			sub: ''
		}, {
			type: '综合授信',
			value: '综合授信',
			sub: ''
		}, {
			type: '信用证开证',
			value: '信用证开证',
			sub: ''
		}, {
			type: '承兑汇票',
			value: '承兑汇票',
			sub: ''
		}, {
			type: '购置固定资产、及其设备等',
			value: '购置固定资产、及其设备等',
			sub: ''
		}, {
			type: '工程保证',
			value: '工程保证',
			sub: 'gcbz'
		}, {
			type: '贸易履约担保',
			value: '贸易履约担保',
			sub: 'mylydb'
		}, {
			type: '司法担保',
			value: '司法担保',
			sub: 'sfdb'
		}],
		sub: {
			gcbz: [{
				type: '投标保证',
				value: '投标保证',
			}, {
				type: '预付款保证',
				value: '预付款保证',
			}, {
				type: '工程履约保证',
				value: '工程履约保证',
			}, {
				type: '业主支持保证',
				value: '业主支持保证',
			}, {
				type: '工程完工保证',
				value: '工程完工保证',
			}, {
				type: '业务延期付款担保',
				value: '业务延期付款担保',
			}],
			mylydb: [{
				type: '保理业务',
				value: '保理业务',
			}, {
				type: '投标保证',
				value: '投标保证',
			}, {
				type: '预付款保证',
				value: '预付款保证',
			}, {
				type: '供货履约保证',
				value: '供货履约保证',
			}, {
				type: '产品维修/质量保证',
				value: '产品维修/质量保证',
			}, {
				type: '买房延期付款担保',
				value: '买房延期付款担保',
			}, {
				type: '政府采购履约保证',
				value: '政府采购履约保证',
			}],
			sfdb: [{
				type: '执行担保',
				value: '执行担保',
			}, {
				type: '财产保全担保',
				value: '财产保全担保',
			}]
		}
	};
	var loanPurposeTpl = ['<option value="">请选择</option>',
		'{{each list as item i}}',
		'      <option value="{{item.value}}">{{item.type}}</option>',
		'{{/each}}'
	].join('');
	//获取二级数据
	function loanPurposeGetSub(value) {
		for (var i = loanPurposeObj.main.length - 1; i >= 0; i--) {
			if (loanPurposeObj.main[i].value == value) {
				return loanPurposeObj.main[i].sub;
			}
		}
	}
	//填充数据
	$loanPurpose.find('#loanPurpose1').html(template.compile(loanPurposeTpl)({
		list: loanPurposeObj.main
	}));
	$loanPurpose.on('change', '#loanPurpose1', function() {
		var _sub = loanPurposeGetSub(this.value);
		if (_sub) {
			$loanPurpose.find('#loanPurpose2').html(template.compile(loanPurposeTpl)({
				list: loanPurposeObj.sub[_sub]
			})).removeClass('fn-hide');
			$loanPurpose.find('input[name="loanPurpose"]').val('');
		} else {
			$loanPurpose.find('#loanPurpose2').html('').addClass('fn-hide');
			$loanPurpose.find('input[name="loanPurpose"]').val(this.value);
		}
	}).on('change', '#loanPurpose2', function() {
		$loanPurpose.find('input[name="loanPurpose"]').val(this.value);
	});
	//--------------------申请贷款/担保情况 end
	//
});