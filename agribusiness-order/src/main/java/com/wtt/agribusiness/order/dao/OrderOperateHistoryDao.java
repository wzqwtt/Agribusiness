package com.wtt.agribusiness.order.dao;

import com.wtt.agribusiness.order.entity.OrderOperateHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:46:32
 */
@Mapper
public interface OrderOperateHistoryDao extends BaseMapper<OrderOperateHistoryEntity> {
	
}
