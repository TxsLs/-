package org.boot.hf.admin.controller;

import javax.validation.constraints.Min;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;
import org.boot.hf.admin.BaseController;
import org.boot.hf.admin.Entity;
import org.boot.hf.admin.entity.Ban;
import org.boot.hf.admin.service.BanService;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

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

@Slf4j
@Api(tags = "封禁管理模块")
@Controller
@RequestMapping("/ban")
public class BanController extends BaseController<Ban, BanService> {

	@ApiOperation(value = "添加封禁信息", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "封禁者编号", required = true),
			@ApiImplicitParam(name = "reason", value = "封禁理由", required = true),
			@ApiImplicitParam(name = "endTime", value = "封禁结束时间", required = true, dataType = "datetime") })
	@RequestMapping(value = "/addEmployee", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> addBan(@Validated({ Default.class }) @ApiIgnore Ban vo) {

		log.debug("call addEmployee");
		boolean result = this.service().insert(vo, true);

		return Result.of(result);
	}
	
	

	@ApiOperation(value = "条件分页查询", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "searchCode", value = "代码(支持like)，允许null"),
			@ApiImplicitParam(name = "searchName", value = "名称(支持like)，允许null"),
			@ApiImplicitParam(name = "sort", value = "排序规则字符串"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "long"),
			@ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "int") })
	@RequestMapping(value = "/queryPage", method = { RequestMethod.GET })
	public @ResponseBody Result<PageSet<? extends Entity>> queryPage(String searchCode, String searchName, String sort,
			@Min(1) @RequestParam("pageNum") long pageNum, @Min(1) @RequestParam int pageSize) {
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

}
