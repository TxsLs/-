package org.study.spring;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.Vo;

import com.github.pagehelper.Page;

/**
 * <b>MetaDao。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
public interface MetaDao<T extends Vo<Long>> {

	@DeleteProvider(type = MybatisSQLProvider.class, method = "delete")
	public int _delete(@Param("entityType") Class<T> entityType, @Param("id") long id);

	@DeleteProvider(type = MybatisSQLProvider.class, method = "deleteMore")
	public int _deleteMore(@Param("entityType") Class<T> entityType, @Param("ids") Iterable<Long> ids);

	@DeleteProvider(type = MybatisSQLProvider.class, method = "deleteAll")
	public long _deleteAll(@Param("entityType") Class<T> entityType, @Param("where") Predicate where);

	@SelectProvider(type = MybatisSQLProvider.class, method = "count")
	public long _count(@Param("entityType") Class<T> entityType, @Param("where") Predicate where);

	@SelectProvider(type = MybatisSQLProvider.class, method = "findOne")
	public T _findOne(@Param("entityType") Class<T> entityType, @Param("id") long id,
			@Param("excluded") String... excluded);

	@SelectProvider(type = MybatisSQLProvider.class, method = "findMore")
	public List<T> _findMore(@Param("entityType") Class<T> entityType, @Param("ids") Iterable<Long> ids,
			@Param("excluded") String... excluded);

	@SelectProvider(type = MybatisSQLProvider.class, method = "findAll")
	public List<T> _findAll(@Param("entityType") Class<T> entityType, @Param("where") Predicate where,
			@Param("sort") Sort sort, @Param("excluded") String... excluded);

	@SelectProvider(type = MybatisSQLProvider.class, method = "findAll")
	public Page<T> _findPage(@Param("entityType") Class<T> entityType, @Param("where") Predicate where,
			@Param("sort") Sort sort, @Param("excluded") String... excluded);

	@SelectProvider(type = MybatisSQLProvider.class, method = "findByName")
	public T _findByName(@Param("entityType") Class<T> entityType, @Param("name") String name,
			@Param("value") Object value, @Param("excluded") String... excluded);

	@SelectProvider(type = MybatisSQLProvider.class, method = "findAllByName")
	public List<T> _findAllByName(@Param("entityType") Class<T> entityType, @Param("name") String name,
			@Param("value") Object value, @Param("sort") Sort sort, @Param("excluded") String... excluded);

	@SelectProvider(type = MybatisSQLProvider.class, method = "exists")
	public boolean _exist(@Param("entityType") Class<T> entityType, @Param("id") long id);

	@SelectProvider(type = MybatisSQLProvider.class, method = "existsByName")
	public boolean _existByName(@Param("entityType") Class<T> entityType, @Param("name") String name,
			@Param("value") Object value, @Param("ignoreId") Long ignoreId);

	@SelectProvider(type = MybatisSQLProvider.class, method = "existsByWhere")
	public boolean _existByWhere(@Param("entityType") Class<T> entityType, @Param("where") Predicate where);

	@InsertProvider(type = MybatisSQLProvider.class, method = "insert")
	public int _insert(@Param("entity") T entity, @Param("ignoreNullValue") boolean ignoreNullValue,
			@Param("excluded") String... excluded);

	@UpdateProvider(type = MybatisSQLProvider.class, method = "update")
	public int _update(@Param("entity") T entity, @Param("ignoreNullValue") boolean ignoreNullValue,
			@Param("where") Predicate where, @Param("excluded") String... excluded);

	@InsertProvider(type = MybatisSQLProvider.class, method = "updateSpecific")
	public int _updateSpecific(@Param("entityType") Class<T> entityType, @Param("voMap") Map<String, Object> voMap,
			@Param("where") Predicate where);

}
