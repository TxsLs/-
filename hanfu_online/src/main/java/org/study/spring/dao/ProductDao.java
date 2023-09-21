package org.study.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.Product;

@Mapper
public interface ProductDao extends Dao<Product> {

}
