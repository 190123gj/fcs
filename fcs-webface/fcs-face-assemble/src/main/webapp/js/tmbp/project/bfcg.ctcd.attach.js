/**
 * 授信落实 上传资产附件/查看附件
 *
 * 主要分为 用户上传的资源（授信id关联） 资产数据资源（资产id关联）
 *
 * 用户上传的资源（授信id关联） value 附件信息 txt 文字
 *
 * 
 */

define(function (require, exports, module) {
    // 项目管理 > 授信前管理 > 授信条件落实情况信息  附件上传、查看
    require('Y-msg');
    var util = require('util');
    var $body = $('body');

    // ------ 上传附件 合集 start

    var ASSET_LIST = {} // 资产数据缓存 资产id作为索引
    var USER_UP_LIST = {} // 用户上传数据 落实id作为索引

    var $upAssetBox = $('<div class="m-modal-box fn-hide"></div>')

    // 收集数据
    $body.find('tbody tr').each(function (index, el) {

        var $this = $(this),
            _id = $this.find('.fnUpAsset').attr('itemid');

        var UPASSETARR = [];

        if (!!_id) {

            // 遍历资源，拿到已上传的数据
            $this.find('.fnUpAssetItem').each(function (index, el) {

                $(this).find('input.fnThisValue').each(function (index, el) {
                    UPASSETARR.push({
                        value: el.value,
                        txt: el.getAttribute('txt')
                    });
                });

            })

            USER_UP_LIST[_id] = UPASSETARR

        }

    });

    // 创建弹出层
    function creatUpAssetBox(listHtml) {

        return [
            '<div class="m-modal-overlay"></div>',
            '<div class="m-modal m-modal-default" style="width: 860px; margin-left: -455px;">',
            '    <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title">上传附件</span></div>',
            '    <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner">',
            listHtml,
            '    </div></div></div>',
            '    <div class="fn-mt10 fn-tac">',
            '        <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-mr20 sure">确定</a>',
            '        <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-ml20 close">取消</a>',
            '    </div>',
            '</div>'
        ].join('');

    }

    /**
     * 创建资源列表
     * @param  {[type]}  assetArr   [资产列表]
     * @param  {[type]}  upAssetArr [用户上传数据]
     * @param  {Boolean} isLook     [是否查看]
     * @return {[type]}             [description]
     */
    function creatUpAssetList(assetArr, upAssetArr, isLook) {

        var _html = '';

        $.each(assetArr, function (i, obj) {

            _html += ['<tr class="fnUpAssetItem">',
                creatUpAssetItem(assetArr[i], upAssetArr[i] ? upAssetArr[i] : {}, isLook),
                '</tr>'
            ].join('');

        });

        return '<div class="fn-m10"><table class="m-table m-table-list"><tbody>' + _html + '</tbody></table></div>';

    }

    /**
     * 创建每行数据
     * @param  {[type]}  assetObj [具体的资产对象]
     * @param  {[type]}  upObj    [具体的上传数据]
     * @param  {Boolean} isLook   [是否查看]
     * @return {[type]}           [description]
     */
    function creatUpAssetItem(assetObj, upObj, isLook) {

        var upVal = upObj.value || '',
            upTxt = upObj.txt || '';

        return [
            '<td>',
            ((assetObj.isRequired == 'IS') ? '<span class="fn-f30 fn-mr5">*</span>' : '') + assetObj.fieldName,
            '</td>',
            '<td width="310px" class="fnInputTd ' + ((assetObj.isRequired == 'IS') ? 'must' : '') + '">',
            isLook ? '<p>' + upTxt + '<input type="hidden" value="' + upTxt + '"></p>' : '<p class="fn-mb5"><input type="text" class="text" maxlength="50" value="' + upTxt + '" maxlength="200"></p>',
            '<div class="fnUpAttach">',
            '<span class="item-files m-up-files">共有[<strong class="upFilesNumber">' + getUpAttachUl(upVal, !isLook).size + '</strong>]附件</span>&ensp;',
            '<span class="fn-csp ui-btn ui-btn-fill ui-btn-blue viewDaliog"><i class="icon icon-look"></i>弹窗查看</span>&nbsp;',
            isLook ? '' : ('<span class="fn-csp ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn" filetype="' + util.swtchFileType(assetObj.attachmentFormat) + '"><i class="icon icon-add"></i>上传附件</span>'),
            '<input class="fnUpAttachVal fnUpAssetVal ' + ((assetObj.isRequired == 'IS') ? 'fnUpAssetInput' : '') + '" type="hidden" imagekey="' + assetObj.fieldName + '" value="' + upVal + '" length="' + getUpAttachUl(upVal, !isLook).size + '">',
            '<div class="m-attach fnUpAttachUl fn-hide">' + getUpAttachUl(upVal, !isLook).html + '</div>',
            '</div></td>',
            '<td width="65px"><div class="fnUpAttach fn-tac">',
            '<a href="javascript:void(0);" class="viewDaliog">资产附件</a>',
            '<input class="fnUpAttachVal" type="hidden" value="' + upVal + '" length="' + getUpAttachUl(assetObj.attributeValue).size + '">',
            '<div class="m-attach fnUpAttachUl fn-hide">' + getUpAttachUl(assetObj.attributeValue).html + '</div>', '</div></td>'
        ].join('');

    }

    // 整理附件
    // 例如闭包的方式做缓存，避免同样的数据多遍历
    var getUpAttachUl = (function () {

        var CHACE = {}

        return function (value, isUp) {

            if (!!!value) {
                return {
                    html: '',
                    size: 0
                }
            }

            if (!!CHACE[value]) {
                return CHACE[value]
            }

            var _valArr = value.split(';'),
                _html = ''

            $.each(_valArr, function (i, str) {

                var _file = str.split(','),
                    isImg = util.isImg(_file[0]),
                    res = {
                        fileName: _file[0],
                        serverPath: _file[1],
                        fileUrl: _file[2]
                    };

                var _more = ''

                if (isUp) {
                    _more = '<span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span>'
                }

                if (/(.doc|.DOC)$/g.test(res.fileName) || /(.docx|.DOCX)$/g.test(res.fileName) ||
                    /(.xls|.XLS)$/g.test(res.fileName) || /(.xlsx|.XLSX)$/g.test(res.fileName) ||
                    /(.ppt|.PPT)$/g.test(res.fileName) || /(.pptx|.PPTX)$/g.test(res.fileName)) {

                    _more += '<a class="attach-item-preview" target="_blank" href="/projectMg/contract/excelMessage.htm?contractItemId=0&read=read&hidTitle=hidTitle&fileName=' + res.fileName + '&fileUrl=' + res.serverPath + '&id=0">预览</a>'

                }

                if (/(.pdf|.PDF)$/g.test(res.fileName)) {
                    _more += '<a class="attach-item-preview" target="_blank" href="' + res.fileUrl + '">预览</a>'
                }

                var _html1 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '" title="' + res.fileName + '"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span>' + (!!isUp ? '<span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span>' : '') + '</span>';

                var _html2 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a title="点击下载' + res.fileName + '" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a>' + _more + '</span>';

                _html += (isImg) ? _html1 : _html2

            })

            CHACE[value] = {
                html: _html,
                size: _valArr.length
            }

            return CHACE[value]

        }

    })();

    // 保存到对应的位置
    function saveUpAsset2Html(isSure) {

        var _html = '',
            _isComplete = true,
            _isCompleteEq,
            _arr = [];

        // 2017.01.10 保证类型附件不必必填
        if (!!isSure) {

            // 暂时不做必填 2016.09.12

            // $upAssetBox.find('.fnUpAssetInput').each(function(index, el) {

            //  if (!!!el.value) {
            //      _isComplete = false;
            //      _isCompleteEq = index;
            //      return false;
            //  }

            // });

            // if (!_isComplete) {
            //  return {
            //      success: false,
            //      message: '请上传完必须的附件'
            //  };
            // }

            // 2016.10.15 附件、文字 二选一

            $upAssetBox.find('.fnInputTd.must').each(function (index, el) {

                var _hasValue = false;

                $(this).find('input').each(function (index, el) {

                    if (!!el.value.replace(/\s/g, '')) {
                        _hasValue = true;
                        return false
                    }

                });

                if (!_hasValue) {
                    _isComplete = false
                    _isCompleteEq = index
                    return false
                }

            });

            if (!_isComplete) {
                return {
                    success: false,
                    message: '第' + (_isCompleteEq + 1) + '行信息未填写完'
                };
            }

        }

        $upAssetBox.find('.fnUpAssetVal').each(function (index, el) {

            var _txt = $(el).parents('td').find('input').eq(0).val();

            _html += creatAssetInput({
                assetId: $upAssetBox.attr('markid'),
                imageKey: el.getAttribute('imagekey'),
                imageValue: el.value,
                creditId: $upAssetBox.attr('itemid'),
                imageTextValue: _txt,
                isrequired: el.className.indexOf('fnUpAssetInput') >= 0
            })

            // _html += [
            //     '<div class="fnUpAssetItem">',
            //     '<input type="hidden" name="assetId" value="' + $upAssetBox.attr('markid') + '">',
            //     '<input type="hidden" name="imageKey" value="' + el.getAttribute('imagekey') + '">',
            //     '<input type="hidden" name="imageValue" value="' + el.value + '" class="fnThisValue">',
            //     '<input type="hidden" name="creditId" value="' + $upAssetBox.attr('itemid') + '">',
            //     '<input type="hidden" name="imageTextValue" value="' + _txt + '">',
            //     '</div>'
            // ].join('')

            _arr.push({
                value: el.value,
                txt: _txt
            })

        })

        USER_UP_LIST[$upAssetBox.attr('itemid')] = _arr

        $('.fnUpAsset[itemid="' + $upAssetBox.attr('itemid') + '"]').nextAll().remove().end().after(_html)

        return {
            success: true
        }

    }

    /**
     * 创建资产数据
     */
    function creatAssetInput(obj) {
        return [
            '<div class="fnUpAssetItem">',
            '<input type="hidden" name="assetId" value="' + obj.assetId + '">',
            '<input type="hidden" name="imageKey" value="' + obj.imageKey + '">',
            '<input type="hidden" name="imageValue" value="' + obj.imageValue + '" class="fnThisValue" txt="' + obj.imageTextValue + '" isrequired="' + (!!obj.isrequired ? 'IS' : 'NO') + '">',
            '<input type="hidden" name="creditId" value="' + obj.creditId + '">',
            '<input type="hidden" name="imageTextValue" value="' + obj.imageTextValue + '">',
            '</div>'
        ].join('')
    }


    // 获取资产详情
    function getAssetInfo(id) {

        var dtd = $.Deferred();

        if (ASSET_LIST[id]) {

            return dtd.resolve(ASSET_LIST[id]);

        } else {

            $.ajax({
                url: '/projectMg/projectCreditCondition/loadAssetAtachment.htm?assetId=' + id,
                success: function (res) {

                    if (res.success) {

                        ASSET_LIST[id] = res;

                        dtd.resolve(ASSET_LIST[id]);

                    } else {

                        dtd.reject(res);

                    }

                }
            });

        }

        return dtd.promise();

    }


    $body.on('click', '.fnUpAsset', function () {

        var $this = $(this)
        var _id = this.getAttribute('assetid')
        var _itemId = $this.parents('tr').find('.fnThisId').val()
        var _isLook = (this.className.indexOf('fnUpAssetLook') > -1) ? true : false // 是否是查看模式

        // 2017.01.06 新增批量操作，点击的时候进行一次更新
        var _arr = []
        $this.parent().find('.fnThisValue').each(function (index, el) {

            _arr.push({
                value: this.value || '',
                txt: this.getAttribute('txt') || ''
            })

        })

        USER_UP_LIST[_itemId] = _arr

        $.when(getAssetInfo(_id))
            .then(function (res) {

                if (res.listImageInfo.length) {

                    $upAssetBox
                        .html(creatUpAssetBox(creatUpAssetList(res.listImageInfo, USER_UP_LIST[_itemId] || [], _isLook)))
                        .removeClass('fn-hide')
                        .attr('markid', _id)
                        .attr('itemid', _itemId);

                } else {

                    var _isRequired = !!$this.parents('tr').find('.fnCheckBox:checked').val() ? true : false,
                        $p = $this.parent();

                    var _div = $('<div></div>').html([
                        // '<input type="text" class="text fnTwoAndOneInput" name="' + $this.parents('td').attr('subdiyname') + 'textInfo" maxlength="200">',
                        '<textarea class="text fnTwoAndOneInput" name="' + $this.parents('td').attr('subdiyname') + 'textInfo" maxlength="50"></textarea>',
                        '<div class="fnUpAttach fn-mt5">',
                        '<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn">上传</a>',
                        '<input class="fnUpAttachVal fn-input-hidden fnTwoAndOneInput" type="text" name="rightVouche">',
                        '<div class="fn-blank5"></div><div class="m-attach fnUpAttachUl block"></div>',
                        '</div>'
                    ].join(''));

                    var _input = _div.find('.fnUpAttachVal');
                    _input[0].name = $this.parent().attr('subdiyname') + _input[0].name;

                    $p.html(_div.html()).removeAttr('ordername subdiyname class').addClass('fnTwoAndOne fnTwoAndOneMust');

                    Y.alert('提示', '资产管理中并未关联数据，请上传普通附件', function (opn) {
                        if (opn == 'yes') {
                            $p.find('.fnUpAttachBtn').trigger('click');
                        }
                    });

                    // 必填
                    // if (_isRequired) {

                    //     $p.find('input.fnUpAttachVal').rules('add', {
                    //         required: true,
                    //         messages: {
                    //             required: '必填'
                    //         }
                    //     });

                    //     $p.find('input.fnUpAttachVal').valid();

                    // }

                }

            }).fail(function (res) {

                // Y.alert('提示', res.message);
                var _isRequired = !!$this.parents('tr').find('.fnCheckBox:checked').val() ? true : false,
                    $p = $this.parent();

                var _div = $('<div></div>').html([
                    // '<input type="text" class="text fnTwoAndOneInput" name="' + $this.parents('td').attr('subdiyname') + 'textInfo" maxlength="200">',
                    '<textarea class="text fnTwoAndOneInput" name="' + $this.parents('td').attr('subdiyname') + 'textInfo" maxlength="50"></textarea>',
                    '<div class="fnUpAttach fn-mt5">',
                    '<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn">上传</a>',
                    '<input class="fnUpAttachVal fn-input-hidden fnTwoAndOneInput" type="text" name="rightVouche">',
                    '<div class="fn-blank5"></div><div class="m-attach fnUpAttachUl block"></div>',
                    '</div>'
                ].join(''));

                var _input = _div.find('.fnUpAttachVal');
                _input[0].name = $this.parent().attr('subdiyname') + _input[0].name;

                $p.html(_div.html()).removeAttr('ordername subdiyname class').addClass('fnTwoAndOne fnTwoAndOneMust');

                Y.alert('提示', '资产管理中并未关联数据，请上传普通附件', function (opn) {
                    if (opn == 'yes') {
                        $p.find('.fnUpAttachBtn').trigger('click');
                    }
                });

            });



    }).append($upAssetBox);

    $upAssetBox.on('click', '.close', function () {

        // 关闭就存数据
        saveUpAsset2Html();

        $upAssetBox.html('').addClass('fn-hide');

    }).on('click', '.sure', function () {

        // 关闭就存数据
        var _res = saveUpAsset2Html(true);

        if (!_res.success) {
            Y.alert('提示', _res.message);
            return;
        }

        $upAssetBox.html('').addClass('fn-hide');

    })

    // 默认点击每个上传按钮
    // 2017.01.05 CTO说不管有没有，点击的时候才去加载
    if (!!$('#projectCode').val()) {

        function getAssetInfoLoop(list) {

            var _index = 0;

            if (!!!list.length) {
                return
            }

            getDate(list[_index]);

            function getDate(thisObj) {

                // 清空
                // if (!!!_index) {
                //     $('.fnUpAsset[assetid="' + thisObj.id + '"]').parent().find('.fnUpAssetItem').remove()
                // }

                if (_index >= list.length) {
                    loading.close()
                    return;
                }

                var _id = thisObj.id,
                    _itemid = thisObj.itemid,
                    _valArr = thisObj.vueArr;

                $.when(getAssetInfo(_id))
                    .then(function (res) {

                        var _html = '';

                        $.each(res.listImageInfo, function (i, obj) {
                            var _obj = _valArr[i] || {};

                            // 新申请，没有缓存数据
                            _obj.attributeKey = obj.fieldName
                            _obj.isRequired = obj.isRequired

                            _html += creatAssetInput({
                                assetId: _id,
                                imageKey: obj.fieldName,
                                imageValue: _obj.value || '',
                                creditId: _itemid,
                                imageTextValue: _obj.txt || '',
                                isrequired: _obj.isRequired == 'IS'
                            })

                        })

                        $('.fnUpAsset[itemid="' + _itemid + '"]').parent().find('.fnUpAssetItem').remove()
                            .end().append(_html)

                        // 会写typeId
                        var _typeId = 0
                        if (res.success && !!res.listImageInfo.length) {
                            _typeId = res.listImageInfo[0].typeId
                        }

                        $('.fnUpAsset[itemid="' + _itemid + '"]').parents('tr').find('.checkbox').attr('typeid', _typeId);

                        getDate(list[++_index]);

                    }).fail(function () {
                        $('.fnUpAsset[itemid="' + _itemid + '"]').parents('tr').find('.checkbox').attr('typeid', 0);
                        getDate(list[++_index]);
                    });

            }

        }

        var HAD_UP_ASSET_ARR = [];

        var loading = new util.loading()

        $('.fnUpAsset').each(function (index, el) {

            HAD_UP_ASSET_ARR.push({
                id: el.getAttribute('assetid'),
                itemid: el.getAttribute('itemid'),
                vueArr: USER_UP_LIST[el.getAttribute('itemid')] || []
            })

        })

        if (!!HAD_UP_ASSET_ARR.length) {
            loading.open()
        }

        getAssetInfoLoop(HAD_UP_ASSET_ARR);

    }

    module.exports = {
        creatUpAssetList: creatUpAssetList,
        creatUpAssetBox: creatUpAssetBox,
        getAssetInfo: getAssetInfo,
        creatAssetInput: creatAssetInput
    }

});