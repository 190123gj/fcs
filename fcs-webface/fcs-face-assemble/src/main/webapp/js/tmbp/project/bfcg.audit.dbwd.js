define(function (require, exports, module) {

	//所属行业 相关
	require('zyw/chooseIndustry');

	//项目管理>授信前管理> 立项审核
	require('Y-imageplayer');

	//上传
	require('zyw/upAttachModify');

	var util = require('util');

	//压缩
	var pako = require('pako')

	//tab切换
	var $step = $('#step');
	$step.on('click', 'li:not(.active)', function () {
		var _this = $(this),
			_thisFTF = _this.attr('ftf'),
			$FTF = $('#' + _thisFTF);
		_this.addClass('active').siblings().removeClass('active');
		$('.fnStep').addClass('fn-hide');
		$FTF.removeClass('fn-hide');
	});
	$('.fnGOBack').on('click', function () {
		window.location.href = '/projectMg/setUp/list.htm';
	});
	$('.fnNext').on('click', function () {
		$step.find('li').eq($(this).parent().find('.step').val()).trigger('click')
	});

	//放大图片
	var $applyUpfileImg = $('.apply-upfile-img:not(.fnUpFile, .noPic)'),
		applyUpfileImgArr = [];

	$applyUpfileImg.each(function (index, el) {

		applyUpfileImgArr.push({
			src: el.src,
			desc: el.alt
		});

	});

	$applyUpfileImg.on('click', function () {

		Y.create('ImagePlayer', {
			imgs: applyUpfileImgArr,
			index: $(this).index('.apply-upfile-img:not(.fnUpFile, .noPic)')
		}).show();

	});

	//------ 审核通用部分 start
	var auditProject = require('zyw/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit({
		// 初始化审核
		// 自定义 确定函数
		// doPass: function(self) {
		// 	alert('1');
		// 	self.audit.$box.find('.close').eq(0).trigger('click');
		// },
		// doNoPass: function(self) {
		// 	alert('3');
		// 	self.audit.$box.find('.close').eq(0).trigger('click');
		// },
		// doBack: function(self) {
		// 	alert('2');
		// 	self.audit.$box.find('.close').eq(0).trigger('click');
		// }
	});
	//------ 审核通用部分 end
	//
	//
	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	var riskQuery = require('zyw/riskQuery');
	var initRiskQuery = new riskQuery.initRiskQuery(false, 'fnUserName', 'fnUserNO', 'userType', 'orgCode', 'isThreeBtn', 'fnUserNO');
	// if (document.getElementById('auditForm')) {
	// 	publicOPN.addOPN([{
	// 		name: '风险检索',
	// 		alias: 'JS',
	// 		event: function () {
	// 			util.risk2retrieve(document.getElementById('fnUserName').value, document.getElementById('fnUserNO').value);
	// 		}
	// 	}, {
	// 		name: '查看相似企业',
	// 		alias: 'XS',
	// 		event: function () {
	// 			initRiskQuery.showXS();
	// 		}
	// 	}, {
	// 		name: '失信黑名单',
	// 		alias: 'SX',
	// 		event: function () {
	// 			initRiskQuery.showSX();
	// 		}
	// 	}]);
	// }
	publicOPN.init().doRender();
	//------ 侧边栏 end
	//
	//
	//------ 审核选人 start
	//BPM弹窗
	var BPMiframe = require('BPMiframe');
	var BPMiframeUser = '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true';
	var BPMiframeConf = {
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '',
			selectUserNames: ''
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	};
	//---业务经理B角
	if ($('#chooseBusiManagerb').val() == 'YES') {
		var _chooseBusiManagerb = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
			title: '选择人员'
		}));

		var $busiManagerbName = $('#busiManagerbName'),
			$busiManagerbId = $('#busiManagerbId'),
			$busiManagerbAccount = $('#busiManagerbAccount');

		$('#busiManagerbBtn').on('click', function () {

			_chooseBusiManagerb.init(function (relObj) {

				$busiManagerbId.val(relObj.userIds);
				$busiManagerbName.val(relObj.fullnames);
				$busiManagerbAccount.val(relObj.accounts);

			});
		});
	}
	//---风险经理
	if ($('#chooseRiskManager').val() == 'YES') {
		var _chooseRiskManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
			title: '风险经理'
		}));

		var $riskManagerName = $('#riskManagerName'),
			$riskManagerId = $('#riskManagerId'),
			$riskManagerAccount = $('#riskManagerAccount');

		$('#riskManagerBtn').on('click', function () {

			_chooseRiskManager.init(function (relObj) {

				$riskManagerId.val(relObj.userIds);
				$riskManagerName.val(relObj.fullnames);
				$riskManagerAccount.val(relObj.accounts);

			});
		});
	}
	//---法务经理
	if ($('#chooseLegalManager').val() == 'YES') {
		var _chooseLegalManager = new BPMiframe(BPMiframeUser, $.extend({}, BPMiframeConf, {
			title: '法务经理'
		}));

		var $legalManagerName = $('#legalManagerName'),
			$legalManagerId = $('#legalManagerId'),
			$legalManagerAccount = $('#legalManagerAccount');

		$('#legalManagerBtn').on('click', function () {

			_chooseLegalManager.init(function (relObj) {

				$legalManagerId.val(relObj.userIds);
				$legalManagerName.val(relObj.fullnames);
				$legalManagerAccount.val(relObj.accounts);

			});
		});
	}
	//---评估公司
	if ($('#evaluateCompanyBtn').length) {
		var getList = require('zyw/getList');
		var _getList = new getList();
		var _AssessmentCompany = $('#AssessmentCompany').val();
		_getList.init({
			title: '评估公司',
			ajaxUrl: '/baseDataLoad/loadAssessCompany.json',
			btn: '#evaluateCompanyBtn',
			input: ['evaluateCompanyName', 'evaluateCompanyId'],
			tpl: {
				tbody: ['{{each pageList as item i}}',
					'    <tr class="fn-tac">',
					'        <td>{{item.companyName}}</td>',
					'        <td>{{item.region}}</td>',
					'        <td>{{item.cityName}}</td>',
					'        <td><a class="choose" evaluateCompanyName="{{item.companyName}}" evaluateCompanyId="{{item.companyId}}" href="javascript:void(0);">选择</a></td>',
					'    </tr>',
					'{{/each}}'
				].join(''),
				form: ['公司名称：',
					'<input class="ui-text fn-w200" type="text" name="companyName">&nbsp;&nbsp;',
					'<input type="hidden" name="region" value="' + _AssessmentCompany + '">',
					// '地域属性：<select class="ui-select" name="region">',
					// '<option value="">全部</option><option value="INSIDE_CITY">市内</option><option value="OUTSIDE_CITY">市外</option>',
					// '</select>&nbsp;&nbsp;',
					'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
				].join(''),
				thead: ['<th width="200">公司名称</th>',
					'<th>地域属性</th>',
					'<th>所在城市</th>',
					'<th width="50">操作</th>'
				].join(''),
				item: 4
			}
		});
	}
	//------ 审核选人 end

	//------ 2017.01.12 打印 申请贷款/担保情况 start

	$('.fnPrint').on('click', function () {

		var $form = $(this).parents('form')

		$form.find('.ui-btn-submit').remove()

		util.print($form.html())

	})

	//------ 2017.01.12 打印 申请贷款/担保情况 end


	//------ 导出
	$('#fnDoExport').on('click', function () {

		var $ex = $('.fnPrintBody')

		$ex.find('.ui-btn-submit').remove()

		util.css2style($ex)

		$('head link, script, .ui-tool, #industryBox').remove()

		//document.documentElement.outerHTML
		var deflate = pako.gzip(document.documentElement.outerHTML, {
			to: 'string'
		});
		//console.log(pako.ungzip(deflate,{ to: 'string' }));
		$.ajax({
			type: 'post',
			url: '/baseDataLoad/createDoc.json',
			data: {
				formId: util.getParam('formId'),
				type: 'SET_UP',
				decode: 'yes',
				htmlData: encodeURIComponent(deflate)
			},
			success: function (res) {
				if (res.success) {
					window.location.href = res.url;
					setTimeout(function() {
						window.location.reload(true)
					}, 1000)
				} else {
					alert(res.message)
					window.location.reload()
				}
			}
		})

	})

});