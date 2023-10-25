package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.BaseService;
import com.example.demo.dao.OrderDetailDao;
import com.example.demo.entity.OrderDetail;
import com.example.demo.service.OrderDetailService;

@Service
public class DetailServiceImpl extends BaseService<OrderDetail, OrderDetailDao> implements OrderDetailService{

}
