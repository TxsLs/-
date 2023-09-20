package org.boot.hf.admin.service.Impl;

import java.util.Date;

import org.boot.hf.admin.BaseService;
import org.boot.hf.admin.dao.EmployeeDao;
import org.boot.hf.admin.entity.Employee;
import org.boot.hf.admin.entity.Photo;
import org.boot.hf.admin.service.EmployeeService;
import org.quincy.rock.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl extends BaseService<Employee, EmployeeDao> implements EmployeeService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Employee findByCode(String code) {
		return this.dao().findByName("code", code);
	}

	@Override
	@Transactional
	public boolean changeSelfPassword(String code, String oldPassword, String newPassword) {
		Employee vo = this.findByCode(code);
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
	public Employee checkPassword(String code, String password) {
		Employee vo = this.findByCode(code);
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
	public boolean updateSelfInfo(Employee vo) {
		return dao().updateSelfInfo(vo) > 0;
	}

	@Override
	public boolean insert(Employee entity, boolean ignoreNullValue, String... excluded) {
		Date date = DateUtil.getDateByWord("now");
		entity.setJoinTime(date);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return super.insert(entity, ignoreNullValue, excluded);
	}

}
