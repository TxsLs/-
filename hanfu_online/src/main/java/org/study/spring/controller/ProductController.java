package org.study.spring.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.lang.DataType;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.spring.BaseController;
import org.study.spring.Entity;
import org.study.spring.entity.Product;
import org.study.spring.service.ProductService;
import org.study.spring.entity.Photo;

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

	@ApiOperation(value = "查询所有实体", notes = "")
	@ApiImplicitParam(name = "sort", value = "排序规则字符串")
	@RequestMapping(value = "/queryAll", method = { RequestMethod.GET })
	public @ResponseBody Result<List<? extends Entity>> queryAll(String sort) {
		log.debug("call queryAll");
		List<? extends Entity> list = this.service().findAll(null, Sort.parse(sort));
		return Result.toResult(list);
	}

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "searchCode", value = "代码(支持like)，允许null"),
			@ApiImplicitParam(name = "searchName", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<? extends Entity>> queryPage( String name, String sort,
			String categoryId, String price, String status, String empStatus, @RequestParam("pageNum") long pageNum,
			@RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();
		where.like("status", "1");
		where.like("empStatus", "1");
		if (StringUtils.isNotEmpty(name))
			where.like("name", name);
		if (StringUtils.isNotEmpty(categoryId))
			where.like("categoryId", categoryId);
		if (StringUtils.isNotEmpty(price))
			where.like("price", price);
		if (StringUtils.isNotEmpty(status))
			where.like("status", status);
		if (StringUtils.isNotEmpty(empStatus))
			where.like("empStatus", empStatus);
		
		
		PageSet<? extends Entity> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}

	@ApiOperation(value = "下载商品照片")
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
}
