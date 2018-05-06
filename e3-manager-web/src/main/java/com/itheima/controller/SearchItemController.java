package com.itheima.controller;

import com.itheima.service.SearchItemService;
import com.itheima.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("index/item/import")
    @ResponseBody
    public E3Result importSolrIndex(){
        try {
            searchItemService.importIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500,"导入失败");
        }
        return E3Result.ok();
    }
}
