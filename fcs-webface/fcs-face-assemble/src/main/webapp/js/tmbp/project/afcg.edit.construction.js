define(function (require, exports, module) {

    var COMMON = require('zyw/project/afcg.editpub');

    var util = require('util')

    // 合计
    var fnsumBox5 = $('.fnsumBox5'),
        fnsumBox6 = $('.fnsumBox6'),
        fnsumBox7 = $('.fnsumBox7');

    function tabSum(tbody) {
        // tid 表格class
        var sum = parseFloat(0),
            i = 1,
            tabBox = tbody,
            numArr = [],
            rslt = tabBox.parents().parents().parents('.fnHeji').children('.fnRslt');

        for (i = 1; i <= 10; i++) {
            var sum = parseFloat('0');
            tabBox.children('tr').each(function () {
                $(this).find('td').eq(i).each(function () {

                    var text = ($(this).children('input').val() || '').replace(/\,/g, '');

                    if (isNaN(text)) {
                        text = 0;
                    }

                    if (text == '') {

                        sum += parseFloat(0);

                    } else {

                        sum += parseFloat(text);

                    }
                })
            });
            numArr[i] = sum;
            var sumTd = rslt.find('td').eq(i).children('input');
            if (sumTd.hasClass('fnFloat')) {
                sumTd.val(util.num2k(numArr[i].toFixed(2)));
            } else if (sumTd.hasClass('fnInt')) {
                sumTd.val(numArr[i].toFixed(0));
            }
        }

    }
    $('input').blur(function () {
        tabSum(fnsumBox5);
        tabSum(fnsumBox6);
        tabSum(fnsumBox7);
    })


    fnsumBox5.on("change", "input", function () {
        tabSum(fnsumBox5);
    })

    fnsumBox6.on("change", "input", function () {
        tabSum(fnsumBox6);
    })

    fnsumBox7.on("change", "input", function () {
        tabSum(fnsumBox7);
    })

    fnsumBox5.on("click", ".fn-del", function () {
        setTimeout(function () {
            tabSum(fnsumBox5);
        }, 20)
    })
    fnsumBox6.on("click", ".fn-del", function () {
        setTimeout(function () {
            tabSum(fnsumBox6);
        }, 20)
    })
    fnsumBox7.on("click", ".fn-del", function () {
        setTimeout(function () {
            tabSum(fnsumBox7);
        }, 20)
    })

    // 成本机构及变动情况
    costChange($('.fnzbBox'));

    $('.fnCost').blur(function (event) {
        costChange($(this).parents('.costChange'));
    })

    function costChange(obj) {
        var selfBox = obj,
            selfTit = selfBox.children('.costChangeTit'),
            fnWrite = selfTit.find('fnWrite input[type="checkbox"]');

        if (fnWrite.hasClass('active')) {

            selfBox.find('input').each(function () {
                var _this = $(this);
                _this.rules('remove', 'required');
            })

        } else {
            for (i = 0; i <= 2; i++) {
                var sum = parseFloat('0');
                selfBox.find('tr').each(function () {
                    $(this).find('td .fnCost').eq(i).each(function () {
                        if ($(this).val() == '') {
                            $(this).val('');
                            sum += parseFloat(0);
                        } else {
                            var selfVal = parseFloat(($(this).val() || '').replace(/\,/g, ''));
                            $(this).val(util.num2k(selfVal.toFixed(2)));
                            sum += selfVal;
                        }
                    })
                })
                selfBox.find('.fnFloat').eq(i).val(util.num2k(sum.toFixed(2)));
            }

            selfBox.find('.fnFloat').each(function () {
                if (parseFloat($(this).val()) > 100) {
                    var selfTitSpan = selfTit.find('span.errorChange');
                    if (selfTitSpan.length > 0) {} else {
                        selfTit.append('<span class="red fn-ml15 errorChange">“总项”填写有误，请参照填写规则填写！</span>');
                    }
                    return false;
                } else {
                    selfTit.find('span.errorChange').remove();
                }
            })
        }
    }

    function costChangeCh(obj) {
        var selfBox = obj,
            selfTit = selfBox.find('.costChangeTit');
        for (i = 0; i <= 2; i++) {
            var sum = parseFloat('0');
            selfBox.find('tr').each(function () {
                $(this).find('td .fnCostCh').eq(i).each(function () {
                    if ($(this).val() == '') {
                        $(this).val('');
                        sum += parseFloat(0);
                    } else {
                        var selfVal = parseFloat($(this).val());
                        $(this).val(selfVal.toFixed(2));
                        sum += selfVal;
                    }
                })
            })
            if (sum > 100.00) {
                var selfTitSpan = $('span.errorChange2');
                if (selfTitSpan.length > 0) {} else {
                    $('.costChangeTit').append('<span class="red fn-ml15 errorChange2">“子项”填写有误，请参照填写规则填写！</span>');
                    return false;
                }
                return false;
            } else {
                $('span.errorChange2').remove();
            }
        }
    }

    $('.fnTableList').each(function () {
        costChangeCh($(this));
    })

    $('body').on('blur', '.fnCostCh', function () {
        costChangeCh($(this).parents('.fnTableList'));
    })

    $('.fn-addline').click(function () {
        $('body').on('blur', '.fnCostCh', function () {
            costChangeCh($(this).parents('.fnTableList'))
        })
    })

    $(".costChange").on("click", ".fn-del", function () {
        setTimeout(function () {
            costChangeCh($(this).parents().parents().parents('.fnTableList'))
        }, 5)
    })

    function trValid(tbody) {
        var self = tbody,
            selfActive = self.parent().parent().parent().find('.fnWrite input[type="checkbox"]');

        if (selfActive.hasClass('active')) {

            self.find('input').each(function () {
                $(this).rules('remove', 'required')
            })

        } else {
            tbody.find('tr').each(function () {
                var selfTr = $(this);
                if (selfTr.index() == 0) {
                    selfTr.find('input[type="text"]').each(function () {
                        $(this).rules('add', {
                            required: true,
                            messages: {
                                required: '必填'
                            }
                        })
                    })
                } else if (selfTr.index() > 0) {
                    var nullVal = 0;
                    selfTr.find('input[type="text"]').each(function () {
                        var inputVal = $(this).val();
                        if (inputVal != '') {
                            $(this).parents('td').parents('tr').find('input[type="text"]').each(function () {
                                $(this).rules('add', {
                                    required: true,
                                    messages: {
                                        required: '必填'
                                    }
                                })
                            })
                            return false;
                        } else {

                            nullVal += 1;
                        }
                    })

                    if (nullVal == selfTr.find('input[type="text"]').length) {
                        selfTr.find('input[type="text"]').each(function () {
                            $(this).rules('remove', 'required');
                        })
                    }
                }
            })
        }

    }

    trValid($('.fnyzbbox'));
    trValid($('.fnzjbbox'));

    $('.fnyzbbox').on('blur', 'input', function () {
        trValid($('.fnyzbbox'));
    })
    $('.fnzjbbox').on('blur', 'input', function () {
        trValid($('.fnzjbbox'));
    })

    // ------ 2017.01.11 【（3）项目分包商】增加一项就为必填，除非勾选未收集到 start

    var $fnSubcontractors = $('#fnSubcontractors')

    $fnSubcontractors.on('click', 'input[type="checkbox"]', function () {

        setSubcontractorsInput(this.checked)

    }).on('click', '.fnAddLine', function () {

        $fnSubcontractors.find('.fn-testtable').append(document.getElementById('tplline12').innerHTML)

        COMMON.resetName()

        // 建立规则

        $fnSubcontractors.find('.fnInput').each(function (index, el) {
            var $this = $(el)
            $this.rules('remove', 'required')
            $this.val()
        })

        var _checked = $fnSubcontractors.find('input[type="checkbox"]').prop('checked')

        if (!_checked) {

            $fnSubcontractors.find('.fnInput').each(function (index, el) {
                $(el).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                })
            })

        }

    })

    function setSubcontractorsInput(checked) {

        if (checked) {

            $fnSubcontractors.find('th span.red').remove()
                .end().find('.fnInput').each(function (index, el) {
                    var $this = $(el)
                    $this.rules('remove', 'required')
                    $this.val()
                })

        } else {

            $fnSubcontractors.find('th').each(function (index, el) {
                    el.innerHTML = '<span class="red">*&nbsp;</span>' + el.innerHTML
                        // $(el).before('<span class="red">*&nbsp;</span>')
                })
                .end().find('.fnInput').each(function (index, el) {
                    $(el).rules('add', {
                        required: true,
                        messages: {
                            required: '必填'
                        }
                    })
                })

        }

    }

    // ------ 2017.01.11 【（3）项目分包商】增加一项就为必填，除非勾选未收集到 end


});