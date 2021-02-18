package com.wtt.agribusiness.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})  //排除数据源有关的配置
public class AgribusinessGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessGatewayApplication.class, args);
    }

}
