package com.wtt.agribusiness.thirdparty.component;

import com.wtt.agribusiness.thirdparty.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Data
@Component
public class SmsComponent {

    private String host;
    private String path;
    private String smsSignId;
    private String templateId;
    private String appcode;

    public void sendSmsCode1(String phone,String code){
//        String host = "https://gyytz.market.alicloudapi.com";
//        String path = "/sms/smsSend";
        String method = "POST";
//        String appcode = appcode;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:"+code+",**minute**:5");
//        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("smsSignId", smsSignId);
//        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        querys.put("templateId", templateId);
        System.out.println(host);
        System.out.println(path);
        System.out.println(smsSignId);
        System.out.println(templateId);
        System.out.println(appcode);

        Map<String, String> bodys = new HashMap<String, String>();
        try {

            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
