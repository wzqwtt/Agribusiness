package com.wtt.agribusiness.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
public class AgribusinessThirdPartyApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Autowired
    OSSClient ossClient;

    @Test
    public void testUpload() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\wtt\\assert\\t01ac3aaf5af8a2239a.jpg");
        ossClient.putObject("agribusiness","t01ac3aaf5af8a2239a.jpg",inputStream);
        ossClient.shutdown();
        System.out.println("上传完成...");

    }

}
