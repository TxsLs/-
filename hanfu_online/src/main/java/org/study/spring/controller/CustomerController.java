package org.study.spring.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.study.spring.AppUtils;
import org.study.spring.BaseController;
import org.study.spring.entity.Customer;
import org.study.spring.entity.Photo;
import org.study.spring.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

//@CrossOrigin(allowCredentials = "true",origins = {"http://127.0.0.1:5501","http://localhost:5501"})
@Slf4j
@Api(tags = "用户管理模块")
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer, CustomerService> {

	@ApiOperation(value = "添加一个实体", notes = "允许文件上传")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "代码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "form"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "email", value = "电子邮箱", paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "电话", paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = true, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "additionalName", value = "附件名称", paramType = "form"),
			@ApiImplicitParam(name = "additionalFile", value = "附件文件url", paramType = "form") })
	@RequestMapping(value = "/addCustomer", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addCustomer(@ApiIgnore Customer vo,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call addCustomer");
		boolean exist = this.service().existByName("code", vo.getCode(), null);
		boolean result = false;
		if (!exist) {//注册账户时先判断输入的账户名是否存在
			result = this.service().insert(vo, true);
			if (result && file != null && !file.isEmpty()) {
				result = this.updatePhoto(vo.id(), file);
			}
			return Result.of(result);
		} else {
			return Result.toResult("1067", "账号名已存在！请重试！");
		}
	}

	@ApiOperation(value = "更新一个实体", notes = "允许文件上传")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "代码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "form"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "email", value = "电子邮箱", paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "电话", paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = true, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "additionalName", value = "附件名称", paramType = "form"),
			@ApiImplicitParam(name = "additionalFile", value = "附件文件url", paramType = "form") })
	@RequestMapping(value = "/updateCustomer", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateCustomer(@ApiIgnore Customer vo,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call updateCustomer");
		boolean result = this.service().update(vo, true, null);
		if (result && file != null && !file.isEmpty()) {
			result = this.updatePhoto(vo.id(), file);
		}
		return Result.of(result);
	}

	//更新照片
	private boolean updatePhoto(long id, MultipartFile file) throws IOException {
		Photo photo = new Photo();
		photo.setId(id);
		if (file != null && !file.isEmpty()) {
			photo.setPhoto(file.getBytes());
			String extName = FilenameUtils.getExtension(file.getOriginalFilename());
			String fileName = IOUtil.getUniqueFileName("up", "." + extName);
			photo.setPhotoFile(fileName);
		}
		return this.service().updatePhoto(photo);
	}

	@ApiOperation(value = "根据顾客id更新自己的信息", notes = "当前用户可以修改自己少部分个人信息")
	@ApiImplicitParam(name = "vo", value = "要修改的用户信息", required = true)
	@RequestMapping(value = "/updateSelfInfo", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateSelfInfo(@RequestBody Customer vo, @ApiIgnore HttpSession session) {
		log.debug("call updateSelfInfo");
		boolean ok = false;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();

			Customer customer = service().findByCode(code);
			if (vo.equals(customer)) {
				ok = this.service().updateSelfInfo(vo);
			}
		}
		return Result.of(ok);
	}

	@ApiOperation(value = "重设密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "用户代码", required = true),
			@ApiImplicitParam(name = "password", value = "新密码", required = true) })
	@RequestMapping(value = "/resetPwd", method = { RequestMethod.POST })
	//@Secured({ "ROLE_ADMIN" })
	public @ResponseBody Result<Boolean> resetPwd(@RequestParam String code, @RequestParam String password) {
		log.debug("call resetPwd");
		boolean result = this.service().changePassword(code, password);
		return Result.of(result);
	}

	@ApiOperation(value = "用户修改自己的密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true) })
	@RequestMapping(value = "/changeSelfPassword", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> changeSelfPassword(@RequestParam String oldPassword,
			@RequestParam String newPassword, @ApiIgnore HttpSession session) {
		log.debug("call changeSelfPassword");
		boolean ok = false;
		//log.debug(AppUtils.getAuthentication().toString());

		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			log.debug(code + "000000000000");
			ok = this.service().changeSelfPassword(code, oldPassword, newPassword);
		}
		return Result.of(ok);
	}

	@ApiOperation(value = "下载用户照片")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/photo", method = { RequestMethod.GET })
	public ResponseEntity<byte[]> photo(@RequestParam long id) {
		log.debug("call photo");
		Photo photo = this.service().getPhoto(id);
		if (Photo.isEmpty(photo))
			return null;
		//
		BodyBuilder builder = ResponseEntity.ok().contentLength(photo.length());
		builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
		builder.header("Content-Disposition", "attachment; filename=" + photo.getPhotoFile());
		return builder.body(photo.getPhoto());
	}

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "工号(支持like)，允许null", dataType = "long"),
			@ApiImplicitParam(name = "name", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "joinTime", value = "开始时间，允许null"),
			@ApiImplicitParam(name = "endTime", value = "结束时间，允许null"),
			@ApiImplicitParam(name = "admin", value = "角色，允许null"),
			@ApiImplicitParam(name = "status", value = "状态，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<Customer>> queryPage(String phone, String name, String sort, String joinTime,
			String endTime, String isviolate, @Min(1) @RequestParam long pageNum, @Min(1) @RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();

		if (StringUtils.isNotEmpty(phone))
			where.like("phone", phone);
		if (StringUtils.isNotEmpty(name))
			where.like("name", name);
		//			if (StringUtils.isNotEmpty(admin))
		//				where.like("admin", admin);
		if (StringUtils.isNotEmpty(isviolate))
			where.like("isviolate", isviolate);
		if (StringUtils.isNotEmpty(joinTime) && StringUtils.isNotEmpty(endTime)) {
			where.between("createTime", joinTime, endTime); // created_time为时间字段名
		}

		PageSet<Customer> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize, "password");
		return Result.toResult(ps);
	}

	@ApiOperation(value = "找回自己的密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "newPassword", value = "新密码", required = true),
			@ApiImplicitParam(name = "code", value = "账号", required = true),
			@ApiImplicitParam(name = "name", value = "姓名", required = true),
			@ApiImplicitParam(name = "phone", value = "电话号", required = true, dataType = "long"), })
	@RequestMapping(value = "/findSelfPassword", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> findSelfPassword(@NotBlank @RequestParam String code,
			@NotBlank @RequestParam String name, @NotNull @RequestParam Long phone,
			@NotBlank @RequestParam String newPassword) {
		log.debug("call changeSelfPassword");
		boolean ok = false;
		//if (AppUtils.isLogin()) {
		//String code = AppUtils.getLoginUser().getUsername();
		Customer employee = this.service().findByCode(code);
		if (employee == null) {
			return Result.toResult("1072", "该账号不存在！");
		} else {
			if (employee.getCode().equals(code) && employee.getName().equals(name) && employee.getPhone().equals(phone)
					&& employee.getIsviolate().equals(1)) {
				ok = this.service().changePassword(code, newPassword);
			} else if (employee.getIsviolate().equals(0)) {
				return Result.toResult("1066", "您的账户已被封禁，请联系管理员邮箱:1034710773@qq.com!");

			} else {
				return Result.toResult("1069", "用户信息不匹配，请重试！");
			}
		}

		//}
		return Result.of(ok);
	}
	
	@ApiOperation(value = "checkpwd", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "用户代码", required = true),
			@ApiImplicitParam(name = "password", value = "密码", required = true) })
	@RequestMapping(value = "/checkpwd", method = { RequestMethod.POST })
	//@Secured({ "ROLE_ADMIN" })
	public @ResponseBody Result<Boolean> checkpwd(@NotBlank @RequestParam String code,
			@NotBlank @RequestParam String password) {
		log.debug("call checkpwd");
		boolean ok = false;
		Customer result = this.service().checkPassword(code, password);
		if (result != null) {
			ok = true;
		}
		return Result.of(ok);
	}
	

}
