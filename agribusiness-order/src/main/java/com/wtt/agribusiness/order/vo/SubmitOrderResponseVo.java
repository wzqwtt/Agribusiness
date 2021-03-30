package com.wtt.agribusiness.order.vo;

import com.wtt.agribusiness.order.entity.OrderEntity;
import lombok.Data;

@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;
    private Integer code;   //错误状态码 0成功



}
