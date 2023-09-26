package com.example.demo.service;

import java.util.Map;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;
import com.example.demo.Service;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.Photo;

public interface MerchantService extends Service<Merchant> {

	/*
	public int updateSelfInfo(Merchant vo);
	
	*//**
		* <b>用户修改自己的密码。</b>
		* <p><b>详细说明：</b></p>
		* <!-- 在此添加详细说明 -->
		* 无。
		* @param code 用户登录名
		* @param oldPassword 旧明文密码
		* @param newPassword 新明文密码
		* @return 更新数据条数
		*/
	/*
	public int changeSelfPassword(String code, String oldPassword, String newPassword);
	
	*//**
		* <b>系统管理员修改密码。</b>
		* <p><b>详细说明：</b></p>
		* <!-- 在此添加详细说明 -->
		* 系统管理员可以修改任何人的密码而无需旧密码。
		* 调用此方法修改密码前，请确保该用户具有系统管理员权限
		* @param code 用户登录名
		* @param newPassword 新明文密码
		* @return 更新数据条数
		*/
	/*
	
	public int changePassword(String code, String newPassword);
	
	*//**
		* <b>检查密码正确性。</b>
		* <p><b>详细说明：</b></p>
		* <!-- 在此添加详细说明 -->
		* 无。
		* @param code 用户登录名
		* @param password 明文密码
		* @return 如果成功则返回用户，否则返回null
		*/
	/*
	
	public Merchant checkPassword(String code, String password);
	
	*//** 
		* @see UserDao#getPhoto(long)
		*/
	/*
	
	public Photo getPhoto(long id);
	
	*//** 
		* @see UserDao#updatePhoto(Photo)
		*/
	/*
	
	public int updatePhoto(Photo photo);
	
	*//** 
		* @see UserDao#findByCode(String)
		*/
			public Merchant findByCode(String code);
}
