/*
* @Author: yanyang
* @Date:   2016-07-06 10:52:24
* @Last Modified by:   yanyang
* @Last Modified time: 2016-07-06 14:20:36
*/
define(function(require, exports, module) {
    require('validate');
    require('validate.extend');
    require('zyw/upAttachModify');

    var getList = require('zyw/getList'),
        util = require('util');

    // 选择项目
    var _getList = new getList();
    _getList.init({
        title: '清收项目',
        ajaxUrl: '/baseDataLoad/queryProjects.json?phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,RE_COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES',
        btn: '.chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
                '        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
                '        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectNumber="{{item.projectCode}}" customerId="{{item.customerId}}" projectName="{{item.projectName}}" href="javascript:void(0);">选择</a></td>', //跳转地址需要的一些参数
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目名称：',
                '<input class="ui-text fn-w160" type="text" name="projectName">',
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
                '<th width="100">授信金额(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function($a) {
            //跳转地址
            // document.getElementById('projectNumber').value = $a.attr('projectNumber');
            document.getElementById('projectName').value = $a.attr('projectName');
        }
    });


    // 表单验证

    var $addForm = $('#form');
    $addForm.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            element.after(error);
        },
        submitHandler: function(form) {
            var _isPass = true,
                _isPassEq;

            $('.fnInput').each(function(index, el) {
                if (!!!el.value.replace(/\s/g, '')) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                }
            });

            if (!_isPass) {

                Y.alert('提醒', '请把表单填写完整', function() {
                    $('.fnInput').eq(_isPassEq).focus();
                });
                return;
            }

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

    function formSubmit(type) {
        document.getElementById('state').value = type || 'SAVE';
        document.getElementById('checkStatus').value = type ? 1 : 0;
        util.resetName();

        //保存数据
        util.ajax({
            url: '/projectMg/afterwardsCheck/saveProject.json',
            data: $addForm.serialize(),
            success: function(res) {
                if (res.success) {
                    if (res.status == 'SUBMIT') {
                        //提交表单
//                        util.ajax({
//                            url: '/projectMg/form/submit.htm',
//                            data: {
//                                formId: res.form.formId
//                            },
//                            success: function(res2) {
//                                if (res2.success) {
//                                    Y.alert('提醒', res2.message, function() {
//                                        window.location.href = '/projectMg/afterwardsCheck/list.htm';
//                                    });
//                                } else {
//                                    Y.alert('提醒', res2.message, function() {
//                                        window.location.reload(true);
//                                    });
//                                }
//                            }
//                        });
                    	util.postAudit({
                            formId: res.form.formId,
                            _SYSNAME: 'AM'
                        }, function () {
                            window.location.href = '/projectMg/afterwardsCheck/list.htm';
                        })
                    } else {
                        Y.alert('提醒', res.message);
                    }
                } else {
                    Y.alert('提醒', res.message);
                }
            }
        });
    }

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    if (document.getElementById('fnEdit')) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'save',
            event: function() {
                var _loading = new util.loading();
                _loading.open();
                formSubmit();
                _loading.close();
            }
        },{
            name: '提交',
            alias: 'submit',
            event: function() {
                $addForm.validate();
                var _isPass = true,
                _isPassEq;

                $('.fnInput').each(function(index, el) {
                    if (!!!el.value.replace(/\s/g, '')) {
                        _isPass = false;
                        _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                    }
                });

                if (!_isPass) {

                    Y.alert('提醒', '请把表单填写完整', function() {
                        $('.fnInput').eq(_isPassEq).focus();
                    });
                    return;
                }

                formSubmit('SUBMIT');
            }
        }]);
    }
    publicOPN.init().doRender();
    //------ 侧边栏 end


    // 评估公司选定
    $('.selectNav a').click(function(){
        var self = $(this),
            selfIndex = self.index(),
            selectBox = self.parents().parents().parents('.companySelect');
        console.log(selfIndex);
        self.addClass('active').siblings().removeClass('active');
        selectBox.find('.company').eq(selfIndex).removeClass('fn-hide').siblings('.company').addClass('fn-hide');
    })

})