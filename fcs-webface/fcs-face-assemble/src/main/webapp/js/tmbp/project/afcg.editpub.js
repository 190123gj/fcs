define(function (require, exports, module) {


    // ------ 导航栏切换 自定义保存数据 start

    var DIY_AJAX = {}

    // ------ 导航栏切换 自定义保存数据 end

    // ------ 客户资料 start

    $('#stepCust').remove()

    // require('tmbp/customer/personal.enterprise.add')

    if (document.getElementById('fnIsEnterpriseInfo')) {
        require.async('tmbp/customer/corporate.add', function (obj) {
            obj.publicOPN.$back.remove()
            obj.publicOPN.remove()
        })
    }

    if (document.getElementById('fnIsIndividualInfo')) {
        require.async('tmbp/customer/personal.add', function (obj) {
            obj.publicOPN.$back.remove()
            obj.publicOPN.remove()
        })
    }

    var contrastTool = require('zyw/changeApply');

    var ALLDATALIST, // 初始值
        $fnChangeForm = $('#fnChangeForm'),
        $fnCustomerForm = $('#form'), // 客户资料
        ORIGINAL_PAGE = ''; // 未修改的查看页面 

    DIY_AJAX.doSaveCustomer = function (callback) {

        var $_form = $fnCustomerForm,
            _isPass = 0;

        if ($_form.valid()) {
            _isPass = 1;
        }

        var _html = contrastTool.getHtml('#form')
            .replace(/(industryBox|addressBox)/g, '') // 去除行业、地区
            .replace(/<a(\w|"|=|\s|\;|\(|\)|\:|\-)*>删除行<\/a>/g, '-') // 去除删除
            .replace(/apply-upfile-img fnUpFile/g, 'apply-upfile-img nomt') // 图片上传换查看
            .replace(/<span(\w|"|=|\s|\;|\(|\)|\:|\-)*>\*<\/span>/g, '') // 必填标志
            .replace(/<div class=\"fn\-mt5\">\s*<p>\(非三证合一证件号码，请填写组织机构代码\)<\/p>\s*<\/div>/g, '')

        _html = encodeURIComponent(util.trimHtml(_html))

        var $fromCopy = $('<form></form>').html($fnCustomerForm.html()),
            $fromCopyNames = $fromCopy.find('[name]')

        $fnCustomerForm.find('[name]').each(function (index, el) {

            var type = (el.tagName == 'INPUT') ? el.type : el.tagName
            var $input = $fromCopyNames.eq(index)

            switch (type) {

                case 'text':
                case 'hidden':
                    $input.attr('value', el.value);
                    break;

                case 'TEXTAREA':
                    $input.html(el.value);
                    break;

                case 'TEXTAREA':
                    $input.attr('value', el.value);
                    break;

                case 'radio':
                case 'checkbox':
                    if (el.checked) {
                        $input.attr('checked', 'checked');
                    } else {
                        $input.removeAttr('checked');
                    }
                    break;
                case 'SELECT':
                    $input.find('option').removeAttr('selected')
                        .end().find('option[value="' + el.value + '"]').attr('selected', 'selected');
                    break;

            }

        });

        $fromCopy.find('#industryBox').html('')
            .end().find('#addressBox').html('')

        var $postForm = $('<form></form>')
            .append('<input type="hidden" value="' + document.getElementsByName('formId')[0].value + '" name="formId">') // 表单id
            .append('<input type="hidden" value="' + document.getElementsByName('customerId')[0].value + '" name="customerId">') // 客户id
            .append('<input type="hidden" value="' + document.getElementsByName('customerName')[0].value + '" name="">') // 客户名称
            .append('<input type="hidden" value="' + encodeURIComponent($fromCopy.html()) + '" name="editHtml">') // 编辑的html
            .append('<input type="hidden" value="' + ORIGINAL_PAGE + '" name="">') // 原始查看页面
            .append('<input type="hidden" value="' + _isPass + '" name="status">') // 是否通过
            .append('<input type="hidden" value="' + _html + '" name="viewHtml" >') // 修改后的查看页面
            .append('<input type="hidden" value="' + encodeURIComponent($fnCustomerForm.serializeJCK()) + '" name="formData" >') // 修改后的表单数据

        util.ajax({
            url: '/projectMg/afterwardsCheck/saveCustomer.json',
            data: $postForm.serialize(),
            success: function (res) {

                if (callback) callback()

            }
        })

    }


    // ------ 客户资料 end

    //表单验证
    require('validate');
    require('validate.extend');
    //增删行
    require('zyw/opsLine');
    //Y对话框
    require('Y-msg');

    //md5加密
    var md5 = require('md5');
    //模板引擎
    var template = require('arttemplate');
    //通用方法
    var util = require('util');
    require('Y-window');
    //字数提示
    require('zyw/hintFont');
    //上传
    require('zyw/upAttachModify');
    require('input.limit');

    var yearsTime = require('zyw/yearsTime');

    var riskQuery = require('zyw/riskQuery');
    var initRiskQuery2 = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');

    var util = require('util');
    var openDirect = util.openDirect;

    $('body').on('click', '#customerId', function (event) {

        openDirect('/assetMg/index.htm', '/assetMg/list.htm?customerId=' + $(this).attr('customerId'), '/assetMg/list.htm');

    }).on('click', '.fnlaydate', function () {
        var yearsTimeFirst = new yearsTime({
            elem: this
        })
    });

    // var setUE = require('zyw/setUE');
    // setTimeout(function () {
    //     setUE();
    // }, 1000);

    // 单位切换

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

    //------ rowspan start

    $(".fn-addtab").on("click", ".fn-addline", function () {
        var self = $(this),
            sum_tr = self.parents().children().children('.fn-testtable').children('tr'),
            sum_trnum = sum_tr.length,
            left_rowspan = sum_tr.children('.fnleft_rowspan'),
            right_rowspan = sum_tr.children('.fnright_rowspan'),
            firstTr = self.parents().find('.fn-testtable tr:first'),
            firsttrTd = firstTr.find('a.fn-del');

        left_rowspan.attr('rowspan', sum_trnum + 1);
        right_rowspan.attr('rowspan', sum_trnum + 1);

        setTimeout(function () {
            firsttrTd.removeClass('jian-btn');
            return false;
        }, 5)
    });

    $(".fn-addtab").on("click", ".fn-del", function () {
        var self = $(this),
            self_tr = self.parent().parent(),
            sum_tr = self.parents().parents().parents('.fn-testtable').children('tr'),
            sum_trnum = sum_tr.length,
            left_rowspan = sum_tr.children('.fnleft_rowspan'),
            firstTr = self.parents().find('.fn-testtable tr:first'),
            firsttrTd = firstTr.find('a.fn-del');
        left_rowspan.attr('rowspan', sum_trnum - 1);

        if (sum_trnum == 1) {
            console.log('表格没有tr啦~')
            sum_tr.parents().parents().parents('.box').remove();
        }

        self_tr.remove();

        setTimeout(function () {
            if (sum_trnum == 2) {
                firsttrTd.addClass('jian-btn');
            }

        }, 5)

    });

    //------ rowspan end

    //------choose-box start
    $('.fn-choose p').click(function () {
        $(this).addClass('active').siblings().removeClass('active');
    })

    //-------choose-box end

    //------ 数组Name命名 start
    function resetName(orderName) {

        // ------ fnTableList resetName 0~N

        $('.fnTableList').each(function () {

            $(this).find('tr[orderName]').each(function (index) {

                var tr = $(this),
                    _orderName = tr.attr('orderName'),
                    index = index;
                // index = tr.index('tr[orderName]', _fnTableList);

                tr.find('[name]').each(function () {

                    var _this = $(this),
                        name = _this.attr('name');

                    if (name.indexOf('.') > 0) {
                        name = name.substring(name.lastIndexOf('.') + 1);
                    }

                    name = _orderName + '[' + index + '].' + name;

                    _this.attr('name', name);

                });

            });

        });

        // 序列化
        var _td;

        if (orderName) {
            _td = $('td[orderName=' + orderName + ']');
        } else {
            _td = $('td[orderName]');
        }


        _td.each(function () {

            var td = $(this),
                _orderName = td.attr('orderName'),
                index = td.index();

            //console.log("_orderName: ",_orderName);

            td.find('[name]').each(function () {

                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.lastIndexOf('.') + 1);
                }

                name = _orderName + '[' + index + '].' + name;

                _this.attr('name', name);

            });

        });

    }
    //------ 数组Name命名 end
    resetName();


    $('.fnTableList').on('change', '.newText', function () {
        var selfVal = $(this).val(),
            hiddenVal = $(this).parents().parents().parents('tbody');
        hiddenVal.find('.newTextVal').val(selfVal);
    });

    //------ 增加下级目录 start
    $('.fn-addbtn').click(function () {
        var self = $(this),
            self_add = self.parent().parent().parent('.fn-addtab').attr('addid'),
            self_tpl = self.parent().parent().parent('.fn-addtab').children('.pub-tab').attr('tplid');
        $('#' + self_add).find('.box:last').after($('#' + self_tpl).html());
        resetName();
    })

    $(".fn-addtab").on("click", ".fn-addline", function () {
        var self = $(this),
            self_addbox = self.parent('.box').children().children('tbody'),
            selfVal = self_addbox.find('.fnleft_rowspan .newTextVal').val(),
            self_tplline = self.parent().parent().children('.fn-publine').attr('tplline'),
            self_itemName = $.trim(self_addbox.children().children('.fnleft_rowspan').text());

        self_addbox.append($('#' + self_tplline).html());
        self_addbox.children().children('.fn-itemName').val(self_itemName);
        self_addbox.find('.newTextVal').val(selfVal);
        resetName();
    });
    //------ 增加下级目录 end


    //--------------------项目基本情况 start

    //----数据验证 项目基本情况 start
    $('#xmjbqk').validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            element.after(error);
        },
        submitHandler: function (form) {
            // if(sjdata()){
            //     $('#step').find('li').eq(1).trigger('click');
            // }
            $('#step').find('li').eq(1).trigger('click');
        },
        rules: {
            'feedbackOpinion': {
                required: true,
                maxlength: 1000
            },
            'customerOpinion': {
                required: true,
                maxlength: 1000
            },
            'spendWay': {
                required: true,
                maxlength: 50
            },
            'collected': {
                required: true
            }
        },
        messages: {
            'feedbackOpinion': {
                required: '必填',
                maxlength: '已超出最大长度1000字'
            },
            'customerOpinion': {
                required: '必填',
                maxlength: '已超出最大长度1000字'
            },
            'spendWay': {
                required: '必填',
                maxlength: '用款方式最多为50个字符'
            },
            'collected': {
                required: '必填'
            }
        }
    });
    //----数据验证 项目基本情况 end

    //--------------------监管内容 start
    // 数据验证
    $(".fn-YN tr:last,.fn-gz tr:last").find('input.radio').removeClass('radio');
    $(".fn-YN tr:last").find('input.text').removeClass('fnInput');
    var $form = $('#jgnr'),
        $fnInput = $('.fnInput'),
        $fnradio = $('input.radio:not(.ignore)'),
        $fntextarea = $('textarea.fnInput:not(.fnMakeUE)'),
        $fntextarea500 = $('textarea.text500'),
        formRules = {
            rules: {},
            messages: {}
        };

    $fnInput.each(function (index, el) {
        //必填规则
        var name = $(this).attr('name');
        if (!name) {
            return true;
        }
        formRules.rules[name] = {
            required: true
        };
        formRules.messages[name] = {
            required: '必填'
        };
    });


    $fnradio.each(function (index, el) {
        //必填规则
        var name = $(this).attr('name');
        if (!name) {
            return true;
        }
        formRules.rules[name] = {
            required: true
        };
        formRules.messages[name] = {
            required: '必填'
        };
    });

    $fntextarea.each(function (index, el) {
        //必填规则
        var name = $(this).attr('name');
        if (!name) {
            return true;
        }

        var _maxlength = +(this.getAttribute('maxlength') || 1000)

        formRules.rules[name] = {
            required: true,
            maxlength: _maxlength
        };
        formRules.messages[name] = {
            required: '必填',
            maxlength: '已超出最大长度' + _maxlength + '字'
        };
    });

    $fntextarea500.each(function (index, el) {
        //必填规则
        var name = $(this).attr('name');
        if (!name) {
            return true;
        }
        formRules.rules[name] = {
            required: true,
            maxlength: 500
        };
        formRules.messages[name] = {
            required: '必填',
            maxlength: '已超出最大长度500字'
        };
    });

    $('.fn-addbtn,.fn-addline').on('click', function () {

        var $this = $(this)

        var $fnWrite = $this.parents('.fnWriteBox').find('.fnWrite')

        if (!!$fnWrite.length && $fnWrite.find('input').eq(0).prop('checked')) {
            return;
        }

        var $table = $this.parent().find('table')

        setTimeout(function () {
            inputRadio($table);
        }, 5)
    })


    $form.validate($.extend({}, formRules, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            if (element.hasClass('radio')) {
                element.parent().siblings('label').after(error);
            } else {
                element.after(error);
            }
        },
        submitHandler: function (form) {

            if (tabUpload()) {

                if (trValidS()) {

                    $('body').find('.fnRemark').each(function () {

                        if ($(this).hasClass('hide')) {

                            $(this).find('textarea').rules('remove', 'required');
                        }
                    })

                    submitForm();
                }
            }
        },
    }));

    // 建筑
    function trValid(tbody) {
        var self = tbody,
            selfInput = self.find('.fnWrite input[type="checkbox"]');
        if (selfInput.hasClass('active')) {

            self.find('input[type="text"]').each(function () {
                $(this).rules('remove', 'required');
            })

        } else {

            self.find('input[type="text"]').each(function () {
                $(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })
            })
        }
    }

    trValid($('.fnzbBox'));

    $('.fnzbBox input').blur(function (event) {
        trValid($('.fnzbBox'));
    });

    function trValidS() {
        var errorChange = $('.errorChange').length,
            errorChange2 = $('.errorChange2').length;
        if (errorChange == 0 && errorChange2 == 0) {
            return true;
        } else {
            Y.alert('提示', '成本机构及变动情况表格填写错误~')
            return false;
        }
    }

    //收起
    $('.packBtn').click(function (event) {
        var _this = $(this);
        _this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
        _this.parent().next().slideToggle();
    });

    var _balance = $('#balance'), //资产负债表
        _cashFlow = $('#cashFlow'), //现金流量表
        _ecAnalyze = $('#ecAnalyze'), //盈利能力分析
        _profit = $('#profit'),
        _sum = []; //利润及利润分配表

    initHtmluploadfor(_balance);
    initHtmluploadfor(_profit);
    initHtmluploadfor(_cashFlow);

    function initHtmluploadfor(_obj) {
        _obj.find('tbody tr').each(function (index, el) {
            var _this = $(el);
            _sum.push(_this.find('td').eq(0).text())
        });
    }

    function htmluploadfor(_x, x, _data, bool) {

        for (var i = 0; i < _data[x].length; i++) {

            _x.find('tr').eq(i).addClass(_data[x][i][0]);

            for (var j = 1; j < _data[x][i].length; j++) {

                var _obj = _x.find('tr').eq(i).find('th,td').eq(j - 1),
                    _val = _data[x][i][j];

                if (j > 3) {
                    _x.find('tr').eq(i).find('th,td').eq(j - 3).find('em').remove();
                    _val ? _x.find('tr').eq(i).find('th,td').eq(j - 3).append('<em class="remind">' + _val + '</em>') : _x.find('tr').eq(i).find('th,td').eq(j - 3).find('em').remove();
                } else {
                    // (i == 0) ? _obj.text(_val).next().val(_val): ((j == 1) ? _obj.text(_val).next().val(_val) : _obj.find('input').val(_val));
                    ((j == 1) && (i != 0)) ? _obj.text(_val).next().val(_val): _obj.find('input').val(_val)
                }

                if (j == 1 && i > 0 && bool) {

                    _sum.push(_val);

                }
            }
            
            var objectTr=_x.find('tr');
            var maxCount=500;
            var time=1;
            while(_x.find('tr').length > _data[x].length&&time<maxCount)
            {
            	_x.find('tr').eq(_x.find('tr').length-1).remove();
            	time++;
            }
        }
    }

    $('.fnUpFileJGNR').click(function () {
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/afterwardsCheck/uploadFinancial.htm?type=' + $('[name="type"]').val()+'&financials_count='+ $('#financials_count').val()+'&profits_count='+ $('#profits_count').val()+'&flows_count='+ $('#flows_count').val(),
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, data, resfile) {
                _sum = [];
                var jsonValue=JSON.parse(data);
                if(jsonValue.success)
                {
                	var _data = jsonValue.datas;
                    //资产负债表
                    htmluploadfor(_balance, 'balance', _data, true);

                    //利润及利润分配表
                    htmluploadfor(_profit, 'profit', _data, true);

                    //现金流量表
                    htmluploadfor(_cashFlow, 'cashFlow', _data, true);
                    $('#importExcel').val('YES');
//                    console.log('数据导入成功！');
	
                }
                else
                {
                	Y.alert("错误提示",jsonValue.message)
                }
           }
        })
    })

    $('.fnUpFileCustomerCredit').on('click', function () {

        var $self = $(this)

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up2',
            uploader: '/baseDataLoad/uploadExcel.htm?type=acr_c_c_f',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, data, resfile) {

                var _data = JSON.parse(data).datas

                _data.shift() // 清除标题

                $.each(_data, function (index, arr) {
                    arr[1] = util.num2k((arr[1] || '').replace(/\,/g, ''))
                })

                var _html = template('t-fnUpFileCustomerCredit', {
                    list: _data
                })

                var $th = $self.parents('.box').find('tbody').find('tr').eq(0)

                $th.nextAll().remove()
                $th.after(_html)

                resetName()

                // 是否必填
                var $fnWriteBox = $self.parents('.fnWriteBox')

                if (!!$fnWriteBox.length && !$fnWriteBox.find('.fnWrite').find('[type="checkbox"]').prop('checked')) {

                    $self.parents('.box').find('tbody').find('.fnInput').each(function (index, el) {

                        $(el).rules('add', {
                            required: true,
                            messages: {
                                required: '必填'
                            }
                        })

                    })

                }

                console.log('数据导入成功！')
            }
        })

    })

    // 合计
    var fnsumBox5 = $('.fnsumBox5');

    function tabSum(tbody) {
        // tid 表格class
        var sum = parseFloat('0'),
            i = 1,
            tabBox = tbody,
            numArr = [],
            rslt = tabBox.parents().parents().parents('.fnHeji').children('.fnRslt');

        for (i = 1; i <= 4; i++) {
            var sum = parseFloat('0');
            tabBox.children('tr').each(function () {
                $(this).find('td').eq(i).each(function () {
                    if ($(this).children('input').val() == '') {
                        $(this).children('input').val('');
                        sum += parseFloat(0);
                    } else {
                        sum += parseFloat($(this).children('input').val());
                    }
                })
            });
            numArr[i] = sum;
            var sumTd = rslt.find('td').eq(i).children('input');
            if (sumTd.hasClass('fnFloat')) {
                sumTd.val(numArr[i].toFixed(2));
            } else if (sumTd.hasClass('fnInt')) {
                sumTd.val(numArr[i].toFixed(0));
            }
        }
    }
    $('input').blur(function () {
        tabSum(fnsumBox5);
    })

    fnsumBox5.on("change", "input", function () {
        tabSum(fnsumBox5);
    })

    fnsumBox5.on("click", ".fn-del", function () {
        setTimeout(function () {
            tabSum(fnsumBox5);
        }, 20)
    })


    $(".fn-qysr tr").each(function () {
        var selfOrderName = $(this).attr('orderName');
        if (selfOrderName != 'incomes') {
            $(this).attr('orderName', 'incomes');
        }
    })

    $(".fn-fdb tr").each(function () {
        var selfOrderName = $(this).attr('orderName');
        if (selfOrderName != 'counters') {
            $(this).attr('orderName', 'counters');
        }
    })

    $(".fb-yjxh tr").each(function () {
        var selfOrderName = $(this).attr('orderName');
        if (selfOrderName != 'warnings') {
            $(this).attr('orderName', 'warnings');
        }
    })

    //------ 触发提交表单 start
    function submitForm() {

        saveForm($step.find('li.active').attr('ftf'), function () {

            resetName();

            var formId = $('[name=formId]').eq(0).val(),
                lastFTFID = $step.find('li.active').attr('ftf'),
                _isPass = true,
                _isPassEq;

            $('#' + lastFTFID + ' .error-tip').each(function (index, el) {
                if ($(this).text() == '必填') {
                    _isPass = false;
                    _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                }
            });

            if (!_isPass) {

                $('#' + lastFTFID + ' .error-tip').eq(_isPassEq).parent().find('textarea,input').focus();
                return false;
            }

            if (document.getElementById('estate')) {

                var formId = $('[name=formId]').eq(0).val(),
                    _is;


                function fdc() {
                    // $('body').find('.fnWrite').each(function () {

                    //     var _self = $(this),
                    //         _selfC = $(this).find('input[type="checkbox"]');

                    //     if (_selfC.hasClass('active')) {

                    //         _is = false;
                    //         return _is;
                    //     } else {

                    //         _is = true;
                    //         return _is;
                    //     }
                    // })
                    var estate = $('.estate-detail ').find('.fnWrite input[type="checkbox"]');

                    if (estate.hasClass('active')) {
                        _is = false;
                        return _is;
                    } else {
                        _is = true;
                        return _is;
                    }
                }

                if (fdc()) {
                    util.ajax({
                        url: '/projectMg/afterwardsCheck/checkContentProject.json',
                        data: {
                            formId: formId
                        },
                        success: function (res2) {
                            // console.log(res2);
                            if(res2.success){
                                Y.confirm('提示', '确认提交当前表单？', function (opn) {
                                    if (opn == 'yes') {

                                        // var formId = $('[name=formId]').eq(0).val();

                                        util.ajax({
                                            url: '/projectMg/form/submit.htm',
                                            data: {
                                                formId: formId
                                            },
                                            success: function (res) {
                                                Y.alert('提示', res.message, function () {
                                                    if (res.success) {
                                                        window.location = '/projectMg/afterwardsCheck/list.htm';
                                                    }
                                                });
                                            }
                                        });

                                        // util.postAudit({
                                        //     formId: formId
                                        // }, function () {
                                        //     window.location = '/projectMg/afterwardsCheck/list.htm';
                                        // })

                                    }

                                });
                            }else {
                                Y.alert('提示', res2.message, function () {});
                            }

                        }
                    });
                } else {
                    Y.confirm('提示', '确认提交当前表单？', function (opn) {

                        if (opn == 'yes') {

                            // var formId = $('[name=formId]').eq(0).val();

                            util.ajax({
                                url: '/projectMg/form/submit.htm',
                                data: {
                                    formId: formId
                                },
                                success: function (res) {
                                    Y.alert('提示', res.message, function () {
                                        if (res.success) {
                                            window.location = '/projectMg/afterwardsCheck/list.htm';
                                        }
                                    });
                                }
                            });

                            // util.postAudit({
                            //     formId: formId
                            // }, function() {
                            //     window.location = '/projectMg/afterwardsCheck/list.htm';
                            // })

                        }

                    });
                }
            } else {
                Y.confirm('提示', '确认提交当前表单？', function (opn) {

                    if (opn == 'yes') {

                        util.ajax({
                            url: '/projectMg/form/submit.htm',
                            data: {
                                formId: formId
                            },
                            success: function (res) {

                                Y.alert('提示', res.message, function () {

                                    if (res.success) {
                                        window.location = '/projectMg/afterwardsCheck/list.htm';
                                    }

                                });
                            }
                        });

                        // util.postAudit({
                        //     formId: formId
                        // }, function () {
                        //     window.location = '/projectMg/afterwardsCheck/list.htm';
                        // })
                    }
                });
            }
        });
    }
    //------ 触发提交表单 end
    //
    //------ 关于表单储存 start
    //
    //--- 缓存表单md5
    var formMd5Obj = {};

    var $step = $('#step'), // 顶部导航
        $fnStep = $('.fnStep'), // 表单集合
        nextIndex = util.getParam('nextIndex'), //第一次保存的锚点
        lastFTFID = $step.find('li.active').attr('ftf'); //上一次的标记

    //--- 绑定事件
    $step.on('click', 'li:not(.active)', function () {

        clickFZ($(this),false);


    }).on('blur', 'input', function () {

        var _goalId = this.getAttribute('goal')
        var self = this
        if (!!!_goalId) {
            return
        }
        setTimeout(function () {

            if (+self.value <= 1) {
                self.value = 1
            }

            (document.getElementById(_goalId) || {}).value = self.value
        }, 50)

    });

    /**
     * click 封裝
     * @param $this
     */
    function clickFZ($this, delegate) {
        //点击保存切换或切换
        var _this = $this;
        var bool = delegate;

        //记录锚点
        nextIndex = _this.index();

        if (nextIndex == 0) {
            $step.find('.fnMakeNumber').removeProp('readonly')
        } else {
            $step.find('.fnMakeNumber').prop('readonly', 'readonly')
        }

        if (!!document.getElementById('fnIsViewPage')) {
            lastFTFID = _this.attr('ftf');
            _this.addClass('active').siblings().removeClass('active');
            $fnStep.addClass('fn-hide');
            $('#' + lastFTFID).removeClass('fn-hide');
            return
        }

        saveForm(lastFTFID, function () {
            if($('#'+lastFTFID).find('.border .error-tip').length && $('#'+lastFTFID).find('.border .error-tip').text() && bool){
                $('body,html').animate({
                    scrollTop: $('.error-tip').eq(0).offset().top-400
                }, 600);
                //console.log($('.error-tip').eq(0).offset())
            }else{
                lastFTFID = _this.attr('ftf');
                _this.addClass('active').siblings().removeClass('active');
                $fnStep.addClass('fn-hide');
                $('#' + lastFTFID).removeClass('fn-hide');
                $('body,html').animate({
                    scrollTop: 0
                }, 600);
            }
        });
    }
    

    //点击切换提交表单的封装

    function TabAndSubmit(_this) {


        //点击保存切换或切换
        //var _this = $(this);

        //记录锚点
        nextIndex = _this.index();

        if (nextIndex == 0) {
            $step.find('.fnMakeNumber').removeProp('readonly')
        } else {
            $step.find('.fnMakeNumber').prop('readonly', 'readonly')
        }

        if (!!document.getElementById('fnIsViewPage')) {
            lastFTFID = _this.attr('ftf');
            _this.addClass('active').siblings().removeClass('active');
            $fnStep.addClass('fn-hide');
            $('#' + lastFTFID).removeClass('fn-hide');
            return
        }

        saveForm(lastFTFID, function () {
            if($('#'+lastFTFID).find('.border .error-tip') && $('#'+lastFTFID).find('.border .error-tip').html()){
                $('body,html').animate({
                    scrollTop: $('.error-tip').eq(0).offset().top-400
                }, 600);

                //console.log($('.error-tip').eq(0).offset())

            }else{
                lastFTFID = _this.attr('ftf');
                _this.addClass('active').siblings().removeClass('active');
                $fnStep.addClass('fn-hide');
                $('#' + lastFTFID).removeClass('fn-hide');
                $('body,html').animate({
                    scrollTop: 0
                }, 600);
            }
        });

    }



    //--- 初始化操作
    if (nextIndex) {
        setTimeout(function () {
            $step.find('li').eq(nextIndex).trigger('click');
        }, 1000);
    }

    setFormMd5Obj();

    //--- 首先缓存md5
    function setFormMd5Obj() {

        //重命名
        resetName();

        $fnStep.each(function (index, el) {
            var _this = $(this),
                _id = _this.attr('id')

            if (!_this.is('form')) {
                _this = _this.find('form')
            }

            formMd5Obj[_id] = md5(_this.serialize())
        });

    }

    //--- MD5是否已经变化
    /**
     * [isFormMd5Changed 对比缓存表单md5值是否变化了]
     * @param  {string}  key        [表单标记，一般情况使用id]
     * @param  {string}  param      [序列化后的字符串]
     * @return {Boolean}            [true:改变了，false:未改变]
     */
    function isFormMd5Changed(key, param) {

        return (md5(param) != formMd5Obj[key]) ? true : false;

    }

    //--- 保存某个表单
    /**
     * [saveForm 保存表单，同时验证表单是否合法]
     * @param  {[type]}   key      [操作表单的标记]
     * @param  {Function} callback [保存表单后的回调]
     * @return {[type]}            [description]
     */
    function saveForm(key, callback) {

        var _$form = $('#' + key); //需要保存的表单
        var _isDiySaveKey = ''; // 自定义放回的键名

        if (!_$form.is('form')) {
            _isDiySaveKey = _$form.attr('diyajax')
            _$form = _$form.find('form')
        }

        //重命名
        resetName();

        if (_$form.valid()) {
            _$form.find('input[name="checkStatus"]').val('1');
        } else {
            _$form.find('input[name="checkStatus"]').val('0');
        }

        if (isFormMd5Changed(key, _$form.serialize())) {

            //--变化，保存
            if (!!_isDiySaveKey) {
                DIY_AJAX[_isDiySaveKey](callback);
            } else {
                saveFormAjax(key, _$form, callback);
            }

        } else {

            //--没有变化，直接回调
            if (callback) {
                callback();
            }

        }
    }

    //保存表单时的ajax请求
    /**
     * [saveFormAjax 保存表单时的ajax请求，并且通过formid判断是否是初次保存]
     * @param  {[type]}   key      [操作表单的标记]
     * @param  {[type]}   _$form   [操作表单的jQuery对象]
     * @param  {Function} callback [保存表单后的回调]
     * @return {[type]}            [description]
     */
    function saveFormAjax(key, _$form, callback) {

        var _isFirstsave = !!!_$form.find('[name="formId"]').val() ? true : false;

        util.ajax({
            url: _$form.attr('action'),
            data: _$form.serializeJCK(),
            success: function (res) {
                if (res.success) {
                    //如果是初次储存
                    if (_isFirstsave) {
                        window.location = '/projectMg/afterwardsCheck/editBaseInfo.htm?formId=' + res.form.formId + '&nextIndex=' + nextIndex;
                    }
                    // 更新 缓存md5
                    formMd5Obj[key] = md5(_$form.serialize());

                    if (callback) {
                        callback();
                    }
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });
    }

    //------ 关于表单储存 end

    var publicOPN = new(require('zyw/publicOPN'))();

    var hasRiskReport = $('#hasRiskReviewReport').val();
    if ('YES' == hasRiskReport) {
        publicOPN.addOPN([{
            name: '风险审核报告',
            url: '/projectMg/riskreview/viewReview.htm?formId=' + $('#amendRecordId').val(),
        }]);
    }
    var DOMcustomerName = document.getElementById('customName');
    var util = require('util');

    // 个人没有 风险检索、查看查看相似企业
    if ($('#customType').val() != 'PERSIONAL') {

        publicOPN.addOPN([{
            name: $('#customType').val() != 'PERSIONAL' ? '风险检索' : '',
            alias: 'someEvent',
            event: function () {
                util.risk2retrieve(DOMcustomerName.value, $('#customLicenseNo').val());
            }
        }, {
            name: $('#customType').val() != 'PERSIONAL' ? '查看相似企业' : '',
            alias: 'someEvent1',
            event: function () {

                initRiskQuery2.showXS();

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
    } else if (document.getElementById('fnEdit')) {
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
            name: '暂存',
            alias: 'save',
            event: function () {
                saveForm($step.find('li.active').attr('ftf'), function () {
                    Y.alert('提示', '已保存~');
                });
            }
        }, {
            name: '提交',
            alias: 'submit',
            event: function () {

                $('#submit').trigger('click');
            }
        }]);
    }
    publicOPN.init().doRender();

    $('#submit,#submitCustomer').on('click', function () {

        if (tabUpload()) {

            if (trValidS()) {

                $('body').find('.fnRemark').each(function () {

                    if ($(this).hasClass('hide')) {

                        $(this).find('textarea').rules('remove', 'required');
                    }
                })

                submitForm();
            }
        }
    });


    $('.fnTxbox').on("click", ".fnFillIn", function () {

        var $href = $(this).parents('.func').attr('href'),
            $detailName = $(this).parents('td').parents('tr').find('.fnName').val(),
            $detailType = $(this).parents('td').parents('tr').find('.fnType').val(),
            _loading = new util.loading();

        if ($detailName == '' || $detailType == '') {
            Y.alert('提示', '请先填写项目名称及类型');
        } else if ($detailName != '' && $detailType != '') {
            _loading.open();
            saveForm($step.find('li.active').attr('ftf'), function () {
                Y.alert('提示', '已保存~');
                _loading.close();
            });
            window.location.href = $href + "&projectName=" + $detailName + "&projectType=" + $detailType;
        }
    })


    //------ 下一步 start
    $('.fnNext').on('click', function () {

        if ($('#viewAudit').val()) {
            //$('#step').find('li').eq(1).trigger('click');

            clickFZ($('#step').find('li').eq(1), true)

        } else {
            // $('#xmjbqk').validate();


            //应该写到成功的会掉函数里面
            //$('#step').find('li').eq($(this).siblings('.step').val()).trigger('click');
            clickFZ($('#step').find('li').eq($(this).siblings('.step').val()), true)

            /*$('body,html').animate({
                scrollTop: 0
            }, 600);
            */
            }
    });
    //------ 下一步 end

    // 企业收入核实
    function qysr() {
        $('.fn-qysr').find('tr').each(function () {
            var numNo = 0,
                numYes = 0,
                tdChecked = 0;
            $(this).find('td').each(function () {
                var lastInput = $(this).parent('tr').children().children('.text:last');
                $(this).find('input[type="radio"]:checked').each(function () {
                    tdChecked += 1;
                    var abs = $(this).attr('value');
                    if (abs == "NO") {
                        numNo += 1;
                    } else if (abs == "YES") {
                        numYes += 1;
                    }
                })
                if (numYes == 3 || tdChecked == 0) {
                    lastInput.rules('remove', 'required');
                    lastInput.removeClass('fnInput');
                } else if (numNo != 0) {
                    $(this).parent('tr').children().children('.text').each(function () {
                        $(this).rules('add', {
                            required: true,
                            messages: {
                                required: '必填'
                            }
                        });
                        $(this).addClass('fnInput');
                    })
                }
            })
        })
    }

    qysr();

    $('.fn-qysr').on('click', '.radio,.fn-addline', function () {
        qysr();
    })

    // 预警信号
    function yjxh() {

        var _confirmBox = $('.fn-YN'),
            trLength = _confirmBox.find('tr').length - 1;

        _confirmBox.find('tr').each(function (index, el) {

            if (index < trLength) {

                $(this).find('td').each(function () {

                    var _$this = $(this),
                        _$text = _$this.parent('tr').find('.text'),
                        _checked = _$this.find('input[type="radio"]:checked')[0],
                        abs = _checked ? _checked.value : '';
                    // var abs = $(this).find('input[type="radio"]:checked').attr('value');

                    if (abs == 'YES') {
                        _$text.rules('add', {
                            required: true,
                            messages: {
                                required: '必填'
                            }
                        });
                        _$text.addClass('fnInput');
                    } else if (abs == 'NO') {
                        _$text.removeClass('fnInput');
                        _$text.rules('remove', 'required');
                    }
                })
            } else {
                $(this).find('input').rules('remove', 'required');
            }

        })

    }

    yjxh();

    $('.fn-YN .radio').click(function (event) {
        yjxh();
    })

    function sjdata() {
        if (!($(".fnCollected input[type='checkbox']").is(':checked'))) {
            Y.alert('提示', '请选择本次回访记录收集的资料！');
            return false;
        } else {
            return true;
        }
    }

    function tabUpload() {
        var _cwbb = $('#fnReport').find('.fnWrite input[type="checkbox"]')
        if ($('#importExcel').val() == 'YES' || 　_cwbb.hasClass('active')) {
            return true;
        } else {
            Y.alert('提示', '请导入数据');
            return false;
        }
    }

    function inputRadio($table) {
        $table.find('.fnInput').each(function () {
            $(this).rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })

        $table.find("input[type='radio']").each(function () {
            $(this).rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })
        })
    }

    // 文本框清除空格
    $("textarea").blur(function () {
        var thisVal = $.trim($(this).val());
        $(this).val(thisVal);
    });

    $('textarea:not(".diyMaxLength")').each(function (index, el) {
        var _this = $(this);
        //是否有值
        if (_this.attr('maxlength')) {
            _this.removeAttr("maxlength");
        }
    });

    // 自动触发监管内容点击事件
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return unescape(r[2]);
        return null;
    }

    if ($.getUrlParam('write') == 1 || $.getUrlParam('write') == 0) {
        $('#step').find('li').eq(1).trigger('click');
        $('html,body').animate({
            scrollTop: $('#pos').offset().top
        }, 5);
    } else {
        console.log('不跳转');
    }

    // 房地产
    function fnInUpAttach() {
        $('.fnInUpAttach').each(function () {
            var self = $(this),
                upVal = self.find('.fnUpAttachVal').val();
            if (upVal == '') {
                Y.alert('提示', '请上传营业税完税凭证和银行流水~');
                return false;
            } else {
                return true;
            }
        })
    }



    // 是否必填

    $('.fnWrite input').click(function () {
        var _self = $(this);
        fnWrite(_self);
    })

    function fnWrite(obj) {

        var _self = obj,
            _selfP = _self.parents('.fnWriteBox'),
            _selfPF = _selfP.find('.fnRemark');

        newHtml = '<div class="m-item fn-mt30 fnRemark" orderName="notCollectes">' + '<label class="m-label"><span class="m-required">*</span>未收集到原因：</label>' + '<textarea class="ui-textarea2 fnInput" name="remark" placeholder="请控制在1000字之内"></textarea>' + '</div>';

        if (_self.hasClass('active')) {

            _selfPF.hide().addClass('hide');

            _selfPF.find('textarea').val('').removeClass('fnInput');
            _self.removeClass('active');
            supervise();

            _selfP.find('input.fnInput,select.fnInput,textarea.fn-w500,textarea.text500,.radio').each(function (index, el) {
                var self = $(this);
                self.rules('add', {
                    required: true,
                    messages: {
                        required: '必填',
                    }
                });
                // self.blur();
            });

            _selfPF.find('textarea').rules('remove', 'required');
            _selfPF.find('textarea').siblings('b.error-tip').hide()

        } else {

            _self.addClass('active');

            if (_selfPF.attr('ordername')) {
                _selfPF.show().removeClass('hide');
                _selfPF.find('textarea').addClass('fnInput');
            } else {
                _self.parent().parent().after(newHtml);
            }

            supervise();

            _selfP.find('input.fnInput,select.fnInput,textarea.fn-w500,textarea.text500,.radio').each(function (index, el) {
                var self = $(this);
                self.rules('remove', 'required');
                self.valid();
                // self.siblings('b.error-tip').hide();
            });

            $('body').find('.fnRemark').each(function () {
                $(this).find('textarea.fnInput').rules('add', {
                    required: true,
                    maxlength: 1000,
                    messages: {
                        required: '必填',
                        maxlength: '已超出最大长度1000字'
                    }
                });
                // $(this).blur();
            })

        }
    }

    function supervise(orderName) {
        // ------ fnTableList resetName 0~N

        xlh($('div.fnRemark'));
    }

    supervise();

    xlh($('label.fnWrite'))


    function xlh(obj) {
        var _this = obj;
        $('body').find(_this).each(function (index) {

            var divs = $(this),
                _orderName = divs.attr('orderName'),
                index = index;

            divs.find('[name]').each(function () {
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

    $('body').find('.fnWrite').each(function (i) {
        var _self = $(this),
            _selfInput = _self.find('[type="checkbox"]'),
            _selfP = _self.parent().parent('div'),
            _val = _self.find($('[type="hidden"]')).val(),
            newHtml = '<div class="m-item fn-mt30 fnRemark" orderName="notCollectes">' + '<label class="m-label"><span class="m-required">*</span>未收集到原因：</label>' + '<textarea class="ui-textarea2 fnInput" name="remark" placeholder="请控制在1000字之内">' + _val + '</textarea>' + '</div>';

        _self.parent().after(newHtml);

        _selfP.find('.fnRemark').hide().addClass('hide');

        _selfP.find('.fnRemark textarea').removeClass('fnInput');

        if (_val != '') {

            _selfP.find('.fnRemark').show().removeClass('hide');
            _selfP.find('.fnRemark textarea').addClass('fnInput');

            if (document.getElementById('fnView')) {
                _selfP.find('.fnRemark textarea').attr('readonly', 'readonly');
                _selfP.find('.fnRemark .m-required').remove();

            }
        }

        xlh($('div.fnRemark'));

        // if (_selfInput.hasClass('active')) {
        if (_selfInput.prop('checked')) {

            _selfP.find('.fnInput,.radio').each(function (index, el) {
                var self = $(this);
                self.rules('remove', 'required');
            });

            _selfP.find('.fnRemark textarea').rules('add', {
                required: true,
                maxlength: 1000,
                messages: {
                    required: '必填',
                    maxlength: '已超出最大长度1000字'
                }
            });

        } else {
            _selfP.find('.fnRemark textarea').rules('remove', 'required');
        }

    })

    xlh($('div.fnRemark'));

    // 审核通用部分 start

    if (document.getElementById('auditForm')) {

        var auditProject = require('/js/tmbp/auditProject')
        var _auditProject = new auditProject('auditSubmitBtn')
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit({}).initPrint('打印的url')

        $step.find('.fnMakeNumber').each(function (index, el) {

            var $el = $(el)

            $el.after(el.value).remove()

        })

    }

    if (!$('#fnReport .onlyOne').length) {
        $('#fnReport span').eq(0).append('<span class="red onlyOne" style="font-size: 14px">请核对：总资产 、 净资产、 资产负债率、营业收入、利润总额是否填写</span>')
    }

    module.exports = {
        resetName: resetName
    }




});