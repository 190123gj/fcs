define(function (require, exports, module) {
    //上传
    require('zyw/upAttachModify');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'applyTime,outputReason,handOverDept,handOverMan,handOverTime,receiveDept,receiveMan,receiveTime,expectReturnTime,borrowReason',
            boll: false
        },
        requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function (rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function (rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        rulesAllBefore = { //所有格式验证的基
            outputReason: {
                required: true,
                maxlength: 1000,
                messages: {
                    maxlength: '已超出1000字',
                    required: '必填'
                }
            },
            borrowReason: {
                required: true,
                maxlength: 1000,
                messages: {
                    maxlength: '已超出1000字',
                    required: '必填'
                }
            },
            expectReturnTime: {
                required: true,
                // min: $('#applyTime').text(),
                messages: {
                    // min: '请选择大于申请时间的时间',
                    required: '请选择大于申请时间的时间'
                }
            }
        },
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
        submitValidataCommonUp = require('zyw/submitValidataCommonUp');

    submitValidataCommonUp.submitValidataCommonUp({

        form: _form, //form
        allWhetherNull: _allWhetherNull, //必填集合与是否反向判断
        rulesAll: _rulesAll, //验证全集
        allEvent: {

            // replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
            // replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
            contentBtn: [{
                clickObj: '#TS',
                eventFun: function (jsonObj) {

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //否必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'N',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },

                        fm5: jsonObj.setInitVal['fm5']

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }, {
                clickObj: '#submit',
                eventFun: function (jsonObj) {

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'Y',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },
                        fm5: jsonObj.setInitVal['fm5']

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }], //内容区提交组
            broadsideBtn: [{
                    name: '保存并提交',
                    alias: 'temporarStorage',
                    events: function (jsonObj) {

                        $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                        jsonObj.portInitVal['submitHandlerContent'] = {

                            validateData: {

                                submitStatus: 'Y',
                                checkStatus: jsonObj.setInitVal['checkStatus']

                            },
                            fm5: jsonObj.setInitVal['fm5']

                        }; //向validate文件里的submitHandler暴露数据接口

                        jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                        jsonObj.objList['form'].submit(); //提交

                    }
                }] //侧边栏提交组

        },

        ValidataInit: {

            successBeforeFun: function (res) { //请求前操作

                var util = require('util'),
                    loading = new util.loading();

                loading.open();

                if (res.validateData['submitStatus'] == 'Y') { //提交

                    return true;

                } else {

                    if (res['fm5']) { //有改变

                        return true;

                    } else { //无改变

                        $('.m-modal-box').remove();
                        hintPopup('数据未改变，保持原有储存');
                        return false;

                    }

                }


            },

            successFun: function (res) { //响应成功操作

                //弹窗提示
                var hintPopup = require('zyw/hintPopup');

                if (res['success']) {

                    if (res['submitStatus'] == 'Y') {

                        //hintPopup(res['message'], '/projectMg/file/fileCatalogMg.htm?type=' + res['type'] + '&doing=' + res['doing']);
                        $.ajax({

                            url: '/projectMg/form/submit.htm',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                formId: res['formId']
                            },

                            success: function (data) {

                                hintPopup(data.message, function () {

                                    if (data.success) {

                                        // window.location.href = '/projectMg/file/' + window.location.href.match(/(?:\&|\?)urlList.+(?=(\&))|(?:\&|\?)urlList.+/)[0].replace(/(\?|\&)urlList=/, '') + '.htm';

                                        //zhurui go-to-list
                                        //if( window.location.href.indexOf(''))
                                            window.location.href = '/projectMg/file/list.htm';

                                    }

                                });

                                $('.m-modal-box').remove();

                            }

                        })

                    } else if (res['submitStatus'] == 'N') {

                        $('.m-modal-box').remove();
                        hintPopup(res.message, function () {

                            var re, href, arr;

                            re = /formId/;
                            href = window.location.href;
                            arr = re.test(href);

                            //if(arr){console.log('有formID刷新页面');}else{console.log('从新增跳转到编辑')};
                            // arr ? window.location.href = window.location.href : window.location.href = window.location.href + '&formId=' + res['formId'];
                        });

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
            //         element.after(error)
            //     }

            // }

        },

        callback: function (objList) { //加载时回调

            //验证方法集初始化
            $.fn.addValidataCommon(objList['rulesAll'], true)
                .initAllOrderValidata();

        }

    })

    $('#expectReturnTime').click(function (event) {

        var NewDate, Time, maxTime;

        NewDate = new Date();
        Time = NewDate.getTime() + 30 * 24 * 3600 * 1000;
        maxTime = new Date(Time).toLocaleString().substring(0, 10).replace(/\//g, '-');
        minTime = new Date(NewDate.getTime()).toLocaleString().substring(0, 10).replace(/\//g, '-');

        laydate({
            elem: '#expectReturnTime',
            max: maxTime,
            min: minTime
        });

    });



})