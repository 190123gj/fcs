/*
 * 视频监控 公共操作
 *
 * init 初始化 deferred 对象
 *
 * connect 链接服务器 deferred 对象
 * 
 */
var VMC_TOOL = typeof VMC_TOOL == 'object' ? $_GLOBAL : {};

// 获取数据的时候需要这个参数
VMC_TOOL.connectId = null

// 当前激活的监控名称
VMC_TOOL.activeWindowName = ''

// 初始化插件对象，必须初始化成功后才可以调用所有接口
VMC_TOOL.load = function () {
    //初始化插件
    try {
        var rv = P_LY.Init(new P_LY.Struct.InitParamStruct(true, function (msg) {}));
        if (rv.rv != P_Error.SUCCESS) {
            return false;
        }
        // 加载默认的连接参数
    } catch (e) {
        // $("#msg_bar").html(e.name + "," + e.message);
        return false;
    }
}

// 释放插件对象，网页退出必须释放插件，否则会出现浏览器异常的错误
VMC_TOOL.unload = function () {
    P_LY.UnLoad();
}

// 要先初始化才能惊喜其他操作

VMC_TOOL.init = function () {

    var dtd = $.Deferred()

    if (window.attachEvent) {
        window.attachEvent('onload', function () {
            VMC_TOOL.load();
            return dtd.resolve();
        });
        window.attachEvent('onunload', function () {
            VMC_TOOL.unload();
            return dtd.reject({
                message: 'onunload'
            })
        });
        window.attachEvent('onbeforeunload', function () {
            VMC_TOOL.unload();
            return dtd.reject({
                message: 'onbeforeunload'
            })
        });
    } else {
        window.addEventListener('load', function () {
            VMC_TOOL.load();
            return dtd.resolve();
        }, false);
        window.addEventListener('unload', function () {
            VMC_TOOL.unload();
            return dtd.reject({
                message: 'onunload'
            })
        }, false);
        window.addEventListener('beforeunload', function () {
            VMC_TOOL.unload();
            return dtd.reject({
                message: 'onbeforeunload'
            })
        }, false);
    }

    return dtd.promise();

}

// 连接服务器
VMC_TOOL.connect = function () {

    var dtd = $.Deferred()

    // 创建连接,并记录在P_LY.Connections中
    // _cf 来自 conf.js
    var ip = _cf.connParams.ip;
    var port = _cf.connParams.port;
    var uid = _cf.connParams.uid;
    var epId = _cf.connParams.epId;
    var pwd = _cf.connParams.pwd;
    var bfix = _cf.connParams.bfix;

    // 构造连接对象
    var param = new P_IF.Struct.ConnParamStruct(ip, port, uid, epId, pwd, bfix || 0);

    // 连接平台
    var conn = P_LY.Connect(param);

    if (conn.rv == P_Error.SUCCESS) {
        VMC_TOOL.connectId = conn.response; // 连接成功，返回connectId，此参数很重要，后面很多操作都需要

        // 开接收流状态事件
        P_LY.NCNotifyManager.Add(P_LY.Enum.NCObjectNotify.stream_status_notify, VMC_TOOL.notify);

        return dtd.resolve()

    } else {
        return dtd.reject({
            message: 'connect: ' + P_Error.Detail(conn.rv)
        })

        // console.log(P_Error.Detail(conn.rv));
    }

    return dtd.promise();

}

// 暂时不知道是什么
VMC_TOOL.notify = function (notify) {
    // 获取显示的DOM
    var container = P_LY.WindowContainers.get('windowbox');
    if (container) {

        if (container.window.status.playvideoing) {

            if (notify.eventName == 'stream_status_notify' && notify._HANDLE == container.window.params.ivStreamHandle) {

                var ivName = container.window.customParams.cameraName;
                var status = "";

                switch (Number(notify.status)) { //-1表示流已断开，0表示正在缓冲，1表示正在播放,2表示正在下载，3表示下载完成
                    case -1:
                        status = '流已断开'
                        break;
                    case 0:
                        status = '正在缓冲'
                        break;
                    case 1:
                        status = '正在播放'
                        break;
                }

                var _bite = function (bite) {
                    return (bite / 1000).toFixed(0) + 'KB/s';
                }

                var bitrate = notify.keyData.bit_rate;
                var bite = _bite(bitrate)
                var framerate = notify.keyData.frame_rate;
                var frame = framerate;
                var recStr = '';
                if (container.window.status.recording) {
                    status += ',正在录像'
                }

                var audStr = '';
                if (container.window.status.playaudioing) {
                    status += ',正在播放声音'
                }

                var upaudStr = '';
                if (container.window.status.calling) {
                    status += ',正在喊话'
                }

                var talkStr = '';
                if (container.window.status.talking) {
                    status += ',正在对讲'
                }

                var fb = '帧率：' + frame + '，码率：' + bite;
                $('#windowtitle').html(ivName + ',' + fb + ',' + status);
            }

        }

    }
}


VMC_TOOL.disconnect = function ($tree) {

    this.stop()

    var rv = P_LY.DisConnection(this.connectId)

    if (rv.rv == P_Error.SUCCESS) {

        this.connectId = null
        $tree.tree('loadData', [])

    } else {

        console.log(P_Error.Detail(rv.rv))

    }

}

// 获取主列表的数据
VMC_TOOL.getMainListData = function () {

    var offset = 0;
    var cnt = 100; // 这里demo只查询100个资源，实际应用，可以根据需求，如果返回的节点数等于要查询的数，说明可能还有资源，可以继续获取    
    var rv = P_LY.ForkResource(VMC_TOOL.connectId, P_LY.Enum.ForkResourceLevel.nppForkPUInfo, offset, cnt, '', '');

    var VM_DATA = [] // 所有数据资源

    if (rv.rv == P_Error.SUCCESS) {

        for (var i = 0; i < rv.response.length; i++) {

            var pu = rv.response[i];

            if (pu.modelType == "ENC" || pu.modelType == "WENC") {
                VM_DATA.push({
                    id: pu._HANDLE,
                    text: pu.name,
                    attributes: {
                        pu: pu
                    },
                    state: "closed",
                    children: [{
                        text: '正在查询资源……'
                    }]
                });
            }

        }

    }

    return VM_DATA

}


/**
 * 获取详细信息
 *
 * Defeered 对象
 * 
 * @param  {[Object]} mainInfo [主要列表中的 具体对象]
 * @return {[Array]}           [具体有多少个摄像头的列表]
 */
VMC_TOOL.getDetailedData = function (mainInfo) {

    var dtd = $.Deferred()

    var rv = P_LY.ForkResource(VMC_TOOL.connectId, P_LY.Enum.ForkResourceLevel.nppForkPUResourceInfo, 0, 120, '', {
        puid: mainInfo.attributes.pu.puid,
        _HANDLE: mainInfo.id
    })

    if (rv.rv == P_Error.SUCCESS) {
        var nodes = new Array()
        for (var j = 0; j < rv.response.length; j++) {
            var pures = rv.response[j]

            // 这里只列出视频输入资源
            if (pures.type == P_LY.Enum.PuResourceType.VideoIn) {
                nodes.push({
                    id: pures._HANDLE,
                    text: pures.name,
                    attributes: {
                        pu: mainInfo.attributes.pu,
                        self: pures
                    }
                })
            }

        }

        // 延时为了避免浏览卡死
        setTimeout(function () {
            return dtd.resolve({
                data: nodes
            })
        }, 500)

    } else {

        return dtd.reject({
            success: false,
            message: P_Error.Detail(rv.rv)
        })

    }

    return dtd.promise()

}

/**
 * 获取所有数据
 *
 * Defeered 对象
 * 
 * @return {[Arryay]} [包含主列表、每个主列表下的详细子数据]
 * 
 */
VMC_TOOL.getAllDetailedData = function () {

    var dtd = $.Deferred()

    var self = this,
        _mainList = this.getMainListData(),
        _length = _mainList.length,
        _index = 0

    __loop(_mainList[0])

    /**
     * 获取主列表下的子数据信息
     * @param  {[Object]} mainInfo [主列表中具体的对象]
     */
    function __loop(mainInfo) {

        $.when(self.getDetailedData(mainInfo))
            .then(function (res) {
                mainInfo.subList = res.data
                __complete()
            }).
        fail(function () {
            __complete()
        })

    }

    /**
     * 遍历主列表
     * 获取详细数据后的回调
     */
    function __complete() {

        if (_index >= _length) {
            return dtd.resolve(_mainList)
        }
        __loop(_mainList[_index++])

    }

    return dtd.promise()

}

/**
 * 简历选择摄像头的树
 * @param {[Object]} $tree [树结构DOM的jQuery对象]
 * @param {[Array]}  data  [树结构的数据，主列表]
 */
VMC_TOOL.setDataTree = function ($tree, data) {

    var self = this

    $tree.tree({
        onExpand: function (node) {

            // 展开单个主列表，获取每个子列表的数据

            var childs = $(this).tree('getChildren', node.target);

            if (childs.length == 1) {

                var l = node.children[0]

                if (l.text == '正在查询资源……') {

                    $.when(self.getDetailedData(node))
                        .then(function (res) {

                            for (var i = 0; i < childs.length; i++) {
                                $tree.tree('remove', childs[i].target);
                            }

                            $tree.tree('append', {
                                parent: node.target,
                                data: res.data
                            });

                        })

                } else {
                    l.text = '没有可用的资源';
                }

            }

        },
        onCollapse: function (node) {
            //console.log(node)
        },
        onDblClick: function (node) {
            // 双击主列表中的子节点 播放视频
            var attr = node.attributes;
            if (attr.pu.online != '1') {
                console.log(pu.name + '不在线');
                return;
            }
            self.play(attr.pu, attr.self);
            return true;
        }
    });

    $tree.tree('loadData', data);

}

// 播放视频
VMC_TOOL.play = function (pu, pures) {

    var self = this

    // 窗口对象一定放到WindowContainers，这个请开发人员注意
    if (!P_LY.WindowContainers.get('windowbox')) {
        P_LY.WindowContainers.set('windowbox', new P_LY.Struct.WindowContainerStruct('windowbox', P_LY.Enum.WindowType.VIDEO));
    }

    if (P_LY.WindowContainers.get('windowbox')) {
        var window = P_LY.WindowContainers.get('windowbox').window;
        if (window != null) {
            if (window.status.playvideoing) {
                self.stop('windowbox');
            }
        }


        var menu_params = {
            menu_command: {
                status: true,
                menu: [{
                    key: 'stopvideo',
                    text: '停止视频'
                }, {
                    key: 'snapshot',
                    text: '抓拍'
                }, {
                    key: 'record',
                    text: '本地录像'
                }, {
                    key: 'playaudio',
                    text: '播放音频'
                }],
                callback: function (key) {
                    switch (key) {
                        case 'stopvideo':
                            self.stop();
                            break;
                        case 'snapshot':
                            self.snap();
                            break;
                        case 'record':
                            self.record();
                            break;
                        case 'playaudio':
                            self.playaudio();
                            break;
                    }
                }
            }
        }
        var wndEvent = new P_LY.Struct.WindowEventStruct();
        wndEvent.menu_command.status = menu_params.menu_command.status;
        wndEvent.menu_command.menu = menu_params.menu_command.menu;
        wndEvent.menu_command.callback = menu_params.menu_command.callback;

        var rv = P_LY.CreateWindow(self.connectId, 'windowbox', P_LY.Enum.WindowType.VIDEO, wndEvent);

        if (rv.rv != P_Error.SUCCESS) {
            console.log('创建窗口失败,rv' + rv.response);
            return false;
        }

        window = rv.response;

        window.customParams.cameraName = pures.name;
        P_LY.WindowContainers.get('windowbox').window = window;
        P_LY.WindowContainers.get('windowbox').description = pures;
        var operator = P_LY.PlayVideo(self.connectId, window, pu.puid, pures.idx);
        P_LY.ResizeWindowDimension(window, "100%", "100%");
        if (operator.rv != P_Error.SUCCESS) {
            P_LY.ResizeWindowDimension(window, 0, 0);
            console.log('播放视频失败,rv =' + operator.rv + ',response=' + operator.response);
            return false;
        }
        $('#playtoolbar').show();
        this.activeWindowName = pu.name
        console.log('正在播放视频' + pu.name);
    }
}

/**
 * 停止视频播放
 */
VMC_TOOL.stop = function () {
    if (!P_LY.WindowContainers.get('windowbox')) {
        return
    }
    console.log('关闭视频' + P_LY.WindowContainers.get('windowbox').window.customParams.cameraName)
    P_LY.StopVideo(P_LY.WindowContainers.get('windowbox').window)
    P_LY.ResizeWindowDimension(P_LY.WindowContainers.get('windowbox').window, 0, 0)
    $('#windowtitle').html('无视频')
    $('#playtoolbar').hide()
}

VMC_TOOL.getNowTime = function () {
    var _now = new Date(),
        _time = _now.getFullYear() + '-' + (_now.getMonth() + 1) + '-' + _now.getDate() + 'x' + _now.getTime()

    return _time
}

/**
 * 抓拍
 * @param  {[string]} path [存储照片的路径]
 * @return {[type]}        [description]
 */
VMC_TOOL.snap = function (path) {

    var _path = path || 'D:/',
        wnd = P_LY.WindowContainers.get('windowbox').window

    if (typeof wnd != 'object') {
        return {
            success: false
        }
    }

    if (wnd.containerId == 'windowbox') {
        if (wnd.status.playvideoing) {
            var _name = this.activeWindowName + '-' + this.getNowTime() + '.bmp'
            var rv = P_LY.Snapshot(wnd, _path, _name)
            console.log('抓拍成功,保存路径为:' + _path);
            return {
                success: true,
                message: '抓拍成功,保存路径为:' + _path
            }
        }
    }

}


/**
 * 录制视频
 * @param  {[type]} path [description]
 * @return {[type]}      [description]
 */
VMC_TOOL.record = function (path) {

    var _path = path || 'D:/'

    var wnd = P_LY.WindowContainers.get('windowbox').window

    if (typeof wnd != 'object') {
        return {
            success: false
        }
    }

    if (wnd.containerId == 'windowbox') {
        if (wnd.status.playvideoing) {

            var _name = this.activeWindowName + '监控视频录像' + this.getNowTime() + '.avi'

            var rv = P_LY.LocalRecord(wnd, _path, _name)

            if (wnd.status.recording) {
                console.log('开始录像,保存路径为:' + _path + _name)
                return {
                    success: true,
                    message: '开始录像,保存路径为:' + _path + _name
                }
            } else {
                console.log('停止录像')
                return {
                    success: true,
                    message: '停止录像'
                }
            }

        }
    }
}

/**
 * 播放音频
 * @return {[type]} [description]
 */
VMC_TOOL.playaudio = function () {

    if (P_LY.WindowContainers.get('windowbox')) {

        var wnd = P_LY.WindowContainers.get('windowbox').window

        if (wnd.containerId == 'windowbox') {

            if (wnd.status.playvideoing) {

                var paStop = P_LY.PlayAudio(wnd);

                if (wnd.status.playaudioing) {
                    console.log('播放音频')
                    return {
                        success: true,
                        message: '播放音频'
                    }
                } else {
                    console.log('停止音频')
                    return {
                        success: true,
                        message: '停止音频'
                    }
                }

            }

        }

    }

}

/**
 * 摄像头方向调整
 * @param  {[type]} director [description]
 * @param  {[type]} $btn     [description]
 * @return {[type]}          [description]
 */
VMC_TOOL.ptzcontrol = function (director, $btn) {

    var self = this

    var wnd = P_LY.WindowContainers.get('windowbox').window

    if (typeof wnd != 'object') {
        return false
    }

    var mode = P_LY.Enum.PTZDirection[director]

    if (typeof mode == 'undefined') {
        mode = P_LY.Enum.PTZDirection.stopturn
    }

    var opts = $btn.linkbutton('options');
    if (opts.selected == true) {
        mode = P_LY.Enum.PTZDirection.stopturn;
    }

    if (wnd.containerId == 'windowbox') {
        if (wnd.status.playvideoing) {
            var puid = wnd.params.puid || ''
            var idx = wnd.params.idx || ''
            var mode = mode || ''
            var operator = P_LY.PTZ.Control(self.connectId, puid, idx, mode)
            if (operator.rv != P_Error.SUCCESS) {
                console.log('操作云台失败，错误码：' + operator.rv)
                return {
                    success: false,
                    message: '操作云台失败，错误码：' + operator.rv
                }
            }
        }
    }
}