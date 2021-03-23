package com.wtt.agribusiness.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class AgribusinessFeignConfig {

    /**
     * Feign远程调用丢失请求头问题
     *
     * @return
     */
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                //1、使用RequestContextHolder拿到刚进来的请求
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();   //老请求
                    //同步请求头数据，Cookie
                    //给新请求同步了老请求的Cookie
                    if (request != null) {
                        template.header("Cookie", request.getHeader("Cookie"));
                    }
                }

                //System.out.println("feign远程之前先进行RequestInterceptor.apply");
            }
        };
    }

}
