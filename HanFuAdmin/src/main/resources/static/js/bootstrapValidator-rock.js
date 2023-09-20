(function ($) {
	
	$.extend($.fn.bootstrapValidator.validators.regexp || {},{
		enableByHtml5: function($field) {
            var pattern = $field.attr('pattern');
            if (pattern) {
            	var attrs={
                    regexp: pattern,
                    message:$field.data("bv-regexp-message") || $field.attr("title")
                };
                return attrs;
            }
            return false;
        },
	});
	
	$.fn.bootstrapValidator.i18n.rest = $.extend($.fn.bootstrapValidator.i18n.rest || {}, {
        'default': 'Please enter a valid value!'
    });
	
	$.fn.bootstrapValidator.validators.rest = {
		html5Attributes: {
			message: 'message',
	        name: 'name',
	        service: 'service',
	        method: 'method',
	        data: 'data',
	        success: 'success',
	        delay: 'delay'
	    },
        destroy: function(validator, $field, options) {
            if ($field.data('bv.rest.timer')) {
                clearTimeout($field.data('bv.rest.timer'));
                $field.removeData('bv.rest.timer');
            }
        },
        validate: function(validator, $field, options) {
            var value = $field.val(),dfd = new $.Deferred();
            if (value === '') {
                dfd.resolve($field, 'rest', { valid: true });
                return dfd;
            }
            //
            var name    = options.name || $field.attr('data-bv-field'),
                data    = options.data || {},
                service = options.service;
                method  = options.method,
                success = options.success || function($field,jsonResult,status){                	
                	if (jsonResult.hasError){
                		alert(rock.errorText(jsonResult));
                		return false;
                	}else{
                		return jsonResult.result;
                	}
                };
            //data
            if ('string' === typeof data){
            	data = rock.isBlank(data)?{}:JSON.parse(data);
            }
            if ('function' === typeof data) {
                data = data.call(validator,$field) || {};
                if (rock.isNull(data[name])) data[name] = value;
            }else{
            	data[name] = value;
            }
            //提前判断是否有效
            if (data.valid){
            	dfd.resolve($field, 'rest', { valid: true });
                return dfd;
            }
            //method
            if ('string' === typeof service){
            	service=rock.isBlank(service)?null:eval(service);
            }else if ('function' === typeof service){
            	service=service.call(validator,$field);
            }
            if ('string' === typeof method){
            	if (rock.isNull(service)){
            		method=eval(method);
            	}else{
            		method=service[method];
            	}
            }
            if (rock.isNull(method)){
            	dfd.resolve($field, 'rest', { valid: false,message: 'Ajax method error!'});
                return dfd;
            }
            //success
            if ('string' === typeof success){
            	success=eval(success);
            }
            //回调函数
            function runCallback() {
            	var fn=function(result,status){
            		var ok= success.call(validator,$field,result,status);
            		ok = typeof ok === 'object' ? ok : {valid:ok};
            		dfd.resolve($field, 'rest', ok);
            	};
            	var failure =function(xhr, msg, e){
            		dfd.resolve($field, 'rest', {valid:false,message:"Ajax service error!"});
            	};
				rock.isNull(service)?method(data,fn,failure):method.call(service,data,fn,failure);
                return dfd;
            }
            //执行
            if (options.delay) {
                if ($field.data('bv.rest.timer')) {
                    clearTimeout($field.data('bv.rest.timer'));
                }
                $field.data('bv.rest.timer', setTimeout(runCallback, options.delay));
                return dfd;
            } else {
                return runCallback();
            }
        }
	};
}(window.jQuery));

(function($) {
    $.fn.bootstrapValidator.i18n.ajax = $.extend($.fn.bootstrapValidator.i18n.ajax || {}, {
        'default': 'Please enter a valid value'
    });

    $.fn.bootstrapValidator.validators.ajax = {
        html5Attributes: {
            message: 'message',
            name: 'name',
            type: 'type',
            url: 'url',
            data: 'data',
            body: 'body',
            success: 'success',
            delay: 'delay'
        },

        destroy: function(validator, $field, options) {
            if ($field.data('bv.ajax.timer')) {
                clearTimeout($field.data('bv.ajax.timer'));
                $field.removeData('bv.ajax.timer');
            }
        },

        validate: function(validator, $field, options) {
            var value = $field.val(),
                dfd   = new $.Deferred();
            if (value === '') {
                dfd.resolve($field, 'ajax', { valid: true });
                return dfd;
            }
            
            var name    = $field.attr('data-bv-field'),
                data    = options.data || {},
                url     = options.url,
                type    = options.type || 'GET',
                body    = options.body || false,
                success = options.success || function($field,jsonResult,status){                	
                	if (jsonResult.hasError){
                		alert(rock.errorText(jsonResult));
                		return false;
                	}else{
                		return jsonResult.result;
                	}
                };
                
            //data
            if ('string' === typeof data){
            	data = rock.isBlank(data)?{}:JSON.parse(data);
            }
            if ('function' === typeof data) {
                data = data.call(validator,$field) || {};
                if (rock.isNull(data[name])) data[name] = value;
            }else{
            	data[name] = value;
            }
            //提前判断是否有效
            if (data.valid){
            	dfd.resolve($field, 'ajax', { valid: true });
                return dfd;
            }
            //url
            if ('function' === typeof url) {
                url = url.call(validator, $field);
            }
            //success
            if ('string' === typeof success){
            	success=eval(success);
            }
            //回调函数
            function runCallback() {
            	var fn=function(result,status){
            		var ok= success.call(validator,$field,result,status);
            		ok = typeof ok === 'object' ? ok : {valid:ok};
            		dfd.resolve($field, 'ajax', ok);
            	};
            	var failure =function(xhr, msg, e){
            		dfd.resolve($field, 'ajax', {valid:false,message:"Ajax service error!"});
            	};            	
            	var xhr=rock.ajax(url,type,data,!body,fn,failure);            	
				dfd.fail(function() {
				    xhr.abort();
				});
            	return dfd;
            }
            //执行
            if (options.delay) {                
                if ($field.data('bv.ajax.timer')) {
                    clearTimeout($field.data('bv.ajax.timer'));
                }
                $field.data('bv.ajax.timer', setTimeout(runCallback, options.delay));
                return dfd;
            } else {
                return runCallback();
            }
        }
    };
}(window.jQuery));
