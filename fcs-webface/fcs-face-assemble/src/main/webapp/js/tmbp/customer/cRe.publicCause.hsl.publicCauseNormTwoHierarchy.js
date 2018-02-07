define(function(require, exports, module) {

    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'level2Name,level2Score,compareMethod,compareStandardValue,level3Score',
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
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',level2Description,calculatingFormula', _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                maxlength: 200,
                messages: {
                    maxlength: '已超出200字'
                }
            };
        }),
        rulesAllBefore = {
            level2Score: {
                isGrade: true,
                groupSummationDown: true,
                messages: {
                    isGrade: '请输入100.0内的数字',
                    groupSummationDown: function(x, obj) {

                        var $obj = $(obj),
                            val = $obj.parent().siblings().eq(0).find('.maxScore').val();

                        return '该组超出总分' + val + '分';
                    }
                }
            },
            level3Score: {
                isGrade: true,
                max: function(This) {

                    var $this;

                    $this = $(This);
                    $target = parseFloat($this.parents('tr').find('.level2Score').val() || 0);

                    return $target

                },
                groupSummationNew: true,
                messages: {
                    isGrade: '请输入100.0内的数字',
                    max: function(x, This) {

                        var $this;

                        $this = $(This);
                        $target = parseFloat($this.parents('tr').find('.level2Score').val() || 0);
                        console.log($this.parents('tr').find('.level2Score'));
                        return '超出满分：' + $target + '分';

                    },
                    groupSummationNew: function(x, obj) {

                        var $obj = $(obj),
                            val = $obj.parents('tr').find('.maxScore').val();

                        return '该组一级指标总分：' + val + '分';
                    }
                }
            }
        },
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules, rulesAllBefore),

        commonTwoHierarchy = require('zyw/customer/cRe.commonTwoHierarchy');

    commonTwoHierarchy({
        form: _form,
        allWhetherNull: _allWhetherNull,
        rulesAll: _rulesAll,
        hintPopup: '请选择增加的一级指标选择项',
        href: 'publicCause.htm?level=3&type=GYYBZ',
        previewHref: 'publicCause.htm?level=2&type=GYYBZ&view=true'
    });



})