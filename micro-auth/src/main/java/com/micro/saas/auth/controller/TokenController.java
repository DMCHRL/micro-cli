package com.micro.saas.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.micro.saas.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * token
 */
@RestController
@AllArgsConstructor
@RequestMapping("/token")
@Api(tags = "token管理")
public class TokenController {
	private final TokenStore tokenStore;
	/**
	 * 退出token
	 *
	 * @param authHeader Authorization
	 */
	@GetMapping(value = "/logout",headers = "version=1.0.0")
	@ApiOperation(value="注销")
	public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
		if (StrUtil.isBlank(authHeader)) {
			return R.ok("退出失败，token 为空");
		}
		String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
		if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
			return R.ok( "退出失败，token 无效");
		}
		// 清空access token
		tokenStore.removeAccessToken(accessToken);
		// 清空 refresh token
		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
		tokenStore.removeRefreshToken(refreshToken);
		return R.ok(Boolean.TRUE);
	}

}
