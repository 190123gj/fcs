define(function (require, exports, module) {
    //项目管理>授信前管理> 立项申请
    require('Y-msg');
    require('input.limit');
    var util = require('util');

    var getList = require('zyw/getList');
    var template = require('arttemplate');

    var pako = require('pako')

    var projectList = new getList();

    if (!$("#projectCode").val()) {
        $("#timeLimit_text").val("");
    }

    projectList.init({
        title: '项目列表',
        ajaxUrl: '/baseDataLoad/queryProjects.json?fromRiskCouncil=YES',
        btn: '#fnToChooseProjectCode',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item">{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\' :item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" customerid="{{item.customerId}}" customername="{{item.customerName}}"  projectCode="{{item.projectCode}}"  projectName="{{item.projectName}}"  busiManagerId="{{item.busiManagerId}}" deptCode="{{item.deptCode}}"  busiManagerName="{{item.busiManagerName}}"    href="javascript:void(0);">选择</a></td>',
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
                '<th width="100">授信金额</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            $('#projectName').val($a.attr('projectName')).trigger('blur');
            $('#projectCode').val($a.attr('projectCode')).trigger('blur');
            $('#busiManagerId').val($a.attr('busiManagerId'));
            $('#busiManagerName').val($a.attr('busiManagerName'));
            $('#customerId').val($a.attr('customerid'));
            $('#customerName').val($a.attr('customername'));


            util.ajax({
                url: "/projectMg/projectRiskReport/queryProjectInfo.json",
                data: {
                    userId: $('#customerId').val(),
                    projectCode: $('#projectCode').val(),
                    reportType: $_GLOBAL.reportType
                },
                success: function (res) {

                    if (res.success) {
                        $("#enterpriseType").val(res.data.enterpriseType);
                        $("#riskManagerId").val(res.data.riskManagerId);
                        $("#riskManagerName").val(res.data.riskManagerName);
                        $("#timeLimit").val(res.data.timeLimit);
                        $("#timeUnit").val(res.data.timeUnit);
                        if (res.data.timeUnitText) {
                            $("#timeLimit_text").val(res.data.timeLimit + "(" + res.data.timeUnitText + ")");
                        }
                        if (res.data.projectRiskReportInfo) {
                            $("#reprot1").val(res.data.projectRiskReportInfo.reprot1);
                            $("#reprot2").val(res.data.projectRiskReportInfo.reprot2);
                            $("#reprot3").val(res.data.projectRiskReportInfo.reprot3);
                            $("#reprot4").val(res.data.projectRiskReportInfo.reprot4);
                            $("#reprot5").val(res.data.projectRiskReportInfo.reprot5);

                        }

                        $("#amount").val(res.data.amount);
                        $("#loanBank").val(res.data.loanBank);
                        $("#guaranteeFee").val(res.data.guaranteeFee);
                        var list = $.parseJSON(res.data.list);
                        var obj = {
                            list: list,
                            isEmpty: true
                        };
                        $('.compTr').remove();
                        if (!!list) obj.isEmpty = false;

                        var str = template('DAICHANG', obj);
                        $('.daichangmm').after(str); //缓存数，以便下次调用
                    }
                }
            });

        }
    });


    var $form = $("#form");
    $form.on("click", "#fnToClearCustomer", function () {
            $("#customerId").val("");
            $("#customerName").val("");
            $('#projectName').val("");
            $('#projectCode').val("");
        }).on("click", "#fnToClearProjectCode", function () {
            $('#projectName').val("");
            $('#projectCode').val("");
        })
        //------ 公共 验证规则 start
    require('validate');
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            // element.parent().append(error);
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else {
                element.parent().append(error);
            }
        },
        submitHandler: function (form) {},
        rules: {
            projectCode: {
                required: true
            }
        },
        messages: {
            projectCode: {
                required: '必填'
            }
        }
    };
    $form.validate(validateRules);

    function dynamAddRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput').not(':hidden');
        $nameList.each(function (i, e) {
            var _this = $(this);
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })
    };

    function dynamicRemoveRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput').not(':hidden');
        $nameList.each(function (i, e) {
            $(this).rules("remove");
        })
    };

    dynamAddRules($('.tableList'));

    //提交
    $('.ui-btn-submit').on('click', function () {

        if (this.className.indexOf('print') >= 0) {

            var $div = $('#apply').removeClass('border')

            $div.find('#optBtn, .m-blank').remove()

            util.print($div.html())
            return
        }

        if (this.className.indexOf('exprot') >= 0) {

            var $ex = $('#apply').removeClass('border')

            $ex.find('#optBtn, .m-blank').remove()

            util.css2style($ex)

            var __body = document.getElementsByTagName('body')[0]

            __body.innerHTML = ''
            __body.appendChild($ex[0])

            $('head link, script, .ui-tool, #industryBox').remove()

            var deflate = pako.gzip(document.documentElement.outerHTML, {
                to: 'string'
            })

            $.ajax({
                type: 'post',
                url: '/baseDataLoad/createDoc.json',
                data: {
                    formId: util.getParam('reportId'),
                    type: $ex.attr('thistype'),
                    decode: 'yes',
                    htmlData: encodeURIComponent(deflate)
                },
                success: function (res) {
                    if (res.success) {
                        window.location.href = res.url;
                        setTimeout(function () {
                            window.location.reload(true)
                        }, 1000)
                    } else {
                        alert(res.message)
                        window.location.reload()
                    }
                }
            })

            return
        }

        var $fnInput = $('.fnInput');
        var _this = $(this);
        var _thisId = _this.attr('id');
        if (_this.hasClass('returnBack')) return;
        if (_thisId == "APPROVAL") {
            $("#reportStatus").val("APPROVAL");
            dynamAddRules($('.tableList'))
        } else {
            $("#reportStatus").val("DRAFT");
            dynamicRemoveRules($('.tableList'))
        }
        if (!$('#form').valid()) return;
        var url = "/projectMg/projectRiskReport/list.htm"
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _isPass = true,
            _isPassEq;
        if (_thisId == 'APPROVAL') {
            $fnInput.each(function (index, el) {

                if (!!!el.value.replace(/\s/g, '')) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                }

            });
        } else {
            if (!!!$('[name*=projectCode]').val().replace(/\s/g, '')) { //暂存只检测是否选择项目
                Y.alert('提示', '暂存请选一个项目！', function () {
                    $(this).focus();
                });
                _this.removeClass('ing');
                return;
            }
        }

        if (!_isPass) {
            Y.alert('提示', '请填写完整表单', function () {
                $fnInput.eq(_isPassEq).focus();
            });
            _this.removeClass('ing');
            return;
        }

        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', '已提交', function () {
                        util.direct(url);

                    });
                } else {
                    Y.alert('提示', res.message);
                    _this.removeClass('ing');
                }
            }
        });
    });
});