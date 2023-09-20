package org.boot.hf.admin;

import java.util.List;
import java.util.Map;

import org.quincy.rock.core.dao.IDao;
import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * <b>Dao。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
public interface Dao<T extends Entity> extends IDao<T>, MetaDao<T> {

	@Override
	default int insert(T entity, boolean ignoreNullValue, String... excluded) {
		return _insert(entity, ignoreNullValue, excluded);
	}

	@Override
	default int update(T entity, boolean ignoreNullValue, Predicate where, String... excluded) {
		return _update(entity, ignoreNullValue, where, excluded);
	}

	@Override
	default int updateMap(Map<String, Object> voMap, Predicate where) {
		return _updateSpecific(null, voMap, where);
	}

	@Override
	default int delete(long id) {
		return _delete(null, id);
	}

	@Override
	default int deleteMore(List<Long> ids) {
		return _deleteMore(null, ids);
	}

	@Override
	default long deleteAll(Predicate where) {
		return _deleteAll(null, where);
	}

	@Override
	default long count(Predicate where) {
		return _count(null, where);
	}

	@Override
	default boolean exist(long id) {
		return _exist(null, id);
	}

	@Override
	default boolean existByName(String name, Object value, Long ignoreId) {
		return _existByName(null, name, value, ignoreId);
	}

	@Override
	default boolean existByWhere(Predicate where) {
		return _existByWhere(null, where);
	}

	@Override
	default T findOne(long id, String... excluded) {
		return _findOne(null, id, excluded);
	}

	@Override
	default List<T> findMore(List<Long> ids, String... excluded) {
		return _findMore(null, ids, excluded);
	}

	@Override
	default List<T> findAll(Predicate where, Sort sort, String... excluded) {
		return _findAll(null, where, sort, excluded);
	}

	@Override
	default T findByName(String name, Object value, String... excluded) {
		return _findByName(null, name, value, excluded);
	}

	@Override
	default List<T> findAllByName(String name, Object value, Sort sort, String... excluded) {
		return _findAllByName(null, name, value, sort, excluded);
	}

	@Override
	default PageSet<T> findPage(Predicate where, Sort sort, long pageNum, int pageSize, String... excluded) {
		if (Sort.isSorted(sort))
			PageHelper.startPage((int) pageNum, pageSize, sort.getSqlFragment());
		else
			PageHelper.startPage((int) pageNum, pageSize);
		Page<T> page = _findPage(null, where, Sort.unsorted(), excluded);
		return AppUtils.toPageSet(page);
	}

}