package com.wtt.agribusiness.order;

import com.wtt.agribusiness.order.entity.OrderReturnReasonEntity;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AgribusinessOrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessageTest(){

        OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
        reasonEntity.setId(1L);
        reasonEntity.setCreateTime(new Date());
        reasonEntity.setName("哈哈");

        //1、发送消息
        String msg = "Hello world";
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",reasonEntity);
        log.info("消息发送完成，消息内容：[{}]",reasonEntity);
    }



    /**
     * 1、如何创建Exchange、Queue、Binding
     * 2、如何接收消息
     */
    @Test
    public void createExchange() {
        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange [{}] 创建成功","hello-java-exchange");
    }

    @Test
    public void createQueue(){
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("Queue [{}] 创建成功","hello-java-queue");
    }

    @Test
    public void createBinding(){
        Binding binding = new Binding("hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",null);

        amqpAdmin.declareBinding(binding);
        log.info("Binding [{}] 创建成功","hello-java-binding");
    }



}
