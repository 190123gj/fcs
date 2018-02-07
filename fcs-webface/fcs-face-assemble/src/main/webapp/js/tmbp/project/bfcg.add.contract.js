define(function(require, exports, module) {
	require('Y-msg');
	var util = require('util');
    require('validate');
    require('validate.extend');
	//上传图片
	require('Y-htmluploadify');
	//增删行
	require('zyw/opsLine');
	var getList = require('zyw/getList');

	var formId = util.getParam('formId');
	var $addFormDBWD = $('#addFormDBWD');
	var $addFormSS = $('#addFormSS');

	var searchForDetails = require('zyw/searchForDetails');

	//新增才加载
	if (!!!formId) {
		searchForDetails({
			ajaxListUrl: '/baseDataLoad/waitRiskReviewProject.json',
			btn: '#choose',
			codeInput: 'projectNumber',
			tpl: {
				tbody: ['{{each pageList as item i}}',
					'    <tr class="fn-tac m-table-overflow">',
					'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
					'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
					'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
					'        <td>{{item.busiTypeName}}</td>',
					'        <td>{{item.amount}}</td>',
					'        <td><a class="choose" projectNumber="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
					'    </tr>',
					'{{/each}}'
				].join(''),
				form: ['项目名称：',
					'<input class="ui-text fn-w160" type="text">',
					'&nbsp;&nbsp;',
					'客户名称：',
					'<input class="ui-text fn-w160" type="text">',
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
			ajaxDetailsUrl: '/baseDataLoad/loadFProjectData.json',
			callback: function(_data) {
				$addFormDBWD.find('#creditAmount').val(_data.amount.amount);
				$addFormDBWD.find('#timeLimitCode').val(_data.timeLimit);
				$addFormDBWD.find('#timeLimitName').text(_data.timeUnit);
				$addFormDBWD.find('#creditRate').val(0);
				$addFormDBWD.find('#projectName').val(_data.projectName);
				$addFormDBWD.find('#creditType').val(_data.busiTypeName);
			}
		});
		// var _getList = new getList();
		// _getList.init({
		// 	title: '选择',
		// 	ajaxUrl: '/baseDataLoad/waitRiskReviewProject.json',
		// 	btn: '#choose',
		// 	input: ['projectNumber'],
		// 	tpl: {
		// 		tbody: ['{{each pageList as item i}}',
		// 			'    <tr class="fn-tac m-table-overflow">',
		// 			'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
		// 			'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
		// 			'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
		// 			'        <td>{{item.busiTypeName}}</td>',
		// 			'        <td>{{item.amount}}</td>',
		// 			'        <td><a class="choose" projectNumber="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
		// 			'    </tr>',
		// 			'{{/each}}'
		// 		].join(''),
		// 		form: ['项目名称：',
		// 			'<input class="ui-text fn-w160" type="text">',
		// 			'&nbsp;&nbsp;',
		// 			'客户名称：',
		// 			'<input class="ui-text fn-w160" type="text">',
		// 			'&nbsp;&nbsp;',
		// 			'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
		// 		].join(''),
		// 		thead: ['<th width="100">项目编号</th>',
		// 			'<th width="120">客户名称</th>',
		// 			'<th width="120">项目名称</th>',
		// 			'<th width="100">授信类型</th>',
		// 			'<th width="100">授信金额</th>',
		// 			'<th width="50">操作</th>'
		// 		].join(''),
		// 		item: 6
		// 	}
		// });
	}
	//------ 新增 DBWD start
	//点击增加
	$addFormDBWD.on('change', '#projectNumber', function() {

		// var self = this;

		// util.ajax({
		// 	url: '/baseDataLoad/loadFProjectData.json',
		// 	data: {
		// 		projectCode: self.value
		// 	},
		// 	success: function(res) {
		// 		if (res.success) {
		// 			var _data = res.data;
		// 			$addFormDBWD.find('#creditAmount').val(_data.amount.amount);
		// 			$addFormDBWD.find('#timeLimitCode').val(_data.timeLimit);
		// 			$addFormDBWD.find('#timeLimitName').text(_data.timeUnit);
		// 			$addFormDBWD.find('#creditRate').val(0);
		// 			$addFormDBWD.find('#projectName').val(_data.projectName);
		// 			$addFormDBWD.find('#creditType').val(_data.busiTypeName);
		// 		} else {
		// 			Y.alert('提示', res.message);
		// 		}
		// 	}
		// });

	}).on('click', '#add', function() {
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		if (!document.getElementById('projectNumber').value) {
			Y.alert('提示', '请填写项目编号');
			_this.removeClass('ing');
			return;
		}

		if (!document.getElementById('report').value) {
			Y.alert('提示', '请填写风险审查报告');
			_this.removeClass('ing');
			return;
		}

		$.ajax({
			url: '/projectMg/riskreview/addRiskReview.json',
			data: $addForm.serialize(),
			dataType: 'json',
			type: 'POST',
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '提交成功', function() {
						location.href = '/projectMg/riskreview/riskReviewList.htm?size=5&page=1';
					});
				} else {
					_this.removeClass('ing');
					Y.alert('提示', res.message);
				}
			}
		});

	});
	//------ 新增 DBWD end

	//------ 上传 start
	$('.fnUpFile').click(function() {
		var _this = $(this);
		var htmlupload = Y.create('HtmlUploadify', {
			key: 'up1',
			uploader: '/upload.json1',
			multi: false,
			auto: true,
			addAble: false,
			fileTypeExts: '*.jpg; *.jpeg',
			fileObjName: 'UploadFile',
			onQueueComplete: function(a, b) {},
			onUploadSuccess: function($this, data, resfile) {
				_this.attr('src', data.xxx).parent().find('input.fnUpFileInput').val(data.xxx);
			},
			renderTo: 'body'
		});
		htmlupload.show();
	});
	//------ 上传 end

    //------新增字段 start
    $('#addField').validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            element.after(error);
        },
        rules: {
            fieldStage: {
                required: true                
            },
            fieldNametable: {
                required: true
            },
            fieldName: {
                required: true
            },
            fieldN: {
                required: true
            },
            fieldRemarks: {
                required: true
            }
        },
        messages: {
            fieldStage: {
                required: '请选择项目阶段'                
            },
            fieldNametable: {
                required: '请输入表名'
            },
            fieldName: {
                required: '请输入字段名称'
            },
            fieldN: {
                required: '请输入字段简称'
            },
            fieldRemarks: {
                required: '请输入备注'
            }
        },
    });
    //------新增字段 end
});