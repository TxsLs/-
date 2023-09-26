package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.BaseService;
import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Photo;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
@Service
public class ProductServiceImpl extends BaseService<Product, ProductDao> implements ProductService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Product findByCode(String code) {
		return this.dao().findByName("code", code);
	}
	
	@Override
	public Photo getPhoto(long id) {
		return this.dao().getPhoto(id);
	}

	@Override
	@Transactional
	public boolean updatePhoto(Photo photo) {
		return dao().updatePhoto(photo) > 0;
	}
	/*
		@Override
		@Transactional
		public boolean changeSelfPassword(String code, String oldPassword, String newPassword) {
			User vo = this.findByCode(code);
			if (vo == null || !passwordEncoder.matches(oldPassword, vo.getPassword()))
				return false;
			else {
				String encodedPassword = passwordEncoder.encode(newPassword);
				return dao().changePassword(code, encodedPassword) > 0;
			}
		}
	
		@Override
		@Transactional
		public boolean changePassword(String code, String newPassword) {
			String encodedPassword = passwordEncoder.encode(newPassword);
			return dao().changePassword(code, encodedPassword) > 0;
		}
	
		@Override
		public User checkPassword(String code, String password) {
			User vo = this.findByCode(code);
			if (vo == null || !passwordEncoder.matches(password, vo.getPassword()))
				return null;
			else {
				return vo;
			}
		}*/


	/*	@Override
		@Transactional
		public boolean updateSelfInfo(User vo) {
			return dao().updateSelfInfo(vo) > 0;
		}
	
		@Override
		public boolean insert(Product entity, boolean ignoreNullValue, String... excluded) {
			if (StringUtil.isEmpty(entity.getPassword())) {
				entity.setPassword("111111");
			}
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			return super.insert(entity, ignoreNullValue, excluded);
		}*/
}
