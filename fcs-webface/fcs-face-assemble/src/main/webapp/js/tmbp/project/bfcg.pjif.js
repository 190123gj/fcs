define(function (require, exports, module) {
    // 项目管理 > 授信前管理 > 承销/发债信息维护
    require('Y-msg');
    require('zyw/upAttachModify');
    require('input.limit');
    require('Y-imageplayer');
    require('validate');
    require('Y-number');
    var util = require('util');
    var getList = require('zyw/getList');
    // 是否有发售记录
    if (document.getElementById('hasSalesRecord') && document.getElementById('hasSalesRecord').value) {
        document.getElementById('projectCodeBtn').classList = 'fn-hide';
    }

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init();

    // ------ 项目信息维护 start
    var ajaxUrl = (util.getParam('type') == '511' || $('#projectType').val() == '511') ? 'loadConsignmentSalesProjectData.json' : 'loadBondProjectData.json';
    // 选择项目
    var _getList = new getList();
    _getList.init({
        title: '项目列表',
        ajaxUrl: '/baseDataLoad/' + ajaxUrl,
        btn: '#projectCodeBtn',
        tpl: {
            tbody: [
                '{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td>{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: [
                '项目名称：',
                '<input class="ui-text fn-w160" type="text" name="projectName">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" type="text" name="customerName">',
                '<input type="hidden" name="" value="' + $('#fnMaintainType').val() + '">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: [
                '<th width="100">项目编号</th>',
                '<th width="120">客户名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            window.location.href = '/projectMg/projectIssueInformation/editProjectIssueInformation.htm?projectCode=' + $a.attr('projectCode');
        }
    });

    // 选择时间
    var FRISTTIME = $('#fnFirstTime').val() || $('#fnContractSignTime').val(),
        ENDTIME = $('#fnEndTime').val(),
        $issueDate = $('#issueDate'),
        $expireTime = $('#expireTime');

    $issueDate.on('click', function () {

        laydate({
            elem: '#issueDate',
            event: 'focus',
            // TODO 待还原
            // min: FRISTTIME,
            max: $expireTime.val()
        });

    });

    $expireTime.on('click', function () {

        if (!$issueDate.val()) {
            Y.alert('提示', '请先选择发行日期', function () {
                document.getElementById('issueDate').focus();
            });
            return;
        }


        ENDTIME = getExpireTimeEnd();


        laydate({
            elem: '#expireTime',
            event: 'focus',
          // TODO 待还原
           // min: $issueDate.val(),
            max: ENDTIME
        });

    });

    function getExpireTimeEnd() {

        var _issueDateArr = $issueDate.val().split('-'),
            _timeLimit = +document.getElementById('fnTimeLimit').value,
            _timeUnit = document.getElementById('fnTimeUnit').value;

        switch (_timeUnit) {
            case 'YEAR':
                _issueDateArr[0] = +_issueDateArr[0] + _timeLimit;
                break;
            case 'MONTH':
                // 月份从0开始计算，计算结束+1
                _issueDateArr[1] = +_issueDateArr[1] + _timeLimit - 1;
                _issueDateArr[0] = +_issueDateArr[0] + (parseInt(+_issueDateArr[1] / 12));
                _issueDateArr[1] = +_issueDateArr[1] % 12;
                _issueDateArr[1] = +_issueDateArr[1] + 1;
                break;
            case 'DAY':
                // 通过时间戳毫秒加减，转换一个新的时间
                var _thisDay = new Date((new Date(_issueDateArr.join('-'))).getTime() + _timeLimit * 24 * 60 * 60 * 1000);
                _issueDateArr[0] = _thisDay.getFullYear();
                _issueDateArr[1] = _thisDay.getMonth() + 1;
                _issueDateArr[2] = _thisDay.getDate();
                break;
        }

        return _issueDateArr.join('-');

    }

    // 是否继续发售
    var $isContinueToSell = $('#isContinueToSell'),
        $actualAmount = $('#actualAmount');


    $actualAmount.on('blur', function () {

        var DOM_surplusAmount = document.getElementById('surplusAmount')
        var SURPLUSAMOUNT = +DOM_surplusAmount.innerHTML.replace(/\,/g, '')
        var _thisVal = +(this.value || '').replace(/\,/g, '')

        if (_thisVal > SURPLUSAMOUNT) {
            this.value = DOM_surplusAmount.innerHTML
            $isContinueToSell.addClass('fn-hide').find('input').prop('disabled', 'disabled')
        }

        if (_thisVal < SURPLUSAMOUNT) {
            $isContinueToSell.removeClass('fn-hide').find('input').removeProp('disabled')
        }
    });

    var $form = $('#form'),
        requiredRules = {
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {

            // 是否分保项目

            if (!!$fnPlanIssueBox.length && !$fnPlanIssueBox.hasClass('fn-hide')) {

                // var _total = getAllReinsurance()

                // if (_total != 100) {

                // Y.alert('提示', '分保比例总和不等于100%')
                // return
                // }

                // 2017.01.10 原来是计算比例，改为计算金额
            	var fnAmountInput = document.getElementById('fnAmountInput');
            	if(fnAmountInput){
	                var _total = 0
	                $fnPlanIssueBox.find('.fnInputReinsuranceMoney').each(function (index, el) {
	                	var thisVal = el.value || '';
	                		thisVal = thisVal.indexOf(',') ? thisVal.replace(/,/g, '') : thisVal;
	                    _total = util.accAdd(_total, +thisVal)
	                })
	                
	                	fnAmountInput = fnAmountInput.value || '';
	                	fnAmountInput = fnAmountInput.indexOf(',') ? fnAmountInput.replace(/,/g, '') : fnAmountInput;
	                if (_total !== +fnAmountInput) {
	                    Y.alert('提示', '分保金额总和不等于监管机构批复金额')
	                    return
	                }
            	}
            }
            util.ajax({
                url: $form.attr('action'),
                data: $form.serializeJCK(),
                success: function (res) {
                    if (res.success) {
                        Y.alert('提示', '已提交', function () {
                            window.location.href = '/projectMg/projectIssueInformation/projectIssueInformationList.htm';
                        });
                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });
        }
    }));

    // 是否分保项目 start
    var $fnPlanIssueBox = $('#fnPlanIssueBox'),
        $fnPlanIssueList = $fnPlanIssueBox.find('#fnPlanIssueList'),
        $fnJGJGPFJE = $('.fnJGJGPFJE')

    $fnPlanIssueList.on('change', '.fnInputReinsurance', function () {

        var $this = $(this)

        setTimeout(function () {

            if (+$this.val() <= 0 && !!$this.val()) {
                $this.val('0.0001')
            }
            if (+$this.val() >= 100) {
                $this.val('99.9999')
            }

            var _total = getAllReinsurance($this[0]),
                _surplus = +util.accSub(100, _total)

            if (+$this.val() > _surplus) {
                $this.val(_surplus)
            }
            //console.log(+$this.val() *+$fnJGJGPFJE.val().replace(/\,/g, '') / 100 || '')

            var _r = _div(_mul(+$this.val(), +($fnJGJGPFJE.val() || '').replace(/\,/g, '') || ''), 100)

            $this.parents('tr').find('.fnInputReinsuranceMoney').val(_r).focus()
        }, 50)

    }).on('change', '.fnInputReinsuranceMoney', function () {

        var $this = $(this)

        setTimeout(function () {

            var _r = ((/\d+\.?\d{1,4}/).exec((+($this.val() || '').replace(/\,/g, '') / +($fnJGJGPFJE.val() || '').replace(/\,/g, '') * 100).toString()) || [''])[0]


            if (+_r<= 0 && !!$this.val()) {
                _r = '0.0001'
            }
            if (+_r >= 100) {
                _r = '99.9999'
            }

            $this.parents('tr').find('.fnInputReinsurance').val(+_r)
        }, 150)

    })

    function _mul(v1, v2) {
        ///<summary>精确计算乘法。语法：Math.mul(v1, v2)</summary>
        ///<param name="v1" type="number">操作数。</param>
        ///<param name="v2" type="number">操作数。</param>
        ///<returns type="number">计算结果。</returns>
        var m = 0;
        var s1 = v1.toString();
        var s2 = v2.toString();
        try
        {
            m += s1.split(".")[1].length;
        }
        catch (e)
        {
        }
        try
        {
            m += s2.split(".")[1].length;
        }
        catch (e)
        {
        }

        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }
    function _div(v1, v2) {
        ///<summary>精确计算除法。语法：Math.div(v1, v2)</summary>
        ///<param name="v1" type="number">操作数。</param>
        ///<param name="v2" type="number">操作数。</param>
        ///<returns type="number">计算结果。</returns>
        var t1 = 0;
        var t2 = 0;
        var r1, r2;
        try
        {
            t1 = v1.toString().split(".")[1].length;
        }
        catch (e)
        {
        }
        try
        {
            t2 = v2.toString().split(".")[1].length;
        }
        catch (e)
        {
        }

        with (Math)
        {
            r1 = Number(v1.toString().replace(".", ""));
            r2 = Number(v2.toString().replace(".", ""));
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }





    function getAllReinsurance(ignore) {

        var _total = 0

        $fnPlanIssueBox.find('.fnInputReinsurance').each(function (index, el) {

            if (!!!ignore || ignore != el) {
                _total = util.accAdd(_total, +el.value)
            }

        })

        return _total

    }

    function setPlanInputMust() {

        util.resetName()

        $fnPlanIssueList.find('input.fnPlanIssueMust').each(function (index, el) {
            $(this).rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            })
        })

    }

    function setPlanInputNotMust() {

        $fnPlanIssueList.find('input.fnPlanIssueMust').each(function (index, el) {
            var $this = $(this)
            $this.rules('remove', 'required')
            $this.valid()
        })

        util.resetName()

    }

    function setPlanIssue(show) {

        if (show) {

            $fnPlanIssueBox.removeClass('fn-hide').find('input').each(function (index, el) {
                $(this).removeProp('disabled')
            });

            setPlanInputMust()

        } else {

            $fnPlanIssueBox.addClass('fn-hide').find('input').each(function (index, el) {
                $(this).prop('disabled', 'disabled')
            });

            setPlanInputNotMust()

        }

    }

    $fnPlanIssueList.on('click', '.fnDelTr.del ', function () {

        $(this).parents('tr').remove()

    })

    $('#fnPlanIssueAdd').on('click', function () {

        $fnPlanIssueList.append(document.getElementById('fnPlanIssueTpl').innerHTML)

        setPlanInputMust()

    })

    $('.fnPlanIssueRadio').on('click', function () {
        setPlanIssue(this.value === 'IS')
    })

    setPlanIssue($('.fnPlanIssueRadio:checked').val() === 'IS')

    // 是否分保项目 end

    // $form.submit(function(event) {

    // var _isPass = true,
    // _isPassEq;
    // $('.fnInput').each(function(index, el) {
    // if (!!!el.value.replace(/\s/g, '')) {
    // _isPass = false;
    // _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
    // }
    // });
    // if (!_isPass) {
    // Y.alert('提醒', '请填写完必填项', function() {
    // $('.fnInput').eq(_isPassEq).focus();
    // });
    // return false;
    // }

    // util.ajax({
    // url: $form.attr('action'),
    // data: $form.serialize(),
    // success: function(res) {
    // if (res.success) {
    // Y.alert('提示', '已提交', function() {
    // window.location.href =
	// '/projectMg/projectIssueInformation/projectIssueInformationList.htm';
    // });
    // } else {
    // Y.alert('提示', res.message);
    // }
    // }
    // });

    // return false;

    // });

    // ------ 项目信息维护 end
    //
    //
    // ------ 查看 start
    $('.fnView').on('click', function () {

        var _imgs = [];

        $('.fnView').each(function (index, el) {
            var _this = $(this);
            _imgs.push({
                src: _this.attr('src'),
                desc: _this.text()
            });
        });

        Y.create('ImagePlayer', {
            imgs: _imgs,
            index: $(this).index('.fnView'),
            scaleLowerLimit: 0.1,
            loop: false,
            fullAble: false,
            firstTip: '这是第一张了',
            firstTip: '这是最后一张了'
        }).show();

    });
    // ------ 查看 end

    // ------ 监管机构批复金额 start

    var FIRST = true

    $('#fnAmountInput').on('blur', function () {

        var self = this;

        // if (+self.value >= +document.getElementById('amount').value) {

        // self.value = document.getElementById('amount').value;

        // }

        setTimeout(function () {
            document.getElementById('surplusAmount').innerHTML = self.value;
            $actualAmount.trigger('blur');

            // var PF_MONEY =
			// +document.getElementById('amount').value.replace(/\,/, '') //
			// 批复金额

            // 比例 批复金额/监管机构金额
            // var _r = ((/\d+\.?\d{1,2}/).exec(((PF_MONEY / + self.value) *
			// 100).toString()) || [''])[0]

            // document.getElementById('fnJCKRatio').setAttribute('maxnumber',
			// _r)

            // document.getElementById('fnAmountInputCapital').innerHTML =
			// Y.Number(+(self.value || '').replace(/\,/g, '')).digitUppercase()

        }, 0);


        // $fnPlanIssueList.find('.fnInputReinsurance').each(function (index, el) {
        //     if($(this).val()>0)
        //         $(this).trigger('blur')
        // });

    });

    $('#fnAmountInput').on('change',function () {
        $fnPlanIssueList.find('.fnInputReinsurance').each(function (index, el) {
            if($(this).val()>0)
                $(this).trigger('change')
        });
    })

    // ------ 监管机构批复金额 end


});