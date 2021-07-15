package com.micro.saas.umps.admin.sms.config;

import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Autowired
    private SmsProperties smsProperties;

    @Bean(name = "smsClient")
    public  com.aliyun.dysmsapi20170525.Client smsClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(smsProperties.getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(smsProperties.getAccessKeySecret());
        // 访问的域名
        config.endpoint = smsProperties.getEndpoint();
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
}
