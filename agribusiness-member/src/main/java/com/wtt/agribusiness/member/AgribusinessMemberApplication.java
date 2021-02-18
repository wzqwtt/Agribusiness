package com.wtt.agribusiness.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.wtt.agribusiness.member.feign")
@EnableDiscoveryClient
@MapperScan("com.wtt.agribusiness.member.dao")
@SpringBootApplication
public class AgribusinessMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessMemberApplication.class, args);
    }

}
