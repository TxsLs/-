package com.example.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.util.MapUtil;
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

import com.example.demo.BaseController;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.Photo;
import com.example.demo.service.MerchantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "商家模块")
@Controller
@RequestMapping("/user")
public class MerchantController extends BaseController<Merchant, MerchantService> {

	/*@ApiOperation(value = "添加一个实体", notes = "允许文件上传")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "代码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "form"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "sfzh", value = "身份证号", required = true, paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = true, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "birthday", value = "出生日期(yyyy-MM-dd)", required = true, paramType = "form"),
			@ApiImplicitParam(name = "deptId", value = "部门id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "jobId", value = "职务id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "workstateId", value = "工作状态id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "phone", value = "电话", paramType = "form"),
			@ApiImplicitParam(name = "email", value = "电子邮箱", paramType = "form"),
			@ApiImplicitParam(name = "sale", value = "薪水", paramType = "form", dataType = "BigDecimal"),
			@ApiImplicitParam(name = "loves", value = "用户爱好id列表(使用逗号分隔)", paramType = "form"),
			@ApiImplicitParam(name = "additionalName", value = "附件名称", paramType = "form"),
			@ApiImplicitParam(name = "additionalFile", value = "附件文件url", paramType = "form"),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/addUser", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addUser(@ApiIgnore Merchant vo,
			@RequestPart(value = "photo", required = false) MultipartFile file,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) throws IOException {
		log.debug("call addUser");
		int result = this.service().insert(vo, ignoreNullValue);
		if (result > 0 && file != null) {
			result = this.updatePhoto(vo.id(), file);
		}
		return Result.of(result > 0);
	}
	*/
	/*@ApiOperation(value = "更新一个实体", notes = "允许文件上传")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要更新实体的主键id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "code", value = "代码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "form"),
			@ApiImplicitParam(name = "sfzh", value = "身份证号", required = true, paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = true, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "birthday", value = "出生日期(yyyy-MM-dd)", required = true, paramType = "form"),
			@ApiImplicitParam(name = "deptId", value = "部门id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "jobId", value = "职务id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "workstateId", value = "工作状态id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "phone", value = "电话", paramType = "form"),
			@ApiImplicitParam(name = "email", value = "电子邮箱", paramType = "form"),
			@ApiImplicitParam(name = "sale", value = "薪水", paramType = "form", dataType = "BigDecimal"),
			@ApiImplicitParam(name = "loves", value = "用户爱好id列表(使用逗号分隔)", paramType = "form"),
			@ApiImplicitParam(name = "additionalName", value = "附件名称", paramType = "form"),
			@ApiImplicitParam(name = "additionalFile", value = "附件文件url", paramType = "form"),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/updateUser", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateUser(@ApiIgnore User vo,
			@RequestPart(value = "photo", required = false) MultipartFile file,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) throws IOException {
		log.debug("call updateUser");
		int result = this.service().update(vo, ignoreNullValue, null);
		if (result > 0 && (ignoreNullValue || file != null)) {
			result = this.updatePhoto(vo.id(), file);
		}
		return Result.of(result > 0);
	}
	
	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "部门id", dataType = "long"),
			@ApiImplicitParam(name = "jobId", value = "职务id", dataType = "long"),
			@ApiImplicitParam(name = "workstateId", value = "工作状态id", dataType = "long"),
			@ApiImplicitParam(name = "code", value = "工号(支持like)，允许null"),
			@ApiImplicitParam(name = "name", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPageByCondition", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<User>> queryPageByCondition(Long deptId, Long jobId, Long workstateId,
			String code, String name, String sort, @RequestParam int pageNum, @RequestParam int pageSize) {
		log.debug("call queryPageByCondition");
		Map<String, Object> condition = MapUtil.asMap(Arrays.asList("deptId", "jobId", "workstateId", "code", "name"),
				Arrays.asList(deptId, jobId, workstateId, code, name));
		PageSet<User> ps = this.service().queryPageByCondition(condition, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}
	
	@ApiOperation(value = "根据用户id更新自己的信息", notes = "当前用户可以修改自己少部分个人信息")
	@ApiImplicitParam(name = "vo", value = "要修改的用户信息", required = true)
	@RequestMapping(value = "/updateSelfInfo", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateSelfInfo(@RequestBody User vo, @ApiIgnore HttpSession session) {
		log.debug("call updateSelfInfo");
		boolean ok = false;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			User user = service().findByCode(code);
			if (vo.equals(user)) {
				int n = this.service().updateSelfInfo(vo);
				ok = n > 0;
			}
		}
		return Result.of(ok);
	}
	
	@ApiOperation(value = "用户修改自己的密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true) })
	@RequestMapping(value = "/changeSelfPassword", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> changeSelfPassword(@RequestParam String oldPassword,
			@RequestParam String newPassword, @ApiIgnore HttpSession session) {
		log.debug("call changeSelfPassword");
		boolean ok = false;
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			int n = this.service().changeSelfPassword(code, oldPassword, newPassword);
			ok = n > 0;
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
		int n = this.service().changePassword(code, password);
		return Result.of(n > 0);
	}
	
	@ApiOperation(value = "上传用户照片", notes = "照片大小不要超过500K")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/uploadPhoto", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> uploadPhoto(@RequestParam long id,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call uploadPhoto");
		int n = this.updatePhoto(id, file);
		return Result.of(n > 0);
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
	}*/

	/*//更新照片
	private int updatePhoto(long id, MultipartFile file) throws IOException {
		Photo photo = new Photo();
		photo.setId(id);
		if (file != null && !file.isEmpty()) {
			photo.setPhoto(file.getBytes());
			String extName = FilenameUtils.getExtension(file.getOriginalFilename());
			String fileName = IOUtil.getUniqueFileName("up", "." + extName);
			photo.setPhotoFile(fileName);
		}
		int n = this.service().updatePhoto(photo);
		return n;
	}*/
}
