package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.CustomerDao;
import org.study.spring.entity.Customer;
import org.study.spring.service.CustomerService;

@Service
public class CustomerServiceImpl extends BaseService<Customer, CustomerDao> implements CustomerService {

	
}
