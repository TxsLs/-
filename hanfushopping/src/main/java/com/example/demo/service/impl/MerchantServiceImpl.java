package com.example.demo.service.impl;

import java.util.Date;

import org.quincy.rock.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.BaseService;
import com.example.demo.dao.MerchantDao;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.Photo;
import com.example.demo.service.MerchantService;

@Service
public class MerchantServiceImpl extends BaseService<Merchant, MerchantDao> implements MerchantService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Merchant findByCode(String code) {
		return this.dao().findByName("code", code);
	}

	@Override
	@Transactional
	public boolean changeSelfPassword(String code, String oldPassword, String newPassword) {
		Merchant vo = this.findByCode(code);
		if (vo == null || !passwordEncoder.matches(oldPassword, vo.getPassword()))
			return false;
		else {
			String encodedPassword = passwordEncoder.encode(newPassword);
			return dao().changePassword(code, encodedPassword) > 0;
		}
	}

	@Override
	@Transactional
	public boolean changePassword(String code, String newPassword) {
		String encodedPassword = passwordEncoder.encode(newPassword);
		return dao().changePassword(code, encodedPassword) > 0;
	}

	@Override
	public Merchant checkPassword(String code, String password) {
		Merchant vo = this.findByCode(code);
		if (vo == null || !passwordEncoder.matches(password, vo.getPassword()))
			return null;
		else {
			return vo;
		}
	}

	@Override
	public Photo getPhoto(long id) {
		return this.dao().getPhoto(id);
	}

	@Override
	@Transactional
	public boolean updatePhoto(Photo photo) {
		return dao().updatePhoto(photo) > 0;
	}

	@Override
	@Transactional
	public boolean updateSelfInfo(Merchant vo) {
		return dao().updateSelfInfo(vo) > 0;
	}

	@Override
	public boolean insert(Merchant entity, boolean ignoreNullValue, String... excluded) {
		Date date=DateUtil.getDateByWord("now");
		entity.setCreated_time(date);;
		return super.insert(entity, ignoreNullValue, excluded);
	}
}
