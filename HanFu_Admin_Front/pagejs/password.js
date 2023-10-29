

$(function ($) {
    var ssoUser = null;  //获取当前用户
    _root.loginUser({}, function (rtn) {
        if (rtn.hasError) {
            alert(rock.errorText(rtn, "获得当前用户失败！"));
        } else {
            ssoUser = rtn.result;
        }
        if (rock.isNull(ssoUser)) {
            $.toasts({
                type: 'danger',
                content: '未登录！请返回登录',
                delay:3000,
                onHidden: function () {
                    location.href = 'login.html';
                }
            });
        } else {



            //前端表单验证
            $('#form').formValidation({
                fields: {
                    oldPassword: {
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
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },

                            different: {//不相同
                                field: 'oldPassword',
                                message: '新密码不能和旧密码相同！'
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
                                field: 'newPassword',
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
                // 排除 rePassword 字段
                // formData = formData.filter(item => item.name !== 'rePassword');
                // 将序列化数据转换为字符串
                let data = $.param(formData);
                $.ajax({
                    //跨域
                    xhrFields: {
                        withCredentials: true
                    },
                    url: "http://127.0.0.1:8081/hanfu/employee/changeSelfPassword",
                    method: 'POST',
                    data: data
                }).then(response => {
                    if (response.result) {
                        $.toasts({
                            type: 'success',
                            content: '修改密码成功！正在转到登录页！',
                            delay: 2000, // 显示的时间（毫秒）
                            onHidden: function () {

                                _root.logout({}, (rtn) => {
                                    if (rtn.hasError || !rtn.result) {
                                        $.toasts({
                                            type: 'danger',
                                            content: '注销登录出错!',

                                        })

                                    } else {
                                        top.location.replace('../pages/login.html');
                                    }
                                });
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


        }


    });



});
