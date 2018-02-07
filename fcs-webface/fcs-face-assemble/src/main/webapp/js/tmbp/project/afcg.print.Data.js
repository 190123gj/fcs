define(function(require, exports, module) {
	//增删行
    require('zyw/opsLine');
    //Y对话框
    require('Y-msg');
    //上传图片
    require('Y-htmluploadify');
    //md5加密
    var md5 = require('md5');
    //模板引擎
    var template = require('arttemplate');
    //通用方法
    var util = require('util');
    require('Y-window');

    //------ tab切换 start
    var $step = $('#step'),
        $fnStep = $('.fnStep'),
        lastFTFID = $step.find('li.active').attr('ftf'),
        formObj = {};
    $step.find('li[ftf]').each(function() {
        var ftf = $(this).attr("ftf"),
            formData = $('#' + ftf).serialize(),
            //formId 不参与签名
            signData = formData,
            signData = signData.replace(/formId=\w*&/g, "");
        signData = signData.replace(/&formId=\w*/g, "");
        formObj[ftf] = md5(signData);
    });
    $step.on('click', 'li:not(.active)', function() {
        var _this = $(this),
            _thisFTF = _this.attr('ftf'),
            $_ajaxForm = $('#' + lastFTFID),
            $_nextForm = $('#' + _thisFTF),
            _ajaxData = $_ajaxForm.serialize(),
            //formId 不参与签名
            _signData = _ajaxData,
            _signData = _signData.replace(/formId=\w*&/g, ""),
            _signData = _signData.replace(/&formId=\w*/g, ""),
            _md5_sign = md5(_signData);
        //对比表单签名
        if (!$_ajaxForm.attr('action') || (formObj[lastFTFID] == _md5_sign)) {
            _this.addClass('active').siblings().removeClass('active');
            $fnStep.addClass('fn-hide');
            $_nextForm.removeClass('fn-hide');
            lastFTFID = _thisFTF;
        } else {
            stepSaveAjax($_ajaxForm, _thisFTF, _this, $_nextForm, formObj);
        }
    });

    // $('.fn-jgnr').click(function(){
    //     $('.ul li:last').addClass('active');
    //     $('.ul li:first').removeClass('active');
    //     $('#xmjbqk').hide();
    //     $('#jgnr').show();
    // })
    //------ tab切换 end
	//------ 申请替换 end
});