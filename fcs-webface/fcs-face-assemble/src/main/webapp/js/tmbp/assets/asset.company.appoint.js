/*
* @Author: yanyang
* @Date:   2016-07-06 10:52:24
* @Last Modified by:   zhurui
* @Last Modified time: 2017-05-24 17:20:36
*/
define(function(require, exports, module) {
    require('validate');
    require('validate.extend');
    require('zyw/upAttachModify');


    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

 // ------ 审核 start
	if (document.getElementById('auditForm')) {
		var auditProject = require('zyw/auditProject');
		var _auditProject = new auditProject('auditForm2');
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
	}
	// ------ 审核 end
    var template = require('arttemplate');

    template.helper('obj2str', function(obj) {

        return JSON.stringify(obj)

    });

    var getList = require('zyw/getList'),
        util = require('util');

    // 选择项目
    var _getList = new getList();
    _getList.init({
        width: '90%',
        title: '评估公司',
        ajaxUrl: '/baseDataLoad/loanAssetsCompany.json',
        btn: '.chooseBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td  class="item" id="{{item.companyName}} title="{{item.companyName}}">{{(item.companyName && item.companyName.length > 6)?item.companyName.substr(0,6)+\'\.\.\':item.companyName}}</td>',
                '        <td>{{(item.countryName && item.countryName.length > 6)?item.countryName.substr(0,6):item.countryName}}</td>',
                '        <td>{{(item.cityName && item.cityName.length > 6)?item.cityName.substr(0,6):item.cityName}}</td>',
                '        <td>{{(item.address && item.address.length > 6)?item.address.substr(0,6):item.address}}</td>',
                '        <td>{{item.quality}}</td>',
                '        <td><a class="choose" contactInfos={{=obj2str(item.contactInfos)}} companyName="{{item.companyName}}" companyId="{{item.companyId}}" cityName="{{item.cityName}}" address="{{item.address}}" quality="{{item.quality}}"  workSituation="{{item.workSituation}}" attachment="{{item.attachment}}" technicalLevel="{{item.technicalLevel}}" evaluationEfficiency="{{item.evaluationEfficiency}}" cooperationSituation="{{item.cooperationSituation}}" serviceAttitude="{{item.serviceAttitude}}" href="javascript:void(0);">选择</a></td>', //跳转地址需要的一些参数
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['评估公司名称：',
                '<input class="ui-text fn-w160" type="text" name="companyName">',
                '&nbsp;&nbsp;',
                '国家：',
                '<input class="ui-text fn-w160" type="text" name="countryName">',
                '&nbsp;&nbsp;',
                '城市：',
                '<input class="ui-text fn-w160" type="text" name="city">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">评估公司名称</th>',
                '<th width="120">国家</th>',
                '<th width="120">城市</th>',
                '<th width="120">地址</th>',
                '<th width="100">资质</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function($a) {
            var contactInfos = $a.attr('contactInfos'),
                a = contactInfos.split('},'),
                b,html='',
                aLength = a.length,
                j = 1;
                b = a[0].split(',');
            for(j = 1 ; j <= aLength ; j++ ){
                var b,c,d;
                b = a[j-1].split(',');
                c = b[2].split(':');
                d = b[3].split(':');
                html += '<div class="fn-clear">'
                    +'<div class="m-item m-item-half fn-left">'
                    +'    <label class="m-label">联系人：</label>'
                    +'<input class="ui-text fn-w200 fnInput" type="text" value=' + c[1] + ' readonly>'
                    +'</div>'
                    +'<div class="m-item m-item-half fn-left">'
                    +'    <label class="m-label">联系电话：</label>'
                    +'    <input class="ui-text fn-w200 fnInput fnMakeTel" type="text" value=' + d[1] + ' readonly>'
                    +'</div>'
                    +'</div>';
            }
            $('.lianXiRen').text('');
            $('.lianXiRen').append(html);
            //跳转地址
            document.getElementById('companyName').value = $a.attr('companyName');
            document.getElementById('address').value = $a.attr('address');
            document.getElementById('ZdcompanyId').value = $a.attr('companyId');
            document.getElementById('ZdcompanyName').value = $a.attr('companyName');

            document.getElementById('workSituation').innerText = $a.attr('workSituation');
            document.getElementById('attachment').innerText = $a.attr('attachment');
            document.getElementById('technicalLevel').innerText = $a.attr('technicalLevel');
            document.getElementById('evaluationEfficiency').innerText = $a.attr('evaluationEfficiency');
            document.getElementById('cooperationSituation').innerText = $a.attr('cooperationSituation');
            document.getElementById('serviceAttitude').innerText = $a.attr('serviceAttitude');
        }
    });

    // 表单验证

    var $addForm = $('#form');

    function formSubmit(type) {

        document.getElementById('state').value = type || 'SAVE';
        var checkStatus = document.getElementById('checkStatus').value = type ? 1 : 0;
        util.resetName();

        //保存数据
        util.ajax({
            url: '/assetMg/assessCompanyApply/save.htm',
            data: $addForm.serialize(),
            success: function(res) {
                if (res.success) {
                    if ( checkStatus == '1') {
                        //提交表单
//                        util.ajax({
//                            url: '/projectMg/form/submit.htm',
//                            data: {
//                                formId: res.form.formId,
//                                _SYSNAME:'AM',
//                            },
//                            success: function(res2) {
//                                if (res2.success) {
//                                    Y.alert('提醒', res2.message, function() {
//                                        window.location.href = '/assetMg/assessCompanyApply/list.htm';
//                                    });
//                                } else {
//                                    Y.alert('提醒', res2.message, function() {
//                                        window.location.reload(true);
//                                    });
//                                }
//                            }
//                        });
                    	util.postAudit({
                            formId: res.form.formId,
                            _SYSNAME: 'AM'
                        }, function () {
                            window.location.href = '/assetMg/assessCompanyApply/list.htm';
                        })
                    } else {
                        Y.alert('提醒', res.message, function() {
                            window.location.href = '/assetMg/assessCompanyApply/list.htm';
                        });
                    }
                } else {
                    Y.alert('提醒', res.message);
                }
            }
        });
    }

    // 评估公司选定
    $('.selectNav a').click(function(){
        var self = $(this),
            selfIndex = self.index(),
            appointWay = $('#appointWay'),
            companyId = $('#companyId'),
            companyName1 = $('#companyName1'),
            selectBox = self.parents().parents().parents('.companySelect');
        self.addClass('active').siblings().removeClass('active');
        selectBox.find('.company').eq(selfIndex).removeClass('fn-hide').siblings('.company').addClass('fn-hide');

        changeVal();
    })

    function changeVal(){
        var appointWay = $('#appointWay'),
            companyId = $('#companyId'),
            companyName1 = $('#companyName1');
        $('.selectNav a.active').each(function(){        
            var selfIndex = $(this).index();
            if( selfIndex == 0 ){
                appointWay.val('EXTRACT');
                companyId.val($('#CqcompanyId').val());
                companyName1.val($('#CqcompanyName').val());

                $('.fnAppointCompany').find('input,textarea').each(function(index, el) {
                    $(this).removeClass('fnInput');
                });

            }else if( selfIndex == 1 ){
                appointWay.val('APPOINT');
                companyId.val($('#ZdcompanyId').val());
                companyName1.val($('#ZdcompanyName').val());

                $('.fnAppointCompany').find('input,textarea').each(function(index, el) {
                    $(this).addClass('fnInput');
                });

            }else{
                appointWay.val('');
                companyId.val();
                companyName1.val();

                $('.fnAppointCompany').find('input,textarea').each(function(index, el) {
                    $(this).removeClass('fnInput');
                });
            }
        })
    }      


    // 提交
    
    $('#submit').click(function(){

        changeVal();

        var _isPass = true,
            _isPassEq;

        $('.fnInput').each(function(index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {

            Y.alert('提醒', '请把表单填写完整', function() {
                $('.fnInput').eq(_isPassEq).focus();
            });
            return;
        }

        if( $('#companyId').val() == '' || $('#companyId').val() == 'companyName1' ){
            Y.alert('提醒', '请选定评估公司');
        }else{
            formSubmit('SUBMIT');
        }
        
    })

    $('#fnAuditBtnPass').click(function(){
        changeVal();
        if( $('#appointWay').val() == '' ){

            Y.alert('提醒', '请选定评估公司');
            return false;

        }else if($('#companyId').val() == '' || $('#companyName1').val() == ''){

            Y.alert('提醒', '请选定评估公司');

            return false;

        }else{
            var _isPass = true,
                _isPassEq;

            $('.fnInput').each(function(index, el) {
                if (!!!el.value.replace(/\s/g, '')) {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                }
            });

            if (!_isPass) {

                Y.alert('提醒', '请把表单填写完整', function() {
                    $('.fnInput').eq(_isPassEq).focus();
                });
                return false;
            }
        }
    })

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


    var OKlength = $('.fnExtractCompany').find('.addCompany').length;
    
    if( OKlength <= 0){

        $('.fnExtract').click(function(){

            var OKlength2 = $('.fnExtractCompany').find('.addCompany').length;

            if( OKlength2 <= 0 ){
                var id = $('#id').val(),
                    cityName = $('#cityName').val(),
                    provinceName = $('#provinceName').val();
                util.ajax({
                    url: '/assetMg/assessCompanyApply/assessCompanyeExtract.htm',
                    data: {
                        id: id,
                        cityName: cityName,
                        provinceName: provinceName,
                    },
                    success: function(res) {
                        var i = 1,
                            j = 0,
                            idHidden  = '',
                            companyName = '',
                            fnExtractCompany = $('.fnExtractCompany'),
                            htmlTpl='',
                            resLength = res.listCompanyForRequired.length;

                        if( resLength == 0 ){

                            Y.alert('提示', '客户所在区域无匹配的评估公司，请指定评估公司！');

                        }else{
                            for(i = 1 ; i <= resLength ; i++ ){
                                var contactInfosHtml = '',
                                    listCompany = res.listCompanyForRequired,
                                    contactInfos = listCompany[i-1].contactInfos,
                                    contactInfosLength = listCompany[i-1].contactInfos.length;
                                idHidden += listCompany[i-1].id + ',' ;
                                companyName += listCompany[i-1].companyName + ',';

                                for( j = 0 ; j < contactInfosLength ; j++ ){
                                    contactInfosHtml+='<div class ="fn-clear" >'
                                        +'    <div class ="m-item m-item-half fn-left">'
                                        +'         <label class = "m-label"><span class="m-required"> * </span>联系人：</label >'
                                        +'         <input class = "ui-text fn-w200 fnInput" type = "text" value = "' + contactInfos[j].contactName + '" readonly>'
                                        +'    </div>'

                                        +'      <div class = "m-item m-item-half fn-left">'
                                        +'          <label class = "m-label"> <span class = "m-required"> * </span>联系电话：</label >'
                                        +'          <input class = "ui-text fn-w200 fnMakeTel fnTel fnInput" type = "text" maxlength = "11" value = "' + contactInfos[j].contactNumber + '" readonly >'
                                        +'      </div>'
                                        +'</div>'
                                }

                                htmlTpl +='<div class="addCompany">'
                                        +'<div class="fn-clear">'
                                        +'  <div class="m-item m-item-half fn-left">'
                                        +'      <label class="m-label">评估公司名称：</label>'
                                        +'      <input class="ui-text fn-w200 fnInput" type="text" value="' + listCompany[i-1].companyName +'" readonly>'
                                        +'  </div>'
                                        +'  <div class="m-item m-item-half fn-left">'
                                        +'      <label class="m-label">联系地址：</label>'
                                        +'      <input class="ui-text fn-w200 fnInput" type="text" value="' + listCompany[i-1].contactAddr + '" readonly>'
                                        +'  </div>'
                                        +'</div>'
                                        +contactInfosHtml
                                        +'<div class = "m-item fn-clear" style = "height: auto;" >'
                                        +'  <label class = "m-label" style="left:170px" > 评估公司评分： </label>'
                                        +'  <div class = "fn-left">'
                                        +'      <ul class="fn-clear pfBox" style="margin-top:-10px">'
                                        +'          <li><span class="pfItem">现场工作情况</span><span class="fnItemsum">' + listCompany[i-1].workSituation + '</span></li>'
                                        +'          <li><span class="pfItem">附件齐全程度</span><span class="fnItemsum">' + listCompany[i-1].attachment +'</span></li>'
                                        +'          <li><span class="pfItem">评估报告技术水平</span><span class="fnItemsum">' + listCompany[i-1].technicalLevel +'</span></li>'
                                        +'          <li><span class="pfItem">评估效率</span><span class="fnItemsum">' + listCompany[i-1].evaluationEfficiency +'</span></li>'
                                        +'          <li><span class="pfItem">合作情况</span><span class="fnItemsum">' + listCompany[i-1].cooperationSituation +'</span></li>'
                                        +'          <li><span class="pfItem">服务态度</span><span class="fnItemsum">' + listCompany[i-1].serviceAttitude +'</span></li>'
                                        +'      </ul>'
                                        +'  </div>'
                                        +'</div>'
                                        +'</div>'
                            }
                            idHidden = idHidden.substring(0,idHidden.length-1);
                            companyName = companyName.substring(0,companyName.length-1);
                            $('.fnExtractCompany').find('.addCompany').each(function() {
                                $(this).remove();
                            });
                            fnExtractCompany.append(htmlTpl);
                            $('#CqcompanyId').val(idHidden);
                            $('#CqcompanyName').val(companyName);
                        }
                    },
                    error: function(res) {
                        Y.alert(res.message)
                    }
                });
            }
        })
    }

})