define(function(require, exports, module) {
    // 项目管理 > 理财项目管理 > 理财产品利息计提明细查询
    require('zyw/publicPage');

    var yearsTime = require('zyw/yearsTime');

    var TODAY_DATE = new Date();
    var getMonth = TODAY_DATE.getMonth() + 1;

    $('.fnInputTime').click(function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: this,
            max: TODAY_DATE.getFullYear() + '-' + (getMonth.toString().length == 1 ? '0' + getMonth : getMonth)
        });
    });
});