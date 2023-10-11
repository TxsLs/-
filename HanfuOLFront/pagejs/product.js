///商品js---------
/**
 * 显示详情页
 */
function showViewById(id) {
	var hasPhoto = getBootstrapTable().bootstrapTable("getRowByUniqueId", id).hasPhoto;
	var $frm = $("#frmView");
	rock.formData($frm, { code: "正在加载..." }, true);
	var modal = $frm.closest(".modal").modal("show");
	var $imgPhoto = modal.find("img.img-photo");
	if (hasPhoto)
		$imgPhoto.attr("src", userService.url("photo") + "?id=" + id);
	else
		$imgPhoto.attr("src", "images/nophoto.png");
	userService.queryOne({ id: id }, function (rtn, status) {
		var hide = true;
		if (rtn.hasError)
			alert(rock.errorText(rtn, "查询数据出错!"));
		else if (rtn.notNull) {
			hide = false;
			var vo = rtn.result;
			vo.gender = vo.gender == 1 ? "男" : "女";
			rock.formData($frm, vo, true);
		} else
			alert("没发现该数据!");
		if (hide) modal.modal("hide");
	});
}
