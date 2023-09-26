package com.example.demo.service.impl;

import java.util.Date;

import org.quincy.rock.core.util.DateUtil;
import org.springframework.stereotype.Service;

import com.example.demo.BaseService;
import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Order_table;
import com.example.demo.service.OrderService;

@Service
public class OrderServiceImpl extends BaseService<Order_table, OrderDao> implements OrderService{
	@Override
	public Order_table findByCode(String code) {
		return this.dao().findByName("code", code);
	}
	
	@Override
	public boolean insert(Order_table entity, boolean ignoreNullValue, String... excluded) {

		Date date=DateUtil.getDateByWord("now");
		entity.setCreated_time(date);
		return super.insert(entity, ignoreNullValue, excluded);
	}
		
}
