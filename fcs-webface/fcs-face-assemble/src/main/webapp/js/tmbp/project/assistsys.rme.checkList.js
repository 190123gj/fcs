define(function (require, exports, module) {
    require('zyw/publicPage');
    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');

    //全选
    $('body').on('change', '.allCheckbox', function () {

        var $this, $target;

        $this = $(this);
        $target = $this.parents('thead').next('tbody').find('input[type="checkbox"]');

        $this.is(':checked') ? $target.attr('checked', true) : $target.attr('checked', false);

    })


    $('body').on('click', '.delivery', function () {

        var _target = $('.m-table-list tbody input[type="checkbox"]:checked'),
            _lastSiblings = _target.eq(0).parent().siblings('.itemNumber').text(),
            _length = _target.length,
            _equal = true,
            _type = 'output',
            contentType = '申请借阅';

        // if (_type == 'Output') {
        //     contentType = '申请出库';
        // } else if (_type == 'View') {
        //     contentType = '申请查阅';
        // } else if (_type == 'Borrow') {
        //     contentType = '申请借阅';
        // }


        // _target.parents('tr').each(function () {
        //
        //     var _this = $(this)
        //     if(!_this.find('[output]').length) {
        //         hintPopup('您选择的档案中存在不能' + contentType + '的项');
        //         _equal = false;
        //         return false;
        //     }
        //
        // })

        if (_length) {

            var url = '/projectMg/file/checkApply.json?type=BORROW&fileIds='   // check url
            var go_url = '/projectMg/file/applyBatchBorrow.htm?type=BORROW&fileIds='; // go url
            var _sum = ''

            _target.each(function (index, el) {

                var _this = $(el),
                    _siblingsDom = _this.parent().siblings('.itemNumber'),
                    _ids = _siblingsDom.attr('ids')

                if(index === _target.length -1){
                    _sum += _ids;

                } else {
                    _sum += _ids + ',';

                }


            });


            $.ajax({

                url: url + _sum,
                type: 'POST',
                dataType: 'json',
            }).done(function (res) {
                if (res.success) {

                    window.location.href = go_url + _sum

                }
                hintPopup(res.message);
            })



        } else {

            hintPopup('请勾选要操作的档案，再点击"' + contentType + '"');
            return false;

        }


    })

    require('Y-msg');

    // href="/projectMg/file/fileReturn.htm?ids=$!{apply.inputListId}"
    $('body').on('click', '.giveBack', function (event) {

        var _this = $(this);

        Y.confirm('提示', '档案是否已归还', function (opn) {

            if (opn == 'yes') {

                $.ajax({

                        url: '/projectMg/file/fileReturn.htm?ids=' + _this.attr('ids'),
                        type: 'POST',
                        dataType: 'json',
                    })
                    .done(function (res) {

                        hintPopup(res.message, function () {

                            if (res.success) {

                                window.location.reload(true);

                            }

                        });

                    })
                    .fail(function () {
                        console.log("error");
                    })
                    .always(function () {
                        console.log("complete");
                    });

            }

        });

    });

})