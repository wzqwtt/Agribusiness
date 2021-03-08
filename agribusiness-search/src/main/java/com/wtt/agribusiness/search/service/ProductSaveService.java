package com.wtt.agribusiness.search.service;

import com.wtt.common.to.es.SkuEsModel;

import java.util.List;

public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels);
}
