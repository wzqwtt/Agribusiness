package com.wtt.agribusiness.order.dao;

import com.wtt.agribusiness.order.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:46:32
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
