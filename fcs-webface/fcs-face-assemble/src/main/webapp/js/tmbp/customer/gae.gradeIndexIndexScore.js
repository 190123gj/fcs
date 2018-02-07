define(function(require, exports, module) {

    var util = require('util'),
        loading = new util.loading();
    //必填集合
    require('zyw/requiredRules');
    var hintPopup = require('zyw/hintPopup'),
        _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'thisScore,calculatingFormula,actualValue' + ',level,evaluetingInstitutions,evaluetingTime',
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
        maxlength20Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                maxlength: 200,
                messages: {
                    maxlength: '已超出200字'
                }
            };
        }),
        besidesRules = {
            auditImg: {
                required: true,
                messages: {
                    required: '请上传'
                }
            },
            actualValue: {
                hasClassNum: {
                    hasClass: 'needsss'
                },
                messages: {
                    hasClassNum: '请输入整数位20位以内，小数点1位的数字'
                }
            },
            thisScore: {
                onePlace: true,
                max: function(This) {

                    return parseFloat($(This).siblings('.maxSore').val() || 999999999999999999999999999);

                },
                messages: {
                    onePlace: '请输入整数位20位以内小数点1位的数字',
                    max: function(a, This) {

                        return '已超出满分：' + parseFloat($(This).siblings('.maxSore').val() || 0) + '分';

                    },

                }
            }
        },
        _rulesAll = $.extend(true, maxlength20Rules, requiredRules, besidesRules),
        gradeIndexCommon = require('zyw/customer/gae.gradeIndexCommon');

    //计算总分
    var $score = $('.score');

    $score.change(function(event) {

        var sum = 0;

        $score.each(function(index, el) {

            sum += parseFloat($(el).val() || 0);

        });

        $('#sumScore').text(sum.toFixed(1));
        $('#subSumScore').val(sum.toFixed(1)); // 提交总分
    });

    //汉字转拼音
    HanZiToSpell = require('zyw/HanZiToSpell');

    $('#HanZiToSpellObj tr').each(function(index, el) {

        var $el, $level1NameText, $evalueTypeChild, code;

        $el = $(el);
        $level1NameText = $el.find('.level1Name').text().replace(/\s/g, '');
        $evalueTypeChild = $el.find('.evalueTypeChild');
        code = ($level1NameText.length > 4) ? $level1NameText.substring(0, 4) : $level1NameText;

        $evalueTypeChild.val(HanZiToSpell.getFullChars(code));

    });

    //公共事业类异步计算请求

    $('body').on('change', '.actualValue,.calculatingFormula', function(event) {

        var $this, $siblings, $score, $maxSore, actualValue, calculatingFormula;

        $this = $(this);
        $siblings = $this.siblings('.siblings');
        $score = $this.siblings('.score');
        $maxSore = $this.siblings('.maxSore');
        maxSoreVal = parseFloat($maxSore.val());

        if (!$siblings.val() || !$this.val()) return false;

        if ($this.hasClass('actualValue')) {

            actualValue = $this.val();
            calculatingFormula = $siblings.val();

        } else {

            actualValue = $siblings.val();
            calculatingFormula = $this.val();

        }

        loading.open();

        $.ajax({
                url: '/customerMg/evaluting/calculating.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    actualValue: actualValue,
                    calculatingFormula: calculatingFormula
                },
            })
            .done(function(res) {

                setTimeout(function() {
                    loading.close();
                }, 100);

                if (res['success']) {

                    $maxSore.length ? (maxSoreVal < parseFloat(res['score']) ? $score.val(maxSoreVal) : $score.val(res['score'])) : $score.val(res['score']);
                    //$score.val(res['score']);
                    $score.next('em').hide();

                } else {

                    hintPopup(res['message']);

                }

            })
            .fail(function() {

                hintPopup('请求失败');

            })


    });

    gradeIndexCommon({
        form: _form,
        allWhetherNull: _allWhetherNull,
        rulesAll: _rulesAll,
        RulesInit: {
            rules: {
                'evaluetingInfo[3].thisScore': {
                    onePlace: true,
                    maxUp: 2,
                    min: 0,
                },
            },
            messages: {
                'evaluetingInfo[3].thisScore': {
                    onePlace: '请输入整数位20位以内小数点1位的数字',
                    maxUp: '超过最大值2分',
                    min: '低于最小值0分',
                },

            }
        }
    })

    if (!$('[mapInfo]').attr('mapInfo')) {

        $('[mapInfo]').hide();
        hintPopup('指标未配置！');

    }

    $('#subSumScore').addClass('cancel');

})