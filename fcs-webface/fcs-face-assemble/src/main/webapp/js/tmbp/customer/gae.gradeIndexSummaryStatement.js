define(function(require, exports, module) {
    var util = require('util'),
        loading = new util.loading();
    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'calculatingFormula,actualValue,thisScore' + ',level,evaluetingInstitutions,evaluetingTime',
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
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',remark', _allWhetherNull['boll'], {}, function(rules, name) {
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
                    hasClass: 'need'
                },
                messages: {
                    hasClassNum: '请输入整数位20位以内，小数点1位的数字'
                }
            },
            // thisScore: {
            //     // max: function(This) {

            //     //     if ($(This).siblings('.maxSore').length) return parseFloat($(This).siblings('.maxSore').val() || 0);

            //     // },
            //     requiredSelect: true,
            //     messages: {
            //         // max: function(a, This) {

            //         //     return '已超出满分：' + parseFloat($(This).siblings('.maxSore').val() || 0) + '分';

            //         // },
            //         requiredSelect: '必选'
            //     }
            // }
        },
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, besidesRules),
        gradeIndexCommon = require('zyw/customer/gae.gradeIndexCommon');

    //计算总分
    var $score = $('.score'),
        $gradeSelect = $('#gradeSelect');

    $score.change(function(event) {

        var sum = 0;

        $score.each(function(index, el) {

            sum += parseFloat($(el).val() || 0);

        });

        $('#sumScore').text(sum.toFixed(1));
        $('#subSumScore').val(sum.toFixed(1)); // 提交总分

        $gradeSelect.find('option').each(function(index, el) {

            var $el, min, max, val;

            $el = $(el);
            min = parseFloat($el.attr('min'));
            max = parseFloat($el.attr('max'));
            val = $el.val();

            if (!val) return true;

            if (index == 1 && sum >= max) {

                $gradeSelect.val(val);
                return false;

            }

            // if (index == $gradeSelect.find('option').length && sum < min) {

            //     $gradeSelect.val(val);
            //     return false;

            // }

            if (sum >= min && sum < max) {

                $gradeSelect.val(val);
                return false;

            }

        });

        if (!$gradeSelect.val()) $gradeSelect.val('F');

        $('.cancels').val($gradeSelect.val());

    });

    $('body').on('change', '.actualValue,.calculatingFormula', function(event) {

        var $this, $siblings, $score, actualValue, calculatingFormula, $maxSore, maxSoreVal, $divValue;

        $this = $(this);
        $siblings = $this.siblings('.siblings');
        $score = $this.siblings('.score');
        $divValue = $this.siblings('.divValue');
        $maxSore = $this.siblings('.maxSore');
        maxSoreVal = parseFloat($maxSore.val());

        if (!$siblings.val() || !$this.val()) return false;

        if ($this.hasClass('actualValue')) {

            actualValue = $this.val();
            calculatingFormula = $siblings.val();

        } else {

            actualValue = $siblings.val() || 0;
            calculatingFormula = $this.val();

        }

        if ($divValue.length) {

            actualValue = (actualValue * 100 / $divValue.val()).toFixed(1);

        }

        if (!/^\-?(0|([1-9]\d{0,19}))(\.\d)?$/.test(actualValue)) return false;

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
                    loading.close()
                }, 100);

                if (res['success']) {

                    $maxSore.length ? (maxSoreVal < parseFloat(res['score']) ? $score.val(maxSoreVal) : $score.val(res['score'])) : $score.val(res['score']);
                    $score.change();

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
        rulesAll: _rulesAll
    })


    $('.cancels').addClass('cancel checkStatusCancel');
    if ($('.cancels').length && $('.cancels').val()) $gradeSelect.val($('.cancels').val());

})