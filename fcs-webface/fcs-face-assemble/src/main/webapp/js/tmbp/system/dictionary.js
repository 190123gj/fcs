define(function(require, exports, module) {
	require('zyw/opsLine');

	// 通用方法
	var util = require('util');

	$('#fnDataCode').on('change', function() {
		// console.log($(this).val())
		window.location.href = '/systemMg/dictionary/form.htm?dataCode=' + $(this).val()
	})
	$('#fnApplyPost').on('click', function() {

		// 重新设置name值
		$("[name$=dataValue]").each(function(i) {
			console.log(i + "" + $(this).val());
			$(this).parent().parent().find("[name]").each(function() {
				var _this = $(this), name = _this.attr('name');
				if (name.indexOf('.') > 0) {
					name = name.substring(name.indexOf('.') + 1);
				}
				name = 'dataOrder[' + i + '].' + name;
				_this.attr('name', name);
			});
		});

		var $_form = $('#form');

		util.ajax({
			url : $_form.attr('action'),
			data : $_form.serialize(),
			success : function(res) {
				if (res.success) {
					window.location.reload();
				} else {
					Y.alert('操作失败', res.message);
				}
			}
		})

	})// └┗
	$('#tbodyQXGS').on('click', '.addsub', function() {
		var $tr = $(this).closest('tr')
		var $ctr = $($('#t-tbodyQXGS').html())
		$ctr.find('td').each(function(i, el) {
			if (i == 0) {
				$(el).html(1).attr('sort', 1)
			} else if (i == 1) {
				$(el).find("input[name='parentId']").val($tr.find("input[name='id']").val())
			} else {
				$(el).find('.addsub').remove()
			}
		})

		addSubLine($tr.find('td:eq(1)').find("input[name='id']").val(), $ctr, $tr, $tr.find('td:eq(0)').html())

	})
	function addSubLine(pid, ctr, tr, sort) {

		var tb = $('#tbodyQXGS').find('tbody[pid="' + pid + '"]');
		if (!tb.length) {
			var str = '<tr><td width="50" sort="' + sort + '"></td><td colspan="2"><table width="100%"><tbody pid="' + pid + '"><tr>' + ctr.html() + '</tr></tbody></table></td></tr>'
			tr.after(str)
		} else {
			var str = '<tr>' + ctr.html() + '</tr>'
			tb.append(str)
			var s = parseInt(tb.find('tr:last').prev().find('td:eq(0)').attr('sort'))
			tb.find('tr:last').find('td:eq(0)').html(s + 1).attr('sort', s + 1)
		}
	}

	$('.fnAddLine').on('click', function() {
		setTimeout(function() {
			var s = parseInt($('#tbodyQXGS tr:last').prev().find('td:eq(0)').attr('sort'))
			$('#tbodyQXGS tr:last').find('td:eq(0)').html(s + 1).attr('sort', s + 1)
		}, 50)

	})
	$('#tbodyQXGS').on('click', '.fnDelLine', function() {
		var $this = $(this)
		if ($this.closest('table').find('tr').length == 1) {
			$this.closest('table').closest('tr').remove()
		} else {
			var $tb = $this.closest('table')
			$this.closest('tr').remove()
			$tb.find('tbody > tr').each(function(i, el) {
				$(el).find('td:eq(0)').html(i + 1).attr('sort', i + 1)
			})
		}
	})

	function loadChildren(_parents) {
		if (_parents && _parents.size() > 0) {
			_parents.each(function(i, el) {
				if (parseInt($(el).attr('childrennum')) > 0) {
					var $tr = $(this).closest('tr');
					var sort = $tr.find('td:eq(0)').html();
					var pid = $tr.find("input[name='id']").val()
					util.ajax({
						url : '/systemMg/dictionary/form.htm?parentId=' + $tr.find("input[name='id']").val(),
						dataType : 'html',
						success : function(res) {
							var $content = $(res).find('#tbodyQXGS');
							var str = '<tr><td width="50" sort="' + sort + '"></td><td colspan="2"><table width="100%"><tbody pid="' + pid + '">' + $content.html() + '</tbody></table></td></tr>'
							$tr.after(str)
							if (!loadChildren($("tbody[pid=" + pid + "]").find('a[childrennum]')))
								return false;
						}
					})
				}
			})
		} else {
			return false;
		}
	}

	$(function() {
		loadChildren($('#tbodyQXGS').find('a[childrennum]'));
		// $('#tbodyQXGS').find('a[childrennum]').each(function(i, el) {
		// if (parseInt($(el).attr('childrennum')) > 0) {
		// var $tr = $(this).parents('tr');
		// var sort = $tr.find('td:eq(0)').html();
		// var pid = $tr.find('td:eq(1)').find("input[name='id']").val()
		// util.ajax({
		// url : '/systemMg/dictionary/form.htm?parentId=' +
		// $tr.find("input[name='id']").val(),
		// dataType : 'html',
		// success : function(res) {
		// console.log()
		// var str = '<tr><td sort="' + sort + '"></td><td colspan="2"><table
		// width="100%"><tbody pid="' + pid + '"><tr>' +
		// $(res).find('#tbodyQXGS').html() + '</tr></tbody></table></td></tr>'
		// $tr.after(str)
		// }
		// })
		// }
		// })
	})

});