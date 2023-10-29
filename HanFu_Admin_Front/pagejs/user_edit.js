$(function ($) {
    $("#hide_id").hide();
   
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

            // 从 sessionStorage 中获取数据并解析为对象
            var selectedUserData = JSON.parse(sessionStorage.getItem('selectedUserData'));

            $("#admin").val(selectedUserData.admin);
            $("#id").attr("value", selectedUserData.id);
            $("#code").attr("value", selectedUserData.code);
            $("#name").attr("value", selectedUserData.name);
            $("#gender").val(selectedUserData.gender);
            $("#phone").attr("value", selectedUserData.phone);
            if (selectedUserData.hasPhoto) {
                var mvc = rock.initSvr(["employee"]);
                var Service = mvc.findService("employee");
                $("#imgEditPhoto").attr("src", Service.url("photo") + "?id=" + selectedUserData.id);
            }




            $("#form").formValidation({
                //验证字段

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
                    //...
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
                    url: "http://127.0.0.1:8081/hanfu/employee/updateEmployee",
                    method: 'post',
                    data: data,
                    processData: false,
                    contentType: false,
                }).then(response => {
                    if (response.result) {
                        $.toasts({
                            type: 'success',
                            content: '修改用户信息成功！',
                            delay: 1000,
                            autohide: true,
                            onHidden: function () {
                                parent.modalInstance.setData(true);
                                parent.modalInstance.close();
                            }
                        });
                    } else if (response.hasError) {
                        var errorMessage = rock.errorText(response, "修改信息错误!");
                        $.toasts({
                            type: 'danger',
                            content: errorMessage,
                            delay: 3050, // 将显示时间设置
                            onShown: function () {
                                if (response.errorCode === "1067") {
                                    $("#code").focus();
                                }
                            }
                        });

                    }

                });

            });

        
   
});





