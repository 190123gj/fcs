define(function (require, exports, module) {

    //require('zyw/publicPage')

    var util = require('util')

    // 可视化图形
    var echarts = require('echarts');

    // 资金数据
    var amountJson = eval(document.getElementById('amountJson').value || '[]'),
        amountJsonEch = {
            title: [],
            data: []
        };

    $.each(amountJson, function (index, obj) {
        amountJsonEch.title.push(obj.name)
        amountJsonEch.data.push({
            value: obj.amount.amount,
            name: obj.name
        })
    });

    // 饼
    var projectInfoChart, projectInfoOption;

    projectInfoChart = echarts.init(document.getElementById('projectInfo'));

    projectInfoOption = {
        title: {
            text: '单位：万元',
            subtext: '资金总额：' + $('input[name=totalAmount]').val() + "万元",
            x: 'right',
            y: 'top'
        },
        tooltip: {
            // trigger: 'item',
            // formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            top: '120px',
            data: amountJsonEch.title
        },
        series: [{
            name: '主要资金构成情况',
            type: 'pie',
            radius: '75%',
            center: ['40%', '50%'],
            data: amountJsonEch.data,
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };

    projectInfoChart.setOption(projectInfoOption);

    projectInfoChart.on('click', function (params) {

        var _url = ''

        switch (params.name) {

            case '可用资金总计':
                _url = '/fundMg/report/accountDetail.htm'
                break
            case '理财产品':
                _url = '/fundMg/report/projectFinancialDetailList.htm'
                break
            case '存出保证金':
                _url = '/fundMg/report/projectDepositPaid.htm'
                break
            case '委托贷款':
                _url = '/fundMg/report/entrustedLoanDetail.htm'
                break

        }

        if (!!_url) {
            util.openDirect('/fundMg/index.htm', _url)
        }

    })

    //柱状

    var xAxisData, flowInto, outflow, balance, i, itemStyle, departmentInfo, departmentInfoOption, data;

    $('.screen a').click(function (event) {

        var $this, dataJson;

        $this = $(this);
        dataJson = eval($this.attr('code'));
        xAxisData = new Array();
        flowInto = new Array();
        outflow = new Array();
        balance = new Array();

        for (i in dataJson) {
            xAxisData.push(dataJson[i].name);
            flowInto.push(parseFloat(dataJson[i].in.standardString.replace(/\,/g, '')));
            outflow.push(-parseFloat(dataJson[i].out.standardString.replace(/\,/g, '')));
            balance.push(parseFloat(dataJson[i].last.standardString.replace(/\,/g, '')));
        }
        // console.log(xAxisData);
        // console.log(flowInto);
        // console.log(outflow);
        // console.log(balance);

        $this.addClass('ui-btn-danger').siblings().removeClass('ui-btn-danger');

        itemStyle = {
            normal: {},
            emphasis: {
                barBorderWidth: 1,
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowOffsetY: 0,
                shadowColor: 'rgba(0,0,0,0.5)'
            }
        };

        departmentInfo = echarts.init(document.getElementById('departmentInfo'));

        departmentInfoOption = {
            title: {
                text: '单位：万元',
                x: 70,
                y: 'bottom'
            },
            backgroundColor: '#fff',
            legend: {
                data: ['流入资金', '流出资金', '资金余额'],
                align: 'left',
                left: 200,
                bottom: -3
            },
            tooltip: {},
            xAxis: {
                data: xAxisData,
                // name: 'X Axis',
                silent: false,
                axisLine: {
                    onZero: true
                },
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                }
            },
            yAxis: {
                inverse: false,
                splitArea: {
                    show: false
                }
            },
            grid: {
                left: 150
            },
            series: [{
                name: '流入资金',
                type: 'bar',
                stack: 'one',
                itemStyle: itemStyle,
                data: flowInto
            }, {
                name: '流出资金',
                type: 'bar',
                stack: 'one',
                itemStyle: itemStyle,
                data: outflow
            }, {
                name: '资金余额',
                type: 'line',
                stack: '资金余额',
                itemStyle: itemStyle,
                data: balance
            }]
        };

        departmentInfo.setOption(departmentInfoOption);

        departmentInfo.on('click', function (params) {
            var fundDirection = '',
                forecastTimeStart = '',
                forecastTimeEnd = '',
                selectType = ''

            if (params.seriesName === '流出资金') {
                fundDirection = 'OUT'
            } else if (params.seriesName === '流入资金') {
                fundDirection = 'IN'
            } else {
                return;
            }

            var _text = $('.screen a.ui-btn-danger').text(),
                _nowTime = new Date(),
                _time = ''

            forecastTimeStart = _nowTime.getFullYear() + '-' + (_nowTime.getMonth() + 1) + '-' + _nowTime.getDate()

            switch (_text.replace(/\d/g, '')) {
                case '天':
                    selectType = 'day'
                    _time = _nowTime.getTime() + (7 - 1) * 24 * 3600 * 1000;
                    forecastTimeEnd = new Date(_time).toLocaleString().substring(0, 10).replace(/\//g, '-')
                    break
                case '周':
                    selectType = 'week'
                    _time = _nowTime.getTime() + (28 - 1) * 24 * 3600 * 1000;
                    forecastTimeEnd = new Date(_time).toLocaleString().substring(0, 10).replace(/\//g, '-')
                    break
                case '个月':
                    selectType = 'month'
                    var __time = new Date(_nowTime.getTime() + 6 * 30 * 24 * 3600 * 1000)
                    forecastTimeEnd = __time.getFullYear() + '-' + (__time.getMonth() + 1) + '-' + _nowTime.getDate()
                    break
                case '季度':
                    selectType = 'quarter'
                    _time = _nowTime.getTime() + 365 * 24 * 3600 * 1000
                    forecastTimeEnd = new Date(_time).toLocaleString().substring(0, 10).replace(/\//g, '-')
                    break
            }

            var _query = [
                'fundDirection=' + fundDirection,
                'selectType=' + selectType,
                'forecastTimeStart=' + forecastTimeStart,
                'forecastTimeEnd=' + forecastTimeEnd
            ]

            util.openDirect('/fundMg/index.htm', '/fundMg/forecast/list.htm?' + _query.join('&'), '/fundMg/forecast/list.htm')

            // console.log(params)
        })

    });

    $('.ui-btn-danger').trigger('click');
})