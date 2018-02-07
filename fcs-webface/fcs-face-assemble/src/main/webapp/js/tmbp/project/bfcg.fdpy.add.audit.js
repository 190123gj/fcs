define(function (require, exports, module) {
    // 资金收付
    require('Y-msg');
    require('input.limit');
    require('zyw/opsLine');
    
	var popupWindow = require('zyw/popupWindow');
	
    var template = require('arttemplate');

    var COMMON = require('./bfcg.contract.common');

    var util = require('util');
    if (!!util.browserType() && util.browserType() < 10) { // IE9及以下不支持多选，加载单选上传
        require.async('tmbp/upAttachModify'); // 上传
    } else {
        require.async('tmbp/upAttachModifyNew'); // 上传
    } 
    
    // 什么什么财务应付审核
    require('./finance.audit.common');
    


    // 表单验证
    require('validate');

    // 2017.01.17 新增需求 银行融资担保 代偿 资金划付事由 需填写细分类
    // 代偿本金必填 其他非必填

    var IS_BANK_FG = (document.getElementById('creditType') || {}).value === '银行融资担保'
    var IS_BANK_FG_DATA = ['COMPENSATORY_PRINCIPAL', 'COMPENSATORY_INTEREST', 'COMPENSATORY_PENALTY', 'COMPENSATORY_LIQUIDATED_DAMAGES', 'COMPENSATORY_OTHER'] // 需要需要填写渠道金额的类型
    var IS_BANK_FG_MUST_DATA = ['COMPENSATORY_PRINCIPAL'] // 必须填写渠道金额的类型

    function needToInputChannelMoney(code) {
        return $.inArray(code, IS_BANK_FG_DATA) >= 0
    }

    function mustToInputChanelMoney(code) {
        return $.inArray(code, IS_BANK_FG_MUST_DATA) >= 0
    }

    var $body = $('body'),
        $fnDetailList = $('#fnDetailList');

    var util = require('util'),
        getList = require('zyw/getList'),
        projectType = $('#fnProjectType').val() || util.getParam('projectType'), // 编辑的时候不能从url中获取类型
        $fnBackAmount = $('#fnBackAmount'), // 回购的标志 如果有值，就是理财回购标志
        $fnPrice = $('#fnPrice'), // 理财产品 金额是单价的整数倍
        isHasSummary = $('#isHasSummary').val(),
        $fnTransferList = $('#fnTransferList');


    /**
	 * 重置 resetName 方法
	 * 
	 * @return {[type]} [description]
	 */
    util.resetName = function (diyname) {

        $('[diyname]').each(function (index, el) {

            var $tr = $(el)
            var _diyname = $tr.attr('diyname')
            var _i = $tr.index()

            $tr.attr('fulldiyname', _diyname + '[' + _i + '].')

            $tr.find('[name]:not(".fnAutoResetName")').each(function (index, el) {

                var _name = el.name

                if (_name.indexOf('.') > 0) {
                    _name = _name.substring(_name.indexOf('.') + 1)
                }

                el.name = _diyname + '[' + _i + '].' + _name

            })

        })

    }

    template.helper('num2k', function (str) {
        return util.num2k(str)
    })

    // ------ 选择项目 start
    var _getList = new getList();

    if (projectType == 'NOT_FINANCIAL_PRODUCT') {
        // 授信业务项目
        _getList.init({
            width: '80%',
            title: '项目列表',
            ajaxUrl: '/baseDataLoad/loanCapitalAppropriation.json',
            btn: '#chooseBtn',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.projectCode}}</td>',
                    '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 10)?item.projectName.substr(0,10)+\'\.\.\':item.projectName}}</td>',
                    '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>',
                    '        <td>{{item.busiTypeName}}</td>',
                    '        <td>{{item.amount}}</td>',
                    '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '项目名称：',
                    '<input class="ui-text fn-w160" type="text" name="projectName">',
                    '&nbsp;&nbsp;',
                    '客户名称：',
                    '<input class="ui-text fn-w160" type="text" name="customerName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: [
                    '<th width="100">项目编号</th>',
                    '<th width="120">项目名称</th>',
                    '<th width="120">客户名称</th>',
                    '<th width="100">业务品种</th>',
                    '<th width="100">授信金额（元）</th>',
                    '<th width="50">操作</th>'
                ].join('')
            },
            callback: function ($a) {
            	var isSimple = $("#isSimple").val();
            	if(isSimple != "IS") isSimple = "NO";
                window.location.href = "/projectMg/fCapitalAppropriationApply/addCapitalAppropriationApply.htm?projectCode=" + $a.attr('projectCode') + '&projectType=' + projectType + '&isSimple=' + isSimple;
            }
        });
    } else if (projectType == 'FINANCIAL_PRODUCT') {
        // 理财项目
        _getList.init({
            width: '80%',
            title: '项目列表',
            ajaxUrl: '/baseDataLoad/purchasingFinancialProject.json',
            btn: '#chooseBtn',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td title="{{item.productName}}">{{(item.productName && item.productName.length > 10)?item.productName.substr(0,10)+\'\.\.\':item.productName}}</td>',
                    '        <td title="{{item.issueInstitution}}">{{(item.issueInstitution && item.issueInstitution.length > 10)?item.issueInstitution.substr(0,10)+\'\.\.\':item.issueInstitution}}</td>',
                    '        <td>{{item.timeLimit}}{{item.timeUnitName}}</td>',
                    '        <td>{{item.price}}</td>',
                    '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '产品名称：',
                    '<input class="ui-text fn-w160" type="text" name="productName">',
                    '&nbsp;&nbsp;',
                    '发行机构：',
                    '<input class="ui-text fn-w160" type="text" name="issueInstitution">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: [
                    '<th width="100">产品名称</th>',
                    '<th width="120">发行机构</th>',
                    '<th width="100">产品期限</th>',
                    '<th width="100">票面单价（元）</th>',
                    '<th width="50">操作</th>'
                ].join('')
            },
            callback: function ($a) {
            	var isSimple = $("#isSimple").val();
            	if(isSimple != "IS") isSimple = "NO";
                window.location.href = "/projectMg/fCapitalAppropriationApply/addCapitalAppropriationApply.htm?projectCode=" + $a.attr('projectCode') + '&projectType=' + projectType + '&isSimple=' + isSimple;
            }
        });
        // 理财不能新增
        $('.fnAddLine[show!=true]').remove();
        // 理财还要去掉 删除 -、-
        if (document.getElementById('addForm')) {
            $('#list').find('tr').each(function (index, el) {
                $(this).find('td,th').last().remove()
            });
        }

        // 编辑状态 还原 回购价 避免后面出错
        if ($('#fnIsBack').val() == 'true' && !!!$fnBackAmount.val()) {
            $fnBackAmount.val($fnDetailList.find('.fnMakeMoney').eq(0).val());
        }

        // 理财产品回购
        $fnDetailList.on('change', 'select', function () {

            if (this.value && $fnBackAmount.val()) {

                $fnDetailList.find('.fnMakeMoney').eq(0).val($fnBackAmount.val()).prop('readonly', 'readonly').trigger('blur');

            } else {
                $fnDetailList.find('.fnMakeMoney').removeProp('readonly');
            }
        }).trigger('change');
    }

    // ------ 选择项目 end

    // ------新增收费 start

    util.resetName();

    var $addForm = $('#addForm'),
        limitObj = {};

    // 当选择费用种类为代偿本金、代偿利息、代偿违约金、代偿罚息、代偿其他时，则在备注下面显示提示“提示：该项目资金渠道为XXX，代偿期限为XX个工作日（自然日）”
    var $fnNeedTip = $('#fnNeedTip');
    $addForm.on('change', 'select', function () {

        var _need = false;
        $addForm.find('option:selected').each(function (index, el) {
            if (/(代偿本金|代偿利息|代偿违约金|代偿罚息|代偿-其他)/g.test(el.innerHTML)) {
                _need = true;
            }
        });

        if (_need) {
            $fnNeedTip.removeClass('fn-hide');
        } else {
            $fnNeedTip.addClass('fn-hide');
        }

    }).on('click', '.fnAddLine', function () {

        setTimeout(function () {
            util.resetName()
            $fnTransferList.find('.fnInput').each(function (index, el) {
                var $this = $(this)
                $this.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })
            });
            // console.log($fnTransferList.find('tr:last').prev().find('input').eq(0).val());
            // var $prev = $fnTransferList.find('tr:last').prev().find('input');
            // $fnTransferList.find('tr:last').find('input').each(function
			// (index, el) {
            // var $this = $(this)
            // $(this).val($prev.eq(index).val())
            // });

            var $prev = $fnTransferList.find('tr:last').prev().find('input').eq(2);
            $fnTransferList.find('tr:last').find('input').eq(2).val($prev.val())


            $fnDetailList.find('.fnChooseMarginValue').each(function (index, el) {
                var $this = $(this)
                $this.rules('remove', 'required')
                $this.valid()
            });

            $fnDetailList.find('.fnInput').each(function (index, el) {
                var $this = $(this)
                $this.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })
                if ($this.hasClass('fnMakeMoney')) {
                    $this.rules('add', {
                        min: 0,
                        messages: {
                            min: '不能为负数'
                        }
                    })
                }
            });
            $fnDetailList.find('.fnChooseMargin:not(".fn-hide")').each(function (index, el) {
                var $this = $(this)
                $this.parent().find('.fnChooseMarginValue').rules('add', {
                    required: true,
                    messages: {
                        required: '请选择存入保证金'
                    }
                })
            });

        }, 50)
    })

    // 汇总限制金额的条件
    $('.fnLimitObj').each(function (index, el) {

        if(el.id != 'COMMISSION_LOAN')
        {
            limitObj[el.id] = {
                max: +el.value,
                alias: el.getAttribute('alias')
            }
        }
    });

    // 是否超过金额限制
    function isThanLimit() {
        // 金额限制
        // 清空累加数据
        for (var k in limitObj) {
            limitObj[k].cumulative = null;
        }
        // 遍历已选 做累加
        $fnDetailList.find('select').each(function (index, el) {
            if (limitObj[el.value]) {
                var _old = limitObj[el.value].cumulative || 0;
                // console.log(this.parentNode.nextSibling.nextSibling.childNodes[0].value);
                limitObj[el.value].cumulative = _old + +($(this).parent().next().find('input')[0].value || '').replace(/\,/g, '');
            }
        });
        // 找出哪些超过了限制
        var limitArr = [];
        // 超出限制的总金额
        var limitToatal = 0;

        for (var k in limitObj) {
            if (limitObj[k].cumulative && limitObj[k].cumulative > limitObj[k].max) {
                limitArr.push(limitObj[k].alias + '(' + limitObj[k].max + '元)');
                limitToatal += limitObj[k].max
            }
        }

        if (limitArr.length) {
            return {
                success: false,
                message: '目前资金划付信息中' + limitArr.join('、') + '的金额超过限额'
            }
        } else {
            return {
                success: true
            }
        }
    }

    $addForm.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            element.parent().append(error);
        },
        submitHandler: function (form) {

            var _isPass = true,
                _isPassEq;

            $('.fnInput').each(function (index, el) {
                if (!!!el.value.replace(/\s/g, '')) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                }
            });

            if (!_isPass) {

                Y.alert('提醒', '请把表单填写完整', function () {
                    $('.fnInput').eq(_isPassEq).focus();
                });
                return;
            }

            // 2016.09.20 决策依据
            // var _checkBasis = COMMON.checkBasis();
            // if (!_checkBasis.success) {
            // Y.alert('提示', _checkBasis.message);
            // return;
            // }

            formSubmit('SUBMIT');

        },
        rules: {
            projectCode: {
                required: true
            },
        },
        messages: {
            projectCode: {
                required: '必填'
            },

        }
    });

    $fnDetailList.find('.fnInput').each(function (index, el) {
        var $this = $(this)
        $this.rules('add', {
            required: true,
            messages: {
                required: '必填'
            }
        })
        if ($this.hasClass('fnMakeMoney')) {
            $this.rules('add', {
                min: 0,
                messages: {
                    min: '不能为负数'
                }
            })
        }
    });


    // ------新增收费 end

    function formSubmit(type) {

        if (!!type) {
            var _channelInfo = MONEY_CHANNEL_INPUT.valid()
            if (!_channelInfo.success) {
                Y.alert('提示', _channelInfo.message)
                return
            }
        }

        var _projectCode = document.getElementById('projectCode');

        if (!!!_projectCode.value) {
            Y.alert('提示', '请选择项目', function () {
                _projectCode.focus();
            })
            return;
        }

        /**
		 * 2017-5-25 blocked by zhurui
		 */
        var _isThanLimit = isThanLimit();

        if (!_isThanLimit.success) {
            Y.alert('提示', _isThanLimit.message)
            return;
        }

        // 2016,10.11 理财产品不再限制 金额是单价的整数倍
        // 理财产品 购买，金额是单价的整数倍
        // var _isNotIntegerTimes = false,
        // _totalAmount = 0;
        // if (+$fnPrice.val() && !!!$fnBackAmount.val()) {

        // $list.find('.fnMakeMoney').each(function(index, el) {
        // _totalAmount += +el.value;
        // });

        // _isNotIntegerTimes = ((_totalAmount % +$fnPrice.val()) != 0) ? true :
		// false;

        // }

        // if (_isNotIntegerTimes) {

        // Y.alert('操作失败', '购买金额并不是单价的整数倍，当前项目的单价为' + $fnPrice.val());
        // return;

        // }

        // 2016.11.01 保证金

        var $btn = $('.fnChooseMargin:visible');
        if (!!$btn.length) {

            var _m = ($btn.parent().find('.fnMakeMoney').val() || '').replace(/\,/g, '')
            var _str = $btn.parent().find('.fnChooseMarginValue').val()
            if (!!!_m || +_m == 0) {
                Y.alert('提示', '请选择存入保证金')
                return
            }

            if (!CUSTOMER_DEPOSIT_REFUND_DATA.valid(_str)) {
                Y.alert('提示', '存入保证金有误，请重新选择')
                return
            }

        }

        document.getElementById('state').value = type || 'SAVE';
        document.getElementById('checkStatus').value = type ? 1 : 0;

        util.resetName();

        // 渠道金额
        $('.fnInputChannelMoneyInfo').each(function (index, el) {

            var $this = $(this)
            var diyname = $this.parents('tr').attr('fulldiyname')

            $this.find('[name]').each(function (index, el) {

                var _name = el.name
                var _len = _name.split('.').length

                if (_len <= 2) {
                    el.name = diyname + _name
                }

            })

        })

        // 保存数据
        util.ajax({
            url: '/projectMg/fCapitalAppropriationApply/saveCapitalAppropriationApply.htm',
            data: $addForm.serializeJCK(),
            success: function (res) {
            	var isSimple = $("#isSimple").val();
            		if(isSimple != "IS") isSimple = "NO";
                if (res.success) {
                    if (res.status == 'SUBMIT') {
                        // 提交表单
                        util.postAudit({
                            formId: res.form.formId
                        }, function () {
                            window.location.href = '/projectMg/fCapitalAppropriationApply/list.htm?isSimple=' + isSimple;
                        })
                    } else {
                        Y.alert('提醒', res.message, function () {
                            window.location.href = '/projectMg/fCapitalAppropriationApply/list.htm?isSimple=' + isSimple;
                        });
                    }
                } else {
                    Y.alert('提醒', res.message);
                }
            }
        });
    }

    // 选择代偿相关数据
    var isShowCompensatory = ($('#isCompensatory').val() == 'true') ? true : false,
        hasShowCompensatory = false,
        $list = $('#list');

    // ------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    if (document.getElementById('addForm')) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'save',
            event: function () {
                Y.confirm('提示', '是否暂存当前信息？', function (opn) {
                    if (opn == 'yes') {
                        formSubmit();
                    }
                });
            }
        }]);
        if (projectType == 'FINANCIAL_PRODUCT' && isHasSummary == 'true') {
            // 理财项目资金划付申请时，需要可以关联查看会议纪要
            publicOPN.addOPN([{
                name: '查看会议纪要',
                // url: '/projectMg/meetingMg/summary/approval.htm?projectCode='
				// + encodeURIComponent($('#summary').val())
                alias: 'lookMeeting',
                event: function () {

                    if (document.getElementById('councilTypeCode').value == 'PROJECT_REVIEW') {
                        window.open('/projectMg/meetingMg/summary/approval.htm?projectCode=' + encodeURIComponent($('#summary').val()))
                    } else {
                        util.openDirect('/projectMg/index.htm', ('/projectMg/meetingMg/summary/uploadMessage.htm?councilId=' + $('#councilId').val() + '&type=read'), '/projectMg/meetingMg/councilList.htm');
                    }

                    // var url =
					// (document.getElementById('councilTypeCode').value ==
					// 'PROJECT_REVIEW') ?
					// ('/projectMg/meetingMg/summary/approval.htm?projectCode='
					// + encodeURIComponent($('#summary').val())) :
					// ('/projectMg/meetingMg/summary/uploadMessage.htm?councilId='
					// + $('#councilId').val() + '&type=read');

                }
            }]);
        }
    }
    var projectType1 = $('#fnProjectType').val();
    if (projectType1 == 'NOT_FINANCIAL_PRODUCT') {
        // 展示项目批复，签报、放用款申请单（这个不管理财业务，只管授信业务）

        var ENCODE_PIFU = encodeURIComponent($('#pifu').val())

        publicOPN.addOPN([{
            name: '查看签报',
            alias: 'lookFormChangeApply',
            event: function () {
                util.openDirect('/projectMg/index.htm', ('/projectMg/formChangeApply/list.htm?projectCode=' + ENCODE_PIFU), '/projectMg/formChangeApply/list.htm');
                // window.open('/projectMg/formChangeApply/list.htm?projectCode='
				// + encodeURIComponent($('#pifu').val()))
                // var url = (document.getElementById('councilTypeCode').value
				// == 'PROJECT_REVIEW') ?
				// ('/projectMg/meetingMg/summary/approval.htm?projectCode=' +
				// encodeURIComponent($('#summary').val())) :
				// ('/projectMg/meetingMg/summary/uploadMessage.htm?councilId='
				// + $('#councilId').val() + '&type=read');

            }
        }]);
        if ($('#fnHasApproval').val() == 'IS') {
            publicOPN.addOPN([{
                name: '查看项目批复',
                // url: '/projectMg/meetingMg/summary/approval.htm?projectCode='
				// + encodeURIComponent($('#summary').val())
                alias: 'lookApproval',
                event: function () {
                    window.open('/projectMg/meetingMg/summary/approval.htm?projectCode=' + ENCODE_PIFU)
                        // var url =
						// (document.getElementById('councilTypeCode').value ==
						// 'PROJECT_REVIEW') ?
						// ('/projectMg/meetingMg/summary/approval.htm?projectCode='
						// + encodeURIComponent($('#summary').val())) :
						// ('/projectMg/meetingMg/summary/uploadMessage.htm?councilId='
						// + $('#councilId').val() + '&type=read');

                }
            }]);
        }
        publicOPN.addOPN([{
            name: '查看放用款申请',
            alias: 'lookLoanUseApply',
            event: function () {
                util.openDirect('/projectMg/index.htm', ('/projectMg/loanUseApply/list.htm?projectCode=' + ENCODE_PIFU), '/projectMg/loanUseApply/list.htm');
                // window.open('/projectMg/loanUseApply/list.htm?projectCode=' +
				// encodeURIComponent($('#pifu').val()))
                // var url = (document.getElementById('councilTypeCode').value
				// == 'PROJECT_REVIEW') ?
				// ('/projectMg/meetingMg/summary/approval.htm?projectCode=' +
				// encodeURIComponent($('#summary').val())) :
				// ('/projectMg/meetingMg/summary/uploadMessage.htm?councilId='
				// + $('#councilId').val() + '&type=read');

            }
        }]);
    }
    // 查看、审核页面
    if (isShowCompensatory) {

        $('#fnDetailList').find('tr').each(function (index, el) {

            var _txt = $(this).find('td').eq(0).text().replace(/\s/g, '');
            if ((/^代偿本金$/g).test(_txt) || (/^代偿利息$/g).test(_txt)) {
                publicOPN.addOPN([{
                    name: '查看风险处置',
                    url: '/projectMg/meetingMg/summary/scheme.htm?projectCode=' + document.getElementById('projectCode').value
                }]);
                return false;
            }

        });

    }

    publicOPN.init().doRender();
    // ------ 侧边栏 end
    //
    //
    // ------ 审核 start
    if (document.getElementById('auditForm')) {
        var auditProject = require('zyw/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    }
    // ------ 审核 end

    $list.on('change', 'select', function () {

        if (!isShowCompensatory) {
            return;
        }

        var _have = false;

        $list.find('select').each(function (index, el) {
            if (el.value == 'COMPENSATORY_PRINCIPAL' || el.value == 'COMPENSATORY_INTEREST') {
                _have = true;
                return false;
            }
        });

        showCompensatory(_have);

    }).find('select').trigger('change');

    function showCompensatory(boole) {

        if (hasShowCompensatory == boole) {
            return;
        }

        if (hasShowCompensatory) {
            // 其他操作中已有
            publicOPN.$box.find('li.fnLook').remove();
        } else {
            publicOPN.$box.find('ul').prepend('<li class="fnLook"><a target="_blank" href="/projectMg/meetingMg/summary/scheme.htm?projectCode=' + document.getElementById('projectCode').value + '">查看风险处置</a></li>');
        }

        hasShowCompensatory = boole;

    }

    // ------- 选择开户行 start
    var _bankList;

    $('#fnChooseBank').on('click', function () {

        if (!!!_bankList) {

            _bankList = new getList();
            _bankList.init({
                title: '选择收款人',
                ajaxUrl: '/baseDataLoad/payee.json',
                btn: '#fnChooseBankBtn',
                tpl: {
                    tbody: [
                        '{{each pageList as item i}}',
                        '    <tr class="fn-tac m-table-overflow">',
                        '        <td title="{{item.userName}}">{{(item.userName && item.userName.length > 6)?item.userName.substr(0,6)+\'\.\.\':item.userName}}</td>',
                        '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 10)?item.bankName.substr(0,10)+\'\.\.\':item.bankName}}</td>',
                        '        <td title="{{item.bankAccountNo}}">{{(item.bankAccountNo && item.bankAccountNo.length > 18)?item.bankAccountNo.substr(0,18)+\'\.\.\':item.bankAccountNo}}</td>',
                        '        <td><a class="choose" userId="{{item.userId}}" userName="{{item.userName}}" bankName="{{item.bankName}}" bankAccountNo="{{item.bankAccountNo}}" href="javascript:void(0);">选择</a></td>',
                        '    </tr>',
                        '{{/each}}'
                    ].join(''),
                    form: [
                        '收款人：',
                        '<input class="ui-text fn-w100" type="text" name="userName">',
                        '&nbsp;&nbsp;',
                        '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                    ].join(''),
                    thead: [
                        '<th>收款人</th>',
                        '<th>开户银行</th>',
                        '<th>银行账号</th>',
                        '<th width="50px">操作</th>'
                    ].join(''),
                    item: 4
                },
                callback: function ($a) {

                    function g(id, val) {
                        return document.getElementById(id).value = val;
                    }

                    g('fnChooseBankName', $a.attr('userName'))
                    g('fnChooseBankBank', $a.attr('bankName'))
                    g('fnChooseBankId', $a.attr('bankAccountNo'))

                }
            });


        }

        document.getElementById('fnChooseBankBtn').click();


    }).after('<div id="fnChooseBankBtn"></div>');

    // ------- 选择开户行 end

    // ------ 金额汇总 start
    var $fnTotalText = $('#fnTotalText')
    $fnDetailList.on('blur', '.fnMakeMoney', function () {

        setTimeout(function () {

            var _all = 0

            $fnDetailList.find('.fnMakeMoney').each(function (index, el) {

                // _all += +el.value
                _all = util.accAdd(_all, +(el.value || '').replace(/\,/g, ''))

            });

            $fnTotalText.html(util.num2k(_all.toFixed(2).toString()) + '元')

        }, 50)

    }).find('.fnMakeMoney').eq(0).trigger('blur');

    var fnTotalTexts = 0;

    $fnDetailList.find('.fnToTotal').each(function (index, el) {

        // fnTotalTexts += +el.innerHTML.replace(/\s/g, '').replace(/\,/g, '');
        fnTotalTexts = util.accAdd(fnTotalTexts, +(el.innerHTML || '').replace(/\s/g, '').replace(/\,/g, ''))

    });

    $fnTotalText.html(util.num2k(fnTotalTexts.toFixed(2).toString()) + '元')

    // ------ 金额汇总 end


    // ------ 退费 start

    var CUSTOMER_DEPOSIT_REFUND_DATA = {
        init: function () {

            var self = this

            $.get('/baseDataLoad/loadChargePay.json?projectCode=' + document.getElementById('projectCode').value + '&affirmFormType=CHARGE_NOTIFICATION&feeType=GUARANTEE_DEPOSIT&pageSize=999')
                .done(function (res) {

                    var _tbody = ''

                    if (res.success) {

                        var _data = {}

                        $.each(res.data.pageList, function (index, obj) {

                            _tbody += [
                                '<tr>',
                                '    <td class="fn-tac">存入保证金</td>',
                                '    <td>' + obj.payAmount.standardString + '</td>',
                                '    <td class="fn-tac">' + obj.payTime + '</td>',
                                '    <td>' + obj.returnAmount.standardString + '</td>',
                                '    <td class="fn-tac"><input type="checkbox" class="checkbox"></td>',
                                '    <td><input type="text" class="fnMakeMoney fnMakeMicrometer text" readonly maxnumber="' + obj.returnAmount.amount + '" minnumber="0" payid="' + obj.payId + '"></td>',
                                '</tr>'
                            ].join('')

                            _data[obj.payId] = obj.returnAmount.amount

                        })

                        self.data = _data

                    }

                    var _html = [
                        '    <div class="m-modal-overlay"></div>',
                        '    <div class="m-modal m-modal-default" id="modal">',
                        '        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span style="position: absolute; left: 25px; top: 20px;">本次申请退还金额：<span id="total">0</span>&nbsp;元</span><span class="title">存入保证金</span></div>',
                        '        <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner">',
                        '            <table class="m-table m-table-list"><thead><tr><th width="80px">费用种类</th><th>付款金额（元）</th><th width="80px">付款时间</th><th>未退还金额（元）</th><th>本次<br>是否退还</th><th>本次申请退还金额</th></tr></thead><tbody>',
                        _tbody,
                        '            </tbody></table>',
                        '    </div></div></div>',
                        '    <div class="fn-mt10 fn-tac"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-mr10 sure">确定</a><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-ml10 close">取消</a></div>'
                    ].join('')

                    self.$box.html(_html)

                    self.$total = self.$box.find('#total')

                })

            self.$box.appendTo('body')
                .on('click', '.checkbox', function () {

                    var $input = $(this).parent().next().find('.fnMakeMoney')

                    if (this.checked) {
                        $input.removeProp('readonly').val(util.num2k((+$input.attr('maxnumber')).toFixed(2)))
                    } else {
                        $input.prop('readonly', 'readonly').val('')
                    }
                })
                .on('click', '.close', function () {
                    self.hide()
                })
                .on('click', '.sure', function () {

                    var _res = self.getTotal()

                    if (_res.total === 0) {
                        Y.alert('提示', '请选择保证金')
                        return
                    }

                    if (!!self.callback) {
                        self.callback(_res)
                    }

                    self.hide()

                })
                .on('click', '.checkbox', function () {
                    self.$total.html(util.num2k(self.getTotal().total))
                })
                .on('blur', '.fnMakeMoney', function () {
                    setTimeout(function () {
                        self.$total.html(util.num2k(self.getTotal().total))
                    }, 50)
                })

            return this

        },
        $box: $('<div></div>').addClass('m-modal-box fn-hide'),
        getTotal: function () {

            var self = this

            var _total = 0,
                _valArr = []

            self.$box.find('td .fnMakeMoney:not([readonly])').each(function (index, el) {

                _total = util.accAdd(_total, +(el.value || '').replace(/\,/g, ''))

                _valArr.push([(el.value || '').replace(/\,/g, ''), el.getAttribute('payid')].join(','))

            })

            return {
                total: _total,
                value: _valArr.join(';')
            }

        },
        show: function () {
            this.$box.removeClass('fn-hide')
            return this
        },
        hide: function () {
            this.$box.addClass('fn-hide')
            return this
        },
        restore: function (str) {

            var self = this

            var _arr = str.split(';')

            var _total = 0;

            self.$box.find('td .fnMakeMoney').each(function (index, el) {
                    el.readonly = true
                    el.value = ''
                })
                .end().find('td .checkbox').each(function (index, el) {
                    el.checked = false
                })

            $.each(_arr, function (index, item) {
                if (!!item) {
                    var _a = item.split(',')

                    self.$box.find('td .fnMakeMoney[payid="' + _a[1] + '"]').val(util.num2k(_a[0])).removeProp('readonly')
                        .parent().prev().find('input').prop('checked', 'checked')

                    _total += +_a[0]

                }
            })

            self.$total.html(util.num2k(_total))

            return self
        },
        addCallback: function (fn) {
            this.callback = fn
            return this
        },
        valid: function (str) {

            var self = this

            var _arr = str.split(';')

            // 超过、或没有
            var _isPass = true
            $.each(_arr, function (index, item) {
                if (!!item) {
                    var _a = item.split(',')
                        // 金额已经用完
                    if (!!!self.data[_a[1]]) {
                        _isPass = false
                        return false
                    }

                    // 使用金额大于可用的金额
                    if (+self.data[_a[1]] < +_a[0]) {
                        _isPass = false
                        return false
                    }

                }
            })

            return _isPass;

        }
    }

    CUSTOMER_DEPOSIT_REFUND_DATA.init()

    function checkMargin(data, inputVal) {

        if (!!!data) {
            return {
                success: false,
                message: '请选择存入保证金'
            }
        }

        var _total = 0,
            _max = 0;

        $.each(data.split(';'), function (index, str) {

            var _val = +(str.split(',')[0] || 0);
            _total += _val
            _max = (_max <= _val) ? _val : _max

        });

        /*
		 * 选择 存入保证金 规则
		 * 
		 * 总金额 大于等于 填写金额
		 * 
		 * 选择多个 填写金额大于 除最大的金额和
		 * 
		 */

        if (_total < inputVal) {
            return {
                success: false,
                message: '所选存入保证金金额小于填写的金额'
            }
        }

        if (inputVal < (_total - _max)) {
            return {
                success: false,
                message: '所选存入保证金金额中最大面值并不能使用到'
            }
        }

        return {
            success: true
        }

    }

    /**
	 * 填写渠道的弹出层管理
	 * 
	 * @type {Object}
	 */
    var MONEY_CHANNEL_INPUT = {
        init: function () {

            var self = this

            $.get('/baseDataLoad/loadCapitalChannel.json?projectCode=' + document.getElementById('projectCode').value)
                .then(function (res) {
                    // console.log(res)
                    self.channels = res
                })

            self.$box.validate(util.validateDefault)

            self.$box.appendTo('body')
                .on('click', '.fnAddTr', function () {
                    self.$box.find('tbody').append(self.renderTh(self.nullTr()))
                    self.resetName().toggleRequired(true)
                })
                .on('click', '.fnDelTr', function () {
                    $(this).parents('tr').remove();
                })
                .on('click', '.check', function () {

                    var $this = $(this)
                    var $input = $this.parent().parent().find('.fnMakeMoney')

                    if ($this.prop('checked')) {
                        $input.removeProp('disabled').removeClass('disabled')
                            .rules('add', {
                                required: true,
                                min: 0.01,
                                messages: {
                                    min: '金额必须大于0',
                                    required: '必填'
                                }
                            })
                    } else {
                        $input.prop('disabled', 'disabled').addClass('disabled').val('')
                            .rules('remove', 'required')
                    }

                })
                .on('change', 'select', function () {
                    var $this = $(this)
                    var $option = $this.find(':selected')
                    $this.parent().find('input').each(function (index, el) {
                        el.value = $option.attr(el.className)
                    })
                })
                .on('click', '.close, .sure', function () {

                    var _html = ''
                    var _allHasMoney = true
                    var _allHasMoneyEq

                    self.$box.find('tbody tr').each(function (index, el) {

                        var $tr = $(el)
                        var _i = $tr.index()
                        var _tr = ''
                        var _total = 0

                        $tr.find('input:not(".disabled")').each(function (index, el) {

                            var _name = el.name || el.className
                            var __name = ''

                            if (_name.indexOf('checkbox') >= 0) {
                                return true
                            }

                            if (_name.indexOf('.') >= 0) {
                                __name = _name.substring(_name.indexOf('.') + 1)
                            } else {
                                __name = _name
                            }

                            _name = 'compensatoryChannelOrders[' + _i + '].' + __name

                            _tr += '<input class="fnAutoResetName" type="hidden" value="' + (el.value || '').replace(/\,/g, '') + '" name="' + _name + '">'

                            if (self.isMoneyEl(__name)) {
                                _total = util.accAdd(_total, +(el.value || 0))
                            }

                        })

                        _html += '<div class="fnChannelVal">' + _tr + '</div>'

                        if (_total == 0) {
                            _allHasMoney = false
                            _allHasMoneyEq = index
                        }

                    })

                    if (this.className.indexOf('sure') >= 0 && self.$box.find('.fnMustMark').hasClass('fn-f30')) {

                        if (!self.$box.valid()) {
                            Y.alert('提示', '请填写勾选的内容')
                            return
                        }

                        if (!_allHasMoney) {
                            Y.alert('提示', '第' + (_allHasMoneyEq + 1) + '行渠道并没有金额')
                            return
                        }

                    }


                    self.toggleRequired(false)

                    self.callback && self.callback(self, _html)
                    self.hide()
                })

        },
        channels: [],
        $box: $('<form></form>').addClass('m-modal-box fn-hide'),
        show: function () {
            this.$box.removeClass('fn-hide')
            return this
        },
        hide: function () {
            this.$box.addClass('fn-hide')
            return this
        },
        resetName: function () {
            var self = this

            self.$box.find('tr[dname="self"]').each(function (index, el) {

                var $tr = $(el)
                var _i = $tr.index()

                $tr.find('.fnMakeMoney').each(function (index, el) {

                    var _name = el.name

                    if (_name.indexOf('.') >= 0) {
                        _name = _name.substring(_name.indexOf('.') + 1)
                    }

                    el.name = 'self[' + _i + '].' + _name

                })

            })

            return this
        },
        toggleThead: function (required) {

            if (required) {
                this.$box.find('.fnMustMark').addClass('fn-f30').html('*')
            } else {
                this.$box.find('.fnMustMark').removeClass('fn-f30').html()
            }

        },
        toggleRequired: function (add) {

            this.$box.find('.fnMakeMoney').each(function (index, el) {

                var $input = $(el)

                $input.rules('remove', 'required')

                $input.valid()

                if (add && !el.readOnly) {
                    $input.rules('add', {
                        required: true,
                        min: 0.01,
                        messages: {
                            min: '金额必须大于0',
                            required: '必填'
                        }
                    })

                }

            })

            return this

        },
        tpl: [
            '<tr dname="self">',
            '   <td class="fn-tac">',
            '       {{if view}}',
            '           {{checkChannel.capitalChannelName}}',
            '       {{else}}',
            '           <select class="ui-select">',
            '               {{each channels as item i}}',
            '                   <option {{if item.capitalChannelId == checkChannel.capitalChannelId }} selected {{/if}} value="{{item.capitalChannelId}}" capitalChannelId="{{item.capitalChannelId}}" capitalChannelName="{{item.capitalChannelName}}" capitalChannelCode="{{item.capitalChannelCode}}" capitalChannelType="{{item.capitalChannelType}}" capitalSubChannelCode="{{item.capitalSubChannelCode}}" capitalSubChannelId="{{item.capitalSubChannelId}}" capitalSubChannelName="{{item.capitalSubChannelName}}" capitalSubChannelType="{{item.capitalSubChannelType}}">{{item.capitalChannelName}}</option>',
            '               {{/each}}',
            '           </select>',
            '           {{each checkChannel as item key}}',
            '               <input type="hidden" class="{{key}}" value="{{item}}">',
            '           {{/each}}',
            '       {{/if}}',
            '   </td>',
            '   <td>',
            '       {{if liquidityLoanAmount && liquidityLoanAmount > 0}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox" checked>流动资金贷款</label><input {{if view}} readonly {{/if}} value="{{=num2k(liquidityLoanAmount)}}" class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer" type="text" name="liquidityLoanAmount"> 元</div>',
            '       {{else}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox">流动资金贷款</label><input {{if view}} readonly {{/if}} class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer disabled" type="text" name="liquidityLoanAmount" disabled="disabled"> 元</div>',
            '       {{/if}}',
            '       {{if fixedAssetsFinancingAmount && fixedAssetsFinancingAmount > 0}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox" checked>固定资产融资</label><input {{if view}} readonly {{/if}} value="{{=num2k(fixedAssetsFinancingAmount)}}" class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer" type="text" name="fixedAssetsFinancingAmount"> 元</div>',
            '       {{else}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox">固定资产融资</label><input {{if view}} readonly {{/if}} class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer disabled" type="text" name="fixedAssetsFinancingAmount" disabled="disabled"> 元</div>',
            '       {{/if}}',
            '       {{if acceptanceBillAmount && acceptanceBillAmount > 0}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox" checked>承兑汇票担保</label><input {{if view}} readonly {{/if}} value="{{=num2k(acceptanceBillAmount)}}" class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer" type="text" name="acceptanceBillAmount"> 元</div>',
            '       {{else}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox">承兑汇票担保</label><input {{if view}} readonly {{/if}} class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer disabled" type="text" name="acceptanceBillAmount" disabled="disabled"> 元</div>',
            '       {{/if}}',
            '       {{if creditLetterAmount && creditLetterAmount > 0}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox" checked>信用证担保</label><input {{if view}} readonly {{/if}} value="{{=num2k(creditLetterAmount)}}" class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer" type="text" name="creditLetterAmount"> 元</div>',
            '       {{else}}',
            '           <div class="fn-mb10"><label class="fn-dpib fn-w100 fn-csp fn-usn"><input {{if view}} disabled {{/if}} class="check checkbox" type="checkbox">信用证担保</label><input {{if view}} readonly {{/if}} class="ui-text fn-w90 fn-ml5 fnMakeMoney fnMakeMicrometer disabled" type="text" name="creditLetterAmount" disabled="disabled"> 元</div>',
            '       {{/if}}',
            '   </td>',
            '   <td class="fn-tac">{{if view}} - {{else}}<a href="javascript:void(0);" class="{{(index == 0)?\'\':\' fnDelTr del\'}}">删除</a>{{/if}}</td>',
            '</tr>'
        ].join(''),
        nullTr: function () {
            var self = this
            var code = self.channels[0] || {}
            return {
                channels: self.channels,
                checkChannel: {
                    capitalChannelCode: code.capitalChannelCode,
                    capitalChannelId: code.capitalChannelId,
                    capitalChannelName: code.capitalChannelName,
                    capitalChannelType: code.capitalChannelType,
                    capitalSubChannelCode: code.capitalSubChannelCode,
                    capitalSubChannelId: code.capitalSubChannelId,
                    capitalSubChannelName: code.capitalSubChannelName,
                    capitalSubChannelType: code.capitalSubChannelType
                },
                liquidityLoanAmount: '',
                fixedAssetsFinancingAmount: '',
                acceptanceBillAmount: '',
                creditLetterAmount: '',
                index: 99
            }
        },
        renderTh: function (obj) {
            return template.compile(this.tpl)(obj)
        },
        render: function (arr, view) {

            var self = this
            var _tpl = ''
            var _arr = (arr && !!arr.length) ? arr : (view ? [] : [self.nullTr()])

            $.each(_arr, function (index, obj) {
                obj.index = index
                _tpl += self.renderTh(obj)
            })

            var _html = [
                '    <div class="m-modal-overlay"></div>',
                '    <div class="m-modal m-modal-default">',
                '        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title">渠道金额</span></div>',
                '        <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner">',
                '            <table class="m-table m-table-list"><thead><tr><th width="200px">实际代偿渠道</th><th><span class="fnMustMark"></span>实际代偿金额（元）</th><th width="50px">操作</th></tr></thead><tbody>',
                _tpl,
                '            </tbody></table>',
                view ? '' : '           <a class="ui-btn ui-btn-fill ui-btn-green fn-mt20 fnAddTr">新增一行</a>',
                '    </div></div></div>',
                '    <div class="fn-mt10 fn-tac">' + (view ? '' : '<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-mr10 sure">确定</a>') + '<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-ml10 close">取消</a></div>'
            ].join('')

            self.$box.html(_html)

        },
        isMoneyEl: function (name) {
            return $.inArray(name, ['liquidityLoanAmount', 'fixedAssetsFinancingAmount', 'acceptanceBillAmount', 'creditLetterAmount']) >= 0
        },
        restore: function ($div, view) {
            /**
			 * $div 所有渠道信息的父级元素
			 */

            var self = this
            var _data = []

            if ($div && !!$div.length) {

                $div.find('.fnChannelVal').each(function (index, el) {

                    var _obj = {
                        checkChannel: {}
                    }
                    _obj.index = index
                    _obj.channels = self.channels

                    if (view) {
                        _obj.view = true
                    }

                    $(this).find('input').each(function (index, el) {

                        var _name = el.name

                        if (_name.indexOf('.') >= 0) {
                            _name = _name.substring(_name.indexOf('.') + 1)
                        }

                        if (_name.indexOf('.') >= 0) {
                            _name = _name.substring(_name.indexOf('.') + 1)
                        }

                        if (self.isMoneyEl(_name)) {

                            _obj[_name] = el.value

                        } else {

                            _obj.checkChannel[_name] = el.value

                        }

                    })

                    _data.push(_obj)

                })

            }

            this.render(_data, view)

            return this

        },
        valid: function () {

            var _success = true
            var _message = ''

            $('.fnInputChannelMoneyInfo.need').each(function (index, el) {

                var $div = $(el)
                var _total = 0
                var _inputMoney = +($div.parent().find('.fnMakeMoney ').val() || '').replace(/\,/g, '')

                $div.find('input').each(function (index, el) {

                    var _name = el.name

                    if (_name.indexOf('.') >= 0) {
                        _name = _name.substring(_name.indexOf('.') + 1)
                    }

                    if (_name.indexOf('.') >= 0) {
                        _name = _name.substring(_name.indexOf('.') + 1)
                    }

                    if ($.inArray(_name, ['liquidityLoanAmount', 'fixedAssetsFinancingAmount', 'acceptanceBillAmount', 'creditLetterAmount']) >= 0) {

                        _total = util.accAdd(_total, +(el.value || 0))

                    }

                })

                // 是否必填 并且相等
                // 代偿金额必填 一旦填写了值
                if ($div.hasClass('must') || _total > 0) {
                    if (_total !== _inputMoney) {
                        _message = '请核对第' + (index + 1) + '个“代偿渠道金额”的划付事由<br>渠道金额总和与申请划付金额不相等'
                        _success = false
                        return false
                    }
                }

            })

            return {
                success: _success,
                message: _message
            }

        }
    }

    if (IS_BANK_FG) {
        MONEY_CHANNEL_INPUT.init()
    }

    var $triggerChooseMargin;
    var $triggerInputChannelMoney;

    $fnDetailList.on('change', 'select', function () {

        var $this = $(this),
            $tr = $this.parents('tr'),
            $fnChooseMargin = $tr.find('.fnChooseMargin'),
            $input = $tr.find('.fnMakeMoney')

        // 2017.01.17 新增 填写渠道金额
        var _fnInputChannelMoneyInfoClass = 'fnInputChannelMoneyInfo'

        if (needToInputChannelMoney(this.value)) {
            $tr.find('.fnInputChannelMoney').removeClass('fn-hide')
            _fnInputChannelMoneyInfoClass += ' need'
        } else {
            $tr.find('.fnInputChannelMoney').addClass('fn-hide')
        }

        if (mustToInputChanelMoney(this.value)) {
            _fnInputChannelMoneyInfoClass += ' must'
        }

        $tr.find('.fnInputChannelMoneyInfo').html('').attr('class', _fnInputChannelMoneyInfoClass)

        if ($fnDetailList.find('select option[value="' + $this.val() + '"]:checked').length >= 2) {
            Y.alert('提示', '已选择了该划付事由，请重新选择', function () {
                $this.find('option').eq(0).prop('selected', 'selected')
            })
            return
        }


        if ($this.val() == 'CUSTOMER_DEPOSIT_REFUND') {
            $fnChooseMargin.removeClass('fn-hide').parent().find('.fnChooseMarginValue').rules('add', {
                required: true,
                messages: {
                    required: '请选择存入保证金'
                }
            })
            $input.prop('readonly', 'readonly').val('')
        } else {
            $fnChooseMargin.addClass('fn-hide').parent().find('.fnChooseMarginValue').rules('remove', 'required')
            $fnChooseMargin.parent().find('.fnChooseMarginValue').valid()
            $input.removeProp('readonly').val('')
        }

    }).on('click', '.fnChooseMargin', function () {
        var $this = $(this)

        $triggerChooseMargin = $this

        CUSTOMER_DEPOSIT_REFUND_DATA.restore($this.parent().find('.fnChooseMarginValue').val()).show().addCallback(function (res) {

            $triggerChooseMargin.parent().find('.fnMakeMoney').val(res.total).trigger('focus')
                .end().find('.fnChooseMarginValue').val(res.value).trigger('blur')
                .end().find('.fnChooseMarginMakeMoney').trigger('blur')

        })

    }).on('click', '.fnLookMargin', function () {

        var _list = this.parentNode.getElementsByTagName('div')[0].innerHTML

        var _html = [
            '<div class="m-modal-overlay"></div>',
            '<div class="m-modal m-modal-default">',
            '    <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title">存入保证金</span></div>',
            '    <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner">',
            _list,
            '    </div></div></div>',
            '</div>'
        ].join('')

        var $div = $('<div class="m-modal-box"></div>').html(_html)

        $div.appendTo('body')
        $div.on('click', '.close', function () {
            $(this).parents('.m-modal-box').remove()
        })

    }).on('click', '.fnInputChannelMoney', function () {

        var $this = $(this)

        $triggerInputChannelMoney = $this

        var $fnInputChannelMoneyInfo = $triggerInputChannelMoney.next('.fnInputChannelMoneyInfo')

        if ($this.hasClass('view')) {

            MONEY_CHANNEL_INPUT.restore($fnInputChannelMoneyInfo, true).show()

        } else {

            MONEY_CHANNEL_INPUT.restore($fnInputChannelMoneyInfo, false).show()

            MONEY_CHANNEL_INPUT.callback = function (self, html) {

                $fnInputChannelMoneyInfo.html(html)

            }
        }

        MONEY_CHANNEL_INPUT.toggleThead($fnInputChannelMoneyInfo.hasClass('must'))

    })

    // ------ 退费 end

    // ------ 打印 start

    $('#fnPrint').click(function (event) {

        var $fnPrintBox = $('#fnPrintBox')

        $fnPrintBox.find('.ui-btn-submit').remove()

        $fnPrintBox.find('.printshow').removeClass('fn-hide')

        util.print($fnPrintBox.html())
    })

    // ------- 打印 end

    
	// 付款账户
	$('body').on('click', '.paymentBtn', function(event) {

		var $this = $(this);

		fundDitch = new popupWindow({

			YwindowObj: {
				content: 'paymentScript', // 弹窗对象，支持拼接dom、template、template.compile
				closeEle: '.close', // find关闭弹窗对象
				dragEle: '.newPopup dl dt' // find拖拽对象
			},

			ajaxObj: {
				url: '/baseDataLoad/bankMessage.json',
				type: 'post',
				dataType: 'json',
				data: {
					// isChargeNotification: "IS",
					// projectCode: _projectCode,
					pageSize: 6,
					pageNumber: 1
				}
			},
			formAll: { // 搜索
				submitBtn: '#PopupSubmit', // find搜索按钮
				formObj: '#PopupFrom', // find from
				callback: function($Y) {

				}
			},
			pageObj: { // 翻页
				clickObj: '.pageBox a.btn', // find翻页对象
				attrObj: 'page', // 翻页对象获取值得属性
				jsonName: 'pageNumber', // 请求翻页页数的dataName
				callback: function($Y) {
					// console.log($Y)
				}
			},

			callback: function($Y) {

				$Y.wnd.on('click', 'a.choose', function(event) {

					var _this = $(this),
						_account = _this.parents('tr').attr("account"),
						_amountStr = _this.parents('tr').find('td').eq(4).text().replace(/\,/g, '');

					$this.siblings('.fnBankAccount').val(_account).blur();

					if ($this.siblings('.feeTypePrev ').length) {

						$this.parents('tr').find('[amountStr]').attr('amountStr', _amountStr).blur();
						$this.parents('tr').attr('paymentAccount', _account);

					}

					$Y.close();

				});

			},
			console: false // 打印返回数据
		});
	});
    
    
	$('body').on('click', '.clearContent', function(event) {

		var $this, $target;

		$this = $(this);
		$target = $this.siblings('.clearContentTarget');

		$target.val('');
		$this.parents('tr').attr('paymentAccount', '').find('[amountStr]').attr('amountStr', '')

	});
	
	
	// ------ 审核选人 start
	// BPM弹窗
	var BPMiframe = require('BPMiframe');
	var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
	var BPMiframeConf = {
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '',
			selectUserNames: ''
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	};
	
	// ---法务经理
	if ($('#chooseLegalManager').val() == 'YES') {
		var _chooseLegalManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
			title: '法务经理'
		}));

		var $legalManagerName = $('#legalManagerName'),
			$legalManagerId = $('#legalManagerId'),
			$legalManagerAccount = $('#legalManagerAccount');

		$('#legalManagerBtn').on('click', function () {

			_chooseLegalManager.init(function (relObj) {

				$legalManagerId.val(relObj.userIds);
				$legalManagerName.val(relObj.fullnames);
				$legalManagerAccount.val(relObj.accounts);

			});
		});
	}
	// ------ 审核选人 end
});