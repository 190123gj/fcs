
define(function(require, exports, module) {

    var util = require('util');
    //上传
    require('zyw/upAttachModify');


    var $addForm = $('#addForm');



    function doSubmit(isSubmit) {

        document.getElementById('checkStatus').value = isSubmit ? '1' : '0';


        util.resetName();

        util.ajax({
            url: $addForm.attr('action'),
            data: $addForm.serialize(),
            success: function(res) {

                if (res.success) {

                    if (!isSubmit) {
                        Y.alert('提示', '操作成功', function() {
                            window.location.href = '/projectMg/contract/invalidList.htm';
                        });
                    } else {
                        util.postAudit({
                            formId: res.formId
                        }, function () {
                            window.location.href = '/projectMg/contract/invalidList.htm';
                        })

                    }

                } else {
                    Y.alert('操作失败', res.message);
                }

            }
        });

    }
    require('validate');
    $('#addForm').validate({
        rules:{
            invalidReason: "required",
            remark: "required",
            withdrawAll: "required"
        },
        messages: {
            invalidReason: "必填",
            remark: "必填",
            withdrawAll: "必选"
        },
        ignore: ".ignore",
        errorPlacement : function(error, element) {
            if (element.attr('name') == 'withdrawAll')
                element.closest('div').find('.thisError').append(error)
            else
                element.after(error);
        },
        submitHandler: function(form) {
            doSubmit(true);
        }
    })
    // $addForm.on('click', '#doSubmit', function() {
    //     //doSubmit(true);
    //
    //     console.log('sss')
    // });
    $('.radioYes').on('click',function(){
        $('#ui-textarea-mk').hide()
        $('#ui-textarea-bz').addClass('ignore')
    })
    $('.radioNo').on('click',function(){
        $('#ui-textarea-mk').show()
        $('#ui-textarea-bz').removeClass('ignore')
    })

    //侧边栏
    var publicOPN = require('zyw/publicOPN'),
        _publicOPN = new publicOPN(),
        projectCode = document.getElementById('projectCode');

    if (projectCode && projectCode.value) {

        _publicOPN.addOPN([ {
            name: '暂存',
            alias: 'save',
            event: function() {
                doSubmit(false);
            }
        }])

    }
    _publicOPN.init().doRender();




});