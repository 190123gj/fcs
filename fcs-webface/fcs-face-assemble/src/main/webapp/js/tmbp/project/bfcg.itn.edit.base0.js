define(function (require, exports, module) {

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
    //选择授信用途
    var loanPurpose = require('zyw/chooseLoanPurpose');

    loanPurpose.init('LOAN_PURPOSE')
    if(!$('#version').val()){
        $('.fnAddLine').click(function() {
            var _this = $(this),
                _tplID = _this.attr('tplID'),
                _cttID = _this.attr('cttID');
            if(_tplID == 't-testChooseChannel') {
                $('#' + _cttID).append($('#' + _tplID).html());
                $.fn.orderName();
                $('#testChooseChannel .fn-font-b2').last().find('.remind').hide()
            }
        });
    }
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),

        requiredRulesMaxlength = _form.requiredRulesSharp('customizeFieldMap_assessment,customizeFieldMap_opinion,assetRemark,other,statement,assetType,ownershipName,evaluationPrice,pledgeRate,pathName_FORM_ATTACH,preUrl,nextUrl,pathName_COUNTER_GUARANTEE,upBp,downBp,assetLiabilityRatio,upRate,downRate,repayWay,pathName_INVESTIGATION_REPORT', true, {}, function (rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRulesPercent = _form.requiredRulesSharp('ratio,upRate,downRate', false, {}, function (rules, name) {
            rules[name] = {
                isPercentTwoDigits: true,
                messages: {
                    isPercentTwoDigits: '请输入0.01-100之间的数字'
                }

            };
        }),
        requiredSpecialRate = _form.requiredRulesSharp('upBp,downBp,upDownRate,assetLiabilityRatio,thresholdRatio,adjustRatio', false, {}, function (rules, name) {
            rules[name] = {
                isSpecialRate: true,
                messages: {
                    isSpecialRate: '请输入整数8位以内,小数2位以内的数字'
                }

            };
        }),
        requiredRulesMoneyMillion = _form.requiredRulesSharp('upBp,downBp', false, {}, function (rules, name) {
            rules[name] = {
                isMoneyMillion: true,
                messages: {
                    isMoneyMillion: '请输入整数12位以内,小数2位以内的数字'
                }

            };
        }),
        requiredRulesMoney = _form.requiredRulesSharp('creditAmountStr,amountStr', false, {}, function (rules, name) {
            rules[name] = {
                isMoney: true,
                messages: {
                    isMoney: '请输入整数14位以内,小数2位以内的数字'
                }

            };
        });

    if(!$('#version').val()) {
        var rulesAllBefore = {
            customizeFieldMap_opinion:{
                required: true,
                messages: {
                    required: '必填'
                }
            },
            synPosition: {
                    required: true,
                    maxlength: 20,
                    messages: {
                        required: '必填',
                        maxlength: '已超过20字'
                    }
                },
            postposition: {
                required: true,
                maxlength: 20,
                messages: {
                    required: '必填',
                    maxlength: '已超过20字'
                }
            },
            debtedAmountStr: {
                isMoneyCommon: true,
                messages: {
                    isMoneyCommon: '请输入整数13位以内,小数2位以内的数字'
                }
            },
            timeLimit: {
                digits: true,
                maxlength: 8,
                messages: {
                    required: '必填',
                    digits: '请输入整数',
                    maxlength: '已超出最大长度8字'
                } //必填.正整数
            },
            // other: {
            //     maxlength: 10000,
            //     messages: {
            //         maxlength: '已超出最大长度10000字'
            //     }
            // },
            // statement: {
            //     maxlength: 10000,
            //     messages: {
            //         maxlength: '已超出最大长度10000字'
            //     }
            // },
            content: {
                maxlength: 20000,
                messages: {
                    maxlength: '已超出20000字'
                }
            },
            guarantorType: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            guarantor: {
                required: true,
                maxlength: 20,
                messages: {
                    required: '必填',
                    maxlength: '已超过20字'
                }
            },
            guaranteeAmountStr: {
                required: true,
                isMoney: true,
                messages: {
                    required: '必填',
                    isMoney: '请输入整数14位以内,小数2位以内的数字'
                }
            },
            guaranteeWay: {
                // required: true,
                maxlength: 500,
                messages: {
                    // required: '必填'
                    maxlength: '已超出500字'
                }
            },
            repayWay: {
                maxlength: 1000,
                messages: {
                    // required: '必填'
                    maxlength: '已超出1000字'
                }
            },
            subChannelName:{
                required: false
            }
        },
            requiredRules2 = _form.requiredRulesSharp('amount', false, {}, function (rules, name) {
                rules[name] = {
                    number: true,
                    maxlength: 20,
                    messages: {
                        number: '请输入浮点数',
                        maxlength: '已超过20字'
                    }

                };
            }),
            _allWhetherNull = 'assetRemark,guaranteeWay,depositAccount,debtedAmountStr,debtedAmount,pathName_FORM_ATTACH,itype,projectSubChannelId,capitalSubChannelId,other,pathName_COUNTER_GUARANTEE,MReviewId,formId,projectCode,projectName,customerId,customerName,token,preUrl,nextUrl,submited,schemeId,cancel,industryCode,processFlag,customizeFieldMap_formRemark0,pathName_INVESTIGATION_REPORT'
    } else {
        var rulesAllBefore = { //所有格式验证的基
            synPosition: {
                required: true,
                maxlength: 20,
                messages: {
                    required: '必填',
                    maxlength: '已超过20字'
                }
            },
            postposition: {
                required: true,
                maxlength: 20,
                messages: {
                    required: '必填',
                    maxlength: '已超过20字'
                }
            },
            debtedAmountStr: {
                isMoneyCommon: true,
                messages: {
                    isMoneyCommon: '请输入整数13位以内,小数2位以内的数字'
                }
            },
            timeLimit: {
                digits: true,
                maxlength: 8,
                messages: {
                    required: '必填',
                    digits: '请输入整数',
                    maxlength: '已超出最大长度8字'
                } //必填.正整数
            },
            // other: {
            //     maxlength: 10000,
            //     messages: {
            //         maxlength: '已超出最大长度10000字'
            //     }
            // },
            // statement: {
            //     maxlength: 10000,
            //     messages: {
            //         maxlength: '已超出最大长度10000字'
            //     }
            // },
            content: {
                maxlength: 20000,
                messages: {
                    maxlength: '已超出20000字'
                }
            },
            guarantorType: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            guarantor: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            guaranteeAmountStr: {
                required: true,
                isMoney: true,
                messages: {
                    required: '必填',
                    isMoney: '请输入整数14位以内,小数2位以内的数字'
                }
            },
            guaranteeWay: {
                // required: true,
                maxlength: 500,
                messages: {
                    // required: '必填'
                    maxlength: '已超出500字'
                }
            },
            repayWay: {
                maxlength: 1000,
                messages: {
                    // required: '必填'
                    maxlength: '已超出1000字'
                }
            }
        },
            requiredRules2 = _form.requiredRulesSharp('amount,guarantor', false, {}, function (rules, name) {
                rules[name] = {
                    number: true,
                    maxlength: 20,
                    messages: {
                        number: '请输入浮点数',
                        maxlength: '已超过20字'
                    }

                };
            }),
            _allWhetherNull = 'assetRemark,guaranteeWay,depositAccount,debtedAmountStr,debtedAmount,pathName_FORM_ATTACH,itype,projectSubChannelId,capitalSubChannelId,other,pathName_COUNTER_GUARANTEE,MReviewId,formId,projectCode,projectName,customerId,customerName,token,preUrl,nextUrl,submited,schemeId,cancel,industryCode,processFlag,customizeFieldMap_formRemark0,pathName_INVESTIGATION_REPORT'
    }
    var requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, true, {}, function (rules, name) {
        rules[name] = {
            required: true,
            messages: {
                required: '必填'
            }
        };
    })
    var rulesAll = $.extend(true, requiredSpecialRate, requiredRulesMaxlength, requiredRules1, requiredRules2, requiredRulesPercent, requiredRulesMoneyMillion, requiredRulesMoney, rulesAllBefore);

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

    var ONLY_VALUE = 0

    $('#saveSubmit').click(function () {
        ONLY_VALUE = 1;
    })

    ValidataCommon(rulesAll, _form, _allWhetherNull, true, function (res) {
        if(ONLY_VALUE === 1){
            showResult(res, hintPopup(res.message,$("#backUrl").attr('data-url')));
        } else {
            showResult(res, hintPopup);
        }

    }, function () {
        require('zyw/project/bfcg.itn.counterGuaranteeCommon')(rulesAll, _form, _allWhetherNull, true);
    });

    //单位改变验证
    require('zyw/project/assistsys.smy.unitChange');

    $('#trial').click(function () {
        $('body').Y('Window', {
            content: ['<div class="newPopup">',
                '    <span class="close">×</span>',
                '    <dl>',
                '        <dt><span>风险覆盖率试算结果</span></dt>',
                '        <dd class="fn-text-c">',
                '            <h1 class="fn-color-4a fn-mt10">该客户的风险覆盖率为<em class="remind">30%</em></h1>',
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
            _creditAmountStr = parseFloat($('td.m-invndd-font [name="creditAmountStr"]').val().replace(/\,/g, '')),
            _remind,
            modalwnd = Y.getCmp('modalwnd'),
            _riskCoverRate = parseFloat($('[name="riskCoverRate"]').val());

        $('body .pledgeTotal').each(function (index, el) {
            var _re = /(?:[1-9][0-9]*(?:\.[0-9]+)?|0(?:\.[0-9]+)?)/,
                _text = $(el).text().replace(/\,/g, '').match(_re),
                // _text = _text.match(_re),
                _num = parseFloat(_text) || 0;
            _sum += _num;
        });
        // console.log(_sum);
        //         $('.debtedAmount').each(function(index, el) {

        //             if (/^(([1-9][0-9]{0,13})|0)(\\.[0-9]{1,2})?$/.test($(this).val())) return true;
        //             _sum -= (parseFloat($(this).val()) || 0);
        // console.log('--------')
        //         });

        _remind = _sum * 100 / _creditAmountStr;

        modalwnd.wnd.find('.remind').text(_remind.toFixed(2) + '%');

        if (_remind.toFixed(2) < _riskCoverRate) {
            modalwnd.wnd.find('h1').after('<p class="fn-mb45 fn-mt25">' +
                '低于公司设定的风险覆盖率阀值' +
                _riskCoverRate +
                '%</p>' +
                '<span>' +
                '  <a class="fn-left ui-btn ui-btn-submit ui-btn-next fn-ml5 fn-mr5 fnNext fn-size16" href="/customerMg/evaluting/add.htm?userId=' + $('#customerId').val() + '" target="_blank">客户评级</a>' +
                '</span>')
        }
    });

    // 选择渠道 start

    var getChannel = require('zyw/getChannel');

    var CHANNEL_LIST = new getChannel('#fnChooseChannelBtn');
    CHANNEL_LIST.init({
        callback: function ($a) {

            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelName').val($a.attr('ccname')).trigger('blur')
            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelId').val($a.attr('ccid')).trigger('blur')
            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelCode').val($a.attr('cccode')).trigger('blur')
            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelType').val($a.attr('cctype')).trigger('blur')
        }
    });

    var $CHOOSE_CHANNEL_BOX;

    $('body').on('click', '.fnChooseChannelBtn', function () {
        $CHOOSE_CHANNEL_BOX = $(this).parent()
        CHANNEL_LIST.query()

    })

    // 选择渠道 end

    //hao坑o 
    $(".MyNewAddLine").click(function () {
        var parent = $(this).parents('.fnCourseSelectitem')
        setTimeout(function () {

            var allInput = parent.find('td .fnChangeInput');
            allInput.each(function () {


                if($(this).attr('name').indexOf('downRate') != -1 ){

                    $(this).attr('name','debtRatios['+$(this).parents('tbody').index()+'].downRate')

                }else if($(this).attr('name').indexOf('downBp') != -1){

                    $(this).attr('name','debtRatios['+$(this).parents('tbody').index()+'].downBp')

                }
            })

        },500)
    })

    //

    //$('#zxCustomerjson').val()

    $('body').click(function () {
        if(!$('#version').val()) {
            $('.fnChooseChannel').each(function () {
                if($(this).closest('.testChooseChannel').length) {
                    if ($(this).find('.fnChooseChannelType').val() == 'YH') {
                        $(this).next().find('.remind').show()
                        $(this).next().next().find('input').each(function (index, el) {
                            if($(el).attr('type')=="text"){
                                $(el).rules('add', {
                                    required: true,
                                    messages: {
                                        required: '必填'
                                    }
                                })
                                $(el).removeAttr('aria-required').removeClass('cancel');
                            }
                        })
                    } else {
                        $(this).next().find('.remind').hide()
                        $(this).next().next().find('input').each(function (index, el) {
                            if($(el).attr('type')=="text") {
                                $(el).rules('add', {
                                    required: false
                                })
                                $(el).removeAttr('aria-required').addClass('cancel');
                            }
                        })
                    }
                }
            })

          $('[name="customizeFieldMap_opinion"],[name="customizeFieldMap_assessment"],[name="statement"]').each(function (index, el) {
              if(!$(el).val().trim()) {
                  $(el).val('')
              }
          })
        }
    }).trigger('click')
    $('#downloadTooltip').hover(function () {
        var _this = $(this);
        $('.hoverTooltip').css('top','30px').empty().append('<a href="'+_this.attr('hover-url')+'" >'+_this.attr('hover')+'</a>')
    })
});