define(function (require, exports, module) {
    //项目管理 > 理财项目管理 > 立项申请
    require('input.limit');
    require('zyw/upAttachModify');
    require('validate');

    var util = require('util');
    var getList = require('zyw/getList');

    //------ 选择结息项目  start
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '产品列表',
        ajaxUrl: '/baseDataLoad/projectFinancial.json?qs=settlement&qType=my',
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
            window.location.href = '/projectMg/financialProject/toSettlement.htm?projectCode=' + $a.attr('projectCode');
        }
    });

    //------ 选择结息项目 end 


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
            util.ajax({
                url: $form.attr("action"),
                data: $form.serializeJCK(),
                success: function (res) {
                    if (res.success) {
                        Y.alert('操作成功', res.message, function () {
                            window.location.href = '/projectMg/financialProject/settlementList.htm'
                        })
                    } else {
                        Y.alert('操作失败', res.message);
                    }
                }
            });
        }
    }));

});