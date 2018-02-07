define(function(require, exports, module) {
	//表单验证
	require('validate');
	require('validate.extend');
	//选择合作机构
	var getList = require('zyw/getList');
	//省市联动
	//require('Y-selectarea');
	//Y对话框
	require('Y-msg');
	//模板引擎
	var template = require('arttemplate');

	//所属行业 相关
	require('zyw/chooseIndustry');
	//所属地区 相关
	require('zyw/chooseRegion');
	//选择授信用途
	var loanPurpose = require('zyw/chooseLoanPurpose');

    loanPurpose.init('LOAN_PURPOSE')
    // 上传图片等公共代码
	var applyFN = require('./bfcg.apply.common');

	// 上传附件
	require('zyw/upAttachModify');

	// 
	// 选择业务品种
	var getTypesOfCredit = require('zyw/getTypesOfCredit');
	var _getTypesOfCredit = new getTypesOfCredit({
		customerType: $("[name=customerType]").eq(0).val(),
		formCode: $("[name=formCode]").eq(0).val()
	});
	_getTypesOfCredit.init({
		btn: '#openChooseBusiness',
		name: '#businessName',
		code: '#businessCode'
	});
	// 选择项目渠道

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

			if (element.hasClass('fnErrorAfter')) {

				element.after(error);

			} else {

				element.parent().append(error);

			}

		},
		submitHandler: function(form) {
			$('#step').find('li').eq(5).trigger('click');
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
			},
			projectChannelName: {
				required: true
			},
			projectSubChannelName: {
				required: true
			}
		},
		messages: {
			projectName: {
				required: '请输入项目名称'
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
			},
			projectChannelName: {
				required: '请选择项目渠道'
			},
			projectSubChannelName: {
				required: '请填写二级项目渠道'
			}
		}
	});
	//----数据验证 申请贷款/担保情况 end

	//是否需要评估公司
	$('#isNeedEvaluate').on('change', function() {
		var _isNeedEvaluates = $('#isNeedEvaluates');
		if (this.value == 'IS') {
			_isNeedEvaluates.removeClass('fn-hide');
		} else {
			_isNeedEvaluates.addClass('fn-hide');
		}
	});

	//--------------------申请贷款/担保情况 end


    //移除validate
    setTimeout(function () {
        var removeObj = 'address'.split(',')
        removeObj.forEach(function (item) {
            //移除规则，顺便移除红色星星
            $('[name="'+item+'"]').rules('remove')
            $('[name="'+item+'"]').closest('.m-item').find('.m-required').remove();
        })
    },500)

});