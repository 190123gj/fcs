define(function (require, exports, module) {
    // 项目管理 > 授信后管理  > 到期解保

    var COMMON = require('./bfcg.contract.common');
    require('Y-msg');
    require('input.limit');
    require('validate');
    //通用 上传附件、图片
    require('zyw/upAttachModify');
    //模板引擎
    var getList = require('zyw/getList');
    var util = require('util');

    // var minTime = !!$('#minTime').val() ? $('#minTime').val() : false;
    var minTime = false;
    var maxTime = formatDate();
    var limitTime = minTime ? "laydate({min:'" + minTime + "',max:'" + maxTime + "'})" : "laydate({max:'" + maxTime + "'})";
    // var $decodeAcrrodItemsTemplate = $('[tmplt="decodeAcrrodItemsTemplate"]').clone(true); //缓存一个还款申请模板
    var $decodeAcrrodItemsTemplate = $($('#RETURN_MONEY').html()); //缓存一个还款申请模板
    $('.rebackMoneyItem').find('.laydate-icon').attr('onclick', limitTime);

    $('body').on('click', '.rebackMoneyItemBtn .optBtn', function () { //增加删除
        var _this = $(this);
        var _thisTr = _this.parents('.rebackMoneyItem');
        var isAddItem = _this.hasClass('toAdd');
        var projectCode = _thisTr.find('[name*=projectCode]').val();
        var $sameCodeTr = _thisTr.siblings('.rebackMoneyItem');

        if (isAddItem) {
            var $newItem = $($('#RETURN_MONEY').html());
            $newItem.find('input').val('').end()
                .find('.laydate-icon').attr('onclick', limitTime).end()
                .find('.optBtn').removeAttr('style');
            if ($sameCodeTr.length == 0) _this.siblings('a').show();
            _this.hide();
            _thisTr.after($newItem);
        } else {
            if ($sameCodeTr.length <= 1) $sameCodeTr.find('.toDelete').hide();
            $sameCodeTr.last().find('.toAdd').show();
            _thisTr.remove();
        };
        orderNameList($('.decodeAcrrod'));
        $sameCodeTr.eq(0).find('.rebackMoneyInput').blur();
    }).on('click', '.fnChooseProCode', function () {
        Y.alert('提示', '请先选择项目编号！');
    }).on('click', '.isRetrunMoneyBtn', function () { //选择是否有还款项
        var _this = $(this);
        var isChecked = _this.prop('checked');
        var $decodeAcrrod = $('.decodeAcrrod');

        if (isChecked) {
            var $relieveAmount = $decodeAcrrod.removeClass('fn-hide').find('.relieveAmount')
            $relieveAmount.val('').removeAttr('disabled').attr('name', $relieveAmount.attr('tempName')).removeClass('ignore');
            updateRebackMoneyTr($decodeAcrrod);
        } else {
            $decodeAcrrod.addClass('fn-hide').find('.decodeAcrrodItems tbody').html('');
            $decodeAcrrod.find('.relieveAmount').val('').addClass('ignore').attr('disabled', 'disabled').removeAttr('name');
        }
        orderNameList($decodeAcrrod)
    }).on('blur', '.rebackMoneyInput', function () { //还款金额失焦，计算本次申请解保金额
        var _this = $(this);
        var resultVal = 0;
        _this.parents('.decodeAcrrodItems').find('.rebackMoneyInput').not('.disabled').each(function (i, e) {
            var _thisInner = $(this);
            var _thisInnerVal = !!_thisInner.val() ? parseFloat(_thisInner.val()) : 0;
            resultVal += _thisInnerVal;
        });
        resultVal = !!resultVal ? resultVal : 0;
        _this.parents('.decodeAcrrod').find('.relieveAmount').val(resultVal.toFixed(2)).trigger('blur')
    }).on('click', '.fnAddTr', function () { //新增一行还款
        var $decodeAcrrod = $('.decodeAcrrod');
        updateRebackMoneyTr($decodeAcrrod,true);
        orderNameList($decodeAcrrod);

    }).on('click', '.decodeAcrrodItemsTemplate .del', function () { //删除一行还款
        var $decodeAcrrod = $('.decodeAcrrod');
        var $thisTr = $(this).parents('tr');
        if($thisTr.siblings('tr').length == 0) {
            Y.alert('提示','至少保留一条还款记录！');
            return;
        }
        $(this).parents('tr').remove();
        $('.rebackMoneyInput').blur();
        orderNameList($decodeAcrrod);

    }).on('change','.ditchNameSelect',function () {//选择渠道切换相应的二级渠道
        var _this = $(this);
        var $thisTr = _this.parents('td');
        var $box = _this.parents('tr');
        var $selectOpt = _this.find("option:selected");
        var nextDitchName = $selectOpt.attr('nextditchname') || '';
        var capitalChannelId = $selectOpt.attr('capitalChannelId') || '';
        var capitalChannelCode = $selectOpt.attr('capitalChannelCode') || '';
        var capitalChannelType = $selectOpt.attr('capitalChannelType') || '';
        var capitalChannelName = $selectOpt.attr('capitalChannelName') || '';
        var capitalSubChannelId = $selectOpt.attr('capitalSubChannelId') || '';
        var capitalSubChannelCode = $selectOpt.attr('capitalSubChannelCode') || '';
        var capitalSubChannelType = $selectOpt.attr('capitalSubChannelType') || '';
        var capitalSubChannelName = $selectOpt.attr('capitalSubChannelName') || '';
        $box.find('.nextDitchName').html(nextDitchName);
        $thisTr.find('.capitalChannelId').val(capitalChannelId);
        $thisTr.find('.capitalChannelCode').val(capitalChannelCode);
        $thisTr.find('.capitalChannelType').val(capitalChannelType);
        $thisTr.find('.capitalChannelName').val(capitalChannelName);
        $thisTr.find('.capitalSubChannelId').val(capitalSubChannelId);
        $thisTr.find('.capitalSubChannelCode').val(capitalSubChannelCode);
        $thisTr.find('.capitalSubChannelType').val(capitalSubChannelType);
        $thisTr.find('.capitalSubChannelName').val(capitalSubChannelName);
    }).on('click','.returnMoneyCheckbox .checkboxLabel, .returnMoneyCheckbox .checkbox', function () { //切换还款条目
        var _this = $(this);
        var $box = _this.parents('.returnMoneyCheckbox');
        var isChecked = $box.find('.checkbox').prop('checked');

        if(isChecked){
            $box.find('input[type=text]').removeAttr('disabled').removeClass('disabled ignore').blur();
        }else {
            $box.find('input[type=text]').attr('disabled',true).addClass('disabled ignore').val('').blur();
        };
    });
    function updateRebackMoneyTr($decodeAcrrod,isAddTr) {//isAddTr为ture表示新增
        var $newItemss = $decodeAcrrodItemsTemplate.clone(true);
        $newItemss.find('.ignore').removeClass('ignore').end()
            .find('.laydate-icon').attr('onclick', limitTime);
        if(!!isAddTr) {
            $decodeAcrrod.find('.decodeAcrrodItems tbody').append($newItemss);
            $('.rebackMoneyInput').blur();
        }else {
            $decodeAcrrod.find('.decodeAcrrodItems tbody').html($newItemss);
        }

    }
    //选择项目
    var _getList = new getList();
    _getList.init({
        width: '80%',
        title: '项目列表',
        ajaxUrl: '/projectMg/counterGuarantee/queryProjects.json?isAdd=YES&select=my',
        btn: '#choose',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item">{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目名称：',
                '<input class="ui-text fn-w160" type="text" name="projectName">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" type="text" name="customerName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">客户名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            window.location.href = '/projectMg/counterGuarantee/edit.htm?projectCode=' + $a.attr('projectCode');
        }
    });
    //页面加载好后 resetName
    util.resetName();

    var $form = $('#form'),
        requiredRules = {
            ignore: '.ignore',
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            var $relieveAmount = $('.relieveAmount');
            if (!!$relieveAmount.length && !!$('.fnChooseGist').length) {

                var num = 0;
                var relieveAmountNum = (!!$relieveAmount.val() && parseFloat($relieveAmount.val() || 0) > 0) ? true : false;
                var FDB_TABEL_LENGTH = $('#FDB_TABEL').find('tbody tr input[type="radio"]:checked').length > 0 ? true : false;
                $('.oneInTwo').each(function (inde, ele) {
                    num += $(this).children().length;
                });

                if (num == 0 && !relieveAmountNum) {
                    Y.alert('提示', '还款和解保依据至少选择一项！',function () {
                        $('[name="isRetrunMoney"]').focus();
                    });
                    return;
                };
                if (!relieveAmountNum && !FDB_TABEL_LENGTH) {
                    Y.alert('提示', '解保内容需完善其中一项：还款金额或者反担保措施！');
                    return;
                }

            };

            util.ajax({
                url: $form.attr('action'),
                data: $form.serialize(),
                success: function (res) {
                    if (res.success) {
                        util.ajax({
                            url: '/projectMg/form/submit.htm',
                            data: {
                                formId: res.form.formId
                            },
                            success: function (res2) {

                                if (res2.success) {
                                    Y.alert('提示', res2.message, function () {
                                        window.location = '/projectMg/counterGuarantee/list.htm';
                                    });
                                } else {
                                    Y.alert('提示', res2.message);
                                }
                            }
                        });
                        // util.postAudit({
                        //     formId: res.form.formId
                        // }, function () {
                        //     window.location.href = '/projectMg/counterGuarantee/list.htm';
                        // })

                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });

        }
    }));

    // 拟解除的反担保措施
    function toggleCounter($isCounter) {

        if ($isCounter.prop('checked')) {

            $isCounter.parents('tr').find('.fnNeed').each(function (index, el) {

                $(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })

            });

        } else {

            $isCounter.parents('tr').find('.fnNeed').each(function (index, el) {

                var $this = $(this)

                $this.rules('remove', 'required')
                $this.valid()

            });

        }

    }

    $('.isRemove').on('click', function () {

        toggleCounter($(this))

    }).each(function (index, el) {
        toggleCounter($(this))
    });
    function orderNameList(ele) {
        var $box = !!ele ? ele : $('body');
        $box.find('[orderName]').find('.orderNameItem').each(function (index1, ele1) {
            var $this = $(this);
            var orderName = $this.parents('[orderName]').attr('orderName');
            $this.find('[name]').each(function (index2, ele2) {
                var _this = $(this);
                var name = _this.attr('name');
                name = name.indexOf(orderName) >= 0 ? name.split('.')[1] : name;
                var newName = orderName + '[' + index1 + '].' + name;
                _this.attr('name', newName);
            })
        });
        dynamicRemoveRules(ele);
        dynamAddRules(ele);

    };

    function dynamAddRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput');
        $nameList.each(function (i, e) {
            var _this = $(this);
            if (_this.hasClass('validateOneInTwo')) {
                _this.rules('add', {
                    oneInTwo: true,
                    messages: {
                        oneInTwo: '还款和依据必选其一！'
                    }
                });
            } else if (_this.hasClass('relieveAmount')) {
                _this.rules('add', {
                    required: true,
                    min: 0.0000000001,
                    max: parseFloat($('#availableReleaseAmount').val()) + 0.0000000001,
                    messages: {
                        required: '必填',
                        min: '必须大于0',
                        max: '不能超过最大解保金额：' + parseFloat($('#availableReleaseAmount').val()) + '元',
                    }
                });
            } else {
                _this.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
            }


        })
    };

    function dynamicRemoveRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput');
        $nameList.each(function (i, e) {
            $(this).rules("remove");
        })
    };

    orderNameList($('.decodeAcrrod'))
    dynamAddRules($('body'));

    function formatDate(date) { //转换日期类型为2016-08-02

        var resDate = '';
        var _val = (!!!date) ? new Date() : new Date(date); //没传入时间则默认为当前系统时间
        var year = _val.getFullYear(),
            month = _val.getMonth() + 1,
            day = _val.getDate();

        month = (month < 10) ? ('0' + month) : month;
        day = (day < 10) ? ('0' + day) : day;
        resDate = year + '-' + month + '-' + day;

        return resDate;

    };

    // 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    if (false) {
        publicOPN.addOPN([{
            name: '查看用户资料',
            alias: 'lookUserInfo',
            event: function () {}
        }])
    }
    publicOPN.init().doRender();
    // 侧边栏 end
});