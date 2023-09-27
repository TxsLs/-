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
	
}
