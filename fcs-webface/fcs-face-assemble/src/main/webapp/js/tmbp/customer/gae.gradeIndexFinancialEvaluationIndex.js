define(function(require, exports, module) {

    //必填集合
    require('zyw/requiredRules');
    var hintPopup = require('zyw/hintPopup'),
        _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'actualValue,memo' + ',level,evaluetingInstitutions,evaluetingTime',
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
                maxlength: 20,
                messages: {
                    maxlength: '已超出20字'
                }
            };
        }),
        isGradeRules = _form.requiredRulesSharp('thisScore', false, {}, function(rules, name) {
            rules[name] = {
                onePlace: true,
                max: function(This) {

                    return parseFloat($(This).parent().siblings('.maxScore').val() || 999999999999999999999999999);

                },
                messages: {
                    onePlace: '请输入整数位20位以内小数点1位的数字',
                    max: function(a, This) {

                        return '已超出满分：' + parseFloat($(This).parent().siblings('.maxScore').val() || 0) + '分';

                    },

                }
            };
        }),
        besidesRules = {
            // actualValue: {
            //     isSignNum: true,
            //     messages: {
            //         isSignNum: '请输入14位数字，有效小数2位的数字',
            //     }
            // },
            memo: {
                isSignNum: true,
                messages: {
                    isSignNum: '请输入14位数字，有效小数2位的数字',
                }
            },
            auditImg: {
                required: true,
                messages: {
                    required: '请上传'
                }
            }
        },
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, besidesRules, isGradeRules),
        gradeIndexCommon = require('zyw/customer/gae.gradeIndexCommon');

    $('body').find('[newOrderName]').each(function(index, el) {

        var $el, newOrderNameIndex, newOrderName;

        $el = $(el);
        newOrderNameIndex = index;
        newOrderName = $el.attr('newOrderName');

        $el.find('[name]').each(function(index, el) {

            var $el, name;

            $el = $(el);
            name = $el.attr('name');
            newName = newOrderName + "[" + newOrderNameIndex + "]." + name;

            $el.attr('name', newName);

        });

    });

    var $sumScore = $('.sumScore');

    $('.thisScore').change(function() {

        var $this, $start, $end, $all, sum, $target;

        $this = $(this);
        $start = $(this).parents('tr').prevAll('.start').eq(0);
        $end = $start.nextAll('.end').eq(0);
        $all = $start.nextUntil($end[0]);
        sum = 0;
        $target = $end.find('.sumScore');

        $all.each(function(index, el) {

            var $el, val;

            $el = $(el);

            if (!/^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$/.test($el.find('.thisScore').val())) return true;

            val = parseFloat($el.find('.thisScore').val()) || 0;

            sum += val;

        });

        $target.val(sum.toFixed(1));
        $sumScore.eq(0).trigger('change');

    })

    $sumScore.change(function() {

        var $totalSun, sum;

        $totalSun = $('.totalSun');
        sum = 0;

        $sumScore.each(function(index, el) {

            var $el;

            $el = $(el);

            sum += parseFloat($el.val() || 0);

        });

        $totalSun.val(sum.toFixed(1));

    }).eq(0).trigger('change');

    $('body').on('change', '.showMemo', function(event) {

        var $this, val, $target;

        $this = $(this);
        val = $this.val();
        $target = $this.parents('td').siblings('.calculatingFormula');

        if (!/^([-]?(([1-9][0-9]{0,13})|0)(\.[0-9]{1,2})?)||\s$/.test(val)) return false;

        $target.val(val ? $target.attr('calculatingFormula').replace(/jxjll/g, val) : $target.attr('calculatingFormula'));
        $this.parent().prevAll('.actualValueBox').find('input').trigger('change');

    });

    gradeIndexCommon({
        form: _form,
        allWhetherNull: _allWhetherNull,
        rulesAll: _rulesAll,
        RulesInit: {
            rules: {
                'evaluetingInfo[5].actualValue': {
                    isSignNum: true,
                },
            },
            messages: {
                'evaluetingInfo[5].actualValue': {
                    isSignNum: '请输入14位数字，有效小数2位的数字',
                },
            }
        }
    })

    if (!$('[mapInfo]').attr('mapInfo')) {

        $('[mapInfo]').hide();
        hintPopup($('#financeName').val() + '财务指标未配置！');

    }

    //    var regular = {
    //         '0': new RegExp('^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$'), ///^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$/,
    //         '1': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$/,
    //         '2': new RegExp('^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$'), ///^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$/,
    //         '3': new RegExp('^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$'), ///^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$/,
    //        '4': new RegExp('^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$'), ///^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$/,
    //        '5': new RegExp('^[-]?(([1-9][0-9]{0,11})|0)(\\.[0-9]{1,2})?$'), ///^(([1-9][0-9]{0,7})|0)(\\.[0-9]{1,2})?$/,
    //         '6': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$/,
    //         '7': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$/,
    //         '8': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$/,
    //         '9': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$/,
    //         '10': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,1})?$|^100(\.0{0,1})?$/,
    //         '11': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$/,
    //         '12': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$/,
    //         '13': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$/,
    //         '14': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$/,
    //         '15': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$/,
    //         '16': new RegExp('^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$'), ///^(0|[1-9][0-9]{0,1})(\.[0-9]{0,2})?$|^100(\.0{0,2})?$/,
    //    }

    var util = require('util'),
        hintPopup = require('zyw/hintPopup'),
        loading = new util.loading();

    $('.actualValue').change(function() {

        var $this, actualValue, memo, $index, $target, $maxScore, maxSoreVal;

        $this = $(this);
        actualValue = $this.val();
        memo = $this.parents('td').siblings('.calculatingFormula').val();
        $index = $('tbody').find('[newOrderName]').index($this.parents('[newOrderName]'));
        $target = $this.parents('td').next().find('.thisScore ');
        $maxScore = $this.parents('td').siblings('.maxScore');
        maxSoreVal = parseFloat($maxScore.val());

        if (!/^[-]?(0|([1-9][0-9]{0,11}))(\.[0-9]{1,2})?$/.test(actualValue) || !memo || /jxjll/.test(memo)) {

            //$target.val('');

            if ($this.parent()[0].tagName !== 'SPAN') $target.removeAttr('readonly');

        } else {

            loading.open();
            //console.log(memo);
            $.ajax({
                    url: '/customerMg/evaluting/calculating.json',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        actualValue: actualValue,
                        calculatingFormula: memo
                    },
                })
                .done(function(res) {

                    setTimeout(function() {
                        loading.close()
                    }, 100);

                    if (res['success']) {

                        $maxScore.length ? (maxSoreVal < res['score']) ? $target.val(maxSoreVal).attr('readonly', true) : $target.val(res['score']).attr('readonly', true) : $target.val(res['score']).attr('readonly', true);

                        $('.thisScore').trigger('change');

                    } else {

                        hintPopup(res['message']);

                    }

                })
                .fail(function() {

                    hintPopup('请求失败');

                });

        }

    }).not('[readonly]').trigger('change');



})