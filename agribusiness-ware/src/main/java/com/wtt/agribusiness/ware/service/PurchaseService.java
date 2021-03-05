package com.wtt.agribusiness.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.ware.vo.MergeVo;
import com.wtt.agribusiness.ware.vo.PurchaseDoneVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.ware.entity.PurchaseEntity;

import java.util.List;
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

    PageUtils queryPageUnreceive(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

