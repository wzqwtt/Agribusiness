package com.wtt.agribusiness.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单确认页要用的数据
 */
@Data
public class OrderConfirmVo {

    //收货地址
    List<MemberAddressVo> address;

    //所有选中的购物项
    List<OrderItemVo> items;

    //优惠卷
    private Integer integration;

    //订单总额
    BigDecimal total;

    //应付价格
    BigDecimal payPrice;


}
