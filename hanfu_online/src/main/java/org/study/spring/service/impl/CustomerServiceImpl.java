package org.study.spring.service.impl;

import org.quincy.rock.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.spring.BaseService;
import org.study.spring.dao.CustomerDao;
import org.study.spring.entity.Customer;
import org.study.spring.entity.Photo;
import org.study.spring.service.CustomerService;


@Service
public class CustomerServiceImpl extends BaseService<Customer, CustomerDao> implements CustomerService {


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Customer findByCode(String code) {
		return this.dao().findByName("code", code);
	}

	@Override
	@Transactional
	public boolean changeSelfPassword(String code, String oldPassword, String newPassword) {
		Customer vo = this.findByCode(code);
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
	public Customer checkPassword(String code, String password) {
		Customer vo = this.findByCode(code);
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
	public boolean updateSelfInfo(Customer vo) {
		return dao().updateSelfInfo(vo) > 0;
	}

	@Override
	public boolean insert(Customer entity, boolean ignoreNullValue, String... excluded) {
		if (StringUtil.isEmpty(entity.getPassword())) {
			entity.setPassword("111111");
		}
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return super.insert(entity, ignoreNullValue, excluded);
	}
	
}
