var ssoUser = null;  //当前用户
$("#idname").hide();

/**
 * 装载用户信息
 */
$(function ($) {
  _root.loginUser({}, function (rtn, status) {
    if (rtn.hasError) {
      alert(rock.errorText(rtn, "获得当前用户失败！"));
    } else {
      ssoUser = rtn.result;
    }
    if (rock.isNull(ssoUser)) {
      $("#loginUser").html("未知用户");
    } else {
      $("#code").attr("value", ssoUser.code);
      $("#id").attr("value", ssoUser.id);
      $("#name").attr("value", ssoUser.name);
      $("#merchantIntroduction").val(ssoUser.merchantIntroduction);
      $("#phone").attr("value", ssoUser.phone);
      $("#email").attr("value", ssoUser.email);
      var mvc = rock.initSvr(["merchant"]);
      var Service = mvc.findService("merchant");
      if (ssoUser.hasPhoto) {

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
                message: '店铺名不能为空'
              },
              stringLength: {
                min: 1,
                max: 20,
                message: '店铺名长度必须在1到20之间'
              }
            }
          },

          merchantIntroduction: {
            validators: {
              message: '店铺描述'
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
          email: {
            validators: {
              notEmpty: {
                message: '邮箱不能为空'
              },
            }
          },
          profileImage: {
            validators: {
              file: {
                extension: 'jpeg,jpg,png,webp',
                type: 'image/jpeg,image/png,image/webp',
                maxSize: 5242880, // 5MB
                message: '请选择小于5MB的JPEG、PNG、webp格式图片文件'
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


        //发起ajax请求
        $.ajax({
          xhrFields: {
            withCredentials: true
          },//跨域权限开启
          method: 'Post',
          url: 'http://127.0.0.1:8082/hanfushopping/merchant/updateMerchant',
          //表单数据
          data: data,
          processData: false,
          contentType: false,
        }).then(res => {

          if (res || data.get("code") == ssoUser.code) {
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
  })
});