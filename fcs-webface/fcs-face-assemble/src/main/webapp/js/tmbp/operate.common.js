/**
 * Created by eryue
 * Create in 2016-12-28 17:15
 * Description:
 *
 */

'use strict';
define(function(require, exports, module) {

    var $body = $('body');
    var $form = $('#form');

    $body.on('click','.addLine',function () {//新增一行
        var $self = $(this);
        var templateId = $self.attr('templateid');
        if(!templateId) return;
        var maxItems = $self.attr('maxitems') || false;
        var $itemBox = $self.parents('.itemBox').find('.itemsList');
        var $template = $($('#' + templateId).html());
        var itemBoxListLength = $itemBox.find('.itemLine').length;
        $template.find('.fn-validate').removeClass('ignore');//移除验证

        if(itemBoxListLength - 1 >= maxItems && !!maxItems) {
            Y.alert('提示','不能超过' + maxItems + '条');
            return;
        };
        $template.find('.itemListIndex').val(itemBoxListLength + 1);
        if($itemBox.find('tr.resltTr').length == 1){
            $itemBox.find('tr.resltTr').before($template)
        }else {
            $itemBox.append($template)
        };
        diyName();
    }).on('click','.deleteLine',function () {//删除一行
        var $self = $(this);
        var $selfItem = $self.parents('.itemLine');
        var $itemBox = $self.parents('.itemsList');
        var residueItems = $itemBox.find('.itemLine').length;
        var minLength = $self.attr('minlength') || 1;
        // console.log(minLength)
        if(residueItems <= minLength) {
            Y.alert('提示','至少保留一条！');
            return;
        };
        $selfItem.remove();
        diyName();
    }).on('click','.delTrData',function () {//删除一行
        var $self = $(this);
        var url = $self.attr('opthref');

        Y.alert('提示','是否确认删除？',function () {
            $.ajax({
                url:url,
                success:function (res) {
                    if(res.success){
                        Y.alert('提示',res.message,function () {
                            window.location.href = window.location.href;
                        });
                    }else {
                        Y.alert('提示',res.message);
                    }
                }
            })
        })
    });

    //序列化name

    function diyName($box) {

        if(!$box) $box = $form;
        var $diyNameBox = $box.find('.diyNameBox');
        $diyNameBox.each(function () {
            $(this).find('[diyname]').each(function(index,ele) {
                var $thisWrap = $(this);
                var diyName = $thisWrap.attr('diyname');

                $thisWrap.find('[name]').each(function() {

                    var _this = $(this),
                        name = _this.attr('name');

                    if (name.indexOf('.') > 0) {
                        name = name.substring(name.lastIndexOf('.') + 1);
                    };

                    name = diyName + '[' + index + '].' + name;
                    _this.attr('name', name);
                    if(index == $(this).find('[diyname]').length - 1){
                        addRules ($(this).find('[diyname]'));
                    };

                });

            });
        });

    };
    function addRules ($validateBox) {
        var $validateList = $validateBox.find('.fn-validate');
        $validateList.each(function (i, e) {
            var _this = $(this);
            _this.rules("remove");
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })
    };
    

//    module.exports = selectType;
})