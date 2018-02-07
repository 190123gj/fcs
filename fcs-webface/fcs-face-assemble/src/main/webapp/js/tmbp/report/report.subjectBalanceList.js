/*
* @Author: erYue
* @Date:   2016-08-16 10:40:40
* @Last Modified by:   erYue
* @Last Modified time: 2016-08-22 19:25:18
*/

define(function(require, exports, module) {
    //上传
    require('Y-htmluploadify');
    require('zyw/publicPage');
    require('tmbp/chooseDateNew');

    var $body = $('body');
    var timer;

    $body.on('click', '.uploadReport', function() {//上传
        var _this = $(this);
        var $thisPs = _this.parents('tr');
        var timeArry = [];

        if ($thisPs.length > 0) {

            timeArry = $thisPs.find('.timeMark').html().split('-');
            chooseWindBox(timeArry,false);

        }else {
            chooseWindBox(timeArry,true);
        };

    }).on('click', '.fnUpAttachBtn', function() {//上传附件
        var _this = $(this),
            _filetype = _this.attr('filetype') || util.fileType.all, //自定义上传类型
            _maxitem = _this.attr('maxitem') || 99,
            _fileSizeLimit = _this.attr('fileSizeLimit') || '5MB',
            $fnUpAttachBox = _this.parents('.fnUpAttach');

        var year = _this.parents('.choose-time-box').find('[name="year"]').val();
        var month = _this.parents('.choose-time-box').find('[name="month"]').val();

        if (!!!year || !!!month) {//是否选择时间
            Y.alert('提示','请先选择时间！');
            return;
        };

        //是否超过
        if ($fnUpAttachBox.find('.fnAttachItem').length >= _maxitem) {
            Y.alert('提醒', '已超过最大上传个数限制');
            return;
        };
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/reportMg/submission/uploadAccountBalance.json?year=' + year + '&month=' + month,
            multi: false,
            auto: true,
            addAble: false,
            fileSizeLimit: _fileSizeLimit,
            fileTypeExts: _filetype,
            fileObjName: 'UploadFile',
            onQueueComplete: function(a, b) {},
            onUploadSuccess: function($this, res, resfile) {
                console.log(res)
                var _res = $.parseJSON(res);
                if (_res.success) {
                    window.location.href = '/reportMg/submission/listBalance.htm';
                }else {
                    Y.alert('提示',_res.message)
                }
            },
            onUploadError: function($this, file) {
                console.log('出事啦')
            },
            renderTo: 'body'
        });

    });

    function chooseWindBox(arry,isCanChoose) { //isCanChoose用来判断是否能够选择

        var currentTime = new Date();
        var year = !!arry[0] ? arry[0] : currentTime.getFullYear();
        var month = !!arry[1] ? arry[1] : (currentTime.getMonth() + 1);
        // var eventClassName = (isCanChoose == false) ? '' : 'chooseTime';
        var eventClassName = (isCanChoose == false) ? '' : 'chooseYM';
        month = parseInt(month) < 10 ? '0' + parseInt(month) : month;
        $body.Y('Window', {
            content: '<div class="m-modal choose-time-box">'+
                '    <div class="view-files-hd">'+
                '        <span class="fn-right fn-usn fn-csp closeBtn" href="javascript:void(0);">×</span>'+
                '        <span class="view-files-ttl">选择会计期间</span>'+
                '    </div>'+
                '    <div class="view-files-body">'+
                '       <div class="item chooseBox">会计期间：'+
                '           <input class="ui-text fn-w90 '+ eventClassName + '" type="text" value="' + year + '-' + month +'"  name="reportTime" placeholder="选择年月" readOnly="true">'+
                '           <input class="ui-text fn-w90 chooseYear" type="hidden" name="year" value="' + year + '" placeholder="选择年" readOnly="true">'+
                '           <input class="ui-text fn-w90 chooseMonth" type="hidden" name="month" value="' + month + '" placeholder="选择月" readOnly="true">'+
                // '           <input class="ui-text fn-w90 chooseYear ' + eventClassName + '" type="hidden" name="year" value="' + year + '" placeholder="选择年" readOnly="true">'+
                // '           <input class="ui-text fn-w90 chooseMonth ' + eventClassName + '" type="hidden" name="month" value="' + month + '" placeholder="选择月" readOnly="true">'+
                '       </div>'+
                '       <div class="fn-mt20 fnUpAttach">' +
                '           <label class="m-label">报表上传：</label>'+
                '           <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn" filetype="xls;xlsx"><i class="icon icon-add"></i>上传附件</a>' +
                '           <input class="fnUpAttachVal" type="hidden" name="file">' +
                '           <div class="fn-blank5"></div>'+
                '           <div class="m-attach fnUpAttachUl" hidden></div>'+
                '       </div>'+
                '       <!--<div class="btns fn-mt40 fn-tac">' +
                '           <input type=button class="ui-btn ui-btn-fill ui-btn-green fn-mr30 sureBtn" value="确定">' +
                '           <input type=button class="ui-btn ui-btn-fill ui-btn-gray closeBtn" value="取消">' +
                '       </div>-->'+
                '    </div>'+
                '</div>',
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn'
        });
    }
})