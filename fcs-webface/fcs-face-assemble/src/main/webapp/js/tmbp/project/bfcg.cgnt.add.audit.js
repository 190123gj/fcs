define(function (require, exports, module) {
    // 费用及保证金收取通知
    require('Y-msg');
    // require('zyw/opsLine');
    // 输入提示
    require('Y-entertip');

    // 表单验证
    require('validate');
    require('validate.extend');
    // 弹窗
    require('Y-window');
    // 弹窗提示
    var hintPopup = require('zyw/hintPopup');
    // 页面共用
    require('zyw/project/assistsys.smy.Common');
    // Y.create('EnterTip', {
    // target: '.fnAmount',
    // mode: 'bankCard'
    // });
    // 上传
    require('zyw/upAttachModify');

    require('input.limit');

    var util = require('util');

    var searchForDetails = require('zyw/searchForDetails');
    // htmlAjax, selectHtml, scriptAddHtml;

    // htmlAjax = '<option value="">请选择</option>';


    // scriptAddHtml = function(html) {

    // var add, newAdd, htmlObj;

    // htmlObj = $('#t-tbodySFZL');

    // add = htmlObj.text();

    // newAdd = add.replace(/\<option[\s\S]+\/option\>/, html);

    // htmlObj.text(newAdd);

    // }
    var CUSTOMER_DEPOSIT_REFUND_DATA = {
        init: function () {

            var self = this;
            $.get('/baseDataLoad/loadChargePay.json?projectCode=' + document.getElementById('projectCode').value + '&affirmFormType=CAPITAL_APPROPROATION_APPLY&feeType=DEPOSIT_PAID&pageSize=999')
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
                                '    <td><input type="text" class="fnMakeMoney fnMakeMicrometer text" readonly maxnumber="' + obj.returnAmount.amount + '" minnumber="0" payid="' + obj.payId + '"></td>',
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

    // 项目编号
    if (document.getElementById('chooseBtn')) {
        searchForDetails({
            ajaxListUrl: '/baseDataLoad/chargeNotificationOrLetterProject.json?hasContract=IS&statusList=NORMAL,RECOVERY,TRANSFERRED,SELL_FINISH,FINISH,EXPIRE,OVERDUE',
            btn: '#chooseBtn',
            codeInput: 'projectCode',
            tpl: {
                tbody: ['{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow onbreaks">',
                    '        <td class="item onbreak" id="{{item.name}}">{{item.projectCode}}</td>',
                    '        <td class="onbreak" title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+"..":item.customerName}}</td>',
                    '        <td class="onbreak" title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+"..":item.projectName}}</td>',
                    '        <td class="onbreak">{{item.busiTypeName}}</td>',
                    '        <td class="onbreak">{{item.amount}}</td>',
                    '        <td class="onbreak"><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
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
            ajaxDetailsUrl: '/baseDataLoad/loadProjectData.json',
            fills: [{
                type: 'html',
                key: 'customerName',
                select: '#customerName'
            }, {
                type: 'html',
                key: 'projectName',
                select: '#projectName'
            }, {
                type: 'html',
                key: 'contractNumber',
                select: '#contractNumber'
            }, {
                type: 'html',
                key: 'creditType',
                select: '#creditType'
            }, {
                type: 'html',
                key: 'guarantyPeriodStart',
                select: '#guarantyPeriodStart'
            }, {
                type: 'html',
                key: 'guarantyPeriodEnd',
                select: '#guarantyPeriodEnd'
            }, {
                type: 'html',
                key: 'agencies',
                select: '#agencies'
            }, {
                type: 'html',
                key: 'amount',
                select: '#amount'
            }],
            callback: function (_data) {

                $('#customerName').val(_data.customerName).trigger('blur')
                $('#projectName').val(_data.projectName).trigger('blur')
                $('#customerId').val(_data.customerId).trigger('blur')
                    // $('#contractNo').val(_data.contractNo)
                $('#spCode').val(_data.spCode)
                $('#busiTypeName').text(_data.busiTypeName)
                $('[name="busiType"]').val(_data.busiType);
                $('[name="showFormChangeApply"]').val(_data.showFormChangeApply);
                $('#timeLimit').text(_data.timeLimit)
                if(_data.institutionNames && _data.institutionNames != 'null')
                $('#institutionName').text(_data.institutionNames)
                $('#amount').text(_data.formatAmount)
                $('#contractAmount').text(_data.contractAmount.standardString)
                $('#balance').text(_data.balance.cent < 0 ? "暂无" : _data.balance.standardString + "元")
                $('#chargeName').text(_data.chargeName + "：")
                $('#chargeFee').text(_data.chargeFee)
                $('#contractNo').val('');
                $('#contractName').val('');

                CUSTOMER_DEPOSIT_REFUND_DATA.init();

                // 加载分保信息
                $("#fenbaoList").load('/baseDataLoad/fenbaoInfo.htm?projectCode=' + _data.projectCode);

                $.ajax({
                        url: '/projectMg/chargeNotification/loadFeeTypeEnum.htm',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            projectCode: _data.projectCode
                        },
                    })
                    .done(function (res) {

                        if (res.success) {

                            var data, html, htmlObj, add, newAdd, i;

                            data = res.feeTypeEnum;
                            htmlObj = $('#t-tbodySFZL');
                            add = htmlObj.text();
                            html = '<option value="">请选择</option>';

                            for (i = 0; i < data.length; i++) {

                                html += '<option value="' + data[i].code + '">' + data[i].message + '</option>';

                            };

                            newAdd = add.replace(/\<option[\s\S]+\/option\>/, html);
                            // console.log(newAdd);
                            htmlObj.text(newAdd);
                            // htmlObj[0].innerText = 'newAdd'
                            // document.getElementById("t-tbodySFZL").innerText
							// = newAdd

                            $('.choice:eq(0) .feeTypeChange').each(function (index, el) { // 存在项添加

                                var This;

                                This = $(el);

                                This.html(html);

                            });

                            $('body').find('.fundCanPayBtn').remove();

                        } else {

                            hintPopup(res.message.split('：')[0] + "(加载收费种类失败)");

                        }

                    })
                    .fail(function () {
                        console.log("error");
                    });

            }
        });
    }

    var _projectCodeGloble = $('[name="projectCode"]').val();

    if (_projectCodeGloble) {

        CUSTOMER_DEPOSIT_REFUND_DATA.init();

    }



    // 合同编号
    var popupWindow = require('zyw/popupWindow');

    $('#contractNoBtn').click(function (event) {

        var $this = $(this),
            _projectCode = $('[name="projectCode"]').val();

        if (!_projectCode) {

            hintPopup('请先选择项目编号')
            return false;

        }

        fundDitch = new popupWindow({

            YwindowObj: {
                content: 'newPopupScript', // 弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', // find关闭弹窗对象
                dragEle: '.newPopup dl dt' // find拖拽对象contractNoBtn
            },

            ajaxObj: {
                url: '/projectMg/contract/contractChoose.htm',
                type: 'post',
                dataType: 'json',
                data: {
                    isChargeNotification: "IS",
                    projectCode: _projectCode,
                    pageSize: 6,
                    pageNumber: 1
                }
            },

            pageObj: { // 翻页
                clickObj: '.pageBox a.btn', // find翻页对象
                attrObj: 'page', // 翻页对象获取值得属性
                jsonName: 'pageNumber', // 请求翻页页数的dataName
                callback: function ($Y) {

                    // console.log($Y)

                }
            },

            callback: function ($Y) {

                $Y.wnd.on('click', 'a.choose', function (event) {

                    var _this = $(this),
                        _text = _this.parents('tr').find('td:eq(1)').text(),
                        _contractName = _this.parents('tr').find('td:eq(2)').text();

                    $this.siblings('#contractNo').val(_text).trigger('blur');
                    $this.parents('.choice').find('#contractName').val(_contractName).trigger('blur');
                    $Y.close();

                });

            },

            console: false // 打印返回数据

        });

    });

    // 签报
    $('#chooseBtn2').click(function (event) {

        var $this = $(this);
        // _projectCode = $('[name="projectCode"]').val();

        // if (!_projectCode) {

        // hintPopup('请先选择项目编号')
        // return false;

        // }

        fundDitch2 = new popupWindow({

            YwindowObj: {
                content: 'newPopupScript3', // 弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', // find关闭弹窗对象
                dragEle: '.newPopup dl dt' // find拖拽对象
            },

            ajaxObj: {
                url: '/projectMg/chargeNotification/loadSign.json',
                type: 'post',
                dataType: 'json',
                data: {
                    // isChargeNotification: "IS",
                    // projectCode: _projectCode,
                    pageSize: 6,
                    pageNumber: 1
                }
            },

            pageObj: { // 翻页
                clickObj: '.pageBox a.btn', // find翻页对象
                attrObj: 'page', // 翻页对象获取值得属性
                jsonName: 'pageNumber', // 请求翻页页数的dataName
                callback: function ($Y) {

                    // console.log($Y)

                }
            },

            formAll: { // 搜索
                submitBtn: '#PopupSubmit', // find搜索按钮
                formObj: '#PopupFrom', // find from
                callback: function ($wnd) { // 点击回调
                    // console.log($wnd)
                }
            },

            callback: function ($Y) {

                $Y.wnd.on('click', 'a.choose', function (event) {

                    var _this = $(this),
                        _text1 = _this.parents('tr').find('td:eq(1)').text(),
                        _text2 = _this.parents('tr').find('td:eq(2)').attr('customerName');

                    $('#applyTitle').val(_text2);
                    $this.siblings('#applyCode').val(_text1);

                    $Y.close();

                });

            },

            console: false // 打印返回数据

        });

    });


    // 字数提示
    require('zyw/hintFont');

    // 必填集合
    require('zyw/requiredRules');
    var _form = $('#addForm'),
        _allWhetherNull = {
            stringObj: 'payName,anotherPayName,projectCode,feeType,payName,contractCode,applyTitle,applyCode,contractName',
            especiallyStringObj: 'chargeRateStr,startTimeStr,endTimeStr,chargeBaseStr',
            boll: false,
            especiallyBoll: false
        },
        especiallyRules = _form.requiredRulesSharp(_allWhetherNull['especiallyStringObj'], _allWhetherNull['boll'], {}, function (rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',payAmount,anotherPayAmount', _allWhetherNull['boll'], {}, function (rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',payBank,anotherPayBank,anotherPayAccount,payAccount', _allWhetherNull['boll'], {}, function (rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        rulesAllBefore = { // 所有格式验证的基
            affirmDetailIds: {
                required: true,
                messages: {
                    required: '请选择存出保证金'
                }
            },
            remark: {
                maxlength: 1000,
                messages: {
                    maxlength: '已超出1000字'
                } // 必填.数字或字母
            },
            payAmount: {
                isMoney: true,
                min: 0.01,
                messages: {
                    isMoney: '请输入整数位14位以内，小数位2位以内的数字',
                    min: '请输入大于0的数字'
                }
            },
            anotherPayAmount: {
                isMoney: true,
                min: 0.01,
                messages: {
                    isMoney: '请输入整数位14位以内，小数位2位以内的数字',
                    min: '请输入大于0的数字'
                }
            },
            chargeBaseStr: {
                // min: 0.01,
                isMoneyCommon: true,
                messages: {
                    // min: '请输入大于0的数字',
                    isMoneyCommon: '请输入整数位13位以内，小数位2位以内的数字'
                }
            },
            chargeAmountStr: {
                // min: 0.01,
                required: true,
                isMoneyCommon: true,
                max: function (This) {

                    var $This, maxpay;

                    $This = $(This);
                    maxpay = parseFloat($This.parents('tr').attr('maxpay'));


                    return ($This.parents('tr').find('td:eq(0) select').val() == 'REFUNDABLE_DEPOSITS_DRAW_BACK' && maxpay) ? maxpay : 99999999999999999999999999999999;

                },
                messages: {
                    // min: '请输入大于0的数字',
                    required: function (a, This) {

                        var $this = $(This),
                            val = $this.parents('tr').find('.fundCanPay').val();

                        return val == 'REFUNDABLE_DEPOSITS_DRAW_BACK' ? '请选择存出保证金' : '必填';

                    },
                    isMoneyCommon: '请输入整数位13位以内，小数位2位以内的数字',
                    max: '超出所选存出保证金总和'
                }
            },
            chargeRateStr: {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入100.00以内的数字'
                }
            },
            payAccount: {
                bankSegmentation: true,
                messages: {
                    bankSegmentation: '请输入18-21位有效数字'
                }
            },
            // startTimeStr: {
            // max: function(This) {
            // var $this, $target;

            // $this = $(This);
            // $target = $this.parent().next().find('input');

            // return $target.val() ? $target.val() : '9999-99-99';
            // },
            // messages: {
            // max: '大于贷款止日'
            // }
            // },
            // endTimeStr: {
            // min: function(This) {

            // var $this, $target;

            // $this = $(This);
            // $target = $this.parent().prev().find('input');

            // return $target.val();

            // },
            // messages: {
            // min: '小于贷款起日'
            // }
            // }
        },
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),
        inherentSubmitValidataCommon = require('zyw/inherentSubmitValidataCommon');

    function ruleChange(condition) {

        var $Amount = $('.ruleChange');

        $Amount.each(function (index, el) {

            var $el = $(el);

            if (condition) {

                $el.rules('add', {
                    min: 0.01,
                    messages: {
                        min: '请输入大于0的数字'
                    }
                });

            } else {

                $el.rules('remove', 'min');

            }

        });



    }

    inherentSubmitValidataCommon({

        form: _form, // form
        allWhetherNull: _allWhetherNull, // 必填集合与是否反向判断
        rulesAll: _rulesAll, // 验证全集
        especiallyRules: especiallyRules, // 特殊变动验证
        changeStringFile: 'zyw/project/bfcg.cgnt.add.audit', // 特殊变动name
																// js文件地址

        allEvent: {

            replaceContentBtn: true, // 默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
            replaceBroadsideBtn: true, // 默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
            contentBtn: [{ // 内容区提交组
                clickObj: '#submit',
                eventFun: function (jsonObj) {

                    if (!CUSTOMER_DEPOSIT_REFUND_DATA.valid($('.fundCanPayBtn').parents('tr').find('.fnChooseMarginValue').val() || '')) {

                        hintPopup('存出保证金不足');
                        return false;

                    }

                    $('body').find('.feeTypeChange').change();
                    ruleChange(true);
                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); // 是必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'Y',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },
                        fm5: jsonObj.setInitVal['fm5']

                    }; // 向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); // validate文件获取数据

                    jsonObj.objList['form'].submit(); // 提交

                }
            }],
            broadsideBtn: [{ // 侧边栏提交组
                name: '暂存',
                alias: 'temporarStorage',
                events: function (jsonObj) {

                    var projectCode = $('#projectCode').val(),
                        applyCode = $('#applyCode').val();

                    if (!$('.choiceRadio [type="radio"]:checked').parent().index() && !projectCode) {

                        hintPopup('请先选择项目编号')
                        return false;

                    }

                    if ($('.choiceRadio [type="radio"]:checked').parent().index() && !applyCode) {

                        hintPopup('请先选择经纪业务单据')
                        return false;

                    }

                    var newRulesAll = $.extend(true, jsonObj.objList['especiallyRules'], jsonObj.objList['rulesAll']);

                    $.fn.whetherMust(newRulesAll, false).allAddValidata(newRulesAll); // 否必填
                    ruleChange(false);
                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'N',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },
                        fm5: jsonObj.setInitVal['fm5']

                    }; // 向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); // validate文件获取数据

                    jsonObj.objList['form'].submit(); // 提交

                }
            }, {
                name: '提交',
                alias: 'fulfilSubmit',
                events: function (jsonObj) {
                    // console.log($('.fundCanPayBtn').parents('tr').find('.fnChooseMarginValue').val());
                    if (!CUSTOMER_DEPOSIT_REFUND_DATA.valid($('.fundCanPayBtn').parents('tr').find('.fnChooseMarginValue').val() || '')) {

                        hintPopup('存出保证金不足');
                        return false;

                    }

                    $('body').find('.feeTypeChange').change();
                    ruleChange(true);
                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); // 是必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'Y',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },
                        fm5: jsonObj.setInitVal['fm5']

                    }; // 向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); // validate文件获取数据

                    jsonObj.objList['form'].submit(); // 提交

                }
            }, {
                name: '历史收费情况',
                alias: 'charge',
                events: function (jsonObj) {

                    var $index, $projectCode;

                    $index = $('.choiceRadio [type="radio"]:checked').parent().index();

                    $projectCode = $index ? $('[name="applyCode"]').val() : $('[name="projectCode"]').val()

                    if (!$projectCode) {

                        if ($index) {

                            hintPopup('请先选择经纪业务单据');

                        } else {

                            hintPopup('请先选择项目编号');

                        }

                        return false;

                    }

                    chargePopupWindow = new popupWindow({

                        YwindowObj: {
                            content: 'historyCharge', // 弹窗对象，支持拼接dom、template、template.compile
                            closeEle: '.close', // find关闭弹窗对象
                            dragEle: '.newPopup dl dt' // find拖拽对象
                        },

                        ajaxObj: {
                            url: '/projectMg/chargeNotification/showChargeHistory.htm',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                projectCode: $projectCode
                            }
                        },

                        console: false // 打印返回数据

                    });


                }
            }, {
                name: '查看合同',
                alias: 'temporarStorage2',
                events: function (jsonObj) {

                    var _projectCode = $('[name="projectCode"]').val();

                    if (!_projectCode) {

                        hintPopup('请先选择项目编号')
                        return false;

                    }

                    fundDitch = new popupWindow({

                        YwindowObj: {
                            content: 'newPopupScript2', // 弹窗对象，支持拼接dom、template、template.compile
                            closeEle: '.close', // find关闭弹窗对象
                            dragEle: '.newPopup dl dt' // find拖拽对象
                        },

                        ajaxObj: {
                            url: '/projectMg/contract/contractChoose.htm',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                projectCode: _projectCode,
                                isChargeNotification: "IS",
                                pageSize: 6,
                                pageNumber: 1
                            }
                        },

                        pageObj: { // 翻页
                            clickObj: '.pageBox a.btn', // find翻页对象
                            attrObj: 'page', // 翻页对象获取值得属性
                            jsonName: 'pageNumber', // 请求翻页页数的dataName
                            callback: function ($Y) {

                                // console.log($Y)

                            }
                        },

                        callback: function ($Y) {

                            $Y.wnd.on('click', 'a.choose', function (event) {

                                $Y.close();

                            });

                        },

                        console: true // 打印返回数据

                    });

                }
            }, {
                name: '查看项目批复',
                alias: 'temporarStorage5',
                events: function (jsonObj) {

                    var _projectCode = $('[name="spCode"]').val();

                    if (!_projectCode) {

                        hintPopup('请先选择项目编号')
                        return false;

                    }

                    window.open('/projectMg/meetingMg/summary/approval.htm?spCode=' + _projectCode, '_blank');

                }
            }, {
                name: '查看项目详情',
                alias: 'temporarStorage6',
                events: function (jsonObj) {

                    var _projectCode = $('[name="projectCode"]').val();

                    if (!_projectCode) {

                        hintPopup('请先选择项目编号')
                        return false;

                    }

                    window.open('/projectMg/viewProjectAllMessage.htm?projectCode=' + _projectCode, '_blank');

                }
            }, {
                name: $('[name="showFormChangeApply"]').val() ? '查看签报' : '',
                alias: 'temporarStorage61',
                events: function (jsonObj) {

                    var _projectCode = $('[name="projectCode"]').val();

                    if (!_projectCode) {

                        hintPopup('请先选择项目编号')
                        return false;

                    }

                    window.open('/projectMg/formChangeApply/list.htm?projectCode= ' + _projectCode + '&formStatus=APPROVAL', '_blank');

                }
            }]

        },

        ValidataInit: {

            // successFun: function(sss) {//成功响应
            // console.log(sss + 21321321)
            // },
            // successBeforeFun: function(sss) {//响应前
            // console.log(sss + 21321321)
            // },

            errorAll: { // validata error属性集

                errorClass: 'error-tip2',
                errorElement: 'em',
                // errorPlacement: function(error, element) {
                // element.after(error)
                // }

            }

        },

        callback: function (objList) { // 加载时回调

            // 验证方法集初始化
            $('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
                .initAllOrderValidata()
                .assignGroupAddValidataUp(objList['rulesAll'], function (hintPopup) {

                    var projectCode = $('[name="projectCode"]').val();

                    if ($('.choiceRadio [type="radio"]:checked').parent().index()) {

                        return true;

                    } else {

                        if (!projectCode) {

                            hintPopup('请先选择项目编号')
                            return false;

                        } else {

                            return true;

                        }

                    }



                });

            // 动态验证后几项
            $('body').on('change', '.feeTypeChange', function () {

                var _this = $(this),
                    _val = _this.val(),
                    _it = _this.parent().nextAll('td').find('.changeRules');

                if ((_val == 'GUARANTEE_FEE' || _val == 'ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL' || _val == 'ENTRUSTED_LOAN_INTEREST_WITHDRAWAL') && $('[name="busiType"]').val() != '211') {

                    _it.each(function (index, el) {

                        $(el).rules('add', {
                            required: true,
                            messages: {
                                required: '必填'
                            }
                        });
                        $(el).blur().removeClass('checkStatusCancel');

                    });

                    exports.portInitString = _allWhetherNull['stringObj'] + _allWhetherNull['especiallyStringObj'];

                } else {

                    _it.each(function (index, el) {

                        $(el).rules('remove', 'required');
                        $(el).blur().addClass('checkStatusCancel');

                    });

                    exports.portInitString = _allWhetherNull['stringObj'] + _allWhetherNull['especiallyStringObj'];

                }

            }).find('.feeTypeChange').trigger('change');

            // 资金划付

            $('.payId').each(function (i, el) {

                var $this, $target;

                $this = $(el);
                $target = $this.parents('tr');

                if ($this.val()) {

                    $target.attr('arr', $this.val().replace(/\,/g, '|'));

                }

            })

            function stringArr(allAmount, arr) {

                var string = new Array();

                for (var i in arr) {

                    if (allAmount <= 0) break;

                    allAmount -= parseFloat(arr[i].match(/.*(?=\|)/)[0]);

                    if (allAmount <= 0) {

                        string.push((parseFloat(arr[i].split('|')[0]) + allAmount) + ',' + arr[i].split('|')[1]);

                    } else {

                        string.push(arr[i].replace(/\|/, ','));

                    }


                }

                return string;

            }

            $('body').on('change', '.fundCanPay', function (event) {

                var $this, $btn, val;

                $this = $(this);
                $btn = $this.parents('tr').find('td:eq(7)');
                val = $this.val();

                if ($this.val() == 'REFUNDABLE_DEPOSITS_DRAW_BACK') {

                    if (!$btn.find('.fundCanPayBtn').length) {
                        $btn.append('<a class="ui-btn ui-btn-fill ui-btn-green fundCanPayBtn"' /*
																								 * +
																								 * (/^(([1-9][0-9]{0,13})|0)(\.[0-9]{1,2})?$/.test($this.parents('tr').find('td:eq(5)
																								 * input').val()) ? '' :
																								 * 'style="display:none"')
																								 */ + ' href="javascript:void(0);">选择存出保证金</a>')
                    }

                    $this.parents('tr').find('td:eq(5) input').attr('readonly', true);
                    $this.siblings('.fnChooseMarginValue').removeClass('cancel');

                } else {

                    $btn.find('.fundCanPayBtn').remove();

                    $this.parents('tr').find('td:eq(5) input').removeAttr('readonly');

                    $this.siblings('.fnChooseMarginValue').val('').blur().siblings('em.error-tip2').hide();
                    $this.siblings('.fnChooseMarginValue').addClass('cancel');

                }

                // console.log($this.parents('tr').siblings());

                $this.parents('tr').siblings().find('.fundCanPay').each(function (index, el) { // 防重复

                    if (val == 'REFUNDABLE_DEPOSITS_DRAW_BACK' && ($(el).val() == val)) {

                        hintPopup('不能选择多个存出保证金划回');
                        $this.val('');
                        $this.change();

                        return false;

                    }

                });

                // $this.parents('td').siblings().find('.fundCanPayAmount').blur();

                // $this.parents('tr').siblings().find('.fundCanPayAmount').blur();

            }).on('keyup', '.fundCanPayAmount', function (event) {

                // var $this, $target;

                // $this = $(this);
                // val = parseFloat($this.val());
                // $target =
				// $this.parents('tr').find('td:eq(7)').find('.fundCanPayBtn');

                // (/^(([1-9][0-9]{0,13})|0)(\.[0-9]{1,2})?$/.test($this.val()))
				// ? $target.show(): $target.hide();

                // var string = $this.parents('tr').attr('arr');

                // if (string) {

                // $this.parents('tr').find('.payId').val(stringArr(val,
				// string.split(';')).join(';'));

                // }

            }).on('click', '.fundCanPayBtn', function (event) {

                var $this;

                $this = $(this);

                // fundDitch = new popupWindow({

                // YwindowObj: {
                // content: 'newPopupScript4',
				// //弹窗对象，支持拼接dom、template、template.compile
                // closeEle: '.close', //find关闭弹窗对象
                // dragEle: '.newPopup dl dt' //find拖拽对象
                // },

                // ajaxObj: {
                // url: '/baseDataLoad/loadChargePay.json',
                // type: 'post',
                // dataType: 'json',
                // data: {
                // projectCode: $('[name="projectCode"]').val(),
                // affirmFormType: 'CAPITAL_APPROPROATION_APPLY',
                // feeType: 'DEPOSIT_PAID',
                // pageSize: 99999,
                // pageNumber: 1
                // }
                // },

                // pageObj: { //翻页
                // clickObj: '.pageBox a.btn', //find翻页对象
                // attrObj: 'page', //翻页对象获取值得属性
                // jsonName: 'pageNumber', //请求翻页页数的dataName
                // callback: function($Y) {

                // //console.log($Y)

                // }
                // },

                // callback: function($Y) {

                // //还原所选
                // var initArr;

                // initArr = $this.parents('tr').attr('arr');

                // if (initArr) {

                // initArr = initArr.split(';');

                // for (var a in initArr) {
                // //console.log(initArr[a],initArr[a].match(/\|(.*)/)[1])
                // $Y.wnd.find('input[type="checkbox"][value="' +
				// initArr[a].match(/\|(.*)/)[1] + '"]').attr('checked', true);

                // }

                // }

                // /////////////////////////////////

                // var arr, obj, allAmount, maxPay;

                // arr = new Array();
                // maxPay = 0;
                // allAmount =
				// parseFloat($this.parents('tr').find('.fundCanPayAmount').val());

                // $Y.wnd.on('click', '#confirm', function(event) {

                // $Y.wnd.find('[type="checkbox"]:checked').each(function(index,
				// el) {

                // var $this, id, amount;

                // $this = $(this);
                // amount = $this.parents('tr').find('td:eq(4)').attr('amount');
                // id = $this.val();

                // maxPay += parseFloat(amount);
                // arr.push(amount + '|' + id);

                // });

                // arr.sort(function(a, b) {

                // return parseFloat(a.match(/.*(?=\|)/)[0]) -
				// parseFloat(b.match(/.*(?=\|)/)[0]);

                // });

                // $this.parents('tr').find('.payId').val(stringArr(allAmount,
				// arr).join(';'));

                // $this.parents('tr').attr('arr', arr.join(';'));
                // $this.parents('tr').attr('maxPay', maxPay);
                // $this.parents('tr').find('.fundCanPayAmount').blur();
                // $Y.close();

                // });

                // },

                // console: true //打印返回数据

                // });
                CUSTOMER_DEPOSIT_REFUND_DATA.restore($this.parents('tr').find('.fnChooseMarginValue').val()).show().addCallback(function (res) {
                    // console.log($this.parents('tr').find('.fnChooseMarginMakeMoney'));
                    $this.parents('tr').find('.fnChooseMarginMakeMoney').val(res.total).trigger('focus').siblings('em.error-tip2').hide();
                    $this.parents('tr').find('.fnChooseMarginValue').val(res.value).trigger('blur').siblings('em.error-tip2').hide();
                    $this.parents('tr').find('.fnChooseMarginMakeMoney').trigger('blur');


                })


            }).find('.fundCanPay').change();

            // 单选选择
            $('body').on('change', '.choiceRadio input[type="radio"]', function (event) {

                var $this, $index, $target, $projectCode, $contractNo, $temporarStorage;

                $this = $(this);
                $index = $this.parent().index();
                $target = $('.choiceAll').find('.choice');
                $projectCode = $('#projectCode');
                $contractNo = $('#contractNo');
                $temporarStorage = $('[trigger="temporarStorage2"],[trigger="temporarStorage5"],[trigger="temporarStorage6"]');


                $target.eq($index).show().find('input,select').each($.repairName).end()
                    .siblings().hide().find('input,select').each($.destroyName);

                $target.eq($index).find('[orderName]').each($.repairOrderName).end()
                    .siblings().find('[orderName]').each($.destroyOrderName);
                // $index ? $projectCode.each($.repairName) :
				// $projectCode.each($.destroyName);

                var applyTitle = $('#applyTitle'),
                    isCopy = $('[name="isCopy"]'),
                    payName = $('.payName'),
                    customerName = $('#customerName');

                if ($index) {

                    $projectCode.each($.destroyName);
                    $contractNo.each($.destroyName);
                    $temporarStorage.hide();

                    // if(applyTitle.val()==''){
                    //
                    // isCopy.removeAttr('checked');
                    // payName.val('');
                    //
                    // }else{
                    //
                    // if(isCopy.is(':checked'))payName.val(applyTitle.val());
                    //
                    // }

                } else {

                    $projectCode.each($.repairName);
                    $contractNo.each($.repairName);
                    $temporarStorage.show();

                    // if(customerName.val()==''){
                    //
                    // isCopy.removeAttr('checked');
                    // payName.val('');
                    //
                    // }else{
                    //
                    // if(isCopy.is(':checked'))payName.val(customerName.val());
                    //
                    // }

                }

                if (!$('[name="formId"]').val()) {
                    isCopy.removeAttr('checked');
                    payName.val('');
                }


                // if (!selectHtml) {

                // selectHtml = $feeTypeChange.eq(0).html();

                // }

                // if ($index) {

                // scriptAddHtml(selectHtml);
                // $feeTypeChange.html(selectHtml);

                // } else {

                // scriptAddHtml(htmlAjax);
                // $feeTypeChange.html(htmlAjax);

                // }


            }).find('.choiceRadio [type="radio"]:checked').trigger('change');

        }

    });

    function toDay(date) {

        var timestamp, handleLaterDate;

        handleLaterDate = /\d{2}(\:)\d{2}(\:)\d{2}/.test(date) ? date.replace(/\-/g, '/') : (date + ' 00:00:00').replace(/\-/g, '/');
        timestamp = new Date(handleLaterDate).getTime();

        return timestamp / (3600000 * 24);

    }

    // 获取焦点
    $('body').on('focus', '.chargeAmountStr', function () {

        var _this = $(this),
            _prevAll = _this.parent().prevAll(),
            _cardinal = parseFloat(_prevAll.eq(3).find('.changeRules').val().replace(/\,/g, '') || 0),
            _rate = parseFloat(_prevAll.eq(2).find('.changeRules').val() || 0),
            _timeStart = _prevAll.eq(1).find('.changeRules').val(),
            _timeEnd = _prevAll.eq(0).find('.changeRules').val(),
            _em = _this.siblings('em.moneyHint');

        if (_cardinal == '' || _rate == '' || _timeStart == '' || _timeEnd == '') return false;

        var countExport = (_cardinal * _rate * (toDay(_timeEnd) - toDay(_timeStart) + 1) / 36000).toFixed(2);

        _em.show().text('应收金额：' + countExport.replace(/\B(?=(?:\d{3})+\b)/g, ',') + '元');

    }).on('blur', '.chargeAmountStr', function () {

        var _this = $(this),
            _em = _this.siblings('em.moneyHint');

        _em.hide();

    });

    // 银行卡
    $('body').on('keydown keyup', '.fnAmount', function (event) {

        var _this = $(this),
            _val = _this.val().replace(/[^\d]/g, '');

        _val ? _this.val(_val.match(/\d{4}|\d{1,4}/g).join(' ')) : _this.val('');

    });

    _form.on('click', 'input[name="isCopy"]', function () {
        var d = document,
            $target = $(this).parent().siblings('.payName');
        if (_form.find('input[name="isCopy"]:checked').val()) {

            if ($('.choiceRadio [type="radio"]:checked').parent().index()) {
                $target.val(d.getElementById('applyTitle').value)
            } else {
                $target.val(d.getElementById('customerName').value);
            }

            // d.getElementById('payAccount').value =
			// d.getElementById('cpayAccount').value;
            // _form.find('#payBank option[value="' +
			// d.getElementById('cpayBank').value + '"]').attr('selected',
			// 'selected').prop('selected', 'selected');
        } else {
            $target.val('');
            // d.getElementById('payAccount').value = '';
            // _form.find('#payBank
			// option').removeAttr('selected').removeProp('selected');
        }

    });

    // 付款显示
    $('body').on('change', '.paymentCheckbox', function (event) {

        var $this, $target;

        $this = $(this);
        $target = $this.parent().next();

        if ($this.is(':checked')) {

            $target.show().find('input').removeAttr('disabled').removeClass('checkStatusCancel');

        } else {

            $target.hide().find('input').attr('disabled', true).addClass('checkStatusCancel');

        }

    }).find('.paymentCheckbox').trigger('change');

    // $('input').change(function(event) {
    // console.log($(this).attr('class'))
    // });

    // $('body').on('blur', 'input', function(event) {

    // $(this).parents('tr').find('input').change();

    // });
    // 搜索框时间限制
    $('body').on('blur', '.fnListSearchDateS', function () {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateE');

        $input.attr('onclick', 'laydate({min: "' + this.value + '"})');

    }).on('blur', '.fnListSearchDateE', function () {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateS');

        $input.attr('onclick', 'laydate({max: "' + this.value + '"})');

    }).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');

    var projectCode = $('[name="projectCode"]').val();
    if (projectCode) {
        $("#fenbaoList").load('/baseDataLoad/fenbaoInfo.htm?projectCode=' + projectCode);
    }



});