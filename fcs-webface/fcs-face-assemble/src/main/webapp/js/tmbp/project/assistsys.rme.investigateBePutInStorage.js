define(function (require, exports, module) {
    //上传
    require('zyw/upAttachModify');

    require('zyw/publicPage');

    require('Y-msg');

    var util = require('util')

    // 审核通用部分 start
    var auditProject = require('/js/tmbp/auditProject');
    var _auditProject = new auditProject('auditSubmitBtn');
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit().initPrint('打印的url');
    //------ 侧边栏 start
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.addOPN([]).init().doRender();
    //------ 侧边栏 end

    mergeTable = require('zyw/mergeTable');
    new mergeTable({

        //jq对象、jq selector
        obj: '.demandMerge',

        //默认为false。true为td下有input或者select
        valType: false,

        //每次遍历合并会调用的callback
        mergeCallback: function (Dom) { //Dom为每次遍历的合并对象和被合并对象

            // var Obj, text, index;

            // Obj = Dom['mergeObj'];
            // text = Obj.text();
            // index = Obj.index();

            // if (!index) {

            //     Obj.html(text + '<div class="headmanCover"><i class = "xlsTop fn-p-abs"></i><i class = "xlsLeft fn-p-abs"></i><i class = "xlsRight fn-p-abs"></i><i class = "xlsBottom fn-p-abs"></i></div>');

            // }

        }

    })

    var popupWindow = require('zyw/popupWindow');
    var hintPopup = require('zyw/hintPopup');
    //关联单据

    $('body').on('click', '.investigateBePutInStorageBtn', function (event) {
        var _this = $(this);
        var _inputListId = _this.parents('tr').attr('inputListId');

        fundDitch = new popupWindow({ //关联单据

            YwindowObj: {
                content: 'investigateBePutInStorageScript', //弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', //find关闭弹窗对象
                dragEle: '.newPopup dl dt' //find拖拽对象
            },

            ajaxObj: {
                url: '/baseDataLoad/inOutHistory.json',
                type: 'post',
                dataType: 'json',
                data: {
                    inputListId: _inputListId,
                    pageSize: 6,
                    pageNumber: 1
                }
            },
            formAll: { //搜索
                submitBtn: '#PopupSubmit', //find搜索按钮
                formObj: '#PopupFrom', //find from
                callback: function ($Y) {

                }
            },
            pageObj: { //翻页
                clickObj: '.pageBox a.btn', //find翻页对象
                attrObj: 'page', //翻页对象获取值得属性
                jsonName: 'pageNumber', //请求翻页页数的dataName
                callback: function ($Y) {

                    //console.log($Y)

                }
            },

            callback: function ($Y) {

                $Y.wnd.on('click', 'a.choose', function (event) {

                    var _this = $(this);

                    window.location.href = objJson.htmlSource + '?sourceId=' + _this.parents('tr').attr('sourceId');

                });

            },

            console: false //打印返回数据

        });


    });

    // ------ 批量借出 start

    var $fnBatchLendForm = $('#fnBatchLendForm')

    $fnBatchLendForm.on('click', '.sure', function () {

        var isSure = true

        $fnBatchLendForm.find("[name='type']").each(function (index, el) {

            if (!!!el.value) {
                isSure = false
                return false
            }

        })

        if (!isSure) {

            Y.alert('提示', '请填写完整批量操作的信息')
            return
        }

        util.ajax({
            url: '/projectMg/file/batchBorrow.htm',
            data: $fnBatchLendForm.serialize(),
            success: function (res) {

                if (res.success) {
                    window.location.href = '/projectMg/file/applyBorrow.htm?ids=' + res.ids + '&type=BORROW'
                } else {
                    Y.alert('提示', res.message)
                }



            }
        })

    })

    // ------ 批量借出 end

    //  ------  打印 start

    // function printdiv(printpage) {
    //     var headstr = "<html><head><title></title></head><body>";
    //     var footstr = "</body>";
    //     var newstr = document.all.item(printpage).innerHTML;
    //     var oldstr = document.body.innerHTML;
    //     document.body.innerHTML = headstr + newstr + footstr;
    //     window.print();
    //     document.body.innerHTML = oldstr;
    //     return false;
    // }

    $('#fnPrint').click(function (event) {
        //cutTrToTable()
        Y.confirm('提示', '是否打印审批记录', function (opn) {

            if (opn != 'close') {

                if (opn == 'no') {
                    $('.printshow').remove()
                }
                cutTrToTable()
                util.print('<h2 class="apply-h2 fn-tac fn-pt20 fn-pb20">' + $('.apply-h2').html() + '</h2><div class="m-invnAll">' + $('#div_print').html() + '</div>')

            }

        })

        // util.print($div.html())

    });


    // 放到一个新的地方
    function cutTrToTable() {
        var $div = $('#div_print')
            ,$el = $div.find('.fnStep').find('tr, h2, div')
            ,$table = $div.find('table')
            ,num = 0
        $('<div id="area"></div>').insertBefore('.fn-p-reb .m-table')
        $el.each(function () {
            var _this = $(this)
            var _t = _this.find('td,th')
            if (_t.length) {
                num++
                _t.each(function () {
                    var me = $(this)
                    me.attr('width', 1 / 7 * (me.attr('colspan') || 1) * 100 + '%')
                })
                _this.appendTo($("#area"))
            } else {
                _this.appendTo($("#area"))
            }
        })
        cut20tr()
    }

    //这是动态拆分
    function cutTr() {
        var tr = $("#area").find('tr')
        tr.each(function () {
            var $this = $(this),
                $t = $this.find('.demandMerge'),
                $prev = $this.prev().find('.demandMerge').last()
            if (($t.attr('rowspan') || $t.css('display') == 'none')) {
                if ($prev.text() == $t.text()) {
                    $this.appendTo($prev.parents('table'))
                } else {
                    $this.wrap('<table class="m-table fn-tac" style="margin: 0;table-layout:fixed;"></table>')
                }
            } else {
                $this.wrap('<table class="m-table fn-tac" style="margin: 0;table-layout:fixed;"></table>')
            }
        })

    }

    //这是固定static 个tr拆分
    function cut20tr (){
        var tr = $("#area").find('tr'), num = 0, static = 30
        tr.each(function () {
            var $this = $(this),
                $prevParent = $this.prev()

            if ((num === static || num === 0)  ) {
                $this.wrap('<table class="m-table fn-tac" outline="1" style="margin-top: -1px;table-layout:fixed;border:0"></table>')
                //如果td.text() 一样就要show 出来
                if ($this.find('td,th').eq(0).text() === $prevParent.find('tr').last().find('td,th').eq(0).text()) {
                    $this.find('td,th').eq(0).show()
                }
                num = 1
            } else {
                if ($prevParent[0].tagName == 'TABLE' && num != 99) {
                    $this.appendTo($prevParent)
                    num++
                } else {
                    //这里应该是审批记录了， 这里全部拆分 不存在 colspan的情况
                    $this.wrap('<table class="m-table fn-tac" outline="1" style="margin-top: -1px;table-layout:fixed;border:0;"></table>')
                    num = 99
                }
            }
        })
    }

    
    //  ------- 打印 end

    //导出 start
    var pako = require('pako')
    $('#fnDoExport').on('click', function () {

        var $ex = $('#div_print');

        $ex.find('.ui-btn-submit').remove()

        $ex.find('.printshow').removeClass('fn-hide')

        util.css2style($ex)

        // var __body = document.getElementsByTagName('body')[0]
        //
        // __body.innerHTML = ''
        // __body.appendChild($ex[0])

        $('body').eq(0).html($ex.html())

        $('head link, script, .ui-tool').remove()

        $('.demandMerge').each(function () {
            var $this = $(this);
            if($this.css('display') == 'none')
                $this.remove();
        })

        //document.documentElement.outerHTML
        var deflate = pako.gzip(document.documentElement.outerHTML, {
            to: 'string'
        });
        // console.log(pako.ungzip(deflate,{ to: 'string' }));
        // debugger
        $.ajax({
            type: 'post',
            url: '/baseDataLoad/createDoc.json',
            // contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {
                formId: util.getParam('formId'),
                type: 'FILE_INPUT',
                decode: 'yes',
                htmlData: encodeURIComponent(deflate)
            },
            success: function (res) {
                if (res.success) {
                    window.location.href = res.url;

                    setTimeout(function() {
                        window.location.reload(true)
                    }, 1000)

                } else {
                    console.log(res.message)
                    //window.location.reload()
                }
            }
        })

    })
    //导出 end

})