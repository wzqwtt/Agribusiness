package com.wtt.agribusiness.order.feign;

import com.wtt.agribusiness.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("agribusiness-cart")
public interface CartFeiginService {
    @GetMapping("/currentUserCartItems")
    public List<OrderItemVo> getCurrentUserCartItems();

}
