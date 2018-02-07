define(function(require, exports, module) {

    //字数提示
    require('zyw/hintFont');
    //必填集合
    require('zyw/requiredRules');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    //验证
    var _form = $('#form'),
        requiredRules = _form.requiredRulesSharp('startNo,endNo,receiptMan,projectName,receiveManName,profiles', false, {}, function(rules, name, This) {

            rules[name] = {
                required: true,
                maxlength: 20,
                messages: {
                    required: '必填',
                    maxlength: '超出20字',
                }
            };

        }),
        rulesAllBefore = {
            profiles: {
                maxlength: 1000,
                messages: {
                    maxlength: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;超出1000字'
                }
            },
            endNo: {
                digits: true,
                comparisonIntersection: true,
                min: function(This) {

                    var _start;

                    _start = parseInt($(This).parent().attr('start'));

                    if (!_start) return false;

                    return _start;

                },
                max: function(This) {

                    var _end;

                    _end = parseInt($(This).parent().attr('end'));

                    if (!_end) return false;
                    return _end;

                },
                messages: {
                    digits: '请输入正整数',
                    min: '不能小于起编号',
                    max: '不能大于最大止编号',
                    comparisonIntersection: '编号相交'
                }
            },
            startNo: {
                digits: true,
                comparisonIntersection: true,
                messages: {
                    digits: '请输入正整数',
                    comparisonIntersection: '编号相交'
                }
            }
        },
        _rulesAll = $.extend(true, requiredRules, rulesAllBefore),
        SPcommon = require('zyw/project/assistsys.sp.SPcommon');


    SPcommon({

        form: _form,
        rulesAll: _rulesAll

    })

    //选人
    //选择授信用途
    require('zyw/chooseLoanPurpose');

    //现场调查人员弹窗
    var scope = "{type:\"system\",value:\"all\"}";
    var selectUsers = {
        selectUserIds: $('#xxxID').val(),
        selectUserNames: $('#xxx').val()
    }
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
        'title': '单选用户',
        'width': 850,
        'height': 460,
        'scope': scope,
        'selectUsers': selectUsers,
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });

    // window.frameElement.dialog=singleSelectUserDialog;
    $('#xxxBtn').on('click', function() {

        selectUsers.selectUserIds = $('#xxxID').val();
        selectUsers.selectUserNames = $('#xxx').val();

        singleSelectUserDialog.init(function(relObj) {

            for (var i = 0; i < relObj.userIds.length; i++) {
                $('#xxxID').val(relObj.userIds);
                $('#xxx').val(relObj.fullnames);
            }

            $('input.start').eq(0).focus().blur();

        });

    });

    $('input.start').eq(0).focus().blur();

    //起止号
    var popupWindow = require('zyw/popupWindow');

    $('body').on('click', '.start', function(event) {

        var $this, $parents;

        $this = $(this);
        $parents = $this.parent();

        $this.blur();

        fundDitch = new popupWindow({

            YwindowObj: {
                content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', //find关闭弹窗对象
                dragEle: '.newPopup dl dt' //find拖拽对象
            },

            ajaxObj: {
                url: '/projectMg/specialPaper/chooseNo.htm?type=PROVIDE_DEPT',
                type: 'post',
                dataType: 'json',
                data: {
                    pageSize: 6,
                    pageNumber: 1
                }
            },

            pageObj: { //翻页
                clickObj: '.pageBox a.btn', //find翻页对象
                attrObj: 'page', //翻页对象获取值得属性
                jsonName: 'pageNumber', //请求翻页页数的dataName
                callback: function($Y) {



                }
            },

            callback: function($Y) {

                $Y.wnd.on('click', 'a.choose', function(event) {

                    var _this, _parents, _startNo, _endNo, _id;

                    _this = $(this);
                    _parents = _this.parents('tr');
                    _id = _parents.attr('idName');
                    _startNo = _parents.find('td:eq(1)').text();
                    _endNo = _parents.find('td:eq(2)').text();

                    $this.val(_startNo);
                    $this.siblings('.end').attr('Placeholder', '止编号范围为' + _startNo + '-' + _endNo);
                    $this.siblings('.id').val(_id);
                    $parents.attr({
                        'start': _startNo,
                        'end': _endNo
                    });
                    $('.start,.end').change();
                    $Y.close();

                });

            },

            console: false //打印返回数据

        });

    });

    var searchForDetails = require('zyw/searchForDetails');
    //项目编号
    if (document.getElementById('chooseBtn')) {
        searchForDetails({
            ajaxListUrl: '/baseDataLoad/queryProjects.json',
            ajaxListUrlFun: function() {
                return 'busiManagerId=' + document.getElementById('xxxID').value
            },
            btn: '#chooseBtn',
            codeInput: 'projectCode',
            tpl: {
                tbody: ['{{each pageList as item i}}',
                    '    <tr class="fn-tac m-table-overflow onbreaks">',
                    '        <td class="item onbreak" id="{{item.name}}">{{item.projectCode}}</td>',
                    '        <td class="onbreak" title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+"..":item.customerName}}</td>',
                    '        <td class="onbreak" title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+"..":item.projectName}}</td>',
                    '        <td class="onbreak">{{item.busiTypeName}}</td>',
                    '        <td class="onbreak">{{item.amount}}</td>',
                    '        <td class="onbreak"><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                    '    </tr>',
                    '{{/each}}'
                ].join(''),
                form: ['项目名称：',
                    '<input class="ui-text fn-w160" type="text" name="projectName">',
                    '&nbsp;&nbsp;',
                    '客户名称：',
                    '<input class="ui-text fn-w160" type="text" name="customerName">',
                    '&nbsp;&nbsp;',
                    '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                ].join(''),
                thead: ['<th width="100">项目编号</th>',
                    '<th width="120">客户名称</th>',
                    '<th width="120">项目名称</th>',
                    '<th width="100">授信类型</th>',
                    '<th width="100">授信金额(元)</th>',
                    '<th width="50">操作</th>'
                ].join(''),
                item: 6
            },
            ajaxDetailsUrl: '/baseDataLoad/loadProjectData.json',
            fills: [{
                type: 'html',
                key: 'customerName',
                select: '#customerName'
            }, {
                type: 'html',
                key: 'projectName',
                select: '#projectName'
            }, {
                type: 'html',
                key: 'contractNumber',
                select: '#contractNumber'
            }, {
                type: 'html',
                key: 'creditType',
                select: '#creditType'
            }, {
                type: 'html',
                key: 'guarantyPeriodStart',
                select: '#guarantyPeriodStart'
            }, {
                type: 'html',
                key: 'guarantyPeriodEnd',
                select: '#guarantyPeriodEnd'
            }, {
                type: 'html',
                key: 'agencies',
                select: '#agencies'
            }, {
                type: 'html',
                key: 'amount',
                select: '#amount'
            }],
            callback: function(_data) {

                $('#customerName').val(_data.customerName);
                $('#projectName').val(_data.projectName);
                $('#customerName').text(_data.customerName);

            }
        });
    }

})