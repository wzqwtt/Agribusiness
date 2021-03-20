package com.wtt.agribusiness.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wtt.agribusiness.member.dao.MemberLevelDao;
import com.wtt.agribusiness.member.entity.MemberLevelEntity;
import com.wtt.agribusiness.member.exception.PhoneExistException;
import com.wtt.agribusiness.member.exception.UsernameExistException;
import com.wtt.agribusiness.member.vo.MemberLoginVo;
import com.wtt.agribusiness.member.vo.MemberRegistVo;
import com.wtt.agribusiness.member.vo.SocialUser;
import com.wtt.common.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        entity.setNickname(vo.getUserName());
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

    @Override
    public MemberEntity login(MemberLoginVo vo) {

        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();

        //1、去数据库查询
        //select * from ums_member where username = ? or mobile = ?
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginacct).or().eq("mobile", loginacct));

        if (entity == null) {
            //登陆失败
            return null;
        } else {
            //获取到数据库的password字段
            String passwordDb = entity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //密码匹配
            boolean matches = passwordEncoder.matches(password, passwordDb);
            if (matches) {
                //登陆成功
                return entity;
            } else {
                return null;
            }

        }
    }

    @Override
    public MemberEntity login(SocialUser socialUser) throws Exception {
        //登陆和注册
        String uid = socialUser.getUid();
        //判断当前社交用户是否已经登陆过系统
        MemberDao memberDao = this.baseMapper;
        MemberEntity memberEntity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));
        if (memberEntity != null) {
            //这个用户注册过
            MemberEntity update = new MemberEntity();
            update.setId(memberEntity.getId());
            update.setAccessToken(socialUser.getAccess_token());
            update.setExpiresIn(socialUser.getExpires_in());

            memberDao.updateById(update);

            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            return memberEntity;
        } else {
            //2、没有查到当前社交用户对应的记录，我们就需要注册一个
            MemberEntity regist = new MemberEntity();
            try {
                //3、查询当前社交用户的社交账号信息（昵称、性别）
                Map<String, String> query = new HashMap<>();
                query.put("access_token", socialUser.getAccess_token());
                query.put("uid", socialUser.getUid());
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String, String>(), query);
                if (response.getStatusLine().getStatusCode() == 200) {
                    //查询成功
                    String json = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObject = JSON.parseObject(json);
                    //当前社交账号对应的昵称
                    String name = jsonObject.getString("name");
                    //性别
                    String gender = jsonObject.getString("gender");
                    //头像
                    String profile_image_url = jsonObject.getString("avatar_large");
                    //个性签名
                    String description = jsonObject.getString("description");
                    //..........
                    regist.setNickname(name);
                    regist.setGender("m".equals(gender) ? 1 : 0);
                    regist.setHeader(profile_image_url);
                    regist.setSign(description);
                    //..........
                }
            }catch (Exception e){

            }
            //设置uid、token、expriedIn
            regist.setSocialUid(socialUser.getUid());
            regist.setAccessToken(socialUser.getAccess_token());
            regist.setExpiresIn(socialUser.getExpires_in());
            memberDao.insert(regist);

            return regist;
        }
    }

}