/**
  * Copyright 2021 bejson.com 
  */
package com.wtt.agribusiness.product.vo;
import java.util.List;

/**
 * Auto-generated: 2021-02-28 13:27:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SpuSaveVo {

    private String spuName;
    private String spuDescription;
    private int catalogId;
    private int brandId;
    private int weight;
    private int publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;
    public void setSpuName(String spuName) {
         this.spuName = spuName;
     }
     public String getSpuName() {
         return spuName;
     }

    public void setSpuDescription(String spuDescription) {
         this.spuDescription = spuDescription;
     }
     public String getSpuDescription() {
         return spuDescription;
     }

    public void setCatalogId(int catalogId) {
         this.catalogId = catalogId;
     }
     public int getCatalogId() {
         return catalogId;
     }

    public void setBrandId(int brandId) {
         this.brandId = brandId;
     }
     public int getBrandId() {
         return brandId;
     }

    public void setWeight(int weight) {
         this.weight = weight;
     }
     public int getWeight() {
         return weight;
     }

    public void setPublishStatus(int publishStatus) {
         this.publishStatus = publishStatus;
     }
     public int getPublishStatus() {
         return publishStatus;
     }

    public void setDecript(List<String> decript) {
         this.decript = decript;
     }
     public List<String> getDecript() {
         return decript;
     }

    public void setImages(List<String> images) {
         this.images = images;
     }
     public List<String> getImages() {
         return images;
     }

    public void setBounds(Bounds bounds) {
         this.bounds = bounds;
     }
     public Bounds getBounds() {
         return bounds;
     }

    public void setBaseAttrs(List<BaseAttrs> baseAttrs) {
         this.baseAttrs = baseAttrs;
     }
     public List<BaseAttrs> getBaseAttrs() {
         return baseAttrs;
     }

    public void setSkus(List<Skus> skus) {
         this.skus = skus;
     }
     public List<Skus> getSkus() {
         return skus;
     }

}