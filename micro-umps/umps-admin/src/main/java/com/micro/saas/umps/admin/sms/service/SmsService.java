package com.micro.saas.umps.admin.sms.service;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.micro.saas.umps.admin.sms.config.SmsProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Autowired
    private com.aliyun.dysmsapi20170525.Client smsClient;

    @Autowired
    private SmsProperties smsProperties;

    @SneakyThrows
    public void sendVerificationCode(String phone,String code){
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(smsProperties.getSignName())
                .setTemplateCode(smsProperties.getVerificationCode())
                .setTemplateParam("{\"code\":\""+code+"\"}");
        // 复制代码运行请自行打印 API 的返回值
        smsClient.sendSms(sendSmsRequest);
    }
}
