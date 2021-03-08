package com.wtt.agribusiness.product.feign;

import com.wtt.common.to.es.SkuEsModel;
import com.wtt.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("agribusiness-search")
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
