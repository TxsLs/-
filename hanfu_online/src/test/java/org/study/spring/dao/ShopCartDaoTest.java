package org.study.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.study.spring.entity.ShopCart;

@SpringBootTest
public class ShopCartDaoTest {
	@Autowired
	private ShopCartDao dao;;

	@Test
	public void test_getProduct() {
		ShopCart cart;
		cart=dao.findProduct("ss", 1);
		System.out.println(cart);
		
	}
}
