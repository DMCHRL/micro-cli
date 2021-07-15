package com.micro.saas.auth;

import com.micro.saas.secuity.annotation.EnableBaseFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableBaseFeignClients
public class MicroAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroAuthApplication.class, args);
    }

}
