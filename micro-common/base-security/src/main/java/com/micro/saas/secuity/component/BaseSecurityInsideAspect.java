package com.micro.saas.secuity.component;

import cn.hutool.core.util.StrUtil;
import com.micro.saas.core.constant.SecurityConstants;
import com.micro.saas.secuity.annotation.Inside;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * <p>
 * 服务间接口不鉴权处理逻辑
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class BaseSecurityInsideAspect {
	private final HttpServletRequest request;

	@SneakyThrows
	@Around("@annotation(inside)")
	public Object around(ProceedingJoinPoint point, Inside inside) {
		String header = request.getHeader(SecurityConstants.FROM);
		if (inside.value() && !StrUtil.equals(SecurityConstants.FROM_IN, header)) {
			log.warn("访问接口 {} 没有权限", point.getSignature().getName());
			throw new AccessDeniedException("访问被拒绝，没有权限");
		}
		return point.proceed();
	}

}
