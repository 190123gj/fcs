define(function(require, exports, module) {

    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'thisScore,memo' + ',level,evaluetingInstitutions,evaluetingTime',
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
                maxlength: 20,
                messages: {
                    maxlength: '已超出20字'
                }
            };
        }),
        isGradeRules = {
            thisScore: {
                onePlace: true,
                messages: {
                    onePlace: '请输入小数点1位以内的数字',
                }
            },
            auditImg: {
                required: true,
                messages: {
                    required: '请上传'
                }
            }
        }
    _rulesAll = $.extend(true, maxlength20Rules, requiredRules, isGradeRules);
    gradeIndexCommon = require('zyw/customer/gae.gradeIndexCommon');

    gradeIndexCommon({
        form: _form,
        allWhetherNull: _allWhetherNull,
        rulesAll: _rulesAll,
        RulesInit: {
            rules: {
                'evaluetingInfo[0].thisScore': {
                    max: 10,
                    min: -5
                },
                'evaluetingInfo[1].thisScore': {
                    max: 0,
                    min: -5
                },
                'evaluetingInfo[2].thisScore': {
                    max: 5,
                    min: -5
                },
            },
            messages: {
                'evaluetingInfo[0].thisScore': {
                    max: '请输入-5.0~10.0的数字',
                    min: '请输入-5.0~10.0的数字',
                },
                'evaluetingInfo[1].thisScore': {
                    max: '请输入-5.0~0的数字',
                    min: '请输入-5.0~0的数字',
                },
                'evaluetingInfo[2].thisScore': {
                    max: '请输入-5.0~5.0的数字',
                    min: '请输入-5.0~5.0的数字',
                },
            }
        }
    })

})