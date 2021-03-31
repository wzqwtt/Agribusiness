package com.wtt.agribusiness.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wtt.agribusiness.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2021000117629988";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnr8SHtSiXZu1K5jCfvPb4/a9aibaq9WzJPEEn/4ETQVtv4JFehdmC0X6ejzQehfqq44s1VWnzHa1X3ZX1u+H4mKPGkfoLNfuJIuFKvJzUrcrRISlqmChfn3YZ+5t5ZjviitnXCMyjg0SM0Y85VhszhUl3wEME4epOv01tAoqENFY70z7YQlHNqWpOIsUE0prhrArdp2iPBUttnH2eqRqqi0P6brc+YvNtMHbadV5timQ9klIz6KIo450Ru02brtezxED1J4HXSQTe76X378O4mJyPnSmn01HJJpw4HEUw8y8Td8x0n2zjmuPiB/I+cOEDx7NVSV2Ju45tg9Z8WoypAgMBAAECggEAPLD5SMTgtCukpv85ihcyrFvlI7fETYwWm58bVga8COQW3B9Dfhqj5x073tel3YZQe5lvwRXxCvUq0Cp7JD2kQ/27CRu8jGI1cCUXtZ0ehjuAtMBz5Qi9VB66+ByA4SeHdS8cOSAOMQBJx03xsKbhoXwUYytLFnFOBMJom5PX3pEawgLMXLDMwWT7oW7p9SWXbnlFMe5St1LXJetpkssovg8bQ/yzou+5Rj9HaTSu3SDEElax5TBxwt2x6/eeuUMa/CH46cEgPFwHB7lgEcLTdMRX9KbN8e1tPCywv1qxjolz5WZeb2gI7c8QArk3px9YiRqTuqzEa5lOed8WW1bcgQKBgQD0Nn2KyLnFtCLrZHG8p199uwt6BIzw5DGDrOYahKCZcWm8Psj1uG2dT2hn8V0AaYEjbopEKdsP7jnu5xqpn13QBP/TzfvdlrSJp32mxO3tE1H0djG6oa18TDRoiZ7D1tFcfeo858pCscf3m35fyrTDI958DuhxJIIfaumXolajlQKBgQCvx7bEjqhOFVy9LnpBkKePP9TDJm03zPVMBLijL5E67cHZuSTaesi9Opz/4cxjAQSqBkJ2NeOI3l7bkFH0iCsqvsXtEdKEPz0ngxNIjhC4Fd3Jwuty5sUOHDlLH2L9JroZ6fg8g7Jzx7Yb1HfYus3R+fPInankGk4iC94FPHk/xQKBgQDHuWDHBg5YkFAWr1kNbtz72OMz8VDLGfUcuWSev2UQ2N4epEJ6I1ESXIUIrS7gTWjd72OJB6ouxyf+KGd33ew9WjlXoZigysXv5lku8NOdFjXqdleJSPy3MSOTcymzBJbIy/mrwe16yia+bB7YfNGE3mrwjsiu76qzwx4zcwLkWQKBgEethJW6Jl9QzrRGE4xQ1ELRoIzDSp4KcaxB8Jh6eRoygW2q1aBRmTGwiPy+sn1cmO5MByX064eQRdX6j7DLJe8UQ9lb/x/2OZW3GVHovGCUahAv75q3B3utfp9neRIJjLGsepESkt4rfdLzXLt/0RXieso/oFtnbpzrgelYs4iRAoGBALyquBgT2Yz1OAGWZqZ7XXVyGquUWVjnZ5FvDp5u7GzdTD+5PzjEf8fCXQfiCKCjtA2ohM/ZmzCzOLKz4YpQpi4S71MBh3ubBC3JX6ohpuOnZAEPKhLbwGne5DRmOxwombOMcgabEbZzxbzREHn6BFYyf8kP07iT2bzaBJaeNPXk";
    // private  String merchant_private_key = "///+Xlzr+/IwLC/++/+++//+/aUJKBVG6fOpRVKkiXulHhrp5csZH0/C7kaj4Hy7TjgUKSWvwlv7i7jgN0dq/bhVJz82y+N9pENWvy5J0I8Kt67XH+6JDEGWjlV58auifMRSx5mRJNn5pM6qrFlQKBgFyZWm/JV1fv1xVyoLjlXlTvBsbO7kMH/jpgqFwtAk1n/x3VEShJ1kayIbTOjotWSopMvCFJG9tqM+0cyxWLatkELXWifAIsNpqRuYWah1FbZD2fu+kxLNtM0a+YyCUUvZeg2cUnIOraWupxbp9e13eMpvdmWMiWXfhM18CRWEwdAoGAUwT0l076EhgUQJwm1JML0jY94eCfpmLbnNJgRe1qysEPr+B1s2IslA7cOqC5we0kyRmmwsuoibQpZYwbRG7JmRAk2pZtgzDRSbpxv7a0rDoBLmbXMOU0Hraqw2+Bf3v2SMc79/9FWnIvrC4EyBYZZPwGOpsNAZRSdEUQX9qrceUCgYB99OOtFFt1ixzyTCyUj3Fuiw7BsPhdI3nuMSoNTPIDNpzRBp/KFXyv/FNJ2CjTAsX3OR3D6KmEYihqUfrYeb0P5zoybcQLMxbXxK+ec6F2o6U2iqFIq0MKwHUqsb9X3pj4qE0ZHbFgRtIHnL2/QGV5PFJdmIZIBKZcvB8fW6ztDA==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAms2JVy2cVxeiLUO7qnnJhlTan9o5zf13rJbA+dJvS1QUGwvIThGbG6zehgDu9/zodq6se5uz8NijBVqOoZvV6JQKBMdYW6ZFrBfV6Pf11G56nQzwLdb8YFl/kgOnuuAevotST1O0Pto/ZNKD/zJgb9uB2dK+F1OAaSoG1T9Z4K06EtpopL0iMkdgllTg9CdFphnrGgyU4ANmpCCmWzpeQAxKJh4X5l7JwBKkl/NpnSK1o17HfiCkBx/RvFFoWF3nRzWgm9ZoWD/CJu8ZaM0qtSjEiUpi9dEfLrhZtFgiX7TySh7NBqD6IFesEsOPu7bwW7/99p8bUyPOHoYsMsZ/DwIDAQAB";

    //private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyQQceVUChTJGtF/a8SXufhSxDTKporieTq9NO7yDZSpDlAX1zVPT/nf0KWAlxq1TYappWMIYtyrOABhJyn6flNP6vuSBiM5lYsepHvYrtRHqlFiJruEkiaCgEZBKL5aCfBHYj0oqgQn9MpNV/PEH4cBYAVaiI4+VX8CBUQfeEGjgN6OkpLULZ3X0JUkmSnVvCNJ1m3PD68IIlbOfEZXJUKCqmZhzprGR5VWswjxA+g87cMwvijL4gdkSy/daG62Bz5vApcmmMkuX1k1fMWP4ajZCASVw8HD+MSLRhd8We9F97gd8CW0TavzbdR+mTS5H4yEgO8F9HRAsbkhV9yu0yQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = "http://337w7673j9.51vip.biz/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://337w7673j9.51vip.biz/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
