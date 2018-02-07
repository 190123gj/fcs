/**
 *
 * 合同申请 选择项目 —> 项目是否有主合同 -> 提醒去合同模板管理
 *                                       -> 选择合同模板 -> 根据选择条件判断是否关联、制式非制式、是否是主合同
 *                                                               -> 主合同不能添加进来
 *                                                               -> 制式合同要暂时一下表单(新增和编辑的地址不同)
 *                                                               -> 判断对应的关联情况
 *                                                                       -> 制式合同 填写，需暂存一下，把关联数据保存下来(需用js打开新窗口)，判断是否已关联数据已经是否还可以关联数据
 *
 *
 *
 * 2016.09.19 新增 纯自由流程
 * 选中纯自由流程后，不能返回到 非自由
 *     原因： 不是 制式合同 的合同，需生成一个填写链接，于是，不得不保存数据，丢失后台筛选的数据
 *     
 * 自由流程中需手动确定一个数据是主合同
 *
 * 页面加载好，判断是否自由流程，非自由流程缓存所有已存在的数据，点击 纯自由流程 隐藏、显示 该内容，已经是否禁用表单控件
 *
 * 2016.09.20 新添加合同都要进行暂存
 * 制式合同、非制式合同、其他合同 填写的时候，需要需要独立的链接跳转过去
 *
 * 2016.09.22 自由流程 不需要 主合同 也可以提交
 *
 * 2016.10.17 授信关联措施分别为 抵押、质押、保证
 *
 * 2016.12.14 自由、非自由 无缝切换
 *
 *  2016.12.22 作废合同重新申请
 *                  主合同作废 无主合同 以前申请的合同皆为自由流程合同 修正无主合同弹出层状态 回归正常申请合同流程
 * 
 */
define(function (require, exports, module) {

    // 项目管理 > 授信前管理 > 合同申请
    require('zyw/upAttachModify');
    require('input.limit');
    require('Y-msg');
    require('validate');

    var COMMON = require('./bfcg.contract.common');

    var getList = require('zyw/getList');
    var template = require('arttemplate');
    var util = require('util');
    var $fnIsFreeFlow = $('#fnIsFreeFlow');

    util.resetName()

    template.helper('json', function (obj) {
        obj.templateContent = null;
        return JSON.stringify(obj);
    });

    var _getList = new getList();
    //------ 初始化 选择项目 start
    COMMON.initChooseProjectList('/baseDataLoad/queryProjects.json?select=my&phasesList=CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES&statusList=NORMAL,RECOVERY,TRANSFERRED,SELL_FINISH,EXPIRE,OVERDUE&isApprovalDel=NO', function ($a) {
        window.location.href = '/projectMg/contract/addProjectContract.htm?projectCode=' + $a.attr('projectCode');
    });
    //------ 初始化 选择项目 end

    COMMON.initSelectDiy()


    //------ 重构 申请合同流程 start 2016.12.14
    // 新增合同的时候，会过滤主合同

    var HASMAIN = (document.getElementById('fnHasMain').value == 'NO' && !!document.getElementById('projectCode').value) ? false : true // 是否有主合同 

    if ($fnIsFreeFlow.prop('checked')) {
        // 自由流程不要求必须有主合同
        HASMAIN = true
        $('.fnIsMainInput').prop('disabled', 'disabled')

    } else {

        // 作废的情况

        if (!!$('.contractStatus[value="INVALID"]').length) {
            // 存在作废合同
            HASMAIN = true
        }

        // 2016.12.26 取消无主合同弹出层提示，随意填写
        HASMAIN = true

        // 隐藏是否主合同选项
        // $('.fnIsFreeFlow').addClass('fn-hide')
        $('.fnIsMainSelect').prop('disabled', 'disabled')

        if (!HASMAIN) {
            $('body').Y('Msg', {
                type: 'alert',
                title: '消息提示',
                content: '没有与当前项目所属业务类型匹配的主合同，请联系管理合同相关人员添加'
            });

            $('#submit').prop('disabled', 'disabled');

        }

    }

    // 切换流程
    $fnIsFreeFlow.on('click', function () {

        var $main = $('.fnIsMainInput[value="IS"]')

        if ($fnIsFreeFlow.prop('checked')) {
            // 是否显示主合同
            // $('.fnIsFreeFlow').removeClass('fn-hide');
            $('.fnIsMainInput').prop('disabled', 'disabled');
            $('.fnIsMainSelect').removeProp('disabled');

            // 2016.12.22 自由流程主合同可以删
            $main.each(function (index, el) {
                $(this).parents('tr').find('td').last().append('<a class="del fn-f30 fn-ml5 fn-mr5 fnDelTr" href="javascript:void(0);">删除</a>')
            });

        } else {
            // $('.fnIsFreeFlow').addClass('fn-hide');
            $('.fnIsMainInput').removeProp('disabled');
            $('.fnIsMainSelect').prop('disabled', 'disabled');

            // 2016.12.22 非自由流程主合同不可以删
            $main.each(function (index, el) {
                $(this).parents('tr').find('.fnDelTr').remove()
            });
        }

    });

    //------ 重构 申请合同流程 end   

    //--- 筛选合同 start
    var $creditConditionType = $('#creditConditionType'),
        $pledgeType = $('#pledgeType'),
        $searchContractObj = $('#searchContractObj'),
        $contractList = $('#contractList'),
        $busiType = $('#busiType'),
        $pledgeTypeBox = $('#pledgeTypeBox');

    //重置搜索ajaxurl
    $('#searchContractBox select').on('change', function () {
        _contractList.resetAjaxUrl('/projectMg/contract/loadContractTemplate.json?status=IN_USE&pledgeType=' + encodeURIComponent($pledgeType.val()) + '&creditConditionType=' + encodeURIComponent($creditConditionType.val()) + '&busiType=' + encodeURIComponent($busiType.val()));
    });

    $pledgeType.on('blur', function () {
        _contractList.resetAjaxUrl('/projectMg/contract/loadContractTemplate.json?status=IN_USE&pledgeType=' + encodeURIComponent($pledgeType.val()) + '&creditConditionType=' + encodeURIComponent($creditConditionType.val()) + '&busiType=' + encodeURIComponent($busiType.val()));
        $searchContractObj.val('');
        document.getElementById('searchContract').value = '';
    });

    //清空参数
    $('#cleanParamBtn').on('click', function () {
        $creditConditionType.find('option').removeProp('selected').end().trigger('change');
        $pledgeTypeBox.find('input').val('').end().addClass('fn-hide');
        $pledgeType.trigger('blur');
        document.getElementById('searchContract').value = '';
        document.getElementById('searchContractObj').value = '';
    });
    //当类型是 保证
    $creditConditionType.on('change', function () {
        if (this.value == '保证' || !!!this.value) {
            $pledgeTypeBox.addClass('fn-hide').find('input').val('')
        } else {
            $pledgeTypeBox.removeClass('fn-hide')
        }
        //一旦变化，各种清空
        $searchContractObj.val('');
        document.getElementById('searchContract').value = '';
        $pledgeTypeBox.removeClass('active')
        $pledgeType.trigger('blur');
    });

    //添加合同
    $('#addContractBtn').on('click', function () {

        // 必须选择一个项目才能添加
        if (!!!document.getElementById('projectCode').value) {

            Y.alert('提示', '请先选择项目');
            return;

        }

        if (this.className.indexOf('ing') >= 0) {
            return
        }

        this.className += ' ing'

        var _html = '';

        // 添加合同前，先判断是否有主合同
        // 如果有主合同、并且列表中又选了一个主合同 全部给那个啥否了
        // if (document.getElementById('fnHasMain').value == 'IS' && !!$('.fnIsMainSelect option[value="IS"]:selected').length) {
        //     $('.fnIsMainSelect option[value="NO"]').last().prop('selected', 'selected');
        // }

        //如果没有选择合同模板，添加一个空白的合同
        if (!!!$searchContractObj.val().replace(/\s/g, '')) {
            _html = template('t-new-null', {
                creditConditionType: $creditConditionType[0].value,
                pledgeType: $pledgeType[0].value
            });
            $contractList.append(_html);
        } else {
            var _searchContractObj = JSON.parse($searchContractObj.val());

            // 如果是主合同，并且不是自由流程中
            // if (_searchContractObj.isMain == 'IS' && !$fnIsFreeFlow.prop('checked')) {
            //     // 判断当前页面是否有主合同
            //     // 主合同只有一个
            //     if ($contractList.find('tr').length > $contractList.find('.fnDelTr').length || HASMAIN) {
            //         Y.alert('提示', '该合同申请已有主合同，不能再添加主合同')
            //         return;
            //     }
            // }

            _html = template('t-new', _searchContractObj);

            $contractList.append(_html);

        }

        // 2016.09.20
        // 由于 合同填写方式改变，制式合同 以外的合同，需生成一个连接才能填写
        // 于是，所有合同都需要链接
        // 于是，新添加的同和都要暂存
        // 暂存后，自由流程就回不去了 啊哈哈~

        ajaxSaveForm(function (res) {
            window.location.href = '/projectMg/contract/editContract.htm?formId=' + res.formId + '&_time=' + (new Date()).getTime();
        }, function () {
            window.location.reload();
        });

    });

    function resetSerialNumber() {

        $contractList.find('tr').each(function (index, el) {

            el.getElementsByTagName('td')[0].innerHTML = index + 1

        })

    }

    $contractList.on('click', '.fnDelTr', function () {

        var $this = $(this)
        var $mainIsMainInput = $('.fnIsMainInput[value="IS"]')

        if ($this.parents('tr').find('.fnIsMainInput').val() == 'IS') {

            if ($mainIsMainInput.length > 1 || !!!$mainIsMainInput.length) {

                $this.parents('tr').remove()

                resetSerialNumber()

            } else {
                Y.confirm('提示', '您将删除列表中最后一个主合同，删除后可能会出错，是否要继续？', function (opn) {

                    if (opn == 'yes') {

                        $this.parents('tr').remove()

                        resetSerialNumber()

                        Y.alert('提示', '主合同已删除，请及时添加或选择主合同')

                    }

                })
            }

        } else {

            $this.parents('tr').remove()

            resetSerialNumber()

        }

    }).on('change', '.fnIsMainSelect', function () {
        // select 主合同与隐藏域 同步
        $(this).parents('tr').find('.fnIsMainInput').val(this.value)
    })

    //搜索合同
    var _contractList = new getList();
    _contractList.init({
        title: '合同列表',
        ajaxUrl: '/projectMg/contract/loadContractTemplate.json?status=IN_USE&busiType=' + encodeURIComponent($busiType.val()),
        btn: '#searchContractBtn',
        input: ['searchContract', 'searchContractObj'],
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.name}}">{{(item.name && item.name.length > 20)?item.name.substr(0,20)+\'\.\.\':item.name}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td><p title="{{item.pledgeType}}" style="display: block;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;width: 200px;">{{item.creditConditionType}} {{item.pledgeType}}</p></td>',
                '        <td><a class="choose" searchContract="{{item.name}}" searchContractObj="{{item | json}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: '反担保措施相关的合同需关联授信措施',
            thead: ['<th>模板名称</th>',
                '<th width="120px">业务类型</th>',
                '<th width="100px">反担保措施</th>',
                '<th width="50px">操作</th>'
            ].join(''),
            item: 4
        }
    });

    //--- 筛选合同 end
    //
    //
    //
    //--- 提交按钮 start
    $.extend($.validator.messages, {
        required: "必填"
    });

    var $addForm = $('#addForm'),
        requiredRules = {
            rules: {},
            messages: {}
        };

        $addForm.validate($.extend(true, util.validateDefault, {
            rules: requiredRules.rules,
            messages: requiredRules.messages,
            submitHandler: function () {

                var emptyFlag = false
                $('#contractList .ccode').each(function (index) {
                    if($(this).val() == '') {
                        emptyFlag = true
                        return
                    }
                })

                justNext()

                /**
                 * bool 是否判断
                 *
                 */
                function justNext() {
                    var n = false;
                    var _flag = true
                    if($('.vali').length == 1){
                        n=true
                    } else {
                        $('.vali').each(function(i,el){
                            $('.vali').each(function(j,ek){
                                if(i!=j){
                                    if($(el).val() == $(ek).val() && $(el).val() !== ''){
                                        if(_flag)
                                            Y.alert('提示', '合同编号不能重复', function () {})
                                        n = false
                                        _flag = false
                                    }else {
                                        n=true
                                    }
                                }
                            })
                        })
                    }
                    if(n && _flag){
                        var _isSealAll = true,
                            _isSealEq,
                            _isSealHas0 = false;

                        $('.fnSeal:not([disabled])').each(function (index, el) {
                            if (+el.value == 0) {
                                _isSealHas0 = true;
                            }
                            if (!!!el.value.replace(/\s/g, '')) {
                                _isSealAll = false;
                                _isSealEq = index;
                                return false;
                            }
                        });

                        if (!_isSealAll) {
                            Y.alert('提示', '请填写完所需的文件份数(第' + (_isSealEq + 1) + '行)', function () {
                                $('.fnSeal:not([disabled])').eq(_isSealEq).focus();
                            });
                            return;
                        }


                        // diy的
                        var _isDiy = true,
                            $isDiyEq,
                            _isDiyTrEq;

                        $('.fnDiyName:not([disabled])').each(function (index, el) {
                            if (!!!el.value.replace(/\s/g, '')) {
                                _isDiy = false
                                $isDiyEq = $(el)
                                _isDiyTrEq = $isDiyEq.parents('tr').index('#contractList tr')
                                return false
                            }
                        });

                        if (!_isDiy) {
                            Y.alert('提示', '其他合同需要填写合同名称(第' + (_isDiyTrEq + 1) + '行)', function () {
                                $isDiyEq.focus();
                            });
                            return;
                        }

                        // 2016.09.20 已经不需要上传附件了
                        //上传附件
                        var _isFilesAll = true,
                            _isFilesTrEq;
                        $('.fnFiles:not([disabled])').each(function (index, el) {
                            if (!!!el.value.replace(/\s/g, '')) {
                                _isFilesTrEq = $(el).parents('tr').index('#contractList tr')
                                _isFilesAll = false
                                return false
                            }
                        });

                        if (!_isFilesAll) {
                            Y.alert('提示', '请上传合同附件(第' + (_isFilesTrEq + 1) + '行)');
                            return;
                        }

                        // 2016.09.20 决策依据
                        var _checkBasis = COMMON.checkBasis();
                        if (!_checkBasis.success) {
                            Y.alert('提示', _checkBasis.message);
                            return;
                        }

                        //关联授信措施
                        if (!checkMortgage() || !checkPledge() || !checkEnsure()) {
                            Y.alert('提示', '还有未关联授信措施的合同，请先关联授信措施')
                            return;
                        }

                        //提交
                        //2016.08.27
                        //合同申请时，份数填写了0，提交时增加一个二次确认提示吧“一式几份为0时，对应合同无法用印，请确认” 点击确定，则提交成功，点击取消，可返回修改
                        //2016.09.19 分数不能填写0
                        if (_isSealHas0) {

                            Y.alert('提示', '一式几份为0时，对应合同无法用印，请重新填写');

                        } else {

                            // 2016.12.23 前端不再判断主合同

                            var isFirst = (document.getElementById('fnIsFirst').value === 'IS')
                            var HAS_MAIN = (document.getElementById('fnHasMain').value === 'IS')

                            // // 自由流程 只能选一个主合同
                            // // 第一次进来，忽视原有的主合同
                            if ($fnIsFreeFlow.prop('checked')) {

                                var _mainLength = $('.fnIsMainSelect option[value="IS"]:selected').length;

                                // 是否是第一次
                                if (isFirst) {

                                    if (_mainLength > 1) {
                                        Y.alert('提示', '一个项目只能有一个主合同');
                                        return;
                                    }

                                } else {

                                    // 是否已有主合同
                                    if (HAS_MAIN) {

                                        if (_mainLength != 0) {
                                            Y.alert('提示', '该项目已有主合同');
                                            return;
                                        }

                                    } else {

                                        if (_mainLength > 1) {
                                            Y.alert('提示', '一个项目只能有一个主合同');
                                            return;
                                        }

                                    }

                                }

                                // 自由流程提示是否有主合同
                                if (!HAS_MAIN && !!!_mainLength) {
                                    Y.confirm('提示', '当前项目还没有主合同，是否要继续提交？', function (opn) {
                                        if (opn == 'yes') {
                                            Y.confirm('提示', '确定已关联了“授信措施”?', function (opn) {
                                                if (opn === 'yes') {
                                                    if(emptyFlag) {
                                                        Y.confirm('提示', '有合同编号为空哦, 继续提交吗？', function (opn) {
                                                            if (opn == 'yes') {
                                                                doSubmit('SUBMIT')
                                                            } else {
                                                                return
                                                            }
                                                        })
                                                    }
                                                    else{
                                                        doSubmit('SUBMIT')
                                                    }
                                                }
                                            })
                                        }
                                    })
                                    return;
                                }


                            } else {

                                //     // 非自由流程，主合同要么为1，要么为0
                                //     // 是否有主合同和第一次申请，推断上次申请的流程类型

                                //     var _mainLength = $('.fnIsMainInput[value="IS"]').length;

                                //     if (isFirst && _mainLength != 1) {

                                //         Y.alert('提示', (_mainLength > 1) ? '一个项目只能有一个主合同' : '需要选择一个合同作为主合同')
                                //         return

                                //     }

                                //     if (!isFirst) {

                                //         if (HAS_MAIN && _mainLength != 0) {
                                //             Y.alert('提示', '已有主合同，不能再添加主合同')
                                //             return
                                //         }

                                //         if (!HAS_MAIN && _mainLength != 1) {
                                //             Y.alert('提示', (_mainLength > 1) ? '一个项目只能有一个主合同' : '需要选择一个合同作为主合同')
                                //             return
                                //         }

                                //     }

                            }

                            //-- *合同名称 zhurui 合同名称不能为空
                            var okflag = true;
                            $('._modifiy').each(function () {
                                if(!$(this).val()){
                                    okflag = false;
                                    if($(this).parent().find('.error-tip').length)
                                        $(this).parent().find('.error-tip').remove();
                                    $('<b class="error-tip" style="display: block">必填</b>').appendTo($(this).parent());
                                    return;
                                }
                            })
                            if(okflag){
                                Y.confirm('提示', '确定已关联了“授信措施”?', function (opn) {
                                    if (opn === 'yes') {
                                        if(emptyFlag) {
                                            Y.confirm('提示', '有合同编号为空哦, 继续提交吗？', function (opn) {
                                                if (opn == 'yes') {
                                                    doSubmit('SUBMIT')
                                                } else {
                                                    return
                                                }
                                            })
                                        } else {
                                            doSubmit('SUBMIT')
                                        }
                                    }
                                })
                            }


                        }}
                }

            }

        })
        )



    // 提交、暂存
    function doSubmit(type) {
        //是否选择项目
        if (!!!document.getElementById('projectCode').value) {
            Y.alert('提示', '请选择项目', function () {
                document.getElementById('choose').click();
            });
            return;
        }
        //是否保存、提交
        var _isSave = true,
            checkStatus = document.getElementById('checkStatus');

        if (type) {
            _isSave = false;
            checkStatus.value = '1';
        } else {
            checkStatus.value = '0';
        }

        $('tr[odiyname]:not([diyname])').remove();
        //resetName();
        util.resetName();

        //post数据
        util.ajax({
            url: '/projectMg/contract/saveProjectContract.htm',
            data: $addForm.serialize(),
            success: function (res) {
                if (res.success) {
                    if (!_isSave) {

                        util.postAudit({
                            formId: res.formId
                        }, function () {
                            window.location.href = '/projectMg/contract/list.htm';
                        })

                    } else {
                        //跳转到编辑页面
                        Y.alert('提示', '已保存', function () {
                            window.location.href = '/projectMg/contract/editContract.htm?formId=' + res.formId;
                        });
                    }
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });


    }
    //--- 提交按钮 end
    //
    //
    //--- 仅仅是保存表单数据 start
    function ajaxSaveForm(successCB, falseCB) {

        document.getElementById('checkStatus').value = '0';
        $('tr[odiyname]:not([diyname])').remove();
        util.resetName();
        util.ajax({
            url: '/projectMg/contract/saveProjectContract.htm',
            data: $addForm.serialize(),
            success: function (res) {
                if (res.success) {
                    successCB && successCB(res)
                } else {
                    Y.alert('提示', res.message, function () {
                        falseCB && falseCB()
                    });
                }
            }
        });
    }
    //--- 仅仅是保存表单数据 end


    //侧边栏
    var publicOPN = require('zyw/publicOPN'),
        _publicOPN = new publicOPN(),
        projectCode = document.getElementById('projectCode');

    if (projectCode && projectCode.value) {

        _publicOPN.addOPN([{
            name: '查看项目批复',
            alias: 'look',
            event: function () {
                util.openDirect('/projectMg/index.htm', '/projectMg/meetingMg/summary/approval.htm?projectCode=' + document.getElementById('projectCode').value);
                // url: "/projectMg/meetingMg/summary/approval.htm?projectCode=" + document.getElementById("projectCode").value
            }

        }, {
            name: '暂存',
            alias: 'save',
            event: function () {
                if (!HASMAIN) {
                    return;
                }
                ajaxSaveForm(function () {
                    Y.alert('提示', '操作成功', function () {
                        window.location.href = '/projectMg/contract/list.htm';
                    });
                });
            }
        }]);

    }
    _publicOPN.init().doRender();

    if (document.getElementById('fnHasApproval').value != 'IS') {

        _publicOPN.removeOPN('查看项目批复').remove().doRender();

    }

    // ------ 抵押、质押、保证 关联数据 start
    function getOriginalData(id) {

        var _dom = document.getElementById(id)

        if (!!_dom) {
            return eval(_dom.value)
        } else {
            return []
        }

    }
    var MORTGAGE_ORIGINAL_DATA = getOriginalData('mortgageData'), // 抵押原始数据
        PLEDGE_ORIGINAL_DATA = getOriginalData('pledgeData'), // 质押原始数据
        ENSURE_ORIGINAL_DATA = getOriginalData('ensureData'), // 保证原始数据
        TEXT_ORIGINAL_DATA = getOriginalData('textData'), // 本文原始数据
        $fnRelevance = $('#fnRelevance'), // 选择关联的弹出层
        $fnRelevanceTitle = $('#fnRelevanceTitle'), // 选择关联弹出层的标题
        $fnRelevanceList = $('#fnRelevanceList'); // 选择关联弹出层的数据列表

    $fnRelevanceList.on('click', '.allCheck', function () {

        var self = this

        $fnRelevanceList.find('.checkbox').each(function (index, el) {
            el.checked = self.checked
        })

    }).on('click', '.checkbox:not(".allCheck")', function () {
        if (!this.checked) {
            $fnRelevanceList.find('.allCheck').removeProp('checked')
        }
    })

    // 还原已选数据
    function restoreData($input, orifinalArr) {

        $input.each(function (index, el) {

            var $this = $(this),
                _codeArr = $this.val().split(',')

            if (!!$this.val()) {

                var _textArr = []
                $.each(orifinalArr, function (index, obj) {
                    if ($.inArray(obj.code.toString(), _codeArr) >= 0) {
                        _textArr.push('[' + obj.type + ']' + obj.txt)
                    }
                })

                $this.prev().val(_textArr)

            }

        });

    }
    // 抵押
    restoreData($('.fnIsMortgage:not([disabled])'), MORTGAGE_ORIGINAL_DATA)

    // 质押
    restoreData($('.fnIsPledge:not([disabled])'), PLEDGE_ORIGINAL_DATA)

    // 保证
    restoreData($('.fnIsEnsure:not([disabled])'), ENSURE_ORIGINAL_DATA)

    // 创建授信措施列表
    function creatMeasuresList(arr, selected) {
        var _s = ''

        if (!!!arr.length) {
            return '无关联条件'
        }

        _s += '<label class="fn-csp"><input class="checkbox allCheck" type="checkbox">全选</label>'
            // 2016.10.15 关联条件变多选
        for (var i = 0; i <= arr.length - 1; i++) {
            _s += [
                '<label class="fn-csp fn-dpbk fn-fs16 fn-pt20">',
                '<input class="fn-mr5 checkbox" type="checkbox" name="choose" value="' + arr[i].code + '" ' + (($.inArray(arr[i].code.toString(), selected) >= 0) ? 'checked' : '') + '>',
                '<span class="fn-mr10">[ ' + arr[i].type + ' ]</span>',
                arr[i].txt,
                '</label>'
            ].join('')
        }
        return _s

    }

    // 创建未选择的条件
    function setCanChooseData(orifinalArr, $choosed) {

        var _chooseArr = [],
            _canArr = [],
            _allChoose = true

        $choosed.each(function (index, el) {
            // console.log(el.value)
            // console.log(!!!el.value)
            if (!!!el.value) {
                _allChoose = false
            } else {
                _chooseArr.push(el.value)
            }
        })

        // 对于多选进行合并
        _chooseArr = _chooseArr.toString().split(',')

        $.each(orifinalArr, function (index, obj) {
            if ($.inArray(obj.code.toString(), _chooseArr) == -1) {
                _canArr.push(obj)
            }
        })
        return {
            list: _canArr,
            allChoose: _allChoose
        }

    }

    // 检测是否还有可关联的数据
    // 要么已经把关联条件用完，要么都已经选了条件
    // 抵押
    function checkMortgage() {
        if (!!!MORTGAGE_ORIGINAL_DATA.length) {
            return true
        }

        var resultObj = setCanChooseData(MORTGAGE_ORIGINAL_DATA, $('input.fnIsMortgage'))

        if (resultObj.allChoose || !!!resultObj.list.length) {
            return true
        } else {
            return false
        }

    }
    // 质押
    function checkPledge() {
        if (!!!PLEDGE_ORIGINAL_DATA.length) {
            return true
        }

        var resultObj = setCanChooseData(PLEDGE_ORIGINAL_DATA, $('input.fnIsPledge'))

        if (resultObj.allChoose || !!!resultObj.list.length) {
            return true
        } else {
            return false
        }

    }

    // 保证
    function checkEnsure() {
        if (!!!ENSURE_ORIGINAL_DATA.length) {
            return true
        }

        var resultObj = setCanChooseData(ENSURE_ORIGINAL_DATA, $('input.fnIsEnsure'))

        if (resultObj.allChoose || !!!resultObj.list.length) {
            return true
        } else {
            return false
        }

    }

    //通过隐藏域获取文字信息  2017-5-3
    var _fn_all = $('.fnAll');
    var _fill_text = $('.fnRelevanceInput');
    _fill_text.each(function (idx) {
        var $this = $(this);

        getTextAndFill($this, _fn_all.eq(idx).val())


    })
    function getTextAndFill(_this, hidden_val) {

        var _type = _this.attr('ctype'), // 选中的类型
            _ntype = _this.attr('ntype'),
            _curOriginalData = (_type === 'IsEnsure') ? ENSURE_ORIGINAL_DATA : ((_type === 'IsPledge') ? PLEDGE_ORIGINAL_DATA : MORTGAGE_ORIGINAL_DATA) // 选中的原始数据

        var _newArr = _curOriginalData.concat(TEXT_ORIGINAL_DATA)

        var temp_arr ='';

        hidden_val = hidden_val.split(',');

        for(var i in _newArr){

            for(var j = 0;j<hidden_val.length;j++){

                if(_newArr[i].code == hidden_val[j]){

                    temp_arr += '['+_newArr[i].type+']'+_newArr[i].txt+', ';

                }

            }

        }

        _this.val(temp_arr);
        _this.attr('test',temp_arr);

    }
    
    
    
    $('body').on('click', '.fnRelevanceInput', function () {
        var $this = $(this).addClass('relevanceCallBack'), // 标记哪个选中了
            $value = $this.next().addClass('relevanceCallBack'), // 用来隔离当前已选的条件
            _type = $this.attr('ctype'), // 选中的类型
            _ntype = $this.attr('ntype'),
            _curOriginalData = (_type === 'IsEnsure') ? ENSURE_ORIGINAL_DATA : ((_type === 'IsPledge') ? PLEDGE_ORIGINAL_DATA : MORTGAGE_ORIGINAL_DATA) // 选中的原始数据
        var _newArr = _curOriginalData.concat(TEXT_ORIGINAL_DATA)

        $fnRelevanceTitle.html((_type === 'IsEnsure') ? '请选择保证人' : '请选择对应反担保物的权利凭证号')
            //替换内容
        // console.log('input.fn' + _type)
        // $fnRelevanceList.html(creatMeasuresList(setCanChooseData(_newArr, $('input.fn' + _type + ':not(.relevanceCallBack)')).list, $value.val().split(',')))
        $fnRelevanceList.html(creatMeasuresList(setCanChooseData(_newArr, $('input.fnAll:not(.relevanceCallBack)')).list, $value.val().split(',')))

        $fnRelevance.removeClass('fn-hide')

    }).on('click', '.fnWriteTMP', function () {

        //填写制式合同
        var _this = $(this),
            _relevance = _this.parent().find('.fnRelevanceInput').next() // 依赖数据
            //直接筛选项目，主合同初始化时无id需暂存一下
            //暂存后id会变成xxx[n].id
        if (_this.parents('tr').find('.fnContractId').val() == 0 || !!!_this.parents('tr').find('.fnContractId').val()) {
            Y.confirm('提示', '该申请单还未保存，请先保存后再继续操作，是否暂存？', function (opn) {
                if (opn == 'yes') {
                    //保存
                    // 添加合同前，先判断是否有主合同
                    // 如果有主合同、并且列表中又选了一个主合同 全部给那个啥否了
                    if (document.getElementById('fnHasMain').value == 'IS' && !!$('.fnIsMainSelect option[value="IS"]:selected').length) {
                        $('.fnIsMainSelect option[value="NO"]').prop('selected', 'selected')
                    }
                    doSubmit()
                }
            })
            return
        }

        //非反担保措施 合同，无需关联
        if (_this.hasClass('fnNotNeedRelevance')) {
            // 避免填写的内容丢失
            ajaxSaveForm(function () {
                COMMON.openOnlineEditing(_this.attr('baseurl'))
            })
            return
        }

        // 可以不关联 或者关联数据用完了
        if (_relevance.length) {
            // 如果没有关联数据，是否已用完关联数据
            // 当前没有值并且管理条件没有使用完
            if (!!!_relevance.val() && !(_relevance.hasClass('fnIsMortgage') ? checkMortgage() : (_relevance.hasClass('fnIsPledge') ? checkPledge() : checkEnsure()))) {
                Y.alert('提示', '请先关联授信措施');
                return
            }
        }

        //合同名称



        ajaxSaveForm(function () {
            COMMON.openOnlineEditing(_this.attr('baseurl'))
        })

    })

    $fnRelevance.on('click', '.close', function () {

        //关闭弹窗
        $fnRelevance.addClass('fn-hide')
        $fnRelevanceList.html('')
        $('.relevanceCallBack').removeClass('relevanceCallBack')

    }).on('click', '#fnRelevanceSure', function () {

        var _arrCode = [],
            _arrTxt = []

        $fnRelevanceList.find('input:checked:not(".allCheck")').each(function (index, el) {
            _arrCode.push(el.value)
            _arrTxt.push(el.parentNode.innerHTML.replace(/<.+?>/gim, '').replace(/\s/g, ''))
        });

        $fnRelevance.addClass('fn-hide')
        $fnRelevanceList.html('')
        $('.relevanceCallBack').removeClass('relevanceCallBack').eq(0).val(_arrTxt).next().val(_arrCode)

    })

    // ------ 抵押、质押、保证 关联数据 end


    //---- textarea zhurui 修改默认值


    $('body').on('keyup','._modifiy',function () {
        var _value = $(this).val();
        $(this).text(_value).attr('value',_value);
        if($(this).val()){
            $(this).siblings('.error-tip').remove();
        }
    })
    



});