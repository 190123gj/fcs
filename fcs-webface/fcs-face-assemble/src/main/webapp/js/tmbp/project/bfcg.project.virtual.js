define(function(require, exports, module) {
	// 项目管理 > 项目列表
	require('Y-msg');

	require('validate');

	require('zyw/upAttachModify');

	// 弹窗
	require('Y-window');
	// 弹窗提示
	var hintPopup = require('zyw/hintPopup');

	var getList = require('zyw/getList');

	var util = require('util');
	
	// 选择项目分页
	window.toPage = function(totalPage, pageNo) {
		if (totalPage < pageNo) {
			return false;
		}
		searchProject(pageNo);
	}

	function searchProject(pageNo) {
		var $form = $("#fnListSearchForm");
		$form.find("#fnPageNumber").val(pageNo);
		util.ajax({
			url : '/projectMg/virtual/selectProject.json',
			dataType : 'html',
			data : $form.serialize(),
			success : function(res) {
				$('#selectProject').html(res);
				// 已选中
				checked_input();
			}
		});
	}
	
	function checked_input() {
		var new_dom = $('.checkbox.fnCheckItem');
		new_dom.each(function() {
			var $this = $(this);
			var new_id = $this.parents('tr').find('td').eq(1).text();
			for ( var i in check_arr) {
				if (new_id == check_arr[i].id) {
					$this.parents('tr').addClass('added');
					$this.attr('checked', true);
				}
			}
		})
	}

	var $addForm = $('#addForm');

    var Rules = {
        rules: {
            projectName:{
            	required: true
			},
            scheme: {
                required: true
			},
            customerId:{
                required: true
			}
        },
        messages: {
            projectName:{
                required: '必填'
            },
            scheme: {
                required: '必填'
            },
            customerId:{
                required: '必选'
            }
        }
    }

    $addForm.validate($.extend(true, util.validateDefault,{
        rules: Rules.rules,
        messages: Rules.messages,
        submitHandler: function () {
            util.resetName()
            util.ajax({
                url: '/projectMg/virtual/save.json',
                data: $addForm.serialize(),
                success: function (res) {
                    if (res.success) {
                        window.location.href = '/projectMg/virtual/list.htm'
                    } else {
                        Y.alert('提示', res.message);
                    }
                },
                error: function () {
                    Y.alert('提示', '网络错误')
                }
            })
        }
    }))

	$('body').on('click', '.fnBtnSearch', function() {
		searchProject(1);
	}).on('click', '#submit,#save', function() {
		var $this = $(this), $isSubmit = ($this.attr('id') == 'submit');
		if ($isSubmit) {
			$("[name=isSubmit]").val("IS");
		} else {
			$("[name=isSubmit]").val("NO");
		}
		util.resetName("detailOrder");

        if(!$(".selectedProject").find('tr').length){
            Y.alert('提示', '请选择项目');
            return;
        }

        $addForm.submit()
		// util.ajax({
		// 	url : '/projectMg/virtual/save.json',
		// 	data : $addForm.serialize(),
		// 	success : function(res) {
		// 		if (res.success) {
		// 			window.location.href = '/projectMg/virtual/list.htm'
		// 		} else {
		// 			Y.alert('提示', res.message);
		// 		}
		// 	}
		// });





	});

	// 过滤数组
	function filterArr(arr) {
		var _o = {}, _arr = []
		$.each(arr, function(index, str) {
			if (!!str && !!!_o[str]) {
				_o[str] = str
				_arr.push(str)
			}
		});
		return _arr
	}

	// ------ 选中显示
	var check_arr = [];

	// 选中封装
	function checked_push($this) {
		var $td = $this.parents('tr').find('td');
		var $id = $td.eq('1').text();
		if ($this.parents('tr').hasClass('added')) {
			$this.parents('tr').removeClass('added');
			for ( var i in check_arr) {
				if (check_arr[i].id == $id) {
					check_arr.splice(i, 1)
				}
			}
		} else {
			var tmp = {
				'id' : $td.eq('1').text(),
				'name' : $td.eq('2').text(),
				'type' : $td.eq('3').text(),
				'manage' : $td.eq('4').text(),
				'amount' : $td.eq('5').text(),
				'balance' : $td.eq('6').text()
			};
			check_arr.push(tmp);
			$this.parents('tr').addClass('added');
		}
		arr_fill();
	}
	function arr_fill() {
		$('#addForm table .new_add').remove();
		for ( var i in check_arr) {
			var check_val = check_arr[i];
			var tmp = $('#addTableRow').html();
			var $tmp = $(tmp);
			var tds = $tmp.find('td');
			// 赋值
			tds.eq(0).find('span').text(check_val.id);
			tds.eq(0).find('.fnSelProjectCode').val(check_val.id)
			tds.eq(1).text(check_val.name);
			tds.eq(2).text(check_val.type);
			tds.eq(3).text(check_val.manage);
			tds.eq(4).text(check_val.amount);
			tds.eq(5).text(check_val.balance);
			$tmp.appendTo($('#addForm table tbody.selectedProject'));
		}
	}
	// 单个选中
	$('body').on('click', '.checkbox.fnCheckItem', function() {
		checked_push($(this));
	});

	// 全选
	$('body').on('click', '.checkbox.fnAllCheck', function() {
		var $this = $(this).parents('table').find('.fnCheckItem');
		var flag = false;
		// 取消全选
		if ($(this).parents('th').hasClass('added')) {
			$(this).parents('th').removeClass('added');
			$this.each(function() {
				var $td = $(this).parents('tr').find('td');
				var $id = $td.eq('1').text();
				for ( var i in check_arr) {
					if (check_arr[i].id == $id) {
						check_arr.splice(i, 1);
					}
				}
				arr_fill();
				$(this).attr('checked', false).parents('tr').removeClass('added');
			});
		}
		// 全选
		else {
			$(this).parents('th').addClass('added');
			$this.each(function() {
				var $td = $(this).parents('tr').find('td');
				var $id = $td.eq('1').text();
				if ($td.parent('tr').hasClass('added') == false) {
					var tmp = {
						'id' : $td.eq('1').text(),
						'name' : $td.eq('2').text(),
						'type' : $td.eq('3').text(),
						'manage' : $td.eq('4').text(),
						'amount' : $td.eq('5').text(),
						'balance' : $td.eq('6').text()
					};
					check_arr.push(tmp);
					$(this).parents('tr').addClass('added');
					$(this).attr('checked', true);
					arr_fill();
				}
			});
		}
	});

	// 删除
	var ajaxObj = {
		del : {
			opn : '删除'
		}
	};
	// 删除已选择项目
	$('#addForm').on('click', '.del', function() {
		var _this = $(this);
		var project_id = _this.parents('tr').find('.fnSelProjectCode').val();
		for ( var i in check_arr) {
			if (check_arr[i].id == project_id) {
				check_arr.splice(i, 1);
				$('.added').each(function() {
					var _this = $(this);
					if (_this.find('td').eq(1).text() == project_id) {
						_this.find('.fnCheckItem').attr('checked', false);
						_this.removeClass('added');
					}
				});
			}
		}
		_this.parents('tr').remove();
	});
	// ------ 选中显示end
	
	
    $('#fnPrint').click(function (event) {
        var $fnPrintBox = $('#div_print')
        $fnPrintBox.find('.ui-btn-submit').remove()
        $fnPrintBox.find('.printshow').removeClass('fn-hide')
        util.print($fnPrintBox.html())
    })	
});