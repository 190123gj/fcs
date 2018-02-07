define(function (require, exports, module) {
    // 我的工作台
    require('zyw/init');

    require('./workflowTemplate');

    require('Y-msg')

    var util = require('util');

    //var publicOPN = new(require('zyw/publicOPN'))();

    var $win = $('.m-main-page'),
        winH = $win.height(),
        $nFooterGoBack = $('#fnFooterGoBack');

    $nFooterGoBack.on('click', function () {
        $('.m-main-page').animate({
            scrollTop: 0
        }, 600);

    });

    $win.scroll(function () {
        console.log(5)

        if ($win.scrollTop() > (winH / 3 * 2)) {

            if ($nFooterGoBack.hasClass('fn-hide')) {
                $nFooterGoBack.removeClass('fn-hide').show(600);
            }

        } else {

            if (!$nFooterGoBack.hasClass('fn-hide')) {
                $nFooterGoBack.addClass('fn-hide').hide(600);
            }

        }

    });


    // ------ DOM 分页 start

    function DomPage($table) {
        this.$table = $table
    }

    DomPage.prototype = {
        constructor: DomPage,
        init: function (fn) {

            var self = this

            // 第几页的索引
            self.$page = $('<div></div>').addClass('m-pages')

            self.$table.after(self.$page)

            var $tr = self.$table.find('tbody tr'),
                pageSize = 10

            self.data = {
                totalCount: $tr.length,
                pageCount: Math.ceil($tr.length / 10)
            }

            showPage($tr, 1, pageSize)

            self.$page.html(getDomPages(self.data, 1))
                .on('click', 'a[page]', function () {

                    var index = +this.getAttribute('page')

                    // 切换前的同步操作
                    self.callback && self.callback()

                    self.$table.find('.checkbox').each(function (index, el) {
                        this.checked = false
                    });
                    showPage($tr, index, pageSize)
                    self.$page.html(getDomPages(self.data, index))

                })

            if (fn) {
                fn(self)
            }

            return self

        },
        addSwitchBefore: function (fn) {
            this.switchBefore = fn
            return this
        }
    }

    function getDomPages(data, index) {

        // 通过a标签上的page确定请求第几页数据
        var _txt = '第' + index + '页，共' + data.pageCount + '页，合计' + data.totalCount + '条&nbsp;|&nbsp;',
            _firstBtn, _preBtn, _nextBtn, _lastBtn

        // 第一页、上一页
        if (index == 1) {
            _firstBtn = '<a class="disabled" href="javascript:void(0);">首页</a>'
            _preBtn = '<a class="disabled" href="javascript:void(0);">上一页</a>'
        } else {
            _firstBtn = '<a page="1" href="javascript:void(0);">首页</a>'
            _preBtn = '<a page="' + (index - 1) + '" href="javascript:void(0);">上一页</a>'
        }

        // 最后页、下一页
        if (index >= data.pageCount) {
            _nextBtn = '<a class="disabled" href="javascript:void(0);">下一页</a>'
            _lastBtn = '<a class="disabled" href="javascript:void(0);">尾页</a>'
        } else {
            _nextBtn = '<a page="' + (index + 1) + '" href="javascript:void(0);">下一页</a>'
            _lastBtn = '<a page="' + data.pageCount + '" href="javascript:void(0);">尾页</a>'
        }

        return _txt + _firstBtn + _preBtn + _nextBtn + _lastBtn;

    }

    function showPage($tr, index, pageSize) {

        var start = (index - 1) * pageSize
        var end = index * pageSize

        $tr.each(function (index, el) {
            if (index >= start && index < end) {
                el.className = ''
            } else {
                el.className = 'fn-hide'
            }
        })

    }

    // ------ DOM 分页 end

    // ------ 项目情况 start
    function getId(id) {
        return document.getElementById(id);
    }

    $('.fnIsMyProject').on('click', function () {
        if (this.value == 'IS') {
            getId('isMyProjectIS').className = '';
            getId('isMyProjectNO').className = 'fn-hide';
        } else {
            getId('isMyProjectIS').className = 'fn-hide';
            getId('isMyProjectNO').className = '';
        }
    });
    // ------ 项目情况 end

    // 异步加载会议列表
    $("#councilList").load("/userHome/deskCouncilList.htm");


    // 异步加载任务
    var $ansycTask = $("#ansycTask"),
        doneLoadTask = false;
    
    // 无菊花版本
    $ansycTask.load('/userHome/ansycTask.htm',function(){
        (new DomPage($ansycTask.find('#fnBusiTask'))).init(function (self) {
            if (self.data.totalCount > 0) {
                $('li[container="busiTask"]').append('<span class="dot">' + self.data.totalCount + '</span>')
            }
        });
        (new DomPage($ansycTask.find('#fnFundTask'))).init(function (self) {
            if (self.data.totalCount > 0) {
                $('li[container="fundTask"]').append('<span class="dot">' + self.data.totalCount + '</span>')
            }
        });   	
    })
// 有菊花版本
// util.ajax({
// url: '/userHome/ansycTask.htm',
// dataType: "html",
// success: function (res) {
// $ansycTask.html(res);
// (new DomPage($ansycTask.find('#fnBusiTask'))).init(function (self) {
// if (self.data.totalCount > 0) {
// $('li[container="busiTask"]').append('<span class="dot">' +
// self.data.totalCount + '</span>')
// }
// });
// (new DomPage($ansycTask.find('#fnFundTask'))).init(function (self) {
// if (self.data.totalCount > 0) {
// $('li[container="fundTask"]').append('<span class="dot">' +
// self.data.totalCount + '</span>')
// }
// });
// }
// });
    
    // 异步加载被驳回任务
    $("#backTask").load("/userHome/backTaskList.htm", function () {
        (new DomPage($('#fnBackTask'))).init(function (self) {
            if (self.data.totalCount > 0) {
                $('li[container="backTask"]').append('<span class="dot">' + self.data.totalCount + '</span>')
            }
        });
    });
    // 异步加载任务分类
    $("#taskGroup").load("/userHome/taskGroup.htm", function () {
        (new DomPage($('#fnFaskGroup'))).init()
    });
    // 异步加载已办任务
    $("#doneTask").load("/userHome/doneTaskList.htm");

    // ------切换任务
    $('#stay2already').on('click', '.list-title li', function () {
        var $this = $(this),
            container = $this.attr('container');
        $this.addClass('active').siblings().removeClass('active');
        $('.task[id!=' + container + ']').hide();
        // console.log($('.task[id!='+container+']'));
        // console.log($('#'+container));
        $('#' + container).show();
    });

    $("body").on("click", "#backGroup", function () {
        $("#taskGroup").show();
        $("#taskGroupList").html("").hide();
    }).on('click', '.fnAllCheck', function () {
        var _checked = this.checked
        $(this).parents('table').find('.fnCheckItem:not([disabled]):visible').each(function (index, el) {
            this.checked = _checked
        })
    }).on('click', '.fnCheckItem', function () {
        var $all = $(this).parents('table').find('.fnAllCheck')
        if (!this.checked && $all.prop('checked')) {
            $all.removeProp('checked')
        }
    }).on('click', '.fnDoBatch', function () {

        var $items = $(this).parent().next('table').find('.fnCheckItem:checked')
        if (!!!$items.length) {
            Y.alert('提示', '请选择数据后再操作')
            return
        }
        var _inputs = '',
            _titles = {}

        $items.each(function (index, el) {

            var $td = $(el).parent()

            $td.find('input').each(function (index, el) {
                _inputs += '<input type="hidden" name="' + el.name + '" value="' + el.value + '">'

            })

            _titles[el.value] = $td.next().text().replace(/\s/g, '')
        })

        _inputs += '<input type="hidden" name="processType" value="' + this.getAttribute('state') + '">'

        var $form = $('<form></form>').html(_inputs)

        util.ajax({
            url: '/projectMg/form/batchProcessTask.json',
            data: $form.serialize(),
            success: function (res) {

                var _head = '<thead><tr><th>任务名称</th><th>审核状态</th><th>备注</th></tr></thead>',
                    _tbody = '',
                    _result = res.result

                $.each(_result.failureFormIdList, function (index, str) {

                    _tbody += '<tr><td>' + _titles[str] + '</td><td>失败</td><td>' + _result.failureMessageList[index] + '</td></tr>'

                })

                $.each(_result.nonSupportFormIdList, function (index, str) {

                    _tbody += '<tr><td>' + _titles[str] + '</td><td>失败</td><td>该单据不支持批量操作</td></tr>'

                })

                $.each(_result.successFormIdList, function (index, str) {

                    _tbody += '<tr><td>' + _titles[str] + '</td><td>成功</td><td>' + _result.successMessageList[index] + '</td></tr>'

                })

                var _html = '<div style="height: auto; max-height: 400px; overflow-y: auto;"><table class="m-table m-table-list">' + _head + '<tbody>' + _tbody + '</tbody></table></div>'

                $('body').Y('Msg', {
                    type: 'alert',
                    width: '750px',
                    content: _html,
                    icon: '',
                    yesText: '确定',
                    callback: function (opn) {

                        window.location.reload(true)

                    }
                });


            }
        })

    })

    // 可视化图形
    var echarts = require('echarts');
    /**
	 * 转化为一个 echarts 饼图 需要的数据结构
	 * 
	 * @param {[type]}
	 *            arr [服务器给出的数组]
	 * @param {[type]}
	 *            name [标示标题的key]
	 * @param {[type]}
	 *            value [表述数据的key]
	 * @return {[type]} [title 用于legend，series用于series]
	 */
    function getChartDate(arr, name, value, keyname) {

        var _o = {
            title: [],
            series: []
        };

        $.each(arr, function (index, el) {

            _o.title.push(el[name]);
            _o.series.push({
                name: el[name],
                value: el[value],
                jumpname: el[keyname]
            });

        });

        return _o;

    }

    // ------ 项目分布 start

    if (document.getElementById('projectInfo')) {

        var projectInfoChart = echarts.init(document.getElementById('projectInfo')),
            projectInfoVal = getChartDate(eval(document.getElementById('fnCountJson').value), 'phasesMessage', 'count', 'phases');

        // 指定图表的配置项和数据
        var projectInfoOption = {
            title: {
                text: '项目分布图',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                left: 'left',
                data: projectInfoVal.title
            },
            series: [{
                name: '项目分布',
                type: 'pie',
                radius: '45%',
                center: ['50%', '60%'],
                data: projectInfoVal.series,
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
        projectInfoChart.setOption(projectInfoOption);

        projectInfoChart.on('click', function (params) {
            util.openDirect('/projectMg/index.htm', '/projectMg/list.htm?phases=' + params.data.jumpname)
        })

        // 假若没有数据
        if (!projectInfoVal.title.length) {
            document.getElementById('projectInfo').innerHTML = '项目分布图暂无数据';
        }

    }

    // ------ 项目分布 end


    // ------ 资金使用 start

    if (document.getElementById('moneyInfo')) {

        var moneyInfoChart = echarts.init(document.getElementById('moneyInfo'));

        // 指定图表的配置项和数据
        var moneyInfoOption = {
            title: {
                text: '资金使用情况',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['放用款', '放款', '授信金额']
            },
            series: [{
                name: '资金使用情况',
                type: 'pie',
                radius: '45%',
                center: ['50%', '50%'],
                data: [{
                    value: 10,
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
        moneyInfoChart.setOption(moneyInfoOption);

    }

    // ------ 资金使用 end


    // ------ 客户部门分布 start

    if (document.getElementById('departmentInfo')) {

        var departmentInfoChart = echarts.init(document.getElementById('departmentInfo')),
            departmentTitle = [],
            departmentData = [{
                value: 0,
                name: '暂无'
            }];

        var departmentInfoJson = $("#departmentInfoJson").html();
        if (departmentInfoJson) {
            departmentData = [];
            departmentInfoJson = eval('(' + departmentInfoJson + ')');
            for (key in departmentInfoJson) {
                departmentTitle.push(key)
                departmentData.push({
                    name: key,
                    value: departmentInfoJson[key]
                });
            }
        }

        // 指定图表的配置项和数据
        var departmentInfoOption = {
            title: {
                text: '客户部门分布',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                left: 'top',
                data: departmentTitle
            },
            series: [{
                name: '客户部门分布',
                type: 'pie',
                radius: '45%',
                center: ['50%', '60%'],
                data: departmentData,
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
        departmentInfoChart.setOption(departmentInfoOption);

        departmentInfoChart.on('click', function (params) {

            var _name = params.name

            $.get('/baseDataLoad/orgId.json?depName=' + _name)
                .then(function (res) {
                    if (res.success) {

                        util.openDirect('/customerMg/index.htm', '/customerMg/companyCustomer/list.htm?orgId=' + res.data)
                        util.openDirect('/customerMg/index.htm', '/customerMg/personalCustomer/list.htm?orgId=' + res.data)

                    }
                })

        })

    }

    // ------ 客户部门分布 end

    // ------ 地域分布 start

    if (document.getElementById('regionalInfo')) {

        var regionalInfoChart = echarts.init(document.getElementById('regionalInfo')),
            indicator = [{
                max: 100,
                name: '暂无'
            }],
            dataValue = [0];

        var regionalInfoJson = $("#regionalInfoJson").html();
        var max = 0;
        if (regionalInfoJson) {
            indicator = new Array();
            dataValue = new Array();
            regionalInfoJson = eval('(' + regionalInfoJson + ')');
            for (key in regionalInfoJson) {
                if (regionalInfoJson[key] > max) {
                    max = regionalInfoJson[key];
                }
            }
            for (key in regionalInfoJson) {
                indicator.push({
                    text: key,
                    max: max
                });
                dataValue.push(regionalInfoJson[key]);
            }
        }
        // console.log(indicator);
        // console.log(dataValue);

        // 指定图表的配置项和数据
        var regionalInfoOption = {
            title: {
                text: '客户地域分布图',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'axis'
            },
            radar: [{
                indicator: indicator,
                center: ['50%', '60%'],
                radius: '40%'
            }],
            series: [{
                type: 'radar',
                tooltip: {
                    trigger: 'item'
                },
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: 'default'
                        }
                    }
                },
                data: [{
                    value: dataValue,
                    name: '客户地域分布'
                }]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        regionalInfoChart.setOption(regionalInfoOption);

    }

    // ------ 地域分布 end


    // ------ 获取用户名，点击后自动登陆BI,数据决策

    (function () {

        var username = $("#_username").val()
        var password = 'MX15310329351mx'
        var fineReportUrl = document.getElementById("fineReportUrl").value;
        var fineBIUrl = document.getElementById("fineBIUrl").value;


        $('#fn_login1, #fn_login2').each(function () {
            var _this = $(this)
            _this.attr('data-href',_this.attr('href'))
            _this.attr('href','javascript:;')
        })

        // 数据决策系统单点登录
        $('#fn_login1').click(function () {
            var _this = $(this)
            var scr = document.createElement("iframe");      //创建iframe
            scr.src = fineReportUrl+"/ReportServer?op=fs_load&cmd=sso&fr_username=" + username + "&fr_password=" + password;   //将报表验证用户名密码的地址指向此iframe
            if (scr.attachEvent){       //判断是否为ie浏览器
                scr.attachEvent("onload", function(){                    //如果为ie浏览器则页面加载完成后立即执行
                    /*跳转到指定登录成功页面，index.jsp
                     var f = document.getElementById("login");
                     f.submit();  */
                    window.open(fineReportUrl + "/ReportServer?op=fs"); //直接跳转到数据决策系统
                });
            } else {
                scr.onload = function(){              //其他浏览器则重新加载onload事件
                    /*跳转到指定登录成功页面,index.jsp
                     var f = document.getElementById("login");
                     f.submit(); */
                    // window.location="/ReportServer?op=fs"; //直接跳转到数据决策系统
                    window.open(fineReportUrl + "/ReportServer?op=fs")
                };
            }
            document.getElementsByTagName("head")[0].appendChild(scr);   //将iframe标签嵌入到head中
        })


        //BI系统单点登录
        $('#fn_login2').click(function () {
            var scrBi = document.createElement("iframe");
            scrBi.src = fineBIUrl+"/ReportServer?op=fs_load&cmd=sso&fr_username=" + username + "&fr_password=" + password;   //将报表验证用户名密码的地址指向此iframe
            if (scrBi.attachEvent){       //判断是否为ie浏览器
                scrBi.attachEvent("onload", function(){
                    //如果为ie浏览器则页面加载完成后立即执行
                    window.open(fineBIUrl + "/ReportServer?op=fs")
                });
            } else {
                scrBi.onload = function(){
                    //其他浏览器则重新加载onload事件
                    window.open(fineBIUrl + "/ReportServer?op=fs")
                };
            }
            document.getElementsByTagName("head")[0].appendChild(scrBi);   //将iframe标签嵌入到head中
            jQuery('.login-form').submit();
        })
    })()



});