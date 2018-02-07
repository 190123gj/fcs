define(function (require, exports, module) {
    // 放用款申请
    require('zyw/publicPage');
    require('Y-msg');
    require('zyw/upAttachModify');
    require('validate');
    require('input.limit');

    var util = require('util');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    $('#list').on('click', '.notwithdraw', function () {
        Y.alert('提示', '您提交的申请单已进入审核流程，无法取消！');
    }).on('click', '.upload', function () {
        // var _this = $(this);
        // if (_this.hasClass('ing')) {
        //     return;
        // }
        // _this.addClass('ing');

        // var _thisTr = _this.parents('tr');
        // var applyId = _thisTr.attr('itemid');

        // util.ajax({
        //     url: '/projectMg/loanUseApply/receiptForm.htm',
        //     data: {
        //         'applyId': applyId
        //     },
        //     dataType: 'html',
        //     success: function (res) {
        //         $('#ajaxForm').html(res);
        //         $fnUpload.removeClass('fn-hide');
        //     }
        // });
    });

    var $fnInputList = $('#fnInputList')
    var $fnInputListAdd = $('#fnInputListAdd')
    var $fnCallBackForm = $('#fnCallBackForm')

    if (!!$fnInputList.length) {

        util.resetName()
        $fnCallBackForm.validate(util.validateDefault)
        setRequired()

        $fnCallBackForm.on('click', '.fnDoSave', function () {

            if (!$fnCallBackForm.valid()) {
                Y.alert('提示', '请填写完整表单数据')
                return
            }

            var $fnThisApplyMoney = $fnInputList.find('.fnThisApplyMoney')
            var _total = 0

            if (!!$fnThisApplyMoney.length) {

                $fnThisApplyMoney.each(function (index, el) {

                    _total = util.accAdd(_total, +(el.value || '').replace(/\,/g, ''))

                })


            } else {

                // 每个数据是否又有选择金额
                var _allHasM = true
                var _allHasMEq;
                $fnInputList.find('.fnInputM').each(function (index, el) {

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

                $fnInputList.find('.fnMakeMoney:not([disabled])').each(function (index, el) {

                    _total = util.accAdd(_total, +(el.value || '').replace(/\,/g, ''))

                })

            }

            if (_total > +document.getElementById('fnThisApply').innerHTML.replace(/\s/g, '').replace(/\,/g, '')) {
                Y.alert('提示', '实际放款金额不能大于可回执金额')
                return
            }

            util.resetName()

            var toRepayPlan = this.className.indexOf('to') >= 0

            util.ajax({
                url: $fnCallBackForm.attr('action'),
                data: $fnCallBackForm.serializeJCK(),
                success: function (res) {
                    if (res.success) {
                        if (toRepayPlan) {
                            window.location.href = '/projectMg/chargeRepayPlan/form.htm?IsChargePlan=NO&isRepayPlan=IS&projectCode=' + $('#receiptProjectCode').val()
                            return
                        }
                        Y.alert('操作成功', res.message, function () {
                            window.location.reload(true)
                        })
                    } else {
                        Y.alert('提示', res.message)
                    }
                }
            });


        })

    }

    $fnInputListAdd.on('click', function () {

        $fnInputList.append(document.getElementById('t-input-list').innerHTML).find('.capitalChannelSel').last().trigger('change')
        util.resetName()
        setRequired()

    })

    $fnInputList.on('click', '.fnDelTr', function () {

        $(this).parents('tr').remove()
        util.resetName()

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
    }).on('change', '.capitalChannelSel', function () {

        var $this = $(this)
        var $option = $this.find('option:selected')

        $this.parent().find('input').each(function (index, el) {
            el.value = $option.attr(el.className) || ''
        })

        $this.parent().next().text($option.attr('subChannelName') || '')

    })

    function setRequired() {

        $fnInputList.find('.fnInput').each(function (index, el) {

            var $this = $(this)

            $this.rules('remove', 'required')
            $this.rules('remove', 'min')

            $this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })

            if ($this.hasClass('fnMakeMoney')) {
                $this.rules('add', {
                    min: 0.01,
                    messages: {
                        min: '金额需要大于0'
                    }
                })
            }

        })

    }


    // var $fnUpload = $('#fnUpload');

    // $fnUpload.on('click', '.fnUploadX', function () {
    //     $('#ajaxForm').html('');
    //     $(".upload").removeClass('ing');
    //     $fnUpload.addClass('fn-hide');
    //     // window.location.reload(true);
    // }).on('click', '.check', function () {

    //     var _this = $(this),
    //         _thisInput = _this.parent().parent().find('input').eq(1);

    //     if (_this.prop('checked')) {
    //         _thisInput.removeClass('disabled').removeProp('disabled')
    //     } else {
    //         _thisInput.addClass('disabled').prop('disabled', 'disabled')
    //     }

    // }).on('click', '.del', function () {
    //     $(this).parents('tr').remove();
    // }).on('change', '.capitalChannelSel', function () {
    //     var _this = $(this);
    //     var _option = _this.find("option:selected");
    //     var channelId = _option.val(),
    //         channelCode = _option.attr('channelCode'),
    //         channelType = _option.attr('channelType'),
    //         channelName = _option.attr('channelName'),
    //         subChannelId = _option.attr('subChannelId'),
    //         subChannelCode = _option.attr('subChannelCode'),
    //         subChannelType = _option.attr('subChannelType'),
    //         subChannelName = _option.attr('subChannelName');
    //     _this.parent().find(".channelId").val(channelId);
    //     _this.parent().find(".channelCode").val(channelCode);
    //     _this.parent().find(".channelType").val(channelType);
    //     _this.parent().find(".channelName").val(channelName);
    //     _this.parent().find(".subChannelId").val(subChannelId);
    //     _this.parent().find(".subChannelCode").val(subChannelCode);
    //     _this.parent().find(".subChannelType").val(subChannelType);
    //     _this.parent().find(".subChannelName").val(subChannelName);
    //     _this.parent().parent().find(".tdSubChannelName").text(subChannelName);
    // }).on('click', '.fnUploadBtn', function () {

    //     var _this = $(this);
    //     if (_this.hasClass('ing')) {
    //         return;
    //     }
    //     _this.addClass('ing');

    //     // 本次申请金额
    //     var THIS_APPLY_MONEY = +$fnUpload.find('#fnThisApply').text().replace(/\,/g, '');

    //     var _isPass = true,
    //         $fnInput = $('.fnInput');

    //     $fnInput.each(function (index, el) {
    //         if (!!!el.value.replace(/\s/g, '')) {
    //             _isPass = false
    //             return false
    //         }
    //     })

    //     if (!_isPass) {
    //         Y.alert('提示', '请输入完整信息');
    //         _this.removeClass('ing')
    //         return
    //     }

    //     var $fnThisApplyMoney = $fnUpload.find('.fnThisApplyMoney')

    //     if (!!$fnThisApplyMoney.length) {

    //         var _val = $fnThisApplyMoney.val()

    //         if (!!!_val || +_val == 0) {
    //             Y.alert('提示', '请填写金额')
    //             _this.removeClass('ing')
    //             return
    //         }

    //         // 是否超过了申请金额
    //         if (+_val > THIS_APPLY_MONEY) {
    //             Y.alert('提示', '当前金额大于本次申请金额，请重新核实')
    //             _this.removeClass('ing')
    //             return
    //         }

    //     } else {

    //         var _money = 0,
    //             _allHasValue = true

    //         $fnUpload.find('.fnMakeMoney:not(.disabled)').each(function (index, el) {

    //             if (!!el.value) {
    //                 _money += +el.value
    //             } else {
    //                 _allHasValue = false
    //                 return false
    //             }

    //         })

    //         if (!_allHasValue) {
    //             Y.alert('提示', '请对所选的实际放款金额填写数值')
    //             _this.removeClass('ing')
    //             return
    //         }

    //         if (_money > THIS_APPLY_MONEY) {
    //             Y.alert('提示', '当前金额大于本次申请金额，请重新核实')
    //             _this.removeClass('ing')
    //             return
    //         }

    //     }

    //     util.resetName();

    //     $toRepayPlan = !!_this.attr('toRepayPlan');

    //     util.ajax({
    //         url: '/projectMg/loanUseApply/saveReceipt.htm',
    //         data: $fnUpload.find('form').serialize(),
    //         success: function (res) {
    //             if (res.success) {
    //                 if ($toRepayPlan) {
    //                     window.location.href = "/projectMg/chargeRepayPlan/form.htm?IsChargePlan=NO&isRepayPlan=IS&projectCode=" + $fnUpload.find('#receiptProjectCode').val();
    //                     return
    //                 }
    //                 Y.alert('操作成功', res.message, function () {
    //                     window.location.reload(true);
    //                 });
    //             } else {
    //                 Y.alert('提示', res.message);
    //             }
    //             _this.removeClass('ing');
    //         }
    //     });

    // }).on('click', '.fnAddTr', function (event) {

    //     var $tbody = $fnUpload.find('tbody')
    //     var $tr = $tbody.find('tr').first()

    //     var $new = $('<tr></tr>').html($tr.html()).attr('diyname', $tr.attr('diyname'));

    //     $new.find('.laydate-icon').val('')
    //         .end().find(':checked').removeProp('checked')
    //         .end().find('.fnMakeMoney').val('').addClass('disabled').prop('disabled', 'disabled')
    //         .end().find('td').last().html('<a href="javascript: void(0);" class="del">删除</a>')
    //         .end().find('option').each(function (index, el) {
    //             this.selected = false
    //         })

    //     $tbody.append($new)

    // });

    $("#formStatus").change(function () {
        if ($(this).val() == "APPROVAL") {
            $("#hasReceipt").show();
        } else {
            $("#hasReceipt").hide();
            $("[name=hasReceipt]").val("");
        }
    }).change();
});