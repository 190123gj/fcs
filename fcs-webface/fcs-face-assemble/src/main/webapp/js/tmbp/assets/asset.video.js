/*
 * @Author: erYue
 * @Date:   2016-07-04 18:05:40
 * @Last Modified by:   Administrator
 * @Last Modified time: 2016-09-18 16:04:13
 */

define(function (require, exports, module) {

    require('validate');
    require('input.limit');
    var util = require('util');
    var getList = require('zyw/getList');
    var videoList = require('tmbp/assets/videoList');

    var allVideoList = [];
    var checkedListVal = $('#ralateVideo').val();
    var checkedList = [];

    //------ 公共 验证规则 start
    var $form = $('#form');
    $form.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            if (element.hasClass('ralateVideo')) {
                element.parent().find('.chooseVideo').append(error);
            } else if (element.hasClass('parentsMitem')) {
                element.parent('.m-item ').append(error);
            } else {
                element.parent().append(error);
            }
        },
        submitHandler: function (form) {},
        rules: {
            id: {
                required: true
            },
            ralateVideo: {
                required: true
            }
        },
        messages: {
            id: {
                required: '必填'
            },
            ralateVideo: {
                required: '必填'
            }
        }
    });

    //选择视频接口还原数据还原
    if (!checkedListVal) {
        checkedList = [];
    } else {
        var checkedListValArry = checkedListVal.split(',');
        $.each(checkedListValArry, function (i, e) {
            checkedList.push({
                text: e,
                value: e
            })
        })
    }

    //获取video list
    $.when(

        VMC_TOOL.init()

    ).then(function () {

        return $.when(VMC_TOOL.connect())

    }).then(function () {

        var _allData = VMC_TOOL.getMainListData();

        $.each(_allData, function (index, obj) {
            allVideoList.push({
                value: obj.text,
                text: obj.text
            });
        });


        new videoList({
            ele: $('#chooseVideo'),
            allVideoList: allVideoList,
            checkedList: checkedList, //还原数据时，需要
            textKey: 'text',
            propKeys: ['value'],
            validateKey: 'text',
            saveKey: 'text',
            saveInputId: 'ralateVideo'

        })

    }).fail(function (res) {

        console.log(res.message)

    });

    // 选择项目
    var _getList = new getList();
    _getList.init({
        title: '资产列表',
        ajaxUrl: '/baseDataLoad/queryAssetSimple.json?ralateVideo="IS"',
        btn: '.chooseBtn',
        tpl: {
            tbody: [
                '{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.assetType}}">{{(item.assetType && item.assetType.length > 12)?item.assetType.substr(0,12) + "...":item.assetType}}</td>',
                '        <td title="{{item.assetRemarkInfo}}">{{(item.assetRemarkInfo && item.assetRemarkInfo.length >0) ? ((item.assetType && item.assetRemarkInfo.length > 12)?item.assetRemarkInfo.substr(0,12) + "...":item.assetRemarkInfo) : "-"}}</td>',
                '        <td title="{{item.ownershipName}}">{{(item.ownershipName && item.ownershipName.length > 6)?item.ownershipName.substr(0,12) + "...":item.ownershipName}}</td>',
                '        <td>{{item.evaluationPrice}}</td>',
                '        <td>{{item.pledgeRate}}</td>',
                '        <td><a class="choose" assetsId="{{item.assetsId}}" assetType="{{item.assetType}}" ownershipName="{{item.ownershipName}}" evaluationPrice="{{item.evaluationPrice}}" href="javascript:void(0);">选择</a></td>', // 跳转地址需要的一些参数
                '    </tr>', '{{/each}}'
            ].join(''),
            form: [
                '资产类型：',
                '<input class="ui-text fn-w160" type="text" name="assetType">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">资产类型</th>',
                '<th width="120">资产关键信息</th>',
                '<th width="120">所有权人</th>',
                '<th width="120">评估价格（元）</th>',
                '<th width="100">抵质押率（%）</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            // 跳转地址
            $('#assetsId').val($a.attr('assetsId'));
            $('#ownershipName').val($a.attr('ownershipName'));
            $('#evaluationPrice').val($a.attr('evaluationPrice'));
            var assetTypeVal = $a.attr('assetType');
            // assetTypeVal = (!!assetTypeVal && assetTypeVal.length > 13) ? assetTypeVal.substr(0,13) + "..." : assetTypeVal;
            $('#assetType').val(assetTypeVal).attr('title', assetTypeVal);
            $('#toReviews').removeClass('fn-hide').attr('href', '/assetMg/toAdd.htm?id=' + $a.attr('assetsId') + '&isView=true');
        }
    });


    $('#submit').on('click', function () { //提交前验证

        if (!$form.valid()) return;
        var _isPass = true,
            _isPassEq,
            $thisError;

        var $validateList = $('.fnInput');
        $validateList.each(function (index, el) {
            var _this = $(this);
            var thisVal = _this.val();
            if (!!!thisVal.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {

            $thisError = $validateList.eq(_isPassEq);

            Y.alert('提醒', '请把表单填写完整', function () {

                $thisError.val('').focus();
            });
            return;
        };
        submitAddAseet();

    });

    function submitAddAseet() { //提交

        util.ajax({
            url: '/systemMg/videoDevice/related.htm',
            type: 'post',
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提醒', res.message, function () {
                        window.location.href = '/systemMg/videoDevice/list.htm';
                    });

                } else {
                    Y.alert('提醒', res.message);
                }
            }

        });
    };

})