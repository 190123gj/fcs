define(function (require, exports, module) {

    require('./finance.audit.common')

    var util = require('util')

    var CUSTOMER_DEPOSIT_REFUND_DATA = {
        init: function () {

            var self = this;
            $.get('/baseDataLoad/loadChargePay.json?projectCode=' + document.getElementById('projectCode').value + '&affirmFormType=CAPITAL_APPROPROATION_APPLY&feeType=DEPOSIT_PAID&expectFormId=' + document.getElementById('expectFormId').value + '&pageSize=999')
                .done(function (res) {

                    var _tbody = ''

                    if (res.success) {

                        var _data = {}

                        $.each(res.data.pageList, function (index, obj) {

                            _tbody += [
                                '<tr>',
                                '    <td class="fn-tac">存出保证金</td>',
                                '    <td>' + obj.payAmount.standardString + '</td>',
                                '    <td class="fn-tac">' + obj.payTime + '</td>',
                                '    <td>' + obj.returnAmount.standardString + '</td>',
                                '    <td class="fn-tac"><input type="checkbox" class="checkbox"></td>',
                                '    <td><input type="text" class="fnMakeMoney fnMakeMicrometer text" readonly maxnumber="' + obj.returnAmount.amount + '" minnumber="0.01" payid="' + obj.payId + '"></td>',
                                '</tr>'
                            ].join('')

                            _data[obj.payId] = obj.returnAmount.amount

                        })

                        self.data = _data

                    }

                    var _html = [
                        '    <div class="m-modal-overlay"></div>',
                        '    <div class="m-modal m-modal-default" id="modal">',
                        '        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span style="position: absolute; left: 25px; top: 20px;">本次申请退还金额：<span id="total">0</span>&nbsp;元</span><span class="title">存出保证金</span></div>',
                        '        <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner">',
                        '            <table class="m-table m-table-list"><thead><tr><th width="80px">费用种类</th><th>付款金额（元）</th><th width="80px">付款时间</th><th>未退还金额（元）</th><th>本次<br>是否退还</th><th>本次申请退还金额</th></tr></thead><tbody>',
                        _tbody,
                        '            </tbody></table>',
                        '    </div></div></div>',
                        '    <div class="fn-mt10 fn-tac"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-mr10 sure">确定</a><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-ml10 close">取消</a></div>'
                    ].join('')

                    self.$box.html(_html)

                    self.$total = self.$box.find('#total')

                })

            self.$box.appendTo('body')
                .on('click', '.checkbox', function () {

                    var $input = $(this).parent().next().find('.fnMakeMoney')

                    if (this.checked) {
                        $input.removeProp('readonly').val(util.num2k((+$input.attr('maxnumber')).toFixed(2)))
                    } else {
                        $input.prop('readonly', 'readonly').val('')
                    }
                })
                .on('click', '.close', function () {
                    self.hide()
                })
                .on('click', '.sure', function () {

                    var _res = self.getTotal()

                    if (_res.total === 0) {
                        Y.alert('提示', '请选择保证金')
                        return
                    }

                    if (!!self.callback) {
                        self.callback(_res)
                    }

                    self.hide()

                })
                .on('click', '.checkbox', function () {
                    self.$total.html(util.num2k(self.getTotal().total))
                })
                .on('blur', '.fnMakeMoney', function () {
                    setTimeout(function () {
                        self.$total.html(util.num2k(self.getTotal().total))
                    }, 50)
                })

            return this

        },
        $box: $('<div></div>').addClass('m-modal-box fn-hide'),
        getTotal: function () {

            var self = this

            var _total = 0,
                _valArr = []

            self.$box.find('td .fnMakeMoney:not([readonly])').each(function (index, el) {

                _total = util.accAdd(_total, +(el.value || '').replace(/\,/g, ''))

                _valArr.push([(el.value || '').replace(/\,/g, ''), el.getAttribute('payid')].join(','))

            })

            return {
                total: _total,
                value: _valArr.join(';')
            }

        },
        show: function () {
            this.$box.removeClass('fn-hide')
            return this
        },
        hide: function () {
            this.$box.addClass('fn-hide')
            return this
        },
        restore: function (str) {

            var self = this

            var _arr = str.split(';')

            var _total = 0;

            self.$box.find('td .fnMakeMoney').each(function (index, el) {
                    el.readonly = true
                    el.value = ''
                })
                .end().find('td .checkbox').each(function (index, el) {
                    el.checked = false
                })

            $.each(_arr, function (index, item) {
                if (!!item) {
                    var _a = item.split(',')

                    self.$box.find('td .fnMakeMoney[payid="' + _a[1] + '"]').val(util.num2k(_a[0])).removeProp('readonly')
                        .parent().prev().find('input').prop('checked', 'checked')

                    _total += +_a[0]

                }
            })

            self.$total.html(util.num2k(_total))

            return self
        },
        addCallback: function (fn) {
            this.callback = fn
            return this
        },
        valid: function (str) {

            var self = this

            var _arr = str.split(';')

            // 超过、或没有
            var _isPass = true
            $.each(_arr, function (index, item) {
                if (!!item) {
                    var _a = item.split(',')
                        // 金额已经用完
                    if (!!!self.data[_a[1]]) {
                        _isPass = false
                        return false
                    }

                    // 使用金额大于可用的金额
                    if (+self.data[_a[1]] < +_a[0]) {
                        _isPass = false
                        return false
                    }

                }
            })

            return _isPass;

        }
    }

    CUSTOMER_DEPOSIT_REFUND_DATA.init();

    $('body').on('click', '.fundCanPayBtn2', function (event) {

        var $this;

        $this = $(this);


        CUSTOMER_DEPOSIT_REFUND_DATA.restore($this.parents('tr').find('.fnChooseMarginValue').val()).show().addCallback(function (res) {

            $this.parents('tr').find('.fnChooseMarginMakeMoney').val(res.total).trigger('focus')
                .end().find('.fnChooseMarginValue').val(res.value)
                .end().find('.fnChooseMarginMakeMoney').trigger('blur')

            // setTimeout(function () {
            //     $this.parents('tr').find('.fnNeedToAdd').trigger('blur')
            // }, 50)

        })


    })

    var popupWindow = require('zyw/popupWindow');

    // 审核通用部分 start
    var auditProject = require('/js/tmbp/auditProject');
    var _auditProject = new auditProject();
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url').addAuditBefore(function (dtd) {

        if (!CUSTOMER_DEPOSIT_REFUND_DATA.valid($('.fundCanPayBtn2').parents('tr').find('.fnChooseMarginValue').val() || '')) {

            return dtd.reject({
                message: '存出保证金不足'
            });

        } else {

            return dtd.resolve();

        }


    });

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.addOPN([{
        name: '查看合同',
        alias: 'temporarStorage2',
        event: function (jsonObj) {

            fundDitch = new popupWindow({

                YwindowObj: {
                    content: 'newPopupScript2', //弹窗对象，支持拼接dom、template、template.compile
                    closeEle: '.close', //find关闭弹窗对象
                    dragEle: '.newPopup dl dt' //find拖拽对象
                },

                ajaxObj: {
                    url: '/projectMg/contract/contractChoose.htm',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        projectCode: $('[projectCode]').attr('projectCode'),
                        isChargeNotification: "IS",
                        pageSize: 6,
                        pageNumber: 1
                    }
                },

                pageObj: { //翻页
                    clickObj: '.pageBox a.btn', //find翻页对象
                    attrObj: 'page', //翻页对象获取值得属性
                    jsonName: 'pageNumber', //请求翻页页数的dataName
                    callback: function ($Y) {

                        //console.log($Y)

                    }
                },

                callback: function ($Y) {

                    $Y.wnd.on('click', 'a.choose', function (event) {

                        $Y.close();

                    });

                },

                console: true //打印返回数据

            });

        }
    }, {
        name: $('[name="showFormChangeApply"]').val() ? '查看签报' : '',
        alias: 'temporarStorage612',
        event: function (jsonObj) {

            window.open('/projectMg/formChangeApply/list.htm?projectCode= ' + $('[name="projectCode"]').val() + '&formStatus=APPROVAL', '_blank');

        }
    }]).init().doRender();
    //------ 侧边栏 end

    //上传
    require('zyw/upAttachModify');

    $('.fundCanPayBtn').click(function (event) {

        fundDitch = new popupWindow({

            YwindowObj: {
                content: 'newPopupScriptss', //弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', //find关闭弹窗对象
                dragEle: '.newPopup dl dt' //find拖拽对象
            },

            // ajaxObj: {
            //  url: '/projectMg/contract/contractChoose.htm',
            //  type: 'post',
            //  dataType: 'json',
            //  data: {
            //      projectCode: $('[projectCode]').attr('projectCode'),
            //      isChargeNotification: "IS",
            //      pageSize: 6,
            //      pageNumber: 1
            //  }
            // },

            // pageObj: { //翻页
            //  clickObj: '.pageBox a.btn', //find翻页对象
            //  attrObj: 'page', //翻页对象获取值得属性
            //  jsonName: 'pageNumber', //请求翻页页数的dataName
            //  callback: function($Y) {

            //      //console.log($Y)

            //  }
            // },

            // callback: function($Y) {

            //  $Y.wnd.on('click', 'a.choose', function(event) {

            //      $Y.close();

            //  });

            // },

            // console: true //打印返回数据

        });

    });
    //  ------  打印 start

    $('#fnPrint').click(function (event) {

        var $fnPrintBox = $('#fnPrintBox')

        $fnPrintBox.find('.ui-btn-submit').remove()

        $fnPrintBox.find('.printshow').removeClass('fn-hide')

        util.print($fnPrintBox.html())
    })

    //  ------- 打印 end


})