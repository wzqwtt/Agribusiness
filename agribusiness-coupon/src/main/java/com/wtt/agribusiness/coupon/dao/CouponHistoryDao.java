package com.wtt.agribusiness.coupon.dao;

import com.wtt.agribusiness.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:19:44
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
