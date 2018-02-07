define(function (require, exports, module) {
    //放用款申请
    require('Y-msg');
    require('input.limit');
    require('zyw/opsLine');

    require('Y-htmluploadify');

    require('./bfcg.ctcd.attach');

    //表单验证
    require('validate');

    require('zyw/upAttachModify');

    //通用方法
    var util = require('util');
    var getList = require('zyw/getList');

    var $body = $('body');

    //------ 选择项目 start 
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '项目列表',
        ajaxUrl: '/baseDataLoad/loanUseProject.json',
        btn: '#chooseBtn',
        tpl: {
            tbody: [
                '{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.timeLimit}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td>{{item.chargeFee}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: [
                '项目编号：',
                '<input class="ui-text fn-w160" name="projectCode" type="text">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" name="customerName" type="text">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: [
                '<th>项目编号</th>',
                '<th>客户名称</th>',
                '<th>项目名称</th>',
                '<th width="80">授信期限</th>',
                '<th width="80">授信金额(元)</th>',
                '<th width="50">授信费用</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 7
        },
        callback: function ($a) {
            window.location = "/projectMg/loanUseApply/form.htm?projectCode=" + $a.attr('projectCode');
        }
    });
    //------ 选择项目 end

    //------ 新增收费 start

    var $addForm = $('#addForm');

    $addForm.validate({
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
        submitHandler: function (form) {

            // 申请放用款金额 必须大于 0
            var fnApplyMoney = document.getElementById('fnApplyMoney');

            if (+(fnApplyMoney.value || '').replace(/\,/g, '') <= 0) {

                Y.alert('提示', '申请放用款金额 必须大于0', function () {
                    fnApplyMoney.focus();
                });
                return;

            }

            resetName();

            if ($addForm.valid()) {
                $addForm.find('input[name="checkStatus"]').val('1');
            } else {
                $addForm.find('input[name="checkStatus"]').val('0');
            }

            util.ajax({
                url: '/projectMg/loanUseApply/saveApply.htm',
                data: $addForm.serialize(),
                success: function (res) {
                    if (res.success) {
                        submitForm(res.form.formId);
                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });

        },
        rules: {
            projectCode: {
                required: true
            },
            amount: {
                required: true
            },
            applyType: {
                required: true
            },
            expectLoanDate: {
                required: true
            },
            receiptName: {
                required: true
            },
            receiptBank: {
                required: true
            },
            interestSettlementCycle: {
                required: true
            },
            receiptAccount: {
                required: true,
                rangelength: [18, 21]
            }
        },
        messages: {
            projectCode: {
                required: '必填'
            },
            amount: {
                required: '必填'
            },
            applyType: {
                required: '必填'
            },
            expectLoanDate: {
                required: '必填'
            },
            receiptName: {
                required: '必填'
            },
            receiptBank: {
                required: '必填'
            },
            interestSettlementCycle: {
                required: '必填'
            },
            receiptAccount: {
                required: '必填',
                rangelength: '{0}-{1}位'
            }
        }
    });

    $addForm.find('.fnMakeMoney').each(function (index, el) {
        $(this).rules('add', {
            min: 0,
            messages: {
                min: '必须大于0'
            }
        })
    })

    $addForm.on('click', '#doSave', function () {

        if (!!!document.getElementById('projectCode').value) {
            return
        }

        if (!!!document.getElementById('moneyType').value) {
            Y.alert('提示', '请选择本次申请放用款类型')
            return
        }
        
        resetName();

        util.ajax({
            url: '/projectMg/loanUseApply/saveApply.htm',
            data: $addForm.serializeJCK(),
            success: function (res) {
                Y.alert('提示', res.message, function () {
                    if (res.success) {
                        window.location.href = '/projectMg/loanUseApply/list.htm'
                    }
                })
            }
        });
    });

    //当前申请金额最大值，根据不同用款类型而变化
    try {
        //审核、编辑公用代码，这里会报错，如果用jQuery就不会，真是纠结
        // var NOWCANUSEMONEY = +document.getElementById('fnFangMoney').innerHTML.replace(/\,/g, '') - +document.getElementById('fnApplyFangMoney').innerHTML.replace(/\,/g, '');
        var NOWCANUSEMONEY = +document.getElementById('fnFangMoney').innerHTML.replace(/\,/g, '');
    } catch (err) {
        var NOWCANUSEMONEY = 0;
    }

    $addForm.on('change', '#fnApplyMoney', function () {
        //今天申请最大值
        if ((this.value || '').replace(/\,/g, '') > NOWCANUSEMONEY) {
            this.value = util.num2k(NOWCANUSEMONEY);
        }
    }).on('change', '#cashDepositAmount', function () {
        //监听计划金额，是否必填
        cashDepositAmountRequired(this.value);
    });

    var $moneyTitle = $('#moneyTitle'),
        $fnApplyMoney = $('#fnApplyMoney');
    $('#moneyType').on('change', function () {
        var _title,
            _this = $(this);
        if (!!!this.value) {
            _title = '<span class="m-required">*</span>申请放用款金额：';
        } else {
            _title = '<span class="m-required">*</span>申请' + _this.find('[value=' + this.value + ']').text() + '金额：';
        }

        //放款、用款不同最大值
        if (this.value == 'USE') {
            // NOWCANUSEMONEY = +document.getElementById('fnYongMoney').innerHTML.replace(/\,/g, '') - +document.getElementById('fnApplyYongMoney').innerHTML.replace(/\,/g, '');
            NOWCANUSEMONEY = +document.getElementById('fnYongMoney').innerHTML.replace(/\,/g, '');
        } else {
            // NOWCANUSEMONEY = +document.getElementById('fnFangMoney').innerHTML.replace(/\,/g, '') - +document.getElementById('fnApplyFangMoney').innerHTML.replace(/\,/g, '');
            NOWCANUSEMONEY = +document.getElementById('fnFangMoney').innerHTML.replace(/\,/g, '');
        }

        $fnApplyMoney.trigger('change');

        $moneyTitle.html(_title);
    });

    // 保证金划付情况 是否必填
    var $cashDepositAmountRequiredInput = $('#cashDepositAmountRequired').find('input');

    function cashDepositAmountRequired(value) {
        if (+(value.replace(/\s/g, '') || 0) > 0) {
            $cashDepositAmountRequiredInput.each(function (index, el) {
                $(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填项'
                    }
                });
            });
        } else {
            $cashDepositAmountRequiredInput.each(function (index, el) {
                $(this).rules('remove', 'required');
            });
        }
        $cashDepositAmountRequiredInput.valid();
    }

    //------ 新增收费 end


    //------ 数组Name命名 start
    function resetName(orderName) {
        var _tr;
        if (orderName) {
            _tr = $('tr[orderName=' + orderName + ']');
        } else {
            _tr = $('tr[orderName]');
        }
        _tr.each(function () {

            var tr = $(this),
                _orderName = tr.attr('orderName'),
                index = tr.index();
            tr.find('[name]').each(function () {
                var _this = $(this),
                    name = _this.attr('name');
                if (name.indexOf('.') > 0) {
                    name = name.substring(name.indexOf('.') + 1);
                }
                name = _orderName + '[' + index + '].' + name;
                _this.attr('name', name);
            });

        });
    }


    function submitForm(formId) {

        util.postAudit({
            formId: formId
        }, function () {
            window.location.href = '/projectMg/loanUseApply/list.htm';
        })

    }


    //------ 审核通用部分 start
    var auditProject = require('zyw/auditProject');
    var _auditProject = new auditProject();
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    //------ 审核通用部分 end

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    //------ 侧边栏 end

    //------ 2017.01.12 授信条件 翻页 start
    // 显示隐藏未落实
    $body.on('click', '#fnHideIsConfirmIsNO', function () {

        getCreditConditions(2, 1)

    })

    var $creditPageList = $('#creditPageList')
    var $window = $(window)

    $creditPageList.find('.m-pages a').removeAttr('attribute name')

    window.getCreditConditions = function (totalPage, pageNo) {

        var PROJECT_CODE = document.getElementById('projectCode').value

        if (!!!PROJECT_CODE) {
            return
        }

        if (totalPage < pageNo) {
            return false;
        }

        var fnHideIsConfirmIsNO = document.getElementById('fnHideIsConfirmIsNO')
        var _hasToCarry = ''

        if (fnHideIsConfirmIsNO.checked) {
            _hasToCarry = fnHideIsConfirmIsNO.value
        }

        $creditPageList.load("/projectMg/loanUseApply/creditPageList.json", {
            'pageNumber': pageNo,
            'projectCode': PROJECT_CODE,
            'isConfirm': _hasToCarry
        }, function () {
            $window.scrollTop($creditPageList.offset().top)
        })


    }

    //------ 2017.01.12 授信条件 翻页 end
    
	function printdiv(printpage) {
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}

	// $('#fnPrint').click(function(event) {
	// 	printdiv('div_print');
	// });

    $('#fnPrint').click(function (event) {

        var $fnPrintBox = $('#div_print')
        console.log($fnPrintBox.html())
        $fnPrintBox.find('.ui-btn-submit').remove()

        $fnPrintBox.find('.printshow').removeClass('fn-hide')

        util.print($fnPrintBox.html())
    })

});