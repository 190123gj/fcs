define(function(require, exports, module) {
	require('zyw/upAttachModify')
	var riskQuery = require('zyw/riskQuery');
	var initRiskQuery2 = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');

	if ($('#form').attr('audit') != 'YES') {
		//------ 侧边栏 start
		var publicOPN = new(require('zyw/publicOPN'))();

		var DOMcustomerName = document.getElementById('customerName');
		var util = require('util');
		//通过页面不同的值，判断是否添加到侧边栏
		if (document.getElementById('isViewRiskMenu')) {
			publicOPN.addOPN([{
				name: '风险检索',
				alias: 'someEvent',
				event: function() {
					util.risk2retrieve(DOMcustomerName.value, $('#customLicenseNo').val());
				}
			}, {
				name: '查看相似企业',
				alias: 'someEvent1',
				event: function() {

					initRiskQuery2.showXS();

				}

			}, {
				name: '查看失信黑名单',
				alias: 'someEvent2',
				event: function() {

					initRiskQuery2.showSX();

				}

			}]);
		}

		publicOPN.init().doRender();

	}

	require('Y-window');

	$('.fnUpAttachUl a').click(function(event) {
		/* Act on the event */
		var _this = $(this),
			_href = _this.attr('href');
		if (_href == 'javascript:void(0);') {
			var _val = _this.find('.fnAttachItem').attr('val'),
				_content = ['<div class="newPopup" style="width:500px;height:400px;">',
					'       <span class="close">×</span>',
					'        <dl>',
					'         <dd class="fn-p-reb fn-text-c">',
					'          <img style="max-width:100%;max-height:100%" src="' + _val + '">',
					'         </dd>',
					'        </dl>',
					'      </div>'
				].join("");
			$('body').Y('Window', {
				content: _content,
				modal: true,
				simple: true,
				closeEle: '.close'
			});
		}

	});


});