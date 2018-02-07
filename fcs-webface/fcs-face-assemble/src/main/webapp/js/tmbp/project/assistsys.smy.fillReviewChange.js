define(function(require, exports, module) {

	var contrastTool = require('zyw/changeApply'), util = require('util');
    var _util = require('util')
	var ALLDATALIST, // 初始值
	$fnChangeForm = $('#fnChangeForm');

	var formContent = $fnChangeForm.find('[name="formContent"]').val(), changeFormId = $fnChangeForm.find('[name="formId"]').val();

	setTimeout(function() {

        if ($fnChangeForm.length > 0) {

            $fnChangeForm.find('[name="originalFormData"]').val($('#form').serialize());
            $fnChangeForm.find('[name="originalPageContent"]').val(encodeURIComponent(_util.trimHtml(contrastTool.getHtml('#form'))));
            ALLDATALIST = contrastTool.getAllChangeApply();

            // 替换编辑页面
            if (changeFormId && changeFormId > 0 && formContent && formContent != '') {
                $('#form').html(decodeURIComponent(formContent));
                // var str = "seajs.use($_GLOBAL.zyw+'/customer/corporate.add');"
                //$(str).appendTo($('body script'))
                // var inputAble = $('#form').find('input,textarea,select');

				if($('input[name="changeType"]').val()==='QB'){

                    // 客户管理 > 企业客户 > 新增、编辑
                    var COMMONTOOL = require('../customer/personal.enterprise.add');

                    require('Y-entertip');
                    require('Y-msg');

                    require('validate');
                    require('validate.extend');

                    // 2017.01.10  信惠公司创建客户这些信息不必填
                    // 法人证件类型 法人证件号码 法定代表人地址
                    // 总资产 去年销售收入
                    // 主要股东名称 出资额（元） 出资方式 股权比例（%）
                    var IS_XH = (document.getElementById('fnIsXH') || {}).value == 'IS' // 是否是信惠公司

                    function removeRequired(id) {

                        var $id = $(id)

                        $id.rules('remove', 'required')

                        $id.parent().find('.m-required').remove()

                    }

                    if (IS_XH) {

                        setTimeout(function () {

                            removeRequired('#totalAsset');
                            removeRequired('#inCome');
                            removeRequired('#legalPersionCertType');
                            $('#fnGQJGTh span.fn-f30').remove()

                        }, 0)

                    }

                    var yearsTime = require('zyw/yearsTime');

                    $('.fnInputTime').click(function (event) {
                        if ($fnProjectStatus.val() == 'IN' || $fnProjectStatus.val() == 'IS') {
                            return
                        }
                        var yearsTimeFirst = new yearsTime({
                            elem: this,
                            format: 'YYYY',
                            max: (new Date()).getFullYear()
                        });
                    });

                    var util = require('util');
                    // 风险查询 信息、相似、失信
                    var riskQuery = require('zyw/riskQuery');
                    // if (document.getElementById('riskQuery')) {
                    // initRiskQuery(id, nameId, certNoId, typeId, orgCodeId, oneCertId, licenseNoId)
                    var initRiskQuery = new riskQuery.initRiskQuery(false, 'fnCustomerName', 'fnCertNo', 'userType', 'orgCode', 'isThreeBtn', 'fnCertNo');
                    // }

                    $('#fnCheckRiskBtn').on('click', function () {

                        initRiskQuery.getAllInfo(true, true, true, function (html) {
                            // document.getElementById('fnCheckRiskTip').innerHTML = html
                            $('#fnCheckRiskTip').html(html).removeClass('fn-hide')
                        })

                    })

                    var checkFRSFZ = new riskQuery.initCerNoNameMobile('legalPersionCertNo', 'legalPersion', false, 'fnCheckFRSFZBtn', 'fnCheckFRSFZTip');
                    // var checkKHM = new riskQuery.initCerNoNameMobile('fnCustomerName', 'fnCertNo', 'fnCheckKHMobile', 'fnCheckKHMBtn', 'fnCheckKHMTip');

                    // 企业客户关联的项目上会以后，除客户与我司关系外都不允许修改，如需修改，需要走签报流程。
                    var $fnProjectStatus = $('#fnProjectStatus');
                    if ($fnProjectStatus.val() == 'IN' || $fnProjectStatus.val() == 'IS') {

                        var _loading = new util.loading();
                        _loading.open();

                        setTimeout(function () {

                            $('#khjbqk').find('input[type="text"],textarea').attr('readonly', 'readonly')
                                .end().find('.laydate-icon').removeAttr('onclick')
                                .end().find('[type="radio"], [type="checkbox"]:not([name="relation"])').each(function (index, el) {

                                if (!!this.getAttribute('checked')) {

                                    var _input = document.createElement('input');
                                    _input.type = 'hidden';
                                    _input.value = this.value;
                                    _input.name = this.name;

                                    this.parentNode.appendChild(_input);

                                }

                            }).attr('disabled', 'disabled')
                                .end().find('select').each(function (index, el) {
                                $(this).find('option:not([selected])').remove()
                            })
                                .end().find('.fnUpFileDel,.fnUpAttachBtn,.fnAddLine').remove()
                                .end().find('img.fnUpFile').removeClass('fnUpFile')
                                .end().find('.fnDelLine').removeClass('fnDelLine del');

                            _loading.close();

                        }, 2000);

                    }

                    var NEEDCHECK = false,
                        $fnBusiLicenseNo = $('#fnCertNo'),
                        OLDNO = $fnBusiLicenseNo.val();

                    // 营业执照号同步
                    $fnBusiLicenseNo.on('change', function () {
                        document.getElementById('fnBusiLicenseNoCopy').value = this.value;
                        if (this.value != OLDNO) {
                            NEEDCHECK = true;
                        } else {
                            NEEDCHECK = false;
                        }
                    });

                    var ISCHANGE = document.getElementById('fnChangeForm') ? true : false, // 是否是签报页面
                        $form = $('#form'),
                        $fnCertNo = $('#fnCertNo'), // 证件号码
                        $fnCertType = $('#fnCertType'); // 证件类型

                    $form.validate($.extend(true, {}, COMMONTOOL.REQUIRERULES, {
                        errorClass: 'error-tip',
                        errorElement: 'b',
                        ignore: '.ignore',
                        onkeyup: false,
                        errorPlacement: function (error, element) {

                            if (element.hasClass('fnErrorAfter')) {

                                element.after(error);

                            } else {

                                element.parent().append(error);

                            }

                        },
                        rules: {
                            staffNum: {
                                min: 1
                            }
                            // legalPersionCertNo: {
                            //  checkID: true
                            // }
                            // taxCertificateNo: {
                            //  maxlength: 15,
                            //  minlength: 15
                            // }
                        },
                        messages: {
                            staffNum: {
                                min: '最小人数为1'
                            }
                            // legalPersionCertNo: {
                            //  checkID: '请输入正确的证件号码'
                            // }
                            // taxCertificateNo: {
                            //  maxlength: '请输入正确的证件号',
                            //  minlength: '请输入正确的证件号'
                            // }
                        }
                    }));

                    // 证件号码的验证
                    /**
                     * 根据条件 给对应的jQuery对象增删验证规则
                     * @param  {[type]} $type [description]
                     * @param  {[type]} $num  [description]
                     */
                    function validateCertNo($type, $num) {

                        if ($type.val() == 'IDENTITY_CARD') {

                            $num.rules('add', {
                                checkID: true,
                                messages: {
                                    checkID: '请输入正确的证件号码'
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
                    var $legalPersionCertType = $('#legalPersionCertType'),
                        $legalPersionCertNo = $('#legalPersionCertNo');

                    $legalPersionCertType.on('change', function () {
                        validateCertNo($legalPersionCertType, $legalPersionCertNo);
                        checkFRSFZ.toggleBtn((this.value === 'IDENTITY_CARD') ? true : false);
                    });

                    validateCertNo($legalPersionCertType, $legalPersionCertNo);

                    function getInputValue(id) {
                        return document.getElementById(id).value.replace(/\s/g, '')
                    }

                    function addVlidate() {
                        $('#gqjg').find('tbody tr').find('input').each(function (index, el) {

                            $(this).rules('add', {
                                required: true,
                                messages: {
                                    required: '必填'
                                }
                            });

                        });
                    }

                    if (!IS_XH) {
                        // 股权结构 必须填一个
                        addVlidate()
                    }

                    $('body').click(function () {
                        addVlidate()
                    })

                    //------ 提交、暂存 start
                    function doSubmit(bool) {

                        // 几个必填项
                        if (!!!getInputValue('fnCustomerName') || !!!getInputValue('fnCertNo')) {
                            Y.alert('提示', '请填写客户名称、信用统一代码/营业执照号', function () {

                                $(window).scrollTop(0);

                            });
                            return;
                        }

                        if (bool && !$form.valid()) {
                            Y.alert('提示', '表单还需完善', function () {

                                // 视野回到错误的地方
                                $(window).scrollTop($form.find('input.error-tip,select.error-tip,textarea.error-tip').eq(0).offset().top - 150);

                            });
                            return;
                        }

                        if (bool && !!!$('#addressResult').val()) {
                            // 很奇怪，地区为怎么就有问题呢
                            Y.alert('提示', '表单还需完善', function () {

                                // 视野回到错误的地方
                                $(window).scrollTop(400);

                            });
                            return;
                        }

                        // 证件号码是否重复
                        $.when(COMMONTOOL.waitCheck()).done(function (xhr) {

                            if (xhr.success && !!!xhr.type) {

                                if (bool) {
                                    $('input[name=status]').val('on');
                                } else {
                                    $('input[name=status]').val('zc'); //暂存状态
                                }

                                util.resetName();

                                util.ajax({
                                    url: $form.attr('action'),
                                    data: $form.serializeJCK(),
                                    success: function (res) {
                                        if (res.success) {

                                            Y.alert('提示', '操作成功', function () {

                                                window.location.href = '/customerMg/companyCustomer/list.htm';

                                            });
                                        } else {
                                            Y.alert('操作失败', res.message)
                                        }
                                    }
                                });

                            } else {
                                Y.alert('提示', '信用统一代码/营业执照号已存在')
                            }

                        });

                    }
                    $('#fnDoSave').on('click', function () {
                        doSubmit(false);
                    });
                    $('#fnDoSubmit').on('click', function () {
                        doSubmit(true);
                    });
                    //------ 提交、暂存 end

                    //------ 侧边栏 start
                    var publicOPN = new(require('zyw/publicOPN'))();
                    //通过页面不同的值，判断是否添加到侧边栏
                    if (document.getElementById('fnDoSave')) {
                        publicOPN.addOPN([{
                            name: '暂存',
                            alias: 'doSave',
                            event: function () {
                                doSubmit(false);
                            }
                        }]);
                    }
                    if (document.getElementById('fnDoSubmit')) {
                        publicOPN.addOPN([{
                            name: '提交',
                            alias: 'doSubmit',
                            event: function () {
                                doSubmit(true);
                            }
                        }]);
                    }

                    if (document.getElementById('fnUserId') && document.getElementById('fnUserId').value) {
                        publicOPN.addOPN([{
                            name: '查看业务记录',
                            alias: 'lookRecord',
                            event: function () {

                                util.ajax({
                                    url: '/customerMg/companyCustomer/queryProject.json?userId=' + document.getElementById('fnUserId').value,
                                    success: function (res) {
                                        $('body').Y('Msg', {
                                            type: 'alert',
                                            width: '900px',
                                            title: '与本担保公司的业务记录',
                                            content: COMMONTOOL.creatRecord(res.list)
                                        });
                                    }
                                });

                            }
                        }]);
                    }

                    // 查看页面
                    // if (document.getElementById('fnIsView')) {
                    var CHANGELIST;
                    publicOPN.addOPN([{
                        name: '查看修改记录',
                        alias: 'lookChange',
                        event: function () {
                            if (!!!CHANGELIST) {
                                CHANGELIST = COMMONTOOL.initChangeList();
                            }

                            document.getElementById('lookChangeBtn').click();

                        }
                    }]);
                    // }

                    publicOPN.init().doRender();
                    //------ 侧边栏 end

                    require('zyw/chooseIndustry');
                    require('Y-htmluploadify');

                    var $body = $('body');

                    //------ 企业基本信息 是否必填、大小限制 地区 start
                    //法人身份证号码 是否必填
                    var $legalPersionCertNo = $('#legalPersionCertNo'), //法人身份证
                        $legalPersionAddress = $('#legalPersionAddress'), //法人地址
                        $netAsset = $('#netAsset'), //净资产
                        $addressResult = $('#addressResult'), //最后一个地区
                        $industryCode = $('#industryCode'),
                        $isGovFP = $('#isGovFP'); //是否是融资平台

                    // $body.on('change', '#typeQYXZ', function () {
                    //
                    //     //法人身份证、地址
                    //     notOrgEnterprise(this.value);
                    //     //是否是国企
                    //     if (this.value === 'STATE_OWNED') {
                    //         $isGovFP.removeClass('fn-hide');
                    //     } else {
                    //         $isGovFP.addClass('fn-hide');
                    //     }
                    //
                    // }).on('change', '#totalAsset', function () {
                    //
                    //     netAssetMax(this.value);
                    //
                    // }).on('blur', '#countryCode', function () {
                    //
                    //     chinaRegion(this.value);
                    //
                    // }).on('blur', '#addressResult', function () {
                    //     $addressResult.valid();
                    // }).on('blur', '#industryCode', function () {
                    //     $industryCode.valid();
                    // });

                    //设置法人身份证、地址是否必填
                    function notOrgEnterprise(value) {

                        if (IS_XH) {
                            return;
                        }

                        var _requiredHtml = '<span class="m-required">*</span>';

                        // 2016.12.07  3)   法人证件类型及证件号码除国有企业以外，都改为必填；
                        // 法人地址原来就要求民营企业必填，有点冲突，暂时保持一致

                        //法人身份证、地址
                        if (value != 'STATE_OWNED') {

                            $legalPersionCertNo.rules('add', {
                                required: true,
                                // checkID: true,
                                messages: {
                                    required: '必填项'
                                    // checkID: '请输入正确的证件号码'
                                }
                            });

                            $legalPersionAddress.rules('add', {
                                required: true,
                                messages: {
                                    required: '必填项'
                                }
                            });

                            if (!$legalPersionCertNo.parents('.m-item').find('.m-required').length) {
                                $legalPersionCertNo.parents('.m-item').find('.m-label').prepend(_requiredHtml);
                            }
                            if (!$legalPersionAddress.parents('.m-item').find('.m-required').length) {
                                $legalPersionAddress.parents('.m-item').find('.m-label').prepend(_requiredHtml);
                            }

                        } else {

                            $legalPersionCertNo.rules('remove', 'required');
                            $legalPersionAddress.rules('remove', 'required');

                            $legalPersionCertNo.parents('.m-item').find('.m-required').remove();
                            $legalPersionAddress.parents('.m-item').find('.m-required').remove();

                        }

                        $legalPersionCertNo.valid();
                        $legalPersionAddress.valid();
                    }
                    //设置净资产小于总资产
                    function netAssetMax(value) {
                        value = (value || '').replace(/\,/g, '')
                        if (!!value) {
                            $netAsset.rules('add', {
                                max: parseFloat((value || 0), 10),
                                messages: {
                                    max: '不能大于总资产'
                                }
                            });
                        } else {
                            $netAsset.rules('remove', 'max');
                        }
                        $netAsset.valid();
                    }

                    //中国地区
                    function chinaRegion(value) {

                        if (value == 'China' || !!!value) {
                            $addressResult.rules('add', {
                                required: true,
                                messages: {
                                    required: '请继续选择'
                                }
                            });
                        } else {
                            $addressResult.rules('remove', 'required');
                        }
                        if (!!$addressResult.val()) {
                            $addressResult.valid();
                        }

                    }

                    setTimeout(function () {

                        if ($form.length == 0) {
                            return;
                        }

                        var _typeQYXZ = document.getElementById('typeQYXZ');

                        if (_typeQYXZ) {
                            notOrgEnterprise(_typeQYXZ.value);
                        }

                        var _totalAsset = document.getElementById('totalAsset');
                        var $totalAsset = $("#totalAsset")
                        $totalAsset.change(function () {
                            if (_totalAsset) {
                                netAssetMax(_totalAsset.value);
                            }
                        })

                        $netAsset.change(function () {
                            if (_totalAsset) {
                                netAssetMax(_totalAsset.value);
                            }
                        })


                        var _countryCode = document.getElementById('countryCode');
                        if (_countryCode) {
                            chinaRegion(_countryCode.value);
                            //$addressResult.valid();
                        }

                        //初始化
                        if (!!$('input[name="isOneCert"]:checked').val()) {
                            $isThree.addClass('fn-hide').find('input.required').each(function (index, el) {
                                var _this = $(this);
                                _this.attr('disabled', 'disabled').val('').rules('remove', 'required');
                                _this.valid();
                            });
                        }


                    }, 1000);
                    //------ 企业基本信息 是否必填、大小限制 end

                    //------ 三证合一 start
                    var $isThree = $('#isThree');

                    $('#isThreeBtn').on('click', function () {

                        var isThree = $('input[name="isOneCert"]:checked').val();

                        if (isThree) {
                            // 诡异的税务登记证 15位，记得清空
                            // $isThree.find('[name="taxCertificateNo"]').val('');
                            $isThree.addClass('fn-hide').find('input').each(function (index, el) {
                                var _this = $(this);
                                _this.attr('disabled', 'disabled').val('').rules('remove', 'required');
                                _this.valid();
                            });
                            $('#orgCode22Box').addClass('fn-hide').next().removeClass('m-item2');
                        } else {
                            $isThree.removeClass('fn-hide').find('input.required').each(function (index, el) {
                                var _this = $(this);
                                _this.removeAttr('disabled').rules('add', 'required');
                                _this.valid();
                            }).end().find('img').each(function (index, el) {
                                this.src = '/styles/tmbp/img/project/apply_upfile.jpg';
                            });
                            $('#orgCode22Box').removeClass('fn-hide').next().addClass('m-item2');
                        }
                    });
                    //------ 2016.10.17 上面新增一个三证合一 start
                    $('#orgCode22').on('blur', function () {
                        document.getElementById('orgCode').value = this.value
                    });
                    //------ 2016.10.17 上面新增一个三证合一 end
                    //------ 三证合一 end

                    //------ 股权不能超过100 start
                    var $fnEquityNew = $('#fnEquityNew'),
                        ISEQUITYWARNED = false;
                    $body.on('blur', '.fnInputEquity', function () {

                        var _thisVal = parseFloat(this.value || 0),
                            isAllEquity = 0;

                        $('.fnInputEquity').each(function (index, el) {
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
                                Y.alert('提醒', '股权已经达到100%', function () {
                                    ISEQUITYWARNED = false;
                                });
                            }
                        }

                        //_thisVal = (_thisVal < (100 - isAllEquity)) ? _thisVal : (100 - isAllEquity);

                        this.value = _thisVal.toFixed(2);

                    });
                    //------ 股权不能超过100 end

                    //------ 计算企业规模 start
                    var ajaxEnterpriseScale = {
                        industryCode: $body.find('#industryCode'), //行业编码
                        inCome: $body.find('#inCome'), //营业收入
                        employeeNum: $body.find('#employeeNum'), //从业人数
                        totalAsset: $body.find('#totalAsset') //资产总额
                    };

                    var ajaxEnterpriseScaleing = false; //是否正在ajax请求计算结果

                    $body.on('blur', '#industryCode,#inCome,#employeeNum,#totalAsset', function () {

                        if (ajaxEnterpriseScaleing) {
                            return;
                        }

                        var _isPass = true,
                            _scale = {};

                        //行业编码必要
                        if (!!!ajaxEnterpriseScale.industryCode.val()) {
                            _isPass = false;
                            return;
                        }

                        for (var k in ajaxEnterpriseScale) {

                            _scale[k] = (ajaxEnterpriseScale[k].val() || '').replace(/\,/g, '');

                        }

                        if (_isPass) {

                            ajaxEnterpriseScaleing = true;

                            $.ajax({
                                url: '/projectMg/common/calculateEnterpriseScale.htm',
                                type: 'POST',
                                dataType: 'json',
                                data: _scale,
                                success: function (res) {
                                    if (res.success) {
                                        $('#enterpriseScale').find('option').removeProp('selected').removeAttr('selected').prop('disabled', 'disabled').attr('disabled', 'disabled').addClass('fn-hide')
                                            .end().find('option[value="' + res.scale + '"]').prop('selected', 'selected').attr('selected', 'selected').removeProp('disabled').removeAttr('disabled').removeClass('fn-hide');
                                        document.getElementById('enterpriseScaleText').innerHTML = $('#enterpriseScale').find('option[value="' + res.scale + '"]').html().replace(/\s/g, '');
                                        document.getElementById('enterpriseScaleTexts').value = $('#enterpriseScale').find('option[value="' + res.scale + '"]').html().replace(/\s/g, '');
                                        document.getElementById('enterpriseScaleHidden').value = res.scale;
                                    } else {
                                        Y.alert('提示', res.message);
                                    }
                                },
                                complete: function () {
                                    ajaxEnterpriseScaleing = false;
                                }
                            });

                        }
                    }).find('#totalAsset').trigger('blur');
                    //------ 计算企业规模 end

                    //------ 选择币种 start
                    $('.fnChooseMoneyType').on('change', function () {

                        var $this = $(this)

                        if ($this.val() === '元') {
                            $this.next().val('元').addClass('fn-hide').rules('remove', 'required')
                        } else {
                            $this.next().removeClass('fn-hide').rules('add', {
                                required: true,
                                messages: {
                                    required: '必填'
                                }
                            })
                        }

                    }).trigger('change');
                    //------ 选择币种 end
                    var isqb = $('.ui-breadcrumb').html().indexOf('签报') > 0
                    var cName = $("[name='busiLicenseNo']")

                    if (isqb) {

                        $("body").on('change','#isThreeBtn',function () {

                            checkIfChecked()

                        })


                        function checkIfChecked() {

                            if($('#isThreeBtn').prop('checked') || $('#isThreeBtn').attr('checked') === 'checked') {

                                cName.removeAttr('readonly')


                            } else {

                                cName.attr('readonly','true')

                            }

                        }

                        checkIfChecked()
                    }
				}

                else if($('input[name="formCode"]').val()==='COUNCIL_SUMMARY_PROJECT_REVIEW'){
                    //验证共用
                    var _form = $('#form'),
                        //所有不必填 editorValue 编辑器bug
                        _allWhetherNull = {
                            stringObj: 'editorValue,timeRemark,assetRemark,belongToNc,amount,beforeDay,isChargeEveryBeginning,depositAccount,debtedAmountStr,interestRate,totalCost,chargeRemark,assetType,ownershipName,evaluationPrice,pledgeRate,remark,guarantorType,itype,other,pathName_COUNTER_GUARANTEE,id,guarantor,guaranteeAmountStr,guaranteeWay,tabId,spId,summaryId,projectCode,cancel,isRepayEqual,checkIndex,checkStatus,formId,formCode,initiatorId,initiatorAccount,processFlag,isMaximumAmount',
                            boll: true
                        },
                        requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
                            rules[name] = {
                                required: true,
                                messages: {
                                    required: '必填'
                                }
                            };
                        }),
                        maxlength50Rules = _form.requiredRulesSharp('editorValue,assetRemark,content,overview,projectOverview,chargeRemark,remark,assetType,ownershipName,evaluationPrice,pledgeRate,other,pathName_COUNTER_GUARANTEE,id,guaranteeAmountStr,tabId,spId,summaryId,projectCode,cancel,isRepayEqual,checkIndex,checkStatus,formId,formCode,initiatorId,initiatorAccount,processFlag,isMaximumAmount,upBp,downBp,assetLiabilityRatio,upRate,downRate', _allWhetherNull['boll'], {}, function(rules, name) {
                            rules[name] = {
                                maxlength: 50,
                                messages: {
                                    maxlength: '已超出50字'
                                }
                            };
                        }),
                        digitsRules = _form.requiredRulesSharp('timeLimit,afterDay,afterYear,afterYearEnd,monthPeriod,beforeDay', false, {}, function(rules, name) {
                            rules[name] = {
                                digits: true,
                                maxlength: 9,
                                messages: {
                                    digits: '请输入9位以内的整数',
                                    maxlength: '请输入9位以内的整数'
                                }
                            };
                        }),
                        isMoneyRules = _form.requiredRulesSharp('amountStr,guaranteeAmountStr', false, {}, function(rules, name) {
                            rules[name] = {
                                isMoney: true,
                                messages: {
                                    isMoney: '请输入整数位14位以内，小数位2位以内的数字'
                                }
                            };
                        }),
                        isPercentTwoDigitsRules = _form.requiredRulesSharp('interestRate,ratio,assetLiabilityRatio,upRate,downRate', false, {}, function(rules, name) {
                            rules[name] = {
                                isPercentTwoDigits: true,
                                messages: {
                                    isPercentTwoDigits: '请输入100.00以内的数字'
                                }
                            };
                        }),
                        isSpecialRateRules = _form.requiredRulesSharp('upDownRate,assetLiabilityRatio,thresholdRatio,adjustRatio', false, {}, function(rules, name) {
                            rules[name] = {
                                isSpecialRate: true,
                                messages: {
                                    isSpecialRate: '请输入整数8位以内,小数2位以内的数字'
                                }

                            };
                        }),
                        maxlengthRules = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
                            rules[name] = {
                                maxlength: 1000,
                                messages: {
                                    maxlength: '已超出1000字'
                                }
                            };
                        }),
                        rulesAllBefore = {
                            content: {
                                maxlength: 20000,
                                messages: {
                                    maxlength: '超出20000字'
                                }
                            },
                            debtedAmountStr: {
                                isMoneyCommon: true,
                                messages: {
                                    isMoneyCommon: '请输入整数13位以内,小数2位以内的数字'
                                }
                            },
                            synPosition: {
                                required: true,
                                messages: {
                                    required: '必填',
                                }
                            },
                            postposition: {
                                required: true,
                                messages: {
                                    required: '必填',
                                }
                            },
                            debtedAmount: {
                                isMoney: true,
                                messages: {
                                    isMoney: '请输入整数14位以内,小数2位以内的数字'
                                }
                            },
                            assetLiabilityRatio: {
                                isSpecialRate: true,
                                messages: {
                                    isSpecialRate: '请输入8位以内，小数点2位的数字'
                                }
                            },
                            amount: {
                                isMoneyMillion: true,
                                messages: {
                                    isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
                                }
                            },
                            upBp: {
                                isMoneyMillion: true,
                                messages: {
                                    isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
                                }
                            },
                            downBp: {
                                isMoneyMillion: true,
                                messages: {
                                    isMoneyMillion: '请输入整数位12位以内，小数位2位以内的数字'
                                }
                            },
                            guarantorType: {
                                required: true,
                                messages: {
                                    required: '必填'
                                }
                            },
                            guarantor: {
                                required: true,
                                messages: {
                                    required: '必填'
                                }
                            },
                            guaranteeAmountStr: {
                                required: true,
                                isMoney: true,
                                messages: {
                                    required: '必填',
                                    isMoney: '请输入整数位14位以内，小数位2位以内的数字'
                                }
                            },
                            // guaranteeWay: {
                            // 	required: true,
                            // 	messages: {
                            // 		required: '必填'
                            // 	}
                            // }
                        },
                        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, digitsRules, isMoneyRules, isPercentTwoDigitsRules, isSpecialRateRules, maxlengthRules, rulesAllBefore);

                    var submitValidataCommon = require('zyw/submitValidataCommon'),
                        isNotShowSidebar = document.getElementById('fnChangeApplyPost') ? true : false;
                    submitValidataCommon.submitValidataCommon({
                        notShowSidebar: isNotShowSidebar,
                        rulesAll: _rulesAll,
                        form: _form,
                        allWhetherNull: _allWhetherNull
                        // ValidataInit:{
                        //     form: _form,
                        //     successFun: function(res) {

                        //             //响应成功操作
                        //             //响应成功操作ssssssss

                        //     }
                        // } //有特殊响应,如无则配置zyw/submitValidataCommon公用响应
                    });

                    $('.changePrev').trigger('change');

                }

                setTimeout(function () {
                    var _textarea = $('textarea')
                    _textarea.each(function () {
                        var _this = $(this);
                        if(_this.hasClass('fnMakeUE')){
                            var _body = _this.prev().find('iframe').contents().find('body');
                            if(_body.length) {
                                _body.html(_this.val())
                            }
                        }
                    })
                },200)

            }

            if ($('#fnMOPN').length && $('.ui-tool').length ) {
                $('#fnMOPN').eq(1).remove()
                $('.ui-tool').eq(1).remove()
            }

            if($('.fnUpFileDel').length) {
                $('body').on('click','.fnUpFileDel', function() {
                    var _thisP = $(this).parent();
                    _thisP.find('.fnUpFile').attr('src', '/styles/tmbp/img/project/apply_upfile.jpg');
                    _thisP.find('.fnUpFileInput').val('');
                    _thisP.find('.fnUpFilePDF').html('');
                })
            }

        }
		// console.log(contrastTool.getHtml('#form'));
		// console.log(ALLDATALIST);

	}, 500);

	var $body = $('body');

	$body.on('click', '.fnChangeApplyPost', function() {
		
		var isDraft = ($(this).attr("draft") == "true");
		
		
        var $fnChangeForm = $('#fnChangeForm')
        var $_form = $('#form');

        $('.edui-editor ').each(function () {

			var _this = $(this)
            var _body = _this.find('iframe').contents().find('body')
            _this.parent().next('textarea').val(_body.html())
            _this.parent().next('textarea').html(_body.html())

        })

        putValueIntoAttr()

		if ($_form.valid()) {

			var differentString = contrastTool.contrast(contrastTool.getAllChangeApply(), ALLDATALIST),
				_differentString = util.json2string(differentString);

			// 找出差异内容
			var _obj = {
				single : [],
				complex : []
			};

			$.each(differentString, function(i, o) {
				// 区分单个还是复杂
				// 复杂组合 xxx[0]
				if (o.group) {
					_obj.complex.push(o.name);
				} else {
					_obj.single.push(o.name);
				}

			});

			var _util = require('util')
			var _html = encodeURIComponent(_util.trimHtml(contrastTool.getHtml('#form', _obj)));

			$fnChangeForm.find('[name="formData"]').val($_form.serializeJCK()); // 修改后的数据序列化
			$fnChangeForm.find('[name="pageContent"]').val(_html); // 修改后的html
			// 带上了标记

            console.log(_differentString)
            debugger
			$fnChangeForm.find('[name="exeUrl"]').val($_form.attr('action')); // 原本要提交的接口
			$fnChangeForm.find('[name="detailData"]').val(_differentString); // 修改记录
			// 编辑页面


            $fnChangeForm.find('[name="formContent"]').val(encodeURIComponent($_form.html()));
            //$fnChangeForm.find('[name="formContent"]').attr('value', encodeURIComponent($_form.html()));

			// console.log(util.trimHtml(contrastTool.getHtml('#form', _obj)))
			// console.log(differentString);
			// return;

			util.ajax({
				url : $fnChangeForm.attr('ajaxaction'),
				data : $fnChangeForm.serialize(),

				success : function(res) {
					if (res.success) {

						// util.ajax({
						// url: '/projectMg/form/submit.htm',
						// data: {
						// formId: res.form.formId
						// },
						// success: function (res2) {
						//
						// if (res2.success) {
						//
						// Y.alert('提示', '提交成功', function () {
						// window.location.href = $fnChangeForm.attr('backurl');
						// })
						//
						// } else {
						// Y.alert('提示', res2.message)
						// }
						//
						// }
						// });
						
						if(!isDraft){
							util.postAudit({
								formId : res.form.formId
							}, function() {
								window.location.href = $fnChangeForm.attr('backurl');
							})
						}else{
							window.location.href = $fnChangeForm.attr('backurl');
						}
					} else {
						Y.alert('提示', res.message)
					}
				}
			});

		} else {
			Y.alert('提示', '请核实填写内容是否正确');
		}

	});

	function putValueIntoAttr() {
		$('input,textarea,select').each(function () {
			var _this = $(this)
			var  type = _this[0].type || _this[0].tagName;

			switch(type){
				case 'text':
					_this.attr('value',_this.val())
					break;
				case 'radio':
					_this.attr('checked',_this.prop('checked'))
					break;
				case 'checkbox':
					_this.attr('checked',_this.prop('checked'))
					break;
				case 'file':
					_this.attr('value',_this.val())
					break;
				case 'select-one':
					_this.find('option').each(function () {
						var __this = $(this)
						if(__this.attr('value') === _this.val()){
							__this.attr('selected','selected')
						} else {
							__this.removeAttr('selected')
						}
                    })
					break;
				case 'textarea':
                    _this.attr('value',_this.val())
                    _this.html(_this.val())
					break;
			}
		})
	}


});