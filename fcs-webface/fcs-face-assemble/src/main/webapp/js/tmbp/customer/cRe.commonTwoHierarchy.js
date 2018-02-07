define(function (require, exports, module) {

    var util = require('util');
    var loading = new util.loading();

    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup'),
        submitValidataCommonUps = require('zyw/submitValidataCommonUp'),
        mergeTable = require('zyw/mergeTable');

    // function isIE() {
    //     if (navigator.userAgent.indexOf("MSIE") > 0) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }

    function maskingChange(_this) {

        $('body').find('.headmanCover').each(function (index, el) { //遮罩高度

            var _this = $(el),
                _parent = parseFloat(_this.parent().css('height'));

            _this.css({

                height: _parent + 22.000182

            });

        });

    }

    $('body').on('change', 'select', function (event) {
        maskingChange();
    });

    //和验证
    jQuery.validator.addMethod('groupSummation', function (value, element, param) {

        var $element, $one, $elementTbody, $oneText, $maxVal, $sameGroupText, $index, max, sum;

        $element = $(element);

        $one = $element.parent().siblings('td').eq(0);
        $elementTbody = $element.parents('tbody');
        $oneText = $one.text().trim();
        $sameGroupText = $oneText.match(/.+?(?=\-)/)[0];
        $maxVal = parseFloat($one.find('input.maxScore').val() || 0);

        $index = $element.parent().index();
        max = 0;
        sum = 0;

        $elementTbody.find('tr').each(function (index, el) {

            var $el, $td, $tdVal, $text, $next, $nextVal, $nextText;

            $el = $(el);
            $td = $el.find('td');
            $tdVal = parseFloat($td.eq($index).find('input').val() || 0);
            $text = $td.eq(0).text().trim();
            $next = $el.next();
            $nextVal = parseFloat($next.find('td').eq($index).find('input').val() || 0);
            $nextText = $next.find('td').eq(0).text().trim();

            if ($text.match(/.+?(?=\-)/)[0] != $sameGroupText) {

                if (!sum) {

                    return true;

                } else {

                    return false;

                }

            }

            max = (max > $tdVal) ? max : $tdVal;

            if ($text != $nextText) {

                // console.log(max);
                sum += max;
                max = 0;

            }

        });

        // console.log(sum, $maxVal);
        return this.optional(element) || (sum <= $maxVal);

    }, $.validator.format("请输入{0}"));

    //和验证2
    jQuery.validator.addMethod('groupSummationDown', function (value, element, param) {

        var $element, $val, $levelname, $tr, sum, $maxVal;

        $element = $(element);
        $val = $element.val();
        $levelname = $element.parents('tr').attr('levelname');
        $tr = $element.parents('tbody').find('[levelname="' + $levelname + '"]');
        $maxVal = parseFloat($element.parents('tr').find('.maxScore').val() || 0);
        sum = 0;

        $tr.each(function (index, el) {

            var $el;

            $el = $(el);

            if ($el.find('.Score').parent().is(':hidden')) return true;

            sum += parseFloat($el.find('.Score').val() || 0);

        });

        return this.optional(element) || (sum <= $maxVal);

    }, $.validator.format("请输入{0}"));


    //和验证3
    jQuery.validator.addMethod('groupSummationNew', function (value, element, param) {

        var $element, $index, $parents, $levelName, max, sum, $maxVal;

        $element = $(element);
        $index = $element.parent('td').index();
        $parents = $element.parents('tr');
        //$levelTwoName = $parents.attr('levelTwoName');
        $levelName = $parents.attr('levelName');
        max = 0;
        sum = 0;
        $maxVal = parseFloat($element.parents('tr').find('.maxScore').val() || 0);

        $element.parents('tbody').find('tr[levelName="' + $levelName + '"]').each(function (index, el) {

            var $el, $next, $levelTwoName, $nextLevelTwoName, $val;

            $el = $(el);
            $next = $el.next();
            $levelTwoName = $el.attr('levelTwoName');
            $nextLevelTwoName = $next.attr('levelTwoName');
            $val = parseFloat($el.find('td').eq($index).find('input').val() || 0);

            max = $val > max ? $val : max;

            if ($levelTwoName !== $nextLevelTwoName) {

                sum += max;
                max = 0;

            }

        });

        return this.optional(element) || (sum <= $maxVal);

    }, $.validator.format("请输入{0}"));


    function commonTwoHierarchy(objJson) {


        submitValidataCommonUps.submitValidataCommonUp({

            form: objJson['form'], //form
            allWhetherNull: objJson['allWhetherNull'], //必填集合与是否反向判断
            rulesAll: objJson['rulesAll'], //验证全集
            allEvent: {

                // replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
                // replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
                contentBtn: [{
                    clickObj: '#submit',
                    eventFun: function (jsonObj) {

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
                    eventFun: function (jsonObj) {

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
                    clickObj: '#next',
                    eventFun: function (jsonObj) {

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
                    eventFun: function (jsonObj) {

                        var $self = $(jsonObj['self']);

                        if ($self.index() == $('#step li.active').index()) return false;

                        $('.submitStatus').attr('tabId', $self.attr('tabId'));

                        if (!jsonObj['setInitVal']['fm5']) {

                            window.location.href = window.location.pathname + '?level=1' + '&type=' + $self.attr('tabId');

                            return false;

                        }
                        $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

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
                        events: function (jsonObj) {

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
                        events: function (jsonObj) {

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
                        events: function (jsonObj) {

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

                successBeforeFun: function (res) {


                    var loading = new util.loading();

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

                successFun: function (res) {

                    // //弹窗提示
                    // var hintPopup = require('zyw/hintPopup');

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

            callback: function (objList) { //加载时回调

                //验证方法集初始化
                $.fn.addValidataCommon(objList['rulesAll'], true)
                    .initAllOrderValidata();

                $('body').on('change', '.score', function (event) {

                    $('.score').blur();

                });

                new mergeTable({

                    //jq对象、jq selector
                    obj: '.demandMerge',
                    objVal: '.demandMergeVal',

                    //默认为false。true为td下有input或者select
                    valType: false,

                    //每次遍历合并会调用的callback
                    mergeCallback: function (Dom) { //Dom为每次遍历的合并对象和被合并对象

                        // var Obj, text, index;

                        // Obj = Dom['mergeObj'];
                        // text = Obj.text();
                        // index = Obj.index();

                        // if (!index) {

                        //     Obj.html(text + '<div class="headmanCover"><i class = "xlsTop fn-p-abs"></i><i class = "xlsLeft fn-p-abs"></i><i class = "xlsRight fn-p-abs"></i><i class = "xlsBottom fn-p-abs"></i></div>');

                        // }

                    },
                    callback: function () { //表格合并完毕后

                        $('.m-modal-box').remove();
                        maskingChange();

                        //双击填写
                        $('body').on('dblclick', '.addDemand .headmanCover', function (event) {

                            var $this, $siblings;

                            $this = $(this);
                            $siblings = $this.siblings('input[type="text"]');

                            if (!$siblings.length) return false;

                            $this.hide();
                            $siblings.focus();

                        }).click(function (event) {

                            var $target;

                            $target = $(event.target);

                            if (!$target.closest('.addDemand').length) $('.addDemand.active .headmanCover').show();

                        }).on('click', '.addDemand .headmanCover', function (event) {

                            var $this, $siblings;

                            $this = $(this);
                            $siblings = $this.parents('tbody').find('.headmanCover');

                            $siblings.show();

                        }).on('change', '.Score', function (event) {

                            var $this, $levelname;

                            $this = $(this);
                            $levelname = $this.parents('tr').attr('levelname');
                            $tr = $this.parents('tbody').find('[levelname="' + $levelname + '"]');

                            $tr.find('.Score').blur();

                        }).on('change', '.Alter', function (event) {

                            var $this, $tr, $index, val;

                            $this = $(this);
                            $index = $this.parent('td').index();
                            $tr = $this.parents('tr');
                            val = $this.val();

                            $tr.nextAll('tr').each(function (index, el) {

                                var $el, $target, $td;

                                $el = $(el);
                                $td = $el.find('td').eq($index);
                                $target = $td.find('.Alter');

                                if ($td.is(':visible')) return false;

                                $target.val(val);

                            });


                        });



                    },
                    transform: {

                        open: true, //开启增删模式
                        active: '.addDemand', //允许增加项对象（支持任何JQselect）
                        on: '.active', //选中样式（支持任何JQselect）
                        addBtn: '.fnAddLineSubitem', //选中增加项后单击增加的按钮（单个）
                        addHbefore: function (callObj) {

                            if ($('.fnAddLineSubitem').length > 1) {

                                var attrSubmit = callObj.This.attr('fnAddLineSubitem')

                                if (callObj.active.index() != attrSubmit) {

                                    hintPopup('请选择需' + callObj.This.text() + '的' + $('th').eq(attrSubmit).text());
                                    return false;

                                }

                            }

                        },
                        addHandleEachExcept: function (callObj) { //选中对象active、新增tr对象newTr

                            if (!callObj.active.length) {

                                hintPopup(objJson['hintPopup']);
                                return false;

                            }

                            var $activeTr, $activeTd;

                            $activeTr = callObj.active.parent();
                            $activeTd = $activeTr.find('td');

                            if ($activeTr.attr('levelname')) {

                                callObj.newTr.attr('levelname', $activeTr.attr('levelname'));

                            }

                            if ($activeTr.attr('levelTwoName')) {

                                callObj.newTr.attr('levelTwoName', $activeTr.attr('levelTwoName'));

                            }

                            $.fn.orderName()

                            callObj.newTr.find('td').each(function (index, el) {

                                var $el, $index;

                                $el = $(el);
                                $index = $el.index();

                                if ($el.is(':hidden')) {

                                    $el.find('input').each(function (index, el) {

                                        var $elInput;

                                        $elInput = $(el);

                                        $elInput.val($activeTd.eq($index).find('input').eq(index).val());

                                    }).eq(0).after($activeTd.eq($index).text().trim());

                                }

                                if ($el.find('input').length) $.fn.addValidataFun($el.find('input'), objJson['rulesAll']);

                            });

                            $.fn.numOrderUp($('tbody tr'), '.testNum', '.valNum');


                        },
                        // addHandleEachTd: function(callObj) { //添加时参与循环改变rowspan的对象

                        //     console.log(2);

                        // },
                        addCallback: function () { //添加完毕后回调

                            maskingChange();

                        },
                        addContent: $('#addList').html(), //Dom或拼接字符串

                        removeBtn: '.removeDemand', //对应tr内td或td内元素
                        removeBeforeHandle: function (callObj) { //当前删除按钮对象,删除前操作

                            var obj = callObj.element.parents('tr').find('td').eq(0),
                                jus = parseInt(obj.attr('rowspan'));

                            if (jus == 1 || (obj.is(':visible') && !jus)) {

                                callObj.element.parents('tr').find('td input[type="text"],td select').val('');
                                return false;

                            }

                        },
                        // removeHandleEachExcept: function(callObj) { //重新显示的TD

                        //     console.log(3);

                        // },
                        // removeHandleEachTd: function(callObj) { //移除时参与循环改变rowspan的对象

                        //     console.log(4);

                        // },
                        removeCallback: function () { //删除完毕后回调

                            maskingChange();

                            $('body').find('.score').eq(0).change();

                            // if (objJson['form'].valid()) {

                            //     $('.score').next('em.error-tip').hide();

                            // } else {

                            //     objJson['form'].submit();

                            // }

                        },

                    }

                });

            }

        })


    }

    module.exports = commonTwoHierarchy;

})