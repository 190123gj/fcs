define(function(require, exports, module) {
	// 辅助系统 > 文书审核 > 文书审核申请
	var COMMON = require('./bfcg.contract.common');

	var util = require('util');

	require('zyw/upAttachModify');

	//------ 初始化 选择项目 start
	COMMON.initChooseProjectList('/baseDataLoad/queryProjects.json?phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,RE_COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES&statusList=NORMAL,RECOVERY,TRANSFERRED,SELL_FINISH,EXPIRE,OVERDUE', function($a) {
		window.location.href = '/projectMg/contract/addProjectContract.htm?applyType=PROJECT_WRIT&projectCode=' + $a.attr('projectCode');
	});
	//------ 初始化 选择项目 end

	var $writList = $('#writList'),
		$addForm = $('#addForm');


	//------ 添加文书 start
	$('#addWritBtn').on('click', function() {
		$writList.append(document.getElementById('t-writ-null').innerHTML);
	});
	$writList.on('click', '.fnDelTr', function() {
		$(this).parents('tr').remove();
	});
	//------ 添加文书 end

	function doSubmit(isSubmit) {

		document.getElementById('checkStatus').value = isSubmit ? '1' : '0';

		util.resetName();

		util.ajax({
			url: $addForm.attr('action'),
			data: $addForm.serialize(),
			success: function(res) {

				if (res.success) {

					if (!isSubmit) {
						Y.alert('提示', '操作成功', function() {
							window.location.href = '/projectMg/contract/list.htm?applyType=PROJECT_WRIT';
						});
					} else {

//						util.ajax({
//							url: '/projectMg/form/submit.htm',
//							data: {
//								formId: res.formId
//							},
//							success: function(data) {
//
//								Y.alert('提示', (data.success ? '保存并提交成功' : data.message), function() {
//									window.location.href = '/projectMg/contract/list.htm?applyType=PROJECT_WRIT';
//								});
//							}
//						});
						util.postAudit({
							formId: res.formId
                        }, function () {
                        	window.location.href = '/projectMg/contract/list.htm?applyType=PROJECT_WRIT';
                        })

					}

				} else {
					Y.alert('操作失败', res.message);
				}

			}
		});

	}

	$addForm.on('click', '#doSubmit', function() {

		if (!!!document.getElementById('projectCode').value) {
			Y.alert('提示', '请选择项目');
			return;
		}

		var $fileName = $('.fileName'),
			_fileNamePass = true,
			_fileNameEq;

		$fileName.each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_fileNamePass = false;
				_fileNameEq = index;
				return false;
			}
		});

		if (!_fileNamePass) {
			Y.alert('提示', '请填写完文件名称', function() {
				$fileName.eq(_fileNameEq).focus();
			});
			return;
		}

		// 2016.09.20 决策依据
        var _checkBasis = COMMON.checkBasis();
        if (!_checkBasis.success) {
            Y.alert('提示', _checkBasis.message);
            return;
        }

		var $fileValue = $('.fileValue'),
			_fileValuePass = true,
			_fileValueEq;

		$fileValue.each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_fileValuePass = false;
				_fileValueEq = index;
				return false;
			}
		});

		if (!_fileValuePass) {
			Y.alert('提示', '请为每个文件上传一个附件', function() {
				$fileValue.eq(_fileValueEq).focus();
			});
			return;
		}

		doSubmit(true);

	});


	//侧边栏
	var publicOPN = require('zyw/publicOPN'),
		_publicOPN = new publicOPN(),
		projectCode = document.getElementById('projectCode');

	if (projectCode && projectCode.value) {

		_publicOPN.addOPN([{
			name: '查看项目批复',
			alias: 'look',
			event: function() {
				util.openDirect('/projectMg/index.htm', '/projectMg/meetingMg/summary/approval.htm?projectCode=' + document.getElementById('projectCode').value);
			}

		}, {
			name: '暂存',
			alias: 'save',
			event: function() {
				doSubmit(false);
			}
		}])

	}
	_publicOPN.init().doRender();

	if (document.getElementById('fnHasApproval').value != 'IS') {

		_publicOPN.removeOPN('查看项目批复').remove().doRender();

	}

});