package com.wtt.agribusiness.cart.controller;

import com.wtt.agribusiness.cart.interceptor.CartInterceptor;
import com.wtt.agribusiness.cart.service.CartService;
import com.wtt.agribusiness.cart.vo.CartItem;
import com.wtt.agribusiness.cart.vo.UserInfoTo;
import com.wtt.common.constant.AuthServerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 去购物车的请求
     * 浏览器有一个cookie，user-key标识用户的身份，一个月后过期
     * 如果第一次使用购物车功能，都会给一个临时的用户身份user-key，
     * 浏览器以后保存。每次都会带上这个user-key
     *
     * 登陆，session有
     * 没登陆：按照cookie里面带来的user-key来做
     * 第一次如果没有临时用户，帮忙创建一个临时用户
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(){

        //1、快速得到用户信息id  user-key
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        System.out.println(userInfoTo);

        return "cartList";
    }


    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            Model model){

        CartItem cartItem = cartService.addToCart(skuId,num);
        model.addAttribute("item",cartItem);
        return "success";
    }


}
