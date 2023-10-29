package org.boot.hf.admin.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.boot.hf.admin.Dao;
import org.boot.hf.admin.entity.PhotoShops;
import org.boot.hf.admin.entity.Product;

import com.github.pagehelper.Page;
@Mapper
public interface ProductDao extends Dao<Product>{

	public Page<Product> findPageByCondition(Map<String, Object> condition);



	public PhotoShops getPhoto(long id);

	public int updatePhoto(PhotoShops photo);


	default Product findByCode(String code) {
		return findByName("code", code);
	}
}
