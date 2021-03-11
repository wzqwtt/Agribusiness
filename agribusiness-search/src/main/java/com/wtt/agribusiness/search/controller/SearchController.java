package com.wtt.agribusiness.search.controller;

import com.wtt.agribusiness.search.service.ShopSearchService;
import com.wtt.agribusiness.search.vo.SearchParam;
import com.wtt.agribusiness.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String listPage(SearchParam param, Model model){
        //1、根据传递过来的页面查询参数，去es中检索商品
        SearchResult result = shopSearchService.search(param);

        model.addAttribute("result",result);

        return "list";
    }

}
