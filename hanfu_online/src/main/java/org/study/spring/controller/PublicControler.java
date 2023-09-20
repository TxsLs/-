package org.study.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.quincy.rock.core.cache.CachePoolRef;
import org.quincy.rock.core.cache.ObjectCache;
import org.quincy.rock.core.util.CaptchaImage;
import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.spring.AppUtils;
import org.study.spring.entity.Customer;
import org.study.spring.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@Slf4j
@Api(tags = "公用模块")
@Controller
@RequestMapping("/")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PublicControler {

	@Autowired
	private UserService service;

	@ApiOperation(value = "用户登录", notes = "可以自己添加验证码功能")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "用户代码", required = true),
			@ApiImplicitParam(name = "password", value = "密码", required = true),
			@ApiImplicitParam(name = "captcha", value = "验证码") })
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> login(@RequestParam String username, @RequestParam String password,
			String captcha, @ApiIgnore HttpSession session) {
		session.removeAttribute(AppUtils.CURRENT_LOGIN_USER_KEY);
		log.debug("call userLogin");
		if (AppUtils.useCaptcha) {
			String code = cacheCaptcha(session);
			if (code == null || !code.equals(captcha)) {
				return Result.toResult("1003", "验证码不正确!");
			}
		}
		Customer customer = service.checkPassword(username, password);
		if (customer == null) {
			return Result.toResult("1001", "用户或密码不正确!");
		}
		org.springframework.security.core.userdetails.User loginUser = new org.springframework.security.core.userdetails.User(
				customer.getCode(), customer.getPassword(), Arrays.asList());
		session.setAttribute(AppUtils.CURRENT_LOGIN_USER_KEY, loginUser);
		return Result.of(true);
	}

	@ApiOperation(value = "注销登录", notes = "")
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> logout(@ApiIgnore HttpSession session) {
		session.invalidate();//真正的删除已经登录用户的信息
		return Result.of(true);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/loginSuccess", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> loginSuccess() {
		return Result.of(true);
	}

	@ApiIgnore
	@RequestMapping(value = "/loginFail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result<Boolean> loginFail() {
		return Result.toResult("1001", "用户或密码不正确!");
	}
	
	@ApiOperation(value = "返回当前用户信息", notes = "未登录则返回null")
	@RequestMapping(value = "/loginUser", method = { RequestMethod.GET })
	public @ResponseBody Result<Customer> loginUser() {
		log.debug("call loginUser");
		Customer customer = null;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			customer = service.findByCode(code);
		}
		return Result.toResult(customer);
	}

	@ApiOperation(value = "返回验证码图片", notes = "")
	@RequestMapping(value = "/captcha.jpg", method = { RequestMethod.GET })
	public ResponseEntity<byte[]> captchaImg(@ApiIgnore HttpSession session) throws IOException {
		log.debug("call captcha.jpg");
		CaptchaImage captchaImg = new CaptchaImage();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
		String captcha = captchaImg.generateCaptcha(baos);
		BodyBuilder builder = ResponseEntity.ok().contentLength(baos.size()).cacheControl(CacheControl.noCache())
				.contentType(MediaType.IMAGE_JPEG);
		builder.header("Content-Disposition", "attachment; filename=captcha.jpg");
		cacheCaptcha(captcha, session);
		return builder.body(baos.toByteArray());
	}

	private void cacheCaptcha(String captcha, HttpSession session) {
		session.setAttribute(AppUtils.PUBLIC_LAST_CAPTCHA_KEY,
				CachePoolRef.createObjectCache(captcha, false, 5 * DateUtil.MILLISECOND_UNIT_MINUTE));
	}

	private String cacheCaptcha(HttpSession session) {
		ObjectCache<String> oc = (ObjectCache) session.getAttribute(AppUtils.PUBLIC_LAST_CAPTCHA_KEY);
		return oc == null ? null : oc.get();
	}
}
