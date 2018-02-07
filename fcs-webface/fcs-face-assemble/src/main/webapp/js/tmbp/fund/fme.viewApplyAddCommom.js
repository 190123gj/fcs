define(function (require, exports, module) {

	var util = require('util')

    var popupWindow = require('zyw/popupWindow')
	// 审核通用部分 start
	var auditProject = require('/js/tmbp/auditProject');
	var _auditProject = new auditProject('auditSubmitBtn');
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');

	//------ 侧边栏 start
	var publicOPN = new(require('zyw/publicOPN'))();

	var isNeedPrint = document.getElementById('fnNeedPrint')

	if (!!isNeedPrint) {

		var isNeedPrintId = isNeedPrint.getAttribute('boxid')

		publicOPN.addOPN([{
			name: '打印',
			alias: 'doPrint',
			event: function () {

				util.print(document.getElementById(isNeedPrintId).innerHTML)

			},
		}])

	}

	publicOPN.init().doRender();
	//------ 侧边栏 end

    $('#fnPrint').click(function (event) {
        var $fnPrintBox = $('#div_print')
        $fnPrintBox.find('.ui-btn-submit').remove()
        $fnPrintBox.find('.printshow').removeClass('fn-hide')
        util.print($fnPrintBox.html())
    })


    //收款账户
    $('body').on('click', '.paymentBtn', function(event) {

        var $this = $(this);


        fundDitch = new popupWindow({

            YwindowObj: {
                content: 'paymentScript', //弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', //find关闭弹窗对象
                dragEle: '.newPopup dl dt' //find拖拽对象
            },

            ajaxObj: {
                url: '/baseDataLoad/bankMessage.json',
                type: 'post',
                dataType: 'json',
                data: {
                    // isChargeNotification: "IS",
                    // projectCode: _projectCode,
                    pageSize: 6,
                    pageNumber: 1
                }
            },
            formAll: { //搜索
                submitBtn: '#PopupSubmit', //find搜索按钮
                formObj: '#PopupFrom', //find from
                callback: function($Y) {

                }
            },
            pageObj: { //翻页
                clickObj: '.pageBox a.btn', //find翻页对象
                attrObj: 'page', //翻页对象获取值得属性
                jsonName: 'pageNumber', //请求翻页页数的dataName
                callback: function($Y) {

                    //console.log($Y)

                }
            },

            callback: function($Y) {

                $Y.wnd.on('click', 'a.choose', function(event) {
                    var _this = $(this),
                        _account = _this.parents('tr').attr("account"),
                        _amountStr = _this.parents('tr').find('td').eq(5).text().replace(/\,/g, '');
                    // console.log(_this.parents('tr').html())
                    $this.siblings('.fnBankAccount').val(_account).blur();

                    if ($this.siblings('.feeTypePrev ').length) {

                        $this.parents('tr').find('[amountStr]').attr('amountStr', _amountStr).blur();
                        $this.parents('tr').attr('paymentAccount', _account);

                        //zhurui  bug fix 2017-5-8
                        var _index = $this.parents('tr').index();
                        $("input[name='feeOrder["+ _index +"].paymentAccount']").val(_account);
                        $("input[name='feeOrder["+ _index +"].paymentAccount']").attr('value',_account);




                    }

                    $Y.close();

                });

            },

            console: false //打印返回数据

        });

    });
    //zhurui  bug fix 2017-5-9
    $('body').on('focus','.laydate-icon',function () {
        var $this = $(this);
        var _value = $(this).val();
        $this.attr('value',_value);
        var _index = $this.parents('tr').index();
        $("input[name='feeOrder["+ _index +"].paymentDateStr']").val(_value);
        $("input[name='feeOrder["+ _index +"].paymentDateStr']").attr('value',_value);
    })
    util.resetName()

})