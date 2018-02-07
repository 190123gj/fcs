define(function (require, exports, module) {
    //辅助系统-征信查询申请
    require('zyw/publicPage');
    require('input.limit');
    require('Y-msg');
    require('validate');

    //上传图片
    require('Y-htmluploadify');
    //相册
    require('Y-imageplayer');
    require('zyw/upAttachModify');

    // require('Y-entertip');

    // Y.create('EnterTip', {
    //  target: '.fnInputBankCard',
    //  mode: 'bankCard'
    // });

    var util = require('util');
    var getList = require('zyw/getList');

    var _type = util.getParam('ccc');

    (new getList()).init({
        title: '选择客户',
        ajaxUrl: '/baseDataLoad/customer.json?customerType=ENTERPRISE',
        btn: '.choosefcom',
        tpl: {
            tbody: [
                '{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.busiLicenseNo}}">{{(item.busiLicenseNo && item.busiLicenseNo.length > 18)?item.busiLicenseNo.substr(0,18)+\'\.\.\':item.busiLicenseNo}}</td>',
                '        <td title="{{item.enterpriseType}}">{{(item.enterpriseType && item.enterpriseType.length > 10)?item.enterpriseType.substr(0,10)+\'\.\.\':item.enterpriseType}}</td>',
                '        <td title="{{item.industry}}">{{(item.industry && item.industry.length > 10)?item.industry.substr(0,10)+\'\.\.\':item.industry}}</td>',
                '        <td><a class="choose" customerid="{{item.customerId}}" customername="{{item.customerName}}" num="{{item.busiLicenseNo}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: [
                '客户名称：',
                '<input class="ui-text fn-w100" type="text" name="likeCustomerName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: [
                '<th>客户名称</th>',
                '<th>营业执照号码</th>',
                '<th>企业性质</th>',
                '<th>所属行业</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function ($a) {
            window.location.href = '/projectMg/creditRefrerenceApply/addCreditRefrerenceApply.htm?customerId=' + $a.attr('customerid')
                // _getList.resetAjaxUrl('/baseDataLoad/queryProjects.json?customerType=ENTERPRISE&customerId=' + $a.attr('customerid'));
                // $('#companyName').val($a.attr('customername'));
                // $('#projectName').val('').valid();
        }
    });

    //------ 选择项目等 start
    var _getList = new getList();

    _getList.init({
        title: '项目列表',
        ajaxUrl: '/baseDataLoad/queryProjects.json?select=my&customerType=ENTERPRISE&customerId=' + $('#fnCustomerId').val(),
        btn: '.choosef',
        tpl: {
            tbody: [
                '{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" companyName="{{item.customerName}}" projectName="{{item.projectName}}" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: [
                '项目名称：',
                '<input class="ui-text fn-w160" type="text" name="projectName">',
                '&nbsp;&nbsp;',
                '企业名称：',
                '<input class="ui-text fn-w160" type="text" name="customerName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: [
                '<th width="100">项目编号</th>',
                '<th width="120">企业名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            window.location.href = '/projectMg/creditRefrerenceApply/addCreditRefrerenceApply.htm?projectCode=' + $a.attr('projectCode')
        }
    });

    //------ 选择项目等 end

    //------ 上传证件照片 start
    $('.fnUpFile').click(function () {

        var _this = $(this),
            _thisP = _this.parent();

        var _load = new util.loading();

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: util.fileType.img + '; *.pdf; *.rar; *.zip',
            fileObjName: 'UploadFile',
            onUploadStart: function ($this) {
                _load.open();
            },
            onQueueComplete: function (a, b) {
                _load.close();
            },
            onUploadSuccess: function ($this, data, resfile) {

                var res = $.parseJSON(data);
                if (res.success) {
                    if (/(.pdf|.PDF|.rar|.RAR|.zip|.ZIP|.tiff|.TIFF|.tif|.TIF)$/g.test(res.data.url)) {
                        _this.attr('src', '/styles/tmbp/img/not_img.jpg')
                        _thisP.find('.fnUpFilePDF').html('<a class="fn-green" href="' + res.data.url + '" download>下载文件</a>')
                    } else {
                        _this.attr('src', res.data.url)
                        _thisP.find('.fnUpFilePDF').html('')
                    }
                    _thisP.find('input.fnUpFileInput').val(res.data.url).trigger('blur');
                } else {
                    Y.alert('提示', '上传失败');
                }

            },
            renderTo: 'body'
        });

    });
    //------ 上传证件照片 end
    //
    //
    //
    //------ 点击查看大图 start
    var $fnViewImg = $('.fnViewImg'),
        fnViewImgArr = [];
    $fnViewImg.each(function (index, el) {
        var _this = $(this);
        fnViewImgArr.push({
            src: _this.attr('src'),
            desc: _this.attr('alt')
        });
    });
    $fnViewImg.on('click', function () {
        Y.create('ImagePlayer', {
            imgs: fnViewImgArr,
            index: $(this).index('.fnViewImg'),
            scaleLowerLimit: 0.1,
            loop: false,
            fullAble: false,
            firstTip: '这是第一张了',
            firstTip: '这是最后一张了'
        }).show();
    });
    //------ 点击查看大图 end

    //------ 是否三证合一 start
    $('.fnIsThreeRadio').on('click', function () {

        if (this.value == 'IS') {
            $('.fnIsThree').removeClass('fn-hide').find('input').removeProp('disabled')
            $('.fnNotThree').addClass('fn-hide').find('input').prop('disabled', 'disabled')
        } else {
            $('.fnIsThree').addClass('fn-hide').find('input').prop('disabled', 'disabled')
            $('.fnNotThree').removeClass('fn-hide').find('input').removeProp('disabled')
        }

    });
    //------ 是否三证合一 end


    //------ 是否是公司客户 start

    $('.fnIsCustomer').on('click', function () {

        if (this.value == 'IS' && $('.fnQueryPurpose:checked').val() == 'BEFOREWARD_EXAMINE') {

            $('#customerApply').find('.m-label').html('<span class="m-required">*</span>客户申请书：')
                .end().find('.fnUpFileInput').addClass('fnInput');

            $("input[name='customerApplyUrl']").rules("add",'required')

        } else {

            $('#customerApply').find('.m-label').html('客户申请书：')
                .end().find('.fnUpFileInput').removeClass('fnInput')

            $("input[name='customerApplyUrl']").rules("remove",'required')
        }

    })

    $('.fnQueryPurpose').on('click', function () {

        if (this.value == 'BEFOREWARD_EXAMINE' && $('.fnIsCustomer:checked').val() == 'IS') {
            $('#customerApply').find('.m-label').html('<span class="m-required">*</span>客户申请书：')
                .end().find('.fnUpFileInput').addClass('fnInput')
            $("input[name='customerApplyUrl']").rules("add",'required');
        } else {
            $('#customerApply').find('.m-label').html('客户申请书：')
                .end().find('.fnUpFileInput').removeClass('fnInput')
            $("input[name='customerApplyUrl']").rules("remove",'required')
        }

    })

    //------ 是否是公司客户 end

    //------ 提交表单 start
    var $busiLicenseNo = $('#busiLicenseNo'),
        $form = $('#form');

    // $.validator.addMethod('loanCardNo', function(value, element, params) {

    //  if (value.length == 16 || value.length == 18 || value.length == 0) {
    //      return true;
    //  } else {
    //      return false;
    //  }

    // }, '请输入16或18位数字');


    $form.validate({
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

        },
        submitHandler: function (form) {

            //证件号码是否对
            // if ($busiLicenseNo.val().length != 18 && $busiLicenseNo.val().length != 15) {
            //  Y.alert('提示', '请输入正确的营业执照号码', function() {
            //      $busiLicenseNo.focus();
            //  });
            //  return;
            // }

            //检查附件
            var _isPass = true,
                _isPassEq;

            var $fnInput = $('.fnInput')

            $fnInput.each(function (index, el) {

                if (!!!el.value) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                }

            });

            if (!_isPass) {
                Y.alert('提示', '第' + (_isPassEq + 1) + '个必须上传的附件还未上传', function () {
                    $(window).scrollTop($fnInput.eq(_isPassEq).prev().offset().top - 200)
                });
                return;
            }

            util.ajax({
                url: '/projectMg/creditRefrerenceApply/saveCreditRefrerenceApply.htm',
                data: $form.serialize(),
                success: function (res) {
                    if (res.success) {

                        util.postAudit({
                            formId: res.formId
                        }, function () {
                            window.location.href = '/projectMg/creditRefrerenceApply/list.htm';
                        })

                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });


        },
        rules: {
            companyName: {
                required: true
            },
            purpose: {
                required: true
            },
            socialUnityCreditCode: {
                required: true
            },
            busiLicenseNo: {
                required: true
            },
            orgCode: {
                required: true
            },
            busiScope:{
                maxlength:499
            },
            // customerApplyUrl: {
            //     required: false
            // }
            // loanCardNo: {
            //  loanCardNo: true
            // }
        },
        messages: {
            companyName: {
                required: '必填'
            },
            purpose: {
                required: '必填'
            },
            socialUnityCreditCode: {
                required: '必填'
            },
            busiLicenseNo: {
                required: '必填'
            },
            orgCode: {
                required: '必填'
            },
            busiScope:{
                maxlength:'字数超过限制'
            }
            // customerApplyUrl: {
            //     required: '必填'
            // }

                // loanCardNo: {
            //  loanCardNo: '请输入16或18位数字'
            // }
        }
    });

    $('body').click(function () {
        var obj = $('input[name="busiScope"]');
        obj.each(function () {
            $(this).attr('maxlength',500);
            $(this).prop('maxlength',500);
        })
    })

    //------ 提交表单 start


    //------ 列表的操作 start
    // $('#list').on('click', '.fnUploadReport', function() {

    //  var _thisTr = $(this).parents('tr');

    //  //上传
    //  Y.create('HtmlUploadify', {
    //      key: 'up1',
    //      uploader: '/upload/imagesUpload.htm',
    //      multi: false,
    //      auto: true,
    //      addAble: false,
    //      fileTypeExts: util.fileType.all,
    //      fileObjName: 'UploadFile',
    //      onQueueComplete: function(a, b) {},
    //      onUploadSuccess: function($this, data, resfile) {
    //          var res = $.parseJSON(data);
    //          if (res.success) {

    //              //把附件url关联项目
    //              postReport2Project(_thisTr.attr('id'), res.data.url);

    //          } else {
    //              Y.alert('提示', res.message);
    //          }
    //      },
    //      renderTo: 'body'
    //  });

    // });

    // function postReport2Project(id, url) {

    //  util.ajax({
    //      url: '/projectMg/creditRefrerenceApply/uploadCreditReportCreditRefrerenceApply.htm',
    //      data: {
    //          id: id,
    //          creditReport: url
    //      },
    //      success: function(res) {
    //          if (res.success) {
    //              Y.alert('提示', '已上传', function() {
    //                  window.location.reload(true);
    //              });
    //          } else {
    //              Y.alert('提示', res.message);
    //          }
    //      }
    //  });

    // }
    //------ 列表的操作 end

    //------ 上传征信报告 start
    var $upForm = $('#upForm');
    $upForm.on('click', '#fnSubmit', function () {

        var _isPass = true;

        $('.fnInput').each(function (index, el) {

            if (!!!this.value.replace(/\s/g, '')) {
                _isPass = false;
                return false;
            }

        });

        if (!_isPass) {

            Y.alert('提示', '请填写完整必填项');
            return;

        }

        Y.confirm('提示', '征信报告只能上传一次，请确保所有文件均上传完成后再点击确定', function (opn) {

            if (opn == 'yes') {

                util.ajax({
                    url: $upForm.attr('action'),
                    data: $upForm.serialize(),
                    success: function (res) {

                        if (res.success) {

                            Y.alert('提示', '操作成功', function () {
                                window.location.href = '/projectMg/creditRefrerenceApply/list.htm';
                            });

                        } else {
                            Y.alert('操作失败', res.message);
                        }

                    }
                });

            }

        });

    });
    //------ 上传征信报告 end

    //------ 审核 start
    if (document.getElementById('auditForm')) {
        var auditProject = require('zyw/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    }
    //------ 审核 end


    //------ 侧边栏 start
    if (!document.getElementById('fnListSearchForm')) {
        (new(require('zyw/publicOPN'))()).init().doRender();
    }
    //------ 侧边栏 start

    //_________________征信信息不能下载了
    $('.noDownload [title="点击下载"]').removeAttr('download target title href').css('cursor','text');
});