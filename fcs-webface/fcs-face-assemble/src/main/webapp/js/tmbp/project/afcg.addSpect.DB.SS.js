define(function (require, exports, module) {

    require('validate');
    require('Y-msg');
    require('input.limit');
    require('zyw/upAttachModify');

    var md5 = require('md5');
    var util = require('util');
    var getList = require('zyw/getList');

    // ------ 客户资料 start

    var DIY_AJAX = {}

    $('#stepCust').remove()

    if (document.getElementById('fnIsEnterpriseInfo')) {
        require.async('tmbp/customer/corporate.add', function (obj) {
            obj.publicOPN.$back.remove()
            obj.publicOPN.remove()
        })
    }

    if (document.getElementById('fnIsIndividualInfo')) {
        require.async('tmbp/customer/personal.add', function (obj) {
            obj.publicOPN.$back.remove()
            obj.publicOPN.remove()
        })
    }

    var contrastTool = require('zyw/changeApply');

    var ALLDATALIST, // 初始值
        $fnChangeForm = $('#fnChangeForm'),
        $fnCustomerForm = $('#form'), // 客户资料
        ORIGINAL_PAGE = ''; // 未修改的查看页面 

    DIY_AJAX.doSaveCustomer = function (callback) {

        var $_form = $fnCustomerForm,
            _isPass = 0;

        if ($_form.valid()) {
            _isPass = 1;
        }

        var _html = contrastTool.getHtml('#form')
            .replace(/(industryBox|addressBox)/g, '') // 去除行业、地区
            .replace(/<a(\w|"|=|\s|\;|\(|\)|\:|\-)*>删除行<\/a>/g, '-') // 去除删除
            .replace(/apply-upfile-img fnUpFile/g, 'apply-upfile-img nomt') // 图片上传换查看
            .replace(/<span(\w|"|=|\s|\;|\(|\)|\:|\-)*>\*<\/span>/g, '') // 必填标志
            .replace(/<div class=\"fn\-mt5\">\s*<p>\(非三证合一证件号码，请填写组织机构代码\)<\/p>\s*<\/div>/g, '')

        _html = encodeURIComponent(util.trimHtml(_html))

        var $fromCopy = $('<form></form>').html($fnCustomerForm.html()),
            $fromCopyNames = $fromCopy.find('[name]')

        $fnCustomerForm.find('[name]').each(function (index, el) {

            var type = (el.tagName == 'INPUT') ? el.type : el.tagName
            var $input = $fromCopyNames.eq(index)

            switch (type) {

                case 'text':
                case 'hidden':
                    $input.attr('value', el.value);
                    break;

                case 'TEXTAREA':
                    $input.html(el.value);
                    break;

                case 'TEXTAREA':
                    $input.attr('value', el.value);
                    break;

                case 'radio':
                case 'checkbox':
                    if (el.checked) {
                        $input.attr('checked', 'checked');
                    } else {
                        $input.removeAttr('checked');
                    }
                    break;
                case 'SELECT':
                    $input.find('option').removeAttr('selected')
                        .end().find('option[value="' + el.value + '"]').attr('selected', 'selected');
                    break;

            }

        });

        $fromCopy.find('#industryBox').html('')
            .end().find('#addressBox').html('')

        var $postForm = $('<form></form>')
            .append('<input type="hidden" value="' + document.getElementsByName('formId')[0].value + '" name="formId">') // 表单id
            .append('<input type="hidden" value="' + document.getElementsByName('customerId')[0].value + '" name="customerId">') // 客户id
            .append('<input type="hidden" value="' + document.getElementsByName('customerName')[0].value + '" name="">') // 客户名称
            .append('<input type="hidden" value="' + encodeURIComponent($fromCopy.html()) + '" name="editHtml">') // 编辑的html
            .append('<input type="hidden" value="' + ORIGINAL_PAGE + '" name="">') // 原始查看页面
            .append('<input type="hidden" value="' + _isPass + '" name="status">') // 是否通过
            .append('<input type="hidden" value="' + _html + '" name="viewHtml" >') // 修改后的查看页面
            .append('<input type="hidden" value="' + encodeURIComponent($fnCustomerForm.serialize()) + '" name="formData" >') // 修改后的表单数据

        util.ajax({
            url: '/projectMg/afterwardsCheck/saveCustomer.json',
            data: $postForm.serialize(),
            success: function (res) {

                if (callback) callback()

            }
        })

    }

    $('#submitCustomer').on('click', function () {
        submitForm()
    })

    // ------ 客户资料 end

    // ------ 项目基本情况 start

    var $formJBQK = $('#khjbqk'),
        requiredRules = {
            rules: {},
            messages: {}
        };

    util.setValidateRequired($('.fnInput'), requiredRules)

    $formJBQK.validate($.extend(true, util.validateDefault, {
        rules: requiredRules.rules,
        messages: requiredRules.messages
    }));

    $formJBQK.on('click', '.fnNext', function () {

        if ($formJBQK.valid()) {
            $step.find('li').eq(1).trigger('click')
        } else {
            Y.alert('提示', '请把表单填写完整')
        }

    })

    // ------ 项目基本情况 end

    // ------ 切换、暂存、保存数据 start

    var formMd5Obj = {};

    var $step = $('#step'), // 顶部导航
        $fnStep = $('.fnStep'), // 表单集合
        nextIndex = util.getParam('nextIndex'), //第一次保存的锚点
        lastFTFID = $step.find('li.active').attr('ftf'); //上一次的标记

    //--- 绑定事件
    $step.on('click', 'li:not(.active)', function () {
        //点击保存切换或切换
        var _this = $(this);

        //记录锚点
        nextIndex = _this.index();

        if (!!document.getElementById('fnIsViewPage')) {
            lastFTFID = _this.attr('ftf');
            _this.addClass('active').siblings().removeClass('active');
            $fnStep.addClass('fn-hide');
            $('#' + lastFTFID).removeClass('fn-hide');
            return
        }

        saveForm(lastFTFID, function () {
            lastFTFID = _this.attr('ftf');
            _this.addClass('active').siblings().removeClass('active');
            $fnStep.addClass('fn-hide');
            $('#' + lastFTFID).removeClass('fn-hide');
        });

    });

    //--- 初始化操作
    if (nextIndex) {
        setTimeout(function () {
            // $step.find('li').eq(nextIndex).trigger('click');
            $step.find('li').removeClass('active').eq(nextIndex).addClass('active');
            $fnStep.addClass('fn-hide').eq(nextIndex).removeClass('fn-hide');
            lastFTFID = $step.find('li.active').attr('ftf');
        }, 0);
    }

    setTimeout(function () {
        setFormMd5Obj();
    }, 500);

    //--- 首先缓存md5
    function setFormMd5Obj() {

        //重命名
        util.resetName();

        $fnStep.each(function (index, el) {
            var _this = $(this),
                _id = _this.attr('id')

            if (!_this.is('form')) {
                _this = _this.find('form')
            }

            formMd5Obj[_id] = md5(_this.serialize())
        });

    }

    //--- MD5是否已经变化
    /**
     * [isFormMd5Changed 对比缓存表单md5值是否变化了]
     * @param  {string}  key        [表单标记，一般情况使用id]
     * @param  {string}  param      [序列化后的字符串]
     * @return {Boolean}            [true:改变了，false:未改变]
     */
    function isFormMd5Changed(key, param) {

        // return (md5(param) != formMd5Obj[key]) ? true : false;
        return true

    }

    //--- 保存某个表单
    /**
     * [saveForm 保存表单，同时验证表单是否合法]
     * @param  {[type]}   key      [操作表单的标记]
     * @param  {Function} callback [保存表单后的回调]
     * @return {[type]}            [description]
     */
    function saveForm(key, callback) {

        var _$form = $('#' + key); //需要保存的表单
        var _isDiySaveKey = ''; // 自定义放回的键名

        if (!_$form.is('form')) {
            _isDiySaveKey = _$form.attr('diyajax')
            _$form = _$form.find('form')
        }

        //重命名
        util.resetName();

        if (_$form.valid()) {
            _$form.find('input[name="checkStatus"]').val('1');
        } else {
            _$form.find('input[name="checkStatus"]').val('0');
        }

        if (isFormMd5Changed(key, _$form.serialize())) {

            //--变化，保存
            if (!!_isDiySaveKey) {
                DIY_AJAX[_isDiySaveKey](callback);
            } else {
                saveFormAjax(key, _$form, callback);
            }

        } else {

            //--没有变化，直接回调
            if (callback) {
                callback();
            }

        }
    }

    //保存表单时的ajax请求
    /**
     * [saveFormAjax 保存表单时的ajax请求，并且通过formid判断是否是初次保存]
     * @param  {[type]}   key      [操作表单的标记]
     * @param  {[type]}   _$form   [操作表单的jQuery对象]
     * @param  {Function} callback [保存表单后的回调]
     * @return {[type]}            [description]
     */
    function saveFormAjax(key, _$form, callback) {

        if (!!!document.getElementById('projectCode').value) {
            Y.alert('提示', '请选择项目')
            return
        }

        var _isFirstsave = !!!_$form.find('[name="formId"]').val() ? true : false;

        util.ajax({
            url: _$form.attr('action'),
            data: _$form.serialize(),
            success: function (res) {
                if (res.success) {
                    //如果是初次储存
                    if (_isFirstsave) {
                        window.location = '/projectMg/afterwardsCheck/editLitigation.htm?formId=' + res.form.formId + '&nextIndex=' + nextIndex;
                    }
                    // 更新 缓存md5
                    formMd5Obj[key] = md5(_$form.serialize());

                    if (callback) {
                        callback();
                    }
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });
    }

    // ------ 切换、暂存、保存数据 end

    //------ 触发提交表单 start

    function submitForm() {

        saveForm($step.find('li.active').attr('ftf'), function () {

            Y.confirm('提示', '确认提交当前表单？', function (opn) {

                if (opn == 'yes') {

                    var _isPass = true,
                        _isPassEq;

                    // 验证所有表单
                    $('.m-main form').each(function (index, el) {

                        if (!$(this).valid()) {
                            _isPass = false
                            _isPassEq = index
                            return false;
                        }

                    });

                    if (!_isPass) {
                        Y.alert('提示', '第' + (_isPassEq + 1) + '个页签内容填写有误', function () {
                            $step.find('li').eq(_isPassEq).trigger('click')
                        })
                        return
                    }

                    var formId = $('[name=formId]').eq(0).val();

                    util.postAudit({
                        formId: formId
                    }, function () {
                        window.location.href = '/projectMg/afterwardsCheck/list.htm';
                    }, function () {})

                }

            });

        })

    }

    //------ 触发提交表单 end

    //------ 选择项目 start 
    var _getList = new getList();
    _getList.init({
        title: '项目列表',
        ajaxUrl: '/projectMg/afterwardsCheck/queryProjects.json?select=my&busiType=211',
        btn: '#chooseBtn',
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
                '项目编号：',
                '<input class="ui-text fn-w160" name="projectCode" type="text">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" name="customerName" type="text">',
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
            item: 8
        },
        callback: function ($a) {
            window.location = "/projectMg/afterwardsCheck/editLitigation.htm?projectCode=" + $a.attr('projectCode');
        }
    });
    //------ 选择项目 end


    // 风险查询 信息、相似、失信
    var riskQuery = require('zyw/riskQuery');
    var initRiskQuery = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');
    if (document.getElementById('fnRisk')) {
        $('#fnRisk').on('click', function () {

            // 查询风险信息
            var _isP = ($('#customType').val() === 'ENTERPRISE') ? false : true,
                _getXX = _isP ? false : true, // 个人无相似、信息
                // _getSX = _isP ? ((document.getElementById('fnCertType').value === 'IDENTITY_CARD') ? true : false) : true; // 个人 身份证 失信
                _getSX = true; // 个人 身份证 失信

            initRiskQuery.getAllInfo(_getXX, _getSX, _getXX, function (html) {
                document.getElementById('fnRiskBox').innerHTML = html
            })

            // initRiskQuery.getAllInfo(true, true, true, function (html) {
            //     document.getElementById('fnRiskBox').innerHTML = html
            // })

        })
    }

    //------ 侧边栏 start

    var publicOPN = new(require('zyw/publicOPN'))();

    var hasRiskReport = $('#hasRiskReviewReport').val();

    if ('YES' == hasRiskReport) {
        publicOPN.addOPN([{
            name: '风险审核报告',
            url: '/projectMg/riskreview/viewReview.htm?formId=' + $('#amendRecordId').val(),
        }]);
    }

    if ($('#customType').val() != 'PERSIONAL') {
        publicOPN.addOPN([{
            name: '查看相似企业',
            alias: 'lookXS',
            event: function () {
                initRiskQuery.showXS();
            }
        }]);
    }

    publicOPN.addOPN([{
        name: '查看失信黑名单',
        alias: 'lookSX',
        event: function () {
            initRiskQuery.showSX();
        }
    }]);

    if (document.getElementById('fnEdit')) {
        publicOPN.addOPN([{
            name: '暂存',
            alias: 'save',
            event: function () {
                saveForm($step.find('li.active').attr('ftf'), function () {
                    Y.alert('提示', '已保存~', function () {
                        window.location.reload()
                    });
                });
            }
        }, {
            name: '提交',
            alias: 'submit',
            event: function () {

                submitForm();

            }
        }]);
    }

    publicOPN.init().doRender();

    //------ 侧边栏 end



    // // 审核通用部分 start
    // var auditProject = require('/js/tmbp/auditProject');
    // var _auditProject = new auditProject('auditSubmitBtn');
    // _auditProject.initFlowChart().initSaveForm().initAssign().initAudit({}).initPrint('打印的url');

    // require('Y-window');
    // var util = require('util');
    // var getList = require('zyw/getList');
    // //增删行
    // require('zyw/opsLine');
    // //Y对话框
    // require('Y-msg');
    // //上传
    // require('zyw/upAttachModify');
    // //表单验证
    // require('validate');
    // require('validate.extend');

    // // 风险查询 信息、相似、失信
    // var riskQuery = require('zyw/riskQuery');
    // if (document.getElementById('fnRisk')) {
    //     var initRiskQuery = new riskQuery.initRiskQuery(false, 'customName', 'customCertNo', 'customType', 'customOrgCode', 'customOneCert', 'customLicenseNo');
    //     $('#fnRisk').on('click', function () {
    //         initRiskQuery.getAllInfo(true, true, true, function (html) {
    //             document.getElementById('fnRiskBox').innerHTML = html
    //         })

    //     })
    // }

    // var util = require('util');
    // var openDirect = util.openDirect;

    // $('body').on('click', '#customerId', function (event) {

    //     openDirect('/assetMg/index.htm', '/assetMg/list.htm?customerId=' + $(this).attr('customerId'), '/assetMg/list.htm');

    // });

    // //------ 选择项目 start 
    // var _getList = new getList();
    // _getList.init({
    //     title: '项目列表',
    //     ajaxUrl: '/projectMg/afterwardsCheck/queryProjects.json?busiType=211',
    //     btn: '#chooseBtn',
    //     tpl: {
    //         tbody: ['{{each pageList as item i}}',
    //             '    <tr class="fn-tac m-table-overflow">',
    //             '        <td>{{item.projectCode}}</td>',
    //             '        <td title="{{item.customerName}}">{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6)+\'\.\.\':item.customerName}}</td>',
    //             '        <td title="{{item.projectName}}">{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6)+\'\.\.\':item.projectName}}</td>',
    //             '        <td>{{item.busiTypeName}}</td>',
    //             '        <td>{{item.amount}}</td>',
    //             '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
    //             '    </tr>',
    //             '{{/each}}'
    //         ].join(''),
    //         form: ['项目编号：',
    //             '<input class="ui-text fn-w160" name="projectCode" type="text">',
    //             '&nbsp;&nbsp;',
    //             '客户名称：',
    //             '<input class="ui-text fn-w160" name="customerName" type="text">',
    //             '&nbsp;&nbsp;',
    //             '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
    //         ].join(''),
    //         thead: ['<th width="100">项目编号</th>',
    //             '<th width="120">客户名称</th>',
    //             '<th width="120">项目名称</th>',
    //             '<th width="100">授信类型</th>',
    //             '<th width="100">授信金额(元)</th>',
    //             '<th width="50">操作</th>'
    //         ].join(''),
    //         item: 8
    //     },
    //     callback: function ($a) {
    //         window.location = "/projectMg/afterwardsCheck/editLitigation.htm?projectCode=" + $a.attr('projectCode');
    //     }
    // });
    // //------ 选择项目 end

    // //上传图片
    // // require('Y-htmluploadify'); //------ 上传 start
    // // $('.fnUpFile').click(function () {
    // //     var _this = $(this);
    // //     var htmlupload = Y.create('HtmlUploadify', {
    // //         key: 'up1',
    // //         uploader: '/upload.json1',
    // //         multi: false,
    // //         auto: true,
    // //         addAble: false,
    // //         fileTypeExts: '*.jpg; *.jpeg',
    // //         fileObjName: 'UploadFile',
    // //         onQueueComplete: function (a, b) {},
    // //         onUploadSuccess: function ($this, data, resfile) {
    // //             _this.attr('src', data.xxx).parent().find('input.fnUpFileInput').val(data.xxx);
    // //         },
    // //         renderTo: 'body'
    // //     });
    // //     htmlupload.show();
    // // });
    // //------ 上传 end


    // var $addForm = $('#form');

    // function formSubmit(type) {
    //     document.getElementById('state').value = type || 'SAVE';
    //     var checkStatus = document.getElementById('checkStatus').value = type ? 1 : 0;
    //     util.resetName();

    //     //保存数据
    //     util.ajax({
    //         url: '/projectMg/afterwardsCheck/saveLitigation.json',
    //         data: $addForm.serialize(),
    //         success: function (res) {
    //             if (res.success) {
    //                 if (checkStatus == '1') {
    //                     //提交表单
    //                     util.postAudit({
    //                         formId: res.form.formId
    //                     }, function () {
    //                         window.location.href = '/projectMg/afterwardsCheck/list.htm';
    //                     })
    //                 } else {
    //                     Y.alert('提醒', res.message);
    //                 }
    //             } else {
    //                 Y.alert('提醒', res.message);
    //             }
    //         }
    //     });
    // }

    // $('#submit').click(function () {

    //     var _isPass = true,
    //         _isPassEq;

    //     $('.fnInput').each(function (index, el) {
    //         if (!!!el.value.replace(/\s/g, '')) {
    //             _isPass = false;
    //             _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
    //         }
    //     });

    //     if (!_isPass) {

    //         Y.alert('提醒', '请把表单填写完整', function () {
    //             $('.fnInput').eq(_isPassEq).focus();
    //         });
    //         return;
    //     }

    //     formSubmit('SUBMIT');
    // })

    // //------ 侧边栏 start
    // var publicOPN = new(require('zyw/publicOPN'))();

    // var hasRiskReport = $('#hasRiskReviewReport').val();
    // if ('YES' == hasRiskReport) {
    //     publicOPN.addOPN([{
    //         name: '风险审核报告',
    //         url: '/projectMg/riskreview/viewReview.htm?formId=' + $('#amendRecordId').val(),
    //     }]);
    // }

    // publicOPN.addOPN([{
    //     name: '查看失信黑名单',
    //     alias: 'someEvent2',
    //     event: function () {
    //         initRiskQuery.showSX();
    //     }
    // }]);

    // if (document.getElementById('fnEdit')) {
    //     publicOPN.addOPN([{
    //         name: '暂存',
    //         alias: 'save',
    //         event: function () {
    //             var _loading = new util.loading();
    //             _loading.open();
    //             formSubmit();
    //             _loading.close();
    //         }
    //     }, {
    //         name: '提交',
    //         alias: 'submit',
    //         event: function () {

    //             var _isPass = true,
    //                 _isPassEq;

    //             $('.fnInput').each(function (index, el) {
    //                 if (!!!el.value.replace(/\s/g, '')) {
    //                     _isPass = false;
    //                     _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
    //                 }
    //             });

    //             if (!_isPass) {

    //                 Y.alert('提醒', '请把表单填写完整', function () {
    //                     $('.fnInput').eq(_isPassEq).focus();
    //                 });
    //                 return;
    //             }

    //             formSubmit('SUBMIT');
    //         }
    //     }]);
    // }
    // publicOPN.init().doRender();
    // //------ 侧边栏 end

    // // 验证
    // function selfValid(obj) {
    //     var CaseStatus = $('.caseStatus').val();
    //     if (CaseStatus == 'WITHDRAW') {
    //         obj.addClass('.fnInput');
    //         $('.laydate-icon').each(function () {
    //             $(this).removeClass('fnInput');
    //         })
    //     } else {
    //         $('.laydate-icon').each(function () {
    //             $(this).addClass('fnInput');
    //         })
    //     }
    // }

    // selfValid($('.caseStatus'));

    // $('.caseStatus').click(function () {
    //     selfValid($(this));
    // })

    // // 风险检索
    // $('#riskSearch').click(function (event) {
    //     if (document.getElementById('customName')) {
    //         var customerName = $('#customName').val(),
    //             customerId = $('#customLicenseNo').val();
    //         if (customerName != '' && customerId != '') {
    //             console.log(customerName, customerId);
    //             util.risk2retrieve(customerName, customerId);
    //         }
    //     } else {
    //         Y.alert('提示', '请选择客户名称');
    //     }
    // });

});