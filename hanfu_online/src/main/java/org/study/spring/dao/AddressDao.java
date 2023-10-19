package org.study.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.Address;
@Mapper
public interface AddressDao extends Dao<Address> {

}
