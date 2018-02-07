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

	// ------ 客户移交 start
	var BPMiframe = require('BPMiframe'); // isSingle=true 表示单选 尽量在url后面加上参数
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
		'title' : '人员',
		'width' : 850,
		'height' : 460,
		'scope' : '{type:\"system\",value:\"all\"}',
		'selectUsers' : {
			selectUserIds : '', // 已选id,多用户用,隔开
			selectUserAccounts : '', // 已选account,多用户用,隔开
			selectUserNames : '' // 已选Name,多用户用,隔开
		},
		'bpmIsloginUrl' : '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl' : '/JumpTrust/makeLoginUrl.htm'
	});

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
			url : '/projectMg/transfer/selectProject.json',
			dataType : 'html',
			data : $form.serialize(),
			success : function(res) {
				$('#selectProject').html(res);
				//已选中
                checked_input();
			}
		});
	}
    function checked_input(){
		var new_dom = $('.checkbox.fnCheckItem');
		new_dom.each(function () {
			var $this = $(this);
			var new_id = $this.parents('tr').find('td').eq(1).text();
			for(var i in check_arr){
				if(new_id == check_arr[i].id){
                    $this.parents('tr').addClass('added');
                    $this.attr('checked',true);
				}
			}
        })

	}

	var selRiskSummary // 风险处置会会议纪要 弹出层

	$('body').on('click', '.fnBtnSearch', function() {
		searchProject(1);
	}).on('click', '.fnChooseGist', function() {

		var _this = $(this), projectCode = _this.closest("tr").find("[name=projectCode]").val();

		if (!!!selRiskSummary) {
			selRiskSummary = new getList();
		}
		selRiskSummary.init({
			width : '90%',
			title : '选择风险处置会议纪要',
			ajaxUrl : '/baseDataLoad/projectRiskHandle.json?projectCode=' + projectCode,
			btn : '#fnChooseGists',
			tpl : {
				tbody : [ '{{each pageList as item i}}', '    <tr class="fn-tac m-table-overflow">', '        <td>{{item.summaryCode}}</td>', '        <td>{{item.councilBeginTime}}</td>', '        <td><input type="checkbox" name="x" class="checkbox" formid="{{item.summaryFormId}}" handleid="{{item.handleId}}" sc="{{item.summaryCode}}"></td>', '    </tr>', '{{/each}}' ].join(''),
				thead : [ '<th>会议纪要编号</th>', '<th width="180px">会议开始时间</th>', '<th width="50px">操作</th>' ].join(''),
				item : 3
			},
			multiple : true,
			callback : function(self) {

				// 已选数据
				var $FKJY = _this.parent().find('.fnFKJY'), $FKJYVal = _this.parent().find('.fnFKJYValue'), selectedArr = $FKJYVal.val().split(';')

				// 当前选中
				var selectArr = []

				self.$box.find('.checkbox:checked').each(function(index, el) {
					selectArr.push([ el.getAttribute('formid'), el.getAttribute('handleid'), el.getAttribute('sc') ].join(','))
				});

				selectedArr = filterArr(selectedArr.concat(selectArr))

				$FKJYVal.val(selectedArr.join(';'))

				var _html = ''

				$.each(selectedArr, function(index, str) {
					var strArr = str.split(',')
					_html += '<li><a href="javascript:void(0);" class="fn-right fn-f30 fnDelBasis" w="FKJY" sfi="' + strArr[0] + '" hi="' + strArr[1] + '" t="' + strArr[2] + '">&times;</a><a class="fn-f30" href="/projectMg/meetingMg/summary/view.htm?formId=' + strArr[0] + '&spId=' + strArr[1] + '">' + strArr[2] + '</a></li>'
				});

				$FKJY.html(_html)
			}
		}).show().getData(1);
	}).on('click', '.fnBasis ul a:not(.fnDelBasis)', function(e) {

		e.preventDefault();
		window.open(this.href);
		// util.openDirect('/projectMg/index.htm', this.href);

	}).on('click', '.fnDelBasis', function() {
		var $this = $(this), $ul = $this.parents('ul'), _switch = $this.attr('w');
		$this.parent().remove();
		var _select = []
		$ul.find('a.fnDelBasis').each(function(index, el) {
			_select.push([ el.getAttribute('sfi'), el.getAttribute('hi'), el.getAttribute('t') ].join(','))
		})
		$ul.parent().find('.fnFKJYValue').val(_select.join(';'))
	}).on('click', '.selectUserBtn', function() {

		var _parent = $(this).parent();
		var $acceptUserId = _parent.find(".acceptUserId"), $acceptUserName = _parent.find(".acceptUserName"), $acceptUserAccount = _parent.find(".acceptUserAccount");

		// 添加选择后的回调，以及显示弹出层
		// 这里也可以更新已选择用户
		singleSelectUserDialog.obj.selectUsers = {
			selectUserIds : $acceptUserId.val(),
			selectUserNames : $acceptUserName.val(),
			selectUserAccounts : $acceptUserAccount.val()
		}

        singleSelectUserDialog.init(function(relObj) {

			$acceptUserId.val(relObj.userIds);
			$acceptUserName.val(relObj.fullnames);
			$acceptUserAccount.val(relObj.accounts);
			// 保存
			var tr = _parent.closest("tr");
			var id = tr.find("[name=id]").val();
			var acceptUserId = tr.find("[name=acceptUserId]").val(), acceptUserName = tr.find("[name=acceptUserName]").val(), acceptUserAccount = tr.find("[name=acceptUserAccount]").val();
			var acceptUserbId = tr.find("[name=acceptUserbId]").val(), acceptUserbName = tr.find("[name=acceptUserbName]").val(), acceptUserbAccount = tr.find("[name=acceptUserbAccount]").val();
			var data = {
				'id' : id,
				'acceptUserId' : acceptUserId,
				'acceptUserName' : acceptUserName,
				'acceptUserAccount' : acceptUserAccount,
				'acceptUserbId' : acceptUserbId,
				'acceptUserbName' : acceptUserbName,
				'acceptUserbAccount' : acceptUserbAccount
			};
			// console.log(data);
			if (id && acceptUserId && acceptUserName && acceptUserAccount && acceptUserbId && acceptUserbName && acceptUserbAccount) {
				util.ajax({
					url : '/projectMg/transfer/setAcceptUser.json',
					data : data,
					success : function(res) {
						if (res.success) {
							tr.attr("success", true);
						} else {
							tr.attr("success", false);
							Y.alert('提示', res.message);
						}
					}
				});
			}
		});
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

	var $addForm = $('#addForm');

	//提交已选择项目
	$("#submit").on("click", function() {
		// 2017 -6 - 16 不做验证了
		//作为验证标识
		// var flag = true;
        // var trs = $addForm.find('tr');
        // trs.each(function () {
			// var $this = $(this);
			// var val_fnFKJYValue = $this.find('.fnFKJYValue').val();
        //     var val_fnUpAttachVal = $this.find('.fnUpAttachVal').val();
			// if( (val_fnFKJYValue||val_fnUpAttachVal) == false){
			// 	flag = false;
			// }
        // })
        // if(flag==false){
         //    Y.alert('提示','风险处置会会议纪要和移交决策必填一项');
        // }else{
        //
		// }

        util.resetName("detailOrder");
        util.ajax({
            url : '/projectMg/transfer/save.json',
            data : $addForm.serialize(),
            success : function(res) {
                if (res.success) {
                    submitForm(res.form.formId);
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });

	})

	function submitForm(formId) {

		var projectCodeArr = [];
		$(".fnSelProjectCode").each(function() {
			projectCodeArr.push($(this).val());
		})
		util.postAudit({
			formId : formId,
			relatedProjectCode : projectCodeArr.join(',')
		}, function(res) {
			if (res.success)
				window.location.href = '/projectMg/transfer/list.htm';
		})
	}

	// ------ 审核 start
	var auditProject = require('zyw/auditProject');

	if (document.getElementById('auditForm')) {
		var _auditProject = new auditProject();
		_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

		_auditProject.addAuditBefore(function(dtd) {
			var _isPass = eval(_auditProject.audit.$isPass.val());
			if (_isPass) {
				// 查看没有设置AB角成功的项目
				var failArr = []
				$("[diyname][success=false]").each(function() {
					failArr.push($(this).find("[name=projectCode]").val());
				});
				if (failArr.length > 0) {
					return dtd.reject({
						message : "项目[" + failArr.join('，') + "]AB角设置有误"
					});
				} else {
					return dtd.resolve();
				}
			} else {
				// 非通过状态不验证是否选择了AB角
				return dtd.resolve();
			}
		});
	}
	// ------ 审核 end

	// ------ 侧边栏 start
	if (document.getElementsByName('form')) {
		var publicOPN = new (require('zyw/publicOPN'))();
		publicOPN.init().doRender();
	}
	// ------ 侧边栏 end

    // ------ 选中显示
    var check_arr = [];

	//选中封装
	function checked_push($this) {
        var $td = $this.parents('tr').find('td');
        var $id = $td.eq('1').text();
        if($this.parents('tr').hasClass('added')){
            $this.parents('tr').removeClass('added');
            for(var i in check_arr){
                if(check_arr[i].id == $id){
                    check_arr.splice(i,1)
                }
            }
        }else{
            var tmp = {
                'id':$td.eq('1').text(),
                'name':$td.eq('2').text(),
                'type':$td.eq('3').text(),
                'manage':$td.eq('4').text(),
            };
            check_arr.push(tmp);
            $this.parents('tr').addClass('added');
        }
        arr_fill();
    }
    function arr_fill() {
        $('#addForm table .new_add').remove();
        for(var i in check_arr){
            var check_val = check_arr[i];
            var tmp = $('#addTableRow').html();
            var $tmp = $(tmp);
            var tds = $tmp.find('td');
            //赋值
            tds.eq(0).find('span').text(check_val.id);
            tds.eq(0).find('.fnSelProjectCode').val(check_val.id)
            tds.eq(1).text(check_val.name );
            tds.eq(2).text(check_val.type);
            tds.eq(3).text(check_val.manage);
            $tmp.appendTo($('#addForm table tbody'));
        }
    }
	//单个选中
    $('body').on('click','.checkbox.fnCheckItem',function(){
        checked_push($(this));
    });

	//全选
    $('body').on('click','.checkbox.fnAllCheck',function(){
    	var $this = $(this).parents('table').find('.fnCheckItem');
    	var flag = false;
    	//取消全选
    	if($(this).parents('th').hasClass('added')){
            $(this).parents('th').removeClass('added');
            $this.each(function () {
                var $td = $(this).parents('tr').find('td');
                var $id = $td.eq('1').text();
                for(var i in check_arr){
                    if(check_arr[i].id == $id){
                        check_arr.splice(i,1);
                    }
                }
                arr_fill();
                $(this).attr('checked',false).parents('tr').removeClass('added');
            });
        }
        //全选
        else{
            $(this).parents('th').addClass('added');
            $this.each(function () {
                var $td = $(this).parents('tr').find('td');
                var $id = $td.eq('1').text();
            	if($td.parent('tr').hasClass('added')==false){
                    var tmp = {
                        'id':$td.eq('1').text(),
                        'name':$td.eq('2').text(),
                        'type':$td.eq('3').text(),
                        'manage':$td.eq('4').text(),
                    };
                    check_arr.push(tmp);
                    $(this).parents('tr').addClass('added');
                    $(this).attr('checked',true);
                    arr_fill();
				}
            });
        }
	});


    //删除
    var ajaxObj = {
        del: {
            opn: '删除'
        }
    };
    //删除已选择项目
    $('#addForm').on('click', '.del', function() {
        var _this = $(this);
		var project_id = _this.parents('tr').find('.fnSelProjectCode').val();
		for(var i in check_arr){
			if(check_arr[i].id==project_id){
				check_arr.splice(i,1);
				$('.added').each(function () {
					var _this = $(this);
					if (_this.find('td').eq(1).text() == project_id) {
						_this.find('.fnCheckItem').attr('checked',false);
						_this.removeClass('added');
					}
				});
			}
		}
		_this.parents('tr').remove();
    });
    // ------ 选中显示end
});