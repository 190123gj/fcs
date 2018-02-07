/*
* @Author: yanyang
* @Date:   2016-07-06 10:52:24
* @Last Modified by:   yanyang
* @Last Modified time: 2016-07-06 14:20:36
*/
define(function(require, exports, module) {
    require('validate');
    require('validate.extend');
    require('zyw/upAttachModify');

    var util = require('util');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    // 表单验证 

    var $addForm = $('#form');

    function formSubmit(type) {
        document.getElementById('state').value = type || 'SAVE';
        var checkStatus = document.getElementById('checkStatus').value = type ? 1 : 0;
        util.resetName();

        util.ajax({
            url: '/assetMg/assessCompanyApply/saveAssessCompanyEvaluate.htm',
            data: $addForm.serialize(),
            success: function(res) {
                console.log(res);
                if (res.success) {
                    Y.alert('提醒', res.message);
                    window.location = '/assetMg/assessCompanyApply/list.htm';
                }else{
                    Y.alert('提醒', res.message);
                }
            }
        });
    }

    // 星星评分

    function stars(obj){
        var self = obj,
            selfIndex = self.index(),
            selfPar = self.parents('.starsIcon'),
            starsSpan = selfPar.find('span'),
            starsNum = selfPar.find('.starsNum');
        selfPar.find('i').removeClass('active');
        if( selfIndex >= 0){
            for ( i = 0 ; i <= selfIndex ; i++ ){
                selfPar.find('i').eq(i).addClass('active');
            }
        }
        starsSpan.text( self.attr('title') );    
        starsNum.val( selfIndex + 1 );
    }

    function starsMouse(obj){
        var self = $(this),
            selfPar = self.parents('.starsIcon'),
            starsNum = self.parents().find('.starsNum').val();
    }

    $('.starsIcon i').click(function(){
        stars( $(this));
        var isClear = $(this).parents('.pfStarsBox').find('.isClear');
        if(isClear.attr('checked')=='checked'){
            isClear.attr('checked',false);
            isClear.parents('.pfStarsBox').find('textarea').removeClass('fnInput').slideUp(400);
        }
    })

    // 本次不评
    $('.isClear').click(function(event) {
        var self = $(this),
            selfP = self.parents('.m-item').find('textarea'),
            pfStarsBox = self.parents('.pfStarsBox');
        if(self.attr('checked')=='checked'){
            // 必填本次不填理由
            selfP.slideDown(400).addClass('fnInput');
            // 评分归0
            pfStarsBox.find('i').each(function(){
                $(this).removeClass('active');
            })
            pfStarsBox.find('.starsNum').each(function(index, el) {
                $(this).val(0);
            });

        }else{
            selfP.slideUp(400).removeClass('fnInput');
        }
    });


    // 编辑还原
    ziZhi();

    function ziZhi(){
        var ziZhi = $('.fnziZhi'),
            chk_value = '',
            qualityLand = $('#qualityLand').val(),
            qualityHouse = $('#qualityHouse').val(),
            qualityAssets = $('#qualityAssets').val(),
            Credentials = $('#Credentials'),
            allChecked = [];
        ziZhi.find('input[type="hidden"]').each(function(){
            var self = $(this).val();
            allChecked.push(self);
        })
        if (qualityLand == '') {
            qualityLand = '';
        }

        if (qualityHouse == '') {
            qualityHouse = '';
        }
        if (qualityLand != "") {
            chk_value += '土地（' + qualityLand + '）;';
        }
        if (qualityHouse != "") {
            chk_value += '房产（' + qualityHouse + '）;';
        }
        if (qualityAssets != "") {
            chk_value += '资产（' + qualityAssets + '）;';
        }

        chk_value = chk_value.substring(0,chk_value.length-1);

        $('#alltext').val(chk_value);
        var i,
            a = [],
            Credentials = $('#Credentials'),
            allLenth = allChecked.length;

        a = qualityAssets.split(',');

        Credentials.find('tr:first() input').each(function(index, el) {
            if ($(this).val() == allChecked[0]) {
                $(this).attr('checked', 'checked');
            }
        })

        Credentials.find('tr:nth-child(2) input').each(function(index, el) {
            if ($(this).val() == allChecked[1]) {
                $(this).attr('checked', 'checked');
            }
        });

        Credentials.find('tr:last() input').each(function(index, el) {

            if  ($(this).val() == a[0] || $(this).val() == a[1] || $(this).val() == a[2] ) {
                $(this).attr('checked', 'checked');
            }
        });

    }

    $('#submit').click(function(){

        var pfStarsBoxL = $('body').find('.pfStarsBox').length,
            trueSum = 0,
            falseSum = 0,
            i;

        for( i = 0 ; i < pfStarsBoxL ; i++ ){

            var _this = $('.pfStarsBox').eq(i);

            if(starsVlidate(_this)){
                trueSum += 1;                
            }else{
                falseSum += 1;
            }
        }

        if( trueSum == pfStarsBoxL ){
            formSubmit('SUBMIT');
        }else{
            Y.alert('提醒', '请对所有评分项进行评分或填写本次不评说明原因');
        }
    })

    if($('#fnView').val() == "view" ){
        var i,j,
            box = $('.pfStarsBox');
        box.find('.pfStars').each(function(){
            var self = $(this),
                spanText = self.find('span'),
                selfVal = self.find('.starsNum').val();
            for( i = 0; i < selfVal ; i++ ){
                self.find('i').eq(i).addClass('active');
            }
            var spanTextVal = self.find('i.active').eq( selfVal - 1 ).attr('title');
            spanText.text(spanTextVal);
        })
    }

    function starsVlidate(obj){
        var self = obj,
            clearContent = self.find('#clearContent').val(),
            j = 6 ;
        self.find('.starsNum').each(function(){
            if( $(this).val() != 0){
                j -= 1;                
            }
        })
        
        if( j != 0 ){
            if( clearContent != '' ){
                return true;
            }else{                
                return false;
            }            
        }else{
            return true;
        }
    }

            
})