package org.study.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.study.spring.Dao;
import org.study.spring.entity.Product;
import org.study.spring.entity.ShopCart;



@Mapper
public interface ShopCartDao extends Dao<ShopCart> {
	/**
	 * <b>getProduct。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 查到商品 -->
	 * 无。
	 * @param code
	 * @param productID
	 * @return Product
	 */
	public ShopCart findProduct(String code,long productID);
	
	
	/**
	 * <b>updateProduct。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 更新已有的商品 -->
	 * 无。
	 * @param code
	 * @param productID
	 * @param number
	 * @return 是否成功
	 */
	public boolean updateProduct(String code,long productID,int number);
	/**
	 * <b>加入购物车。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 顾客表code
	 * @param productID 商品id
	 * @param number 商品数量
	 * @return 更新数据条数
	 */
	public boolean addProduct(String code,long productID,int number);
	/**
	 * <b>updateSCProduct。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 更新购物车中已有的商品 -->
	 * 无。
	 * @param code
	 * @param productID
	 * @param number
	 * @return 是否成功
	 */
	public boolean updateSCProduct(String code,long productID,int number);
}
