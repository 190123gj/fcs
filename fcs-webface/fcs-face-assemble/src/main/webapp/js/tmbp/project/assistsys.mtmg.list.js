define(function(require, exports, module) {
	//辅助系统-会议管理-会议列表
	require('zyw/publicPage');
	require('Y-msg');
	require('zyw/upAttachModify')

	var Select = require('zyw/multSelect');
	var util = require('util');
	var getList = require('zyw/getList');

	//------ 修改讨论项目 start

	var BPMiframe = require('BPMiframe');
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true&isExternal=true', {
		'title': '组织选择器',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	// window.frameElement.dialog=singleSelectUserDialog;
	$('#applyDeptBtn').on('click', function() {
		singleSelectUserDialog.init(function(relObj) {
			$('#applyDeptID').val(relObj.orgId); //多选用逗号隔开
			$('#applyDeptName').val(relObj.orgName); //多选用逗号隔开
		});

	});

	var discuss = new Select('上会项目'),
		isAjaxIng = false,
		selectedArr = []; //缓存已选的上会项目

	$('.canEdit').on('click', function() {
		var _this = $(this),
			_councilCode = _this.attr('typecode'),
			_typeId = _this.attr('typeId'),
			_councilId = _this.attr('councilid'),
			_projectcode = _this.attr('projectcode'),
			_projectskey = _this.attr('projectskey'),
			_valArr = _this.attr('val').split(','),
			_txtArr = _this.text().split(','),
			_codeArr = _projectcode.split(','),
			_keyArr = _projectskey.split(',');

		selectedArr.length = 0; //清空上会项目

		for (var i = 0; i < _valArr.length; i++) {
			selectedArr.push({
				applyId: _valArr[i],
				projectName: _txtArr[i],
				projectCode: _codeArr[i],
				projectsKey: _keyArr[i]
			});
		}

		discuss.init({
			// input: {
			// 	val: 'applyIds',
			// 	text: 'projectsName'
			// },
			// ajaxUrl: '/baseDataLoad/councilApply.json?councilCode=' + _councilCode,
			// keyName: [{
			// 	val: 'applyId',
			// 	text: 'projectName',
			// 	list: 'subOrg'
			// }],
			// selected: selectedArr,
			// level: ['待上会项目列表'],
			input: ['applyId', 'projectName', 'projectCode'],
			markKey: {
				val: 'projectCode',
				txt: 'projectName'
			},
			ajaxUrl: '/baseDataLoad/councilApply.json?councilCode=' + _councilCode + '&typeId=' + _typeId,
			selected: $.extend(true, [], selectedArr),
			listTitle: '待上会项目列表',
			close: function(self) {
				if (isAjaxIng) {
					return;
				}

				//如果全部都删了
				if (!!!self.result().applyId) {
					Y.alert('提示', '请选择待上会项目');
					return;
				}


				//提交数据并刷新
				//对比数据是否有改动
				var _valueArr = self.result().applyId || [],
					_index = -1,
					_isOld = false,
					_applyIds = [],
					_projectsKey = [],
					_resultArr = []; //结果数据
				// for (var i = 0; i < _valueArr.length; i++) {
				// 	var _obj = {};
				// 	_obj.applyId = _valueArr[i];
				// 	_obj.projectName = _textArr[i];
				// 	_resultArr.push(_obj);
				// }

				// 获取到结果数组，还原key

				for (var i = 0; i < _valueArr.length; i++) {

					// 循环 applyId
					for (var j = 0; j < selectedArr.length; j++) {

						if (selectedArr[j].applyId == _valueArr[i]) {
							//如果是老数据
							_isOld = true;
							_index = j;

						}

					}

					//对比数据
					_applyIds.push(_valueArr[i]);
					_projectsKey.push(_isOld ? selectedArr[_index].projectsKey : '0');

					//还原标记
					_index = -1;
					_isOld = false;

				}

				changeProject(self, _councilId, _applyIds.toString(), _projectsKey.toString());


				// if (_resultArr.length == selectedArr.length) {
				// 	var _equalArr = [];
				// 	for (var i = 0; i < selectedArr.length; i++) {
				// 		for (var j = 0; j < _resultArr.length; j++) {
				// 			if (util.isObjectValueEqual(selectedArr[i], _resultArr[j])) {
				// 				_equalArr.push(selectedArr[i]);
				// 			}
				// 		}
				// 	}
				// 	if (_equalArr.length == selectedArr.length) {
				// 		//相等
				// 		self.$box.remove();
				// 	} else {
				// 		changeProject(self, _councilId);
				// 	}
				// } else {
				// 	//不相等
				// 	changeProject(self, _councilId);
				// }
			},
			hide: function() {
				//重新类中的hide方法
				window.location.reload(true);
			}
		}).query(function(self) {

			//追加项目
			var _s = '';
			for (var i = 0; i < selectedArr.length; i++) {
				_s += '<li class="fn-csp li fnChoose" applyId="' + selectedArr[i].applyId + '" projectName="' + selectedArr[i].projectName + '" val="' + selectedArr[i].projectCode + '" projectCode="' + selectedArr[i].projectCode + '">[' + selectedArr[i].projectCode + ']' + selectedArr[i].projectName + '</li>';
			}

			self.listHtml += '<ul class="ul">' + _s + '</ul>';
			self.$box.find('.multiple').eq(0).find('.multiple-list').html(self.listHtml);

			self.show();

		});

	});

	function changeProject(self, councilId, applyIds, projectsKey) {

		isAjaxIng = true;
		util.ajax({
			url: '/projectMg/meetingMg/changeCouncilProjects.json',
			data: {
				councilId: councilId,
				applyIds: applyIds,
				projectsKey: projectsKey
			},
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '修改成功，立即刷新网页？', function(opn) {
						if (opn == 'yes') {
							location.reload(true);
						} else {
							self.$box.remove();
							isAjaxIng = false;
						}
					});
				} else {
					Y.alert('提示', res.message);
					isAjaxIng = false;
				}
			}
		});

	}

	//------ 修改讨论项目 end

	//------ 删除
	$('#delBtn').on('click', function() {
		var _arr = [],
			$_checked = $('input.fnCheckbox:checked');
		$_checked.each(function(index, el) {
			_arr.push('councilIds[' + index + ']=' + el.value);
		});
		if (!_arr.length) {
			Y.alert('提示', '请选择需要删除的会议');
			return;
		}

		Y.confirm('提示', '确定删除已选择的会议？', function(opn) {

			if (opn == 'yes') {

				util.ajax({
					url: '/projectMg/meetingMg/deleteCouncil.json?' + _arr.join('&'),
					success: function(res) {
						if (res.success) {
							Y.alert('提示', '操作成功', function() {
								window.location.reload(true);
							});
						} else {
							Y.alert('提示', res.message);
						}
					}
				});

			}

		});

	});

	//------ 待上会列表 start

	var awaitUrl, meetingTypeCode = '', //跳转url、会议类型code
		applyProjectName = [], // 申请项目的名称
		applyDeptId = []; // 申请项目部门的Id
	$('#awaitAdd').on('click', function(e) {

		e.preventDefault();

		var _href = this.href;

		var _arr = [],
			canSubmit = true;

		var _$checked = $('#awaitList').find('input[name="applyIds"]:checked');

		if (!!!_$checked.length) {
			Y.alert('提示', '请选择上会项目');
			return;
		}

		// 每次清空 已选的部门id
		applyDeptId = [];
		applyProjectName = [];

		_$checked.each(function(index, el) {
			var _this = $(this);
			if (!index) {
				meetingTypeCode = _this.attr('councilType');
			} else {
				if (meetingTypeCode != _this.attr('councilType')) {
					canSubmit = false;
					return;
				}
			}
			_arr.push({
				councilType: _this.attr('councilType'),
				applyIds: _this.attr('applyIds'),

			});
			applyDeptId.push(_this.attr('applyDeptId'));
			applyProjectName.push(_this.attr('projectName'));
		});

		if (canSubmit) {
			var _applyIds = [];
			for (var i = 0; i < _arr.length; i++) {
				_applyIds.push(_arr[i].applyIds);
			}
			//缓存跳转链接，弹出会议选择层
			awaitUrl = _href + '?applyIds=' + _applyIds.join(',') + '&councilCode=' + meetingTypeCode + '&councilTypeId=';
			//弹出选会议
			_getList.resetAjaxUrl('/baseDataLoad/councilTypeList.json?typeCode=' + meetingTypeCode);
			document.getElementById('awaitShowBtn').click();
			// window.location.href = _href + '?councilTypeId=1&applyIds=' + _applyIds.join(',');
			// window.location.href = _href + '?councilType=' + firstType + '&projectsID=' + _projectsID.join(',') + '&projects=' + _projects.join(',');
		} else {
			Y.alert('提示', '请选择相同的上会类型');
		}

	});

	var _getList = new getList();
	_getList.init({
		ajaxUrl: '/baseDataLoad/councilTypeList.json?typeCode=' + meetingTypeCode,
		btn: '#awaitShowBtn',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac">',
				'        <td title="{{item.typeName}}">{{(item.typeName.length > 10)? item.typeName.substr(0,10)+\'\.\.\' : item.typeName}}</td>',
				'        <td title="{{item.decisionInstitutionName}}">{{(item.decisionInstitutionName.length > 10)? item.decisionInstitutionName.substr(0,10)+\'\.\.\' : item.decisionInstitutionName}}</td>',
				'        <td title="{{item.applyCompany}}">{{(item.applyCompany && item.applyCompany.length > 10)? item.applyCompany.substr(0,10)+\'\.\.\' : item.applyCompany}}</td>',
				'        <td>{{item.ifVote}}</td>',
				'        <td><a class="choose" typeid="{{item.typeId}}" applydeptid="{{item.applyDeptId}}" href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: '',
			thead: ['<th>会议名称</th>',
				'<th>决策机构</th>',
				'<th>适用公司/部门</th>',
				'<th width="70">是否需要投票表决</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {

			// 验证当前项目是否在选择的同一个部门
			var _isPass = true,
				_isPassEq,
				_noPassName = [],
				_passName = [],
				_applydeptidArr = $a.attr('applydeptid').split(',');

			// applyProjectName
			$.each(applyDeptId, function(i, val) {

				if ($.inArray(val, _applydeptidArr) == -1) {
					// _isPass = false;
					// _isPassEq = index;
					// return false;
					_noPassName.push(applyProjectName[i]);
				} else {
					_passName.push(applyProjectName[i]);
				}

			});


			// if (!_isPass) {
			// 	Y.alert('提示', '第' + (_isPassEq + 1) + '个项目的申请部门并不在当前会议中');
			// 	return;
			// }

			if (_noPassName.length > 0) {
				Y.alert('提示', '当前会议' + ((_passName.length > 0) ? ('适用于“' + _passName.join('、') + '”，') : '') + '不适用于“' + _noPassName.join('、') + '”');
				return;
			}

			util.direct2param(awaitUrl + $a.attr('typeid'), '/projectMg/meetingMg/toAddCouncil.htm');
			// window.location.href = awaitUrl + $a.attr('typeid');
		}
	});
	//------ 待上会列表 end

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	if (document.getElementById('hddFormId')) {
		publicOPN.addOPN([{
			name: '查看尽职调查',
			url: '/projectMg/investigation/view.htm?formId=' + $('#hddFormId').val(),
		}]);
		publicOPN.init().doRender();
	}
	//------ 侧边栏 end

	util.listOPN({
		withdraw: {
			url: '/projectMg/form/cancel.htm',
			message: '已撤销',
			opn: '撤回'
		}
	});

	//弹窗
	require('Y-window');

	$('#list').on('click', '.endCouncil', function() {
		//------ 结束会议
		var _this = $(this),
			_$tr = _this.parents('tr');

		Y.confirm('提示', '确定要结束该会议？', function(opn) {

			if (opn == 'yes') {
				util.ajax({
					url: '/projectMg/meetingMg/endCouncil.json',
					data: {
						councilId: _$tr.attr('councilid'),
						//oldCouncil: _this.attr('oldCouncil'),
						//councilVoteMessage: modalwnd.wnd.find('#councilVoteMessage').val()
					},
					success: function(res) {
						$('.util-loading').remove();
						_this.removeClass('ing');
						if (res.success) {
							Y.alert('消息提醒', res.message, function() {
								window.location.reload(true)
							});
						} else {
							Y.alert('消息提醒', res.message);
						}
						modalwnd.wnd.close();
					}
				});
			}

		});

	});


	$('#list').on('click', '.endCouncilNew', function() {
		//------ 结束会议
		var _this = $(this),
			_$tr = _this.parents('tr');

		$('body').Y('Window', {
			content: ['<div class="newPopup">',
				'    <span class="close">×</span>',
				'    <dl>',
				// '        <dt><span>123</span></dt>',
				'        <dd class="fn-text-c">',
				// '            <h1 class="fn-color-4a fn-mt10">该客户的风险覆盖率为<em class="remind">30%</em></h1>',
				'            <div>',
				'                <span class="m-label">投票明细：</span>',
				'                <span>',
				'                    <input class="ui-text fn-200" type="text" id="councilVoteMessage">',
				'                </span>',
				'                <span class="fn-ml20">格式：通过票数/总票数</span>',
				'            </div>',
				'            <a class="ui-btn ui-btn-submit ui-btn-next fnNext fn-mt10 councilVoteMessageNext">确定</a>',
				'        </dd>',
				'    </dl>',
				'</div>'
			].join(""),
			modal: true,
			key: 'modalwnd',
			simple: true,
			closeEle: '.close'
		});

		var modalwnd = Y.getCmp('modalwnd');

		modalwnd.wnd.on('click', '.councilVoteMessageNext', function() {

			Y.confirm('提示', '确定要结束该会议？', function(opn) {

				if (opn == 'yes') {
					util.ajax({
						url: '/projectMg/meetingMg/endCouncil.json',
						data: {
							councilId: _$tr.attr('councilid'),
							oldCouncil: _this.attr('oldCouncil'),
							councilVoteMessage: modalwnd.wnd.find('#councilVoteMessage').val()
						},
						success: function(res) {
							$('.util-loading').remove();
							_this.removeClass('ing');
							if (res.success) {
								Y.alert('消息提醒', res.message, function() {
									window.location.reload(true)
								});
							} else {
								Y.alert('消息提醒', res.message);
							}
							modalwnd.wnd.close();
						}
					});
				}

			});

		});

	});


	$('#list').on('click', '.initSummary', function() {
		//------ 初始化会议纪要
		var _this = $(this),
			_$tr = _this.parents('tr');
		util.ajax({
			url: '/projectMg/meetingMg/summary/initSummary.htm',
			data: {
				councilId: _$tr.attr('councilid')
			},
			success: function(res) {
				_this.removeClass('ing');
				if (res.success) {
					window.location.href = "/projectMg/meetingMg/summary/edit.htm?formId=" + res.formId;
				} else {
					Y.alert('消息提醒', res.message);
				}
			}
		});
	});

	//打印
    $('#fnPrint').click(function (event) {
        var $fnPrintBox = $('.m-main.border.ui-bg-fff')
        $fnPrintBox.find('.ui-btn-submit').remove()
        $fnPrintBox.find('.printshow').remove()
        util.print($fnPrintBox.html())
    })


});