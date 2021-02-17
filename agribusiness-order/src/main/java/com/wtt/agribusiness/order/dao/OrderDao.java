package com.wtt.agribusiness.order.dao;

import com.wtt.agribusiness.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:46:33
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
