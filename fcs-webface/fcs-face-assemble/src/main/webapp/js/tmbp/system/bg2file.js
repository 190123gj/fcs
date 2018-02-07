define(function (require, exports, module) {

    require('Y-msg')

    require('Y-htmluploadify')

    require('zyw/upAttachModify')

    var util = require('util')

    var $form = $('#form')

    $form.on('click', '#submitBtn', function () {

        util.ajax({
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function (res) {
                Y.alert('提示', res.message, function () {
                    window.location.reload(true)
                })
            }
        })

    })

    $('.fnUpImg').on('click', function () {

        var $this = $(this),
            $thisP = $this.parent();

        var _load = new util.loading();

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: util.fileType.img,
            fileObjName: 'UploadFile',
            onUploadStart: function ($this) {
                _load.open();
            },
            onQueueComplete: function (a, b) {
                _load.close();
            },
            onUploadSuccess: function ($this2, data, resfile) {

                var res = $.parseJSON(data);
                if (res.success) {
                    $this.prop('src', res.data.url)
                    $thisP.find('input.fnUpFileInput').val(res.data.url);
                } else {
                    Y.alert('提示', '上传失败');
                }

            },
            renderTo: 'body'
        });

    })

})