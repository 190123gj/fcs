define(function(require, exports, module) {
require('zyw/upAttachModify')
    // 审核通用部分 start
    var auditProject = require('/js/tmbp/auditProject');
    var util = require('util');
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

    function printdiv(printpage) {
        var headstr = "<html><head><title></title></head><body>";
        var footstr = "</body>";
        var newstr = document.all.item(printpage).innerHTML;
        var oldstr = document.body.innerHTML;
        document.body.innerHTML = headstr + newstr + footstr;
        window.print();
        document.body.innerHTML = oldstr;
        return false;
    }

    // $('#fnPrint').click(function (event) {
    //     printdiv('div_print');
    // });
    $('#fnPrint').click(function (event) {
        var $fnPrintBox = $('#div_print')
        util.print($fnPrintBox.html())
    })
});