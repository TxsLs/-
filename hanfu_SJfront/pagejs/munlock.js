function redirectToLogin() {
    window.location.href = "login.html";
  }

$(function () {
    //前端表单验证

    $('#form').formValidation({

        fields: {
            code: {
                validators: {
                    notEmpty: {
                        message: '账号不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '用户名长度必须在1到20个字符之间'
                    }
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '用户名长度必须在1到20个字符之间'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '电话号码不能为空'
                    },
                    regexp: {
                        regexp: /^\d{11}/,
                        message: '请输入有效的11位电话号码!'
                    }
                }
            },
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
        var code = $('#code').val();
        var name = $('#name').val();
        var phone = $('#phone').val();
        var dataToSend = {
            reason: reason,
            code: code,
            type: 2,
            status: 0,
            name: name,
            phone: phone
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
                        location.href = 'login.html';
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