define(function (require, exports, module) {

	//Nav选中样式添加
	require('zyw/project/bfcg.itn.toIndex');
	//上传
	require('zyw/upAttachModify');

	var riskQuery = require('zyw/riskQuery');
	var initRiskQuery2 = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');

	var util = require('util');
	var openDirect = util.openDirect;

	$('body').on('click', '#customerId', function (event) {

		openDirect('/assetMg/index.htm', '/assetMg/list.htm?customerId=' + $(this).attr('customerId'), '/assetMg/list.htm');

	});


	if ($('#auditScript').attr('audit') != "YES") {

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
			}]);
		}
		var DOMcustomerName = document.getElementById('customerName');
		var util = require('util');
		//通过页面不同的值，判断是否添加到侧边栏
		if (document.getElementById('isViewRiskMenu')) {
			publicOPN.addOPN([{
				name: '风险检索',
				alias: 'someEvent',
				event: function () {
					util.risk2retrieve(DOMcustomerName.value, $('#customLicenseNo').val());
				}
			}, {
				name: '查看相似企业',
				alias: 'someEvent1',
				event: function () {

					initRiskQuery2.showXS();

				}

			}, {
				name: '查看失信黑名单',
				alias: 'someEvent2',
				event: function () {

					initRiskQuery2.showSX();

				}

			}]);
		}
		publicOPN.init().doRender();
		//------ 侧边栏 end
	}

	var Arr = [
        [],
        [],
        [],
		[],
        [],
        [],
		[],
        [],
        [],
	];

	$('body').on('change', '.changeMoneyUnit', function (event) {

		var $this, $target, $index, $val, $rideNum;
        //console.log(Arr)
		$this = $(this);
		$val = $(this).val();
		$index = $('body .changeMoneyUnit').index($this);
		$target = $this.parents('dl').find('tbody td');

		//console.log($index)

		if ($val == 'UNIT') {

			$rideNum = 1;

		} else {

			$rideNum = 0.0001;

		}

		$target.each(function (index, el) {

			var $el, $text;

			$el = $(el);
			$text = parseFloat($el.text().replace(/\,/g, ''));

			console.log(typeof(Arr[$index][index]))

			if (Arr[$index][index] == undefined) { //如果相应队列中不存在该值则添加

				Arr[$index][index] = $text;

			}
			if (Arr[$index][index]) {

				$el.text((Math.round(Arr[$index][index] * $rideNum * 100) / 100).toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));

			}

		});


	});

	function parseDom(arg) {

		var objE = document.createElement("div");

		objE.innerHTML = arg;　　

		return objE;

	};

	var template = require('arttemplate');
	//js引擎
	$('.popup').click(popup);

	function popup() {
		var _this = $(this),
			_parents = _this.parents('tr'),
			_post;

		//请求已保存数据
		$.ajax({
			url: '/projectMg/investigation/findFInvestigationFinancialReviewData.htm',
			type: 'post',
			dataType: 'json',
			async: false,
			data: {
				id: _parents.attr('dataid')
			},
			success: function (res) {
				_post = res;
			}
		})


		var explainationJson = _post.data.explaination

		// _post.data.explaination = parseDom(explainationJson);
		// console.log(_post);

		var source = ['<div class="newPopup">',
			'<span class="close">×</span>',
			'<dl>',
			'<dt><span>数据解释</span></dt>',
			'<dd class="fn-tac">',
			'<table class="m-table" border="1">',
			'<thead>',
			'<tr>',
			'<th class="onbreak">序号</th>',
			'<th>名称</th>',
			'<th>金额（元）</th>',
			'<th>占比率（%）</th>',
			'</tr>',
			'</thead>',
			'<tbody id="test">',
			'{{if data.kpiExplains}}',
			'{{each data.kpiExplains as value i}}',
			'<tr class="fnNewLine" orderName="kpiExplains">',
			'<td class="testNum">{{i+1}}</td>',
			'<td>{{value.kpiName}}</td>',
			'<td>{{value.kpiValue}}</td>',
			'<td>{{value.kpiRatio}}</td>',
			'</tr>',
			'{{/each}}',
			'{{/if}}',
			'</tbody>',
			'</table>',
			'<div class="fn-tac fn-pt30">',
			'<span class="contract-text" style="max-width:100%;">' + explainationJson + '</span>',
			'</div>',
			'</dd>',
			'</dl>',
			'</form>',
			'</div>'
		].join('');

		//弹窗
		$('body').Y('Window', {
			content: template.compile(source)(_post),
			modal: true,
			key: 'modalwnd',
			simple: true
		});
		var modalwnd = Y.getCmp('modalwnd');
		modalwnd.bind(modalwnd.wnd.find('.close'), 'click', function () {
			modalwnd.close();
		});

	}



	//收起
    $('.packBtn').click(function (event) {

        var _this = $(this);
        _this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
        _this.parent().next().slideToggle();
    });


	if($('.isModificationTbody').length){
        $('.isModificationTbody').each(function () {

            if($('.contrastTabCon').index($(this).parents('.contrastTabCon')) == 2){

                this.style.background = '#E61A1A';

            }

        })
    }
});