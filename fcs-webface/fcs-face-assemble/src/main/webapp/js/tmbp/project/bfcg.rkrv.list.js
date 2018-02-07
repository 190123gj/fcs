define(function (require, exports, module) {
    //尽职调查阶段项目列表
    require('zyw/publicPage');

    //授信类型

    var getTypesOfCredit = require('zyw/getTypesOfCredit');
    var _getTypesOfCredit = new getTypesOfCredit();
    _getTypesOfCredit.init({
        chooseAll: true,
        btn: '#businessTypeBtn',
        name: '#businessTypeName',
        code: '#businessTypeCode'
    });

    //上传回执
    require('Y-msg');
    require('Y-htmluploadify');

    var util = require('util');

    $('#list').on('click', '.fnUpReceipt', function () {
        /**
         * 上传回执 先上传附件，附件上传成功后再ajax
         */
        var _this = $(this);
        // 上传附件
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: util.fileType.all,
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, res, resfile) {

                var _res = $.parseJSON(res);

                if (_res.success) {

                    ajaxPostReceipt(_this, _res.data.url);

                } else {
                    Y.alert('提示', _res.message);
                }
            },
            renderTo: 'body'
        });
    }).on('click', '.fnToEdit', function (e) {

        e.preventDefault();

        var $this = $(this)

        $.get('/projectMg/projectCreditCondition/checkIsExistData.htm', {
            formId: $this.parents('tr').attr('formid')
        }).done(function (res) {
            if (res.success && res.isExist) {
                window.location.href = $this.attr('href')
            } else {
                Y.alert('提示', res.message)
            }
        })

    })

    /**
     * Ajax更新项目回执信息
     * @param  {[type]} $this     [当前'上传回执'的jQuery对象]
     * @param  {[string]} fileUrl [附件地址]
     */
    function ajaxPostReceipt($this, fileUrl) {

        util.ajax({
            url: '/projectMg/consentIssueNotice/uploadReceipt.json',
            data: {
                noticeId: $this.parents('tr').attr('noticeId'),
                receiptAttachment: fileUrl
            },
            success: function (res) {
                if (res.success) {
                    window.location.reload(true);
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });

    }

});