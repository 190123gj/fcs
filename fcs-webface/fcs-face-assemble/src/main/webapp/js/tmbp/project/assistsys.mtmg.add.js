/**
 * 
 * 新增会议 流程
 * 
 * 会议类型 的变动，直接影响影响 会议名称、讨论项目，间接影响 参会评委(当会议类型改变的时候，还是要清空参会评委才行)、评委人数（根据选择的会议而订）
 *     通过会议类型的value值获取当前类型下的 会议列表（会议名称），同时，每个会议的 评委人数也带出来了
 *
 * 会议名称 的变动，直接影响 参会评委、评委人数
 *     通过option上的自定义属性，更新 最高评委人数、最低评委人数
 *     通过会议名称的value值获取当前的参会评委
 *
 * 列席人员 从BPM取数据
 *
 * 讨论项目 这里有点小麻烦，直接影响 列席人员
 * 
 *     Select 这个插件设计的很单纯，初始化后直接查询数据，请求的地址就不能再改变
 *     Select 初始化参数时，selected已选择参数，由于数组是 赋址 的方式，所以需生成一个全新的数值，避免重新初始化中乱套
 *     会议类型 的变动直接影响着取哪个分类的数据，于是，每次变动就要重新初始化一次
 *     先申明一个空变量 discuss，点击 选择 的时候，判断 discuss 是否不为空，空就初始化，不空就触发弹出选择框
 *     会议类型 的变动的时候，制空 discuss ，回到原点
 *
 *     当讨论项目的数值改变的时候，就去请求最新的类型人员
 *
 * 提交
 *     需要做非空验证
 *     参会评委 的数量不能大于 最高评委人数，不能小于 最低评委人数
 *
 * 从待上会过来，或直接新增，差异不大，对应取数据的时候该怎么就怎么
 *
 * 2017.03.08 会议编号 从服务器获取
 * 
 */
define(function (require, exports, module) {
    //辅助系统-会议管理-新增会议
    var Select = require('zyw/multSelect');

    var util = require('util');

    //获取参数
    var getParam = util.getParam(),
        councilTypeCode = document.getElementById('councilTypeCode'),
        discussSelected = [];
    //已选项目
    var $projectName = $('#projectName'),
        $applyId = $('#applyId'),
        $projectKey = $('#projectKey'),
        $projectCode = $('#projectCode');

    var projectNameVal = ($projectName.val() || '').split(','),
        applyIdVal = ($applyId.val() || '').split(','),
        projectKeyVal = ($projectKey.val() || '').split(','),
        projectCodeVal = ($projectCode.val() || '').split(',');
    for (var i = projectNameVal.length - 1; i >= 0; i--) {
        if (projectNameVal[i]) {
            discussSelected.push({
                applyId: applyIdVal[i],
                projectName: projectNameVal[i],
                projectKey: projectKeyVal[i],
                projectCode: projectCodeVal[i]
            });
        }
    }

    //选择时间
    $('#startTime').on('click', function () {
        var self = this;
        laydate({
            elem: '#' + this.id,
            event: 'focus',
            format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
            festival: false,
            istime: true,
            min: getToday(),
            start: getToday()
        });
    });

    function getToday() {
        var _t = new Date();
        return [
            _t.getFullYear(),
            '-',
            _t.getMonth() + 1,
            '-',
            _t.getDate()
            // ' ',
            // _t.getHours(),
            // ':',
            // _t.getMinutes(),
            // ':',
            // _t.getSeconds()
        ].join('')
    }

    //------ 列席人员 start
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
    // 初始化弹出层
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=false', {
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

    var participantsName = document.getElementById('participantsName'),
        participantsId = document.getElementById('participantsId'),
        participantsKey = document.getElementById('participantsKey');

    // 添加选择后的回调，以及显示弹出层
    $('#membersBtn').on('click', function () {

        singleSelectUserDialog.obj.selectUsers.selectUserIds = participantsId.value;
        singleSelectUserDialog.obj.selectUsers.selectUserNames = participantsName.value;

        singleSelectUserDialog.init(function (relObj) {

            participantsId.value = relObj.userIds;
            participantsName.value = relObj.fullnames;

            var _arr = relObj.userIds ? relObj.userIds.split(',') : [],
                _arrs = [];

            for (var i = 0; i < _arr.length; i++) {
                _arrs.push('0');
            }

            participantsKey.value = _arrs.length ? _arrs.toString() : '';

        });

    });

    //------ 列席人员 end
    //
    var $councilType = $('#councilType');
    $councilType.on('change', function () {
        var _code = this.value,
            _html = '';
        JUDGESARR = [];
        // 参会评委
        if (!!_code) {
            util.ajax({
                url: '/projectMg/meetingMg/findCouncilJudgesFromCouncilType.json?councilTypeId=' + _code,
                success: function (res) {
                    if (res.success) {

                        var _s = '';

                        $.each(res.data.judges, function (index, obj) {
                            _s += '<option value="' + obj.judgeId + '">' + obj.judgeName + '</option>';
                            JUDGESARR.push({
                                id: obj.id,
                                judgeId: obj.judgeId,
                                judgeName: obj.judgeName,
                                checked: false
                            });
                        });

                        $fnChooseHost.html(_s).trigger('change');
                    } else {
                        Y.alert('提示', res.message);
                        document.getElementById('judgesList').innerHTML = _html;
                    }
                }
            });
        } else {
            document.getElementById('judgesList').innerHTML = _html;
        }
        // 最大最小的数据
        var _$option = $(this).find('option[value="' + _code + '"]');
        document.getElementById('majorNum').value = _$option.attr('majorNum');
        document.getElementById('lessNum').value = _$option.attr('lessNum');

        // 清空讨论项目
        if (!!discuss) {
            discuss.$box.remove();
            discuss = null;
            $projectName[0].value = '';
            $applyId[0].value = '';
            $projectKey[0].value = '';
        }

    });
    //------ 选择会议类型 start
    councilTypeCode.onchange = function () {

        var _code = this.value;
        util.ajax({
            url: '/baseDataLoad/councilTypeList.json?pageSize=9999&typeCode=' + _code,
            success: function (res) {
                var _html = '';
                if (res.success) {
                    var _list = res.data.pageList;
                    _html += '<option value="">请选择会议</option>';
                    for (var i = 0; i < _list.length; i++) {
                        _html += '<option value="' + _list[i].typeId + '" majorNum="' + _list[i].majorNum + '" lessNum="' + _list[i].lessNum + '">' + _list[i].typeName + '</option>';
                    }
                } else {
                    Y.alert('提示', res.message);
                }
                $councilType[0].innerHTML = _html;
            }
        });

        // 清空讨论项目
        if (!!discuss) {
            discuss.$box.remove();
            discuss = null;
            $projectName[0].value = '';
            $applyId[0].value = '';
            $projectKey[0].value = '';
        }
        document.getElementById('judgesList').innerHTML = '';

    };
    //------ 选择会议类型 start

    //------ 讨论项目 start
    /**
     * 讨论项目根据 会议类型而改变（councilCode值不同，所以需要初始化不同的次数）
     * 会议类型改变 discuss 被清空，并且弹出层也删了
     */
    var discuss; //一个标示 会议名称改变，变为空

    $('#discussBtn').on('click', function () {

        if (!!!discuss) {
            //如果项目讨论为空，初始化
            discuss = new Select('上会项目');

            discuss.init({
                input: ['applyId', 'projectName', 'projectCode'],
                btn: '#discussBtn',
                markKey: {
                    val: 'projectCode',
                    txt: 'projectName'
                },
                ajaxUrl: '/baseDataLoad/councilApply.json?ruleCheck=YES&councilCode=' + councilTypeCode.value + '&typeId=' + $councilType.val(),
                selected: $.extend(true, [], discussSelected),
                listTitle: '待上会项目列表',
                close: function (self) {

                    //如果有值，进行过滤 新增加的值都已0代替
                    //通过具有唯一性的 applyId进行过滤，记录code的下标，还原原code
                    var _codeArr = self.result().projectCode,
                        _applyArr = self.result().applyId || [],
                        _index = -1, //避免增删后，已存在的code被抹除
                        _isOld = false;

                    //applyIdVal
                    //projectKeyVal

                    for (var i = 0; i < _applyArr.length; i++) {

                        for (var j = 0; j < applyIdVal.length; j++) {

                            if (applyIdVal[j] == _applyArr[i]) {
                                _isOld = true;
                                _index = j;
                            }

                        }

                        if (!_isOld) {
                            _codeArr[i] = 0;
                        } else {
                            _codeArr[i] = projectKeyVal[_index];
                        }

                        _index = -1;
                        _isOld = false;

                    }

                    $applyId[0].value = self.result().applyId || '';
                    $projectName[0].value = self.result().projectName || '';
                    $projectKey[0].value = _codeArr || '';
                    $projectCode[0].value = self.result().projectCode || '';

                    $applyId.trigger('change');

                    self.hide();

                }
            }).query(function (self) {

                //针对编辑状态，追加已存在的项目
                if (!!document.getElementById('fnStatus').value) {
                    var _s = '';
                    for (var i = 0; i < discussSelected.length; i++) {
                        _s += '<li class="fn-csp li fnChoose" applyId="' + discussSelected[i].applyId + '" projectName="' + discussSelected[i].projectName + '" projectCode="' + discussSelected[i].projectCode + '" val="' + discussSelected[i].projectCode + '">[' + discussSelected[i].projectCode + ']' + discussSelected[i].projectName + '</li>';
                    }

                    self.listHtml += '<ul class="ul">' + _s + '</ul>';
                    self.$box.find('.multiple').eq(0).find('.multiple-list').html(self.listHtml);
                }

                self.show();
            });
        } else {
            document.getElementById('discussBtns').click();
        }
    });



    //------ 讨论项目 end
    //
    //------ 更新列席人员 start
    $applyId.on('change', function () {
        if (!!!this.value) {
            return;
        }

        getCouncilCode();

        util.ajax({
            url: '/projectMg/meetingMg/findParticipants.json',
            data: {
                projectsCode: $projectCode.val(),
                participantsName: participantsName.value,
                participantsId: participantsId.value,
                participantsKey: participantsKey.value
            },
            success: function (res) {
                if (res.participantsName) {
                    participantsName.value = res.participantsName;
                    participantsId.value = res.participantsId;
                    participantsKey.value = res.participantsKey;
                }
            }
        });

    });
    //------ 更新列席人员 end
    //
    //
    //
    //提交
    var $addForm = $('#addForm');

    $('#submit').on('click', function () {

        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var $fnInput = $('.fnInput:visible');

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

        if (!!!document.getElementById('applyId').value) {
            Y.alert('提示', '请选择讨论项目/事项', function () {
                $fnInput.eq(_isPassEq).focus();
            });
            _this.removeClass('ing');
            return;
            return
        }

        // 会议时间

        if ((new Date(document.getElementById('startTime').value)).getTime() <= (new Date()).getTime()) {
            Y.alert('提示', '会议时间必须大于当前时间');
            _this.removeClass('ing');
            return;
        }

        var majorNum = +document.getElementById('majorNum').value,
            lessNum = +document.getElementById('lessNum').value,
            hasMaXLimit = (majorNum == 0) ? false : true;

        lessNum = lessNum ? lessNum : 1;

        //参会评委是否有超限
        var _judgesNum = $('.fnJudges:checked').length;

        if (_judgesNum < lessNum) {
            Y.alert('提示', '当前参会评委人数小于会议最低评委人数');
            _this.removeClass('ing');
            return;
        }

        if (hasMaXLimit && _judgesNum > majorNum) {
            Y.alert('提示', '当前参会评委人数大于会议最高评委人数');
            _this.removeClass('ing');
            return;
        }

        resetName();

        util.ajax({
            url: $addForm.attr('action'),
            data: $addForm.serialize(),
            success: function (res) {

                if (res.success) {
                    Y.alert('提示', '已提交', function () {
                        util.direct('/projectMg/meetingMg/councilList.htm');
                        // window.location.href = '/projectMg/meetingMg/councilList.htm';
                    });
                } else {
                    Y.alert('提示', res.message);
                    _this.removeClass('ing');
                }

            }
        });

    });



    //------ 数组Name命名 start
    function resetName(orderName) {

        var _tr;

        if (orderName) {
            _tr = $('label[orderName=' + orderName + ']');
        } else {
            _tr = $('label[orderName]');
        }

        _tr.each(function () {

            var tr = $(this),
                _orderName = tr.attr('orderName'),
                index = tr.index();

            tr.find('[name]').each(function () {

                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.indexOf('.') + 1);
                }

                name = _orderName + '[' + index + '].' + name;

                _this.attr('name', name);

            });

        });
    }
    //------ 数组Name命名 end


    //------ 选择线上会议 start
    var $fnIsOLMeeting = $('#fnIsOLMeeting');
    $fnIsOLMeeting.on('click', function () {

        if ($fnIsOLMeeting.prop('checked')) {

            // $('#startTime').prop('disabled', 'disabled').parent().addClass('fn-hide');
            $('#councilPlace').prop('disabled', 'disabled').parent().addClass('fn-hide');

        } else {

            // $('#startTime').removeProp('disabled').parent().removeClass('fn-hide');
            $('#councilPlace').removeProp('disabled').parent().removeClass('fn-hide');

        }

    });

    if ($fnIsOLMeeting.prop('checked')) {
        // $('#startTime').prop('disabled', 'disabled').parent().addClass('fn-hide');
        $('#councilPlace').prop('disabled', 'disabled').parent().addClass('fn-hide');
    }
    //------ 选择线上会议 end

    //------ 选择主持人 start

    var $fnChooseHost = $('#fnChooseHost'),
        $compereName = $fnChooseHost.parent().find('input'),
        JUDGESARR = [],
        HOSTID = $fnChooseHost.val();

    function getJudgesArr() {

        JUDGESARR = [];

        $('[orderName="judges"]').each(function (index, el) {

            var $this = $(this),
                $judgeId = $this.find('[name="judgeId"]');
            JUDGESARR.push({
                id: $this.find('[name="id"]').val(),
                judgeName: $this.text().replace(/\s/g, ''),
                judgeId: $judgeId.val(),
                checked: $judgeId.prop('checked')
            });

        });
    }

    getJudgesArr();

    $('#judgesList').on('click', '.fnJudges', function () {
        getJudgesArr();
    });

    $fnChooseHost.on('change', function () {
        setTimeout(function () {
            doRenderJudges();
        }, 10);
    }).trigger('change');

    function doRenderJudges() {

        var _html = '';

        $.each(JUDGESARR, function (index, obj) {

            if (obj.judgeId == $fnChooseHost.val()) {

                _html += ['<label class="fn-csp fn-mr20" orderName="judges">',
                    '<input class="fnJudges" type="checkbox" name="judgeId" value="' + obj.judgeId + '" checked disabled >' + obj.judgeName,
                    '<input type="hidden" name="judgeId" value="' + obj.judgeId + '">',
                    '<input type="hidden" name="judgeName" id="judgeName" value="' + obj.judgeName + '">',
                    obj.id ? '<input type="hidden" name="id" value="' + obj.id + '">' : '',
                    '</label>'
                ].join('');

                $compereName.val(obj.judgeName);

            } else {

                _html += ['<label class="fn-csp fn-mr20" orderName="judges">',
                    '<input class="fnJudges" type="checkbox" name="judgeId" value="' + obj.judgeId + '" ' + (obj.checked ? ' checked ' : '') + '>' + obj.judgeName,
                    '<input type="hidden" name="judgeName" id="judgeName" value="' + obj.judgeName + '">',
                    obj.id ? '<input type="hidden" name="id" value="' + obj.id + '">' : '',
                    '</label>'
                ].join('');

            }

            document.getElementById('judgesList').innerHTML = _html;

        });

        // var _html = '';

        // $.each(JUDGESARR, function(i, obj) {

        //  if (obj.judgeId != HOSTINFO.userId) {

        //      _html += ['<label class="fn-csp fn-mr20" orderName="judges">',
        //          '<input class="fnJudges" type="checkbox" name="judgeId" value="' + obj.judgeId + '">' + obj.judgeName,
        //          '<input type="hidden" name="judgeName" id="judgeName" value="' + obj.judgeName + '">',
        //          '<input type="hidden" name="id" value="' + obj.id + '">',
        //          '</label>'
        //      ].join('');

        //  }

        // });

        // document.getElementById('judgesList').innerHTML = _html;

    }

    //------ 选择主持人 end

    //------ 会议编号 获取 start

    function getCouncilCode() {

        // councilTypeCode.value
        if (!!!$applyId.val()) {
            return
        }

        $.post('/projectMg/meetingMg/getNextCouncilCode.json', {
            typeCode: councilTypeCode.value,
            applyIds: $applyId.val()
        }, function (res, textStatus, xhr) {

            if (res.success) {
                document.getElementById('councilCode').value = res.councilCode
                document.getElementById('councilCodeOld').value = res.councilCode
            }

        })

    }

    //------ 会议编号 获取 end

});