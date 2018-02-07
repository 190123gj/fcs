define(function (require, exports, module) {
    // 项目管理 > 授信前管理 > 合同申请审核
    require('zyw/upAttachModify')
    require('Y-msg')
    require('validate')
    require('input.limit')
    var util = require('util');
    //------ 审核通用部分 start
    var auditProject = require('zyw/auditProject');
    var _auditProject = new auditProject();
    var $form = $('#addForm')
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    //------ 审核通用部分 end
    //
    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    //------ 侧边栏 end

    //BPM弹窗
    var BPMiframe = require('BPMiframe');
    var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
    var BPMiframeConf = {
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'selectUsers': {
            selectUserIds: '',
            selectUserNames: ''
        },
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    };

    var _chooseLegalManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
        title: '法务人员'
    }));

    var $legalManagerName = $('#legalManagerName'),
        $legalManagerId = $('#legalManagerId'),
        $legalManagerAccount = $('#legalManagerAccount');

    $('#legalManagerBtn').on('click', function () {

        _chooseLegalManager.init(function (relObj) {

            $legalManagerId.val(relObj.userIds);
            $legalManagerName.val(relObj.fullnames);
            $legalManagerAccount.val(relObj.accounts);

        });
    });

    //查看
    $('.fnWindowOpen').on('click', function (e) {
        e.preventDefault();
        window.open(this.href);
    });
    //新窗口打开
    $('.fnNewWindowOpen').on('click', function (e) {
        e.preventDefault();
        util.openDirect('/projectMg/index.htm', this.href);
    });

    //合同审查意见不必填
    // $('[name="workflowVoteContent"]').removeClass('fnAuditInput').parent().find('.m-required').html('');
    $('#auditForm').attr('noreason', 'ui-btn-green,ui-btn-gray');

    // 查看授信措施
    $('.fnLookMeasures').on('click', function () {
        var $this = $(this)
        var _html = '<div style="width: 600px; max-height: 400px; overflow-y: auto;">' + $this.next().html() + '</div>'
        $('body').Y('Msg', {
            width: '600px',
            type: 'alert',
            content: _html,
            icon: ''
        });
        // Y.alert('关联授信措施', _html)
    })

    //  ------  打印 start

    $('#fnPrint').click(function (event) {

        var $fnPrintBox = $('#fnPrintBox')

        $fnPrintBox.find('.ui-btn-submit').remove()

        $fnPrintBox.find('.printshow').removeClass('fn-hide')

        util.print($fnPrintBox.html())
    })

    //  ------- 打印 end



    // ---------保存

    util.resetName()
    var minRules = {
        rules: {

        },
        messages: {
        }
    }
    $('.fnInput').each(function (idx, el) {
        minRules.rules[el.name] = {
            required:true,
            min: 1
        }
        minRules.messages[el.name] = {
            required:'份数必填',
            min: '最小份数为1'
        }
    })



    function addMyValidate(cb) {
        $form.validate($.extend(true, util.validateDefault,{
            rules: minRules.rules,
            messages: minRules.messages,
            invalidHandler: function() {
                setTimeout(function () {
                    $('#fnAuditBox').addClass('fn-hide')
                })
                return
            },
            submitHandler: function () {
                util.resetName()
                util.ajax({
                    url: $form.attr('action'),
                    data: $form.serialize(),
                    success: function (res) {
                        cb && cb(res)
                    },
                    error: function () {
                        Y.alert('提示', '网络错误')
                    }
                })
            }
        }))
    }
    

    $('#submit').click(function () {
        addMyValidate(function (res) {
            Y.alert('提示', res.message, function () {
                if (res.success) {
                    window.location.reload()
                }
            })
        })
        $form.submit()
    })

    $('#fnAuditBtnPass').click(function () {
        addMyValidate()
        $form.submit()
    })



});