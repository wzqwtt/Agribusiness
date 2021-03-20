package com.wtt.agribusiness.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.wtt.agribusiness.member.exception.PhoneExistException;
import com.wtt.agribusiness.member.exception.UsernameExistException;
import com.wtt.agribusiness.member.feign.CouponFeignService;
import com.wtt.agribusiness.member.vo.MemberLoginVo;
import com.wtt.agribusiness.member.vo.MemberRegistVo;
import com.wtt.agribusiness.member.vo.SocialUser;
import com.wtt.common.exception.BizCodeEnume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wtt.agribusiness.member.entity.MemberEntity;
import com.wtt.agribusiness.member.service.MemberService;
import com.wtt.common.utils.PageUtils;
import com.wtt.common.utils.R;



/**
 * 会员
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:28:55
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @PostMapping("/oauth2/login")
    public R oauthlogin(@RequestBody SocialUser socialUser) throws Exception {

            MemberEntity entity = memberService.login(socialUser);
        if(entity!=null){
            return R.ok().setData(entity);
        }else{
            return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getCode(),BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getMsg());
        }
    }


    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo){

        MemberEntity entity = memberService.login(vo);
        if(entity!=null){
            return R.ok().setData(entity);
        }else{
            return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getCode(),BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getMsg());
        }
    }


    @PostMapping("/regist")
    public R regist(@RequestBody MemberRegistVo vo){

        try {
            memberService.regist(vo);
        }catch (PhoneExistException e){
            return R.error(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(),BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
        }catch (UsernameExistException e){
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(),BizCodeEnume.USER_EXIST_EXCEPTION.getMsg());
        }

        return R.ok();
    }

    //测试
    @RequestMapping("/coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R membercoupons = couponFeignService.membercoupons();
        return R.ok().put("member",memberEntity).put("coupons",membercoupons.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
