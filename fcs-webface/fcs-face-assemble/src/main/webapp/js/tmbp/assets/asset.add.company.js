/*
 * @Author: yanyang
 * @Date:   2016-07-04 18:05:40
 * @Last Modified by:   yanyang
 * @Last Modified time: 2016-07-12 20:11:13
 */

define(function(require, exports, module) {
	// 审核通用部分 start
    var auditProject = require('/js/tmbp/auditProject');
    var _auditProject = new auditProject('auditSubmitBtn');
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit({}).initPrint('打印的url');

	//通用方法
	var util = require('util');
	//Y对话框
	require('Y-msg');
	//字数提示
    require('zyw/hintFont');
	//上传
	require('zyw/upAttachModify');
	// 输入限制
	require('input.limit');
    //validate
    require('validate');
    require('validate.extend');

    var chooseRegion = require('tmbp/chooseRegion'); //引入

	//------ 数组Name命名 start
    function resetName(orderName) {

        // ------ fnTableList resetName 0~N

        $('.fnTableList').each(function() {

            $(this).find('tr[orderName]').each(function(index) {

                var tr = $(this),
                    _orderName = tr.attr('orderName'),
                    index = index;
                // index = tr.index('tr[orderName]', _fnTableList);

                tr.find('[name]').each(function() {

                    var _this = $(this),
                        name = _this.attr('name');

                    if (name.indexOf('.') > 0) {
                        name = name.substring(name.lastIndexOf('.') + 1);
                    }

                    name = _orderName + '[' + index + '].' + name;

                    _this.attr('name', name);

                });

            });

        });
    }

	//------ 数组Name命名 end
	resetName();

	// 选择资质
	$('.chooseBtn').click(function(){
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

                qualityAssets = qualityAssets.substring(0,qualityAssets.length-1);

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
                //chk_value += '土地（' + qualityLand + '）;房产（' + qualityHouse + '）;资产（' + qualityAssets +'）;';

                chk_value = chk_value.substring(0,chk_value.length-1);
                
                $('#qualityLand').val(qualityLand);
                $('#qualityHouse').val(qualityHouse);
                $('#qualityAssets').val(qualityAssets);
                $('#alltext').val(chk_value);
	            box.fadeOut(400);
	        }

		})
	})

    // 编辑还原
    if( $('#fnWrite').val() == 'true'){
        ziZhi()
    }

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

            if  ($(this).val() == a[0] || $(this).val() == a[1] || $(this).val() == a[2]|| $(this).val() == a[3] ) {
                $(this).attr('checked', 'checked');
            }
        });

    }


    // 添加联系人
    $('#contactBox').on('click','.fn-addline',function(){
        var self = $(this),
            selfBox = self.parents().find('.contactBox'),
            selfAdd = $('.fn-publine'),
            selfTr = selfBox.find('tr').length + 1 ;
        if(selfTr < 5){
            selfBox.append(selfAdd.html());        
            resetName();
        }else if(selfTr == 5){
            selfBox.append(selfAdd.html());        
            resetName();
            self.remove();
        }else{
            self.remove();
        }
    });

    $('#contactBox').on('click','.fn-del',function(){
        var self = $(this),
            selfBox = self.parents('.fnNewLine'),
            contactBox = self.parents('#contactBox'),
            selfAdd = contactBox.find('.fn-addline').attr('title');
            selfTr = self.parents('.contactBox').find('tr').length - 1;
        selfBox.remove();
        resetName();
        if( selfTr < 5 ){
            if( selfAdd == undefined){
                contactBox.append('<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-green fn-addline addBtn2"><i class="icon icon-add"></i>增加</a>');
            }
        }
    })

    // 表单验证

    var $addForm = $('#form'),
        $fnInput = $('.fnInput'),
        $fnradio = $('input.radio'),
        formRules = {
            rules: {},
            messages: {}
        };

        $fnInput.each(function(index, el) {
            //必填规则
            var name = $(this).attr('name');
            if (!name) {
                return true;
            }
            formRules.rules[name] = {
                required: true
            };
            formRules.messages[name] = {
                required: '必填'
            };
        });


        $fnradio.each(function(index, el) {
            //必填规则
            var name = $(this).attr('name');
            if (!name) {
                return true;
            }
            formRules.rules[name] = {
                required: true
            };
            formRules.messages[name] = {
                required: '必填'
            };
        });

    $addForm.validate($.extend({}, formRules, {
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

            $('.fnTel').each(function(){
                var self = $(this);
                checkTel(self);
            })
            if (quyu()) {
                if (fanwei()) {
                    if ($('#fnWrite').val() == 'true') {
                        formSubmit('SUBMIT');
                    } else {
                        var a = name();
                        if (!a) {
                            formSubmit('SUBMIT');
                        } else {
                            companyName.focus();
                        }
                    }
                }
            }
        }
    }));


    function formSubmit(type) {

        util.resetName();
        
        //保存数据
        util.ajax({
            url: '/assetMg/assessCompany/save.htm',
            data: $addForm.serialize(),
            success: function(res) {
                if (res.success) {
                    Y.alert('提醒', res.message, function() {
                        window.location.href = '/assetMg/assessCompany/list.htm';
                    });
                } else {
                    Y.alert('提醒', res.message, function() {
                        window.location.reload(true);
                    });
                }
            }
        });
    }

    //项目名称是否重复

    function name() {

        var _is = false;

        var $companyName = $('#companyName').val();
        util.ajax({
            url: '/assetMg/assessCompany/checkCompanyName.htm',
            type: 'POST',
            async: false,
            dataType: 'json',
            data: {
                companyName: $companyName,
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

        return _is;
    }


    function resetName2(orderName) {

        // ------ fnTableList resetName 0~N

        $('.fnSelect').each(function() {

            var select = $(this).find('select'),
                _orderName = select.attr('orderName'),
                index = index;

            select.find('option[name]').each(function(index) {

                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {

                    name = name.substring(name.lastIndexOf('.') + 1);
                }

                name = _orderName + '[' + index + '].' + name;

                _this.attr('name', name);

            });

        });
    }
    resetName2();


    // 获取城市编码
    $('.addressBox').on('change','select',function(){
        var addressBox = $('.addressBox'),
            selectAddress = $('#selectAddress'),
            _str = [];

        addressBox.find('select').each(function(){
            var checked = $(this).find('option:selected'),
                code = checked.val(),
                name = checked.text();
            _str.push(code,name);
        })
        selectAddress.find('input.fnDizhi').each(function(){
            var self = $(this),
                selfIndex = self.index();
            self.val(_str[selfIndex - 1 ]);
        })
    })   


    var $areaList = $('.areaBox');
    $.getJSON('/baseDataLoad/region.json', {
            parentCode: 'China'
    }, function(res, textStatus) {
        if (res.success) {
            $.each(res.data,function (index,ele) {
                $areaList.append('<option value="' + res.data[index].code + '" name="city">' + res.data[index].name + '</option>');
            })
        } else {
            Y.alert('提示', res.message);
        }

        if( $('#fnWrite').val() == 'true'){
            scopeInfos();
        }

    });

    // 评估公司业务范围
    $(function(){  
        //选择一项  
        $("#addOne").click(function(){
            var option = $("#from option:selected");
            option.clone().appendTo("#to");  
            option.remove();
            resetName2();

            if( $('#to').find('option').text() == '全国' ){
                $('#from option').css('background','#ccc').attr('disabled',true);
                $('#from #nation').css('background','#fff').attr('disabled',false);
            }else{
                $('#from option').css('background','#fff').attr('disabled',false);
                $('#from #nation').css('background','#ccc').attr('disabled',true);
            }

            classChange();
            
            paixu();

            $('#to').find('option').each(function(){
                $(this).removeAttr('selected');
            })

            
        });
            
        //移除一项  
        $("#removeOne").click(function(){
            var option = $("#to option:selected");
            option.clone().appendTo("#from");  
            option.remove();
            resetName2();
            var optionLen = $('#to option').length;

            if( optionLen == 1 && option.text() =='全国' ){
                $('#from option').css('background','#fff').attr('disabled',false);
            }else if( optionLen == 0 ){
                $('#from option').css('background','#fff').attr('disabled',false);
            }

            classChange();

            var removeOption = option.attr('class');
            $('.fnSelect input').each(function(){
                if( $(this).hasClass(removeOption) ){
                    $(this).remove();
                    paixu();
                }
            })
        }); 

        //上移一行  
        $("#Up").click(function(){  
            var selected = $("#to option:selected");  
            if(selected.get(0).index!=0){  
                selected.each(function(){  
                    $(this).prev().before($(this));  
                });  
            }

            classChange();

            paixu()
        });
  
        //下移一行  
        $("#Down").click(function(){  
            var allOpts = $("#to option");  
            var selected = $("#to option:selected");  
            if(selected.get(selected.length-1).index!=allOpts.length-1){  
                for(i=selected.length-1;i>=0;i--){  
                   var item = $(selected.get(i));  
                   item.insertAfter(item.next());  
                }  
            }
            classChange();
            paixu();
        });

        function classChange(){
            $('#to option').each(function(){
                var self =$(this),
                    index = self.index();

                if( self.attr('class') == undefined ){
                    self.addClass('a'+ index );
                }else{
                    self.attr('class','a'+ index);
                }
            })

            $('#from option').each(function(){
                $(this).attr('class','');
            })
        }
    });

    // 固话手机验证

    function checkTel(tel){
        var isPhone = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(14[0-9]{1}))+\d{8})$|^(0\d{2,4}-?\d{7,8})$|^(\d{7,8})$/,
            isMob=/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/,
            is400 = /^400(\d{3,4}-)?\d{7}$/,
            value= tel,
            text = value.val().trim();
        if( isMob.test(text) || isPhone.test(text) || is400.test(text)){
            return true;
        }else{
            tel.val('');
            return false;
        }
    }

    $('body').on('blur','.fnTel',function(){
        $('.fnTel').each(function(){
            var self = $(this);
            checkTel(self);
        })
    })

    // $('.fnTel').blur(function(){
    //     $('.fnTel').each(function(){
    //         var self = $(this);
    //         checkTel(self);
    //     })       
    // })

    $('.fnTel').each(function(){
        var self = $(this);
        checkTel(self);
    }) 
    

    //排序
    function paixu() {
        var box = $('#to'),
            fnSelected = $('.fnSelected'),
            option = box.find('option');
        fnSelected.text('');

        option.each(function() {

            var self = $(this),
                _html = '',
                code = self.val(),
                city = self.text(),
                sort = self.index();

            _html += '<input type="hidden" class="a' + sort + '" name="scopeOrders[' + sort + '].code" value="' + code + '" /></br>'
                    + '<input type="hidden" class="a' + sort + '" name="scopeOrders[' + sort + '].businessScopeRegion" value="' + city + '" /></br>'
                    + '<input type="hidden" class="a' + sort + '" name="scopeOrders[' + sort + '].sort" value="' + sort + '" />';
            if (fnSelected.text() == '') {
                $('.fnSelected').append(_html);
            } else {
                $('.fnSelected input').remove();
                $('.fnSelected').append(_html);
            }
        })
    }

    // 还原评估公司业务范围

    function scopeInfos(){
        var scopeInfos = $('#scopeInfos').val(),
            to = $('#to'),
            allArry = [],
            code,city,sort,a,b,c,arry,i,x,j,m;
        arry = scopeInfos.split('],');

        // 所有的城市编码 名称 序号
        for( i = 0 ; i < arry.length ; i++ ){
            var _html = '',
                d = [];
            a = arry[i].split(',');
            for( j = 2 ; j <= 4 ; j++ ){
                b = a[j].split('=');
                d.push(b[1]);
            }
            allArry.push(d[1]);
            _html += '<option value="' + d[1] + '" name="scopeOrders[' + d[2] +'].city" class="a' + d[2] + '">' + d[0] + '</option>';
            // $('#to').append(_html);
        }
        for( m = 1 ; m <= allArry.length ; m++ ){

            var newText = allArry[m-1];
            $('.areaBox option').each(function(){
                var self = $(this),
                    selfVal = self.val();
                if( selfVal == newText ){
                    self.attr('selected','selected');
                    $('#addOne').trigger('click');
                }
            })
        }
    }

    function fanwei(){
        var _is = true;
        if( $('#to option').length < 1 ){
            Y.alert('提醒', '请选择评估公司业务范围');
            _is = false;
        }else{
            _is = true
        }
        return  _is;
    }

    function selectAddress(){
        var selectAddress = $('#selectAddress'),
            countryName = $('#countryName').val(),
            provinceName = $('#provinceName').val(),
            cityName = $('#cityName').val(),
            countyName = $('#countyName').val(),            
            newText = '';
        newText += countryName + ' ' + provinceName + ' ' + cityName + ' ' + countyName;
        $('#newAddressBox').text(newText);

    }

    selectAddress()

    function quyu(){
        var addressResult = $('#addressResult').val();
        if(addressResult == ''){
            Y.alert('提醒', '请选择所属区域');
            return false;
        }else{
            return true;
        }
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