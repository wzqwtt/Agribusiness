package com.wtt.agribusiness.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.ware.vo.LockStockResult;
import com.wtt.agribusiness.ware.vo.SkuHasStockVo;
import com.wtt.agribusiness.ware.vo.WareSkuLockVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.ware.entity.WareSkuEntity;

import java.util.List;
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

    /**
     * 检查库存
     * @param skuIds
     * @return
     */
    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);

    /**
     * 锁定库存方法
     * @param vo
     * @return
     */
    Boolean orderLockStock(WareSkuLockVo vo);
}

