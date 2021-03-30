package com.wtt.common.exception;

public class NoStockException extends RuntimeException {

    private Long skuId;

    public NoStockException(String msg) {
        super(msg);
    }

//    public NoStockException() {
//        super("没有足够的库存了");
//    }

    public NoStockException(Long skuId) {
        super("商品id是：" + skuId + "的没有足够的库存了");
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
