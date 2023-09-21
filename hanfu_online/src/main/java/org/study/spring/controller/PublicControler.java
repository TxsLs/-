package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@Api(tags = "公用模块")
@Controller
@RequestMapping("/")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PublicControler {

}
