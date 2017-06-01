package com.solidarize.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class SolidarizeNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolidarizeNotificationApplication.class, args);
    }
}
