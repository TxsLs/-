package com.example.demo.service;

import com.example.demo.Service;
import com.example.demo.entity.Photo;
import com.example.demo.entity.Product;

public interface ProductService extends Service<Product>{
	
	public Photo getPhoto(long id);
	
	/** 
		* @see UserDao#updatePhoto(Photo)
		*/
	public boolean updatePhoto(Photo photo);

	public Product findByCode(String code);
}
