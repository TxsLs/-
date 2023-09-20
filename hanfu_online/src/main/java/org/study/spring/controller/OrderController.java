package org.study.spring.controller;

import java.io.IOException;
import java.sql.Date;

import org.apache.commons.io.FilenameUtils;
import org.quincy.rock.core.util.DateUtil;
import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.study.spring.BaseController;
import org.study.spring.entity.Order;
import org.study.spring.entity.Photo;
import org.study.spring.entity.Customer;
import org.study.spring.service.OrderService;
import org.study.spring.service.UserService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "订单管理模块")
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController<Order, OrderService> {

	@ApiOperation(value = "添加一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "vo", value = "实体值对象", required = true),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/addOrder", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addOrder(@RequestBody Order vo,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) {
		log.debug("call addOrder");
		int result = this.service().insert(vo, ignoreNullValue);
		return Result.of(result > 0);
	}
	

}
