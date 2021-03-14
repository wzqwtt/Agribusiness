package com.wtt.agribusiness.auth.feign;

import com.wtt.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("agribusiness-third-party")
public interface ThirdPartFeignService {

    @GetMapping("/sms/sendcode")
    public R senCode(@RequestParam("phone") String phone, @RequestParam("code") String code);

}
