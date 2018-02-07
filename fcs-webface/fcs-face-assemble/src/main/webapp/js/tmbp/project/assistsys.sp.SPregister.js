define(function(require, exports, module) {

    //字数提示
    require('zyw/hintFont');
    //必填集合
    require('zyw/requiredRules');


    //验证
    var _form = $('#form'),
        requiredRules = _form.requiredRulesSharp('startNo,endNo', false, {}, function(rules, name, This) {

            rules[name] = {
                required: true,
                maxlength: 20,
                digits: true,
                comparisonIntersection: true,
                remote: function(This) {

                    var _start, _end, _parent, data = new Object();
                    var id=document.getElementById('checkId').value;
                    _parent = $(This).parent();
                    _start = _parent.find('.start');
                    _end = _parent.find('.end');

                    data['id']=id;

                    data['startNo'] = function(This) {
                        return _start.val() ? _start.val() : '0';
                    };
                    data['endNo'] = function(This) {
                        return _end.val() ? _end.val() : '0';
                    };
                    data['type'] = function() {
                        return "CHECKIN";
                    };

                    return {
                        url: '/projectMg/specialPaper/checkNO.htm', //远程验证的接口url
                        data: data,
                        type: "post", //数据发送方式
                        dataType: "json"
                    }

                },
                messages: {
                    required: '必填',
                    maxlength: '超出20字',
                    digits: '请输入正整数',
                    remote: '起止编号中含有已存在的编号',
                    comparisonIntersection: '编号相交'
                }
            };
        }),
        rulesAllBefore = {

            endNo: {
                min: function(This) {

                    var _start = parseInt($(This).siblings('.start').val());

                    if (!_start) return false;
                    return _start;

                },
                messages: {
                    min: '不能小于起编号'
                },
                // max: function(This) {

                //     var _length,_parent;

                //     _length = $('#test').find('[orderName]').length;
                //     _parent = $(This).parent().next().find('selector');

                //     if (!_start) return false;
                //     return _start;

                // },
            },
            // startNo: {
            //     min: function(This) {

            //     var _prevEnd;

            //     //_length = $('#test').find('[orderName]').length;
            //     _prevEnd = parseInt($(This).parent().prev().find('.end').val());

            //     if (!_prevEnd) return false;
            //         return _prevEnd;

            //     },
            //     messages: {
            //         max: '不能大于前一项止编号'
            //     }
            // }

            // startNo: {
            //     max: function(This) {

            //         var _end = parseInt($(This).siblings('.end').val());

            //         if (!_end) return false;
            //         console.log(_end);
            //         return _end;

            //     },
            //     messages: {
            //         max: '不能大于止编号'
            //     }
            // }
        },
        _rulesAll = $.extend(true, requiredRules, rulesAllBefore),
        SPcommon = require('zyw/project/assistsys.sp.SPcommon');

    SPcommon({

        form: _form,
        rulesAll: _rulesAll

    })



})