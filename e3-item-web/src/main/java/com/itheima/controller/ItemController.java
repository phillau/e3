package com.itheima.controller;

import com.itheima.pojo.Item;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import com.itheima.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("item/{itemId}")
    public String index(@PathVariable String itemId,Model model){
        TbItem tbItem = itemService.getItemById(Long.parseLong(itemId));
        Item item = new Item(tbItem);
        TbItemDesc tbItemById = itemService.getTbItemById(Long.parseLong(itemId));
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",tbItemById);
        return "item";
    }

    @RequestMapping("freemarker")
    @ResponseBody
    public String testFreeMarker() throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map<String,Object> map = new HashMap();
        map.put("name","liufei");
        map.put("date",new Date());
        FileWriter fileWriter = new FileWriter("E:\\javaProject\\csdn-collector\\freemarker\\hello.txt");
        template.process(map,fileWriter);
        fileWriter.close();
        return "OK";
    }
}
