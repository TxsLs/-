package org.study.spring.controller;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.study.spring.BaseController;
import org.study.spring.entity.Photo;
import org.study.spring.entity.Customer;
import org.study.spring.service.UserService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "用户管理模块")
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer, UserService> {
	@ApiOperation(value = "添加一个实体", notes = "允许文件上传")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "form"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form"),
			@ApiImplicitParam(name = "email", value = "电子邮箱", paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "电话", paramType = "form"),
			@ApiImplicitParam(name = "gender", value = "性别", paramType = "form"),
			@ApiImplicitParam(name = "address", value = "地址", paramType = "form"),
			@ApiImplicitParam(name = "addressTwo", value = "备选地址二", paramType = "form"),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/addUser", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addUser(@ApiIgnore Customer vo,
			@RequestPart(value = "photo", required = false) MultipartFile file,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) throws IOException {
		log.debug("call addUser");
		int result = this.service().insert(vo, ignoreNullValue);
		if (result > 0 && file != null) {
			result = this.updatePhoto(vo.id(), file);
		}
		
		//...................................................待补充，添加创建时间的代码
		return Result.of(result > 0);
	}
	
	//更新照片
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
		}

}
