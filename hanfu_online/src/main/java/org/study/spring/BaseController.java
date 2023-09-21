package org.study.spring;

import java.util.Arrays;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.quincy.rock.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>基类Controller。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
@Slf4j
@CrossOrigin
public abstract class BaseController<T extends Entity, S extends Service<T>> {
	@Autowired
	private S service;

	/**
	 * <b>获得Service接口。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return Service接口
	 */
	protected S service() {
		return this.service;
	}

	@ModelAttribute
	public void checkLogin() throws LoginException {
		if (AppUtils.useCaptcha && !AppUtils.isLogin())
			throw new LoginException("未登录!");
	}

	@ApiOperation(value = "添加一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "vo", value = "实体值对象", required = true),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> add(@Valid @RequestBody T vo,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) {
		log.debug("call add");
		boolean result = this.service().insert(vo, ignoreNullValue);
		return Result.of(result);
	}

	@ApiOperation(value = "更新一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "vo", value = "实体值对象", required = true),
			@ApiImplicitParam(name = "ignoreNullValue", value = "是否忽略空值(default=false)", dataType = "boolean") })
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> update(@RequestBody T vo,
			@RequestParam(defaultValue = "false") boolean ignoreNullValue) {
		log.debug("call update");
		boolean result = this.service().update(vo, ignoreNullValue, null);
		return Result.of(result);
	}

	@ApiOperation(value = "使用Map更新一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParam(name = "voMap", value = "实体值对象Map", required = true)
	@RequestMapping(value = "/updateMap", method = { RequestMethod.POST })
	public @ResponseBody Result<Boolean> updateMap(@RequestBody Map<String, Object> voMap) {
		log.debug("call updateMap");
		boolean result = this.service().updateMap(voMap, null);
		return Result.of(result);
	}

	@ApiOperation(value = "删除一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/remove", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> remove(@RequestParam long id) {
		log.debug("call remove");
		boolean result = this.service().delete(id);
		return Result.of(result);
	}

	@ApiOperation(value = "删除多个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParam(name = "id", value = "多个主键id", required = true, dataType = "long", allowMultiple = true)
	@RequestMapping(value = "/removeMore", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> removeMore(@RequestParam("id") Long[] ids) {
		log.debug("call removeMore");
		boolean result = this.service().deleteMore(Arrays.asList(ids));
		return Result.of(result);
	}

	@ApiOperation(value = "查询一个实体", notes = "该接口继承自BaseController")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/queryOne", method = { RequestMethod.GET })
	public @ResponseBody Result<T> queryOne(@RequestParam long id) {
		log.debug("call queryOne");
		T vo = this.service().findOne(id, "password");
		return Result.toResult(vo);
	}

	@ApiOperation(value = "根据指定的属性名和值返回一条数据", notes = "该接口继承自SimpleController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "propName", value = "属性名", required = true),
			@ApiImplicitParam(name = "propValue", value = "属性值", required = true) })
	@RequestMapping(value = "/queryByName", method = { RequestMethod.GET })
	public @ResponseBody Result<T> queryByName(@RequestParam String propName, @RequestParam Object propValue) {
		log.debug("call queryByName");
		T vo = this.service().findByName(propName, propValue, "password");
		return Result.toResult(vo);
	}

	@ApiOperation(value = "根据主键id查询数据是否存在", notes = "该接口继承自BaseController")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "long")
	@RequestMapping(value = "/exists", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> exists(@RequestParam long id) {
		log.debug("call exists");
		boolean result = this.service().exist(id);
		return Result.of(result);
	}

	@ApiOperation(value = "根据指定的属性名和属性值查询数据是否存在", notes = "该接口继承自BaseController")
	@ApiImplicitParams({ @ApiImplicitParam(name = "propName", value = "属性名", required = true),
			@ApiImplicitParam(name = "propValue", value = "属性值", required = true),
			@ApiImplicitParam(name = "ignoreId", value = "忽略的主键id", dataType = "long") })
	@RequestMapping(value = "/existsByName", method = { RequestMethod.GET })
	public @ResponseBody Result<Boolean> existsByName(@RequestParam String propName, @RequestParam Object propValue,
			Long ignoreId) {
		log.debug("call existsByName");
		boolean result = this.service().existByName(propName, propValue, ignoreId);
		return Result.of(result);
	}
}
