/**
 * 合同申请、出具函/通知书、文书审核 公共文件
 *
 *  初始化 选择项目
 * 
 * 选择 决策依据 三种决策依据都可以选择、多选
 * 
 */

define(function (require, exports, module) {

    var $body = $('body'),
        PROJECTCODE = $('#projectCode').val();

    var util = require('util');

    var getList = require('zyw/getList');


    // ------ 初始化选择项目 statr
    function initChooseProjectList(ajaxUrl, callback) {

        // 选择项目、跳转
        var PROJECTLIST = new getList();

        PROJECTLIST.init({
            width: '90%',
            title: '项目列表',
            ajaxUrl: ajaxUrl,
            btn: '#choose',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td class="item">{{item.projectCode}}</td>',
                    '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6) + \'\.\.\':item.customerName}}</td>',
                    '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6) + \'\.\.\':item.projectName}}</td>',
                    '        <td>{{item.busiTypeName}}</td>',
                    '        <td>{{item.amount}}</td>',
                    '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '项目名称：',
                    '<input class="ui-text fn-w160" type="text" name="projectName">',
                    '&nbsp;&nbsp;',
                    '客户名称：',
                    '<input class="ui-text fn-w160" type="text" name="customerName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: [
                    '<th width="100">项目编号</th>',
                    '<th width="120">客户名称</th>',
                    '<th width="120">项目名称</th>',
                    '<th width="100">授信类型</th>',
                    '<th width="100">授信金额(元)</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            },
            callback: function ($a) {
                callback($a);
                // window.location.href = document.getElementById('projectListCallBackUrl').value + $a.attr('projectCode');
            }
        });

    }
    // ------ 初始化选择项目 end


    /**
     * 貌似这个选择决策机构被很多地方引用了
     *
     * 最基本的选项 批复、签报、风险处置会会议纪要
     *
     * 额外变异了 `已立项` 出具函/通知书 文件名称中包含了“担保意向函” case 'YLX'
     *
     * 额外变异了`合同` 退费申请单新增
     * 
     */

    // ------ 选择 决策依据 start

    var HISTORYGISTARR = eval($('#fnHistoryGist').val()) || [], // 历史决策依据，签报的formId
        $GISTTRIGGER,
        JCYJ_TYPE = 'FORM_CHANGE', // 2016.10.22 好像并没有用了
        FORM_CHANGE_LISt, // 签报 弹出层
        RISK_HANDLE_COUNCIL_SUMMARY_LIST, // 风险处置会会议纪要 弹出层
        CONTRACT_LIST; // 合同弹出层

    function setDecision(type, url, id) {

        var _text = '';

        switch (type) {

            case 'PROJECT_APPROVAL':
                _text = '批复';
                break;

            case 'FORM_CHANGE':
                _text = '签报';
                break;

            case 'RISK_HANDLE_COUNCIL_SUMMARY':
                _text = '风控纪要';
                break;

        }

        $GISTTRIGGER.html('重新选择')
            .parent().find('.fnWhich').html('<a class="fn-f30" href="' + url + '">' + _text + '</a><input type="hidden" name="basisOfDecision" value="' + id + '"><input type="hidden" name="decisionType" value="' + type + '">');

    }

    function initFormChangeList() {

        FORM_CHANGE_LISt = new getList();
        FORM_CHANGE_LISt.init({
            width: '90%',
            title: '请选择依据',
            ajaxUrl: '/baseDataLoad/loadFormChangeApply.json?formStatus=APPROVAL&projectCode=' + PROJECTCODE,
            btn: '#fnChooseGists',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.applyCode}}</td>',
                    '        <td>{{item.applyTypeMessage}}</td>',
                    '        <td>{{item.author}}</td>',
                    '        <td><input type="checkbox" name="xxxx" class="checkbox" value="{{item.applyFormId}}" ac="{{item.applyCode}}"></td>',
                    // '        <td><a class="choose" formid="{{item.applyFormId}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '<span class="fnChooseGist2Reply"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">批复</a></span>',
                    '<span class="fnChooseGist2Projected fn-hide"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">已立项</a></span>',
                    '<span class="fnChooseGist2Risk"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">风险处置会会议纪要</a></span>',
                    '<span class="fnChooseGist2Contract"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">合同</a></span>'
                ].join(''),
                thead: [
                    '<th>签报编号</th>',
                    '<th width="120px">签报单据</th>',
                    '<th width="100px">提交人</th>',
                    '<th width="50px">操作</th>'
                ].join(''),
                item: 4
            },
            multiple: true,
            callback: function (self) {

                // setDecision(JCYJ_TYPE, '/projectMg/formChangeApply/view.htm?formId=' + $a.attr('formid'), $a.attr('formid'))

                // 已选
                var $QB = $GISTTRIGGER.parent().find('.fnQB'),
                    $QBVal = $GISTTRIGGER.parent().find('.fnQBValue'),
                    selectedArr = $QBVal.val().split(';')

                // 当前选中
                var selectArr = []

                self.$box.find('.checkbox:checked').each(function (index, el) {
                    selectArr.push([el.value, el.getAttribute('ac')].join(','))
                });

                selectedArr = filterArr(selectedArr.concat(selectArr))

                $QBVal.val(selectedArr.join(';'))

                var _html = ''

                $.each(selectedArr, function (index, str) {
                    var strArr = str.split(',')
                    _html += '<li><a href="javascript:void(0);" class="fn-right fn-f30 fnDelBasis" w="QB" val="' + strArr[0] + '" t="' + strArr[1] + '">&times;</a><a class="fn-green" href="/projectMg/formChangeApply/view.htm?formId=' + strArr[0] + '">签报(' + strArr[1] + ')</a></li>'
                });

                $QB.html(_html)

            }
        });

    }

    // 过滤数组
    function filterArr(arr) {

        var _o = {},
            _arr = []

        $.each(arr, function (index, str) {
            if (!!str && !!!_o[str]) {
                _o[str] = str
                _arr.push(str)
            }
        });

        return _arr

    }

    function initRiskHandleCouncilSummaryList() {

        RISK_HANDLE_COUNCIL_SUMMARY_LIST = new getList();
        RISK_HANDLE_COUNCIL_SUMMARY_LIST.init({
            width: '90%',
            title: '请选择依据',
            ajaxUrl: '/baseDataLoad/projectRiskHandle.json?projectCode=' + PROJECTCODE,
            btn: '#fnChooseGists',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.summaryCode}}</td>',
                    '        <td>{{item.councilBeginTime}}</td>',
                    // '        <td><a class="choose" formid="{{item.summaryFormId}}" handleid="{{item.handleId}}" href="javascript:void(0);">选择</a></td>',
                    '        <td><input type="checkbox" name="x" class="checkbox" formid="{{item.summaryFormId}}" handleid="{{item.handleId}}" sc="{{item.summaryCode}}"></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '<span class="fnChooseGist2Reply"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">批复</a></span>',
                    '<span class="fnChooseGist2Projected fn-hide"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">已立项</a></span>',
                    '<span class="fnChooseGist2Form"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">签报</a></span>',
                    '<span class="fnChooseGist2Contract"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">合同</a></span>'
                ].join(''),
                thead: [
                    '<th>会议纪要编号</th>',
                    '<th width="180px">会议开始时间</th>',
                    '<th width="50px">操作</th>'
                ].join(''),
                item: 3
            },
            multiple: true,
            callback: function (self) {

                // setDecision(JCYJ_TYPE, '/projectMg/meetingMg/summary/view.htm?formId=' + $a.attr('formid') + '&spId=' + $a.attr('handleid'), $a.attr('formid') + ',' + $a.attr('handleid'))

                // 已选数据
                var $FKJY = $GISTTRIGGER.parent().find('.fnFKJY'),
                    $FKJYVal = $GISTTRIGGER.parent().find('.fnFKJYValue'),
                    selectedArr = $FKJYVal.val().split(';')

                // 当前选中
                var selectArr = []

                self.$box.find('.checkbox:checked').each(function (index, el) {
                    selectArr.push([el.getAttribute('formid'), el.getAttribute('handleid'), el.getAttribute('sc')].join(','))
                        // selectArr.push(el.getAttribute('summaryFormId') + ',' + el.getAttribute('handleid'))
                });

                selectedArr = filterArr(selectedArr.concat(selectArr))

                $FKJYVal.val(selectedArr.join(';'))

                var _html = ''

                $.each(selectedArr, function (index, str) {
                    var strArr = str.split(',')
                    _html += '<li><a href="javascript:void(0);" class="fn-right fn-f30 fnDelBasis" w="FKJY" sfi="' + strArr[0] + '" hi="' + strArr[1] + '" t="' + strArr[2] + '">&times;</a><a class="fn-f30" href="/projectMg/meetingMg/summary/view.htm?formId=' + strArr[0] + '&spId=' + strArr[1] + '">风控纪要(' + strArr[2] + ')</a></li>'
                });

                $FKJY.html(_html)

            }
        });

    }

    function initContractList() {


        CONTRACT_LIST = new getList();
        CONTRACT_LIST.init({
            width: '90%',
            title: '请选择依据',
            ajaxUrl: '/baseDataLoad/projectContract.json?projectCode=' + PROJECTCODE,
            btn: '#fnChooseGists',
            tpl: {
                tbody: [
                    '{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow">',
                    '        <td>{{item.contractCode}}</td>',
                    '        <td>{{item.contractName}}</td>',
                    '        <td><input type="checkbox" name="x" class="checkbox" contractcode="{{item.contractCode}}" contractname="{{item.contractName}}"></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: [
                    '<span class="fnChooseGist2Form"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">签报</a></span>',
                    '<span class="fnChooseGist2Risk"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">风险处置会会议纪要</a></span>',
                ].join(''),
                thead: [
                    '<th>合同编号</th>',
                    '<th width="180px">合同名称</th>',
                    '<th width="50px">操作</th>'
                ].join(''),
                item: 3
            },
            multiple: true,
            callback: function (self) {

                // 已选数据
                var $HT = $GISTTRIGGER.parent().find('.fnHT'),
                    $HTVal = $GISTTRIGGER.parent().find('.fnHTValue'),
                    selectedArr = $HTVal.val().split(';')

                // 当前选中
                var selectArr = []

                self.$box.find('.checkbox:checked').each(function (index, el) {
                    selectArr.push([el.getAttribute('contractcode'), el.getAttribute('contractname')].join(','))
                        // selectArr.push(el.getAttribute('summaryFormId') + ',' + el.getAttribute('handleid'))
                });

                selectedArr = filterArr(selectedArr.concat(selectArr))

                $HTVal.val(selectedArr.join(';'))

                var _html = ''

                $.each(selectedArr, function (index, str) {
                    var strArr = str.split(',')
                    _html += '<li><a href="javascript:void(0);" class="fn-right fn-f30 fnDelBasis" w="HT" val="' + strArr[0] + '" t="' + strArr[1] + '">&times;</a>' + strArr[1] + '(' + strArr[0] + ')</li>'
                });

                $HT.html(_html)

            }
        });

    }

    if (!!PROJECTCODE) {

        // 签报
        initFormChangeList()

        // 会议纪要
        initRiskHandleCouncilSummaryList()

    }

    /**
     * 是否显示已立项
     * @param  {[type]} instance [getList的实例]
     * @param  {[type]} show     [是否显示]
     * @return {[type]}          [description]
     */
    function toggleProjected(instance, show) {

        var $btn = instance.$box.find('.fnChooseGist2Projected')

        if (show) {
            $btn.removeClass('fn-hide')
        } else {
            $btn.addClass('fn-hide')
        }

    }

    function toggleContract(instance, show) {

        var $btn = instance.$box.find('.fnChooseGist2Contract')

        if (show) {
            $btn.removeClass('fn-hide')
        } else {
            $btn.addClass('fn-hide')
        }

    }

    $body.on('click', '.fnChooseGist', function () {

        $GISTTRIGGER = $(this);

        JCYJ_TYPE = 'FORM_CHANGE';

        if (!!!PROJECTCODE) {
            PROJECTCODE = $('#projectCode').val()
        }

        if (!!!FORM_CHANGE_LISt) {
            initFormChangeList()
        }

        if (!!!RISK_HANDLE_COUNCIL_SUMMARY_LIST) {
            initRiskHandleCouncilSummaryList()
        }

        // 是否能选择签报
        if (this.getAttribute('hasnopf') == 'IS') {
            FORM_CHANGE_LISt.$box.find('.fnChooseGist2Reply').html('');
            RISK_HANDLE_COUNCIL_SUMMARY_LIST.$box.find('.fnChooseGist2Reply').html('');
        } else {
            FORM_CHANGE_LISt.$box.find('.fnChooseGist2Reply').html('<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">批复</a></span>');
            RISK_HANDLE_COUNCIL_SUMMARY_LIST.$box.find('.fnChooseGist2Reply').html('<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fn-mr20">批复</a></span>');
        }

        if (!!$GISTTRIGGER.parent().find('.fnYLXValue').length) {
            var _showProjected = $GISTTRIGGER.parents('tr').find('.fnDiyName').val().indexOf('担保意向函') >= 0

            toggleProjected(FORM_CHANGE_LISt, _showProjected)
            toggleProjected(RISK_HANDLE_COUNCIL_SUMMARY_LIST, _showProjected)
        }

        var _hadHT = !!$('.fnHTValue').length
        toggleContract(FORM_CHANGE_LISt, _hadHT)
        toggleContract(RISK_HANDLE_COUNCIL_SUMMARY_LIST, _hadHT)
            // 合同？
        if (_hadHT && !!!CONTRACT_LIST) {
            initContractList()
        }

        FORM_CHANGE_LISt.show();
        FORM_CHANGE_LISt.getData(1);

    }).on('click', '.fnChooseGist2Reply', function () {

        // setDecision('PROJECT_APPROVAL', '/projectMg/meetingMg/summary/approval.htm?projectCode=' + PROJECTCODE, '');

        $GISTTRIGGER.parent().find('.fnPF').html('<li><a href="javascript:void(0);" class="fn-right fn-f30 fnDelBasis" w="PF">&times;</a><a class="fn-blue" href="/projectMg/meetingMg/summary/approval.htm?projectCode=' + PROJECTCODE + '">批复</a></li>')
        $GISTTRIGGER.parent().find('.fnPFValue').val(PROJECTCODE)


        if (JCYJ_TYPE == 'FORM_CHANGE') {
            FORM_CHANGE_LISt.hide();
        } else {
            RISK_HANDLE_COUNCIL_SUMMARY_LIST.hide();
        }

    }).on('click', '.fnChooseGist2Projected', function () {

        $GISTTRIGGER.parent().find('.fnYLX').html('<li><a href="javascript:void(0);" class="fn-right fn-f30 fnDelBasis" w="YLX">&times;</a>已立项</li>')
        $GISTTRIGGER.parent().find('.fnYLXValue').val('IS')

        if (JCYJ_TYPE == 'FORM_CHANGE') {
            FORM_CHANGE_LISt.hide();
        } else {
            RISK_HANDLE_COUNCIL_SUMMARY_LIST.hide();
        }

    }).on('click', '.fnChooseGist2Risk', function () {

        JCYJ_TYPE = 'RISK_HANDLE_COUNCIL_SUMMARY';

        FORM_CHANGE_LISt.hide();

        !!CONTRACT_LIST && CONTRACT_LIST.hide();

        RISK_HANDLE_COUNCIL_SUMMARY_LIST.show();
        RISK_HANDLE_COUNCIL_SUMMARY_LIST.getData(1);

    }).on('click', '.fnChooseGist2Form', function () {

        JCYJ_TYPE = 'FORM_CHANGE';

        RISK_HANDLE_COUNCIL_SUMMARY_LIST.hide();

        !!CONTRACT_LIST && CONTRACT_LIST.hide();

        FORM_CHANGE_LISt.show();
        FORM_CHANGE_LISt.getData(1);

    }).on('click', '.fnChooseGist2Contract', function () {

        JCYJ_TYPE = 'FORM_CHANGE';

        RISK_HANDLE_COUNCIL_SUMMARY_LIST.hide();
        FORM_CHANGE_LISt.hide();

        CONTRACT_LIST.show();
        CONTRACT_LIST.getData(1);

    }).on('click', '.fnBasis ul a:not(.fnDelBasis)', function (e) {

        e.preventDefault();
        window.open(this.href);
        // util.openDirect('/projectMg/index.htm', this.href);

    }).on('click', '.fnDelBasis', function () {

        var $this = $(this),
            $ul = $this.parents('ul'),
            _switch = $this.attr('w');

        $this.parent().remove();

        switch (_switch) {

            case 'PF':
                $ul.parent().find('.fnPFValue').val('')
                break

            case 'QB':

                var _select = []

                $ul.find('a.fnDelBasis').each(function (index, el) {
                    _select.push([el.getAttribute('val'), el.getAttribute('t')].join(','))
                })

                $ul.parent().find('.fnQBValue').val(_select.join(';'))

                break

            case 'FKJY':

                var _select = []

                $ul.find('a.fnDelBasis').each(function (index, el) {
                    _select.push([el.getAttribute('sfi'), el.getAttribute('hi'), el.getAttribute('t')].join(','))
                })

                $ul.parent().find('.fnFKJYValue').val(_select.join(';'))

                break

            case 'YLX':

                $ul.parent().find('.fnYLXValue').val('')

                break

            case 'HT':

                var _select = []

                $ul.find('a.fnDelBasis').each(function (index, el) {
                    _select.push([el.getAttribute('val'), el.getAttribute('t')].join(','))
                })

                $ul.parent().find('.fnHTValue').val(_select.join(';'))

                break

        }

    })

    function checkBasis() {

        // 2016.10.26 承销业务决策依据不必填
        if ($('#fnHasApproval').val() == 'NO') {
            return {
                success: true
            }
        }

        var _isPass = true,
            _isPassEq;

        $('.fnBasis').each(function (index, el) {

            var _hasVal = false

            $(this).find('input').each(function (index, el) {
                if (!!el.value) {
                    _hasVal = true
                    return false
                }
            })

            if (!_hasVal) {
                _isPass = false
                _isPassEq = index
                return false
            }

        })

        if (_isPass) {
            return {
                success: true
            }
        } else {
            return {
                success: false,
                message: '第' + (_isPassEq + 1) + '个未选择决策依据'
            }
        }

    }

    // ------ 选择 决策依据 end

    function openOnlineEditing(url) {
        // window.open(url, 'OnlineEditing', 'height=635,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
        window.open(url, 'OnlineEditing');
        //        location.href = url;
    }

    // ------ 初始化 select-diy start

    function initSelectDiy() {

        $('.fnSelectDiy').each(function (index, el) {

            var $fnSelectDiy = $(this)

            $fnSelectDiy.on('click', '.m-select-bar', function () {

                $fnSelectDiy.toggleClass('active').find('.m-select-list-box').find('dt, dd').removeClass('active');

            }).on('click', 'dt', function () {

                $(this).toggleClass('active').next().toggleClass('active')

            }).on('click', 'p', function () {

                $fnSelectDiy.removeClass('active').find('.m-select-text').val(this.innerHTML)
                    .end().find('.m-select-value').val(this.getAttribute('typeid')).trigger('blur')

            });

        })

    }

    // ------ 初始化 select-diy end

    module.exports = {
        initChooseProjectList: initChooseProjectList,
        openOnlineEditing: openOnlineEditing,
        checkBasis: checkBasis,
        initSelectDiy: initSelectDiy
    }

});