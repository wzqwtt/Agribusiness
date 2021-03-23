package com.wtt.agribusiness.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.wtt.agribusiness.order.constant.OrderConstant;
import com.wtt.agribusiness.order.feign.CartFeiginService;
import com.wtt.agribusiness.order.feign.MemberFeignService;
import com.wtt.agribusiness.order.feign.WmsFeignService;
import com.wtt.agribusiness.order.interceptor.LoginUserInterceptor;
import com.wtt.agribusiness.order.vo.MemberAddressVo;
import com.wtt.agribusiness.order.vo.OrderConfirmVo;
import com.wtt.agribusiness.order.vo.OrderItemVo;
import com.wtt.agribusiness.order.vo.SkuStockVo;
import com.wtt.common.utils.R;
import com.wtt.common.vo.MemberRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wtt.common.utils.PageUtils;
import com.wtt.common.utils.Query;

import com.wtt.agribusiness.order.dao.OrderDao;
import com.wtt.agribusiness.order.entity.OrderEntity;
import com.wtt.agribusiness.order.service.OrderService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    CartFeiginService cartFeiginService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    WmsFeignService wmsFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();

//        List<MemberAddressVo> address = memberFeignService.getAddress(memberRespVo.getId());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();


        CompletableFuture<Void> getAddressFuture = CompletableFuture.runAsync(() -> {
            //1、远程查询s所有的收获列表
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddressVo> address = memberFeignService.getAddress(memberRespVo.getId());
            confirmVo.setAddress(address);
        }, executor);

        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            //2、远程查询购物车所有选中的购物项
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItemVo> items = cartFeiginService.getCurrentUserCartItems();
            confirmVo.setItems(items);
        }, executor).thenRunAsync(() -> {
            List<OrderItemVo> items = confirmVo.getItems();
            List<Long> collect = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());

            R hasStock = wmsFeignService.getSkusHasStock(collect);
            List<SkuStockVo> data = hasStock.getData(new TypeReference<List<SkuStockVo>>() {
            });
            if (data != null) {
                Map<Long, Boolean> map = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                confirmVo.setStocks(map);
            }
        }, executor);

        //3、查询用户积分
        Integer integration = memberRespVo.getIntegration();
        confirmVo.setIntegration(integration);

        //4、其他数据自动计算

        //TODO 5、防重令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId(), token, 30, TimeUnit.MINUTES);
        confirmVo.setOrderToken(token);

        CompletableFuture.allOf(getAddressFuture, cartFuture).get();

        return confirmVo;
    }

}