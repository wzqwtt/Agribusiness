package com.wtt.agribusiness.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SkuItemSaleAttrVo {
    private Long attrId;    //属性id
    private String attrName;    //属性名
    private List<AttrValueWithSkuIdVo> attrValues;
}
