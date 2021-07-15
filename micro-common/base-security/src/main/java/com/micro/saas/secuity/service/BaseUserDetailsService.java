package com.micro.saas.secuity.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author
 */
public interface BaseUserDetailsService extends UserDetailsService {

	/**
	 * 手机验证码登录
	 * @param phone
	 * @return
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException;


}
