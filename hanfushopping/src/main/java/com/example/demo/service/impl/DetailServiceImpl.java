package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.BaseService;
import com.example.demo.dao.DetailDao;
import com.example.demo.entity.Order_detail;
import com.example.demo.service.DetailService;

@Service
public class DetailServiceImpl extends BaseService<Order_detail, DetailDao> implements DetailService{

	@Override
	public Order_detail findByCode(String code) {
		return this.dao().findByName("code", code);
	}
}
