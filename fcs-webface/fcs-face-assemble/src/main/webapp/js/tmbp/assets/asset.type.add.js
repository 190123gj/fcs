/*
 * @Author: erYue
 * @Date:   2016-07-04 18:05:40
 * @Last Modified by:   erYue
 * @Last Modified time: 2016-08-22 13:16:39
 */

define(function(require, exports, module) {
    require('validate');
    require('lib/lodash');
    require('Y-msg');
    require('input.limit');

    var util = require('util');

    var templates = {
        '$newItem0' : $('<tr flag="1" orderName="pledgeTextCustomOrders">'
            + '<input type="hidden" name="sortOrder" value="" class="sortOrder">'
            + '<input type="hidden" name="relationFieldName" value="" class="relationFieldName">'
            + '<input type="hidden" name="isByRelation" value="NO" class="isByRelation">'
            + '<td width="250px">'
            + '字段名称：'
            + '<input type="text" class="ui-text fn-wauto fieldName fn-validate fnIgnoreInput" name="fieldName" maxlength="30">'
            + '</td>'
            + '<td class="strType">'
            + '字段类型：'
            + '<select class="ui-select selectType fn-validate" name="fieldType">'
            + '<option value="NONE">请选择</option>'
            + '<option value="TEXT">文本型</option>'
            + '<option value="SELECT">选择型</option>'
            + '<option value="SELECT_CONTION_RELATION">条件关联</option>'
            + '<option value="DATE">日期型</option>'
            + '<option value="NUMBER">数值型</option>'
            + '<option value="ADMINISTRATIVE_PLAN">行政区划</option>'
            + '<option value="MTEXT">多行表格型</option>'
            + '</select>'
            + '</td>'
            + '<td class="strTypeRslt">'
            + '<span class="type-none" value="none">请选择字段类型！</span>'
            + '</td>'
            + '<td width="145px" class="fileTypeRadio fn-validate">'
            + '是否必填：'
            + '<i class="radio-no isRequiredIS" value="IS"></i>是'
            + '<i class="radio-no isRequiredIS" value="NO"></i>否'
            + '<input type="text" name="isRequired" value="" class="fn-hide fn-validate">'
            + '</td>'
            + '<td width="215px" class="relationOption fn-tac">---'
            // + '最迟录入表单：'
            // + '<select class="ui-select fn-validate"
            // name="latestEntryForm">'
            // + '<option value="NONE">请选择</option>'
            // + '<option value="INVESTIGATION">尽职调查报告</option>'
            // + '<option value="CREDIT">授信条件落实</option>'
            // + '</select>'
            + '</td>'
            + '<td width="70px" class="fn-text-c fn-clear fn-option">'
            + '<a href="javascript:void(0);" title="排序"><i class="icon-sort sortItem"></i><i class="icon-sort-up sortItem"></i></a>'
            + '</td>'
            + '<td width="30px" class="fn-text-c fn-clear">'
            + '<a href="javascript:void(0);" class="fn-dib delete-btn" title="删除"><i class="icon-del-2 deleteItem">删除</i></a>'
            + '</td>' + '</tr>'),
        '$newItem1' : $('<tr flag="1" orderName="pledgeImageCustomOrders">'
            + '<input type="hidden" name="sortOrder" value="" class="sortOrder">'
            + '<td width="250px">'
            + '字段名称：'
            + '<input type="text" class="ui-text fn-wauto fieldName fn-validate fnIgnoreInput" name="fieldName" maxlength="30">'
            + '</td>'
            + '<td class="fileTypeCheckBox">'
            + '文件格式：'
            + '<input type="checkbox" class="checkbox" value="img">图片'
            + '<input type="checkbox" class="checkbox" value="word">WORD'
            + '<input type="checkbox" class="checkbox" value="excle">EXCLE'
            + '<input type="checkbox" class="checkbox" value="pdf">PDF'
            + '<input type="checkbox" class="checkbox" value="video">视频'
            + '<input type="checkbox" class="checkbox" value="audio">音频'
            + '<input type="text" class="attachmentFormat fn-hide fn-validate" name="attachmentFormat" value="">'
            + '</td>'
            + '<td width="145px" class="fileTypeRadio fn-validate">'
            + '是否必填：'
            + '<i class="radio-no isRequiredIS" value="IS"></i>是'
            + '<i class="radio-no isRequiredIS" value="NO"></i>否'
            + '<input type="text" name="isRequired" value="" class="fn-hide fn-validate">'
            + '</td>'
            + '<td width="215px">'
            + '录入表单：'
            + '<select class="ui-select fn-validate" name="latestEntryForm">'
            + '<option value="NONE">请选择</option>'
            + '<option value="INVESTIGATION">资产新增</option>'
            + '<option value="CREDIT">授信条件落实</option>'
            + '</select>'
            + '</td>'
            + '<td width="70px" class="fn-text-c fn-clear fn-option">'
            + '<a href="javascript:void(0);" title="排序"><i class="icon-sort sortItem"></i><i class="icon-sort-up sortItem"></i></a>'
            + '</td>'
            + '<td width="30px" class="fn-text-c fn-clear">'
            + '<a href="javascript:void(0);" class="fn-dib delete-btn" title="删除"><i class="icon-del-2 deleteItem">删除</i></a>'
            + '</td>' + '</tr>'),
        '$newItem2' : $('<tr flag="1" orderName="pledgeNetworkCustomOrders">'
            + '<td width="100px" class="fn-text-c">关联网站</td>'
            + '<td class="strType">'
            + '网站名称：'
            + '<input type="text" class="ui-text fn-wauto fn-validate fieldName fnIgnoreInput" name="websiteName"  maxlength="30">'
            + '</td>'
            + '<td>'
            + '网址：'
            + '<input type="text" class="ui-text fn-wauto fn-validate" name="websiteAddr" >'
            + '</td>'
            + '<td width="30px" class="fn-text-c fn-clear">'
            + '<a href="javascript:void(0);" class="fn-dib delete-btn" title="删除"><i class="icon-del-2 deleteItem">删除</i></a>'
            + '</td>' + '</tr>'),
        'setSelect' : '<a href="javascript:void(0);" class="setSelect">设置</a>',
        'sortIcon' : '<a href="javascript:void(0);" class="ui-btn ui-btn-fill"><i class="icon-sort sortItem"></i><i class="icon-sort-up sortItem"></i></a>',
        // 'required' : '<input type="hidden" name="relationFieldName" value=""
        // class="isRequired">',
        'none' : '<span class="type-none" value="none">请选择字段类型！</span>',
        'relationOp' : $('<label>关联条件项：</label>'
            + '<select class="ui-select fn-validate" name="relationConditionItem">'
            + '<option value="NONE">请选择</option>' + '</select>'),
        'TEXT' : '<span class="type-text" value="TEXT">最长字数：'
        + '<input type="text" class="ui-text fn-w40 fn-validate fnMakeNumber" name="controlLength" maxlength="10">'
        + '</span>',
        'DATE' : '<span class="type-date" value="DATE">范围：'
        + '<select class="fn-w70 ui-select fn-validate" name="timeSelectionRange">'
        + '<option  value="NONE">请选择</option>'
        + '<option value="RANDOM_TIME">任意时间</option>'
        + '<option value="SYSTEM_TIME_BEFORE">系统时间前</option>'
        + '<option value="SYSTEM_TIME_AFTER">系统时间后</option>'
        + '</select>' + '</span>',
        'NUMBER' : '<span class="type-number" value="NUMBER">计量单位：'
        + '<input type="text" class="ui-text fn-w35 fn-validate" name="measurementUnit" maxlength="10">'
        + '</span>',
        'SELECT' : '<span class="type-choose" value="SELECT">填写选择项：'
        + '<input type="text" class="ui-text fn-w40 fn-validate" name="mostCompleteSelection">'
        + '</span>',
        'ADMINISTRATIVE_PLAN' : '',
        'MTEXT' : '<input type="hidden" name="conditionItem">',
        'SELECT_CONTION_RELATION' : '<span class="type-select" value="SELECT_CONTION_RELATION">'
        + '条件项：<input type="text" class="ui-text fn-w70 fn-validate conditionItem fnIgnoreInput" name="conditionItem">'
        + '</span>',
        'MTEXT_SELECT_OPTIONS' : '<option value="NONE">请选择</option>'
        + '<option value="TEXT">文本型</option>'
        + '<option value="NUMBER">数值型</option>',

    };

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    //------ 公共 验证规则 start
    var $form = $('#form');
    jQuery.validator.addMethod("selectOp", function(value, element) {//验证下拉选择
        var newVal = value.replace(/\s/g, '');
        return this.optional(element) || (newVal.indexOf('NONE') < 0 && !!newVal);
    }, "请选择类型");
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            // element.parent().append(error);
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else {
                element.parent().append(error);
            }
        },
        submitHandler: function(form) {
        },
        rules: {
            levelOne: {
                required: true,
                selectOp:true
            },
            levelTwo: {
                required: true,
                selectOp:true
            },
            levelThree: {
                required: true
            },
            pledgeRate: {
                required: true
            }
        },
        messages: {
            levelOne: {
                required: '必填'
            },
            levelTwo: {
                required: '必填'
            },
            levelThree: {
                required: '必填'
            },
            pledgeRate: {
                required: '必填'
            }
        }
    };
    $form.validate(validateRules);
    // 邮政编码验证

    function dynamAddRules(ele) {
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fn-validate').not('.disabled');
        dynamicRemoveRules($('.tableList'));
        $nameList.each(function (i,e) {
            var _this = $(this);
            if(_this.hasClass('ui-select')){
                _this.rules('add', {
                    // required: true,
                    selectOp: true,
                    messages: {
                        required: '必填',
                        selectOp: '必填'
                    }
                });
                return;
            }
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })
    };
    function dynamicRemoveRules(ele) {
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fn-validate').not('.disabled');
        $nameList.each(function (i,e) {
            $(this).rules("remove");
        })
    };
    dynamAddRules($('.tableList'));

    var $option = $('.optionSelect'), // 一级二级分类
        $table = $('.tableList'), // 表格
        $addItem = $('.addItem'); // 新增字段

    var selectVal = 1, // 标记当前分类VAL
        selectFlag = 2; // 标记当前SELECT

    var isEdit = $('#form').attr('isEdit'); // 判断当前页面是否为编辑
    // var isCanSubmit = false; //重名标记

    $(function() { // 加载默认字段后，序列化并排序
        setOrderNum();
    });

    // 增加一级分类
    $option.on('click', '.toAdd', function() {
        toAdd($(this));
    });

    // 回退选择
    $option.on('click', '.toSelect', function(event) {

        toSelect($(this));
        // dynamAddRules($('.typeInfo'))
        selectVal = 1; // 初始化当前标记
    });

    $option.on('change', 'select', function() { // 选择分类

        var thisVal = $(this).val(); // 获取当前select的VALUE作为selectVal标记
        var $thisBox = $(this).parents('.optionSelect');
        var thisFlag = $(this).attr('tempName'); // 获取当前select的NAME作为selectFlag标记
        var $nextBox = $thisBox.next('.optionSelect');
        var $nextSelect = $nextBox.find('select');

        $nextSelect.find('option').eq(0).attr('selected', 'selected');
        $nextBox.find('.toAdd').addClass('toHidden');
        lockInPut($(this));

        if (thisVal != 'NONE') {

            unLockInPut($(this));
            if ($nextBox.length > 0) {

                if (thisFlag == selectFlag) {
                    if (thisVal != selectVal) {
                        $nextSelect.find('option').not('[value="NONE"]')
                            .remove();
                        selectOption(thisVal, $nextSelect);

                    } else {

                        lockInPut($(this));
                    }
                } else {
                    $nextSelect.find('option').not('[value="NONE"]').remove();
                    selectOption(thisVal, $nextSelect);
                }
            }

        } else {
            if ($nextBox.length > 0) {
                $nextBox.find('select option').eq(0).attr('selected',
                    'selected').end().find('.new-option').val('').end()
                    .find('select').removeClass('toHidden').end().find(
                    '.new-option,.toSelect').removeClass('show');
            }
            ;
        }
        ;
        selectVal = thisVal;
        selectFlag = thisFlag;

    });

    // 新增分类验证
    $option.on('blur', '.getVal', function() {

        var thisVal = $(this).val();
        var thisName = $(this).attr('tempName');
        thisName = (typeof (thisName) == 'undefined') ? $(this).attr('name')
            : thisName;

        validateOption(thisVal, thisName, $(this));

    });

    function toAdd($ele) { // 点击新增
        dynamicRemoveRules($('.optionSelect'))
        $('.optionSelect').find('b.error-tip').remove();
        var $thisBox = $ele.parents('.optionSelect');
        var $nextBox = $thisBox.nextAll();

        $thisBox.find('select option').eq(0).attr('selected', 'selected');
        $thisBox.find('.new-option').val('');
        $thisBox.find('select,.toAdd').addClass('toHidden');
        $thisBox.find('.toSelect,.new-option').addClass('show');
        if ($nextBox.length > 0) {
            $nextBox.find('select option').eq(0).attr('selected', 'selected');
            $nextBox.find('.new-option').val('');
            $nextBox.find('select,.toAdd').addClass('toHidden');
            $nextBox.find('.new-option').addClass('show');
        }
        ;
        changeName();
        dynamAddRules($('.optionSelect'))

    }
    ;

    function toSelect($ele) { // 点击回退
        dynamicRemoveRules($('.optionSelect'));
        $('.optionSelect').find('b.error-tip').remove();
        var $thisBox = $ele.parents('.optionSelect');
        var $nextBox = $thisBox.nextAll();

        $thisBox.find('select option').eq(0).attr('selected', 'selected');
        $thisBox.find('.new-option').val('');
        $thisBox.find('select,.toAdd').removeClass('toHidden');
        $thisBox.find('.toSelect,.new-option').removeClass('show');
        if ($nextBox.length > 0) {
            $nextBox.find('select option').eq(0).attr('selected', 'selected');
            $nextBox.find('.new-option').val('');
            $nextBox.find('select').removeClass('toHidden');
            $nextBox.find('.toSelect,.new-option').removeClass('show');
        }
        ;
        changeName();
        lockInPut($ele);
        dynamAddRules($('.optionSelect'))
    }
    ;

    function selectOption(val, $ele) {

        var $thisBox = $ele.parents('.optionSelect');

        $thisBox.find('.toAdd').removeClass('toHidden');
        util.ajax({
            url : '/assetMg/pledgeType/add.htm',
            type : 'POST',
            data : {
                'levelOne' : val
            },
            success : function(res) {
                var data = res.listInfo;
                if (data) {

                    $.each(data, function(i, ele) {
                        $ele.append('<option value="' + ele.levelTwo + '">'
                            + ele.levelTwo + '</option>');
                    });

                } else {
                    if (!!!data) {
                        return;
                    }
                    Y.alert('提醒', res.message);
                }
            }
        });

    }
    ;

    function validateOption(val, name, $ele) {

        if (!!!val) {
            lockInPut($ele);
            return;
        }

        var data = {};

        if (name == 'levelTwo') {
            data.levelOne = $('[name="levelOne"]').val();
        }
        ;
        if (name == 'levelThree') {
            data.levelOne = $('[name="levelOne"]').val();
            data.levelTwo = $('[name="levelTwo"]').val();
        }
        ;

        data[name] = val;
        data['isAdd' + name] = true;

        util.ajax({
            url : '/assetMg/pledgeType/checkName.htm',
            type : 'POST',
            data : data,
            success : function(res) {

                if (res.success == false) {
                    Y.alert('提醒', res.message, function() {
                        $ele.val('');
                        lockInPut($ele);
                    });

                } else {
                    $ele.val(val);
                    unLockInPut($ele);
                }

            }
        });
    }
    ;

    function unLockInPut($ele) {

        var flag = $ele.parents('.optionSelect').find('[name*=level]').attr(
            'name');

        if (flag == 'levelOne') {
            if ($('[name=levelTwo]').hasClass('ui-select2')) {

                $('[name=levelTwo]').removeAttr('disabled');

            } else {

                $('[name=levelTwo]').removeAttr('readOnly');

            }

        } else if (flag == 'levelTwo') {

            $('[name=levelThree]').removeAttr('readOnly');

        } else {

            return;

        }
    }
    ;
    function lockInPut($ele) {

        var flag = $ele.parents('.optionSelect').find('[name*=level]').attr(
            'name');

        if (flag == 'levelOne') {

            if ($('[name=levelTwo]').hasClass('ui-select2')) {

                $('[name=levelTwo]').attr('disabled', 'disabled');

            } else {

                $('[name=levelTwo]').attr('readOnly', 'true');

            }
            $('[name=levelThree]').attr({
                readOnly : 'true'
            });

        } else if (flag == 'levelTwo') {

            $('[name=levelThree]').attr({
                readOnly : 'true'
            });

        } else {

            return;

        }
    }
    ;

    function changeName() {

        $option.each(function() {
            var $select = $(this).find('select');
            var $newInput = $(this).find('.new-option');
            var thisName = $select.attr('tempName');

            if ($select.hasClass('toHidden')) {
                $select.removeAttr('name').removeClass('fn-validate');
                $newInput.attr('name', thisName).addClass('fn-validate');
            } else {
                $newInput.removeAttr('name').removeClass('fn-validate');
                $select.attr('name', thisName).addClass('fn-validate');
            }
            ;
        })

    }

    // ------------------------------select结束---------------------------------
    //

    var counter = 1; // 计数器，用于计算增加的字段

    // 新增字段
    $addItem.bind('click', function() {

        var $thisTable = $(this).parents('.tableList').find('table');
        var $thieNewItem;

        if ($(this).attr('id') == 'addText') {
            $thieNewItem = templates.$newItem0;
        } else if ($(this).attr('id') == 'addImg') {
            $thieNewItem = templates.$newItem1;
        } else {
            $thieNewItem = templates.$newItem2;
        }
        ;
        $thieNewItem = $thieNewItem.clone(true).attr('flag', counter);
        if ($thisTable.find('.hasMTEXT').length == 0) {
            $thisTable.append($thieNewItem);
        } else {
            $thisTable.find('.hasMTEXT').first().before($thieNewItem);
        }
        ;
        counter++;
        setOrderNum();
        dynamAddRules($('.tableList'));

    });

    // 删除条目
    $table.on('click', '.deleteItem',function() {

        var $thisTr = $(this).parents('tr');
        var thisItemFlag = $thisTr.attr('firstItem');
        var thisSecondItem = $thisTr.attr('secondItem');
        var $relationItem = $('[firstItem="' + thisSecondItem+ '"]');
        $(this).parents('tr').remove();
        if (typeof (thisItemFlag) != 'undefined') {
            $('[secondItem=' + thisItemFlag + ']').remove();
        };
        if (typeof (thisSecondItem) != 'undefined'
            && $relationItem.length > 0) {

            var length = $('[seconditem=' + thisSecondItem
                + ']').length;
            $relationItem.find('[name*="conditionItem"]').val(
                length);

        }
        setOrderNum();
        dynamAddRules($('.tableList'));
    });

    // 关联选择
    $table.on('change', '.selectType', function() {

        var $this = $(this);
        var $thisTr = $this.parents('tr');
        var thisFlag = parseInt($thisTr.attr('flag'));
        var $thisTable = $(this).parents('.tableList').find('table');
        var thisItemFlag = $thisTr.attr('secondItem');
        var thisVal = $this.find('option:selected').val();
        var thisOldVal = $thisTr.attr('oldVal');

        $thisTr.find('td.strTypeRslt').html(templates[thisVal]);
        $thisTr.find('.isByRelation').val('NO');
        if (thisVal == 'SELECT_CONTION_RELATION' || thisVal == 'MTEXT') {

            if (thisOldVal == 'SELECT_CONTION_RELATION'
                || thisOldVal == 'MTEXT') {
                var thisfirstItem = $thisTr.attr('firstItem');
                $('[secondItem=' + thisfirstItem + ']').remove();
            }
            $thisTr.attr('firstItem', 'firstItem' + thisFlag).find(
                'td.fn-option').html(templates.setSelect);
            $thisTr.find('.isByRelation').val('IS');

            if (thisVal == 'MTEXT') {
                $thisTr.addClass('hasMTEXT').appendTo($thisTable);
            }
            ;

        } else {

            if (thisOldVal == 'SELECT_CONTION_RELATION'
                || thisOldVal == 'MTEXT') {
                var thisfirstItem = $thisTr.attr('firstItem');
                $('[secondItem=' + thisfirstItem + ']').remove();
                $thisTr.removeAttr('firstItem').find('td.fn-option').html(
                    templates.sortIcon);
                if (typeof (thisItemFlag) == 'undefined'
                    && thisOldVal == 'MTEXT'
                    && $thisTable.find('.hasMTEXT').length > 1) {
                    $thisTable.find('.hasMTEXT').first().before($thisTr);
                }
                ;
            }

        }
        ;
        setOrderNum();
        $thisTr.attr('oldVal', thisVal);
        dynamAddRules($('.tableList'));

    });

    // 关联字段设置
    $table.on('click', '.setSelect',function() {

            var $thisTr = $(this).parents('tr');
            var thisFieldType = $thisTr.find('[name*="fieldType"]').val();
            var $thisTrInput = $thisTr
                .find('.strTypeRslt [name*=conditionItem]');

            if (!$thisTrInput.val()
                && thisFieldType == 'SELECT_CONTION_RELATION') {

                Y.alert('提示', '请先设置条件关联项', function() {
                    $thisTrInput.focus();
                });

            } else {

                addSelectOp($(this));
                if (thisFieldType == 'MTEXT') {
                    var length = $('[seconditem='
                        + $thisTr.attr('firstitem') + ']').length;
                    $thisTr.find('[name*="conditionItem"]').val(length);
                }

            }
        dynamAddRules($('.tableList'));
        });

    // 排序
    $table.on('click','.sortItem',function() {

                var $this = $(this), $thisTr = $(this).parents('tr'), $thisTable = $(
                    this).parents('.tableList');
                $prevTr = $thisTr.prev();// firstItem2
                $nextTr = $thisTr.next();// firstItem2
                // var length = $thisTable.find('tr').length,
                // index = $thisTable.find('tr').index($thisTr);
                var msg1 = typeof ($prevTr.attr('firstItem')) == 'undefined' ? '已经排在第一位啦！'
                    : '已经排在当前关联字段第一位啦！', msg2 = typeof ($nextTr
                    .attr('firstItem')) == 'undefined' ? '已经是最末啦！'
                    : '已经排在当前关联字段最末位啦！';

                if ($this.hasClass('icon-sort')) {

                    if ($prevTr.length == 0
                        || typeof ($prevTr.attr('firstItem')) != 'undefined') {

                        Y.alert('提示', msg1);
                        return;

                    } else {

                        $thisTr.insertBefore($prevTr);

                    };

                } else {

                    if ($nextTr.length == 0
                        || typeof ($nextTr.attr('firstItem')) != 'undefined') {

                        Y.alert('提示', msg2);
                        return;

                    } else {

                        $thisTr.insertAfter($nextTr);

                    };
                    // setOrderNum();
                };

                setOrderNum();

            }).on('blur','.conditionItem',function() {// 关联条件失焦更新

                var $this = $(this);
                var thisVal = $this.val();
                var tempOptionVal = $this.attr('tempOptionVal');

                if (thisVal == tempOptionVal)
                    return; // 当前输入框的值未发生改变

                var thisFlag = $this.parents('tr').attr('firstItem');
                var $secondItem = $('[secondItem="' + thisFlag + '"]');

                if (!thisVal) {
                    Y.alert('提示', '需先填写关联项才能新增关联字段哦！');
                    return;
                }
                ;
                if (!checkItems(thisVal, $this))
                    return;
                if ($secondItem.length > 0) {

                    $
                        .each(
                            $secondItem,
                            function() {

                                var $thisSelect = $(this).find(
                                    '.relationOption');
                                var optionTplt = templates.relationOp
                                    .clone(true);
                                var thisOptionVal = $thisSelect
                                    .attr('value');

                                $thisSelect
                                    .html(optionTplt)
                                    .find('select')
                                    .append(
                                        creatOption($this
                                            .parents('tr')))
                                    .find(
                                        '[value="'
                                        + thisOptionVal
                                        + '"]')
                                    .attr('selected',
                                        'selected');

                            })
                }
                ;
                resetName();
                $this.attr('tempOptionVal', thisVal);

            }).on(
        'blur',
        '.fieldName',
        function() {

            var _this = $(this);
            var thisVal = _this.val();

            var $thisTableFileName = _this.parents('table').find(
                '.fieldName').not($(this));

            $thisTableFileName
                .each(function(index, el) {

                    if (thisVal == $(this).val()
                        && thisVal.length > 0) {

                        Y.alert('提示', '字段名称不能重复，请仔细检查！',
                            function() {
                                _this.val('');
                            });
                        // isCanSubmit = false;
                        return;

                    }

                    // isCanSubmit = true;

                });

        }).on('blur', '.type-choose input', function() {
        var $this = $(this);
        var thisVal = $this.val();
        checkItems(thisVal, $this);
    })

    function addSelectOp($item) {

        var $thisTr = $item.parents('tr');
        var thisFirstItem = $thisTr.attr('firstItem');
        var conditionItemVal = $thisTr.find('.fieldName').val();
        var $firstItem = templates.$newItem0.clone(true).attr('secondItem',
            thisFirstItem);

        if ($thisTr.hasClass('hasMTEXT')) {
            $firstItem.find('.selectType').html(templates.MTEXT_SELECT_OPTIONS); // 移除条件关联项
        }

        $firstItem.find('[value="SELECT_CONTION_RELATION"],[value="MTEXT"]')
            .remove(); // 移除条件关联项

        if ($thisTr.find('[name*="fieldType"]').val() == "SELECT_CONTION_RELATION") {
            var optionTplt = templates.relationOp.clone(true);

            $firstItem.find('.relationOption').html(optionTplt).find('select')
                .append(creatOption($thisTr))
        }

        if (conditionItemVal.length > 0) {
            $firstItem.find('.relationFieldName').val(conditionItemVal);
        }
        if ($('[secondItem="' + thisFirstItem + '"]').length > 0) {
            $('[secondItem="' + thisFirstItem + '"]').last().after($firstItem);
        } else {
            $('[firstItem="' + thisFirstItem + '"]').after($firstItem);
        }
        ;
        setOrderNum();

    }
    ;

    // 条件关联项
    function creatOption($ele) {

        var thisVal = $ele.find('[value="SELECT_CONTION_RELATION"] input')
            .val();
        thisVal = formatStr(thisVal);
        var arry = thisVal.split(',');
        var options = '';

        $.each(arry, function(i, el) {
            options += '<option value="' + el + '">' + el + '</option>';
        });

        return options;

    }
    ;

    // 判断条件项是否有重复

    function checkItems(val, $ele) {
        if (!val)
            return true;
        var arryItems = val.split(',');
        var afterCheckItems = _.uniq(arryItems);

        if (arryItems.length > afterCheckItems.length) {
            // Y.alert('提示','选择项有重复项，点击确定将去除重复项，点击取消将清空输入框！',function () {
            // $ele.val(afterCheckItems.join(','));
            // })
            Y.confirm('提示', '选择项有重复项，点击确定将去除重复项，点击取消将清空输入框！', function(opn) {
                if (opn == "yes") {
                    $ele.val(afterCheckItems.join(',')).focus();
                } else {
                    $ele.val('').focus();
                }
                ;
            });
            return false;
        }
        return true;
    }
    ;

    // 编辑页面操作
    if (isEdit == 'true') {

        // 初始化关联条件项,包含了resetName
        if ($('.conditionItem').length > 0) {

            $('.conditionItem').each(function() {

                $(this).bind('blur').trigger('blur');
            })
        }
        ;
        setOrderNum();
    }

    // 表单处理start
    //
    $table.on('blur', '.fieldName', function() { // 失焦赋值

        var _this = $(this);
        var _thisVal = _this.val();
        var $thisTr = _this.parents('tr');
        var thisTrSelect = $thisTr.attr('firstItem');

        $('tr[secondItem="' + thisTrSelect + '"').find('.relationFieldName')
            .val(_thisVal);
    });

    $table.on('click', '.fileTypeCheckBox .checkbox', function() { // 逗号分割文件类型并保存到隐藏域
        var thisVal = $(this).val();
        var $thisTempBox = $(this).siblings('.attachmentFormat');
        var oldVal = $thisTempBox.val();

        if ($(this).prop('checked') == true) {
            oldVal = oldVal + ',' + thisVal;
        } else {
            oldVal = oldVal.replace(thisVal, '');
        }
        ;

        oldVal = formatStr(oldVal);
        $thisTempBox.val(oldVal).blur();
    });
    $table.on('click', '.fileTypeRadio .radio', function() { // 保存RADIO的值，以便验证
        var thisVal = $(this).val();
        var $thisBox = $(this).parents('.fileTypeRadio');

        $thisBox.attr('value', thisVal);
    });
    // 表单处理end

    function setOrderNum() { // 排序
        $table.find('tr').each(function(index, el) {
            if ($(this).find('.sortOrder').length >= 0) {
                $(this).find('.sortOrder').val(index);
            }
            ;
            if (index == ($table.find('tr').length - 1)) {
                resetName();
            }
        });

    }
    ;
    // ------ 数组Name命名 start
    function resetName() {

        // ------ fnTableList resetName 0~N

        $('.tableList').each(
            function(index1, ele1) {
                // listResetName($(this).find('tr[orderName]'));
                $(this).find('tr[orderName]').each(
                    function(index2, ele2) {

                        var $thisWrap = $(this), _orderName = $thisWrap
                            .attr('orderName'), index = $thisWrap
                            .index();

                        $thisWrap.find('[name]').each(
                            function() {

                                var _this = $(this), name = _this
                                    .attr('name');

                                if (name.indexOf('.') > 0) {
                                    name = name.substring(name
                                            .lastIndexOf('.') + 1);
                                }

                                name = _orderName + '[' + index
                                    + '].' + name;

                                _this.attr('name', name);

                            });

                    });

            });
        // doChecked(); // 修正RADIO的NAME值相同冲突问题,当到最后

    };

    // 去除连续逗号，首尾逗号，多余空格，输出"55,77,99,00格式字符串"
    function formatStr(str) {
        var lostSpace = /[ ]/g; // 匹配所有空格
        var lostContinueComma = /,{1,}/g; // 去除连续逗号，保留一个
        var lostRLComma = /(^\,*)|(\,*$)/g; // 去除首尾逗号

        return str.replace(lostSpace, '').replace(lostContinueComma, ',')
            .replace(lostRLComma, '');
    }
    ;

    // 表单处理

    $('body').on('click', '.isRequiredIS', function() {

        var _this = $(this);
        var thisVal = _this.attr('value');
        var checkClass = 'radio-checked';

        if (!_this.hasClass(checkClass)) {
            _this.addClass(checkClass);
        }

        _this.siblings('i').removeClass(checkClass);
        _this.siblings('[name]').val(thisVal).blur();

    });

    var $addForm = $('#form');

    $('#submit').on('click',function() {
        if(!$form.valid()) return;
        var _isPass = true, _isPassEq;
        var $inputDom = $('.fn-validate'), $first = $('[firstitem]');
        var errorArry = [];

        errorArry = _.filter(_.chain($first).map(function(dom) {// 验证关联条件项和多行表格是否存在子项
            var flag = $(dom).attr('firstitem');
            var $secondItems = $('[seconditem=' + flag + ']');
            return {
                status : $secondItems.length == 0 ? true : false,
                dom : $(dom)
            }
        }).value(), function(n) {
            return n.status == true;
        });

        if (_.size(errorArry) > 0) {
            Y.alert('提示', '选择多行表格和条件关联类型，必须为其设置子项！', function() {
                errorArry[0].dom.find('.fieldName').focus();
            });
            return;
        };
        $inputDom.each(function(index, el) {
            var thisVal;
            if ($(this).hasClass('fileTypeRadio')) {
                thisVal = $(this).find('[name*=isRequired]').val();
            } else {
                thisVal = $(this).val();
            }
            var status = $(this).attr("disabled")
            if (status == true)return;
            if (typeof (thisVal) == 'undefined' || thisVal.length == 0
                || thisVal == 'NONE') {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {

            Y.alert('提醒', '请把表单填写完整', function() {
                $thisError = $inputDom.eq(_isPassEq);

                if ($thisError.attr('type') != "hidden") {
                    $thisError.focus();
                } else { // 针对hidden类型
                    $thisError.parent().focus();
                }
            });
            return;
        }
        formSubmit();
    });

    function formSubmit() {
        util.ajax({
            url : '/assetMg/pledgeType/save.htm',
            type : 'post',
            data : $addForm.serialize(),
            success : function(res) {
                if (res.success) {
                    Y.alert('提醒', res.message, function() {
                        window.location.href = '/assetMg/pledgeType/list.htm';
                    });

                } else {
                    Y.alert('提醒', res.message);
                }
            }
        });
    }
    ;

})