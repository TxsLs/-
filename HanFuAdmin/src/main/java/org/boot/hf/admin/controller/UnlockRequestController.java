package org.boot.hf.admin.controller;

import javax.validation.groups.Default;

import org.boot.hf.admin.BaseController;
import org.boot.hf.admin.entity.Employee;
import org.boot.hf.admin.entity.UnlockRequest;
import org.boot.hf.admin.service.EmployeeService;
import org.boot.hf.admin.service.UnlockRequestService;
import org.quincy.rock.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
	EmployeeService employeeService;

	@ApiOperation(value = "提交申请信息", notes = "")
	@ApiImplicitParam(name = "vo", value = "理由", required = true)

	@RequestMapping(value = "/addRequest", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addRequest(@Validated({ Default.class }) @RequestBody UnlockRequest vo) {
		log.debug("call addRequest");
		boolean ok = false;
		//if (AppUtils.isLogin()) {
		//String code = AppUtils.getLoginUser().getUsername();
		Employee employee = employeeService.findByCode(vo.getCode());
		boolean exist = this.service().existByName("code", vo.getCode(), null);
		if (!exist) {
			if (employee != null && employee.getCode().equals(vo.getCode()) && employee.getName().equals(vo.getName())
					&& employee.getPhone().equals(vo.getPhone())) {
				ok = this.service().insert(vo, true);
			}

			else {
				return Result.toResult("1069", "用户信息不匹配，请重试！");
			}
		} else {
			return Result.toResult("1070", "你已提交过申请！请勿重复提交！");
		}
		//}
		return Result.of(ok);
	}

}
