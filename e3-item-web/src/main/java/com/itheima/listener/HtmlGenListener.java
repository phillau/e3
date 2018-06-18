package com.itheima.listener;

import com.itheima.pojo.Item;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import com.itheima.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class HtmlGenListener implements MessageListener{
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void onMessage(Message message) {
        Writer out = null;
        try {
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            TextMessage textMessage = (TextMessage) message;
            String text = null;
            text = textMessage.getText();
            Long itemId = new Long(text);
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc tbItemById = itemService.getTbItemById(itemId);
            Map<String,Object> map = new HashMap<>();
            map.put("item",item);
            map.put("itemDesc",tbItemById);
            out = new FileWriter(new File("E:\\javaProject\\csdn-collector\\freemarker\\"+itemId+".html"));
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
