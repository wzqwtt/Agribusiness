package com.wtt.agribusiness.cart.service;

import com.wtt.agribusiness.cart.vo.CartItem;

public interface CartService {
    /**
     * 将商品添加到购物车
     * @param skuId
     * @param num
     * @return
     */
    CartItem addToCart(Long skuId, Integer num);

    /**
     * 获取购物车中某个购物项
     * @param skuId
     * @return
     */
    CartItem getCartItem(Long skuId);
}
