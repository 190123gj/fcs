define(function (require, exports, module) {

    $('#printBtn').click(function (event) {
        window.print();
    });

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.addOPN([]).init().doRender();
    //------ 侧边栏 end
    require('zyw/publicPage');
    //---选择部门 start
    // var $fnOrgName = $('#fnOrgName'),
    //  $fnOrgId = $('#fnOrgId');

    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
    // 初始化弹出层
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true', {
        'title': '部门组织',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'arrys': [], //[{id:'id',name='name'}];
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });
    // 添加选择后的回调，以及显示弹出层
    $('.creditorBtn').on('click', function () {

        //这里也可以更新已选择机构
        var _arr = [],
            _this = $(this),
            $fnOrgName = _this.siblings('.fnOrgName'),
            $fnOrgId = _this.siblings('.fnOrgId'),
            _nameArr = $fnOrgName.val().split(','),
            _idArr = $fnOrgId.val().split(',');

        for (var i = 0; i < _nameArr.length; i++) {
            if (_nameArr[i]) {
                _arr.push({
                    id: _idArr[i],
                    name: _nameArr[i]
                });
            }
        }

        singleSelectUserDialog.obj.arrys = _arr;

        singleSelectUserDialog.init(function (relObj) {

            $fnOrgId.val(relObj.orgId);
            $fnOrgName.val(relObj.orgName);

        });

    });
    //---选择部门 end


    //选人
    require('zyw/chooseLoanPurpose');

    //现场调查人员弹窗
    var scope = "{type:\"system\",value:\"all\"}";
    var selectUsers = {
        selectUserIds: $('#handleNameID').val(),
        selectUserNames: $('#handleName').val(),
        selectUserAccs: $('#handleAcc').val()
    }
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
    var singleSelectUserDialog1 = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
        'title': '单选选用户',
        'width': 850,
        'height': 460,
        'scope': scope,
        'selectUsers': selectUsers,
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });

    // window.frameElement.dialog=singleSelectUserDialog;
    $('#handleNameBtn').on('click', function () {

        selectUsers.selectUserIds = $('#handleNameID').val();
        selectUsers.selectUserNames = $('#handleName').val();
        selectUsers.selectUserAccs = $('#handleAcc').val()

        singleSelectUserDialog1.init(function (relObj) {

            for (var i = 0; i < relObj.userIds.length; i++) {
                $('#handleNameID').val(relObj.userIds).blur();
                $('#handleName').val(relObj.fullnames).blur();
                $('#handleAcc').val(relObj.accounts).blur();
            }


        });
    });
    $("#fnListExport").click(function () {
        var url = $(this).attr("exportUrl");
        url = url + "?" + $("#fnListSearchForm").serialize();
        window.location = url;
    });
    var yearsTime = require('zyw/yearsTime');
    $('body').on('focus', '.birth', function (event) {
        var yearsTimeFirst = new yearsTime({
            elem: this,
            format: 'YYYY-MM',
            // callback: function(_this, _time) {
            //     _this.val(_time[0] + '-' + ((_time[1].length == 1) ? '0' + _time[1] : _time[1]));
            // }
        });
    });

    $('.screen a').click(function (event) {
        $this = $(this);
        $this.nextAll('input').val($this.attr('code'));
        $('#fnListSearchBtn').click();
    });
})