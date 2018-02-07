define(function(require, exports, module) {

	var util = require('util')

	var $fnContent = $('#fnContent')

	$('#fnPrint').click(function(event) {
		$fnContent.find('.m-blank, .ui-btn-submit').remove()
		$fnContent.find('.printshow').removeClass('fn-hide')
		util.print($fnContent.html())
	})

	// ------ 侧边栏 start
	var publicOPN = new (require('zyw/publicOPN'))();

	publicOPN.addOPN([ {
		name : '打印',
		alias : 'doPrient',
		event : function() {
			$fnContent.find('.m-blank, .ui-btn-submit').remove()

			util.print($fnContent.html())
		}
	}, {
		name : '导出',
		alias : 'doExport',
		event : function() {
			$fnContent.find('.m-blank, .ui-btn-submit').remove()

			document.getElementsByTagName('body')[0].innerHTML = $fnContent.html()

			$('head link').remove()

			$.ajax({
				type : 'POST',
				url : '/baseDataLoad/createDoc.json',
				data : {
					formId : util.getParam('formId'),
					type : 'RISK_LEVEL',
					htmlData : document.documentElement.outerHTML
				},
				success : function(res) {
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

		}
	} ])

	publicOPN.init().doRender();
	// ------ 侧边栏 end
});