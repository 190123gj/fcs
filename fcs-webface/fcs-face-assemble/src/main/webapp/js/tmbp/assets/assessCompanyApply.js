/*
 * @Author: erYue
 * @Date:   2016-07-06 10:52:24
 * @Last Modified by:   erYue
 * @Last Modified time: 2016-07-06 14:20:36
 */
define(function (require, exports, module) {
    require('validate');
    require('validate.extend');
    require('zyw/upAttachModify');
    require('input.limit');
    // ------ 审核 start
    if (document.getElementById('auditForm')) {
        var auditProject = require('zyw/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    }
    // ------ 审核 end
    var getList = require('zyw/getList'),
        util = require('util'),
        chooseRegion = require('tmbp/chooseRegion'); //引入

    //调用是否委托清收
    $('#isClearBox').on('click', 'label', function () {
        var self = $(this);
        self.addClass('active').siblings().removeClass('active');
        isClear();
    });
    // 是否委托清收
    var isClear = function () {
        var clearContent = $('#clearContent'),
            firstLabel = $('#isClearBox label:first'),
            firstLast = $('#isClearBox label:last'),
            fnInput = clearContent.find('input,textarea');
        if (firstLabel.hasClass('active')) {

            clearContent.show();
            fnInput.each(function () {
                $(this).addClass('fnInput');
            })

            return true;

        } else if (firstLast.hasClass('active')) {

            clearContent.hide();
            fnInput.each(function () {
                $(this).removeClass('fnInput');
            })
            return true;

        } else {
            Y.alert('提醒', '请把表单填写完整');
            return false;
        }
    };

    // if( $('#fnWrite').val() == 'true'){
    //     isClear();
    // }

    // 选择项目
    var _getList = new getList();
    _getList.init({
        title: '清收项目',
        ajaxUrl: '/baseDataLoad/loanProjectForCompany.json',
        btn: '.chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
                '        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
                '        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectNumber="{{item.projectCode}}" projectName="{{item.projectName}}" customerName="{{item.customerName}}" customerId="{{item.customerId}}" customerType="{{item.customerType}}" customerAddress="{{item.customerAddress}}" countryName="{{item.countryName}}" countryCode="{{item.countryCode}}" provinceName="{{item.provinceName}}" provinceCode="{{item.provinceCode}}" cityName="{{item.cityName}}" cityCode="{{item.cityCode}}" countyName="{{item.countyName}}" countyCode="{{item.countyCode}}" projectName="{{item.projectName}}" href="javascript:void(0);">选择</a></td>', //跳转地址需要的一些参数
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
        callback: function ($a) {
            //跳转地址
            document.getElementById('customerName').value = $a.attr('customerName');
            document.getElementById('projectCode').value = $a.attr('projectNumber');
            document.getElementById('projectName').value = $a.attr('projectName');
            document.getElementById('customerId').value = $a.attr('customerId');
            document.getElementById('customerType').value = $a.attr('customerType');

            document.getElementById('customerAddr').value = $a.attr('customerAddress');
            document.getElementById('countryName').value = $a.attr('countryName');
            document.getElementById('countryCode').value = $a.attr('countryCode');
            document.getElementById('provinceName').value = $a.attr('provinceName');
            document.getElementById('provinceCode').value = $a.attr('provinceCode');

            document.getElementById('cityName').value = $a.attr('cityName');
            document.getElementById('cityCode').value = $a.attr('cityCode');
            document.getElementById('countyName').value = $a.attr('countyName');
            document.getElementById('countyCode').value = $a.attr('countyCode');
        }
    });

    $('body').on('click', '.choose', function () {
        addQy();
    })

    addQy();

    function addQy() {
        var alltext = '',
            countryName = $('#countryName').val(),
            provinceName = $('#provinceName').val(),
            cityName = $('#cityName').val(),
            countyName = $('#countyName').val();
        alltext = countryName + ' ' + provinceName + ' ' + cityName + ' ' + countyName;
        $('#alltextdiqu').val(alltext);
    }

    // 表单验证

    var $addForm = $('#form'),
        $fnInput = $('.fnInput'),
        $fnradio = $('input.radio'),
        formRules = {
            rules: {},
            messages: {}
        };

    $fnInput.each(function (index, el) {
        //必填规则
        var name = $(this).attr('name');
        if (!name) {
            return true;
        }
        formRules.rules[name] = {
            required: true
        };
        formRules.messages[name] = {
            required: '必填'
        };
    });


    $fnradio.each(function (index, el) {
        //必填规则
        var name = $(this).attr('name');
        if (!name) {
            return true;
        }
        formRules.rules[name] = {
            required: true
        };
        formRules.messages[name] = {
            required: '必填'
        };
    });

    $addForm.validate($.extend({}, formRules, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {

            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else if (element.hasClass('radio')) {
                element.parent().parent().append(error);
            } else {
                element.parent().append(error);
            }
        },
        submitHandler: function (form) {

            formSubmit('SUBMIT');

        }
    }));

    function formSubmit(type) {

        document.getElementById('state').value = type || 'SAVE';
        var checkStatus = document.getElementById('checkStatus').value = type ? 1 : 0;
        util.resetName();

        //保存数据
        util.ajax({
            url: '/assetMg/assessCompanyApply/save.htm',
            data: $addForm.serialize(),
            success: function (res) {
                if (res.success) {
                    if (checkStatus == '1') {
                        //提交表单
                        //                        util.ajax({
                        //                            url: '/projectMg/form/submit.htm',
                        //                            data: {
                        //                                formId: res.form.formId,
                        //                                _SYSNAME:'AM',
                        //                            },
                        //                            success: function(res2) {
                        //                                if (res2.success) {
                        //                                    Y.alert('提醒', res2.message, function() {
                        //                                        window.location.href = '/assetMg/assessCompanyApply/list.htm';
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
                            window.location.href = '/assetMg/assessCompanyApply/list.htm';
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


    $('#submit').click(function () {

        var _isPass = true,
            _isPassEq;

        $('.fnInput').each(function (index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {
            $('.fnInput').eq(_isPassEq).focus();
            return;
        }
    })

    $addForm.on('blur', '.fnInputDateS', function () {

        var _thisVal = this.value;
        $('#clearContent').find('.fnInputDateE').attr('onclick', 'laydate({min: "' + _thisVal + '"})');

    }).on('blur', '.fnInputDateE', function () {

        var _thisVal = this.value;
        $('.fnInputDateS').attr('onclick', 'laydate({max: "' + _thisVal + '"})');

    });


    // 选择资质
    $('.chooseZizhi').click(function () {
        var box = $('#Credentials');
        box.fadeIn(400);
        $('.close').click(function () {
            box.fadeOut(400);
        })

        $('#submit2').click(function () {

            if (!($("#Credentials input").is(':checked'))) {
                Y.alert('提醒', '土地、房产、资产请至少选择一项~');
                return false;
            } else {
                var chk_value = '',
                    qualityAssets = "",
                    qualityLand = $('#Credentials').find('tr:first() input:checked').val(),
                    qualityHouse = $("#Credentials tr:nth-child(2)").find('input:checked').val(),
                    assetsChecked = $('#Credentials').find('tr:last() input:checked');


                assetsChecked.each(function () {
                    qualityAssets += $(this).val() + ',';
                })

                qualityAssets = qualityAssets.substring(0, qualityAssets.length - 1);

                if (qualityLand == undefined) {
                    qualityLand = '';
                }

                if (qualityHouse == undefined) {
                    qualityHouse = '';
                }
                if (qualityLand != "") {
                    chk_value += '土地（' + qualityLand + '）;';
                }
                if (qualityHouse != "") {
                    chk_value += '房产（' + qualityHouse + '）;';
                }
                if (qualityAssets != "") {
                    chk_value += '资产（' + qualityAssets + '）;';
                }
                //chk_value += '土地（' + qualityLand + '）;房产（' + qualityHouse + '）;资产（' + qualityAssets +'）;';

                chk_value = chk_value.substring(0, chk_value.length - 1);

                $('#qualityLand').val(qualityLand);
                $('#qualityHouse').val(qualityHouse);
                $('#qualityAssets').val(qualityAssets);
                $('#alltext').val(chk_value);
                box.fadeOut(400);
            }

        })
    })

    // 编辑还原
    if ($('#fnWrite').val() == 'true') {
        ziZhi()
    }

    function ziZhi() {
        var ziZhi = $('.fnziZhi'),
            chk_value = '',
            qualityLand = $('#qualityLand').val(),
            qualityHouse = $('#qualityHouse').val(),
            qualityAssets = $('#qualityAssets').val(),
            Credentials = $('#Credentials'),
            allChecked = [];

        // qualityAssets = qualityAssets.substring(0,qualityAssets.length-1);
        ziZhi.find('input[type="hidden"]').each(function () {
            var self = $(this).val();
            allChecked.push(self);
        })
        if (qualityLand == '') {
            qualityLand = '';
        }

        if (qualityHouse == '') {
            qualityHouse = '';
        }
        if (qualityLand != "") {
            chk_value += '土地（' + qualityLand + '）;';
        }
        if (qualityHouse != "") {
            chk_value += '房产（' + qualityHouse + '）;';
        }
        if (qualityAssets != "") {
            chk_value += '资产（' + qualityAssets + '）;';
        }

        chk_value = chk_value.substring(0, chk_value.length - 1);

        $('#alltext').val(chk_value);
        var i,
            a = [],
            Credentials = $('#Credentials'),
            allLenth = allChecked.length;

        a = qualityAssets.split(',');

        Credentials.find('tr:first() input').each(function (index, el) {
            if ($(this).val() == allChecked[0]) {
                $(this).attr('checked', 'checked');
            }
        })

        Credentials.find('tr:nth-child(2) input').each(function (index, el) {
            if ($(this).val() == allChecked[1]) {
                $(this).attr('checked', 'checked');
            }
        });

        Credentials.find('tr:last() input').each(function (index, el) {

            if ($(this).val() == a[0] || $(this).val() == a[1] || $(this).val() == a[2] || $(this).val() == a[3]) {
                $(this).attr('checked', 'checked');
            }
        });

    }

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    //------ 侧边栏 end

    //---- 移除作废按钮
    $("#fnMOPN").find('ul li [trigger="endForm"]').parent().remove()

})