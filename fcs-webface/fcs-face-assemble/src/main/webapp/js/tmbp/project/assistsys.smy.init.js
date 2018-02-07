define(function(require, exports, module) {

    //先手后手
    $('body').on('change', '[name="chargePhase"]', function() {

        var $this, textContent, $test20;

        $this = $(this);
        $test20 = $('#t-test20');
        textContent = $this.val() == 'AFTER' ? '后' : '前';

        $('.chargePhaseChange').text(textContent);
        $test20.text($test20.text().replace(/前|后/, textContent));

    }).find('[name="chargePhase"]').change();

    // 选择渠道 start

    var getChannel = require('zyw/getChannel');

    var CHANNEL_LIST = new getChannel('#fnChooseChannelBtn');
    CHANNEL_LIST.init({
        callback: function($a) {

            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelName').val($a.attr('ccname')).blur();
            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelId').val($a.attr('ccid'))
            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelCode').val($a.attr('cccode'))
            $CHOOSE_CHANNEL_BOX.find('.fnChooseChannelType').val($a.attr('cctype'))

        }
    });

    var $CHOOSE_CHANNEL_BOX;

    $('body').on('click', '.fnChooseChannelBtn', function() {

        $CHOOSE_CHANNEL_BOX = $(this).parent()
        CHANNEL_LIST.query()

    })

    // 选择渠道 end

    //改变多选按钮位置beforeDay
    $('body').on('blur', '.beforeDay:visible', function(event) {

        var _this = $(this),
            _next = _this.next();
        if (_next.length && _next.is(':visible')) {

            var _left = _next.offset().left,
                _width = parseFloat(_next.css('width'));

            $('.addition span').css({
                'left': _width + _left + 140 + 'px'
            });

            $('.addition a').css({
                'left': -(_width + _left - 300) + 'px'
            });

        } else {

            $('.addition span').css({
                'left': '560px'
            });

            $('.addition a').css({
                'left': '-110px'
            });

        }

    });

    $('body').on('change', '.checkboxTime', function(event) {

        $('.beforeDay:visible').blur();

    });


    // //选择授信用途
    // require('input.limit');

    //选择授信用途
    require('zyw/chooseLoanPurpose');

    //现场调查人员弹窗
    var scope = "{type:\"system\",value:\"all\"}";
    var selectUsers = {
        selectUserIds: $('#xxxID').val(),
        selectUserNames: $('#xxx').val()
    }
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
        'title': '多选用户',
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

        });

        $('input').eq(0).focus().blur();

    });

    $('input').eq(0).focus().blur();

    // $('body').on('click', 'input,textarea', function() {

    //  this.blur();
    //  this.focus();

    // });


    var submitedFun = require('zyw/submited');

    //移除页签中第一个页面或最后一个页面中不该存在的按钮
    var _navLength = $('.apply-step .item').length,
        _activeIndex = $('.apply-step .item.active').index();

    if (_navLength > 1) {

        if (_activeIndex == 0) {

            $('[branch="submitPrev"]').remove();
            $('[branch="submit"]').remove();

        } else if (_activeIndex == _navLength - 1) {

            $('[branch="submitNext"]').remove();

        } else {

            $('[branch="submit"]').remove();

        }


    } else if (_navLength == 1) {

        $('[branch="submitPrev"]').remove();
        $('[branch="submitNext"]').remove();

    }


    function smyInit(objList) {

        //是否有过提交动作
        var _href = window.location.href,
            _indexOf = _href.indexOf('submited');

        if (_indexOf == -1) return false;

        var _hrefObj = _href.substring(_indexOf),
            _submitedVal = _hrefObj.substring(_hrefObj.indexOf('=') + 1),
            _checkStatus = submitedFun(1 + _submitedVal, true);

        if (_submitedVal != undefined) {

            $('#step').attr('submitedAll', _submitedVal);

            if (_checkStatus == 0) {

                objList['form'].submit();

            }

        }
    }

    module.exports = smyInit

});