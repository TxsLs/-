package org.study.spring.controller;

import javax.validation.constraints.Min;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.spring.BaseController;
import org.study.spring.entity.Customer;
import org.study.spring.entity.UnlockRequest;
import org.study.spring.service.CustomerService;
import org.study.spring.service.UnlockRequestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>UnlockRequestController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
@Api(tags = "解封模块")
@Controller
@Slf4j
@Validated
@RequestMapping("/unlock")
public class UnlockRequestController extends BaseController<UnlockRequest, UnlockRequestService> {

	@Autowired
	CustomerService employeeService;

	@ApiOperation(value = "提交申请信息", notes = "")
	@ApiImplicitParam(name = "vo", value = "理由", required = true)
	@RequestMapping(value = "/addRequest", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addRequest(@Validated({ Default.class }) @RequestBody UnlockRequest vo) {
		log.debug("call addRequest");
		boolean ok = false;
		//if (AppUtils.isLogin()) {
		//String code = AppUtils.getLoginUser().getUsername();
		Customer employee = employeeService.findByCode(vo.getCode());
		boolean exist = this.service().existByName("code", vo.getCode(), null);
		if (employee == null) {
			return Result.toResult("1072", "此账号不存在！");
		} else {

			if (employee.getIsviolate() == 1) {
				return Result.toResult("1071", "你的账户未被封禁！");
			} else {
				if (exist) {
					return Result.toResult("1070", "你已提交过申请！请勿重复提交！");
				} else {
					if (employee != null && employee.getCode().equals(vo.getCode())
							&& employee.getName().equals(vo.getName()) && employee.getPhone().equals(vo.getPhone())) {
						ok = this.service().insert(vo, true);
					} else {
						return Result.toResult("1069", "用户信息不匹配，请重试！");
					}
				}
			}

		}

		//}
		return Result.of(ok);
	}

	@ApiOperation(value = "提交申请信息", notes = "")
	@ApiImplicitParam(name = "vo", value = "理由", required = true)
	@RequestMapping(value = "/addRequestProduct", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addRequestProduct(
			@Validated({ Default.class }) @RequestBody UnlockRequest vo) {
		log.debug("call addRequestProduct");
		boolean ok = false;
		//if (AppUtils.isLogin()) {
		//String code = AppUtils.getLoginUser().getUsername();
		//Merchant employee = employeeService.findByCode(vo.getCode());
		boolean exist = this.service().existByName("name", vo.getName(), null);
		if (!exist) {
			ok = this.service().insert(vo, true);
		} else {
			return Result.toResult("1070", "你已提交过申请！请勿重复提交！");
		}
		//}
		return Result.of(ok);
	}

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "工号(支持like)，允许null", dataType = "long"),
			@ApiImplicitParam(name = "name", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "joinTime", value = "开始时间，允许null"),
			@ApiImplicitParam(name = "endTime", value = "结束时间，允许null"),
			@ApiImplicitParam(name = "type", value = "角色，允许null"),
			@ApiImplicitParam(name = "status", value = "状态，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<UnlockRequest>> queryPage(String phone, String name, String sort,
			String joinTime, String endTime, String type, String status, @Min(1) @RequestParam long pageNum,
			@Min(1) @RequestParam int pageSize) {
		log.debug("call queryPage");
		Predicate where = DaoUtil.and();

		if (StringUtils.isNotEmpty(phone))
			where.like("phone", phone);
		if (StringUtils.isNotEmpty(name))
			where.like("name", name);
		if (StringUtils.isNotEmpty(type))
			where.like("type", type);
		if (StringUtils.isNotEmpty(status))
			where.like("status", status);
		if (StringUtils.isNotEmpty(joinTime) && StringUtils.isNotEmpty(endTime)) {
			where.between("requestTime", joinTime, endTime); // created_time为时间字段名
		}

		PageSet<UnlockRequest> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}

}
