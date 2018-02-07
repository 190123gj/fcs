define(function(require, exports, module) {
	// ------ 字数限制 start
	// 该部分内容小的输入框字符限制20字，大输入框1000字（如果特殊说明）
	$('input').each(function(index, el) {

		var _this = $(this);
		// 隐藏
		if (this.type == 'hidden' || this.type == 'button' || this.type == 'submit' || this.type == 'checkbox' || this.type == 'radio') {
			return;
		}

		// 只读
		if (_this.attr('readonly') || _this.attr('maxlength') || _this.hasClass('fn-input-hidden')) {
			return;
		}

		_this.attr('maxlength', '20');

		if (_this.hasClass('fnMakeMoney')) {
			_this.attr('maxlength', '15');
		}

	});
	// ------ 字数限制 end

	// 项目管理 > 追偿管理 > 新增、查看
	require('input.limit');
	require('Y-msg');
	require('zyw/upAttachModify');
	require('validate');
	require('zyw/opsLine');

	var util = require('util'), getList = require('zyw/getList'), $body = $('body'), $form = $('#form');

	// 建立必填机制
	// 债务人重整或破产清算 只有在切换选择中建立
	$form.validate(util.validateDefault);

	// 建立必填机制
	function setValidateRequired($div) {

		$div.find('.fnInput').each(function(index, el) {

			$(this).rules('add', {
				required : true,
				messages : {
					required : '必填'
				}
			})

		});
	}





	$('.fnLookHide').html('附件格式请上传' + util.fileType.all.replace(/(\*|\.|\s)/g, '').replace(/\;/g, '、') + '格式。');

	if (!!document.getElementById('fnIsView').value) {
		// ------ 查看模式 start
		(function($body) {

			var _readLoad = new util.loading();
			_readLoad.open();

			var $fnRecoverySSStepTab = $body.find('#fnRecoverySSStepTab').addClass('read');

			$fnRecoverySSStepTab.find('.last').remove();

			$fnRecoverySSStepTab.find('li').last().addClass('end').end().each(function(index, el) {

				// var _div = document.createElement('div');
				// _div.className = 'timestamp';
				// _div.innerHTML = el.getAttribute('timestamp');
                //
				// el.appendChild(_div);

			});

			setTimeout(function() {

				$body.find('.fnChoosePlan').each(function(index, el) {

					this.setAttribute('disabled', 'disabled');

				});

				$body.find('select').each(function(index, el) {

					var $this = $(this), _text = $this.find('option:selected').text()

					$this.before(_text).remove();

					// var self = this,
					// options = self.getElementsByTagName('option'),
					// _html;
					// for (var i = 0; i < options.length; i++) {

					// if (options[i].value == self.value) {
					// options[i].setAttribute('selected', 'selected')
					// _html = options[i];
					// }

					// }

					// self.setAttribute('disabled', 'disabled');

				});

				$body.find('input.text,input.ui-text,textarea:not(#ueditor_textarea_editorValue)').each(function(index, el) {

					// input\textarea -> text

					var _val = this.value, _prent = this.parentNode, _div = document.createElement('span');

					if (this.className.indexOf('ui-textarea') != -1) {

						_div = document.createElement('div');
						if (this.className.indexOf('fnMakeUE') != -1) {
							_div.className = 'contract-text';
						} else {
							_div.className = 'fn-w700';
						}

					}

					if (/fnMakeMoney/g.test(this.className)) {

						_val = util.num2k(_val);

					}

					_div.innerHTML = _val;

					// 时间段
					if (this.className.indexOf('fnInputDateS') != -1) {

						_div.innerHTML += ' -';

					}

					_prent.insertBefore(_div, this);
					_prent.removeChild(this);

				}).end().find('.ui-btn:not(.viewDaliog, .ui-btn-ignore), .fnUpAttachBtn, .m-table-btn, .m-required, td span.fn-f30, .fnLookHide, .edui-editor, .edui-default').remove();

				_readLoad.close();

			}, 1000);

		})($body);
		// ------ 查看模式 end
	} else {

		var editLoad = new util.loading();
		editLoad.open();
		// ------ 编辑模式 start
		setTimeout(function() {

			function clearInput($div) {

				// $div.find('.fnUpAttachBtn, .fnUpAttachDel, .fnTableMergeAdd,
				// .fnTableMergeDel, .m-table-btn-add, .fnBondedAdd, .fnAddLine,
				// .m-table-btn-del, .edui-editor,
				// .edui-default').remove().end().find('input.text,
				// textarea,input.ui-text').prop('readonly',
				// false).end().find('input.laydate-icon').attr('nodate',
				// 'IS').end()
				// .find('select').each(function(index, el) {
				//
				// var self = this, options =
				// self.getElementsByTagName('option'), _html =
				// document.createElement('input');
				// _html.type = 'hidden';
				// _html.name = self.name;
				// for (var i = 0; i < options.length; i++) {
				//
				// if (options[i].value == self.value) {
				// options[i].setAttribute('selected', 'selected');
				// // _html = options[i];
				// _html.value = options[i].value;
				// }
				//
				// }
				// self.setAttribute('disabled', 'false');
				// console.log(self)
				// self.parentNode.appendChild(_html);
				// // self.innerHTML = '';
				// // self.appendChild(_html);
				//
				// }).end().find('.fnMakeUE').each(function(index, el) {
				// var $this = $(this)
				// $this.parent().addClass('contract-text').prepend($this.val())
				// });
			}

			// 更新 已选择的就不能修改
			// 不能上传图片 不能修改值
			// 诉讼和债务人重整或破产清算 要分开处理才行
			// var $fnRecoveryPlan = $body.find('#fnRecoveryPlan');

			var $fnDebtor = $body.find('.fnDebtor'), $fnSS = $body.find('.fnSS'), $fnProjectRecoveryOther = $body.find('.fnProjectRecoveryOther');

			if ($('[name="litigationOn"]:checked').length > 0) {
				// 如果诉讼被选中
				clearInput($fnSS)
			}

			if ($('[name="debtorReorganizationOn"]:checked').length > 0) {
				// 债务人重整或破产清算
				clearInput($fnDebtor)
			}

			if ($('[name="otherOn"]:checked').length > 0) {
				// 其他
				clearInput($fnProjectRecoveryOther)
			}

			$('.fnChoosePlan:checked').each(function(i, e) {

				e.setAttribute('disabled', 'disabled');

				var _input = document.createElement('input');
				_input.name = e.name;
				_input.value = e.value;
				_input.type = 'hidden';

				e.parentNode.appendChild(_input);

			});

			editLoad.close();

		}, 1000);
		// ------ 编辑模式 end

	}

	// ------ 选择项目 start
	var _projectList = new getList();
	_projectList.init({
		width : '90%',
		title : '项目列表',
		ajaxUrl : '/projectMg/recovery/recoveryProject.json',
		btn : '#fnChoose',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td>{{item.projectCode}}</td>', '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>', '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>', '        <td>{{item.timeLimit}}</td>', '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>', '    </tr>', '{{/each}}' ].join(''),
			form : [ '项目编号：', '<input class="ui-text fn-w160" name="projectCode" type="text">', '&nbsp;&nbsp;', '客户名称：', '<input class="ui-text fn-w160" name="customerName" type="text">', '&nbsp;&nbsp;', '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">' ].join(''),
			thead : [ '<th>项目编号</th>', '<th>客户名称</th>', '<th>项目名称</th>', '<th width="80">授信期限</th>', '<th width="50">操作</th>' ].join(''),
			item : 5
		},
		callback : function($a) {
			window.location.href = '/projectMg/recovery/toSaveProjectRecovery.htm?projectCode=' + $a.attr('projectCode');
		}
	});
	// ------ 选择项目 end

	// ------ 更新、编辑明细 start
	$('#fnEditorDetail').on('click', function() {

		$('.fnDetail').toggleClass('fn-hide');

	});
	// ------ 更新、编辑明细 end

	// ------ 方案切换 start
	var $fnPlanTab = $('.fnPlanTab'), $fnPlanItem = $('.fnPlanItem');

	$fnPlanTab.on('click', 'li:not(.active)', function() {

		var $this = $(this);
		$this.addClass('active').siblings().removeClass('active');
		$fnPlanItem.addClass('fn-hide').eq($this.index()).removeClass('fn-hide');

	});

	// ------ 方案切换 end

	// ------ 选择方案 start

	$('.fnChoosePlan').on('click', function() {

		if (!!!document.getElementById('projectCode').value) {
			Y.alert('提示', '请先选择项目');
			$('.fnChoosePlan').removeProp('checked');
			return;
		}
		showPlan();

	});

	showPlan();

	function showPlan() {

		var arr = [];

		$('.fnChoosePlan:checked').each(function() {
			arr.push(this.name);
		});

		if (arr.length) {

			$('#fnNoPlan').addClass('fn-hide');
			$('#fnRecoveryPlan,#fnDoSave').removeClass('fn-hide');

			$fnPlanTab.find('li').addClass('fn-hide').each(function(index, el) {

				if ($.inArray(el.getAttribute('alias'), arr) > -1) {

					$(this).removeClass('fn-hide');

				}

			});

			$fnPlanTab.find('li:not(.fn-hide)').eq(0).trigger('click');

		} else {

			$('#fnRecoveryPlan,#fnDoSave').addClass('fn-hide');
			$('#fnNoPlan').removeClass('fn-hide');

		}

		// 建立 债务人重整或破产清算 必填
		if ($.inArray('debtorReorganizationOn', arr) > -1) {
			$('.fnDebtor').find('.fnInput').each(function(index, el) {
				$(this).rules('add', {
					required : true,
					messages : {
						required : '必填'
					}
				});
			});
		} else {
			// 移除 债务人重整或破产清算 必填
			$('.fnDebtor').find('.fnInput').each(function(index, el) {
				$(this).rules('remove', 'required');
			});
		}

		// 其他 必填
		if ($.inArray('otherOn', arr) > -1) {
			$('.fnProjectRecoveryOther').find('.fnInput').each(function(index, el) {
				$(this).rules('add', {
					required : true,
					messages : {
						required : '必填'
					}
				});
			});
		} else {
			$('.fnProjectRecoveryOther').find('.fnInput').each(function(index, el) {
				$(this).rules('remove', 'required');
			});
		}
	}

	// ------ 选择方案 end

	// ------ 诉讼步骤切换 start
	var $fnRecoverySSStepTab = $('#fnRecoverySSStepTab'), $fnRecoverySSStepBox = $('#fnRecoverySSStepBox'), NODENAMEREUSE = [], // 多次选择的节点//2017-7-13现在全部都是可以重复选择了
	NODENAMEREFEREE = [ 'projectRecoveryLitigationOpeningOrder', 'projectRecoveryLitigationSecondAppealOrder', 'projectRecoveryLitigationAdjournedProcedureOrder', 'projectRecoveryLitigationAdjournedSecondOrder' ]; // 需要裁判的节点

	// 裁判的标示
	var REFEREEMARK = {
		projectRecoveryLitigationOpeningOrder : 'OPENING',
		projectRecoveryLitigationSecondAppealOrder : 'SECOND_APPEAL',
		projectRecoveryLitigationAdjournedProcedureOrder : 'ADJOURNED_PROCEDURE_FIRST',
		projectRecoveryLitigationAdjournedSecondOrder : 'ADJOURNED_PROCEDURE_SECOND'
	};

	// --- 删除节点 后的操作
	function delNodeCallBack() {

		$fnRecoverySSStepTab.find('li:not(.last)').last().trigger('click');

		// 删除后，针对最后一个 选择节点 去掉 disabled
		// 已存的节点不能修改
		if (!!!$fnRecoverySSStepBox.find('.fnChooseNode').last().attr('saved')) {
			$fnRecoverySSStepBox.find('.fnChooseNode').last().removeProp('disabled');
		}

		$('#fnRecoverySSStepTab li').not('.last').each(function(index) {
			var $this = $(this)
			$this.attr('litigationindex', index)
		})

		$('#fnRecoverySSStepBox .fnSSStep').each(function(index) {
			var $this = $(this)
			$this.attr('litigationindex', index)
			$this.find('[name$="litigationIndex"]').val(index)
		})
	}

	setTimeout(function() {
		$fnRecoverySSStepTab.find('li').first().trigger('click');
		// 已存的节点不能修改
		if ($fnRecoverySSStepBox.find('.fnChooseNode').last().val()) {

			$fnRecoverySSStepBox.find('.fnChooseNode').last().attr('saved', 'saved');

		}
	}, 0);


	$fnRecoverySSStepTab.on('click', 'li:not(.last)', function() {

		// 切换
		var $this = $(this);
		$this.addClass('active').siblings().removeClass('active');
		$fnRecoverySSStepBox.find('.fnSSStep').addClass('fn-hide').eq($this.index()).removeClass('fn-hide');

	}).on('click', '.recover-ss-step-del', function(e) {

		e.stopPropagation();

		// 删除
		var $this = $(this), _i = $this.parent().index('#fnRecoverySSStepTab li'), $litigationIndex = $this.parent().attr('litigationIndex');
		$this.parent().remove();
		$this.remove();
		$('#fnRecoverySSStepBox .fnSSStep[litigationIndex=' + $litigationIndex + ']').remove();
		delNodeCallBack();

		// if ($this.parent().nextAll().length > 1) {
		// // 如果后面还有节点
		// Y.confirm('提醒', '删除当前节点，后面的节点也会一并删除，确定要删了吗？', function (opn) {
		//
		// if (opn == 'yes') {
		//
		// $fnRecoverySSStepBox.find('.fnSSStep').eq(_i).nextAll().remove().end().remove();
		// $this.parent().nextAll().each(function (index, el) {
		//
		// if (util.hasClass(el, 'last')) {
		// return false;
		// }
		// $(this).remove();
		//
		// }).end().remove();
		//
		// delNodeCallBack();
		//
		// }
		//
		// });
		// } else {
		// $fnRecoverySSStepBox.find('.fnSSStep').eq(_i).remove();
		// $this.parent().remove();
		// delNodeCallBack();
		// }

		// 删除的时候，是否关联了 查看档案
		CHOOSELOOKFILELIST = hasLookFileLink();
		if (!CHOOSELOOKFILELIST) {
			publicOPN.removeOPN('查看档案').remove().doRender();
		}

	}).on('click', 'li.last', function() {

		// 新增
		var $this = $(this), _h = '<li><label class="fn-csp">选择节点</label><span class="recover-ss-step-del"></span></li>';

		// 找出上一个节点是什么，然后截取
		var _$lastChoose = $fnRecoverySSStepBox.find('.fnChooseNode').last(),
			_$firstChoose = $fnRecoverySSStepBox.find('.fnChooseNode').first(),
			_div = document.createElement('div');

		console.log(_$firstChoose)

		// 获取active
		var $active = $('#fnRecoverySSStepTab li.active')
        var $active_index = $active.index()
        var $active_box = $('#fnRecoverySSStepBox ').find('.fnSSStep').eq($active_index)

		// 假若到了最后一个节点
		// if (!_$lastChoose.val()) {
		// 	Y.alert('提醒', '请先已添加的步骤选择节点');
		// 	return;
		// }

		// 对上一个节点添加 disabled 避免中途修改，造成流程错乱
		//$fnRecoverySSStepBox.find('.fnChooseNode').last().prop('disabled', 'disabled');

		_$firstChoose.find('option').eq(0).nextAll().each(function(i, el) {
			// 如果上一个是裁判，需要跟裁判的节点去掉
			// if (i == 0 && (_$lastChoose.val() ==
			// 'projectRecoveryLitigationRefereeOrder' && $.inArray(el.value,
			// NODENAMEREUSE) > -1)) {
			// return true;
			// }
			_div.appendChild(el.cloneNode(true));

		});
		var _select = [ '<div class="m-item">', '    <label class="m-label"><span class="m-required">*</span>节点名称：</label>', '    <select class="ui-select fnChooseNode" name="">', '        <option value="">请选择</option>', _div.innerHTML, '    </select>', '</div>', '<div class="m-dashed fn-mt5 fn-mb20"></div>', '<div class="fnSSStepView"></div>' ].join('');

		// 步骤添加
		if ($active.length) {
			$active.after(_h)
			$active_box.after(('<div class="fnSSStep fn-hide">' + _select + '</div>'))
			$active_box.next().find('.fnChooseNode option').removeProp('selected')
		} else {
			$this.before(_h)
			$fnRecoverySSStepBox.append('<div class="fnSSStep fn-hide">' + _select + '</div>').find('option').removeProp('selected')
		}

		$('#fnRecoverySSStepTab li').not('.last').each(function(index) {
			var $this = $(this)
			$this.attr('litigationindex', index)
		})

		$('#fnRecoverySSStepBox .fnSSStep').each(function(index) {
			var $this = $(this)
			$this.attr('litigationindex', index)
			$this.find('[name$="litigationIndex"]').val(index)
		})

		// 如果节点是可复用的
		// if ($.inArray(_$lastChoose.val(), NODENAMEREUSE) > -1) {
		//
		// _div.appendChild(_$lastChoose.find('option[value="' +
		// _$lastChoose.val() + '"]')[0].cloneNode(true));
		//
		// }

		// 如果节点需要裁判
		// if ($.inArray(_$lastChoose.val(), NODENAMEREFEREE) > -1) {
		//
		// var _cp = document.createElement('option');
		// _cp.value = 'projectRecoveryLitigationRefereeOrder';
		// _cp.innerHTML = '裁判';
		// _div.insertBefore(_cp, _div.getElementsByTagName('option')[0]);
		//
		// }

		if ($active.length) {
			$active.next().trigger('click');
		} else {
			$this.prev().trigger('click');
		}

	});

	$fnRecoverySSStepBox.on('change', '.fnChooseNode', function() {

		// 选择节点，改变标题
		var $this = $(this),
			_i = $this.index('#fnRecoverySSStepBox .fnChooseNode'),
			_text = $this.find('option[value="' + $this.val() + '"]').html();
		_$label = $fnRecoverySSStepTab.find('label').eq(_i);

		if (!!!this.value) {

			// 请选择节点
			_$label.html('选择节点');
			$this.parents('.fnSSStep').find('.fnSSStepView').html('');
			return;
		}

		_$label.html(_text);

		if (_text.length > 5) {
			_$label.parent().addClass('line-feed');
		} else {
			_$label.parent().removeClass('line-feed');
		}

		var _$fnSSStepView = $this.parents('.fnSSStep').find('.fnSSStepView');

		_$fnSSStepView.html($('.' + $this.val()).html()).attr('alias', $this.val());

		// 建立必填机制
		setValidateRequired(_$fnSSStepView);

		// 预加载一些数据
		// 立案
		switch ($this.val()) {

		// ---- 立案 实现担保物权特别程序 start
		case 'projectRecoveryLitigationPlaceOnFileOrder':
		case 'projectRecoveryLitigationSpecialProcedureOrder':

			var _$target = $('.fnSSStepView[alias="projectRecoveryLitigationBeforePreservationOrder"]');
			if (_$target.length > 0) {

				preloadData(_$target.last(), _$fnSSStepView, [ 'fnCourt', 'fnAgentLawFirm', 'fnAgentAttorney', 'fnAgentJudge' ])

			}
			break;
		// ---- 立案 实现担保物权特别程序 end

		// ---- 开庭 start
		case 'projectRecoveryLitigationOpeningOrder':

			var _$target = $('.fnSSStepView[alias="projectRecoveryLitigationBeforeTrailOrder"]');
			if (_$target.length > 0) {

				preloadData(_$target.last(), _$fnSSStepView, [ 'fnOpeningTime' ])

			}
			break;
		// ---- 开庭 end

		}

		if (this.value == 'projectRecoveryLitigationRefereeOrder') {

			// 如果是裁判，把前面的节点标示放到裁判里面
			_$fnSSStepView.find('.projectRecoveryRefereeType').val(REFEREEMARK[$fnRecoverySSStepBox.find('.fnChooseNode').eq(-2).val()])

		}

		// 立案、实现担保物权特别程序、强制执行公证执行证书 侧边栏增加 查看档案
		opnLookFile2OPN($this);

	});

	// ---- 添加 查看档案 操作 start
	function opnLookFile2OPN($self) {

		// 曾经选了
		if (HASLOOKFILELISTLINK) {
			return;
		}

		var _i = $.inArray($self.val(), LOOKFILELISTNODE);

		if ($self.attr('look') == 'IS') {
			// 如果是当前节点关联了 查看档案

			if (_i == -1) {
				var _ii = publicOPN.indexOf('查看档案');

				if (_ii > -1) {
					publicOPN.removeOPN(_ii).remove().doRender();
				}
				CHOOSELOOKFILELIST = false;
				$self.removeAttr('look');
			}

		} else {

			if (CHOOSELOOKFILELIST) {
				return;
			}

			if (_i > -1) {
				publicOPN.remove().addOPN([ LOOKFILELISTOPNOBJ ]).doRender();
				CHOOSELOOKFILELIST = true;
				$self.attr('look', 'IS');
			} else {
				var _ii = publicOPN.indexOf('查看档案');

				if (_ii > -1) {
					publicOPN.removeOPN(_ii).remove().doRender();
				}
			}

		}

	}

	// ---- 添加 查看档案 操作 start

	// ---- 预加载数据 start
	function preloadData($target, $itself, dataClassArr) {

		$.each(dataClassArr, function(i, o) {

			$itself.find('.' + o).val($target.find('.' + o).val());

		});

	}

	// ---- 预加载数据 end

	// ------ 诉讼步骤切换 end

	// ------ table 带合并的单元格的操作 start
	var $fnTableMergeTbody;
	$body.on('click', '.fnTableMergeMg .fnTableMergeAdd', function() {

		// 新增一个
		var $mg = $(this).parents('.fnTableMergeMg'), $th = $mg.find('.fnTableMergeTh'), $tbody = $mg.find('.fnTableMergeTbody'), $template = $mg.find('.fnTableMergeTemplate');

		$tbody.append($template.html());
		$th.attr('rowspan', $tbody.find('tr').length);

	}).on('click', '.fnTableMergeMg .fnTableMergeDel', function() {

		// 删除
		var $tr = $(this).parents('tr'), $mg = $tr.parents('.fnTableMergeMg'), $th = $mg.find('.fnTableMergeTh'), $tbody = $mg.find('.fnTableMergeTbody');

		$fnTableMergeTbody = $tbody;

		$tr.remove();
		$th.attr('rowspan', $tbody.find('tr').length);
		$tbody.find('tr').eq(0).find('input').trigger('blur');

	});

	// ------ table 带合并的单元格的操作 end

	// ------ 日期限制 最大或最小 start
	$body.on('blur', '.fnInputDateS', function() {

		var $this = $(this), $input = $this.parents('.fnInputDateP').find('.fnInputDateE');

		if ($input.attr('nodate') == 'IS') {
			return;
		}

		$input.attr('onclick', 'laydate({min: "' + $this.val() + '"})');

	}).on('blur', '.fnInputDateE', function() {

		var $this = $(this), $input = $this.parents('.fnInputDateP').find('.fnInputDateS');

		if ($input.attr('nodate') == 'IS') {
			return;
		}

		$input.attr('onclick', 'laydate({max: "' + $this.val() + '"})');

	});
	// ------ 日期限制 最大或最小 end

	// ------ 侧边栏 start
	var publicOPN = new (require('zyw/publicOPN'))();

	if (document.getElementById('projectCode') && document.getElementById('projectCode').value) {

		publicOPN.addOPN([ {
			name : '保存',
			alias : 'doSubmit',
			event : function() {

				doSubmit(true);

			}
		} ]);

	}

	var $customerId = $('#customerId');

	if (!!$customerId.val()) {

		publicOPN.addOPN([ {
			name : '查看客户资料',
			alias : 'lookCusInfo',
			event : function() {

				var _s = (document.getElementById('customerType').value == 'PERSIONAL') ? 'personalCustomer' : 'companyCustomer', _url = '/customerMg/' + _s + '/info.htm?userId=' + $customerId.val(), _list = '/customerMg/' + _s + '/list.htm';

				util.openDirect('/customerMg/index.htm', _url, _list);

			}
		} ]);

	}

	publicOPN.init().doRender();
	// ------ 侧边栏 end

	// ------ 底部保存 start
	$form.on('click', '#fnDoSave', function() {

		doSubmit(true);

	});

	// ------ 保存、暂存 start
	/**
	 * 保存数据操作
	 * 
	 * @param {Boolean}
	 *            isSubmit [是否保存 true 保存 false 暂存]
	 * @return {[type]} [description]
	 */
	function doSubmit(isSubmit) {

        newSetName($('#fnRecoverySSStepBox .fnSSStep .fnSSStepView'))
        newSetName($('#fnRecoveryPlan>.fnDebtor'))


        //return

		if (!!!document.getElementById('projectCode').value) {

			Y.alert('提示', '请先选择项目', function() {

				document.getElementById('projectCode').focus();

			});
			return;

		}

		if (isSubmit) {

			// 追偿处理方案
			if ($('.fnChoosePlan:checked').length == 0) {
				Y.alert('提示', '请选择追偿处理方案');
				return;
			}

		}

		document.getElementById('checkStatus').value = isSubmit ? '1' : '0';

		// 是否验证必填数据
		if (isSubmit) {

			$form.valid(); // 仅仅是激活必填

			var _isPass = true, _isPassEq, // 哪个输入框
			_nav, // 哪个tab
			_planName, // 追偿处理方案
			_planNode, // 节点名称
			_planNodeIndex; // 第几个节点

			// 通过激活的导航分辨哪些需要找必填
			// 激活的导航没有 `fn-hide`

			var _$fnRecoverySSStepBox = $('#fnRecoverySSStepBox');

			$('.fnPlanTab li').each(function(index, el) {

				if (util.hasClass(el, 'fn-hide')) {

					// 未激活的不需要遍历
					return true;

				}

				_nav = this.getAttribute('alias'); // 哪个tab
				_planName = this.innerHTML.replace(/<.+?>/gim, '').replace(/\s/g, ''); // 追偿处理方案

				// 根据不同的方案
				if (_nav == 'litigationOn') {

					// ---- 诉讼 start

					// 是否选择了节点
					var _$fnChooseNode = _$fnRecoverySSStepBox.find('.fnChooseNode');
					if (_$fnChooseNode.length == 1 && !!!_$fnChooseNode.val()) {

						_isPass = false;
						_planNodeIndex = 0;
						_planNode = '第一个节点';
						return false;

					}

					// 选择了节点，检测是否必填
					// 为了拿到 planNodeIndex 做了那么多循环 -、-
					_$fnRecoverySSStepBox.find('.fnSSStepView').each(function(planNodeIndex, fnSSStepView) {

						$(this).find('.fnInput').each(function(fnInputIndex, fnInput) {

							if (!!!fnInput.value.replace(/\s/g, '')) {

								_planNodeIndex = planNodeIndex;
								_planNode = $('#fnRecoverySSStepTab li').eq(planNodeIndex).text();
								_isPass = false;
								_isPassEq = fnInputIndex;
								return false;

							}

						});

						if (!_isPass) {

							return false;

						}

					});

					// ---- 诉讼 end

				} else if (_nav == 'debtorReorganizationOn') {

					// ---- 债务人重整或破产清算 start

					$('.fnDebtor .fnInput').each(function(fnInputIndex, fnInput) {

						if (!!!fnInput.value.replace(/\s/g, '')) {
							_isPass = false;
							_isPassEq = fnInputIndex;
							return false;

						}

					});

					// ---- 债务人重整或破产清算 end

				} else {

					$('.fnProjectRecoveryOther .fnInput').each(function(fnInputIndex, fnInput) {

						if (!!!fnInput.value.replace(/\s/g, '')) {
							_isPass = false;
							_isPassEq = fnInputIndex;
							return false;

						}

					});

				}

				if (!_isPass) {
					return false;

				}

			});

			if (!_isPass) {

				Y.alert('提示', _planName + (_planNode ? ('的“' + _planNode + '“') : '') + '有必填项未填写', function() {

					// 回到哪个计划
					$('.fnPlanTab li[alias="' + _nav + '"]').trigger('click');
					// 如果有节点就是诉讼
					if (_planNodeIndex > -1) {

						// 回到哪个节点
						$('#fnRecoverySSStepTab li').eq(_planNodeIndex).trigger('click');
						// 聚焦到哪个输入项
						_$fnRecoverySSStepBox.find('.fnSSStepView').eq(_planNodeIndex).find('.fnInput').eq(_isPassEq).focus();

					} else {

						// 聚焦到哪个输入项
						$('.fnDebtor .fnInput').eq(_isPassEq).focus();

					}

				});
                removeSetName()
				return;

			}
		}

		// 保存数据前整理 name
		// 单个节点
		// util.resetName();

		// 特殊的节点
		// $('.fnResetNameBox [diyname]').each(function(index, el) {
		//
		// var $this = $(this), _diyname = $this.attr('diyname'), _index =
		// index;
		//
		// $this.find('[name]').each(function() {
		//
		// var _this = $(this), name = _this.attr('name');
		//
		// if (name.indexOf('.') > 0) {
		// name = name.substring(name.indexOf('.') + 1);
		// }
		//
		// name = _diyname + '[' + _index + '].' + name;
		//
		// _this.attr('name', name);
		//
		// });
		// 2017-7-12
		// });

		// 针对节点排序
		var moreTimeNode = [ 'projectRecoveryLitigationOpeningOrder', 'projectRecoveryLitigationSecondAppealOrder', 'projectRecoveryLitigationRefereeOrder', 'projectRecoveryLitigationAdjournedProcedureOrder' ]; // 会多次出现的节点

		// $.each(moreTimeNode, function(i, val) {
		//
		// // 在编辑或不成功的转台下， alias 的值会变成 xxx[0] 的形式
		// var _$e = $('div[alias^="' + val + '"]');
		//
		// if (_$e.length > 0) {
		//
		// _$e.each(function(index, el) {
		//
		// this.setAttribute('alias', val + '[' + index + ']');
		//
		// });
		//
		// }
		// 2017-7-12
		// });

		// 整个表单
		// $('div[alias]').each(function(index, el) {
		//
		// var $this = $(this), _prefix = $this.attr('alias');
		//
		// $this.find('[name]').each(function(index, el) {
		//
		// var name = this.name;
		//
		// // 可能中间删了几个呢？
		// if (this.name.indexOf(_prefix.replace(/\[\d*\]/g, '')) != -1) {
		// name = name.substring(name.indexOf('.') + 1);
		// }
		//
		// this.name = _prefix + '.' + name;
		//
		// });
		// 2017-7-12
		// });




		util.ajax({
			url : $form.attr('action'),
			data : $form.serialize(),
			success : function(res) {

                //重置一下name 方便验证
                removeSetName()

				if (res.success) {

					// 执行 或 债务人重整或破产清算 保存时提示是否做收款通知单

					// var $debtorReorganizationOn =
					// $('[name="debtorReorganizationOn"]');

					// if ($form.find('.fnChooseNode').last().val() ==
					// 'projectRecoveryLitigationExecuteOrder' ||
					// ($debtorReorganizationOn.prop('checked') &&
					// !$debtorReorganizationOn.prop('disabled'))) {

					// Y.confirm('提示', '操作成功，是否增加收款通知单', function(opn) {

					// if (opn == 'yes') {
					// util.direct('/projectMg/chargeNotification/addChargeNotification.htm',
					// '/projectMg/chargeNotification/list.htm');
					// } else {
					// window.location.href =
					// '/projectMg/recovery/projectRecoveryList.htm';
					// }

					// });

					// } else {

					// Y.alert('提示', '操作成功', function() {
					// window.location.href =
					// '/projectMg/recovery/projectRecoveryList.htm';
					// });

					// }
					Y.alert('提示', '操作成功', function() {
						window.location.href = '/projectMg/recovery/projectRecoveryList.htm';
					});

				} else {
					Y.alert('操作失败', res.message);
				}

			},
			error : function() {
				// 重置一下name 验证
                removeSetName()
			}
		});

	}

	// ------ 保存、暂存 end

	// ------ 以物抵债 输入、计算 start

	$body.on('blur', '.fnBondedBox .fnInputDebtAmount', function() {

		var $p = $(this).parents('.fnBondedBox'), $total = $p.find('.fnTotalDebtAmount'), $input = $p.find('.fnInputDebtAmount');

		setTimeout(function() {

			var _t = 0;
			$input.each(function() {

				_t += +this.value.replace(/\,/g, '');

			});

			$total.val((_t > 0) ?  util.num2k(_t.toFixed(2)) : '').trigger('blur');

		}, 10);

	}).on('blur', '.fnBondedBox .fnInputMoney', function() {

		var $p = $(this).parents('.fnBondedBox'), $total = $p.find('.fnTotalRecycle'), $input = $p.find('.fnInputMoney');

		setTimeout(function() {

			var _t = 0;
			$input.each(function() {

				_t += +this.value.replace(/\,/g, '');

			});

			$total.val((_t > 0) ? util.num2k(_t.toFixed(2)) : '');

		}, 20);

	}).on('click', '.fnBondedDel', function() {

		setTimeout(function() {
			if ($fnTableMergeTbody.find('tr').length == 0) {
				$fnTableMergeTbody.parent().find('.fnTotalDebtAmount').val('').trigger('blur');
			}
		}, 5);

	});
	setTimeout(function() {
		$body.find('.fnTableMergeTbody input').trigger('blur');
	}, 0);

	// ------ 以物抵债 输入、计算 end

	// ------ 抵质押资产抵债 start
	var $THISBONDEDLIST, $THISBONDEDLISTTPL;
	$body.on('click', '.fnBondedAdd', function() {
		document.getElementById('fnChooseBonded').click();
		var $p = $(this).parent();
		$THISBONDEDLIST = $p.find('.fnTableMergeTbody');
		$THISBONDEDLISTTPL = $($p.find('.fnTableMergeTemplate').html());
	}).append('<div id="fnChooseBonded"></div>');

	var _assetList = new getList();

	_assetList.init({
		width : '90%',
		title : '抵债资产选择',
		ajaxUrl : '/projectMg/recovery/initPledge.json?projectCode=' + $('#projectCode').val(),
		btn : '#fnChooseBonded',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td title="{{item.assetType}}">{{(item.assetType && item.assetType.length > 16)?item.assetType.substr(0,16)+\'\.\.\':item.assetType}}</td>', '        <td title="{{item.ownershipName}}">{{(item.ownershipName && item.ownershipName.length > 6)?item.ownershipName.substr(0,6)+\'\.\.\':item.ownershipName}}</td>', '        <td title="{{item.warrantNo}}">{{(item.warrantNo && item.warrantNo.length > 10)?item.warrantNo.substr(0,10)+\'\.\.\':item.warrantNo}}</td>', '        <td>{{item.evaluationPrice.standardString}}</td>', '        <td>{{item.mortgagePrice.standardString}}</td>', '        <td><input type="checkbox" value="{{item.assetId}}" sname="{{item.pledgeName}}"></td>', '    </tr>',
					'{{/each}}' ].join(''),
			form : '',
			// form: ['项目编号：',
			// '<input class="ui-text fn-w160" name="projectCode" type="text">',
			// '&nbsp;&nbsp;',
			// '客户名称：',
			// '<input class="ui-text fn-w160" name="customerName"
			// type="text">',
			// '&nbsp;&nbsp;',
			// '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit"
			// value="筛选">'
			// ].join(''),
			thead : [ '<th>抵押物类型</th>', '<th>所有权人</th>', '<th>权证号</th>', '<th width="80px">评估价格（元）</th>', '<th width="80px">抵押价格（元）</th>', '<th width="50px">操作</th>' ].join('')
		},
		multiple : true,
		renderCallBack : function(res, self) {

			$THISBONDEDLIST.find('.fnBondedId').each(function(index, el) {
				self.$box.find('tbody input[value="' + el.value + '"]').prop('checked', 'checked')
			})

		},
		callback : function(self) {

			var $c = self.$list.find('[type="checkbox"]:checked'), _arr = [];
			$c.each(function(i, e) {

				_arr.push({
					name : e.getAttribute('sname'),
					value : e.value
				});

			});

			appendBonded($THISBONDEDLIST, _arr, $THISBONDEDLISTTPL);

		}
	});

	function appendBonded($list, arr, $tr) {

		var _idArr = [];
		$list.find('.fnBondedId').each(function(i, e) {
			if (e.value != '0') {
				_idArr.push(e.value);
			}
		});

		var $div = $('<div></div>');

		$.each(arr, function(i, o) {

			if ($.inArray(o.value, _idArr) == -1) {
				// 排除已选择
				$tr.find('.fnBondedId').attr('value', o.value);
				$tr.find('.fnBondedName').attr('value', o.name);

				$div.append($tr.clone());
			}

		});

		$list.append($div.html());

	}

	// ------ 抵质押资产抵债 end

	// ------ 保全措施 选了 保全种类 时间 必填 start
	$body.on('change', '.fnSelectValu2InputMust', function() {

		var $p = $(this).parents('.fnSelectValu2InputMustBox');

		if (!!this.value) {
			$p.find('.fnSelectValu2InputMustItem').addClass('fnInput');
		} else {
			$p.find('.fnSelectValu2InputMustItem').removeClass('fnInput');
		}

	});
	// ------ 保全措施 选了 保全种类 时间 必填 end

	// ------ 管理查看档案列表 start
	// publicOPN
	var LOOKFILELISTNODE = [ 'projectRecoveryLitigationPlaceOnFileOrder', 'projectRecoveryLitigationSpecialProcedureOrder', 'projectRecoveryLitigationCertificateOrder' ], HASLOOKFILELISTLINK = hasLookFileLink(), // 节点中是否已存在查看档案
	CHOOSELOOKFILELIST = false; // 选择的中是否已选择
	LOOKFILELISTOPNOBJ = {
		name : '查看档案',
		alias : 'lookFileList',
		event : function() {
			util.openDirect('/projectMg/index.htm', document.getElementById('fnLookFileListUrl').value, '/projectMg/file/detailFileList.htm')
		}
	};
	// 是否有 查看档案
	/**
	 * 在存在的节点选择中查找节点中是否在 LOOKFILELISTNODE 数组中
	 * 
	 * @return {Boolean} [description]
	 */
	function hasLookFileLink() {

		var _has = false;
		$('#fnRecoveryPlan').find('.fnChooseNode').each(function(index, el) {

			if ($.inArray(el.value, LOOKFILELISTNODE) > -1) {
				_has = true;
				return false;
			}
		});
		return _has;

	}

	if (HASLOOKFILELISTLINK) {

		publicOPN.remove().addOPN([ LOOKFILELISTOPNOBJ ]).doRender();

	}

	// ------ 管理查看档案列表 start

	// 派生诉讼----
	$('[name=isAppend]').on('click', function() {
		if ($(this).is(":checked")) {
			$('.appendRecovery').show();
			$('.litigationOn').html("派生诉讼");
			// if (!$('[name=litigationOn]').is(":checked")) {
			// $('[name=litigationOn]').prop("checked",
			// true).click().prop("checked", true);
			// }
		} else {
			$('.appendRecovery').hide();
			$('.litigationOn').html("诉讼");
		}
	});
	// 选择方案
	$('#fnChooseRecovery').on('click', function() {

	});

	var recoveryId = $('[name=id]').val(), projectCode = $('[name=projectCode]').val();
	if (!recoveryId)
		recoveryId = 0;

	(new getList()).init({
		title : '选择方案',
		noPage : true,
		ajaxUrl : '/baseDataLoad/queryRecovery.json?projectCode=' + projectCode + '&recoveryId=' + recoveryId,
		btn : '#fnChooseRecovery',
		tpl : {
			tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td title="{{item.projectCode}}">{{item.projectCode}}</td>', '        <td title="{{item.recoveryName}}">{{(item.recoveryName && item.recoveryName.length > 18)?item.recoveryName.substr(0,18)+\'\.\.\':item.recoveryName}}</td>', '        <td title="{{item.recoveryStatus}}">{{item.recoveryStatus}}</td>', '        <td title="{{item.statusUpdateTime}}">{{item.statusUpdateTime}}</td>', '        <td><a class="choose" recoveryid="{{item.recoveryId}}" recoveryname="{{item.recoveryName}}" href="javascript:void(0);">选择</a></td>', '    </tr>', '{{/each}}' ].join(''),
			// form : [ '客户名称：', '<input class="ui-text fn-w100" type="text"
			// name="likeCustomerName">', '&nbsp;&nbsp;', '<input
			// class="ui-btn ui-btn-fill ui-btn-green" type="submit"
			// value="筛选">' ].join(''),
			thead : [ '<th>项目编号</th>', '<th>方案名称</th>', '<th>状态</th>', '<th>状态更新时间</th>', '<th width="50">操作</th>' ].join(''),
			item : 5
		},
		callback : function($a) {
			console.log($a);
			$('[name=appendRecoveryId]').val($a.attr("recoveryid"));
			$('[name=appendRecoveryName]').val($a.attr("recoveryname"));
		}
	});

	// function setName() {
    //
	// 	var view_dom = $('.fnSSStepView')
	// 	// 先把alias保存到一个对象里面 arr = {alias:1,alias2: 1}
	// 	var obj = {}
	// 	view_dom.each(function() {
    //
	// 		var _this = $(this)
    //
	// 		var _alias = _this.attr('alias')
    //
	// 		if (_alias) {
	// 			if (obj[_alias] == null || obj[_alias] == undefined) {
	// 				obj[_alias] = 0
	// 			} else {
	// 				obj[_alias]++
	// 			}
	// 			_this.attr('same_alias_index', obj[_alias])
	// 		}
	// 	})
    //
	// 	// 新增一行的bug
	// 	var obj_tbody_id = {}
	// 	var litigationIndex = {}
	// 	var input_same_view_dom = view_dom.find("input,textarea,select")
    //
	// 	view_dom.each(function() {
    //
	// 		var o_obj = {}
	// 		var _this = $(this)
    //
	// 		_this.find('input,select,textarea').each(function() {
    //
	// 			var _this = $(this), _index = _this.parents('.fnSSStepView').attr('same_alias_index'), _alias = _this.parents('.fnSSStepView').attr('alias'), _name = _this.attr('name')
    //
	// 			// 先把name取出来保存一下
	// 			if (!_this.attr('o_name')) {
	// 				_this.attr('o_name', _this.attr('name'))
	// 			}
	// 			var o_name = _this.attr('o_name')
    //
	// 			if (o_obj[o_name] == null || o_obj[o_name] == undefined) {
	// 				o_obj[o_name] = 0
	// 			} else {
	// 				o_obj[o_name]++
	// 			}
    //
	// 			if (_this.attr('name') == 'litigationIndex') {
	// 				_this.val(_this.parents('.fnSSStep').index())
	// 			}
    //
	// 			var trs = _this.parents('tr')
	// 			// 有diyname的情况
	// 			if (trs.attr('diyname')) {
	// 				var tmp_diy = trs.attr('diyname')
	// 				var tr_index = trs.index()
	// 				if (tmp_diy == 'projectRecoveryLitigationExecuteStuffOrder') {
	// 					// 变卖，拍卖 情况
	// 					var _tr = trs.parents('.fnSSStepView').find('[diyname="' + tmp_diy + '"]')
	// 					var s_index = trs.index('[diyname="' + tmp_diy + '"]')
	// 					_this.attr('name', _alias + '[' + _index + '].' + tmp_diy + '[' + s_index + '].' + o_name)
    //
	// 				} else {
	// 					_this.attr('name', _alias + '[' + _index + '].' + tmp_diy + '[' + tr_index + '].' + o_name)
	// 				}
    //
	// 			} else {
	// 				_this.attr('name', _alias + '[' + _index + '].' + o_name)
	// 			}
    //
	// 		})
	// 		_this.find('[o_name="litigationIndex"]').each(function() {
	// 			var __this = $(this)
	// 			var o_name = __this.attr('o_name')
	// 			var p_name = _this.attr('alias')
	// 			if (litigationIndex[p_name] == undefined || litigationIndex[p_name] == null) {
	// 				litigationIndex[p_name] = 0
	// 			} else {
	// 				litigationIndex[p_name]++
	// 			}
	// 			__this.attr('name', p_name + '[' + litigationIndex[p_name] + '].' + o_name)
	// 		})
	// 	});
    //
    //
    //
	// 	// asd
	// 	$('body').click(function() {
	// 		var cM_obj = {}
	// 		var _inputs = $(this).find('#creditorsMeeting input')
	// 		if (_inputs.length) {
	// 			_inputs.each(function() {
	// 				var $this = $(this)
	// 				var str = 'projectRecoveryDebtorReorganizationOrder.'
	// 				if ($this.attr('name')) {
	// 					if (!$this.attr('o_name')) {
	// 						$this.attr('o_name', $this.attr('name').replace(str, ''))
	// 					}
    //
	// 					var o_name = $this.attr('o_name')
	// 					if (cM_obj[o_name] == undefined || cM_obj[o_name] == null) {
	// 						cM_obj[o_name] = 0
	// 					} else {
	// 						cM_obj[o_name]++
	// 					}
	// 					$this.attr('name', str + $this.parent('.m-item').attr('diyname') + '[' + cM_obj[o_name] + '].' + $this.attr('o_name'))
	// 				}
	// 			})
	// 		}
	// 	})
    //
	// 	$("body").on('click', '#fnRecoveryPlan .fnDebtor', function() {
    //
	// 		var $inputs = $(this).find('input,select,textarea')
    //
	// 		$inputs.each(function () {
	// 			var $this = $(this)
	// 			if ($this.parents('tr').attr('diyname')) {
     //                var $mg = $(this).parents('.fnTableMergeMg')
     //                $mg.each(function() {
     //                    var mg_obj = {}
     //                    var _this = $(this)
     //                    var _inputs = _this.find('input,select,textarea')
     //                    _inputs.each(function() {
     //                        var $$this = $(this)
     //                        if ($$this.attr('name')) {
     //                            // name save 一下
     //                            if (!$$this.attr('o_name')) {
     //                                $$this.attr('o_name', $$this.attr('name'))
     //                            }
    //
     //                            var o_name = $$this.attr('o_name')
     //                            if (mg_obj[o_name] == undefined || mg_obj[o_name] == null) {
     //                                mg_obj[o_name] = 0
     //                            } else {
     //                                mg_obj[o_name]++
     //                            }
     //                            $$this.attr('name', 'projectRecoveryDebtorReorganizationOrder.' + $$this.parents('tr').attr('diyname') + '[' + mg_obj[o_name] + '].' + $$this.attr('o_name'))
     //                        }
     //                    })
     //                })
	// 			} else {
     //                if ($this.attr('name')) {
     //                    if (!$this.attr('o_name')) {
     //                        $this.attr('o_name', $this.attr('name'))
     //                    }
     //                    $this.attr('name', 'projectRecoveryDebtorReorganizationOrder.' + $this.attr('o_name'))
     //                }
	// 			}
     //        })
    //
	// 	})
    //
    //
	// 	//fnInput
    //
    //
	// 	$("body").trigger('click')
    //
    //
    //
     //    if($('#fnRecoveryPlan  .fnDebtor').length)
     //        $('#fnRecoveryPlan  .fnDebtor').trigger('click')
    //
	// }

	$('.fn-green.viewDaliog').each(function () {
		//var _this = $(this).
    })


    // 再处理一下新增一行

    $('body').click(function () {
        $('.fnAddLine').each(function() {

            // if(obj_tbody_id)
            var _this = $(this)

            if (!_this.attr('o_cttid')) {
                _this.attr('o_cttid', _this.attr('cttid'))
            }

            var _same = _this.attr('o_cttid')
            var _same_obj = $('[o_cttid="' + _same + '"]')

            _same_obj.each(function(index, el) {

                var $this = $(this)
                var _index = $this.parents('.fnSSStep').index()
                if (_index >= 0)
                    $this.attr('cttid', $this.attr('o_cttid') + _index)
            })

            var tbody = _this.parents('.fnSSStepView').find('tbody')
            var o_cttid = _this.attr('o_cttid')

            tbody.each(function() {

                var _$this = $(this)

                if (_$this.attr('id') == o_cttid) {
                    if (!_$this.attr('o_id')) {
                        _$this.attr('o_id', _$this.attr('id'))
                    }
                    _$this.attr('id', _$this.attr('o_id') + _$this.parents('.fnSSStep').index())
                }
            })

        })
    })

	$('select.fnSelectValu2InputMust').trigger('change')
	
	function newSetName(allPlans) {

		var objAlias = {}
        allPlans.each(function () {

            var objDiyName = {}
            var _this = $(this)
            var alias =_this.attr('alias')
            var tr = _this.find('[diyname]')
			var allInput = _this.find('[name]')

            writeObj(objAlias,alias)

			tr.each(function () {
            	var self = $(this)
				var diyname = self.attr('diyname')
                var allInput_tr = self.find('[name]')
				writeObj(objDiyName, diyname)
                allInput_tr.each(function () {
					var my = $(this)
                    changeName(my, function () {
                        //债务人重整或破产清算 不会出现多次
                        if(alias == 'projectRecoveryDebtorReorganizationOrder'){
                            my.attr('name',alias+'.'+diyname+'['+objDiyName[diyname]+'].'+my.attr('o_name'))
						} else {
                            my.attr('name',alias+'['+objAlias[alias]+'].'+diyname+'['+objDiyName[diyname]+'].'+my.attr('o_name'))
						}
                    })
                })
            })

            allInput.each(function () {
                var me = $(this)
                changeName(me ,function () {

                	//债务人重整或破产清算 不会出现多次
                	if(alias == 'projectRecoveryDebtorReorganizationOrder'){
                        me.attr('name',alias+'.'+ me.attr('o_name'))
					} else {
                        me.attr('name',alias+'['+objAlias[alias]+'].'+ me.attr('o_name'))
                    }

                    // litigationIndex 的值
                    if(me.attr('o_name') == 'litigationIndex') {
                        me.val(me.parents('.fnSSStep').index())
                    }

                })

            })

        })

        //validata 验证不出来啊 = =
        $('#fnRecoverySSStepBox .fnInput').each(function () {
            var _this = $(this)
            if(_this.val()=='' && !_this.hasClass('error1')){
                _this.addClass('error1')
                if(_this.hasClass('fnErrorAfter')){
                    if(!_this.parent().find('.error-tip1').length)
                        _this.parent().append('<b class="error-tip1" style="font-weight: 400;color: #f30">必填</b>')
                } else {
                    _this.after('<b class="error-tip1" style="font-weight: 400;color: #f30">必填</b>')
                }
            }
        })
        $('body').on('blur','.fnInput',function () {
            var _this = $(this)
            _this.blur(function () {
                if(_this.val()!='') {
                    _this.removeClass('error1')
                    if(_this.hasClass('fnErrorAfter')){
                        _this.parent().find('.error-tip1').remove()
                    } else {
                        _this.next('.error-tip1').remove()
                    }
                }
            })
        })
    }

    /**
	 * 对象写入值
     * @param obj
     * @param key
     */
    function writeObj(obj, key) {
        if (obj[key] == undefined || obj[key] == null) {
            obj[key] = 0
        } else {
            obj[key] ++
        }
    }

    /**
	 * 保存name值
     * @param $obj
     */
    function changeName($obj, cb) {
        if($obj.attr('o_name') == undefined && $obj.attr('name')) {
            $obj.attr('o_name', $obj.attr('name'))
			cb()
		}
    }

    /**
	 * 还原 input
	 * 移除o_name
     */
    function removeSetName() {
        $("[name]").each(function() {
            if ($(this).attr('o_name')) {
                $(this).attr('name', $(this).attr('o_name'))
                $(this).removeAttr('o_name')
            }
        })
    }
	
});