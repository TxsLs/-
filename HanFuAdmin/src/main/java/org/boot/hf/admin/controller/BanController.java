package org.boot.hf.admin.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.boot.hf.admin.BaseController;
import org.boot.hf.admin.entity.Ban;
import org.boot.hf.admin.service.BanService;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>BanController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */

@Api(tags = "封禁管理模块")
@Controller
@Slf4j
@RequestMapping("/ban")
public class BanController extends BaseController<Ban, BanService> {

	@ApiOperation(value = "根据指定的id数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "封禁id", required = true,dataType = "long"),
		@ApiImplicitParam(name = "type", value = "类型",required = true, dataType = "int") })
	@RequestMapping(value = "/queryByBanId", method = { RequestMethod.GET })
	public @ResponseBody Result<List<Ban>> queryByBanId(@NotNull @RequestParam Long userId,@NotNull @RequestParam Integer type) {
		log.debug("call queryByBanId");
		List<Ban> vo = this.service().getBanMes(userId,type);
		return Result.toResult(vo);
	}

}
