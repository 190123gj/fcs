define(function(require, exports, module) {

    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'string1,string2,string3,string4,string5,string6,string7,string8,string9,string10,string11,string12,string13,string14,string15,string16,string17,string17,string18,string19,str1,str2,str3,str4,integer1,double1',
            boll: false
        },
        requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        maxlength20Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                maxlength: 200,
                messages: {
                    maxlength: '已超出200字'
                }
            };
        }),
        isGradeRules = _form.requiredRulesSharp('string3,string6,string10,string14,string15,string18,string19,integer1', false, {}, function(rules, name) {
            rules[name] = {
                isGrade: true,
                messages: {
                    isGrade: '请输入100以内有效数字，最多包含1位小数'
                }
            };
        }),
        isSpecialRateRules = _form.requiredRulesSharp('string2,string12', false, {}, function(rules, name) {
            rules[name] = {
                isSpecialRate: true,
                messages: {
                    isSpecialRate: '请输入8位有效数字，最多包含两位小数'
                }
            };
        }),
        isPercentTwoDigitsRules = _form.requiredRulesSharp('string17,double1', false, {}, function(rules, name) {
            rules[name] = {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入100.00以内的数字'
                }
            };
        }),
        summationRules = _form.requiredRulesSharp('string3,string19', false, {}, function(rules, name) {
            rules[name] = {
                summation: {
                    obj: '.totalScore',
                    max: 100
                },
                messages: {
                    summation: '该列总分已超出100分'
                }
            };
        }),
        summationUpRules = _form.requiredRulesSharp('integer1', false, {}, function(rules, name) {
            rules[name] = {
                summationUp: {
                    obj: '.totalScoreUp',
                    box: '.totalScoreBox',
                    max: function(This) {

                        return parseFloat(This.parents('.totalScoreBox').find('.totalScoreUpSum').val() || 0);

                    }
                },
                messages: {
                    summationUp: '超出满分'
                }
            };
        }),
        maxRules = _form.requiredRulesSharp('string6', false, {}, function(rules, name) {
            rules[name] = {
                max: function(This) {

                    var $This, $prev;

                    $This = $(This);
                    $prev = parseFloat($This.parent().prevAll().find('.totalScore').val());

                    return $prev;

                },
                messages: {
                    max: '已超出改行满分值'
                }
            };
        }),
        _rulesAll = $.extend(true, maxlength20Rules, requiredRules, isGradeRules, isSpecialRateRules, isPercentTwoDigitsRules, summationRules, maxRules, summationUpRules),
        submitValidataCommonUp = require('zyw/submitValidataCommonUp');

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

//            if ($('[mapInfo]').attr('mapInfo') == '{}') {
//
//                hintPopup('指标未配置');
//
//                $('form').hide();
//            }

            $('body').find('[newOrderName]').each(function(index, el) {

                var $el, newOrderNameIndex, newOrderName;

                $el = $(el);
                newOrderNameIndex = index;
                newOrderName = $el.attr('newOrderName');

                $el.find('.newOrderName [name]').each(function(index, el) {

                    var $el, name;

                    $el = $(el);
                    name = $el.attr('name');
                    newName = newOrderName + "[" + newOrderNameIndex + "]." + name;

                    $el.attr('name', newName)

                });

            });

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
                        clickObj: '#step li',
                        eventFun: function(jsonObj) {

                            var $self = $(jsonObj['self']);

                            if ($self.index() == $('#step li.active').index()) return false;

                            $('.submitStatus').attr('tabId', $self.attr('tabId'));

                            if (!jsonObj['setInitVal']['fm5']) {

                                window.location.href = window.location.pathname + '?type=' + $('.submitStatus').attr('tabId');

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

                        // //弹窗提示
                        // var hintPopup = require('zyw/hintPopup');

                        if (res['success']) {

                            $('.m-modal-box').remove();

                            if ($('.submitStatus').attr('submitStatus') == 'li') {

                                //var judge = /\?/.test(window.location.href) ? '&' : '?';

                                window.location.href = window.location.pathname + '?type=' + $('.submitStatus').attr('tabId');

                            } else {

                                hintPopup(res.message, window.location.href);

                            }

                        } else {

                            $('.m-modal-box').remove();
                            hintPopup(res['message']);

                        }

                    },
                    errorAll: { //validata error属性集

                        errorClass: 'error-tip',
                        errorElement: 'span',
                        errorPlacement: function(error, element) {
                            // if (element.hasClass('radioSpan')) {

                            // }
                            element.after(error)
                        }

                    },
                    RulesInit: {

                        rules: {
                            'financialSetInfoTs[0].string11': {
                                isGrade: true,
                                summation: {
                                    obj: '.totalScore',
                                    max: 100
                                },
                                min: function(This) {

                                    var $this, $target, val;

                                    $this = $(This);
                                    $target = $this.parents('.totalScoreBox').find('.totalScoreUp2');
                                    val = 0;

                                    $target.each(function(index, el) {

                                        var $el;

                                        $el = $(el);

                                        val = val < parseFloat($el.val()) ? parseFloat($el.val()) : val;

                                    });

                                    return val;

                                }
                            },
                            'financialSetInfoTs[0].string7': {
                                max: function(This) {

                                    var $this, $target, val;

                                    $this = $(This);
                                    $target = $this.parents('.totalScoreBox').find('.totalScoreUpSum');

                                    return $target.val();

                                }
                            },
                            'financialSetInfoTs[0].string8': {
                                max: function(This) {

                                    var $this, $target, val;

                                    $this = $(This);
                                    $target = $this.parents('.totalScoreBox').find('.totalScoreUpSum');

                                    return $target.val();

                                }
                            },
                            'financialSetInfoTs[0].string9': {
                                max: function(This) {

                                    var $this, $target, val;

                                    $this = $(This);
                                    $target = $this.parents('.totalScoreBox').find('.totalScoreUpSum');

                                    return $target.val();

                                }

                            },
                            'financialSetInfoTs[0].string10': {
                                max: function(This) {

                                    var $this, $target, val;

                                    $this = $(This);
                                    $target = $this.parents('.totalScoreBox').find('.totalScoreUpSum');

                                    return $target.val();

                                }
                            },
                            'financialSetInfoTs[1].string8': {
                                isSpecialRate: true
                            }
                        },
                        messages: {
                            'financialSetInfoTs[0].string11': {
                                isGrade: '请输入100以内有效数字，最多包含1位小数',
                                min: '满分小于子项分值',
                                summation: '该列总分已超出100分'
                            },
                            'financialSetInfoTs[1].string8': {
                                isSpecialRate: '请输入8位有效数字，最多包含两位小数'
                            },
                            'financialSetInfoTs[0].string7': {
                                max: '超出该组满分'

                            },
                            'financialSetInfoTs[0].string8': {
                                max: '超出该组满分'

                            },
                            'financialSetInfoTs[0].string9': {
                                max: '超出该组满分'

                            },
                            'financialSetInfoTs[0].string10': {
                                max: '超出该组满分'

                            }
                        }

                    }
                },

                callback: function(objList) { //加载时回调

                    //验证方法集初始化
                    $('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
                        .initAllOrderValidata()
                        .assignGroupAddValidataUpUp(objList['rulesAll'], {

                            length: function(obj) {

                                return true;
                            },
                            later: function(obj) {

                                $('.totalScoreUp').blur();
                            }

                        });

                    $('body').on('change', '.totalScore', function(event) {

                        $('.totalScore').blur();

                    }).on('change', '.totalScoreUp,.totalScoreUpSum', function(event) {

                        $(this).parents('.totalScoreBox').find('.totalScoreUp').blur();

                    }).on('change', '.totalScoreUp2', function(event) {

                        $(this).parents('.totalScoreBox').find('.totalScoreUpSum').blur();

                    });

                }

            })

        },

    });



})