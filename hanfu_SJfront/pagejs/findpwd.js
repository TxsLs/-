
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
        newPassword: {
          validators: {
            notEmpty: {
              message: '密码不能为空'
            },
            stringLength: {
              min: 6,
              max: 20,
              message: '密码长度必须在6到20个字符之间'
            }
          }
        },
  
        confirmpassword: {
          validators: {
            notEmpty: {
              message: '密码不能为空'
            },
  
            identical: {//相同
              field: 'newPassword',
              message: '两次输入的密码不一致'
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

      //发起ajax请求
      $.ajax({
        method: 'Post',
        url: 'http://127.0.0.1:8082/hanfushopping/merchant/findSelfPassword',
        //表单数据
        data: data,
      }).then(response => {
        if (response.result) {
          $.toasts({
            type: 'success',
            content: '重置密码成功！',
            onHidden: function () {
              location.href = 'login.html';
            }
          });
        } else if (response.hasError) {
          var errorMessage = rock.errorText(response, "找回密码失败!");
          $.toasts({
            type: 'danger',
            content: errorMessage,
            delay: 3050, // 将显示时间设置
            onShown: function () {
              if (response.errorCode === "1069") {
                $("#code").focus();
              }
            }
          });
  
  
        }
        // $.toasts({
        //   type: 'danger',
        //   content: '注册失败！',
        //   // onHidden: function () {
        //   //   location.href = 'register.html';
        //   // }
        // });
        // if (response.errorCode == "1067") {
        //   alert(rock.errorText(response, "注册错误!"));
        //   $("#code").focus();
  
        // }
  
      });
    });
  });