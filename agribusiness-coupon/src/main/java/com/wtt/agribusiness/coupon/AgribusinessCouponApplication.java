package com.wtt.agribusiness.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.wtt.agribusiness.coupon.dao")
@SpringBootApplication
public class AgribusinessCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessCouponApplication.class, args);
    }

}
