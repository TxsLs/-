
$(function () {
	//初始化控件的输入键行为
	rock.initInputKey("[tabindex]");
	//初始化校验器
	var bv = rock.initValidators("frmLogin", [":disabled", "textarea"]);
	$("#imgCaptcha").click((evt) => {
		$(evt.target).attr("src", _root.url("captcha.jpg?") + Math.random());
	}).trigger("click");
	var err = rock.getUrlParam("error");
	if (err == 1)
	$.toasts({
		type: 'danger',
		content: '登录失败！',
	  });
});


/**
 * 提交保存表单
 * @param $frm 表单控件
 */
function submitForm($frm) {
	var bv = $frm.data("bootstrapValidator").validate();
	if (bv.isValid()) {
		//没有错误
		var vo = rock.formData($frm);
		_root.login(vo, (rtn) => {
			if (!$.isPlainObject(rtn))
				alert(rtn, "未返回对象!");
			else if (rtn.hasError) {
				
				if (rtn.errorCode == "1020" || rtn.errorCode == "1001" || rtn.errorCode == "1002") {

					var errorMessage = rock.errorText(rtn, "登录错误!");
					$.toasts({
						type: 'danger',
						//content: '登录失败！',
						content: errorMessage,
						delay: 3050, // 显示的时间（毫秒）
						onShown: function () {
							  $("#username").focus();
							
						  }
					  });
					  //alert(rock.errorText(rtn, "登录错误!"));
					//$("#username").focus();
				} else if (rtn.errorCode == "1003") {
					var errorMessage = rock.errorText(rtn, "登录错误!")
					$.toasts({
						type: 'danger',
						content: errorMessage,
						delay: 3050, // 显示的时间（毫秒）
						onShown: function () {
							$("#captch").focus();
						}
					  });
					  //alert(rock.errorText(rtn, "登录错误!"));
					//$("#captch").focus();
				} else {
					var errorMessage = rock.errorText(rtn, "登录错误!")
					$.toasts({
						type: 'danger',
						content: errorMessage,
						delay: 3050, // 显示的时间（毫秒）
					  });
					//alert(rock.errorText(rtn, "登录错误!"));
				}
			} else if (rtn.result) {
				$.toasts({
					type: 'success',
					content: '超级管理员登录成功！',
					onHidden: function () {
						location.href = "../index.html";
					}
				});

			} else if (!rtn.result) {
				$.toasts({
					type: 'success',
					content: '商城员工登录成功！',
					onHidden: function () {
						location.href = "../employee.html";
					}
				});

			} 
		});
	} else {
		$.toasts({
			type: 'warning',
			content: '用户名或密码输入格式不正确!',
			
		});
		//alert("请检查输入数据是否正确!");
		var $first = bv.getInvalidFields().eq(0);
		$first.focus();
	}
}
