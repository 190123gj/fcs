define(function (require, exports, module) {
    // 项目管理 > 理财项目管理 > 理财产品转让申请
    require('input.limit');
    require('zyw/upAttachModify');
    require('validate');

    var util = require('util');
    var getList = require('zyw/getList');

    var $fnInput = $('.fnInput'), //填写项目
        $form = $('#form'),
        $redeemForm = $('#redeemForm');

    function doSubmit(boole) {

        if (!!!document.getElementById('productName').value) {
            Y.alert('提示', '请选择一个产品');
            return;
        }

        var _state = boole ? '1' : '0';

        $('[name=checkStatus]').val(_state);

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
                                    window.location.href = '/projectMg/financialProject/redeem/list.htm';
                                })
                            }

                        });

                    } else {

                        Y.alert('提示', '操作成功', function () {
                            window.location = '/projectMg/financialProject/redeem/list.htm';
                        });

                    }

                } else {

                    Y.alert('操作失败', res.message);

                }
            }
        });

    }


    var requiredRules = {
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
    }));

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

    $redeemForm.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            util.ajax({
                url: '/projectMg/financialProject/redeem/confirm.htm',
                data: $redeemForm.serializeJCK(),
                success: function (res) {
                    Y.alert('提示', res.message);
                    if (res.success) {
                        window.location.href = "/projectMg/financialProject/redeem/list.htm";
                    } else {
                        _this.removeClass('ing');
                    }
                }
            });
        }
    }));

    // $redeemForm.on('click', '#submit', function() {
    //  //转让信息维护
    //  var _this = $(this);
    //  if (_this.hasClass('ing')) {
    //      return;
    //  }
    //  _this.addClass('ing');

    //  var _isPass = true;

    //  $fnInput.each(function(index, el) {
    //      if (!!!el.value) {
    //          _isPass = false;
    //      }
    //  });

    //  if (!_isPass) {
    //      Y.alert('提示', '请填写完整信息');
    //      _this.removeClass('ing');
    //      return;
    //  }


    //  util.ajax({
    //      url: '/projectMg/financialProject/redeem/confirm.htm',
    //      data: $redeemForm.serialize(),
    //      success: function(res) {
    //          Y.alert('提示', res.message);
    //          if (res.success) {
    //              window.location.href = "/projectMg/financialProject/redeem/list.htm";
    //          } else {
    //              _this.removeClass('ing');
    //          }
    //      }
    //  });
    // });

    function expectPrincipalAndInterest() {
        var buyNum = $("#redeemNum").val(),
            caculateDate = $("#redeemTime").val(),
            projectCode = $("[name=projectCode]").val();
        if (buyNum && caculateDate && projectCode) {
            $.ajax({
                url: '/projectMg/financialProject/expectPrincipalAndInterest.htm',
                type: 'POST',
                dataType: 'json',
                data: {
                    "buyNum": buyNum,
                    "projectCode": projectCode,
                    "caculateDate": caculateDate
                },
                success: function (res) {
                    if (res.success) {
                        $('#redeemPrincipal').val(util.num2k(res.principal));
                        $('#redeemInterest').val(res.interest);
                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });
        }
    }

    $("#redeemNum").on('blur', function () {

        var self = this,
            KSHFS = self.getAttribute('canback')
        if (!!KSHFS) {

            KSHFS = +KSHFS.replace(/\,/g, '')

            setTimeout(function () {

                if (+self.value > KSHFS) {
                    self.value = KSHFS
                }
                expectPrincipalAndInterest()

            }, 5)

        } else {
            expectPrincipalAndInterest()
        }

    })
    $("#redeemTime").change(expectPrincipalAndInterest);

    var $body = $('body');
    //------ 计算转让收益 start
    // var redeemTime = $("#redeemTime"),
    //  fnPurchaseDate = $('#fnPurchaseDate').html(),
    //  fnExpireDate = $('#fnExpireDate').html();
    // redeemTime.on('click', function() {
    //  var self = this;
    //  laydate({
    //      elem: '#' + self.id,
    //      min: fnPurchaseDate,
    //      max: fnExpireDate
    //  });
    // });

    var ajaxInterestRate = {
        principal: $body.find('#actualRedeemPrincipal'), //实收本金
        interest: $body.find('#actualRedeemInterest'), //实收收益
        projectCode: $body.find("[name=projectCode]"),
        caculateDate: $body.find('#redeemTime')
    };

    var ajaxInterestRateing = false; //是否正在ajax请求计算结果

    $body.on('blur', '#actualRedeemPrincipal,#actualRedeemInterest,#redeemTime', function () {

        if (ajaxInterestRateing) {
            return;
        }

        var _isPass = true,
            _scale = {};

        if (!ajaxInterestRate.principal.val() || !ajaxInterestRate.interest.val()) {
            _isPass = false;
            return;
        }

        for (var k in ajaxInterestRate) {
            _scale[k] = (ajaxInterestRate[k].val() || '').replace(/\,/g, '');
        }

        if (_isPass) {

            ajaxInterestRateing = true;

            $.ajax({
                url: '/projectMg/financialProject/caculateInterestRate.htm',
                type: 'POST',
                dataType: 'json',
                data: _scale,
                success: function (res) {
                    if (res.success) {
                        $('#redeemInterestRate').val(res.interestRatePercent);
                    } else {
                        Y.alert('提示', res.message);
                    }
                },
                complete: function () {
                    ajaxInterestRateing = false;
                }
            });

        }
    });
    //------ 计算转让收益 end 


    //------ 选择项目 start
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '理财项目列表',
        ajaxUrl: '/baseDataLoad/financialTransferOrRedeemProject.json?hasHoldNum=IS&from=redeem',
        btn: '#chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.productName}}">{{(item.productName && item.productName.length > 6)?item.productName.substr(0,6)+\'\.\.\':item.productName}}</td>',
                '        <td title="{{item.issueInstitution}}">{{(item.issueInstitution && item.issueInstitution.length > 6)?item.issueInstitution.substr(0,6)+\'\.\.\':item.issueInstitution}}</td>',
                '        <td>{{item.holdNum}}</td>',
                '        <td>{{item.buyDate}}</td>',
                '        <td>{{item.interestRate}}%</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['产品名称：',
                '<input class="ui-text fn-w160" type="text" name="productName">',
                '&nbsp;&nbsp;',
                '发行机构：',
                '<input class="ui-text fn-w160" type="text" name="issueInstitution">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">产品名称</th>',
                '<th width="120">发行机构</th>',
                '<th width="200">可赎回份数</th>',
                '<th width="120">申购日</th>',
                '<th width="100">年化收益率</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 7
        },
        callback: function ($a) {
            window.location.href = '/projectMg/financialProject/redeem/form.htm?projectCode=' + $a.attr('projectCode');
        }
    });


    //------ 审核通用部分 start
    var auditProject = require('zyw/auditProject');
    var _auditProject = new auditProject();
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    //------ 审核通用部分 end
    //
    //
    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();

    if ($form.length) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'doSave',
            event: function () {
                doSubmit();
            }
        }]);
    }

    var productId = $("#productId").val();
    if (productId && productId > 0) {
        publicOPN.addOPN([{
            name: '查看产品信息',
            alias: 'pdinfo',
            event: function () {
                util.openDirect('/projectMg/index.htm', '/projectMg/basicmaintain/financialProduct/view.htm?productId=' + productId)
            }
        }]);
    }
    publicOPN.init().doRender();
    //------ 侧边栏 end
});