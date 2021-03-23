package com.wtt.agribusiness.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.ware.vo.FareVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.ware.entity.WareInfoEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 仓库信息
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:52:30
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据收获地址模拟计算运费
     * @param addrId
     * @return
     */
    FareVo getFare(Long addrId);
}

