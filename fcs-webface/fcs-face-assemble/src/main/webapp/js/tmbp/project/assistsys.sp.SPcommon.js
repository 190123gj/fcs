define(function(require, exports, module) {


    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup'),
        submitValidataCommonUp = require('zyw/submitValidataCommonUp');

    //比对相交
    jQuery.validator.addMethod('comparisonIntersection', function(value, element, param) {

        var _parent, _index, _all, _list, _startSelf, _endSelf;

        _parent = $(element).parent();
        _index = _parent.index();
        _all = $('.comparisonIntersection');
        _list = _all.children();
        _startSelf = parseFloat(_list.eq(_index).find('.start').val());
        _endSelf = parseFloat(_list.eq(_index).find('.end').val());

        for (i = 0; i < _list.length; i++) {

            if (i == _index) continue;

            if (((_startSelf <= parseFloat(_list.eq(i).find('.end').val())) && (_endSelf >= parseFloat(_list.eq(i).find('.start').val())))) {

                _all.attr('legal', 'N');

                return this.optional(element) || false;

            }

        }

        _all.attr('legal', 'Y');

        return this.optional(element) || true;

    }, $.validator.format("请输入{0}"));

    module.exports = function(objList) {

        submitValidataCommonUp.submitValidataCommonUp({

            form: objList['form'],
            rulesAll: objList['rulesAll'],
            ValidataInit: {
                // RulesInit: {
                //     rules: {
                //         'listOrder[0].endNo': {
                //             customRemote: {
                //                 url: '/projectMg/specialPaper/checkNo.htm', //远程验证的接口url
                //                 //data: data,
                //                 customError: function(element, response) { //验证失败的回调，返回值将作为错误提示显示出来
                //                     return response.message;
                //                 },
                //                 customSuccess: function(element, data, response) { //验证通过的回调，一般用不着
                //                 }
                //             }
                //         }
                //     }
                // },
                successBeforeFun: function(res) {

                    var util = require('util'),
                        loading = new util.loading();

                    loading.open();
                    $.fn.orderName();
                    return true;

                },

                successFun: function(res) {

                    $('.m-modal-box').remove();
                    hintPopup(res['message'], window.location.href);

                },

                // errorAll: { //validata error属性集

                //     errorClass: 'error-tip2',
                //     errorElement: 'span',
                //     // errorPlacement: function(error, element) {
                //     //     element.after(error)
                //     // }

                // }
            },

            callback: function(objList) {

                //验证方法集初始化
                $('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
                    .initAllOrderValidata()
                    .assignGroupAddValidataUp();

                //计算张数
                $('body').on('change', '.start,.end', function(event) {

                    var _test, _target, _end, _output, _sum = 0;

                    _test = $('#test');
                    _target = _test.find('.start');

                    _target.each(function(index, el) {

                        var _start, _end, _startVal, _endVal;

                        _start = $(el);
                        _startVal = parseFloat(_start.val()) || 0;
                        _end = _start.siblings('.end');
                        _endVal = parseFloat(_end.val()) || 0;

                        if (((_endVal - _startVal) < 0) || (_start.val() == '') || (_end.val() == '')) return true;

                        _sum += (_endVal - _startVal + 1);

                    });

                    _output = $('#output');

                    setTimeout(function() {
                        (_test.attr('legal') == 'N') ? _output.text('编号相交'): _output.text(_sum)
                    }, 100);

                }).on('change', '.start,.end', function(event) { //输入起编号触发止编号验证

                    $('.comparisonIntersection').find('.start,.end').blur();

                }).on('click', '.fnDelLine', function(event) {

                    $('.start,.end').change();

                });

            }

        }); //validate初始化

    }



})