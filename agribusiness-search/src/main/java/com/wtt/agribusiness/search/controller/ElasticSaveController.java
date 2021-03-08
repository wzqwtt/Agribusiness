package com.wtt.agribusiness.search.controller;

import com.wtt.agribusiness.search.service.ProductSaveService;
import com.wtt.common.exception.BizCodeEnume;
import com.wtt.common.to.es.SkuEsModel;
import com.wtt.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;

    //上架商品
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean b = false;
        try {
            b = productSaveService.productStatusUp(skuEsModels);
        } catch (Exception e) {
            log.error("ElasticSaveController商品上架错误！错误原因：{}", e);
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }

        if (b) return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        else return R.ok();
    }

}
