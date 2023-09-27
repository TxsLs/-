package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.ShopCart;
import org.study.spring.service.ShopCartService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "购物车管理模块")
@Controller
@RequestMapping("/ShopCart")
public class ShopCartController extends BaseController<ShopCart, ShopCartService> {

	

}
