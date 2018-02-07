define(function(require, exports, module) {

    var data = {

        success: true,

        list: [

            {lineOne:'赤斑羚1',lineTwo:'海南虎斑1',lineTree:'豚鹿1',lineFour:'河狸',lineFive:'藏酋猴1'},
            {lineOne:'赤斑羚1',lineTwo:'海南虎斑1',lineTree:'豚鹿1',lineFour:'河狸',lineFive:'藏酋猴2'},
            {lineOne:'赤斑羚1',lineTwo:'海南虎斑1',lineTree:'豚鹿2',lineFour:'河狸',lineFive:'藏酋猴3'},
            {lineOne:'赤斑羚1',lineTwo:'海南虎斑2',lineTree:'豚鹿',lineFour:'河狸',lineFive:'藏酋猴4'},
            {lineOne:'赤斑羚1',lineTwo:'海南虎斑2',lineTree:'豚鹿',lineFour:'河狸',lineFive:'藏酋猴5'},

            {lineOne:'赤斑羚3',lineTwo:'海南虎斑1',lineTree:'豚鹿',lineFour:'河狸',lineFive:'藏酋猴6'},
            {lineOne:'赤斑羚3',lineTwo:'海南虎斑1',lineTree:'豚鹿',lineFour:'河狸',lineFive:'藏酋猴7'},
            {lineOne:'赤斑羚3',lineTwo:'海南虎斑2',lineTree:'豚鹿1',lineFour:'河狸',lineFive:'藏酋猴8'},
            {lineOne:'赤斑羚3',lineTwo:'海南虎斑2',lineTree:'豚鹿2',lineFour:'河狸',lineFive:'藏酋猴9'},
            {lineOne:'赤斑羚3',lineTwo:'海南虎斑2',lineTree:'豚鹿2',lineFour:'河狸',lineFive:'藏酋猴10'},

            {lineOne:'赤斑羚2',lineTwo:'海南虎斑1',lineTree:'豚鹿1',lineFour:'河狸',lineFive:'藏酋猴11'},
            {lineOne:'赤斑羚2',lineTwo:'海南虎斑1',lineTree:'豚鹿1',lineFour:'河狸',lineFive:'藏酋猴12'},
            {lineOne:'赤斑羚2',lineTwo:'海南虎斑1',lineTree:'豚鹿2',lineFour:'河狸1',lineFive:'藏酋猴13'},
            {lineOne:'赤斑羚2',lineTwo:'海南虎斑1',lineTree:'豚鹿2',lineFour:'河狸1',lineFive:'藏酋猴14'},
            {lineOne:'赤斑羚2',lineTwo:'海南虎斑1',lineTree:'豚鹿2',lineFour:'河狸2',lineFive:'藏酋猴15'}

        ]
            // list:[

        //     {lineOne:'赤斑羚1',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚1',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚1',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚1',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚1',lineFive:'藏酋猴'},

        //     {lineOne:'赤斑羚3',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚3',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚3',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚3',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚3',lineFive:'藏酋猴'},

        //     {lineOne:'赤斑羚2',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚2',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚2',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚2',lineFive:'藏酋猴'},
        //     {lineOne:'赤斑羚2',lineFive:'藏酋猴'}

        // ]

    }

    //js引擎
    var template, html;

    template = require('arttemplate');
    html = template('t-test', data);

    $('#test').html(html);

    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合
    require('zyw/requiredRules');
    var _form = $('#form'),
        _allWhetherNull = {
            stringObj: 'fileType,fileName',
            boll: false
        },
        requiredRules = _form.requiredRulesSharp(_allWhetherNull['stringObj'], _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                required: true,
                messages: {
                    required: '必填'
                }
            };
        }),
        maxlength50Rules = _form.requiredRulesSharp(_allWhetherNull['stringObj'] + ',remark', _allWhetherNull['boll'], {}, function(rules, name) {
            rules[name] = {
                maxlength: 20,
                messages: {
                    maxlength: '已超出20字'
                }
            };
        }),
        _rulesAll = $.extend(true, maxlength50Rules, requiredRules),
        submitValidataCommonUp = require('zyw/submitValidataCommonUp');

    var mergeTable = require('zyw/mergeTable');

    new mergeTable({

        //jq对象、jq selector
        obj: '.demandMerge',

        //默认为false。true为td下有input或者select
        valType: false,

        //每次遍历合并会调用的callback
        mergeCallback: function(Dom) {//Dom为每次遍历的合并对象和被合并对象

            var Obj, text, index;

            Obj = Dom['mergeObj'];
            text = Obj.text();
            index = Obj.index();

            if (!index) {

                Obj.html(text + '<div class="headmanCover"><i class = "xlsTop fn-p-abs"></i><i class = "xlsLeft fn-p-abs"></i><i class = "xlsRight fn-p-abs"></i><i class = "xlsBottom fn-p-abs"></i></div>');

            }

        },
        callback: function() {//表格合并完毕后



        },
        transform: {

            open: true, //开启增删模式
            active: '.addDemand', //允许增加项对象（支持任何JQselect）
            on: '.active',//选中样式（支持任何JQselect）
            addBtn: '.fnAddLineSubitem', //选中增加项后单击增加的按钮（单个）
            addHandleEachExcept: function(callObj) {//选中对象active、新增tr对象newTr

                //return false;//如不满足条件 return false 则不再往下执行
                console.log(1);

            },
            addHandleEachTd: function(callObj) {//添加时参与循环改变rowspan的对象

                console.log(2);

            },
            addContent: $('#addList').html(), //Dom或拼接字符串

            removeBtn: '.removeDemand', //对应tr内td或td内元素
            removeHandleEachExcept: function(callObj) {//重新显示的TD

                console.log(3);

            },
            removeHandleEachTd: function(callObj) {//移除时参与循环改变rowspan的对象

                console.log(4);

            },

        }

    });



})