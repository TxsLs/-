package com.example.demo.service;


import com.example.demo.Service;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.PhotoMerchant;

public interface MerchantService extends Service<Merchant> {

	public Merchant findByCode(String code);
	/**
	 * <b>更新个人信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据用户id更新用户信息，值对象的id必须有效。
	 * 可以修改自己少部分个人信息。
	 * @param vo　个人用户信息
	 * @return　是否成功
	 */
	public boolean updateSelfInfo(Merchant vo);

	/**
	 * <b>用户修改自己的密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 用户登录名
	 * @param oldPassword 旧明文密码
	 * @param newPassword 新明文密码
	 * @return 是否成功
	 */
	public boolean changeSelfPassword(String code, String oldPassword, String newPassword);

	/**
	 * <b>系统管理员修改密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 系统管理员可以修改任何人的密码而无需旧密码。
	 * 调用此方法修改密码前，请确保该用户具有系统管理员权限
	 * @param code 用户登录名
	 * @param newPassword 新明文密码
	 * @return 是否成功
	 */
	public boolean changePassword(String code, String newPassword);

	/**
	 * <b>检查密码正确性。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 用户登录名
	 * @param password 明文密码
	 * @return 如果成功则返回用户，否则返回null
	 */
	public Merchant checkPassword(String code, String password);

	/**
	 * <b>获得照片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回的照片数据存放在二进制数组里。
	 * @param id 主键id
	 * @return Photo
	 */
	public PhotoMerchant getPhoto(long id);

	/**
	 * <b>更新照片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param photo 照片实体对象
	 * @return 是否成功
	 */
	public boolean updatePhoto(PhotoMerchant photo);

}
