define(function(require, exports, module) {
	// 输入限制
	require('input.limit');
	// 增删行
	require('zyw/opsLine');
	// 上传图片
	require('Y-htmluploadify');
	require('Y-msg');
	// md5加密
	var md5 = require('md5');
	// 通用方法
	var util = require('util');

	var $body = $('body');

	// 选择渠道 start

	var getChannel = require('zyw/getChannel');

	var CHANNEL_LIST = new getChannel('#fnChooseChannelBtn');
	CHANNEL_LIST.init({
		callback : function($a) {

			$CHOOSE_CHANNEL_BOX.find('.fnChooseChannelName').val($a.attr('ccname'))
			$CHOOSE_CHANNEL_BOX.find('.fnChooseChannelId').val($a.attr('ccid'))
			$CHOOSE_CHANNEL_BOX.find('.fnChooseChannelCode').val($a.attr('cccode'))
			$CHOOSE_CHANNEL_BOX.find('.fnChooseChannelType').val($a.attr('cctype'))

		}
	});

	var $CHOOSE_CHANNEL_BOX;

	$body.on('click', '.fnChooseChannelBtn', function() {

		$CHOOSE_CHANNEL_BOX = $(this).parent()
		CHANNEL_LIST.query()

	})

	// 选择渠道 end

	// ------ 资金渠道 多选 start

	var $fnCapitalChannelMultiple = $('#fnCapitalChannelMultiple')

	$fnCapitalChannelMultiple.on('click', '.fnDelContact', function() {
		$(this).parents('.m-item').remove()
	}).on('click', '.fnAddContact', function() {
		$fnCapitalChannelMultiple.find('.orderlist').append($fnCapitalChannelMultiple.find('.tpl').html())
	})

	// ------ 资金渠道 多选 end

	// ------ 获取最新客户资料 start 2016.12.27

	$('#fnGetNewCustomerInfo').on('click', function() {

		doSaveForm(function() {
			window.location.href = '/projectMg/setUp/edit.htm?fetchFromCrm=YES&formId=' + $('[name="formId"]').eq(0).val()
		})

	})

	// ------ 获取最新客户资料 end 2016.12.27

	// ------ 选择币种 start
	$('.fnChooseMoneyType').on('change', function() {

		var $this = $(this)

		if ($this.val() === '元') {
			$this.next().val('元').addClass('fn-hide').rules('remove', 'required')
		} else {
			$this.next().removeClass('fn-hide').rules('add', {
				required : true,
				messages : {
					required : '必填'
				}
			})
		}

	});
	// ------ 选择币种 end

	$body.on('click', '.fnGOBack', function() {

		Y.confirm('提示', '确定返回？', function(opn) {
			if (opn == 'yes') {
				window.location.href = '/projectMg/setUp/list.htm';
			}
		});

	}).on('click', '.submitBtn', function() {
		var $form = $(this).parents('form');
		if (!$form.valid()) {
			Y.alert('提示', '请填写完整表单')
			return;
		}
		submitForm();

	}).on('click', '.fnPrePage', function() {
		$step.find('li').eq(-2).trigger('click');
		$(window).scrollTop(0);
	}).on('blur', '[name="projectName"]', function() {
		// 避免后面出现问题，所有项目屏蔽”,“
		this.value = this.value.replace(/(\,|\，)/g, '')
	}).on('blur', '.fnInputGStart', function() {

		var _thisVal = this.value;
		$(this).parent().find('.fnInputGEnd').attr('onclick', 'laydate({min: "' + _thisVal + '"})');

	}).on('blur', '.fnInputGEnd', function() {

		var _thisVal = this.value;
		$(this).parent().find('.fnInputGStart').attr('onclick', 'laydate({max: "' + _thisVal + '"})');

	}).on('blur', '.fnInputSS', function() {

		// var self = this,
		// _max = $(this).parents('tr').find('.fnInputDK').val();

		// setTimeout(function () {
		// if (!!_max.replace(/\s/g, '') && +self.value >= +_max) {
		// self.value = _max;
		// }
		// }, 10);

	}).on('blur', '.fnInputDK', function() {

		// var self = this,
		// $ss = $(this).parents('tr').find('.fnInputSS');

		// setTimeout(function () {
		// if (!!self.value.replace(/\s/g, '') && +self.value <= +$ss.val()) {
		// $ss.val(self.value);
		// }
		// }, 10);

	});

	// ------ 企业基本信息 是否必填、大小限制 地区 start
	// 法人身份证号码 是否必填
	var $legalPersionCertNo = $('#legalPersionCertNo'), // 法人身份证
	$legalPersionAddress = $('#legalPersionAddress'), // 法人地址
	$legalPersionCertType = $('#legalPersionCertType'), // 烦人证件类型
	$netAsset = $('#netAsset'), // 净资产
	$addressResult = $('#addressResult'), // 最后一个地区
	$industryCode = $('#industryCode'), $isGovFP = $('#isGovFP'); // 是否是融资平台

	$body.on('change', '#typeQYXZ', function() {

		// 法人身份证、地址
		notOrgEnterprise(this.value);
		// 是否是国企
		if (this.value === 'STATE_OWNED') {
			$isGovFP.removeClass('fn-hide');
		} else {
			$isGovFP.addClass('fn-hide');
		}

	}).on('blur', '#totalAsset', function() {

		var _val = this.value;

		setTimeout(function() {
			netAssetMax((_val || '').replace(/\,/g, ''));
		}, 0);

	}).on('blur', '#countryCode', function() {

		chinaRegion(this.value);

	}).on('blur', '#addressResult', function() {
		$addressResult.valid();
	}).on('blur', '#industryCode', function() {
		$industryCode.valid();
	});

	// 设置法人身份证、地址是否必填
	function notOrgEnterprise(value) {

		if (document.getElementById('fnApplySSype')) {
			value = 'PRIVATE';
		}

		var _requiredHtml = '<span class="m-required">*</span>';

		// 法人身份证、地址
		if (value != 'STATE_OWNED') {

			$legalPersionCertNo.rules('add', {
				required : true,
				checkID : true,
				messages : {
					required : '必填项',
					checkID : '请输入正确的证件号码'
				}
			});

			$legalPersionAddress.rules('add', {
				required : true,
				messages : {
					required : '必填项'
				}
			});

			$legalPersionCertType.rules('add', {
				required : true,
				messages : {
					required : '必填项'
				}
			})

			if (!$legalPersionCertNo.parents('.m-item').find('.m-required').length) {
				$legalPersionCertNo.parents('.m-item').find('.m-label').prepend(_requiredHtml);
			}
			if (!$legalPersionAddress.parents('.m-item').find('.m-required').length) {
				$legalPersionAddress.parents('.m-item').find('.m-label').prepend(_requiredHtml);
			}
			if (!$legalPersionCertType.parents('.m-item').find('.m-required').length) {
				$legalPersionCertType.parents('.m-item').find('.m-label').prepend(_requiredHtml);
			}

		} else {
			$legalPersionCertNo.rules('remove', 'required');
			$legalPersionAddress.rules('remove', 'required');
			$legalPersionCertType.rules('remove', 'required');
			$legalPersionCertNo.parents('.m-item').find('.m-required').remove();
			$legalPersionAddress.parents('.m-item').find('.m-required').remove();
			$legalPersionCertType.parents('.m-item').find('.m-required').remove();
		}
		$legalPersionCertNo.valid();
		$legalPersionAddress.valid();
		$legalPersionCertType.valid();
	}
	// 证件号码的验证
	/**
	 * 根据条件 给对应的jQuery对象增删验证规则
	 * 
	 * @param {[type]}
	 *            $type [description]
	 * @param {[type]}
	 *            $num [description]
	 */
	function validateCertNo($type, $num) {

		if ($type.val() == 'IDENTITY_CARD') {

			$num.rules('add', {
				checkID : true,
				messages : {
					checkID : '请输入正确的证件号码'
				}
			});

		} else {

			$num.rules('remove', 'checkID');

		}

		if ($num.val()) {
			$num.valid();
		}

	}

	// 法人证件号码
	var riskQuery = require('zyw/riskQuery');
	var checkFRSFZ = new riskQuery.initCerNoNameMobile('legalPersionCertNo', 'legalPersion', false, 'legalPersionCheck', 'legalPersionMsg');

	$legalPersionCertType.on('change', function() {
		validateCertNo($legalPersionCertType, $legalPersionCertNo);

		checkFRSFZ.toggleBtn((this.value == 'IDENTITY_CARD') ? true : false)
	});
	setTimeout(function() {
		$legalPersionCertType.trigger('change');
	}, 500);

	// 设置净资产小于总资产
	function netAssetMax(value) {
		if (!!value) {
			$netAsset.rules('add', {
				max : parseFloat((value || 0), 10),
				messages : {
					max : '不能大于总资产'
				}
			});
		} else {
			$netAsset.rules('remove', 'max');
		}
		$netAsset.valid();
	}

	// 中国地区
	function chinaRegion(value) {

		if (value == 'China') {
			$addressResult.rules('add', {
				required : true,
				messages : {
					required : '请继续选择'
				}
			});
		} else {
			$addressResult.rules('remove', 'required');
		}

		$addressResult.valid();

	}

	setTimeout(function() {

		var _typeQYXZ = document.getElementById('typeQYXZ');
		if (_typeQYXZ) {
			notOrgEnterprise(_typeQYXZ.value);
		}

		var _totalAsset = document.getElementById('totalAsset');
		if (_totalAsset) {
			netAssetMax((_totalAsset.value || '').replace(/\,/g, ''));
		}

		var _countryCode = document.getElementById('countryCode');
		if (_countryCode) {
			chinaRegion(_countryCode.value);
			// $addressResult.valid();
		}

		// 初始化
		if (!!$('input[name="isOneCert"]:checked').val()) {
			$isThree.addClass('fn-hide').find('input.required').each(function(index, el) {
				var _this = $(this);
				_this.attr('disabled', 'disabled').val('').rules('remove', 'required');
				_this.valid();
			});
		}

		validateCertNo($legalPersionCertType, $legalPersionCertNo);

	}, 1000);
	// ------ 企业基本信息 是否必填、大小限制 end

	// ------ 三证合一 start
	var $isThree = $('#isThree');

	$('#isThreeBtn').on('click', function() {

		var isThree = $('input[name="isOneCert"]:checked').val();

		if (isThree) {
			// 诡异的税务登记证 15位，记得清空
			$isThree.find('[name="taxCertificateNo"]').val('');
			$isThree.addClass('fn-hide').find('input.required').each(function(index, el) {
				var _this = $(this);
				_this.attr('disabled', 'disabled').val('').rules('remove', 'required');
				_this.valid();
			});
		} else {
			$isThree.removeClass('fn-hide').find('input.required').each(function(index, el) {
				var _this = $(this);
				_this.removeAttr('disabled').rules('add', 'required');
				_this.valid();
			}).end().find('img').each(function(index, el) {
				this.src = '/styles/tmbp/img/project/apply_upfile.jpg';
			});
		}
	});
	// ------ 三证合一 end

	// ------ 上传证件照片 start
	$('.fnUpFile').click(function() {

		var _this = $(this), _thisP = _this.parent();

		var _load = new util.loading();

		var htmlupload = Y.create('HtmlUploadify', {
			key : 'up1',
			uploader : '/upload/imagesUpload.htm',
			multi : false,
			auto : true,
			addAble : false,
			fileTypeExts : util.fileType.img + '; *.pdf',
			fileObjName : 'UploadFile',
			onUploadStart : function($this) {
				_load.open();
			},
			onQueueComplete : function(a, b) {
				_load.close();
			},
			onUploadSuccess : function($this, data, resfile) {

				var res = $.parseJSON(data);
				if (res.success) {
					if (/(.pdf|.PDF|.tiff|.TIFF|.tif|.TIF)$/g.test(res.data.url)) {
						_this.attr('src', '/styles/tmbp/img/not_img.jpg')
						_thisP.find('.fnUpFilePDF').html('<a class="fn-green" href="' + res.data.url + '" download>下载文件</a>')
					} else {
						_this.attr('src', res.data.url)
						_thisP.find('.fnUpFilePDF').html('')
					}
					_thisP.find('input.fnUpFileInput').val(res.data.url).trigger('blur');
				} else {
					Y.alert('提示', '上传失败');
				}

			},
			renderTo : 'body'
		});

	});


	$('body').on('click','.fnUpFileDel', function() {
        var _thisP = $(this).parent();
        _thisP.find('.fnUpFile').attr('src', '/styles/tmbp/img/project/apply_upfile.jpg');
        _thisP.find('.fnUpFileInput').val('');
        _thisP.find('.fnUpFilePDF').html('');
    })
	// ------ 上传证件照片 end

	// ------ 股权不能超过100 starticon_pdf.png
	var $fnEquityNew = $('#fnEquityNew'), ISEQUITYWARNED = false;
	$body.on('blur', '.fnInputEquity', function() {

		var _thisVal = parseFloat(this.value || 0), isAllEquity = 0;

		$('.fnInputEquity').each(function(index, el) {
			isAllEquity += parseFloat(this.value || 0);
		});

		isAllEquity -= _thisVal;

		if (_thisVal < (100 - isAllEquity)) {
			_thisVal = _thisVal;
			$fnEquityNew.removeClass('fn-hide');
		} else {
			_thisVal = 100 - isAllEquity;
			$fnEquityNew.addClass('fn-hide');
			if (!ISEQUITYWARNED) {
				ISEQUITYWARNED = true;
				Y.alert('提醒', '股权已经达到100%', function() {
					ISEQUITYWARNED = false;
				});
			}
		}

		// _thisVal = (_thisVal < (100 - isAllEquity)) ? _thisVal : (100 -
		// isAllEquity);

		this.value = _thisVal.toFixed(2);

	});
	// ------ 股权不能超过100 end
	//
	// ------ 合作机构服务费 切换 start
	$('#deposit, #coInstitutionCharge').attr('maxlength', '10');

	function PercentToAmount(type, $input) {
		if (type == 'PERCENT') {
			$input.removeClass('fnMakeMoney').addClass('fnMakePercent100').trigger('blur');
		} else {
			$input.removeClass('fnMakePercent100').addClass('fnMakeMoney').trigger('blur');
		}
	}

	$('#coInstitutionChargeType').on('change', function() {

		PercentToAmount(this.value, $('#coInstitutionCharge'));

	});

	$('#depositType').on('change', function() {

		PercentToAmount(this.value, $('#deposit'));

	}).trigger('change');
	//
	// ------ 合作机构服务费 切换 end
	//
	//
	//
	// ------ 费率、费用？ 单位转换 start
	$('.fnPercent2money').on('change', 'select', function() {

		var _$this = $(this), _$input = _$this.parents('.fnPercent2money').find('input');

		if (_$this.val() == 'PERCENT') {

			_$input.removeClass('fnMakeMoney').addClass('fnMakePercent100');

		} else {

			_$input.removeClass('fnMakePercent100').addClass('fnMakeMoney');

		}

		_$input.trigger('keyup').trigger('blur');

	}).find('select').each(function(index, el) {

		$(el).trigger('change');

	}).trigger('change').end().find('input').attr('maxlength', '10');
	// ------ 费率、费用？ 单位转换 end
	//
	//
	// ------ 计算企业规模 start
	var ajaxEnterpriseScale = {
		industryCode : $body.find('#industryCode'), // 行业编码
		inCome : $body.find('#inCome'), // 营业收入
		employeeNum : $body.find('#employeeNum'), // 从业人数
		totalAsset : $body.find('#totalAsset')
	// 资产总额
	};

	var ajaxEnterpriseScaleing = false; // 是否正在ajax请求计算结果

	$body.on('blur', '#industryCode,#inCome,#employeeNum,#totalAsset', function() {

		if (ajaxEnterpriseScaleing) {
			return;
		}

		var _isPass = true, _scale = {};

		// 行业编码必要
		if (!!!ajaxEnterpriseScale.industryCode.val()) {
			_isPass = false;
			return;
		}

		for ( var k in ajaxEnterpriseScale) {

			_scale[k] = (ajaxEnterpriseScale[k].val() || '').replace(/\,/g, '');

		}

		if (_isPass) {

			ajaxEnterpriseScaleing = true;

			$.ajax({
				url : '/projectMg/common/calculateEnterpriseScale.htm',
				type : 'POST',
				dataType : 'json',
				data : _scale,
				success : function(res) {
					if (res.success) {
						// $('#enterpriseScale').find('option').removeProp('selected').removeAttr('selected').prop('disabled',
						// 'disabled').attr('disabled',
						// 'disabled').addClass('fn-hide')
						// .end().find('option[value="' + res.scale +
						// '"]').prop('selected', 'selected').attr('selected',
						// 'selected').removeProp('disabled').removeAttr('disabled').removeClass('fn-hide');
						document.getElementById('enterpriseScaleText').innerHTML = $('#enterpriseScale').find('option[value="' + res.scale + '"]').html().replace(/\s/g, '')
						document.getElementById('enterpriseScaleHidden').value = res.scale
					} else {
						Y.alert('提示', res.message);
					}
				},
				complete : function() {
					ajaxEnterpriseScaleing = false;
				}
			});

		}
	}).find('#totalAsset').trigger('blur');
	// ------ 计算企业规模 end
	//
	//
	// ------ 数组Name命名 start
	function resetName(orderName) {

		var _tr;

		if (orderName) {
			_tr = $('[orderName=' + orderName + ']');
		} else {
			_tr = $('[orderName]');
		}

		_tr.each(function() {

			var tr = $(this), _orderName = tr.attr('orderName'), index = tr.index();

			tr.find('[name]').each(function() {

				var _this = $(this), name = _this.attr('name');

				if (name.indexOf('.') > 0) {
					name = name.substring(name.indexOf('.') + 1);
				}

				name = _orderName + '[' + index + '].' + name;

				_this.attr('name', name);

			});

		});
	}
	// ------ 数组Name命名 end
	//
	//
	// ------ 触发提交表单 start
	function submitForm() {

		saveForm($step.find('li.active').attr('ftf'), function() {

			resetName();

			Y.confirm('提示', '确认提交当前表单？', function(opn) {

				if (opn == 'yes') {

					var formId = $('[name=formId]').eq(0).val();

					util.postAudit({
						formId : formId
					}, function() {
						window.location.href = '/projectMg/setUp/list.htm';
					}, function(res) {
						// 未通过
						var _sArr = res.form.checkStatus.split(''), _iArr = [];
						// 下标
						$.each(_sArr, function(i, s) {
							if (s == '0') {
								_iArr.push(i);
							}
						});

						_sArr = [];
						$step.find('li').each(function(i, e) {
							if ($.inArray(i, _iArr) > -1) {
								_sArr.push(e.innerHTML.replace(/<.+?>/gim, '').replace(/\s/g, ''));
							}
						});

						// Y.alert('操作失败', _sArr.join('、') + ' 信息填写有误', function
						// () {

						// $step.find('li').eq(_iArr[0]).trigger('click');

						// });
						$step.find('li').eq(_iArr[0]).trigger('click');
					})

				}

			});

		});

	}
	// ------ 触发提交表单 end
	//
	//
	// ------ 触发暂存数据 start

	function doSaveForm(cb) {
		var _loading = new util.loading();

		_loading.open();

		saveForm($step.find('li.active').attr('ftf'), function() {

			_loading.close();

			if (cb) {
				cb()
			}

		});
	}

	// ------ 触发暂存数据 end
	//
	//
	// ------ 关于表单储存 start
	//
	// --- 缓存表单md5
	var formMd5Obj = {};

	var $step = $('#step'), // 顶部导航
	$fnStep = $('.fnStep'), // 表单集合
	nextIndex = util.getParam('nextIndex'), // 第一次保存的锚点
	lastFTFID = $step.find('li.active').attr('ftf'); // 上一次的标记

	// --- 绑定时间
	$step.on('click', 'li:not(.active)', function() {
		// 点击保存切换或切换
		var _this = $(this);

		// 记录锚点
		nextIndex = _this.index();

		saveForm(lastFTFID, function() {
			lastFTFID = _this.attr('ftf');
			_this.addClass('active').siblings().removeClass('active');
			$fnStep.addClass('fn-hide');
			$('#' + lastFTFID).removeClass('fn-hide');
		});

	});
	// --- 初始化操作
	if (nextIndex) {
		setTimeout(function() {
			// $step.find('li').eq(nextIndex).trigger('click');
			$step.find('li').removeClass('active').eq(nextIndex).addClass('active');
			$fnStep.addClass('fn-hide').eq(nextIndex).removeClass('fn-hide');
			lastFTFID = $step.find('li.active').attr('ftf');
		}, 0);
	}

	setTimeout(function() {
		setFormMd5Obj();
	}, 500);

	// --- 首先缓存md5
	function setFormMd5Obj() {

		// 重命名
		resetName();

		$fnStep.each(function(index, el) {
			var _this = $(this);
			formMd5Obj[_this.attr('id')] = md5(_this.serialize())
		});

	}

	// --- MD5是否已经变化
	/**
	 * [isFormMd5Changed 对比缓存表单md5值是否变化了]
	 * 
	 * @param {string}
	 *            key [表单标记，一般情况使用id]
	 * @param {string}
	 *            param [序列化后的字符串]
	 * @return {Boolean} [true:改变了，false:未改变]
	 */
	function isFormMd5Changed(key, param) {

		if (key === 'qyjbqk') {
			return true
		}

		return (md5(param) != formMd5Obj[key]) ? true : false;

	}

	// --- 保存某个表单
	/**
	 * [saveForm 保存表单，同时验证表单是否合法，如果有锚点，就不保存，避免数据还原的时候，验证出错]
	 * 
	 * @param {[type]}
	 *            key [操作表单的标记]
	 * @param {Function}
	 *            callback [保存表单后的回调]
	 * @return {[type]} [description]
	 */
	function saveForm(key, callback) {

		var _$form = $('#' + key); // 需要保存的表单

		// 重命名
		resetName();

		if (_$form.valid()) {
			_$form.find('input[name="checkStatus"]').val('1');
		} else {
			_$form.find('input[name="checkStatus"]').val('0');
		}

		if (isFormMd5Changed(key, _$form.serialize())) {

			// --变化，保存
			saveFormAjax(key, _$form, callback);

		} else {

			// --没有变化，直接回调
			if (callback) {
				callback();
			}

		}
	}

	// 保存表单时的ajax请求
	/**
	 * [saveFormAjax 保存表单时的ajax请求，并且通过formid判断是否是初次保存]
	 * 
	 * @param {[type]}
	 *            key [操作表单的标记]
	 * @param {[type]}
	 *            _$form [操作表单的jQuery对象]
	 * @param {Function}
	 *            callback [保存表单后的回调]
	 * @return {[type]} [description]
	 */
	function saveFormAjax(key, _$form, callback) {

		var _isFirstsave = !!!_$form.find('[name="formId"]').val() ? true : false;

		$.ajax({
			url : _$form.attr('action'),
			data : _$form.serializeJCK(),
			type : 'POST',
			success : function(res) {
				if (res.success) {
					// 如果是初次储存
					if (_isFirstsave) {
						window.location = '/projectMg/setUp/edit.htm?formId=' + res.form.formId + '&nextIndex=' + nextIndex;
					}
					// 更新 缓存md5
					formMd5Obj[key] = md5(_$form.serialize());

					if (callback) {
						callback();
					}
				} else {
					Y.alert('提示', res.message);
				}
			}
		});

	}

	// ------ 关于表单储存 end
	//
	// ------ 侧边工具栏 start
	(new (require('zyw/publicOPN'))()).addOPN([ {
		name : '暂存',
		alias : 'save',
		event : function() {

			doSaveForm(function() {
				Y.alert('提示', '已保存');
			})

		}
	}, {
		name : '提交',
		alias : 'submit',
		event : function() {
			submitForm();
		}
	} ]).init().doRender();
	// ------ 侧边工具栏 end
	//
	// ------ 下一步 start
	$('.fnNext').on('click', function() {
		$step.find('li').eq($(this).siblings('.step').val()).trigger('click');
	});
	// ------ 下一步 end
	//
	//
	//
	// ------ 公共 验证规则 start
	var validateRules = {
		errorClass : 'error-tip',
		errorElement : 'b',
		ignore : '.ignore',
		onkeyup : false,
		errorPlacement : function(error, element) {

			if (element.hasClass('fnErrorAfter')) {

				element.after(error);

			} else {

				element.parent().append(error);

			}

		},
		submitHandler : function(form) {
			$('#step').find('li').eq(1).trigger('click');
		},
		rules : {
			customerName : {
				required : true
			},
			enterpriseType : {
				required : true
			},
			industryCode : {
				required : true
			},
			registerCapital : {
				required : true
			},
			establishedTime : {
				required : true
			},
			address : {
				required : true
			},
			busiScope : {
				required : true
			},
			legalPersion : {
				required : true
			},
			legalPersionCertNo : {
				required : true
			// checkID: true
			},
			busiLicenseNo : {
				required : true
			},
			busiLicenseUrl : {
				required : true
			},
			orgCode : {
				required : true
			// isOrgCode: true
			},
			orgCodeUrl : {
				required : true
			},
			countryCode : {
				required : true
			},
			totalAsset : {
				required : true
			},
			salesProceedsLastYear : {
				required : true
			},
			contactNo : {
				isPhoneOrMobile : true
			},
			// taxCertificateNo: {
			// maxlength: 15,
			// minlength: 15
			// },
			staffNum : {
				required : true,
				min : 1
			},
			addressResult : {
				required : true
			},
			applyScanningUrl : {
			// required: true
			}
		},
		messages : {
			customerName : {
				required : '请输入企业名称'
			},
			enterpriseType : {
				required : '请选择企业性质'
			},
			industryCode : {
				required : '请选择所属行业'
			},
			registerCapital : {
				required : '请输入注册资本'
			},
			establishedTime : {
				required : '请选择成立时间'
			},
			address : {
				required : '请输入企业地址'
			},
			busiScope : {
				required : '请输入经营范围'
			},
			legalPersion : {
				required : '请输入法定代表人'
			},
			legalPersionCertNo : {
				required : '请输入证件号'
			// checkID: '请输入正确的证件号'
			},
			busiLicenseNo : {
				required : '必填项'
			},
			busiLicenseUrl : {
				required : '请上传图片'
			},
			orgCode : {
				required : '必填项'
			// isOrgCode: '请输入正确的代码'
			},
			orgCodeUrl : {
				required : '请上传图片'
			},
			countryCode : {
				required : '请选择地区'
			},
			totalAsset : {
				required : '必填项'
			},
			salesProceedsLastYear : {
				required : '必填项'
			},
			contactNo : {
				isPhoneOrMobile : '请输入正确的电话号码'
			},
			// taxCertificateNo: {
			// maxlength: '请输入正确的证件号',
			// minlength: '请输入正确的证件号'
			// },
			staffNum : {
				required : '必填项',
				min : '必须大于0'
			},
			addressResult : {
				required : '请选择'
			},
			applyScanningUrl : {
			// required: '请选择'
			}
		}
	};
	// ------ 公告 验证规则 end
	//

	// -------选择授信金额币种----start

	$('.fnForeignCurrencyCode').on('change', function() {
		var _this = $(this), fnForeignAmount = _this.siblings('.fnForeignAmount'), fnCnyAmount = _this.siblings('.fnCnyAmount'), fnExchangeInfo = _this.siblings(".fnExchangeInfo");
		if (_this.val() == "CNY") {
			fnForeignAmount.hide();
			fnExchangeInfo.hide();
			if (fnForeignAmount.val())
				fnCnyAmount.val(fnForeignAmount.val());
			fnCnyAmount.show();
		} else {
			fnCnyAmount.hide();
			fnForeignAmount.show();
			if (fnCnyAmount.val() && !fnForeignAmount.val())
				fnForeignAmount.val(fnCnyAmount.val());
			toCny(fnForeignAmount, fnCnyAmount, _this, fnExchangeInfo);
		}
	});

	$('.fnForeignAmount').on('change', function() {
		var _this = $(this), fnForeignCurrencyCode = _this.siblings('.fnForeignCurrencyCode'), fnCnyAmount = _this.siblings('.fnCnyAmount'), fnExchangeInfo = _this.siblings(".fnExchangeInfo");
		console.log(_this.val());
		if (fnForeignCurrencyCode.val() == "CNY") {
			_this.hide();
			fnExchangeInfo.hide();
			if (_this.val())
				fnCnyAmount.val(_this.val());
			fnCnyAmount.show();
		} else {
			fnCnyAmount.hide();
			_this.show();
			toCny(_this, fnCnyAmount, fnForeignCurrencyCode, fnExchangeInfo);
		}
	});

	// 请求汇率接口
	function toCny(fnForeignAmount, fnCnyAmount, currency, fnExchangeInfo) {
		var fnAmount = fnForeignAmount.val(), currencyCode = currency.val();
		if (currencyCode && currencyCode != "CNY" && fnAmount) {
			util.ajax({
				url : '/baseDataLoad/toCny.json',
				data : {
					'currencyCode' : currencyCode,
					'currencyAmount' : fnAmount
				},
				success : function(res) {
					if (res.success) {
						var data = res.data;
						if (data) {
							var currencyName = currency.find("option:selected").text();
							fnCnyAmount.val(data.amount);
							fnExchangeInfo.find('.fnCnyAmountShow').text(data.amount);

							fnForeignAmount.siblings(".fnEchangeUpdateTime").val(data.exchangeTime + ":00");
							fnExchangeInfo.find('.fnExchangeUpdateTime').text(data.exchangeTime);

							fnForeignAmount.siblings(".fnForeignCurrencyName").val(currencyName);
							fnExchangeInfo.find('.fnForeignCurrencyName').text(currencyName);

							fnForeignAmount.siblings(".fnExchangeRate").val(data.exchangeRate);
							fnExchangeInfo.find('.fnExchangeRate').text(data.exchangeRate);
							fnExchangeInfo.show();
						}
					} else {
						Y.alert('提醒', res.message, function() {
						});
					}
				}
			});
		}
	}

	// -------选择授信金额币种----end

	// ------ 向外暴露方法
	module.exports = {
		submitForm : submitForm,
		resetName : resetName,
		validateRules : validateRules
	}
});