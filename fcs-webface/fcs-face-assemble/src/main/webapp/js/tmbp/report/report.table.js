/*
 * @Author: erYue
 * @Date:   2016-08-12 14:18:47
 * @Last Modified by:   erYue
 * @Last Modified time: 2016-08-18 17:55:43
 */

define(function (require, exports, module) {

    // require('zyw/publicPage');
    require('tmbp/chooseDateNew');
    require('Y-msg');
    // require('tmbp/report/reportPrint');

    var util = require('util')

    var mergeTable = require('zyw/mergeTable');
    var newMergeTable = new mergeTable();

    var $form = $('#fnListSearchForm');
    var url = $form.attr('action');
    var isEmptyData = $('#hasAllData').length = 0 ? false : !!$('#hasAllData').val();

    newMergeTable.init({
        bridge: true,
        obj: '.root1',
        //valType: false,
        // mergeCallback: function(Dom //Dom为每次遍历的合并对象和被合并对象) {//每次遍历合并会调用的callback
        // },
        callback: function () {
            $('.hiddenTable').css('visibility', 'visible');
        }

    });

    $('body').on('click', '#fnListSearchBtn', function () {

        if (!checkDate()) return;
        window.location.href = '/reportMg/report/edit.htm?code=' + $('[name="reportCode"]').val() + '&year=' + $('[name="year"]').val() + '&month=' + $('[name="month"]').val();

    }).on('click', '#submitBtn', function () {

        startSubmit();

    }).on('click', '#fnDoExport', function () {
        // 导出
    	var sss = $('.report-table').parent();
    	sss.find('td:hidden').remove();
    	
    	$('.report-table').attr("border","1");
    	
        var ss = sss.html();
//        console.log(ss);
//        ss.find('td:hidden').remove();
        $('input[name=s1]').val(ss);
//        alert(ss);
    	// 添加标题
    	var titleDC = $('.m-h2-title').text();
//    	alert(titleDC);
    	$('input[name=titleDC]').val(titleDC);
    	// 添加时间
    	var timeDC = $('input[name=reportTime]').val();
//    	alert(timeDC);
    	$('input[name=timeDC]').val(timeDC);
        $('#form2').submit();
    }).on('click', '#fnDoPrient', function () {
        // 打印
        util.print(document.getElementById('formTable').innerHTML)
    })

    function startSubmit() {

        var url = $form.attr('action');
        var year = $('[name="year"]').val(),
            month = $('[name="month"]').val(),
            reportCode = $('[name="reportCode"]').val(),
            reportName = $('[name="reportName"]').val(),
            content = $('#formTable').html();
        var data = {
            reportYear: year,
            reportMonth: month,
            reportName: reportName,
            reportCode: reportCode,
            content: content
        };

        if (!checkDate()) return;
        // if (boolean) data.content = content;
        if (isEmptyData) {
            Y.confirm('提示', '有尚未报送的数据，是否确认保存？', function (opn) {
                if (opn == "yes") {
                    ajaxForm(data);
                };
            });
        } else {
            ajaxForm(data);
        }

    };

    function ajaxForm(data) {

        $.ajax({
            url: url,
            type: 'POST',
            data: data,
            success: function (res) {
                console.log(res)
                if (res.success) {
                    Y.alert('提示', res.message, function () {
                        window.location.href = location.href;
                    });
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });

    }

    function checkDate() {

        var year = $('[name="year"]').val(),
            month = $('[name="month"]').val();

        if (($('[name="year"]').length > 0 && !!!year) || ($('[name="month"]') > 0 && !!!month)) {
            Y.alert('提示', '请先选择时间！', function () {
                if (!!!year) {
                    $('[name="year"]').focus();
                    return;
                };
                if (!!!month) {
                    $('[name="month"]').focus();
                    return;
                }
            });
            return false;
        };
        return true;
    };
    // _.uniq([1, 1, 2], true);
    // => [1, 2]
  
})