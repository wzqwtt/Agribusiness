package com.wtt.agribusiness.cart.service;

import com.wtt.agribusiness.cart.vo.CartItem;

public interface CartService {
    CartItem addToCart(Long skuId, Integer num);
}
