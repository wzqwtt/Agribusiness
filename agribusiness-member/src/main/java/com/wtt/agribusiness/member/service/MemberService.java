package com.wtt.agribusiness.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wtt.agribusiness.member.exception.PhoneExistException;
import com.wtt.agribusiness.member.exception.UsernameExistException;
import com.wtt.agribusiness.member.vo.MemberLoginVo;
import com.wtt.agribusiness.member.vo.MemberRegistVo;
import com.wtt.common.utils.PageUtils;
import com.wtt.agribusiness.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:28:55
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);

    void checkPhoneUnique(String phone) throws PhoneExistException;

    void checkUsernameUnique(String username) throws UsernameExistException;

    MemberEntity login(MemberLoginVo vo);
}

