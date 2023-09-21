package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.OrderDao;
import org.study.spring.entity.Order;
import org.study.spring.service.OrderService;

@Service
public class OrderServiceImpl extends BaseService<Order, OrderDao> implements OrderService {


	
}
