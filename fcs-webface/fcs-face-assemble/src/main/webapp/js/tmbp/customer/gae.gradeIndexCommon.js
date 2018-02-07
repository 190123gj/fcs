define(function(require, exports, module) {

    require('zyw/customer/gae.gradeIndexInitCommon');

    var submitValidataCommonUp = require('zyw/submitValidataCommonUp'),
        hintPopup = require('zyw/hintPopup'),
        mergeTable = require('zyw/mergeTable'),
        popupWindow = require('zyw/popupWindow'),
        navSubmited = require('zyw/navSubmited'),
        aa = $('#fnAuditBtnPass').length ? true : false


    module.exports = function(objJson) {

        //    	var allWhetherNullArr = objJson['allWhetherNull']['stringObj'].split(','); //审核通过时必须完整必填项
        //
        //        $('[name]').each(function(index, el) {
        //
        //            var $this, name;
        //
        //            $this = $(el);
        //            name = $this.attr('name');
        //
        //            if (name != undefined && name.indexOf("].") > 0) {
        //                name = name.substring(name.indexOf("].") + 2);
        //            }
        //
        //            if ($.inArray(name, allWhetherNullArr) != -1) {
        //
        //                $this.addClass('fnAuditRequired');
        //
        //            }
        //
        //
        //        });
        submitValidataCommonUp.submitValidataCommonUp({

            form: objJson['form'], //form
            allWhetherNull: objJson['allWhetherNull'], //必填集合与是否反向判断
            rulesAll: objJson['rulesAll'], //验证全集
            extendFun: function(extendJson) {

                var _checkStatus = $('[name="editStatus"]').val().split('');

                _checkStatus[extendJson['index']] = extendJson['value'];

                $('[name="editStatus"]').val(_checkStatus.join(''));

            },
            allEvent: {

                // replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
                // replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
                contentBtn: [{
                    clickObj: '#submit',
                    eventFun: function(jsonObj) {

                        $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                        if (!$('[name="evaluetingType"]:checked').index() && !$('[name="isProsecute"]').is(':checked')) { //内部评级

                            if (!jsonObj.objList['form'].valid()) return false; //验证当前页是否通过

                            jsonObj.objList.extendFun({ //更改当前页面已完整状态
                                index: $('.apply-step .item.active').index(),
                                value: jsonObj.setInitVal['checkStatus']
                            });

                            if (/0/.test($('[name="editStatus"]').val()) && (!$('[name="evaluetingType"]:checked').index() || !$('[name="isProsecute"]').is(':checked'))) { //某页未填完整

                                navSubmited.navSubmitedNew($('[name="editStatus"]').val());

                                $('body,html').animate({

                                    scrollTop: 0

                                });

                                $('.m-modal-box').remove();

                                return false;

                            }

                        }

                        if ( $('[audit]').length && $('[name="projectCode"]').val()) { //审核

                            jsonObj.portInitVal['submitHandlerContent'] = {

                                validateData: {

                                    //checkStatus: jsonObj.setInitVal['checkStatus']

                                },
                                submitStatus: 'submit',
                                fm5: jsonObj.setInitVal['fm5']

                            }; //向validate文件里的submitHandler暴露数据接口
                            jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据
                            jsonObj.objList['form'].submit(); //提交

                        } else { //编辑

                            fundDitch = new popupWindow({

                                YwindowObj: {
                                    content: 'newPopupScript2', //弹窗对象，支持拼接dom、template、template.compile
                                    closeEle: '.close', //find关闭弹窗对象
                                    dragEle: '.newPopup dl dt' //find拖拽对象
                                },

                                ajaxObj: {
                                    url: '/customerMg/evaluting/queryProject.json?userId=' + $('[userId]').attr('userId'),
                                    type: 'post',
                                    dataType: 'json',
                                    data: {
                                        // isChargeNotification: "IS",
                                        // projectCode: _projectCode,
                                        pageSize: 6,
                                        pageNumber: 1
                                    }
                                },

                                pageObj: { //翻页
                                    clickObj: '.pageBox a.btn', //find翻页对象
                                    attrObj: 'page', //翻页对象获取值得属性
                                    jsonName: 'pageNumber', //请求翻页页数的dataName
                                    callback: function($Y) {

                                        //console.log($Y)

                                    }
                                },

                                callback: function($Y) {

                                    $Y.wnd.on('click', 'a.choose', function(event) {
                                        var $this = $(this),
                                            projectCode = $this.parents('tr').find('td:eq(1)').attr('projectCode');
                                        $('[name="projectCode"]').val(projectCode);
                                        jsonObj.portInitVal['submitHandlerContent'] = {

                                            validateData: {

                                                //checkStatus: jsonObj.setInitVal['checkStatus'],
                                                //projectCode: projectCode

                                            },
                                            submitStatus: 'submit',
                                            fm5: jsonObj.setInitVal['fm5']

                                        }; //向validate文件里的submitHandler暴露数据接口
                                        jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据
                                        $Y.close();
                                        jsonObj.objList['form'].submit(); //提交

                                    });

                                },

                                console: false //打印返回数据

                            });

                        }

                    }
                }, {
                    clickObj: '#TS',
                    eventFun: function(jsonObj) {

                        var $active;

                        $active = $('#step li.active');

                        $('.submitStatus').attr('tabId', $active.attr('tabId'));

                        $.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                        jsonObj.objList.extendFun({
                            index: $('.apply-step .item.active').index(),
                            value: jsonObj.setInitVal['checkStatus']
                        });

                        jsonObj.portInitVal['submitHandlerContent'] = {

                            validateData: {

                                isTemporary: 'IS',
                                //checkStatus: jsonObj.setInitVal['checkStatus']

                            },
                            submitStatus: 'TS',
                            fm5: jsonObj.setInitVal['fm5']

                        }; //向validate文件里的submitHandler暴露数据接口

                        jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据
                        $('[name="projectCode"]').val('');
                        jsonObj.objList['form'].submit(); //提交

                    }
                }, {
                    clickObj: '#step li',
                    eventFun: function(jsonObj) {

                        var $self, $customerName;

                        $self = $(jsonObj['self']);
                        $customerName = $('#customerName').val();

                        if ($self.index() == $('#step li.active').index()) return false;

                        if ($customerName === '') {

                            hintPopup('请选择客户');
                            return false;

                        }

                        $('.submitStatus').attr('tabId', $self.attr('tabId'));
                        //console.log(jsonObj['setInitVal']['fm5']);
                        if (!jsonObj['setInitVal']['fm5']) {

                            window.location.href = $('#nextUrl').val() + 'showType=' + $self.attr('tabId') + "&yearEvalueId=" + $('input[name=yearEvalueId]').val() + '&customerId=' + $('#customerId').val() + '&taskId=' + $('[name="taskId"]').val() + '&formId=' + $('[name="formId"]').val();

                            return false;

                        }

                        $.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                        jsonObj.objList.extendFun({
                            index: $('.apply-step .item.active').index(),
                            value: jsonObj.setInitVal['checkStatus']
                        });

                        jsonObj.portInitVal['submitHandlerContent'] = {

                            validateData: {

                                isTemporary: 'IS',
                                //checkStatus: jsonObj.setInitVal['checkStatus']

                            },
                            submitStatus: 'li',
                            fm5: jsonObj.setInitVal['fm5']

                        }; //向validate文件里的submitHandler暴露数据接口

                        jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据


                        $('[name="projectCode"]').val('');

                        jsonObj.objList['form'].submit(); //提交

                    }
                }, {
                    clickObj: '.step',
                    eventFun: function(jsonObj) {

                        var $self, step, $active, $target, $customerName;

                        $self = $(jsonObj['self']);
                        step = $self.attr('stepatt');
                        $active = $('#step li.active');
                        $target = (step == 'next') ? $active.next() : $active.prev();
                        $customerName = $('#customerName').val();

                        if ($customerName === '') {

                            hintPopup('请选择客户');
                            return false;

                        }

                        $('.submitStatus').attr('tabId', $target.attr('tabId'));

                        $.fn.whetherMust(jsonObj.objList['rulesAll'], (step == 'next') ? true : false).allAddValidata(jsonObj.objList['rulesAll']);

                        if (!jsonObj['setInitVal']['fm5'] && ((step != 'next') || ((step == 'next') && jsonObj.objList['form'].valid()))) {

                            window.location.href = $('#nextUrl').val() + 'showType=' + $target.attr('tabId') + '&customerId=' + $('#customerId').val() + "&yearEvalueId=" + $('input[name=yearEvalueId]').val() + '&taskId=' + $('[name="taskId"]').val() + '&formId=' + $('[name="formId"]').val();

                            return false;

                        }

                        jsonObj.objList.extendFun({
                            index: $('.apply-step .item.active').index(),
                            value: jsonObj.setInitVal['checkStatus']
                        });

                        jsonObj.portInitVal['submitHandlerContent'] = {

                            validateData: {

                                isTemporary: 'IS',
                                //checkStatus: jsonObj.setInitVal['checkStatus']

                            },
                            submitStatus: 'step',
                            fm5: jsonObj.setInitVal['fm5']

                        }; //向validate文件里的submitHandler暴露数据接口

                        jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                        $('[name="projectCode"]').val('');

                        jsonObj.objList['form'].submit(); //提交

                    }
                }], //内容区提交组
                broadsideBtn: [{
                        name: $('[audit]').length ? '' : '保存并提交',
                        alias: 'temporarStorage',
                        events: function(jsonObj) {

                            $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                            if (!$('[name="evaluetingType"]:checked').index() && !$('[name="isProsecute"]').is(':checked')) { //内部评级

                                if (!jsonObj.objList['form'].valid()) return false;

                                jsonObj.objList.extendFun({
                                    index: $('.apply-step .item.active').index(),
                                    value: jsonObj.setInitVal['checkStatus']
                                });

                                if (/0/.test($('[name="editStatus"]').val()) && (!$('[name="evaluetingType"]:checked').index() || !$('[name="isProsecute"]').is(':checked'))) { //某页未填完整

                                    navSubmited.navSubmitedNew($('[name="editStatus"]').val());

                                    $('body,html').animate({

                                        scrollTop: 0

                                    });

                                    $('.m-modal-box').remove();

                                    return false;

                                }

                            }

                            if ($('[audit]').length && $('[name="projectCode"]').val()) {

                                jsonObj.portInitVal['submitHandlerContent'] = {

                                    validateData: {

                                        //checkStatus: jsonObj.setInitVal['checkStatus']

                                    },
                                    submitStatus: 'submit',
                                    fm5: jsonObj.setInitVal['fm5']

                                }; //向validate文件里的submitHandler暴露数据接口
                                jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据
                                jsonObj.objList['form'].submit(); //提交

                            } else {

                                fundDitch = new popupWindow({

                                    YwindowObj: {
                                        content: 'newPopupScript2', //弹窗对象，支持拼接dom、template、template.compile
                                        closeEle: '.close', //find关闭弹窗对象
                                        dragEle: '.newPopup dl dt' //find拖拽对象
                                    },

                                    ajaxObj: {
                                        url: '/customerMg/evaluting/queryProject.json?userId=' + $('[userId]').attr('userId'),
                                        type: 'post',
                                        dataType: 'json',
                                        data: {
                                            // isChargeNotification: "IS",
                                            // projectCode: _projectCode,
                                            pageSize: 6,
                                            pageNumber: 1
                                        }
                                    },

                                    pageObj: { //翻页
                                        clickObj: '.pageBox a.btn', //find翻页对象
                                        attrObj: 'page', //翻页对象获取值得属性
                                        jsonName: 'pageNumber', //请求翻页页数的dataName
                                        callback: function($Y) {

                                            //console.log($Y)

                                        }
                                    },

                                    callback: function($Y) {

                                        $Y.wnd.on('click', 'a.choose', function(event) {

                                            var $this = $(this),
                                                projectCode = $this.parents('tr').find('td:eq(1)').attr('projectCode');

                                            $('[name="projectCode"]').val(projectCode);

                                            jsonObj.portInitVal['submitHandlerContent'] = {

                                                validateData: {

                                                    //checkStatus: jsonObj.setInitVal['checkStatus'],
                                                    //projectCode: projectCode

                                                },
                                                submitStatus: 'submit',
                                                fm5: jsonObj.setInitVal['fm5']

                                            }; //向validate文件里的submitHandler暴露数据接口
                                            jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                                            $Y.close();
                                            jsonObj.objList['form'].submit(); //提交

                                        });

                                    },

                                    console: false //打印返回数据

                                });

                            }

                        }
                    }, {
                        name: '暂存',
                        alias: 'temporarStorage2222',
                        events: function(jsonObj) {

                            var $active;

                            $active = $('#step li.active');

                            $('.submitStatus').attr('tabId', $active.attr('tabId'));

                            $.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                            jsonObj.objList.extendFun({
                                index: $('.apply-step .item.active').index(),
                                value: jsonObj.setInitVal['checkStatus']
                            });

                            jsonObj.portInitVal['submitHandlerContent'] = {

                                validateData: {

                                    isTemporary: 'IS',
                                    //checkStatus: jsonObj.setInitVal['checkStatus']

                                },
                                submitStatus: 'TS',
                                fm5: jsonObj.setInitVal['fm5']

                            }; //向validate文件里的submitHandler暴露数据接口

                            jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                            $('[name="projectCode"]').val('');

                            jsonObj.objList['form'].submit(); //提交

                        }
                    }] //侧边栏提交组

            },
            ValidataInit: {

                successBeforeFun: function(res) {

                    var util = require('util'),
                        loading = new util.loading();

                    var fnMakeMoneyfnMakeMicrometer = $('.fnMakeMoney.fnMakeMicrometer');

                    fnMakeMoneyfnMakeMicrometer.each(function(index, el) {
                        var $el = $(el);
                        $el.val($el.val().replace(/\,/g, ''))
                    });

                    $.fn.orderName();

                    if (res['submitStatus'] == 'TS') { //二级页面

                        if (res['fm5']) { //有改变

                            loading.open();
                            $('.submitStatus').attr('submitStatus', res['submitStatus']);
                            return true;

                        } else { //无改变

                            if ($('[standardUseEnumsGetType]').length) {

                                loading.open();
                                $('.submitStatus').attr('submitStatus', res['submitStatus']);
                                return true;

                            } else {

                                hintPopup('数据未改变，保持原有储存');

                                return false;

                            }

                        }

                    } else {

                        loading.open();
                        $('.submitStatus').attr('submitStatus', res['submitStatus']);
                        return true;

                    }

                    // $('.submitStatus').attr('submitStatus', res['submitStatus']);
                    // return true;

                },

                successFun: function(res) {

                    if (res['success']) {

                        // $('.m-modal-box').remove();

                        if ($('.submitStatus').attr('submitStatus') == 'TS') {

                            hintPopup(res.message, $('#nextUrl').val() + 'showType=' + $('.submitStatus').attr('tabId') + '&customerId=' + $('#customerId').val() + "&yearEvalueId=" + $('input[name=yearEvalueId]').val() + '&taskId=' + $('[name="taskId"]').val() + '&formId=' + res['formId']);
                            $('.m-modal-box').remove();

                        } else if ($('.submitStatus').attr('submitStatus') == 'submit') {

                            if ($('[audit]').length) {

                                hintPopup(res.message, '/customerMg/evaluting/list.htm');
                                $('.m-modal-box').remove();

                            } else {

                                $.ajax({

                                    url: '/projectMg/form/submit.htm',
                                    type: 'post',
                                    dataType: 'json',
                                    data: {
                                        formId: res['formId'],
                                        _SYSNAME: 'CRM'
                                    },
                                    success: function(data) {

                                        hintPopup(data.message, function() {

                                            if (data.success) {
                                                window.location.href = '/customerMg/evaluting/list.htm';
                                            }

                                            $('.m-modal-box').remove();

                                        });

                                    }

                                });

                            }


                        } else {

                            window.location.href = $('#nextUrl').val() + 'showType=' + $('.submitStatus').attr('tabId') + '&customerId=' + $('#customerId').val() + "&yearEvalueId=" + $('input[name=yearEvalueId]').val() + '&taskId=' + $('[name="taskId"]').val() + '&formId=' + res['formId'];

                        }

                    } else {

                        $('.m-modal-box').remove();
                        hintPopup(res['message']);

                    }

                },
                RulesInit: objJson['RulesInit'],
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
                $.fn.addValidataCommon(objList['rulesAll'], true)
                    .initAllOrderValidata();

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
                    }

                })



            }

        })
    }

    // 审核通用部分 start
    var auditProject = require('/js/tmbp/auditProject');
    var _auditProject = new auditProject('auditSubmitBtn');
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit({
        // 初始化审核
        // 自定义 确定函数
        // doPass: function(self) {
        //  alert('1');
        //  self.audit.$box.find('.close').eq(0).trigger('click');
        // },
        // doNoPass: function(self) {
        //  alert('3');
        //  self.audit.$box.find('.close').eq(0).trigger('click');
        // },
        // doBack: function(self) {
        //  alert('2');
        //  self.audit.$box.find('.close').eq(0).trigger('click');
        // }
    }).initPrint('打印的url').addAuditBefore(function(dtd) {
        var $form = $('#form');

        if ($('.qualifiedCheckbox').is(':checked')) {
            return dtd.resolve();

        } else {

            if ($form.valid()) {
                $form.ajaxSubmit({
                    data: {
                        isTemporary: 'IS'
                    },
                    success: function(res) {

                        if (res.success) {
                            dtd.resolve();
                        } else {
                            dtd.reject(res);
                        }
                        //hintPopup(res['message']);
                    }
                });

            } else {
                return dtd.reject({
                    message: '表单填写有误'
                })
            }


        }
    }).addAuditCallback(function() {
        window.location.href = "/customerMg/evaluting/list.htm"
    });

    //------ 侧边栏 start
    // var publicOPN = new(require('zyw/publicOPN'))();
    // publicOPN.addOPN([]).init().doRender();
    //------ 侧边栏 end

})