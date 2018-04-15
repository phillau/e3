package com.itheima.controller;

import com.itheima.pojo.TbContent;
import com.itheima.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private ContentCatService  contentCatService;

    @RequestMapping("/index")
    public String index(Model model){
        List<TbContent> ad1List = contentCatService.findContentList();
        model.addAttribute("ad1List",ad1List);
        return "index";
    }
}
