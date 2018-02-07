define(function (require, exports, module) {
    // 辅助系统 >  出具函、通知书 > 出具函、通知书
    var COMMON = require('./bfcg.contract.common');

    var getList = require('zyw/getList');

    var template = require('arttemplate');

    var util = require('util');

    require('zyw/upAttachModify');

    template.helper('json', function (obj) {
        obj.templateContent = null;
        return JSON.stringify(obj);
    });

    //------ 初始化 选择项目 start
    COMMON.initChooseProjectList('/baseDataLoad/queryProjects.json?select=my&phasesList=INVESTIGATING_PHASES,COUNCIL_PHASES,RE_COUNCIL_PHASES,CONTRACT_PHASES,LOAN_USE_PHASES,FUND_RAISING_PHASES,AFTERWARDS_PHASES,RECOVERY_PHASES,FINISH_PHASES&statusList=NORMAL,RECOVERY,TRANSFERRED,SELL_FINISH,FINISH,EXPIRE,OVERDUE', function ($a) {
        window.location.href = '/projectMg/contract/addProjectContract.htm?applyType='+$('#applyType').val()+'&projectCode=' + $a.attr('projectCode');
    });
    //------ 初始化 选择项目 end

    COMMON.initSelectDiy()

    //------ 2017.01.17 新增需求
    //是否能添加担保意向函其他的函 IS/NO 为NO时只能添加担保意向函，为IS时没限制
    var IS_ONLY_INTENT_LETTER = (document.getElementById('fnIsOnlyIntentLetter') || {}).value === 'NO'

    var $writList = $('#writList'),
        $addForm = $('#addForm');


    //--- 筛选合同 start
    var $creditConditionType = $('#creditConditionType'),
        $pledgeType = $('#pledgeType'),
        $searchContractObj = $('#searchContractObj'),
        $contractList = $('#contractList'),
        $busiType = $('#busiType'),
        $pledgeTypeBox = $('#pledgeTypeBox');

    //重置搜索ajaxurl
    $('#searchContractBox select').on('change', function () {
        _contractList.resetAjaxUrl('/projectMg/contract/loadContractTemplate.json?templateType=LETTER&status=IN_USE&pledgeType=' + encodeURIComponent($pledgeType.val()) + '&creditConditionType=' + encodeURIComponent($creditConditionType.val()) + '&busiType=' + encodeURIComponent($busiType.val()));
    });
    $pledgeType.on('blur', function () {
        _contractList.resetAjaxUrl('/projectMg/contract/loadContractTemplate.json?templateType=LETTER&status=IN_USE&pledgeType=' + encodeURIComponent($pledgeType.val()) + '&creditConditionType=' + encodeURIComponent($creditConditionType.val()) + '&busiType=' + encodeURIComponent($busiType.val()));
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
    });
    //添加函、通知书
    $('#addContractBtn').on('click', function () {

        // 必须选择一个项目才能添加
        if (!!!document.getElementById('projectCode').value) {

            Y.alert('提示', '请先选择项目');
            return;

        }

        var _html = '';

        //如果没有选择合同模板，添加一个空白的合同
        if (!!!$searchContractObj.val().replace(/\s/g, '')) {
            _html = template('t-new-null', {
                creditConditionType: $creditConditionType[0].value
            });
            $contractList.append(_html);
        } else {
            var _searchContractObj = JSON.parse($searchContractObj.val());

            if (_searchContractObj.isMain == 'IS') {
                // 判断当前页面是否有主合同
                // 主合同只有一个
                if ($contractList.find('tr').length > $contractList.find('.fnDelTr').length) {
                    Y.alert('提示', '该合同申请已有主合同，不能再添加主合同')
                    return;
                }
            }

            //------ 2017.01.17 新增需求
            //是否能添加担保意向函其他的函 IS/NO 为NO时只能添加担保意向函，为IS时没限制
            if (IS_ONLY_INTENT_LETTER && _searchContractObj.name.indexOf('担保意向函') < 0 && $('.ui-breadcrumb').text().indexOf('出具担保函') > 0) {

                Y.alert('提示', '当前项目只能添加担保意向函')
                return

            }

            _html = template('t-new', _searchContractObj);

            $contractList.append(_html);

        }

        ajaxSaveForm(function (res) {
            window.location.href = '/projectMg/contract/editContract.htm?formId=' + res.formId + '&_time=' + (new Date()).getTime();
        });


    });

    $contractList.on('click', '.fnDelTr', function () {
        $(this).parents('tr').remove();
    });
    //搜索合同
    var _contractList = new getList();
    _contractList.init({
        title: '函、通知书列表',
        ajaxUrl: '/projectMg/contract/loadContractTemplate.json?templateType=LETTER&status=IN_USE&busiType=' + encodeURIComponent($busiType.val()),
        btn: '#searchContractBtn',
        input: ['searchContract', 'searchContractObj'],
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.name}}">{{(item.name && item.name.length > 20)?item.name.substr(0,20)+\'\.\.\':item.name}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.creditConditionType}} {{item.pledgeType}}</td>',
                '        <td><a class="choose" searchContract="{{item.name}}" searchContractObj="{{item | json}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: '反担保措施相关的合同类函需关联授信措施',
            thead: ['<th>模板名称</th>',
                '<th width="120px">业务类型</th>',
                '<th width="100px">反担保措施</th>',
                '<th width="50px">操作</th>'
            ].join(''),
            item: 6
        }
    });

    //--- 筛选合同 end

    //--- 提交按钮 start
    var $addForm = $('#addForm');
    $addForm.on('click', '#submit', function () {

        // diy的
        var _isDiy = true,
            _isDiyEq;

        $('.fnDiyName:not([disabled])').each(function (index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _isDiy = false;
                _isDiyEq = index;
                return false;
            }
        });

        if (!_isDiy) {
            Y.alert('提示', '请填写文件名称', function () {
                $('.fnDiyName').eq(_isDiyEq).focus();
            });
            return;
        }

        // 2016.09.20 决策依据
        var _checkBasis = COMMON.checkBasis();
        if (!_checkBasis.success) {
            Y.alert('提示', _checkBasis.message);
            return;
        }

        //上传附件
        var _isFilesAll = true;
        $('.fnFiles:not([disabled])').each(function (index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _isFilesAll = false;
            }
        });
        if (!_isFilesAll) {
            Y.alert('提示', '请上传附件');
            return;
        }

        //关联授信措施
        if (!checkMortgage() || !checkPledge() || !checkEnsure()) {
            Y.alert('提示', '还有未关联授信措施的合同，请先关联授信措施')
            return;
        }

        //提交
        doSubmit('SUBMIT');
    });


    function doSubmit(isSubmit) {

        document.getElementById('checkStatus').value = isSubmit ? '1' : '0';

        //------ 2017.01.17 新增需求
        //是否能添加担保意向函其他的函 IS/NO 为NO时只能添加担保意向函，为IS时没限制

        if (IS_ONLY_INTENT_LETTER && $('.ui-breadcrumb').text().indexOf('出具担保函') > 0) {

            var _isAllIntentLetter = true
            var _isAllIntentLetterEq

            $addForm.find('.fnDiyName').each(function (index, el) {
                if($(el).closest('tr').find('select[name="letterType"]').val() == 'WRIT') return true
                if (el.value.indexOf('担保意向函') < 0) {
                    _isAllIntentLetter = false
                    _isAllIntentLetterEq = index
                    return false
                }

            })

            if (!_isAllIntentLetter) {
                Y.alert('提示', '第' + (_isAllIntentLetterEq + 1) + '个文件不是担保意向函', function () {
                    $addForm.find('.fnDiyName').eq(_isAllIntentLetterEq).focus()
                })
                return
            }

        }

        util.resetName();

        util.ajax({
            url: $addForm.attr('action'),
            data: $addForm.serialize(),
            success: function (res) {

                if (res.success) {

                    if (!isSubmit) {
                        Y.alert('提示', '操作成功', function () {
                            window.location.href = '/projectMg/contract/list.htm?applyType='+$('#applyType').val();
                        });
                    } else {

                        util.postAudit({
                            formId: res.form.formId
                        }, function () {
                            window.location.href = '/projectMg/contract/list.htm?applyType='+$('#applyType').val();
                        })

                    }

                } else {
                    Y.alert('操作失败', res.message);
                }

            }
        });

    }

    $addForm.on('click', '#doSubmit', function () {

        if (!!!document.getElementById('projectCode').value) {
            Y.alert('提示', '请选择项目');
            return;
        }

        var $fileName = $('.fileName'),
            _fileNamePass = true,
            _fileNameEq;

        $fileName.each(function (index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _fileNamePass = false;
                _fileNameEq = index;
                return false;
            }
        });

        if (!_fileNamePass) {
            Y.alert('提示', '请填写完文件名称', function () {
                $fileName.eq(_fileNameEq).focus();
            });
            return;
        }

        var $fileValue = $('.fileValue'),
            _fileValuePass = true,
            _fileValueEq;

        $fileValue.each(function (index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _fileValuePass = false;
                _fileValueEq = index;
                return false;
            }
        });

        if (!_fileValuePass) {
            Y.alert('提示', '请为每个文件上传一个附件', function () {
                $fileValue.eq(_fileValueEq).focus();
            });
            return;
        }

        doSubmit(true);

    });

    //--- 仅仅是保存表单数据 start
    function ajaxSaveForm(cb) {
        document.getElementById('checkStatus').value = '0';
        $('tr[odiyname]:not([diyname])').remove();
        util.resetName();
        util.ajax({
            url: '/projectMg/contract/saveProjectContract.htm',
            data: $addForm.serialize(),
            success: function (res) {
                if (res.success) {
                    if (cb) {
                        cb(res)
                    }
                } else {
                    Y.alert('提示', res.message);
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
            }

        }, {
            name: '暂存',
            alias: 'save',
            event: function () {
                doSubmit(false);
            }
        }])

    }
    _publicOPN.init().doRender();

    if ($('#fnHasApproval').val() != 'IS') {

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
            if (!!!el.value) {
                _allChoose = false
            } else {
                _chooseArr.push(el.value)
            }
        })

        // 对于多选进行合并
        _chooseArr.toString().split(',').join(',')

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

    $('body').on('click', '.fnRelevanceInput', function () {

        var $this = $(this).addClass('relevanceCallBack'), // 标记哪个选中了
            $value = $this.next().addClass('relevanceCallBack'), // 用来隔离当前已选的条件
            _type = $this.attr('ctype'), // 选中的类型
            _curOriginalData = (_type === 'IsEnsure') ? ENSURE_ORIGINAL_DATA : ((_type === 'IsPledge') ? PLEDGE_ORIGINAL_DATA : MORTGAGE_ORIGINAL_DATA) // 选中的原始数据

        $fnRelevanceTitle.html((_type === 'IsEnsure') ? '请选择保证人' : '请选择对应反担保物的权利凭证号')
            //替换内容
        $fnRelevanceList.html(creatMeasuresList(setCanChooseData(_curOriginalData, $('input.fn' + _type + ':not(.relevanceCallBack)')).list, $value.val().split(',')))

        $fnRelevance.removeClass('fn-hide')

    }).on('click', '.fnWriteTMP', function () {

        //填写制式合同
        var _this = $(this),
            _relevance = _this.parent().find('.fnRelevanceInput').next() // 依赖数据
            //直接筛选项目，主合同初始化时无id需暂存一下
            //暂存后id会变成xxx[n].id
        if (_this.parents('tr').find('td').eq(0).find('input[name*="id"]')[0].value == 0 || !!!_this.parents('tr').find('td').eq(0).find('input[name*="id"]')[0].value) {
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
                Y.alert('提示', '请先关联授信措施')
                return
            }

        }

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

    // 辅助系统 > 出具函/通知书 > 法院裁定书
    require('validate');
    var $orderForm = $('#orderForm'),
        requiredRules = {
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $orderForm.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            util.ajax({
                url: $orderForm.attr("action"),
                data: $orderForm.serialize(),
                success: function (res) {
                    if (res.success) {
                        window.location.href = "/projectMg/contract/list.htm?applyType="+$('#applyType').val()
                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });
        }
    }));

    //------ 2017.01.12 授信条件 翻页 start
    var $body = $('body');
    // 显示隐藏未落实
    $body.on('click', '#fnHideIsConfirmIsNO', function () {

        getCreditConditions(2, 1)

    })

    var $creditPageList = $('#creditPageList')
    var $window = $(window)

    $creditPageList.find('.m-pages a').removeAttr('attribute name')

    window.getCreditConditions = function (totalPage, pageNo) {

        var PROJECT_CODE = document.getElementById('projectCode').value

        if (!!!PROJECT_CODE) {
            return
        }

        if (totalPage < pageNo) {
            return false;
        }

        var fnHideIsConfirmIsNO = document.getElementById('fnHideIsConfirmIsNO')
        var _hasToCarry = ''

        if (fnHideIsConfirmIsNO.checked) {
            _hasToCarry = fnHideIsConfirmIsNO.value
        }

        $creditPageList.load("/projectMg/contract/creditPageList.json", {
            'pageNumber': pageNo,
            'projectCode': PROJECT_CODE,
            'isConfirm': _hasToCarry
        }, function () {
            $window.scrollTop($creditPageList.offset().top)
        })


    }

    //------ 2017.01.12 授信条件 翻页 end

    //添加担保函
    var _dbList = new getList();
    _dbList.init({
        title: '添加担保函',
        ajaxUrl: '/projectMg/contract/loadContractTemplate.json?templateType=LETTER&status=IN_USE&isAddDBL=IS',
        btn: '#addDBL',
        input: ['searchContract', 'searchContractObj'],
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.name}}">{{(item.name && item.name.length > 20)?item.name.substr(0,20)+\'\.\.\':item.name}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td><a class="choose" searchContract="{{item.name}}" searchContractObj="{{item | json}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            // form: '反担保措施相关的合同类函需关联授信措施',
            thead: ['<th>文件名称</th>',
                '<th width="120px">文件类型</th>',
                '<th width="50px">操作</th>'
            ].join(''),
            item: 6

        },
        callback:function (data) {
            var _data = JSON.parse(data.attr('searchcontractobj'))
            var _html = ''
            _html = template('t-new',_data);
            $contractList.append(_html);
            ajaxSaveForm(function (res) {
                window.location.href = '/projectMg/contract/editContract.htm?formId=' + res.formId + '&_time=' + (new Date()).getTime();
            });
        }
    });

    // $body.on('click', '#addDBL', function () {
    //     // 必须选择一个项目才能添加
    //     if (!!!document.getElementById('projectCode').value) {
    //
    //         Y.alert('提示', '请先选择项目');
    //         return;
    //
    //     }
    //     util.ajax({
    //         url: '/projectMg/contract/loadContractTemplate.json?templateType=LETTER&status=IN_USE&isAddDBL=IS',
    //         success: function (res) {
    //             if (res.success) {
    //                 console.log(res.data)
    //                 var _html = '';
    //                 _html = template('t-new',(res.data.pageList)[0]);
    //                 console.log((res.data.pageList)[0])
    //                 $contractList.append(_html);
    //                 // ajaxSaveForm(function (res) {
    //                 //     window.location.href = '/projectMg/contract/editContract.htm?formId=' + res.formId + '&_time=' + (new Date()).getTime();
    //                 // });
    //             } else {
    //                 Y.alert('操作失败', res.message);
    //             }
    //
    //         }
    //     });
    //
    // })
});