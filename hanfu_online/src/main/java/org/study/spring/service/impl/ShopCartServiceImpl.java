package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.ShopCartDao;
import org.study.spring.entity.ShopCart;
import org.study.spring.service.ShopCartService;

@Service
public class ShopCartServiceImpl extends BaseService<ShopCart, ShopCartDao> implements ShopCartService {

	@Override
	public boolean addProduct(String code, long productID, int number) {
				//先判断是否存在
				//如果存在调用update方法。
				//如果不存在调用插入方法。
		if (this.dao().findProduct(code, productID) != null) {
			return this.dao().updateProduct(code, productID, number);
		} else {
			return this.dao().addProduct(code, productID, number);
		}
	}

	@Override
	public boolean updateProduct(String code, long productId, int quantity) {

		return this.dao().updateSCProduct(code, productId, quantity);
	}

}
