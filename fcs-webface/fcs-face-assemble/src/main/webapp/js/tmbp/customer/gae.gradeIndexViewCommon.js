define(function(require, exports, module) {


    //上传
    require('zyw/upAttachModify');

    // 审核通用部分 start
    var auditProject = require('/js/tmbp/auditProject');
    var _auditProject = new auditProject('auditSubmitBtn');
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit({
        // 初始化审核
        // 自定义 确定函数
        // doPass: function(self) {
        //  alert('1');
        //  self.audit.$box.find('.close').eq(0).trigger('click');
        // },
        // doNoPass: function(self) {
        //  alert('3');
        //  self.audit.$box.find('.close').eq(0).trigger('click');
        // },
        // doBack: function(self) {
        //  alert('2');
        //  self.audit.$box.find('.close').eq(0).trigger('click');
        // }
    }).initPrint('打印的url');

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.addOPN([]).init().doRender();
    //------ 侧边栏 end

    addClassFun = function(index, el) {

        $(el).addClass('cancel');

    }

    removeClassFun = function(index, el) {

        $(el).removeClass('cancel');

    }

    //页面dom操作
    $('body').on('change', '.qualifiedCheckbox', function(event) {

        var judge = $(this).attr('checked')

        if (judge) {

            $('.qualified').hide().find('input,select').each(addClassFun);
            $('.onQualified').show().find('input,select').each(removeClassFun);

        } else {

            $('.qualified').show().find('input,select').each(removeClassFun);
            $('.onQualified').hide().find('input,select').each(addClassFun);
            $('body').find('#aboutRadio [type="radio"]:checked').trigger('change');

        }


    }).find('.qualifiedCheckbox').trigger('change');

    $('body').on('change', '#aboutRadio [type="radio"]', function(event) {

        var index = $(this).index(),
            $step = $('.step'),
            $about = $('#about').children();

        $about.eq(index).show().find('input,select').each(removeClassFun).end()
            .siblings().hide().find('input,select').each(addClassFun);

        index ? $step.hide() : $step.show();

    }).find('#aboutRadio [type="radio"]:checked').trigger('change');


    //按钮展示情况控制
    var active, length;

    active = parseInt($('#step li.active').index());
    length = $('#step li').length;

    if (active == 0) {

        $('[stepatt="prev"]').remove();

    } else if (active == length - 1) {

        $('[stepatt="next"]').remove();

    }

    //计算总分
    var sumScore = 0;
    $('.Score').each(function(index, el) {

        var $this, text;

        $this = $(this);

        text = parseFloat($this.text() || 0);

        sumScore += text;

    });
    $('.sumScore').text(sumScore.toFixed(1));


    var mergeTable = require('zyw/mergeTable');
    new mergeTable({

        //jq对象、jq selector
        obj: '.demandMerge',

        //默认为false。true为td下有input或者select
        valType: false,

        //每次遍历合并会调用的callback
        mergeCallback: function(Dom) { //Dom为每次遍历的合并对象和被合并对象

            // var Obj, text, index;

            // Obj = Dom['mergeObj'];
            // text = Obj.text();
            // index = Obj.index();

            // if (!index) {

            //     Obj.html(text + '<div class="headmanCover"><i class = "xlsTop fn-p-abs"></i><i class = "xlsLeft fn-p-abs"></i><i class = "xlsRight fn-p-abs"></i><i class = "xlsBottom fn-p-abs"></i></div>');

            // }

        },
        callback: function() { //表格合并完毕后
        }
    })

})