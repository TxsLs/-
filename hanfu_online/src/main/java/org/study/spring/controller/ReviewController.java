package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.Review;
import org.study.spring.service.ReviewService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "评价管理模块")
@Controller
@RequestMapping("/review")
public class ReviewController extends BaseController<Review, ReviewService> {

	

}
