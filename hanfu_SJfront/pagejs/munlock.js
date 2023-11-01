function redirectToLogin() {
    window.location.href = "login.html";
  }


  function queryBan() {

    var code = $('#code').val()

    $.ajax({
        //跨域
        xhrFields: {
            withCredentials: true
        },
        //url: 'http://127.0.0.1:8080/hanfu/ban/queryByName?propName=userId&propValue=' + encodeURIComponent(row.id),
        url: 'http://127.0.0.1:8082/hanfushopping/merchant/queryByName',
        method: 'get',
        data: {
            propName: 'code',
            propValue: code
        },
    }).then(res => {
        $.ajax({
            //跨域
            xhrFields: {
                withCredentials: true
            },
            //url: 'http://127.0.0.1:8080/hanfu/ban/queryByName?propName=userId&propValue=' + encodeURIComponent(row.id),
            url: 'http://127.0.0.1:8081/hanfu/ban/queryByBanId',
            method: 'get',
            data: {
                userId: res.result.id,
                type: 2
            },
        }).then(response => {

            //用数组在取出时单个的reason不会拆开
            var reasons = [];
            response.result.forEach(ban => {
                //reasons += ban.reason;
                reasons.push(ban.reason);
            });


            // var times = '';
            // response.result.forEach(ban => {
            //   times += ban.beginTime ;
            // });
            // console.log(times)

            // 遍历所有 Ban 对象，将 beginTime 格式化为日期时间字符串
            var formattedBeginTimes = response.result.map(ban => {
                // 将时间戳转换为 Date 对象
                var beginTime = new Date(ban.beginTime);
                // 使用 Intl.DateTimeFormat 对象进行格式化
                var formattedBeginTime = new Intl.DateTimeFormat('zh-CN', {
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit',
                    hour: '2-digit',
                    minute: '2-digit',
                    second: '2-digit'
                }).format(beginTime);
                return formattedBeginTime;
            });

            // 将格式化后的 beginTime 数组和 reasons 字符串连接起来
            var bodyContent = '';
            for (var i = 0; i < formattedBeginTimes.length; i++) {
                bodyContent += '封禁日期：' + formattedBeginTimes[i] + '<br>';
                bodyContent += '封禁理由：' + reasons[i] + '<br><br>';
            }
            // // 将时间戳转换为 Date 对象
            // var beginTime = new Date(times);
            // // 使用日期时间格式函数格式化日期时间
            // var formattedBeginTime = beginTime.getFullYear() + '-' + (beginTime.getMonth() + 1) + '-' + beginTime.getDate() + ' ' +
            //   beginTime.getHours() + ':' + beginTime.getMinutes() + ':' + beginTime.getSeconds();

            window.modalInstance = $.modal({

                body: '你的账号:\n' + res.result.code  + '<br><br>' + '已被封禁\n' + response.result.length + '\n次' + '<br><br>' + bodyContent,



            })


        });

    })

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
            url: 'http://127.0.0.1:8082/hanfushopping/unlock/addRequest',
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