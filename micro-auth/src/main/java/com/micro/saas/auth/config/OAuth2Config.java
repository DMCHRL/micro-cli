package com.micro.saas.auth.config;

import com.micro.saas.auth.handler.OAuthWebResponseExceptionTranslator;
import com.micro.saas.auth.sms.SmsTokenGranter;
import com.micro.saas.core.constant.RedisKeyPrefix;
import com.micro.saas.core.constant.SecurityConstants;
import com.micro.saas.secuity.entity.SystemUser;
import com.micro.saas.secuity.service.BaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Zhifeng.Zeng
 * @description OAuth2服务器配置
 */
@Configuration
public class OAuth2Config {



    /**
     * @description 认证授权服务器
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private RedisConnectionFactory connectionFactory;

        @Autowired
        private BaseUserDetailsService userDetailsService;

        @Autowired
        @Qualifier(value = "smsAuthenticationProvider")
        private AuthenticationProvider smsAuthenticationProvider;

        @Autowired
        private DataSource dataSource;

        /**
         * 配置ClientDetails信息
         * @param clients
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientService());
        }

        @Bean
        public RedisTokenStore redisTokenStore() {
            RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
            redisTokenStore.setPrefix(RedisKeyPrefix.OAUTH_PREFIX);
            return redisTokenStore;
        }

        /**
         * 从数据库加载客户端信息
         * @return
         */
        @Bean
        public ClientDetailsService clientService() {
            return new JdbcClientDetailsService(dataSource);
        }

        /**
         * 配置TokenService参数
         * @return
         */
        @Bean
        public DefaultTokenServices tokenService(){
            DefaultTokenServices tokenService = new DefaultTokenServices();
            tokenService.setTokenStore(redisTokenStore());
            tokenService.setSupportRefreshToken(true);
            tokenService.setClientDetailsService(clientService());
            tokenService.setTokenEnhancer(tokenEnhancer());
            tokenService.setReuseRefreshToken(false);
            return tokenService;
        }

        /**
         * token增强，客户端模式不增强。
         *
         * @return TokenEnhancer
         */
        @Bean
        public TokenEnhancer tokenEnhancer() {
            return (accessToken, authentication) -> {
//                if (SecurityConstants.CLIENT_CREDENTIALS
//                        .equals(authentication.getOAuth2Request().getGrantType())) {
//                    return accessToken;
//                }
                final Map<String, Object> additionalInfo = new HashMap<>(8);
                SystemUser baseUser = (SystemUser) authentication.getUserAuthentication().getPrincipal();
                additionalInfo.put(SecurityConstants.DETAILS_USER_ID, baseUser.getUserId());
                additionalInfo.put(SecurityConstants.DETAILS_USERNAME, baseUser.getUsername());
                additionalInfo.put(SecurityConstants.DETAILS_TENANT_ID, baseUser.getTenantId());
                additionalInfo.put(SecurityConstants.DETAILS_ORGAN_ID, baseUser.getOrganId());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
                return accessToken;
            };
        }


        /**
         * @description token及用户信息存储到redis，当然你也可以存储在当前的服务内存，不推荐
         * @param endpoints
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            //token信息存到redis
            endpoints.tokenStore(redisTokenStore()).authenticationManager(authenticationManager);
            endpoints.tokenServices(tokenService());
            endpoints.userDetailsService(userDetailsService);
            List<TokenGranter> tokenGranters = new ArrayList<>();
            /**
             * 添加模式看AbstractTokenGranter实现类
             */
            //刷新token
            tokenGranters.add(new RefreshTokenGranter(endpoints.getTokenServices(),clientService(),
                    endpoints.getOAuth2RequestFactory()));
            //自定义手机验证码模式
            tokenGranters.add(new SmsTokenGranter(smsAuthenticationProvider, endpoints.getTokenServices(),
                    clientService(), endpoints.getOAuth2RequestFactory()));
            endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));
            endpoints.exceptionTranslator(new OAuthWebResponseExceptionTranslator());

        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            //允许表单认证
            // 允许使用Query字段验证客户端，即client_id/client_secret 能够放在查询参数中
            oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("isAuthenticated()")
                    .checkTokenAccess("permitAll()").passwordEncoder(new BCryptPasswordEncoder());
        }
    }



}
