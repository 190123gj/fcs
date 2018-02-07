/**
 * 页面涉及到新增和签报
 *
 * 签报和新增验证规则一致，某些交互不同
 *
 * 验证规则通过控件上的class命添加
 * 
 */

define(function (require, exports, module) {
    // 客户管理 > 个人客户 > 新增、编辑
    var COMMONTOOL = require('./personal.enterprise.add');

    require('zyw/upAttachModify');

    require('Y-msg');

    require('validate');
    require('validate.extend');

    var util = require('util');

    // 风险查询 信息、相似、失信
    var riskQuery = require('zyw/riskQuery');
    // if (document.getElementById('riskQuery')) {
    var initRiskQuery = new riskQuery.initRiskQuery(false, 'fnCustomerName', 'fnCertNo', 'userType', 'orgCode', 'isThreeBtn', 'fnCertNo');
    // initRiskQuery.toggleXS(false)
    // initRiskQuery.toggleXX(false)
    var $fnCheckRiskBtn = $('#fnCheckRiskBtn');
    $fnCheckRiskBtn.on('click', function () {

        initRiskQuery.getAllInfo(false, true, false, function (html) {
            // document.getElementById('fnCheckRiskTip').innerHTML = html
            document.getElementById('fnCheckRiskTip').innerHTML = html
        })

    });
    // 非身份证不给看
    var $ptype = $('#fnCertType');
    if ($ptype.val() == 'IDENTITY_CARD') {
        // initRiskQuery.toggleSX(false)
        $fnCheckRiskBtn.removeClass('fn-hide')
    }
    $ptype.on('change', function () {
        // initRiskQuery.toggleSX((this.value === 'IDENTITY_CARD') ? true : false)
        if (this.value === 'IDENTITY_CARD') {
            $fnCheckRiskBtn.removeClass('fn-hide')
        } else {
            $fnCheckRiskBtn.addClass('fn-hide')
                // $('#fnCheckRiskTip').html('<input type="hidden" name="messageInfo">').addClass('fn-hide')
            document.getElementById('fnCheckRiskTip').innerHTML = '<input type="hidden" name="messageInfo">'
        }
    });
    // }

    var checkKHSF = new riskQuery.initCerNoNameMobile('fnCertNo', 'fnCustomerName', false, 'fnCheckKHSFBtn', 'fnCheckKHSFTip');
    var checkKHM = new riskQuery.initCerNoNameMobile('fnCertNo', 'fnCustomerName', 'fnCheckKHMobile', 'fnCheckKHMBtn', 'fnCheckKHMTip');
    var checkSpoCertSF = new riskQuery.initCerNoNameMobile('fnSpoCertNo', 'fnSpouseName', false, 'fnSpouseCheckNBtn', 'fnSpouseCheckNTip');
    var checkSpoCertM = new riskQuery.initCerNoNameMobile('fnSpoCertNo', 'fnSpouseName', 'fnSpouseCheckMMobile', 'fnSpouseCheckMBtn', 'fnSpouseCheckMTip');

    new riskQuery.initCheckBankCard('fnCheckKHQKName', 'fnCheckKHQKNo', 'fnCheckKHQKBtn', 'fnCheckKHQKTip');
    new riskQuery.initCheckBankCard('fnCheckBRGZDFName', 'fnCheckBRGZDFNo', 'fnCheckBRGZDFBtn', 'fnCheckBRGZDFTip');
    new riskQuery.initCheckBankCard('fncheckPOGZDFName', 'fncheckPOGZDFNo', 'fncheckPOGZDFBtn', 'fncheckPOGZDFTip');

    // 是否需要检测用户
    // 如果原来的证件号码和原来一直，就不需要检测
    var NEEDCHECK = false,
        $fnCertNo = $('#fnCertNo'),
        OLDNO = $fnCertNo.val();

    var ISCHANGE = document.getElementById('fnChangeForm') ? true : false, // 是否是签报页面
        $form = $('#form');

    $form.validate($.extend(true, {}, COMMONTOOL.REQUIRERULES, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {

            if (element.hasClass('fnErrorAfter')) {

                element.after(error);

            } else {

                element.parent().append(error);

            }

        }
    }));

    // 上会的时候，个人信息不允许修改的只是个人基本信息（除客户与我司关系）
    var $fnProjectStatus = $('#fnProjectStatus');
    if ($fnProjectStatus.val() == 'IN' || $fnProjectStatus.val() == 'IS') {

        var _loading = new util.loading();
        _loading.open();

        setTimeout(function () {
            $('#khjbqk').find('input[type="text"],textarea').attr('readonly', 'readonly')
                .end().find('.laydate-icon').removeAttr('onclick')
                .end().find('[type="radio"], [type="checkbox"]:not([name="relation"])').each(function (index, el) {

                    if (!!this.getAttribute('checked')) {

                        var _input = document.createElement('input');
                        _input.type = 'hidden';
                        _input.value = this.value;
                        _input.name = this.name;

                        this.parentNode.appendChild(_input);

                    }

                }).attr('disabled', 'disabled')
                .end().find('select').each(function (index, el) {
                    $(this).find('option:not([selected])').remove()
                })
                .end().find('.fnUpFileDel,.fnUpAttachBtn').remove()
                .end().find('img.fnUpFile').removeClass('fnUpFile');

            _loading.close();

        }, 2000);

    }

    //------ 证件相关的验证 start
    // var $fnCertNo = $('#fnCertNo'), // 证件号码
    var $fnCertType = $('#fnCertType'); // 证件类型

    $fnCertType.on('change', function () {
        validateCertNo($fnCertType, $fnCertNo);
        // 是否是身份证 上传图片不同
        if (this.value == 'IDENTITY_CARD') {
            showProfilePicture($('.fnIsIDCard'));
            hideProfilePicture($('.fnNotIDCard'));
        } else {
            showProfilePicture($('.fnNotIDCard'));
            hideProfilePicture($('.fnIsIDCard'));
        }
        checkKHSF.toggleBtn((this.value == 'IDENTITY_CARD') ? true : false)
        checkKHM.toggleBtn((this.value == 'IDENTITY_CARD') ? true : false)
    });

    validateCertNo($fnCertType, $fnCertNo);

    function showProfilePicture($div) {

        $div.removeClass('fn-hide')
            .find('input').removeProp('disabled');

    }

    function hideProfilePicture($div) {

        $div.addClass('fn-hide')
            .find('input').prop('disabled', 'disabled');

    }

    var $fnSpoCertType = $('#fnSpoCertType'),
        $fnSpoCertNo = $('#fnSpoCertNo');

    $fnSpoCertType.on('change', function () {
        validateCertNo($fnSpoCertType, $fnSpoCertNo);
        checkSpoCertSF.toggleBtn((this.value === 'IDENTITY_CARD') ? true : false)
        checkSpoCertM.toggleBtn((this.value === 'IDENTITY_CARD') ? true : false)
    });

    validateCertNo($fnSpoCertType, $fnSpoCertNo);
    //------ 证件相关的验证 end

    // 证件号码的验证
    /**
     * 根据条件 给对应的jQuery对象增删验证规则
     * @param  {[type]} $type [description]
     * @param  {[type]} $num  [description]
     */
    function validateCertNo($type, $num) {

        if ($type.val() == 'IDENTITY_CARD') {

            $num.rules('add', {
                checkID: true,
                messages: {
                    checkID: '请输入正确的证件号码'
                }
            });

        } else {

            $num.rules('remove', 'checkID');

        }

        if ($num.val()) {
            $num.valid();
        }

        if ($num.val()) {
            $num.trigger('change');
        }

    }

    //------ 提交、暂存 start
    function doSubmit(bool) {

        if (bool && !$form.valid()) {
            Y.alert('提示', '表单还需完善', function () {

                // 视野回到错误的地方
                $(window).scrollTop($form.find('input.error-tip,select.error-tip,textarea.error-tip').eq(0).offset().top - 150);

            });
            return;
        }

        if (bool && !!!$('#addressResult').val()) {
            // 很奇怪，地区为怎么就有问题呢
            Y.alert('提示', '所属区域未填写', function () {

                // 视野回到错误的地方
                $(window).scrollTop(400);

            });
            return;
        }

        // 证件号码是否重复
        $.when(COMMONTOOL.waitCheck()).done(function (xhr) {

            if (xhr.success && !!!xhr.type) {

                if (bool) {
                    $('input[name=status]').val('on');
                } else {
                    $('input[name=status]').val('zc'); //暂存状态
                }

                util.resetName();

                util.ajax({
                    url: $form.attr('action'),
                    data: $form.serializeJCK(),
                    success: function (res) {
                        if (res.success) {
                            Y.alert('提示', '操作成功', function () {

                                window.location.href = '/customerMg/personalCustomer/list.htm';

                            });
                        } else {
                            Y.alert('操作失败', res.message)
                        }
                    }
                });

            } else {
                Y.alert('提示', '证件号码已存在')
            }

        });

    }
    $('#fnDoSave').on('click', function () {
        doSubmit(false);
    });
    $('#fnDoSubmit').on('click', function () {
        doSubmit(true);
    });
    //------ 提交、暂存 end

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    //通过页面不同的值，判断是否添加到侧边栏
    if (document.getElementById('fnDoSave')) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'doSave',
            event: function () {
                doSubmit(false);
            }
        }]);
    }
    if (document.getElementById('fnDoSubmit')) {
        publicOPN.addOPN([{
            name: '提交',
            alias: 'doSubmit',
            event: function () {
                doSubmit(true);
            }
        }]);
    }

    if (document.getElementById('fnUserId') && document.getElementById('fnUserId').value) {
        publicOPN.addOPN([{
            name: '查看业务记录',
            alias: 'lookRecord',
            event: function () {

                util.ajax({
                    url: '/customerMg/personalCustomer/queryProject.json?userId=' + document.getElementById('fnUserId').value,
                    success: function (res) {
                        $('body').Y('Msg', {
                            type: 'alert',
                            width: '900px',
                            title: '与本担保公司的业务记录',
                            content: COMMONTOOL.creatRecord(res.list)
                        });
                    }
                });

            }
        }]);
    }

    // 查看页面
    // if (document.getElementById('fnIsView')) {
    var CHANGELIST;
    publicOPN.addOPN([{
        name: '查看修改记录',
        alias: 'lookChange',
        event: function () {
            if (!!!CHANGELIST) {
                CHANGELIST = COMMONTOOL.initChangeList();
            }

            document.getElementById('lookChangeBtn').click();

        }
    }]);
    // }

    publicOPN.init().doRender();

    //------ 侧边栏 end

    function setTelR() {

        $('#tbodyQXGS .fnMakeTel').each(function (index, el) {

            var $this = $(this);

            $this.rules('remove', 'isPhoneOrMobile');

            $this.rules('add', {
                isPhoneOrMobile: true,
                messages: {
                    isPhoneOrMobile: '请输入正确的电话号码'
                }
            });

        });

    }

    $('.fnDelLine, .fnAddLine').on('click', function () {
        setTimeout(function () {
            setTelR();
        }, 10);

    });


    setTimeout(function () {
        setTelR();
    }, 0);

    //删除个人客户旗下公司
    function deleteCompany(id) {
        if (id != null && id != "") {
            $.ajax("/customerMg/personalCustomer/deleteCompany.json", id);
        } else {
            return false;
        }

    }

    exports.publicOPN = publicOPN

});