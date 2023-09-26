package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.BaseController;
import com.example.demo.entity.Merchant;
import com.example.demo.service.MerchantService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Api(tags = "商家管理模块")
@Controller
@RequestMapping("/user")
public class MerchantController extends BaseController<Merchant, MerchantService> {

}
