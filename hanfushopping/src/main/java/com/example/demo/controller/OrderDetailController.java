package com.example.demo.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.lang.DataType;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.BaseController;
import com.example.demo.Entity;
import com.example.demo.entity.OrderDetail;
import com.example.demo.service.OrderDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "订单详情管理模块")
@Controller
@RequestMapping("/orderdetail")
public class OrderDetailController extends BaseController<OrderDetail, OrderDetailService> {

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "long"),
			@ApiImplicitParam(name = "searchCode", value = "代码(支持like)，允许null"),
			@ApiImplicitParam(name = "searchName", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "merchantId", value = "商家id"), @ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<? extends Entity>> queryPage(Long merchantId, String productName,
			String totalPrice, String orderStatus, Long orderId, String searchName, String sort,
			@RequestParam("pageNum") long pageNum, @RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();
		if (orderId != null)
			where.equal(DataType.LONG, "orderId", orderId.toString());
		if (StringUtils.isNotEmpty(productName)) {
			where.like("productName", productName);
		}
		if (StringUtils.isNotEmpty(totalPrice)) {
			where.like("totalPrice", totalPrice);
		}
		if (StringUtils.isNotEmpty(orderStatus)) {
			where.like("orderStatus", orderStatus);
		}

		if (merchantId != null)
			where.equal(DataType.LONG, "merchantId", merchantId.toString());
		PageSet<OrderDetail> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}

	@ApiOperation(value = "根据指定的属性名和值删除所有数据", notes = "该接口继承自SimpleController")
	@RequestMapping(value = "/delAllByName", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> delAllByName(Long orderId, String sort) {
		log.debug("call delAllByName");
		Predicate where = DaoUtil.and();
		if (orderId != null)
			where.equal(DataType.LONG, "orderId", orderId.toString());
		boolean vo = this.service().deleteAll(where);
		return Result.toResult(vo);

	}

	@ApiOperation(value = "根据指定的属性名和值返回所有数据", notes = "该接口继承自SimpleController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "propName", value = "属性名", required = true),
			@ApiImplicitParam(name = "propValue", value = "属性值", required = true) })
	@RequestMapping(value = "/queryAllByName", method = { RequestMethod.GET })
	public @ResponseBody Result<List<OrderDetail>> queryAllByName(@RequestParam String propName,
			@RequestParam Object propValue, String sort) {
		log.debug("call queryAllByName");

		List<OrderDetail> vo = this.service().findAllByName(propName, propValue, Sort.parse(sort));
		
		return Result.toResult(vo);

	}

}
