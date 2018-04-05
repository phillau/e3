package com.itheima.controller;

import com.itheima.pojo.EasyUIDataGridResult;
import com.itheima.pojo.TbItem;
import com.itheima.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem itemById = itemService.getItemById(itemId);
        return itemById;
    }

    @RequestMapping("list")
    @ResponseBody
    public EasyUIDataGridResult list(Integer page, Integer rows){
        return itemService.getEasyUIDataGridResult(page,rows);
    }
}
