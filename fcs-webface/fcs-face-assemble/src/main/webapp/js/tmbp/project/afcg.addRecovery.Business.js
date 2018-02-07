define(function(require, exports, module) {
	//保后项目汇总表 新增
	require('Y-msg');
	//通用 上传附件、图片
	require('zyw/upAttach');
	//增删行
	require('zyw/opsLine');
	//模板引擎
	var template = require('arttemplate');
	var getList = require('zyw/getList');
	var util = require('util');
	// 
	//获取id
	var formId = util.getParam('formId');
	//假若有id
	if (!!!formId) {
		var _getList = new getList();
		_getList.init({
			title: '选择',
			btn: '#choose',
			input: ['projectNumber', 'customerName', 'projectName', 'contractNumber', 'creditPeriod', 'creditAmount', 'relieveAmount'],
			tpl: {
				tbody: ['{{each list as item i}}',
					'    <tr class="fn-tac">',
					'        <td class="item" id="{{item.name}}">{{item.name}}</td>',
					'        <td>{{item.name}}</td>',
					'        <td>{{item.name}}</td>',
					'        <td>{{item.name}}</td>',
					'        <td>{{item.name}}</td>',
					'        <td><a class="choose" projectNumber="{{item.name}}" customerName="{{item.name}}" projectName="{{item.name}}" contractNumber="{{item.name}}" creditType="{{item.name}}" creditAmount="{{item.name}}" relieveAmount="{{item.name}}" href="javascript:void(0);">选择</a></td>',
					'    </tr>',
					'{{/each}}'
				].join(''),
				form: ['部门：',
					'<select class="ui-select" name="text1">',
					'    <option value="">&nbsp;</option>',
					'    <option value="1">&ge;</option>',
					'    <option value="2">&lt;</option>',
					'</select>&nbsp;&nbsp;',
					'角色：',
					'<select class="ui-select" name="text1">',
					'    <option value="">&nbsp;</option>',
					'    <option value="1">&ge;</option>',
					'    <option value="2">&lt;</option>',
					'</select>&nbsp;&nbsp;',
					'职务：',
					'<select class="ui-select" name="text1">',
					'    <option value="">&nbsp;</option>',
					'    <option value="1">&ge;</option>',
					'    <option value="2">&lt;</option>',
					'</select>&nbsp;&nbsp;',
					'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
				].join(''),
				thead: ['<th width="100">项目编号</th>',
					'<th width="120">客户名称</th>',
					'<th width="120">项目名称</th>',
					'<th width="100">授信类型</th>',
					'<th width="100">授信金额</th>',
					'<th width="50">操作</th>'
				].join(''),
				item: 6
			}
		});
	}

	//--------dd_Recovery_Justice
	//select start
	$("#someId").click(function() {
		var self = $(this).children("option:selected")
		indexI = self.index();
		$('.node-type-box').children(".node-type").eq(indexI).removeClass('fn-hide').siblings().addClass("fn-hide");
	})
	//select end


});