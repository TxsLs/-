$(function ($) {
    $("#hide_status").hide();
    // 从 sessionStorage 中获取数据并解析为对象
    var selectedUserData = JSON.parse(sessionStorage.getItem('selectedUserData'));

    $("#id").attr("value", selectedUserData.id);
    //$("#status").val(selectedUserData.status);
    $("#code").attr("value", selectedUserData.code);
    

    $("#form").formValidation({
        //验证字段

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
            },

        }
    }).on('success.form.fv', function (e) {
        //阻止表单提交
        e.preventDefault();

        // let $form = $(e.target);
        // // 获取表单数据
        // let formData = $form.serializeArray();
        // // 将序列化数据转换为字符串
        // let data = $.param(formData);

        // 获取 status 的值
        // var status = $('#status').val();
        // var id=$('#id').val();

        $.modal({
            body: '确定要封禁吗？',
            cancelBtn: true,
            ok: function () {
      
                $.ajax({
                    //跨域
                    xhrFields: {
                        withCredentials: true
                    },
                    url: "http://127.0.0.1:8080/hanfu/employee/updateEmployee",
                    method: 'post',
                    data: { status: 0, id: selectedUserData.id },
        
                }).then(response => {
                    if (response.result) {
                        $.toasts({
                            type: 'success',
                            content: '封禁用户成功！',
                            delay: 1000,
                            autohide: true,
                            // onHidden: function () {
                            //     console.log(parent.modalInstance)
                            //     parent.modalInstance.setData(true);
                            //     parent.modalInstance.close();
                            // }
                        });
                    } else if (response.hasError) {
        
                        $.toasts({
                            type: 'danger',
                            content: '封禁失败',
                            delay: 3050, // 将显示时间设置
        
                        });
                    }
                });
        
                var reason = $('#reason').val();
                var dataToSend = {
                    reason: reason,
                    userId: selectedUserData.id,
                    type: 1
                };
                $.ajax({
                    //跨域
                    xhrFields: {
                        withCredentials: true
                    },
                    url: "http://127.0.0.1:8080/hanfu/ban/add",
                    method: 'post',
                    contentType: 'application/json',
                    //data: { reason: reason, userId: selectedUserData.id,type:1 },
                    data: JSON.stringify(dataToSend),
                }).then(response => {
                    if (response.result) {
                        $.toasts({
                            type: 'success',
                            content: '添加封禁信息成功！',
                            delay: 1050,
                            autohide: true,
                            onHidden: function () {
                                parent.modalInstance.setData(true);
                                parent.modalInstance.close();
                            }
                        });
                    } else if (response.hasError) {
        
                        $.toasts({
                            type: 'danger',
                            content: '添加封禁信息失败',
                            delay: 3050, // 将显示时间设置
        
                        });
        
                    }
        
                });          
      
            
            },

   cancel:function(){
    location.reload();
   }         

          });



       



    });



});





