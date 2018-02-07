define(function (require, exports, module) {
	// 审核通用部分 start
	var auditProject = require('/js/tmbp/auditProject');
	var _auditProject = new auditProject(document.getElementById('auditFormBtn') ? 'auditFormBtn' : '');
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.init().doRender();
	//------ 侧边栏 end


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
	//---评估公司
	if ($('#evaluateCompanyBtn').length) {
		var getList = require('zyw/getList');
		var _getList = new getList();
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
					'地域属性：<select class="ui-select" name="region">',
					'<option value="">全部</option><option value="INSIDE_CITY">市内</option><option value="OUTSIDE_CITY">市外</option>',
					'</select>&nbsp;&nbsp;',
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

});