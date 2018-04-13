package com.itheima.controller;

import com.itheima.pojo.EasyUITreeNode;
import com.itheima.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> contentCatTree(){
        return null;
    }
}
