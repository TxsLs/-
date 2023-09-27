package com.example.demo.service;

import com.example.demo.Service;
import com.example.demo.entity.Order_detail;

public interface DetailService extends Service<Order_detail>{
	public Order_detail findByCode(String code);
}
