package com.yuntu.saas.account.common.organ.feign;

import com.yuntu.saas.account.common.organ.entity.SysOrgan;
import com.micro.saas.core.api.R;
import com.micro.saas.core.constant.SecurityConstants;
import com.micro.saas.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "feignSysOrganService", value = ServiceNameConstants.UMPS_SERVICE)
public interface FeignSysOrganService {

    /**
     * 根据Id获取租户信息
     * @param organId
     * @param from
     * @return
     */
    @GetMapping(value = "/organ/getById")
    R<SysOrgan> getOne(@RequestParam String organId , @RequestHeader(SecurityConstants.FROM) String from);

}
