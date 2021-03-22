package com.wtt.agribusiness.order.config;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyRabbitConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate
     */
    @PostConstruct
    public void initRabbitTemplate() {
        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *  只要消息抵达Broker就ack=true
             * @param correlationData   当前消息的唯一关联数据（这个是消息的唯一id）
             * @param ack   消息是否成功收到
             * @param cause 失败的原因
             */

            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm...correlationData[" + correlationData + "]==>ack[" + ack + "]==>cause[" + cause + "]");
            }
        });

        //设置消息抵达队列的确认回调，
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败的回调
             * @param message   投递失败的消息详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange  当时消息发给哪个交换机
             * @param routingKey    当时消息用哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Fail Message[" + message + "]==>replyCode[" + replyCode + "]==>" +
                        "replyText[" + replyText + "]==>exchange[" + exchange + "]==>routingKey[" + routingKey + "]");
            }
        });
    }

}
