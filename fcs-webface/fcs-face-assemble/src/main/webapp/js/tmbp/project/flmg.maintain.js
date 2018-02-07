define(function (require, exports, module) {
    //项目管理 > 理财项目管理 > 立项申请
    require('input.limit');
    require('zyw/upAttachModify');
    require('validate');

    var util = require('util');
    var getList = require('zyw/getList');

    //------ 选择项目信息维护  start
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '产品列表',
        ajaxUrl: '/baseDataLoad/projectFinancial.json?status=PURCHASING&qType=my',
        btn: '#chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.productName}}">{{(item.productName && item.productName.length > 6)?item.productName.substr(0,6)+\'\.\.\':item.productName}}</td>',
                '        <td title="{{item.issueInstitution}}">{{(item.issueInstitution && item.issueInstitution.length > 6)?item.issueInstitution.substr(0,6)+\'\.\.\':item.issueInstitution}}</td>',
                '        <td>{{item.timeLimit}}{{item.timeUnitName}}</td>',
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
                '<input type="hidden" name="" value="' + $('#fnMaintainType').val() + '">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">理财项目编号</th>',
                '<th width="120">产品名称</th>',
                '<th width="120">发行机构</th>',
                '<th width="120">产品期限</th>',
                '<th width="100">预计年化收益率</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            window.location.href = '/projectMg/financialProject/maintain.htm?projectCode=' + $a.attr('projectCode');
        }
    });

    //------ 选择项目信息维护 end   


    //------ 选择项目到期信息维护  start
    var _getListExpire = new getList();
    _getListExpire.init({
        width: '90%',
        title: '产品列表',
        ajaxUrl: '/baseDataLoad/projectFinancial.json?qs=expire&qType=my',
        btn: '#chooseBtnExpire',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.productName}}">{{(item.productName && item.productName.length > 6)?item.productName.substr(0,6)+\'\.\.\':item.productName}}</td>',
                '        <td title="{{item.issueInstitution}}">{{(item.issueInstitution && item.issueInstitution.length > 6)?item.issueInstitution.substr(0,6)+\'\.\.\':item.issueInstitution}}</td>',
                '        <td>{{item.timeLimit}}{{item.timeUnitName}}</td>',
                '        <td>{{item.interestRate}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
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
            thead: ['<th width="100">理财项目编号</th>',
                '<th width="120">产品名称</th>',
                '<th width="120">发行机构</th>',
                '<th width="120">产品期限</th>',
                '<th width="100">预计年化收益率(%)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            window.location.href = '/projectMg/financialProject/maintainExpire.htm?projectCode=' + $a.attr('projectCode');
        }
    });

    //------ 选择项目信息维护 end       
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        requiredRules = {
            rules: {
                interestRate: {
                    isPercentTwoDigits: true,
                }
            },
            messages: {
                interestRate: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字'
                }
            }
        }

        var hintPopup = require('zyw/hintPopup');

        _form.validate($.extend(true, util.validateDefault, {

            rules: requiredRules.rules,

            messages: requiredRules.messages,

            submitHandler: function () {
            util.ajax({
                url: _form.attr("action"),
                data: _form.serializeJCK(),
                success: function (res) {
                    if (res.success) {
                        hintPopup(res.message, function () {
                            window.location.href = "/projectMg/financialProject/list.htm"
                        })
                    } else {
                        hintPopup(res.message)
                    }
                }
            });
        }

        }));

    // $form.on('click', '#submit', function() {
    //  //提交表单
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
    //      url: $form.attr("action"),
    //      data: $form.serialize(),
    //      success: function(res) {
    //          if (res.success) {
    //              window.location.href = "/projectMg/financialProject/list.htm"
    //          } else {
    //              Y.alert('提示', res.message);
    //              _this.removeClass('ing');
    //          }
    //      }
    //  });
    // });


    $("#buyDate").blur(function () {
        var projectCode = $("#projectCode").val(),
            buyDate = $("#buyDate").val();
        if (projectCode && buyDate) {
            $.ajax({
                url: '/projectMg/financialProject/caculateExpireDate.htm',
                type: 'POST',
                dataType: 'json',
                data: {
                    "projectCode": projectCode,
                    "buyDate": buyDate
                },
                success: function (res) {
                    if (res.success) {
                        $('#expireDate').val(res.expireDate);
                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });
        }
    });

    var $body = $(document.body);
    //------ 计算转让收益 start
    var ajaxInterestRate = {
        principal: $body.find('#actualPrincipal'), //实收本金
        interest: $body.find('#actualInterest'), //实收收益
        projectCode: $("#projectCode")
    };

    var ajaxInterestRateing = false; //是否正在ajax请求计算结果

    $body.on('blur', '#actualPrincipal,#actualInterest', function () {

        if (ajaxInterestRateing) {
            return;
        }

        var _isPass = true,
            _scale = {};

        //行业编码必要
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
                        $('#actualInterestRate').val(res.interestRatePercent);
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

    // ------ 是否滚动 start

    $('.fnIsRoll').on('click', function () {

        if (this.value == 'IS') {
            $('#fnIsOpenBox').addClass('fn-hide').find('input[value="NO"]').prop('checked', 'checked')
        } else {
            $('#fnIsOpenBox').removeClass('fn-hide')
        }

    })

    // ------ 是否滚动 end

    // ------ 实收利息 不能小于 已结息金额 start 
    // 2016.11.07 暂时没有决定是否这样限制

    // $('#actualInterest').on('blur', function () {

    //     var self = this

    //     var DomSettlementAmount = document.getElementById('settlementAmount')

    //     if (!!DomSettlementAmount) {

    //         setTimeout(function () {

    //             var _min = +DomSettlementAmount.innerHTML.replace(/\,/g, '')

    //             if (+self.value <= _min) {
    //                 self.value = _min
    //             }

    //         }, 10)

    //     }

    // })

    // ------ 实收利息 不能小于 已结息金额 end

});