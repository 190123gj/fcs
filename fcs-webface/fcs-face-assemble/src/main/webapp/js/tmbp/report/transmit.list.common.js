define(function(require, exports, module) {

	// 数据分析 > 数据报送 > 列表、提交
	require('input.limit');
	require('zyw/publicPage');
	require('Y-msg');
	require('tmbp/chooseDateNew');
    require('validate');

	var util = require('util');

	var $form = $('#form');
    //------ 公共 验证规则 start
    $form.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        focusInvalid:true,
        errorPlacement: function(error, element) {
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else if(element.hasClass('parentsMitem')){
                element.parent('.m-item ').append(error);
            }else{
                element.parent().append(error);
            }
        },
        submitHandler: function(form) {},
        rules: {
            reportTime: {
                required: true
            }
        },
        messages: {
            reportTime: {
                required: '请先选择报送时间！'
            }
        }
    });

    function dynamAddRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[type="text"].text');
        // dynamicRemoveRules($form);
        $nameList.each(function(i, e) {
            var _this = $(this);
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });

        })
    };

    function dynamicRemoveRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[type="text"].text');
        $nameList.each(function(i, e) {
            $(this).rules("remove");
        })
    };
    dynamAddRules($form);
    // $('.fn-moneyNumberThree,.fnMakeMicrometer').each(function (index, ele) {
    //     var _self = $(this);
    //     var isInput = _self.attr('type') == 'text';
    //     var _val = isInput ? _self.val() : _self.html();
    //     _self.attr('nmaxlength','19')
    //     if (isNaN(_val) || !!!_val) return; //如果不是数字或者为空，0就不千分位
    //     _val = _val.split('').reverse().join('').replace(/(\d{3}(?=\d)(?!\d+\.|$))/g, '$1,').split('').reverse().join(''); //逗号分割
    //     if (isInput) {
    //         _self.val(_val);
    //     } else {
    //         _self.html(_val);
    //     };
    //
    // })
	$form.on('click', '.fnSubmit', function() {

		// var year = $('[name="reportYear"]').val();
		// var month = $('[name="reportMonth"]').val();
        //
		// if (($('[name="reportYear"]').length > 0 && !!!year) || ($('[name="reportMonth"]') > 0 && !!!month)) {
		// 	Y.alert('提示', '请先选择时间！', function() {
        //
		// 		if (!!!year) {
		// 			$('[name="reportYear"]').focus();
		// 			return;
		// 		};
		// 		if (!!!month) {
		// 			$('[name="reportMonth"]').focus();
		// 			return;
		// 		}
        //
		// 	});
		// 	return;
		// };

		if (!$(this).hasClass('fnPost')) {

			// 暂存
            dynamicRemoveRules($form);
            if (!$form.valid()) return;
			doSubmit(false);

		} else {
            dynamAddRules($form);
            if (!$form.valid()) return;
			// 保存
			var $fnInput = $('[type="text"].text'),
				_isPass = true,
				_isPassEq;

			$fnInput.each(function(index, el) {

				if (!!!el.value.replace(/\s/g, '')) {

					_isPass = false;
					_isPassEq = index;
					return false;

				}

			});

			if (!_isPass) {

				Y.alert('提示', '请填写完表单', function() {

					$fnInput.eq(_isPassEq).focus();

				});
				return;

			}

			doSubmit(true);

		}
	});

	$('body').on('click', '.delItem', function() {
		//<a class="delItem" href="javascript:void(0)" delUrl="/reportMg/submission/delete.htm" keyValue="$!{item.submissionId}" keyName="submissionId">[ 删除 ]</a>
		//
		var _this = $(this);

		Y.confirm('请选择','确定删除该条数据？',function(opn){

			if(opn=="yes"){

				var url = _this.attr('delUrl');
				var keyName = _this.attr('keyName');
				var keyValue = _this.attr('keyValue');
				var data = {};

				data[keyName] = keyValue;

				$.ajax({
					url: url,
					type: 'POST',
					dataType: 'JSON',
					data: data,
					success:function (res) {

						if (res.success) {
		 					Y.alert('提示', res.message, function() {

								window.location.href = '/reportMg/submission/list.htm';

							});
						}else {
							Y.alert('提示', res.message);
						}

					}
				})
			}
		});
	});

	function doSubmit(boole) {//暂存或者提交

		document.getElementById('checkStatus').value = boole ? '1' : '0';

		// util.resetName();

		// 保存数据
		util.ajax({
			url: $form.attr('action'),
			data: $form.serializeJCK(),
			success: function(res) {

				if (res.success) {

					if (boole) {
						Y.alert('提示', '操作成功', function() {
							window.location.href = '/reportMg/submission/list.htm';
						});
					}else {
						Y.alert('提示', '操作成功');
					}


					// 暂时不管发送
					// if (util.hasClass(self, 'fnPost')) {

					// 	// 发送数据
					// 	Y.confirm('提示', '确定报送？', function(opn) {

					// 		if (opn == 'yes') {

					// 			return;
					// 			util.ajax({
					// 				url: '/',
					// 				data: {},
					// 				success: function(res2) {

					// 					Y.alert('提示', res2.message, function() {

					// 						window.location.href = '/';

					// 					});

					// 				}
					// 			});


					// 		} else {

					// 			window.location.href = '/';

					// 		}

					// 	});



					// } else {

					// 	Y.alert('提示', '操作成功', function() {
					// 		window.location.href = '/';
					// 	});

					// }

				} else {

					Y.alert('操作失败', res.message);

				}

			}
		});

	}



});