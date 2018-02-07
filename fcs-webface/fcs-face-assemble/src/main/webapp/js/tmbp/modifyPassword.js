define(function (require, exports, module) {
    //修改密码
    require('validate');
    require('Y-msg');
    var util = require('util');

    var $form = $('#form');

    $.validator.addMethod('passwordRule', function (value, element, param) {

        // 密码要求 字母、数字、符号 二选一

        var regLetter = /[a-zA-Z]/g
        var regNum = /\d/g
        var regSymbol = /(\.|_|\~|\!|\@|\#|\$|\%|\^|\&|\*)/g

        var rank = 0;

        if (regLetter.test(value)) {
            rank += 1
            console.log('regLetter')
        }
        if (regNum.test(value)) {
            rank += 1
        }
        if (regSymbol.test(value)) {
            rank += 1
        }

        return (rank >= 2) ? true : false

    }, '请输入字母、符号、数字的密码（两个及其以上的类型组合）');

    $form.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            element.after(error);
        },
        submitHandler: function (form) {
            util.ajax({
                url: '/userHome/modifyPasswordSubmit.htm',
                data: $form.serialize(),
                success: function (res) {
                    Y.alert('提示', res.message, function () {
                        if (res.success) {
                            //修改成功，退出
                            window.top.document.getElementById('logout').click();
                        }
                    });
                }
            });
        },
        rules: {
            oldpassword: {
                required: true
            },
            newpassword: {
                required: true,
                rangelength: [8, 16],
                passwordRule: true
            },
            relpassword: {
                required: true,
                equalTo: '#newPWD'
            }
        },
        messages: {
            oldpassword: {
                required: '请输入当前的密码'
            },
            newpassword: {
                required: '请输入新密码',
                rangelength: '请输入长度在 {0} 到 {1} 之间的密码'
            },
            relpassword: {
                required: '请二次确认新密码',
                equalTo: '请输入正确的新密码'
            }
        }
    });
});