define(function(require, exports, module) {

    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'wu',
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
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',remark,level3Score,calculatingFormula', _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                maxlength: 200,
                messages: {
                    maxlength: '已超出200字'
                }
            };
        }),
        rulesAllBefore = {
            level3Score: {
                isGrade: true,
                max: function(This) {

                    var $this;

                    $this = $(This);
                    $target = parseFloat($this.parents('tr').find('.level2Score').val() || 0);

                    return $target

                },
                groupSummation: true,
                messages: {
                    isGrade: '请输入100.0内的数字',
                    max: function(x, This) {

                        var $this;

                        $this = $(This);
                        $target = parseFloat($this.parents('tr').find('.level2Score').val() || 0);

                        return '超出二级指标满分：' + $target + '分';

                    },
                    groupSummation: function(x, obj) {

                        var $obj = $(obj),
                            val = $obj.parent().siblings().eq(0).find('.maxScore').val();

                        return '超出该组满分：' + val + '分';
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
        hintPopup: '请选择增加的二级指标选择项',
        href: 'www.有支支招字哦.com',
        previewHref: 'publicCause.htm?level=3&type=GYYBZ&view=true'
    });



})