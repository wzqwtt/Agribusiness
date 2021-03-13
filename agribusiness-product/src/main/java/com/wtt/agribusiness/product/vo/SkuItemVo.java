package com.wtt.agribusiness.product.vo;

import com.wtt.agribusiness.product.entity.SkuImagesEntity;
import com.wtt.agribusiness.product.entity.SkuInfoEntity;
import com.wtt.agribusiness.product.entity.SpuInfoDescEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SkuItemVo {
    //1、sku基本信息获取  pms_sku_info
    SkuInfoEntity info;

    boolean hasStock = true;

    //2、sku的图片信息
    List<SkuImagesEntity> images;

    //3、获取spu销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;

    //4、获取spu的介绍
    SpuInfoDescEntity desp;

    //5、获取spu的规格参数信息
    List<SpuItemAttrGroupVo> groupAttrs;


}
