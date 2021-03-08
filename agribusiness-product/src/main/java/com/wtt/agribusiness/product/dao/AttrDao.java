package com.wtt.agribusiness.product.dao;

import com.wtt.agribusiness.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-16 20:54:07
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
