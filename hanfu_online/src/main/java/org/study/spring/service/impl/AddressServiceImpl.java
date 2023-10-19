package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.AddressDao;
import org.study.spring.entity.Address;
import org.study.spring.service.AddressService;


@Service
public class AddressServiceImpl extends BaseService<Address, AddressDao> implements AddressService {

}
