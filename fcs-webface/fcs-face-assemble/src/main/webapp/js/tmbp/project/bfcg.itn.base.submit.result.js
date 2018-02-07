define(function(require, exports, module) {

    var submitedFun = require('zyw/submited')

    function processResult(res, hintPopup) {

        if (res.success) {
            var formId = $('#formId').val();
            if (isNaN(formId) || formId == '0') {
                formId = res.form.formId;
                $('#formId').val(formId);
            }
            var projectCode = $('#projectCode').val();
            var submited = $('#submited').val();
            var nextUrl = $('#nextUrl').val();
            if (res.url == '#') {
                hintPopup(res.message, window.location.href.replace(/\&scrollTop.+(?=\&{0,1})/g, '') + '&scrollTop=' + $('#years').attr('scrolltop'));
            } else if (res.url == '##') {

                var _checkStatus = res.form.checkStatus;

                submitedFun(_checkStatus);
                $('[name="submited"]').val(_checkStatus);
                if (res.token) {
                    $('#token').val(res.token);
                }

                if (_checkStatus != '1111111111' && _checkStatus !='1111111011') {
                    $('body,html').animate({
                        scrollTop: 0
                    });
                    return false;
                }

                if (res.riskCoverRate) {
                	$('[name="customerCoverRate"]').val(res.riskCoverRate);
                }
                var _customerCoverRate = parseFloat($('[name="customerCoverRate"]').val()),
                    _riskCoverRate = parseFloat($('[name="riskCoverRate"]').val());
                $('body').Y('Window', {
                    content: ['<div class="newPopup">',
                        '    <span class="close">×</span>',
                        '    <dl>',
                        '        <dt><span>风险覆盖率试算结果</span></dt>',
                        '        <dd class="fn-text-c">',
                        '            <h1 class="fn-color-4a fn-mb25">该客户的风险覆盖率为<em class="remind">' + _customerCoverRate + '%</em></h1>',
                        '            <span>',
                        '                <a class="fn-left ui-btn ui-btn-submit ui-btn-next fn-ml5 fn-mr5 fnNext fn-size16 submit">提交</a>',
                        '                <a class="fn-left ui-btn ui-btn-submit ui-btn-cancel fn-ml5 fn-mr5 fnNext fn-size16">客户评级</a>',
                        '                <a class="fn-left ui-btn ui-btn-submit ui-btn-blue fn-ml5 fn-mr5 fnNext fn-size16 close">取消</a>',
                        '            </span>',
                        '        </dd>',
                        '    </dl>',
                        '</div>'
                    ].join(""),
                    modal: true,
                    key: 'modalwnd',
                    simple: true,
                    closeEle: '.close'
                });

                var modalwnd = Y.getCmp('modalwnd');
                if (_customerCoverRate < _riskCoverRate) {
                    modalwnd.wnd.find('h1').after('<p class="fn-mb45">低于公司设定的风险覆盖率阀值' + _riskCoverRate + '%</p>')
                }
                modalwnd.wnd.find('.submit').click(function(event) {
                    $.ajax({
                        url: '/projectMg/form/submit.htm',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            formId: formId
                        },
                        success: function(data) {
                            hintPopup(data.message, function() {
                                if (data.success) {
                                    window.location.href = '/projectMg/investigation/list.htm';
                                }
                            });
                        }
                    })
                });



            //} else if (res.url != '') {
            //   window.location.href = res.url + '?formId=' + formId + '&projectCode=' + projectCode + '&submited=' + submited;
            } else {
                //window.location.href = nextUrl + '?formId=' + formId + '&projectCode=' + projectCode + '&submited=' + submited;
                window.location.href = '/projectMg/investigation/edit.htm' + '?formId=' + formId + '&toIndex=' + res.toIndex + '&submited=' + submited;
            }
        } else {
            hintPopup(res.message);
        }
    }
    module.exports = processResult
})