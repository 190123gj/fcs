/*
* @Author: erYue资产受让
* @Date:   2016-07-06 10:52:24
* @Last Modified by:   erYue
* @Last Modified time: 2016-07-06 14:20:36
*/
define(function(require, exports, module) {
	 require('validate');
	 require('validate.extend');
	 require('zyw/upAttachModify');
	 require('input.limit');

	  var getList = require('zyw/getList'),
	       util = require('util');  
    
    // 选择项目
    var _getList = new getList();
    _getList.init({
        title: '清收项目',
        ajaxUrl: '/baseDataLoad/loanAssetsTransferee.json',
        btn: '.chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item" id="{{item.name}}">{{item.projectCode}}</td>', 
                '        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
                '        <td>{{(item.transferCompany && item.transferCompany.length > 6)?item.transferCompany.substr(0,6):item.transferCompany}}</td>',
                '        <td>{{(item.isTrusteeLiquidate && item.isTrusteeLiquidate.length > 6)?item.isTrusteeLiquidate.substr(0,6):item.isTrusteeLiquidate}}</td>',
                '        <td>{{item.liquidateTime}}</td>',
                '        <td>{{(item.liquidatePrice && item.liquidatePrice.length > 6)?item.liquidatePrice.substr(0,6):item.liquidatePrice}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" projectName="{{item.projectName}}" transferCompany="{{item.transferCompany}}" projectName="{{item.projectName}}" href="javascript:void(0);">选择</a></td>', //跳转地址需要的一些参数
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目名称：',
                '<input class="ui-text fn-w160" type="text" name="projectName">',
                '&nbsp;&nbsp;',
                '转让单位：',
                '<input class="ui-text fn-w160" type="text" name="transferCompany">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: [
                '<th width="100">项目编号</th>',
                '<th width="120">项目名称</th>',
                '<th width="120">转让单位</th>',
                '<th width="100">是否清收</th>',
                '<th width="100">清收时间</th>',
                '<th width="100">清收价格(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 7
        },
        callback: function($a) {
            //跳转地址
            var projectName = document.getElementById('projectName'),
                transferCompany = document.getElementById('transferCompany'),
                fnClear = document.getElementById('fnClear');
                chooseBtn = document.getElementById('chooseBtn');

        	document.getElementById('projectCode').value = $a.attr('projectCode');
            projectName.value = $a.attr('projectName');
            transferCompany.value = $a.attr('transferCompany');
            projectName.setAttribute("readonly","readonly");
            transferCompany.setAttribute("readonly","readonly");
            //chooseBtn.className = 'ui-btn ui-btn-fill ui-btn-blue chooseBtn fn-hide';
            //fnClear.className = 'ui-btn ui-btn-fill ui-btn-blue';


        }
    });

    // 表单验证
    var $addForm = $('#form'),
        $projectName = $('#projectName').val();
    
    $addForm.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else if(element.hasClass('radio')) {
                element.parent().parent().append(error);
            } else{
                element.parent().append(error);
            }
        },
        submitHandler: function(form) {
            formSubmit('SUBMIT');
        },
        rules: {
            'projectCode':{
                required: true,
            },
            'projectName': {
                required: true,
            },
            'transfereePrice': {
                required: true,
            },
            'transferCompany':{
                required: true,
                maxlength: 50,
            },
            'transfereeTime':{
                required: true,
            },
            'isTrusteeLiquidate':{
                required: true,
            }
        },
        messages: {
            'projectCode':{
                required: '必填',
            },
            'projectName': {
                required: '必填',
            },
            'transfereePrice': {
                required: '必填',
            },
            'transferCompany':{
                required: '必填',
            },
            'transfereeTime':{
                required: '必填',
            },
            'isTrusteeLiquidate':{
                required: '必填',
            }
        }
    });

    function formSubmit(type) {

        if (!($('#fnWrite').val() == 'true')) {
            if ($('#fnClear').hasClass('fn-hide')) {
                var a = name();
                if (!a) {
                    submit(type)
                } else {
                    $('#projectName').val('');
                    $('#projectName').focus();
                }
            } else {

                submit(type)
            }
        }else{
            
            var textProjectName = $('#projectName').val();
            if( $projectName == textProjectName ){

                submit(type);

            }else{
                var a = name();
                if (!a) {
                    submit(type)
                } else {
                    $('#projectName').val('');
                    $('#projectName').focus();
                }
            }            
        }
        
    }

    function submit(type){

        document.getElementById('state').value = type || 'SAVE';
        var checkStatus = document.getElementById('checkStatus').value = type ? 1 : 0;
        util.resetName();
        //保存数据
        util.ajax({
            url: '/assetMg/transferee/save.htm',
            data: $addForm.serializeJCK(),
            success: function(res) {
                if (res.success) {
                    if (checkStatus == '1') {
                        //提交表单
//                        util.ajax({
//                            url: '/projectMg/form/submit.htm',
//                            data: {
//                                formId: res.form.formId,
//                                _SYSNAME:'AM'
//                            },
//                            success: function(res2) {
//                                if (res2.success) {
//                                    Y.alert('提醒', res2.message, function() {
//
//                                        window.location.href = '/assetMg/transferee/list.htm';
//
//                                        var projectCode = $('#projectCode').val();
//                                    });
//
//                                } else {
//                                    Y.alert('提醒', res2.message, function() {
//                                        window.location.reload(true);
//                                    });
//                                }
//                            }
//                        })
                    	util.postAudit({
                            formId: res.form.formId,
                            _SYSNAME: 'AM'
                        }, function () {
                            window.location.href = '/assetMg/transferee/list.htm';
                        })
                    }else{
                        Y.alert('提醒','已保存~', function() {
                            window.location.href = '/assetMg/transferee/list.htm';
                        });
                    }
                }else{
                    Y.alert('提醒', res.message);
                }
            }
        });
    }

    $addForm.on('blur', '.fnInputDateS', function() {

        var _thisVal = this.value;
        $('#clearContent').find('.fnInputDateE').attr('onclick', 'laydate({min: "' + _thisVal + '"})');

    }).on('blur', '.fnInputDateE', function() {

        var _thisVal = this.value;
        $('.fnInputDateS').attr('onclick', 'laydate({max: "' + _thisVal + '"})');

    });


    // 选择资质
    $('.chooseZizhi').click(function(){
        var box = $('#Credentials');
        box.fadeIn(400);
        $('.close').click(function(){
            box.fadeOut(400);
        })

        $('#submit2').click(function(){

            if(!($("#Credentials input").is(':checked'))){          
                Y.alert('提醒', '土地、房产、资产请至少选择一项~');
                return false;
            }else{
                var chk_value ='',
                    qualityAssets = "",
                    qualityLand = $('#Credentials').find('tr:first() input:checked').val(),
                    qualityHouse = $("#Credentials tr:nth-child(2)").find('input:checked').val(),
                    assetsChecked = $('#Credentials').find('tr:last() input:checked');

                assetsChecked.each(function(){
                    qualityAssets += $(this).val() + ',';
                })

                if(qualityLand == undefined){
                    qualityLand = '';
                }

                if(qualityHouse == undefined){
                    qualityHouse = '';
                }
                if(qualityLand!=""){
                    chk_value += '土地（' + qualityLand + '）;';
                }   
                if(qualityHouse!=""){
                    chk_value += '房产（' + qualityHouse + '）;';
                }   
                if(qualityAssets!=""){
                    chk_value += '资产（' + qualityAssets + '）;';
                }             
                
                $('#qualityLand').val(qualityLand);
                $('#qualityHouse').val(qualityHouse);
                $('#qualityAssets').val(qualityAssets);
                $('#alltext').val(chk_value);
                box.fadeOut(400);
            }

        })
    })


    // 资产受让 新增

    $('body').on('click','#fnClear',function(){
        $(this).addClass('fn-hide');
        $('#chooseBtn').removeClass('fn-hide');
        $('#projectName').val('').removeAttr('readonly');
        $('#transferCompany').val('').removeAttr('readonly');
        $('#projectCode').val('');
    })

    //项目名称是否重复

    function name() {

        var _is = false;

        var $companyName = $('#projectName').val();
        util.ajax({
            url: '/assetMg/transferee/checkOuterProjectName.htm',
            type: 'POST',
            async: false,
            dataType: 'json',
            data: {
                projectName: $companyName,
            },
            success: function(res2) {
                if (res2.success) {

                    Y.alert('提醒', res2.message);
                    _is = true;

                } else {
                    _is =  false;

                }
            }
        })
        console.log(_is);
        return _is;
    }

    //调用是否委托清收
    $('#isClearBox').on('click','label',function () {
        var self = $(this);
        self.addClass('active').siblings().removeClass('active');
        isClear();
    });
    // 是否委托清收
    var isClear = function() {
        var clearContent = $('#clearContent'),
            firstLabel = $('#isClearBox label:first'),
            firstLast = $('#isClearBox label:last'),
            fnInput = clearContent.find('input,textarea');
        if (firstLabel.hasClass('active')) {
            clearContent.show();
            fnInput.each(function(){
                $(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
            })

            return true;

        } else if(firstLast.hasClass('active')) {
            clearContent.hide();
            fnInput.each(function(){
                $(this).rules('remove','required');
            })
            return true;

        } else {
            Y.alert('提醒', '请把表单填写完整');
            return false;
        }
    };

    if( $('#fnWrite').val() == 'true'){
        isClear();
    }

    $('#submit').click(function() {

        var _isPass = true,
            _isPassEq;

        $('.fnInput').each(function(index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {
            $('.fnInput').eq(_isPassEq).focus();
            return;
        }
    })

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    //------ 侧边栏 end
})