package com.micro.saas.secuity.utils;


import com.micro.saas.secuity.entity.SystemUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 安全工具类
 *
 * @author
 */
@UtilityClass
public class SecurityUtils {
	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 * @return BaseUser
	 * <p>
	 * 获取当前用户的全部信息 EnableBaseResourceServer true
	 * 获取当前用户的用户名 EnableBaseResourceServer false
	 */
	public SystemUser getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof SystemUser) {
			return (SystemUser) principal;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public SystemUser getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}

	public String getUsername() {
		Authentication authentication = getAuthentication();
		SystemUser user = getUser(authentication);
		if(user == null)return null;
		return user.getUsername();
	}

	/**
	 * 获取用户Id
	 */
	public String getUserId(){
		Authentication authentication = getAuthentication();
		return Objects.requireNonNull(getUser(authentication)).getUserId();
	}

	public String getOrganId(){
		Authentication authentication = getAuthentication();
		return Objects.requireNonNull(getUser(authentication)).getOrganId();
	}


}
