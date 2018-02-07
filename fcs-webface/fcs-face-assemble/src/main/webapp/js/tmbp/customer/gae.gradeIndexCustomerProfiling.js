define(function(require, exports, module) {

    //上传
    require('zyw/upAttachModify')
        //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'loanCardNo,actualCapital,subordinateRelationship,showScale,salesProceedsLastYear,accountNo' + ',level,evaluetingInstitutions,evaluetingTime',
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
        isMoneyMillionRules = _form.requiredRulesSharp('actualCapital,salesProceedsLastYear', _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                isMoneyMillion: true,
                messages: {
                    isMoneyMillion: '请输入整数位12位，小数点两位内的的正数'
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
            loanCardNo: {
                isCardNo: true,
                messages: {
                    isCardNo: '请输入正确的贷款卡号'
                }
            },
            accountNo: {
                isBankSegmentation: true,
                messages: {
                    isBankSegmentation: '请输入正确的银行卡号'
                }
            }
        }
    _rulesAll = $.extend(true, maxlength50Rules, requiredRules, besidesRules, isMoneyMillionRules);
    gradeIndexCommon = require('zyw/customer/gae.gradeIndexCommon');

    gradeIndexCommon({
        form: _form,
        allWhetherNull: _allWhetherNull,
        rulesAll: _rulesAll
    })



})