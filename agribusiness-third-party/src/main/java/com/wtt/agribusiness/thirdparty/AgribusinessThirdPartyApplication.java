package com.wtt.agribusiness.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AgribusinessThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgribusinessThirdPartyApplication.class, args);
    }

}
