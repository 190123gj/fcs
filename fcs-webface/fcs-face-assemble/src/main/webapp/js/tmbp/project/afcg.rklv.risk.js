define(function (require, exports, module) {
    //保后项目汇总表 新增
    require('input.limit');
    require('validate');
    require('Y-msg');

    var util = require('util');

    var $fnInput = $('.fnInput'), //所有得分必要输入新

        $fnComprehensive = $('.fnComprehensive'),
        $comprehensive = $('#comprehensive'),

        $fnSituation = $('.fnSituation'),
        $situation = $('#situation'),

        $fnGuarantee = $('.fnGuarantee'),
        $guarantee = $('#guarantee'),

        $totalInput = $('#totalInput'),

        $form = $('#form'),
        formRules = {
            rules: {},
            messages: {}
        },
        requiredRules = {
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput:visible'), requiredRules)

    $form.on('change', '#comprehensive,#situation,#guarantee', function () {
        getScore();
    }).on('blur', '.fnInputReason', function () {
        $(this).parent().prev().find('input').trigger('blur');
    }).validate($.extend({}, requiredRules, {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            element.after(error);
        },
        submitHandler: function (form) {

            // 提交前必填数据
            var _isPass = true,
                _isPassEq;

            $('.fnInput:visible').each(function (index, el) {

                if (!!!el.value.replace(/\s/g, '')) {
                    _isPass = false;
                    _isPassEq = index;
                    return false;
                }

            });

            if (!_isPass) {

                Y.alert('提示', '评分表未填写完整', function () {
                    $('.fnInput').eq(_isPassEq).focus();
                });
                return;

            }

            //得分
            var _score = +$totalInput.val(),
                _level;

            if (_score < 30) {
                _level = '损失类'
            } else if (_score >= 30 && _score < 40) {
                _level = '可疑类'
            } else if (_score >= 40 && _score < 60) {
                _level = '次级类'
            } else if (_score >= 60 && _score < 75) {
                _level = '关注类'
            } else {
                _level = '正常类'
            }

            var _content = '<p>合计得分：' + _score + '分</p><p class="fn-mt20">该项目风险等级为<span class="fn-fs20 fn-f30 fn-fwb">' + _level + '</span></p>'

            var $select = $('#fnSelectLevel')

            if (!!$select.length) {
                _content = '<p>该项目风险等级为<span class="fn-fs20 fn-f30 fn-fwb">' + $select.find('option[value="' + $select.val() + '"]').text() + '</span></p>'
            }

            // 显示计算结果
            $('body').Y('Msg', {
                type: 'confirm',
                title: '提示',
                content: _content,
                yesText: '确认提交',
                noText: '返回修改',
                callback: function (opn) {
                    if (opn == 'yes') {
                        doSubmit('SUBMIT');
                    }
                }
            });

        }
    }));

    // 2016.10.25
    $('#reevaluationDescs').rules('add', {
        required: true,
        messages: {
            required: '必填'
        }
    })

    //求和
    function getTotal($els, $totalEl) {

        var _n = 0;
        $els.each(function (index, el) {
            //_n += +(el.value || 0);
            _n = util.accAdd(_n, +el.value)
        });
        $totalEl.val(_n).trigger('change');
    }

    //小分段求和
    function getCombined(self) {

        var _classArr = self.className.split(' '),
            _class,
            _n = 0;
        //找到可能的那个class
        for (var i = 0; i < _classArr.length; i++) {
            if (_classArr[i].indexOf('subth_') == 0) {
                _class = _classArr[i];
            }
        }
        $('.' + _class).each(function (index, el) {
            _n = util.accAdd(_n, +el.value)
        });
        document.getElementById(_class).innerHTML = _n;

    }

    //总分
    function getScore() {
        // $totalInput.val(+$comprehensive.val() + +$situation.val() + +$guarantee.val())
        $totalInput.val(util.accAdd(+$comprehensive.val(), util.accAdd(+$situation.val(), +$guarantee.val())));
    }

    getScore();

    $fnInput.each(function (index, el) {
        //必填规则
        // var name = $(this).attr('name');
        // if (!name) {
        //     return true;
        // }
        // formRules.rules[name] = {
        //     required: true
        // };
        // formRules.messages[name] = {
        //     required: '必填'
        // };
    }).on('blur', function () {
        var _this = $(this),
            _thisVal = +_this.val(),
            _max = +(_this.parent().prev().prev().text().replace(/[^\d]/g, '')),
            _lastScore = +_this.parent().prev().text(),
            _$reason = _this.parent().next().find('input');

        //输入值范围限定
        if (_thisVal < 0) {
            _this.val(0)
        }
        if (_thisVal > _max) {
            _this.val(_max)
        }
        _thisVal = _this.val();
        // 分配完成率M/N变动30%以上
        //  (复评分 - 初评分) / 初评 的绝对值
        var _ratio = Math.abs((+_thisVal - _lastScore) / _lastScore).toFixed(8) * 100;
        //是否超过30%

        if ((_ratio > 30 || !!!_max) && _thisVal.length) {
            _$reason.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        } else {
            _$reason.rules('remove', 'required');
            _$reason.parent().find('b').remove();
        }
        _$reason.valid();

        //求和
        if (_this.hasClass('fnComprehensive')) {
            getTotal($fnComprehensive, $comprehensive);
        }

        if (_this.hasClass('fnSituation')) {
            getTotal($fnSituation, $situation);
        }

        if (_this.hasClass('fnGuarantee')) {
            getTotal($fnGuarantee, $guarantee);
        }

        //如果需要小的求和
        if (this.className.indexOf('subth_') >= 0) {
            getCombined(this)
        }

    });

    // 再次填写的时候 还原？
    $('.fnGuarantee').each(function (index, el) {
        if (this.className.indexOf('subth_') >= 0 && this.value.replace(/\s/g, '')) {
            getCombined(this)
        }
    });

    function doSubmit(type) {

        document.getElementById('checkStatus').value = type ? 1 : 0;

        util.ajax({
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提示', type ? res.message : '已保存', function () {
                        window.location.href = '/projectMg/riskLevel/list.htm'
                    });
                    // util.postAudit({
                    //     formId: res.form.formId
                    // }, function () {
                    //     window.location.href = '/projectMg/riskLevel/list.htm'
                    // })
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });

    }

    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    // publicOPN.addOPN([{
    //  name: '暂存',
    //  event: function() {
    //      doSubmit()
    //  }
    // }]);
    publicOPN.init().doRender();
    //------ 侧边栏 end

});