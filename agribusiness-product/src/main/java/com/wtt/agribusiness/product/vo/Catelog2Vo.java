package com.wtt.agribusiness.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 二级分类vo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catelog2Vo {
    private String catalog1Id;  //一级父分类
    private List<Catelog3Vo> catalog3List;  //三级子分类
    private String id;
    private String name;

    /**
     * 三级分类vo
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Catelog3Vo{
        private String catalog2Id;  //父分类ID,2级
        private String id;
        private String name;
    }

}
