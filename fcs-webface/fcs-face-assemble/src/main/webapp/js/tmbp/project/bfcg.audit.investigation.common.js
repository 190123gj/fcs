define(function(require, exports, module) {

	var riskQuery = require('zyw/riskQuery');
	// var initRiskQuery2 = new riskQuery.initRiskQuery(false, 'customName', 'licenseNo', 'customerType');
	var initRiskQuery2 = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');

	if ($('#form').attr('audit') != 'YES') {
		//------ 侧边栏 start
		var publicOPN = new(require('zyw/publicOPN'))();

		var DOMcustomerName = document.getElementById('customerName');
		var util = require('util');
		//通过页面不同的值，判断是否添加到侧边栏
		if (document.getElementById('isViewRiskMenu')) {

			// 个人没有 风险检索、查看查看相似企业
			if ($('#customType').val() != 'PERSIONAL') {

				publicOPN.addOPN([{
					name: $('#customerType').val() != 'person' ? '风险检索' : '',
					alias: 'someEvent',
					event: function() {
						util.risk2retrieve(DOMcustomerName.value, $('#customLicenseNo').val());
					}
				}, {
					name: $('#customerType').val() != 'person' ? '查看相似企业' : '',
					alias: 'someEvent1',
					event: function() {

						initRiskQuery2.showXS();

					}

				}]);

			}

			publicOPN.addOPN([{
				name: '查看失信黑名单',
				alias: 'someEvent2',
				event: function() {

					initRiskQuery2.showSX();

				}

			}]);
		}

		publicOPN.init().doRender();

	}

});