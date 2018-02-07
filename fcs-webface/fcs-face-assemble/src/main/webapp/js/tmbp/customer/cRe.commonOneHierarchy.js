define(function(require, exports, module) {

    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');

    //必填集合
    require('zyw/requiredRules');

    function commonOneHierarchy(objJson) {
        var _form = $('#form'),
            _allWhetherNull = {
                stringObj: 'level1Name,level1Score',
                boll: false
            },
            // must = {
            //     level1Name: {
            //         required: true,
            //         messages: {
            //             required: '必填'
            //         }
            //     }
            // },
            requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name, This) {
                rules[name] = {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                };
            }),
            maxlength20Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',level1Description', _allWhetherNull['boll'], {}, function(rules, name) {
                rules[name] = {
                    maxlength: 200,
                    messages: {
                        maxlength: '已超出200字'
                    }
                };
            }),
            rulesAllBefore = {
                level1Score: {
                    isGrade: true,
                    summation: {
                        obj: '.score',
                        max: objJson['Score'] || 100
                    },
                    messages: {
                        isGrade: '请输入100以内小数点1位的数字',
                        summation: '超过总分数'
                    }
                }
            },
            _rulesAll = $.extend(true, requiredRules, maxlength20Rules, rulesAllBefore);

        var mergeTable = require('zyw/mergeTable');

        new mergeTable({

            //jq对象、jq selector
            obj: '.demandMerge',

            //默认为false。true为td下有input或者select
            valType: false,

            //每次遍历合并会调用的callback
            mergeCallback: function(Dom) { //Dom为每次遍历的合并对象和被合并对象

                // var Obj, text, index;

                // Obj = Dom['mergeObj'];
                // text = Obj.text();
                // index = Obj.index();

                // if (!index) {

                //     Obj.html(text + '<div class="headmanCover"><i class = "xlsTop fn-p-abs"></i><i class = "xlsLeft fn-p-abs"></i><i class = "xlsRight fn-p-abs"></i><i class = "xlsBottom fn-p-abs"></i></div>');

                // }

            },
            callback: function() { //表格合并完毕后

                var submitValidataCommonUp = require('zyw/submitValidataCommonUp');

                submitValidataCommonUp.submitValidataCommonUp({

                    form: _form, //form
                    allWhetherNull: _allWhetherNull, //必填集合与是否反向判断
                    rulesAll: _rulesAll, //验证全集
                    allEvent: {

                        // replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
                        // replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
                        contentBtn: [{
                            clickObj: '#submit',
                            eventFun: function(jsonObj) {

                                $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                                jsonObj.portInitVal['submitHandlerContent'] = {

                                    // validateData: {

                                    //     checkStatus: jsonObj.setInitVal['checkStatus']

                                    // },
                                    submitStatus: 'submit',
                                    fm5: jsonObj.setInitVal['fm5']

                                }; //向validate文件里的submitHandler暴露数据接口

                                jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                jsonObj.objList['form'].submit(); //提交

                            }
                        }, {
                            clickObj: '#TS',
                            eventFun: function(jsonObj) {

                                var must = {
                                    level1Name: {
                                        required: true,
                                        messages: {
                                            required: '必填'
                                        }
                                    }
                                }

                                $.fn.whetherMust(jsonObj.objList['rulesAll'], false).whetherMust(must, true).allAddValidata($.extend(jsonObj.objList['rulesAll'], must)); //是必填

                                jsonObj.portInitVal['submitHandlerContent'] = {

                                    // validateData: {

                                    //     checkStatus: jsonObj.setInitVal['checkStatus']

                                    // },
                                    submitStatus: 'TS',
                                    fm5: jsonObj.setInitVal['fm5']

                                }; //向validate文件里的submitHandler暴露数据接口

                                jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                jsonObj.objList['form'].submit(); //提交

                            }
                        }, {
                            clickObj: '#next',
                            eventFun: function(jsonObj) {

                                if (!jsonObj['setInitVal']['fm5']) {

                                    window.location.href = objJson['href'];

                                    return false;

                                }


                                $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                                jsonObj.portInitVal['submitHandlerContent'] = {

                                    // validateData: {

                                    //     checkStatus: jsonObj.setInitVal['checkStatus']

                                    // },
                                    submitStatus: 'next',
                                    fm5: jsonObj.setInitVal['fm5']

                                }; //向validate文件里的submitHandler暴露数据接口

                                jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                jsonObj.objList['form'].submit(); //提交

                            }
                        }, {
                            clickObj: '#step li',
                            eventFun: function(jsonObj) {

                                var $self = $(jsonObj['self']);

                                if ($self.index() == $('#step li.active').index()) return false;

                                $('.submitStatus').attr('tabId', $self.attr('tabId'));

                                if (!jsonObj['setInitVal']['fm5']) {

                                    window.location.href = window.location.pathname + '?level=1' + '&type=' + $self.attr('tabId');

                                    return false;

                                }

                                $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //否必填

                                jsonObj.portInitVal['submitHandlerContent'] = {

                                    // validateData: {

                                    //     checkStatus: jsonObj.setInitVal['checkStatus']

                                    // },
                                    submitStatus: 'li',
                                    fm5: jsonObj.setInitVal['fm5']

                                }; //向validate文件里的submitHandler暴露数据接口

                                jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                jsonObj.objList['form'].submit(); //提交

                            }
                        }], //内容区提交组
                        broadsideBtn: [{
                                name: '保存',
                                alias: 'temporarStorage',
                                events: function(jsonObj) {

                                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                                    jsonObj.portInitVal['submitHandlerContent'] = {

                                        // validateData: {

                                        //     checkStatus: jsonObj.setInitVal['checkStatus']

                                        // },
                                        submitStatus: 'submit',
                                        fm5: jsonObj.setInitVal['fm5']

                                    }; //向validate文件里的submitHandler暴露数据接口

                                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                    jsonObj.objList['form'].submit(); //提交

                                }
                            }, {
                                name: '暂存',
                                alias: 'temporarStorage22',
                                events: function(jsonObj) {

                                    $.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                                    jsonObj.portInitVal['submitHandlerContent'] = {

                                        // validateData: {

                                        //     checkStatus: jsonObj.setInitVal['checkStatus']

                                        // },
                                        submitStatus: 'TS',
                                        fm5: jsonObj.setInitVal['fm5']

                                    }; //向validate文件里的submitHandler暴露数据接口

                                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                    jsonObj.objList['form'].submit(); //提交

                                }
                            }, {
                                name: objJson['previewHref'] ? '预览' : '',
                                alias: 'temporarStoragesss',
                                events: function(jsonObj) {

                                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                                    jsonObj.portInitVal['submitHandlerContent'] = {

                                        // validateData: {

                                        //     checkStatus: jsonObj.setInitVal['checkStatus']

                                        // },
                                        submitStatus: 'previewHref',
                                        fm5: jsonObj.setInitVal['fm5']

                                    }; //向validate文件里的submitHandler暴露数据接口

                                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                    jsonObj.objList['form'].submit(); //提交

                                }
                            }] //侧边栏提交组

                    },
                    ValidataInit: {

                        successBeforeFun: function(res) {

                            var util = require('util'),
                                loading = new util.loading();

                            loading.open();
                            $.fn.orderName();

                            if (res['submitStatus'] == 'submit' || res['submitStatus'] == 'TS') { //二级页面


                                if (res['fm5']) { //有改变

                                    $('.submitStatus').attr('submitStatus', res['submitStatus']);
                                    return true;

                                } else { //无改变

                                    hintPopup('数据未改变，保持原有储存');
                                    $('.m-modal-box').remove();
                                    return false;

                                }

                            } else {

                                $('.submitStatus').attr('submitStatus', res['submitStatus']);
                                return true;

                            }

                        },

                        successFun: function(res) {

                            if (res['success']) {

                                $('.m-modal-box').remove();

                                if ($('.submitStatus').attr('submitStatus') == 'next') {

                                    window.location.href = objJson['href'];

                                } else if ($('.submitStatus').attr('submitStatus') == 'previewHref') {

                                    window.open(objJson['previewHref'], '_blank');

                                } else if ($('.submitStatus').attr('submitStatus') == 'li') {

                                    window.location.href = window.location.pathname + '?level=1' + '&type=' + $('.submitStatus').attr('tabId');

                                } else {

                                    hintPopup(res.message, window.location.href);

                                }

                            } else {

                                $('.m-modal-box').remove();
                                hintPopup(res['message']);

                            }

                        },
                        // errorAll: { //validata error属性集

                        //     errorClass: 'errorClassConfirm',
                        //     errorElement: 'em',
                        //     errorPlacement: function(error, element) {
                        //         if(element.hasClass('radioSpan')){

                        //         }
                        //         element.after(error)
                        //     }

                        // }
                    },

                    callback: function(objList) { //加载时回调

                        //验证方法集初始化
                        $('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
                            .initAllOrderValidata()
                            .assignGroupAddValidataUpUp(objList['rulesAll'], {

                                length: function(obj) {

                                    var list = obj.This.parents('[orderName]');

                                    if (!list.siblings().length) {

                                        list.find('input[type="text"]').val('');
                                        list.find('.id').remove();
                                        return false;

                                    } else {

                                        return true;

                                    };

                                },
                                Num: true

                            });

                        $('body').on('change', '.score', function() {

                            $('.score').blur();

                        })

                    }

                })

            },

        });

    }

    module.exports = commonOneHierarchy;

})