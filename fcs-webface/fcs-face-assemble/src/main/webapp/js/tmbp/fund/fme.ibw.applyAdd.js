define(function(require, exports, module) {

	var hintPopup = require('zyw/hintPopup');
	//必填集合
	require('zyw/requiredRules');
	var _form = $('#form'),
		_allWhetherNull = {
			stringObj: 'loanAmount,useTime,backTime,interestTime,interestRate,protocolCode,creditorName,formInnerLoanInterestType,applyDeptName',
			boll: false
		},
		requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				required: true,
				messages: {
					required: '必填'
				}
			};
		}),
		maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',remark,compareStandardValue', _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				maxlength: 50,
				messages: {
					maxlength: '已超出50字'
				}
			};
		}),
		isTimeRules = _form.requiredRulesSharp('useTime,backTime,interestTime', _allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				isTime: true,
				messages: {
					isTime: '请输入正确的时间格式'
				}
			};
		}),
		rulesAllBefore = {
			loanAmount: {
				isMoneyMillion: true,
				messages: {
					isMoneyMillion: '请输入整数12位以内,小数2位以内的数字'
				}
			},
			interestRate: {
				isPercentTwoDigits: true,
				messages: {
					isPercentTwoDigits: '请输入0.01-100之间的数字'
				}
			},
			loanReason: {
				maxlength: 1000,
				messages: {
					maxlength: '已超出1000字'
				}
			},
			// protocolCode: {
			// 	isPercentTwoDigits: true,
			// 	messages: {
			// 		isPercentTwoDigits: '请输入0.01-100之间的数字'
			// 	}
			// }
		},
		_rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore, isTimeRules),
		validataCommon = require('zyw/fund/fme.ibw.validataCommon');

	validataCommon({
		form: _form,
		allWhetherNull: _allWhetherNull,
		rulesAll: _rulesAll,
		// hintPopup: '请选择增加的二级指标选择项',
		href: '/fundMg/innerLoan/innerLoanList.htm',
		// previewHref: 'cityEnterprise.htm?level=3&type=CTCW&view=true'
		condition: function() {

			var creditoRemind = $('.creditoRemind:not(".cancel")').val();
			var borrowRemind = $('.borrowRemind:not(".cancel")').val();

			if (creditoRemind && borrowRemind && creditoRemind == borrowRemind) {

				hintPopup('借款部门与债权人不能相同');
				return false;

			}

		}
	});

	$('.choiceList').find('tbody').eq($('.choiceSelect').find('option').not(':selected').index()).find('input,select').val('');

	//合同编号
	// var popupWindow = require('zyw/popupWindow');

	// $('#creditorBtn').click(function(event) {

	// 	var $this = $(this);

	// 	fundDitch = new popupWindow({

	// 		YwindowObj: {
	// 			content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
	// 			closeEle: '.close', //find关闭弹窗对象
	// 			dragEle: '.newPopup dl dt' //find拖拽对象
	// 		},

	// 		ajaxObj: {
	// 			url: '/projectMg/contract/contractChoose.htm',
	// 			type: 'post',
	// 			dataType: 'json',
	// 			data: {
	// 				// isChargeNotification: "IS",
	// 				// projectCode: _projectCode,
	// 				pageSize: 6,
	// 				pageNumber: 1
	// 			}
	// 		},

	// 		pageObj: { //翻页
	// 			clickObj: '.pageBox a.btn', //find翻页对象
	// 			attrObj: 'page', //翻页对象获取值得属性
	// 			jsonName: 'pageNumber', //请求翻页页数的dataName
	// 			callback: function($Y) {

	// 				//console.log($Y)

	// 			}
	// 		},

	// 		callback: function($Y) {

	// 			$Y.wnd.on('click', 'a.choose', function(event) {

	// 				var _this = $(this),
	// 					_text = _this.parents('tr').find('td:eq(1)').text();

	// 				$this.siblings('#creditor').val(_text);

	// 				$Y.close();

	// 			});

	// 		},

	// 		console: false //打印返回数据

	// 	});

	// });

	//---选择部门 start
	// var $fnOrgName = $('#fnOrgName'),
	// 	$fnOrgId = $('#fnOrgId');

	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	// 初始化弹出层
	var singleSelectUserDialog1 = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true&selectTop=Y', {
		'title': '部门组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$('.creditorBtn1').on('click', function() {

		//这里也可以更新已选择机构
		var _arr = [],
			_this = $(this),
			$fnOrgName = _this.siblings('.fnOrgName'),
			$fnOrgId = _this.siblings('.fnOrgId'),
			_nameArr = $fnOrgName.val().split(','),
			_idArr = $fnOrgId.val().split(',');

		for (var i = 0; i < _nameArr.length; i++) {
			if (_nameArr[i]) {
				_arr.push({
					id: _idArr[i],
					name: _nameArr[i]
				});
			}
		}

		singleSelectUserDialog1.obj.arrys = _arr;

		singleSelectUserDialog1.init(function(relObj) {

			if (/公司领导/.test(relObj.orgName)) {
				hintPopup('不能选择公司领导');
				return false;
			}

			$fnOrgId.val(relObj.orgId).blur();
			$fnOrgName.val(relObj.orgName).blur();

		});

	});


	var singleSelectUserDialog2 = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true', {
		'title': '部门组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$('.creditorBtn2').on('click', function() {

		//这里也可以更新已选择机构
		var _arr = [],
			_this = $(this),
			$fnOrgName = _this.siblings('.fnOrgName'),
			$fnOrgId = _this.siblings('.fnOrgId'),
			_nameArr = $fnOrgName.val().split(','),
			_idArr = $fnOrgId.val().split(',');

		for (var i = 0; i < _nameArr.length; i++) {
			if (_nameArr[i]) {
				_arr.push({
					id: _idArr[i],
					name: _nameArr[i]
				});
			}
		}

		singleSelectUserDialog2.obj.arrys = _arr;

		singleSelectUserDialog2.init(function(relObj) {

			if (/公司领导/.test(relObj.orgName)) {
				hintPopup('不能选择公司领导');
				return false;
			}

			$fnOrgId.val(relObj.orgId).blur();
			$fnOrgName.val(relObj.orgName).blur();

		});

	});
	//---选择部门 end


	$('body').on('change', '.selectChange', function(event) {

		var $this, $target;

		$this = $(this);
		$target = $this.parents('table').find('tbody').eq($this.find(':selected').index());

		$target.show().find('[name]').removeAttr('disabled').removeClass('cancel').end().siblings('tbody').hide().find('[name]').attr('disabled', true).addClass('cancel');

	}).find('.selectChange').trigger('change');

})