package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.util.MapUtil;

import com.example.demo.Dao;
import com.example.demo.entity.Order_detail;
import com.github.pagehelper.Page;


@Mapper
public interface DetailDao extends Dao<Order_detail>{

	public Page<Order_detail> findPageByCondition(Map<String, Object> condition);

	public int updateSelfInfo(Order_detail vo);

	public com.example.demo.entity.Photo getPhoto(long id);

	public int updatePhoto(com.example.demo.entity.Photo photo);


	default int changePassword(String code, String newPassword) {
		return updateMap(MapUtil.asMap("password", newPassword), DaoUtil.and().equal("code", code));
	}

	default Order_detail findByCode(String code) {
		return findByName("code", code);
	}
}
