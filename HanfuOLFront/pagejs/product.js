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

}
