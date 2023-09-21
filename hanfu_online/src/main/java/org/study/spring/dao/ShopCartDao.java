package org.study.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.ShopCart;



@Mapper
public interface ShopCartDao extends Dao<ShopCart> {
}
