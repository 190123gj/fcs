define(function(require, exports, module) {

    //Nav选中样式添加
    require('zyw/project/bfcg.itn.toIndex');
    require('Y-window');
    $('#trial').click(function() {
        $('body').Y('Window', {
            content: ['<div class="newPopup">',
                        '    <span class="close">×</span>',
                        '    <dl>',
                        '        <dt><span>风险覆盖率试算结果</span></dt>',
                        '        <dd class="fn-text-c">',
                        '            <h1 class="fn-color-4a fn-mb25">该客户的风险覆盖率为<em class="remind">30%</em></h1>',
                        '            <p class="fn-mb45">公司设定的风险覆盖率阀值70%</p>',
                        '            <span>',
                        '                <a class="fn-left ui-btn ui-btn-submit ui-btn-next fn-ml5 fn-mr5 fnNext fn-size16">客户评级</a>',
                        '                <a class="fn-left ui-btn ui-btn-submit ui-btn-blue fn-ml5 fn-mr5 fnNext fn-size16">采用第三方评级结果</a>',
                        '            </span>',
                        '        </dd>',
                        '    </dl>',
                        '</div>'].join(""),
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.close'
        });
        var _sum = 0,
            _creditAmountStr = parseFloat($('[name="creditAmountStr"]').text().replace(',',''))*10000,
            _remind;
        console.log(_creditAmountStr)
        $('body .pledgeTotal').each(function(index, el) {
            var _re = /(?:[1-9][0-9]*(?:\.[0-9]+)?|0(?:\.[0-9]+)?)/,
                _text = $(el).text(),
                _text = _text.replace(',',''),
                _text = _text.match(_re),
                _num = parseFloat(_text)||0;
                _sum += _num;
        });
        _remind =_sum/_creditAmountStr;
        $('.remind').text(_remind.toFixed(4)*100+'%')
        //console.log(_remind)
    });
});