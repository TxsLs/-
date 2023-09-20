package org.study.spring.service.impl;

import java.util.Map;

import org.quincy.rock.core.dao.SQLProvider;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.spring.AppUtils;
import org.study.spring.BaseService;
import org.study.spring.dao.CustomerDao;
import org.study.spring.entity.Photo;
import org.study.spring.entity.Customer;
import org.study.spring.service.UserService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class UserServiceImpl extends BaseService<Customer, CustomerDao> implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public PageSet<Customer> queryPageByCondition(Map<String, Object> condition, Sort sort, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy(sort.toString(SQLProvider.getFieldNameConverter()));
		Page<Customer> p = this.operate().findPageByCondition(condition);
		return AppUtils.toPageSet(p);
	}

	@Override
	public Customer findByCode(String code) {
		return operate().findByCode(code);
	}

	@Override
	@Transactional
	public int changeSelfPassword(String code, String oldPassword, String newPassword) {
		Customer vo = operate().findByCode(code);
		if (vo == null || !passwordEncoder.matches(oldPassword, vo.getPassword()))
			return 0;
		else {
			String encodedPassword = passwordEncoder.encode(newPassword);
			return operate().changePassword(code, encodedPassword);
		}
	}

	@Override
	@Transactional
	public int changePassword(String code, String newPassword) {
		String encodedPassword = passwordEncoder.encode(newPassword);
		return operate().changePassword(code, encodedPassword);
	}

	@Override
	public Customer checkPassword(String code, String password) {
		Customer vo = operate().findByCode(code);
		if (vo == null || !passwordEncoder.matches(password, vo.getPassword()))
			return null;
		else {
			return vo;
		}
	}

	@Override
	public Photo getPhoto(long id) {
		return this.operate().getPhoto(id);
	}

	@Override
	@Transactional
	public int updatePhoto(Photo photo) {
		return operate().updatePhoto(photo);
	}

	@Override
	@Transactional
	public int updateSelfInfo(Customer vo) {
		return operate().updateSelfInfo(vo);
	}

	/** 
	 * insert。
	 * @see org.teach.study.boot.BaseService#insert(org.teach.study.boot.Entity, boolean)
	 */
	@Override
	@Transactional
	public int insert(Customer entity, boolean ignoreNullValue) {
		if (StringUtil.isEmpty(entity.getPassword())) {
			entity.setPassword("111111");
		}
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return super.insert(entity, ignoreNullValue);
	}
}
