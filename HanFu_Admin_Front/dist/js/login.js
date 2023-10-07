
$(function() {
	//初始化控件的输入键行为
	rock.initInputKey("[tabindex]");
	//初始化校验器
	var bv = rock.initValidators("frmLogin", [":disabled", "textarea"]);
	$("#imgCaptcha").click((evt) => {
		$(evt.target).attr("src", _root.url("captcha.jpg?") + Math.random());
	}).trigger("click");
	var err = rock.getUrlParam("error");
	if (err == 1)
		alert("登录失败!");
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
					alert(rock.errorText(rtn, "登录错误!"));
					$("#username").focus();
				} else if (rtn.errorCode == "1003") {
					alert(rock.errorText(rtn, "登录错误!"));
					$("#captch").focus();
				} else {
					alert(rock.errorText(rtn, "登录错误!"));
				}
			} else if (rtn.result) {
				location.href = "../index.html";
			}else if (!rtn.result) {
				location.href = "../employee.html";
			} else {
				alert("用户名或密码不正确!");
			}
		});
	} else {
		alert("请检查输入数据是否正确!");
		var $first = bv.getInvalidFields().eq(0);
		$first.focus();
	}
}
