package com.ass.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //该注解表名当前类是一个Eureka Server服务端
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
