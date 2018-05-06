package com.itheima.controller;

import com.itheima.pojo.SearchItem;
import com.itheima.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private SearchItemService searchItemService;
    @Value("${row}")
    private Integer row;

    @RequestMapping("search")
    public String index(Model model, String keyword, @RequestParam(defaultValue = "1") Integer page) throws Exception {
        List<SearchItem> searchItemList = searchItemService.findItemByKeyword(keyword, page, row);
        model.addAttribute("itemList",searchItemList);
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", 234);
        model.addAttribute("recourdCount", 432);
        model.addAttribute("page", 1);
        return "search";
    }
}
