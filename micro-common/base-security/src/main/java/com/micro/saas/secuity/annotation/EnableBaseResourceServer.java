package com.micro.saas.secuity.annotation;

import com.micro.saas.secuity.component.BaseResourceServerAutoConfiguration;
import com.micro.saas.secuity.component.BaseSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author
 * <p>
 * 资源服务注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true) //权限拦截
@Import({BaseResourceServerAutoConfiguration.class, BaseSecurityBeanDefinitionRegistrar.class})
public @interface EnableBaseResourceServer {

}
