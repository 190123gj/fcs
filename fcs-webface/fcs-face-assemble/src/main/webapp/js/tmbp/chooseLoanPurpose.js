define(function(require, exports, module) {
	
	//模板引擎
	var template = require('arttemplate');
    var util = require('util');
    var URL = '/systemMg/dictionary/data.json?dataCode=';
    var $loanPurpose = $('#loanPurpose');
    var $hid = $('#loanPurposeHidden');
    var tree=[],arrDate=[];
    var loanPurposeTpl = ['<option value="">请选择</option>',
    	'{{each list as item i}}',
    	'      <option value="{{item.id}}" childrenNum="{{item.childrenNum}}" {{if item.selectd}}selected{{/if}}>{{item.dataValue}}</option>',
    	'{{/each}}'
    ].join('');

    function init(code){
        var DATAURL = URL+code

        util.ajax({
            url : DATAURL,
            dataTpye : 'json',
            success : function(res) {
                $loanPurpose.find('.loanPurpose').html(template.compile(loanPurposeTpl)({
                    list: res.data
                }))
                if($hid.val()){
                    $('.loanPurpose').find('option').each(function(i,el){
                        if($hid.val() == $(el).text()){
                            $(el).attr("selected",true)
							return false
                        }else {
                            if($(el).attr('childrenNum')!='0' && !!$(el).attr('childrenNum')) {
                                searchData($(el).val())
                            }
                        }
                    })
                }
            }
        })

        $('body').on('change', '#loanPurpose .loanPurpose', function() {
            var $this = $(this)
            if(!!$this.find("option:selected").attr('childrenNum')){
                $hid.val($this.find("option:selected").text())
            }
            $this.nextAll('.loanPurpose').remove()
            if($this.find("option:selected").attr('childrenNum')!='0' && !!$this.find("option:selected").attr('childrenNum')){
                util.ajax({
                    url : DATAURL+'&parentId='+$(this).val(),
                    dataTpye : 'json',
                    success : function(res) {
                        var temp = '<select class="ui-select fn-w200 loanPurpose">'+template.compile(loanPurposeTpl)({list: res.data})+'</select>'
                        $this.after(temp)
                    }
                })
            }
        })

        function searchData(id){
			util.ajax({
				url : DATAURL+'&parentId='+id,
				dataTpye : 'json',
				success : function(res) {
					tree.push({
						pid:id,
						data:res.data
					})
					for(var i = 0; i < res.data.length; i++){
                        if($hid.val() == res.data[i].dataValue){
                            setData(res.data[i].id)
                            return false
                        }else{
                            if(res.data[i].childrenNum!='0' && !!res.data[i].childrenNum) {
                                searchData(res.data[i].id)
                            }
                        }
					}
				}
			})
		}
		var setDataTemp = ''
		function setData(id){
            searchParentId(id)
            var n=true;
            for(var i=arrDate.length-1; i>-1; i--){
                if(n){
                    $('.loanPurpose').find('option').each(function(m,el){
                        if($(el).val() == arrDate[i].pid) $(el).attr("selected",true)
                        n=false
                    })
                }
                setDataTemp += '<select class="ui-select fn-w200 loanPurpose">'+template.compile(loanPurposeTpl)({list: arrDate[i].data})+'</select>'
            }
            $('.loanPurpose').after(setDataTemp)
		}
        function searchParentId(id){
            for(var i=0; i<tree.length; i++){
				for(var j=0; j<tree[i].data.length; j++){
					if(tree[i].data[j].id == id){
                        tree[i].data[j].selectd = true
                        arrDate.push({
                            pid:tree[i].data[j].parentId,
                            data:tree[i].data
                        })
                        searchParentId(tree[i].data[j].parentId)
					}
				}
			}
        }
	}



    module.exports={
    	init:init
	}
	//----授信用途 start
	//var $loanPurpose = $('#loanPurpose');
	// var loanPurposeObj = {
	// 	main: [{
	// 		type: '项目投资',
	// 		value: '项目投资',
	// 		sub: ''
	// 	}, {
	// 		type: '流动资金',
	// 		value: '流动资金',
	// 		sub: ''
	// 	}, {
	// 		type: '综合授信',
	// 		value: '综合授信',
	// 		sub: ''
	// 	}, {
	// 		type: '信用证开证',
	// 		value: '信用证开证',
	// 		sub: ''
	// 	}, {
	// 		type: '承兑汇票',
	// 		value: '承兑汇票',
	// 		sub: ''
	// 	}, {
	// 		type: '购置固定资产、及其设备等',
	// 		value: '购置固定资产、及其设备等',
	// 		sub: ''
	// 	}, {
	// 		type: '工程保证',
	// 		value: '工程保证',
	// 		sub: 'gcbz'
	// 	}, {
	// 		type: '贸易履约担保',
	// 		value: '贸易履约担保',
	// 		sub: 'mylydb'
	// 	}, {
	// 		type: '司法担保',
	// 		value: '司法担保',
	// 		sub: 'sfdb'
	// 	}],
	// 	sub: {
	// 		gcbz: [{
	// 			type: '投标保证',
	// 			value: '投标保证',
	// 		}, {
	// 			type: '预付款保证',
	// 			value: '预付款保证',
	// 		}, {
	// 			type: '工程履约保证',
	// 			value: '工程履约保证',
	// 		}, {
	// 			type: '业主支持保证',
	// 			value: '业主支持保证',
	// 		}, {
	// 			type: '工程完工保证',
	// 			value: '工程完工保证',
	// 		}, {
	// 			type: '业务延期付款担保',
	// 			value: '业务延期付款担保',
	// 		}],
	// 		mylydb: [{
	// 			type: '保理业务',
	// 			value: '保理业务',
	// 		}, {
	// 			type: '投标保证',
	// 			value: '投标保证',
	// 		}, {
	// 			type: '预付款保证',
	// 			value: '预付款保证',
	// 		}, {
	// 			type: '供货履约保证',
	// 			value: '供货履约保证',
	// 		}, {
	// 			type: '产品维修/质量保证',
	// 			value: '产品维修/质量保证',
	// 		}, {
	// 			type: '买房延期付款担保',
	// 			value: '买房延期付款担保',
	// 		}, {
	// 			type: '政府采购履约保证',
	// 			value: '政府采购履约保证',
	// 		}],
	// 		sfdb: [{
	// 			type: '执行担保',
	// 			value: '执行担保',
	// 		}, {
	// 			type: '财产保全担保',
	// 			value: '财产保全担保',
	// 		}]
	// 	}
	// };
	// var loanPurposeTpl = ['<option value="">请选择</option>',
	// 	'{{each list as item i}}',
	// 	'      <option value="{{item.value}}">{{item.type}}</option>',
	// 	'{{/each}}'
	// ].join('');
	// var $loanPurposeVal = $loanPurpose.find('input[name="loanPurpose"]');
	// //获取二级数据
	// function loanPurposeGetSub(value) {
	// 	for (var i = loanPurposeObj.main.length - 1; i >= 0; i--) {
	// 		if (loanPurposeObj.main[i].value == value) {
	// 			return loanPurposeObj.main[i].sub;
	// 		}
	// 	}
	// }
	//填充数据
	// $loanPurpose.find('#loanPurpose1').html(template.compile(loanPurposeTpl)({
	// 	list: loanPurposeObj.main
	// }));
	// $loanPurpose.on('change', '#loanPurpose1', function() {
    //
	// 	var _sub = loanPurposeGetSub(this.value);
    //
	// 	if (_sub) {
	// 		$loanPurpose.find('#loanPurpose2').html(template.compile(loanPurposeTpl)({
	// 			list: loanPurposeObj.sub[_sub]
	// 		})).removeClass('fn-hide');
	// 		$loanPurposeVal.val('').trigger('blur');
	// 	} else {
	// 		$loanPurpose.find('#loanPurpose2').html('').addClass('fn-hide');
	// 		$loanPurposeVal.val(this.value).trigger('blur');
	// 	}
    //
	// }).on('change', '#loanPurpose2', function() {
    //
	// 	$loanPurposeVal.val($loanPurpose.find('#loanPurpose1').val() + ',' + this.value).trigger('blur');
	//
	// });
	// //还原
	// if (!!$loanPurposeVal.val()) {
	// 	reductionLoanPurpose($loanPurposeVal.val())
	// }
    //
	// function reductionLoanPurpose(value) {
	// 	var _loanPurposeValArr = value.split(',');
	// 	$loanPurpose.find('#loanPurpose1 option[value=' + _loanPurposeValArr[0] + ']').attr('selected', 'selected');
	// 	if (_loanPurposeValArr.length > 1) {
	// 		var _sub = loanPurposeGetSub(_loanPurposeValArr[0]);
	// 		$loanPurpose.find('#loanPurpose2').html(template.compile(loanPurposeTpl)({
	// 			list: loanPurposeObj.sub[_sub]
	// 		})).removeClass('fn-hide').find('option[value=' + _loanPurposeValArr[1] + ']').attr('selected', 'selected');
	// 	}
	// }

});