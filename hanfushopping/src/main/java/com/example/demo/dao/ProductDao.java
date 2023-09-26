package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.util.MapUtil;

import com.example.demo.Dao;
import com.example.demo.entity.Photo;
import com.example.demo.entity.Product;
import com.github.pagehelper.Page;
@Mapper
public interface ProductDao extends Dao<Product>{

	public Page<Product> findPageByCondition(Map<String, Object> condition);

	public int updateSelfInfo(Product vo);

	public Photo getPhoto(long id);

	public int updatePhoto(Photo photo);

	default int changePassword(String code, String newPassword) {
		return updateMap(MapUtil.asMap("password", newPassword), DaoUtil.and().equal("code", code));
	}

	default Product findByCode(String code) {
		return findByName("code", code);
	}
}
