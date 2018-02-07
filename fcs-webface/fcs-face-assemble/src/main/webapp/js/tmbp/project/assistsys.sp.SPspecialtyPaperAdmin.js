define(function(require, exports, module) {

	//字数提示
	require('zyw/hintFont');
	//必填集合
	require('zyw/requiredRules');
	//弹窗
	require('Y-window');
	//弹窗提示
	var hintPopup = require('zyw/hintPopup');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

	//验证
	var _form = $('#form'),
		requiredRules = _form.requiredRulesSharp('startNo,endNo,deptName,receiveManName', false, {}, function(rules, name, This) {

			rules[name] = {
				required: true,
				maxlength: 20,
				messages: {
					required: '必填',
					maxlength: '超出20字',
				}
			};

		}),
		rulesAllBefore = {
			remark: {
				maxlength: 1000,
				messages: {
					maxlength: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;超出1000字'
				}
			},
			endNo: {
				digits: true,
				comparisonIntersection: true,
				min: function(This) {

					var _start;

					_start = parseInt($(This).parent().attr('start'));

					if (!_start) return false;

					return _start;

				},
				max: function(This) {

					var _end;

					_end = parseInt($(This).parent().attr('end'));

					if (!_end) return false;
					return _end;

				},
				messages: {
					digits: '请输入正整数',
					min: '不能小于起编号',
					max: '不能大于最大止编号',
					comparisonIntersection: '编号相交'
				}
			},
			startNo: {
				digits: true,
				comparisonIntersection: true,
				messages: {
					digits: '请输入正整数',
					comparisonIntersection: '编号相交'
				}
			}
		},
		_rulesAll = $.extend(true, requiredRules, rulesAllBefore),
		SPcommon = require('zyw/project/assistsys.sp.SPcommon');


	SPcommon({

		form: _form,
		rulesAll: _rulesAll

	})

	//选人
	//选择授信用途
	require('zyw/chooseLoanPurpose');

	//现场调查人员弹窗
	var scope = "{type:\"system\",value:\"all\"}";
	var selectUsers = {
		selectUserIds: $('#xxxID').val(),
		selectUserNames: $('#xxx').val()
	}
	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	var singleSelectUserDialog1 = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
		'title': '单选选用户',
		'width': 850,
		'height': 460,
		'scope': scope,
		'selectUsers': selectUsers,
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	$("#xxxID").change(function() {
		var userId = $(this).val();
		if (userId) {
			$.ajax({
					url: '/baseDataLoad/userDetail.json',
					type: 'POST',
					dataType: 'json',
					data: {
						userId: userId
					},
				}).done(function(res) {

					if (res.success) {

						var orgList = res.data.orgList;

						if (orgList && orgList.length > 0) {

							for (var i in orgList) {

								if (orgList[i].primary) {

									$('#fnOrgName').val(orgList[i].name);
									$('#fnOrgId').val(orgList[i].id);

								}

							}

						}

					} else {
						hintPopup(res.message);
					}
				})
				.fail(function() {
					hintPopup('error');
				})
		}
	}).change();

	// window.frameElement.dialog=singleSelectUserDialog;
	$('#xxxBtn').on('click', function() {

		selectUsers.selectUserIds = $('#xxxID').val();
		selectUsers.selectUserNames = $('#xxx').val();

		singleSelectUserDialog1.init(function(relObj) {

			for (var i = 0; i < relObj.userIds.length; i++) {
				$('#xxxID').val(relObj.userIds);
				$('#xxx').val(relObj.fullnames);
			}

			$('input.start').eq(0).focus().blur();
			$('#xxxID').change();

		});

	});

	$('input.start').eq(0).focus().blur();

	//---选择部门 start
	var $fnOrgName = $('#fnOrgName'),
		$fnOrgId = $('#fnOrgId');

	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true', {
		'title': '部门组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$('#fnOrgBtn').on('click', function() {

		//这里也可以更新已选择机构
		var _arr = [],
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

		singleSelectUserDialog.obj.arrys = _arr;

		singleSelectUserDialog.init(function(relObj) {

			$fnOrgId.val(relObj.orgId);
			$fnOrgName.val(relObj.orgName);

		});

	});
	//---选择部门 end

	//起止号
	var popupWindow = require('zyw/popupWindow');

	$('body').on('click', '.start', function(event) {

		var $this, $parents;

		$this = $(this);
		$parents = $this.parent();

		$this.blur();

		fundDitch = new popupWindow({

			YwindowObj: {
				content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
				closeEle: '.close', //find关闭弹窗对象
				dragEle: '.newPopup dl dt' //find拖拽对象
			},

			ajaxObj: {
				url: '/projectMg/specialPaper/chooseNo.htm?type=CHECKIN',
				type: 'post',
				dataType: 'json',
				data: {
					pageSize: 6,
					pageNumber: 1
				}
			},

			pageObj: { //翻页
				clickObj: '.pageBox a.btn', //find翻页对象
				attrObj: 'page', //翻页对象获取值得属性
				jsonName: 'pageNumber', //请求翻页页数的dataName
				callback: function($Y) {



				}
			},

			callback: function($Y) {

				$Y.wnd.on('click', 'a.choose', function(event) {

					var _this, _parents, _startNo, _endNo, _id;

					_this = $(this);
					_parents = _this.parents('tr');
					_id = _parents.attr('idName');
					_startNo = _parents.find('td:eq(1)').text();
					_endNo = _parents.find('td:eq(2)').text();

					$this.val(_startNo);
					$this.siblings('.end').attr('Placeholder', '止编号范围为' + _startNo + '-' + _endNo);
					$this.siblings('.id').val(_id);
					$parents.attr({
						'start': _startNo,
						'end': _endNo
					});
					$('.start,.end').change();
					$Y.close();

				});

			},

			console: false //打印返回数据

		});

	});

})