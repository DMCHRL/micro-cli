package com.micro.saas.data.tenant;

import cn.hutool.core.util.StrUtil;
import com.micro.saas.core.constant.CommonConstant;
import com.micro.saas.core.constant.RedisKeyPrefix;
import com.micro.saas.core.constant.SecurityConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 租户拦截器
 * @author
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

	@Autowired
	@Qualifier(value = "jdkRedisTemplate")
	private  RedisTemplate jdkRedisTemplate;


	@Override
	@SneakyThrows
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//需要token验权的请求，header中有token，通过token找租户ID
		String authorization = request.getHeader(CommonConstant.AUTHORIZATION);
		if(StrUtil.isNotBlank(authorization) && authorization.contains(OAuth2AccessToken.BEARER_TYPE)){
			String tokenValue = authorization.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
			String key = StrUtil.concat(false, RedisKeyPrefix.OAUTH_PREFIX,"access:",tokenValue);
			OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) jdkRedisTemplate.opsForValue().get(key);
			if(oAuth2AccessToken != null){
				String tenantId = String.valueOf(oAuth2AccessToken.getAdditionalInformation().get(SecurityConstants.DETAILS_TENANT_ID));
				if (StrUtil.isNotBlank(tenantId)) {
					TenantContextHolder.setTenantId(tenantId);
				}
			}
		}else{
			//不需要token验权的请求，header中无token，用header中的租户ID
			String tenantId = request.getHeader(CommonConstant.TENANT_ID);
			if (StrUtil.isNotBlank(tenantId)) {
				TenantContextHolder.setTenantId(tenantId);
			}
		}

		filterChain.doFilter(request, response);
		TenantContextHolder.clear();
	}
}
