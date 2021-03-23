package com.wtt.agribusiness.order.web;


import com.wtt.agribusiness.order.service.OrderService;
import com.wtt.agribusiness.order.vo.OrderConfirmVo;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {

    @Autowired
    OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("orderConfirmData",confirmVo);
        //展示订单确认的数据
        return "confirm";
    }

}
