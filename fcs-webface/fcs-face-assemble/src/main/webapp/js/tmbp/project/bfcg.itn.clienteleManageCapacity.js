define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //必填集合
    require('zyw/requiredRules');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
  //页面提交后跳转处理
	var showResult = require('zyw/process.result');
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
         _allWhetherNull = 'degree,title,resume,name,sex,leaderReview,staffReview,age',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull,false,{},function(rules,name){
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('token,preUrl,nextUrl',true,{},function(rules,name){
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRules2 = _form.requiredRulesSharp('sex',false,{},function(rules,name){
            rules[name] = {
                checkZh: true,
                messages: {
                    checkZh: '请输入汉字'
                }
            };
        }),
        requiredRules3 = _form.requiredRulesSharp('leaderReview,staffReview',false,{},function(rules,name){
            rules[name] = {
                maxlength: 1000,
                messages: {
                    maxlength: '已超出1000字'
                }
            };
        }),
        rulesAllBefore = {//所有格式验证的基
            age: {
                digits: true,
                maxlength: 3,
                messages: {
                    digits: '有效整数',
                    maxlength: '已超出3字'
                }
            },
            degree:{
                maxlength:10,
                messages:{
                    maxlength:'已超出10字'
                }
            },
            title:{
                maxlength:20,
                messages:{
                    maxlength:'已超出20字'
                }
            },
            resume:{
                maxlength:500,
                messages:{
                    maxlength:'已超出500字'
                }
            }
        },
        rulesAll = $.extend(true,requiredRulesMaxlength,rulesAllBefore,requiredRules1,requiredRules2,requiredRules3);

        ValidataCommon(rulesAll,_form,_allWhetherNull,false,function(res) {
        	showResult(res, hintPopup);
        });

    //上传
    require('Y-htmluploadify');
    $('.fnUpFile').click(function() {
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/investigation/uploadExcel.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function(a, b) {},
            onUploadSuccess: function($this, data, resfile) {
                var _data = JSON.parse(data).datas,
                    _obj = $('#test'),
                    _orderName = _obj.find('tr').attr('orderName');
                    _obj.html('');
                    //console.log(_data)
                    //'<td><input class="text" type="text" name="'+_orderName+'['+(i-1)+'].name"></td>'
                    for (var i = 1; i<_data.length; i++) {
                        var _string = '<tr class="fnNewLine" orderName="leadingTeams">' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].name"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].sex"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].age"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].degree"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].title"></td>' +
                            '<td><input class="text" type="text" name="' + _orderName + '[' + (i - 1) + '].resume"></td>'
                        var _or;
                        (i!=1)?_or = '<td class="fn-text-c"><a class="ui-btn ui-btn-fill ui-btn-green fnDelLine" parentsClass="fnNewLine"><i class="icon icon-del"></i>删除一行</a></td></tr>':_or = '<td></td>';
                        _obj.append(_string+_or)
                        for (var j = 0; j<_data[i].length; j++) {
                            _obj.find('tr').eq(i-1).find('td').eq(j).find('input').val(_data[i][j])
                        };
                    };
            }
            //renderTo: 'body'
        });
       // htmlupload.show();
    });

});