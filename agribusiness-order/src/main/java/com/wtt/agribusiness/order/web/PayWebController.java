package com.wtt.agribusiness.order.web;

import com.alipay.api.AlipayApiException;
import com.wtt.agribusiness.order.config.AlipayTemplate;
import com.wtt.agribusiness.order.service.OrderService;
import com.wtt.agribusiness.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayWebController {

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    OrderService orderService;

    @ResponseBody
    @GetMapping(value = "/payOrder",produces = "text/html")
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        /**
         *         PayVo payVo = new PayVo();
         *         payVo.setBody();            //订单备注
         *         payVo.setOut_trade_no();    //订单号
         *         payVo.setSubject();         //订单标题
         *         payVo.setTotal_amount();    //订单金额
         */
        PayVo payVo = orderService.getOrderPay(orderSn);
        String pay = alipayTemplate.pay(payVo);
        System.out.println(pay);
        return pay;
    }

}
