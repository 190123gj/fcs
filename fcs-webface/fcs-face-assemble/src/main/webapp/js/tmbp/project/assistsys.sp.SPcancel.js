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
                digits: true,
                comparisonIntersection: true,
                maxlength: 20,
                comparisonUrl: function(This) {

                    var _start, _end, _parent, data = new Object();

                    _parent = $(This).parent();
                    _start = _parent.find('.start');
                    _end = _parent.find('.end');

                    data['startNo'] = function(This) {
                        return _start.val() ? _start.val() : '0';
                    };
                    data['endNo'] = function(This) {
                        return _end.val() ? _end.val() : '0';
                    };
                    data['type'] = function() {
                        return "INVALID";
                    };

                    return {
                        url: '/projectMg/specialPaper/checkInvalid.htm', //远程验证的接口url
                        data: data,
                        succes: function(res) {

                            if (res.success) {

                                $('#receiver').text(res.info.receiveManName);
                                $('#recevieManId').val(res.info.keepingManId);
                                $('#recevieManName').val(res.info.receiveManName).blur();
                                $('#recevieDate').val(res.receiveDate);
                                return true;

                            } else {

                                $('#receiver').text('');

                                if (data['startNo'] == '0' || data['endNo'] == '0' || data['endNo'] < data['startNo']) {

                                    return true;

                                } else {

                                    return false;

                                }

                            }

                        }
                    }

                },
                messages: {
                    required: '必填',
                    maxlength: '超出20字',
                    digits: '请输入正整数',
                    comparisonUrl: '起止编号中含有不已存在的编号',
                    comparisonIntersection: '编号相交'
                }
            };
        }),
        rulesAllBefore = {
            receiveManName: {
                required: true,
                messages: {
                    required: '编号无相应领取人'
                }
            },
            remark: {
                maxlength: 1000,
                messages: {
                    maxlength: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;超出1000字'
                }
            },
            endNo: {
                min: function(This) {

                    var _start = parseInt($(This).siblings('.start').val());

                    if (!_start) return false;
                    return _start;

                },
                messages: {
                    min: '不能小于起编号'
                }
            },
        },
        _rulesAll = $.extend(true, requiredRules, rulesAllBefore),
        SPcommon = require('zyw/project/assistsys.sp.SPcommon');


    SPcommon({

        form: _form,
        rulesAll: _rulesAll

    })

})