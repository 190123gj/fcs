define(function (require, exports, module) {

    require('zyw/project/afcg.editpub');

    var util = require('util')

    // 合计
    var fnsumBox = $('.fnsumBox'),
        fnsumBox2 = $('.fnsumBox2'),
        fnsumBox3 = $('.fnsumBox3'),
        fnsumBox4 = $('.fnsumBox4'),
        fnsumBox5 = $('.fnsumBox5');

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
                        sum += parseFloat(0.00);
                    } else {
                        sum += parseFloat(($(this).children('input').val() || '').replace(/\,/g, ''));
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
        tabSum(fnsumBox);
        tabSum(fnsumBox2);
        tabSum(fnsumBox3);
        tabSum(fnsumBox4);
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

    // 贷款金额集中度
    $('.fnRange').blur(function () {
        loanValid($(this));
        console.log($(this).val());
        if ($(this).val() == 'NaN') {
            $(this).val('');
            console.log($(this).val())
        }
        LoanAmount();
    })

    $('.fnHouseholds').blur(function () {
        LoanAmount();
    })

    function loanValid(obj) {
        var i = 0,
            self = obj,
            selfVal = parseFloat((self.val() || '').replace(/\,/, '')).toFixed(2),
            selfTr = self.parents('tr'),
            selfTrFirst = selfTr.find('td').eq(0).text(),
            selfTrFirstVal = selfTrFirst.split('，'),
            selfTrLast = selfTr.find('td').text();

        if (selfTrFirst == '2000以上') {
            selfTrFirstVal = parseFloat('2000以上').toFixed(2);
            if (selfVal <= 2000) {
                Y.alert('提示', '请输入区间范围内的金额~');
                self.val('');
                return false;
            }
        } else {
            for (i = 0; i < 2; i++) {
                selfTrFirstVal[i] = parseInt(selfTrFirstVal[i].replace(/[^0-9]+/g, ''));
            }

            if (selfVal <= selfTrFirstVal[0] || selfVal > selfTrFirstVal[1]) {

                Y.alert('提示', '请输入区间范围内的金额~');
                self.val('');
                return false;
            }
        }
        self.val(util.num2k(selfVal));
    }

    function LoanAmount() {
        var self = $('.LoanAmount'),
            i,
            numArr = [];
        self.find('tr').each(function () {
            var selfTr = $(this);
            for (i = 0; i <= 3; i++) {
                numArr[i] = selfTr.find('input.text').eq(i).val();
            }

            if ((numArr[0] == '' && numArr[1] != '') || (numArr[0] != '' && numArr[1] == '')) {
                selfTr.find('input.text').eq(0).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
                selfTr.find('input.text').eq(1).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });

            } else if (numArr[0] == '' && numArr[1] == '') {
                selfTr.find('input.text').eq(0).rules('remove', 'required');
                selfTr.find('input.text').eq(1).rules('remove', 'required');

            } else if (numArr[0] != '' && numArr[1] != '') {
                selfTr.find('input.text').eq(0).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
                selfTr.find('input.text').eq(1).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
            }

            if ((numArr[2] == '' && numArr[3] != '') || (numArr[2] != '' && numArr[3] == '')) {

                selfTr.find('input.text').eq(2).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
                selfTr.find('input.text').eq(3).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });

            } else if (numArr[2] == '' && numArr[3] == '') {

                selfTr.find('input.text').eq(2).rules('remove', 'required');
                selfTr.find('input.text').eq(3).rules('remove', 'required');

            } else if (numArr[2] != '' && numArr[3] != '') {

                selfTr.find('input.text').eq(2).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
                selfTr.find('input.text').eq(3).rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
            }
        })
    }

    LoanAmount();

});