package org.study.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.Order;

@Mapper
public interface OrderDao extends Dao<Order> {



}
