package org.study.spring.service.impl;

import java.util.Map;

import org.quincy.rock.core.dao.SQLProvider;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.spring.AppUtils;
import org.study.spring.BaseService;
import org.study.spring.dao.OrderDao;
import org.study.spring.entity.Order;
import org.study.spring.entity.Photo;
import org.study.spring.service.OrderService;
import org.teach.study.boot.entity.User;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class OrderServiceImpl extends BaseService<Order, OrderDao> implements OrderService {


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
	@Override
	public PageSet<Order> queryPageByCondition(Map<String, Object> condition, Sort sort, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy(sort.toString(SQLProvider.getFieldNameConverter()));
		Page<Order> p = this.operate().findPageByCondition(condition);
		return AppUtils.toPageSet(p);
	}


	

	


	
	@Override
	public boolean insert(Order entity, boolean ignoreNullValue, String... excluded) {
		
		return super.insert(entity, ignoreNullValue, excluded);
	}






	@Override
	public Order findByCode(String code) {
		return operate().findByCode(code);
	}
}
