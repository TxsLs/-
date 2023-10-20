package org.study.spring.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.exception.LoginException;
import org.quincy.rock.core.lang.DataType;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.spring.AppUtils;
import org.study.spring.BaseController;
import org.study.spring.Entity;
import org.study.spring.entity.Order;
import org.study.spring.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "订单管理模块")
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController<Order, OrderService> {

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "searchCode", value = "代码(支持like)，允许null"),
			@ApiImplicitParam(name = "searchName", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<? extends Entity>> queryPage(String searchCode, String searchName, String sort,
			@RequestParam("pageNum") long pageNum, @RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			where.equal(DataType.STRING, "customerCode", code.toString());
			if (StringUtils.isNotEmpty(searchCode)) {
				where.like("code", searchCode);
			}
			if (StringUtils.isNotEmpty(searchName)) {
				where.like("name", searchName);
			}
			PageSet<? extends Entity> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize);
			return Result.toResult(ps);
		} else {
			throw new LoginException("未登录!");
		}
	}

	@ApiOperation(value = "添加一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "vo", value = "实体值对象", required = true),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/addorder", method = { RequestMethod.POST })
	public @ResponseBody Long addorder(@Valid @RequestBody Order vo,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) {
		log.debug("call add");
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			LocalDateTime now = LocalDateTime.now();
			vo.setCode(code + now);//订单号当前时间
			vo.setCustomerCode(code);
			this.service().insert(vo, ignoreNullValue);
			return vo.getId();//返回订单id值
		} else {
			throw new LoginException("未登录!");
		}
	}

}
