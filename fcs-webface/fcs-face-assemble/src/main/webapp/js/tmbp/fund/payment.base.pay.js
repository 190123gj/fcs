define(function (require, exports, module) {
    // 资金管理 > 用户信息维护
    require('Y-msg');
    var util = require('util');
    var getList = require('zyw/getList');

    var $fnListTbody = $('#fnListTbody'),
        $form = $('#form'),
        $subjectTd,
        $fnSubjectBtn = $('#fnSubjectBtn');

    $fnListTbody.on('click', '.fnListDel', function () {
        $('#delId').val($('#delId').val() + ";" + $(this).parents('tr').find('.categoryId').val());
        $(this).parents('tr').remove();
    }).on('click', '.fnChooseSubject', function () {

        $fnSubjectBtn.trigger('click');
        $subjectTd = $(this).parent();

    }).on('click', '.radio', function () {
        //      $(this).parents('tr').find('.status').val($(this).val());
    });

    $('#fnListAdd').on('click', function () {

        var $oldTr = $fnListTbody.find('tr').last(),
            $tr = $('<tr></tr>').html($oldTr.html()).attr('diyname', $oldTr.attr('diyname'));

        $tr.find('input:not([type="radio"])').val('').removeProp('readonly');
        $tr.find('[type="radio"]').removeProp('checked').removeProp('disabled');

        $tr.find('input.fnChooseBtn').addClass('fnChooseSubject').prop('readonly', 'readonly');

        // 新增数据可以删除
        if ($tr.find('.fnListDel').length == 0) {
            $tr.find('td').last().append('<a style="position: absolute; right: -30px; top: 50%; margin-top: -10px;" href="javascript:void(0);" class="m-table-btn m-table-btn-del fnListDel"></a>');
        }

        $tr.find('[name*="categoryId"]').val('0');
        $fnListTbody.append($tr);

        util.resetName();

    });

    $form.on('click', '#doSubmit', function () {

        // 必填项
        var $fnInput = $('.fnInput:visible'),
            _isPass = true,
            _isPassEq;

        $fnInput.each(function (i, e) {

            if (!!!e.value.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = i;
                return false;
            }

        });

        if (!_isPass) {
            Y.alert('提示', '请填写完整表单', function () {
                $fnInput.eq(_isPassEq).focus();
            });
            return;
        }

        // 输入的金额 大于 0
        var _inputMonye = true,
            _inputMonyeEq,
            $fnInputMoney = $('.fnInputMoney');

        $fnInputMoney.each(function (index, el) {

            if (+el.value <= 0) {
                _inputMonye = false;
                _inputMonyeEq = index;
                return false;
            }

        });

        if (!_inputMonye) {
            Y.alert('提示', '输入的金额需要大于0', function () {
                $fnInputMoney.eq(_inputMonyeEq).focus();
            });
            return;
        }

        util.resetName();

        util.ajax({
            url: $form.attr('action'),
            data: $form.serializeJCK(),
            success: function (res) {

                if (res.success) {
                    Y.alert('提示', '操作成功', function () {
                        window.location.href = $form.attr('raction');
                    });
                } else {
                    Y.alert('操作失败', res.message);
                }

            }
        });

    });

    // 选择关联会计科目
    var _getList = new getList();
    _getList.init({
        title: '请选择会计科目',
        ajaxUrl: '/baseDataLoad/accountTitle.json?',
        btn: '#fnSubjectBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.atCode}}">{{(item.atCode && item.atCode.length > 12)?item.atCode.substr(0,12)+\'\.\.\':item.atCode}}</td>',
                '        <td title="{{item.atName}}">{{(item.atName && item.atName.length > 12)?item.atName.substr(0,12)+\'\.\.\':item.atName}}</td>',
                '        <td><a class="choose" projectcode="{{item.atCode}}" projectname="{{item.atName}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['科目代码：',
                '<input class="ui-text fn-w160" type="text" name="atCode">',
                '&nbsp;&nbsp;',
                '会计科目名称：',
                '<input class="ui-text fn-w160" type="text" name="atName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>会计科目代码</th>',
                '<th>会计科目名称</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 3
        },
        keyup: true,
        callback: function ($a) {
            $subjectTd.find('.fnChooseBtn').val($a.attr('projectcode') + " " + $a.attr('projectname'));
            $subjectTd.find('.accountName').val($a.attr('projectname'));
            $subjectTd.find('.accountCode').val($a.attr('projectcode'));
        }
    });

});