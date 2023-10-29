$("#hide_id").hide();


var ssoUser = null;  //当前用户
$(function ($) {



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
                onHidden: function () {
                    top.location.replace('login.html');

                }
            });
        } else {
            $("#id").attr("value", ssoUser.id);
            $("#code").attr("value", ssoUser.code);
            $("#name").attr("value", ssoUser.name);
            $("#gender").val(ssoUser.gender);
            $("#phone").attr("value", ssoUser.phone);
            if (ssoUser.hasPhoto) {
                var mvc = rock.initSvr(["employee"]);
                var Service = mvc.findService("employee");
                $("#imgEditPhoto").attr("src", Service.url("photo") + "?id=" + ssoUser.id);
            }


            //照片上传字段设置
            $("#profileImage").change((evt) => {
                var ele = evt.target, url;
                if (rock.isMsie()) {
                    url = ele.value;
                } else {
                    var file = ele.files[0];
                    if (file) {
                        url = window.URL.createObjectURL(file);
                    }

                }


                $("#imgEditPhoto").attr("src", url);
            });




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




                    gender: {
                        validators: {
                            notEmpty: {
                                message: '请选择性别'
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
                    profileImage: {
                        validators: {
                            file: {
                                extension: 'jpeg,jpg,png',
                                type: 'image/jpeg,image/png',
                                maxSize: 5242880, // 5MB
                                message: '请选择小于5MB的JPEG或PNG格式图片文件'
                            }
                        }
                    }
                }
            }).on('success.form.fv', function (e) {
                //阻止表单提交
                e.preventDefault();
                //得到表单对象
                let $form = $(e.target);
                //获取数据
                let data = new FormData($form[0]); // 使用FormData对象

                // 获取照片文件
                let photoInput = document.getElementById('profileImage');
                if (photoInput.files.length > 0) {
                    let photo = photoInput.files[0];
                    data.append('photo', photo); // 添加照片数据到FormData对象
                }

                //得到序列化数据
                $.ajax({
                    //跨域
                    xhrFields: {
                        withCredentials: true
                    },
                    url: "http://127.0.0.1:8081/hanfu/employee/updateSelfInfo",
                    method: 'Post',
                    data: data,
                    processData: false,
                    contentType: false,
                }).then(res => {

                    if (res && data.get("code") == ssoUser.code) {
                        $.toasts({
                            type: 'success',
                            content: '修改个人信息成功！',
                            onHidden: function () {

                                top.location.replace('../index.html');
                            }
                        });
                    } else {
                        _root.logout({}, (rtn) => {
                            if (rtn.hasError || !rtn.result) {
                                $.toasts({
                                    type: 'danger',
                                    content: '注销登录出错!',

                                })

                            } else {

                                $.toasts({
                                    type: 'success',
                                    content: '修改个人信息成功！（修改账号将会退出登录！）',
                                    onHidden: function () {
                                        top.location.replace('login.html');
                                    }
                                });


                            }
                        });

                    }

                });
            });
        }
    });
});