package com.wtt.agribusiness.auth.feign;

import com.wtt.agribusiness.auth.vo.UserRegistVo;
import com.wtt.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("agribusiness-member")
public interface MemberFeignService {

    @PostMapping("/member/member/regist")
    public R regist(@RequestBody UserRegistVo vo);

}
