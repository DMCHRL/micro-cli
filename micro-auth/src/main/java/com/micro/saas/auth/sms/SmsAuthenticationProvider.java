package com.micro.saas.auth.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.*;


public class SmsAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private UserDetailsService userDetailsService;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        UserDetails user = retrieveUser(username);

        preAuthenticationChecks.check(user);
        String phoneNumber = authentication.getPrincipal().toString().split(":")[0];
        String code = authentication.getCredentials().toString();
//        // 尝试从Redis中取出Model
//        BasePhone basePhone =
//                Optional.ofNullable(
//                        (BasePhone)redisTemplate.opsForValue().get(RedisKeyPrefix.SMS_CODE_PREFIX+phoneNumber))
//                        .orElseThrow(() -> new ApiException(CommonExceptionEnum.SMS_VERIFY_CODE_INVALID));
//        //是否已验证
//        ApiAssert.isFalse(basePhone.getVerify(),CommonExceptionEnum.SMS_VERIFY_CODE_INVALID);
//
//        //是否有效期内
//        LocalDateTime lastSendTime = basePhone.getLastSendTime();
//        LocalDateTime verifyTime = LocalDateTime.now().minusMinutes(CommonConstant.SMS_CODE_VERIFY_TIMES);
//        ApiAssert.isTrue(verifyTime.isBefore(lastSendTime),CommonExceptionEnum.SMS_VERIFY_CODE_INVALID);
//
//        //校验验证码
//        Optional.of(basePhone).filter(x -> x.getCode().equals(code))
//                .orElseThrow(() -> new ApiException(CommonExceptionEnum.SMS_VERIFY_CODE_ERROR));
//
//        basePhone.setVerify(true);
//        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
//        long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(),midnight);
//        redisTemplate.opsForValue().set(RedisKeyPrefix.SMS_CODE_PREFIX + phoneNumber, basePhone, millSeconds, TimeUnit.MILLISECONDS);

        postAuthenticationChecks.check(user);

        return createSuccessAuthentication(user, authentication, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        this.logger.debug("Authenticated user");
        return result;
    }

    protected final UserDetails retrieveUser(String username) throws AuthenticationException {

        try {
            UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException var4) {
            throw var4;
        } catch (InternalAuthenticationServiceException var5) {
            throw var5;
        } catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        private DefaultPreAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                SmsAuthenticationProvider.this.logger.debug("Failed to authenticate since user account is locked");
                throw new LockedException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            } else if (!user.isEnabled()) {
                SmsAuthenticationProvider.this.logger.debug("Failed to authenticate since user account is disabled");
                throw new DisabledException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            } else if (!user.isAccountNonExpired()) {
                SmsAuthenticationProvider.this.logger.debug("Failed to authenticate since user account has expired");
                throw new AccountExpiredException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                SmsAuthenticationProvider.this.logger.debug("Failed to authenticate since user account credentials have expired");
                throw new CredentialsExpiredException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
            }
        }
    }

}
