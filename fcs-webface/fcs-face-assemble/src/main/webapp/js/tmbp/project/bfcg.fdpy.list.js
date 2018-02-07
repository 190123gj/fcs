define(function (require, exports, module) {
    // 项目管理 > 授信前管理  > 资金划付申请单列表
    require('zyw/publicPage');
    require('input.limit');
    require('Y-window');
    require('zyw/upAttachModify');

    var util = require('util');
	//授信类型
	$("#fnListExport").click(function() {
		var url = $(this).attr("exportUrl");
		url = url + "?" + $("#fnListSearchForm").serialize();
		window.location = url;
	});
	
    // 选择项目类型 star//
    $('.fn-addtype').click(function () {
        $('body').Y('Window', {
            content: '#fnNewC',
            simple: true,
            modal: false,
            closeEle: '.close'
        });
    });
    // 选择项目类型 end//
    // 
    // 选择业务品种 start
    var isSimple = $('#isSimple').val();
    var getTypesOfCredit = require('zyw/getTypesOfCredit');
    var _getTypesOfCredit = new getTypesOfCredit();
    _getTypesOfCredit.init({
        chooseAll: true,
        btn: '#businessTypeBtn',
        name: '#businessTypeName',
        code: '#businessTypeCode',
        callback: function (self) {
        	if(isSimple != "IS"){
	            var _html = ['<li>',
	                '  <p class="h-title fn-fwb fnCanChoose" businesstypecode="FINANCIAL_PRODUCT" customertype="FINANCIAL_PRODUCT"><span class="fn-ml10 name">理财业务</span></p>',
	                '  <ul class="fn-clear fn-pl20">',
	                '    <li class="fn-left fn-w148 sub-li fnCanChoose fnCanChoose" businesstypecode="FINANCIAL_PRODUCT" customertype="FINANCIAL_PRODUCT">',
	                '      <span class="fn-ml10 name">理财</span>',
	                '    </li>',
	                '  </ul>',
	                '</li>'
	            ].join('');
	            self.$box.find('ul').eq(0).append(_html)
        	}
        },
        diychoose: function (self, $choose) {
            if ($choose.attr('businesstypecode') == 'FINANCIAL_PRODUCT') {
                $businessTypeCode.attr('name', 'projectType')
                $businessTypeName.attr('name', 'busiTypeNameNotNeed')
            } else {
                $businessTypeCode.attr('name', 'busiType')
                $businessTypeName.attr('name', 'busiTypeName')
            }
            $businessTypeName.val($choose.find('.name').text());
            $businessTypeCode.val($choose.attr('businessTypeCode'));
        }
    });

    var $businessTypeName = $('#businessTypeName'),
        $businessTypeCode = $('#businessTypeCode');

    // 选择业务品种 end

    $('#list').on('click', '.notwithdraw', function () {
        Y.alert('提示', '您提交的申请单已进入审核流程，无法取消！');
    }).on('click', '.upload', function () {
        $fnUpload.removeClass('fn-hide');
        $fnUpload.find('#formId').val(this.getAttribute('formid'));
        $fnUpload.find('#fnFKJE').attr('max', this.getAttribute('money'));
    });

    var $fnUpload = $('#fnUpload');

    $fnUpload.on('click', '.fnUploadX', function () {

        window.location.reload(true);

    }).on('click', '.fnUploadBtn', function () {

        // 一个都没有填写 提示
        var _isPass = true,
            _isPassEq,
            $fnInput = $fnUpload.find('.fnInput');

        $fnInput.each(function (index, el) {
            if (!!!el.value.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = index;
                return;
            }
        });

        if (!_isPass) {
            Y.alert('提示', '请输入信息', function () {
                $fnInput.eq(_isPass).focus();
            });
            return false;
        }

        util.ajax({
            url: '/projectMg/fCapitalAppropriationApply/saveReceipt.htm',
            data: $fnUpload.find('form').serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', '操作成功', function () {
                        window.location.reload(true);
                    });
                } else {
                    Y.alert('提示', res.message);
                }

            }
        });

    });

    $("#formStatus").change(function () {
        if ($(this).val() == "APPROVAL") {
            $("#hasReceipt").show();
        } else {
            $("#hasReceipt").hide();
            $("[name=hasReceipt]").val("");
        }
    }).change();
});