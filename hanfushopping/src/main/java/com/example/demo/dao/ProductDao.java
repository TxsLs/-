package com.example.demo.dao;

import java.util.Map;

import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.util.MapUtil;

import com.example.demo.Dao;
import com.example.demo.entity.Photo;
import com.example.demo.entity.Product;
import com.github.pagehelper.Page;

public interface ProductDao extends Dao<Product>{
	/**
	 * <b>组合条件查询。</b>
	 */
	public Page<Product> findPageByCondition(Map<String, Object> condition);

	/**
	 * <b>更新个人信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据用户id更新用户信息，值对象的id必须有效。
	 * 可以修改自己少部分个人信息。
	 * @param vo　个人用户信息
	 * @return　更新数据条数
	 */
	public int updateSelfInfo(Product vo);

	/**
	 * <b>获得照片。</b>
	 */
	public Photo getPhoto(long id);

	/**
	 * <b>更新照片。</b>
	 */
	public int updatePhoto(Photo photo);

	/**
	 * <b>修改密码。</b>
	 */
	default int changePassword(String code, String newPassword) {
		return updateMap(MapUtil.asMap("password", newPassword), DaoUtil.and().equal("code", code));
	}

	/**updateMap
	 * <b>查询用户信息。</b>
	 */
	default Product findByCode(String code) {
		return findByName("code", code);
	}
}
