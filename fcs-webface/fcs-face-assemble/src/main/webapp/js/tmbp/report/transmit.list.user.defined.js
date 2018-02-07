define(function (require, exports, module) {

	// 数据分析 > 数据报送 > 列表、提交
	// require('zyw/publicPage');
	require('tmbp/chooseDateNew');

    var util = require('util');
	var mergeTable = require('zyw/mergeTable');
	var newMergeTable = new mergeTable();
    var getList = require('zyw/getList');


    var loading= new util.loading();
	var $body = $('body');
	var $form = $('#form');
	var $tabItems = $('#tabItems');
	if ($('.root1').length > 0) {
		newMergeTable.init({

			obj: '.root1',

			valType: false,

			// mergeCallback: function(Dom //Dom为每次遍历的合并对象和被合并对象) {//每次遍历合并会调用的callback

			// },
			callback: function () {
				// $('.hiddenTable').css('visibility', 'visible');
				$('.hiddenTable').removeClass('hiddenTable')
			}

		});
	}

    var SEARCH_LIST = new getList();
    function serchListDailog(_para,_tpl) {
        var _listHtml = !!_tpl ?  _tpl : {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.projectCode}}">{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 12)?item.customerName.substr(0,12)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}" class="projectName">{{(item.projectName && item.projectName.length > 12)?item.projectName.substr(0,12)+\'\.\.\':item.projectName}}</td>',
                '        <td><a class="choose" projectname="{{item.projectName}}" customername="{{item.customerName}}" projectcode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: '',
            thead: ['<th class="projectCode">项目编号</th>',
                '<th>客户名称</th>',
                '<th class="projectName">项目名称</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 4
        };

        SEARCH_LIST.init({
            title: '查询结果',
            ajaxUrl: '/baseDataLoad/plProjectList.json' + _para,
            btn: '#fnListSearchHiddenBtn',
            tpl: _listHtml,
            renderCallBack:function (res,self) {
                self.$box.find('.choose').attr('pagename',$('.tab-search.active').attr('page'));
            },
            callback: function ($a) {
                var page = $a.attr('pagename');
                var paraData = {
                    page: page
                };
                if(page === 'customerBasicInfo') {
                    paraData.customerId = $a.attr('customerid');
                }else {
                    paraData.projectCode = $a.attr('projectcode');
                };

                $.ajax({
                    url:'/baseDataLoad/loadPageData.json',
                    type:"post",
                    data:paraData,
                    success:function(res){
                        if(res.success){
                            $('#' + $('#tabItems').find('.item.active').attr('ftf')).html(!$(res.data).html() ? $(res.data) : $(res.data).html());
                        }
                    }
                });
            }
        });

    }

	$tabItems.on('click', 'li', function () { //切换查看
		var $this = $(this);
		var ftf = $this.attr('ftf');
		var $mod = $('.tabCh').find('#' + ftf);
		var $search = $('.tab-mod-head').find('.search-' + ftf);

		$this.addClass('active').siblings('li').removeClass('active');
		$search.addClass('active').siblings('.tab-search').removeClass('active');
		$mod.addClass('active').siblings('.tab-mod').removeClass('active');
	});

	$body.on('click', '.toSaveAll', function () {
		chooseWindBox();
	}).on('click','.sureSaveAll',function () {
        loading.open('<div class="m-modal"><div class="m-ajax-loading"></div><p class="m-ajax-loading-text">批量保存正在进行中，请勿刷新页面...</p></div>');
        var $box = $(this).parents('.choose-time-box');
        var reportYear = $box.find('#reportYear').val();
        var reportMonth = $box.find('#reportMonth').val();
        // ##根据批量保存选定的年月查询满足条件的项目
        var url1='/baseDataLoad/plProjectList.json';
        var url2='/baseDataLoad/loadPageData.json';
        var url3='/reportMg/report/save.json';
        var errListt = [];
        var $ftf = $('#tabItems li')
        var $page = $('#fnReportTableBar .tab-search');
        var allToTalLength = 0 ;
        function getPageList(index){
            var ftf = $ftf.eq(index).attr('ftf');
            var page = $page.eq(index).attr('page');
            var pageTitle = $page.eq(index).html();
            // console.log(index)
            // console.log(ftf)
            // console.log(page)
            // console.log($ftf.eq(index).length == 0 || !page)
            var reportName = '';
            if($ftf.eq(index).length == 0 || !page) {
                var msg = '总共提交' + allToTalLength.toString() + '条数据，其中：成功' + (allToTalLength - errListt.length) + '条数据，失败' + errListt.length + '条数据';
                (errListt.length > 0) && (msg = msg + '出错的数据为：<br>' + errListt);
                loading.close();
                Y.alert('提示',msg,function () {
                    window.location.reload(true);
                });
                return;
            }
            $.ajax({
                url:url1,
                type:"post",
                data:{page:page},
                success:function(pageListRes){
                    console.log(pageListRes);
                    if(pageListRes.success){
                        // console.log(pageListRes.success)

                        pageListSubmit(0);
                        function pageListSubmit(pageListIndex){
                            var data2 = pageListRes.list[pageListIndex];
                            // console.log(projectName)
                            // console.log(pageListRes.list[pageListIndex].projectName)
                            // console.log(pageListRes.list[pageListIndex].customerName)
                            if(!data2 || pageListRes.list.length == 0 || pageListRes.list.length == pageListIndex) {
                                index++
                                getPageList(index);
                                return;
                            }
                            data2.reportYear = reportYear;
                            data2.reportMonth = reportMonth;
                            data2.page = page;
                            data2.type = 'pl';

                            reportName = 'J-' + pageTitle + '-' + (!data2.projectName ? data2.customerName : data2.projectName);
                            // console.log('projectName：' + reportName)
                            // console.log(data2)
                            $.ajax({//每个页签更新数据
                                url: url2,
                                type: 'post',
                                data: data2,
                                success:function (res2) {
                                    // console.log(res2)
                                    if(res2.success){
                                        var $contentHtml = $('.m-main').clone(true);
                                        // var $condition = $contentHtml.find('#condition');
                                        var $contentData = $(res2.data);

                                        // $condition.html('<span class="fn-right rightBtn"><i class="icon-print fn-ml20" id="fnDoPrint">打印</i></span>');
                                        $contentHtml.find('#tabItems').remove();
                                        $contentData.addClass('active').removeClass('hiddenTable')
                                        // if($contentData.hasClass('tab-mod')) $contentData = $('<div class="fn-ml15 fn-mr15 fn-mt20 fn-mb20 tab-mod active" >' + $contentData.html() + '</div>');
                                        // $contentHtml.find('#' + ftf).html(!$(res2.data).html() ? $(res2.data) : $(res2.data).html());
                                        $contentHtml.find('.tabCh').html($contentData);
                                        $contentHtml.find('input,select').attr('disabled','disabled');
                                        // console.log($contentHtml.find('#title').val());
                                        $contentHtml.find('#fnReportTableBar').html('<p class="fn-tac fn-fwb fn-fs24 fn-f0 fn-mt20">' + reportName + '</p>');
                                        // $contentHtml.find('#fnReportTableBar').html('<span class="fn-right rightBtn" style="font-size: 14px;"><i class="icon-print fn-ml20" id="fnDoPrint">打印</i></span><p class="fn-tac fn-fwb fn-fs24 fn-f0 fn-mt20">' + reportName + '</p>');

                                        //总是执行下一条数据
                                        pageListIndex++;
                                        subimtAll(pageListIndex,$contentHtml.html());
                                    }else {//如果某次查询失败，则整条数据不提交，并标记为失败
                                        (errListt.indexOf(reportName) == -1) && errListt.push(reportName);

                                        //总是执行下一条数据
                                        pageListIndex++;
                                        subimtAll(pageListIndex);
                                    }

                                },
                                error: function () {//如果某次查询失败，则整条数据不提交，并标记为失败
                                    (errListt.indexOf(reportName) == -1) && errListt.push(reportName)
                                },
                                complete: function () {
                                }

                            });
                        };

                        //提交一条完整的数据
                        function subimtAll(d,content) {

                            // console.log(projectName)
                            if(!content) {
                                pageListSubmit(d);
                                return;
                            }
                            var data3 = {
                                reportYear: reportYear,
                                reportMonth: reportMonth,
                                reportName: reportName,
                                // reportCode: $('[name=reportCode]').val(),
                                reportCode: 'BASE_REPORT',
                                content: '<div class="m-main border ui-bg-fff">' + content + '</div>'
                            };
                            $.ajax({
                                url:url3,
                                type:'post',
                                data:data3,
                                success: function (res3) {
                                    // console.log(res3)
                                    //提交失败，标记提交失败
                                    (!res3.success && errListt.indexOf(reportName) == -1) && errListt.push(reportName);

                                },
                                error: function () {
                                    //提交失败，标记提交失败
                                    (errListt.indexOf(reportName) == -1) && errListt.push(reportName)
                                },
                                complete: function () {
                                    //总是执行下一条数据
                                    // listIndex++;
                                    pageListSubmit(d);
                                    allToTalLength++;
                                }
                            })
                        };
                    }else {
                        // loading.close();
                        // Y.alert('提示',pageListRes.message || '保存出错');
                    }
                },
                // complete: function () {
                //     //总是执行下一条数据
                //     index++;
                //     getPageList(index);
                // }
            });
        };
        getPageList(0);
        // return;
        // $.ajax({
        //     url:url1,
        //     type:"post",
        //     data:data1,
        //     success:function(res1){
        //         console.log(res1);
        //         var errListt = [];
        //         if(res1.success){
        //
        //             // res1.total总条数
        //             // 依次触发每个标签生成带数据页面，并替换
        //             // 调用报表提交接口保存
        //
        //             var list = res1.list;
        //             if(res1.total > 0) ajaxList(0);//条数大于0，进行循环
        //
        //             //循环每条数据，拼接提交
        //             function ajaxList(listIndex) {
        //
        //                 if(res1.total == listIndex) {//超过总条数就终止
        //                     // console.log('res1：' + res1.total);
        //                     // console.log('errListt：' + errListt.length);
        //                     //
        //                     var msg = '总共提交' + res1.total.toString() + '条数据，其中：成功' + (res1.total - errListt.length) + '条数据，失败' + errListt.length + '条数据';
        //                     (errListt.length > 0) && (msg = msg + '出错的数据项目编号为：' + errListt);
        //                     loading.close();
        //                     Y.alert('提示',msg,function () {
        //                         window.location.reload(true);
        //                     });
        //                     return;
        //                 };
        //
        //                 var projectCode = list[listIndex].projectCode;
        //                 var customerId = list[listIndex].customerId;
        //                 var projectName = list[listIndex].projectName;
        //                 var $contentHtml = $('.m-main').clone(true);
        //                 var $condition = $contentHtml.find('#condition');
        //
        //                 $condition.html('<span class="fn-right rightBtn"><i class="icon-print fn-ml20" id="fnDoPrint">打印</i></span>');
        //                 $contentHtml.find('#tabItems li').eq(0).click();//切换到第一个页签
        //
        //                 updatePgae(0);
        //                 //更新每个页签
        //                 function updatePgae(i) {
        //                     var $this = $('#tabItems li').eq(i);
        //                     var ftf = $this.attr('ftf');
        //                     if($this.length == 0 || typeof ftf == 'undefined') {
        //                         (errListt.indexOf(projectCode) == -1 ) && subimtAll();
        //                         return;
        //                     };
        //                     var $fnReportTableBar = $('#fnReportTableBar');
        //                     var page = $fnReportTableBar.find('p.search-' + ftf).attr('page');
        //                     var data2 = {
        //                         reportYear: reportYear,
        //                         reportMonth: reportMonth,
        //                         page: page,
        //                         type: 'pl',
        //                         customerId: customerId,
        //                         projectCode: projectCode
        //                     };
        //
        //                     $.ajax({//每个页签更新数据
        //                         url: url2,
        //                         type: 'post',
        //                         data: data2,
        //                         success:function (res2) {
        //                             // console.log(res2)
        //                             if(res2.success){
        //                                 $contentHtml.find('#' + ftf).html(!$(res2.data).html() ? $(res2.data) : $(res2.data).html());
        //                                 i++;
        //                                 updatePgae(i);
        //                             }else {//如果某次查询失败，则整条数据不提交，并标记为失败
        //                                 (errListt.indexOf(projectCode) == -1) && errListt.push(projectCode);
        //                             }
        //
        //                         },
        //                         error: function () {//如果某次查询失败，则整条数据不提交，并标记为失败
        //                             (errListt.indexOf(projectCode) == -1) && errListt.push(projectCode)
        //                         },
        //
        //                     })
        //                 };
        //                 //提交一条完整的数据
        //                 function subimtAll() {
        //                     var data3 = {
        //                         reportYear: reportYear,
        //                         reportMonth: reportMonth,
        //                         reportName: '基层定期报表-' + projectName,
        //                         // reportCode: $('[name=reportCode]').val(),
        //                         reportCode: 'BASE_REPORT',
        //                         content: '<div class="m-main border ui-bg-fff">' + $contentHtml.html() + '</div>'
        //                     };
        //                     $.ajax({
        //                         url:url3,
        //                         type:'post',
        //                         data:data3,
        //                         success: function (res3) {
        //                             // console.log(res3)
        //                             //提交失败，标记提交失败
        //                             (!res3.success && errListt.indexOf(projectCode) == -1) && errListt.push(projectCode);
        //                         },
        //                         error: function () {
        //                             //提交失败，标记提交失败
        //                             (errListt.indexOf(projectCode) == -1) && errListt.push(projectCode)
        //                         },
        //                         complete: function () {
        //                             //总是执行下一条数据
        //                             listIndex++;
        //                             ajaxList(listIndex);
        //                         }
        //                     })
        //                 };
        //             };
        //         }else {
        //             loading.close();
        //             Y.alert('提示',res1.message || '保存出错');
        //         }
        //     }
        // });
    }).on('click','#fnListSearchBtn',function(){//搜索

        var page=$('#fnReportTableBar').find('.active').attr('page');
        var paramStr = '?page=' + page;
        var isCanSearch = false;
        $.each($('#condition').find('.active input[name]'),function (i,el) {
            if(!!el.value){
                paramStr += '&' + $(el).attr('name') + '=' + el.value;
                isCanSearch = true;
            };
        });
        // console.log(isCanSearch)
        if(!isCanSearch) {
            Y.alert('提示','搜索条件不能为空！');
            return;
        };
        if(page === 'customerBasicInfo') {
            var _newTpl = {
                tbody: ['{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td title="{{item.customerId}}">{{item.customerId}}</td>',
                    '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 12)?item.customerName.substr(0,12)+\'\.\.\':item.customerName}}</td>',
                    '        <td><a class="choose" customerid="{{item.customerId}}" customername="{{item.customerName}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: '',
                thead: ['<th>客户ID</th>',
                    '<th>客户名称</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 3
            };
            serchListDailog(paramStr,_newTpl);
        }else {
            serchListDailog(paramStr);
        };
        $('#fnListSearchHiddenBtn').click();

    }).on('click','#fnDoPrint',function () {
        var $fnReportTableBar = $('#fnReportTableBar').clone(true);
        var $tabCh = $('.tabCh').clone(true);

        $(this).remove();
        $fnReportTableBar.find('#condition').remove();
        $tabCh.find('.tab-mod').not('.active').remove();
        var str1 = '<div class="report-table-bar fn-clear tab-mod-head" id="fnReportTableBar">' + $fnReportTableBar.html() + '</div>';
        var str2 = '<div class="tabCh">' + $tabCh.html() + '</div>';

        util.print(str1 + str2);

    })
	function chooseWindBox(arry, isCanChoose) { //isCanChoose判断是否能够选择

		var currentTime = new Date();
		var year = !!!arry ? currentTime.getFullYear() : arry[0];
		var month = !!!arry ? (currentTime.getMonth() + 1) : arry[1];
		var eventClassName = (isCanChoose == false) ? '' : 'chooseYM';
		month = parseInt(month) < 10 ? '0' + parseInt(month) : month;
		$body.Y('Window', {
			content: '<div class="m-modal choose-time-box">' +
				'    <div class="view-files-hd">' +
				'        <span class="fn-right fn-usn fn-csp closeBtn" href="javascript:void(0);">×</span>' +
				'        <span class="view-files-ttl">批量保存</span>' +
				'    </div>' +
				'    <div class="view-files-body">' +
				'       <div class="item chooseBox">查询截止月：' +
				'           <input class="ui-text fn-w90 ' + eventClassName + '" type="text" value="' + year + '-' + month + '"  name="reportTime" placeholder="选择年月" readOnly="true">' +
				'           <input class="ui-text fn-w90 chooseYear" type="hidden" id="reportYear" name="year" value="' + year + '" placeholder="选择年" readOnly="true"><label class="fenge" hidden>-</label>' +
				'           <input class="ui-text fn-w90 chooseMonth" type="hidden" id="reportMonth" name="month" value="' + month + '" placeholder="选择月" readOnly="true">' +
				'       </div>' +
				'       <div class="btns fn-mt40 fn-tac">' +
				'           <input type=button class="ui-btn ui-btn-fill ui-btn-green fn-mr30 sureBtn sureSaveAll" value="确定保存">' +
				'           <input type=button class="ui-btn ui-btn-fill ui-btn-gray closeBtn" value="取消">' +
				'       </div>' +
				'    </div>' +
				'</div>',
			modal: true,
			key: 'modalwnd',
			simple: true,
			closeEle: '.closeBtn'
		});
	}
});