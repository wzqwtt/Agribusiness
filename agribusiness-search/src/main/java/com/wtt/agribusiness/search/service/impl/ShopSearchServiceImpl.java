package com.wtt.agribusiness.search.service.impl;

import com.wtt.agribusiness.search.config.AgribusinessElasticSearchConfig;
import com.wtt.agribusiness.search.constant.EsConstant;
import com.wtt.agribusiness.search.service.ShopSearchService;
import com.wtt.agribusiness.search.vo.SearchParam;
import com.wtt.agribusiness.search.vo.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.text.Highlighter;
import java.io.IOException;

@Service
public class ShopSearchServiceImpl implements ShopSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam param) {
        //1、动态构建出查询所需要的DSL语句

        SearchResult result = null;
        //准备检索请求
        SearchRequest searchRequest = buildSearchRequst(param);

        try {
            //执行检索请求
            SearchResponse response = client.search(searchRequest, AgribusinessElasticSearchConfig.COMMON_OPTIONS);
            //分析响应数据，封装成需要的格式
            result = buildSearchResult(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 准备检索请求
     * 模糊匹配，过滤（按照属性，分类，品牌，价格区间，库存），排序，分页，高亮，聚合分析
     *
     * @return
     */
    private SearchRequest buildSearchRequst(SearchParam param) {
        //构建DSL语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        /**
         * 查询：模糊匹配，过滤（按照属性，分类，品牌，价格区间，库存）
         */
        //1、 构建bool-query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //1.1、must-模糊匹配
        if (!StringUtils.isEmpty(param.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
        //1.2、bool - filter - 按照三级分类id查询
        if (param.getCatalog3Id() != null) {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
        //1.2、bool - filter - 按照品牌id查询
        if (param.getBrandId() != null && param.getBrandId().size() > 0) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
        }
        //1.2、bool - filter - 按照所有指定的属性进行查询
        if (param.getAttrs() != null && param.getAttrs().size() > 0) {
            for (String attrStr : param.getAttrs()) {
                //attrs=1_5寸:8寸&attrs=2_16G:8G
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                //attr = 1_5寸:8寸
                String[] s = attrStr.split("_");
                String attrId = s[0];   //检索的属性id
                String[] attrValues = s[1].split(":");  //这个属性的检索用的值
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
                //每一个必须都要生成nested查询
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }
        //1.2、bool - filter - 按照库存是否有进行查询
        boolQuery.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 1));
        ////1.2、bool - filter - 按照价格区间进行查询
        if (!StringUtils.isEmpty(param.getSkuPrice())) {
            //1_500/_500/500_
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            String[] s = param.getSkuPrice().split("_");
            if (s.length == 2) {
                //区间
                rangeQuery.gte(s[0]).lte(s[1]);
            } else if (s.length == 1) {
                if (param.getSkuPrice().startsWith("_")) {
                    //_500
                    rangeQuery.lte(s[0]);
                }
                if (param.getSkuPrice().endsWith("_")) {
                    //500_
                    rangeQuery.gte(s[0]);
                }
            }
            boolQuery.filter(rangeQuery);
        }
        //把以前的所有条件都拿过来，进行封装
        sourceBuilder.query(boolQuery);

        /**
         * 排序，分页，高亮
         */
        //2.1、排序
        if (!StringUtils.isEmpty(param.getSort())) {
            String sort = param.getSort();
            //sort = hostScore_asc/desc
            String[] s = sort.split("_");
            SortOrder order = s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0], order);
        }
        //2.2、分页    pageSize : 5
        //pageNum:1 form:0  [0,1,2,3,4]
        //pageNum:2 form:5  []
        //from = (pageNum-1)*size
        sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        //2.3、高亮
        if (!StringUtils.isEmpty(param.getKeyword())) {
            HighlightBuilder builder = new HighlightBuilder();
            builder.field("skuTitle");
            builder.preTags("<b style='color:red'>");
            builder.postTags("</b>");
            sourceBuilder.highlighter(builder);
        }

        /**
         * 聚合分析
         */
        String s = sourceBuilder.toString();
        System.out.println("构建的DSL：" + s);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
        return searchRequest;
    }

    /**
     * 构建结果数据
     *
     * @param response
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse response) {
        return null;
    }


}
