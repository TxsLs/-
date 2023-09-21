package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.Product;
import org.study.spring.service.ProductService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "商品管理模块")
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController<Product, ProductService> {


}
