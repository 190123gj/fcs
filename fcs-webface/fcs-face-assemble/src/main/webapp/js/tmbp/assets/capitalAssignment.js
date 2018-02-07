/*
 * @Author: yanyang
 * @Date:   2016-07-06 10:52:24
 * @Last Modified by:   yanyang
 * @Last Modified time: 2016-07-06 14:19:20
 */
define(function(require, exports, module) {

	// ------ 审核 start
	if (document.getElementById('auditForm')) {
		var auditProject = require('zyw/auditProject');
		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
	}
	// ------ 审核 end
	require('validate');
	require('validate.extend');
	require('zyw/upAttachModify');
	require('input.limit');

	var getList = require('zyw/getList'), util = require('util');	

	// 选择项目
	var _getList = new getList();
	_getList.init({
		title : '项目列表',
		ajaxUrl : '/baseDataLoad/loanAssetsTransfer.json',
		btn : '.chooseBtn',
		tpl : {
			tbody : [
				'{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
				'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
				'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
				'        <td>{{item.busiTypeName}}</td>',
				'        <td>{{item.amount}}</td>',
				'        <td><a class="choose" projectCode="{{item.projectCode}}" customerId="{{item.customerId}}" projectName="{{item.projectName}}" compensatoryAmount="{{item.compensatoryAmount}}" href="javascript:void(0);">选择</a></td>', // 跳转地址需要的一些参数
				'    </tr>', '{{/each}}' ].join(''),
			form : [
				'项目名称：',
				'<input class="ui-text fn-w160" type="text" name="projectName">',
				'&nbsp;&nbsp;',
				'客户名称：',
				'<input class="ui-text fn-w160" type="text" name="customerName">',
				'&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ].join(''),
			thead : [ '<th width="100">项目编号</th>',
					'<th width="120">客户名称</th>',
					'<th width="120">项目名称</th>',
					'<th width="100">授信类型</th>',
					'<th width="100">授信金额(元)</th>',
					'<th width="50">操作</th>' ].join(''),
			item : 6
		},
		callback : function($a) {
			// 跳转地址
			document.getElementById('projectCode').value = $a.attr('projectCode');
			document.getElementById('projectName').value = $a.attr('projectName');
			document.getElementById('compensatoryAmount').value = $a.attr('compensatoryAmount');
		}
	});

	// 表单验证

	var $addForm = $('#form'),
		$fnInput = $('.fnInput'),
		$fnradio = $('input.radio'),
		formRules = {
			rules: {},
			messages: {}
		};

		$fnInput.each(function(index, el) {
			//必填规则
			var name = $(this).attr('name');
			if (!name) {
				return true;
			}
			formRules.rules[name] = {
				required: true
			};
			formRules.messages[name] = {
				required: '必填'
			};
		});


	    $fnradio.each(function(index, el) {
	        //必填规则
	        var name = $(this).attr('name');
	        if (!name) {
	            return true;
	        }
	        formRules.rules[name] = {
	            required: true
	        };
	        formRules.messages[name] = {
	            required: '必填'
	        };
	    });

	$addForm.validate($.extend({}, formRules, {
		errorClass: 'error-tip',
		errorElement: 'b',
		ignore: '.ignore',
		onkeyup: false,
		errorPlacement: function(error, element) {

			if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else if(element.hasClass('radio')) {
                element.parent().parent().append(error);
            } else{
            	element.parent().append(error);
            }
		},
		submitHandler: function(form) {

			formSubmit('SUBMIT');

		}
	}));

	function formSubmit(type) {
		document.getElementById('state').value = type || 'SAVE';
		var checkStatus = document.getElementById('checkStatus').value = type ? 1 : 0;
		util.resetName();

		//保存数据
		util.ajax({
			url: '/assetMg/transfer/save.htm',
			data: $addForm.serializeJCK(),
			success: function(res) {
				if (res.success) {
					if (checkStatus == '1') {
						//提交表单
//						util.ajax({
//							url: '/projectMg/form/submit.htm',
//							data: {
//								formId: res.form.formId,
//								_SYSNAME:'AM'
//							},
//							success: function(res2) {
//								if (res2.success) {
//									Y.alert('提醒', res2.message, function() {
//
//										window.location.href = '/assetMg/transfer/list.htm';
//									});
//								} else {
//									Y.alert('提醒', res2.message, function() {
//										window.location.reload(true);
//									});
//								}
//							}
//						});
						util.postAudit({
                            formId: res.form.formId,
                            _SYSNAME: 'AM'
                        }, function () {
                            window.location.href = '/assetMg/transfer/list.htm';
                        })
					}else{
						Y.alert('提醒','已保存~', function() {
							window.location.href = '/assetMg/transfer/list.htm';
						});
					}
				}else{
					Y.alert('提醒', res.message);
				}
			}
		});
	}
	

	$('#submit').click(function() {
		var _isPass = true, _isPassEq;

		$('.fnInput').each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}
		});

		if (!_isPass) {
			$('.fnInput').eq(_isPassEq).focus();
			return;
		}		
	})

	$('.fn-shanghui input').click(function(event) {
		shanghui();
	});

	function shanghui(){
		var self = $('.fn-shanghui'),
			fnShui = self.find('.fnShui'),
			selfChecked = self.find('input[type="radio"]:checked').attr('value');

		if( selfChecked == 'IS' ){

			fnShui.show();
			self.find('select').rules('add', {
				required: true,
				messages: {
					required: '必填'
				}
			});
			return true;

		}else if( selfChecked == 'NO' ){

			fnShui.hide();
			self.find('select').rules("remove");
			return true;

		}else{
			return false;
		}
	}

	// 董事会决议上传
	function chairman(){
		var fnZhuanrang = $('.fnZhuanrang').val(),
			fnDaichang = $('.fnDaichang').val(),
			fnDongshi = $('.fnDongshi'),
			fnShui = $('.fnShui'),
			yesNo = $('#fn-shanghui').find('input').eq(2),
			shanghuiType = $('.fnShui select').val(),
			baifb,baifbB;

		var self = $('.fn-shanghui'),
			selfChecked = self.find('input[type="radio"]:checked').val();

		baifb = parseFloat(fnZhuanrang)/parseFloat(fnDaichang);
		baifbB = parseFloat((baifb * 100).toFixed(2));

		if(fnZhuanrang == '' || fnDaichang == '' ){
			baifbB = parseFloat(100);
		}

		if( baifbB <= 80.00 ){
			if( selfChecked == 'IS' ){

				fnDongshi.removeClass('fnInput');
				$('.dstiip').hide();

				if(shanghuiType == 'MANAGER_MEET'){
					fnDongshi.addClass('fnInput');
					$('.dstiip').show();
				}		
			}else if( selfChecked == 'NO' ){
				fnDongshi.addClass('fnInput');
				$('.dstiip').show();

			}else if( selfChecked != undefined ){
				fnDongshi.addClass('fnInput');
				$('.dstiip').show();
			}
		}else if( baifbB > 80.00 ){
			fnDongshi.removeClass('fnInput');
			$('.dstiip').hide();
		}

	}

	chairman()

	$('body').change(function(event) {
		chairman();		
	});

	$addForm.on('blur', '.fnInputDateS', function() {

		var _thisVal = this.value;
		$('#clearContent').find('.fnInputDateE').attr('onclick', 'laydate({min: "' + _thisVal + '"})');

	}).on('blur', '.fnInputDateE', function() {

		var _thisVal = this.value;
		$('.fnInputDateS').attr('onclick', 'laydate({max: "' + _thisVal + '"})');

	});

	// 是否委托清收
	var isClear = function(obj) {
		var clearContent = $('#clearContent'),
			firstLabel = $('.isClearBox label:first'),
			firstLast = $('.isClearBox label:last'),
			fnInput = clearContent.find('input,textarea');
		if (firstLabel.hasClass('active')) {

			clearContent.show();

			fnInput.each(function(){
				$(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
			})

			return true;

		} else if(firstLast.hasClass('active')) {

			clearContent.hide();
			fnInput.each(function(){
				$(this).rules('remove','required');
			})
			return true;

		} else {
			// Y.alert('提醒', '请把表单填写完整');
			return false;
		}
	};

	$('.isClearBox').on('click','label', function() {
		var self = $(this);
		self.addClass('active').siblings().removeClass('active');
		isClear(self);
	});

	

	if( $('#fnWrite').val() == 'true'){
		isClear();
		if($('#shouR').val() == 'YES'){
			shanghui();
		}    	
	}

	//------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    //------ 侧边栏 end

})