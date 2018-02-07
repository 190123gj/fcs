define(function (require, exports, module) {
    // 项目管理 > 授信前管理 > 合同回传
    require('input.limit');
    require('zyw/upAttachModify');
    require('Y-msg');
    require('validate');
    require('Y-number');
    require('Y-number');
    require('input.limit');
    var util = require('util');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    //BPM弹窗
    var BPMiframe = require('BPMiframe');
    var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=false';
    var BPMiframeConf = {
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'selectUsers': {
            selectUserIds: '',
            selectUserNames: ''
        },
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    };

    var _chooseLegalManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
        title: '人员选择'
    }));

    $('.fnChoose').on('click', function () {

        var _thisP = $(this).parent(),
            _$name = _thisP.find('.personName'),
            _$id = _thisP.find('.personId'),
            _$personAccount = _thisP.find('.personAccount');

        _chooseLegalManager.obj.selectUsers.selectUserIds = _$id.val();
        _chooseLegalManager.obj.selectUsers.selectUserNames = _$name.val();

        _chooseLegalManager.init(function (relObj) {

            _$id.val(relObj.userIds);
            _$name.val(relObj.fullnames);
            _$personAccount.val(relObj.accounts);

        });
    });

    util.resetName();

    var $form = $('#form'),
        requiredRules = {
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {

            // 是否是同一个人
            // var _isPerson = false,
            //     _isPersonId;
            // $('.personId').each(function (index, el) {
            //
            //     if (index === 0) {
            //         _isPersonId = el.value
            //     } else {
            //
            //         if (_isPersonId == el.value) {
            //             _isPerson = true;
            //         }
            //
            //     }
            //
            // });
            //
            // if (_isPerson) {
            //     Y.alert('提示', '签订人不能选择同一个人');
            //     return;
            // }

            // 如果需要填写金额
            if (!!$fnChannelList.length) {

                // 每个数据是否又有选择金额
                var _allHasM = true
                var _allHasMEq;
                $fnChannelList.find('.fnInputM').each(function (index, el) {

                    if (!!!$(this).find('.fnMakeMoney:not([disabled])').length) {
                        _allHasM = false
                        _allHasMEq = index
                        return false
                    }

                })

                if (!_allHasM) {
                    Y.alert('提示', '第' + (_allHasMEq + 1) + '行未选择金额')
                    return
                }

            }

            util.ajax({
                url: $form.attr('action'),
                data: $form.serializeJCK(),
                success: function (res) {
                    if (res.success) {
                        Y.alert('提示', '已保存', function () {
                            if(window.location.href.indexOf("applyType=PROJECT_LETTER")>=0){
                                window.location.href = '/projectMg/contract/list.htm?applyType=PROJECT_LETTER';
                            }else if(window.location.href.indexOf("applyType=PROJECT_DB_LETTER")>=0){
                                window.location.href = '/projectMg/contract/list.htm?applyType=PROJECT_DB_LETTER';
                            } else {
                                window.location.href = '/projectMg/contract/list.htm';
                            }

                        });

                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });


        }
    }))

    // 合同回传 仅支持 图片、PDF
    $('#fnPassBackUpFile').attr('filetype', util.fileType.img + '; *.pdf; *.rar; *.zip');

    var $fnContractTotal = $('#fnContractTotal')
    var $fnChannelList = $('#fnChannelList');

    //zhurui 触发校验
    setTimeout(function () {
        $fnContractTotal.trigger('focus');
    },1)

    $fnContractTotal.on('blur', function () {

        document.getElementById('fnContractTotalCapital').innerHTML = Y.Number(+(this.value).replace(/\,/g,'')).digitUppercase()

    })


    /*$('#fnContractTotal').blur(function () {
        if($(this).val().replace(/\,/g,'')<=0){
            Y.alert('提示', '请输入正确的合同金额！');
            $(this).val(0.01).blur();
        }
    })*/


    $('#fnContractTotal').rules('add', {
        required: true,
        min: 0.01,
        messages: {
            required: '必填',
            min: '金额需要大于0'
        }
    })

    $fnChannelList.on('blur', '.fnMakeMoney', function () {

        var _total = 0

        setTimeout(function () {

            $fnChannelList.find('.fnMakeMoney:not([disabled])').each(function (index, el) {

                _total = util.accAdd(_total, +(el.value || '').replace(/\,/g, ''))

            })

            $fnContractTotal.val(_total.toFixed(2)).trigger('blur')

        }, 50)

    }).on('click', '.check', function () {
        var _this = $(this),
            _thisInput = _this.parent().parent().find('input').eq(1);

        if (_this.prop('checked')) {
            _thisInput.removeClass('disabled').removeProp('disabled')
            _thisInput.rules('add', {
                required: true,
                min: 0.01,
                messages: {
                    required: '必填',
                    min: '金额需要大于0'
                }
            })
        } else {
            _thisInput.addClass('disabled').prop('disabled', 'disabled')
            _thisInput.rules('remove', 'required')
            _thisInput.rules('remove', 'min')
            _thisInput.valid()
        }

        _thisInput.trigger('blur')

    })


});