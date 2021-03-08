package com.wtt.agribusiness.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.product.vo.AttrGroupRelationVo;
import com.wtt.agribusiness.product.vo.AttrRespVo;
import com.wtt.agribusiness.product.vo.AttrVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.product.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-16 20:54:07
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    /**
     * 在指定的所有属性集合中挑出检索属性
     * @param attrIds
     * @return
     */
    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

