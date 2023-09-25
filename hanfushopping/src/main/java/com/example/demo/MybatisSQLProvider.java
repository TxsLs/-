package com.example.demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.quincy.rock.core.dao.SQLProvider;
import org.quincy.rock.core.util.StringUtil;

/**
 * <b>MybatisSQLProvider。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class MybatisSQLProvider {
	private final SQLProvider<ProviderContext> sqlProvider;

	/**
	 * <b>构造方法。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 */
	public MybatisSQLProvider() {
		sqlProvider = new SQLProvider<ProviderContext>(ctx -> {
			Class<?> clazz = ctx.getMapperType();
			ParameterizedType type = (ParameterizedType) clazz.getGenericInterfaces()[0];
			Type[] types = type.getActualTypeArguments();
			return (Class<?>) types[0];
		}, ctx -> {
			String name = ctx.getDatabaseId(), quote;
			if ("mysql".equalsIgnoreCase(name))
				quote = "`";
			else
				quote = null;
			return quote;
		});
		//
		sqlProvider.setNameConverter((alias, name, isObj, isCol) -> {
			return StringUtil.objName2DbName(name, isCol ? "f_" : "t_", StringUtil.CHAR_UNDERLINE);
		});
	}

	public String delete(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.delete(ctx, paramMap);
	}

	public String deleteMore(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.deleteMore(ctx, paramMap);
	}

	public String deleteAll(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.deleteAll(ctx, paramMap);
	}

	public String count(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.count(ctx, paramMap);
	}

	public String findOne(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.findOne(ctx, paramMap);
	}

	public String findMore(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.findMore(ctx, paramMap);
	}

	public String findAll(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.findAll(ctx, paramMap);
	}

	public String findByName(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.findByName(ctx, paramMap);
	}

	public String findAllByName(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.findAllByName(ctx, paramMap);
	}

	public String exists(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.exists(ctx, paramMap);
	}

	public String existsByName(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.existsByName(ctx, paramMap);
	}

	public String existsByWhere(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.existsByWhere(ctx, paramMap);
	}

	public String insert(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.insert(ctx, paramMap);
	}

	public String update(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.update(ctx, paramMap);
	}

	public String updateSpecific(ProviderContext ctx, Map<String, Object> paramMap) {
		return sqlProvider.updateSpecific(ctx, paramMap);
	}

}
