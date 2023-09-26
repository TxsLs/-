var mvc = rock.initSvr("job");
var service = mvc.findService("job");
rock.initBasicMethod(service);

$(function () {
	//初始化工具提示
	rock.initTooltips("form input.form-control");
	//给必输项前面加*号
	$("label.control-label.text-danger").prepend("<span style='font-size:120%; color: red'> * </span>");
	//初始化控件的输入键行为
	rock.initInputKey("[tabindex]");
	//初始化对话框
	rock.initModalDialog();
	//装载数据列表
	loadTableData();
	//初始化校验器
	var bv = rock.initValidators("frmEdit", [":disabled", "textarea"], {
		code: {
			validators: {
				ajax: {
					message: "编号已经存在",
					url: service.url("existsByName"),
					data: function ($field) {
						var frm = $field.prop("form");
						var oldData = $(frm).data("oldData");
						var data = { 
							propName:"code",
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
		url: service.url("queryPage"),
		queryParamsType: "page",
		queryParams: function (options) {
			var param = {};
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
			checkbox: true
		}, {
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
			title: '职务编号',
			align: 'left',
			valign: 'middle',
			width: '200px',
			sortable: true,
			formatter: function (value, row, index) {
				var result = "<a href='javascript:void(0);' title='查看详情' onclick='showViewById(%s)'>%s</a>";
				result = rock.format(result, [row.id, row.code]);
				return result;
			}
		}, {
			field: 'name',
			title: '职务名称',
			align: 'left',
			valign: 'middle',
			sortable: true
		}, {
			title: '操作',
			align: 'center',
			valign: 'middle',
			width: '100px',
			formatter: function (value, row, index) {
				var result = "";
				result += "<a href='javascript:void(0);' title='编辑' onclick='initForm({frm},true,{id})'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				result += "<a href='javascript:void(0);' title='删除' style='color:red;' onclick='deleteById({id})'>删除</a>";
				result = result.replace(/{id}/g, row.id).replace(/{frm}/g, '$("#frmEdit")');
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
		pageList: [10, 20, 50, 100, 500],//可选的每页数据
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
 * 从后台删除记录
 * @param id 主键id或id数组
 */
function removeById(id) {
	service[$.isArray(id)?"removeMore":"remove"]({ "id": id}, function (rtn, status) {
		if (rtn.hasError) {
			alert(rock.errorText(rtn, "删除数据失败!"));
		} else {
			var $table = getBootstrapTable();
			$table.bootstrapTable("refresh");
		}
	},null,null,{traditional:true});   //traditional=true可以传数组到后台
}

/**
 * 删除选中的所有记录
 */
function deleteAllSelections() {	
	var $table = getBootstrapTable();
	var rows = $table.bootstrapTable("getSelections");
	var ids = $.map(rows, function (row,i) {
		return row.id;
	});
	if (rock.isEmpty(ids)) {
		alert("请选择要删除的记录!");
	} else if (confirm("是否确定删除选定的记录？")) {
		removeById(ids);
	}
}

/**
 * 删除一条记录
 * @param id 主键id
 */
function deleteById(id) {
	if (!rock.isEmpty(id) && confirm("是否确定删除该条记录？")) {
		removeById(id);
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
		id: id
	});
	//init
	var modal = $frm.closest(".modal");
	var $modalTitle = modal.find(".modal-title");
	if (rock.isBlank(id)) {
		$modalTitle.html("添加职务信息");
		if (show)
			modal.modal("show");
	} else {
		$modalTitle.html("修改职务信息");
		service.queryOne({ id: id }, function (rtn, status) {
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
		var vo = rock.formData($frm);
		if (rock.isBlank(id)) {
			service.add(vo, function (rtn, status) {
				if (rtn.hasError)
					alert(rock.errorText(rtn, "保存数据出错!"));
				else if (confirm("保存数据成功，是否继续添加？")) {
					initForm($frm);
				} else {
					modal.modal('hide');
					var $table = getBootstrapTable();
					$table.bootstrapTable("refresh");
				}
			},null,"ignoreNullValue=true");
		} else {
			service.update(vo, function (rtn, status) {
				if (rtn.hasError)
					alert(rock.errorText(rtn, "保存数据出错!"));
				else {
					modal.modal('hide');
					var $table = getBootstrapTable();
					$table.bootstrapTable("refresh");
				}
			},null,"ignoreNullValue=true");
		}
	} else {
		alert("请检查输入数据是否正确!");
		var $first = bv.getInvalidFields().eq(0);
		$first.focus();
	}
}

/**
 * 显示详情页
 */
function showViewById(id) {
	var $frm = $("#frmView");
	rock.formData($frm, { id: "正在加载..." }, true);
	var modal = $frm.closest(".modal").modal("show");  //找到带.modal样式类的祖先元素	
	service.queryOne({ id: id }, function (rtn, status) {
		var hide = true;
		if (rtn.hasError)
			alert(rock.errorText(rtn, "查询数据出错!"));
		else if (rtn.notNull) {
			hide = false;
			var vo = rtn.result;
			rock.formData($frm, vo, true);
		} else
			alert("没发现该数据!");
		if (hide) modal.modal("hide");
	});
}
