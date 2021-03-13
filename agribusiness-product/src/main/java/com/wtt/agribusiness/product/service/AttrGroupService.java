package com.wtt.agribusiness.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.product.vo.AttrGroupWithAttrsVo;
import com.wtt.agribusiness.product.vo.SkuItemVo;
import com.wtt.agribusiness.product.vo.SpuItemAttrGroupVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-16 20:54:07
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);


    /**
     * 查出当前spu对应的所有属性的分组信息以及当前分组下所有属性对应的值
     * @param spuId
     * @param catalogId
     * @return
     */
    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

