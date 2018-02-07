define(function(require, exports, module) {

    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'level3Name',
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
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules),

        commonTwoHierarchy = require('zyw/customer/cRe.commonTwoHierarchy');

    commonTwoHierarchy({
        form: _form,
        allWhetherNull: _allWhetherNull,
        rulesAll: _rulesAll,
        hintPopup: '请选择增加的指标说明选择项',
        href: 'ordinary.htm?level=4&type=YBQY',
        previewHref: 'ordinary.htm?level=4&type=YBQY&view=true'
    });

})