$(function () {
	_root.loginUser(null,function(rtn, status){
			if (rtn.hasError){
				alert(rock.errorText(rtn, "连接到服务器失败！"));
			}else if (rtn.notNull) {
				location.href="workstate.html";
			} else {
				location.href="login.html";
			}
		})
});
