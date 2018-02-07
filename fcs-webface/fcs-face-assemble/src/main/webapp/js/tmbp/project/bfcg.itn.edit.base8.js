define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //必填集合
    require('zyw/requiredRules');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //上传
    require('zyw/upAttachModify');
    // 保存并返回
    var ONLY_VALUE = 0
    $('#saveSubmit').click(function () {
        ONLY_VALUE = 1;
    })
    //验证共用
    var _form = $('#form'),
        _allWhetherNull = 'riskAnalysis,conclusion',
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        rulesAllBefore = { //所有格式验证的基
            riskAnalysis: {
                //maxlength: 10000,
                required: true,
                messages: {
                    required: '必填',
                    //maxlength: '已超出10000字'
                }
            },
            conclusion: {
                // maxlength: 10000,
                required: true,
                messages: {
                    required: '必填',
                    //maxlength: '已超出10000字'
                }
            }
        },
        rulesAll = rulesAllBefore,
        toIndex, _formSerializeMd5New, fm5, checkStatus;
    _form.validate({
        errorClass: 'error-tip',
        errorElement: 'p',
        errorPlacement: function(error, element) {
            element.after(error);
        },
        onkeyup: false,
        ignore: '.cancel',
        submitHandler: function(form) {
            $.fn.orderName();
            $(form).ajaxSubmit({
                type: 'post',
                dataType: 'json',
                data: {
                    toIndex: toIndex,
                    fm5: fm5,
                    checkStatus: checkStatus
                },
                success: function(res) {
                    if(ONLY_VALUE === 1){
                        showResult(res, hintPopup(res.message,$("#backUrl").attr('data-url')));
                    } else {
                        showResult(res, hintPopup);
                    }
                },
                error: function(a, b, c) {
                    console.log(a)
                    console.log(b)
                    console.log(c)
                }
            });
        }
    });
    //验证方法集初始化
    $('.fnAddLine').addValidataCommon(rulesAll, true)
        .initAllOrderValidata()
        .groupAddValidata();

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
        checkStatus = _form.allWhetherNull(_allWhetherNull, false); //是否填写完整
    }

    var _initSerializeMd5 = formSerializeMd5(_form); //初始页面数据

    //提交
    $('#submits').click(function(event) {
        fm5WhetherChange();
        $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        checkStatus = 1;
        toIndex = -2;
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

    var cp = $('#checkPoint').val()
    var ar = []

    if (cp == '' || cp == undefined) {
        var ar = [{
            name: '暂存',
            alias: 'temporarStorage',
            event: function() {
                fm5WhetherChange();
                rulesAllFalse();
                toIndex = -1;
                _form.submit();
            }},{
            name: '提交',
            alias: 'fulfilSubmit',
            event: function() {
                fm5WhetherChange();
                $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
                checkStatus = 1;
                toIndex = -2;
                _form.submit();
            }}]
        $('#tempSave,#saveSubmit').hide();
    } else {
        $('#tempSave,#saveSubmit').show()
        $('#tempSave,#saveSubmit').click(function () {
            fm5WhetherChange();
            rulesAllFalse();
            toIndex = -1;
            _form.submit();
        })
    }
    (new(require('zyw/publicOPN'))()).addOPN(ar).init().doRender();


    // (new(require('zyw/publicOPN'))()).addOPN([{
    //     name: '暂存',
    //     alias: 'temporarStorage',
    //     event: function() {
    //         fm5WhetherChange();
    //         rulesAllFalse();
    //         toIndex = -1;
    //         _form.submit();
    //     }
    // }, {
    //     name: '提交',
    //     alias: 'fulfilSubmit',
    //     event: function() {
    //         fm5WhetherChange();
    //         $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
    //         checkStatus = 1;
    //         toIndex = -2;
    //         _form.submit();
    //     }
    // }]).init().doRender();
    // $('#tempSave').click(function () {
    //     fm5WhetherChange();
    //     rulesAllFalse();
    //     toIndex = -1;
    //     _form.submit();
    // })
    //是否有过提交动作
    var submitedFun = require('zyw/submited'),
        _submited = $('[name="submited"]').val(),
        _currentBoll = submitedFun(_submited);
    if (_currentBoll == '0' && _submited) {
        //$.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        _form.submit();
    }

});