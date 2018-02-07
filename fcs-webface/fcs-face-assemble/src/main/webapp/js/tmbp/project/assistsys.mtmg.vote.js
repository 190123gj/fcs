var keyName = getJudgeKey();
define(function (require, exports, module) {
    //辅助系统-会议管理-会议列表
    require('zyw/publicPage');
    require('Y-msg');
    require('input.limit');
    var util = require('util');

    var template = require('arttemplate');
    //操作相似的ajax请求，合并数据
    var ajaxObj = {
        dontDis: {
            url: '/projectMg/meetingMg/doVote.json',
            message: '已设置本次不议',
            opn: '本次不议'
        },
        endMeeting: {
            url: '/xxxx',
            message: '已结束当前会议',
            opn: '束当前会议'
        }
    };

    $('#voteList').on('click', '.toUrge', function () {
        //------ 催促投票
        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _$tr = _this.parents('tr');

        util.ajax({
            url: '/projectMg/meetingMg/urgeVote.json',
            data: {
                councilId: _$tr.attr('councilid'),
                projectCode: _$tr.attr('projectcode'),
                id: _$tr.attr('voteId')
            },
            success: function (res) {

                _this.removeClass('ing');
                if (res.success) {
                    Y.alert('消息提醒', '催促投票信息已发送至各评委的站内信息及手机！');
                } else {
                    Y.alert('消息提醒', res.message);
                }
            }
        });

    })
        .on('click', '.dontDis,.endMeeting', function () {
        //------ 本次不议 会议结束
        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var _thisType = util.getJqHasClass(_this, ajaxObj);

        var _$tr = _this.parents('tr');

        Y.confirm('信息提醒', '针对本次上会项目 ' + _$tr.attr('loantype') + ' ，您操作了' + ajaxObj[_thisType].opn + '，是否确认？', function (opn) {

            if (opn == 'yes') {

                util.ajax({
                    url: ajaxObj[_thisType].url,
                    data: {
                        voteResult: _this.attr('code'),
                        councilId: _$tr.attr('councilid'),
                        projectCode: _$tr.attr('projectcode'),
                        id: _$tr.attr('voteId')
                    },
                    success: function (res) {
                        if (res.success) {
                            Y.alert('消息提醒', ajaxObj[_thisType].message, function () {
                                window.location.reload(true);
                            });
                        } else {
                            _this.removeClass('ing');
                            Y.alert('消息提醒', res.message);
                        }
                    }
                });

            } else {

                _this.removeClass('ing');

            }

        });

    })
        .on('click', '.toVote', function () {
            var _$tr = $(this).parents('tr');
            $voteBox.html(template('t-voteBox', {
                projectName: _$tr.attr('projectname'),
                loanType: _$tr.attr('loantype'),
                projectAmount: _$tr.attr('projectamount'),
                projectTimeLimit: _$tr.attr('projecttimelimit'),
                councilId: _$tr.attr('councilid'),
                projectCode: _$tr.attr('projectcode'),
                id: _$tr.attr('voteid'),
            }));
            $('.meeting-vote-btns .btn').show()
            $(this).attr('progress') == 'IN_PROGRESS' ? $('.meeting-vote-btns .btn.attach').hide() : $('.meeting-vote-btns .btn.disagree,.meeting-vote-btns .btn.agree').hide()

            // if ($(this).attr('progress') == 'NOT_BEGIN')  {
            //     $('.meeting-vote-btns .btn').each(function () {
            //         if($(this).hasClass('attach')){
            //         } else {
            //             $(this).remove()
            //         }
            //     })
            // }
    });

    var $voteBox = $('#voteBox');
    $voteBox.on('click', '.btn:not(.active)', function () {

        var $this = $(this);
        $this.addClass('active').siblings().removeClass('active');
        // 带文字
        var _idea = document.getElementById('idea');

        if ($this.text() == '同意' && !!!_idea.value) {
            _idea.value = $this.text()
        }

        if ($this.text() != '同意' && _idea.value == '同意') {
            _idea.value = ''
        }

        // var _textArr = [''];

        // $this.parent().find('.btn').each(function (index, el) {
        //     _textArr.push(el.innerHTML.replace(/<.+?>/gim, ''))
        // });

        // if ($.inArray(idea.value, _textArr) >= 0) {
        //     idea.value = $this.text()
        // }

    }).on('click', '.close', function () {
        $voteBox.empty();
    }).on('click', '.sure', function () {
        var _this = $(this);
        if (_this.hasClass('ing')) {
            return;
        }
        _this.addClass('ing');

        var ideaDom = document.getElementById('idea');

        if (!ideaDom.value.replace(/\s/g, '')) {
            Y.alert('提示', '请填写意见');
            _this.removeClass('ing');
            return;
        }

        if (!$('input[name="voteResult"]:checked').val()) {

            var _textArr = [];
            $('.meeting-vote-btns').find('.btn').each(function (index, el) {
                _textArr.push(el.innerHTML.replace(/<.+?>/gim, ''))
            });

            Y.alert('提示', '请选择投票决议：' + _textArr.join('、'));
            _this.removeClass('ing');
            return;
        }

        //删除意见中的前后空格
        ideaDom.value = ideaDom.value.replace(/(^\s*)|(\s*$)/g, '');

        keyName = getJudgeKey();
        //alert(keyName);
        var submitMessage = "";
        submitMessage += '&key=' + keyName;
        submitMessage += '&' + $voteBox.find('form').serialize();
        util.ajax({
            url: '/projectMg/meetingMg/doVote.json',
            data: submitMessage,
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', '投票成功', function () {
                        // window.location.reload(true);
                        util.doBack()
                    });
                } else {
                    Y.alert('提示', res.message);
                    _this.removeClass('ing');
                }
            }
        });

    }).on('click', '.fnLookProject', function (e) {
        //查看项目详情
        e.preventDefault();
        util.openDirect('/projectMg/index.htm', this.href)
    });



});

function checkSinature() {
    //获取KEY密钥盘的SN序列号
    //var keyName = document.getElementById("SignatureControl").WebGetKeySN();
    //var keyName = "123";

    keyName = getJudgeKey();
    //alert(keyName);
    if (keyName == "") {
        alert("请先插入KEY或选用支持证书的浏览器!");
    } else {
        //alert("验证成功!");
        jQuery.ajax({
            url: '/projectMg/meetingMg/checkJudgeKey.json',
            data: {
                key: keyName
            },
            type: 'post',
            success: function (res) {

                if (res.code == 1) {
                    Y.alert('提示', '校验通过！');
                    //alert("校验通过！");
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });
    }


}

function getJudgeKey() {
    //var SignatureControl = document.getElementById("SignatureControl")
    //var keyName = (SignatureControl && SignatureControl.WebGetKeySN)?SignatureControl.WebGetKeySN():'123';

    var SignatureControl = document.getElementById('SignatureControl');
    var keyName = '';

    try {
        keyName = SignatureControl.WebGetKeySN();
    } catch (e) {}


    //keyName = document.getElementById("SignatureControl").WebGetKeySN();

    //alert(keyName);



    return keyName;
}