package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.service.MerchantService;

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
	private MerchantService userService;

	/** 
	 * loadUserByUsername。
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
		com.example.demo.entity.Merchant user = userService.findByCode(code);
		if (user == null)
			throw new UsernameNotFoundException("用户名不存在!");
		List<GrantedAuthority> authList = new ArrayList<>();
		//authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		User su = new User(code, user.getPassword(), authList);
		return su;
	}

}
