define(function (require, exports, module) {
    //辅助系统  > 合同管理 > 合同模板管理 
    //表单验证
    require('Y-msg');
    require('zyw/upAttachModify');

    var util = require('util');

    var getList = require('zyw/getList');

    // 强行保存
    var fnIsForcedModify = document.getElementById('fnIsForcedModify') || {}

    //------ 新增合同模板 start

    //tab切换 start
    var $fnLabelBox = $('.fnLabelBox ');
    $('.fnLabel input').on('click', function () {
        var _thisVal = this.value;
        $fnLabelBox.addClass('fn-hide').each(function (index, el) {
            if ($fnLabelBox.eq(index).attr('val') == _thisVal) {
                $fnLabelBox.eq(index).removeClass('fn-hide');
            }
        });
    });
    //tab切换 end
    //
    //授信类型
    var getTypesOfCredit = require('zyw/getTypesOfCredit');
    var _getTypesOfCredit = new getTypesOfCredit();
    _getTypesOfCredit.init({
        chooseAll: true,
        btn: '#businessTypeName',
        name: '#businessTypeName',
        code: '#businessTypeCode'
    });

    var $fnIsMan = $('#fnIsMan'), //是否是主合同
        $fnMeasures = $('#fnMeasures'); //反担保措施

    $('#businessTypeClear').on('click', function () {
        $('#businessTypeName').val('').trigger('change');
        $('#businessTypeCode').val('').trigger('change');
    });
    $('#businessTypeCode').on('change', function () {
        if (this.value) {
            $fnIsMan.removeProp('disabled');
        } else {
            $fnIsMan.prop('disabled', 'disabled').removeProp('checked');
        }
    });

    $fnIsMan.on('click', showMeasures);

    function showMeasures() {
        if ($fnIsMan.prop('checked')) {
            $fnMeasures.addClass('fn-hide').find('select').prop('disabled', 'disabled');
        } else {
            $fnMeasures.removeClass('fn-hide').find('select').removeProp('disabled');
        }
    }

    showMeasures();
    //
    //
    //反担保措施
    var $pledgeType = $('#pledgeType'),
        $pledgeTypeBox = $('#pledgeTypeBox');
    $('#creditConditionType').on('change', function () {
        if (this.value == '保证' || !!!this.value) {
            $pledgeTypeBox.addClass('fn-hide').find('input').val('')
        } else {
            $pledgeTypeBox.removeClass('fn-hide')
        }
    }).trigger('change');

    $('.fnSelectDiy').each(function (index, el) {

        var $fnSelectDiy = $(this)

        $fnSelectDiy.on('click', '.m-select-bar', function () {

            $fnSelectDiy.toggleClass('active').find('.m-select-list-box').find('dt, dd').removeClass('active');

        }).on('click', 'dt', function () {

            $(this).toggleClass('active').next().toggleClass('active')

        }).on('click', 'p', function () {
            $fnSelectDiy.removeClass('active').find('.m-select-text').val(this.innerHTML)
                .end().find('.m-select-value').val(this.getAttribute('typeid')).trigger('blur')

        });

        $fnSelectDiy.find('.m-select-text').val($fnSelectDiy.find('p[typeid="' + $fnSelectDiy.find('.m-select-value').val() + '"]').text())

    })

    //富文本
    var editorUE,
        doSubmit,
        inputIndex = 0;
    if (document.getElementById('editor')) {

        var $div = $('<div></div>');

        $div.html(document.getElementById('editor').innerHTML);

        var _$fnTotalInput = $div.find('input#fnTotalInput');

        if (_$fnTotalInput.length) {
            inputIndex = +_$fnTotalInput.val();
            _$fnTotalInput.parent().remove();
        }

        try {
            document.getElementById('editor').innerHTML = $div.html();
        } catch (e) {
            document.getElementById('editor').text = $div.html();
        }

        editorUE = UE.getEditor('editor');
        //有富文本框就可以提交合同模板
        doSubmit = function (type) {

            var fnChooseManName = document.getElementById('fnChooseManName');

            if (fnChooseManName && !!!fnChooseManName.value) {
                Y.alert('提示', '请选择法务人员');
                return false;
            }

            if (!!!document.getElementById('templateName').value.replace(/\s/g, '')) {
                Y.alert('提示', '请填写模板名称', function () {
                    document.getElementById('templateName').focus();
                });
                return false;
            }

            // 暂存还是提交
            document.getElementById('checkStatus').value = type ? '1' : '0';

            // 如果是保存 反担保措施 必须填完整
            // 突然又不要了 =.=  
            // if (type) {

            //  if (!$fnIsMan.prop('checked')) {
            //      // 反担保措施 与 主合同选一， 并且 反担保措施要么选完整，要么不选择
            //      var creditConditionTypeVal = document.getElementById('creditConditionType').value;

            //      if (!!creditConditionTypeVal && creditConditionTypeVal != '保证' && !$pledgeType[0].value) {
            //          Y.alert('提示', '反担保措施请选择到最后一级', function() {
            //              $pledgeType[0].focus();
            //          });
            //          return false;
            //      }

            //  }

            // }

            //判断 模板类别
            if ($('.fnLabel input:checked').val() == 'STANDARD') {
                editorUE.setContent('<input id="fnTotalInput" value="' + (inputIndex) + '" type="hidden">', true);
                document.getElementById('templateContent').value = escape(editorUE.getContent());
            } else {
                document.getElementById('templateContent').value = '';
                // 避免非必填报错 -、-
                try {
                    if (type && !!!document.getElementById('templateFile').value) {
                        Y.alert('提示', '请上传范例合同模板');
                        return false;
                    }
                } catch (err) {}
            }
            // document.getElementById('templateContent').value = ($('.fnLabel input:checked').val() == 'STANDARD') ? escape(editorUE.getContent()) : '';
            util.ajax({
                url: $addTemplate.attr('action'),
                data: $addTemplate.serialize(),
                success: function (res) {
                    setTimeout(function () {
                        if (res.success) {

                            // 选择了人就要走流程
                            if (!!$('#fnChooseManName').val() && type && !!!fnIsForcedModify.value) {

                                util.postAudit({
                                    formId: res.form.formId
                                }, function () {
                                    util.direct('/projectMg/contract/templateList.htm?templateType=' + $("#templateType").val());
                                })

                            } else {
                                Y.alert('操作成功', '已保存', function () {
                                    // window.location.href = "/projectMg/contract/templateList.htm";
                                    var _templateType = $("#templateType").val()
                                    var _url = (_templateType != 'CONTRACT') ? ('/projectMg/contract/templateList.htm?templateType=' + _templateType) : '/projectMg/contract/templateList.htm'
                                    util.direct(_url);
                                });

                            }

                        } else {
                            Y.alert('提示', res.message);
                        }
                    }, 50);
                }
            });

        }
    }
    //添加一个输入框
    $('#fnAddInput').on('click', function () {
        editorUE.execCommand('insertHtml', '<span style="text-indent:0; border-bottom: 1px solid #ccc; display: inline; padding: 3px 5px;"><input class="fnContractInput" name="fnContractInput' + (inputIndex++) + '" type="text" style="width: 200px; border: 0;" readonly="readonly"></span>')
    });
    //添加一个输入框
    $('#fnAddInputNoLine').on('click', function () {
        editorUE.execCommand('insertHtml', '<span style="text-indent:0; border-bottom: 1px solid #ccc; display: inline; padding: 3px 5px;"><input class="fnContractInput fnNoBottomLine" name="fnContractInput' + (inputIndex++) + '" type="text" style="width: 200px; border: 0;" readonly="readonly"></span>')
    });

    //---选择标签 start
    if (document.getElementById('fnAddLabel')) {

        var fixedTop = $('#editorBox').offset().top,
            $editorAddButton = $('#editorAddButton');

        $(window).scroll(function (event) {

            if ($(window).scrollTop() >= fixedTop) {
                if (!$editorAddButton.hasClass('editorAddButton')) {
                    $editorAddButton.addClass('editorAddButton');
                }
            } else {
                if ($editorAddButton.hasClass('editorAddButton')) {
                    $editorAddButton.removeClass('editorAddButton');
                }
            }


        });

        $('#fnAddLabel').on('click', function () {
            $fnChooseLabel.removeClass('fn-hide');
        });
        // 表、字段选择
        var $fnChooseLabel = $('#fnChooseLabel'),
            projectPhase = document.getElementById('projectPhase'),
            dataStr = document.getElementById('dataStr'),
            dataCode = document.getElementById('dataCode'),
            fieldStr = document.getElementById('fieldStr'),
            fieldCode = document.getElementById('fieldCode'),
            fnMarkCode, fnMarkStr;

        $fnChooseLabel.on('click', '.close', function () {

            $fnChooseLabel.addClass('fn-hide')

        }).on('change', '#projectPhase', function () {

            dataStr.value = '';
            dataCode.value = '';

        }).on('click', '#dataStr', function () {
            // 选择数据库表
            $fnChooseTitle.html('请选择数据库表');

            fnMarkCode = dataCode;
            fnMarkStr = dataStr;

            util.ajax({
                url: '/projectMg/contract/loadDBTable.json?pageNumber=1&pageSize=99999&projectPhase=' + projectPhase.value,
                success: function (res) {

                    $fnChooseList.after(creatDataItem(res.data.pageList, {
                        short: 'tableForShort',
                        name: 'tableName',
                        value: 'tableId',
                        intro: 'remark'
                    }));
                    $fnChooseData.removeClass('fn-hide');

                }
            });

        }).on('click', '#fieldStr', function () {

            if (!dataCode.value) {
                Y.alert('提示', '请先选择数据库表');
                return;
            }


            // 选择数据库字段
            $fnChooseTitle.html('请选择数据字段');

            fnMarkCode = fieldCode;
            fnMarkStr = fieldStr;

            util.ajax({
                url: '/projectMg/contract/loadDBFieldfromTable.json?pageNumber=1&pageSize=99999&tableId=' + dataCode.value,
                success: function (res) {

                    $fnChooseList.after(creatDataItem(res.data.pageList, {
                        short: 'fieldForShort',
                        name: 'fieldName',
                        value: 'fieldId',
                        intro: 'remark'
                    }));
                    $fnChooseData.removeClass('fn-hide');

                }
            });

        }).on('click', '.sure', function () {
            if (!dataCode.value || !fieldCode.value) {
                Y.alert('提示', '请填写完整表单');
                return;
            }
            //添加到编辑器
            editorUE.execCommand('insertHtml', '${' + dataStr.value + '.' + fieldStr.value + '?default(\'\')}');
            $fnChooseLabel.addClass('fn-hide').find('input').val('');
            $fnChooseLabel.find('option').removeProp('selected');
        });
        // 具体选择
        var $fnChooseData = $('#fnChooseData'),
            $fnChooseTitle = $('#fnChooseTitle'),
            $fnChooseList = $('#fnChooseList');

        $fnChooseData.on('click', '.close', function () {

            $fnChooseData.addClass('fn-hide');
            $fnChooseList.nextAll().remove();

        }).on('click', '.sure', function () {

            var _val = $fnChooseData.find('input:checked').val();
            if (_val) {
                //填充数据
                fnMarkCode.value = _val.split(';')[0];
                fnMarkStr.value = _val.split(';')[1]
                $fnChooseData.addClass('fn-hide');
                $fnChooseList.nextAll().remove();
            }

        });

        //创建html
        function creatDataItem(arr, key) {

            var _s = '';

            if (!!!arr.length || arr == '[]') {
                return '<div class="m-item">暂无数据</div>';
            }

            for (var i = 0; i < arr.length; i++) {
                _s += ['<div class="m-item">',
                    '<label class="m-label" title="' + arr[i][key.short] + '">' + util.subStr(arr[i][key.short], 8) + '：</label>',
                    '<label class="fn-csp"><input type="radio" value="' + arr[i][key.value] + ';' + arr[i][key.name] + '" name="c">' + arr[i][key.name] + '（' + arr[i][key.intro] + '）</label>',
                    '</div>'
                ].join('');
            }

            return _s;

        }

    }
    //---选择标签 end


    //新增合同模板验证
    var $addTemplate = $('#addTemplate');
    $addTemplate.submit(function (event) {

        doSubmit('SUBMIT');

        return false;

    }).on('click', '#fnDoSave', function () {
        doSubmit()
    });

    //------ 新增合同模板 start
    //
    //------ 合同模板审核 start
    if (document.getElementById('auditForm')) {
        //--- 审核通用部分 start
        var auditProject = require('zyw/auditProject');
        var _auditProject = new auditProject();
        _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();
        //--- 审核通用部分 end
    }
    //------ 合同模板审核 end

    //--- 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    if (document.getElementById('fnDoSave') && !!!fnIsForcedModify.value) {
        publicOPN.addOPN([{
            name: '暂存',
            event: function () {
                doSubmit()
            }
        }]);
    } else {
        $('#fnDoSave').addClass('fn-hide')
    }
    if (document.getElementById('fnDoSubmit')) {
        publicOPN.addOPN([{
            name: '提交',
            event: function () {
                doSubmit('SUBMIT');
            }
        }]);
    }
    publicOPN.init().doRender();
    //--- 侧边栏 end

    //------ 文件类别 start
    if (document.getElementById('fnTZSType')) {
        $('.fnletterType').on('click', function () {
            if (this.value == 'CONTRACT') {
                $('.letterTypeNot').removeClass('fn-hide').find('select,input').removeProp('disabled');
            } else {
                $('.letterTypeNot').addClass('fn-hide').find('select,input').prop('disabled', 'disabled');
            }

        });

        if ($('.fnletterType:checked').val() != 'CONTRACT') {
            $('.letterTypeNot').addClass('fn-hide').find('select,input').prop('disabled', 'disabled');
        }

    }
    //------ 文件类别 end

    //------ 选择人员
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    // 初始化弹出层
    var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
        'title': '人员',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'selectUsers': {
            selectUserIds: '', //已选id,多用户用,隔开
            selectUserNames: '', //已选Name,多用户用,隔开
            selectUserAccounts: '' //已选account,多用户用,隔开
        },
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });
    // 添加选择后的回调，以及显示弹出层
    $('#fnChooseMan').on('click', function () {

        //这里也可以更新已选择用户
        // singleSelectUserDialog.obj.selectUsers = {
        //  selectUserIds: '', //已选id,多用户用,隔开
        //  selectUserNames: '', //已选Name,多用户用,隔开
        //  selectUserAccounts: '' //已选account,多用户用,隔开
        // }

        singleSelectUserDialog.init(function (relObj) {

            document.getElementById('fnChooseManNameId').value = relObj.userIds;
            document.getElementById('fnChooseManName').value = relObj.fullnames;
            document.getElementById('fnChooseManNameAccount').value = relObj.accounts;

            $('#templateFile').attr('id', '').parent().find('.m-required').remove();

        });

    });


});