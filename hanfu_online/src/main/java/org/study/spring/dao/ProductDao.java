package org.study.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.Product;
import org.study.spring.entity.Photo;

@Mapper
public interface ProductDao extends Dao<Product> {
	/**
	 * <b>获得照片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回的照片数据存放在二进制数组里。
	 * @param id 主键id
	 * @return Photo
	 */
	public Photo getPhoto(long id);
	

}
