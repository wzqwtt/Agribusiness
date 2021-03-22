package com.wtt.agribusiness.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgribusinessOrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 1、如何创建Exchange、Queue、Binding
     * 2、如何接收消息
     */
    @Test
    public void createExchange() {
//        amqpAdmin
    }



}
