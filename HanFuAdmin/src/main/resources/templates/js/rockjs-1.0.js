/**
 * rock rest json service
 */

if (typeof jQuery === 'undefined') {
	throw new Error('Rockjs requires jQuery');
}

if (typeof rock === "undefined")
{
	rock = {};
	
	/**
	 * 判断变量是否是定义的
	 * @param v 变量
	 */
	rock.isDefined=function(v){
		return (typeof v !== "undefined");
	}
	
	/**
	 * 判断变量是否为null
	 * @param v 变量
	 */
	rock.isNull=function(v){
		return (typeof v === "undefined") || v===null;
	}
	
	/**
	 * 判断对象是否为空
	 * @param o 对象
	 */
	rock.isEmpty=function(o){
		var type=$.type(o);
		return type==="undefined" || o===null ||  ((type==="string" || type==="array") && o.length==0) ||(type==="object" && $.isEmptyObject(o));
	}
	
	/**
	 * 判断字符串是否为空白
	 * @param str 字符串
	 */
	rock.isBlank=function(str){
		return rock.isEmpty(str) || /^\s*$/.test(str);
	}
	/**
	 * 判断浏览器是否为IE
	 * @returns 浏览器是否为IE
	 */
	rock.isMsie=function(){
		var userAgent = navigator.userAgent;
		return userAgent.indexOf('MSIE')!=-1;
	}

	/**
	 * 判断数据对象是否是FormData
	 * @param formData 数据对象
	 * @returns 是否是FormData
	 */
	rock.isFormData=function(formData){
		try {
			return formData instanceof FormData;
		} catch (err) {
			return false;
		}
	}

	/**
	 * 查找值索引
	 */
	rock.indexOf=function(list,v){
		for (var i=0,l=list.length;i<l;i++){
			if (list[i]==v){
				return i;
			}	
		}
		return -1;
	}
	
	/**
	 * 生成错误结果字符串
	 * @param errorResult jsonResult 
	 * @param msg 自定义消息
	 */
	rock.errorText=function(errorResult,msg){
		if (rock.isBlank(msg))
			msg=errorResult.errorText;
		var errorText = msg + "\n\n错误代码：" + errorResult.errorCode + "\n错误消息："+ errorResult.errorText; 
		if (!rock.isBlank(errorResult.errorCause))
			errorText+="\n错误原因："+errorResult.errorCause;
		return errorText;
	}
	
	/**
	 * 参数格式化
	 */
	rock.format=function(message, parameters){
		if ("array" !== $.type(parameters)) {
            parameters = [parameters];
        }
        for (var i in parameters) {
            message = message.replace('%s', parameters[i]);
        }
        return message;
	}
	
	/**
	 * 获得url中的请求参数
	 */
	rock.getUrlParam = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	    var r = window.location.search.substring(1).match(reg);
	    return rock.isEmpty(r)?null:r[2];
	}
	
	/**
	 * 为对象属性赋值
	 * 支持name中带'.'的多层次对象
	 */
	rock.assignValue = function(obj,name,value){
		var index=name.indexOf('.');
		if (index==-1){
			if (rock.isDefined(obj[name])){
				if ("array"!==$.type(obj[name])){
					obj[name]=[obj[name]];
				}
				obj[name].push(value);
			}else{
				obj[name]=value;
			}
		}else{
			var parent=name.substring(0,index);
			if (rock.isNull(obj[parent])){
				obj[parent]={};
			}
			if ($.isPlainObject(obj[parent])){
				rock.assignValue(obj[parent],name.substring(index+1),value);
			}
		}
	}
	
	/**
	 * 检索对象属性值
	 * 支持name中带'.'的多层次对象
	 */
	rock.retrieveValue = function(obj,name) {
		if (rock.isEmpty(obj)){
			return undefined;
		}else if (rock.isDefined(obj[name])){
			return obj[name];
		}else {
			var index=name.indexOf('.');
			if (index==-1){
				return obj[name];
			}else{
				return rock.retrieveValue(obj[name.substring(0,index)],name.substring(index+1));
			}
		}
	}
	
	/**
	 * 检索对象属性名数组
	 * 支持name中带'.'的多层次对象
	 */
	rock.retrieveNames = function(obj,cascade){
		if (rock.isEmpty(obj)){
			return [];
		}else{
			var names=[];
			$.each(obj,function(name,value){
				if (cascade && $.isPlainObject(value)){
					$.each(rock.retrieveNames(value,cascade),function(i,v){
						names.push(name+'.'+v);
					});
				}else{
					names.push(name);
				}
			});
			return names;
		}
	}
	
	/**
	 * 获得或设置表单数据
	 * 支持name中带'.'的多层次对象
	 * @param $frm Form
	 * @param data 表单数据,如果为空或布尔值则代表提取表单数据(true:处理多层次对象)
	 * @param tip 赋值时是否要更新title属性
	 * @return json对象
	 */
	rock.formData=function($frm,data,tip){
		if ($.isPlainObject(data)){
			//set
			var list=[],names=rock.retrieveNames(data,true),flatData={};
			$.each(names,function(i,name){
				var value=rock.retrieveValue(data,name);
				flatData[name]=value;
			});
			$.each(flatData,function(name,value){
				var input=$frm.prop(name);
				var $input=$(input);
				if (rock.isNull(input) || !$input.is("[name="+name+"]")) 
					return;				
				//找到了对应的输入控件，赋值
				list.push(name);
				if ($input.is("input:radio")){
					$.each($input,function(i,ele){
						var $ele=$(ele);
						if ($ele.val()==value){
							$ele.click();
						}
					});
				}else if ($input.is("input:checkbox")){
					var values;
					if ("string" === $.type(value)){
						values=$.map(value.split(','),function(v){return $.trim(v)});
					}else if ("array" === $.type(value)){
						values=value;
					}else{
						values=[value];
					}
					$.each($input,function(i,ele){
						var $ele=$(ele);
						$ele.prop("checked",rock.indexOf(values,$ele.val())!=-1);
					});
				}else if ($input.is("textarea")) {
					$input.val(rock.isNull(value)?"":value);
				}else if ($input.is("select")){
					var values;
					if ("string" === $.type(value)){
						values=$.map(value.split(','),function(v){return $.trim(v)});
					}else if ("array" === $.type(value)){
						values=value;
					}else{
						values=[value];
					}
					$.each($input.find("option"),function(i,ele){
						var $ele=$(ele);
						$ele.prop("selected",rock.indexOf(values,$ele.val())!=-1);
					});
				}else{
					$input.val(rock.isNull(value)?"":value);
					if(tip){
						$input.prop("title",rock.isNull(value)?"":value);
					}
				}
			});
			//剩下的给空值
			$.each($frm.serializeArray(),function(i,v){
				if (rock.indexOf(list,v.name)!=-1)
					return;
				//clear
				var input=$frm.prop(v.name);
				var $input=$(input);
				if ($input.is("input:radio")){
					$input.eq(0).click();
				}else if ($input.is("input:checkbox")){
					$.each($input,function(i,ele){
						$(ele).prop("checked",false);
					});
				}else if ($input.is("textarea")) {
					$input.val("");
				}else if ($input.is("select")){
					$.each($input.find("option"),function(i,ele){
						$(ele).prop("selected",false);
					});
				}else{
					$input.val("");
					if(tip){
						$input.prop("title","");
					}
				}
			});
			$frm.data("oldData",flatData);
		}else{
			//get
			if (data){
				data={};
				$.each($frm.serializeArray(),function(i,v){
					rock.assignValue(data,v.name,v.value);
				});
			}else{
				data={};
				$.each($frm.serializeArray(),function(i,v){
					if (rock.isDefined(data[v.name])){
						if ("array"!== $.type(data[v.name])){
							data[v.name]=[data[v.name]];
						}
						data[v.name].push(v.value);
					}else{
						data[v.name]=v.value;
					}
				});
			}
		}
		return data;
	}
	
	/**
	 * 检查表单数据是否有改变
	 * @param 表单
	 * @param 旧数据(可选)
	 * @return 表单是否有改变
	 */
	rock.checkFormChanged=function($frm,oldData){
		if (rock.isNull(oldData)){
			oldData=$frm.data("oldData");
		}
		var changed=false;
		var data=rock.formData($frm);		
		$.each(oldData,function(name,oldValue){
			var value=data[name];
			//只检查双方都定义的属性
			if (rock.isDefined(value) && rock.isDefined(oldValue)){
				if ("array" === $.type(value) || "array" === $.type(oldValue)){
					//只要有一方是数组则按数组进行比较
					if ("string" === $.type(value)){
						value=$.map(value.split(','),function(v){return $.trim(v)});
					}
					if ("string" === $.type(oldValue)){
						oldValue=$.map(oldValue.split(','),function(v){return $.trim(v)});
					}
					if ("array" !== $.type(value)){
						value=[value];
					}
					if ("array" !== $.type(oldValue)){
						value=[oldValue];
					}
					var len=value.length;
					if (len!=oldValue.length){
						changed=true;  //有改变
					}else{
						for (var i=0;i<len;i++){
							if (rock.indexOf(oldValue,value[i])==-1){
								changed=true;  //有改变
								break;
							}
						}
					}
				}else if (!rock.isEmpty(value) || !rock.isEmpty(oldValue)){
					if (value!=oldValue){
						changed=true;  //有改变
					}
				}
			}
			if (changed) return false;
		});
		return changed;
	}
	
	/**
	 * 初始化控件的输入键行为
	 */
	rock.initInputKey=function(ele,options){
		var _options=$.extend({},options);
		$(document).on("keypress",ele,function(e){
			var $this=$(this);
			if (e.which==13 && $this.prop("tabindex")>0 && !$this.is("a,textarea,:button,:submit,:reset")){
				var next=rock.findNextFocus(this,_options.maxTabindex);
				$(next).focus();
				e.preventDefault();
			}
		});
	}
	
	/**
	 * 查找下一个焦点控件
	 */
	rock.findNextFocus=function(focus,max){
		var $focus=$(focus);
		if (rock.isNull(max)) max=100;
		var index=$focus.prop("tabindex");
		if ($.isNumeric(index)){
			var search="[tabindex=%s]:visible";
			for (var i=++index;i<max;i++){
				var next=$(rock.format(search,i)).get(0);
				if (next) return next;
			}
		}
		return null;
	}
	
	/**
	 * 初始化控件的工具提示(popover)
	 */
	rock.initTooltips=function(ele,options){
		var _options=$.extend({},options);
		$(document).on("mouseover",ele,function(){
			var me=$(this);
			var toggle=me.data("toggle");
			if (toggle=="popover"){
				if (rock.isEmpty(me.data("content")) && !rock.isEmpty(this.title)){
					me.data("content",this.title);
					this.title="";
				}
				if (rock.isEmpty(me.data("placement"))){
					me.data("placement",rock.isEmpty(_options.placement)?"bottom":_options.placement);
				}
				me.popover('show');
			}
		});
		$(document).on("mouseout",ele,function(){
			var me=$(this);
			var toggle=me.data("toggle");
			if (toggle=="popover"){
				me.popover('destroy');
			}
		})
	}
	
	/**
	 * 初始化日历组件
	 */
	rock.initDatetimepicker=function(selector,options){
		var $ele;
		if ("string" === typeof selector){
			$ele=$(selector);
		}else{
			$ele=$("<div style='display:none;'></div>").appendTo($(document.body));
			options=selector;
		}
		$ele.datetimepicker(options);
		var dp=$ele.data("datetimepicker");
		dp.formatDate=function(v){
			if ("date"===typeof v){
				this.setDate(v);
			}else{
				this.setDate(new Date(v));
			}
			return this.getFormattedDate();
		};
		return dp;
	}
	
	/**
	 * 初始化表单校验器
	 */
	rock.initValidators=function(frmId,excluded,fields){
		frmId="#"+frmId;
		$(document).on("focus",frmId + " .form-group .form-control",function(){	
			var bv=$(this.form).data("bootstrapValidator");
			bv.resetField($(this));
		})
		//
		var $frm=$(frmId);
		$frm.bootstrapValidator({
			live: "enabled",
			trigger: "blur",
			excluded: excluded,
			feedbackIcons: {
				valid: "glyphicon",
				invalid: "glyphicon glyphicon-edit",
				validating: "glyphicon glyphicon-refresh"
			},
			fields: fields
		});
		
		return $frm.data("bootstrapValidator");
	}
	
	/**
	 * 返回表单是否有错误反馈
	 * @param 表单
	 * @return 第一个出现错误的控件组
	 */
	rock.hasErrorFeedback=function($frm){
		var $eles=$frm.find(".form-group.has-error");
		if ($eles.length>0)
			return $eles[0];
		else
			return false;
	}
	
	/**
	 * 初始化模态对话框
	 */
	rock.initModalDialog=function($dialog){
		if (rock.isNull($dialog)){
			$(document).on("shown.bs.modal",".modal",function(){
				var dialog = $(this).find(".modal-dialog");
				rock.initModalDialog(dialog);
			});
		}else{
			$dialog.draggable({
		        handle: ".modal-header",   // 只能点击头部拖动
		        refreshPositions: false,
		        scroll: false,
		        containment: "parent"
		    });
		}
	}
	
	/**
	 * 限制多行选择框只能选择一个项目
	 * @param $select 多行列表框
	 */
	rock.restrictMultiple=function($select){
		$select.change(function(evt){
			$this=$(this);
			var options=$this.find("option:selected");
			if (options.length>1){
				var selectedItem=$this.data("selectedItem");
				if ($.inArray(selectedItem,options)==-1){
					selectedItem=options.get(0);
				}
				$.each(options,function(i,option){
					if (option!==selectedItem){
						$(option).prop("selected",false);
					}
				});
			}else{
				$this.data("selectedItem",options.get(0));
			}
		});
	}
	
	/**
	 * 返回上下文路径(以斜杠结尾)
	 * @param rootPath 根路径，必须以斜杠结尾
	 * @param prjName 项目名称
	 */
	rock.contextPath=function(rootPath,prjName){
		return rootPath+prjName+"/";
	}
	
	//ajax
	rock._ajax = function(url, type, data, process, async, success, error, option) {
		process=process==null?true:process;   //默认是处理为请求参数
		if (typeof error !== "function") {
			error = function(xhr, msg, e) {
				if (xhr.status && xhr.responseText)
					alert("执行ajax时发生错误："+xhr.status+"\n"+xhr.responseText);
				else
					alert("执行ajax时发生错误："+ msg +".\n路径：" + url + "\n原因：" + (rock.isBlank(e)?"未知":e) + "!");
			};
		}
		var opts={
			url : url,
			async : async,
			type : type,
			processData : rock.isFormData(data)?false:process,
			contentType : rock.isFormData(data)?false:process?"application/x-www-form-urlencoded":"application/json",
			dataType : "text",
			data : (!process && $.isPlainObject(data))?JSON.stringify(data):data,
			error : error,
			success : function(rtn, status) {
				if (typeof success === "function")
				{
					var result;
					try {
						result=JSON.parse(rtn);
					} catch (error) {
						result=rtn;
					}
					success(result, status);
				}
			}
		};
		if ($.isPlainObject(option)){
			$.extend(true,opts,option);
		}
		var xhr=$.ajax(opts);
		return xhr;
	};

	/**
	 * 执行异步ajax调用
	 * @param url url地址
	 * @param type 请求类型(GET/POST) 
	 * @param data json参数对象，不能为空
	 * @param process 是否处理请求参数数据(true:请求参数字符串,false:json)
	 * @param success 成功回调函数，不能为空
	 * @param error 失败回调函数,可以为空
	 * @param option 额外选项,可以为空
	 */
	rock.ajax = function(url, type, data, process, success, error, option){
		return rock._ajax(url,type,data,process,true,success,error,option);
	}

	/**
	 * 执行同步ajax调用
	 * @param url url地址
	 * @param type 请求类型(GET/POST) 
	 * @param data json参数对象，不能为空
	 * @param process 是否处理请求参数数据(true:请求参数字符串,false:json)
	 * @param success 成功回调函数，不能为空
	 * @param error 失败回调函数,可以为空
	 * @param option 额外选项,可以为空
	 */
	rock.callAjax = function(url, type, data, process, success, error, option){
		return rock._ajax(url,type,data,process,false,success,error,option);
	}
	
	rock._prjs = {};

	/**
	 * 调用该方法初始化业务服务对象，例子:
	 * var sso_mvc=rock.initService("sso_mvc","../../sso-mvc/",["dept","user"]);
	 * @param prjName 项目名
	 * @param baseUrl 项目根url，必须以斜杠结尾
	 * @param serviceNames 服务名列表
	 * @param 工程对象
	 */
	rock.initService = function(prjName, baseUrl, serviceNames) {
		if (rock.isNull(rock._prjs[prjName]))
			rock._prjs[prjName]={prjName:prjName,_services:{}};
		var prj=rock._prjs[prjName];
		if (rock.isBlank(baseUrl))		
			baseUrl=prj["baseUrl"] || "";
		prj["baseUrl"]=baseUrl;
		if(rock.isBlank(serviceNames))
			serviceNames="_root";
		if  (typeof serviceNames==="string")
			serviceNames=[serviceNames];
		prj.fullUrl=function(url){
			if (url.charAt(0)=='/')
				url=url.substring(1);
			return this.baseUrl+url;
		};
		serviceNames.forEach(function(serviceName) {
			if(rock.isBlank(serviceName))
				serviceName="_root";
			var service = prj._services[serviceName] = {
				_baseUrl : baseUrl,
				_serviceUrl : (serviceName=="_root")? baseUrl:(baseUrl+serviceName+"/"),
				_callMethod : function(methodName,type,vo,process,success,error,queryString,option) {
					var url=this.url(methodName);
					if (!rock.isBlank(queryString)) url=url+"?"+queryString;
					rock.ajax(url,type,vo,process,success,error,option);
				},
				/**
				 * 添加rest方法
				 * @param name 方法名
				 * @param type 方法类型（POST/GET）
				 * @param process 是否处理请求参数数据(true:请求参数字符串,false:json)
				 * @param alias 方法别名(可选)
				 */
				addMethod : function(name,type,process,alias) {
					if (rock.isBlank(alias)) alias=name;
					this[alias] = function(vo,success,error,queryString,option) {
						this._callMethod(name,type,vo,process,success,error,queryString,option);
					};
				},
				/**
				 * 添加rest方法(GET)
				 * @param name 方法名
				 * @param process 是否处理请求参数数据(true:请求参数字符串,false:json)
				 * @param alias 方法别名(可选)
				 */
				addGetMethod : function (name,process,alias) {
					this.addMethod(name,"GET",process,alias);
				},
				/**
				 * 添加rest方法(POST)
				 * @param name 方法名
				 * @param process 是否处理请求参数数据(true:请求参数字符串,false:json)
				 * @param alias 方法别名(可选)
				 */
				addPostMethod : function (name,process,alias) {
					this.addMethod(name,"POST",process,alias);
				},
				/**
				 * 返回baseUrl
				 */
				baseUrl : function(){
					return this._baseUrl; 
				},
				/**
				 * 返回本服务或指定方法使用的url
				 * @params name 方法名称，如果没有则返回本服务url
				 */
				url : function(name){
					if (name==null)
						return this._serviceUrl;
					else
						return this._serviceUrl+name;
				}
			};
		});
		
		/**
		 * 调用该方法获得指定的服务,每个业务方法都有5个参数:json参数对象,成功回调函数,失败回调函数,查询字符串,options。例子:
		 * var sso_mvc=rock.initService("sso_mvc","../../sso-mvc/","dept");
		 * var service=sso_mvc.findService("dept");
		 * service.get({id:1},function(rtn,status){ 
		 *   if (rtn.hasError) {
		 *     alert(rtn.errorText); 
		 *   }else if (rtn.notNull) {
		 *     alert(rtn.result.id); 
		 *   }else { 
		 *     alert("没发现该数据!"); 
		 *   }
		 * });
		 */
		prj.findService = function(serviceName) {
			return rock.findService(this.prjName,serviceName);
		};
		return prj;
	};

	/**
	 * 调用该方法获得指定的服务,每个业务方法都有5个参数:json参数对象,成功回调函数,失败回调函数,查询字符串,options。例子:
	 * var service=rock.findService("sso_mvc","dept");
	 * service.get({id:1},function(rtn,status){ 
	 *   if (rtn.hasError) {
	 *     alert(rtn.errorText); 
	 *   }else if (rtn.notNull) {
	 *     alert(rtn.result.id); 
	 *   }else { 
	 *     alert("没发现该数据!"); 
	 *   }
	 * });
	 * 
	 * @param prjName 项目名
	 * @param serviceName 服务名
	 * @return 服务对象
	 */
	rock.findService = function(prjName,serviceName) {
		return rock._prjs[prjName]._services[rock.isEmpty(serviceName)?"_root":serviceName];
	};
}