  //前端表单验证
  $('#form').formValidation({
    fields: {
        desc: {
            validators: {
                notEmpty: true,
            }
        }
    }
}).on('success.form.fv', function (e) {
    //阻止表单提交
    e.preventDefault();
    //得到表单对象
    let $form = $(e.target);
    let data = $form.serialize();

    alert('表单验证通过,即将发起ajax');
    //得到序列化数据
    $.ajax({
        url: "/login.php",
        method: 'POST',
        data
    }).then(function (res) {
        if (res.code === 200) {
            //登录成功
        } else {
            //登录失败
        }
    });
});