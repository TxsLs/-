package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.BaseController;
import com.example.demo.entity.Order_table;
import com.example.demo.service.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Api(tags = "订单管理模块")
@Controller
@RequestMapping("/Order_table")
public class OrderController  extends BaseController<Order_table, OrderService>{

}
