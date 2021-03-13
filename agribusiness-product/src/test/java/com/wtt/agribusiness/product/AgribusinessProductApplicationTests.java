package com.wtt.agribusiness.product;


//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSClientBuilder;
import com.wtt.agribusiness.product.dao.AttrGroupDao;
import com.wtt.agribusiness.product.dao.SkuSaleAttrValueDao;
import com.wtt.agribusiness.product.entity.BrandEntity;
import com.wtt.agribusiness.product.service.BrandService;

import com.wtt.agribusiness.product.service.CategoryService;
import com.wtt.agribusiness.product.vo.SkuItemSaleAttrVo;
import com.wtt.agribusiness.product.vo.SkuItemVo;
import com.wtt.agribusiness.product.vo.SpuItemAttrGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AgribusinessProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Test
    public void test(){
//        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(14L, 266L);
//        System.out.println(attrGroupWithAttrsBySpuId.toString());
        List<SkuItemSaleAttrVo> saleAttrsBySpuId = skuSaleAttrValueDao.getSaleAttrsBySpuId(14L);
        System.out.println(saleAttrsBySpuId);
    }

    @Test
    public void testFindPath(){
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("完整路径：{}", Arrays.asList(catelogPath));
    }

    @Test
    public void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("华为");
        brandService.save(brandEntity);
        System.out.println("保存成功:" + brandEntity.getName());
    }


}
