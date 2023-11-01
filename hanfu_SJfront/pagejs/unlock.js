function redirectToLogin() {
    top.location.replace('../index.html');
}



function queryBan() {

    var selectedUserData = JSON.parse(sessionStorage.getItem('selectedUserData'));
  
        $.ajax({
            //跨域
            xhrFields: {
                withCredentials: true
            },
            //url: 'http://127.0.0.1:8080/hanfu/ban/queryByName?propName=userId&propValue=' + encodeURIComponent(row.id),
            url: 'http://127.0.0.1:8081/hanfu/ban/queryByBanId',
            method: 'get',
            data: {
                userId: selectedUserData.id,
                type: 4
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

                body: '你的商品:\n' + selectedUserData.name  + '<br><br>' + '已被封禁\n' + response.result.length + '\n次' + '<br><br>' + bodyContent,



            })


        });

    




}



$(function ($) {
    //前端表单验证
    // 从 sessionStorage 中获取数据并解析为对象
    var selectedUserData = JSON.parse(sessionStorage.getItem('selectedUserData'));
    $('#name').attr('value', selectedUserData.name)

    $('#form').formValidation({

        fields: {
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
        // var name = $('#name').val();
        var dataToSend = {
            reason: reason,
            productId: selectedUserData.id,
            type: 4,
            status: 0,
            name: selectedUserData.name,
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
                        top.location.replace('../index.html');
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