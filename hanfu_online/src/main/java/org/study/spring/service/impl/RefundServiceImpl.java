package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.RefundDao;
import org.study.spring.entity.Refund;
import org.study.spring.service.RefundService;


@Service
public class RefundServiceImpl extends BaseService<Refund, RefundDao> implements RefundService {

}
