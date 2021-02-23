package com.wtt.agribusiness.product;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.wtt.agribusiness.product.entity.BrandEntity;
import com.wtt.agribusiness.product.service.BrandService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgribusinessProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    OSSClient ossClient;

    @Test
    public void testUpload() throws FileNotFoundException {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI4GCc6NNhAPGCf5PSFi4R";
//        String accessKeySecret = "ioyHDagLGLZmG1DMEk8vEhKFVRNaIA";
//
//// 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        InputStream inputStream = new FileInputStream("D:\\wtt\\assert\\aa64034f78f0f7363a4c2d510455b319ebc4132b.jpg");
        ossClient.putObject("agribusiness", "aa64034f78f0f7363a4c2d510455b319ebc4132b.jpg", inputStream);

// 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("wen jian shang chuan cheng gong!!");
    }

    @Test
    public void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("华为");
        brandService.save(brandEntity);
        System.out.println("保存成功:" + brandEntity.getName());
    }


}
