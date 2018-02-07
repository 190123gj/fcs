define(function(require, exports, module) {


    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //输入限制
    //require('input.limit');
    //异步提交
    require('form')();
    //表单验证
    require('validate');
    require('validate.extend');
    //验证方法集
    require('zyw/project/bfcg.itn.addValidataCommon');
    //字数提示
    require('zyw/hintFont');
    //验证共用
    var _form = $('#form'),
        _allWhetherNull = 'WU',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, false, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('wu,token,preUrl,nextUrl', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRules2 = _form.requiredRulesSharp('benefitReview,background,approval,progress,costFundraising,overview,marketOutlook', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过1000字'
                },
                maxlength: 1000

            };
        }),
        requiredRulesPercentTwoDigits = _form.requiredRulesSharp('itemPercent,fundPercent', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    isPercentTwoDigits: '请输入正确0.01-100之间的数字'
                },
                isPercentTwoDigits: true

            };
        }),
        requiredRulesMoney = _form.requiredRulesSharp('itemAmountStr,fundAmountStr,totalCostStr', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                },
                isMoney: true

            };
        }),
        rulesAllBefore = { //所有格式验证的基

        },
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRules1, requiredRules2, rulesAllBefore, requiredRulesPercentTwoDigits, requiredRulesMoney);


    var toIndex, _formSerializeMd5New, fm5, checkStatus;
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
                success: function(res){
                    showResult(res, hintPopup);
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
    $('#submit').click(function(event) {
        fm5WhetherChange();
        $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        checkStatus = 1;
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

    (new(require('zyw/publicOPN'))()).addOPN([{
        name: '暂存',
        alias: 'temporarStorage',
        event: function() {
            fm5WhetherChange();
            rulesAllFalse();
            toIndex = -1;
            _form.submit();
        }
    }, {
        name: '提交',
        alias: 'fulfilSubmit',
        event: function() {
            fm5WhetherChange();
            $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
            checkStatus = 1;
            toIndex = -2;
            _form.submit();
        }
    }]).init().doRender();

    //上传
    require('Y-htmluploadify');
    $('.fnUpFile').click(function() {
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/investigation/uploadExcel.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function(a, b) {},
            onUploadSuccess: function($this, data, resfile) {
                    var _data = JSON.parse(data).datas,
                        _obj = $('#test');
                    for (var i = 1; i < _data.length; i++) {
                        for (var j = 0; j < _data[i].length; j++) {
                            _obj.find('tr').eq(i - 1).find('td').eq(j).find('input').val(_data[i][j])
                        };
                    };
                }
                //renderTo: 'body'
        });
        // htmlupload.show();
    });

});