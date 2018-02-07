/**
 * Created by eryue
 * Create in 2016-09-13 11:16
 * Description:
 */

'use strict';

define(function(require, exports, module) {
    require('Y-msg');
    require('zyw/publicPage');

    var util = require('util');
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    var getList = require('zyw/getList');

    var _getList = new getList(); // 选择项目

    var itemNum = 2;
    var itemsMaxNum = 400;
    var userName = $('#hddUserName').val();
    var $meryChooseBtn; //缓存落实人员选择按钮
    // 必备参数
    var scope = '{type:\"system\",value:\"all\"}';
    var selectUsers = {
        selectUserIds: '', //已选id,多用户用,隔开
        selectUserNames: '' //已选Name,多用户用,隔开
    }

    var singleSelectMemberNamesDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
        'title': '人员',
        'width': 850,
        'height': 460,
        'scope': scope,
        'selectUsers': selectUsers,
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });

    // 添加选择后的回调，以及显示弹出层
    var $body = $('body');

    // $body.on("click", ".fnChImplementMembers", function() {
    //     var _this = $(this);
    //     singleSelectMemberNamesDialog.init(function(relObj) {
    //         _this.parent().children('[name*="confirmManNames"]').val(relObj.fullnames);
    //         _this.parent().children('[name*="confirmManIds"]').val(relObj.userIds);
    //     });
    // });
    var $item = $('.summary_items_warp').find('.summary_item').eq(0).clone();
    $('body').on('click', '.fnToClearlementMembers', function() {

        $(this).parent().find('.implementMembers').val('');

    }).on('click', '.addItem', function() { //新增会议记录

        if (itemNum > itemsMaxNum) {
            Y.alert('提示', '会议纪要不能超过' + itemsMaxNum + '条！');
            return;
        };
        var $newItem = $('<div class="summary_item" diyname="summaryOrder"></div>').html($item.html());
        $('.summary_item').find('.deleteItem').addClass('fn-hide')
        $newItem.find('.summary_num label').html(itemNum).end()
            .find('.deleteItem').removeClass('fn-hide').end()
            // .find('.radio').removeClass('toChecked').end()
            // .find('.radio').eq(1).addClass('toChecked').end()
            .find('[name]:not("[type=\"radio\"], [type=\"checkbox\"]")').val('');

        $('.summary_items_warp').append($newItem);

        util.resetName();
        itemNum++;
        toChecked(); //单选

    }).on('click', '.deleteItem', function() { //删除会议记录
        var $this = $(this);
        var $thisP = $this.parents('.summary_item');
        var length = $thisP.siblings('.summary_item').length;
        if(length > 1) $thisP.prev().find('.deleteItem').removeClass('fn-hide');
        $thisP.remove();
        util.resetName();
        setItemsIndex();
        itemNum--;
        toChecked(); //单选

    }).on('click', '.summary_box .radio', function() { //落实人员显示隐藏

        var $this = $(this);
        if ($this.hasClass('noCheck')) return;
        var $implementMembers = $this.parents('.m-item').nextAll('.m-item');

        if ($this.hasClass('radioYes')) {
            $implementMembers.show().find('input[name],textarea[name]').not(':hidden').addClass('fnInput');
        } else {
            $implementMembers.hide().find('input[name],textarea[name]').not(':hidden').removeClass('fnInput').val('');
        }

        // toChecked ???
        $this.parents('.m-item').find('input').removeClass('toChecked');
        $this.addClass('toChecked');
        dynamicRules($('.summary_box'));

    }).on('click', '.fnChImplementMembers', function() { //缓存点击按钮，方便回调
        $meryChooseBtn = $(this);
    });

    // function orderName() { //name值序列化
    //     var $orderName = $('[orderName]');

    //     $.each($orderName, function(index, ele) {
    //         var _this = $(this);
    //         var orderNameVal = _this.attr('orderName');
    //         _this.find('[name]').each(function(n, e) {
    //             var $this = $(this);
    //             var name = $this.attr('name');
    //             $this.attr('name', orderNameVal + '[' + index + '].' + name);
    //         })
    //     });
    //     toChecked();
    // };

    function setItemsIndex() { //设置序列号.summary_num label

        var $itemBox = $('.summary_items_warp'),
            $item = $itemBox.find('.summary_item');

        $.each($item, function(index, ele) {
            $(this).find('.summary_num label').html(index + 1);
        });

    };

    function toChecked() {

        $('.toChecked').trigger('click');


    };

    util.resetName();

    //------ 公共 验证规则 start
    require('validate');
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            element.parent().append(error);
            // if (element.hasClass('fnErrorAfter')) {
            //     element.after(error);
            // } else {
            //     element.parent().append(error);
            // }
        },
        submitHandler: function(form) {
        },
        rules: {
            projectCode: {
                required: true
            },
            customerName: {
                required: true
            },
            councilType: {
                required: true
            },
            beginTime: {
                required: true
            },
            councilPlace: {
                required: true
            },
            councilSubject:{
                required: true
            },
            participantNames:{
                required: true
            }
        },
        messages: {
            projectCode: {
                required: '必填'
            },
            customerName: {
                required: '必填'
            },
            councilType: {
                required: '必填'
            },
            beginTime: {
                required: '必填'
            },
            councilPlace: {
                required: '必填'
            },
            councilSubject:{
                required: '必填'
            },
            participantNames:{
                required: '必填'
            }
        }
    };
    $('#form').validate(validateRules);
    function dynamicRules(ele) {
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput');
        $nameList.each(function (i,e) {
            var _this = $(this);
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })
    };

    dynamicRules($('.summary_box'));
    //提交
    var $addForm = $('#form'),
        $fnInput = $('.fnInput');

    $('#submit').on('click', function() {
        if(!$('#form').valid()) return;
        $fnInput = $('.fnInput');
        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _isPass = true,
            _isPassEq;
        $fnInput.each(function(index, el) {

            if (!!!el.value.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }

        });

        if (!_isPass) {
            Y.alert('提示', '请填写完整表单', function() {
                $fnInput.eq(_isPassEq).focus();
            });
            _this.removeClass('ing');
            return;
        }

        util.ajax({
            url: $addForm.attr('action'),
            data: $addForm.serialize(),
            success: function(res) {
                if (res.success) {
                    Y.alert('提示', '已提交', function() {
                        util.direct('/projectMg/councilRisk/list.htm');
                    });
                } else {
                    Y.alert('提示', res.message);
                    _this.removeClass('ing');
                }
            }
        });
    });

    // 风险小组会议纪要落实人员只能是小组成员 2016.10.06
    var $fnLXRYBox = $('#fnLXRYBox'),
        $whichChooseBtn;
    $fnLXRYBox.on('click', '.close', function() {
        $fnLXRYBox.addClass('fn-hide').find('.fnCheckbox').removeProp('checked');
    }).on('click', '.sure', function() {

        var _nameArr = [],
            _idArr = [];

        $fnLXRYBox.find('.fnCheckbox:checked').each(function(index, el) {

            _nameArr.push(el.getAttribute('username'));
            _idArr.push(el.value);

        });

        $whichChooseBtn.parent().find('.userName').val(_nameArr).trigger('blur')
        $whichChooseBtn.parent().find('.userId').val(_idArr)

        $fnLXRYBox.addClass('fn-hide').find('.fnCheckbox').removeProp('checked');

    });
    $.ajax({
        url: '/baseDataLoad/riskTeamMember.json?projectCode=' + document.getElementById('projectCode').innerHTML,
        type: 'GET',
        dataType: 'json'
    }).done(function(res) {

        var _html = '';

        if (res.success && res.data.length) {

            $.each(res.data, function(index, obj) {
                _html += [
                    '<div class="m-item"><label class="fn-csp"><input class="checkbox fn-mr5 fnCheckbox" type="checkbox" value="' + obj.userId + '" username="' + obj.userName + '">' + obj.userName + '&emsp;' + obj.userMobile + '&emsp;' + obj.userEmail + '</label></div>'
                ].join('');
            });

        } else {
            _html = '<p class="fn-tac fn-pt20">无落实人员</p>'
        }

        $fnLXRYBox.find('#manlist').html(_html);

    });

    $addForm.on('click', '.fnChooseLSRY', function() {

        $whichChooseBtn = $(this);

        var _idArr = $whichChooseBtn.parent().find('.userId').val().split(',');

        $.each(_idArr, function(index, id) {

            $fnLXRYBox.find('input[value="' + id + '"]').prop('checked', 'checked')

        });

        $fnLXRYBox.removeClass('fn-hide');

    }).on('click', '.fnDelLSRY', function() {

        var $p = $(this).parent();
        $p.find('.userName').val('')
        $p.find('.userId').val('')

    });

})