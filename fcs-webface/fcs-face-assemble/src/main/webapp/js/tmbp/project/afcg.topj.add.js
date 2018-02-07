define(function (require, exports, module) {
    // 项目管理 > 授信后管理  > 到期解保

    var COMMON = require('./bfcg.contract.common');
    require('Y-msg');
    require('input.limit');
    require('validate');
    //通用 上传附件、图片
    require('zyw/upAttachModify');
    //模板引擎
    var getList = require('zyw/getList');
    var util = require('util');

    // var minTime = !!$('#minTime').val() ? $('#minTime').val() : false;
    var minTime = false;
    var maxTime = formatDate();
    var limitTime = minTime ? "laydate({min:'" + minTime + "',max:'" + maxTime + "'})" : "laydate({max:'" + maxTime + "'})";
    $('.rebackMoneyItem').find('.laydate-icon').attr('onclick', limitTime);

    $('body').on('click', '.rebackMoneyItemBtn .optBtn', function () { //增加删除
        var _this = $(this);
        var _thisTr = _this.parents('.rebackMoneyItem');
        var isAddItem = _this.hasClass('toAdd');
        var projectCode = _thisTr.find('[name*=projectCode]').val();
        var $sameCodeTr = _thisTr.siblings('.rebackMoneyItem');

        if (isAddItem) {
            var $newItem = $($('#RETURN_MONEY').html());
            $newItem.find('input').val('').end()
                .find('.laydate-icon').attr('onclick', limitTime).end()
                .find('.optBtn').removeAttr('style');
            if ($sameCodeTr.length == 0) _this.siblings('a').show();
            _this.hide();
            _thisTr.after($newItem);
        } else {
            if ($sameCodeTr.length <= 1) $sameCodeTr.find('.toDelete').hide();
            $sameCodeTr.last().find('.toAdd').show();
            _thisTr.remove();
        };
        orderNameList($('.decodeAcrrod'));
        $sameCodeTr.eq(0).find('.rebackMoneyInput').blur();
    }).on('click', '.fnChooseProCode', function () {
        Y.alert('提示', '请先选择项目编号！');
    }).on('click', '.isRetrunMoneyBtn', function () { //选择是否有还款项
        var _this = $(this);
        var isChecked = _this.prop('checked');
        var $decodeAcrrod = $('.decodeAcrrod');

        if (isChecked) {
            var $newItemss = $($('#RETURN_MONEY').html());
            var $relieveAmount = $decodeAcrrod.removeClass('fn-hide').find('.relieveAmount')
            $newItemss.find('.ignore').removeClass('ignore').end()
                .find('.laydate-icon').attr('onclick', limitTime).end()
                .find('.relieveAmount').removeAttr('disabled');
            $relieveAmount.val('').attr('name', $relieveAmount.attr('tempName'));
            $decodeAcrrod.find('.decodeAcrrodItems').html($newItemss);
        } else {
            $decodeAcrrod.addClass('fn-hide').find('.decodeAcrrodItems').html('');
            $decodeAcrrod.find('.relieveAmount').val('').addClass('ignore').attr('disabled', 'disabled').removeAttr('name');
        }
        orderNameList($('.decodeAcrrod'))
    }).on('blur', '.rebackMoneyInput', function () { //还款金额失焦，计算本次申请解保金额
        var _this = $(this);
        var resultVal = 0;
        _this.parents('.decodeAcrrodItems').find('.rebackMoneyInput').each(function (i, e) {
            var _thisInner = $(this);
            var _thisInnerVal = !!_thisInner.val() ? parseFloat(_thisInner.val()) : 0;
            resultVal += _thisInnerVal;
        });
        resultVal = !!resultVal ? resultVal : 0;
        _this.parents('.decodeAcrrod').find('.relieveAmount').val(resultVal.toFixed(2)).trigger('blur')
    })
    //选择项目
    var _getList = new getList();
    _getList.init({
        width: '80%',
        title: '项目列表',
        ajaxUrl: '/projectMg/counterGuarantee/queryProjects.json?isAdd=YES&select=my',
        btn: '#choose',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item">{{item.projectCode}}</td>',
                '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
                '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
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
                '<th width="100">授信金额(元)</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function ($a) {
            window.location.href = '/projectMg/counterGuarantee/edit.htm?projectCode=' + $a.attr('projectCode');
        }
    });
    //页面加载好后 resetName
    util.resetName();

    var $form = $('#form'),
        requiredRules = {
            ignore: '.ignore',
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $form.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages,
        submitHandler: function () {
            var $relieveAmount = $('.relieveAmount');
            if (!!$relieveAmount.length && !!$('.fnChooseGist').length) {

                var num = 0;
                var relieveAmountNum = (!!$relieveAmount.val() && parseFloat($relieveAmount.val() || 0) > 0) ? true : false;
                var FDB_TABEL_LENGTH = $('#FDB_TABEL').find('tbody tr input[type="radio"]:checked').length > 0 ? true : false;
                $('.oneInTwo').each(function (inde, ele) {
                    num += $(this).children().length;
                });

                if (num == 0 && !relieveAmountNum) {
                    Y.alert('提示', '还款和解保依据至少选择一项！',function () {
                        $('[name="isRetrunMoney"]').focus();
                    });
                    return;
                };
                if (!relieveAmountNum && !FDB_TABEL_LENGTH) {
                    Y.alert('提示', '解保内容需完善其中一项：还款金额或者反担保措施！');
                    return;
                }

            }

            // alert('ok')
            // return;
            util.ajax({
                url: $form.attr('action'),
                data: $form.serialize(),
                success: function (res) {
                    if (res.success) {
                       util.ajax({
                           url: '/projectMg/form/submit.htm',
                           data: {
                               formId: res.form.formId
                           },
                           success: function (res2) {
                               if (res2.success) {
                                   Y.alert('提示', res2.message, function () {
                                       window.location = '/projectMg/counterGuarantee/list.htm';
                                   });
                               } else {
                                   Y.alert('提示', res2.message);
                               }
                           }
                       });
                        // util.postAudit({
                        //     formId: res.form.formId
                        // }, function () {
                        //     window.location.href = '/projectMg/counterGuarantee/list.htm';
                        // })

                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });

        }
    }));

    // 拟解除的反担保措施
    function toggleCounter($isCounter) {

        if ($isCounter.prop('checked')) {

            $isCounter.parents('tr').find('.fnNeed').each(function (index, el) {

                $(this).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })

            });

        } else {

            $isCounter.parents('tr').find('.fnNeed').each(function (index, el) {

                var $this = $(this)

                $this.rules('remove', 'required')
                $this.valid()

            });

        }

    }

    $('.isRemove').on('click', function () {

        toggleCounter($(this))

    }).each(function (index, el) {
        toggleCounter($(this))
    });

    // 提交 start
    // $form.submit(function(event) {
    //  $fnInput = $('.fnInput');
    //  var _isPass = true,
    //      _isPassEq;
    //  $fnInput.each(function(index, el) {
    //      if (!!!el.value.replace(/\s/g, '')) {
    //          _isPass = false;
    //          _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
    //      }
    //  });

    //  if (!_isPass) {
    //      var $thisError = $fnInput.eq(_isPassEq),
    //          msgs = '请输入必填项!';
    //      if ($thisError.hasClass('fnUpAttachVal')) {
    //          msgs = '请上传必要附件'
    //      };
    //      Y.alert('提示', msgs, function() {
    //          $thisError.val('').focus();
    //      });
    //      return false;
    //  }

    //  var _isRemove = true,
    //      _isRemoveEq,
    //      $isRemoveInput = $('.isRemoveInput');

    //  $isRemoveInput.each(function(index, el) {
    //      if (!!!el.value.replace(/\s/g, '')) {
    //          _isRemove = false;
    //          _isRemoveEq = (_isRemoveEq >= 0) ? _isRemoveEq : index;
    //      }
    //  });

    //  if (!_isRemove) {
    //      Y.alert('提示', '还有未填写的解除依据或原因', function() {
    //          $isRemoveInput.eq(_isRemoveEq).focus();
    //      });
    //      return false;
    //  }

    //  util.ajax({
    //      url: $form.attr('action'),
    //      data: $form.serialize(),
    //      success: function(res) {
    //          if (res.success) {
    //              util.ajax({
    //                  url: '/projectMg/form/submit.htm',
    //                  data: {
    //                      formId: res.form.formId
    //                  },
    //                  success: function(res2) {
    //                      Y.alert('提示', res2.message, function() {
    //                          if (res2.success) {
    //                              window.location = '/projectMg/counterGuarantee/list.htm';
    //                          }
    //                      });
    //                  }
    //              });

    //          } else {
    //              Y.alert('提示', res.message);
    //          }
    //      }
    //  });

    //  return false;
    // });

    function orderNameList(ele) {
        var $box = !!ele ? ele : $('body');
        $box.find('[orderName]').find('.orderNameItem').each(function (index1, ele1) {
            var $this = $(this);
            var orderName = $this.parents('[orderName]').attr('orderName');
            $this.find('[name]').each(function (index2, ele2) {
                var _this = $(this);
                var name = _this.attr('name');
                name = name.indexOf(orderName) >= 0 ? name.split('.')[1] : name;
                var newName = orderName + '[' + index1 + '].' + name;
                _this.attr('name', newName);
            })
        });
        dynamicRemoveRules(ele);
        dynamAddRules(ele);

    };

    function dynamAddRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fnInput');
        $nameList.each(function (i, e) {
            var _this = $(this);
            if (_this.hasClass('validateOneInTwo')) {
                _this.rules('add', {
                    oneInTwo: true,
                    messages: {
                        oneInTwo: '还款和依据必选其一！'
                    }
                });
            } else if (_this.hasClass('relieveAmount')) {
                _this.rules('add', {
                    required: true,
                    min: 0.0000000001,
                    max: parseFloat($('#availableReleaseAmount').val()) + 0.0000000001,
                    messages: {
                        required: '必填',
                        min: '必须大于0',
                        max: '不能超过最大解保金额：' + parseFloat($('#availableReleaseAmount').val()) + '元',
                    }
                });
            } else {
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
        var $nameList = ele.find('[name].fnInput');
        $nameList.each(function (i, e) {
            $(this).rules("remove");
        })
    };

    orderNameList($('.decodeAcrrod'))
    dynamAddRules($('body'));

    function formatDate(date) { //转换日期类型为2016-08-02

        var resDate = '';
        var _val = (!!!date) ? new Date() : new Date(date); //没传入时间则默认为当前系统时间
        var year = _val.getFullYear(),
            month = _val.getMonth() + 1,
            day = _val.getDate();

        month = (month < 10) ? ('0' + month) : month;
        day = (day < 10) ? ('0' + day) : day;
        resDate = year + '-' + month + '-' + day;

        return resDate;

    };
    //最大解保金额
    // $('#relieveAmount').on('blur', function () {
    //     var _applyAmount = parseFloat(this.value);
    //     var _maxAmount = parseFloat($('#availableReleaseAmount').val());
    //     if (_applyAmount > _maxAmount) {
    //         Y.alert('提示', '超过了最大可解保金额');
    //     }
    // });
    // 提交 end

    // 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    if (false) {
        publicOPN.addOPN([{
            name: '查看用户资料',
            alias: 'lookUserInfo',
            event: function () {}
        }])
    }
    publicOPN.init().doRender();
    // 侧边栏 end
});