package com.example.demo.service;

import com.example.demo.Service;
import com.example.demo.entity.Order_table;

public interface OrderService extends Service<Order_table>{
	public Order_table findByCode(String code);
}
