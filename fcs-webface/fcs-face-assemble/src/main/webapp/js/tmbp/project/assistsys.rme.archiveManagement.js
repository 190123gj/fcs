define(function(require, exports, module) {

    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'fileType,fileName',
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
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',remark', _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        rulesAllBefore = {
            fileType: {
                required: true,
                maxlength: 50,
                messages: {
                    required: '必填',
                    maxlength: '已超出50字'
                }
            },
            remark: {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            },
            fileName: {
                required: true,
                maxlength: 50,
                messages: {
                    required: '必填',
                    maxlength: '已超出50字'
                }
            }
        },
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
        submitValidataCommonUp = require('zyw/submitValidataCommonUp');

    submitValidataCommonUp.submitValidataCommonUp({

        form: _form, //form
        allWhetherNull: _allWhetherNull, //必填集合与是否反向判断
        rulesAll: _rulesAll, //验证全集
        extendFun: function(extendJson) {

            var _checkStatus = $('[name="checkStatus"]').val().split(''),
                sum = '';

            $('#test').find('input').each(function(index, el) {

                var _this = $(el),
                    _name = $.fn.nameOperationFun(_this);

                if (_name != 'id') sum += _this.val();

            });

            (sum == '') ? _checkStatus[extendJson['index']] = 2: _checkStatus[extendJson['index']] = extendJson['presentCheckStatus'];

            return _checkStatus.join('').replace(/0/g, '3');

        },
        allEvent: {

            // replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
            // replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
            contentBtn: [{
                clickObj: '#step ul li',
                eventFun: function(jsonObj) {

                    if ($(jsonObj['self']).hasClass('active')) return false;

                    if (!jsonObj['setInitVal']['fm5']) {

                        window.location.href = '/projectMg/file/fileCatalogMg.htm?type=' + $(jsonObj['self']).attr('codeType');

                        return false;

                    }

                    var _checkStatus = jsonObj.objList['extendFun']({

                        form: jsonObj.objList['form'],
                        index: $('#step ul li.active').attr('index'),
                        presentCheckStatus: jsonObj.setInitVal['checkStatus']

                    }); //拼接checkStatus

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //否必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            type: $(jsonObj['self']).attr('codeType'),
                            submitStatus: 'N'

                        },

                        checkStatus: _checkStatus

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }, {
                clickObj: '#submit',
                eventFun: function(jsonObj) {

                    var _checkStatus = jsonObj.objList['extendFun']({

                        form: jsonObj.objList['form'],
                        index: $('#step ul li.active').attr('index'),
                        presentCheckStatus: jsonObj.setInitVal['checkStatus']

                    }); //拼接checkStatus

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            type: $('.apply-step .item.active').attr('codeType'),
                            submitStatus: 'Y',
                            doing: 'has'

                        },

                        checkStatus: _checkStatus

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }], //内容区提交组
            broadsideBtn: $('[isFileAdmin]').attr('isFileAdmin')=="true"?[]:[{
                    name: '保存',
                    alias: 'temporarStorage',
                    events: function(jsonObj) {

                        var _checkStatus = jsonObj.objList['extendFun']({

                            form: jsonObj.objList['form'],
                            index: $('#step ul li.active').attr('index'),
                            presentCheckStatus: jsonObj.setInitVal['checkStatus']

                        }); //拼接checkStatus

                        $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                        jsonObj.portInitVal['submitHandlerContent'] = {

                            validateData: {

                                type: $('.apply-step .item.active').attr('codeType'),
                                submitStatus: 'Y',
                                doing: 'has'

                            },
                            checkStatus: _checkStatus

                        }; //向validate文件里的submitHandler暴露数据接口

                        jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                        jsonObj.objList['form'].submit(); //提交

                    }
                }] //侧边栏提交组

        },

        ValidataInit: {

            successBeforeFun: function(res) { //请求前操作

                if (!res) return false;

                var util = require('util'),
                    loading = new util.loading();

                loading.open();
                $.fn.orderName();

                $('[name="checkStatus"]').val(res['checkStatus']);

                if (res.validateData['submitStatus'] == 'Y') { //保存

                    if (/0/.test(res['checkStatus'])) { //某页未填完整

                        var navSubmited = require('zyw/navSubmited');

                        navSubmited.navSubmited(res['checkStatus']);
                        $('[name="doing"]').val('has');
                        $('body,html').animate({

                            scrollTop: 0

                        });

                        $('.m-modal-box').remove();

                        return false;

                    } else {

                        return true;

                    }

                } else { //切换

                    return true;

                }


            },

            successFun: function(res) { //响应成功操作

                if (res['success']) {

                    if (res['submitStatus'] == 'Y') {

                        hintPopup(res['message'], '/projectMg/file/fileCatalogMg.htm?type=' + res['type'] + '&doing=' + res['doing']);
                        $('.m-modal-box').remove();

                    } else {

                        window.location.href = '/projectMg/file/fileCatalogMg.htm?type=' + res['type'] + '&doing=' + res['doing'];

                    }

                } else {

                    $('.m-modal-box').remove();
                    hintPopup(res['message']);

                }

            },
            // successBeforeFun: function(sss) {//响应前
            //     console.log(sss + 21321321)
            // },

            // errorAll: { //validata error属性集

            //     errorClass: 'errorClassConfirm',
            //     errorElement: 'em',
            //     errorPlacement: function(error, element) {
            //         element.after(error)
            //     }

            // }

        },

        callback: function(objList) { //加载时回调

            //验证方法集初始化
            $('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
                .initAllOrderValidata()
                //新增类别动态添加项、orderName、验证
                .assignGroupAddValidataUp();

            //动态添加新增文档项、orderName、验证
            $('body').on('click', '.fnAddLineSubitem', function(event) {

                var _active = $('.headmanCover.active'),
                    _sourceHidden = _active.next().val(),
                    _allChange = $('.allChange'),
                    _parent = _active.parent('td'),
                    _rowspan = parseInt(_parent.attr('rowspan')),
                    _parents = _active.parents('tr'),
                    _next = _parents.nextAll('.headman');

                if (_active.length == 0) { //无选择

                    hintPopup('请先单击表格里的类别项，选择类别');
                    return false;

                }

                _parent.attr('rowspan', _rowspan + 1);

                if (_next.length) { //添加元素

                    $(_next[0]).before($('#t-testSubitem').html());
                    $(_next[0]).prev().children('input:hidden.cancel').val(_sourceHidden);

                } else {

                    _allChange.append($('#t-testSubitem').html());
                    _allChange.children('tr').eq(_allChange.children('tr').length - 1).children('input:hidden.cancel').val(_sourceHidden);

                }

                var _activeUp = parseFloat($('.headmanCover.active').parent().css('height')); //遮罩高度改变

                _active.css({

                    height: _activeUp + 22.000182

                });

                $.fn.eachFunaddValidata($('#test'), objList['rulesAll']).numOrder($('#test').children(), '.testNum'); //加验证加序号



            })

            //保存过

            if ($('[name="doing"]').val() != 'null' || $('[name="doing"]').val() != '') {

                var navSubmited = require('zyw/navSubmited');

                navSubmited.navSubmited($('[name="checkStatus"]').val());
                objList['form'].submit();
                //$('#submit').click();

            }

        }

    })



    //效果部分
    $('body').on('click', '.headmanCover', function(event) { //选择新增类别

        var _this = $(this),
            _siblings = _this.parents('tr').siblings().find('.headmanCover');

        _this.addClass('active');
        _siblings.removeClass('active').show() //.siblings('input').hide().siblings('p').show();

    }).on('dblclick', '.headmanCover', function(event) { //双击填写

        var _this = $(this),
            _siblings = _this.parents('tr').siblings().find('.headmanCover');

        _this.hide().siblings('input').focus() //.show()//.siblings('p').hide();
        _siblings.show();

    }).on('click', function(event) { //点击类别以外的地方

        var _target = $(event.target);

        if (_target.closest('.headmanCoverTd').length == 0) {

            $('.headmanCover.active').show() //.siblings('input').hide().siblings('p').show();

        }

    }).on('mouseover mouseout', '.hoverDelete', function(event) { //hover每行删除按钮

        var _this = $(this),
            _hover = _this.parent().find('.subitemDelete');

        if (event.type == "mouseover") {

            _hover.show();

        } else if (event.type == "mouseout") {

            _hover.hide();

        }

        $.fn.numOrder($('#test').children(), '.testNum');

    }).on('click', '.totalDelete', function(event) { //删除整个类别

        var _this = $(this),
            _parents = _this.parents('tr'),
            _nextHas = _parents.nextAll('.headman').length;

        if (_nextHas) {

            _parents.nextUntil('.headman').remove().end().remove();


        } else {

            _parents.nextAll('tr').remove().end().remove();

        }

        $.fn.numOrder($('#test').children(), '.testNum');


    }).on('click', '.subitemDelete', function(event) { //删除某档案行

        var _this = $(this),
            _parent = _this.parents('tr');

        if (_parent.hasClass('headman')) {

            var _headmanCoverTd = _parent.find('.headmanCoverTd'),
                _html = _headmanCoverTd.html(),
                _rowspan = parseInt(_headmanCoverTd.attr('rowspan')),
                _next = _parent.next();
            //_parentsHeadmanCoverTd = _this.parents('tr').find('.headmanCoverTd');

            if (!_next.hasClass('headman')) {

                _next.addClass('headman').find('input:hidden').remove().end().find('td:eq(0)').after('<td class="headmanCoverTd" rowspan="' + (_rowspan - 1) + '">' + _html + '</td>');

            }


        } else {

            var _prev = $(_parent.prevAll('.headman')[0]).find('.headmanCoverTd'),
                _rowspan = parseInt(_prev.attr('rowspan'));

            // _parentsHeadmanCoverTd = $(_this.parents('tr').prevAll('.headman')[0]).find('.headmanCoverTd');

            _prev.attr('rowspan', _rowspan - 1);

        }

        // _parentsHeadmanCoverTd.find('.headmanCover').css({

        //     height: parseFloat(_parentsHeadmanCoverTd.css('height')) + 22.000182

        // });

        _parent.remove();
        maskingChange();

    }).on('change', '.headmanCoverTd input', function(event) { //同一类型其他hidden orderName添加相同val

        var _this = $(this),
            _val = _this.val(),
            _else = _this.parents('tr').nextUntil('.headman').find('input:hidden.cancel'),
            _nextHas = _this.parents('tr').nextAll('.headman').length,
            _next = _this.parents('tr').nextAll('tr').find('input:hidden.cancel');

        if (_nextHas) {

            _else.val(_val)

        } else {

            _next.val(_val)

        }

    });


    function maskingChange(_this) {

        $('body').find('.headmanCover').each(function(index, el) { //遮罩高度

            var _this = $(el),
                _parent = parseFloat(_this.parent().css('height'));

            _this.css({

                height: _parent + 22.000182

            });

        });

    }

    maskingChange();


})