package com.micro.saas.data.tenant;

import cn.hutool.core.util.StrUtil;
import com.micro.saas.core.constant.CommonConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 */
@Slf4j
public class BaseFeignTenantInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (StrUtil.isBlank(TenantContextHolder.getTenantId())) {
			return;
		}
		requestTemplate.header(CommonConstant.TENANT_ID, TenantContextHolder.getTenantId());
	}
}
