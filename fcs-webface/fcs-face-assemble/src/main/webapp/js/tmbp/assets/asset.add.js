/*
 * @Author: erYue
 * @Date:   2016-07-04 18:05:40
 * @Last Modified by:   Administrator
 * @Last Modified time: 2016-09-18 16:04:13
 */

define(function (require, exports, module) {

    require('lib/lodash');
    require('validate');
    require('input.limit');
    require('tmbp/selectType'); //下拉选择
    require('Y-htmluploadify');

    var util = require('util');
    var template = require('arttemplate');
    var chooseRegionNew = require('tmbp/chooseRegionNew'); //所属地区 相关
    var publicOPN = new(require('zyw/publicOPN'))(); //侧边栏
    var hintPopup = require('zyw/hintPopup');
    var getList = require('zyw/getList');
    var $form = $('#form');
    publicOPN.init();
    if (!!util.browserType() && util.browserType() < 10) { //IE9及以下不支持多选，加载单选上传
        require.async('tmbp/upAttachModify'); //上传
    } else {
        require.async('tmbp/upAttachModifyNew'); //上传
    }

    var chooseRegion = new chooseRegionNew();
    // 选择项目
    var _getList = new getList();

    var $selectRslt = $('.selectRslt'),
        $validateList = $('.fn-validate');

    var isEdit = $('#isEdit').length == 0 ? false : true,
        isView = $('#isView').length == 0 ? false : true,
        isAdd = (!isEdit && !isView) ? true : false,
        lastRslt = isAdd ? 'isAdd' : isEdit ? 'isEdit' : 'isView',
        thisUrlHash = window.location.search,
        toReviews = thisUrlHash && thisUrlHash.indexOf('toReviewIdeas') >= 0,
        disReturn = thisUrlHash && thisUrlHash.indexOf('disReturn') >= 0,
        hiddenBtn = thisUrlHash && thisUrlHash.indexOf('hiddenBtn') >= 0,
        toViewReview = thisUrlHash && thisUrlHash.indexOf('toViewReview') >= 0, //无按钮
        isUsed = thisUrlHash && thisUrlHash.indexOf('isUsed') >= 0; //资产是否被使用

    var isUsedItem = ['抵质押率', '权利人', '住所', '权证号', '数量', '单位', '评估价格', '抵押率', '所有权利人名称', '所有权人', '拟质押金额'];

    var dataUrl = isEdit ? '/assetMg/edit.htm' : isView ? '/assetMg/view.htm' : '/assetMg/add.htm';

    var tempTableData = {}; //缓存表格数据
    var assetsIdCode = $('#assetsId').val();
    var initMapPonit = ['106.552566', '29.556923'],
        zoomSize1 = 9,
        zoomSize2 = 18,
        firstClick = true,
        searchTimes = 0;

    // //初始化地图
    var map = new BMap.Map("allmap");
    if (isAdd) {
        map.centerAndZoom("重庆", 9);
    } else {
        setPonit(initMapPonit[0], initMapPonit[1]); //初始化中心
    }
    var ac = new BMap.Autocomplete({ //建立一个自动完成的对象
        "input": "keySearch", //关键字搜索框ID
        "location": map
    });
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.MapTypeControl());
    map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.OverviewMapControl());
    map.setDefaultCursor('auto');
    // map.enableScrollWheelZoom(false);

    $('body').on('click', '#mapSearch', function () { //关键词搜索
        var keyValue = !!$('#keySearch').val() ? $('#keySearch').val() : $('#keySearch').attr('placeholder');
        if (!keyValue) {
            // Y.alert('提示', '请先输入关键字！');
            return;
        }
        setPlace(keyValue);
        searchTimes++;
    }).on('click', '#mapLocationSearch', function () { //经纬度手动定位
        mapLocation()
    }).on('click', '[byrelationid] .isRequiredIS', function () { //关联选择

        var $this = $(this);
        if (!!$this.parents('.radiosBox').attr('status')) return;
        var thisVal = $this.attr('value');
        var thisTypeId = $this.parents('td').attr('byrelationid');

        var checkClass = 'radio-checked';

        if (!$this.hasClass(checkClass)) {
            $this.addClass(checkClass);
        }

        $this.siblings('i').removeClass(checkClass);
        $this.siblings('[name]').val(thisVal);

        $('[torelationid^="' + thisTypeId + '"]').parents('tr').attr('class', 'fn-hide').find('.will-validate').removeClass('fn-validate').addClass('ignore');
        $('[torelationid="' + thisTypeId + thisVal + '"]').parents('tr').removeAttr('class').find('.will-validate').addClass('fn-validate').removeClass('ignore');
        $('[torelationid^="' + thisTypeId + '"]').not('[torelationid="' + thisTypeId + thisVal + '"]').parents('tr').find('input[name*="attributeValue"]').val('');
        $('[torelationid^="' + thisTypeId + '"]').not('[torelationid="' + thisTypeId + thisVal + '"]').parents('tr').find('select [value="NONE"]').attr('selected', 'selected');

        // $validateList = $('.fn-validate');
        dynamAddRules($('.textInfoList'))
    }).on('click', '[name="isCustomer"]', function () { //表单处理--融资客户

        if ($(this).prop('checked') == true) {
            $(this).val('IS');
        } else {
            $(this).val('NO');
        };

    }).on('blur', '.maxTextInput', function () {
        var $this = $(this);
        var thisIndex = $this.parents('tr').find('td').index($this.parents('td'));
        var $thistargetTr = $this.parents('table').find('.specailType');
        var $reslt = $this.parents('table').find('.specailRslt').find('td').eq(thisIndex).find('[name*=attributeValue]');
        var isRequriedFlag = $reslt.attr('isrequiredflag');
        var arry = [];
        // if(isRequriedFlag == 'NO')
        $.each($thistargetTr, function (index, el) {
            var _this = $(this);
            var thisVal = _this.find('td').eq(thisIndex).find('.maxTextInput').val();
            arry.push(thisVal);
        });
        // console.log(arry)
        $reslt.val(arry.join('&&'));

        if (isRequriedFlag == 'NO') {

            var isRequried = false;
            $.each($this.parents('tr').find('.maxTextInput'), function (i, e) {
                if (!!$(this).val().replace(/\s/g, '')) isRequried = true;
            });
            if (isRequried) {
                $this.parents('tr').find('.maxTextInput').removeClass('ignore')
            } else {
                $this.parents('tr').find('.maxTextInput').addClass('ignore')
            };
        }


    }).on('click', '.toDeleteLine', function () {

        var $this = $(this);
        var $thisTr = $this.parents('tr');
        var $tmpTr = $thisTr.siblings('tr');
        var thisIndex = $this.parents('tr').find('td').index($this.parents('td'));
        var $reslt = $this.parents('table').find('.specailRslt').find('td').eq(thisIndex).find('[name*=attributeValue]');
        var isRequriedFlag = $reslt.attr('isrequiredflag');
        $thisTr.remove();
        $.each($tmpTr.find('.maxTextInput'), function (index, el) {
            $(this).trigger('blur');
        });
        if ($tmpTr.length == 1 && isRequriedFlag == 'YES') $tmpTr.find('.ignore').removeClass('ignore');
        orderNameIngron()

    }).on('click', '.toNewLine', function () {

        var $this = $(this);
        var $thisTr = $this.parents('tr');
        var $templateTr = $thisTr.clone(true);
        // var thisIndex = $this.parents('tr').find('td').index($this.parents('td'));
        // var $reslt = $this.parents('table').find('.specailRslt').find('td').eq(thisIndex).find('[name*=attributeValue]');
        // var isRequriedFlag = $reslt.attr('isrequiredflag');
        // if(isRequriedFlag == 'NO)
        $thisTr.find('.ignore').removeClass('ignore');
        $templateTr.find('[type="text"]').val('').removeClass('ignore').end()
            .find('td').removeAttr('orderName').end()
            .find('.new-line').addClass('fn-hide').end()
            .find('.less-line').removeClass('fn-hide');
        // .find('.ignore').removeClass('ignore');
        $this.parents('tbody').append($templateTr);
        orderNameIngron()
    }).on('blur', '.prcieNum,.mortgagePre', function () {

        setTimeout(function () {
            var pre = $('.mortgagePre').val().replace(/\,/g, '') || 0;
            var num = $('.prcieNum').val().replace(/\,/g, '') || 0;
            var reslt = parseFloat(pre) * parseFloat(num) * 0.01;
            reslt = reslt.toFixed(2);
            $('.mortgageNum').val(util.num2k(reslt)).valid();
        },50)

    }).on('focus', '[name="searchKey"]', function () {
        var text = $(this).attr('placeholder');
        $(this).val(text).removeAttr('placeholder')
    }).on('click','.exportBtn',function(){
    	if(!$('[name=typeId]').val()) {
    	    Y.alert('提示','请先选择抵押物类型！');
            return;
        };
    	$(this).attr('href','/assetMg/templateExport.json?typeId=' + $('[name=typeId]').val());
    }).on('click','#dataImport',function(){
        if(!$('[name=typeId]').val() || !$('[name=ownershipName]').val()){
            Y.alert('提示','请先选择抵押物类型和填写所有权人！');
            return;
        }
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/assetMg/excelImport.json?typeId=' + $('[name=typeId]').val() + '&ownershipName=' + $('[name=ownershipName]').val(),
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls;*.xlsx',
            fileObjName: 'UploadFile',
            onQueueComplete: function(a, b) {},
            onUploadSuccess: function($this, data, resfile) {

                var jsonData = JSON.parse(data);
                console.log(jsonData)

                hintPopup(jsonData.message, function() {

                    if (jsonData.success) {

                        window.location.href = '/assetMg/list.htm';

                    }

                });

            }

        });
    })
    // //上传
    // require('Y-htmluploadify');
    // //导入
    // $('#dataImport').click(function(event) {
    //     console.log('/assetMg/excelImport.json?typeId=' + $('[name=typeId]').val() + '&ownershipName=' + $('[name=ownershipName]').val())
    //
    //
    //
    // });

    //------ 公共 验证规则 start
    var $form = $('#form');
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {
            // element.parent().append(error);
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else if (element.hasClass('parentsMitem')) {
                element.parent('.m-item ').append(error);
            } else {
                element.parent().append(error);
            }
            // console.log(element)
        },
        submitHandler: function (form) {},
        rules: {
            assetType: {
                required: true
            },
            ownershipName: {
                required: true
            }
        },
        messages: {
            assetType: {
                required: '必填'
            },
            ownershipName: {
                required: '必填'
            }
        }
    };
    $form.validate(validateRules);

    function dynamAddRules(ele) {
        if (!ele) ele = $('body');
        // var $nameList = ele.find('[name].fn-validate,.leesOneItemInput.fn-validate').not('.lessOneItem');
        var $nameList = ele.find('[name].fn-validate').not('.lessOneItem');
        dynamicRemoveRules($form);
        $nameList.each(function (i, e) {
            var _this = $(this);
            if (_this.hasClass('leesOneItemInput')) {
                _this.rules('add', {
                    required: true,
                    lessOne: true,
                    messages: {
                        required: '附件和文本必填一项！',
                        lessOne: '附件和文本必填一项！'
                    }
                });
            } else {
                _this.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
            };
            if (!!parseInt(_this.attr('maxlength'))) {
                _this.rules('add', {
                    required: true,
                    maxlength: _this.attr('maxlength'),
                    messages: {
                        required: '必填',
                        maxlength: '不能超过' + _this.attr('maxlength') + '个字符！'
                    }
                });
            }
        })
    };

    function dynamicRemoveRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fn-validate,.leesOneItemInput.fn-validate');
        $nameList.each(function (i, e) {
            $(this).rules("remove");
        })
    };
    if (disReturn) $('#optBtn .returnBack').hide(); //去掉返回按钮
    if (toReviews || toViewReview) { //资产复评和查看资产复评
        // var textareaVal = $('#toReviews').find('textarea').val().replace(/\s/g, '');
        $('#form').addClass('ui-bg-faf').find('.fn-validate').removeClass('fn-validate');
        // $('#toReviews').removeAttr('style').find('textarea').val(textareaVal);
        $('#toReviews').removeAttr('style');
        $('#submit').show();

    } else {
        $('#toReviews').remove();
    };
    if (toViewReview) { //查看资产复评隐藏提交按钮
        $('#toReviews').find('input,textarea').attr('readonly', 'true');
        $('#submit').hide();
        $('#optBtn .returnBack').show();
    };

    if (!isAdd) { //如果不是新增
        // console.log(toViewReview)
        if (isView && !toViewReview && !toReviews) {
            //初始化侧边栏
            publicOPN.addOPN([{
                name: '查看关联项目',
                alias: 'viewDtl',
                event: function () {
                    $('#viewDtlBtn').click();
                }
            },{
                name: '复制资产',
                alias: 'copyAsset',
                event: function () {
                    // console.log($('.toCopyBtn').attr('href'))
                    // $('.toCopyBtn').click();
                    window.location.href = $('.toCopyBtn').attr('href');
                }
            }]);

            publicOPN.init().doRender(); //初始化侧边栏

            _getList.init({ //初始化项目关联弹窗
                title: '项目列表',
                ajaxUrl: '/baseDataLoad/queryAssetRelationProject.json?assetsId=' + assetsIdCode,
                btn: '#viewDtlBtn',
                tpl: {
                    tbody: [
                        '{{each pageList as item i}}',
                        '    <tr class="fn-tac m-table-overflow">',
                        '        <td><a projectCode="{{item.projectCode}}" href="/projectMg/viewProjectAllMessage.htm?projectCode={{item.projectCode}}" target="view_window">{{item.projectCode}}</a></td>',
                        '        <td><a projectName="{{item.projectName}}" href="/projectMg/viewProjectAllMessage.htm?projectCode={{item.projectCode}}" target="view_window">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</a></td>',
                        '        <td><a customerName="{{item.customerName}}" href="/customerMg/companyCustomer/info.htm?userId={{item.customerId}}" target="view_window">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
                        '        <td>{{item.busiTypeName}}</td>',
                        '        <td>{{item.assetsStatus}}</td>',
                        '    </tr>', '{{/each}}'
                    ].join(''),
                    form: [
                            '项目编号：',
                            '<input class="ui-text fn-w160" type="text" name="projectCode">&nbsp;&nbsp;',
                            '项目名称：',
                            '<input class="ui-text fn-w160" type="text" name="projectName">&nbsp;&nbsp;',
                            '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
                        ]
                        .join(''),
                    thead: ['<th width="100">项目编号</th>',
                        '<th width="120">项目名称</th>',
                        '<th width="120">客户名称</th>',
                        '<th width="100">业务品种</th>',
                        '<th width="100">资产状态</th>'
                    ].join(''),
                    item: 5
                }
            });
        }

        var data = {};
        data.id = assetsIdCode;
        util.ajax({
            url: dataUrl,
            type: 'post',
            data: data,
            success: function (res) {
                // if(!!res.info)
                // console.log(res)
                if (res.success && !!res.info.typeId) {
                    // if (!!!res.info.typeId) return;
                    var typeId = res.info.typeId;
                    var isRenderOver = renderTable(res, typeId);

                    if (isRenderOver) {

                        $('#assetType').attr('value', res.info.assetType);
                        $('#typeId').val(typeId);
                        $('#ownershipName').val(res.info.ownershipName);

                        if (res.info.isCustomer == 'IS') {
                            $('[name="isCustomer"]').trigger('click').attr({
                                value: 'IS'
                            });
                        };
                        addDataToPage(typeId);
                        doChecked();
                        chooseRegion.init();
                        initMap();
                        $('.mapSearchClickBtn').click();
                        dynamicRemoveRules($form);
                        dynamAddRules($form);
                        //被使用的不能被编辑

                        if (isUsed) {
                            $('#form').find('input[type=text].isUsedItemNotCanEdit').attr('readOnly', 'true').end()
                                .find('select.isUsedItemNotCanEdit,input[type=radio].isUsedItemNotCanEdit,input[type=checkbox].isUsedItemNotCanEdit').attr('disabled', 'disabled').end()
                                .find('.laydate-icon.isUsedItemNotCanEdit').removeAttr('onclick').removeClass('laydate-icon').end()
                            if (isUsedItem.indexOf('所有权人') >= 0) {
                                $('[name=ownershipName]').attr('readonly', true);
                            }
                        }

                        if (isView) {
                            $('#form').find('input[type=text],textarea').not('.viewCanEdit').attr('readOnly', 'true').end()
                                .find('select,input[type=radio],input[type=checkbox]').attr('disabled', 'disabled').end()
                                .find('.laydate-icon').removeAttr('onclick').removeClass('laydate-icon');
                            $form.find('.fnUpAttachBtn').remove();
                            $form.find('.fnUpAttachDel').remove();
                            // $('.map-location').hide();
                            setTimeout(function () {
                                $('body').find('select').attr('disabled', 'disabled');
                            }, 300);
                            if (!!res.isDebtAsset && res.isDebtAsset == 'true') {
                                $('.assetTypeFlag').removeClass('fn-hide')
                            };


                        };

                    } else {
                        Y.alert('提示', '加载失败！');
                    };
                } else {
                    Y.alert('提示', '无当前资产相关信息！');
                };
            }

        });

    } else {
        $selectRslt.on('click', 'p', function () {

            var $this = $(this);
            if (!$this.hasClass('loadType')) { //不是末级分类，请求下一级分类
                tableData($this);
                searchTimes = 0;
                dynamAddRules($form);
            }

        })
    };

    function tableData($ele) {

        var typeId = $ele.attr('typeId');

        var isCache = !!tempTableData[typeId]; //是否缓存
        var preLoadTime; //上次缓存时间

        if (isCache) { //如果有缓存，则读上次取缓存时间

            dynamicRemoveRules($form);
            preLoadTime = tempTableData[typeId].preLoadTime;
        }

        if (!!!preLoadTime) { //如果上次缓存时间未定义或为空
            preLoadTime = 0;
        } else {
            preLoadTime = parseInt(preLoadTime);
        }

        var nowTime = new Date().getTime();
        var pastTime = (nowTime - preLoadTime); //过期时间，5分钟为一个过期时间

        if (pastTime < 300000) { //是否过期，5分钟为一个过期时间

            addDataToPage(typeId);

            if (tempTableData[typeId].isMapPosition == 'IS') {
                $('.mapShow').removeClass('isMapHide');
            } else {
                $('.mapShow').addClass('isMapHide');
            }
            doChecked();
            initMap();
            dynamicRemoveRules($form);
            dynamAddRules($form);
        } else {

            var data = {
                levelOne: $ele.attr('levelOne'),
                levelTwo: $ele.attr('levelTwo'),
                levelThree: $ele.attr('levelThree')
            };

            util.ajax({

                url: dataUrl,
                type: 'post',
                data: data,
                success: function (res) {
                    if (res.success) {
                        var isRenderOver = renderTable(res, typeId);

                        if (isRenderOver) {

                            addDataToPage(typeId);
                            doChecked();
                            chooseRegion.init();
                            initMap();
                            dynamicRemoveRules($form);
                            dynamAddRules($form);

                        } else {
                            Y.alert('提示', '加载失败！');
                        };
                    } else {
                        Y.alert('提示', '加载失败！');
                    }

                }

            });

        };

    };

    function renderTable(data, typeId) { //数据渲染，并缓存到一个对象，方便下次读取

        if (!!!data || !!!typeId) return;

        tempTableData[typeId] = {}; //通过TYPEID缓存对象

        var renderOver = 0; //设置渲染参数
        var $tablePro = $('<table class="m-table-view tableData tableList"></table>'); //TABLE镜像
        var $trBoxPro = $('<tr></tr>'); //TR镜像
        var orderName = 'pledgeTypeAttributeOrders';
        var isLocationMapEdit; //缓存定位关联数据

        tempTableData[typeId].isMapPosition = data.info.isMapPosition; //缓存是否定位

        if (!!data.textInfoList) { //渲染文本信息

            var dataText = data.textInfoList;
            var $tableText = $tablePro.clone(true);
            var $trBox = $trBoxPro.clone(true);
            var i,
                sss = 0, //当前数据的实际奇偶
                idFlag1 = 1, //被关联标记
                idFlag2 = 1; //被关联标记

            var MTEXTVALUE = '', //多行文本标记
                MtextFlag = 0; //多行文本标记

            for (i = 0; i < dataText.length; i++) {

                var crnData = dataText[i];
                //fieldType: TEXT DATE NUMBER SELECT ADMINISTRATIVE_PLAN SELECT_CONTION_RELATION MTEXT
                var thisFieldType = crnData.fieldType;
                var thisFileName = (!!crnData.attributeKey) ? crnData.attributeKey : crnData.fieldName;
                var $templateType = $($('#' + thisFieldType).html()); //根据类型获取模板
                var thisAttributeValue = crnData.attributeValue;

                if (thisFieldType == 'TEXT') { //限制最大字数

                    $templateType.find('[name*="attributeValue"]').val(thisAttributeValue).attr('maxLength', crnData.controlLength);
                    if (isUsedItem.indexOf(thisFileName) >= 0) $templateType.find('[name*="attributeValue"]').addClass('isUsedItemNotCanEdit');

                };

                if (thisFieldType == 'NUMBER') { //数值型添加单位
                    // console.log(!!thisAttributeValue)
                    // var moneyUnit = ['角','分','元','千元','万','万元','百万','千万','亿','亿元','元/㎡','元/㎡/月'];
                    var thisNumVal = !!thisAttributeValue ? addNumSplitMark(thisAttributeValue) : thisAttributeValue;
                    $templateType.find('.unit').html(crnData.measurementUnit);
                    if (isView) {
                        $templateType.find('[name*="attributeValue"]').val(thisNumVal);
                    } else {
                        // if(moneyUnit.indexOf(crnData.measurementUnit) >= 0) $templateType.find('[name*="attributeValue"]').addClass('fnMakeMoney fnMakeMicrometer');
                        $templateType.find('[name*="attributeValue"]').val(thisNumVal).addClass('fnMakeMoney fnMakeMicrometer').attr('nMaxlength', '16');
                    };
                    if (isUsedItem.indexOf(thisFileName) >= 0) $templateType.find('[name*="attributeValue"]').addClass('isUsedItemNotCanEdit');

                };

                if (thisFieldType == 'SELECT') { //添加选择项，分割选项为SELECT

                    var str = formatStr(crnData.mostCompleteSelection);
                    var options = '';
                    var j;

                    for (j = 0; j < str.length; j++) {

                        if (str[j] == thisAttributeValue) {
                            options += '<option value="' + str[j] + '" selected="selected">' + str[j] + '</option>';
                        } else {
                            options += '<option value="' + str[j] + '">' + str[j] + '</option>';
                        }

                    };

                    if (j == str.length) {
                        $templateType.find('select').append(options);
                    }
                    if (isUsedItem.indexOf(thisFileName) >= 0) $templateType.find('select').addClass('isUsedItemNotCanEdit');

                };

                if (thisFieldType == 'DATE') { //日期型，限制日期
                    var nowTime = isAdd ? formatDate() : formatDate(data.info.rawAddTime);
                    var obj = '';
                    if (crnData.timeSelectionRange == 'SYSTEM_TIME_BEFORE') {
                        obj = "laydate({max:'" + nowTime + "'})";
                    } else if (crnData.timeSelectionRange == 'SYSTEM_TIME_AFTER') {
                        obj = "laydate({min:'" + nowTime + "'})";
                    } else {
                        obj = "laydate()";
                    };

                    // $templateType.find('.timeLimt');
                    $templateType.find('[name*="attributeVal"]').val(thisAttributeValue).attr('onclick', obj);
                    if (isUsedItem.indexOf(thisFileName) >= 0) $templateType.find('select').addClass('isUsedItemNotCanEdit');

                };

                if (thisFieldType == 'ADMINISTRATIVE_PLAN') { //行政区域，选择地区插件
                    $templateType.find('[name*="attributeVal"]').val(thisAttributeValue);
                    if (isUsedItem.indexOf(thisFileName) >= 0) $templateType.find('select').addClass('isUsedItemNotCanEdit');
                };
                // if (thisFieldType == 'MTEXT') { //多行文本
                //     $templateType.eq(0).attr('rowspan', crnData.conditionItem);
                //     MTEXTVALUE = thisFileName;
                //     MtextFlag++;
                // };

                if (thisFieldType == 'SELECT_CONTION_RELATION') { //条件关联，分割关联项为RADIO

                    var str = formatStr(crnData.conditionItem);
                    var radio = '';
                    // var radioClass1 = isView ? "radio-no radio-checked" : "radio-no radio-checked isRequiredIS",
                    //     radioClass2 = isView ? "radio-no" : "radio-no isRequiredIS";
                    var radioClass1 = false ? "radio-no radio-checked" : "radio-no radio-checked isRequiredIS",
                        radioClass2 = false ? "radio-no" : "radio-no isRequiredIS";
                    var j;
                    $templateType.find('[name*="attributeVal"]').val(thisAttributeValue);
                    for (j = 0; j < str.length; j++) {

                        if (!!thisAttributeValue) {

                            if (str[j] == thisAttributeValue) {

                                radio += '<i class="' + radioClass1 + '" value="' + str[j] + '"></i>' + str[j];
                            } else {
                                radio += '<i class="' + radioClass2 + '" value="' + str[j] + '"></i>' + str[j];
                            };

                        } else {

                            if (j == 0) {
                                radio += '<i class="' + radioClass1 + '" value="' + str[j] + '"></i>' + str[j];
                            } else {
                                radio += '<i class="' + radioClass2 + '" value="' + str[j] + '"></i>' + str[j];
                            };

                        };

                    };

                    if (j == str.length) {
                        $templateType.find('.radiosBox').append(radio);
                    };
                    if (isUsedItem.indexOf(thisFileName) >= 0) $templateType.find('select').addClass('isUsedItemNotCanEdit');

                };

                if (thisFileName == '抵质押率') {

                    var maxNum = (!!data.info.pledgeRate) ? data.info.pledgeRate : data.pledgeRate;
                    var props = isView ? {
                        'maxNumber': maxNum
                    } : {
                        'maxNumber': maxNum,
                        'placeholder': '不得超过当前分类抵质押率阈值' + maxNum + '%'
                    };
                    $templateType.find('[name*=attributeValue]').attr(props).addClass('hasMaxNum mortgagePre').val(crnData.attributeValue);

                }
                if (thisFileName == '评估价格' || thisFileName == '拟质押金额') {

                    $templateType.find('[name*=attributeValue]').addClass('prcieNum toZero');

                }
                if (thisFileName == '抵质价格') {

                    $templateType.find('[name*=attributeValue]').addClass('mortgageNum').attr('readOnly', true);
                    // $templateType.find('[name*=attributeValue]').addClass('mortgageNum toZero').attr('readOnly', true);

                }
                if (thisFileName.indexOf('坐落') >= 0 && !!!isLocationMapEdit) {

                    $templateType.find('[name*=attributeValue]').addClass('locationMapEdit');
                    isLocationMapEdit = thisAttributeValue;

                }

                //公共操作
                if (crnData.isRequired == 'NO') { //是否必填
                    $templateType.find('.m-required').html('').end()
                        .find('[name]').addClass('ignore');
                } else {
                    if (thisFieldType != 'MTEXT') {
                        $templateType.find('[name]').addClass('fn-validate');
                    }

                }

                if (crnData.isByRelation == 'IS') { //如果被关联，做标记

                    $templateType.eq(0).next().attr('byRelationid', crnData.fieldName + idFlag1);
                    idFlag2 = idFlag1;
                    idFlag1 = idFlag1 + 5;

                };

                if (!!crnData.relationConditionItem) { //如果有关联，关联的那一条数据的某个选项

                    $templateType.find('.attributeKey').addClass('rltItem').end()
                        .find('.fn-validate').addClass('will-validate').removeClass('fn-validate').end()
                        .eq(0).attr('torelationid', (crnData.relationFieldName + idFlag2 + crnData.relationConditionItem));

                };
                if (crnData.relationFieldName == MTEXTVALUE) {
                    $templateType.eq(0).next().attr('mtext', MTEXTVALUE + MtextFlag);
                }

                $templateType.find('[name*="attributeKey"]').val(thisFileName); //标记字段名称name，以便提交
                $templateType.find('[name*="attributeId"]').attr('value', crnData.attributeId); //标记字段名称name，以便提交
                $templateType.find('.attributeKey').html(thisFileName); //字段名称,用于展示

                if (thisFieldType == 'ADMINISTRATIVE_PLAN' || thisFieldType == 'SELECT_CONTION_RELATION' || thisFieldType == 'MTEXT' || !!crnData.relationFieldName) {
                    if (thisFieldType == 'MTEXT' || crnData.relationFieldName == MTEXTVALUE) {
                        sss--;
                    } else {
                        if ($trBox.find('td').length == 2) {
                            $trBox.append('<td class="fn-w150"></td><td></td>');
                            sss--;
                        };

                        if ($trBox.find('td').length != 0) {
                            $tableText.append($trBox);
                            $trBox = $trBoxPro.clone(true);
                        };

                        $trBox.append($templateType); //添加到tr

                        if (!!crnData.relationConditionItem) {
                            $trBox.addClass('fn-hide').find('td').eq(1).attr('colspan', '3');
                        };

                        $tableText.append($trBox);
                        $trBox = $trBoxPro.clone(true);
                        sss++;
                    }

                } else {

                    $trBox.append($templateType);

                    if (sss % 2 == 1) {
                        $tableText.append($trBox); //添加到table
                        $trBox = $trBoxPro.clone(true);
                    };

                };

                if (i == (dataText.length - 1) && $trBox.find('td').length != 0) {
                    $trBox.find('td').eq(1).attr('colspan', '3');
                    $tableText.append($trBox);
                };

                sss++;
            };

            if (i == dataText.length) {
                tempTableData[typeId].textInfoList = $tableText; //缓存数，以便下次调用
                renderOver++;
            }
        } else {

            tempTableData[typeId].textInfoList = $('<p class="fn-tac fn-fs20">暂无数据</p>');
            renderOver++;

        };

        if (!!data.mtextInfoList) { //渲染多行关联文本
            var dataMtext = data.mtextInfoList;
            var mark;

            if (!!!dataMtext) return;
            var obj = {};
            var kkk = -1;

            obj.lastRslt = lastRslt;
            obj.parentItem = {};
            obj.childrenItem = {};
            var tmpArry = []

            $.each(dataMtext, function (index, el) { //重新组装数据

                if (el.fieldType == "MTEXT") {

                    mark = index;
                    kkk++;

                    el.childrenTitle = [];
                    obj.childrenItem[kkk] = [];
                    obj.childrenItem[kkk].arryss = [];
                    obj.parentItem[kkk] = el;

                } else {
                    var preItem = dataMtext[index - 1];
                    var preItemType = preItem.fieldType;
                    var tmpLength = !el.attributeValue ? 0 : el.attributeValue.split('&&').length;

                    obj.parentItem[kkk].childrenTitle.push(el.fieldName);
                    obj.childrenItem[kkk].push(el);

                    if (preItemType == "MTEXT") {
                        dataMtext[mark].childrenLengthtmp = tmpLength;
                    } else {

                        if (dataMtext[mark].childrenLengthtmp < tmpLength) {
                            dataMtext[mark].childrenLengthtmp = tmpLength;
                        }
                    }

                    if (!_.isArray(tmpArry[kkk])) {
                        tmpArry[kkk] = [];
                    }
                    tmpArry[kkk].push(el.attributeValue);

                }

            });

            $.each(tmpArry, function (index, el) {
                obj.childrenItem[index].arryss = _.transform(el, function (r, n) {
                    if (!n) {
                        n = '';
                        for (var xx = 1; xx < obj.parentItem[index].childrenLengthtmp; xx++) {
                            n += '&&';
                        }
                    };
                    var tmpData = _.split(n, '&&');
                    _.map(tmpData, function (val, key) {
                        if (!_.isArray(r[key])) {
                            r[key] = [];
                        }
                        r[key].push(val);
                    });
                }, []);
            });
            // console.log(obj)
            var html = template('templateTest', obj);
            tempTableData[typeId].mtextInfoList = html; //缓存数，以便下次调用

        }

        //渲染图像信息
        if (!!data.imageInfoList) {

            var dataImg = data.imageInfoList;
            var $tableImg = $tablePro.clone(true);
            var $trBox = $trBoxPro.clone(true);
            var i;
            // console.log(data.imageInfoList)
            for (i = 0; i < dataImg.length; i++) {

                var crnData = dataImg[i];
                var $templateType = $($('#IMAGES_INFO').html());
                var upLoadFiletype = [];
                var thisFileName = (!!crnData.attributeKey) ? crnData.attributeKey : crnData.fieldName;

                //公共操作
                if (crnData.isRequired == 'NO') { //是否必填
                    $templateType.find('.m-required').html('');
                } else {
                    // $templateType.find('[name]').addClass('lessOneItem fn-validate');
                    $templateType.find('[name*=attributeImageText],[name*=attributeValue]').addClass('lessOneItem fn-validate');
                    // $templateType.find('.leesOneItemInput').addClass('fn-validate').attr('id','filesUp' + i);
                }

                $templateType.find('[name*="attributeKey"]').val(thisFileName).end()
                    .find('.attributeKey').html(thisFileName).end()
                    .find('[name*="attributeId"]').val(crnData.attributeId).end()
                    .find('.fnUpAttachBtn').attr('filetype', util.swtchFileType(crnData.attachmentFormat));
                if (!!crnData.attributeImageText) $templateType.find('[name*="attributeImageText"]').val(crnData.attributeImageText);
                if (!isAdd) {

                    var resStr = crnData.attributeValue; //文件名
                    if (!!resStr) {
                        resStr = resStr.split(';');


                        $.each(resStr, function (index, ele) {
                            var newStr = resStr[index].split(',');
                            var fileInfo = {};
                            fileInfo.fileName = newStr[0];
                            fileInfo.serverPath = newStr[1];
                            fileInfo.fileUrl = newStr[2];
                            upFileCallBack($templateType.find('.fnUpAttach'), fileInfo);
                        });
                    }

                };

                $trBox.append($templateType);

                if (i % 2 == 1) {
                    $tableImg.append($trBox); //添加到table
                    $trBox = $trBoxPro.clone(true);
                };

                if (i == (dataImg.length - 1) && $trBox.find('td').length != 0) {
                    $trBox.append('<td class="fn-w150"></td><td></td>');
                    $tableImg.append($trBox);
                };

            };

            if (i == dataImg.length) {
                tempTableData[typeId].imageInfoList = $tableImg; //缓存数，以便下次调用
                renderOver++;
            };

        } else {
            tempTableData[typeId].imageInfoList = $('<p class="fn-tac fn-fs20">暂无数据</p>');
            renderOver++;
        };

        //渲染网络信息

        if (data.info.isMapPosition == 'IS' || data.isMapPosition == 'IS') { //是否有定位
            var $tableMap = $($('#ISMAPPOSITION').html());
            $('.mapShow').removeClass('isMapHide');
            // $tableMap.find('[name="longitude"],[name="latitude"]').addClass('fn-validate');thisAttributeValue
            if (!!data.info.searchKey) {
                $tableMap.find('[name="searchKey"]').attr('placeholder', data.info.searchKey);
            } else {
                $tableMap.find('[name="searchKey"]').attr('placeholder', isLocationMapEdit);
                $tableMap.find('#mapSearch').addClass('mapSearchClickBtn');
            }

            if (!!data.info.longitude && !!data.info.longitude) {
                $tableMap.find('#longitude').attr('value', data.info.longitude);
                $tableMap.find('#latitude').attr('value', data.info.latitude);
                // .find('.locationMapPoint').val(data.info.longitude + ',' + data.info.latitude);
            }

            tempTableData[typeId].tableMapInfoList = $tableMap; //缓存数，以便下次调用

        } else {
            $('.mapShow').addClass('isMapHide');
            tempTableData[typeId].networkInfoList = '<p class="fn-tac fn-fs20 fn-pb20">暂无关联地图信息</p>';
        };

        if (!!data.networkInfoList) { //是否有地址

            var dataNetwork = data.networkInfoList;
            var $tableNetwork = $('<div></div>');
            var $tableNetworkBox = $('#networkInfo');
            var i;

            $tableNetworkBox.find('.link-list').html(''); //清空上次数据

            for (i = 0; i < dataNetwork.length; i++) {

                var thisWebsiteAddr = (!!dataNetwork[i].attributeValue) ? dataNetwork[i].attributeValue : dataNetwork[i].websiteAddr;
                var thisWebsiteName = (!!dataNetwork[i].attributeKey) ? dataNetwork[i].attributeKey : dataNetwork[i].websiteName;

                thisWebsiteAddr = (thisWebsiteAddr.indexOf('http://') > -1) ? thisWebsiteAddr : ('http://' + thisWebsiteAddr);

                var $templateType = $('<div class="fn-pb10" orderName="pledgeTypeAttributeOrders"><a href="' + thisWebsiteAddr + '" target="_blank">' + thisWebsiteName + '</a>' +
                    '<input type="hidden" name="attributeKey" value="' + thisWebsiteName + '">' +
                    '<input type="hidden" name="attributeValue" value="' + thisWebsiteAddr + '">' +
                    '<input class="ui-text fn-w200" type="hidden" name="customType" value="NETWORK" ></div>');
                if (!isAdd) {
                    $templateType.append('<input class="ui-text fn-w200" type="hidden" name="attributeId" value="' + dataNetwork[i].attributeId + '" >');
                };

                $tableNetwork.append($templateType);

            };

            if (i == dataNetwork.length) {
                tempTableData[typeId].networkInfoList = $tableNetwork; //缓存数，以便下次调用
                renderOver++;
            };

        } else {
            tempTableData[typeId].networkInfoList = $('<p class="fn-tac fn-fs20 fn-pb40">暂无关联网站信息</p>');
            renderOver++;
        };

        //渲染视频信息
        if (!!data.info.ralateVideo && isView) {

            $('#videofame').attr({
                'src': $('#videofame').attr('basesrc') + '?' + encodeURI(data.info.ralateVideo),
                'height': '500px'
            });

        } else {
            // $('#videoInfos').html('<p class="fn-tac fn-fs20">暂无数据</p>');
        };

        //根据渲染状态返回值
        if (renderOver == 3) { //渲染完成
            tempTableData[typeId].preLoadTime = new Date().getTime();
            return true;
        } else {
            return false;
        };

    };

    function addDataToPage(typeId) {
        $('#form').removeAttr('style');
        $('#textInfoList').html(tempTableData[typeId].textInfoList).append(tempTableData[typeId].mtextInfoList);
        $('#imageInfoList').html(tempTableData[typeId].imageInfoList);
        $('#networkInfoList').find('.link-list').html(tempTableData[typeId].networkInfoList).end().find('.isMapLocation').html(tempTableData[typeId].tableMapInfoList);
        // $('#networkInfoList').find('.link-list').html('').end().find('.isMapLocation').html(tempTableData[typeId].tableMapInfoList);
        $('#networkInfo').removeAttr('style');
    };

    // 2016.12.05 放置 util.js 内
    // function swtchFileType(str) { //转换文件类型

    //     var upLoadFiletype = [];
    //     var fileType = {
    //         // 'img':'jpg,png,jpeg,bmp,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw',
    //         // 'excle':'xlsx,xls',
    //         // 'word':'doc,docx',
    //         // 'pdf':'pdf',
    //         // 'video':'mp4,avi,3gp,flv,mkv,mpg,mpeg',
    //         // 'audio':'mp3,wmv',
    //         // 'common':'zip,7z,rar,cab,iso,gz,bz2,tar,apk'
    //         'img': '*.jpg,*.png,*.jpeg,*.bmp,*.tiff,*.pcx,*.tga,*.exif,*.fpx,*.svg,*.psd,*.cdr,*.pcd,*.dxf,*.ufo,*.eps,*.ai,*.raw',
    //         'excle': '*.xlsx,*.xls',
    //         'word': '*.doc,*.docx',
    //         'pdf': '*.pdf',
    //         'video': '*.mp4,*.avi,*.3gp,*.flv,*.mkv,*.mpg,*.mpeg',
    //         'audio': '*.mp3,*.wmv',
    //         'common': '*.zip,*.7z,*.rar,*.cab,*.iso,*.gz,*.bz2,*.tar,*.apk' //默认格式
    //     }

    //     if (!!!str) {
    //         $.each(fileType, function(index, el) {
    //             upLoadFiletype.push(el);
    //         });
    //     } else {
    //         var newStr = str.split(',');
    //         newStr.push('common');
    //         $.each(newStr, function(index, el) {
    //             upLoadFiletype.push(fileType[el]);
    //         });
    //     };
    //     upLoadFiletype = upLoadFiletype.join(',').split(',').join(';'); //转成成字符串，分割成数组，分号链接转化成字符串
    //     return upLoadFiletype;
    // };

    function formatStr(str) { // 去除连续逗号，首尾逗号，多余空格，输出"aa,bb,cc,dd格式字符串"

        var lostSpace = /[ ]/g; //匹配所有空格
        var lostContinueComma = /,{1,}/g; //去除连续逗号，保留一个
        var lostRLComma = /(^\,*)|(\,*$)/g; //去除首尾逗号
        var tmp = str.replace(lostSpace, '').replace(lostContinueComma, ',').replace(lostRLComma, '');
        tmp = str.indexOf(',') >= 0 ? tmp.split(',') : str.indexOf('，') >= 0 ? tmp.split('，') : tmp;

        return tmp;
    };

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

    //////----还原上传组件 start
    function totalVal($fnUpAttachBox) {

        //统计当前上传后的链接
        var _arr = [];

        $fnUpAttachBox.find('.fnAttachItem').each(function (index, el) {
            _arr.push($(this).attr('val'));
        });

        $fnUpAttachBox.find('.fnUpAttachVal').val(_arr.join(';')).attr('length', _arr.length).next('.radioSpan').hide(); //统计个数，并以数字显示，此处在隐藏域
        $fnUpAttachBox.find('.fnUpAttachVal').trigger('blur');
        $fnUpAttachBox.parents('td').find('.upFilesNumber').html(_arr.length); //资产新增边边页面同级附件个数，并显示

    };

    function upFileCallBack($fnUpAttachBox, res) {
        //上传后的回调
        var _html = '';
        var _$fnUpAttachUl = $fnUpAttachBox.find('.fnUpAttachUl'),
            isCanDownload = $fnUpAttachBox.find('.fnUpAttachBtn').attr('todownload') || false, //是否开启下载功能
            isImg = util.isImg(res.fileName), //文件名，判断是否是图片
            isOffice = !isImg ? judgeFiletype(res.fileName) == 'office' : false,
            isPPT = (!isImg && !isOffice) ? judgeFiletype(res.fileName) == 'ppt' : false,
            _more = '';

        if (!!isOffice) {

            _more = '<a class="attach-item-preview" target="_blank" href="/projectMg/contract/excelMessage.htm?contractItemId=0&read=read&hidTitle=hidTitle&fileName=' + encodeURIComponent(res.fileName) + '&fileUrl=' + res.serverPath + '&id=0">预览</a>'

        }

        if (isPPT) {
            _more = '<a class="attach-item-preview" target="_blank" href="' + res.fileUrl + '">预览</a>'
        }
        // console.log('isImg:' + isImg)
        // console.log('isOffice:' + isOffice)
        //图片
        var _html1 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '" title="' + res.fileName + '"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        //一般文件,开启下载
        var _html2 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a title="点击下载' + res.fileName + '" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        //未开启下载
        var _html4 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        //offfice || ppt
        var _html3 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a title="点击下载' + res.fileName + '" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0" target="_blank"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span>' + _more + '</span>';

        // _html = (!!isCanDownload && !isImg) ? _html2 : _html1; //下载开启(isCanDownload=true)并且不为图片(isImg=false)
        // _html = isImg ? _html1 : isOffice ? _html3 : isCanDownload ? _html2 : _html4;
        _html = isImg ? _html1 : (isOffice || isPPT) ? _html3 : _html2;

        _$fnUpAttachUl.append(_html);

        totalVal($fnUpAttachBox);
    };
    function judgeFiletype(fileName) { //判断是否是office或者PPt文件
        var _fileName = fileName || '',
            _fileNameArr = _fileName.split('.'),
            _type = _fileNameArr[(_fileNameArr.length - 1)].toLowerCase(), //文件名.后面最后为文件类型
            fileType1 = ['doc', 'docx', 'xlsx', 'xls'],
            fileType2 = ['ppt', 'pptx'],
            _fileTypeRslt = false;
        if(fileType1.indexOf(_type) >= 0) {
            _fileTypeRslt = 'office';
        }else if(fileType2.indexOf(_type) >= 0){
            _fileTypeRslt = 'ppt'
        }else {
            _fileTypeRslt = false;
        };
        return _fileTypeRslt;
    };
    //////----上传组件还原 end

    // ------ 数组Name命名 start
    function resetNameAll($ele) {

        if (!!!$ele) {
            $ele = $('.tableList');
        };

        var temp = 0;
        $('.textInfoList .tableList').not('.specialTbale').find('td[ordername]').each(function (i, ele1) { //td上的ordername
            var $thisWrap = $(this),
                _orderName = $thisWrap.attr('orderName'),
                index = parseInt(i / 2);
            // if ($thisWrap.hasClass('toMoreLine')) temp++ ;

            $thisWrap.find('[name]').each(function () {

                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.lastIndexOf('.') + 1);
                };

                name = _orderName + '[' + index + '].' + name;
                _this.attr('name', name);

            });
            temp = index;

        });

        $('.specialTbale').find('td[ordername]').each(function (i, el) {
            temp++;
            var _orderName = $(this).attr('orderName'),
                input = $(this).find('[name]');

            input.each(function (j, ele) {

                var name = $(this).attr('name');
                name = _orderName + '[' + temp + '].' + name;
                $(this).attr('name', name);
            });

        });

        $('.imageInfoList').find('[orderName]').each(function (j, ele2) {

            var _this = $(this),
                _orderName = _this.attr('orderName');

            _this.find('[name]').each(function (index, el) {

                var name = $(this).attr('name');
                var index = parseInt(j / 2) + temp + 1;

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.lastIndexOf('.') + 1);
                };

                name = _orderName + '[' + index + '].' + name;
                $(this).attr('name', name);

            });

        });
    };

    function orderNameIngron() {

        $('[orderNameListIngro]').each(function (j, ele2) {

            var _this = $(this),
                _orderName = _this.attr('orderNameListIngro');

            _this.find('[name]').each(function (index, el) {

                var name = $(this).attr('name');
                var i = j + index;
                if (name.indexOf('.') > 0) {
                    name = name.substring(name.lastIndexOf('.') + 1);
                };

                name = _orderName + '[' + j + index + '].' + name;
                $(this).attr('name', name);

            });

        });
        dynamAddRules();
    }

    function getPoint(e) {
        var center = e.point;
        var eX = center.lng,
            eY = center.lat;
        $('#longitude').val(eX);
        $('#latitude').val(eY);
        if (!$('#allmap').hasClass('disClickToSearch')) setPonit(eX, eY);
    }

    function mapLocation() { // 用经纬度设置地图中心点

        var longitude = $('#longitude').val();
        var latitude = $('#latitude').val();
        if (!!!longitude || !!!latitude) { //如果出错则跳到默认经纬度
            longitude = initMapPonit[0];
            latitude = initMapPonit[1];
        };
        if (longitude != initMapPonit[0] && latitude != initMapPonit[1]) firstClick = false;
        setPonit(longitude, latitude)
    };

    function initMap() {
        setTimeout(function () {
            if (isAdd) {
                map.clearOverlays();
            }
            $('#mapLocationSearch').trigger('click');
            // map.addEventListener("click", getPoint); //给地图添加事件一个点击事件
            ac.addEventListener("onconfirm", function (e) { //鼠标点击下拉列表后的事件
                var _value = e.item.value;
                var searchVal = _value.province + _value.city + _value.district + _value.street + _value.business;
                $('#keySearch').val(searchVal);
                setPlace(searchVal);
            });
        }, 100);
    }

    function setPonit(lng, lat) { //经纬度定位
        // var new_zoomSize = (firstClick && searchTimes == 0) ? zoomSize1 : zoomSize2;
        if (firstClick) map.clearOverlays();
        var new_point = new BMap.Point(lng, lat);
        var marker = new BMap.Marker(new_point); // 创建标注
        map.centerAndZoom(new_point, 18);
        map.addOverlay(marker); // 将标注添加到地图中
        map.panTo(new_point);
    }

    function setPlace(keyVals) { //keyVals搜索关键词
        map.clearOverlays(); //清除地图上所有覆盖物
        function myFun() {
            if (!!!local.getResults() || !!!local.getResults().getPoi(0)) {
                // Y.alert('提示', '没有匹配地点，请更换关键词重新搜索！', function() {
                //
                // })
                $('.locationMapPoint').val('');
                return;
            }
            var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
            map.centerAndZoom(pp, 18);
            map.addOverlay(new BMap.Marker(pp)); //添加标注

            $('#longitude').val(pp.lng);
            $('#latitude').val(pp.lat);
            $('#allmap').addClass('disClickToSearch');
        }

        var local = new BMap.LocalSearch(map, { //智能搜索
            renderOptions: {
                map: map
            },
            onSearchComplete: myFun
        });
        local.search(keyVals);
    };

    function addNumSplitMark(num) {
        num = num.split('').reverse().join('').replace(/(\d{3}(?=\d)(?!\d+\.|$))/g, '$1,').split('').reverse().join('');
        return num;
    }

    function doChecked($ele) { //修正RADIO的NAME值相同冲突问题,当到最后

        if (!!!$ele) {
            $ele = $('body');
        };

        resetNameAll();
        $ele.find('.radio-checked').trigger('click');
        // console.log($ele.find('.isRequiredIS'))
        if (isView) $ele.find('.isRequiredIS').parents('.radiosBox').attr('status', 'true');

    };



    $('#submit').on('click', function () { //提交前验证
        if (!$form.valid()) return;
        var _isPass = true,
            _isPassEq,
            maxNum,
            $thisError;

        var $validateList = $('[name].fn-validate,.maxTextInput.fn-validate').not('.leesOneItemInput,.ignore');

        $validateList.each(function (index, el) {
            var _this = $(this);
            var thisVal = '';
            var thisMaxNumFlag = _this.hasClass('hasMaxNum');
            // console.log(mapLocationFlag)
            if (thisMaxNumFlag) maxNum = parseInt(_this.attr('maxNumber'));

            if (_this.hasClass('fileTypeRadio')) {
                thisVal = _this.attr('value');
            } else if (_this.hasClass('lessOneItem')) {
                var _lessOneItem = _this.parents('td').find('.lessOneItem');
                _lessOneItem.each(function (i, e) {
                    thisVal += e.value;
                });
            } else {
                thisVal = _this.val();
            };
            if ((!!!thisVal.replace(/\s/g, '')) || typeof (thisVal) == 'undefined' || thisVal.length == 0 || thisVal == 'NONE' || (thisMaxNumFlag && thisVal > maxNum)) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
            }
        });

        if (!_isPass) {

            $thisError = $validateList.eq(_isPassEq);

            if ($thisError.hasClass('assetType')) { //先选择资产分类

                Y.alert('提醒', '请先选择资产分类！', function () {
                    $('.selectFn').focus()
                });

                return;
            };

            if ($thisError.hasClass('hasMaxNum')) {

                Y.alert('提醒', '当前分类抵质押率阈值为' + maxNum + '%,不得超过阈值', function () {
                    $thisError.focus();
                });

                return;
            };
            if ($thisError.hasClass('lessOneItem')) {
                // console.log($thisError)
                Y.alert('提醒', '附件上传和文本至少选择一种！', function () {
                    $thisError.parents('td').find('.lessOneItem').eq(0).focus();
                });

                return;
            };

            Y.alert('提醒', '请把表单填写完整', function () {

                if ($thisError.hasClass('fnUpAttachVal')) {

                    $thisError.parents('td').find('.focusInput').focus();

                } else if ($thisError.hasClass('adressCode')) {

                    $thisError.parents('td').find('select:last').focus();

                } else {

                    $thisError.val('').focus();

                }
            });
            return;
        }

        // var _lessOneItemPass = true;
        // var itemsIndex = -1;
        //
        // $('.fnLessOneItem').each(function(index1, el1) {
        //
        //     var _has = false;
        //     $(this).find('input.lessOneItem').each(function(index2, el2) {
        //         if (!!el2.value.replace(/\s/g, '')) {
        //             _has = true;
        //             return false;
        //         }
        //     });
        //
        //     if (!_has) {
        //         _lessOneItemPass = false;
        //         itemsIndex = index1;
        //         return false;
        //     };
        //
        // });
        //
        // if (!_lessOneItemPass) {
        //     Y.alert('提醒', '附件上传和文本至少选择一种！',function () {
        //         if(itemsIndex === -1) return;
        //         $('.fnLessOneItem').eq(itemsIndex).find('.lessOneItem').eq(0).focus();
        //     });
        //     return;
        // }

        if (toReviews) {
            submitReviews();
        } else {
            submitAddAseet();
        }

    });

    function submitAddAseet() { //新增资产提交
        var isViewStr = isView ? '?isView=true' : '';
        util.ajax({
            url: '/assetMg/save.htm' + isViewStr,
            type: 'post',
            data: $('#form').serializeJCK(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提醒', res.message, function () {
                        if ($('input[name=isInvestigation]').val() == 'YES') {
                            var formId = 400;
                            var itype = $('input[name=itype]').val();
                            window.location.href = '/projectMg/investigation/edit.htm?formId=' + formId + '&aid=' + res.id + '&itype=' + itype;
                        } else {
                            window.location.href = '/assetMg/list.htm';
                        }
                    });

                } else {
                    Y.alert('提醒', res.message);
                }
            }

        });
    };

    function submitReviews() { //复评意见提交

        util.ajax({
            url: '/projectMg/assetReview/saveRemark.json',
            type: 'post',
            data: $('#toReviews').serializeJCK(),
            success: function (res) {
                if (res.success) {
                    Y.alert('提醒', res.message, function () {
                        window.location.href = '/projectMg/assetReview/edit.htm?id=' + $('#arid').val();
                    });
                } else {
                    Y.alert('提醒', res.message);
                }
            }

        });
    };

})