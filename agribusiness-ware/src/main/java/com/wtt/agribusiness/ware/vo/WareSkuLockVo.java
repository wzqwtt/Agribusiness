package com.wtt.agribusiness.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class WareSkuLockVo {

    private String orderSn; //订单号

    private List<OrderItemVo> locks;    //需要锁的所有库存信息


}
