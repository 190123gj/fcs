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
                maxlength: 200,
                messages: {
                    maxlength: '已超出200字'
                }
            };
        }),
        // isGradeRules = {
        //     thisScore: {
        //         isGrade: true,
        //         max: 5,
        //         min: 0,
        //         messages: {
        //             isGrade: '请输入小数点1位以内的数字',
        //             max: '请输入0~5.0以内的数字',
        //             min: '请输入0~5.0以内的数字'
        //         }
        //     }
        // },
        besidesRules = {
            auditImg: {
                required: true,
                messages: {
                    required: '请上传'
                }
            }
        },
        _rulesAll = $.extend(true, maxlength20Rules, requiredRules, besidesRules),
        gradeIndexCommon = require('zyw/customer/gae.gradeIndexCommon');

    gradeIndexCommon({
        form: _form,
        allWhetherNull: _allWhetherNull,
        rulesAll: _rulesAll,
        RulesInit: {
            rules: {
                'evaluetingInfo[0].thisScore': {
                    isGradeUp: true,
                    max: 5,
                    min: 0,
                },
                'evaluetingInfo[1].thisScore': {
                    isGradeUp: true,
                    max: 5,
                    min: 0,
                },
                'evaluetingInfo[2].thisScore': {
                    place135: true,
                },
                'evaluetingInfo[3].thisScore': {
                    placeABC: true,
                },
                // 'evaluetingInfo[4].thisScore': {
                //     isGradeUp: true
                // }
            },
            messages: {
                'evaluetingInfo[0].thisScore': {
                    isGradeUp: '请输入小数点1位以内的数字',
                    max: '请输入0~5.0以内的数字',
                    min: '请输入0~5.0以内的数字'
                },
                'evaluetingInfo[1].thisScore': {
                    isGradeUp: '请输入小数点1位以内的数字',
                    max: '请输入0~5.0以内的数字',
                    min: '请输入0~5.0以内的数字'
                },
                'evaluetingInfo[2].thisScore': {
                    place135: '请输入分数1、3、5',
                },
                'evaluetingInfo[3].thisScore': {
                    placeABC: '请输入分数5、4、3.5、3、2.5、2',
                },
                // 'evaluetingInfo[4].thisScore': {
                //     isGradeUp: '请输入小数点1位以内的数字'
                // }
            }
        }
    })

    jQuery.validator.addMethod('place135', function(value, element, param) {
        var floatNum = /^(1|3|5)$/;
        return this.optional(element) || floatNum.test(value);
    }, $.validator.format("请输入正确的{0}"));

    jQuery.validator.addMethod('placeABC', function(value, element, param) {
        var floatNum = /^(5|4|(3\.5)|3|(2\.5)|2)$/;
        return this.optional(element) || floatNum.test(value);
    }, $.validator.format("请输入正确的{0}"));

    // 限制输入分数
    // _form.on('blur', '.fnInputLimit', function() {

    //     var _arr = eval(this.getAttribute('limitarr')),
    //         $this = $(this);

    //     setTimeout(function() {
    //         if ($.inArray($this.val(), _arr) == -1 && !!$this.val()) {
    //             $this.val('').trigger('keyup');
    //         }
    //     }, 10);

    // })

})