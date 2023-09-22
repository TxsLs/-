package org.boot.hf.admin.security;

import java.util.ArrayList;
import java.util.List;

import org.boot.hf.admin.entity.Employee;
import org.boot.hf.admin.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <b>LoginUserDetailsService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeService service;

	/** 
	 * loadUserByUsername。
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
		Employee user = service.findByCode(code);
		int admin = user.getAdmin();
		List<GrantedAuthority> authList = new ArrayList<>();
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在!");
		} else if (admin == 0) {
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		User su = new User(code, user.getPassword(), authList);
		return su;
	}

}
