package org.study.spring.service;

import java.util.Map;

import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;
import org.study.spring.Service;
import org.study.spring.entity.Product;
import org.study.spring.entity.Customer;

public interface ProductService extends Service<Product> {

	PageSet<Product> queryPageByCondition(Map<String, Object> condition, Sort parse, int pageNum, int pageSize);

}
