package org.study.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.Order;
import org.study.spring.entity.OrderDetail;

@Mapper
public interface OrderDetailDao extends Dao<OrderDetail> {



}
