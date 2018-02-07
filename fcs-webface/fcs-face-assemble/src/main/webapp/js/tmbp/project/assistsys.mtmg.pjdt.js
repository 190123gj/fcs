define(function (require, exports, module) {
    // 项目管理 > 会议管理 > 查看项目详情

    require('zyw/upAttachModify')

    var util = require('util'),
        echarts = require('echarts');

    // var riskQuery = require('zyw/riskQuery');
    // var initRiskQuery = new riskQuery.initRiskQuery(false, 'fnRiskQueryName', 'fnRiskQueryCertNo', 'fnRiskQueryType', 'fnRiskQueryOrgCode', 'fnRiskQueryOneCert', 'fnRiskQueryLicenseNo');

    var $window = $(window),
        $fnNav = $('#fnNav');

    $window.on('scroll', function () {
        // 导航栏跟随
        if ($window.scrollTop() > 100) {
            $fnNav.addClass('fixed')
        } else {
            $fnNav.removeClass('fixed')
        }
    })
    $('a:not(.fnInternal)').on('click', function (e) {
        // 链接新窗口打开
        e.preventDefault();
        // var _mainUrl = this.getAttribute('mainurl') || '/projectMg/index.htm';
        // util.openDirect(_mainUrl, this.href)
        window.open(this.href)

    });

    function getECHData(arr, title, value) {

        var _title = [],
            _data = [];

        $.each(arr, function (i, obj) {

            _title.push(obj[title]);
            _data.push({
                name: obj[title],
                value: obj[value]
            });

        });

        return {
            title: _title,
            data: _data
        }

    }

    // 画图
    if (document.getElementById('derogatoryInfo')) {

        // 查询风险信息
        var _isP = (document.getElementById('fnRiskQueryType').value === 'ENTERPRISE') ? false : true,
            _getXX = _isP ? false : true, // 个人无相似、信息
            _getSX = _isP ? ((document.getElementById('fnRiskQueryCertType').value === 'IDENTITY_CARD') ? true : false) : true; // 个人 身份证 失信

        // if (!$_GLOBAL.isApp) {

        //     initRiskQuery.getAllInfo(_getXX, _getSX, _getXX, function (html) {
        //         document.getElementById('derogatoryInfo').innerHTML = html
        //     })

        // }

        //------ 该客户负面消息 start
        // var derogatoryInfoEcharts = echarts.init(document.getElementById('derogatoryInfo'));
        // derogatoryInfoEcharts.showLoading();

        // // 指定图表的配置项和数据
        // var derogatoryInfoOption = {
        //  tooltip: {
        //      trigger: 'item',
        //      formatter: "{a} <br/>{b} : {c} ({d}%)"
        //  },
        //  title: {
        //      text: '该客户负面消息(条)',
        //      x: 'center'
        //  },
        //  series: [{
        //      name: '关键词',
        //      type: 'pie',
        //      radius: '55%',
        //      center: ['50%', '60%'],
        //      data: [{
        //          value: 0,
        //          name: '倒闭'
        //      }, {
        //          value: 0,
        //          name: '停产'
        //      }, {
        //          value: 0,
        //          name: '离职'
        //      }, {
        //          value: 0,
        //          name: '败诉'
        //      }, {
        //          value: 0,
        //          name: '跑路'
        //      }],
        //      itemStyle: {
        //          emphasis: {
        //              shadowBlur: 10,
        //              shadowOffsetX: 0,
        //              shadowColor: 'rgba(0, 0, 0, 0.5)'
        //          }
        //      }
        //  }]
        // };

        // // 使用刚指定的配置项和数据显示图表。
        // derogatoryInfoEcharts.hideLoading();
        // derogatoryInfoEcharts.setOption(derogatoryInfoOption);

        //------ 该客户负面消息 end
        //
    }

    if (document.getElementById('creditAmount')) {

        //------ 客户授信金额 start
        var creditAmountEcharts = echarts.init(document.getElementById('creditAmount'));
        creditAmountEcharts.showLoading();

        var fnValKHSXJE = getECHData(eval(document.getElementById('fnValKHSXJE').value), 'moneyName', 'money');

        // 指定图表的配置项和数据
        var creditAmountOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            title: {
                text: '客户授信金额',
                x: 'right'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: fnValKHSXJE.title
            },
            series: [{
                name: '详情',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: fnValKHSXJE.data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        creditAmountEcharts.hideLoading();
        creditAmountEcharts.setOption(creditAmountOption);

        //------ 客户授信金额 end
        //
    }

    if (document.getElementById('putInMoneyInfo')) {

        //------ 放用款总体情况 start
        var putInMoneyInfoEcharts = echarts.init(document.getElementById('putInMoneyInfo'));
        putInMoneyInfoEcharts.showLoading();

        // 指定图表的配置项和数据
        var putInMoneyInfoOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            title: {
                text: '放用款总体情况',
                x: 'center'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['放用款', '放款', '授信金额']
            },
            series: [{
                name: '关键词',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [{
                    value: 0,
                    name: '放用款'
                }, {
                    value: 56,
                    name: '放款'
                }, {
                    value: 56,
                    name: '授信金额'
                }],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        putInMoneyInfoEcharts.hideLoading();
        putInMoneyInfoEcharts.setOption(putInMoneyInfoOption);

        //------ 放用款总体情况 end
        //
    }

    if (document.getElementById('costInfo')) {

        //------ 费用及保证金收取情况 start
        var costInfoEcharts = echarts.init(document.getElementById('costInfo'));
        costInfoEcharts.showLoading();

        // 指定图表的配置项和数据
        var costInfoOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            title: {
                text: '费用及保证金收取情况',
                x: 'center'
            },
            legend: {
                orient: 'vertical',
                left: 'right',
                data: ['担保费', '顾问费', '保证金']
            },
            series: [{
                name: '关键词',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [{
                    value: 0,
                    name: '担保费'
                }, {
                    value: 56,
                    name: '顾问费'
                }, {
                    value: 56,
                    name: '保证金',
                    itemStyle: {
                        normal: {
                            // 设置扇形的颜色
                            color: '#ffde4f'
                                // shadowBlur: 200,
                                // shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        costInfoEcharts.hideLoading();
        costInfoEcharts.setOption(costInfoOption);

        //------ 费用及保证金收取情况 end
        //
    }


    // ------ 2016.08.09 添加一个查看流程图
    // 查看审批流程
    var BPMiframe = require('BPMiframe');
    var showWorkFlowOPN = {
            //初始化流程图
            BPMiframe: {
                url: '/bornbpm/platform/bpm/processRun/processImage.do?actInstId=',
                cfg: {
                    'title': '审批流程',
                    'width': $(window).width() - 20,
                    'height': $(window).height() - 20,
                    'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=',
                    'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
                }
            }
        },
        showWorkFlowDialog = new BPMiframe('', {
            'title': '审批流程',
            'width': $(window).width() - 20,
            'height': $(window).height() - 20,
            'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=',
            'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
        });

    $('#fnOpenWorkFlowDialog').on('click', 'li', function () {

        var _flowid = this.getAttribute('flowid'),
            _flowname = this.getAttribute('flowname');

        if (_flowid && _flowname) {

            showWorkFlowDialog.resetSrc('/bornbpm/platform/bpm/processRun/processImage.do?actInstId=' + _flowid);

            showWorkFlowDialog.obj.bpmIsloginUrl = '/bornbpm/bornsso/islogin.do?userName=' + _flowname;

            showWorkFlowDialog.init();

        }

    });

    var $addForm = $('#addForm');
    $('#submit').on('click', function () {
        util.ajax({
            url: $addForm.attr('action'),
            data: $addForm.serialize(),
            success: function (res) {

                if (res.success) {
                    Y.alert('提示', '已提交', function () {
                        util.direct('/projectMg/meetingMg/councilList.htm');
                        // window.location.href = '/projectMg/meetingMg/councilList.htm';
                    });
                } else {
                    Y.alert('提示', res.message);
                    _this.removeClass('ing');
                }

            }
        });
    });

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();

});