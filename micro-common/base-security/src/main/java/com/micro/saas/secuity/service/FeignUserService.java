package com.micro.saas.secuity.service;

import com.micro.saas.core.api.R;
import com.micro.saas.core.constant.SecurityConstants;
import com.micro.saas.core.constant.ServiceNameConstants;
import com.micro.saas.secuity.entity.SystemUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author
 */
@FeignClient(contextId = "feignUserService", value = ServiceNameConstants.UMPS_SERVICE)
public interface FeignUserService {

    /**
     * 手机号查询用户、角色信息
     *
     * @param phone
     * @param from  调用标志
     * @return
     */
    @GetMapping("/user/info/{phone}")
    R<SystemUser> infoByPhone(@PathVariable("phone") String phone
            , @RequestHeader(SecurityConstants.FROM) String from);

}
