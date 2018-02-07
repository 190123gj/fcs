define(function(require, exports, module) {

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.addOPN([]).init().doRender();
	//------ 侧边栏 end
	var hintPopup = require('zyw/hintPopup');
	require('zyw/publicPage')
		//---选择部门 start
		// var $fnOrgName = $('#fnOrgName'),
		// 	$fnOrgId = $('#fnOrgId');

	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	// 初始化弹出层
	var singleSelectUserDialog1 = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true&selectTop=Y', {
		'title': '部门组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$('.creditorBtn1').on('click', function() {

		//这里也可以更新已选择机构
		var _arr = [],
			_this = $(this),
			$fnOrgName = _this.siblings('.fnOrgName'),
			$fnOrgId = _this.siblings('.fnOrgId'),
			_nameArr = $fnOrgName.val().split(','),
			_idArr = $fnOrgId.val().split(',');

		for (var i = 0; i < _nameArr.length; i++) {
			if (_nameArr[i]) {
				_arr.push({
					id: _idArr[i],
					name: _nameArr[i]
				});
			}
		}

		singleSelectUserDialog1.obj.arrys = _arr;

		singleSelectUserDialog1.init(function(relObj) {

			if (/公司领导/.test(relObj.orgName)) {
				hintPopup('不能选择公司领导');
				return false;
			}

			$fnOrgId.val(relObj.orgId).blur();
			$fnOrgName.val(relObj.orgName).blur();

		});

	});


	var singleSelectUserDialog2 = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true', {
		'title': '部门组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name='name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$('.creditorBtn2').on('click', function() {

		//这里也可以更新已选择机构
		var _arr = [],
			_this = $(this),
			$fnOrgName = _this.siblings('.fnOrgName'),
			$fnOrgId = _this.siblings('.fnOrgId'),
			_nameArr = $fnOrgName.val().split(','),
			_idArr = $fnOrgId.val().split(',');

		for (var i = 0; i < _nameArr.length; i++) {
			if (_nameArr[i]) {
				_arr.push({
					id: _idArr[i],
					name: _nameArr[i]
				});
			}
		}

		singleSelectUserDialog2.obj.arrys = _arr;

		singleSelectUserDialog2.init(function(relObj) {

			if (/公司领导/.test(relObj.orgName)) {
				hintPopup('不能选择公司领导');
				return false;
			}

			$fnOrgId.val(relObj.orgId).blur();
			$fnOrgName.val(relObj.orgName).blur();

		});

	});
	//---选择部门 end



	$("#fnListExport").click(function() {
		var url = $(this).attr("exportUrl");
		url = url + "?" + $("#fnListSearchForm").serialize();
		window.location = url;
	});
	var yearsTime = require('zyw/yearsTime');
	$('body').on('focus', '.birth', function(event) {
		var yearsTimeFirst = new yearsTime({
			elem: this,
			format: 'YYYY-MM',
			callback: function(_this, _time) {
				$('.fnListSearchDateS,.fnListSearchDateE').val('');
			}
		});
	});
	//搜索框时间限制
	$('body').on('blur', '.fnListSearchDateS', function() {

		var $p = $(this).parents('.fnListSearchDateItem'),
			$input = $p.find('.fnListSearchDateE');

		$input.attr('onclick', 'laydate({min: "' + this.value + '",choose:function(){$(".birth").val("")}})');

	}).on('blur', '.fnListSearchDateE', function() {

		var $p = $(this).parents('.fnListSearchDateItem'),
			$input = $p.find('.fnListSearchDateS');

		$input.attr('onclick', 'laydate({max: "' + this.value + '",choose:function(){$(".birth").val("")}})');

	}).find('.fnListSearchDateS,.fnListSearchDateE').trigger('blur');
})