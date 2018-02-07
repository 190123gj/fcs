define(function (require, exports, module) {
    //项目管理 > 理财项目管理 > 立项申请
    require('input.limit');
    require('zyw/upAttachModify');
    require('validate');

    var util = require('util');
    var getList = require('zyw/getList');

    var $form = $('#form'),
        requiredRules = {
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            doSubmit(true);
        }
    }))

    var $fnRateRangeStart = $('#fnRateRangeStart')
    var $fnRateRangeEnd = $('#fnRateRangeEnd')

    $fnRateRangeStart.on('blur', function () {

        var $this = $(this)

        setTimeout(function () {

            if ($this.val() > 100) {
                $this.val(100)
            }

            if ($this.val() < 0) {
                $this.val(0)
            }

            $fnRateRangeEnd.rules('remove', 'min')

            if (+$this.val()) {

                $fnRateRangeEnd.rules('add', {
                    min: +$this.val(),
                    messages: {
                        min: '最小值为{0}'
                    }
                })

            }

            if ($fnRateRangeEnd.val()) {
                $fnRateRangeEnd.valid()
            }

        }, 50)

    }).trigger('blur')

    $fnRateRangeEnd.on('blur', function () {

        var $this = $(this)

        setTimeout(function () {

            if ($this.val() > 100) {
                $this.val(100)
            }

            if ($this.val() < 0) {
                $this.val(0)
            }

            $fnRateRangeStart.rules('remove', 'max')

            if (+$this.val()) {

                $fnRateRangeStart.rules('add', {
                    max: +$this.val(),
                    messages: {
                        max: '最大值为{0}'
                    }
                })

            }


            if ($fnRateRangeStart.val()) {
                $fnRateRangeStart.valid()
            }

        }, 50)

    }).trigger('blur')

    var $fnProductName = $('#fnProductName'),
        OLD_PRODUCT_NAME = $fnProductName.val()

    if (OLD_PRODUCT_NAME) {

        $fnProductName.on('change', function () {

            setProductNameOnly(this.value != OLD_PRODUCT_NAME)

        })

    } else {
        setProductNameOnly(true)
    }

    function setProductNameOnly(isSet) {

        if (isSet) {

            $fnProductName.rules('add', {
                remote: {
                    url: '/baseDataLoad/checkFinancialProductName.json?_time=' + (new Date()).getTime(), //后台处理程序
                    type: 'post', //数据发送方式
                    dataType: 'json', //接受数据格式   
                    data: { //要传递的数据
                        productName: function () {
                            return $('input[name=productName]').val();
                        }
                    }
                },
                messages: {
                    remote: '名称已存在'
                }
            });

        } else {
            $fnProductName.rules('remove', 'remote');
            $fnProductName.valid();
        }

    }


    var $fnProductName = $('#productName'),
        OLD_PRODUCT_NAME = $fnProductName.val()

    setProductNameOnly(!!!OLD_PRODUCT_NAME)

    function setProductNameOnly(isSet) {

        if (isSet) {

            $fnProductName.rules('add', {
                remote: {
                    url: '/baseDataLoad/checkFinancialProductName.json?_time=' + (new Date()).getTime(), //后台处理程序
                    type: 'post', //数据发送方式
                    dataType: 'json', //接受数据格式   
                    data: { //要传递的数据
                        productName: function () {
                            return $('input[name=productName]').val();
                        }
                    }
                },
                messages: {
                    remote: '名称已存在'
                }
            });

        } else {
            $fnProductName.rules('remove', 'remote');
            $fnProductName.valid();
        }

    }


    // 保存数据
    function doSubmit(boole) {

        // 是否选择项目
        if (!!!document.getElementById('productName').value) {
            Y.alert('提示', '请选择一个产品');
            return;
        }

        var _state = boole ? '1' : '0';

        $('[name="checkStatus"]').val(_state);

        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {
                if (res.success) {

                    if (boole) {
                        //提交表单
                        Y.confirm('提示', '确认提交当前表单？', function (opn) {
                            if (opn == 'yes') {

                                util.postAudit({
                                    formId: res.form.formId
                                }, function () {
                                    window.location.href = '/projectMg/financialProject/setUp/list.htm';
                                })

                            }
                        });
                    } else {
                        Y.alert('提示', '操作成功', function () {
                            window.location = '/projectMg/financialProject/setUp/list.htm';
                        });
                    }

                } else {
                    Y.alert('操作失败', res.message);
                }
            }
        });


    }

    // $form.on('click', '#submit', function() {
    //  //提交表单

    //  var _isPass = true,
    //      _isPassEq;

    //  $fnInput.each(function(index, el) {
    //      if (!!!el.value) {
    //          _isPass = false;
    //          _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
    //      }
    //  });

    //  if (!_isPass) {
    //      Y.alert('提示', '请填写完整信息', function() {
    //          $fnInput.eq(_isPassEq).focus();
    //      });
    //      return;
    //  }

    //  doSubmit(true);

    // });


    /**计算到期时间*/
    function caculateExpireDate() {
        var productId = $("#productId").val(),
            buyDate = $("#buyDate").val(),
            timeLimit = $("#timeLimit").val(),
            timeUnit = $("#timeUnit").val();
        if (buyDate && (productId || (timeLimit && timeUnit))) {
            $.ajax({
                url: '/projectMg/financialProject/caculateExpireDate.htm',
                type: 'POST',
                dataType: 'json',
                data: {
                    "productId": productId,
                    "buyDate": buyDate,
                    "timeLimit": timeLimit,
                    "timeUnit": timeUnit
                },
                success: function (res) {
                    if (res.success) {
                        $('#expireDate').val(res.expireDate).trigger('blur');
                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });
        }
    }

    /**计算期限类型*/
    function caculateTermType() {
        var timeLimit = $("#timeLimit").val(),
            timeUnit = $("#timeUnit").val();
        if (timeLimit && timeLimit > 0 && timeUnit) {
            $.ajax({
                url: '/projectMg/basicmaintain/financialProduct/calculateProductTermType.htm',
                type: 'POST',
                dataType: 'json',
                data: {
                    "timeLimit": timeLimit,
                    "timeUnit": timeUnit
                },
                success: function (res) {
                    if (res.success) {
                        $("#termType").val(res.data.code);
                        $("#termTypeName").html(res.data.message);
                    } else {
                        Y.alert('提示', res.message);
                    }
                },
                complete: function () {
                    ajaxProductTermTypeing = false;
                }
            });
        }
        caculateExpireDate();
    }

    $("#buyDate").blur(caculateExpireDate);
    $("#timeLimit").change(caculateTermType);
    $("#timeUnit").change(caculateTermType);

    //------ 选择项目 start
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '产品列表',
        ajaxUrl: '/baseDataLoad/financialProduct.json',
        btn: '#chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.productName}}">{{(item.productName && item.productName.length > 6)?item.productName.substr(0,6)+\'\.\.\':item.productName}}</td>',
                '        <td title="{{item.issueInstitution}}">{{(item.issueInstitution && item.issueInstitution.length > 6)?item.issueInstitution.substr(0,6)+\'\.\.\':item.issueInstitution}}</td>',
                '        <td>{{item.timeLimit}}{{item.timeUnitName}}</td>',
                '        <td>{{item.interestRate}}%</td>',
                '        <td><a class="choose" productId="{{item.productId}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['产品名称：',
                '<input class="ui-text fn-w160" type="text" name="productName">',
                '&nbsp;&nbsp;',
                '发行机构：',
                '<input class="ui-text fn-w160" type="text" name="issueInstitution">',
                '<input type="hidden" name="" value="' + $('#fnMaintainType').val() + '">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">产品名称</th>',
                '<th width="120">发行机构</th>',
                '<th width="120">产品期限</th>',
                '<th width="100">预计年化收益率</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function ($a) {
            window.location.href = '/projectMg/financialProject/setUp/form.htm?productId=' + $a.attr('productId');
        }
    });

    //------ 选择项目 end


    //------ 审核通用部分 start
    var auditProject = require('zyw/auditProject');
    var _auditProject = new auditProject();
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

    // 不知道怎么了 审核要加点东西
    var $fnAuditSelects = $('.fnAuditSelect'),
        $fnAuditSelectDiv = $('.fnAuditSelectDiv'),
        $fnAuditSelect = $('#fnAuditSelect');
    $('.fnAuditRadio').on('click', function () {
        if (this.value == 'NO') {
            $fnAuditSelectDiv.addClass('fn-hide');
            $fnAuditSelect.val("").removeClass('fnAuditRequired');
        } else {
            $fnAuditSelects.change();
            $fnAuditSelectDiv.removeClass('fn-hide');
            $fnAuditSelect.addClass('fnAuditRequired');
        }

        document.getElementById('fnAuditRadio').value = this.value;

    });
    $fnAuditSelects.on('change', function () {
        $fnAuditSelect.val(this.value);
    });

    //------ 审核通用部分 end 


    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();

    if (document.getElementById('form')) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'doSave',
            event: function () {
                doSubmit()
            }
        }]);
    }

    publicOPN.addOPN([{
        name: '产品目录',
        alias: 'productIndex',
        event: function () {
            util.openDirect('/projectMg/index.htm', '/projectMg/basicmaintain/financialProduct/list.htm')
        }
    }]);
    publicOPN.init().doRender();
    //------ 侧边栏 end     
    
    
	// BPM弹窗
	var BPMiframe = require('BPMiframe');
	var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
	var BPMiframeConf = {
		'width' : 850,
		'height' : 460,
		'scope' : '{type:\"system\",value:\"all\"}',
		'selectUsers' : {
			selectUserIds : '',
			selectUserNames : ''
		},
		'bpmIsloginUrl' : '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl' : '/JumpTrust/makeLoginUrl.htm'
	};

	// ---风险经理
	if ($('#chooseRiskManager').val() == 'YES') {
		var _chooseRiskManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
			title : '风险经理'
		}));

		var $riskManagerName = $('#riskManagerName'), $riskManagerId = $('#riskManagerId'), $riskManagerAccount = $('#riskManagerAccount');

		$('#riskManagerBtn').on('click', function() {

			_chooseRiskManager.init(function(relObj) {

				$riskManagerId.val(relObj.userIds);
				$riskManagerName.val(relObj.fullnames);
				$riskManagerAccount.val(relObj.accounts);

			});
		});
	}
});