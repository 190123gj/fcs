define(function(require, exports, module) {
        //输入限制
    require('input.limit');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //异步提交
    require('form')();
    //表单验证
    require('validate');
    require('validate.extend');
    //必填集合
    require('zyw/requiredRules');
    //验证方法集
    require('zyw/project/bfcg.itn.addValidataCommon');
    //字数提示
    require('zyw/hintFont');

    var _form = $('#form'),
    _allWhetherNull ='MReviewId,schemeId,submited,preUrl,token,nextUrl,formId,projectCode,projectName,customerId,customerName,litigationId,canle,token',
    requiredRules = _form.requiredRulesSharp(_allWhetherNull,true,{},function(rules,name){
        rules[name] = {
            required:true,
            messages:{
                required:'必填'
            }

        };
    }),
    requiredRulesIsPercentTwoDigits = _form.requiredRulesSharp('guaranteeRate,informationFee',false,{},function(rules,name){
        rules[name] = {
            isPercentTwoDigits: true,
            messages:{
                isPercentTwoDigits :'请输入正确0.01-100之间的数字'
            }

        };
    }),
    requiredRulesMaxlength = _form.requiredRulesSharp('wu',true,{},function(rules,name){
        rules[name] = {
            maxlength: 50,
            messages:{
                maxlength :'已超出50字'
            }

        };
    }),
    rulesAllBefore = {
        content:{
            maxlength:1000,
            messages:{
                maxlength :'已超过1000字'
            }
        },
        caseIntroduce:{
            maxlength:1000,
            messages:{
                maxlength :'已超过1000字'
            }
        },
        guaranteeAmountStr:{
            isMoney:true,
            messages:{
                isMoney :'请输入整数位14位以内，小数位2位以内的数字'
            }
        }
    },
    rulesAll = $.extend(true,requiredRulesMaxlength,requiredRules,requiredRulesIsPercentTwoDigits,rulesAllBefore),
    fm5,checkStatus,toIndex;
    _form.validate({
        errorClass: 'error-tip',
        errorElement: 'p',
        errorPlacement: function(error, element) {
            element.after(error);
        },
        onkeyup: true,
        ignore: '.cancel',
        submitHandler: function(form) {
            // console.log('toIndex'+toIndex)
            // console.log('fm5'+fm5)
            // console.log('checkStatus'+checkStatus)
            $(form).ajaxSubmit({
                type: 'post',
                dataType : 'json',
                data: {
                    toIndex: toIndex,
                    fm5: fm5,
                    checkStatus: checkStatus
                },
                success : function(res) {
                	if (res.success) {
                		if (res.url == '#') {
                            hintPopup(res.message,window.location.href);
                        } else {
	                        	$.ajax({
	                        		url: '/projectMg/form/submit.htm',
	                        		type: 'post',
	                        		dataType : 'json',
	                        		data:{
	                        			formId:$('input[name=formId]').val()
	                        		},
	                        		success : function(resf) {
	                        			hintPopup(resf.message,function(){
                                            if (resf.success) {
                                                window.location.href='/projectMg/investigation/list.htm';
                                            }
                                        });
	                        		}
	                        	})
                        }
                	} else {
                		hintPopup(res.message);
                	}
                }
            });
        }
    });
    //验证方法集初始化
    $.fn.initAllOrderValidata(rulesAll, true)
    var md5 = require('md5'); //md5加密
    function formSerializeMd5(_form) {
        var _formSerialize = _form.serialize();
        return md5(_formSerialize);
    }

    function fm5WhetherChange() {
        var _newSerializeMd5 = formSerializeMd5(_form);
        fm5 = (_newSerializeMd5 != _initSerializeMd5) ? 1 : 0; //数据是否有改变
    }

    function rulesAllFalse() {
        $.fn.whetherMust(rulesAll, false).allAddValidata(rulesAll); //否必填
        checkStatus = _form.allWhetherNull(_allWhetherNull, true); //是否填写完整
    }

    var _initSerializeMd5 = formSerializeMd5(_form); //初始页面数据

    $('#submit').click(function(event) {
        fm5WhetherChange();
        $.fn.whetherMust(rulesAll, true).allAddValidata(rulesAll); //是必填
        checkStatus = 1;
        //toIndex = undefined;
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
    // },
    // {
    //     name: '提交',
    //     alias: 'fulfilSubmit',
    //     event: function() {
    //         //console.log(1111)
    //     }
    }]).init().doRender();

});