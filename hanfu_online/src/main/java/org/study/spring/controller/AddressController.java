package org.study.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.exception.LoginException;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.spring.AppUtils;
import org.study.spring.BaseController;
import org.study.spring.Entity;
import org.study.spring.entity.Address;
import org.study.spring.service.AddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(allowCredentials = "true", origins = { "http://127.0.0.1:5501", "http://locolhost:5501" })
@Slf4j
@Api(tags = "地址模块")
@Controller
@RequestMapping("/address")
public class AddressController extends BaseController<Address, AddressService> {

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
		if (StringUtils.isNotEmpty(searchCode)) {
			where.like("code", searchCode);
		}
		if (StringUtils.isNotEmpty(searchName)) {
			where.like("name", searchName);
		}
		PageSet<? extends Entity> ps = this.service().findPage(where, Sort.parse(sort), pageNum, pageSize);
		return Result.toResult(ps);
	}

	@ApiOperation(value = "根据指定的属性名和值返回所有数据", notes = "该接口继承自SimpleController")
	@RequestMapping(value = "/queryAllByName", method = { RequestMethod.GET })
	public @ResponseBody Result<List<Address>> queryAllByName(String sort) {
		log.debug("call queryAllByName");
		//非常好地址列表
		if (AppUtils.isLogin()) {
			String propName="customerCode";
			String propValue = AppUtils.getLoginUser().getUsername();
			List<Address> vo = this.service().findAllByName(propName, propValue, Sort.parse(sort), "password");
			return Result.toResult(vo);
		} else {
			throw new LoginException("未登录!");
		}
	}
	
	

	@ApiOperation(value = "添加一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "vo", value = "实体值对象", required = true),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/addNewAddress", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> add(@Valid @RequestBody Address vo,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) {
		log.debug("call addNewAddress");
		if (AppUtils.isLogin()) {
			String propValue = AppUtils.getLoginUser().getUsername();
			vo.setCustomerCode(propValue);
			boolean result = this.service().insert(vo, ignoreNullValue);
			return Result.of(result);
		}else {
			throw new LoginException("未登录!");
		}

	}
	



	
	
	

}
