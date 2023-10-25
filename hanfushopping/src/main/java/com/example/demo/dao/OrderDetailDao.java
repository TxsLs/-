package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.util.MapUtil;

import com.example.demo.Dao;
import com.example.demo.entity.OrderDetail;
import com.github.pagehelper.Page;


@Mapper
public interface OrderDetailDao extends Dao<OrderDetail>{

}
