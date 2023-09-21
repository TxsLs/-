package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.ShopCartDao;
import org.study.spring.entity.ShopCart;
import org.study.spring.service.ShopCartService;

@Service
public class ShopCartServiceImpl extends BaseService< ShopCart,  ShopCartDao> implements  ShopCartService {

}
