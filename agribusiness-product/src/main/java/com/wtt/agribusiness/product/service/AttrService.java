package com.wtt.agribusiness.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.product.vo.AttrRespVo;
import com.wtt.agribusiness.product.vo.AttrVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.product.entity.AttrEntity;

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

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);
}

