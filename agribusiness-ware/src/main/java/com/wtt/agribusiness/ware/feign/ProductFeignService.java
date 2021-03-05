package com.wtt.agribusiness.ware.feign;

import com.wtt.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("agribusiness-product")
public interface ProductFeignService {

    /**
     * 获取sku的名字
     * @param skuId
     * @return
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);
}
