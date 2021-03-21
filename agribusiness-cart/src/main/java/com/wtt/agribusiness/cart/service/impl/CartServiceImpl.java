package com.wtt.agribusiness.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wtt.agribusiness.cart.interceptor.CartInterceptor;
import com.wtt.agribusiness.cart.service.CartService;
import com.wtt.agribusiness.cart.vo.CartItem;
import com.wtt.agribusiness.cart.vo.SkuInfoVo;
import com.wtt.agribusiness.cart.vo.UserInfoTo;
import com.wtt.common.utils.R;
import com.wtt.agribusiness.cart.fegin.ProductFeignService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    private final String CART_PREFIX = "agribusiness:cart:";

    @Override
    public CartItem addToCart(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        //1、远程查询当前要添加的商品的信息，按照skuId

        String res = (String) cartOps.get(skuId.toString());
        CartItem cartItem = new CartItem();
        if(StringUtils.isEmpty(res)){
            //购物车无此商品
            //2、新商品添加到购物车
            R skuInfo = productFeignService.getSkuInfo(skuId);
            SkuInfoVo data = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
            });
            cartItem.setCheck(true);
            cartItem.setCount(num);
            cartItem.setImages(data.getSkuDefaultImg());
            cartItem.setTitle(data.getSkuTitle());
            cartItem.setSkuId(skuId);
            cartItem.setPrice(data.getPrice());
            //3、远程查询sku组合信息
//        cartItem.setSkuAttr();
            List<String> values = productFeignService.getSkuSaleAttrValues(skuId);
            cartItem.setSkuAttr(values);

            String s = JSON.toJSONString(cartItem);
            cartOps.put(skuId.toString(),s);
        }else{
            //购物车有此商品，修改商品数量即可
            cartItem = JSON.parseObject(res, CartItem.class);
            cartItem.setCount(cartItem.getCount() + num);
            cartOps.put(skuId.toString(),JSON.toJSONString(cartItem));
        }


        return cartItem;
    }


    /**
     * 获取到要操作的购物车
     * @return
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        //1、
        String cartKey = "";
        if (userInfoTo.getUserId() != null) {
            //登陆了
            cartKey = CART_PREFIX + userInfoTo.getUserId();
        } else {
            cartKey = CART_PREFIX + userInfoTo.getUserKey();
        }
        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(cartKey);
        return operation;
    }
}
