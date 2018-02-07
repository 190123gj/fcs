define(function (require, exports, module) {

    var util = require('util')

    //压缩
    var pako = require('pako')

    $('#btn').click(function (event) {

        $('#btnBox').remove()


        function nocss(obj) {
            obj.css({
                wordBreak:'break-all',
                whiteSpace: 'normal',
                wordWrap: 'break-word'
                //width: 1 / ($(this).siblings().length + 1)*100 + '%'
            })
            obj.children().removeAttr('style')
            // obj.children().each(function () {
            //     var _this = $(this)[0]
            //     if (_this.tagName == 'B' || _this.tagName == 'STRONG') {
            //         _this.style.fontWeight = 'normal'
            //     }
            // })
            if(obj.children().length) {
                nocss(obj.children())
            }
        }
        nocss($('.nocss'))

        $('body').css('width','520px')
        $('table').css('width','90%').css('max-width','480px').removeAttr('width')
        $('th').css({
            wordBreak:'break-all',
            whiteSpace: 'normal',
            editable:'true',
            // width: 1 / ($('th').siblings().length + 1)*100 + '%'
        }).removeAttr('width').attr('siblings', $(this).siblings())
        $('td').each(function () {
            $(this).css({
                wordBreak:'break-all',
                whiteSpace: 'normal',
                wordWrap: 'break-word',
                // width: 1 / ($(this).siblings().length + 1)*100 + '%'
            }).removeAttr('nowrap').removeAttr('width').find('p,span').css({
                wordBreak:'break-all',
                whiteSpace: 'normal',
                wordWrap: 'break-word'
            })
        })

        


        var deflate = pako.gzip(document.documentElement.outerHTML, {
            to: 'string'
        });

        //console.log('<!DOCTYPE html>' + document.documentElement.outerHTML);
        $.ajax({
                url: '/baseDataLoad/createDoc.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    formId: $('[name="formId"]').val(),
                    type: $('[name="type"]').val(),
                    decode: 'yes',
                    htmlData: encodeURIComponent(deflate)
                },
            })
            .done(function (res) {

                if (res.success) {
                    window.location.href = res.url;
                    setTimeout(function () {
                        window.location.reload(true)
                    }, 1000)
                } else {
                    alert(res.message)
                    window.location.reload()
                }
            })
            .fail(function () {
                console.log("error");
            })
            .always(function () {
                console.log("complete");
            });
    });


    xlh($('label.fnWrite'));

    $('table').attr({
        'style': 'width: 96%;margin: 0 2%;border: 1px solid #d1d1d1; background-color: #fff; border-spacing: 0;border-collapse: collapse;',
        'border': 1
    }).find('td').attr('style', 'word-break: break-all;white-space: normal;position: relative;padding: 10px 5px;line-height: 20px; border: 1px solid #d1d1d1;word-break: break-all;word-wrap: break-word;')


    function xlh(obj) {
        var _this = obj;
        $('body').find(_this).each(function (index) {

            var divs = $(this),
                _orderName = divs.attr('orderName'),
                index = index;

            divs.find('[name]').each(function () {
                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.lastIndexOf('.') + 1);
                }

                name = _orderName + '[' + index + '].' + name;

                _this.attr('name', name);

            });
        })
    }

    $('body').find('.fnWrite').each(function () {
        var _self = $(this),
            _selfInput = _self.children('[type="checkbox"]');
        _selfP = _self.parent().parent('div'),
            _val = _self.find($('[type="hidden"]')).val(),
            newHtml = '<div class="m-item fn-mt30 fnRemark" orderName="notCollectes" style="padding: 10px 0; padding-left: 170px; line-height: 26px; min-height: 26px; word-wrap:break-word;margin-top:30px" >' + '<label class="m-label" style="position: absolute; margin-left: -170px; width: 170px; text-align: right;">未收集到原因：</label>' + '<textarea class="ui-textarea2 fnInput fn-w500" name="remark" placeholder="请控制在1000字之内" style="color:#666;border-color: #ccc; line-height: 25px; padding: 5px; height: 170px; width: 95%; border-radius: 4px; font-family:Microsoft YaHei">' + _val + '</textarea>' + '</div>';

        _self.parent().after(newHtml);

        _selfP.find('.fnRemark').hide();

        _selfP.find('.fnRemark textarea').removeClass('fnInput');

        if (_val != '') {
            _selfP.find('.fnRemark').show();
            _selfP.find('.fnRemark textarea').addClass('fnInput').attr('readonly', 'readonly');
        }

        xlh($('div.fnRemark'));
    })

    xlh($('div.fnRemark'));

    $('#btnPrint').on('click', function () {
        var $body = $('body')

        $body.find('#btn, #btnPrint, #btnGoBack').remove()

        util.print($body.html())
    })

});