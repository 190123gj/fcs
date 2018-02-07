/**
 * 上传附件 附件信息从资产那边获取
 * 附件上传二选一， 文字或附件
 * 如果资产id为0或资产信息为空，普通上传
 *
 * 批量操作 体验与单个保持一致
 * 是否落实、落实日期比如覆盖，附件看情况
 * 
 * 2017.01.10 保证类型的附件信息不必填
 * 保证类型不会有关联资产信息
 *
 * 
 */
define(function (require, exports, module) {
    // 项目管理 > 授信前管理 > 授信条件落实情况信息维护
    require('input.limit');
    require('Y-msg');
    require('validate');

    var template = require('arttemplate');

    var ATTACH_COMMON = require('./bfcg.ctcd.attach');

    var util = require('util');
    var getList = require('zyw/getList');
    var $body = $('body');
    var template = require('arttemplate');
    var $fnCreditList = $('#fnCreditList');

    if (!!util.browserType() && util.browserType() < 10) { //IE9及以下不支持多选，加载单选上传
        require.async('tmbp/upAttachModify'); //上传
    } else {
        require.async('tmbp/upAttachModifyNew'); //上传
    }

    template.helper('getTimex', function (st) {

        var t = util.getNowTime(st);

        return t.YY + '-' + t.MM + '-' + t.DD;

    });

    $('.fnUpContract').attr('filetype', util.fileType.img + '; *.pdf');

    // 选择项目
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '项目列表',
        ajaxUrl: '/baseDataLoad/loanCreditProject.json',
        btn: '#projectCodeBtn',
        tpl: {
            tbody: [
                '{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6) + \'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6) + \'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td>{{item.notCreditSize}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>', '{{/each}}'
            ].join(''),
            form: [
                    '项目名称：',
                    '<input class="ui-text fn-w160" type="text" name="projectName">',
                    '&nbsp;&nbsp;',
                    '客户名称：',
                    '<input class="ui-text fn-w160" type="text" name="customerName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ]
                .join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">客户名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额(元)</th>',
                '<th width="80">未落实</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 7
        },
        callback: function ($a) {
            window.location.href = '/projectMg/projectCreditCondition/editProjectCreditCondition.htm?projectCode=' + $a.attr('projectCode');
        }
    });

    // ------ 选择人 start
    var scope = '{type:\"system\",value:\"all\"}';
    var selectUsers = {
        selectUserIds: '', // 已选id,多用户用,隔开
        selectUserNames: '' // 已选Name,多用户用,隔开
    }

    var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选
    // 初始化弹出层
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=false', {
        'title': '落实人员',
        'width': 850,
        'height': 460,
        'scope': scope,
        'selectUsers': selectUsers,
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });
    // 添加选择后的回调，以及显示弹出层
    $body.on('click', '.fnChooseManBtn', function () {

        var _thisP = $(this).parent(),
            _$fnChooseManCode = _thisP.find('.fnChooseManCode'),
            _$fnChooseManName = _thisP.find('.fnChooseManName'),
            _$fnChooseManAccount = _thisP.find('.fnChooseManAccount'),
            _$fnChooseManNameText = _thisP.find('.fnChooseManNameText');
        // 更新已选
        selectUsers.selectUserIds = _$fnChooseManCode.val();
        selectUsers.selectUserNames = _$fnChooseManName.val();
        selectUsers.selectUserAccounts = _$fnChooseManAccount.val();

        singleSelectUserDialog.init(function (relObj) {

            var _idArr = relObj.userIds.split(',');

            // 选择人不能包括当前用户
            if ($.inArray(document.getElementById('currentLoginUserId').value, _idArr) > -1) {
                Y.alert('提示', '选择人员中不能包含当前用户，请重新选择');
                return;
            }

            _$fnChooseManCode.val(relObj.userIds).trigger('blur');
            _$fnChooseManName.val(relObj.fullnames);
            _$fnChooseManAccount.val(relObj.accounts);
            _$fnChooseManNameText.html(relObj.fullnames);

        });

    });
    // ------ 选择人 end
    //
    // 表单验证
    var $addForm = $('#addForm');
    $addForm.validate({
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

            formSubmit('SUBMIT');

        },
        rules: {
            projectCode: {
                required: true
            }
        },
        messages: {
            projectCode: {
                required: '必填'
            }
        }
    });
    //
    // 是否落实、必填
    $('.fnRadio').on('click', function () {
        checkRequired($(this))
    })
    $('.fnRadio:checked').each(function (index, el) {
        checkRequired($(this))
    });
    //

    function checkRequired($el) {

        var $p = $el.parents('tr')
        var $req = $p.find('.fnRequired')

        if ($el.val() == 'IS') {

            $req.each(function (index, el) {

                var _$input = $(this)

                _$input.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })

                _$input.valid()

            })

            $p.find('.fnTwoAndOne').addClass('fnTwoAndOneMust')
            $p.find('.fnChooseMan').removeClass('fn-hide')

        } else {

            $req.each(function (index, el) {

                var _$input = $(this)

                _$input.rules('remove', 'required')

                _$input.valid()

            })

            $p.find('.fnTwoAndOne').removeClass('fnTwoAndOneMust')
            $p.find('.fnChooseMan').addClass('fn-hide')

        }

    }

    function formSubmit(type) {

        if (!!!$fnCreditList.find('tr').length || !!$fnCreditList.find('tr.nodata').length) {
            Y.alert('提示', '暂无落实条件的项目，不能进行下去')
            return
        }

        document.getElementById('state').value = type || 'SAVE';
        document.getElementById('checkStatus').value = type ? 1 : 0;

        // 上传的附加 数组处理
        $body.find('td.fnUpAssetTd').each(function (index, el) {

            var _prefix = this.getAttribute('subdiyname'),
                _ordername = this.getAttribute('ordername');

            $(this).find('.fnUpAssetItem').each(function (i, e) {

                    var _i = i;
                    $(this).find('input').each(function (index, el) {

                        var _name = this.name;

                        if (_name.indexOf('.') > 0) {
                            _name = _name.substring(_name.indexOf('.') + 1);
                        }

                        if (_name.indexOf('.') > 0) {
                            _name = _name.substring(_name.indexOf('.') + 1);
                        }

                        this.name = _ordername + '[' + _i + '].' + _name;

                    });

                })
                .end().find('input').each(function (index, el) {

                    this.name = _prefix + this.name;

                });

        });

        if (!!type) {

            // 资产附件是否必填
            var _isAssetPass = true,
                _isAssetPassEq,
                _allAssetHasInput = true, // 避免资产附件为空的情况
                _allAssetHasInputEq;
            $('.fnRadioBox').each(function (index, el) {

                var $this = $(this);
                var $radio = $this.find(':checked');
                var $tr = $this.parent();

                if ($radio.val() == 'IS') {

                    $tr.find('.fnThisValue').each(function (index, el) {

                            if (!!!el.value && el.getAttribute('isrequired') == 'IS' && !!!$(el).parent().find('[name*=imageTextValue]').val()) {
                                _isAssetPass = false;
                                return false;
                            }

                        })
                        .end().find('.fnUpAssetTd').each(function (index, el) {

                            if (!!!$(this).find('input').length) {
                                _allAssetHasInput = false
                                return false
                            }

                        })

                }

                if (!_allAssetHasInput) {
                    _allAssetHasInputEq = index;
                    return false;
                }

                if (!_isAssetPass) {
                    _isAssetPassEq = index;
                    return false;
                }

            })

            if (!_allAssetHasInput) {
                Y.alert('提示', '第' + (_allAssetHasInputEq + 1) + '个授信条件，附件不完整(资产无附件，但未上传普通附件)');
                return;
            }

            if (!_isAssetPass) {
                Y.alert('提示', '第' + (_isAssetPassEq + 1) + '个授信条件，附件不完整(资产附件中有必需文件未上传)');
                return;
            }

            // 附件二选一
            var _isTwoPass = true,
                _isTwoPassEq;
            $('.fnTwoAndOne.fnTwoAndOneMust').each(function (index, el) {

                var _has = false
                var $this = $(this)
                var $input = $this.find('.fnTwoAndOneInput')

                // 2017.01.10 保证类型附件无需必填
                var _isRequired = !!!$this.parents('tr').attr('isensure')

                if (_isRequired) {

                    $input.each(function (index, el) {
                        if (!!el.value.replace(/\s/, '')) {
                            _has = true
                            return false
                        }
                    })

                    if (!!!$input.length) {
                        _has = true
                    }

                    if (!_has) {
                        _isTwoPass = false
                        _isTwoPassEq = index
                        return false
                    }

                }

            });

            if (!_isTwoPass) {
                Y.alert('提示', '所落实的第' + (_isTwoPassEq + 1) + '个授信条件，附件不完整(普通附件)')
                return
            }

        }

        // 保存数据
        util.ajax({
            url: '/projectMg/projectCreditCondition/saveProjectCreditCondition.htm',
            data: $('#addForm').serializeJCK(),
            success: function (res) {
                if (res.success) {
                    if (res.status == 'SUBMIT') {
                        // 提交表单
                        util.postAudit({
                            formId: res.form.formId
                        }, function () {
                            window.location.href = '/projectMg/projectCreditCondition/list.htm';
                        })
                    } else {
                        Y.alert('提醒', res.message, function () {
                            window.location.href = '/projectMg/projectCreditCondition/list.htm';
                        });
                    }
                } else {
                    Y.alert('提醒', res.message);
                }
            }
        });
    }

    // ------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    if (document.getElementById('addForm')) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'save',
            event: function () {
                formSubmit();
                // if (!!!$('.fnCheckBox:checked').length) {
                //     Y.alert('提示', '请填写本次落实授信条件，未勾选已落实将不会暂存');
                //     return;
                // }
                // Y.confirm('提示', '未勾选是否落实的信息将不会暂存，是否暂存当前信息？', function (opn) {
                //     if (opn == 'yes') {
                //         formSubmit();
                //     }
                // });
            }
        }]);
    }
    if (!!$('#projectCode').val()) {
        publicOPN.addOPN([{
            name: '已落实授信条件',
            alias: 'lookHas',
            event: function () {

                document.getElementById('lookHas').click();

            }
        }]);

        $body.append('<div id="lookHas"></div>');

        var _hasList = new getList();
        _hasList.init({
            width: '90%',
            title: '已落实授信条件',
            ajaxUrl: '/baseDataLoad/loadProjectCreditConditionPass.json?projectCode=' + $('#projectCode').val(),
            btn: '#lookHas',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td title="{{item.itemDesc}}">{{(item.itemDesc && item.itemDesc.length > 30)?item.itemDesc.substr(0,30) + \'\.\.\':item.itemDesc}}</td>',
                    '        <td title="{{item.confirmManName}}">{{(item.confirmManName && item.confirmManName.length > 10)?item.confirmManName.substr(0,10) + \'\.\.\':item.confirmManName}}</td>',
                    '        <td>{{=getTimex(item.confirmDate)}}</td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: '',
                thead: ['<th>授信条件</th>',
                    '<th width="200px">落实人员</th>',
                    '<th width="120px">落实日期</th>',
                ].join(''),
                item: 7
            },
            renderCallBack: function (res) {
                _hasList.$box.find('#boxSearch').html('<p>本项目已落实的保证金金额为' + res.data.marginAmount.standardString + '元，保证金划回金额' + res.data.customerDeposit.standardString + '元</p>');
            }
        });
    }

    publicOPN.init().doRender();
    // ------ 侧边栏 end
    //
    //
    // ------ 审核 start
    if (document.getElementById('auditForm')) {
        var auditProject = require('zyw/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
    }
    // ------ 审核 end

    // ------ 保证金落实情况 start
    var $fnIsBZJLSQK = $('#fnIsBZJLSQK'),
        $fnIsBZJLSQKBox = $('#fnIsBZJLSQKBox');

    $fnIsBZJLSQK.on('click', function () {

        if ($fnIsBZJLSQK.prop('checked')) {
            showBZJLSQK()
        } else {
            hideBZJLSQK()
        }

    });

    if ($fnIsBZJLSQK.prop('checked')) {
        showBZJLSQK()
    } else {
        hideBZJLSQK()
    }

    function showBZJLSQK() {
        $fnIsBZJLSQKBox.removeClass('fn-hide').find('input,select').removeProp('disabled').end().find('.fnInput').each(function (index, el) {
            $(this).rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        });
    }

    function hideBZJLSQK() {
        $fnIsBZJLSQKBox.addClass('fn-hide').find('input,select').prop('disabled', 'disabled').each(function (index, el) {
            $(this).rules('remove', 'required');
        });
    }

    // ------ 保证金落实情况 end

    // ------ 选择 存入银行 start

    var _bankList = new getList();
    _bankList.init({
        width: '90%',
        title: '选择银行',
        ajaxUrl: '/baseDataLoad/chooseBankMessage.json',
        btn: '#chooseBank',
        tpl: {
            tbody: [
                '{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.bankName}}">{{(item.bankName && item.bankName.length > 20)?item.bankName.substr(0,20)+\'\.\.\':item.bankName}}</td>',
                '        <td title="{{item.accountNo}}">{{(item.accountNo && item.accountNo.length > 25)?item.accountNo.substr(0,25)+\'\.\.\':item.accountNo}}</td>',
                '        <td><a class="choose" bname="{{item.bankName}}" bno="{{item.accountNo}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: '',
            thead: ['<th>开户银行 </th>',
                '<th width="200px">账户号码</th>',
                '<th width="50px">操作</th>',
            ].join(''),
            item: 3
        },
        callback: function ($a) {
            $('#bankName').val($a.attr('bname')).valid();
            $('#bankNO').val($a.attr('bno')).valid();
            // document.getElementById('bankName').value = $a.attr('bname');
            // document.getElementById('bankNO').value = $a.attr('bno');
        }
    });
    // ------ 选择 存入银行 end

    // ------ 相同合同编号 合同文件一致 start
    $body.on('blur', '.fnUpContentInput', function () {

        var _cno = this.getAttribute('cno'),
            $sameContent = $('.fnUpContentInput[con="' + _cno + '"]');

        if (!!!$sameContent.length) {
            return;
        }

        var $fnUpAttachUl = $(this).parent().find('.fnUpAttachUl'),
            _value = this.value;

        $sameContent.each(function (index, el) {

            var $this = $(this);
            $this.val(_value).trigger('blur');
            $this.parent().find('.fnUpAttachUl').html($fnUpAttachUl.html())

        });

    });
    // ------ 相同合同编号 合同文件一致 end


    /**
     *
     * 2017.01.06 针对授信落实添加批量操作
     *
     * 同 资产id 才能批量操作
     *
     * 批量操作数据 是否落实 时间 资产附件
     * 
     */

    // ------ 批量操作 start

    var $fnBatchBox = $('#fnBatchBox')
    var $window = $(window)

    if (!!$fnBatchBox.length) {

        var $fnBatchBoxAfter = $('<div></div>').outerHeight($fnBatchBox.outerHeight()).hide()

        $fnBatchBox.after($fnBatchBoxAfter)

        var fnBatchBoxTop = $fnBatchBox.offset().top

        $window.on('scroll', function () {

            if ($window.scrollTop() >= fnBatchBoxTop) {

                $fnBatchBoxAfter.show()

                $fnBatchBox.addClass('fixed')

            } else {

                $fnBatchBoxAfter.hide()

                $fnBatchBox.removeClass('fixed')

            }

        })

    }


    var currentAssetId; // 资产id
    var currentAssetTypeId; // 资产类型id
    var IS_ENSURE = false;
    var $fnBatchOPNBox = $('#fnBatchOPNBox')
    var $upAssetBox = $('<div class="m-modal-box fn-hide"></div>').appendTo('body')
    var BATCH_OPN_DATA = {
        isConfirm: 'NO',
        confirmDateStr: '',
        attach: [], // 资源附件数组
        tTxt: '', // 普通上传二选一
        tAttach: '' // 普通上传附件
    }

    $fnBatchOPNBox.validate(util.validateDefault)

    $fnCreditList.on('click', '.checkbox', function () {

        // 妹子选择数据，都清空数据
        BATCH_OPN_DATA = {
            isConfirm: 'NO',
            confirmDateStr: '',
            attach: [], // 资源附件数组
            tTxt: '', // 普通上传二选一
            tAttach: '' // 普通上传附件
        }

        if (this.checked) {

            var $checked = $fnCreditList.find('.checkbox:checked')

            if ($checked.length == 1) {
                currentAssetId = this.value
                currentAssetTypeId = this.getAttribute('typeid')
                return
            }

            if (this.getAttribute('typeid') != currentAssetTypeId) {
                this.checked = false
            }


        }

    })

    $('#fnBatchCan').on('click', function () {

        var $checked = $fnCreditList.find('.checkbox:checked')

        if (!!!currentAssetTypeId || !!!$checked.length) {
            Y.alert('提示', '请先选择一个授信条件')
            return
        }

        var _total = 0

        $fnCreditList.find('.checkbox[typeid="' + currentAssetTypeId + '"]').each(function (index, el) {

            el.checked = true
            _total++

        })

        Y.alert('提示', '已选中' + _total + '个可批量操作的授信条件')

    })

    $('#fnBatchCancel').on('click', function () {

        $fnCreditList.find('.checkbox:checked').each(function (index, el) {
            el.checked = false
        })

        currentAssetId = undefined
        currentAssetTypeId = undefined

    })

    $('#fnBatchOPN').on('click', function () {

        var $checked = $fnCreditList.find('.checkbox:checked')

        if ($checked.length == 0) {
            Y.alert('提示', '请选择相同资产信息的授信条件后再操作')
            return
        }

        // 资产id为0和部位0 两种情况

        var _html = template('t-fnBatchOPNBox', {
            currentAssetId: currentAssetId
        })

        $fnBatchOPNBox.html(_html).removeClass('fn-hide')

        // 资产信息是否必填
        IS_ENSURE = currentAssetId == '0'

    })

    // 批量操作弹出层
    $fnBatchOPNBox.on('click', '.close', function () {
        $fnBatchOPNBox.html('').addClass('fn-hide')
    }).on('click', '.fnRadio', function () {

        BATCH_OPN_DATA.isConfirm = this.value

        var $fnRequired = $(this).parents('tr').find('.fnRequired')

        if (this.value == 'IS') {
            $fnRequired.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })
        } else {
            $fnRequired.rules('remove', 'required')
        }

        $fnRequired.valid()

    }).on('blur', '.laydate-icon', function () {
        BATCH_OPN_DATA.confirmDateStr = this.value
    }).on('blur', '.fnTwoAndOneInput', function () {

        if (this.className.indexOf('fnUpAttachVal') >= 0) {
            BATCH_OPN_DATA.tAttach = this.value
        } else {
            BATCH_OPN_DATA.tTxt = this.value
        }

    }).on('click', '.fnUp', function () {

        var $td = $(this).parents('td')

        $.when(ATTACH_COMMON.getAssetInfo(currentAssetId))
            .then(function (res) {

                if (res.listImageInfo.length) {

                    $upAssetBox
                        .html(ATTACH_COMMON.creatUpAssetBox(ATTACH_COMMON.creatUpAssetList(res.listImageInfo, BATCH_OPN_DATA.attach || [], false)))
                        .removeClass('fn-hide')

                } else {

                    // 无资产信息，转换为普通上传
                    var _html = [
                        '<textarea class="text fnTwoAndOneInput" maxlength="50"></textarea>',
                        '<div class="fnUpAttach fn-mt5">',
                        '   <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn">上传</a>',
                        '   <input class="fnUpAttachVal fn-input-hidden fnTwoAndOneInput" type="text" value="">',
                        '   <div class="fn-blank5"></div><div class="m-attach fnUpAttachUl block"></div>',
                        '</div>'
                    ].join('')

                    $td.html(_html).removeClass('fn-tac').addClass('fnTwoAndOne')

                    Y.alert('提示', '资产管理中并未关联数据，请上传普通附件', function (opn) {
                        if (opn == 'yes') {
                            $td.find('.fnUpAttachBtn').trigger('click');
                        }
                    });

                }

            })
            .fail(function (res) {

                // Y.alert('提示', res.message);
                // 无资产信息，转换为普通上传
                var _html = [
                    '<textarea class="text fnTwoAndOneInput" maxlength="50"></textarea>',
                    '<div class="fnUpAttach fn-mt5">',
                    '   <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn">上传</a>',
                    '   <input class="fnUpAttachVal fn-input-hidden fnTwoAndOneInput" type="text" value="">',
                    '   <div class="fn-blank5"></div><div class="m-attach fnUpAttachUl block"></div>',
                    '</div>'
                ].join('')

                $td.html(_html).removeClass('fn-tac').addClass('fnTwoAndOne')

                Y.alert('提示', '资产管理中并未关联数据，请上传普通附件', function (opn) {
                    if (opn == 'yes') {
                        $td.find('.fnUpAttachBtn').trigger('click');
                    }
                });

            });


    }).on('click', '.sure', function () {


        if (!$fnBatchOPNBox.valid()) {
            return
        }

        var $fnTwoAndOneInput = $fnBatchOPNBox.find('.fnTwoAndOneInput')

        if (BATCH_OPN_DATA.isConfirm == 'IS') {

            // 是否是资产信息
            // 保证类型 资产信息不必必填
            if (!!$fnTwoAndOneInput.length && !IS_ENSURE) {

                var _two = false

                $fnTwoAndOneInput.each(function (index, el) {

                    if (!!el.value) {
                        _two = true
                        return false
                    }

                })

                if (!_two) {
                    Y.alert('提示', '附件信息请选择一个填写')
                    return
                }

            } else {

                var _attachAll = true
                var _attachAllEq;

                $.each(BATCH_OPN_DATA.attach, function (index, obj) {

                    if (obj.isrequired) {

                        if (!!!obj.txt && !!!obj.value) {
                            _attachAll = false
                            _attachAllEq = index
                            return false
                        }

                    }

                })

                if (!_attachAll) {
                    Y.alert('提示', '第' + (_attachAllEq + 1) + '个资产附件必填')
                    return
                }

            }

        }

        // 给一个批量提示
        // 差异点 点击了上传附件，就算是空也会被替换为空
        var _tip = ['是否落实', '落实日期']
        if (BATCH_OPN_DATA.attach.length || (!!BATCH_OPN_DATA.tTxt || !!BATCH_OPN_DATA.tAttach)) {
            _tip.push('附件 ')
        }

        Y.confirm('批量提示', '本次将批量修改已选择落实情况的“' + _tip.join('、') + '”，是否批量修改？', function (opn) {

            if (opn == 'yes') {

                // 批量导入

                var $attach = $fnBatchOPNBox.find('td').last()

                $attach.find('textarea, input').each(function (index, el) {

                    if (el.type === 'text') {
                        el.setAttribute('value', el.value)
                    } else {
                        el.innerHTML = el.value
                    }

                })

                $fnCreditList.find('.checkbox:checked').each(function (index, el) {

                    var $this = $(this)
                    var $tr = $this.parents('tr')

                    if (!!$fnTwoAndOneInput.length) {

                        var _index = $this.index('#fnCreditList .checkbox')

                        $attach.find('textarea').attr('name', 'projectCreditConditionOrders[' + _index + '].textInfo')
                        $attach.find('.fnUpAttachVal').attr('name', 'projectCreditConditionOrders[' + _index + '].rightVouche')

                        $tr.find('td').last().removeAttr('class subdiyname ordername').addClass('fnTwoAndOne').html($attach.html())

                    } else {

                        var $up = $tr.find('.fnUpAsset')
                        var _html = ''

                        $.each(BATCH_OPN_DATA.attach, function (index, obj) {

                            _html += ATTACH_COMMON.creatAssetInput({
                                assetId: $up.attr('assetid'),
                                imageKey: obj.imageKey,
                                imageValue: obj.imageValue || '',
                                creditId: $up.attr('itemid'),
                                imageTextValue: obj.imageTextValue || '',
                                isrequired: obj.isrequired
                            })

                        })

                        if (_html) {
                            $up.nextAll().remove().end().after(_html)
                        }

                    }

                    $tr.find('.laydate-icon').val(BATCH_OPN_DATA.confirmDateStr)
                    $tr.find('.fnRadio[value="' + BATCH_OPN_DATA.isConfirm + '"]').trigger('click')

                    el.checked = false

                })

                $fnBatchOPNBox.find('.laydate-icon').rules('remove', 'required')

                $fnBatchOPNBox.html('').addClass('fn-hide')

            }

        })

    })

    // 资产上传弹出层
    $upAssetBox.on('click', '.close', function () {

        saveBatchOPNAttach()

        $upAssetBox.html('').addClass('fn-hide')

    }).on('click', '.sure', function () {

        var _res = saveBatchOPNAttach(true);

        if (!_res.success) {
            Y.alert('提示', _res.message);
            return;
        }

        $upAssetBox.html('').addClass('fn-hide')

    })

    function saveBatchOPNAttach(isSure) {

        // 判断必填
        if (isSure) {

            var _isComplete = true,
                _isCompleteEq

            $upAssetBox.find('.fnInputTd.must').each(function (index, el) {

                var _hasValue = false;

                $(this).find('input').each(function (index, el) {

                    if (!!el.value.replace(/\s/g, '')) {
                        _hasValue = true;
                        return false
                    }

                });

                if (!_hasValue) {
                    _isComplete = false
                    _isCompleteEq = index
                    return false
                }

            });

            if (!_isComplete) {
                return {
                    success: false,
                    message: '第' + (_isCompleteEq + 1) + '行信息未填写完'
                };
            }

        }

        BATCH_OPN_DATA.attach = []

        $upAssetBox.find('.fnUpAssetVal').each(function (index, el) {

            var _txt = $(el).parents('td').find('input').eq(0).val();

            var _obj = {
                assetId: currentAssetId,
                imageKey: el.getAttribute('imagekey'),
                imageValue: el.value,
                imageTextValue: _txt,
                isrequired: el.className.indexOf('fnUpAssetInput') >= 0,
                txt: _txt,
                value: el.value
            }

            BATCH_OPN_DATA.attach.push(_obj)

        })

        return {
            success: true
        }

    }

    // ------ 批量操作 end


});