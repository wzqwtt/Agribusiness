package com.wtt.agribusiness.search.service;

import com.wtt.agribusiness.search.vo.SearchParam;
import com.wtt.agribusiness.search.vo.SearchResult;

public interface ShopSearchService {
    /**
     *
     * @param param 检索的所有参数
     * @return 返回检索的结果，里面包含页面需要的所有信息
     */
    SearchResult search(SearchParam param);
}
