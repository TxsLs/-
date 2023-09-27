package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.Refund;
import org.study.spring.service.RefundService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "退货申请模块")
@Controller
@RequestMapping("/refund")
public class RefundController extends BaseController<Refund, RefundService> {

	

}
