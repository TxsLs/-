



// function checkPassword() {
//   var password = document.getElementById("password").value;
//   var confirmPassword = document.getElementById("confirmpassword").value;
//   var errorContainer = document.getElementById("err_password");

//   if (password !== confirmPassword) {
//     errorContainer.innerText = "两次输入的密码不一致！";
//   } else {
//     errorContainer.innerText = "";
//   }
// }



$(function () {
  //前端表单验证


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
        }
      },

      confirmpassword: {
        validators: {
          notEmpty: {
            message: '密码不能为空'
          },

          identical: {//相同
            field: 'password',
            message: '两次输入的密码不一致'
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

    /*  // 检查密码是否一致
      var password = document.getElementById("password").value;
      var confirmPassword = document.getElementById("confirmpassword").value;
 
      if (password !== confirmPassword) {
        // 密码不一致，提示错误信息
        $.toasts({
          type: 'error',
          content: '密码不一致！',
 
        });
        return false;
      }*/

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
      method: 'Post',
      url: 'http://127.0.0.1:8081/hanfu/employee/addEmployee',
      //表单数据
      data: data,
      processData: false,
      contentType: false,
    }).then(response => {
      if (response.result) {
        $.toasts({
          type: 'success',
          content: '注册成功！',
          onHidden: function () {
            location.href = 'login.html';
          }
        });
      } else if (response.hasError) {
        var errorMessage = rock.errorText(response, "注册错误!");
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