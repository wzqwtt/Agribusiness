package com.wtt.agribusiness.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 封装订单提交数据的VO
 */
@Data
public class OrderSubmitVo {
    private Long addrId;    //收获地址的ID
    private Integer payType;    //支付方式
    //无需提交需要购买的商品，去购物车再获取一遍
    //优惠、发票等等

    private String orderToken;  //防重令牌
    private BigDecimal payPrice;    //应付价格，验价

    private String note;    //订单备注
    //用户相关信息都在session里，直接去session取
}
