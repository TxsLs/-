package org.boot.hf.admin.controller;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.boot.hf.admin.BaseController;
import org.boot.hf.admin.entity.PhotoShops;
import org.boot.hf.admin.entity.Product;
import org.boot.hf.admin.service.ProductService;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.lang.DataType;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
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
@Api(tags = "商品管理模块")
@Controller
@RequestMapping("/product")
public class ProductControllrer extends BaseController<Product, ProductService> {
	@ApiOperation(value = "添加一个商品", notes = "允许文件上传")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "商品名称 ", required = false, paramType = "form"),
			@ApiImplicitParam(name = "description", value = "商品描述", required = false, paramType = "form"),
			@ApiImplicitParam(name = "price", value = "商品价格", required = false, paramType = "form", dataType = "decimal"),
			@ApiImplicitParam(name = "stock", value = "商品库存", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "categoryId", value = "商品分类id", required = false, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "categoryName", value = "商品分类名称", required = false, paramType = "form", dataType = "long"), })
	@RequestMapping(value = "/addProduct", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addUser(@ApiIgnore Product vo,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call addProduct");
		boolean result = this.service().insert(vo, true);
		if (result && file != null && !file.isEmpty()) {
			result = this.updatePhoto(vo.id(), file);
		}
		return Result.of(result);
	}

	@ApiOperation(value = "更新商品信息", notes = "允许文件上传")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "商品名称 ", required = false, paramType = "form"),
			@ApiImplicitParam(name = "description", value = "商品描述", required = false, paramType = "form"),
			@ApiImplicitParam(name = "price", value = "商品价格", required = false, paramType = "form", dataType = "decimal"),
			@ApiImplicitParam(name = "stock", value = "商品库存", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "categoryId", value = "商品分类id", required = false, paramType = "form", dataType = "long"),
			@ApiImplicitParam(name = "categoryName", value = "商品分类名称", required = false, paramType = "form", dataType = "long"), })

	@RequestMapping(value = "/updateProduct", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateProduct(// 添加注解
			@ApiIgnore Product vo, @RequestPart(value = "photo", required = false) MultipartFile file)
			throws IOException {
		log.debug("call updateProduct");
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

		/*	    log.debug("call updateMerchant");
		vo.setId(id); // 将商家ID赋值给vo对象的id属性
		boolean result = this.service().update(vo, true, null);
		if (result && file != null && !file.isEmpty()) {
		result = this.updatePhoto(vo.id(), file);
		}
		return Result.of(result);（未判断登录）*/
	}

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "merchantId", value = "工号(支持like)，允许null"),
			@ApiImplicitParam(name = "name", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "categoryId", value = "排序规则字符串"),
			@ApiImplicitParam(name = "price", value = "排序规则字符串"), 
			@ApiImplicitParam(name = "status", value = "status"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int"),
			@ApiImplicitParam(name = "empStatus", value = "empStatus") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<Product>> queryPage(String name, String sort, Long merchantId,
			String categoryId, String price, String status, String empStatus, @RequestParam long pageNum,
			@RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();
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
		if (merchantId != null)
			where.equal(DataType.LONG, "merchantId", merchantId.toString());
		//	if (jobId != null)
		//		where.equal(DataType.LONG, "jobId", jobId.toString());
		//	if (workstateId != null)
		//		where.equal(DataType.LONG, "workstateId", workstateId.toString());
		PageSet<Product> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}

	//更新照片
	private boolean updatePhoto(long id, MultipartFile file) throws IOException {
		PhotoShops photo = new PhotoShops();
		photo.setId(id);
		if (file != null && !file.isEmpty()) {
			photo.setPhoto(file.getBytes());
			String extName = FilenameUtils.getExtension(file.getOriginalFilename());
			String fileName = IOUtil.getUniqueFileName("up", "." + extName);
			photo.setPhotofile(fileName);
		}
		return this.service().updatePhoto(photo);
	}

	@ApiOperation(value = "下载商品照片")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/photo", method = { RequestMethod.GET })
	public ResponseEntity<byte[]> photo(@RequestParam long id) {
		log.debug("call photo");
		PhotoShops photo = this.service().getPhoto(id);
		if (PhotoShops.isEmpty(photo))
			return null;
		//
		BodyBuilder builder = ResponseEntity.ok().contentLength(photo.length());
		builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
		builder.header("Content-Disposition", "attachment; filename=" + photo.getPhotofile());
		return builder.body(photo.getPhoto());
	}

	@ApiOperation(value = "上传商品照片", notes = "照片大小不要超过500K")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/uploadPhoto", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> uploadPhoto(@RequestParam long id,
			@RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
		log.debug("call uploadPhoto");
		boolean result = this.updatePhoto(id, file);
		return Result.of(result);
	}

	@ApiOperation(value = "删除商品照片", notes = "")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/deletePhoto", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> deletePhoto(@RequestParam long id) throws IOException {
		log.debug("call deletePhoto");
		boolean result = this.updatePhoto(id, null);
		return Result.of(result);
	}

}
