/**
 * 这是一个公共js文件，每个页面均需要导入。依赖rockjs和jquery。
 * 可以在这个文件中定义项目需要的全局常量和变量。
 */

if (typeof rock === "undefined") {
	throw new Error('common.js requires rockjs');
}

/**
 * 在这里修改提供mvc rest接口的服务器url地址
 */
var mvc_base_url = "/hanfu/";
//var mvc_base_url = "http://localhost:8080/spring-boot/";

/**
 * 调用该方法初始化业务服务对象
 * @param svrNames 服务名列表
 * @return 工程对象
 */
rock.initSvr = function (svrNames) {
	return rock.initService("spring_boot", mvc_base_url, svrNames);
}

/**
 * 初始化基本的方法
 * @param services 业务服务对象 
 */
rock.initBasicMethod = function (services) {
	if (!$.isArray(services))
		services = [services];
	services.forEach(function (service, i) {
		service.addPostMethod("add", false);
		service.addPostMethod("update", false);
		service.addPostMethod("updateMap", false);
		service.addGetMethod("remove", true);
		service.addGetMethod("removeMore", true);				
		service.addGetMethod("exists", true);
		service.addGetMethod("existsByName", true);
		//
		service.addGetMethod("queryOne", true);
		service.addGetMethod("queryByName", true);
		service.addGetMethod("queryAll", true);
		service.addGetMethod("queryAllByName", true);
		service.addGetMethod("queryPage", true);
	});
}

var mvc = rock.initSvr("_root");
var _root = mvc.findService("_root");
_root.addGetMethod("loginUser",true);
_root.addPostMethod("login",true);
_root.addGetMethod("logout",true);
