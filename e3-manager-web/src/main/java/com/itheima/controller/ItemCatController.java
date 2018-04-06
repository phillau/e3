package com.itheima.controller;

import com.itheima.pojo.EasyUITreeNode;
import com.itheima.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("list")
    @ResponseBody
    public List<EasyUITreeNode> list(@RequestParam(value = "id",defaultValue = "0") int parentId){
        List<EasyUITreeNode> easyUITreeNodeList = itemCatService.getItemCatByParentId(parentId);
        return easyUITreeNodeList;
    }
}
