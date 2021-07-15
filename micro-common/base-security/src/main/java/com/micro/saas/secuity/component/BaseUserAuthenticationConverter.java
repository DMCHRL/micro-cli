package com.micro.saas.secuity.component;

import com.micro.saas.core.constant.SecurityConstants;
import com.micro.saas.secuity.entity.SystemAuthority;
import com.micro.saas.secuity.entity.SystemUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author
 * <p>
 * 根据checktoken 的结果转化用户信息
 */
public class BaseUserAuthenticationConverter implements UserAuthenticationConverter {
	private static final String N_A = "N/A";

	/**
	 * Extract information about the user to be used in an access token (i.e. for resource servers).
	 *
	 * @param authentication an authentication representing a user
	 * @return a map of key values representing the unique information about the user
	 */
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put(USERNAME, authentication.getName());
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		return response;
	}

	/**
	 * Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an Authentication from a map.
	 *
	 * @param map a map of user information
	 * @return an Authentication representing the user or null if there is none
	 */
	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey(USERNAME)) {
			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
			List<SystemAuthority> authoritiesStr = authorityListToList(authorities);
			String username = (String) map.get(USERNAME);
			String userId = (String) map.get(SecurityConstants.DETAILS_USER_ID);
			String organId = (String) map.get(SecurityConstants.DETAILS_ORGAN_ID);
			String tenantId = (String) map.get(SecurityConstants.DETAILS_TENANT_ID);
			SystemUser user = new SystemUser(userId, username,tenantId, "", authoritiesStr, organId);
			return new UsernamePasswordAuthenticationToken(user, N_A, authorities);
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		Object authorities = map.get(AUTHORITIES);
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
					.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		throw new IllegalArgumentException("Authorities must be either a String or a Collection");
	}

	private static  List<SystemAuthority> authorityListToList(
			Collection<? extends GrantedAuthority> userAuthorities) {
		Assert.notNull(userAuthorities, "userAuthorities cannot be null");
		List<SystemAuthority> list = new ArrayList<>(userAuthorities.size());

		for (GrantedAuthority authority : userAuthorities) {
			SystemAuthority baseGrantedAuthority = new SystemAuthority(authority.getAuthority());
			list.add(baseGrantedAuthority);
		}
		return list;
	}
}
