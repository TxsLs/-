package com.example.demo.controller;

import java.io.IOException;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.BaseController;
import com.example.demo.entity.Photo;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "商品管理模块")
@Controller
@RequestMapping("/product")
public class ProductControllrer extends BaseController<Product, ProductService> {
	 		@ApiOperation(value = "添加一个商品", notes = "允许文件上传")
			@ApiImplicitParams({
					@ApiImplicitParam(name = "name", value = "商品名称 ", required = false, paramType = "form"),
					@ApiImplicitParam(name = "description", value = "商品描述", required = false, paramType = "form"),
					@ApiImplicitParam(name = "price", value = "商品价格", required = false, paramType = "form",dataType = "decimal"),
					@ApiImplicitParam(name = "stock", value = "商品库存", required = false, paramType = "form", dataType = "int"),
					@ApiImplicitParam(name = "category_id", value = "商品分类id", required = false, paramType = "form", dataType = "long"),
					@ApiImplicitParam(name = "category_name", value = "商品分类名称", required = false, paramType = "form", dataType = "long"),
					@ApiImplicitParam(name = "code", value = "检索字段", required = false, paramType = "form")})
				@RequestMapping(value = "/addProduct", method = { RequestMethod.POST })
				public @ResponseBody Result<Boolean> addUser(@ApiIgnore Product vo,
							@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
						log.debug("call addUser");
						boolean result = this.service().insert(vo, true);
						if (result && file != null && !file.isEmpty()) {
							result = this.updatePhoto(vo.id(), file);
						}
						return Result.of(result);
					}
					private boolean updatePhoto(long id, MultipartFile file) throws IOException {
						Photo photo = new Photo();
						photo.setId(id);
						if (file != null && !file.isEmpty()) {
							photo.setPhoto(file.getBytes());
						}
						return this.service().updatePhoto(photo);
		}
}
