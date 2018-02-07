define(function (require, exports, module) {
    // 项目管理 > 辅助系统  > 用印配置
    require('Y-msg')
    require('input.limit')
    require('validate')

    var util = require('util')
    var BPMiframe = require('BPMiframe')

    util.resetName()

    var $form = $('#fnListSearchForm'),
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
                url: $form.attr('act'),
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

    window.toPage = function (totalPage, pageNo) {
        if (totalPage < pageNo) {
            return false;
        }
        if($('.changed').length){
            Y.confirm('提醒','修改数据未保存！是否返回保存？',function(opn){
                if(opn == 'yes') {

                } else {
                    document.getElementById('fnPageNumber').value = pageNo;
                    document.getElementById('fnListSearchForm').submit();
                }
            });
        }else {
            document.getElementById('fnPageNumber').value = pageNo;
            $('#fnListTbody').find('input').attr('disabled',true);
            document.getElementById('fnListSearchForm').submit();
        }
    }

    $('#fnListSearchGo').on('click', function () {

        var _input = +document.getElementById('fnListSearchInput').value,
            _max = +$(this).attr('maxitem');
        if (_input > _max || _input <= 0) {
            return;
        }

        if($('.changed').length){
            Y.confirm('提醒','修改数据未保存！是否返回保存？',function(opn){
                if(opn == 'yes') {

                } else {
                    document.getElementById('fnPageNumber').value = _input;
                    document.getElementById('fnListSearchForm').submit();
                }
            });
        }else {
            document.getElementById('fnPageNumber').value = _input;
            $('#fnListTbody').find('input').attr('disabled',true);
            document.getElementById('fnListSearchForm').submit();
        }

    });

    $form.on('change','.fnInput ',function(){
        $(this).addClass('changed')
    })
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
        serial()

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
        $tr.find('input').attr('title','')

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
        serial()
    })


    function serial(){
        $('.serial').each(function(i,el){
            $(el).html(i+1)
        })
    }
    serial()
});