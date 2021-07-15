package com.micro.saas.secuity.service.impl;

import com.micro.saas.core.api.R;
import com.micro.saas.core.constant.SecurityConstants;
import com.micro.saas.secuity.entity.SystemUser;
import com.micro.saas.secuity.service.BaseUserDetailsService;
import com.micro.saas.secuity.service.FeignUserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用户详细信息
 *
 * @author
 */
@Slf4j
@Service
@AllArgsConstructor
public class BaseUserDetailsServiceImpl implements BaseUserDetailsService {
    private final FeignUserService feignUserService;

    /**
     * 用户密码登录
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        return this.loadUserByPhone(username);
    }

    /**
     * 手机验证码登录
     *
     * @param phone
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByPhone(String phone) {
        return getUserDetails(feignUserService.infoByPhone(phone, SecurityConstants.FROM_IN));
    }


    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return
     */
    private UserDetails getUserDetails(R<SystemUser> result) {
        if (result == null || result.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return result.getData();
    }
}
