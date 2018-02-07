define(function (require, exports, module) {
    // 项目管理 > 项目签报

    require('zyw/upAttachModify');
    require('input.limit');
    require('validate');

    var getList = require('zyw/getList'),
        util = require('util');

    var $projectNumber = $('#projectNumber')
    var $changeForm = $('#changeForm')

    // 签报类型
    $('.fnRadio').on('click', function () {

        $('.fnRadioBox').addClass('fn-hide').find('.fnInputNeed').removeClass('fnInput')
            .end().find('input,select,textarea').prop('disabled', 'disabled').each(function (index, el) {
                $(this).rules('remove', 'required')
            });
        $('.fnRadioBox[val="' + this.value + '"]').removeClass('fn-hide').find('.fnInputNeed').addClass('fnInput')
            .end().find('input,select,textarea').removeProp('disabled')
            // .end().find('.fnInput').each(function (index, el) {
            // $(this).rules('add', {
            // required: true,
            // messages: {
            // required: '必填'
            // }
            // })
            // });

        if (this.value == 'ITEM') {
            $('#changeForm').find('option').removeProp('selected');
            // $('#fnMatters').trigger('change');
            $('#fnProjectCodeBox').find('.m-required').remove()

            setInputValidata($projectNumber, false)
            setInputValidata($changeForm, false)

            // $('#projectNumber').rules('remove', 'required')
            // $('#projectNumber').valid()
        }

        if (this.value == 'FORM') {
            $('#fnProjectCodeBox').removeClass('fn-hide')
                .find('.m-label:first').html('<span class="m-required">*</span>项目编号：')

            setInputValidata($projectNumber, true)
            setInputValidata($changeForm, true)

            // $('#fnMattersName').addClass('fn-hide');
        }

    });

    function setInputValidata($el, set) {

        $el.rules('remove', 'required')

        $el.valid()

        if (set) {
            $el.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })
        }

    }

    // 选择项目
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '项目列表',
        ajaxUrl: '/baseDataLoad/queryProjects.json?select=my&statusList=NORMAL,FAILED,RECOVERY,FINISH,SELL_FINISH,EXPIRE,OVERDUE',
        btn: '#chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item">{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td>{{item.phases}}</td>',
                '        <td><a class="choose" projectNumber="{{item.projectCode}}" customerId="{{item.customerId}}" customerName="{{item.customerName}}" href="javascript:void(0);">选择</a></td>', // 跳转地址需要的一些参数
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目编号：',
                '<input class="ui-text fn-w160" type="text" name="projectCode">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" type="text" name="customerName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">客户名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额<br>(万元)</th>',
                '<th width="100">项目阶段</th>',
                '<th width="50">操作</th>'
            ].join('')
        },
        callback: function ($a) {
            // 跳转地址
            $('#customerId').val($a.attr('customerId')).trigger('change').trigger('blur');
            document.getElementById('customerName').innerHTML = $a.attr('customerName');
            // $('#customerName').val($a.attr('customerName')).trigger('change').trigger('blur');
            $("#projectNumber").val($a.attr('projectNumber')).trigger('change').trigger('blur');
        }
    });

    // 事项切换
    // $('#fnMatters').on('change', function() {
    //
    // if (this.value == 'xm') {
    //
    // $('#fnProjectCodeBox').removeClass('fn-hide');
    // $('#fnMattersName').addClass('fn-hide');
    // $('#fnIsNeedCouncil').addClass('fn-hide');
    //
    // } else {
    //
    // $('#fnProjectCodeBox').addClass('fn-hide');
    // $('#fnMattersName').removeClass('fn-hide');
    // $('#fnIsNeedCouncil').removeClass('fn-hide');
    //
    // }
    //
    // });

    // 提交
    var $form = $('#form'),
        requiredRules = {
            rules: {},
            messages: {}
        };
    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages
    }));

    $('.fnRadio[checked="checked"]').click();

    $form.on('click', '.fnSubmit', function () {

        // var $fnInput = $('.fnInput'),
        // _isPass = true,
        // _isPassEq;

        // $fnInput.each(function (index, el) {
        // if (!!!el.value.replace(/\s/g, '')) {
        // _isPass = false;
        // _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
        // }
        // });

        // if (!_isPass) {
        // Y.alert('提示', '表单内容填写不完整', function () {
        // $fnInput.eq(_isPassEq).focus()
        // })
        // return;
        // }

        if (!$form.valid()) {
            Y.alert('提示', '表单内容填写不完整')
            return;
        }

        // 表单提交/ajax提交

        if ($('input[name="applyType"]:checked').val() == 'FORM') {
            $form.submit();
        } else {

            // 是否填好表单
            // if (document.getElementById('fnMatters').value == 'xm') {
            // if (!!!document.getElementById('projectNumber').value) {
            // Y.alert('提示', '请选择签报项目');
            // return;
            // }
            //
            // } else {
            // if (!!!document.getElementById('fnMattersNames').value) {
            // Y.alert('提示', '请填写事项名称');
            // return;
            // }
            // }

            if (!!!$('[name="changeRemark"]').val()) {
                Y.alert('提示', '请填写签报事项')
                return
            }

            util.ajax({
                url: $form.attr('ajaxaction'),
                data: $form.serialize(),
                success: function (res) {
                    if (res.success) {

                        util.postAudit({
                            formId: res.form.formId
                        }, function () {
                            window.location.href = '/projectMg/formChangeApply/list.htm'
                        })

                    } else {
                        Y.alert('提示', res.message)
                    }
                }
            });

        }

    });

    var _jump2FormBtn = $("#jump2FormBtn");

    function checkCanChange() {
        var changeForm = $("#changeForm").val(),
            projectCode = $("#projectNumber").val(),
            customerId = $("#customerId").val();
        if (changeForm && projectCode) {
        	if(changeForm == "CHARGE_REPAY_PLAY"){
        		$("#chargeRepayPlan").load("/projectMg/chargeRepayPlan/queryForChangeApply.json",{"projectCode" : projectCode});
        	}else{
        		$("#chargeRepayPlan").html("");
        	}
        	
            util.ajax({
                url: "/projectMg/formChangeApply/checkCanChange.htm",
                data: {
                    "projectCode": projectCode,
                    "changeForm": changeForm,
                    "customerId": customerId || 0
                },
                success: function (res) {
                    if (res.success) {
                        if (res.canChange) {
                            _jump2FormBtn.removeClass('ing');
                        } else {
                            Y.alert('提示', res.message)
                            _jump2FormBtn.addClass('ing');
                        }
                    } else {
                        Y.alert('提示', res.message);
                        _jump2FormBtn.addClass('ing');
                    }
                }
            });
        } else {
            _jump2FormBtn.addClass('ing');
        }

    }

    $("#changeForm").on("change", checkCanChange);
    $("#projectNumber").on("change", checkCanChange);


    // 审核部分分支选择后处理
    var $auditForm = $("#auditForm"),
        $leaderTaskNodeId = $auditForm.find("[name=leaderTaskNodeId]").val(),
        $deptTaskNodeId = $auditForm.find("[name=deptTaskNodeId]").val(),
        $wyhTaskNodeId = $auditForm.find("[name=wyhTaskNodeId]").val();
    $("body").on("click", "[name=radNextNodeId]", function () {
        var nodeId = $(this).val();
        if (nodeId == $leaderTaskNodeId) {
            $auditForm.find(".leaderTaskNodeId").show().find('[name]').removeAttr("disabled");
            $auditForm.find(".deptTaskNodeId").hide().find('[name]').attr("disabled", true);
            $auditForm.find(".wyhTaskNodeId").hide().find('[name]').attr("disabled", true);
        } else if (nodeId == $deptTaskNodeId) {
            $auditForm.find(".deptTaskNodeId").show().find('[name]').removeAttr("disabled");
            $auditForm.find(".leaderTaskNodeId").hide().find('[name]').attr("disabled", true);
            $auditForm.find(".wyhTaskNodeId").hide().find('[name]').attr("disabled", true);
        }  else if (nodeId == $wyhTaskNodeId) {
            $auditForm.find(".wyhTaskNodeId").show().find('[name]').removeAttr("disabled");
            $auditForm.find(".leaderTaskNodeId").hide().find('[name]').attr("disabled", true);
            $auditForm.find(".deptTaskNodeId").hide().find('[name]').attr("disabled", true);
        }else {
            $auditForm.find(".leaderTaskNodeId").hide().find('[name]').attr("disabled", true);
            $auditForm.find(".deptTaskNodeId").hide().find('[name]').attr("disabled", true);
            $auditForm.find(".wyhTaskNodeId").hide().find('[name]').attr("disabled", true);
        }
    }).on("click", "#wyhSel", function () {
    	var _this = $(this),
    	 	wyh = _this.val();
    	_this.siblings("[wyh]").hide();
    	_this.siblings("[wyh="+wyh+"]").show();
    })

    if (document.getElementById('auditForm')) {

        var auditProject = require('zyw/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

        var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选
												// 尽量在url后面加上参数
        // 初始化弹出层
        var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
            'title': '人员',
            'width': 850,
            'height': 460,
            'scope': '{type:\"system\",value:\"all\"}',
            'selectUsers': {
                selectUserIds: '', // 已选id,多用户用,隔开
                selectUserNames: '' // 已选Name,多用户用,隔开
            },
            'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
            'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
        });
        // 添加选择后的回调，以及显示弹出层
        $('#fnChooseMan').on('click', function () {

            var $p = $(this).parent(),
                $userName = $p.find('.userName'),
                $userId = $p.find('.userId');

            singleSelectUserDialog.init(function (relObj) {

                $userId.val(relObj.userIds);
                $userName.val(relObj.fullnames);

            });

        });

    }

    // ------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    var projectCode = $("#projectNumber").val();
    if (projectCode) {
        publicOPN.addOPN([{
            name: '项目详情',
            url: '/projectMg/viewProjectAllMessage.htm?projectCode=' + projectCode
        }]);
    }
    publicOPN.init().doRender();
    // ------ 侧边栏 end

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

    // $('#fnPrint').click(function (event) {
    // printdiv('div_print');
    // });
    $('#fnPrint').click(function (event) {

        var $fnPrintBox = $('#div_print')
        $fnPrintBox.find('.ui-btn-submit').remove()
        $fnPrintBox.find('.printshow').removeClass('fn-hide')
        util.print($fnPrintBox.html())
    })

    //table里面的内容溢出了
    $('td').children().each(function () {
      $(this).css('text-indent').replace('px','') > 0 ? $(this).css('text-indent','0px') : ''
    })

})