define(function(require, exports, module) {

    //上传
    require('zyw/upAttachModify')
        //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'projectVoteResult,summaryUrl',
            boll: false
        },
        _rulesAll = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        });

    var submitValidataCommonUp = require('zyw/submitValidataCommonUp');
    submitValidataCommonUp.submitValidataCommonUp({

        rulesAll: _rulesAll,
        form: _form,
        allWhetherNull: _allWhetherNull,
        //extraObj:'',

        allEvent: {

            contentBtn: [{
                clickObj: '.submit',
                eventFun: function(jsonObj) {

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        // validateData: {

                        //     submitStatus: 'Y'

                        // },
                        fm5: jsonObj.setInitVal['fm5']

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }],
            //broadsideBtn: []

        },

        ValidataInit: {

            successBeforeFun: function(res) {

                if (res['fm5']) { //有改变

                    return true;

                } else { //无改变

                    hintPopup('数据未改变，保持原有储存', '/projectMg/meetingMg/councilList.htm');
                    return false;

                }

            },

            successFun: function(res) {

                hintPopup(res['message'], '/projectMg/meetingMg/councilList.htm');

            },

            errorAll: { //validata error属性集

                errorClass: 'radioSpan',
                errorElement: 'span',
                errorPlacement: function(error, element) {

                    if (element.is('input[type="radio"]')) {

                        element.parent().after(error);

                    } else {

                        element.after(error)

                    }

                }

            }


        },

        callback: function(objList) {

            //验证方法集初始化
            $.fn.addValidataCommon(objList['rulesAll'], true)
                .initAllOrderValidata();

            $('[orderName="voteInfo"]').each(function(index, el) {

                var $this;

                $this = $(this);
                console.log($this.find('[type="radio"]:checked').length)
                if ($this.find('[type="radio"]:checked').length) $this.find('[type="radio"]').attr('disabled', true);

            });

        }

    });

});