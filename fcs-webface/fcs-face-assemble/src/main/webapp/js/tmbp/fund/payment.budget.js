define(function (require, exports, module) {
    // 资金管理 > 差旅费报销单新增

    require('zyw/publicPage');

    require('zyw/upAttachModify');
    var yearsTime = require('zyw/yearsTime');

    require('./payment.base.pay');

    var util = require('util');
    var getList = require('zyw/getList');
    var $fnTbody = $('#fnListTbody');
    var $fnCategory;
    // 新打开窗口
    $fnTbody.on('click', '.fnChooseCostCategory', function () {
        $fnCategory = $(this).parent();
    });


    $('#fnListAdd').on('click', function () {
        setTimeout(function () {
            $fnTbody.find('tr').last().find('[value="NO"]').trigger('click')
        }, 0);
    });

    // ------ 收款人 start

    var _costList = new getList();
    _costList.init({
        title: '选择费用种类',
        ajaxUrl: '/baseDataLoad/costCategory.json',
        btn: '.fnChooseCostCategory',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.categoryNm}}">{{(item.categoryNm && item.categoryNm.length > 8)?item.categoryNm.substr(0,8)+\'\.\.\':item.categoryNm}}</td>',
                '        <td title="{{item.accountCode}}">{{item.accountCode + \' \' + item.accountName}}</td>',
                '        <td><a class="choose" categoryId="{{item.categoryId}}" categoryNm="{{item.categoryNm}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['费用种类：',
                '<input class="ui-text fn-w100" type="text" name="categoryNm">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>费用种类</th>',
                '<th>关联会计科目</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 4
        },
        callback: function ($a) {
            $fnCategory.find('.fnCategoryNm').val($a.attr('categoryNm'));
            $fnCategory.find('.fnCategoryId').val($a.attr('categoryId'));
        }
    });

    // ------ 筛选时间 start
    var $fnListSearchDateS = $('.fnListSearchDateS'),
        $fnListSearchDateE = $('.fnListSearchDateE');

    $('#fnDateSelect').on('change', function () {

        var _start,
            _end,
            _now = util.getNowTime(),
            _thisYear = _now.YY,
            _thisMonth = _now.MM;

        switch (this.value) {

            case 'YEAR':
                _start = _thisYear + '-01-01';
                _end = _thisYear + '-12-31';
                break;
            case 'MONTH':
                _start = _thisYear + '-' + _thisMonth + '-01';
                _end = _thisYear + '-' + _thisMonth + '-' + util.getDayOfMonth(_thisYear, _thisMonth);
                break;
            case 'QUARTER':
                var _a = ['01', '03', '04', '06', '07', '09', '10', '12'],
                    _i = Math.floor(_thisMonth / 3);
                _start = _thisYear + '-' + _a[_i * 2] + '-01';
                _end = _thisYear + '-' + _a[_i * 2 + 1] + '-' + util.getDayOfMonth(_thisYear, _a[_i * 2 + 1]);
                break;

        }

        $fnListSearchDateS.val(_start);
        $fnListSearchDateE.val(_end);

        // $fnListSearchDateS.val(_start).attr('onclick', 'laydate({max: "' + _end + '"})');
        // $fnListSearchDateE.val(_end).attr('onclick', 'laydate({min: "' + _start + '"})');

    });
    if (!!!$fnListSearchDateS.val() && !!!$fnListSearchDateE.val()) {
        $('#fnDateSelect').trigger('change');
    }
    // ------ 筛选时间 end


    // ------ 选择周期相关 start
    var $fnSelectYear = $('#fnSelectYear'),
        $fnSelectCycles = $('.fnSelectCycle');
    $('#fnSelectCycle').on('change', function () {

        $fnSelectCycles.addClass('fn-hide');
        if (this.value == 'YEAR') {
            $fnSelectCycles.eq(0).removeClass('fn-hide');
            $('#fnChooseThisYear').removeProp('checked');
        }
        if (this.value == 'SEASON') {
            $fnSelectCycles.eq(1).removeClass('fn-hide');
        }
        if (this.value == 'MONTH') {
            $fnSelectCycles.eq(2).removeClass('fn-hide');
        }

        $fnSelectYear.addClass('canChoose');

    }).trigger('change');

    $fnSelectYear.click(function (event) {
        if (!util.hasClass(this, 'canChoose')) {
            return
        }
        var yearsTimeFirst = new yearsTime({
            elem: this,
            format: 'YYYY'
        });
    });

    $('#fnChooseThisYear').on('click', function () {

        if ($(this).prop('checked')) {
            $fnSelectYear.removeClass('canChoose').val((new util.getNowTime()).YY);
        } else {
            $fnSelectYear.addClass('canChoose');
        }

    });

    var $fnSelectSeason = $('#fnSelectSeason'),
        $fnChooseThisSeason = $('#fnChooseThisSeason');

    $fnChooseThisSeason.on('click', function () {

        if ($fnChooseThisSeason.prop('checked')) {

            var _thisS = Math.floor((new util.getNowTime()).MM / 3) + 1;

            $fnSelectSeason.find('option').removeProp('selected')
                .end().find('option[value="' + _thisS + '"]').prop('selected', 'selected')
        }

    });

    $fnSelectSeason.on('change', function () {

        var _thisS = Math.floor((new util.getNowTime()).MM / 3) + 1;

        if (_thisS == this.value) {
            $fnChooseThisSeason.prop('checked', 'checked');
        } else {
            $fnChooseThisSeason.removeProp('checked');
        }

    });

    var $fnSelectMonth = $('#fnSelectMonth'),
        $fnChooseThisMonth = $('#fnChooseThisMonth');

    $fnChooseThisMonth.on('click', function () {

        if ($fnChooseThisMonth.prop('checked')) {

            var _thisS = +(new util.getNowTime()).MM;

            $fnSelectMonth.find('option').removeProp('selected')
                .end().find('option[value="' + _thisS + '"]').prop('selected', 'selected')
        }

    });

    $fnSelectMonth.on('change', function () {

        var _thisS = +(new util.getNowTime()).MM;

        if (_thisS == this.value) {
            $fnChooseThisMonth.prop('checked', 'checked');
        } else {
            $fnChooseThisMonth.removeProp('checked');
        }

    });
    // ------ 选择周期相关 end
});