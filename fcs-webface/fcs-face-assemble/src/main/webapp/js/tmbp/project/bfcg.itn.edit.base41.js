define(function (require, exports, module) {
    //审计是否
    $('input[type="radio"]').change(function (event) {
        var _this = $(this),
            _bool = (_this.index() == 0),
            _obj = _this.parent().nextAll('td');
        _bool ? _obj.find('input').removeAttr('readonly').end()
            .find('select').removeAttr('disabled') : _obj.find('input').attr('readonly', true).val('').end()
            .find('select').attr('disabled', true)
            .find('option:first').attr('selected', 'selected').change();
        _bool ? _obj.find('input,select').each(function (index, el) {
                $(this).removeClass('cancel');
            }) :
            _obj.find('input,select').each(function (index, el) {
                $(this).addClass('cancel').next('.error-tip').hide();
            });
        //console.log(requiredSpecial)
    });
    $('input[type="radio"]:checked').trigger('change');
    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    //必填集合
    require('zyw/requiredRules');
    //js引擎
    var template = require('arttemplate');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //页面提交后跳转处理
    var showResult = require('zyw/process.result');
    //上传
    require('zyw/upAttachModify');
    //上传证件照片 start
    var _balance = $('#balance'), //资产负债表
        _cashFlow = $('#cashFlow'), //现金流量表
        _cfAnalyze = $('#cfAnalyze'), //现金流分析
        _dpaAnalyze = $('#dpaAnalyze'), //偿债能力分析
        _ecAnalyze = $('#ecAnalyze'), //盈利能力分析
        _ocAnalyze = $('#ocAnalyze'), //运营能力分析
        _profit = $('#profit'),
        _years = $('#years'), //重大科目异常变动提醒科目时间
        _sum = []; //利润及利润分配表
    //验证共用
    var _form = $('#form'),
        ValidataCommon = require('zyw/project/bfcg.itn.ValidataCommon'),
        _allWhetherNull = 'auditSuggestExplain,debtPayingAbility,operatingAbility,profitAbility,cashFlowExplain,assetQualityReview,kpiUnit0,kpiUnit1,kpiUnit2,kpiUnit3,auditValue,auditRemark',
        requiredRules1 = _form.requiredRulesSharp(_allWhetherNull, false, {}, function (rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        requiredRulesMaxlength = _form.requiredRulesSharp('wu', false, {}, function (rules, name) {
            rules[name] = {
                maxlength: 50,
                messages: {
                    maxlength: '已超出50字'
                }
            };
        }),
        requiredRules3 = _form.requiredRulesSharp('wu', false, {}, function (rules, name) {
            rules[name] = {
                maxlength: 10000,
                messages: {
                    maxlength: '已超出10000字'
                }
            };
        }),
        rulesAllBefore = { //所有格式验证的基
            itemName: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            itemDate: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
            itemValue: {
                required: true,
                messages: {
                    required: '必填'
                }
            },
        },
        rulesAll = $.extend(true, requiredRulesMaxlength, rulesAllBefore, requiredRules1, requiredRules3);


    // 保存并返回
    var ONLY_VALUE = 0

    var NEW_FUNC = 0
    var _url = ''

    $('#saveSubmit').click(function () {
        ONLY_VALUE = 1;
    })
    $('body').on('click', '#newfn a', function () {
        NEW_FUNC = 1
        _url = $(this).attr('data-href')
    })

    ValidataCommon(rulesAll, _form, _allWhetherNull, false, function (res) {
        if (NEW_FUNC && res.success) {
            window.location.href = _url;
        } else {
            if(ONLY_VALUE === 1){
                showResult(res, hintPopup(res.message,$("#backUrl").attr('data-url')));
            } else {
                showResult(res, hintPopup);
            }
        }
    }, false, function () {
        if ($('[name="importExcel"]').val() != 'YES') {
            //console.log(!_years.attr('toLead'))
            return false;
        } else {
            return true;
        }
        // return true;
    }, function () {

        var auditInfosId = $('#auditInfosId').find('input[type="radio"]:checked'),
            judge = false,
            cashFlow = $('#cashFlow').find('input[type="text"]'),
            content = false;

        auditInfosId.each(function (index, el) {

            var $el = $(el);

            if ($el.val() == 'Y') {

                judge = true;

                return false

            }

        });

        cashFlow.each(function (index, el) {

            var $el = $(el),
                val = $el.val();

            if (val != '0' && val != '0.00' && val != 0 && val != 0.00 && val != '' && val != null && val != '——') {
                //console.log(val, val == '——', val != '0' || val != '0.00' || val != 0 || val != 0.00 || val != '' || val != null || val != '——');
                content = true;

                return false;

            }

        });

        if (judge && !content) {

            return false;

        } else {

            return true;

        }



    });


    //上传
    require('Y-htmluploadify');

    function initHtmluploadfor(_obj) {
        _obj.find('tbody tr').each(function (index, el) {
            var _this = $(el);
            _sum.push(_this.find('td').eq(0).text().replace(/^([一二三四五六七八九十]*\、|[加减]\：)/, ''));
        });
    }

    initHtmluploadfor(_balance);
    initHtmluploadfor(_profit);
    initHtmluploadfor(_cashFlow);

    for (var a = 0; a < _sum.length; a++) {
        for (var b = 0; b < $('.dimInquire').length; b++) {
            if (_sum[a] == $('.dimInquire').eq(b).val()) {
                $('.dimInquire').eq(b).attr('valY', a);
            }
        }
    }

    function htmluploadfor(_x, x, _data, bool) {
        for (var i = 0; i < _data[x].length; i++) {
            if(_x.find('tr').length < i + 1){
                _x.find('tr').eq(i -1).clone().insertAfter(_x.find('tr').eq( i - 1));
                _x.find('tr').eq(i).find('input').each(function () {
                    $(this).attr('name',$(this).attr('name').replace(i-2,i));
                });
            }
            _x.find('tr').eq(i).removeAttr('class').addClass(_data[x][i][0]);
            for (var j = 1; j < _data[x][i].length; j++) {
                var _obj = _x.find('tr').eq(i).find('th,td').eq(j - 1),
                    _val = _data[x][i][j];
                if (j > 5 && (x == 'balance' || x == 'profit' || x == 'cashFlow')) {
                    _val ? _x.find('tr').eq(i).find('th,td').eq(j - 5).append('<em class="remind">' + _val + '</em>') : _x.find('tr').eq(i).find('th,td').eq(j - 5).find('em').remove();
                } else {
                   (i == 0) ? _obj.text(_val).next().val(_val): ((j == 1) ? _obj.text(_val).next().val(_val) : _obj.find('input').val(_val));
                }
                if (j == 1 && i > 0 && bool) {
                    _sum.push(_val.replace(/^([一二三四五六七八九十]*\、|[加减]\：)/, ''))
                }
            }
        }
    }

    $("#dpaAnalyze input[type='text'],#ocAnalyze input[type='text'],#ecAnalyze input[type='text'],#cfAnalyze input[type='text']").each(function () {
        $(this).addClass('fnMakeMoney')
        $(this).trigger('focus')
        $(this).trigger('blur')
    })

    $("#cfAnalyze input[type='text']").each(function () {
        $(this).addClass('fnMakeMoney fnMakeMicrometer')
        $(this).trigger('focus')
        $(this).trigger('blur')
    })


    $('.fnUpFile').click(function () {
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/projectMg/investigation/uploadFinancialExcel.htm?type=' + $('[name="type"]').val()+'&subIndex=1',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls',
            fileObjName: 'UploadFile',
            onQueueComplete: function (a, b) {},
            onUploadSuccess: function ($this, data, resfile) {
                var _resfile = JSON.parse(resfile)
                //console.log(_resfile.success)
                if(!_resfile.success){
                    Y.alert('',_resfile.message,function(){});
                }else{
                    _sum = [];
                    var _data = JSON.parse(data).datas;

                    //资产负债表
                    htmluploadfor(_balance, 'balance', _data, true);

                    //利润及利润分配表
                    htmluploadfor(_profit, 'profit', _data, true);

                    //现金流量表
                    htmluploadfor(_cashFlow, 'cashFlow', _data, true);


                    //现金流分析
                    //htmluploadfor(_cfAnalyze, 'cfAnalyze', _data, false);

                    //偿债能力分析
                    //htmluploadfor(_dpaAnalyze, 'dpaAnalyze', _data, false);

                    //盈利能力分析
                    //htmluploadfor(_ecAnalyze, 'ecAnalyze', _data, false);

                    //运营能力分析
                    //htmluploadfor(_ocAnalyze, 'ocAnalyze', _data, false);

                    var $allTable = $('#allTable')

                    // 过滤已丢失、修改的数据
                    _years.find('tr').each(function (index, el) {

                        var $tr = $(el)
                        var _itemName = $tr.find('.dimInquire').val()

                        // 没有值 删了
                        if (!!!_itemName) {
                            $tr.remove()
                            return true
                        }
                        // 是否还在
                        if ($.inArray(_itemName, _sum) == -1) {
                            $tr.remove()
                            return true
                        }

                        var $vInput = $allTable.find('input[value="' + _itemName + '"]')
                        var $vTable = $vInput.parents('table')
                        var vIsYears = false // 是否年份改变
                        var vIsYearsIndex = 0 // 年份的位置
                        var thisYears = $tr.find('.fillInquire').val()

                        $vTable.find('thead th').each(function (index, el) {

                            if (el.innerHTML.replace(/\s/g, '') == thisYears) {
                                vIsYears = true
                                vIsYearsIndex = index
                                return false
                            }

                        })

                        // 是否年份还存在
                        if (!vIsYears) {
                            $tr.remove()
                            return true
                        }

                        var vMoney = $vInput.parents('tr').find('td').eq(vIsYearsIndex).find('input').val()

                        // 是否金额一致
                        if (vMoney != $tr.find('.kpiValue ').val()) {
                            $tr.remove()
                            return true
                        }


                    })

                    //重大科目异常变动提醒科目时间
                    var _option = '<option value="">请选择</option>';
                    for (var i = 0; i < _data.years.length; i++) {
                        _option += '<option value="' + _data.years[i] + '">' + _data.years[i] + '</option>'
                    }
                    _years.find('select').each(function (index, el) {

                        var _val = _this.val()

                        var _this = $(el);
                        _this.html(_option);

                        // 年份更新，还原当前年份
                        if (!!_val) {
                            _this.find('option[value="' + _val + '"]').prop('selected', 'selected')
                        }

                    });
                    // console.log($('#t-years').text().replace(/\<select[\S\s]+\/select>/, '<select class="text fillInquire" name="itemDate">' + _option + '</select>'))
                    $('#t-years').text($('#t-years').text().replace(/\<select[\S\s]+\/select>/, '<select class="text fillInquire" name="itemDate">' + _option + '</select>'));
                    //重大科目异常变动提醒全部重置
                    // _years.find('td').each(function (index, el) {
                    //     var _this = $(this);
                    //     _this.find('select option:first').attr('selected', 'selected').end().find('input').val('');
                    // });
                    //审计时间
                    $('[orderName="auditInfos"] input.termType').each(function (index, el) {
                        $(el).val(_data['balance'][0][index + 2])
                    });

                    _years.attr('toLead', 'Y');

                    $('[name="importExcel"]').val('YES');

                }


                }
                //renderTo: 'body'
        });
        // htmlupload.show();
    });

    // var  _re  = /^[1-9]d*.d*|0.d*[1-9]d*$/,
    //     _tet = '11131,.221';
    //             console.log(_tet.replace(_re,''))

    var fnMakeUE_name_num = 0;

    $('body').on('click', '.popup', popup);

    function popup() {
        var _this = $(this),
            _parents = _this.parents('tr'),
            _rental = _parents.find('.kpiValue').val(),
            _rentalNum = parseFloat(_rental.replace(/,/g, '')),
            _popupListCode = _parents.find('.popupListCode'),
            _popupExplainationCode = _parents.find('.popupExplainationCode'),
            _popupExplainationCodeVal = _popupExplainationCode.val().replace(/[\r\n]/g, "@lineFeed&"),
            _post = eval("(" + "{kpiExplain:" + _popupListCode.val() + "}" + ")");
        // _post = eval("(" + "{explaination:" + _popupExplainationCodeVal + "}" + ")");
        // .replace(/\@lineFeed\&/g, "\n")
        // console.log(_popupExplainationCodeVal);

        _post.explaination = _popupExplainationCodeVal


        if (_rental == 0 || _rental == '0' || _rental == '') return false;

        _years.attr('scrollTop', $('body').scrollTop());

        //弹窗
        $('body').Y('Window', {
            content: template('newPopupScript', _post),
            modal: true,
            key: 'modalwnd',
            simple: true
        });
        var modalwnd = Y.getCmp('modalwnd');
        modalwnd.bind(modalwnd.wnd.find('.close'), 'click', function () {
            modalwnd.close();
        });
        // console.log($_GLOBAL.setUE);
        var fnMakeUE_dom = modalwnd.wnd.find('.fnMakeUE');
        var fnMakeUE_name = fnMakeUE_dom.attr('name');

        fnMakeUE_dom.attr('name', fnMakeUE_name + fnMakeUE_name_num);
        fnMakeUE_name_num++;

        $_GLOBAL.setUE(modalwnd.wnd)

        //验证规则
        var _formPopup = $('#formPopup'),
            requiredRules4 = _formPopup.requiredRulesSharp('wu', false, {}, function (rules, name) {
                rules[name] = {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                };
            }),
            rulesAllBefore = { //所有格式验证的基
                explainName: {
                    maxlength: 50,
                    messages: {
                        maxlength: '已超出50字'
                    }
                },
                explainValue: {
                    number: true,
                    min: 0,
                    // max: _rentalNum,
                    messages: {
                        min: '请输入正确的数值',
                        // max: '大于总额',
                        number: '请输入正确的格式'
                    } //必填.正整数
                },
                explainRate: {
                    number: true,
                    messages: {
                        number: '请输入正确的格式'
                    } //必填.正整数
                },
                // explaination: {
                //     maxlength: 1000,
                //     messages: {
                //         maxlength: '已超出1000字'
                //     }
                // }
            },
            rulesAll = $.extend(true, requiredRules4, rulesAllBefore);

        _formPopup.validate({
            errorClass: 'error-tip',
            errorElement: 'p',
            errorPlacement: function (error, element) {
                element.after(error);
            },
            onkeyup: true,
            ignore: '.cancel',
            submitHandler: function (form) {

                // _popupListCode_popupExplainationCode
                var _popupListCodeArr = new Array();
                modalwnd.wnd.find('#test tr').each(function (index, el) {

                    var $el, $explainName, $explainValue, $explainRate;

                    $el = $(el);
                    $explainName = $el.find('.explainName1');
                    $explainValue = $el.find('.explainValue1');
                    $explainRate = $el.find('.explainRate1');

                    _popupListCodeArr.push({
                        explainName: $explainName.val(),
                        explainValue: $explainValue.val(),
                        explainRate: $explainRate.val()
                    })

                });

                _popupListCode.val(JSON.stringify(_popupListCodeArr));
                _popupExplainationCode.val(modalwnd.wnd.find('.fnMakeUE').val());

                modalwnd.close();

            }

        });

        //动态添加验证规则初始化
        $('.fnAddLineNew').addValidataCommon(rulesAll, true)
            .fnDelLineFun()
            .initAllOrderValidata()
            .popupGroupAddValidata();

        //提交
        $('#formPopupSubmit').click(function (event) {
            var _countRental = 0;
            $('#formPopup .explainName').each(function (index, el) {
                _countRental += parseFloat($(this).val());
            });

            // if (_countRental.toFixed(2) > _rentalNum) {
            //     hintPopup('超出总额');
            //     return false;
            // } else if (_countRental.toFixed(2) < _rentalNum) {
            //     hintPopup('小于总额');
            //     return false;
            // }
            $('#formPopup').submit()
        });

        //比率
        $('body').on('keyup', '.explainName', function () {
            var _this = $(this);
            _this.parents('tbody').find('.explainValue').each(function (index, el) {
                var _this = $(this),
                    _import = parseFloat(_this.parent().prev().find('input.explainName').val().replace(/\,/g, '')) || 0,
                    _percent = (_import * 100 / _rentalNum).toFixed(2)
                _this.val(_percent)
            });
        })

    }



    //收起
    $('.packBtn').click(function (event) {
        var _this = $(this);
        _this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
        _this.parent().next().slideToggle();
    });

    //模糊查询
    $('body').on('keyup change', '.dimInquire', function (event) {

        var _this = $(this),
            _parents = _this.parents('tr'),
            _val = _this.val().replace(/(\(|\))/g, '\\$1'),
            _html = '';

        var _re = new RegExp(_val),
            _re2 = new RegExp('^' + _val + '$');
        if (!_val) return;
        _this.nextAll('.dimInquireUl').remove();
        for (var i = 0; i < _sum.length; i++) {
            if (_re.test(_sum[i])) {
                _html += '<li valY="' + i + '">' + _sum[i] + '</li>';
            }
            if (_re2.test(_sum[i])) {
                var _valX = _parents.find('.fillInquire option:selected').index();
                _this.nextAll('.dimInquireUl').remove();
                _this.attr('valY', i);
                kpiValue(_this, _valX, i);
                return false;
            } else {
                _parents.find('.kpiValue').val(0);
            }
        }
        if (_html == '') _html = '输入科目不存在';
        _html = '<div class="dimInquireUl"><ul>' + _html + '</ul></div>';
        _this.after($(_html));

    }).on('click', '.dimInquireUl ul li', function () { //获取金额

        var _this = $(this),
            _valX = _this.parents('tr').find('.fillInquire option:selected').index(),
            _valY = _this.attr('valY');
        _this.parents('.dimInquireUl').hide()
            .prev('.dimInquire').val(_this.text())
            .attr('valY', _this.attr('valY'));
        kpiValue(_this, _valX, _valY);

    }).on('click', function (event) {
        $('.dimInquireUl').remove();
    });

    $('body').on('change', '.fillInquire', function () {
        var _this = $(this),
            _valX = _this.find('option:selected').index(),
            _valY = _this.parents('tr').find('.dimInquire').attr('valY');
        kpiValue(_this, _valX, _valY);
    })

    function kpiValue(_this, _valX, _valY) {
        var _val = $('#allTable').find('tbody tr').eq(_valY).find('td').eq(_valX).find('input').val(),
            _val = _val ? _val : 0;
        _this.parents('tr').find('.kpiValue').val(_val).blur();
    }


    if (/\&scrollTop=(.+)(?=\&{0,1})/g.exec(window.location.href)) {

        var _scrollTop = /\&scrollTop=(.+)(?=\&{0,1})/g.exec(window.location.href)[1];

    }

    if (_scrollTop) {

        $('body,html').scrollTop(_scrollTop)

    }


    $('#auditInfosId .radio').change(function () {
        $(this).siblings('p.error-tip').hide();
    })

    require('tmbp/project/bfcg.itn.newfn')

});