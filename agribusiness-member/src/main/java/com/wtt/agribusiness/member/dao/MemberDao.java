package com.wtt.agribusiness.member.dao;

import com.wtt.agribusiness.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:28:55
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
