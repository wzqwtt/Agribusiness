package com.wtt.agribusiness.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.product.entity.SkuInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-16 20:54:07
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);
}

