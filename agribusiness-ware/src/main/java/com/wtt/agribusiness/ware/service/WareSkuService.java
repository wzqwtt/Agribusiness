package com.wtt.agribusiness.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:52:30
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);
}

