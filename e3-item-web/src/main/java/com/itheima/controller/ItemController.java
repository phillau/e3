package com.itheima.controller;

import com.itheima.pojo.Item;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import com.itheima.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("item/{itemId}")
    public String index(@PathVariable String itemId,Model model){
        TbItem tbItem = itemService.getItemById(Long.parseLong(itemId));
        Item item = new Item(tbItem);
        TbItemDesc tbItemById = itemService.getTbItemById(Long.parseLong(itemId));
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",tbItemById);
        return "item";
    }
}
