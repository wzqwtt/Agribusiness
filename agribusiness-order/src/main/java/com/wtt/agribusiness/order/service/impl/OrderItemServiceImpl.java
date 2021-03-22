package com.wtt.agribusiness.order.service.impl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wtt.common.utils.PageUtils;
import com.wtt.common.utils.Query;

import com.wtt.agribusiness.order.dao.OrderItemDao;
import com.wtt.agribusiness.order.entity.OrderItemEntity;
import com.wtt.agribusiness.order.service.OrderItemService;

@RabbitListener(queues = {"hello-java-queue"})
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()        );

        return new PageUtils(page);
    }

    /**
     * queues：声明需要监听的对列
     */
//    @RabbitListener(queues = {"hello-java-queue"})
    public void recieveMessage(Message message){
//        byte[] body = message.getBody();
        System.out.println("接收到的消息...内容：" + message +"===>类型："+message);
    }

}