package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.ShopCartDao;
import org.study.spring.entity.ShopCart;
import org.study.spring.service.ShopCartService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
			log.debug(this.dao().findProduct(code, productID)+"-----------------------");
			return this.dao().addProduct(code, productID, number);
		}
	}

}
