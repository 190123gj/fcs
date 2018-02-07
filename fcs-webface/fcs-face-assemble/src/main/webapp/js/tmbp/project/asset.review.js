/**
 * Created by eryue
 * Create in 2016-09-20 14:27
 * Description:
 */

'use strict';
define(function(require, exports, module) {
    require('Y-msg');
    var util = require('util');
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();
    $('#submitBtn').on('click', function() {//提交前验证

        var _isPass = true,
            _isPassEq;

        var $validateList = $('.fnInput');

        $validateList.each(function(index, el) {
            var thisVal;

            if ($(this).hasClass('fileTypeRadio')) {
                thisVal = $(this).attr('value');
            } else {
                thisVal = $(this).val();
            }
            if (typeof(thisVal) == 'undefined' || thisVal.length == 0 || thisVal == 'NONE') {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {

            var $thisError = $validateList.eq(_isPassEq);

            Y.alert('提醒', '请把表单填写完整', function() {

                $thisError.focus();
            });
            return;
        }

        submit(); //验证通过提交

    });

    function submit() {//提交

        util.ajax({
            url: '/projectMg/assetReview/submit.json',
            type: 'post',
            data: $('#form').serialize(),
            success: function(res) {
                if (res.success) {
                    Y.alert('提醒', res.message,function () {
                        window.location.href = '/projectMg/assetReview/list.htm';
                    });

                } else {
                    Y.alert('提醒', res.message);
                }
            }

        });
    };
})