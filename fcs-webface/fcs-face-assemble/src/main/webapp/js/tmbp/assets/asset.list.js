/*
 * @Author: erYue
 * @Date:   2016-07-14 14:29:18
 * @Last Modified by:   erYue
 * @Last Modified time: 2016-07-14 14:48:38
 */

define(function(require, exports, module) {
	require('Y-msg');
	require('zyw/publicPage');
	require('validate');
	require('validate.extend');
	require('zyw/upAttachModify');

	var getList = require('zyw/getList'), util = require('util');

	// 选择项目
	var _getList = new getList();
	_getList.init({
				title : '项目列表',
				ajaxUrl : '/baseDataLoad/queryProjects.json?phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,RE_COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES',
				btn : '.chooseBtn',
				tpl : {
					tbody : [
							'{{each pageList as item i}}',
							'    <tr class="fn-tac m-table-overflow">',
							'        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
							'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
							'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
							'        <td>{{item.busiTypeName}}</td>',
							'        <td>{{item.amount}}</td>',
							'        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>', // 跳转地址需要的一些参数
							'    </tr>', '{{/each}}' ].join(''),
					form : [
							'项目名称：',
							'<input class="ui-text fn-w160" type="text" name="projectName">',
							'&nbsp;&nbsp;',
							'客户名称：',
							'<input class="ui-text fn-w160" type="text" name="customerName">',
							'&nbsp;&nbsp;',
							'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ]
							.join(''),
					thead : [ '<th width="100">项目编号</th>',
							'<th width="120">客户名称</th>',
							'<th width="120">项目名称</th>',
							'<th width="100">授信类型</th>',
							'<th width="100">授信金额(元)</th>',
							'<th width="50">操作</th>' ].join(''),
					item : 6
				},
				callback : function($a) {
					// 跳转地址
					document.getElementById('projectCode').value = $a
							.attr('projectCode');
				}
			});

	// 清除输入框
	$('#businessTypeClear').on('click', function() {
		$('#projectCode').val('');
	});

    $('body').on('click','.relatedAssetVideo', function () {

        var url = $(this).attr('basehref');
        Y.confirm('提示','是否确认取消所有关联？',function (opt) {
            if(opt == 'yes'){
                $.ajax({
                    url: url,
                    success: function (res) {

                        if(!!res.success){
                            Y.alert('提示','取消成功！',function () {
                                window.location.href = "/systemMg/videoDevice/list.htm";
                            });
                        }else {
                            Y.alert('提示','取消失败！');
                        }

                    }
                })
            }
        })

    }).on('click','.deletAssetItem', function () {

        var url = $(this).attr('basehref');
        Y.confirm('提示','是否确认删除当前资产？',function (opt) {
            if(opt == 'yes'){
                $.ajax({
                    url: url,
                    success: function (res) {

                        if(!!res.success){
                            Y.alert('提示',res.message,function () {
                                window.location.href = "/assetMg/list.htm";
                            });
                        }else {
                            Y.alert('提示',res.message);
                        }

                    }
                })
            }
        })

    }).on('click','.viewRalateVideoDtl',function () {
        var datas = $(this).attr('ralatevideolist');
        datas = datas.indexOf(',') >= 0 ? datas.split(',') : datas;
        var _html = '';
        $.each(datas, function (i, e) {
            _html += '<span class="hiddenMax fn-dib ralatevideolistItem" title="' + e + '">' + e + '</span>'
        });
        var $content = [
            '<div class="wnd-wnd uploadFileM">',
            '      <div class="wnd-header" title="可拖动" style="cursor: move; width: 600px;"><span class="wnd-title">当前资产视频关联列表</span> ',
            '            <a href="javascript:void(0)" class="wnd-close" title="关闭"></a>',
            '      </div>',
            '      <div class="wnd-body ralatevideolistItem" style="display: block; width: 600px; height: auto; overflow: auto;">',_html,
            '      <div>',
            '</div>'
        ].join("");
        $('body').Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.wnd-close'
        });
    })
})