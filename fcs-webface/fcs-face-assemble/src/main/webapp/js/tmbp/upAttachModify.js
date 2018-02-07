/**
 * 通用上传附件
 *
 * 上传按钮(fnUpAttachBtn)上添加属性todownload，其值为true则表示开启下载功能，false为不开启。默认不添加为false.其中图片不支持下载
 * demo1：
    <div class="fnUpAttach">
        <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn"><i class="icon icon-add"></i>上传附件</a>
        <input class="fnUpAttachVal" type="hidden" name="">
        <div class="fn-blank5"></div>
        <div class="m-attach fnUpAttachUl"></div>
    </div>
 *
 * demo2：增加附件预览和统计功能
    <div class="fnUpAttach">
        <span class="item-files fn-dib m-up-files">共有[<strong class="upFilesNumber">0</strong>]附件</span>
        <span class="fn-csp ui-btn ui-btn-fill ui-btn-blue viewDaliog"><i class="icon icon-look"></i>弹窗查看</span>
        <span class="fn-csp ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn"><i class="icon icon-add"></i>上传附件</span>
        <input class="fnUpAttachVal" type="hidden" name="">
        <div class="fn-blank5"></div>
        <div class="m-attach fnUpAttachUl fn-hide"></div>
    </div>
 *
 *
 */

define(function (require, exports, module) {
    //通用代码
    //查看图片
    require('Y-imageplayer');
    //上传图片
    require('Y-htmluploadify');
    require('Y-msg');
    var util = require('util');

    var $body = $('body');

    $body.on('click', '.fnUpAttachBtn', function ()
    {

        $("body").off('change','[name="UploadFile"]')
        //上传附件
        var _this = $(this),
            _filetype = _this.attr('filetype') || util.fileType.all, //自定义上传类型
            _maxitem = _this.attr('maxitem') || 99,
            _fileSizeLimit = _this.attr('fileSizeLimit') || '5MB',
            $fnUpAttachBox = _this.parents('.fnUpAttach');

        //是否超过
        if ($fnUpAttachBox.find('.fnAttachItem').length >= _maxitem) {
            Y.alert('提醒', '已超过最大上传个数限制，请先删除一部分再上传！');
            return;
        }

        var _load = new util.loading();

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileSizeLimit: _fileSizeLimit,
            fileTypeExts: _filetype,
            fileObjName: 'UploadFile',
            onUploadStart: function ($this) {
                _load.open();
            },
            onQueueComplete: function (a, b) {
                _load.close();
            },
            onUploadSuccess: function ($this, res, resfile) {
                var _res = $.parseJSON(res || '{"success": false}');
                if (_res.success) {

                    var oldFileName = _res.data.oldFileName;
                    var isPathOldFileName = oldFileName.indexOf('\\') > 0;
                    var _file = {
                        fileName: isPathOldFileName ? oldFileName.split('\\')[oldFileName.split('\\').length - 1] : oldFileName,
                        serverPath: _res.data.serverPath,
                        fileUrl: _res.data.url
                    }

                    upFileCallBack($fnUpAttachBox, _file);
                } else {
                    Y.alert('操作失败', '上传失败');
                }
            },
            onUploadError: function ($this, file) {
                console.log('出事啦');
                _load.close();
            },
            renderTo: 'body'
        });

    }).on('click', '.fnUpAttachDel', function ()
    {
        //删除某个附件
        var _this = $(this),
            $fnUpAttachBox = _this.parents('.fnUpAttach');

        if ($fnUpAttachBox.length == 0) { //判断点击的弹窗的删除按钮
            var flag = _this.parents('.viewUpfiles').attr('flag'); //从弹窗获取对应上传组件的索引标记
            var index = _this.parents('.fnUpAttachUl').find('.fnUpAttachDel').index(_this); //获取当前删除按钮在当前弹窗的索引

            $('.fnUpAttach').eq(flag).find('.fnUpAttachDel').eq(index).trigger('click'); //找到对应上传组件对应的删除按钮触发其点击事件
        }

        _this.parent().remove();

        totalVal($fnUpAttachBox);

    }).on('click', '.fnAttachView', function ()
    {
        //查看所有上传的附件


        var _imgs = [];

        $('.fnAttachView').each(function (index, el) {
            var _this = $(this);
            _imgs.push({
                src: _this.parent().attr('val').split(',')[2],
                desc: _this.text()
            });
        });

        var params = {
            imgs: _imgs,
            index: $(this).index('.fnAttachView'),
            scaleLowerLimit: 0.1,
            loop: false,
            fullAble: false,
            firstTip: '这是第一张了',
            firstTip: '这是最后一张了'
        }

        if(parent.frameElement){
            if(parent.frameElement.id == 'fnIfame')
                params._own_param = parent.frameElement.offsetHeight;
        }


        Y.create('ImagePlayer', params).show();

        if(params._own_param　|| $(this).offset().top ){
            $('.photo-browse').css({
                top:$(this).offset().top - $('.photo-browse')[0].offsetHeight/3-60
            });
        }


    }).on('click', '.viewDaliog', function ()
    { //触发弹窗查看附件
        var _this = $(this);
        var $fnUpAttachBox = _this.parents('.fnUpAttach');
        var fnUpAttachUlHtml = $fnUpAttachBox.find('.fnUpAttachUl').html(); //获取组件内容
        var index = $fnUpAttachBox.index('.fnUpAttach'); //获取当前上传组件的索引，并标记到当前弹窗
        var length = $fnUpAttachBox.find('.fnUpAttachVal').attr('length');

        if (!!!length || parseInt(length) == 0) {
            Y.alert('提示', '暂无附件');
            return;
        }

        $body.Y('Window', {
            // content: '<div class="m-modal m-modal-default view-files viewUpfiles" flag="' + index + '">' +
            //     '    <div class="view-files-hd">' +
            //     '        <span class="fn-right fn-usn fn-csp closeBtn" href="javascript:void(0);">×</span>' +
            //     '        <span class="view-files-ttl">附件列表</span>' +
            //     '    </div>' +
            //     '    <div class="view-files-body">' +
            //     '        <div class="m-attach fnUpAttachUl">' + fnUpAttachUlHtml + '</div>' +
            //     '    </div>' +
            //     '</div>',
            content: [
                '<div class="m-modal m-modal-default view-files viewUpfiles" flag="' + index + '">',
                '    <div class="m-modal-title"><span class="m-modal-close closeBtn">&times;</span><span class="title">附件列表</span></div>',
                '    <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner">',
                // '        <div class="view-files-body">',
                '            <div class="m-attach fnUpAttachUl">' + fnUpAttachUlHtml + '</div>',
                // '        </div>',
                '    </div></div></div>',
                '</div>'
            ].join(''),
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn'
        });

    }).on('click', '.new_upload',function () {
        //上传附件
        var _this = $(this),
            _filetype = _this.attr('filetype') || util.fileType.all, //自定义上传类型
            _maxitem = _this.attr('maxitem') || 99,
            _fileSizeLimit = _this.attr('fileSizeLimit') || '5MB',
            $fnUpAttachBox = _this.parent('.fnUpAttach');

        //是否超过
        if ($fnUpAttachBox.find('.fnAttachItem').length >= _maxitem) {
            Y.alert('提醒', '已超过最大上传个数限制，请先删除一部分再上传！');
            return;
        }

        var _load = new util.loading();
        var html = '<div style="height: 39px;"><input type="file" name="UploadFile" style="float:left;" class="wnd-upload-file"><span class="fn-upinfo wnd-upload-info" style="display:none;"></span><span class="base-btn fn-upclear" style="float:right;"><span>清空</span></span></div>'
        var flag = false


        $("body").off('change','[name="UploadFile"]')
        $("body").off('click','[name="UploadFile"]')


        $("body").on('click','[name="UploadFile"]', function () {
            flag = true
            var _this = $(this)
        })

        $("body").on('change','[name="UploadFile"]', function () {
            var _this = $(this)
            if (!flag || _this.attr('data-added')) {
                return
            }
           var _box = _this.parents('.fn-main')
            _this.attr('data-added','true')
            $(html).appendTo(_box)
            flag = false
        } )

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileSizeLimit: _fileSizeLimit,
            fileTypeExts: _filetype,
            fileObjName: 'UploadFile',
            onUploadStart: function ($this) {
                _load.open();
            },
            onQueueComplete: function (a, b) {
                _load.close();
            },
            onUploadSuccess: function ($this, res, resfile) {
                var _res = $.parseJSON(res || '{"success": false}');
                if (_res.success) {

                    var oldFileName = _res.data.oldFileName;
                    var isPathOldFileName = oldFileName.indexOf('\\') > 0;
                    var _file = {
                        fileName: isPathOldFileName ? oldFileName.split('\\')[oldFileName.split('\\').length - 1] : oldFileName,
                        serverPath: _res.data.serverPath,
                        fileUrl: _res.data.url
                    }
                    upFileCallBack($fnUpAttachBox, _file);
                } else {
                    Y.alert('操作失败', '上传失败');
                }
            },
            onUploadError: function ($this, file) {
                console.log('出事啦');
                _load.close();
            },
            renderTo: 'body'
        });

    });

    function totalVal($fnUpAttachBox) {
        //统计当前上传后的链接

        var _arr = [];

        $fnUpAttachBox.find('.fnAttachItem').each(function (index, el) {
            _arr.push($(this).attr('val'));
        });

        $fnUpAttachBox.find('.fnUpAttachVal').val(_arr.join(';')).attr('length', _arr.length).next('.radioSpan').hide(); //统计个数在隐藏域
        $fnUpAttachBox.find('.fnUpAttachVal').trigger('blur');
        $fnUpAttachBox.find('.upFilesNumber').html(_arr.length); //统计附件个数

    }

    function upFileCallBack($fnUpAttachBox, res) {
        //上传后的回调
        var _html = '';
        var _$fnUpAttachUl = $fnUpAttachBox.find('.fnUpAttachUl'),
            isCanDownload = $fnUpAttachBox.find('.fnUpAttachBtn').attr('todownload') || false, //是否开启下载功能
            isImg = util.isImg(res.fileName), //文件名，判断是否是图片
            isOffice = !isImg ? judgeFiletype(res.fileName) == 'office' : false,
            isPPT = (!isImg && !isOffice) ? judgeFiletype(res.fileName) == 'ppt' : false,
            _more = '';


        if (!!isOffice) {

            _more = '<a class="attach-item-preview" target="_blank" href="/projectMg/contract/excelMessage.htm?contractItemId=0&read=read&hidTitle=hidTitle&fileName=' + encodeURIComponent(res.fileName) + '&fileUrl=' + res.serverPath + '&id=0">预览</a>'

        }

        if (isPPT) {
            _more = '<a class="attach-item-preview" target="_blank" href="' + res.fileUrl + '">预览</a>'
        }
        // console.log('isImg:' + isImg)
        // console.log('isOffice:' + isOffice)
        //图片
        var _html1 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '" title="' + res.fileName + '"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        //一般文件,开启下载
        var _html2 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a title="点击下载' + res.fileName + '" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        //未开启下载
        var _html4 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        //offfice || ppt
        var _html3 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a title="点击下载' + res.fileName + '" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0" target="_blank"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span>' + _more + '</span>';

        // _html = (!!isCanDownload && !isImg) ? _html2 : _html1; //下载开启(isCanDownload=true)并且不为图片(isImg=false)
        // _html = isImg ? _html1 : isOffice ? _html3 : isCanDownload ? _html2 : _html4;
        _html = isImg ? _html1 : (isOffice || isPPT) ? _html3 : _html2;

        _$fnUpAttachUl.append(_html);
        totalVal($fnUpAttachBox);

    };

    function swtichType(fileType) { //转换文件类型
        if (!fileType || fileType.length == 0) return;
        var newFileType = [];

        $.each(fileType.split(';'), function (i, e) {
            newFileType.push(e.toUpperCase());
            newFileType.push(e.toLowerCase());
        });

        newFileType = newFileType.join(';');

        return newFileType;
    };

    function judgeFiletype(fileName) { //判断是否是office或者PPt文件
        var _fileName = fileName || '',
            _fileNameArr = _fileName.split('.'),
            _type = _fileNameArr[(_fileNameArr.length - 1)].toLowerCase(), //文件名.后面最后为文件类型
            fileType1 = ['doc', 'docx', 'xlsx', 'xls'],
            fileType2 = ['ppt', 'pptx'],
            _fileTypeRslt = false;
        if (fileType1.indexOf(_type) >= 0) {
            _fileTypeRslt = 'office';
        } else if (fileType2.indexOf(_type) >= 0) {
            _fileTypeRslt = 'ppt'
        } else {
            _fileTypeRslt = false;
        };
        return _fileTypeRslt;
    };
});