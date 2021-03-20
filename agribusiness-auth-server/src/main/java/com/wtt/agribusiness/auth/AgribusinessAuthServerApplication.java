package com.wtt.agribusiness.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession //整合Redisz作为session的存储
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AgribusinessAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessAuthServerApplication.class, args);
    }

}
