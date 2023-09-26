package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.util.MapUtil;

import com.example.demo.Dao;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.Order_table;
import com.github.pagehelper.Page;

@Mapper
public interface OrderDao extends Dao<Order_table>{

		public Page<Order_table> findPageByCondition(Map<String, Object> condition);

		public int updateSelfInfo(Merchant vo);

		public com.example.demo.entity.Photo getPhoto(long id);

		public int updatePhoto(com.example.demo.entity.Photo photo);

		default int changePassword(String code, String newPassword) {
			return updateMap(MapUtil.asMap("password", newPassword), DaoUtil.and().equal("code", code));
		}

		default Order_table findByCode(String code) {
			return findByName("code", code);
		}
	}

