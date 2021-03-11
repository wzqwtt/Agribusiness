package com.wtt.agribusiness.search.vo;

import com.wtt.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

    //查询到的所有商品信息
    private List<SkuEsModel> products;

    /**
     * 以下为分页信息
     */
    private Integer pageNum;    //当前页码
    private Long total;         //总记录数
    private Integer totalPage;  //总页码

    private List<BrandVo> brands;   //当前查询到的结果，所有涉及到的品牌

    private List<CatalogVo> catalogs;   //当前查询到的结果，所有涉及到的分类

    private List<AttrVo> atts;      //当前查询到的结果，所有涉及到的所有属性

    //==================以上是返回给页面的所有信息============================

    @Data
    public static class BrandVo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class AttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    public static class CatalogVo{
        private Long catalogId;
        private String catalogName;

    }

}
