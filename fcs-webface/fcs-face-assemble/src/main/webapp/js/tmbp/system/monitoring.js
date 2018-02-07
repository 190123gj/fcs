define(function (require, exports, module) {

    require('Y-msg')

    /*
     * Can browse 可浏览
     *
     * 如果是空数据，不限制
     * 
     */
    var CAN_BROWSE_DATA = [],
        CAN_BROWSE_STR = window.location.search.replace(/^\?/, ''),
        CAN_BROWSE_IS = false,
        $fnConnect = $('#fnConnect'),
        $fnDataTree = $('#fnDataTree'),
        $fnDisconnect = $('#fnDisconnect');

    // 获取过滤的数据
    $.each(CAN_BROWSE_STR.split(','), function (index, str) {

        if (!!str) {

            CAN_BROWSE_DATA.push(decodeURIComponent(str))

            CAN_BROWSE_IS = true

        }

    })

    // 断开连接
    $fnDisconnect.on('click', function () {
        VMC_TOOL.disconnect($fnDataTree)
        $fnConnect.show()
        $fnDisconnect.hide()
    })

    // 关闭视频
    $('#fnStopVideo').on('click', function () {
        VMC_TOOL.stop()
    })

    // 抓拍
    $('#fnSnapVideo').on('click', function () {
        var res = VMC_TOOL.snap()
        if (res.success) {
            Y.alert('提示', res.message)
        } else {
            Y.alert('提示', '失败')
        }
    })

    // 播放音频
    $('#fnPlayAudio').on('click', function () {
        var res = VMC_TOOL.playaudio()
        if (res.success) {
            Y.alert('提示', res.message)
        } else {
            Y.alert('提示', '失败')
        }
    })

    // 录像
    $('#fnRecordVideo').on('click', function () {
        var _text = this.getAttribute('toggletext'),
            _btn = this.getElementsByTagName('span')[1],
            _btnText = _btn.innerHTML

        var res = VMC_TOOL.record()
        if (res.success) {
            _btn.innerHTML = _text
            this.setAttribute('toggletext', _btnText)
            Y.alert('提示', res.message)
        } else {
            Y.alert('提示', '失败')
        }
    })

    // 方向
    $('.fnTurnBtn').on('click', function () {

        VMC_TOOL.ptzcontrol(this.getAttribute('direction'), $(this))

    })

    $.when(VMC_TOOL.init())
        .then(function () {

            // 绑定事件
            $('#fnConnect').on('click', function () {

                // 链接屏
                $.when(VMC_TOOL.connect())
                    .then(function () {

                        $fnConnect.hide()
                        $fnDisconnect.show()

                        // 获取住列表
                        // 展示列表
                        var _mainListData = VMC_TOOL.getMainListData(),
                            _showData = []

                        if (!!CAN_BROWSE_DATA.length) {

                            $.each(_mainListData, function (index, obj) {

                                if ($.inArray(obj.text, CAN_BROWSE_DATA) >= 0) {
                                    _showData.push(obj)
                                }

                            });

                        } else {
                            _showData = _mainListData
                        }

                        VMC_TOOL.setDataTree($fnDataTree, _showData)

                    })
                    .fail(function (res) {
                        console.log(res.message)
                    })

            })

        })
        .fail(function (res) {
            console.log(res.message)
        })

});