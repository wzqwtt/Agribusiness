package com.wtt.agribusiness.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.wtt.agribusiness.search.config.AgribusinessElasticSearchConfig;
import com.wtt.agribusiness.search.constant.EsConstant;
import com.wtt.agribusiness.search.service.ShopSearchService;
import com.wtt.agribusiness.search.vo.SearchParam;
import com.wtt.agribusiness.search.vo.SearchResult;
import com.wtt.common.to.es.SkuEsModel;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.ParsedAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.text.Highlighter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopSearchServiceImpl implements ShopSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam param) {
        //1????????????????????????????????????DSL??????

        SearchResult result = null;
        //??????????????????
        SearchRequest searchRequest = buildSearchRequst(param);

        try {
            //??????????????????
            SearchResponse response = client.search(searchRequest, AgribusinessElasticSearchConfig.COMMON_OPTIONS);
            //?????????????????????????????????????????????
            result = buildSearchResult(response, param);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * ??????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    private SearchRequest buildSearchRequst(SearchParam param) {
        //??????DSL??????
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        /**
         * ??????????????????????????????????????????????????????????????????????????????????????????
         */
        //1??? ??????bool-query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //1.1???must-????????????
        if (!StringUtils.isEmpty(param.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
        //1.2???bool - filter - ??????????????????id??????
        if (param.getCatalog3Id() != null) {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
        //1.2???bool - filter - ????????????id??????
        if (param.getBrandId() != null && param.getBrandId().size() > 0) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
        }
        //1.2???bool - filter - ???????????????????????????????????????
        if (param.getAttrs() != null && param.getAttrs().size() > 0) {
            for (String attrStr : param.getAttrs()) {
                //attrs=1_5???:8???&attrs=2_16G:8G
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                //attr = 1_5???:8???
                String[] s = attrStr.split("_");
                String attrId = s[0];   //???????????????id
                String[] attrValues = s[1].split(":");  //??????????????????????????????
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
                //???????????????????????????nested??????
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }

        //1.2???bool - filter - ?????????????????????????????????
        if (param.getHasStock() != null) {
            boolQuery.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 1));
        }
        ////1.2???bool - filter - ??????????????????????????????
//        if (!StringUtils.isEmpty(param.getSkuPrice())) {
//            //1_500/_500/500_
//            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
//            String[] s = param.getSkuPrice().split("_");
//            if (s.length == 2) {
//                //??????
//                rangeQuery.gte(s[0]).lte(s[1]);
//            } else if (s.length == 1) {
//                if (param.getSkuPrice().startsWith("_")) {
//                    //_500
//                    rangeQuery.lte(s[0]);
//                }
//                if (param.getSkuPrice().endsWith("_")) {
//                    //500_
//                    rangeQuery.gte(s[0]);
//                }
//            }
//            boolQuery.filter(rangeQuery);
//        }
        //???????????????????????????????????????????????????
        sourceBuilder.query(boolQuery);

        /**
         * ????????????????????????
         */
        //2.1?????????
        if (!StringUtils.isEmpty(param.getSort())) {
            String sort = param.getSort();
            //sort = hostScore_asc/desc
            String[] s = sort.split("_");
            SortOrder order = s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0], order);
        }
        //2.2?????????    pageSize : 5
        //pageNum:1 form:0  [0,1,2,3,4]
        //pageNum:2 form:5  []
        //from = (pageNum-1)*size
        sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        //2.3?????????
        if (!StringUtils.isEmpty(param.getKeyword())) {
            HighlightBuilder builder = new HighlightBuilder();
            builder.field("skuTitle");
            builder.preTags("<b style='color:red'>");
            builder.postTags("</b>");
            sourceBuilder.highlighter(builder);
        }

        /**
         * ????????????

         //3.1???????????????
         TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
         brand_agg.field("brandId.keyword").size(50);
         //brand_agg????????????
         brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName.keyword").size(1));
         brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg.keyword").size(1));
         //TODO ??????????????????
         sourceBuilder.aggregation(brand_agg);

         //3.2???????????????
         TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalogId.keyword").size(20);
         catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName.keyword").size(1));
         //TODO ??????????????????
         sourceBuilder.aggregation(catalog_agg);

         //3.3???????????????
         NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
         //????????????????????????attrId
         TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId.keyword");
         //?????????????????????attrId?????????attrName
         attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName.keyword").size(1));
         //?????????????????????attrId?????????????????????????????????attrValue
         attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue.keyword").size(50));
         attr_agg.subAggregation(attr_id_agg);
         //TODO ??????????????????
         sourceBuilder.aggregation(attr_agg);
         */

        String s = sourceBuilder.toString();
        System.out.println("?????????DSL???" + s);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
        return searchRequest;
    }

    /**
     * ??????????????????
     *
     * @param response
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse response, SearchParam param) {
        SearchResult result = new SearchResult();
        //1????????????????????????????????????
        SearchHits hits = response.getHits();
        List<SkuEsModel> esModels = new ArrayList<>();
        if (hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel esModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                if (!StringUtils.isEmpty(param.getKeyword())) {
                    HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                    String string = skuTitle.getFragments()[0].string();
                    esModel.setSkuTitle(string);
                }
                esModels.add(esModel);
            }
        }
        result.setProducts(esModels);

        /**
         * ????????????

         //2???????????????????????????????????????????????????
         List<SearchResult.AttrVo> attrVos = new ArrayList<>();
         ParsedNested attr_agg = response.getAggregations().get("attr_agg");
         ParsedLongTerms attr_id_agg = attr_agg.getAggregations().get("attr_id_agg");
         for (Terms.Bucket bucket : attr_id_agg.getBuckets()) {
         SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
         //????????????id
         long attrId = bucket.getKeyAsNumber().longValue();
         //??????????????????
         String attrName = ((ParsedStringTerms) bucket.getAggregations().get("attr_name_agg")).getBuckets().get(0).getKeyAsString();
         //?????????????????????
         List<String> attrValues = ((ParsedStringTerms) bucket.getAggregations().get("attr_value_agg")).getBuckets().stream().map(item -> {
         String keyAsString = ((Terms.Bucket) item).getKeyAsString();
         return keyAsString;
         }).collect(Collectors.toList());
         attrVo.setAttrId(attrId);
         attrVo.setAttrName(attrName);
         attrVo.setAttrValue(attrValues);
         attrVos.add(attrVo);
         }
         result.setAtts(attrVos);

         //3?????????????????????????????????????????????
         List<SearchResult.BrandVo> brandVos = new ArrayList<>();
         ParsedLongTerms brand_agg = response.getAggregations().get("brand_agg");
         for (Terms.Bucket bucket : brand_agg.getBuckets()) {
         SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
         //????????????id
         long brandId = bucket.getKeyAsNumber().longValue();
         //??????????????????
         String brandName = ((ParsedStringTerms) bucket.getAggregations().get("brand_name_agg")).getBuckets().get(0).getKeyAsString();
         //?????????????????????
         String brandImg = ((ParsedStringTerms) bucket.getAggregations().get("brand_img_agg")).getBuckets().get(0).getKeyAsString();
         brandVo.setBrandId(brandId);
         brandVo.setBrandName(brandName);
         brandVo.setBrandImg(brandImg);
         brandVos.add(brandVo);
         }
         result.setBrands(brandVos);

         //4?????????????????????????????????????????????
         ParsedLongTerms catalog_agg = response.getAggregations().get("catalog_agg");
         List<SearchResult.CatalogVo> catalogVos = new ArrayList<>();
         List<? extends Terms.Bucket> buckets = catalog_agg.getBuckets();
         for (Terms.Bucket bucket : buckets) {
         SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
         //????????????ID
         String keyAsString = bucket.getKeyAsString();
         catalogVo.setCatalogId(Long.parseLong(keyAsString));
         //???????????????
         ParsedStringTerms catalog_name_agg = bucket.getAggregations().get("catalog_name_agg");
         String catalog_name = catalog_name_agg.getBuckets().get(0).getKeyAsString();
         catalogVo.setCatalogName(catalog_name);

         catalogVos.add(catalogVo);
         }
         result.setCatalogs(catalogVos);
         //================?????????????????????======================
         */

        //5?????????????????????????????????????????????
        //5.1 ????????????-??????
        result.setPageNum(param.getPageNum());
        //5.2 ????????????-????????????
        long total = hits.getTotalHits().value;
        result.setTotal(total);
        //5.3 ????????????-?????????-?????? 11/2 = 5...1
        int totalPages = (int) total % EsConstant.PRODUCT_PAGESIZE == 0 ? ((int) total / EsConstant.PRODUCT_PAGESIZE) : ((int) total / EsConstant.PRODUCT_PAGESIZE + 1);
        result.setTotalPage(totalPages);

        List<Integer> pageNavs = new ArrayList<>();
        for(int i = 1; i<= totalPages;i++){
            pageNavs.add(i);
        }
        result.setPageNavs(pageNavs);

        return result;
    }


}
