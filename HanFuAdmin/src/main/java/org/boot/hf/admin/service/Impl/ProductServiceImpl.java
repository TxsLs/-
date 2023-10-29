package org.boot.hf.admin.service.Impl;

import org.boot.hf.admin.BaseService;
import org.boot.hf.admin.dao.ProductDao;
import org.boot.hf.admin.entity.PhotoShops;
import org.boot.hf.admin.entity.Product;
import org.boot.hf.admin.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProductServiceImpl extends BaseService<Product, ProductDao> implements ProductService {

	@Override
	public Product findByCode(String id) {
		return this.dao().findByName("code", id);
	}

	@Override
	public PhotoShops getPhoto(long id) {
		return this.dao().getPhoto(id);
	}

	@Override
	@Transactional
	public boolean updatePhoto(PhotoShops photo) {
		return dao().updatePhoto(photo) > 0;
	}

//	@Override
//	public boolean insert(Product entity, boolean ignoreNullValue, String... excluded) {
//		Date date=DateUtil.getDateByWord("now");
//		entity.setCreatedTime(date);;
//		return super.insert(entity, ignoreNullValue, excluded);
//	}
}
