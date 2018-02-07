define(function(require, exports, module) {

    //弹窗提示
    var hintPopup = require('zyw/hintPopup');

	function submited(_submited,noFulfil){

		if(!_submited) return false;
        var _arr = _submited.split(''),
            _index = $('.apply-step .item.active').index()+1;
        var version = $("#version").val();

        for(var i=0; i<_arr.length; i++){
        	//老数据提交数据校验
        	if ((i == 3 || i == 4|| i == 6 || i == 7 || i == 8 || i == 9) && _arr.length == 10 && !version) {
        		_arr[i] = 1;
        	}
            if(_arr[i]=='0'&&i!=7 ){
                if($('.apply-step .item').eq(i-1).find('em.remind').length){
                    $('.apply-step .item').eq(i-1).find('em.remind').html('<b>请完整该页必填项</b>');
                }else{
                	//当校验信息没填写，提示
                	if (!version && i == 5) {
                		 $('.apply-step .item').eq(2).append('<em class="remind"><b>请完整该页必填项</b></em>');
                	} else {
                		$('.apply-step .item').eq(i-1).append('<em class="remind"><b>请完整该页必填项</b></em>');
                	}
                	
                }
            }else if(_arr[i]=='1'){
                $('.apply-step .item').eq(i-1).find('em.remind').remove();
            }

            if(_arr[i]=='0'&&i==0&&!noFulfil){
                hintPopup('尽职调查报告申明必填项未填写完整');
            }
        }

        return _arr[_index]


	}

	module.exports = submited

})