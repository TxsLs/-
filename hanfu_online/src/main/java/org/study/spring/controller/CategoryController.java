package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.Category;
import org.study.spring.service.CategoryService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "商品分类模块")
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController<Category, CategoryService> {

	

}
