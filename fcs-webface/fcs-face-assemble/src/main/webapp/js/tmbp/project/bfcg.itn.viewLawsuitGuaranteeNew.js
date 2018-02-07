define(function(require, exports, module) {


	//(new(require('zyw/publicOPN'))()).addOPN([]).init().doRender();

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
	
	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();
	//publicOPN.init().doRender();
	var hasRiskReport = $('#hasRiskReviewReport').val();
	if ('Y'==hasRiskReport) {
		publicOPN.addOPN([{
	 		name: '风险审核报告',
	 		url: '/projectMg/investigation/viewLitigationAudit.htm?formId='+$('#amendRecordId').val(),
	 	}]);
	} 
	publicOPN.init().doRender();	
	//------ 侧边栏 end

});