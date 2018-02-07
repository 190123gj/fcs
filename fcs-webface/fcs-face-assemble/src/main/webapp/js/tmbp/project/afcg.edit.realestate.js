define(function (require, exports, module) {
    require('zyw/project/afcg.editpub');

    // 填写详情
    $('.fnNewaddline').click(function () {
        var formId = $('#formId').val();
        setTimeout(function () {
            $.ajax({
                type: "GET",
                url: "/projectMg/afterwardsCheck/saveProject.json",
                data: {
                    formId: formId
                },
                success: function (data) {
                    if (data.success) {
                        var $thisTr = $('.fnTxbox').find('tr:last');
                        $thisId = data.id;
                        $thisTr.append('<input type="hidden" name="id" value=' + $thisId + '>');
                        $thisTr.find('div.func').attr('href', data.url);
                    } else {
                        Y.alert('提醒', data.message);
                    }

                }
            });
        }, 1000);
    });


    $(document).on("change",".eSel",function() {
        if($(this).find('option:selected').val() == 'PROJECTING'){
            $(this).closest('tr').find('.func').html($('#tplUpLoad').html())
        }else{
            $(this).closest('tr').find('.func').html('<span class="green fnFillIn" style="cursor: pointer;">填写详情</span>')
        }

    });
    $(document).on("click",".fnUpload",function(){
        var _this = $(this);
        $(".fnShowEndFileBox").addClass("fn-hide");
        _this.siblings(".fnShowEndFileBox").removeClass("fn-hide");
    });

    $(document).on('click', '.close',function() {
        $(this).parents(".fnShowEndFileBox").addClass("fn-hide");
    })
});