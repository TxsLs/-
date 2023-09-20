package org.study.spring.controller;

import java.util.Arrays;
import java.util.Map;

import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.util.MapUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.spring.BaseController;
import org.study.spring.entity.Product;
import org.study.spring.entity.Customer;
import org.study.spring.service.ProductService;
import org.study.spring.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "商品管理模块")
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController<Product, ProductService> {
	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "categoryCode", value = "商品分类id", dataType = "long"),
			@ApiImplicitParam(name = "name", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPageByCondition", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<Product>> queryPageByCondition(String categoryCode, String name, String sort, @RequestParam int pageNum, @RequestParam int pageSize) {
		log.debug("call queryPageByCondition");
		Map<String, Object> condition = MapUtil.asMap(Arrays.asList("categoryCode", "name"),
				Arrays.asList(categoryCode, name));
		PageSet<Product> ps = this.service().queryPageByCondition(condition, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}

}
