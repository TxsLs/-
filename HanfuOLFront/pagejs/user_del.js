var ssoUser = null;  //获取当前用户
_root.loginCustomer({}, function (rtn) {
    if (rtn.hasError) {
        alert(rock.errorText(rtn, "获得当前用户失败！"));
    } else {
        ssoUser = rtn.result;
    }
    if (rock.isNull(ssoUser)) {
        $.toasts({
            type: 'danger',
            content: '未登录！请返回登录',
            delay: 3500,
            onHidden: function () {
                top.location.replace('../login.html');
            }
        });
    } else {



        //前端表单验证
        $('#check').formValidation({
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
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        // stringLength: {
                        //     min: 6,
                        //     max: 20,
                        //     message: '密码长度必须在6到20个字符之间'
                        // }
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
            let data = $.param(formData);


            $.modal({
                body: '确定要注销账号吗？（此操作将删除此账号的所有信息且无法恢复！请谨慎考虑！）',
                cancelBtn: true,

                ok: function () {


                    $.ajax({
                        //跨域
                        xhrFields: {
                            withCredentials: true
                        },
                        url: "http://127.0.0.1:8080/spring-boot/customer/checkpwd",
                        method: 'POST',
                        data: data
                    }).then(response => {
                        if (response.result) {
                            $.toasts({
                                type: 'success',
                                content: '注销账号成功！！',
                                delay: 2000, // 显示的时间（毫秒）
                                onHidden: function () {

                                    _root.logout({}, (rtn) => {
                                        if (rtn.hasError || !rtn.result) {
                                            $.toasts({
                                                type: 'danger',
                                                content: '注销登录出错!',

                                            })

                                        } else {

                                            $.ajax({
                                                //跨域
                                                xhrFields: {
                                                    withCredentials: true
                                                },
                                                url: "http://127.0.0.1:8080/spring-boot/customer/remove",
                                                method: 'get',
                                                data: { id: ssoUser.id }
                                            }).then(response => {

                                                if (response.result) {
                                                    $.toasts({
                                                        type: 'success',
                                                        content: '删除账号信息成功！',
                                                        onHidden: function () {
                                                            top.location.replace('../index.html');
                                                        }
                                                    });

                                                } else {
                                                    $.toasts({
                                                        type: 'danger',
                                                        content: '注销用户失败！',
                                                        onHidden: function () {
                                                            parent.modalInstance.setData(true);
                                                            parent.modalInstance.close();
                                                        }
                                                    });

                                                }
                                            });




                                        }
                                    });



                                }
                            });

                        } else {
                            $.toasts({
                                type: 'danger',
                                content: '用户名或密码不正确！',

                            });

                        }



                    });



                },
                cancel: function () {
                    location.reload();
                }

            })










        });


    }


});