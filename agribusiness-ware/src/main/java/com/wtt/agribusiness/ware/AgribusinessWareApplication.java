package com.wtt.agribusiness.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wtt.agribusiness.ware.dao")
@SpringBootApplication
public class AgribusinessWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessWareApplication.class, args);
    }

}
