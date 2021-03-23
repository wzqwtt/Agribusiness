package com.wtt.agribusiness.order.web;


import com.wtt.agribusiness.order.service.OrderService;
import com.wtt.agribusiness.order.vo.OrderConfirmVo;
import com.wtt.agribusiness.order.vo.OrderSubmitVo;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {

    @Autowired
    OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("orderConfirmData", confirmVo);
        //展示订单确认的数据
        return "confirm";
    }

    /**
     * 下单功能
     *
     * @param vo
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo) {
        System.out.println("订单提交的数据..." + vo);

        //下单

        //下单成功来到支付选择页

        //下单失败回到d订单确认页，重新确认订单
        return null;
    }

}
