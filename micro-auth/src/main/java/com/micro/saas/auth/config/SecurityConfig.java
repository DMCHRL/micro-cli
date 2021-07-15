package com.micro.saas.auth.config;

import com.micro.saas.auth.sms.SmsAuthenticationProvider;
import com.micro.saas.secuity.service.BaseUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * @description Security核心配置
 * @author Liang
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //开启接口权限拦截
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BaseUserDetailsService baseUserDetailsService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(smsAuthenticationProvider());
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
      return baseUserDetailsService;
    }

    @Bean(name = "smsAuthenticationProvider")
    public AuthenticationProvider smsAuthenticationProvider(){
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(baseUserDetailsService);
       smsAuthenticationProvider.setRedisTemplate(redisTemplate);
        return smsAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v2/api-docs").permitAll() //放行/v2/api-docs
                .antMatchers("/token/logout").permitAll()
                .anyRequest()
                .authenticated();
    }
}
