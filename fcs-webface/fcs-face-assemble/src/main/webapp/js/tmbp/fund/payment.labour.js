define(function (require, exports, module) {
    // 资金管理 > 费用支付申请单新增

    require('./payment.common')

    require('Y-msg');
    require('validate');

    var getList = require('zyw/getList');

    var util = require('util'),
        $form = $('#form')

    var validObj = {
        rules: {},
        messages: {}
    };

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    //------ 侧边栏 end

    util.resetName();

    $('.fnInput:visible').each(function (index, el) {

        util.ObjAddkey(validObj.rules, el.name, {
            required: true
        });
        util.ObjAddkey(validObj.messages, el.name, {
            required: '必填'
        });

        if (el.className.indexOf('fnMakeMoney') >= 0) {

            util.ObjAddkey(validObj.rules, el.name, {
                min: 0.01
            });
            util.ObjAddkey(validObj.messages, el.name, {
                min: '必填大于0'
            });

        }

    })

    $form.validate($.extend({}, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {

            if (element.hasClass('fnErrorAfter')) {

                element.after(error)

            } else {

                element.parent().append(error)

            }

        }
    }, validObj))

    $form.on('click', '.doSubmit', function () {

        var _isSave = util.hasClass(this, 'save')

        document.getElementById('status').value = _isSave ? 'DRAFT' : 'SUBMIT'

        // 避免误触发
        setTimeout(function () {

            if (!_isSave && !$form.valid()) {
                Y.alert('提示', '表单未填写完整', function () {
                    $('input.error-tip').eq(0).focus()
                })
                return
            }

            // 还原原来的 name
            $('[diyname="self"] [name]').each(function (index, el) {

                var _name = el.name,
                    _nameArr = _name.split('.')

                _name = _nameArr[_nameArr.length - 1]

                el.name = _name

            });

            doSave(_isSave)

        }, 500)

    }).on('change', '.expenseType', function () {

        $(this).parents('tr').find('.fnListSearchOrgName').trigger('change')

    })

    setTimeout(function () {
        $('.expenseType').each(function (index, el) {
            $(this).trigger('change')
        });
    }, 0)

    function doSave(isSave) {

        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {

                if (res.success) {

                    // 审核走流程
                    if (isSave) {
                        Y.alert('提示', '操作成功', function () {
                            window.location.href = '/fundMg/labourCapital/ealist.htm';
                        });
                    } else {

                        util.ajax({
                            url: '/projectMg/form/submit.htm',
                            data: {
                                formId: res.form.formId,
                                _SYSNAME: 'FM'
                            },
                            success: function (xhr) {

                                Y.alert('提示', xhr.success ? (xhr.message + '<br>单据号：' + res.billNo) : xhr.message, function () {
                                    window.location.href = '/fundMg/labourCapital/ealist.htm';
                                })

                            }
                        })

                    }

                } else {
                    Y.alert('操作失败', res.message);
                    // 更新 预算余额
                    $('.fnListSearchOrgName').trigger('change');
                }

            }
        });
    }


    var ALL_COST_TYPE = [] // 所有可选的费用类型
    var LABOR_COST_TYPE = '工资#养老保险#失业保险#医疗保险#工伤保险#生育保险#住房公积金#补充医疗保险#企业年金#补充养老保险#外派劳务费#其他（劳资）'.split('#') // 劳资类
    var TAX_COST_TYPE = '房产税#车船使用税#土地使用税#印花税#其他（税金）'.split('#') // 税金类
    var OHTER_COST_TYPE = '其他'.split('#') // 其他类

    // 获取所有费用类型
    $('#fnAllCostType').find('option').each(function (index, el) {
        ALL_COST_TYPE.push({
            value: el.value,
            text: el.innerHTML.replace(/\s/g, '')
        })
    })

    // 初始化
    // if ((document.getElementById('formId') || {}).value) {
    //     setCostType($('.expenseType').eq(0).find('option:selected').text())
    // }

    // 限制费用类型
    function setCostType(type) {

        var _arr = []

        if (!!type) {

            $.each([LABOR_COST_TYPE, TAX_COST_TYPE, OHTER_COST_TYPE], function (index, list) {

                if ($.inArray(type, list) >= 0) {

                    _arr = filterCostType(list)
                    return false

                }

            })

        } else {

            _arr = ALL_COST_TYPE

        }

        $('.expenseType').each(function (index, el) {

            var $select = $(this)

            $select.html(creatCostType(_arr, $select.val())).trigger('change')

        })

    }

    function filterCostType(canArr) {

        var _arr = []

        $.each(ALL_COST_TYPE, function (index, obj) {

            if ($.inArray(obj.text, canArr) >= 0) {

                _arr.push(obj)

            }

        })

        return _arr

    }

    function creatCostType(list, select) {

        var _html = ''

        $.each(list, function (index, obj) {

            _html += '<option value="' + obj.value + '" ' + ((obj.value == select) ? 'selected' : '') + '>' + obj.text + '</option>'

        })

        return _html

    }

});