define(function(require, exports, module) {

    require('zyw/publicPage');
    var util = require('util');
    //搜索框时间限制
    $('body').on('blur', '.fnListSearchDateS', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateE');

        $input.attr('onclick', 'laydate({min: "' + this.value + '"})');

    }).on('blur', '.fnListSearchDateE', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateS');

        $input.attr('onclick', 'laydate({max: "' + this.value + '"})');

    }).on('click','.exportBtn',function(){
        $(this).attr('href','/projectMg/file/importTemplate.json');
    }).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');
//上传
    require('Y-htmluploadify');
    //导入
    $('#dataImport').click(function (event) {
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/file/dataImport.json',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, data, resfile) {

                var jsonData = JSON.parse(data);

                if (jsonData.success) {

                    var _head = '<thead><tr><th>项目编号</th><th>档案编号</th><th>导入结果</th></tr></thead>',
                        _tbody = '',
                        _result = jsonData.message

                    $.each(_result, function (index, obj) {

                        _tbody += '<tr><td>' + (obj.projectCode || '') + '</td><td>' + obj.fileCode + '</td><td>' + obj.importResult + '</td></tr>'

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
                    })

                } else {

                    Y.alert('提示', jsonData.message)

                }



            }

        });

    });
    $('#list').on('click', '.fnDelAll', function () {

        var $tr = $(this).parents('tr')

        $.when(getAllFileCode($tr.attr('formid')))
            .then(function (titles) {

                Y.confirm('信息提醒', '您确定要删除以下档案？<div class="fn-tal" style="max-height: 300px; overflow-y: auto">' + titles.join('<br>') + '</div>', function (opn) {
                    if (opn == 'yes') {
                        util.ajax({
                            url: '/projectMg/form/delete.htm',
                            data: {
                                formId: $tr.attr('formid')
                            },
                            success: function (res) {
                                if (res.success) {
                                    Y.alert('消息提醒', res.message, function () {
                                        window.location.reload(true);
                                    });
                                } else {
                                    Y.alert('消息提醒', res.message);
                                }
                            }
                        });
                    }
                });

            })

    }).on('click', '.fnWithdrawAll', function () {

        var $tr = $(this).parents('tr')

        $.when(getAllFileCode($tr.attr('formid')))
            .then(function (titles) {

                Y.confirm('信息提醒', '您确定要撤回以下档案？<div class="fn-tal" style="max-height: 300px; overflow-y: auto">' + titles.join('<br>') + '</div>', function (opn) {
                    if (opn == 'yes') {
                        util.ajax({
                            url: '/projectMg/form/cancel.htm',
                            data: {
                                formId: $tr.attr('formid')
                            },
                            success: function (res) {
                                if (res.success) {
                                    Y.alert('消息提醒', res.message, function () {
                                        window.location.reload(true);
                                    });
                                } else {
                                    Y.alert('消息提醒', res.message);
                                }
                            }
                        });
                    }
                });

            })

    })

    function getAllFileCode(formId) {

        var dtd = $.Deferred()

        util.ajax({
            url: '/projectMg/file/getFormFileCode.json?formId=' + formId,
            success: function (res) {

                if (!res.success) {
                    Y.alert('提示', '查询档案详情失败')
                    return
                }

                var titleArr = []

                $.each(res.data.pageList, function (index, obj) {

                    titleArr.push(obj.fileCode)

                })

                return dtd.resolve(titleArr)

            }
        })

        return dtd.promise()

    }

})