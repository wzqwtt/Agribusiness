package com.wtt.agribusiness.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class WareSkuLockVo {

    private String orderSn; //订单号

    private List<OrderItemVo> locks;    //需要锁的所有库存信息


}
