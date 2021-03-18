package com.wtt.agribusiness.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wtt.agribusiness.auth.feign.MemberFeignService;
import com.wtt.agribusiness.auth.vo.MemberRespVo;
import com.wtt.agribusiness.auth.vo.SocialUser;
import com.wtt.common.utils.HttpUtils;
import com.wtt.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理社交登陆请求
 */
@Slf4j
@Controller
public class OAuth2Controller {

    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("client_id","269863394");
        map.put("client_secret","349d272d32c8fde428c690578d0de728");
        map.put("grant_type","authorization_code");
        map.put("redirect_uri","http://auth.agribusiness.com/oauth2.0/weibo/success");
        map.put("code",code);
        //1、根据code换取AccessToken
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), map, new HashMap<>());

        if(response.getStatusLine().getStatusCode() == 200){
            //成功获取到了Token
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
            System.out.println(socialUser);

            //知道当前是哪个社交用户
            //1）、当前用户如果是第一次进网站就自动注册进来（为当前社交用户生成一个会员信息账号，以后这个社交账号就对应某个会员）
            //登陆或者注册这个社交用户
            R oauthlogin = memberFeignService.oauthlogin(socialUser);
            if(oauthlogin.getCode() == 0){
                MemberRespVo data = oauthlogin.getData("data", new TypeReference<MemberRespVo>(){});
                log.info("登陆成功，用户：{}",data.toString());
                //2、登陆成功就跳回首页
                return "redirect:http://agribusiness.com";

            }else{
                return "redirect:http://auth.agribusiness.com/login.html";
            }
        }else{
            return "redirect:http://auth.agribusiness.com/login.html";
        }
    }

}
