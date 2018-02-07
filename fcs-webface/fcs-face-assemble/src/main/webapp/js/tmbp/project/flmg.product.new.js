define(function (require, exports, module) {
    //项目管理 > 理财项目管理 > 立项申请
    require('input.limit');
    require('Y-msg');
    require('validate');

    var util = require('util');

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
                url: $form.attr('action'),
                data: $form.serializeJCK(),
                success: function (res) {
                    if (res.success) {
                        Y.alert('提示', '操作成功', function () {
                            window.location.href = '/projectMg/basicmaintain/financialProduct/list.htm';
                        })
                    } else {
                        Y.alert('操作失败', res.message);
                    }
                }
            });
        }
    }))

    $('[name="timeLimit"]').rules('add', {
        min: 0.01,
        messages: {
            min: '必填大于0'
        }
    })

    var $fnRateRangeStart = $('#fnRateRangeStart')
    var $fnRateRangeEnd = $('#fnRateRangeEnd')

    $fnRateRangeStart.on('blur', function () {

        var $this = $(this)

        setTimeout(function () {

            if ($this.val() > 100) {
                $this.val(100)
            }

            if ($this.val() < 0) {
                $this.val(0)
            }

            $fnRateRangeEnd.rules('remove', 'min')

            if (+$this.val()) {

                $fnRateRangeEnd.rules('add', {
                    min: +$this.val(),
                    messages: {
                        min: '最小值为{0}'
                    }
                })

            }

            if ($fnRateRangeEnd.val()) {
                $fnRateRangeEnd.valid()
            }

        }, 50)

    }).trigger('blur')

    $fnRateRangeEnd.on('blur', function () {

        var $this = $(this)

        setTimeout(function () {

            if ($this.val() > 100) {
                $this.val(100)
            }

            if ($this.val() < 0) {
                $this.val(0)
            }

            $fnRateRangeStart.rules('remove', 'max')

            if (+$this.val()) {

                $fnRateRangeStart.rules('add', {
                    max: +$this.val(),
                    messages: {
                        max: '最大值为{0}'
                    }
                })

            }


            if ($fnRateRangeStart.val()) {
                $fnRateRangeStart.valid()
            }

        }, 50)

    }).trigger('blur')

    var $fnProductName = $('#fnProductName'),
        OLD_PRODUCT_NAME = $fnProductName.val()

    if (OLD_PRODUCT_NAME) {

        $fnProductName.on('change', function () {

            setProductNameOnly(this.value != OLD_PRODUCT_NAME)

        })

    } else {
        setProductNameOnly(true)
    }

    function setProductNameOnly(isSet) {

        if (isSet) {

            $fnProductName.rules('add', {
                remote: {
                    url: '/baseDataLoad/checkFinancialProductName.json?_time=' + (new Date()).getTime(), //后台处理程序
                    type: 'post', //数据发送方式
                    dataType: 'json', //接受数据格式   
                    data: { //要传递的数据
                        productName: function () {
                            return $('input[name=productName]').val();
                        }
                    }
                },
                messages: {
                    remote: '名称已存在'
                }
            });

        } else {
            $fnProductName.rules('remove', 'remote');
            $fnProductName.valid();
        }

    }


    var $body = $('body');

    //------ 计算产品分类 start
    var ajaxProductTermType = {
        timeLimit: $body.find('input[name=timeLimit]'),
        timeUnit: $body.find('select[name=timeUnit]')
    };

    var ajaxProductTermTypeing = false; //是否正在ajax请求计算结果

    $body.on('blur', '[name=timeLimit],[name=timeUnit]', function () {

        if (ajaxProductTermTypeing) {
            return;
        }

        var _isPass = true,
            _data = {};

        //行业编码必要
        if (!!!ajaxProductTermType.timeLimit.val() || !!!ajaxProductTermType.timeUnit.val()) {
            _isPass = false;
            return;
        }

        for (var k in ajaxProductTermType) {
            _data[k] = ajaxProductTermType[k].val();

        }

        if (_isPass) {

            ajaxProductTermTypeing = true;

            $.ajax({
                url: '/projectMg/basicmaintain/financialProduct/calculateProductTermType.htm',
                type: 'POST',
                dataType: 'json',
                data: _data,
                success: function (res) {
                    if (res.success) {
                        $("#termType").val(res.data.code);
                        $("#termTypeName").html(res.data.message);
                    } else {
                        Y.alert('提示', res.message);
                    }
                },
                complete: function () {
                    ajaxProductTermTypeing = false;
                }
            });

        }
    });
    //------ 计算产品分类  end    
});