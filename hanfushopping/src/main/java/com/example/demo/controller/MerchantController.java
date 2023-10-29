package com.example.demo.controller;

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

import com.example.demo.AppUtils;
import com.example.demo.BaseController;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.PhotoMerchant;
import com.example.demo.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "商家管理模块")
@Controller
@RequestMapping("/merchant")

public class MerchantController extends BaseController<Merchant, MerchantService> {

	@ApiOperation(value = "注册一个商家", notes = "允许文件上传")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "商家名称", required = false, paramType = "form"),
			@ApiImplicitParam(name = "merchantAddress", value = "商家地址", required = false, paramType = "form"),
			@ApiImplicitParam(name = "merchantPassword", value = "商家密码", required = false, paramType = "form"),
			@ApiImplicitParam(name = "code", value = "商家账号", required = false, paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "商家电话", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "email", value = "商家邮箱", required = false, paramType = "form") })
	@RequestMapping(value = "/addMerchant", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addMerchant(@ApiIgnore Merchant vo,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call addUser");
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

	@ApiOperation(value = "更新店铺信息", notes = "允许文件上传")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "code", value = "账号", required = false, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "店铺名称", required = false, paramType = "form"),
			@ApiImplicitParam(name = "merchantIntroduction", value = "店铺描述", required = false, paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "商家电话", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "email", value = "商家邮箱", required = false, paramType = "form") })
	@RequestMapping(value = "/updateMerchant", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateMerchant(// 添加注解
			@ApiIgnore Merchant vo, @RequestPart(value = "photo", required = false) MultipartFile file)
			throws IOException {
		log.debug("call updateMerchant");
		boolean exist = this.service().existByName("code", vo.getCode(), vo.getId());
		boolean result = this.service().update(vo, true, null);
		if (!exist) {
			if (result && file != null && !file.isEmpty()) {
				result = this.updatePhoto(vo.id(), file);
			}

			return Result.of(result);
		} else {
			return Result.toResult("1068", "未登录！请返回登录界面！");
		}

	}

	@ApiOperation(value = "根据用户id更新自己的信息", notes = "当前用户可以修改自己少部分个人信息")
	@ApiImplicitParam(name = "vo", value = "要修改的用户信息", required = true)
	@RequestMapping(value = "/updateSelfInfo", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateSelfInfo(@RequestBody Merchant vo, @ApiIgnore HttpSession session) {
		log.debug("call updateByid");
		boolean ok = false;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			Merchant user = service().findByCode(code);
			if (vo.equals(user)) {
				ok = this.service().updateSelfInfo(vo);
			}
		}
		return Result.of(ok);
	}

	@ApiOperation(value = "商家修改自己的密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true) })
	@RequestMapping(value = "/changeSelfPassword", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> changeSelfPassword(@RequestParam String oldPassword,
			@RequestParam String newPassword, @ApiIgnore HttpSession session) {
		log.debug("call changeSelfPassword");
		boolean ok = false;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			ok = this.service().changeSelfPassword(code, oldPassword, newPassword);
		}
		return Result.of(ok);
	}

	//更新照片
	private boolean updatePhoto(long id, MultipartFile file) throws IOException {
		PhotoMerchant photo = new PhotoMerchant();
		photo.setId(id);
		if (file != null && !file.isEmpty()) {
			photo.setPhoto(file.getBytes());
			String extName = FilenameUtils.getExtension(file.getOriginalFilename());
			String fileName = IOUtil.getUniqueFileName("up", "." + extName);
			photo.setPhotoFile(fileName);
		}
		return this.service().updatePhoto(photo);
	}

	@ApiOperation(value = "下载用户照片")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/photo", method = { RequestMethod.GET })
	public ResponseEntity<byte[]> photo(@RequestParam long id) {
		log.debug("call photo");
		PhotoMerchant photo = this.service().getPhoto(id);
		if (PhotoMerchant.isEmpty(photo))
			return null;
		//
		BodyBuilder builder = ResponseEntity.ok().contentLength(photo.length());
		builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
		builder.header("Content-Disposition", "attachment; filename=" + photo.getPhotoFile());
		return builder.body(photo.getPhoto());
	}

	@ApiOperation(value = "上传用户照片", notes = "照片大小不要超过500K")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/uploadPhoto", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> uploadPhoto(@RequestParam long id,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call uploadPhoto");
		boolean result = this.updatePhoto(id, file);
		return Result.of(result);
	}

	@ApiOperation(value = "删除用户照片", notes = "")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/deletePhoto", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> deletePhoto(@RequestParam long id) throws IOException {
		log.debug("call deletePhoto");
		boolean result = this.updatePhoto(id, null);
		return Result.of(result);
	}

	@ApiOperation(value = "checkpwd", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "用户代码", required = true),
			@ApiImplicitParam(name = "merchantPassword", value = "密码", required = true) })
	@RequestMapping(value = "/checkpwd", method = { RequestMethod.POST })
	//@Secured({ "ROLE_ADMIN" })
	public @ResponseBody Result<Boolean> checkpwd(@NotBlank @RequestParam String code,
			@NotBlank @RequestParam String merchantPassword) {
		log.debug("call checkpwd");
		boolean ok = false;
		Merchant result = this.service().checkPassword(code, merchantPassword);
		if (result != null) {
			ok = true;
		}
		return Result.of(ok);
	}

	@ApiOperation(value = "商家找回自己的密码", notes = "")
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
		Merchant employee = this.service().findByCode(code);
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
	public @ResponseBody Result<PageSet<Merchant>> queryPage(String phone, String name, String sort, String joinTime,
			String endTime,  String isviolate, @Min(1) @RequestParam long pageNum,
			@Min(1) @RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();

		if (StringUtils.isNotEmpty(phone))
			where.like("phone", phone);
		if (StringUtils.isNotEmpty(name))
			where.like("name", name);
//		if (StringUtils.isNotEmpty(admin))
//			where.like("admin", admin);
		if (StringUtils.isNotEmpty(isviolate))
			where.like("isviolate", isviolate);
		if (StringUtils.isNotEmpty(joinTime) && StringUtils.isNotEmpty(endTime)) {
			where.between("createdTime", joinTime, endTime); // created_time为时间字段名
		}

		PageSet<Merchant> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize, "password");
		return Result.toResult(ps);
	}
	
	
	@ApiOperation(value = "重设员工密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "用户代码", required = true),
			@ApiImplicitParam(name = "merchantPassword", value = "新密码", required = true) })
	@RequestMapping(value = "/resetPwd", method = { RequestMethod.POST })
	//@Secured({ "ROLE_ADMIN" })
	public @ResponseBody Result<Boolean> resetPwd(@NotBlank @RequestParam String code,
			@NotBlank @RequestParam String merchantPassword) {
		log.debug("call resetPwd");
		boolean result = this.service().changePassword(code, merchantPassword);
		return Result.of(result);
	}
	

}
