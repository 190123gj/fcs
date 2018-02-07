define(function (require, exports, module) {
    //辅助系统 > 函、通知书 > 函、通知书列表

    require('Y-htmluploadify');
    require('zyw/publicPage');
    require('Y-msg');

    var util = require('util');

    //授信类型
    var getTypesOfCredit = require('zyw/getTypesOfCredit');
    var _getTypesOfCredit = new getTypesOfCredit();
    _getTypesOfCredit.init({
        chooseAll: true,
        btn: '#businessTypeBtn',
        name: '#businessTypeName',
        code: '#businessTypeCode'
    });

    // 申请用印
    $('#fnApplySeal').on('click', function () {

        var $checked = $('.fnCheck:checked'),
            _checkIds = [],
            _checkIdMark,
            _checkIdPass = true;

        $checked.each(function (index, el) {

            if (index == 0) {
                _checkIdMark = el.getAttribute('projectcode');
            } else {

                if (_checkIdMark != el.getAttribute('projectcode')) {
                    _checkIdPass = false;
                    return false;
                }

            }

            _checkIds.push(el.value);

        });

        if (!_checkIdPass) {
            Y.alert('提示', '请选择同一个项目的文书');
            return;
        }

        if (_checkIds.length == 0) {
            Y.alert('提示', '请选择文书');
            return;
        }

        util.direct('/projectMg/stampapply/addStampApply.htm?letterIds=' + _checkIds.join(','), '/projectMg/stampapply/addStampApply.htm');

    });

    // 回传文书
    $('.fnPassBack').on('click', function () {

        var self = this;

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: util.fileType.all,
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, data, resfile) {

                var _res = $.parseJSON(data);
                if (_res.success) {

                    var _file = [_res.data.oldFileName, _res.data.serverPath, _res.data.url].join(',');

                    util.ajax({
                        url: '/projectMg/contract/saveContractBack.htm',
                        data: {
                            contractScanUrl: _file,
                            id: self.getAttribute('backid')
                        },
                        success: function (res) {
                            if (res.success) {

                                Y.alert('提示', '已保存', function () {
                                    window.location.reload(true);
                                });

                            } else {
                                Y.alert('提示', res.message);
                            }
                        }
                    });

                } else {
                    Y.alert('提示', '上传失败');
                }

            },
            renderTo: 'body'
        });

    });

    //合同函确认
    $('#list').on('click', '.sure', function () {

        var _thisTr = $(this).parents('tr');

        Y.confirm('合同函确认', '是否确认执行”合同函确认“操作？', function (opn) {

            if (opn == 'yes') {

                util.ajax({
                    url: '/projectMg/contract/contractConfirm.htm',
                    data: {
                        ids: _thisTr.attr('id'),
                        projectCode: _thisTr.attr('projectcode')
                    },
                    success: function (res) {
                        if (res.success) {
                            Y.alert('提示', '成功处理', function () {
                                window.location.reload(true);
                            });
                        } else {
                            Y.alert('提示', res.message);
                        }
                    }
                });

            }

        });

    });

    // 上传法院裁定书
    $('.fnCourtRuling').on('click', function () {

        var self = this;

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: util.fileType.all,
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, data, resfile) {

                var _res = $.parseJSON(data);
                if (_res.success) {
                    var _file = [_res.data.oldFileName, _res.data.serverPath, _res.data.url].join(',');

                    util.ajax({
                        url: '/projectMg/contract/saveCourtRuling.htm',
                        data: {
                            courtRulingUrl: _file,
                            id: self.getAttribute('backid')
                        },
                        success: function (res) {
                            if (res.success) {

                                Y.alert('提示', '已保存', function () {
                                    window.location.reload(true);
                                });

                            } else {
                                Y.alert('提示', res.message);
                            }
                        }
                    });

                } else {
                    Y.alert('提示', '上传失败');
                }

            },
            renderTo: 'body'
        });

    });
    if(window.location.href.indexOf("applyType=PROJECT_LETTER")>=0){
        $('.a-url').each(function(i,el){
            var h=$(this).attr("href")
            $(this).attr("href",h+"&applyType=PROJECT_LETTER")
        })
    }
    if(window.location.href.indexOf("applyType=PROJECT_DB_LETTER")>=0){
        $('.a-url').each(function(i,el){
            var h=$(this).attr("href")
            $(this).attr("href",h+"&applyType=PROJECT_DB_LETTER")
        })
    }

});