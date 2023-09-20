package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.ChatRecordDao;
import org.study.spring.entity.ChatRecord;
import org.study.spring.service.ChatRecordService;

@Service
public class ChatRecordServiceImpl extends BaseService<ChatRecord, ChatRecordDao> implements ChatRecordService {

}
