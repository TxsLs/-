package org.boot.hf.admin.config;

import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.quincy.rock.core.dao.annotation.Table;
import org.quincy.rock.core.util.ReflectUtil;
import org.springframework.stereotype.Service;

/**
 * <b>ResultMap拦截器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 拦截ResultSetHandler。
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Service
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }))
public class ResultMapInterceptor implements Interceptor {

	/** 
	 * intercept。
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		DefaultResultSetHandler rsh = (DefaultResultSetHandler) invocation.getTarget();
		MappedStatement ms = ReflectUtil.getFieldValue(rsh, "mappedStatement");
		if (ms.getSqlCommandType() == SqlCommandType.SELECT && ms.getSqlSource() instanceof ProviderSqlSource) {
			List<ResultMap> rms = ms.getResultMaps();
			rms = ReflectUtil.getFieldValue(rms, "list");
			for (int i = 0, len = rms.size(); i < len; i++) {
				ResultMap rm = rms.get(i);
				if (rm.getId().contains("._find")) {
					Table ann = ReflectUtil.findAnnotation(Table.class, rm.getType());
					if (ann != null && StringUtils.isNotBlank(ann.resultMap())) {
						ResultMap resultMap = ms.getConfiguration().getResultMap(ann.resultMap());
						if (resultMap != null)
							rms.set(i, resultMap);
					}
				}
			}
		}
		return invocation.proceed();
	}

}
