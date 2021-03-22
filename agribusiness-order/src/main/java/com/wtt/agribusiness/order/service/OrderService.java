package com.wtt.agribusiness.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.order.vo.OrderConfirmVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:46:33
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 给订单确认页返回需要用的数据
     * @return
     */
    OrderConfirmVo confirmOrder();

}

