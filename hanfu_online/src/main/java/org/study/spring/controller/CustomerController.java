package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.Customer;
import org.study.spring.service.CustomerService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "用户管理模块")
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer, CustomerService> {


}
