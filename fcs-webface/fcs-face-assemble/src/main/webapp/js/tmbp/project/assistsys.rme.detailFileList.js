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
            _sum = [],
            _type = $(this).attr('type'),
            contentType;

        // if (_type == 'Output') {
        //     contentType = '申请出库';
        // } else if (_type == 'View') {
        //     contentType = '申请查阅';
        // } else if (_type == 'Borrow') {
        //     contentType = '申请借阅';
        // }
        switch (_type) {

            case 'Output':
                contentType = '申请出库'
                break
            case 'View':
                contentType = '申请查阅'
                break
            case 'Borrow':
                contentType = '申请借阅'
                break
            case 'Back':
                contentType = '档案归还'
                break

        }

        if (_length) {

            _target.each(function (index, el) {

                var _this = $(el),
                    _siblingsDom = _this.parent().siblings('.itemNumber'),
                    _ids = _siblingsDom.attr('ids'),
                    _siblings = _siblingsDom.text(),
                    Type = _this.parents('tr').find('[' + _type + ']').length;

                if (!Type) {

                    hintPopup('您选择的档案中存在不能' + contentType + '的项');
                    _equal = false;
                    return false;

                }

                if (_siblings != _lastSiblings) {

                    hintPopup('请选择同一个档案编号下的档案！');
                    _equal = false;
                    return false;

                }

                _sum.push(_ids);

            });

            if (!_equal) {

                return false;

            }

            // if (_type == 'Output') {

            //     window.location.href = '/projectMg/file/applyOutput.htm?ids=' + _sum.join(',') + '&type=OUTPUT&urlList=detailFileList';

            // } else if (_type == 'View') {

            //     window.location.href = '/projectMg/file/applyOutput.htm?ids=' + _sum.join(',') + '&type=VIEW&urlList=detailFileList';

            // } else if (_type == 'Borrow') {

            //     window.location.href = '/projectMg/file/applyBorrow.htm?ids=' + _sum.join(',') + '&urlList=detailFileList';

            // }

            var _url = ''

            switch (_type) {

                case 'Output':
                    _url = '/projectMg/file/applyOutput.htm?ids=' + _sum.join(',') + '&type=OUTPUT&urlList=detailFileList';
                    break
                case 'View':
                    _url = '/projectMg/file/applyOutput.htm?ids=' + _sum.join(',') + '&type=VIEW&urlList=detailFileList';
                    break
                case 'Borrow':
                    _url = '/projectMg/file/applyBorrow.htm?ids=' + _sum.join(',') + '&type=BORROW&urlList=detailFileList';
                    break
                case 'Back':
                    _url = '/projectMg/file/applyBorrow.htm?ids=' + _sum.join(',') + '&type=RETURN&urlList=detailFileList'
                    break

            }

            window.location.href = _url


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