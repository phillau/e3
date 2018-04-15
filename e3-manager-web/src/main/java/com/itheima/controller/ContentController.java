package com.itheima.controller;

import com.itheima.pojo.EasyUITreeNode;
import com.itheima.pojo.TbContent;
import com.itheima.service.ContentCatService;
import com.itheima.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class ContentController {

    @Autowired
    @Qualifier("aaa")
    private ContentCatService contentCatService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> contentCatTree(@RequestParam(name="id", defaultValue="0")Long parentId){
        List<EasyUITreeNode> easyUITreeNodeList = contentCatService.contentCatTree(parentId);
        return easyUITreeNodeList;
    }

    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateNode(Long parentId, String name){
        E3Result e3Result = contentCatService.saveContentCat(parentId,name);
        return e3Result;
    }

    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result addContent(TbContent tbContent){
        E3Result e3Result = contentCatService.addContent(tbContent);
        return e3Result;
    }
}
