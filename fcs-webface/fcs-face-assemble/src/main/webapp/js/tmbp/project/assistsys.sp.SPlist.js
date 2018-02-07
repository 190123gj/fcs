define(function(require, exports, module) {
    require('input.limit');
    // 分页公共操作
    var util = require('util');

    //删等操作
    util.listOPN({
        del: {
            url: '/projectMg/specialPaper/deleteCheckIn.htm',
            message: '已删除',
            opn: '删除'
        }
    },'id');

    //全局方法
    window.toPage = function(totalPage, pageNo) {
        if (totalPage < pageNo) {
            return false;
        }
        document.getElementById('fnPageNumber').value = pageNo;
        document.getElementById('fnListSearchForm').submit();
    }

    //升降序
    var fnSortOrder = document.getElementById('fnSortOrder') || {},
        fnSortCol = document.getElementById('fnSortCol') || {},
        $fnListSearchTh = $('#fnListSearchTh');

    //还原表头的升降序状态
    if (fnSortCol.value) {
        var _$markTh = $fnListSearchTh.find('th[sortcol="' + fnSortCol.value + '"]');
        if (_$markTh.length) {
            _$markTh[0].innerHTML += '<span class="fn-fs16 fn-ml5 fn-fwb">' + ((fnSortOrder.value == 'ASC') ? '&uarr;' : '&darr;') + '</span>';
            _$markTh[0].className += (fnSortOrder.value == 'ASC') ? ' ASC' : ' DESC';
        }
    }
    $fnListSearchTh.on('click', 'th', function() {

        var _this = $(this),
            _key = _this.attr('sortcol');
        if (!!!_key) {
            return;
        }
        //键名
        fnSortCol.value = _key;
        //升降序
        if (_this.hasClass('ASC')) {
            fnSortOrder.value = 'DESC';
        } else {
            fnSortOrder.value = 'ASC';
        }
        document.getElementById('fnListSearchForm').submit();

    });

    //搜索按钮
    $('#fnListSearchBtn').on('click', function() {
        document.getElementById('fnPageNumber').value = '1';
        document.getElementById('fnListSearchForm').submit();
    });

    //翻页的go
    $('#fnListSearchGo').on('click', function() {

        var _input = +document.getElementById('fnListSearchInput').value,
            _max = +$(this).attr('maxitem');
        if (_input > _max || _input <= 0) {
            return;
        }
        document.getElementById('fnPageNumber').value = _input;
        document.getElementById('fnListSearchForm').submit();

    });


});