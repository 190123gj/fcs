define(function (require, exports, module) {

    //上传
    require('zyw/upAttachModify');
    require('Y-imageplayer');

    //通用方法
    var util = require('util');

    var riskQuery = require('zyw/riskQuery');
    var initRiskQuery2 = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');

    var util = require('util');
    var openDirect = util.openDirect;

    $('body').on('click', '#customerId', function (event) {

        openDirect('/assetMg/index.htm', '/assetMg/list.htm?customerId=' + $(this).attr('customerId'), '/assetMg/list.htm');

    });

    if (document.getElementById('auditForm')) {
        // 审核通用部分 start
        var auditProject = require('/js/tmbp/auditProject');
        var _auditProject = new auditProject('auditForms');
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');
    }

    //------ 侧边栏 start

    var publicOPN = new(require('zyw/publicOPN'))();
    if (document.getElementById('fnView')) {
        var riskLevelFormId = $('input[name="riskLevelFormId"]').val(),
            lastCheckFormId = $('input[name="lastCheckFormId"]').val();
        if (riskLevelFormId > 0) {
            publicOPN.addOPN([{
                name: '风险等级评定',
                url: '/projectMg/riskLevel/view.htm?formId=' + riskLevelFormId,
            }]);
        }
        if (lastCheckFormId > 0) {
            publicOPN.addOPN([{
                name: '查看检查报告',
                url: '/projectMg/afterwardsCheck/view.htm?formId=' + lastCheckFormId,
            }]);
        }
        publicOPN.addOPN([{
            name: '风险检索',
            alias: 'risksearch',
            event: function () {
                var customerName = $('.customerName').val(),
                    customerId = $('.customerId').val();
                util.risk2retrieve(customerName, customerId);
            }
        }]);
    }
    publicOPN.addOPN([{
        name: '查看失信黑名单',
        alias: 'someEvent2',
        event: function () {
            initRiskQuery2.showSX();
        }
    }]);
    publicOPN.init().doRender();

    //------ 侧边栏 end

    var $step = $('#step'), // 顶部导航
        $fnStep = $('.fnStep'), // 表单集合
        lastFTFID = $step.find('li.active').attr('ftf'); //上一次的标记

    //--- 绑定事件
    $step.on('click', 'li:not(.active)', function () {
        var _this = $(this);
        lastFTFID = _this.attr('ftf');
        _this.addClass('active').siblings().removeClass('active');
        $fnStep.addClass('fn-hide');
        $('#' + lastFTFID).removeClass('fn-hide');
    });
    $('.fnNext').click(function () {
        $('#step').find('li').eq(1).trigger('click');
    })

    //收起
    $('.packBtn').click(function (event) {
        var _this = $(this);
        _this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
        _this.parent().next().slideToggle();
    });

    var Arr = [
        [],
        [],
        []
    ];

    $('body').on('change', '.changeMoneyUnit', function (event) {

        var $this, $target, $index, $val, $rideNum;

        $this = $(this);
        $val = $(this).val();
        $index = $('body .changeMoneyUnit').index($this);
        $target = $this.parents('dl').find('tbody td');

        if ($val == 'Y') {

            $rideNum = 1;

        } else {

            $rideNum = 0.0001;

        }

        $target.each(function (index, el) {

            var $el, $text;

            $el = $(el);
            $text = parseFloat($el.text().replace(/\,/g, ''));

            if (Arr[$index][index] == undefined) { //如果相应队列中不存在该值则添加

                Arr[$index][index] = $text;

            }

            if (Arr[$index][index]) {

                $el.text((Math.round(Arr[$index][index] * $rideNum * 100) / 100).toFixed(2).replace(/\B(?=(?:\d{3})+\b)/g, ','));

            }

        });

    });


    // 是否必填

    function supervise(orderName) {
        // ------ fnTableList resetName 0~N
        
        xlh( $('div.fnRemark') );
    }

    supervise();

    xlh( $('label.fnWrite') )


    function xlh(obj){
        var _this = obj;
        $('body').find(_this).each(function(index){

            var divs = $(this),
                _orderName = divs.attr('orderName'),
                index = index;

            divs.find('[name]').each(function() {
                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.lastIndexOf('.') + 1);
                }

                name = _orderName + '[' + index + '].' + name;

                _this.attr('name', name);

            });
        })
    }

    $('body').find('.fnWrite').each(function(){
        var _self = $(this),
            _selfInput = _self.children('[type="checkbox"]');
            _selfP = _self.parent().parent('div'),
            _val = _self.find($('[type="hidden"]')).val(),
            newHtml = '<div class="m-item fn-mt30 fnRemark" orderName="notCollectes">' 
                            +'<label class="m-label"><span class="m-required">*</span>未收集到原因：</label>'
                            +'<textarea class="ui-textarea2 fnInput fn-w500" name="remark" placeholder="请控制在1000字之内">' + _val +  '</textarea>'
                        +'</div>';
                        
        _self.parent().after(newHtml);

        _selfP.find('.fnRemark').hide();

        _selfP.find('.fnRemark textarea').removeClass('fnInput');

        if( _val != '' ){
            _selfP.find('.fnRemark').show();
            _selfP.find('.fnRemark textarea').addClass('fnInput');

            if (document.getElementById('fnView')){
                _selfP.find('.fnRemark textarea').attr('readonly','readonly');
            }
        }

        xlh( $('div.fnRemark') );
    })

    xlh( $('div.fnRemark') );

    $(document).on("click",".fnUpload",function(){
        var _this = $(this);
        $(".fnShowEndFileBox").addClass("fn-hide");
        _this.siblings(".fnShowEndFileBox").removeClass("fn-hide");
    });

    $(document).on('click', '.close',function() {
        $(this).parents(".fnShowEndFileBox").addClass("fn-hide");
    })

});