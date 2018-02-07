define(function(require, exports, module) {
    //异步提交
    require('form')();
    //表单验证
    require('validate');
    require('validate.extend');
    //验证方法集
    require('zyw/project/bfcg.itn.addValidataCommon');
    //字数提示
    require('zyw/hintFont');
    require('input.limit');
    var util = require('util'),
        loading = new util.loading();

    function ValidataCommon(rulesAll, _form, _allWhetherNull, _boll, _success, counterGuaranteeCommon, toLead, auditInfos) {
        var toIndex, _formSerializeMd5New, fm5, checkStatus;
        _form.validate({
            errorClass: 'error-tip',
            errorElement: 'p',
            errorPlacement: function(error, element) {
                element.after(error);

                var globalHint = $('#globalHint');
                var errorHtml = '<span class="fn-lh26">' + element.attr('name') + ' ' + error.text() + '</span>'

                if (!globalHint.length) {
                    _form.after('<div id="globalHint" style="font-size:12px"><p class="fn-hide"></p><div class="fn-lh26" style="color: #f00">数据录入不完整(请认真检查)</div></div>');
                }

                // if (!globalHint.html() || globalHint.html().length < 350) {
                globalHint.find('p').append(errorHtml);
                // }

                if (console && console.log) {
                    console.log(element.attr('name') + ' ' + error.text());
                }

            },
            onkeyup: false,
            ignore: '.cancel',
            submitHandler: function(form) {
                $.fn.orderName();
                loading.open();

                var fnMakeMoneyfnMakeMicrometer = $('.fnMakeMoney.fnMakeMicrometer');

                fnMakeMoneyfnMakeMicrometer.each(function(index, el) {
                    var $el = $(el);
                    $el.val($el.val().replace(/\,/g, ''))
                });

                $(form).ajaxSubmit({
                    type: 'post',
                    dataType: 'json',
                    data: {
                        toIndex: toIndex,
                        fm5: fm5,
                        checkStatus: checkStatus
                    },
                    success: _success,
                    error: function(a, b, c) {
                        //loading.close();
                        alert('请求失败')
                    }
                });
            }
        });

        //验证方法集初始化
        $('.fnAddLine').addValidataCommon(rulesAll, true)
            .initAllOrderValidata()
            .assignGroupAddValidataUpUp();



        var md5 = require('md5'); //md5加密
        function formSerializeMd5(_form) {
            var _formSerialize = _form.serialize();
            return md5(_formSerialize);
        }

        function fm5WhetherChange() {
            var _newSerializeMd5 = formSerializeMd5(_form);
            fm5 = (_newSerializeMd5 != _initSerializeMd5) ? 1 : 0; //数据是否有改变
        }

        function rulesAllFalse() { //否必填共用
            $.fn.whetherMust(rulesAll, false).allAddValidata(rulesAll); //否必填
            checkStatus = _form.allWhetherNull(_allWhetherNull, _boll); //是否填写完整
        }

        var _initSerializeMd5 = formSerializeMd5(_form); //初始页面数据

        //提交
        $('#submits').click(function(event) {
            if (toLead && !toLead()) {
                var hintPopup = require('zyw/hintPopup');
                hintPopup('请导入数据');
                return false;
            }
            if (auditInfos && !auditInfos()) {
                var hintPopup = require('zyw/hintPopup');
                hintPopup('存在审计信息，现金流量表不能为空');
                return false;
            }
            fm5WhetherChange();
            $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填

            // 不限制字数
            _form.find('.notlimit').each(function(index, el) {
                var $this = $(this)
                $this.rules('remove', 'maxlength')
                $this.rules('remove', 'required')
            });

            checkStatus = 1;
            toIndex = parseInt($('#step').attr('toIndex')) + 1;
            _form.submit();
        });

        $('#step li').click(function(event) {
            toIndex = $(this).index(); //跳到哪
            var _activeIindex = $('#step li.active').index();
            if (_activeIindex == toIndex) return false; //原页面上不请求
            fm5WhetherChange();
            rulesAllFalse();
            _form.submit();
        });

        $('body').on('click', '#newfn a', function () {
            if($(this).hasClass('active')) {
                return
            }
            fm5WhetherChange();
            rulesAllFalse();
            _form.submit();
        })



        var cp=$('#checkPoint').val()
        var ar= []
        if(cp=='' || cp==undefined){
            ar=[{
                name: '暂存',
                alias: 'temporarStorage',
                event: function() {
                    fm5WhetherChange();
                    rulesAllFalse();
                    toIndex = -1;
                    _form.submit();
                }}, {
                name: $('.apply-step .item.active').index() == 0 ? '获取最新客户资料' : '',
                alias: 'temporarStorage2b',
                event: function() {
                    fm5WhetherChange();
                    rulesAllFalse();
                    toIndex = -1;
                    $('#formId').attr('forCustomer', 1);
                    _form.submit();
                }
            },{
                name: '提交',
                alias: 'fulfilSubmit',
                event: function() {
                    if (toLead && !toLead()) {
                        var hintPopup = require('zyw/hintPopup');
                        hintPopup('请导入数据');
                        return false;
                    }
                    if (auditInfos && !auditInfos()) {
                        var hintPopup = require('zyw/hintPopup');
                        hintPopup('存在审计信息，现金流量表不能为空');
                        return false;
                    }
                    fm5WhetherChange();
                    $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
                    checkStatus = 1;
                    toIndex = -2;
                    _form.submit();
                }
            }]
            $('#tempSave,#saveSubmit').hide();
        } else {

            $('#tempSave,#saveSubmit').show()
            $('#tempSave').click(function () {
                fm5WhetherChange();
                rulesAllFalse();
                toIndex = -1;
                _form.submit();
            })
            $('#saveSubmit').click(function () {
                fm5WhetherChange();
                rulesAllFalse();
                toIndex = -1;
                _form.submit();
            })
        }
        (new(require('zyw/publicOPN'))()).addOPN(ar).init().doRender();

        //反担保共用
        if (counterGuaranteeCommon) counterGuaranteeCommon();
        //是否有过提交动作
        var submitedFun = require('zyw/submited'),
            _submited = $('[name="submited"]').val(),
            _currentBoll = submitedFun(_submited);

        if (_currentBoll == '0' && _submited) {
            _form.submit();
        }

    }


    //是否还存在错误字段
    var removeGlobal = setInterval(function () {
        var len = $('input.error-tip,textarea.error-tip').length;
        //如果没有错误字段
        if(len==0){
            $('#globalHint').hide();
        }else{
            $('#globalHint').show();
        }
    },100);


    module.exports = ValidataCommon;
})