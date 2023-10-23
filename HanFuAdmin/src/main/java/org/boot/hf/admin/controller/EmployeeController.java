package org.boot.hf.admin.controller;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Update;
import org.boot.hf.admin.AppUtils;
import org.boot.hf.admin.BaseController;
import org.boot.hf.admin.entity.Employee;
import org.boot.hf.admin.entity.Photo;
import org.boot.hf.admin.service.EmployeeService;
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

	@ApiOperation(value = "添加员工实体", notes = "允许文件上传！")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "代码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = true, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "电话号", required = true, paramType = "form", dataType = "long") })
	@RequestMapping(value = "/addEmployee", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addEmployee(@Validated({ Default.class }) @ApiIgnore Employee vo,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call addEmployee");
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

	@ApiOperation(value = "更新员工实体", notes = "允许文件上传！")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要更新实体的主键id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "code", value = "代码", required = false, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = false, paramType = "form"),
			@ApiImplicitParam(name = "admin", value = "是否管理员(1-是,0-不是)", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "phone", value = "电话号", required = false, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "status", value = "设置工作状态(1-在职,0-离职)", required = false, paramType = "form", dataType = "int") })
	@RequestMapping(value = "/updateEmployee", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateEmployee(
			@Validated({ Update.class, Default.class }) @ApiIgnore Employee vo,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call updateEmployee");
		String code = AppUtils.getLoginUser().getUsername();
		Employee employee = service().findByCode(code);
		if (AppUtils.isLogin() && employee.getAdmin() == 1) {
			boolean exist = this.service().existByName("code", vo.getCode(), vo.getId());
			if (!exist) {
				boolean result = this.service().update(vo, true, null);
				if (result && file != null && !file.isEmpty()) {
					result = this.updatePhoto(vo.id(), file);
				}
				return Result.of(result);
			} else {
				return Result.toResult("1067", "账号已存在！请重试！");
			}
		} else {
			return Result.toResult("1068", "未登录或权限不够！请返回登录界面！");
		}
	}

	@ApiOperation(value = "根据员工id更新自己的信息", notes = "当前用户可以修改自己少部分个人信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要更新实体的主键id", required = true, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "code", value = "账号", required = false, paramType = "form"),
			@ApiImplicitParam(name = "name", value = "名称", required = false, paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别(1-男,2-女)", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "phone", value = "电话号", required = false, paramType = "form", dataType = "long") })
	@RequestMapping(value = "/updateSelfInfo", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateSelfInfo(
			@Validated({ Update.class, Default.class }) @ApiIgnore Employee vo,
			@RequestPart(value = "photo", required = false) MultipartFile file, @ApiIgnore HttpSession session)
			throws IOException {
		log.debug("call updateSelfInfo");
		//boolean ok = false;
		if (AppUtils.isLogin()) {

			String code = AppUtils.getLoginUser().getUsername();
			Employee employee = service().findByCode(code);
			Long idLong = employee.getId();
			Boolean exist = service().existByName("code", vo.getCode(), idLong);
			if (!exist) {
				boolean result = this.service().update(vo, true, null);
				if (result && file != null && !file.isEmpty()) {
					result = this.updatePhoto(vo.id(), file);
				}
				return Result.of(result);

				/*String code = AppUtils.getLoginUser().getUsername();
				Employee employee = service().findByCode(code);
				Long idLong = employee.getId();
				Boolean exist = service().existByName("code", vo.getCode(), idLong);
				if (!exist) {
					
				//								if (file != null && !file.isEmpty()) {
				//									ok = this.updatePhoto(vo.id(), file);
				//								}
				//								if (vo.equals(employee)) {
				//									// 如果信息没有变化，只更新照片
				//									return Result.of(ok);
				//								} else {
				//									// 如果信息有变化，先更新个人信息，然后再更新照片
				//									boolean result = this.service().updateSelfInfo(vo);
				//									if (result) {
				//										ok = this.updatePhoto(vo.id(), file);
				//									}
				//									return Result.of(result);
				//								}
				System.out.println(vo.equals(employee));
					if (vo.equals(employee)) {
					
						if (ok && file != null && !file.isEmpty()) {
							ok = this.updatePhoto(vo.id(), file);
						}
						return Result.of(ok);
					} else {
						ok = this.service().updateSelfInfo(vo);
						if (ok && file != null && !file.isEmpty()) {
							ok = this.updatePhoto(vo.id(), file);
						}
						return Result.of(ok);
					}*/

			} else {
				return Result.toResult("1067", "账号名已存在！请重试！");
			}

		} else

		{
			return Result.toResult("1068", "未登录！请返回登录界面！");
		}

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

	@ApiOperation(value = "员工找回自己的密码", notes = "")
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
		Employee employee = this.service().findByCode(code);
		if (employee != null && employee.getCode().equals(code) && employee.getName().equals(name)
				&& employee.getPhone().equals(phone)) {
			ok = this.service().changePassword(code, newPassword);
		} else if (employee.getStatus().equals(0)) {
			return Result.toResult("1066", "您的账户已被封禁，请联系管理员邮箱:1034710773@qq.com!");

		} else {
			return Result.toResult("1069", "用户信息不匹配，请重试！");
		}

		//}
		return Result.of(ok);
	}

	@ApiOperation(value = "重设员工密码", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "用户代码", required = true),
			@ApiImplicitParam(name = "password", value = "新密码", required = true) })
	@RequestMapping(value = "/resetPwd", method = { RequestMethod.POST })
	//@Secured({ "ROLE_ADMIN" })
	public @ResponseBody Result<Boolean> resetPwd(@NotBlank @RequestParam String code,
			@NotBlank @RequestParam String password) {
		log.debug("call resetPwd");
		boolean result = this.service().changePassword(code, password);
		return Result.of(result);
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
		Employee result = this.service().checkPassword(code, password);
		if (result != null) {
			ok = true;
		}
		return Result.of(ok);
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
	public @ResponseBody Result<PageSet<Employee>> queryPage(String phone, String name, String sort, String joinTime,
			String endTime, String admin, String status, @Min(1) @RequestParam long pageNum,
			@Min(1) @RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();

		if (StringUtils.isNotEmpty(phone))
			where.like("code", phone);
		if (StringUtils.isNotEmpty(name))
			where.like("name", name);
		if (StringUtils.isNotEmpty(admin))
			where.like("admin", admin);
		if (StringUtils.isNotEmpty(status))
			where.like("status", status);
		if (StringUtils.isNotEmpty(joinTime) && StringUtils.isNotEmpty(endTime)) {
			where.between("joinTime", joinTime, endTime); // created_time为时间字段名
		}

		PageSet<Employee> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize, "password");
		return Result.toResult(ps);
	}

}
