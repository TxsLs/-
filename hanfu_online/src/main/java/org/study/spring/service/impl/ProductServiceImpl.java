package org.study.spring.service.impl;

import java.util.Map;

import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;
import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.ProductDao;
import org.study.spring.entity.Product;
import org.study.spring.entity.Customer;
import org.study.spring.service.ProductService;

@Service
public class ProductServiceImpl extends BaseService<Product, ProductDao> implements ProductService {

	public PageSet<Product> queryPageByCondition(Map<String, Object> condition, Sort parse, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
