package com.wtt.agribusiness.member.feign;

import com.wtt.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("agribusiness-coupon")
public interface CouponFeignService {
    //编写一个接口
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
