package com.wtt.agribusiness.product.dao;

import com.wtt.agribusiness.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-16 20:54:07
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
