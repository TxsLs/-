package org.study.spring.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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
import org.study.spring.entity.Refund;
import org.study.spring.entity.Review;
import org.study.spring.service.RefundService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "退货申请模块")
@Controller
@RequestMapping("/refund")
public class RefundController extends BaseController<Refund, RefundService> {
	@ApiOperation(value = "添加一个申请实体", notes = "该接口继承自BaseController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "vo", value = "实体值对象", required = true),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/addrefund", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addreview(@Valid @RequestBody Refund vo,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) {
		log.debug("call addreview");
		if (AppUtils.isLogin()) {
			boolean exist = this.service().existByName("orderId", vo.getOrderId(), null);
			if (!exist) {
				String code = AppUtils.getLoginUser().getUsername();
				vo.setCustomerCode(code);
				vo.setRefundStatus("退货中");
				// 获取当前时间
				LocalDateTime now = LocalDateTime.now();
				Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
				vo.setRequestTime(date);
				boolean result = this.service().insert(vo, ignoreNullValue);
				return Result.of(result);
			} else {
				return Result.toResult("1067", "退款申请已存在！请重试！");
			}

		} else {
			throw new LoginException("未登录!");
		}
	}

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "searchCode", value = "代码(支持like)，允许null"),
			@ApiImplicitParam(name = "searchName", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "merchantId", value = "排序规则字符串", dataType = "long"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<? extends Entity>> queryPage(String searchCode, String searchName,
			Long merchantId, String sort, String joinTime, String endTime, String status,
			@RequestParam("pageNum") long pageNum, @RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();
		if (StringUtils.isNotEmpty(searchCode)) {
			where.like("code", searchCode);
		}
		if (StringUtils.isNotEmpty(searchName)) {
			where.like("name", searchName);
		}
		if (merchantId != null)
			where.equal(DataType.LONG, "merchantId", merchantId.toString());

		if (StringUtils.isNotEmpty(status))
			where.like("refundStatus", status);
		if (StringUtils.isNotEmpty(joinTime) && StringUtils.isNotEmpty(endTime)) {
			where.between("requestTime", joinTime, endTime); // created_time为时间字段名
		}

		PageSet<? extends Entity> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}

	@ApiOperation(value = "查询所有实体", notes = "")
	@ApiImplicitParam(name = "sort", value = "排序规则字符串")
	@RequestMapping(value = "/queryAll", method = { RequestMethod.GET })
	public @ResponseBody Result<List<? extends Entity>> queryAll(String sort) {
		log.debug("call queryAll");
		Predicate where = DaoUtil.and();
		if (AppUtils.isLogin()) {
			String code = AppUtils.getLoginUser().getUsername();
			where.equal(DataType.STRING, "customerCode", code.toString());
			List<? extends Entity> list = this.service().findAll(where, Sort.parse(sort));
			return Result.toResult(list);

		} else {
			throw new LoginException("未登录!");
		}
	}

}