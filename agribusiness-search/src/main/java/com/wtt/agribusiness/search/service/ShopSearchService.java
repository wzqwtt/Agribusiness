package com.wtt.agribusiness.search.service;

import com.wtt.agribusiness.search.vo.SearchParam;

public interface ShopSearchService {
    /**
     *
     * @param param 检索的所有参数
     * @return 返回检索的结果
     */
    Object search(SearchParam param);
}
