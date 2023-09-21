package org.boot.hf.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Update;
import org.boot.hf.admin.AppUtils;
import org.boot.hf.admin.BaseController;
import org.boot.hf.admin.entity.Employee;
import org.boot.hf.admin.entity.Photo;
import org.boot.hf.admin.service.EmployeeService;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.vo.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "员工管理模块")
@Controller
@Validated
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Employee, EmployeeService> {

	@ApiOperation(value = "添加员工实体", notes = "允许文件上传")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "代码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = true, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "电话号", required = true, paramType = "form", dataType = "int"),
			 })
	@RequestMapping(value = "/addEmployee", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addEmployee(@Validated({ Default.class }) @ApiIgnore Employee vo,
			@RequestPart(value = "photo", required = false) MultipartFile file
			) throws IOException {
		log.debug("call addEmployee");
		boolean result = this.service().insert(vo, true);
		if (result && file != null && !file.isEmpty()) {
			result = this.updatePhoto(vo.id(), file);
		}
		return Result.of(result);
	}

	@ApiOperation(value = "更新员工实体", notes = "允许文件上传！")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要更新实体的主键id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "code", value = "代码", required = false, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = false, paramType = "form"),
			@ApiImplicitParam(name = "admin", value = "是否管理员(1-是,0-不是)", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "phone", value = "电话号", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "status", value = "工作状态(1-在职,0-离职)", required = false, paramType = "form", dataType = "int"),
			})
	@RequestMapping(value = "/updateEmployee", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateEmployee(@Validated({ Update.class, Default.class }) @ApiIgnore Employee vo,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call updateEmployee");
		boolean result = this.service().update(vo, true, null);
		if (result && file != null && !file.isEmpty()) {
			result = this.updatePhoto(vo.id(), file);
		}
		return Result.of(result);
	}

	@ApiOperation(value = "根据员工id更新自己的信息", notes = "当前用户可以修改自己少部分个人信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要更新实体的主键id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "code", value = "代码", required = false, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = false, paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "phone", value = "电话号", required = false, paramType = "form", dataType = "int"),
			 })
	@RequestMapping(value = "/updateSelfInfo", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateSelfInfo(@Validated({ Update.class, Default.class }) @ApiIgnore Employee vo,
			@RequestPart(value = "photo", required = false) MultipartFile file, @ApiIgnore HttpSession session) {
		log.debug("call updateSelfInfo");
		boolean ok = false;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			Employee employee = service().findByCode(code);
			if (vo.equals(employee)) {
				ok = this.service().updateSelfInfo(vo);
			}
		}
		return Result.of(ok);
	}

	@ApiOperation(value = "员工修改自己的密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "newPassword", value = "新密码", required = true),
			@ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true) })
	@RequestMapping(value = "/changeSelfPassword", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> changeSelfPassword(@NotBlank @RequestParam String oldPassword,
			@NotBlank @RequestParam String newPassword, @ApiIgnore HttpSession session) {
		log.debug("call changeSelfPassword");
		boolean ok = false;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			ok = this.service().changeSelfPassword(code, oldPassword, newPassword);
		}
		return Result.of(ok);
	}

	@ApiOperation(value = "重设员工密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "用户代码", required = true),
			@ApiImplicitParam(name = "password", value = "新密码", required = true) })
	@RequestMapping(value = "/resetPwd", method = { RequestMethod.POST })
	//@Secured({ "ROLE_ADMIN" })
	public @ResponseBody Result<Boolean> resetPwd(@NotBlank @RequestParam String code,@NotBlank @RequestParam String password) {
		log.debug("call resetPwd");
		boolean result = this.service().changePassword(code, password);
		return Result.of(result);
	}

	@ApiOperation(value = "上传员工照片", notes = "照片大小不要超过500K")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/uploadPhoto", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> uploadPhoto(@NotNull @RequestParam long id,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call uploadPhoto");
		boolean result = this.updatePhoto(id, file);
		return Result.of(result);
	}

	@ApiOperation(value = "删除员工照片", notes = "")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/deletePhoto", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> deletePhoto(@NotNull @RequestParam long id) throws IOException {
		log.debug("call deletePhoto");
		boolean result = this.updatePhoto(id, null);
		return Result.of(result);
	}

	@ApiOperation(value = "下载员工照片")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/photo", method = { RequestMethod.GET })
	public ResponseEntity<byte[]> photo(@NotNull @RequestParam long id) {
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

}
