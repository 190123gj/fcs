define(function (require, exports, module) {
    // 项目管理 > 理财项目管理 > 理财产品转让申请
    require('input.limit');
    require('zyw/upAttachModify');
    require('validate');

    var util = require('util');
    var getList = require('zyw/getList');

    var $fnInput = $('.fnInput'), //填写项目
        $form = $('#form'),
        $transferForm = $('#transferForm');


    // ------ 申请转让 时间的限制 start
    var fnPurchaseDate = $('#fnPurchaseDate').html(),
        fnExpireDate = ($('#fnExpireDate').html() || '').replace(/\s/g, ''),
        $fnTransferDate = $('#transferTime'),
        $fnBuyBackTime = $('#fnBuyBackTime');

    fnExpireDate = (fnExpireDate === '-') ? '' : fnExpireDate

    $fnTransferDate.on('click', function () {
        var self = this;
        if (util.hasClass(self, 'free')) {
            return;
        }
        laydate({
            elem: '#' + self.id,
            min: fnPurchaseDate,
            max: fnExpireDate
        });
    });

    // 回购时间 大于转让 结束时间 小于 到期时间
    $fnBuyBackTime.on('click', function () {
        var self = this;
        laydate({
            elem: '#' + self.id,
            min: $fnTransferDate.val(),
            max: $('#expireDate').val()
        });
    });

    // ------ 申请转让 时间的限制 end

    // ------ 信息维护 转让时间start
    //      $transferTime = $('.transferTime');
    //
    //  $transferTime.on('click', function() {
    //      var self = this;
    //      laydate({
    //          elem: '#' + self.id,
    //          min: document.getElementById('fnBuyDate').innerHTML,
    //          max: $fnBuyBackTime.val()
    //      });
    //  });

    // ------ 信息维护 转让时间end

    //------ 选择项目 end
    $("[name=isBuyBack]").click(function () {
        if ($(this).val() != "IS") {
            $("#buyBackField").hide().find("[name]").attr("disabled", "disabled").removeClass('fnInput');
        } else {
            $("#buyBackField").show().find("[name]").removeAttr("disabled").addClass('fnInput');
        }
    });

    function doApplySubmit(boole) {

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

                    //提交表单
                    if (boole) {
                        Y.confirm('提示', '确认提交当前表单？', function (opn) {
                            if (opn == 'yes') {
                                util.postAudit({
                                    formId: res.form.formId
                                }, function () {
                                    window.location.href = '/projectMg/financialProject/transfer/list.htm';
                                })
                            }
                        });
                    } else {
                        Y.alert('提示 ', '操作成功', function () {
                            window.location = '/projectMg/financialProject/transfer/list.htm';
                        });
                    }

                } else {
                    Y.alert('操作失败', res.message);
                }
            }
        });

    }

    // 转让申请 转让份数限制
    $('#transferNum').on('blur', function () {

        var self = this

        setTimeout(function () {

            var _max = document.getElementById('fnKZRFS')

            if (!!!_max) {
                return
            }

            if (+self.value > +_max.innerHTML) {
                self.value = _max.innerHTML
                $('#transferNum').trigger('blur')
            }

        }, 10)

    });

    var requiredRules = {
        rules: {},
        messages: {}
    };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            doApplySubmit(true);
        }
    }));

    // 是否回购
    $('.fnIsRepo').on('click', function () {

        var $input = $('#buyBackField input.ui-text');

        if (this.value == 'IS') {
            $input.each(function (index, el) {

                $(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })

            });
        } else {
            $input.each(function (index, el) {

                $(this).rules('remove', 'required')

            });
            $input.valid()
        }
    });

    // $form.on('click', '#submit', function() {
    //  //提交表单
    //  var _isPass = true,
    //      _isPassEq;

    //  $fnInput = $('.fnInput');

    //  $fnInput.each(function(index, el) {
    //      if (!!!el.value.replace(/\s/g, '')) {
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

    //  doApplySubmit(true);

    // });

    $transferForm.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            util.ajax({
                url: '/projectMg/financialProject/transfer/confirm.htm',
                data: $transferForm.serializeJCK(),
                success: function (res) {
                    Y.alert('提示', res.message, function () {
                        if (res.success) {
                            window.location.href = '/projectMg/financialProject/transfer/list.htm';
                        }
                    });
                }
            });
        }
    }));

    // $transferForm.on('click', '#submit', function() {

    //  //转让信息维护
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

    //  // 是否是转让
    //  if (isFinanceTransfer && !isEarnings) {
    //      Y.alert('提示', '转让收益为负值，不能保存')
    //      return;
    //  }

    //  util.ajax({
    //      url: '/projectMg/financialProject/transfer/confirm.htm',
    //      data: $transferForm.serialize(),
    //      success: function(res) {
    //          Y.alert('提示', res.message, function() {
    //              if (res.success) {
    //                  window.location.href = '/projectMg/financialProject/transfer/list.htm';
    //              }
    //          });
    //      }
    //  });
    // });

    //------ 理财产品转让信息维护 需求，假若计算出来的 转让收益 是负数，不能提交
    var isFinanceTransfer = document.getElementById('fnIsFinanceTransfer') ? true : false, // 是理财 转让
        isEarnings = false; // 盈利 收益是正数

    var $body = $(document.body);
    //------ 计算转让收益 start
    var ajaxTransferInterest = {
        transferNum: $body.find('#transferNum'), //转让份额
        transferPrice: $body.find('#transferPrice'), //转让单价
        transferTime: $body.find('#transferTime'), //转让日期
        projectCode: $body.find('#projectCode') //项目编号
    };

    var ajaxTransferInteresting = false; //是否正在ajax请求计算结果

    $body.on('blur', '#transferNum,#transferPrice,#transferTime', function () {

        if (ajaxTransferInteresting) {
            return;
        }

        var _isPass = true,
            _scale = {};

        //行业编码必要
        if (!ajaxTransferInterest.transferNum.val() || !ajaxTransferInterest.transferPrice.val() || !ajaxTransferInterest.transferTime.val()) {
            _isPass = false;
            return;
        }

        for (var k in ajaxTransferInterest) {
            _scale[k] = (ajaxTransferInterest[k].val() || '').replace(/\,/g, '');
        }

        if (_isPass) {

            ajaxTransferInteresting = true;

            $.ajax({
                url: '/projectMg/financialProject/transfer/caculateTransferInterest.htm',
                type: 'POST',
                dataType: 'json',
                data: _scale,
                success: function (res) {
                    if (res.success) {
                        $('#transferInterest').val(util.num2k(res.transferInterest));
                        // 收拾是否是正数
                        if (res.transferInterest >= 0) {
                            isEarnings = true;
                        } else {
                            isEarnings = false;
                        }
                    } else {
                        Y.alert('提示', res.message);
                    }
                },
                complete: function () {
                    ajaxTransferInteresting = false;
                }
            });

        }
    });
    $("#transferNum").blur();
    //------ 计算转让收益 end 

    //------ 选择项目 start
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '理财项目列表',
        ajaxUrl: '/baseDataLoad/financialTransferOrRedeemProject.json?from=transfer',
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
                '<th width="200">可转让份数</th>',
                '<th width="120">申购日</th>',
                '<th width="100">年化收益率</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 7
        },
        callback: function ($a) {
            window.location.href = '/projectMg/financialProject/transfer/form.htm?projectCode=' + $a.attr('projectCode');
        }
    });

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
    //
    //
    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();

    if ($form.length) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'doSave',
            event: function () {
                doApplySubmit()
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

    //------ 是否过户 start
    $('.fnIsTransferOwnership').on('click', function () {

        if (this.value === 'IS') {
            $('.fnIsBuyBackBox').removeClass('fn-hide').find('input[value="NO"]').prop('checked', 'checked').trigger('click')
        } else {
            $('.fnIsBuyBackBox').addClass('fn-hide').find('input[value="IS"]').prop('checked', 'checked').trigger('click')
        }

    });
    //------ 是否过户 end

});