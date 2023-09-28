var ssoUser=null;  //当前用户
var treeNode = { name: "newNode", text: "newNode", state: {}, href: "javascript:void(0)", title: function () { return this.text } };  //树节点模板串
var $banner, $treePanel, $leftMenu, $contentPanel, $content;

$(function ($) {
	$(window).resize(function (e, flag) {
		resizeContent(flag);
	}).trigger("resize", 1);  //flag=1

	$("#leftSearcher").keypress(function (e) {
		if (e.which == 13) {
			searchLeftMenu($.trim($(this).val()));
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
	showLeftMenu();
});

/**
 * 根据窗口尺寸变化修正内容容器大小
 */
function resizeContent(flag) {
	var adjustAll, adjustLeft;
	if (flag == 1) {
		adjustAll = 45;
		adjustLeft = 5;
		$banner = $(".banner");
		$treePanel = $(".panel-tree");
		$leftMenu = $("#leftMenu");
		$contentPanel = $(".panel-content");
		$content = $("#content");
	} else {
		adjustAll = 25;
		adjustLeft = 0;
	}
	var head;
	var height = $(window).height() - $banner.height() - adjustAll;
	head = $treePanel.height() - $leftMenu.height();
	$leftMenu.height(height - head - adjustLeft);
	head = $contentPanel.height() - $content.height();
	$content.height(height - head);
	$("#wsize").html($(window).width() + ',' + $(window).height());
}

/**
 * 装载用户信息
 */
function loadUser() {
	_root.loginUser({},function(rtn, status){
		if (rtn.hasError){
			alert(rock.errorText(rtn, "获得当前用户失败！"));
		}else {
			ssoUser=rtn.result;
		}
		if (rock.isNull(ssoUser)) {
			$("#loginUser").html("未知用户");
		} else {
			$("#loginUser").html(ssoUser.name);
		}
	})
}

/**
 * 显示左边的菜单树
 */
function showLeftMenu() {
	//构建菜单树
	var nodes = [{
		id:1,
		code:
		"01",
		name:"功能列表",
		children:[
			{id:101,code:"0101",name:"部门管理",actionPage:"dept.html"},
			{id:102,code:"0102",name:"职务管理",actionPage:"job.html"},
			{id:103,code:"0103",name:"用户管理",actionPage:"user.html"}
		]
	}];
	
	var tree = [];
	var selected = makeNodeTree(nodes, tree);
	if (selected) selected.state.selected = true;
	var tv = fillLeftMenuTree(tree);
	selected = tv.getSelected()[0];
	if (selected) {
		tv.revealNode(selected);  //展开选择的节点
		tv_onNodeSelected(tv, selected);
	}
}

/**
 * 构建节点树数据结构
 */
function makeNodeTree(rs, tree) {
	var node;
	for (var i = 0; i < rs.length; i++) {
		var r = rs[i];
		tree[i] = $.extend(true, {}, treeNode, { name: r.name, text: r.name });
		if (rock.isNull(r.actionPage)) {
			tree[i].selectable = false;
		} else {
			tree[i].selectable = true;
			tree[i].actionPage = r.actionPage;
			if (rock.isNull(node)) node = tree[i];
		}
		if (!rock.isEmpty(r.children)) {
			tree[i].nodes = [];
			var tmp = makeNodeTree(r.children, tree[i].nodes);
			if (rock.isNull(node)) node = tmp;
		}
	}
	return node;
}

/**
 * 当节点被选中时触发
 * @param tv treeview
 * @param node 节点
 */
function tv_onNodeSelected(tv, node) {		
	if (!rock.isNull(node.actionPage)) {
		//显示功能页面
		$("#content").attr("src", node.actionPage);
		//更新标题
		$("#actionTitle").html(node.name);
	}
	//展开节点
	if (!rock.isEmpty(node.nodes)) {
		tv.expandNode(node);
	}
}

/**
 * 使用树形结构数据填充左侧菜单DIV
 */
function fillLeftMenuTree(menuTree) {	
	var $leftMenu = $('#leftMenu').treeview({
		expandIcon: "glyphicon glyphicon-stop",    //设置要在可展开树节点上使用的图标
		collapseIcon: "glyphicon glyphicon-unchecked",  //设置要在可折叠树节点上使用的图标
		nodeIcon: "glyphicon glyphicon-user",   //设置要在所有节点上使用的默认图标，除非在数据中以每个节点为基础重写
		color: "#696969",	     //每一级通用的几点字体颜色    
		backColor: "#87CEFA",    //每一级通用的节点字北京色
		onhoverColor: "#FFFFFF",  //选中浮动颜色
		showBorder: false,    //是否在节点周围显示边框
		showTags: true,   //是否在每个节点的右侧显示标签
		highlightSelected: true,  //是否凸出显示选定的节点
		selectedColor: "#A1B1C2",  //设置选定节点的前景色
		selectedBackColor: "#F0F0F0",  //设置选定节点的背景色
		enableLinks: false,
		data: menuTree,     //获取数据节点
		onNodeSelected: function (event, node) {
			var tv = $(this).data("treeview");
			tv_onNodeSelected(tv, tv.getNode(node.nodeId));
		}
	});
	return $leftMenu.data("treeview");
}

/**
 * 从左边菜单树搜索指定的节点
 */
function searchLeftMenu(nodeText) {
	if (!rock.isBlank(nodeText)) {
		var tv = $("#leftMenu").data("treeview");
		tv.search(nodeText, { ignoreCase: false, exactMatch: false });
	}
};
