define(function(require, exports, module) {
	var riskQuery = require('zyw/riskQuery');
	var initRiskQuery2 = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');
	var DOMcustomerName = document.getElementById('customName');
	var util = require('util');
	//通过页面不同的值，判断是否添加到侧边栏
	// 审核通用部分 start
	var auditProject = require('/js/tmbp/auditProject');
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
	}).initPrint('打印的url');

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	//publicOPN.init().doRender();
	var hasRiskReport = $('#hasRiskReviewReport').val();
	if ('YES' == hasRiskReport) {
		publicOPN.addOPN([{
			name: '风险审核报告',
			url: '/projectMg/riskreview/viewReview.htm?formId=' + $('#amendRecordId').val(),
		}, {
			name: document.getElementById('isViewRiskMenu') ? '查看失信黑名单' : '',
			alias: 'someEvent2',
			event: function() {

				initRiskQuery2.showSX();

			}

		}]);
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
	} else {

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

			};

			publicOPN.addOPN([{
				name: '查看失信黑名单',
				alias: 'someEvent2',
				event: function() {

					initRiskQuery2.showSX();

				}

			}]);
			var cp = $(window.frames[0].document).find('#checkPoint').val()
            if(cp != '' && typeof(cp) != "undefined"){
                publicOPN.addOPN([{
                    name: '编辑',
                    alias: 'eidt',
                    event: function() {
                        var fi = $(window.frames[0].document).find('#formId').val(),
                            ci = $(window.frames[0].document).find('#curIndex').val(),
                            url =''
                        if (ci == -1) {
                            url = '/projectMg/investigation/editDeclare.htm?formId='+fi+'&checkPoint='+cp+'&toIndex='+ci
                        } else {
                            url = '/projectMg/investigation/edit.htm?formId='+fi+'&checkPoint='+cp+'&toIndex='+ci
                        }
//                        window.open(url)
                        // console.log(url)
                        window.location.href = url
                    }
                }]);
            }
		}
	}
	publicOPN.init().doRender();

	$('body').on('click','#fnMOPN',function (e) {
		var cp = $(window.frames[0].document).find('#checkPoint').val();
		var a = $(this).find('li a');
		var added = false;
		a.each(function () {
			if( ($(this).attr('trigger')=='eidt') || ($(this).attr('trigger1')=='edit') )
			{
                added = true;

            }
        });
		if(!added){
            
            if(cp != '' && typeof(cp) != "undefined"){

                var dom = '<li><a href="javascript:void(0);" trigger1="edit">编辑</a></li>';

                $(dom).insertBefore($('#fnMOPN .ul li').eq(0));
                var fi = $(window.frames[0].document).find('#formId').val(),
                    ci = $(window.frames[0].document).find('#curIndex').val(),
                    url ='';

                if (ci == -1) {
                    url = '/projectMg/investigation/editDeclare.htm?formId='+fi+'&checkPoint='+cp+'&toIndex='+ci
                } else {
                    url = '/projectMg/investigation/edit.htm?formId='+fi+'&checkPoint='+cp+'&toIndex='+ci
                }
                
                $('#fnMOPN .ul li').eq(0).click(function (e) {
//                    window.open(url)
                    window.location.href = url
                })
            }

		}

    })

	//------ 侧边栏 end

	//是否上母公司会议
	$('body').on('change', '.radioChange', function(event) {

		var $this, $index, $radioSelect, $siblings;

		$this = $(this);
		$index = $this.index();
		$radioSelect = $('.radioSelect');
		$siblings = $this.siblings('.fnAuditRequired');

		$siblings.val(1);
		//!$index ? $radioSelect.show() : $radioSelect.hide();

		if (!$index) {

			$radioSelect.show().find('select').addClass('fnAuditRequired');

		} else {

			$radioSelect.hide().find('select').removeClass('fnAuditRequired');

		}

	}).find('.radioChange:checked').trigger('change');

});