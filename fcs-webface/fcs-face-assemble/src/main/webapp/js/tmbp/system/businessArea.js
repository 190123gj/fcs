define(function (require, exports, module) {
    require('zyw/publicPage')
    require('Y-msg')
    var util = require('util')
    var videoList = require('tmbp/assets/videoList')
    var ALL_REGION_LIST = [{
            name: '全国',
            code: 'China'
        }] // 所有中国地区
    var REGION_LIST_CHOOSE // 选择对象

    $.getJSON('/baseDataLoad/region.json?parentCode=China')
        .then(function (res) {
            if (res.success) {
                $.each(res.data, function (index, obj) {
                    ALL_REGION_LIST.push({
                        name: obj.name,
                        code: obj.code
                    })
                })
            }
        })

    // 显示分配弹出层 并初始化已选择数据
    function initSelectList(selected) {

        var _selected = selected || []

        REGION_LIST_CHOOSE = new videoList({
            ele: $fnDistributionBox.find('#fnDistributionSelect'),
            allVideoList: ALL_REGION_LIST,
            checkedList: _selected,
            textKey: 'name',
            validateKey: 'code'
        })

    }

    var $fnDistributionBox = $('#fnDistributionBox')

    // 分配
    $('#fnDistribution').on('click', function () {
        $fnDistributionBox.removeClass('fn-hide')
    })

    $fnDistributionBox.on('click', '.close', function () {
        $fnDistributionBox.addClass('fn-hide')
            .find('.fnListSearchOrgChoose').removeClass('fn-hide')
            .end().find('.fnListSearchOrgName').val('')
            .end().find('.fnListSearchOrgId').val('')

        document.getElementById('fnDistributionSelect').innerHTML = ''

    }).on('click', '.sure', function () {

        if (!!!$fnDistributionBox.find('.fnListSearchOrgId').val()) {
            Y.alert('提示', '请选择部门')
            return
        }

        var _code = [],
            _name = []

        $.each(REGION_LIST_CHOOSE.checkedList, function (i, obj) {
            if (obj.code == 'China') {
                _code = ['China']
                _name = ['全国']
                return false
            }
            _code.push(obj.code)
            _name.push(obj.name)
        })

        document.getElementById('fnDistributionCode').value = _code.join(',')
        document.getElementById('fnDistributionText').value = _name.join(',')

        util.ajax({
            url: $fnDistributionBox.attr('action'),
            data: $fnDistributionBox.serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', res.message, function () {
                        window.location.reload(true)
                    })
                } else {
                    Y.alert('操作失败', res.message)
                }
            }
        })

    }).on('change', '.fnListSearchOrgId', function () {

        var _id = this.value

        util.ajax({
            url: '/systemMg/busyRegoin/query.json?depPath=' + _id,
            success: function (res) {

                var _selected = []

                if (res.success) {

                    $.each(res.data, function (index, obj) {
                        _selected.push({
                            code: obj.code,
                            name: obj.name
                        })
                    })

                    // REGION_LIST_CHOOSE = new videoList({
                    //     ele: $fnDistributionBox.find('#fnDistributionSelect'),
                    //     allVideoList: ALL_REGION_LIST,
                    //     checkedList: _selected,
                    //     textKey: 'name',
                    //     validateKey: 'code'
                    // })

                }

                document.getElementById('fnDistributionSelect').innerHTML = ''

                initSelectList(_selected)

            }
        })

    })

    // 编辑
    $('.edit').on('click', function () {

        var _selected = []
        var _code = (this.getAttribute('scode') || '').split(',')
        var _cname = (this.getAttribute('sname') || '').split(',')

        for (var i = 0; i < _code.length; i++) {
            if (!!_code[i]) {
                _selected.push({
                    name: _cname[i],
                    code: _code[i]
                })
            }
        }

        initSelectList(_selected)

        $fnDistributionBox.removeClass('fn-hide')

        $fnDistributionBox.find('.fnListSearchOrgChoose').addClass('fn-hide')
            .end().find('.fnListSearchOrgName').val(this.getAttribute('deptname'))
            .end().find('.fnListSearchOrgId').val(this.getAttribute('deptid'))
    })

    // 区域限制
    $('#fnAreaLimits').on('click', function () {

        var self = this

        Y.confirm('提示', '是否' + self.innerHTML, function (opn) {

            if (opn == 'yes') {

                util.ajax({
                    url: '/systemMg/busyRegoin/changeStatus.json?status=' + !!self.getAttribute('isenable'),
                    success: function (res) {
                        Y.alert('提示', res.message, function () {
                            window.location.reload(true)
                        })
                    }
                })

            }

        })

    })



});