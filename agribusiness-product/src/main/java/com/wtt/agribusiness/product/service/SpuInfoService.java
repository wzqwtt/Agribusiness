package com.wtt.agribusiness.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.product.entity.SpuInfoDescEntity;
import com.wtt.agribusiness.product.vo.SpuSaveVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-16 20:54:07
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity infoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);
}

