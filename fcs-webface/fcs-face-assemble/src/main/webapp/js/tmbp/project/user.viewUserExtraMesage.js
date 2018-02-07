define(function (require, exports, module) {
    //辅助系统-会议管理-会议列表
    require('zyw/publicPage');
    require('Y-msg');

    var $userName = $('#userName')
    var $userId = $('#userId')

    $userName.on('change', function () {

        var userId = $userId.val();

        if (!!!userId) {
            return;
        }

        $.ajax({
            url: '/systemMg/userExtra/getUserExtraMessage.json',
            data: {
                userId: userId
            },
            success: function (res) {
                if (res.success) {
                    //Y.alert('提示', '已保存!');
                    if (res.userExtraInfo) {
                        // var key = (res.userExtraInfo || {}).userJudgeKey || '';
                        // alert(key);
                        $('input[name=userJudgeKey]').val(res.userExtraInfo.userJudgeKey);
                        $('input[name=oaSystemId]').val(res.userExtraInfo.oaSystemId);
                        $('input[name=bankName]').val(res.userExtraInfo.bankName);
                        $('input[name=bankAccountNo]').val(res.userExtraInfo.bankAccountNo);
                    }
                    $('input[name=userAccount]').val(res.sysUser.account);

                } else {
                    Y.alert('提示', res.message);
                }
            }
        });

    })

    var $_form = $('#addForm');
    var $fnInput = $('.fnInput');

    $('#submit').click(function () {

        //alert(2);
        // 验证key不能为空！
        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _isPass = true,
            _isPassEq;
        $fnInput.each(function (index, el) {

            if (!!!el.value.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }

        });

        if (!_isPass) {
            Y.alert('提示', '请填写完整表单', function () {
                $fnInput.eq(_isPassEq).focus();
            });
            _this.removeClass('ing');
            return;
        }


        $.ajax({
            url: '/systemMg/userExtra/saveUserExtraMessage.json',
            data: $_form.serialize(),
            success: function (res) {
                if (res.success) {
                    //var key = res.userExtraInfo.userJudgeKey;
                    //alert(key);
                    //$('input[name=userJudgeKey]').val(key);
                    //_this.removeClass('ing');
                    Y.alert('提示', '操作成功', function () {
                        window.location.href = '/systemMg/userExtra/listUser.htm';
                    });
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });
    });

    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    // 初始化弹出层
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
        'title': '人员',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'selectUsers': {
            selectUserIds: '', //已选id,多用户用,隔开
            selectUserNames: '' //已选Name,多用户用,隔开
        },
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });
    // 添加选择后的回调，以及显示弹出层
    $(".findUser").on('click', function () {

        singleSelectUserDialog.init(function (relObj) {

            $userId.val(relObj.userIds);
            $userName.val(relObj.fullnames).trigger('change');

        });

    });

});