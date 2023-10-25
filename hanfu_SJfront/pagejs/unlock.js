function redirectToLogin() {
    top.location.replace('../index.html');
}

$(function ($) {
    //前端表单验证
    // 从 sessionStorage 中获取数据并解析为对象
    var selectedUserData = JSON.parse(sessionStorage.getItem('selectedUserData'));
    $('#name').attr('value', selectedUserData.name)

    $('#form').formValidation({

        fields: {
            reason: {
                validators: {
                    notEmpty: {
                        message: '理由不能为空！'
                    },
                    stringLength: {
                        min: 1,
                        max: 200,
                        message: '封禁理由长度必须在1到200个字之间'
                    }
                }
            }



        }
    }).on('success.form.fv', function (e) {

        //阻止表单提交
        e.preventDefault();

        var reason = $('#reason').val();
        // var name = $('#name').val();
        var dataToSend = {
            reason: reason,
            productId: selectedUserData.id,
            type: 4,
            status: 0,
            name: selectedUserData.name,
        };

        //发起ajax请求
        $.ajax({
            method: 'Post',
            url: 'http://127.0.0.1:8082/hanfushopping/unlock/addRequestProduct',
            //表单数据
            contentType: 'application/json',

            data: JSON.stringify(dataToSend),
        }).then(response => {
            if (response.result) {
                $.toasts({
                    type: 'success',
                    content: '申请提交成功！你的申请将在七个工作日类受理，请耐心等待！',
                    delay: 3500,
                    onHidden: function () {
                        top.location.replace('../index.html');
                    }
                });
            } else if (response.hasError) {
                var errorMessage = rock.errorText(response, "提交申请失败!");
                $.toasts({
                    type: 'danger',
                    content: errorMessage,
                    delay: 3100, // 将显示时间设置
                });


            }


        });
    });
});