package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient//向注册中心注册
@EnableFeignClients//开启远程调用
public class SearchServiceStarter {
    public static void main(String[] args) {
        SpringApplication.run(SearchServiceStarter.class,args);
    }
}
