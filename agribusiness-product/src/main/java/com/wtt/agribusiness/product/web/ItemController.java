package com.wtt.agribusiness.product.web;

import com.wtt.agribusiness.product.service.SkuInfoService;
import com.wtt.agribusiness.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;

@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 展示当前sku的详情
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId){
        System.out.println("准备查询"+skuId+"的详情");
        SkuItemVo vo = skuInfoService.item(skuId);
        return "item";
    }

}
