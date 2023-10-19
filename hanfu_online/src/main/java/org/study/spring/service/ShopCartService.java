package org.study.spring.service;

import org.study.spring.Service;
import org.study.spring.entity.ShopCart;

public interface ShopCartService  extends Service<ShopCart> {
	/**
	 * <b>加入购物车。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 顾客表code
	 * @param productID 商品id
	 * @param number 商品数量
	 * @return
	 */
	public boolean addProduct(String code,long productID,int number);

	/**
	 * <b>更新购物车中的商品。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 顾客表code
	 * @param productId 商品id
	 * @param quantity 商品数量
	 * @return
	 */
	public boolean updateProduct(String code, long productId, int quantity);
	
	
}
