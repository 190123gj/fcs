define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //必填集合
    require('zyw/requiredRules');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //上传
    require('zyw/upAttachModify');
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        _allWhetherNull = 'downDesc,upDesc,pathName_FORM_ATTACH,name,MReviewId,schemeId,submited,preUrl,nextUrl,token,csrReviewId,formId,projectCode,projectName,customerId,customerName,cancel,remark,income1Str,income2Str,income3Str,incomeRatio1,incomeRatio2,incomeRatio3,reportPeriod1,reportPeriod2,reportPeriod3,explaination,customizeFieldMap_formRemark3,editorValue',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, true, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('downDesc,upDesc,wu,token,preUrl,nextUrl,pathName_FORM_ATTACH,strategyMarketpos,industryEnv,competitivenessRival,explaination', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRules2 = _form.requiredRulesSharp('wu', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    maxlength: '已超过10000字'
                },
                maxlength: 10000

            };
        }),
        requiredRules3 = _form.requiredRulesSharp('endingBalance,income1,income2,income3', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    number: '请输入正确的浮点数',
                    maxlength: '已超过20字'
                },
                number: true,
                maxlength: 20

            };
        }),
        requiredRulesPercentTwoDigits = _form.requiredRulesSharp('incomeRatio1,incomeRatio2,incomeRatio3', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字'
                },
                isPercentTwoDigits: true

            };
        }),
        requiredRulesMoney = _form.requiredRulesSharp('income1Str,income2Str,income3Str,endingBalanceStr', false, {}, function(rules, name) {
            rules[name] = {
                messages: {
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                },
                isMoney: true

            };
        }),
        rulesAllBefore = { //所有格式验证的基
            name: {
                requiredUp: true,
                messages: {
                    requiredUp: '必填'
                }
            },
            clearingForm: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            paymentTerms: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            endingBalanceStr: {
                required: true,
                isMoney: true,
                messages: {
                    required: '必填',
                    isMoney: '请输入整数为14位以内，小数位为两位的数字'
                }
            }
        },
        rulesAll = $.extend(true, requiredRulesMaxlength, requiredRules1, requiredRules2, requiredRules3, requiredRulesPercentTwoDigits, requiredRulesMoney, rulesAllBefore);

    // 保存并返回
    var ONLY_VALUE = 0

    $('#saveSubmit').click(function () {
        ONLY_VALUE = 1;
    })

    ValidataCommon(rulesAll, _form, _allWhetherNull, true, function(res) {
        if(ONLY_VALUE === 1){
            showResult(res, hintPopup(res.message,$("#backUrl").attr('data-url')));
        } else {
            showResult(res, hintPopup);
        }
    });

    //计算合计
    $('body').on('keyup', '.test tr td.endingBalanceStr', function(event) {
        if($(this).find('input').length) {
            var _this = $(this),
                _parents = _this.parents('.test'),
                _next = _parents.next(),
                _siblings = _parents.find('td.endingBalanceStr input'),
                _sum = 0;
            ///console.log(_this.length)
            _siblings.each(function (index, el) {
                var _val = parseFloat($(el).val().replace(/\,/g, ''));
                if (!_val) return false;
                _sum += parseFloat($(el).val().replace(/\,/g, ''))
                //console.log($(el).val())
            });
            //console.log(_sum.toFixed(2))
            _next.find('tr td.endingBalanceStr').text(_sum.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));
        }
    });

    $('.test').parent().click(function(event) {

        setTimeout(function() {

            var $target = $(this).find('td.endingBalanceStr input'),
                _next = $(this).find('.test').next(),
                _sum = 0;
            $target.each(function(index, el) {
                var _val = parseFloat($(el).val().replace(/\,/g, ''));
                if (!_val) return false;
                _sum += parseFloat($(el).val().replace(/\,/g, ''))
                    //console.log($(el).val())
            });

            _next.find('tr td.endingBalanceStr').text(_sum.toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));

            $(this).find('.test tr').length ? _next.show() : _next.hide();

        }.bind(this), 10)

    });

    $('.fnAddLineStater').click(function(event) {

        $(this).prev().find('.test').next().show();

    });

    //时间选择
    // $('#id').click(function(event) {
    //     var sel = $(this)
    //     laydate({
    //         elem: '#id', //需显示日期的元素选择器
    //         event: 'click', //触发事件
    //         format: 'YYYY-MM', //日期格式
    //         istime: false, //是否开启时间选择
    //         isclear: false, //是否显示清空
    //         istoday: true, //是否显示今天
    //         issure: true, //是否显示确认
    //         festival: true, //是否显示节日
    //         min: '1900-01-01 00:00:00', //最小日期
    //         max: '2099-12-31 23:59:59', //最大日期
    //         start: '2014-6-15 23:00:00',    //开始日期
    //         fixed: false, //是否固定在可视区域
    //         zIndex: 99999999, //css z-index
    //         choose: function(dates){ //选择好日期的回调
    //             var dates = dates.match(/\d+/g),
    //                 year = parseInt(dates[0]),
    //                 month = parseInt(dates[1]);
    //                 $('[name="productStructures[0].reportPeriod2"]').val('上一年（'+(year-1)+'年）');
    //                 $('[name="productStructures[0].reportPeriod3"]').val('上二年（'+(year-2)+'年）');
    //                 $('[name="productStructures[0].reportPeriod1"]').val('报告期（'+year+'年'+month+'月）');
    //                 sel.blur();
    //         }
    //     });
    // // var _this = $(this),
    // //     _val = _this.val();
    // //     console.log(_val)
    // });
    var yearsTime = require('zyw/yearsTime');
    $('#id').click(function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: '#id',
            callback: function(_this, _time) {
                if (_time) {
                    var _reportPeriod2 = '上一年（' + (_time[0] - 1) + '年）',
                        _reportPeriod3 = '上二年（' + (_time[0] - 2) + '年）'
                    _this.val('报告期(' + _time[0] + '年' + _time[1] + '月)');
                } else {
                    var _reportPeriod2 = '',
                        _reportPeriod3 = ''
                }
                $('[name="productStructures[0].reportPeriod2"]').val(_reportPeriod2);
                $('[name="productStructures[0].reportPeriod3"]').val(_reportPeriod3);
            }
        });
    });
    $('.yearsTimebBtn').click(function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: this
        });
    });


});