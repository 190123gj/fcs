define(function (require, exports, module) {
    //表单验证
    require('validate');
    require('validate.extend');

    //md5加密
    var md5 = require('md5');
    //通用方法
    var util = require('util');
    require('Y-window');
    //字数提示
    require('zyw/hintFont');
    require('input.limit');

    // 表单验证

    var $addForm = $('#form'),
        $fnInput = $('.fnInput'),
        $fntextarea = $('textarea.fnInput'),
        $fnradio = $('input.radio'),
        formRules = {
            rules: {},
            messages: {}
        };

    $fntextarea.each(function () {
        var self = $(this);
        if (self.hasClass('ignoreMaxlength')) {
            self.removeAttr('maxlength');
        }
    })

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

    $fntextarea.each(function (index, el) {
        var name = $(this).attr('name');
        if (!name) {
            return true;
        }
        formRules.rules[name] = {
            required: true,
            maxlength: 1000
        };
        formRules.messages[name] = {
            required: '必填',
            maxlength: '已超出最大长度1000字'
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

    //------新增收费 end

    function formSubmit(type) {
        document.getElementById('state').value = type || 'SAVE';
        var checkStatus = document.getElementById('checkStatus').value = type ? 1 : 0,
            formId = $('#formId').val();
        util.resetName();

        //保存数据
        util.ajax({
            url: '/projectMg/afterwardsCheck/saveProject.json',
            data: $addForm.serializeJCK(),
            success: function (res) {
                if (res.success) {
                    if (checkStatus == '1') {
                        Y.alert('提醒', '已提交~', function () {
                            window.location.href = '/projectMg/afterwardsCheck/editBaseInfo.htm?formId=' + formId + '&write=' + checkStatus;
                        });
                    } else {
                        Y.alert('提醒', '已保存~', function () {
                            window.location.href = '/projectMg/afterwardsCheck/editBaseInfo.htm?formId=' + formId + '&write=' + checkStatus;
                        });
                    }
                } else {
                    Y.alert('提醒', res.message);
                }
            }
        });
    }

    $('#fnBack').click(function () {
        var checkStatus = $('#checkStatus').val(),
            formId = $('#formId').val();
        window.location.href = '/projectMg/afterwardsCheck/editBaseInfo.htm?formId=' + formId + '&write=' + checkStatus;
    })


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

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    if (document.getElementById('fnEdit')) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'save',
            event: function () {
                var _loading = new util.loading();
                _loading.open();
                formSubmit();
                _loading.close();
            }
        }]);
    }
    publicOPN.init().doRender();
    //------ 侧边栏 end

})