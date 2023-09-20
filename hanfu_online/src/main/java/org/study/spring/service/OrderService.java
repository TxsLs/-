package org.study.spring.service;

import java.util.Map;

import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;
import org.study.spring.Service;
import org.study.spring.entity.Photo;
import org.study.spring.entity.Order;

public interface OrderService extends Service<Order> {
	

	/**
	 * <b>组合条件查询。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param condition 组合条件Map
	 * @param sort 排序规则，允许null
	 * @param pageNum 页码(从1开始)
	 * @param pageSize 页大小
	 * @return 一页数据列表
	 */
	public PageSet<Order> queryPageByCondition(Map<String, Object> condition, Sort sort, int pageNum, int pageSize);


	/** 
	 * @see CustomerDao#findByCode(String)
	 */
	public Order findByCode(String code);
}

