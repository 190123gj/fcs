define(function(require, exports, module) {

    var hintPopup = require('zyw/hintPopup');
    require('zyw/publicPage');

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.addOPN([]).init().doRender();
    //------ 侧边栏 end

    $('.pageSelect').change(function(event) {
        $("#fnPageSize").val($(this).val())
        $('#fnListSearchBtn').click();
    });

    // // 分页公共操作
    // var util = require('util');

    // //删等操作
    // util.listOPN({
    //  start: {
    //      url: '/fundMg/bankMessage/list.htm?bankId=$!item.bankId&status=NORMAL',
    //      message: '已启用',
    //      opn: '启用'
    //  },
    //  stop: {
    //      url: '/fundMg/bankMessage/list.htm?bankId=$!item.bankId&status=BLOCK_UP',
    //      message: '已停用',
    //      opn: '停用'
    //  }
    // }, 'bankId');

    $('#list').on('click', '.fnOPNnew', function() {

        var $this, opn, code;

        $this = $(this);
        opn = $this.attr('opn');
        code = $this.attr('code');
        message = $this.attr('message');

        Y.confirm('信息提醒', '您确定要' + opn + '该银行账户' + '？', function(opn) {
            if (opn == 'yes') {
                //var _data = {};
                $.ajax({
                    url: '/fundMg/bankMessage/changeStatus.htm?bankId=' + code,
                    // data: {
                    //  _SYSNAME: _this.attr('sysname') || '',
                    // },
                    success: function(res) {
                        if (res.success) {
                            Y.alert('消息提醒', message);
                            window.location.reload(true);
                        } else {
                            Y.alert('消息提醒', res.message);
                        }
                    }
                });
            }
        });
    });

    //导出

    $('#dataExport').click(function(event) {
        window.location.href = '/fundMg/report/cardDownLoad.htm?' + $('#fnListSearchForm').serialize();

    });


    //上传
    require('Y-htmluploadify');
    //导入
    $('#dataImport').click(function(event) {

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/fundMg/bankMessage/excelParse.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function(a, b) {},
            onUploadSuccess: function($this, data, resfile) {

                var jsonData = JSON.parse(data);


                hintPopup(jsonData.message, function() {

                    if (jsonData.success) {

                        // window.location.href = window.location.href;
                        $('#fnListSearchBtn').click();

                    }

                });

            }

        });

    });

    //搜索框时间限制
    $('body').on('blur', '.fnListSearchDateS', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateE');

        $input.attr('onclick', 'laydate({min: "' + this.value + '",choose:function(){$("#time").val("")}})');

    }).on('blur', '.fnListSearchDateE', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateS');

        $input.attr('onclick', 'laydate({max: "' + this.value + '",choose:function(){$("#time").val("")}})');

    }).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');


    var yearsTime = require('zyw/yearsTime');
    $('#time').click(function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: '#time',
            callback: function() {
                $('.fnListSearchDateS,.fnListSearchDateE').val('');
            },
        });
    });

   // if (!$('.fnListSearchDateS').val() && !$('.fnListSearchDateE').val()&&!$("#time").val()) {
   //     var d = new Date();
   //     $("#time").val(d.getFullYear() + '-' + (d.getMonth() + 1));
//
   // }

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
    $('.creditorBtn').on('click', function() {

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

        singleSelectUserDialog.init(function(relObj) {

            $fnOrgId.val(relObj.orgId);
            $fnOrgName.val(relObj.orgName);

        });

    });
    //---选择部门 end
    $("#fnListExport").click(function() {
        var url = $(this).attr("exportUrl");
        url = url + "?" + $("#form").serialize();
        window.location = url;
    });
    var yearsTime = require('zyw/yearsTime');
    $('body').on('focus', '.birth', function(event) {
        var yearsTimeFirst = new yearsTime({
            elem: this,
            format: 'YYYY-MM',
            // callback: function(_this, _time) {
            //     _this.val(_time[0] + '-' + ((_time[1].length == 1) ? '0' + _time[1] : _time[1]));
            // }
        });
    });

})