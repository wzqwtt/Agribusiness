package com.wtt.agribusiness.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@MapperScan("com.wtt.agribusiness.product.dao")
@SpringBootApplication
public class AgribusinessProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessProductApplication.class, args);
    }

}
