package com.micro.saas.umps.admin;

import com.micro.saas.secuity.annotation.EnableBaseFeignClients;
import com.micro.saas.secuity.annotation.EnableBaseResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableBaseResourceServer
@EnableBaseFeignClients
public class MicroUmpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroUmpsApplication.class, args);
    }

}
