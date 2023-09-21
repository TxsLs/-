package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.Order;
import org.study.spring.service.OrderService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "订单管理模块")
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController<Order, OrderService> {


}
