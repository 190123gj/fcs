define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('input.limit');
	var util = require('util');

    var pako = require('pako')

	var getList = require('zyw/getList');

	//// ------- 企业
	//(new getList()).init({
	//	title: '选择客户',
	//	ajaxUrl: '/baseDataLoad/customer.json',
	//	btn: '#fnCBtnCustomer',
	//	tpl: {
	//		tbody: ['{{each pageList as item i}}',
	//			'    <tr class="fn-tac m-table-overflow">',
	//			'        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 10)?item.customerName.substr(0,10)+\'\.\.\':item.customerName}}</td>',
	//			'        <td title="{{item.busiLicenseNo}}">{{(item.busiLicenseNo && item.busiLicenseNo.length > 18)?item.busiLicenseNo.substr(0,18)+\'\.\.\':item.busiLicenseNo}}</td>',
	//			'        <td title="{{item.enterpriseType}}">{{(item.enterpriseType && item.enterpriseType.length > 10)?item.enterpriseType.substr(0,10)+\'\.\.\':item.enterpriseType}}</td>',
	//			'        <td title="{{item.industry}}">{{(item.industry && item.industry.length > 10)?item.industry.substr(0,10)+\'\.\.\':item.industry}}</td>',
	//			'        <td><a class="choose" customerid="{{item.customerId}}" customername="{{item.customerName}}" num="{{item.busiLicenseNo}}" href="javascript:void(0);">选择</a></td>',
	//			'    </tr>',
	//			'{{/each}}'
	//		].join(''),
	//		form: ['客户名称：',
	//			'<input class="ui-text fn-w100" type="text" name="customerName">',
	//			'&nbsp;&nbsp;',
	//			'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
	//		].join(''),
	//		thead: ['<th>客户名称</th>',
	//			'<th>营业执照号码</th>',
	//			'<th>企业性质</th>',
	//			'<th>所属行业</th>',
	//			'<th width="50">操作</th>'
	//		].join(''),
	//		item: 5
	//	},
	//	callback: function($a) {
    //
	//		projectList.resetAjaxUrl('/baseDataLoad/queryProjects.json?customerType=ENTERPRISE&customerId=' + $a.attr('customerid'));
	//		$('#projectName').val('');
	//		$('#projectCode').val('');
	//	}
	//});
	var projectList = new getList()

	projectList.init({
		title: '项目列表',
		ajaxUrl: '/baseDataLoad/queryProjects.json?fromRiskCouncil=YES',
		btn: '#fnToChooseProjectCode',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td class="item">{{item.projectCode}}</td>',
				'        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
				'        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
				'        <td>{{item.busiTypeName}}</td>',
				'        <td>{{item.amount}}</td>',
				'        <td><a class="choose"  customerId="{{item.customerId}}" customerName="{{item.customerName}}"  projectCode="{{item.projectCode}}"  projectName="{{item.projectName}}"  busiManagerId="{{item.busiManagerId}}" deptCode="{{item.deptCode}}"  busiManagerName="{{item.busiManagerName}}"    href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['项目名称：',
				'<input class="ui-text fn-w160" type="text" name="projectName">',
				'&nbsp;&nbsp;',
				'客户名称：',
				'<input class="ui-text fn-w160" type="text" name="customerName">',
				'&nbsp;&nbsp;',
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
		},
		callback: function($a) {
			$('#projectName').val($a.attr('projectName')).trigger('blur');
			$('#projectCode').val($a.attr('projectCode')).trigger('blur');
			$('#busiManagerId').val($a.attr('busiManagerId'));
			$('#busiManagerName').val($a.attr('busiManagerName'));
			$("#customerId").val($a.attr('customerId'));
			$("#customerName").val($a.attr('customerName')).trigger('blur');
		}
	});


	var $form = $("#form");
	$form.on("click","#fnToClearCustomer",function(){
		$("#customerId").val("");
		$("#customerName").val("");
		$('#projectName').val("");
		$('#projectCode').val("");
	}).on("click","#fnToChooseProjectCode",function(){
		//if ($("#customerId").val() =="0" || !$("#customerId").val()) {
		//	Y.alert('提示', '请先选择客户');
		//	return false;
		//}
	}).on("click","#fnToClearProjectCode",function(){
		$('#projectName').val("");
		$('#projectCode').val("");
	})
    //------ 公共 验证规则 start
    require('validate');
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            // element.parent().append(error);
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else {
                element.parent().append(error);
            }
        },
        submitHandler: function(form) {
        },
        rules: {
            projectCode: {
                required: true
            },
            customerName: {
                required: true
            },
            reprot1: {
                required: true
            }
        },
        messages: {
            projectCode: {
                required: '必填'
            },
            customerName: {
                required: '必填'
            },
            reprot1: {
                required: '必填'
            }
        }
    };
    $form.validate(validateRules);
    function dynamAddRules(ele) {
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput').not(':hidden');
        $nameList.each(function (i,e) {
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
        if(!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput').not(':hidden');
        $nameList.each(function (i,e) {
            $(this).rules("remove");
        })
    };
	//提交
	var $addForm = $('#form');

	$('.ui-btn-submit').on('click', function() {

        if (this.className.indexOf('print') >= 0) {

            var $div = $('#form').removeClass('border')

            $div.find('#optBtn, .m-blank').remove()

            util.print($div.html())
            return
        }

        if (this.className.indexOf('exprot') >= 0) {

            var $ex = $('#form').removeClass('border')

            $ex.find('#optBtn, .m-blank').remove()

            util.css2style($ex)

            var __body = document.getElementsByTagName('body')[0]

            __body.innerHTML = ''
            __body.appendChild($ex[0])

            $('head link, script, .ui-tool, #industryBox').remove()

            var deflate = pako.gzip(document.documentElement.outerHTML, {
                to: 'string'
            })

            $.ajax({
                type: 'post',
                url: '/baseDataLoad/createDoc.json',
                data: {
                    formId: util.getParam('reportId'),
                    type: $ex.attr('thistype'),
                    decode: 'yes',
                    htmlData: encodeURIComponent(deflate)
                },
                success: function (res) {
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

            return
        }

        var $fnInput = $('.fnInput');

		var url = "/projectMg/projectRiskReport/list.htm"
		var _this = $(this);
        if(_this.hasClass('returnBack')) return;
        if(!$('#form').valid()) return;
		if (_this.hasClass('ing')) {
			return;
		}
		_this.addClass('ing');

		var _isPass = true,
			_isPassEq;
		$fnInput.each(function(index, el) {

			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}

		});

		if (!_isPass) {
			Y.alert('提示', '请填写完整表单', function() {
				$fnInput.eq(_isPassEq).focus();
			});
			_this.removeClass('ing');
			return;
		}

		util.ajax({
			url: $addForm.attr('action'),
			data: $addForm.serialize(),
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '已提交', function() {
						util.direct(url);

					});
				} else {
					Y.alert('提示', res.message);
					_this.removeClass('ing');
				}
			}
		});
	});
});