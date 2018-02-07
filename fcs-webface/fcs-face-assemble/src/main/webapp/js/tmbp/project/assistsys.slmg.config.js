define(function (require, exports, module) {
    // 项目管理 > 辅助系统  > 用印配置
    require('Y-msg')
    require('input.limit')
    require('validate')

    var util = require('util')
    var BPMiframe = require('BPMiframe')

    util.resetName()

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

            util.resetName()

            util.ajax({
                url: $form.attr('action'),
                data: $form.serialize(),
                success: function (res) {

                    Y.alert('提示', res.message, function () {

                        if (res.success) {
                            window.location.reload()
                        }

                    })

                }
            })

        }
    }))


    // 选择公司 start

    var singleSelectOrgDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true', {
        'title': '组织',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'arrys': [],
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    })

    $form.on('click', '.fnCompanyBtn', function () {

        var $td = $(this).parents('td')

        singleSelectOrgDialog.init(function (relObj) {

            // 唯一性判定
            if (!!$form.find('.fnCompanyId[value="' + relObj.orgId + '"]').length) {
                Y.alert('提示', '配置中已存在' + relObj.orgName)
                return
            }

            $td.find('.fnCompanyName').val(relObj.orgName).trigger('blur')
            $td.find('.fnCompanyId').val(relObj.orgId)

        })

    })

    // 选择公司 end

    // 列表增删 start

    $form.on('click', '.fnListDel', function () {

        if ($('.fnListDel').length === 1) {
            return
        }

        $(this).parents('tr').remove()

    }).on('click', '#fnListAdd', function (event) {

        $form.find('.fnInput').each(function (index, el) {

            var $el = $(el)
            $el.rules('remove', 'required')
            $el.attr({
                'aria-describedby': '',
                'aria-required': ''
            })

        })

        $form.find('b.error-tip').remove()

        var $tr = $form.find('tr').last().clone(true)

        $tr.find('input').val('')

        $form.find('tbody').append($tr)

        util.resetName()

        $form.find('.fnInput').each(function (index, el) {

            $(el).rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })

        })

    })

    // 列表增删 end

});