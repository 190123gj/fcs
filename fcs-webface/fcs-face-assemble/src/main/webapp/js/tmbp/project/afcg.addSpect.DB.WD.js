define(function (require, exports, module) {

    require('Y-window');
    var util = require('util');
    var getList = require('zyw/getList');

    //------ 选择项目 start 
    var _getList = new getList();
    _getList.init({
        title: '项目列表',
        ajaxUrl: '/projectMg/afterwardsCheck/queryProjects.json?select=my&phasesList=LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES',
        btn: '#chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目编号：',
                '<input class="ui-text fn-w160" name="projectCode" type="text">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" name="customerName" type="text">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">客户名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 8
        },
        callback: function ($a) {
            window.location = "/projectMg/afterwardsCheck/edit.htm?projectCode=" + $a.attr('projectCode');
        }
    });
    //------ 选择项目 end

    var COPYPROJECTLIST,
        COPYPROJECTV;
    $('#copy').click(function () {

        var _projectCode = document.getElementById('projectCode').value,
            _customerName = document.getElementById('customerName').value,
            DomeEdition = document.getElementById('edition'),
            _edition = DomeEdition.value;

        if (!!!_projectCode || !!!_customerName || !!!_edition) {
            Y.alert('提示', '请选择项目编号、报告类型');
            return;
        }

        if (!!!COPYPROJECTLIST) {

            COPYPROJECTLIST = new getList();
            COPYPROJECTLIST.init({
                title: '项目列表',
                btn: '#fnChooseProject',
                tpl: {
                    tbody: ['{{each pageList as item i}}',
                        '    <tr class="fn-tac m-table-overflow">',
                        '        <td>{{item.projectCode}}</td>',
                        '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                        '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                        '        <td>{{item.busiTypeName}}</td>',
                        '        <td>{{item.amount}}</td>',
                        '        <td><a class="choose" projectcode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                        '    </tr>',
                        '{{/each}}'
                    ].join(''),
                    form: ['项目编号：',
                        '<input class="ui-text fn-w160" name="projectCode" type="text">',
                        '&nbsp;&nbsp;',
                        '客户名称：',
                        '<input class="ui-text fn-w160" name="customerName" type="text">',
                        '&nbsp;&nbsp;',
                        '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                    ].join(''),
                    thead: ['<th width="100">项目编号</th>',
                        '<th width="120">客户名称</th>',
                        '<th width="120">项目名称</th>',
                        '<th width="100">授信类型</th>',
                        '<th width="100">授信金额(元)</th>',
                        '<th width="50">操作</th>'
                    ].join('')
                }
            });

        }

        COPYPROJECTLIST.resetAjaxUrl('/baseDataLoad/queryAfterwards.json?customerId=' + document.getElementById('customerId').value + '&edition=' + _edition);

        COPYPROJECTLIST.callback = function ($a) {

            if (!!!COPYPROJECTV) {
                COPYPROJECTV = new getList();
                COPYPROJECTV.init({
                    title: '版本列表',
                    btn: '#fnChooseV',
                    tpl: {
                        tbody: ['{{each pageList as item i}}',
                            '    <tr class="fn-tac m-table-overflow">',
                            '        <td>{{item.round}}</td>',
                            '        <td title="{{item.projectCode}}">{{(item.projectCode && item.projectCode.length > 6)?item.projectCode.substr(0,6)+\'\.\.\':item.projectCode}}</td>',
                            '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                            '        <td><a class="choose" formid="{{item.formId}}" href="javascript:void(0);">选择</a></td>',
                            '    </tr>',
                            '{{/each}}'
                        ].join(''),
                        form: '',
                        thead: ['<th width="100">期数</th>',
                            '<th width="120">项目编号</th>',
                            '<th width="120">项目名称</th>',
                            '<th width="50">操作</th>'
                        ].join('')
                    },
                    callback: function ($a) {

                        var $selectForm = $('#addForm').append('<input type="hidden" name="copiedFormId" value="' + $a.attr('formid') + '">');
                        util.ajax({
                            url: '/projectMg/afterwardsCheck/save.json',
                            data: $selectForm.serialize(),
                            success: function (res) {
                                if (res.success) {
                                    window.location.href = '/projectMg/afterwardsCheck/edit.htm?formId=' + res.form.formId;
                                } else {
                                    Y.alert('提示', res.message);
                                }
                            }
                        });

                    }
                });
            }

            COPYPROJECTV.resetAjaxUrl('/baseDataLoad/queryAfterwardsEdition.json?projectCode=' + $a.attr('projectcode') + '&edition=' + _edition);

            document.getElementById('fnChooseV').click();

        }

        document.getElementById('fnChooseProject').click();

        // $('.wnd-over,.apply-org-new').fadeIn(400);
        // $('.error').hide();
    }).after('<div id="fnChooseProject"></div><div id="fnChooseV"></div>')

    $('#fn-dbWd').delegate('.closeBtn', 'click', function () {
        $('.wnd-over,.apply-org-new').fadeOut(400);
    })

    // 选择提交
    $('#selectSubmit').click(function () {
        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var val = $('.busiType option:selected').val(),
            _loading = new util.loading();

        if (val == '') {
            $('.error').show();
            _this.removeClass('ing');
            return false;
        } else {
            _loading.open();
            $('.error').hide();
            util.ajax({
                url: $('#selectForm').attr('action'),
                data: $('#selectForm').serialize(),
                success: function (res) {
                    if (res.success) {
                        _loading.close();
                        window.location.href = '/projectMg/afterwardsCheck/editBaseInfo.htm?formId=' + res.form.formId;
                    } else {
                        Y.alert('提示', res.message);
                        _this.removeClass('ing');
                    }
                }
            })
        }
    });

    // 风险检索
    // $('#riskSearch').click(function (event) {
    //     var customerName = $('#customName').val(),
    //         customerId = $('#customLicenseNo').val();
    //     if (customerName != '' && customerId != '') {
    //         util.risk2retrieve(customerName, customerId);
    //     } else {
    //         Y.alert('提示', '请选择客户名称');
    //     }
    // });

    // 风险查询 信息、相似、失信
    var riskQuery = require('zyw/riskQuery');
    if (document.getElementById('fnRisk')) {
        // initRiskQuery(id, nameId, certNoId, typeId, orgCodeId, oneCertId, licenseNoId)
        // var initRiskQuery = new riskQuery.initRiskQuery('riskQuery', 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');
        var initRiskQuery = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');
        $('#fnRisk').on('click', function () {
            initRiskQuery.getAllInfo(true, true, true, function (html) {
                document.getElementById('fnRiskBox').innerHTML = html
            })

        })
    }


    //提交
    var $addForm = $('#addForm'),
        $fnInput = $('.fnInput');
    $fnInput.blur(function () {
        $(this).val().replace(/[^\d]/g, '')
    });
    $('#submit').on('click', function () {

        var _this = $(this),
            _loading = new util.loading();

        var _isPass = true;
        $fnInput.each(function (index, el) {

            if (!!!el.value.replace(/\s/g, '')) {
                _isPass = false;
            }

        })

        if (!_isPass) {
            Y.alert('提示', '请填写完整表单');
            return;
        }

        _loading.open();

        util.ajax({
            url: $addForm.attr('action'),
            data: $addForm.serialize(),
            success: function (res) {
                if (res.success) {
                    window.location.href = '/projectMg/afterwardsCheck/editBaseInfo.htm?formId=' + res.form.formId;
                } else {
                    Y.alert('提示', res.message);
                }

            }
        });

    });


});