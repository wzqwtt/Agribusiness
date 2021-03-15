package com.wtt.agribusiness.member.service.impl;

import com.wtt.agribusiness.member.dao.MemberLevelDao;
import com.wtt.agribusiness.member.entity.MemberLevelEntity;
import com.wtt.agribusiness.member.exception.PhoneExistException;
import com.wtt.agribusiness.member.exception.UsernameExistException;
import com.wtt.agribusiness.member.vo.MemberRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wtt.common.utils.PageUtils;
import com.wtt.common.utils.Query;

import com.wtt.agribusiness.member.dao.MemberDao;
import com.wtt.agribusiness.member.entity.MemberEntity;
import com.wtt.agribusiness.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = new MemberEntity();

        //设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        entity.setLevelId(levelEntity.getId());

        //检查用户名和手机号是否唯一,为了让controller能感知异常，异常机制
        checkPhoneUnique(vo.getPhone());
        checkUsernameUnique(vo.getUserName());

        entity.setMobile(vo.getPhone());
        entity.setUsername(vo.getUserName());

        //密码：加密存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        entity.setPassword(encode);

        //其他默认信息

        //保存
        memberDao.insert(entity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException {
        MemberDao memberDao = this.baseMapper;
        Integer mobile = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (mobile > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameExistException {
        MemberDao memberDao = this.baseMapper;
        Integer count = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (count > 0) {
            throw new UsernameExistException();
        }
    }

}