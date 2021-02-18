package com.wtt.agribusiness.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.ware.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:52:30
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
