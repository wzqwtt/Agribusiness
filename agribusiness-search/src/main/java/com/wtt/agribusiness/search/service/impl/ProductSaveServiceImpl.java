package com.wtt.agribusiness.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.wtt.agribusiness.search.config.AgribusinessElasticSearchConfig;
import com.wtt.agribusiness.search.constant.EsConstant;
import com.wtt.agribusiness.search.controller.ElasticSaveController;
import com.wtt.agribusiness.search.service.ProductSaveService;
import com.wtt.common.to.es.SkuEsModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @SneakyThrows
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels){
        //将数据保存到es中
        //1、给es中建立索引  product，建立好映射关系

        //2、给es中保存数据
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : skuEsModels) {
            //构造保存请求
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, AgribusinessElasticSearchConfig.COMMON_OPTIONS);

        //TODO 如果批量错误
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架完成：{},返回数据：{}",collect,bulk.toString());


        return b;
    }
}
