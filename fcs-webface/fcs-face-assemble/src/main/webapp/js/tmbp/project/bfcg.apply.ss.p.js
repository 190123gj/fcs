define(function(require, exports, module) {
	//表单验证
	require('validate');
	require('validate.extend');
	//选择合作机构
	var getList = require('zyw/getList');
	//Y对话框
	require('Y-msg');
	//模板引擎
	var template = require('arttemplate');
	//所属地区 相关
	require('zyw/chooseRegion');
	//上传图片等公共代码
	var applyFN = require('./bfcg.apply.common');

	//项目管理>授信前管理> 立项申请

	//--------------------企业基本情况 start

	var riskQuery = require('zyw/riskQuery');
	var checkFRSFZ = new riskQuery.initCerNoNameMobile('legalPersionCertNo', 'legalPersion', 'fnMobile', 'legalPersionMCheck', 'legalPersionMMsg');

	//企业性质 

	//----数据验证 基本信息 start
	$('#qyjbqk').validate({
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
			$('#step').find('li').eq(1).trigger('click');
		},
		rules: {
			customerName: {
				required: true
			},
			//			certNo: {
			//				required: true,
			//				checkID: true
			//			},
			contactMan: {
				required: true
			},
			contactNo: {
				required: true,
				isPhoneOrMobile: true
			},
			address: {
				required: true
			},
			country: {
				required: true
			},
			applyScanningUrl: {
				// required: true
			},
			busiLicenseUrl: {
				required: true
			},
			orgCodeUrl: {
				required: true
			}
		},
		messages: {
			customerName: {
				required: '请输入企业名称'
			},
			certNo: {
				required: '请填写身份证号码',
				checkID: '请填写正确的身份证号码'
			},
			contactMan: {
				required: '请填写联系人'
			},
			contactNo: {
				required: '请填写联系电话',
				isPhoneOrMobile: '请输入正确的号码'
			},
			address: {
				required: '请输入企业地址'
			},
			country: {
				required: '请选择地区'
			},
			applyScanningUrl: {
				// required: '请上传客户申请书'
			},
			busiLicenseUrl: {
				required: '请上传证件照片'
			},
			orgCodeUrl: {
				required: '请上传证件照片'
			}
		}
	});
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
				required: true
			},
			timeLimit: {
				required: true
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
			assureObject: {
				required: true
			},
			mountStr: {
				required: true,
				min: 0.01
			}
		},
		messages: {
			projectName: {
				required: '必填'
			},
			amount: {
				required: '请输入金额'
			},
			timeLimit: {
				required: '请输入时间'
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
			assureObject: {
				required: '必填'
			},
			amountStr: {
				required: '必填',
				min: '必须大于0'
			}
		}
	});
	//----数据验证 申请贷款/担保情况 end

	//--------------------申请贷款/担保情况 end

	// var $fnCertType = $('#fnCertType'),
	// 	$fnNotCard = $('.fnNotCard'),
	// 	$fnIsCard = $('.fnIsCard');

	// $fnCertType.on('change', function() {

	// 	setRightImg(this.value);

	// });

	// function setRightImg(type) {

	// 	if (type == 'IDENTITY_CARD') {

	// 		setRightImgInput($fnIsCard);
	// 		removeRightImgInput($fnNotCard);

	// 	} else {

	// 		setRightImgInput($fnNotCard);
	// 		removeRightImgInput($fnIsCard);

	// 	}

	// }

	// function setRightImgInput($div) {
	// 	$div.removeClass('fn-hide').find('input').removeProp('disabled', 'disabled');
	// }

	// function removeRightImgInput($div) {
	// 	$div.addClass('fn-hide').find('input').prop('disabled', 'disabled');
	// }

});