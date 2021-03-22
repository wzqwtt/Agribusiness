package com.wtt.agribusiness.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableRabbit
@EnableDiscoveryClient
@MapperScan("com.wtt.agribusiness.order.dao")
@SpringBootApplication
public class AgribusinessOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessOrderApplication.class, args);
    }

}
