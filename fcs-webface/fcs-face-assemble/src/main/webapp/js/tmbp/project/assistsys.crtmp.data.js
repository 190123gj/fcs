define(function (require, exports, module) {
    //辅助系统  > 合同管理 > 合同模板管理 表和字段 列表的公共操作

    require('zyw/publicPage');
    require('Y-msg');

    var util = require('util');

    var getList = require('zyw/getList');
    //
    //------ 列表 删除 集合 start
    util.listOPN({
        // 常用数据库表维护
        delDatabase: {
            url: '/projectMg/contract/deleteTable.htm',
            message: '已删除',
            opn: '删除'
        },
        // 常用数据库字段维护
        delField: {
            url: '/projectMg/contract/deleteField.htm',
            message: '已删除',
            opn: '删除'
        }
        // 合同模板管理
        // delTMP: {
        //     url: '/projectMg/contract/deleteTemplate.htm',
        //     message: '已删除',
        //     opn: '删除'
        // }
    }, 'id');

    // 2016.12.15 删除合同模板
    $('#list').on('click', '.delTMP', function () {

        var $this = $(this)
        var $tr = $this.parents('tr')

        Y.confirm('信息提醒', '您确定要删除' + $tr.attr('formname') + '？', function (opn) {
            if (opn == 'yes') {

                var _url = ''

                if ($tr.attr('formid') !== '0'&&$tr.attr('status') !== 'IN_USE') {
                    _url = '/projectMg/form/delete.htm?formId=' + $tr.attr('formid')
                } else {
                    _url = '/projectMg/contract/deleteTemplate.htm?id=' + $tr.attr('id')
                }

                util.ajax({
                    url: _url,
                    data: {
                        _SYSNAME: $this.attr('sysname') || '',
                    },
                    success: function (res) {
                        if (res.success) {
                            Y.alert('消息提醒', res.message, function(){
                                window.location.reload(true);
                            });
                        } else {
                            Y.alert('消息提醒', res.message);
                        }
                    }
                })
            }
        })

    })

    //------ 列表 删除 集合 end
    //
    //授信类型
    var getTypesOfCredit = require('zyw/getTypesOfCredit');
    var _getTypesOfCredit = new getTypesOfCredit();
    _getTypesOfCredit.init({
        chooseAll: true,
        btn: '#businessTypeBtn',
        name: '#businessTypeName',
        code: '#businessTypeCode'
    });
    //
    //------ 常用数据库表维护 start
    var $addDataBase = $('#addDataBase'),
        $tableName = $('#tableName'),
        tableNameVal = $tableName.val();
    $addDataBase.on('click', '.submit', function () {

        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _isPass = true;
        $('.fnInput').each(function (index, el) {
            if (!!!el.value) {
                _isPass = false;
            }
        });

        if (!_isPass) {
            Y.alert('提示', '请填写必填项');
            _this.removeClass('ing');
            return;
        }

        //是否重名
        var _isRepeat = false;
        if ($tableName.val() != tableNameVal) {
            util.ajax({
                url: '/projectMg/contract/checkTableName.htm?tableName=' + $tableName.val(),
                async: false,
                success: function (res) {
                    if (res.success) {
                        _isRepeat = true;
                    }
                }
            });
        }

        if (_isRepeat) {
            Y.alert('提示', '表名称已存在');
            _this.removeClass('ing');
            return;
        }

        var _isExit = _this.hasClass('fnExit');

        util.ajax({
            url: $addDataBase.attr('action'),
            data: $addDataBase.serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', '已保存', function () {
                        if (_isExit) {
                            window.location.href = '/projectMg/contract/dbTableList.htm';
                        } else {
                            window.location.reload(true);
                        }
                    });
                } else {
                    Y.alert('提示', res.message);
                    _this.removeClass('ing');
                }
            }
        });
    });
    //------ 常用数据库表维护 end
    //
    //
    //------ 常用数据库字段维护 start
    var $addField = $('#addField'),
        $fieldName = $('#fieldName'),
        fieldNameVal = $fieldName.val();
    $addField.on('click', '.submit', function () {

        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _isPass = true;
        $('.fnInput').each(function (index, el) {
            if (!!!el.value) {
                _isPass = false;
            }
        });

        if (!_isPass) {
            Y.alert('提示', '请填写必填项');
            _this.removeClass('ing');
            return;
        }

        //是否重名
        var _isRepeat = false;
        if ($fieldName.val() != fieldNameVal) {
            util.ajax({
                url: '/projectMg/contract/checkFieldName.htm?tableId=' + $('#tableId').val() + '&fieldName=' + $fieldName.val(),
                async: false,
                success: function (res) {
                    if (res.success) {
                        _isRepeat = true;
                    }
                }
            });
        }

        if (_isRepeat) {
            Y.alert('提示', '表名称已存在');
            _this.removeClass('ing');
            return;
        }

        var _isExit = _this.hasClass('fnExit');

        util.ajax({
            url: $addField.attr('action'),
            data: $addField.serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', '已保存', function () {
                        if (_isExit) {
                            window.location.href = '/projectMg/contract/dbFieldList.htm';
                        } else {
                            window.location.reload(true);
                        }
                    });
                } else {
                    Y.alert('提示', res.message);
                    _this.removeClass('ing');
                }
            }
        });
    });
    if ($addField.length) {
        //选择表名
        var getTableNameSelect = eval($('#projectPhase').val());
        var _getTableName = new getList();
        _getTableName.init({
            title: '常用数据库表',
            ajaxUrl: '/projectMg/contract/loadDBTable.json',
            btn: '#tableName',
            input: ['tableName', 'tableId'],
            tpl: {
                tbody: ['{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td title="{{item.tableForShort}}">{{(item.tableForShort && item.tableForShort.length > 6)?item.tableForShort.substr(0,6)+\'\.\.\':item.tableForShort}}</td>',
                    '        <td title="{{item.tableName}}">{{(item.tableName && item.tableName.length > 16)?item.tableName.substr(0,16)+\'\.\.\':item.tableName}}</td>',
                    '        <td>{{item.projectPhase}}</td>',
                    '        <td title="{{item.remark}}">{{(item.remark && item.remark.length > 10)?item.remark.substr(0,10)+\'\.\.\':item.remark}}</td>',
                    '        <td><a class="choose" tableName="{{item.tableName}}" tableId="{{item.tableId}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: ['表名简称：',
                    '<input class="ui-text fn-w90" type="text" name="tableForShort">',
                    '&nbsp;&nbsp;',
                    '表名：',
                    '<input class="ui-text fn-w90" type="text" name="tableName">',
                    '&nbsp;&nbsp;',
                    '项目阶段：',
                    '<select class="ui-select fn-w100" name="projectPhase">',
                    (function (arr) {
                        var _str = '';
                        for (var i = 0; i <= arr.length - 1; i++) {
                            _str += '<option value="' + arr[i].code + '">' + arr[i].txt + '</option>'
                        }
                        return _str;
                    })(getTableNameSelect),
                    '</select>',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: ['<th width="100">表名简称</th>',
                    '<th width="120">表名</th>',
                    '<th width="120">项目阶段</th>',
                    '<th width="100">说明</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            }
        });
    }
    //------ 常用数据库字段维护 end


});