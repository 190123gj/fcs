define(function(require, exports, module) {

    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'compareMethod,compareStandardValue,level3Score',
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
        rulesAllBefore = {
            level3Score: {
                isGrade: true,
                groupSummation: true,
                messages: {
                    isGrade: '请输入100以内小数点1位的数字',
                    groupSummation: function(x, obj) {

                        var $obj = $(obj),
                            val = $obj.parent().siblings().eq(0).find('.maxScore').val();

                        return '该组超出总分' + val + '分';
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
        href: 'www.有支支招字哦.com',
        previewHref: 'cityEnterprise.htm?level=3&type=CTBZ&view=true'
    });



})