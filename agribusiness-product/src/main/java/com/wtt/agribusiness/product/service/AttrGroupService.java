package com.wtt.agribusiness.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.product.entity.AttrGroupEntity;

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
}

