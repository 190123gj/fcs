define(function(require, exports, module) {

    require('Y-msg');
    require('validate');
    require('validate.extend');
    
    
    $('body').on('blur','.isMobile',function () {
        var allTel = []
        $.each($('.isMobile'),function (i,e) {
            if(!!$(this).val()) allTel.push($(this).val());
        });
        $('[name=messageReceiver]').val(allTel)
    }).on('click','.addLine',function () {//新增一行
        var $self = $(this);
        var templateId = $self.attr('templateid');
        if(!templateId) return;
        var maxItems = $self.attr('maxitems') || false;
        var $itemBox = $self.parents('.itemBox').find('.itemsList');
        var $template = $($('#' + templateId).html());
        var itemBoxListLength = $itemBox.find('.itemLine').length;
        $template.find('.fn-validate').removeClass('ignore');//移除验证

        if(itemBoxListLength - 1 >= maxItems && !!maxItems) {
            Y.alert('提示','不能超过' + maxItems + '条');
            return;
        };
        $template.find('.itemListIndex').val(itemBoxListLength + 1);
        if($itemBox.find('tr.resltTr').length == 1){
            $itemBox.find('tr.resltTr').before($template)
        }else {
            $itemBox.append($template)
        };
        diyName();
    }).on('click','.deleteLine',function () {//删除一行
        var $self = $(this);
        var $selfItem = $self.parents('.itemLine');
        var $itemBox = $self.parents('.itemsList');
        var residueItems = $itemBox.find('.itemLine').length;
        var minLength = $self.attr('minlength') || 1;
        // console.log(minLength)
        if(residueItems <= minLength) {
            Y.alert('提示','至少保留一条！');
            return;
        };
        $selfItem.remove();
        diyName();
        $('.isMobile').eq(0).blur();
    }).on('click','.delTrData',function () {//删除一行
        var $self = $(this);
        var url = $self.attr('opthref');

        Y.alert('提示','是否确认删除？',function () {
            $.ajax({
                url:url,
                success:function (res) {
                    if(res.success){
                        Y.alert('提示',res.message,function () {
                            window.location.href = window.location.href;
                        });
                    }else {
                        Y.alert('提示',res.message);
                    }
                }
            })
        })
    });

    //序列化name

    function diyName($box) {

        if(!$box) $box = $form;
        var $diyNameBox = $box.find('.diyNameBox');
        $diyNameBox.each(function () {
            $(this).find('[diyname]').each(function(index,ele) {
                var $thisWrap = $(this);
                var diyName = $thisWrap.attr('diyname');

                $thisWrap.find('[name]').each(function() {

                    var _this = $(this),
                        name = _this.attr('name');

                    if (name.indexOf('.') > 0) {
                        name = name.substring(name.lastIndexOf('.') + 1);
                    };

                    name = diyName + '[' + index + '].' + name;
                    _this.attr('name', name);
                    if(index == $(this).find('[diyname]').length - 1){
                        // addRules ($(this).find('[diyname]'));
                        dynamAddRules()
                    };

                });

            });
        });

    };
    
    //------ 公共 验证规则 start
    var $form = $('#form');
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            // element.parent().append(error);
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else if (element.hasClass('parentsMitem')) {
                element.parent('.m-item ').append(error);
            } else {
                element.parent().append(error);
            }
            // console.log(element)
        },
        submitHandler: function (form) {},
        rules: {
            assetType: {
                required: true
            }
        },
        messages: {
            assetType: {
                required: '必填'
            }
        }
    };
    $form.validate(validateRules);

    function dynamAddRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fn-validate');
        dynamicRemoveRules($form);
        $nameList.each(function (i, e) {
            var _this = $(this);
            if(_this.hasClass('isMobile')){
                _this.rules('add', {
                    required: true,
                    isMobile: true,
                    messages: {
                        required: '必填',
                        isMobile:'请输入正确格式手机号！'
                    }
                });
            }else {
                _this.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
            }

        })
    };

    function dynamicRemoveRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fn-validate,.leesOneItemInput.fn-validate');
        $nameList.each(function (i, e) {
            $(this).rules("remove");
        })
    };
    dynamAddRules();

    var _isPass = true;//防止重复提交
    $('.submitBtn').on('click', function () { //提交前验证
        if (!_isPass) return;

        dynamAddRules()
        if (!$form.valid()) return;
        _isPass = false;
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提醒', res.message, function () {
                        window.location.href = '/systemMg/shortMessage/list.htm';
                    });
                } else {
                    Y.alert('提醒', res.message);
                };
                _isPass = true;
            }

        });


    });
    
    
});