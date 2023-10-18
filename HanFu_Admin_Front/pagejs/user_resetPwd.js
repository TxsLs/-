$(function ($) {
    $("#hide_code").hide();
     // 从 sessionStorage 中获取数据并解析为对象
     var selectedUserData = JSON.parse(sessionStorage.getItem('selectedUserData'));
     $("#code").attr("value",selectedUserData.code);

            //前端表单验证
            $('#form').formValidation({
                fields: {                  
                    password: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 20,
                                message: '密码长度必须在6到20个字符之间'
                            }
                        },

                    },
                    rePassword: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },

                            identical: {//相同
                                field: 'password',
                                message: '两次密码不一致'
                            }
                        }
                    }
                }
            }).on('success.form.fv', function (e) {
                //阻止表单提交
                e.preventDefault();
                // 得到表单对象
                let $form = $(e.target);
                // 获取表单数据
                let formData = $form.serializeArray();
                // 将序列化数据转换为字符串
                let data = $.param(formData);
                $.ajax({
                    //跨域
                    xhrFields: {
                        withCredentials: true
                    },
                    url: "http://127.0.0.1:8080/hanfu/employee/resetPwd",
                    method: 'POST',
                    data: data
                }).then(response => {
                    if (response.result) {
                        $.toasts({
                            type: 'success',
                            content: '重置密码成功！',
                            delay: 1000, // 显示的时间（毫秒）
                            autohide: true,
                            onHidden: function () {
                                parent.rolemodal.setData(true);
                                parent.rolemodal.close();
                            }
                        });

                    } else {
                        $.toasts({
                            type: 'danger',
                            content: '修改密码失败！',

                        });

                    }



                });



            });

  });
