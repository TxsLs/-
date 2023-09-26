package com.example.demo.service.impl;

import java.util.Date;

import org.quincy.rock.core.util.DateUtil;
import org.springframework.stereotype.Service;

import com.example.demo.BaseService;
import com.example.demo.dao.MerchantDao;
import com.example.demo.entity.Merchant;
import com.example.demo.service.MerchantService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class MerchantServiceImpl extends BaseService<Merchant, MerchantDao> implements MerchantService {

	@Override
	public Merchant findByCode(String code) {
		return this.dao().findByName("code", code);
	}

	
	
	
	
	@Override
	public boolean insert(Merchant entity, boolean ignoreNullValue, String... excluded) {

		Date date=DateUtil.getDateByWord("now");
		entity.setCreated_time(date);
		return super.insert(entity, ignoreNullValue, excluded);
	}
				
}
