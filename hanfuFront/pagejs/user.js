var mvc = rock.initSvr(["dept", "job", "workstate", "love", "user"]);
var deptService = mvc.findService("dept");
var jobService = mvc.findService("job");
var workstateService = mvc.findService("workstate");
var loveService = mvc.findService("love");
var userService = mvc.findService("user");
rock.initBasicMethod([deptService, jobService, workstateService, loveService, userService]);

userService.addPostMethod("resetPwd", true);
userService.addPostMethod("addUser", true);
userService.addPostMethod("updateUser", true);
userService.addGetMethod("deletePhoto", true);

$(function () {
	//初始化工具提示
	rock.initTooltips("form input.form-control,select.form-control");
	//给必输项前面加*号
	$("label.control-label.text-danger").prepend("<span style='font-size:120%; color: red'> * </span>");
	//初始化控件的输入键行为
	rock.initInputKey("[tabindex]");
	//初始化对话框
	rock.initModalDialog();
	//初始化日历组件
	rock.initDatetimepicker(".input-group.date", {
		language: "zh-CN",
		autoclose: true,
		todayHighlight: true,
		format: "yyyy-mm-dd",
		minView: 2,
		todayBtn: true
	});
	dtPicker = rock.initDatetimepicker({ format: "yyyy-mm-dd" });
	//装载数据列表
	loadJobs();
	loadDepts();
	loadWorkstates();
	loadLoves();
	loadTableData();
	//初始化校验器
	var bv = rock.initValidators("frmEdit", [":disabled", "textarea"], {
		code: {
			validators: {
				ajax: {
					message: "工号已经存在",
					url: userService.url("existsByName"),
					data: function ($field) {
						var frm = $field.prop("form");
						var oldData = $(frm).data("oldData");
						var data = {
							propName: "code",
							propValue: $field.val()
						};
						data.valid = oldData.code == data.propValue || oldData.ok_code == data.propValue;    //值未改变，不用去后台判断
						return data;
					},
					success: function ($field, rtn, status) {
						var result = {};
						if (rtn.hasError) {
							alert(rock.errorText(rtn), "Ajax service error!");
							result.valid = false;
							result.message = "Ajax service error!";
						} else {
							result.valid = !rtn.result;
						}
						if (result.valid) {
							var frm = $field.prop("form");
							var oldData = $(frm).data("oldData");
							oldData.ok_code = $field.val();
						}
						return result;
					}
				}
			}
		}
	});
	rock.initValidators("frmPwd", [":disabled", "textarea"]);
	//照片上传字段设置
	$("#editPhoto").change((evt) => {
		var ele = evt.target,url;
		if (rock.isMsie()) {
			url=ele.value;
		} else {
			var file = ele.files[0];
			if (file) {
				url = window.URL.createObjectURL(file);
			} else {
				url = "images/nophoto.png";
			}
		}
		$("#imgEditPhoto").attr("src", url);
	});
});

/**
 * 获得表格组件
 */
function getBootstrapTable() {
	return $("#tableData");
}

/**
 * 装载数据表数据
 */
function loadTableData() {
	var $table = getBootstrapTable();
	$table.bootstrapTable("destroy");
	$table.bootstrapTable({
		url: userService.url("queryPage"),
		queryParamsType: "page",
		queryParams: function (options) {
			var param = rock.formData($("#searchForm"));
			param.pageSize = options.pageSize;
			param.pageNum = options.pageNumber;
			param.sort = options.sortName + " " + options.sortOrder;
			return param;
		},
		responseHandler: function (rtn) {
			var data = {};
			if (rtn.hasError) {
				alert(rock.errorText(rtn, "无法获得数据列表!"));
			}
			else {
				var ps = rtn.result;
				data.total = ps.totalCount;
				data.rows = ps.content;
			}
			return data;
		},
		columns: [{
			title: '序号',
			valign: 'middle',
			align: 'center',
			width: '50px',
			formatter: function (value, row, index) {
				var options = getBootstrapTable().bootstrapTable("getOptions");
				var pageSize = options.pageSize;
				var pageNum = options.pageNumber;
				return pageSize * (pageNum - 1) + index + 1;//返回每条的序号：每页条数 * (当前页 - 1)+ 序号   
			}
		}, {
			field: 'code',
			title: '工号',
			align: 'left',
			valign: 'middle',
			width: '120px',
			sortable: true,
			formatter: function (value, row, index) {
				var result = "<a href='javascript:void(0);' title='查看用户详情' onclick='showViewById(%s)'>%s</a>";
				result = rock.format(result, [row.id, row.code]);
				return result;
			}
		}, {
			field: 'name',
			title: '姓名',
			align: 'left',
			valign: 'middle',
			sortable: true
		}, {
			field: 'gender',
			title: '性别',
			align: 'center',
			valign: 'middle',
			width: '50px',
			formatter: function (value, row, index) {
				return value == 1 ? "男" : "女";
			},
			sortable: true
		}, {
			field: 'jobId',
			title: '职务',
			align: 'left',
			valign: 'middle',
			width: '150px',
			formatter: function (value, row, index) {
				return row.jobName;
			},
			sortable: true
		}, {
			field: 'deptId',
			title: '部门',
			align: 'left',
			valign: 'middle',
			width: '150px',
			formatter: function (value, row, index) {
				return row.deptName;
			},
			sortable: true
		}, {
			field: 'workstateId',
			title: '工作状态',
			align: 'center',
			valign: 'middle',
			width: '50px',
			formatter: function (value, row, index) {
				return row.workstateName;
			},
			sortable: true
		}, {
			title: '操作',
			align: 'center',
			valign: 'middle',
			width: '180px',
			formatter: function (value, row, index) {
				var result = "";
				result += "<a href='javascript:void(0);' title='编辑' onclick='initForm({frm},true,{id})'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				result += "<a href='javascript:void(0);' title='修改密码' onclick='initPwdForm({frmPwd},true,{id})'>重设密码</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				result += "<a href='javascript:void(0);' title='删除' style='color:red;' onclick='deleteById({id})'>删除</a>";
				result = result.replace(/{id}/g, row.id).replace(/{frm}/g, '$("#frmEdit")').replace(/{frmPwd}/g, '$("#frmPwd")');
				return result;
			}
		}],
		method: "GET",
		locale: "zh-CN",
		cache: false,   //缓冲数据
		clickToSelect: false,  //是否启用点击选中行
		singleSelect: false,  //仅能选择一行
		pagination: true,  //开启分页  
		sidePagination: 'server',//服务器端分页  
		pageNumber: 1,//默认加载页  
		pageSize: 10,//每页数据
		pageList: [10, 20, 50],//可选的每页数据
		sortable: true,
		sortName: "id",  //默认排序
		sortOrder: "asc",
		uniqueId: "id",
		idField: "id",
		showRefresh: true,
		showColumns: true,
		minimumCountColumns: 3
	});
}

/**
 * 删除一条记录
 * @param id 主键id
 */
function deleteById(id) {
	var user = getBootstrapTable().bootstrapTable("getRowByUniqueId", id);
	if (confirm("是否确定删除该条记录？")) {
		userService.remove({ id: user.id }, function (rtn) {
			if (rtn.hasError) {
				alert(rock.errorText(rtn, "删除数据失败!"));
			} else {
				var $table = getBootstrapTable();
				$table.bootstrapTable("refresh");
			}
		});
	}	
}

/**
 * 初始化表单
 * @param $frm 表单控件
 * @param show 是否显示form
 * @param id 当前id(新增时为空)
 */
function initForm($frm, show, id) {
	//reset	
	var bv = $frm.data("bootstrapValidator");
	bv.resetForm(true);
	rock.formData($frm, {
		id: id,
		gender: 1,
		birthday: dtPicker.formatDate(new Date())
	});
	//init
	var modal = $frm.closest(".modal");
	var $modalTitle = modal.find(".modal-title");
	var $imgPhoto = modal.find("img.img-photo");
	if (rock.isBlank(id)) {
		$modalTitle.html("添加用户信息");
		$imgPhoto.attr("src", "images/nophoto.png");
		if (show)
			modal.modal("show");
	} else {
		$modalTitle.html("修改用户信息");
		var hasPhoto = getBootstrapTable().bootstrapTable("getRowByUniqueId", id).hasPhoto;
		if (hasPhoto)
			$imgPhoto.attr("src", userService.url("photo") + "?id=" + id);
		else
			$imgPhoto.attr("src", "images/nophoto.png");
		userService.queryOne({ id: id }, function (rtn, status) {
			if (rtn.hasError)
				alert(rock.errorText(rtn, "查询数据出错!"));
			else if (rtn.notNull) {
				var vo = rtn.result;
				rock.formData($frm, vo);
				if (show)
					modal.modal("show");
			} else
				alert("没发现该数据!");
		});
	}
}

/**
 * 提交保存表单
 * @param $frm 表单控件
 * @param id 当前id(新增时为空)
 */
function submitForm($frm, id) {
	var bv = $frm.data("bootstrapValidator").validate();
	if (bv.isValid()) {
		//没有错误反馈
		var modal = $frm.closest(".modal");
		var vo = new FormData($frm[0]);
		//vo.set("loves",vo.getAll("loves").join(","));
		if (rock.isBlank(id)) {
			//vo.set("password","111111");			
			userService.addUser(vo, function (rtn) {
				if (rtn.hasError)
					alert(rock.errorText(rtn, "保存数据出错!"));
				else if (confirm("保存数据成功，是否继续添加？")) {
					initForm($frm);
				} else {
					modal.modal('hide');
					var $table = getBootstrapTable();
					$table.bootstrapTable("refresh");
				}
			});
		} else {
			userService.updateUser(vo, function (rtn) {
				if (rtn.hasError)
					alert(rock.errorText(rtn, "保存数据出错!"));
				else {
					modal.modal('hide');
					var $table = getBootstrapTable();
					$table.bootstrapTable("refresh");
				}
			});
		}
	}else {
		alert("请检查输入数据是否正确!");
		var $first = bv.getInvalidFields().eq(0);
		$first.focus();
	}
}

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

/**
 * 装载爱好(checkbox)
 */
function loadLoves() {
	loveService.queryAll({ sort: "code" }, function (rtn, status) {
		if (rtn.hasError) {
			alert(rock.errorText(rtn, "查询爱好列表失败!"));
		} else if (rtn.notEmpty) {
			var $view = $("#viewLoves"), $edit = $("#editLoves");
			var viewItem = $view.html(), editItem = $edit.html();
			$view.empty();
			$edit.empty();
			$.each(rtn.result, function (i, vo) {
				$view.append(rock.format(viewItem, [vo.id, vo.name]));
				$edit.append(rock.format(editItem, [vo.id, vo.name]));
			});
		} else {
			alert("未查询到爱好列表!");
		}
	});
}

/**
 * 装载职务下拉列表
 */
function loadJobs() {
	jobService.queryAll({ sort: "code" }, function (rtn, status) {
		if (rtn.hasError) {
			alert(rock.errorText(rtn, "查询职务列表失败!"));
		} else if (rtn.notEmpty) {
			var list = rtn.result, option = '<option value="%s">[%s] － %s</option>';
			var $search = $("#searchJobId"), $edit = $("#editJobId");
			$.each(list, function (i, vo) {
				$search.append(rock.format(option, [vo.id, vo.code, vo.name]));
				$edit.append(rock.format(option, [vo.id, vo.code, vo.name]));
			});
		} else {
			alert("未查询到职务列表!");
		}
	});
}

/**
 * 装载工作状态下拉列表
 */
function loadWorkstates() {
	workstateService.queryAll({ sort: "code" }, function (rtn, status) {
		if (rtn.hasError) {
			alert(rock.errorText(rtn, "查询工作状态列表失败!"));
		} else if (rtn.notEmpty) {
			var list = rtn.result, option = '<option value="%s">[%s] － %s</option>';
			var $search = $("#searchWorkstateId"), $edit = $("#editWorkstateId");
			$.each(list, function (i, vo) {
				$search.append(rock.format(option, [vo.id, vo.code, vo.name]));
				$edit.append(rock.format(option, [vo.id, vo.code, vo.name]));
			});
		} else {
			alert("未查询到工作状态列表!");
		}
	});
}

/**
 * 装载部门下拉列表
 */
function loadDepts() {
	deptService.queryAll({ sort: "code" }, function (rtn, status) {
		if (rtn.hasError) {
			alert(rock.errorText(rtn, "查询部门列表失败!"));
		} else if (rtn.notEmpty) {
			var list = rtn.result, option = '<option value="%s">[%s] － %s</option>';
			var $search = $("#searchDeptId"), $edit = $("#editDeptId");
			$.each(list, function (i, vo) {
				$search.append(rock.format(option, [vo.id, vo.code, vo.name]));
				$edit.append(rock.format(option, [vo.id, vo.code, vo.name]));
			});
		} else {
			alert("未查询到部门列表!");
		}
	});
}

/**
 * 初始化口令表单
 * @param $frm 表单控件
 * @param show 是否显示form
 * @param id 当前id(新增时为空)
 */
function initPwdForm($frm, show, id) {
	//reset
	var bv = $frm.data("bootstrapValidator");
	bv.resetForm(true);
	var user = getBootstrapTable().bootstrapTable("getRowByUniqueId", id);
	rock.formData($frm, {
		id: user.id,
		code: user.code,
		name: user.name
	});
	if (show) {
		$frm.closest(".modal").modal("show");
	}
}

/**
 * 提交保存口令表单
 * @param $frm 表单控件
 * @param id 当前id
 */
function submitPwdForm($frm) {
	var bv = $frm.data("bootstrapValidator").validate();
	if (bv.isValid()) {
		var vo = rock.formData($frm);
		userService.resetPwd(vo, function (rtn) {
			if (rtn.hasError)
				alert(rock.errorText(rtn, "修改密码失败!"));
			else if (rtn.result)
				alert("修改密码成功.");
			else
				alert("修改密码失败!");
			$frm.closest(".modal").modal('hide');
		});
	} else {
		alert("请检查输入数据是否正确!");
		var $first = bv.getInvalidFields().eq(0);
		$first.focus();
	}
}
