define(function (require, exports, module) {
    // 客户管理 > 个人客户 > 客户列表

    require('input.limit');
    require('zyw/upAttachModify');

    require('validate');
    require('validate.extend');

    require('Y-msg');

    var util = require('util');
    var getList = require('zyw/getList')

    util.resetName();

    var IS_NEW_CHANNEL = ($('#fnIsNewChannel').val() === 'IS') ? true : false,
        CHANNEL_CODE = IS_NEW_CHANNEL ? '' : $('#fnChannelCode').val(),
        CONTRACT_LIST_TPL_FORM = !IS_NEW_CHANNEL ? '' : [
            '渠道名称：',
            '<input class="ui-text fn-w160" type="text" name="likeChannelName">',
            '&nbsp;&nbsp;',
            '渠道编号：',
            '<input class="ui-text fn-w160" type="text" name="likeChannelCode">',
            '&nbsp;&nbsp;',
            '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
        ].join('')

    // 2016.10.28 选择合同
    var CONTRACT_LIST = new getList();
    CONTRACT_LIST.init({
        title: '合同列表',
        ajaxUrl: '/baseDataLoad/channalContrat.json?channelType=' + $('#fnChannelType').val() + '&channelCode=' + CHANNEL_CODE,
        btn: '#fnChooseC',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.contractNo}}</td>',
                '        <td>{{item.channalCode}}</td>',
                '        <td title="{{item.channalName}}">{{(item.channalName && item.channalName.length > 10)?item.channalName.substr(0,10)+\'\.\.\':item.channalName}}</td>',
                '        <td>{{item.rawAddTime}}</td>',
                '        <td><a class="choose" ccode="{{item.channalCode}}" cname="{{item.channalName}}" cno="{{item.contractNo}}" contract="{{item.contract}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: CONTRACT_LIST_TPL_FORM,
            thead: ['<th width="100">合同编号</th>',
                '<th width="120">渠道编号</th>',
                '<th width="120">渠道名称</th>',
                '<th width="200">提交时间</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function ($a) {
            // window.location.href = '/projectMg/financialProject/redeem/form.htm?projectCode=' + $a.attr('projectCode');
            document.getElementById('fnChannelCode').value = $a.attr('ccode')
            document.getElementById('fnContractNo').value = $a.attr('cno')
            document.getElementById('fnAttachBox').innerHTML = util.setUpAttachHtml($a.attr('contract'))
            document.getElementById('fnAttachUrl').value = $a.attr('contract')

            // 渠道名称默认可以修改 选择合同后不能修改
            $('#fnChannelName').val($a.attr('cname')).prop('readonly', 'readonly')

            $fnChannelName.rules('remove', 'remote')
            $fnChannelName.valid()

            $('#fnChannelType').prop('disabled', 'disabled').parent().find('.fnChannelTypeVal').html('<input type="hidden" value="' + $('#fnChannelType').val() + '" name="channelType">')
        }
    });

    // 日期限制
    var $fnStartDate = $('#fnStartDate'),
        $fnEndDate = $('#fnEndDate'),
        renewalDateE;

    $fnStartDate.on('click', function () {
        var self = this;
        // 续签，开始时间的限制

        laydate({
            elem: '#' + self.id,
            max: $fnEndDate.val(),
            min: renewalDateE ? renewalDateE : '',
            start: renewalDateE ? renewalDateE : ''
        });
    });

    $fnEndDate.on('click', function () {
        var self = this;
        laydate({
            elem: '#' + self.id,
            min: $fnStartDate.val()
        });
    });

    var REQUIRERULES = { // 验证规则
        rules: {},
        messages: {}
    };

    // 必填
    $('.fnRequired').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            required: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            required: '必填'
        });

    });

    // 电话号码
    $('.fnIsPhoneOrMobile').each(function (index, el) {
        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            isPhoneOrMobile: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            isPhoneOrMobile: '请输入正确的电话号码'
        });
    });

    var $form = $('#form');

    $form.validate($.extend(true, REQUIRERULES, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {

            if (element.hasClass('fnErrorAfter')) {

                element.after(error);

            } else {

                element.parent().append(error);

            }

        },
        rules: {
            // channelCode: {
            //     number: true,
            //     maxlength: 6,
            //     minlength: 6
            // }
        },
        messages: {
            // channelCode: {
            //     number: '请输入数字',
            //     maxlength: '请输入6位数字',
            //     minlength: '请输入6位数字'
            // }
        }
    }));

    // 假若是编辑状态，就不需要去验证
    var $fnChannelCode = $('#fnChannelCode'),
        $fnChannelName = $('#fnChannelName'),
        $fnInstitutionalAttributes = $('#fnInstitutionalAttributes');

    var OLDCHANNELCODE = $fnChannelCode.val(),
        OLDCHANNELNAME = $fnChannelName.val();

    // 是否有值 有就绑定 change 事件
    if (!!OLDCHANNELCODE) {

        $fnChannelCode.on('change', function () {

            setChanelCodeValid(($fnChannelCode.val() != OLDCHANNELCODE) ? true : false);

        });

        $fnChannelCode.valid();

    } else {

        setChanelCodeValid(true, true);

    }

    if (!!OLDCHANNELNAME) {

        $fnChannelName.on('change', function () {

            setChanelNameValid(($fnChannelName.val() != OLDCHANNELNAME) ? true : false);

        });

        $fnChannelName.valid();

    } else {

        setChanelNameValid(true, true);

    }

    function setChanelCodeValid(add, first) {

        // 2016.10.20
        // 渠道编号 带出来
        return;

        if (!!!$fnChannelCode.length) {
            return;
        }

        if (add) {

            $fnChannelCode.rules('add', {
                remote: {
                    url: '/customerMg/channal/validataChannalCode.json?_time=' + (new Date()).getTime(), //后台处理程序
                    type: 'post', //数据发送方式
                    dataType: 'json', //接受数据格式   
                    data: { //要传递的数据
                        channelCode: function () {
                            return $('input[name=channelCode]').val();
                        }
                    }
                },
                messages: {
                    remote: '编号已重复'
                }
            });

        } else {

            $fnChannelCode.rules('remove', 'remote');

        }

        if (!first) {
            $fnChannelCode.valid();
        }

    }

    function setChanelNameValid(add, first) {

        // 2016.10.29
        // 渠道名称 带出来
        // return;

        if (!!!$fnChannelName.length) {
            return;
        }

        if (add) {

            $fnChannelName.rules('add', {
                remote: {
                    url: '/customerMg/channal/validataChannalName.json?_time=' + (new Date()).getTime(), //后台处理程序
                    type: 'post', //数据发送方式
                    dataType: 'json', //接受数据格式   
                    data: { //要传递的数据
                        channelName: function () {
                            return $('input[name=channelName]').val();
                        }
                    }
                },
                messages: {
                    remote: '名称已存在'
                }
            });

        } else {

            $fnChannelName.rules('remove', 'remote');

        }

        if (!first) {
            $fnChannelName.valid();
        }

    }

    if (!!$fnInstitutionalAttributes, length) {
        $fnInstitutionalAttributes.valid();
    }

    // 暂存
    function fnDoSave() {

        document.getElementById('isTemporary').value = 'IS';

        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', '操作成功', function () {
                        window.location.href = '/customerMg/channal/list.htm';
                    })
                } else {
                    Y.alert('操作失败', res.message)
                }
            }
        });

    }

    // 保存
    function fnDoSubmit(exit, isTemporary, needCheck) {

        $fnInstitutionalAttributes.valid();

        if (needCheck) {

            if (!$form.valid()) {
                Y.alert('提示', '表单内容填写数据有误或必填项未填完');
                return;
            }

            //验证必填项
            var _isInputPass = true,
                _isInputPassEq,
                $input = $('.fnInput:visible:not([readonly])');
            $input.each(function (index, e) {

                if (!!!e.value.replace(/\s/g, '')) {

                    _isInputPass = false;
                    _isInputPassEq = index;
                    return false;

                }

            });

            if (!_isInputPass) {
                Y.alert('提示', '表单输入框填写数据不完整', function () {
                    $input.eq(_isInputPassEq).focus();
                });
                return;
            }

            //授信额度\单笔限额二选一
            var $fnInput2x1 = $('.fnInput2x1:visible'),
                _2x1Pass = true;

            $fnInput2x1.each(function (index, el) {

                if (!!!$fnInput2x1.eq(index).find('[type="checkbox"]:checked').length) {
                    _2x1Pass = false;
                    return false;
                }

            });

            if (!_2x1Pass) {
                Y.alert('提示', '授信额度、单笔限额不可为空，请选择并填写对应的值');
                return;
            }


        } else {

            // 渠道编号、名称、必须正确
            if (!$fnChannelName.hasClass('valid') || !$fnInstitutionalAttributes.hasClass('valid')) {
                Y.alert('提示', '渠道编号、名称、金融机构属性填写有误');
                return;
            }

        }

        document.getElementById('isTemporary').value = isTemporary;

        util.resetName();

        util.ajax({
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', '操作成功', function () {
                        if (exit) {
                            window.location.href = '/customerMg/channal/list.htm';
                        } else {
                            window.location.href = '/customerMg/channal/add.htm';
                            //window.location.reload(true);
                        }
                    })
                } else {
                    Y.alert('操作失败', res.message)
                }
            }
        });

    }

    // 渠道分类切换
    $('#fnChannelType').on('change', function () {
        channelChange(this.value)
        CONTRACT_LIST.resetAjaxUrl('/baseDataLoad/channalContrat.json?channelType=' + $('#fnChannelType').val() + '&channelCode=' + CHANNEL_CODE)

        // 没有禁用
        if (!this.disabled && this.className.indexOf('notFist') >= 0) {
            $.ajax('/baseDataLoad/newChannalCode.json?channalType=' + this.value)
                .then(function (res) {
                    if (res.success) {
                        document.getElementById('fnChannelCode').value = res.data
                    }
                })
        }
    }).trigger('change').addClass('notFist');

    // 渠道分类切换 dom改变
    function channelChange(value) {

        var _$fnBankNeedHalf = $('.fnBankNeedHalf');
        if (value == 'YH') {
            if (!_$fnBankNeedHalf.hasClass('m-item-half')) {
                $('.fnBankNeed').removeClass('fn-hide').find('input,select').removeProp('disabled');
                _$fnBankNeedHalf.addClass('m-item-half fn-left');
            }
        } else {
            if (_$fnBankNeedHalf.hasClass('m-item-half')) {
                $('.fnBankNeed').addClass('fn-hide').find('input,select').prop('disabled', 'disabled');
                _$fnBankNeedHalf.removeClass('m-item-half fn-left');
            }
        }
    }

    // 暂存
    $('#fnDoSave').on('click', function () {

        fnDoSubmit(true, 'IS', false);

    });

    // 保存
    $('.fnDoSubmit').on('click', function () {

        fnDoSubmit(util.hasClass(this, 'fnExit'), 'NO', true);

    });

    var $fnContacts = $('.fnContacts');

    $fnContacts.on('click', '.fnAddContact', function () {

        $fnContacts.find('#fnContacts').append(document.getElementById('t-contact').innerHTML);

        util.resetName();

        setTelValid();

    }).on('click', '.fnDelContact', function () {

        $(this).parents('.fn-clear').remove();

    });

    function setTelValid() {
        $fnContacts.find('#fnContacts .contactMobile').each(function (index, el) {

            $(this).rules('add', {
                isPhoneOrMobile: true,
                messages: {
                    isPhoneOrMobile: '请输入正确的电话号码'
                }
            });

        });
    }

    setTimeout(function () {
        setTelValid();
    }, 0);

    // 是否必填
    $('.fnNeedInput').on('click', function () {
        var $this = $(this),
            $p = $this.parents('.m-item');
        if ($this.prop('checked')) {
            $this.parent().after('<span class="fn-f30 fnTipInput">*</span>');
            $p.find('.fnMakeMoney').addClass('fnInput').removeProp('readonly').rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        } else {
            $p.find('.fnTipInput').remove();
            $p.find('.fnMakeMoney').removeClass('fnInput').prop('readonly', 'readonly').rules('remove', 'required');
            $p.find('.fnMakeMoney').valid();
        }
    });
    $('.fnNeedInput2').on('click', function () {
        var $this = $(this),
            $p = $this.parents('.m-item');
        if ($this.prop('checked')) {
            $this.parent().find('.m-label').prepend('<span class="m-required">*</span>');
            $p.find('.fnMakeMoney').addClass('fnInput').removeProp('readonly').rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        } else {
            $p.find('.m-required').remove();
            $p.find('.fnMakeMoney').removeClass('fnInput').prop('readonly', 'readonly').rules('remove', 'required');
            $p.find('.fnMakeMoney').valid();
        }
    });

    // 续签合同
    $('#fnRenewal').on('click', function () {

        // 记录上次授信结束时间
        renewalDateE = $('#shouxinSel option').first().html().split('~')[1];
        // 加一天
        renewalDateE = laydate.now((new Date(renewalDateE)).getTime() + 24 * 60 * 60 * 1000);

        //续签合同状态设置为续签
        $('input[name=isXuQian]').val('IS');
        // 渠道名 编号 类型 设置为  只读  授信起始日必须大于先前的截止日 
        $('#fnChannelCode').attr('readonly', true);
        $('#fnChannelName').attr('readonly', true);
        // $('#fnChannelType').attr('disabled', true).after('<input type="hidden" name="channelType" value="' + $('#fnChannelType').val() + '">');

        document.getElementById('fnContacts').innerHTML = '';

        $('.fnUpAttachUl').html('');
        $('.fnUpAttachVal').val('');
        $form.find('input.ui-text:not(.fnRenewal)').val('')
            .end().find('[type="checkbox"]').removeProp('checked')
            .end().find('.fnTipInput').remove()
            .end().find('#fnTimeLimit').removeClass('fn-hide')
            .end().find('.fnNeedInput, .fnNeedInput2').each(function (i, e) {

                var $input = $(this).parents('.m-item').find('.fnMakeMoney');
                $input.prop('readonly', 'readonly');

                if ($input[0].name == 'percent') {
                    $input.val('10.00');
                }

                $input.rules('remove', 'required');

            });

        $form.find('select:not(.fnRenewal)').each(function (index, el) {

            $(this).val('').find('option').each(function (index, el) {

                $(this).removeProp('selected');

            });

        });

        $fnStartDate.attr('onclick', 'laydate({min: "' + renewalDateE + '"})');
        $fnEndDate.attr('onclick', 'laydate()');

        $('#shouxinSel').attr('disabled', 'disabled');

        // 续签 合同必须关联
        $('#fnChooseC').removeClass('fn-hide').parent().removeClass('fn-hide')
            .find('.m-label').prepend('<span class="m-required">*</span>')
            .end().find('input').rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })

        $('.fnNeedInput2, .fnNeedInput').each(function (index, el) {
            $(this).parents('.m-item').find('.m-required').remove()
                .end().find('.fnTipInput').remove()
        });

        // CONTRACT_LIST.resetAjaxUrl('/baseDataLoad/channalContrat.json?channelType=' + document.getElementById('fnChannelType').value + '&channalCode=' + document.getElementById('fnChannelCode').value)

        // 2016.11.03 续签必选合同
        // $('#fnContractNoBox').find('.m-label').prepend('<span class="m-required">*</span>')
        //     .end().find('#fnContractNo').rules('add', {
        //         required: true,
        //         messages: {
        //             required: '必填'
        //         }
        //     })

        $(this).remove();
    });

    // 查看
    $('#fnView').on('change', function () {

        if (!!this.value) {

            document.getElementsByTagName('form')[0].submit();

        }

    });

    // 代偿期限 无期限
    $('#fnNoTimeLimit').on('click', function () {
        if ($(this).prop('checked')) {
            $('#fnTimeLimit').addClass('fn-hide').find('[name="compensatoryLimit"]').val('-1')
        } else {
            $('#fnTimeLimit').removeClass('fn-hide').find('[name="compensatoryLimit"]').val('')
        }
    })

});