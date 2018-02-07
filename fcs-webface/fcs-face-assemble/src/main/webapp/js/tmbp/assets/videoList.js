/*
 * @Author: erYue
 * @Date:   2016-07-04 18:05:40
 * @Last Modified by:   Administrator
 * @Last Modified time: 2016-09-18 16:04:13
 */


/*
*
    new videoList({
        ele: $('#chooseVideo'),   //必须且为ID选择，目标元素
        allVideoList: allVideoList, //所有数据
        checkedList: checkedList,  //已选数据
        textKey: 'text', //展示文本在数据的key，如果不设置默认为text
        propKeys: ['value'], //其他需要添加到option上的prop属性，如果不设置，默认为[]
        validateKey: 'text'  //唯一性校验key，默认指定为textKey；其中validateKey必须是textKey或者propKeys中的一个，否则将会重新指定为默认值textKey
    })
*
*/

define(function (require, exports, module) {

    /*
    *   new videoList({
            ele: $('#chooseVideo'),   //必须且为ID选择，目标元素
            allVideoList: allVideoList, //所有数据
            checkedList: checkedList,  //已选数据
            textKey: 'text', //展示文本在数据的key，如果不设置默认为text
            propKeys: ['value'], //其他需要添加到option上的prop属性，如果不设置，默认为[]
            validateKey: 'text'  //唯一性校验key，默认指定为textKey；其中validateKey必须是textKey或者propKeys中的一个，否则将会重新指定为默认值textKey
        })
    * 
    */

    var $body = $('body');
    var _template = [
        '<div class="chooseVideo fn-clear">',
        '    <div class="chooseVideoLeft chooseVideoList fn-left">',
        '        <select multiple="multiple">',
        '        </select>',
        '    </div>',
        '    <div class="choooseVideoMid fn-left fn-clear">',
        '        <div>',
        '            <p class="chooseVideoAdd fn-dischoose">&gt;&gt;</p>',
        '            <p class="chooseVideoRemove fn-dischoose">&lt;&lt;</p>',
        '        </div>',
        '    </div>',
        '    <div class="chooseVideoRight chooseVideoList fn-left">',
        '        <select multiple="multiple">',
        '        </select>',
        '    </div>',
        '</div>'
    ].join("");

    function videoList(obj) {
        var _self = this;
        if (!!obj) {
            _self.init(obj);
        }
    }

    videoList.prototype = {

        constructor: videoList,
        init: function (obj) { //初始化各项配置

            var _self = this;

            //自定义变量
            _self.allVideoList = obj.allVideoList || []; //缓存所有videoList
            _self.checkedList = obj.checkedList || []; //缓存已经选择的videoList
            _self.checkedListVal = obj.checkedListVal || [];
            _self.ele = !!obj.ele ? $(obj.ele) : $('#chooseVideo');
            _self.textKey = obj.textKey || 'text';
            _self.propKeys = obj.propKeys || [];
            _self.validateKey = (!!!obj.validateKey && _self.propKeys.indexOf(obj.validateKey) >= 0) ? obj.validateKey : _self.textKey;
            _self.saveKey = obj.saveKey || _self.validateKey; //需要保存的字段，只支持一个
            _self.saveInputId = (!!_self.saveKey && !!obj.saveInputId) ? obj.saveInputId : ''; //保存到指定的input上，保证唯一性，支持ID

            //添加内容
            _self.ele.html(_template);

            //初始化事件及其它
            if(!_self.ele.hasClass('onlyView')) _self.bindEvent();
            _self.ResolveEle();

        },
        ResolveEle: function () { //初始化DOM

            var _self = this;

            if (!_self.ele && _self.ele.length != 1) return; //如果不唯一则中断
            _self.genList();

        },
        genList: function () { //初始化Video列表

            var _self = this;

            _self.pushVideoList(_self.ele.find('.chooseVideoLeft select'), _self.allVideoList); //加载所有分类
            if(_self.checkedList.length <= 0) return;
            _self.pushVideoList(_self.ele.find('.chooseVideoRight select'), _self.checkedList); //加载已有分类

        },
        pushVideoList: function ($box, data) { //第一次进入渲染

            var _self = this;
            var _html = '';

            if (data.length == 0 && !$box.hasClass('notFirst')) return;

            $.each(data, function (index, obj) {
                _html += _self.genHtml(index,obj);
            });

            $box.html(_html).addClass('notFirst');
            // $('#' + _self.saveInputId).val(_self.checkedListVal);

        },
        genHtml: function (index,obj) {

            var _self = this;
            var result = '';
            var dataText = !obj[_self.textKey] ? obj : obj[_self.textKey];
            var dataValidate = !obj[_self.validateKey] ? obj : obj[_self.validateKey];
            var propAttrStr = ' ';
            $.each(_self.propKeys, function (propKeyIndex, propKey) {
                if(!!!obj[propKey]) return;
                var propKeyVal = !obj[propKey] ? obj : obj[propKey];
                propAttrStr += propKey + '="' + propKeyVal + '" ';
            });
            result += '<option' + propAttrStr + ' index="' + index + '" title="' + dataText + '" validatKey="' + dataValidate + '">' + dataText + '</option>';
            return result;

        },
        filterList: function ($ele) {

            var _self = this;
            var index = $ele.attr('index');
            var text = $ele.attr('validatkey');
            if (_self.ele.find('.chooseVideoRight option[validatkey="' + text + '"]').length > 0) return;
            _self.checkedList.push(_self.allVideoList[index]);
            return _self.genHtml(index,_self.allVideoList[index]);

        },
        genVal: function () {

            var _self = this;
            _self.checkedListVal = [];
            $.each(_self.checkedList, function (i,obj) {
                _self.checkedListVal.push(obj[_self.saveKey]);
            });
            $('#' + _self.saveInputId).val(_self.checkedListVal);

        },
        bindEvent: function () { //绑定事件

            var _self = this;
            $body.on('click', '.chooseVideoAdd', function () {

                    var $selectOption = _self.ele.find('.chooseVideoLeft option:selected');
                    var _html = '';
                    $selectOption.each(function (i, e) {
                        _html += _self.filterList($(this));
                    });
                    _self.ele.find('.chooseVideoRight select').append(_html);
                    _self.genVal();
                    clearSelectedStatus(_self.ele)

                })
                .on('click', '.chooseVideoRemove', function () {

                    var $selectOption = _self.ele.find('.chooseVideoRight select option:selected');
                    $selectOption.each(function (i, e) {
                        var $this = $(this);
                        var index = $this.attr('index');
                        _self.checkedList.splice(index, 1);
                        $this.remove();
                    });
                    _self.genVal();
                    clearSelectedStatus(_self.ele)

                })
                .on('dblclick', '.chooseVideoLeft option', function () {

                    var $this = $(this);
                    var _html = _self.filterList($this);
                    _self.ele.find('.chooseVideoRight select').append(_html);
                    _self.genVal();
                    clearSelectedStatus(_self.ele)

                })
                .on('dblclick', '.chooseVideoRight option', function () {

                    var $this = $(this);
                    var index = $this.attr('index');
                    _self.checkedList.splice(index, 1);
                    $this.remove();
                    _self.genVal();
                    clearSelectedStatus(_self.ele)

                });

        }

    };
    function  clearSelectedStatus($box) {//清除select的选择状态
        $box.find('option').removeProp('selected');
    }
    module.exports = videoList;
})