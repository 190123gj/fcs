define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请 列表
	var util = require('util');
	var template = require('arttemplate');
	require('Y-msg');
	require('input.limit');

	//选择项目
	var getList = require('zyw/getList');
	var _getList = new getList();
	_getList.init({
		title: '项目列表',
		ajaxUrl: '/baseDataLoad/queryProjects.json?phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,RE_COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES&isAdd=YES',
		btn: '#choose',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
				'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
				'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
				'        <td>{{item.busiTypeName}}</td>',
				'        <td>{{item.amount}}</td>',
				'        <td><a class="choose" projectcode="{{item.projectCode}}" customerId="{{item.customerId}}" customerName="{{item.customerName}}" href="javascript:void(0);">选择</a></td>', //跳转地址需要的一些参数
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
				'<th width="100">授信金额(元)</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 6
		},
		callback: function($a) {
			//跳转地址
			window.location.href = '/projectMg/riskWarning/edit.htm?customerName=' + $a.attr('customerName');
		}
	});

	//侧边栏
	(new(require('zyw/publicOPN'))()).addOPN([{
		name: '暂存',
		alias: 'save',
		event: function() {
			formSubmit('SAVE');
		}
	}]).init().doRender();

	//公共提交\暂存
	function formSubmit(type) {

		
		document.getElementById('state').value = type;
		util.ajax({
			url: $form.attr('action'),
			data: $('#form').serialize(),
			success: function(res) {
				if (res.success) {
					if (type=='SUBMIT') {
//						util.ajax({
//							url: '/projectMg/form/submit.htm',
//							data: {
//								formId: res.form.formId
//							},
//							success: function(res2) {
//								if (res2.success) {
//									Y.alert('提示', function() {
//										window.location.href = '/projectMg/riskWarning/list.htm';
//									});
//								} else {
//									Y.alert('提示', res2.message);
//								}
//							}
//						});
						util.postAudit({
                            formId: res.form.formId
                        }, function () {
                            window.location.href = '/projectMg/riskWarning/list.htm';
                        })
					} else {
						Y.alert('提示', '已保存', function() {
							//window.location.reload(true);
							window.location.href = '/projectMg/riskWarning/list.htm';
						});
					}
				} else {
					Y.alert('提示', res.message);
				}
			}
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
            liftingReason: {
                required: true
            }
        },
        messages: {
            liftingReason: {
                required: '必填'
            }
        }
    };

	//------ 提交 start
	//

	var $fnInput = $('.fnInput'),
		$form = $('#form');
    $form.validate(validateRules);
	$form.on('click', '.submit', function() {
        if(!$form .valid()) return;
		var _isPass = true;

		$fnInput.each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
			}
		});

		if (!_isPass) {
			Y.alert('提示', '请填写完必填项');
			return;
		}		var _isSave = $(this).hasClass('save');

		formSubmit(_isSave ? 'SAVE' : 'SUBMIT');

	});

	//------ 提交 end

	// template 函数
	template.helper('isChecked', function(key, arr) {
		return ($.inArray(key, arr) >= 0) ? ' checked="checked"' : '';
	});

	//------ 信号的选择 start
	var $fnSignalBox = $('#fnSignalBox'),
		$fnSignalTitle = $('#fnSignalTitle'),
		$fnSignalList = $('#fnSignalList'),
		isSpecial = true, //用于判断是否填了值
		specialCode = document.getElementById('specialCode'), //用于判断是否填了值
		generalCode = document.getElementById('generalCode'), //用于判断是否填了值
		$special = $('#special'),
		$general = $('#general');

	$special.on('click', function() {
		isSpecial = true;
		showSignalBox(specialCode.value.split(','), '选择特别信号种类');
	});
	$general.on('click', function() {
		isSpecial = false;
		showSignalBox(generalCode.value.split(','), '选择一般信号种类');
	});
	// 关闭
	$fnSignalBox.on('click', '.close', function() {
		$fnSignalBox.addClass('fn-hide');
		$fnSignalList.empty();
	}).on('click', '#fnSignalSure', function() {
		//确定
		var _codeArr = [],
			_strArr = [];

		$fnSignalList.find('input:checked').each(function(index, el) {
			var _this = $(this);
			_codeArr.push(_this.val());
			_strArr.push(_this.parent().text());
		});

		if (isSpecial) {
			document.getElementById('specialStr').value = _strArr.join('');
			specialCode.value = _codeArr;
		} else {
			document.getElementById('generalStr').value = _strArr.join('');
			generalCode.value = _codeArr;
		}

		$fnSignalBox.addClass('fn-hide');
		$fnSignalList.empty();

	});

	//显示弹出层
	function showSignalBox(value, title) {

		//显示
		$fnSignalBox.removeClass('fn-hide');

		$fnSignalTitle.html(title);
		//还原数据
		$fnSignalList.html(template('t-signal', {
			list: isSpecial ? signalArr[0] : signalArr[1],
			checkedArr: value
		}));

	}

	var signalArr;
	//	var signalArr = [
	//		[{
	//			code: '1',
	//			str: '法定代表人、实际控制人突然死亡，且严重影响我司授信资产安全的。'
	//		}, {
	//			code: '2',
	//			str: '法定代表人、实际控制人失踪或发生其他突发性事件，以及发生吸毒、严重赌博等不良行为的。'
	//		}, {
	//			code: '3',
	//			str: '作为违约或被告方涉及诉讼、仲裁，且严重影响我司授信资产安全的。'
	//		}, {
	//			code: '4',
	//			str: '出现财产被查封、银行账户被冻结，且严重影响我司授信资产安全的。'
	//		}, {
	//			code: '5',
	//			str: '被其他金融机构列为淘汰对象或已经采取淘汰措施的。'
	//		}, {
	//			code: '6',
	//			str: '在我司或其他金融机构出现本金逾期、垫款或欠息，或在其他金融机构资产风险分类出现不良的。'
	//		}, {
	//			code: '7',
	//			str: '抵（质）押物价值明显减少，且授信对象无能力或不配合我司采取补救措施的。'
	//		}, {
	//			code: '8',
	//			str: '生产经营出现异常，股权、财务状况发生重大变化，可能会造成我司授信资产损失的。'
	//		}, {
	//			code: '9',
	//			str: '上市公司被ST、*ST、违规处罚的。'
	//		}, {
	//			code: '10',
	//			str: '投资经营失误、被欺诈、遭受灾害或遭遇大额经济纠纷等并造成重大损失的。'
	//		}, {
	//			code: '11',
	//			str: '进行改制、改造、联营、合并、分立、合资、重组等，且严重影响我司授信资产安全的。'
	//		}, {
	//			code: '12',
	//			str: '被有关部门如海关、税务、证券监管、环保、工商等立案查处，且影响企业正常经营的。'
	//		}, {
	//			code: '13',
	//			str: '授信到期前三个工作日内，企业还款账户内资金无法足额清偿我司担保债务本息的。'
	//		}, {
	//			code: '14',
	//			str: '资产大量被抵押，或对外提供大额的担保，接近或超过自身的承受能力，但又未能说明融资用途。'
	//		}, {
	//			code: '15',
	//			str: '出售、变卖主要的生产、经营性固定资产。'
	//		}, {
	//			code: '16',
	//			str: '其他可能导致我司授信资产损失的事项。'
	//		}],
	//		[{
	//			code: '1',
	//			str: '频繁更换主要管理人员、财务人员或技术人员,以及出现集体辞职现象的。'
	//		}, {
	//			code: '2',
	//			str: '法定代表人、实际控制人死亡，但不会对我司授信资产安全造成严重影响的。'
	//		}, {
	//			code: '3',
	//			str: '作为违约或被告方涉及诉讼、仲裁，但不会对我司授信资产安全造成严重影响的。'
	//		}, {
	//			code: '4',
	//			str: '出现财产被查封、银行账户被冻结，但不会对我司授信资产安全造成严重影响的。'
	//		}, {
	//			code: '5',
	//			str: '突然发生重大关联交易的。'
	//		}, {
	//			code: '6',
	//			str: '授信需求增长异常或短期债务超常增加的。'
	//		}, {
	//			code: '7',
	//			str: '核心业务发生较大变动，库存发生异常，主要供货商或客户流失的；大额资金突然向新上下游企业转移的。'
	//		}, {
	//			code: '8',
	//			str: '主要财务指标发生异常，没有合理原因出现总资产、总负债、应收账款、其他应收款、存货、金融机构授信余额等与年初及上年同期比大幅增减及现金流量发生异常等对企业经营产生不利影响的。'
	//		}, {
	//			code: '9',
	//			str: '员工人数、用电量、上缴税款等有较大幅度减少的。'
	//		}, {
	//			code: '10',
	//			str: '客户自身的配套资金不到位或不充足的。'
	//		}, {
	//			code: '11',
	//			str: '产品出现严重质量问题，或企业被新闻媒体负面曝光，影响较大的。'
	//		}, {
	//			code: '12',
	//			str: '股东变更频繁的。'
	//		}, {
	//			code: '13',
	//			str: '资金往来异常的。'
	//		}, {
	//			code: '14',
	//			str: '贷款资金违规流入房市、股市等未及时进行纠正的。资金用途与评审报告披露信息严重不符，甚至将贷款挪作不正当用途，要求改正而不予改正。'
	//		}, {
	//			code: '15',
	//			str: '无正当理由而不按要求提供财务报表，不配合授信后跟踪检查。'
	//		}, {
	//			code: '16',
	//			str: '对授信后跟踪检查中发现的问题和纠纷意见持不理睬或敷衍应付态度。'
	//		}, {
	//			code: '17',
	//			str: '尚无逾月拖欠还款、还息、支付保费情形，但不能主动、及时履行，须多次催促、提醒。'
	//		}, {
	//			code: '18',
	//			str: '对于保函项目，如因承包原因导致工程进度略有滞后、工程款有拖欠但尚不严重的，受益人、保函申请人之间矛盾有日趋加深迹象。'
	//		}, {
	//			code: '19',
	//			str: '经判断其他应当列为一般预警信号的现象。'
	//		}]
	//	];

	$.ajax({
		url: '/projectMg/riskWarning/querySignal.json',
		type: 'get',
		dataType: 'json',
		data: {},
		success: function(res) {
			if (res.success) {
				signalArr = res.data.signals;
			}
		}
	});

	//------ 信号的选择 end

});