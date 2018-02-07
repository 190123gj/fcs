define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        _allWhetherNull = 'MReviewId,formId,projectCode,projectName,customerId,customerName,token,preUrl,nextUrl,submited,schemeId,cancel,industryCode,processFlag',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, true, {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('wu,preUrl,nextUrl', true, {}, function(rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRules2 = _form.requiredRulesSharp('amount', false, {}, function(rules, name) {
            rules[name] = {
                number: true,
                maxlength: 20,
                messages: {
                    number: '请输入浮点数',
                    maxlength: '已超过20字'
                }

            };
        }),
        requiredRulesPercent = _form.requiredRulesSharp('ratio,chargeRate', false, {}, function(rules, name) {
            rules[name] = {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字'
                }

            };
        }),
        requiredSpecialRate = _form.requiredRulesSharp('upDownRate,assetLiabilityRatio,thresholdRatio,adjustRatio', false, {}, function(rules, name) {
            rules[name] = {
                isSpecialRate: true,
                messages: {
                    isSpecialRate: '请输入整数8位以内,小数2位以内的数字'
                }

            };
        })
    requiredRulesMoneyMillion = _form.requiredRulesSharp('creditAmountStr,guaranteeAmountStr', false, {}, function(rules, name) {
            rules[name] = {
                isMoneyMillion: true,
                messages: {
                    isMoneyMillion: '请输入整数12位以内,小数2位以内的数字'
                }

            };
        }),
        requiredRulesMoney = _form.requiredRulesSharp('amountStr', false, {}, function(rules, name) {
            rules[name] = {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数14位以内,小数2位以内的数字'
                }

            };
        }),
        rulesAllBefore = { //所有格式验证的基
            timeLimit: {
                digits: true,
                maxlength: 8,
                messages: {
                    required: '必填',
                    digits: '请输入整数',
                    maxlength: '已超出最大长度8字'
                } //必填.正整数
            },
            warrantNo: {
                checkAZ09: true,
                messages: {
                    checkAZ09: '请输入数字或字母'
                } //必填.数字或字母
            },
            statement: {
                maxlength: 1000,
                messages: {
                    maxlength: '已超出最大长度1000字'
                }
            },
            content: {
                maxlength: 500,
                messages: {
                    maxlength: '已超出最大长度500字'
                }
            }
        },
        rulesAll = $.extend(true, requiredSpecialRate, requiredRulesMaxlength, requiredRules1, requiredRules2, requiredRulesPercent, requiredRulesMoneyMillion, requiredRulesMoney, rulesAllBefore);
    // //过程选择
    // $('#courseSelect dt input[type="checkbox"]').change(function(event) {
    //     var _this = $(this),
    //         _index = _this.index(),
    //         _checked = _this.attr('checked'),
    //         _courseSelectDd = $('#courseSelect dd');
    //     _checked ? _courseSelectDd.eq(_index).show().find('input,select').each(function(index, el) {
    //         $(this).removeClass('cancel');
    //     }) : _courseSelectDd.eq(_index).hide().find('input,select').each(function(index, el) {
    //         $(this).addClass('cancel');
    //     });
    // }).trigger('change');
    // //反担保子项有填其他子项必填
    // $('body').on('change', '[ordername="pledgeOrders"] input,[ordername="mortgageOrders"] input,[ordername="pledgeOrders"] select,[ordername="mortgageOrders"] select', function(event) {
    //     var _this = $(this),
    //         //_val = _this.val(),
    //         _parents = _this.parents('[ordername]'),
    //         _find = _parents.find('input,select'),
    //         _fill = true;
    //     //_val = _find.val();
    //     _find.each(function(index, el) {
    //         var _this = $(this),
    //             _val = _this.val();

    //     //console.log(_val)
    //         _val ? _fill &= false : _fill &= true;
    //     });
    //     //console.log(_fill)
    //     _fill ? _find.each(function(index, el) {
    //         var _this = $(el);
    //         _this.addClass('cancel').next('.error-tip').hide();
    //     }) : _find.each(function(index, el) {
    //         var _this = $(el);
    //         _this.removeClass('cancel').next('.error-tip').show();
    //     });
    // }).find('[ordername="pledgeOrders"] input,[ordername="mortgageOrders"] input,[ordername="pledgeOrders"] select,[ordername="mortgageOrders"] select').trigger('change');
    ValidataCommon(rulesAll, _form, _allWhetherNull, true, function(res) {
        showResult(res, hintPopup);
    });


    //反担保共用
    require('zyw/project/bfcg.itn.counterGuaranteeCommon');

    $('#trial').click(function() {
        $('body').Y('Window', {
            content: ['<div class="newPopup">',
                '    <span class="close">×</span>',
                '    <dl>',
                '        <dt><span>风险覆盖率试算结果</span></dt>',
                '        <dd class="fn-text-c">',
                '            <h1 class="fn-color-4a fn-mb25">该客户的风险覆盖率为<em class="remind">30%</em></h1>',
                '            <span>',
                '                <a class="fn-left ui-btn ui-btn-submit ui-btn-next fn-ml5 fn-mr5 fnNext fn-size16">客户评级</a>',
                '                <a class="fn-left ui-btn ui-btn-submit ui-btn-blue fn-ml5 fn-mr5 fnNext fn-size16">采用第三方评级结果</a>',
                '            </span>',
                '        </dd>',
                '    </dl>',
                '</div>'
            ].join(""),
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.close'
        });
        var _sum = 0,
            _creditAmountStr = parseFloat($('td.m-invndd-font [name="creditAmountStr"]').val().replace(',', '')) * 10000,
            _remind,
            modalwnd = Y.getCmp('modalwnd'),
            _riskCoverRate = parseFloat($('[name="riskCoverRate"]').val());
        $('body .pledgeTotal').each(function(index, el) {
            var _re = /(?:[1-9][0-9]*(?:\.[0-9]+)?|0(?:\.[0-9]+)?)/,
                _text = $(el).text();
            _text = _text.match(_re),
                _num = parseFloat(_text) || 0;
            _sum += _num;
        });
        _remind = _sum * 100 / _creditAmountStr;
        modalwnd.wnd.find('.remind').text(_remind.toFixed(2) + '%')
        if (_remind.toFixed(2) < _riskCoverRate) {
            modalwnd.wnd.find('h1').after('<p class="fn-mb45">低于公司设定的风险覆盖率阀值' + _riskCoverRate + '%</p>')
        }
    });




});