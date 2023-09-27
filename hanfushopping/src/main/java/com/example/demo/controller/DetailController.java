package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.BaseController;
import com.example.demo.entity.Order_detail;
import com.example.demo.service.DetailService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "订单详情管理模块")
@Controller
@RequestMapping("/detail")
public class DetailController extends BaseController <Order_detail, DetailService>{

}
