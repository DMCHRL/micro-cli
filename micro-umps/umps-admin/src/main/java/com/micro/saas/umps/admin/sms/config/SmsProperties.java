package com.micro.saas.umps.admin.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 短信配置
 */
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
@Data
public class SmsProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String verificationCode;
    private String endpoint;
}
