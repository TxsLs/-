package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.ProductDao;
import org.study.spring.entity.Photo;
import org.study.spring.entity.Product;
import org.study.spring.service.ProductService;

@Service
public class ProductServiceImpl extends BaseService<Product, ProductDao> implements ProductService {

	
	@Override
	public Photo getPhoto(long id) {
		return this.dao().getPhoto(id);
	}

}
