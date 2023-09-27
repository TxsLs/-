package org.study.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.spring.BaseController;
import org.study.spring.entity.ChatRecord;
import org.study.spring.service.ChatRecordService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "聊天记录模块")
@Controller
@RequestMapping("/chatRecord")
public class ChatRecordController extends BaseController<ChatRecord, ChatRecordService> {

	

}
