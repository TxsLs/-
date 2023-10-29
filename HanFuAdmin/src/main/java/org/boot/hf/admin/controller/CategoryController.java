package org.boot.hf.admin.controller;

import java.io.IOException;

import org.boot.hf.admin.BaseController;
import org.boot.hf.admin.entity.Category;
import org.boot.hf.admin.service.CategoryService;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>CategoryController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
@Slf4j
@Api(tags = "分类管理模块")
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController<Category, CategoryService> {

	@ApiOperation(value = "加分类", notes = "")
	@ApiImplicitParam(name = "vo", value = "分类", required = true)
	@RequestMapping(value = "/addCategory", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addMerchant(@RequestBody Category vo) throws IOException {
		log.debug("call addcate");
		boolean exist = this.service().existByName("name", vo.getName(), null);
		boolean result = false;
		if (!exist) {//注册账户时先判断输入的账户名是否存在
			result = this.service().insert(vo, true);

			return Result.of(result);
		} else {
			return Result.toResult("1075", "此分类已存在！请换一个吧！*^____^*");
		}

	}

}
