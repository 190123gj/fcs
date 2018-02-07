define(function(require, exports, module) {


    var mergeTable = require('zyw/mergeTable');

    new mergeTable({

        //jq对象、jq selector
        obj: '.demandMerge',

        //默认为false。true为td下有input或者select
        valType: false,

        //每次遍历合并会调用的callback
        mergeCallback: function(Dom) { //Dom为每次遍历的合并对象和被合并对象

            // var Obj, text, index;

            // Obj = Dom['mergeObj'];
            // text = Obj.text();
            // index = Obj.index();

            // if (!index) {

            //     Obj.html(text + '<div class="headmanCover"><i class = "xlsTop fn-p-abs"></i><i class = "xlsLeft fn-p-abs"></i><i class = "xlsRight fn-p-abs"></i><i class = "xlsBottom fn-p-abs"></i></div>');

            // }

        }

    });



})