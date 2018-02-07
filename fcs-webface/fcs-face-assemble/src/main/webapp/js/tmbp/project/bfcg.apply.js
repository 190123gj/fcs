define(function(require, exports, module) {
	// 项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('input.limit');
	var util = require('util');
	var getList = require('zyw/getList');

	// 风险查询 信息、相似、失信
	var riskQuery = require('zyw/riskQuery');
	var initRiskQuery = new riskQuery.initRiskQuery(false, 'customerName', 'license', 'userType', 'fnCertNo', 'isThreeBtn', 'license');

	// 授信类型

	var getTypesOfCredit = require('zyw/getTypesOfCredit');
	var _getTypesOfCredit = new getTypesOfCredit();
	var $businessTypeName = $('#businessTypeName');

	var ISCHOOSE = false;

	_getTypesOfCredit.init({
		btn : '#openChooseTypes',
		name : '#businessTypeName',
		code : '#businessTypeCode',
		beforeQuery : function() {

			var _license = document.getElementById('license'), _isTitle = false, _isPass = true;

			if (customerType == 'PERSIONAL') {

				// _isPass = (util.checkIdcard(_license.value) == '验证通过!') ?
				// true : false;
				// _isTitle = false;

			} else if (customerType == 'ENTERPRISE') {

				// _isPass = (_license.value.length == 15 ||
				// _license.value.length == 18) ? true : false;
				// _isTitle = false;

			} else {

				_isPass = false;
				_isTitle = true;

			}

			if (!_isPass) {

				Y.alert('提示', (_isTitle ? '请先选择 客户类型' : '请填写正确的 证照号码'), function() {
					if (!_isTitle) {
						document.getElementById('license').focus();
					}
				});

			}

			return _isPass;
		}
	});

	_getTypesOfCredit.$box.on('click', '.fnCanChoose', function() {
		var _this = $(this);
		if (_this.find('.name').html() == '其他') {
			$businessTypeName.removeAttr('readonly').focus();
		} else {
			$businessTypeName.attr('readonly', 'readonly');
		}
	});

	// ------- 企业
	(new getList()).init({
		title : '选择客户',
		ajaxUrl : '/baseDataLoad/customer.json?customerType=ENTERPRISE&includePublic=IS',
		btn : '#fnCBtn',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>', '        <td title="{{item.busiLicenseNo}}">{{(item.busiLicenseNo && item.busiLicenseNo.length > 18)?item.busiLicenseNo.substr(0,18)+\'\.\.\':item.busiLicenseNo}}</td>', '        <td title="{{item.enterpriseType}}">{{(item.enterpriseType && item.enterpriseType.length > 10)?item.enterpriseType.substr(0,10)+\'\.\.\':item.enterpriseType}}</td>', '        <td title="{{item.industry}}">{{(item.industry && item.industry.length > 10)?item.industry.substr(0,10)+\'\.\.\':item.industry}}</td>',
					'        <td><a class="choose" trueCustomerId="{{item.trueCustomerId}}" customerid="{{item.customerId}}" customername="{{item.customerName}}" num="{{item.busiLicenseNo}}" certno="{{item.certNo}}" href="javascript:void(0);">选择</a></td>', '    </tr>', '{{/each}}' ].join(''),
			form : [ '客户名称：', '<input class="ui-text fn-w100" type="text" name="likeCustomerName">', '&nbsp;&nbsp;', '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ].join(''),
			thead : [ '<th>客户名称</th>', '<th>营业执照号码</th>', '<th>企业性质</th>', '<th>所属行业</th>', '<th width="50">操作</th>' ].join(''),
			item : 5
		},
		callback : function($a) {
			if (ChooseTrueCustomer) {
                setDOMValue('trueCustomerId', $a.attr('customerid'))
                setDOMValue('trueCustomerName', $a.attr('customername'))
				//显示的
                setDOMValue('add', $a.attr('customername'))
                setDOMValue('add1', $a.attr('num'))
                checkCustomerOccupy($a.attr('customerid'), $a.attr('customername'),'',true)
            } else {
                setUserInfo($a.attr('customerid'), $a.attr('customername'), $a.attr('num'), '', $a.attr('certno'), ($a.attr('certno') == $a.attr('num')));
                ForEnterprise.removeClass('fn-hide')
            }
            ChooseTrueCustomer = false
		}
	});

	
	function checkCustomerOccupy(a,b,c,bool) {
        $.ajax({
            url : '/projectMg/setUp/checkCustomerOccupy.json',
            data : {
                'customerId' : a,
                'customerName' : b,
            },
            success : function(res) {
                if (res.success) {
                	$('#toCopy').show()
					c && c();
                } else {
                    Y.alert('操作失败', res.message+'，请重新选择', function () {
                    	if (bool) {
                            $('#ChooseTrueCustomer').trigger('click')
						} else {
                            $('#fnToChoose').trigger('click')
						}
                    });

                }
            }
		})
    }
	
	// ------- 个人
	(new getList()).init({
		title : '选择客户',
		ajaxUrl : '/baseDataLoad/customer.json?customerType=PERSIONAL',
		btn : '#fnPBtn',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>', '        <td title="{{item.certTypeName}}">{{(item.certTypeName && item.certTypeName.length > 18)?item.certTypeName.substr(0,18)+\'\.\.\':item.certTypeName}}</td>', '        <td title="{{item.certNo}}">{{(item.certNo && item.certNo.length > 18)?item.certNo.substr(0,18)+\'\.\.\':item.certNo}}</td>', '        <td title="{{item.mobile}}">{{(item.mobile && item.mobile.length > 11)?item.mobile.substr(0,11)+\'\.\.\':item.mobile}}</td>',
					'        <td><a class="choose" customerid="{{item.customerId}}" customername="{{item.customerName}}" num="{{item.certNo}}" certno="{{item.certNo}}" certtype="{{item.certType}}" href="javascript:void(0);">选择</a></td>', '    </tr>', '{{/each}}' ].join(''),
			form : [ '客户名称：', '<input class="ui-text fn-w100" type="text" name="likeCustomerName">', '&nbsp;&nbsp;', '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ].join(''),
			thead : [ '<th>客户名称</th>', '<th>证件类型</th>', '<th>证件号</th>', '<th>联系方式</th>', '<th width="50">操作</th>' ].join(''),
			item : 5
		},
		callback : function($a) {
			setUserInfo($a.attr('customerid'), $a.attr('customername'), $a.attr('num'), ($a.attr('certtype') || 'noType'), $a.attr('certno'), false);
		}
	});

	function setUserInfo(id, name, num, certType, certNo, isOne) {

		document.getElementById('allInfo').innerHTML = ''

		setDOMValue('customerId', id);
		setDOMValue('customerName', name);
		setDOMValue('license', num);
		setDOMValue('fnCertNo', certNo);
		setDOMValue('isThreeBtn', isOne ? 'IS' : '');

		ISCHOOSE = true;

		// 设置证件类型
		if (certType) {

			$fnCertType.find('option').removeProp('selected');
			$fnCertType.find('[value="' + certType + '"]').prop('selected', 'selected');
			$fnCertType.prop('disabled', 'disabled');
			$fnCertTypeHide.attr('name', $fnCertType.attr('name')).val(certType);
			$fnCertType.removeAttr('name');

			if (certType === 'IDENTITY_CARD') {
				$fnCheckRisk.removeClass('fn-hide')
			} else {
				$fnCheckRisk.addClass('fn-hide')
			}

			// initRiskQuery.toggleSX((certType == 'IDENTITY_CARD') ? true :
			// false)

		} else {
			// initRiskQuery.toggleSX(true)
			$fnCheckRisk.removeClass('fn-hide')
		}

		$toCopy.removeClass('fn-hide');

		// 查询风险信息
		var _isP = (customerType === 'ENTERPRISE') ? false : true, _getXX = _isP ? false : true, // 个人无相似、信息
		_getSX = _isP ? ((document.getElementById('fnCertType').value === 'IDENTITY_CARD') ? true : false) : true; // 个人
		// 身份证
		// 失信

		initRiskQuery.getAllInfo(_getXX, _getSX, _getXX, function(html) {
			document.getElementById('allInfo').innerHTML = html
		})

	}

	function setDOMValue(id, value) {
		var x = document.getElementById(id);
		if (value) {
			x.value = value;
			// x.setAttribute('readonly', 'readonly');
		} else {
			x.value = '';
			// var _readonly = x.getAttributeNode('readonly');
			// if (_readonly) {
			// x.removeAttributeNode(_readonly);
			// }
		}

	}

	// ------- 复制表单
	var _copyForm = new getList();
	_copyForm.init({
		title : '存量立项申请单',
		btn : '#fnCopyForm',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 10)?item.projectName.substr(0,10)+\'\.\.\':item.projectName}}</td>', '        <td title="{{item.projectCode}}">{{(item.projectCode && item.projectCode.length > 18)?item.projectCode.substr(0,18)+\'\.\.\':item.projectCode}}</td>', '        <td title="{{item.amount}}">{{(item.amount && item.amount.length > 18)?item.amount.substr(0,18)+\'\.\.\':item.amount}}</td>', '        <td>{{item.setupTime}}</td>',
					'        <td><a class="choose" formid="{{item.formId}}" href="javascript:void(0);">选择</a>&emsp;<a target="_blank" href="/projectMg/index.htm?systemNameDefautUrl=/projectMg/setUp/view.htm&formId={{item.formId}}">查看</a></td>', '    </tr>', '{{/each}}' ].join(''),
			form : '',
			thead : [ '<th>项目名称</th>', '<th>项目编号</th>', '<th>金额(元)</th>', '<th>申请时间</th>', '<th width="100">操作</th>' ].join(''),
			item : 5
		},
		callback : function($a) {

			util.ajax({
				url : '/projectMg/setUp/copyHistory.htm',
				data : {
					'formId' : $a.attr('formid'),
					'busiType' : $('#businessTypeCode').val(),
					'busiTypeName' : $('#businessTypeName').val(),
					'trueCustomerId' : $('#trueCustomerId').val(),
					'trueCustomerName' : $('#trueCustomerName').val()
				},
				success : function(res) {
					if (res.success) {
						window.location.href = '/projectMg/setUp/edit.htm?formId=' + res.form.formId;
					} else {
						Y.alert('操作失败', res.message);
					}
				}
			});

		}
	});

	// 风险检索

	var $fnCheckRisk = $('#fnCheckRisk').on('click', function() {
		// 查询风险信息
		var _isP = (customerType === 'ENTERPRISE') ? false : true, _getXX = _isP ? false : true, // 个人无相似、信息
		_getSX = _isP ? ((document.getElementById('fnCertType').value === 'IDENTITY_CARD') ? true : false) : true; // 个人
		// 身份证
		// 失信

		initRiskQuery.getAllInfo(_getXX, _getSX, _getXX, function(html) {
			document.getElementById('allInfo').innerHTML = html
		})
	});

	var $apply = $('#apply'),
		customerType, // 证件类型
		$fnCertType = $('#fnCertType'),
		$fnCertTypeHide = $('#fnCertTypeHide'),
		$toCopy = $('#toCopy').after('<span id="fnCopyForm"></span>');

    // 2017-9-22 新增需求
	var ForEnterprise = $('.ForEnterprise'),
		// 判断是否是第二个按钮触发的弹出列表
        ChooseTrueCustomer = false



    $apply.on('click', '#ChooseTrueCustomer', function () {
    	$('#fnCBtn').trigger('click');
        ChooseTrueCustomer = true
    })


	$fnCertType.on('change', function() {
		if (this.value === 'IDENTITY_CARD') {
			$fnCheckRisk.removeClass('fn-hide')
		} else {
			$fnCheckRisk.addClass('fn-hide')
		}
	})

	$apply.on('blur', '#license', function() {

		// 过滤输入值
		this.value = this.value.replace(/[^\w]/g, '').toUpperCase();

	}).on('click', '#fnToChoose', function() {

		if (!!!customerType) {
			Y.alert('提示', '请先选择客户类型');
			return;
		}

		$('#' + ((customerType == 'PERSIONAL') ? 'fnPBtn' : 'fnCBtn')).trigger('click');

	}).on('click', '#fnToClear', function() {

		setDOMValue('customerId');
		setDOMValue('customerName');
		setDOMValue('license');
		document.getElementById('businessTypeName').value = '';

		ISCHOOSE = false;

		$fnCertType.find('option').removeProp('selected').end().attr('name', $fnCertTypeHide.attr('name')).removeProp('disabled');
		$fnCertTypeHide.removeAttr('name');

		$toCopy.addClass('fn-hide');

	}).on('click', '.customerType', function() {

		customerType = this.value;

		document.getElementById('allInfo').innerHTML = ''

		_getTypesOfCredit.setQueryData({
			customerType : customerType
		});

		if (customerType === 'PERSIONAL') {
			$fnCertType.find('option').removeProp('selected').end().attr('name', $fnCertTypeHide.attr('name')).removeProp('disabled');
			$fnCertTypeHide.removeAttr('name');
            ForEnterprise.addClass('fn-hide')
		} else {
			$fnCheckRisk.removeClass('fn-hide')
		}

		// 改变文字
		document.getElementById('fnText').innerHTML = (customerType == 'PERSIONAL') ? '个人证件号码' : '客户证照号码';
		// 显示提示

		// 证件类型
		document.getElementById('fnCertTypeBox').className = (customerType == 'PERSIONAL') ? 'm-item' : 'm-item fn-hide';

		// 情况客户
		document.getElementById('customerName').value = '';
		document.getElementById('license').value = '';

		if (!!document.getElementById('makrNo')) {
			// 每次切换，清空业务种类
			document.getElementById('businessTypeName').value = '';
		}

		$('#license').trigger('change');

		document.getElementById('userType').value = customerType;

		// initRiskQuery.toggleXS((customerType == 'PERSIONAL') ? false : true);
		// initRiskQuery.toggleXX((customerType == 'PERSIONAL') ? false : true);

	}).on('click', '.fnApply', function() {

		// 去申请
		var _this = $(this);
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing;')
		var d = document;

		var pass = !d.getElementById('customerName').value || !d.getElementById('license').value || !d.getElementById('businessTypeCode').value || !!!customerType;
		
		if(d.getElementById('businessTypeCode').value == "999"){
			pass = !d.getElementById('businessTypeCode').value || !!!customerType;
		}
		if (pass) {
			console.log(!d.getElementById('businessTypeCode').value)
			Y.alert('提示', '请填写完整的申请信息');
			_this.removeClass('ing');
			return;
		}


        if(!$('#trueCustomerName').val()){
			if(d.getElementById('businessTypeCode').value == "999"){
                d.getElementById('form').submit();
			}else {
                checkCustomerOccupy($('#customerId').val(),$('#customerName').val(), function () {

                    if (_this.attr('id') == 'toCopy') {

                        _copyForm.resetAjaxUrl('/baseDataLoad/canCopySetupForm.json?customerId=' + document.getElementById('customerId').value + '&busiType=' + document.getElementById('businessTypeCode').value);

                        document.getElementById('fnCopyForm').click();

                    } else {

                        var _val = $('#add1').val().replace(/\s/g, '') || d.getElementById('license').value.replace(/\s/g, '');

                        $.when(checkPCard(_val)).then(function() {
                            return $.when(checkCard(_val, customerType))
                        }).done(function() {

                            d.getElementById('form').submit();

                        }).fail(function(res) {

                            Y.alert('提示', res.message);

                        });

                    }

                },false)
			}


        } else {

        	if (_this.attr('id') == 'toCopy') {

                _copyForm.resetAjaxUrl('/baseDataLoad/canCopySetupForm.json?customerId=' + document.getElementById('customerId').value + '&busiType=' + document.getElementById('businessTypeCode').value);

                document.getElementById('fnCopyForm').click();

            } else {

                var _val = $('#add1').val().replace(/\s/g, '') || d.getElementById('license').value.replace(/\s/g, '');

                $.when(checkPCard(_val)).then(function() {
                    return $.when(checkCard(_val, customerType))
                }).done(function() {

                    d.getElementById('form').submit();

                }).fail(function(res) {

                    Y.alert('提示', res.message);

                });

            }

        }


	});

	/**
	 * 查看是否已存在的证件号码
	 * 
	 * @param {[type]}
	 *            value [证件号码]
	 * @param {[type]}
	 *            customerType [证件类型]
	 * @return {[type]} [description]
	 */
	function checkCard(value, customerType) {

		var dtd = $.Deferred();

		if (ISCHOOSE) {
			dtd.resolve();
		} else {

			util.ajax({
				url : ((customerType == 'PERSIONAL') ? '/customerMg/personalCustomer/validata.json?certNo=' : '/customerMg/companyCustomer/validata.json?busiLicenseNo=') + value,
				success : function(xhr) {

					if (xhr.success && !!!xhr.type) {

						dtd.resolve();

					} else {
						dtd.reject({
							message : xhr.message
						});
					}

				}
			});
		}

		return dtd.promise();

	}

	function checkPCard(value) {

		var dtd = $.Deferred();

		if (customerType == 'PERSIONAL') {

			if (ISCHOOSE) {
				return dtd.resolve();
			} else {

				if (!!!$fnCertType.val()) {
					return dtd.reject({
						message : '客户证件类型'
					})
				}

				if ($fnCertType.val() != 'IDENTITY_CARD') {
					return dtd.resolve();
				} else {

					var _isPass = util.checkIdcard(value);

					if (_isPass == '验证通过!') {
						return dtd.resolve();
					} else {
						return dtd.reject({
							message : _isPass
						})
					}

				}

			}

		} else {
			return dtd.resolve();
		}

	}

	// 初始化isRightCer
	document.getElementById('form').reset();
});