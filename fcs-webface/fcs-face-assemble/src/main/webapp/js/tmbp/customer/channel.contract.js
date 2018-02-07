define(function (require, exports, module) {
    // 客户管理 > 渠道合同 > 渠道合同维护

    require('zyw/upAttachModify');

    require('input.limit');
    require('Y-msg');
    require('validate');
    var util = require('util');
    var getList = require('zyw/getList')

    var $form = $('#form'),
        requiredRules = {
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            doSubmit(true)
        }
    }));

    var $channalName = $('[name="channalName"]')

    function channalNameOnly(set) {

        if (set) {

            $channalName.rules('add', {
                remote: {
                    url: '/customerMg/channal/validataChannalName.json?_time=' + (new Date()).getTime(), //后台处理程序
                    type: 'post', //数据发送方式
                    dataType: 'json', //接受数据格式   
                    data: { //要传递的数据
                        channelName: function () {
                            return $channalName.val();
                        }
                    }
                },
                messages: {
                    remote: '名称已存在'
                }
            });

        } else {

            $channalName.rules('remove', 'remote');

        }

    }

    channalNameOnly(!!!$channalName.val())

    // 选择渠道 start

    var CHANNEL_LIST = new getList()

    CHANNEL_LIST.init({
        title: '选择渠道',
        ajaxUrl: '/baseDataLoad/channel.json?channelType=YH',
        btn: '.fnChooseChannelBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac">',
                '        <td>{{item.channelType}}</td>',
                '        <td title="{{item.channelName}}">{{(item.channelName && item.channelName.length > 15)?item.channelName.substr(0,15)+\'\.\.\':item.channelName}}</td>',
                '        <td>{{item.creditAmount}}</td>',
                '        <td><a class="choose" channelcode="{{item.channelCode}}" channetypename="{{item.channelType}}" cname="{{item.channelName}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: [
                '渠道名称：',
                '<input class="ui-text fn-w160" type="text" name="likeChannelName">',
                '&nbsp;&nbsp;',
                // '渠道编号：',
                // '<input class="ui-text fn-w160" type="text" name="likeChannelCode">',
                // '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100px">渠道分类</th>',
                '<th>渠道名称</th>',
                '<th>授信额度(元)</th>',
                '<th width="50px">操作</th>'
            ].join('')
        },
        callback: function ($a) {
            document.getElementById('channalCode').value = $a.attr('channelcode')
            $channalName.val($a.attr('cname')).attr('readonly', 'readonly')
            $('#fnChannelTypeBox').html('<select class="ui-select fn-w150" disabled><option>' + $a.attr('channetypename') + '</option></select><input type="hidden" name="channalType" value="YH">')
            document.getElementById('newChannal').value = 'false'
            channalNameOnly(false)
            $channalName.valid()
        }
    });

    function doSubmit(isSubmit) {

        document.getElementById('checkStatus').value = isSubmit ? '1' : '0';

        util.ajax({
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function (res) {

                if (res.success) {

                    if (isSubmit) {

                        util.postAudit({
                            formId: res.formId,
                            _SYSNAME: 'CRM'
                        }, function () {
                            window.location.href = '/customerMg/channalContract/list.htm';
                        })

                    } else {
                        Y.alert('提示', '操作成功', function () {
                            window.location.href = '/customerMg/channalContract/list.htm';
                        });
                    }


                } else {
                    Y.alert('操作失败', res.message);
                }

            }
        });

    }


    //------ 侧边工具栏 start
    var _publicOPN = new(require('zyw/publicOPN'))();

    if ($form.length > 0) {

        _publicOPN.addOPN([{
            name: '暂存',
            alias: 'save',
            event: function () {
                doSubmit(false);
            }
        }, {
            name: '提交',
            alias: 'submit',
            event: function () {
                doSubmit(true);
            }
        }]);

    }
    _publicOPN.init().doRender();
    //------ 侧边工具栏 end

    // 必备参数
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    // 初始化弹出层
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
        'title': '人员',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'selectUsers': {
            selectUserIds: '', //已选id,多用户用,隔开
            selectUserNames: '', //已选Name,多用户用,隔开
            selectUserAccounts: '' //已选account,多用户用,隔开
        },
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });
    // 添加选择后的回调，以及显示弹出层

    $('#fnChooseMan').on('click', function () {

        var $p = $(this).parent()

        singleSelectUserDialog.init(function (relObj) {

            $p.find('.fnManName').val(relObj.fullnames)
            $p.find('.fnManId').val(relObj.userIds)
            $p.find('.fnManAccount').val(relObj.accounts)

        });


    })

    if (document.getElementById('auditForm')) {
        var auditProject = require('zyw/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    }

    // 2016.11.15 渠道类型改变，重新生成渠道编号
    $('#fnChannelType').on('change', function () {

        $.ajax('/baseDataLoad/newChannalCode.json?channalType=' + this.value)
            .then(function (res) {
                if (res.success) {
                    document.getElementById('channalCode').value = res.data
                }
            })

    })


});