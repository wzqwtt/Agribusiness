package com.wtt.agribusiness.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.wtt.agribusiness.auth.feign.MemberFeignService;
import com.wtt.agribusiness.auth.feign.ThirdPartFeignService;
import com.wtt.agribusiness.auth.vo.UserRegistVo;
import com.wtt.common.constant.AuthServerConstant;
import com.wtt.common.exception.BizCodeEnume;
import com.wtt.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 发送一个请求直接跳转到一个页面
     * SpringMVC viewController将请求和页面映射过来
     *
     * @return
     */

    /**
     * 获取短信验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {

        //TODO 1、接口防刷

        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60000) {
                //60秒内不能再发
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        //2、验证码的再次校验
        String code  = UUID.randomUUID().toString().substring(0, 5);
        String substring = code + "_" + System.currentTimeMillis();
        //redis缓存验证码，防止同一个phone在60秒内再次发送验证码
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, substring, 10, TimeUnit.MINUTES);
        thirdPartFeignService.senCode(phone, code);

        return R.ok();

    }

    /**
     * redirectAttributes模拟重定向携带数据
     * @param vo
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/regist")
    public String regist(@Valid UserRegistVo vo, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            Map<String, String> errors = result.getFieldErrors()
                    .stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
//            model.addAttribute("errors",errors);
            redirectAttributes.addFlashAttribute("errors",errors);
            //校验出错，转发到注册页
            return "redirect:http://auth.agribusiness.com/reg.html";
        }
        //真正注册，调用远程服务进行注册
        //1、校验验证码
        String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if(!StringUtils.isEmpty(s)){
            if(code.equals(s.split("_")[0])){
                //删除验证码,令牌机制
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                //验证码成功，调用远程服务进行注册
                R r = memberFeignService.regist(vo);
                if(r.getCode() == 0){
                    //成功
                    return "redirect:http://auth.agribusiness.com/login.html";
                }else{
                    //失败
                    Map<String,String> errors = new HashMap<>();
                    errors.put("msg",r.getData(new TypeReference<String>(){}));
                    redirectAttributes.addFlashAttribute("errors",errors);
                    return "redirect:http://auth.agribusiness.com/reg.html";
                }
            }else{
                Map<String, String> errors = new HashMap<>();
                errors.put("code","验证码错误");
                redirectAttributes.addFlashAttribute("errors",errors);
                return "redirect:http://auth.agribusiness.com/reg.html";
            }
        }else{
            Map<String, String> errors = new HashMap<>();
            errors.put("code","验证码错误");
            redirectAttributes.addFlashAttribute("errors",errors);
            //校验出错，转发到注册页
            return "redirect:http://auth.agribusiness.com/reg.html";
        }
    }

}
