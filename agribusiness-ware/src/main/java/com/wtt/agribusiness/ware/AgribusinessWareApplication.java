package com.wtt.agribusiness.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan("com.wtt.agribusiness.ware.dao")
@SpringBootApplication
public class AgribusinessWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessWareApplication.class, args);
    }

}
