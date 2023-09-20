package org.study.spring.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.Order;
import org.study.spring.entity.Photo;

import com.github.pagehelper.Page;

@Mapper
public interface OrderDao extends Dao<Order> {

	/**
	 * <b>组合条件查询。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param condition 组合条件Map
	 * @return 一页数据列表
	 */
	public Page<Order> findPageByCondition(Map<String, Object> condition);

	/**
	 * <b>更新订单信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据用户id更新用户信息，值对象的id必须有效。
	 * 可以修改自己少部分个人信息。
	 * @param vo　订单信息
	 * @return　更新数据条数
	 */
	public int updateSelfInfo(Order vo);

	/**
	 * <b>获得照片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回的照片数据存放在二进制数组里。
	 * @param id 主键id
	 * @return Photo
	 */
	public Photo getPhoto(long id);

	/**
	 * <b>更新照片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param photo 照片实体对象
	 * @return 更新数据条数
	 */
	public int updatePhoto(Photo photo);
	/**
	 * <b>查询用户信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回信息包含加密的密码。
	 * @param code 工号
	 * @return User
	 */
	default Order findByCode(String code) {
		return findByName("code", code);
	}
	
}
