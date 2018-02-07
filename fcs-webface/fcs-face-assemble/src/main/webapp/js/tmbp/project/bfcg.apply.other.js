define(function(require, exports, module) {
	//表单验证
	require('validate');
	require('validate.extend');

	//省市联动
	//require('Y-selectarea');
	//Y对话框
	require('Y-msg');
	//模板引擎
	var template = require('arttemplate');
    // 通用方法
    var util = require('util');
    var md5 = require('md5');

    var $body = $('body');
	// 上传附件
	require('zyw/upAttachModify');
    // ------ 关于表单储存 end
    //
    // ------ 侧边工具栏 start
    (new (require('zyw/publicOPN'))()).addOPN([ {
        name : '暂存',
        alias : 'save',
        event : function() {

            doSaveForm(function() {
                Y.alert('提示', '已保存',function () {
                    window.location.href = '/projectMg/setUp/list.htm';
                });

            })

        }
    }, {
        name : '提交',
        alias : 'submit',
        event : function() {
            submitForm();
        }
    } ]).init().doRender();

    function submitForm() {

        saveForm("form", function(formId) {



            Y.confirm('提示', '确认提交当前表单？', function(opn) {

                if (opn == 'yes') {

                    util.postAudit({
                        formId : formId
                    }, function() {
                        window.location.href = '/projectMg/setUp/list.htm';
                    })

                }

            });

        });

    }


    function saveForm(key, callback) {

        var _$form = $('#' + key); // 需要保存的表单

        // 重命名

        if (_$form.valid()) {
            _$form.find('input[name="checkStatus"]').val('1');
        } else {
            _$form.find('input[name="checkStatus"]').val('0');
        }

        if (isFormMd5Changed(key, _$form.serialize())) {

            // --变化，保存
            saveFormAjax(key, _$form, callback);

        } else {

            // --没有变化，直接回调
            if (callback) {
                callback();
            }

        }
    }

    var formMd5Obj = {};


    function isFormMd5Changed(key, param) {

        if (key === 'qyjbqk') {
            return true
        }

        return (md5(param) != formMd5Obj[key]) ? true : false;

    }


    function saveFormAjax(key, _$form, callback) {



        $.ajax({
            url : _$form.attr('action'),
            data : _$form.serializeJCK(),
            type : 'POST',
            success : function(res) {
                if (res.success) {
                    // 更新 缓存md5
                    formMd5Obj[key] = md5(_$form.serialize());

                    if (callback) {
                        callback(res.form.formId);
                    }
                } else {
                    _loading.close();
                    Y.alert('提示', res.message);
                }
            }
        });

    }

    var _loading = new util.loading();
    function doSaveForm(cb) {


        _loading.open();

        saveForm("form", function() {

            _loading.close();

            if (cb) {
                cb()
            }

        });
    }


    // ------ 选择币种 end

    $body.on('click', '.fnGOBack', function() {

        Y.confirm('提示', '确定返回？', function(opn) {
            if (opn == 'yes') {
                window.location.href = '/projectMg/setUp/list.htm';
            }
        });

    }).on('click', '.submitBtn', function() {
        var $form = $(this).parents('form');
        if (!$form.valid()) {
            Y.alert('提示', '请填写完整表单')
            return;
        }
        if($('[name=projectName]').val() == ''){
            Y.alert('提示', '请填写项目名称')
            return;
        }
        submitForm();

    })




});