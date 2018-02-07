define(function (require, exports, module) {
    // 客户管理 > 个人客户 > 客户列表
    require('Y-msg');
    require('zyw/publicPage');

    var util = require('util');
    var getList = require('zyw/getList');

    var DIYYWPZLISTC = require('./channel.diy.business')

    var getTypesOfCredit = require('zyw/getTypesOfCredit');
    var _getTypesOfCredit = new getTypesOfCredit();
    _getTypesOfCredit.init({
        chooseAll: true,
        btn: '#businessTypeBtn2',
        name: '#businessTypeName2',
        code: '#businessTypeCode2'
    });
    //业务品种 自定义 start

    var $DIYYWPZLIST = $('<div class="m-modal-box fn-hide"></div>'),
        $businessTypeName = $('#businessTypeName'),
        $businessTypeCode = $('#businessTypeCode');

    $('#businessTypeBtn').on('click', function () {

        if (!!!$DIYYWPZLIST.html()) {

            $DIYYWPZLIST.html(DIYYWPZLISTC)

            $DIYYWPZLIST.appendTo('body')

        }
        $DIYYWPZLIST.removeClass('fn-hide')

    });

    $DIYYWPZLIST.on('click', '.close', function () {
        $DIYYWPZLIST.addClass('fn-hide')
    }).on('click', '.fnCanChoose', function () {
        $businessTypeName.val(this.innerHTML.replace(/<.+?>/gim, '').replace(/\s/g, ''));
        $businessTypeCode.val(this.getAttribute('businesstypecode'));
        $DIYYWPZLIST.addClass('fn-hide');

    });

    //业务品种 自定义 end

    //清除
    $('#businessTypeClear').on('click', function () {
        var d = document;
        d.getElementById('businessTypeName').value = '';
        d.getElementById('businessTypeCode').value = '';
    });

    $('#list').on('click', '.fnDelCustomer', function () {

        // 删除客户

        var _this = $(this),
            _candel = _this.attr('candel') ? true : false;

        if (_candel) {

            Y.confirm('提示', '是否确认删除所选中的客户信息？', function (opn) {

                if (opn == 'yes') {

                    util.ajax({
                        url: _this.parents('tbody').attr('delaction'),
                        data: {
                            id: _this.parents('tr').attr('formid')
                        },
                        success: function (res) {

                            if (res.success) {

                                Y.alert('提示', '操作成功', function () {
                                    window.location.reload(true);
                                });

                            } else {

                                Y.alert('操作失败', res.message);

                            }

                        }
                    });

                }

            });

        } else {

            Y.alert('提示', '该客户有关联的项目，不允许删除！')

        }

    }).on('click', '.fnChangeState', function () {

        // 改变 渠道状态
        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _state = _this.hasClass('enable') ? 'on' : 'off';

        util.ajax({
            url: '/customerMg/channal/updateStatus.json',
            data: {
                id: _this.attr("data_id"),
                status: _state
            },
            success: function (res) {

                if (res.success) {
                    Y.alert('提示', '操作成功', function () {
                        window.location.reload(true);
                    });
                } else {
                    Y.alert('操作失败', res.message)
                    _this.removeClass('ing');
                }

            }
        });

    });

    var CHANNELTYPE = '',
        CHANNELTYPEDATAAll = [{
            value: '',
            text: '全部'
        }],
        CHANNELTYPEDATABANK = [{
            value: 'YH',
            text: '银行'
        }],
        CHANNELTYPEDATANOBANK = [{
            value: 'ZQ',
            text: '证券公司'
        }, {
            value: 'XT',
            text: '信托公司'
        }, {
            value: 'JYPT',
            text: '交易平台'
        }, {
            value: 'JJ',
            text: '基金公司'
        }, {
            value: 'ZL',
            text: '租赁公司'
        }, {
            value: 'WLJR',
            text: '网络金融平台'
        }, {
            value: 'ZZYX',
            text: '自主营销'
        }, {
            value: 'QT',
            text: '其他渠道'
        }, {
            value: 'BGS',
            text: '本公司'
        }];

    var CHOOSECHANNEL;

    $('body').on('click', '.fnChooseChannelBtn', function () {

        var $p = $(this).parent();

        if (!!!CHOOSECHANNEL) {

            var _cArr = [];

            if (/notBank/g.test(this.className)) {
                _cArr = CHANNELTYPEDATANOBANK
            } else if (/isBank/g.test(this.className)) {
                _cArr = CHANNELTYPEDATABANK
            } else {
                _cArr = CHANNELTYPEDATAAll.concat(CHANNELTYPEDATABANK.concat(CHANNELTYPEDATANOBANK))
            }

            $.each(_cArr, function (index, obj) {
                CHANNELTYPE += '<option value="' + obj.value + '">' + obj.text + '</option>'
            });

            CHOOSECHANNEL = new getList();
            CHOOSECHANNEL.init({
                width: '90%',
                title: '选择渠道',
                ajaxUrl: '/baseDataLoad/channel.json',
                btn: '#fnChooseChannel',
                tpl: {
                    tbody: ['{{each pageList as item i}}',
                        '    <tr class="fn-tac m-table-overflow">',
                        '        <td title="{{item.channelType}}">{{(item.channelType && item.channelType.length > 16)?item.channelType.substr(0,16)+\'\.\.\':item.channelType}}</td>',
                        '        <td title="{{item.channelCode}}">{{(item.channelCode && item.channelCode.length > 6)?item.channelCode.substr(0,6)+\'\.\.\':item.channelCode}}</td>',
                        '        <td title="{{item.channelName}}">{{(item.channelName && item.channelName.length > 10)?item.channelName.substr(0,10)+\'\.\.\':item.channelName}}</td>',
                        '        <td><input type="checkbox" value="{{item.channelCode}}" sname="{{item.channelName}}"></td>',
                        '    </tr>',
                        '{{/each}}'
                    ].join(''),
                    form: '',
                    form: ['渠道类别：<select class="ui-select" name="channelType">' + CHANNELTYPE + '</select>',
                        '&nbsp;&nbsp;',
                        '渠道编号：<input class="ui-text fn-w160" name="likeChannelCode" type="text">',
                        '&nbsp;&nbsp;',
                        '渠道名称：<input class="ui-text fn-w160" name="likeChannelName" type="text">',
                        '&nbsp;&nbsp;',
                        '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                    ].join(''),
                    thead: ['<th>渠道类别</th>',
                        '<th>渠道编号</th>',
                        '<th>渠道名称</th>',
                        '<th width="50">操作</th>'
                    ].join(''),
                    item: 6
                },
                multiple: true,
                callback: function (self) {

                    var $c = self.$list.find('[type="checkbox"]:checked'),
                        _id = [],
                        _name = [];
                    $c.each(function (i, e) {

                        _id.push(e.value);
                        _name.push(e.getAttribute('sname'));

                    });

                    $p.find('.fnChooseChannelName').val(_name)
                    $p.find('.fnChooseChannelId').val(_id)

                }
            });

        }

        document.getElementById('fnChooseChannel').click();

    }).on('click', '.fnChooseChannelClear', function () {

        var $p = $(this).parent();
        $p.find('.fnChooseChannelName').val('')
        $p.find('.fnChooseChannelId').val('')

    }).append('<div id="fnChooseChannel"></div>');

    // ------ 自定义列 start
    var $fnDiyColBox = $('#fnDiyColBox'),
        $fnDiyColName = $('.fnDiyColName'),
        $fnDiyColCode = $('.fnDiyColCode');

    $fnDiyColBox.on('click', '.close', function () {

        $fnDiyColBox.addClass('fn-hide');

    }).on('click', '.sure', function () {

        var _text = [],
            _code = [];

        $fnDiyColBox.find('input:checked').each(function (index, el) {

            _code.push(this.value);
            _text.push(this.parentNode.innerHTML.replace(/<.+?>/gim, ''));

            $fnDiyColName.val(_text);
            $fnDiyColCode.val(_code);

            $fnDiyColBox.addClass('fn-hide');

        });
    });
    $('.fnDiyColBtn').on('click', function () {

        var _codeArr = $fnDiyColCode.val().split(',');


        $fnDiyColBox.find('input').each(function (index, el) {

            if ($.inArray(this.value, _codeArr) > -1) {

                $(this).prop('checked', 'checked');

            } else {

                $(this).removeProp('checked');

            }

        });

        $fnDiyColBox.removeClass('fn-hide');

    });
    // ------ 自定义列 end



});