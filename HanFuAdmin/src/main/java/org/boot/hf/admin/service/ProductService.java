package org.boot.hf.admin.service;

import org.boot.hf.admin.Service;
import org.boot.hf.admin.entity.PhotoShops;
import org.boot.hf.admin.entity.Product;

public interface ProductService extends Service<Product>{
	
	public Product findByCode(String code);
	/**
	 * <b>更新个人信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据用户id更新用户信息，值对象的id必须有效。
	 * 可以修改自己少部分个人信息。
	 * @param vo　个人用户信息
	 * @return　是否成功
	 */

	public PhotoShops getPhoto(long id);

	public boolean updatePhoto(PhotoShops photo);
}
