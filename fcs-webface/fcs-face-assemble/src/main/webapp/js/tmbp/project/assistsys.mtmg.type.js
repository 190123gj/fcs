define(function(require, exports, module) {
	// 项目管理 > 会议管理 > 新增会议类型

	require('input.limit');
	require('Y-msg');

	var util = require('util');

	var getList = require('zyw/getList');

	//------ 适用公司/部门 start
	var scope = "{type:\"system\",value:\"all\"}";
	var arrys = []; //[{id:'id',name='name'}];
	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	var applyCompanyDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=false', {
		'title': '适用公司/部门',
		'width': 850,
		'height': 460,
		'scope': scope,
		'arrys': arrys,
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	var applyCompanyName = document.getElementById('applyCompanyName');
	var applyDeptId = document.getElementById('applyDeptId');

	$('#applyCompanyBtn').on('click', function() {

		//已选择
		var _idArr = applyDeptId.value.split(','),
			_nameArr = applyCompanyName.value.split(',');

		arrys.length = 0;

		for (var i = _idArr.length - 1; i >= 0; i--) {
			if (_idArr[i]) {
				arrys.push({
					id: _idArr[i],
					name: _nameArr[i]
				});
			}
		}

		applyCompanyDialog.init(function(relObj) {

			applyCompanyName.value = relObj.orgName;
			applyDeptId.value = relObj.orgId;

		});

	});

	$('#applyCompanyC').on('click', function() {
		applyCompanyName.value = '';
		applyDeptId.value = '';
	});
	//------ 适用公司/部门 end

	//------ 决策机构 start
	var _getList = new getList();
	_getList.init({
		title: '合作决策机构',
		ajaxUrl: '/baseDataLoad/decisionInstitution.json',
		btn: '#decisionInstitutionBtn',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac">',
				'        <td>{{item.institutionName}}</td>',
				'        <td>{{item.institutionMembers}}</td>',
				'        <td><a class="choose" decisionInstitutionName="{{item.institutionName}}" decisionInstitutionCode="{{item.institutionId}}" decisionInstitutionMan="{{item.institutionMembers}}" decisionInstitutionUser="{{item.institutionMembers.split(\'\,\').length}}" href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['决策机构名称：',
				'<input class="ui-text fn-w200" type="text" name="institutionName">&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th width="200">决策机构名称</th>',
				'<th>人员</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 3
		},
		callback: function($a) {
			document.getElementById('decisionInstitutionName').value = $a.attr('decisionInstitutionName');
			document.getElementById('decisionInstitutionCode').value = $a.attr('decisionInstitutionCode');
			document.getElementById('decisionInstitutionUser').value = $a.attr('decisionInstitutionUser');
			document.getElementById('decisionInstitutionMan').value = $a.attr('decisionInstitutionMan');
			document.getElementById('decisionInstitutionManNum').innerHTML = $a.attr('decisionInstitutionUser');
		}
	});
	$('#decisionInstitutionC').on('click', function() {
		document.getElementById('decisionInstitutionName').value = '';
		document.getElementById('decisionInstitutionCode').value = '';
		document.getElementById('decisionInstitutionUser').value = '0';
		document.getElementById('decisionInstitutionMan').value = '';
		document.getElementById('decisionInstitutionManNum').innerHTML = '0';
	});
	//------ 决策机构 end

	//会议类型
	$('#typeCode').on('change', function() {

		var self = this;

		$fnIfVote.removeAttr('disabled');

		if (!!self.value) {
			$fnIfVote.each(function(index, el) {
				if ((el.value == 'NO' && self.value == 'GM_WORKING') || (el.value == 'IS' && (self.value == 'PROJECT_REVIEW' || self.value == 'RISK_HANDLE'))) {
					el.click();
				} else {
					$fnIfVote.eq(index).attr('disabled', 'disabled');
				}
			});
		}
		// if (this.value == 'GM_WORKING') {
		// 	$fnIfVote.each(function(index, el) {
		// 		if (el.value == 'NO') {
		// 			el.click();
		// 		} else {
		// 			$fnIfVote.eq(index).attr('disabled', 'disabled');
		// 		}
		// 	});
		// } else if (this.value == 'PROJECT_REVIEW' || this.value == 'RISK_HANDLE') {
		// 	$fnIfVote.each(function(index, el) {
		// 		if (el.value == 'YES') {
		// 			el.click();
		// 		} else {
		// 			$fnIfVote.eq(index).attr('disabled', 'disabled');
		// 		}
		// 	});
		// } else {
		// 	$fnIfVote.removeAttr('disabled');
		// }
	});
	//切换投票规则
	var $fnVoteRules = $('.fnVoteRules'),
		$voteRuleType = $('#voteRuleType');
	$voteRuleType.on('change', function() {

		var _class = this.value;

		$fnVoteRules.find('input').removeClass('fnInput').val('').attr('disabled', 'disabled');

		$fnVoteRules.addClass('fn-hide').each(function(index, el) {
			if ($fnVoteRules.eq(index).hasClass(_class)) {
				$fnVoteRules.eq(index).removeClass('fn-hide').find('input').addClass('fnInput').removeAttr('disabled');
			}
		});

	});

	//是否投票
	var $isToVote = $('#isToVote'),
		$fnIfVote = $('.fnIfVote');
	$fnIfVote.on('click', function() {
		var _thisVal = this.value;
		if (_thisVal == 'IS') {
			$isToVote.removeClass('fn-hide').find('.fnRequired').addClass('fnInput');
			$isToVote.find('input,select').removeAttr('disabled');
			$voteRuleType.trigger('change');
		} else {
			$isToVote.addClass('fn-hide').find('.fnRequired').removeClass('fnInput');
			$isToVote.find('input,select').attr('disabled', 'disabled');
		}
	});

	//决策人
	var $majorNum = $('#majorNum'),
		$lessNum = $('#lessNum');
	$majorNum.on('blur', function() {
		if (+this.value < +$lessNum.val()) {
			this.value = $lessNum.val()
		}
		//决策机构最大人数
		var _max = document.getElementById('decisionInstitutionUser').value || 0;
		if (this.value > +_max) {
			this.value = _max;
		}
	});
	$lessNum.on('blur', function() {
		if (+this.value > +$majorNum.val()) {
			this.value = $majorNum.val();
		}
		if (+this.value < 1) {
			this.value = 1;
		}
	});
	//提交
	var $addForm = $('#addForm'),
		$typeName = $('#typeName'),
		$typeCode = $('#typeCode'),
		typeCodeVal = $typeCode.val(),
		typeNameVal = $typeName.val();
	$addForm.on('click', '#submit', function() {

		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		var _isPass = true,
			_isPassEq;

		$('.fnInput').each(function(index, el) {
			if (!!!el.value) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}
		});

		if (!_isPass) {
			Y.alert('提示', '请填写完整表单', function() {
				$('.fnInput').eq(_isPassEq).focus();
			});
			_this.removeClass('ing');
			return false;
		}

		//验证会议名称是否重复
		var _isRepeat = false;
		if ((!typeNameVal && !typeCodeVal) || (typeNameVal != $typeName.val() || typeCodeVal != $typeCode.val())) {
			util.ajax({
				url: '/projectMg/meetingMg/checkCouncilName.htm?typeName=' + encodeURIComponent($typeName.val()) + '&typeCode=' + encodeURIComponent($typeCode.val()),
				async: false,
				success: function(res) {
					if (res.success) {
						_isRepeat = true;
					}
				}
			});
		}

		if (_isRepeat) {
			Y.alert('提示', '会议名称已存在');
			_this.removeClass('ing');
			return;
		}

		//决策人数 是否填写
		if ($majorNum.val() == '') {
			$majorNum.attr('disabled', 'disabled');
		}
		if ($lessNum.val() == '') {
			$lessNum.attr('disabled', 'disabled');
		}
		util.ajax({
			url: $addForm.attr('action'),
			data: $addForm.serialize(),
			success: function(res) {

				if (res.success) {
					Y.alert('提示', '保存成功', function() {
						window.location.href = '/projectMg/meetingMg/councilTypeList.htm';
					});
				} else {
					checkCouncilName.htm
					Y.alert('提示', res.message);
					_this.removeClass('ing');
				}

			}
		});

	}).on('blur', '.fnInputCount', function() {
		//最大限制
		//决策机构最大人数
		var _maxs = document.getElementById('decisionInstitutionUser').value || 0;
		var _max = ($majorNum.val() == '') ? _maxs : $majorNum.val();
		if (+this.value > +_max) {
			this.value = _max;
		}
	});


});