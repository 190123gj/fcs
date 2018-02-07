/*
* @Author: erYue
* @Date:   2016-08-08 15:09:22
* @Last Modified by:   erYue
* @Last Modified time: 2016-08-08 15:40:53
*
<div class="selectFnBox">
    <span class="ui-text fn-w200 fnInput selectFn fn-text-overflow">
    <label>请选择</label>
    <i class="icon icon-select-down"></i>
    <input class="ui-text fn-w200" type="hidden" name="typeId" value="$!info.typeId">
    <input class="ui-text fn-w200 assetType fn-validate parentsMitem" type="hidden" name="assetType" >
    </span>
    <div class="selectRslt fn-p-abs fn-usn">
    #foreach($!level in $!listInfo)
    <p class="fn-pl25 loadType fn-text-overflow" levelOne="$!level.levelOne" title="$!level.levelOne"><i class="icon icon-select-left fn-ml5"></i>$!level.levelOne</p>
    #end
    </div>
</div>
*/

define(function(require, exports, module) {

    var $select = $('.selectFn'),
        $selectRslt = $('.selectRslt');

    var timer = null; //定时器

    $select.on('click', function() {
        $selectRslt.addClass('showBox');
    }).mouseover(function() {
        clearTimeout(timer);
    }).mouseout(function() {
        hideBox(200);
    });

    $selectRslt.on('click', 'p', function() {

        var $this = $(this);
        if ($this.hasClass('loadType')) {//不是末级分类，请求下一级分类

            var isLoad = $this.hasClass('isLoad'); //isLoad表示已经加载并缓存过了，不一定显示
            var isShow = $this.hasClass('isShow'); //isShow表示已经加载并显示

            if (isShow) {

                $this.removeClass('isShow').find('i').removeClass('down');
                $this.next().removeClass('isShow');

            }else {

                if (!isLoad) {//第一次请求

                    loadType ($this);//请求加载分类
                    $this.addClass('isLoad');

                };

                $this.addClass('isShow').find('i').addClass('down');

                if ($this.next().hasClass('isDiv')) {

                    $this.next().addClass('isShow');

                }
            }

            $this.parents('.selectRslt').show();

        }else {//末级分类，显示当前选择项，完成后执行回调

            //显示当前选择项
            $select.find('label').html($this.html());
            $select.find('[name="assetType"]').val($this.attr('valueData'));
            $select.find('[name="typeId"]').val($this.attr('typeId'));
            hideBox (20);

            //回调，渲染数据
            // tableData ($this);

        }

    }).mouseover(function() {
        clearTimeout(timer);
    }).mouseout(function() {
        hideBox(1000);
    });

    function hideBox (t) {//初始化状态

        timer = setTimeout(function () {
            $selectRslt.removeClass('showBox').find('div,p').removeClass('isShow');
            $selectRslt.find('i').removeClass('down')
        }, t);

    };

    function loadType ($ele) {//加载数据

        var data = {};
        var levelTwoVal = $ele.attr('levelTwo');
        data.levelOne = $ele.attr('levelOne');

        if (typeof(levelTwoVal) != 'undefined') {
            data.levelTwo = levelTwoVal;
        };

        $.ajax({

            url: '/assetMg/loadType.htm',
            type: 'post',
            data: data,
            success: function(res) {

                var resList= res.listInfo

                if (resList.length > 0) {

                    var $temp = $('<div class="isShow isDiv"></div>');

                    if (typeof(levelTwoVal) == 'undefined') {

                        $.each(resList, function(i, el) {
                            // var valueData = data.levelOne + ' - ' + data.levelTwo + ' - ' + resList[i].levelThree;
                            $temp.append('<p class="fn-pl35 loadType fn-text-overflow" levelOne="' + data.levelOne + '" levelTwo="' + resList[i].levelTwo + '" title="' + resList[i].levelTwo + '"><i class="icon icon-select-left fn-ml15"></i>' + resList[i].levelTwo + '</p>');
                        });

                    };

                    if (typeof(levelTwoVal) != 'undefined') {

                        $.each(resList, function(i, el) {

                            var valueData = data.levelOne + ' - ' + data.levelTwo + ' - ' + resList[i].levelThree;

                            $temp.append('<p class="fn-pl45 fn-text-overflow" levelOne="' + data.levelOne + '" levelTwo="' + data.levelTwo + '" levelThree="' + resList[i].levelThree + '" typeId="' + resList[i].typeId + '" valueData="' + valueData + '" title="' + valueData + '">' + resList[i].levelThree + '</p>');

                        });

                    };

                    $ele.after($temp);

                } else {
                    Y.alert('提醒', res.message);
                };

            }

        });

    };
});