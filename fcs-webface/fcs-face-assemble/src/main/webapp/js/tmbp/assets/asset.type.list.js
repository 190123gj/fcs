/*
 * @Author: erYue
 * @Date:   2016-07-04 18:05:40
 * @Last Modified by:   erYue
 * @Last Modified time: 2016-08-05 14:44:36
 */

define(function(require, exports, module) {

    require('input.limit');
    var mergeTable = require('zyw/mergeTable');
    var util = require('util');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    var newMergeTable = new mergeTable();
    newMergeTable.init({

        obj: '.root1',

        valType: false,

        // mergeCallback: function(Dom //Dom为每次遍历的合并对象和被合并对象) {//每次遍历合并会调用的callback

        // },
        callback: function() {
            $('.hiddenTable').css('visibility', 'visible');
        }

    });

    var $opt = $('.opt');

    $opt.on('click', '.del', function() {

        var $this = $(this);
        var id = $(this).attr('typeId');
        var data = {};

        data.id = id;
        if ($(this).hasClass('levelOne')) {
            data.level = 'one';
        }else if($(this).hasClass('levelTwo')) {
            data.level = 'two';
        }else {
            data.level = 'three';
        };

        util.ajax({
            url: '/assetMg/pledgeType/delete.htm',
            type: 'POST',
            data: data,
            success: function(res) {
                console.log(res)
                if (res.success == true) {
                    Y.alert('提醒', res.message,function  () {
                        window.location.href = '/assetMg/pledgeType/list.htm';
                    });

                }else {
                    Y.alert('提醒', res.message);
                }

            }
        });

    });
})