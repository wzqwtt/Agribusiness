package com.wtt.agribusiness.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wtt.agribusiness.order.dao")
@SpringBootApplication
public class AgribusinessOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessOrderApplication.class, args);
    }

}
