var ssoUser = null;  //当前用户



$(function (){
	_root.loginUser(null, function (rtn, status) {
		if (rtn.hasError) {
			alert(rock.errorText(rtn, "连接到服务器失败！"));
		} else if (rtn.notNull) {
			location.href = "index.html";
		} else {
			location.href = "login.html";
		}
	});
	$("#btnLogout").click((evt)=>{
		_root.logout({},(rtn)=>{
			if (rtn.hasError || !rtn.result)
			{
				alert("注销登录出错!");
			}else 
			{
				location.href="index.html";
			}
		});
	});
	loadUser();
	
});
/**
 * 装载用户信息
 */
function loadUser() {
	_root.loadUser({}, function (rtn, status) {
		if (rtn.hasError) {
			alert(rock.errorText(rtn, "获得当前用户失败！"));
		} else {
			ssoUser = rtn.result;
		}
		if (rock.isNull(ssoUser)) {
			alert(ssoUser+"---------------");
			$("#loginUser").html("未知用户");
		} else {
			$("#loginUser").html(ssoUser.name);
		}
		if (rtn.notNull) {
			document.getElementById("login-dropdown").style.display = "none";
			document.getElementById("profile-dropdown").style.display = "block";
		} else {
			document.getElementById("login-dropdown").style.display = "block";
			document.getElementById("profile-dropdown").style.display = "none";
		}
	})
}