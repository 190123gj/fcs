/**
 * Created by eryue
 * Create in 2016-11-28 17:51
 * Description:
 */

'use strict';

define(function(require, exports, module) {

    require('validate');
	require('zyw/publicPage');
    // require('input.limit');
    require('Y-msg');
    require('tmbp/upAttachModify');
    var util = require('util');


    var projectCode = $('#projectCode').val();

    $('body').on('click', '.deleteAttachmentFiles', function () {

        var url = $(this).attr('datahref');
        Y.confirm('提示', '是否确认删除当前附件？', function (opt) {
            if (opt == 'yes') {
                $.ajax({
                    url: url,
                    success: function (res) {

                        if (!!res.success) {
                            Y.alert('提示', res.message, function () {
                                window.location.href = "/systemMg/attachmentManage/viewList.htm?projectCode=" + projectCode;
                            });
                        } else {
                            Y.alert('提示', res.message);
                        }

                    }
                })
            }
        })

    }).on('click', '.uploadAttachmentFiles', function () {

        var url = $(this).attr('datahref');
        var _html = [
                '<div class="fnUpAttach">',
                '   <div class="fnUpAttach">',
                '   <label class="m-label"><span class="m-required">*</span>更新附件：</label>',
                '       <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn" maxitem="1"><i class="icon icon-add"></i>上传附件</a>',
                '       <input class="fnUpAttachVal fn-validate" type="hidden" name="file" id="fileInfo">',
                '       <div class="fn-blank5"></div>',
                '       <div class="m-attach fnUpAttachUl"></div>',
                '   </div>',
                '</div>',
                '<div class="m-submit-btn">',
                '   <input type="button" class="ui-btn ui-btn-fill ui-btn-green submit-btn" value="提交保存" datahref="',url,'">',
                '</div>'
                ].join("");

        var $content = [
            '<div class="wnd-wnd uploadFileM">',
            '   <form method="POST" id="uploadfileForm">',
            '      <div class="wnd-header" title="可拖动" style="cursor: move; width: 600px;"><span class="wnd-title">重新上传</span> ',
            '            <a href="javascript:void(0)" class="wnd-close" title="关闭"></a>',
            '      </div>',
            '      <div class="wnd-body updateFiles" style="display: block; width: 600px; height: auto; overflow: auto;">',_html,
            '      <div>',
            '   </form>',
            '</div>'
        ].join("");
        $('body').Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.wnd-close'
        });

    }).on('click','.submit-btn',function () {

        var url = $(this).attr('datahref');
        var fileInfo = $('#fileInfo').val();

        if(!fileInfo){
            Y.alert('提示','上传附件为必填！');
            return;
        };

        var file = fileInfo.split(',');
        var data = {
            fileName: file[0],
            filePhysicalPath: file[1],
            requestPath: file[2]
        };
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            success: function (res) {

                if (!!res.success) {
                    Y.alert('提示', res.message, function () {
                        window.location.href = "/systemMg/attachmentManage/viewList.htm?projectCode=" + projectCode;
                    });
                } else {
                    Y.alert('提示', res.message);
                }

            }
        })
    }).on('click','.fnWriteTMP',function () {
        var $this = $(this);
        var fileName = $this.attr('filename');
        var isImg = util.isImg(fileName);
        if(!isImg) return;
        $this.parents('tr').find('.fileVIews .fnAttachView').click();
    });

    $(function () {
        $.each($('.fnWriteTMP'),function () {
            var $this = $(this);
            var fileName = $this.attr('filename');
            var serverPath = $this.attr('filepath');
            var fileUrl = $this.attr('requestPath');
            var isImg = util.isImg(fileName);
            var isOffice = !isImg ? judgeFiletype(fileName) == 'office' : false;
            var isPPT = (!isImg && !isOffice) ? judgeFiletype(fileName) == 'ppt' : false;
            if(isOffice){
                var officeUrl = $this.attr('datahref');
                $this.attr('href',officeUrl).removeClass('fnWriteTMP');
                return;
            };
            if(!isImg){
                var href = '/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(fileName) + '&path=' + serverPath + '&id=0';
                $this.attr('href',href).removeClass('fnWriteTMP');
                return;
            };

            var _html = '<span class="attach-item fnAttachItem" val="' + fileName + ',' + serverPath + ',' + fileUrl + '" title="' + fileName + '"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + fileName + '</span><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
            $this.removeAttr('target');
            $this.parents('tr').find('.fileVIews').html(_html);
        })
    });
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

})