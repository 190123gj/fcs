define(function(require, exports, module) {
    // var template = require('arttemplate');
    require('lib/lodash');

    var util = require('util');
    var mergeTable = require('zyw/mergeTable');
    var newMergeTable = new mergeTable();

    var $body = $('body');
    // var isAddSetTable = $('#isAddSetTable').length == 0 ? false : true;
    var $dataChooseBtn = $('.set-content-left label');
    var $nameBox = $('.set-content-right-b label');
    var maxNum = 30;
    var nameBoxIndex = 0; //用于记录盛放自定义字段索引
    var isChoosedObj = []; //记录哪些字段已被选取的标记
    var isChoosedName = []; //记录哪些字段已被选取的值
    var isChoosedObjTmp = []; //记录哪些字段已被选取的标记,缓存
    var isChoosedNameTmp = []; //记录哪些字段已被选取的值,缓存


    if($('.root1').length > 0){
        newMergeTable.init({
            obj: '.root1',
            valType: false,
            callback: function() {
                $('.hiddenTable').removeClass('hiddenTable');
            }
        });
    };

    $('#DATA_CHOOSE').find('.report-table').each(function (index1,ele1) {//给每个tochoosedata生成唯一编号
        var $thisP = $(this);
        var $thisPP = $thisP.parents('.tab-mod');
        var ftf = $thisPP.attr('id');
        var thisPPIndex = $body.find('.tab-mod').index($thisPP);
        $thisP.find('[tochoosedata]').each(function (index2,ele2) {
            var $this = $(this);
            var thisIndex = $thisPP.find('[tochoosedata]').index($this);
            $this.attr('flag',ftf + '-' + thisPPIndex + '-' + thisIndex);
        })
    });
    
    $dataChooseBtn.click(function () {

        reBackIsChoosedName(isChoosedObjTmp);

        var $this = $(this);
        var ftf = $this.attr('ftf');
        var $templateData = $($('#DATA_CHOOSE').html());
        var title = $templateData.find('#' + ftf + ' #title').val();
        var _thisDataTmp = $templateData.find('#' + ftf + ' .table-html-cnt').html();

        $body.Y('Window', {
            content: '<div class="m-modal set-data-table" ftf="'+ ftf +'">'+
            '    <div class="view-files-hd">'+
            '        <span class="fn-right fn-usn fn-csp closeBtn" href="javascript:void(0);">×</span>'+
            '        <span class="view-files-ttl">数据选择&nbsp;>&nbsp;' + title + '</span>'+
            '    </div>'+
            '   <p class="choosed-num-tips">当前还可以选择<em id="choosedNumTips">' + (maxNum - nameBoxIndex) + '</em>个字段</p>'+
            '    <div class="view-files-body">'+ _thisDataTmp +
            '    </div>'+
            '       <div class="btns fn-mt20 fn-mb20 fn-tac">' +
            '           <input type=button class="ui-btn ui-btn-fill ui-btn-green fn-mr30 sureBtn" value="确定保存">' +
            '           <input type=button class="ui-btn ui-btn-fill ui-btn-gray closeBtn" value="取消">' +
            '       </div>'+
            '</div>',
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn'
        });
    });

    $nameBox.find('i').click(function () {//删除字段
        var $thisBox = $(this).parent();
        var $thisBoxP = $thisBox.parent();
        var flag = $thisBox.attr('flag'); //读出当前字段唯一标记
        var name = $thisBox.attr('title'); //读出当前字段唯一标记

        $thisBox.removeAttr('flag').find('em').html('');
        $thisBoxP.append($thisBox);
        updateChoosedName(flag, name, 'del');
        isChoosedObjTmp = isChoosedObj;
        isChoosedNameTmp = isChoosedName;

    });

    // if(!isAddSetTable) return; //判断是否需要绑定此后事件

    $body.on('click','[tochoosedata]',function () {//选择字段
        var $this = $(this);
        var flag = $this.attr('flag');
        var name = $this.html().replace('：','');

        if(nameBoxIndex == maxNum){
            Y.alert('提示','最多只能选择' + maxNum + '个！');
            return;
        };
        if(typeof(name) == 'undefined') return;
        if($this.hasClass('isChoosed')){
            $this.removeClass('isChoosed');
            updateChoosedName(flag, name, 'del');

        }else {
            $this.addClass('isChoosed');
            updateChoosedName(flag, name, 'add');
        };

        $('#choosedNumTips').html(maxNum - nameBoxIndex);

    }).on('click', '.sureBtn', function () {
        $nameBox.find('em').html('');
        $.each(isChoosedObj,function(i,e) {
            $nameBox.eq(i).attr({
                'flag':isChoosedObj[i],
                'title':isChoosedName[i]
            }).find('em').html(isChoosedName[i]);
        });
        isChoosedObjTmp = isChoosedObj;
        isChoosedNameTmp = isChoosedName;
        $('.closeBtn').click();
    });

    function updateChoosedName(str,name,opt) {//更新选择字段

        if(opt == 'add'){
            isChoosedObj = _.union(isChoosedObj,[str]);//从记录序列中删除当前字段唯一标记
            isChoosedName = _.union(isChoosedName,[name]);//从记录序列中删除当前字段名称
            nameBoxIndex++; //更新当前盛放自定义字段索引
        }else{
            isChoosedObj = _.without(isChoosedObj,str);//从记录序列中删除当前字段唯一标记
            isChoosedName = _.without(isChoosedName,name);//从记录序列中删除当前字段名称
            nameBoxIndex--; //更新当前盛放自定义字段索引
        }
    };

    function reBackIsChoosedName(arry) {
        $('[tochoosedata]').removeClass('isChoosed');
        $.each(arry,function(i,e) {
            var id = e.split('-')[0];
            var index = e.split('-')[2];
            $('#DATA_CHOOSE').find('#' + id).find('[tochoosedata]').eq(index).addClass('isChoosed');
        })
    }

})