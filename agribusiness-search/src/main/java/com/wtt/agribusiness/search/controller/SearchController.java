package com.wtt.agribusiness.search.controller;

import com.wtt.agribusiness.search.service.ShopSearchService;
import com.wtt.agribusiness.search.vo.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @Autowired
    ShopSearchService shopSearchService;

    /**
     * 自动将页面提交过来的所有请求查询参数封装成指定对象
     * @param param
     * @return
     */
    @GetMapping("/list.html")
    public String listPage(SearchParam param){
        Object result = shopSearchService.search(param);

        return "list";
    }

}
