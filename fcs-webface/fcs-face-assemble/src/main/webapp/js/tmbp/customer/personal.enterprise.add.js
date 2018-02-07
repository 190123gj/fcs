define(function (require, exports, module) {

    require('input.limit');
    require('zyw/opsLine');

    require('zyw/chooseRegion');

    require('Y-imageplayer');
    require('Y-htmluploadify');

    require('Y-entertip');

    var getList = require('zyw/getList');

    var util = require('util');
    var isPerson = ($('#fnCustomerType').val() == 'P') ? true : false;

    util.resetName();

    $('body').on('click', '.fnAddLine,.fnDelLine', function () {
        setTimeout(function () {
            util.resetName();
        }, 0);
    });

    // 公共签报
    require('../project/assistsys.smy.fillReviewChange')

    // tab
    var $step = $('#stepCust'),
        $window = $(window),
        $div = $('<div class="fnChangeApplyHidden"></div>').height($step.outerHeight()).hide(),
        HALFWINDOWH = $window.height() / 2,
        TITLEH = [];

    $step.after($div);

    $step.outerWidth($step.outerWidth());

    // 缓存高度
    $('.fnStep').each(function (index, el) {

        TITLEH.push({
            key: el.id,
            top: $(el).offset().top
        });
    });

    function whichNavActive(scrollTop) {

        scrollTop = scrollTop + HALFWINDOWH / 2 * 1;

        $.each(TITLEH, function (i, o) {

            if (i == 0) {
                if (scrollTop < TITLEH[1].top) {
                    activeItem(o.key);
                    return false;
                }
            } else if (i == TITLEH.length - 1) {
                if (scrollTop > o.top) {
                    activeItem(o.key);
                    return false;
                }
            } else {
                if (scrollTop < TITLEH[(i + 1)].top && scrollTop > o.top) {
                    activeItem(o.key);
                    return false;
                }
            }

        });

        function activeItem(key) {
            var $keyItem = $step.find('li.item[ftf="' + key + '"]');
            if (!$keyItem.hasClass('active')) {
                $step.find('li.item').removeClass('active');
                $keyItem.addClass('active');
            }
        }

    }

    if ($window.scrollTop() > 100) {
        $step.addClass('step-fiexd');
        $div.show();
        whichNavActive($window.scrollTop());
    }

    $window.scroll(function (event) {

        $step = $('#stepCust')

        if ($window.scrollTop() > 100) {
            if (!$step.hasClass('step-fiexd')) {
                $step.addClass('step-fiexd');
                $div.show();
            }
        } else {
            if ($step.hasClass('step-fiexd')) {
                $step.removeClass('step-fiexd');
                $div.hide();
            }
        }

        whichNavActive($window.scrollTop());

    });

    $('body').on('click','#stepCust li.item',function () {

        var self = this;

        $window.scrollTop($('#' + self.getAttribute('ftf')).offset().top - $(self).parents('#stepCust').outerHeight());
        setTimeout(function () {
            $(self).addClass('active').siblings().removeClass('active');
        }, 150);

    })

    // $step.on('click', 'li.item', function () {
    //
    //     var self = this;
    //
    //     $window.scrollTop($('#' + self.getAttribute('ftf')).offset().top - $step.outerHeight());
    //     setTimeout(function () {
    //         $(self).addClass('active').siblings().removeClass('active');
    //     }, 150);
    //
    // });

    //------ 签报时的诸多限制 start
    var ISCHANGE = document.getElementById('fnChangeForm') ? true : false; // 是否是签报页面
    if (ISCHANGE) {
        // 不允许修改项
        $('.fnChangeReadOnly').prop('readonly', 'readonly').each(function (index, el) {
            //select
            var $this = $(this),
                $options = $this.find('option');

            if (!!$options.length) {

                // $options.each(function(index, el) {
                //  if (el.value != $this.val()) {
                //      $options.eq(index).attr('disabled', 'disabled');
                //  }
                // });
                $this.prop('disabled', 'disabled').after('<input type="hidden" value="' + $this.val() + '" name="' + $this[0].name + '">');
                $this.attr('name', '');

            }

        });
        // 1)   客户信息签报时增加选择项：三证合一更新证照号码，如果勾选了该选项，则系统允许修改营业执照号码为三证合一后的号码。
        // 选择三证合一后，可以修改 营业执照号码
        // 取消三证合一，还原营业执照号码
        (function () {

            var $isThreeBtn = $('#isThreeBtn')
            var $fnCertNo = $('#fnCertNo')
            var oldCertNo = $fnCertNo.val()
            if ($isThreeBtn.prop('checked')) {
                $fnCertNo.removeProp('readonly')
            }
            $isThreeBtn.on('click', function () {

                if (this.checked) {
                    $fnCertNo.removeProp('readonly')
                } else {
                    $fnCertNo.prop('readonly', 'readonly').val(oldCertNo)
                }

            })

        })();
    }
    //------ 签报时的诸多限制 end


    //------ 上传证件照片 start
    $('body').on('click', '.fnUpFile', function () {

        var _this = $(this),
            _thisP = _this.parent();

        var _load = new util.loading();

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: util.fileType.img + '; *.pdf',
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
                    if (/(.pdf|.PDF|.tiff|.TIFF|.tif|.TIF)$/g.test(res.data.url)) {
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

    $('.fnUpFileDel').on('click', function () {
        var _thisP = $(this).parent();
        _thisP.find('.fnUpFile').attr('src', '/styles/tmbp/img/project/apply_upfile.jpg');
        _thisP.find('.fnUpFileInput').val('');
        _thisP.find('.fnUpFilePDF').html('');
    });
    //放大图片
    var $applyUpfileImg = $('.apply-upfile-img:not(.fnUpFile, .noPic)'),
        applyUpfileImgArr = [];

    $applyUpfileImg.each(function (index, el) {

        applyUpfileImgArr.push({
            src: el.src,
            desc: el.alt
        });

    });

    $applyUpfileImg.on('click', function () {

        Y.create('ImagePlayer', {
            imgs: applyUpfileImgArr,
            index: $(this).index('.apply-upfile-img:not(.fnUpFile, .noPic)')
        }).show();

    });
    //------ 上传证件照片 end

    // 输入银行卡的提示
    $('.fnInputBankCard').each(function (i, el) {

        Y.create('EnterTip', {
            target: '.fnInputBankCard' + i,
            mode: 'bankCard'
        });

    });

    // 选择渠道 start

    var $fnChannelList = $('#fnChannelList'),
        $fnChannelInput = $('#fnChannelInput'),
        $fnChannelId = $('#fnChannelId'),
        $fnChannelName = $('#fnChannelName');


    $('body').on('change','#fnResoursFrom',function () {

        $fnChannelList = $('#fnChannelList'),
            $fnChannelInput = $('#fnChannelInput'),
            $fnChannelId = $('#fnChannelId'),
            $fnChannelName = $('#fnChannelName')

        if (this.value == 'QDTJ') {
            $('#fnIsChannel').removeClass('fn-hide').find('select, input').removeProp('disabled').valid();
        } else {
            $('#fnIsChannel').addClass('fn-hide').find('select, input').prop('disabled', 'disabled').valid();
        }
    })

    // $('#fnResoursFrom').on('change', function () {
    //
    //     if (this.value == 'QDTJ') {
    //         $('#fnIsChannel').removeClass('fn-hide').find('select, input').removeProp('disabled').valid();
    //     } else {
    //         $('#fnIsChannel').addClass('fn-hide').find('select, input').prop('disabled', 'disabled').valid();
    //     }
    //
    // });

    setTimeout(function () {

        $('#fnResoursFrom').trigger('change');

    }, 0);

    var $fnChannelList = $('#fnChannelList'),
        $fnChannelInput = $('#fnChannelInput'),
        $fnChannelId = $('#fnChannelId'),
        $fnChannelName = $('#fnChannelName');

    $('body').on('change','#fnChannelList',function () {

        $('#fnChannelId').val(this.value)

        $('#fnChannelName').val($(this).find('[value="' + this.value + '"]').html()).valid()

    })

    // $fnChannelList.on('change', function () {
    //
    //     $fnChannelId.val(this.value)
    //     $fnChannelName.val($(this).find('[value="' + this.value + '"]').html()).valid()
    //
    // });

    $('body').on('change','#fnChannelInput',function () {

        $('#fnChannelId').val('')
        $('#fnChannelName').val(this.value.replace(/\s/g, '')).valid()

    })

    $('body').on('change','#fnChannelInput',function () {
        $fnChannelId.val('')
        $fnChannelName.val(this.value.replace(/\s/g, '')).valid()
    })

    $('body').on('change','#fnChannelType', function () {

        var _val = this.value;

        getChannelList(_val, function () {

            $fnChannelId.val('').valid();
            $fnChannelName.val('');

        });

    });

    // channelId 为 0 表示没有选择
    if (!!+$fnChannelId.val()) {

        getChannelList($('#fnChannelType').val(), function () {

            $fnChannelList.find('[value="' + $fnChannelId.val() + '"]').attr('selected', 'selected');

        });

    }

    function getChannelList(type, cb) {

        if (type === 'QT') {

            $fnChannelInput.removeClass('fn-hide').val('').trigger('change')
            $fnChannelList.addClass('fn-hide')

        } else {

            $fnChannelList.removeClass('fn-hide')
            $fnChannelInput.addClass('fn-hide')

            util.ajax({
                url: '/customerMg/customer/selectChannal.json?status=on&channelType=' + type,
                data: {
                    pageSize: 999
                },
                success: function (res) {

                    var _html = '<option value="">请选择</option>';
                    $.each(res.list, function (index, o) {

                        _html += '<option value="' + o.id + '">' + o.channelName + '</option>';

                    });

                    $fnChannelList.html(_html);

                    if (cb) {
                        cb();
                    }

                }
            });


        }

    }

    // 选择渠道 end

    // 查询客户的相关记录 start
    var NEEDCHECK = false,
        $fnCertNo = $('#fnCertNo'),
        OLDNO = $fnCertNo.val();

    function creatHistory(arr) {

        var _tbody = '',
            _thead = '<thead><tr><th>序号</th><th>项目名称</th><th>启动时间</th><th>项目状态</th><th>最新评价</th></tr></thead>';

        $.each(arr, function (i, obj) {

            _tbody += '<tr><td>' + (i + 1) + '</td><td>' + obj.name + '</td><td>' + obj.name + '</td><td>' + obj.name + '</td><td>' + obj.name + '</td></tr>';

        });

        return '<p class="fn-ml20 fn-mr20 fn-mb20 fn-fs16 fn-tal fn-f0">该客户是您的历史客户，参与的项目基本情况如下：</p><div style="height: auto; max-height: 358px;" class="fn-ml20 fn-mr20 apply-org-form-in"><table class="m-table m-table-list fn-tac">' + _thead + '<tbody>' + _tbody + '</tbody></table></div>';

    }
    // 查询用户是否有关联数据
    function queryUserHasxx(data) {

        if (!NEEDCHECK) {
            return;
        }

        var _middle = isPerson ? 'personalCustomer' : 'companyCustomer';

        // 暂无数据支持
        //console.log(data)
        // return;
        util.ajax({
            url: '/customerMg/' + _middle + '/validata.json',
            data: data,
            success: function (res) {

                if (res.success) {

                    // res.memo "type字段：pub :公海 ，per：自己的，other:别人的"

                    // 历史客户、历史简介客户

                    switch (res.type) {

                        // 公海客户、别人的客户，要么回到新增，要么回到列表
                        case 'pub':
                        case 'other':
                            $('body').Y('Msg', {
                                type: 'confirm',
                                content: res.message,
                                icon: '',
                                yesText: '添加其他客户',
                                noText: '取消',
                                callback: function (opn) {

                                    if (opn == 'yes') {
                                        window.location.href = '/customerMg/' + _middle + '/add.htm'
                                    } else {
                                        window.location.href = '/customerMg/' + _middle + '/list.htm'
                                    }

                                }
                            });
                            break;
                            // 与自己有关联的客户，要么跳转到新增，带出以前的信息，要么回到列表
                        case 'per':
                            // 自己的历史客户，直接false了？
                            $('body').Y('Msg', {
                                type: 'confirm',
                                width: '600px',
                                content: creatHistory([{
                                    name: 'ceshioshujku 23'
                                }]),
                                icon: '',
                                yesText: '修改信息',
                                noText: '取消',
                                callback: function (opn) {

                                    if (opn == 'yes') {
                                        window.location.href = '/customerMg/' + _middle + '/info.htm?userId=' + res.userId + '&isUpdate=true'
                                    } else {
                                        window.location.href = '/customerMg/' + _middle + '/list.htm'
                                    }

                                }
                            });
                            break;
                        default:
                            break;

                    }


                } else {

                    Y.alert('提示', res.message)

                }

            }
        });

    }
    // 新增时需要验证 客户名称是否有关联
    var timeOutAjax,
        $fnCustomerName = $('#fnCustomerName');

    $('#fnCustomerName,#fnCertNo').on('change', function () {

        if (this.name == 'certNo' || this.name == 'busiLicenseNo') {
            if (this.value != OLDNO) {
                NEEDCHECK = true;
            } else {
                NEEDCHECK = false;
            }
        }

        clearTimeout(timeOutAjax);

        // 异步的验证唯一性

        if ($fnCustomerName.valid() && $fnCertNo.valid()) {

            timeOutAjax = setTimeout(function () {

                // 查询当前用户名是否有关联数据
                var _data = {};

                _data[$fnCustomerName.attr('name')] = $fnCustomerName.val();
                _data[$fnCertNo.attr('name')] = $fnCertNo.val();

                queryUserHasxx(_data);

            }, 100);

        }


    });
    // 查询客户的相关记录 end

    // 验证 证件号码唯一性 start
    // 验证的延迟对象
    var waitCheck = function () {　　

        // 证件号码是否重复
        // 
        // 如果已有证件号码，却未做修改，无需验证
        // 
        var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象

        var _middle = isPerson ? 'personalCustomer' : 'companyCustomer',
            _data = {},
            DOMNO = document.getElementById('fnCertNo');

        _data[DOMNO.name] = encodeURIComponent(DOMNO.value);

        if (NEEDCHECK) {

            util.ajax({
                url: '/customerMg/' + _middle + '/validata.json',
                data: _data,
                success: function (xhr) {

                    dtd.resolve(xhr);

                }
            });

        } else {

            return dtd.resolve({
                success: true,
                type: ''
            });

        }

        return dtd.promise(); // 返回promise对象
    };
    // 验证 证件号码唯一性 end

    var REQUIRERULES = { // 验证规则
        rules: {},
        messages: {}
    };

    // 必填
    $('.fnRequired').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            required: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            required: '必填'
        });

    });


    // 字母
    $('.fnLetter').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            checkAZ: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            checkAZ: '请输入英文字母'
        });

    });

    // 电话号码
    $('.fnIsPhoneOrMobile').each(function (index, el) {
        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            isPhoneOrMobile: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            isPhoneOrMobile: '请输入正确的电话号码'
        });
    });

    // email
    $('.fnEmail').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            email: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            email: '请输入正确的邮箱地址'
        });

    });

    // 中文
    $('.fnIsZh').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            checkZh: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            checkZh: '请输入中文'
        });

    });

    // 邮编
    $('.fnIsZipCode').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            checkZhZipCode: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            checkZhZipCode: '请输入6位数字格式'
        });

    });

    // 银行卡
    $('.fnIsBankCard').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            checkZhBankCard: true,
            number: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            checkZhBankCard: '请输入18或21位数字',
            number: '请输入数字'
        });

    });

    // 贷款卡号
    $('.fnIsLoanCard').each(function (index, el) {

        util.ObjAddkey(REQUIRERULES.rules, el.name, {
            isCardNo: true,
            number: true
        });
        util.ObjAddkey(REQUIRERULES.messages, el.name, {
            isCardNo: '请输入16或18位数字',
            number: '请输入数字'
        });

    });

    function getTime(ts) {

        if (!!!ts) {
            return '-';
        }

        var _timer = new util.getNowTime(ts);

        return _timer.YY + '-' + _timer.MM + '-' + _timer.DD;

    };

    function creatRecord(arr) {

        var _tbody = '',
            _thead = '<thead><tr><th width="50px">序号</th><th width="180px">项目编号</th><th width="150px">业务品种</th><th width="150px">项目金额(元)</th><th width="100px">授信期限</th><th width="150px">授信起始日期</th><th width="150px">授信截止日期</th><th width="80px">操作</th></tr></thead>';

        $.each(arr, function (i, o) {

            _tbody += ['<tr>',
                '<td>' + (i + 1) + '</td>',
                '<td>' + o.projectCode + '</td>',
                '<td>' + o.busiTypeName + '</td>',
                '<td>' + o.amount.standardString + '</td>',
                '<td>' + o.timeLimitView + '</td>',
                '<td>' + getTime(o.startTime) + '</td>',
                '<td>' + getTime(o.endTime) + '</td>',
                '<td><a target="_blank" href="/projectMg/viewProjectAllMessage.htm?projectCode=' + o.projectCode + '">查看详情</a></td>',
                '</tr>'
            ].join('');

        });

        if (arr.length == 0) {
            _tbody = '<tr><td colspan="8" class="fn-tac">暂无数据</td></tr>'
        }

        return '<div style="max-height: 500px; overflow-y: auto;"><table class="m-table m-table-list fn-tac">' + _thead + '<tbody>' + _tbody + '</tbody></table></div>';

    };

    function initChangeList() {

        var someObj = new getList();
        someObj.init({
            // width: '90%',
            title: '查看修改记录',
            ajaxUrl: '/customerMg/customer/change/list.json?customerUserId=' + document.getElementById('fnUserId').value,
            btn: '#lookChangeBtn',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.changeType}}</td>',
                    // '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 10)?item.projectName.substr(0,10)+\'\.\.\':item.projectName}}</td>',
                    // '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>',
                    // '        <td>{{item.busiTypeName}}</td>',
                    '        <td>{{item.operName}}</td>',
                    '        <td>{{=getYYYMMDD(item.rawUpdateTime)}}</td>',
                    '        <td><a href="/customerMg/customer/change/info.htm?changeId={{item.changeId}}" target="_blank">查看详情</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '修改来源：',
                    '<select class="ui-select" name="changeType"><option value="">全部</option><option value="LX">立项</option><option value="JD">尽调</option><option value="QB">签报</option><option value="ZX">征信查询</option></select>',
                    // '&nbsp;&nbsp;',
                    // '修改字段：',
                    // '<input class="ui-text fn-w160" type="text" name="">',
                    '&nbsp;&nbsp;',
                    '修改人：',
                    '<input class="ui-text fn-w160" type="text" name="operName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: ['<th width="80px">修改来源</th>',
                    // '<th width="120px">修改字段</th>',
                    // '<th>修改前</th>',
                    // '<th>修改后</th>',
                    '<th width="100px">修改人</th>',
                    '<th width="120px">信息更新时间</th>',
                    '<th width="50px">操作</th>'
                ].join(''),
                item: 6
            }
        });
        $('body').append('<div id="lookChangeBtn"></div>');
        return someObj;

    }

    module.exports = {
        REQUIRERULES: REQUIRERULES,
        creatRecord: creatRecord,
        waitCheck: waitCheck,
        initChangeList: initChangeList
    };

});