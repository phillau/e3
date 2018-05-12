package com.itheima.controller;

import com.itheima.pojo.EasyUIDataGridResult;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import com.itheima.service.ItemService;
import com.itheima.utils.FastDFSClient;
import com.itheima.utils.IDUtils;
import com.itheima.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Value("${picServerIp}")
    private String picServerIp;

    @RequestMapping("item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem itemById = itemService.getItemById(itemId);
        return itemById;
    }

    @RequestMapping("item/list")
    @ResponseBody
    public EasyUIDataGridResult list(Integer page, Integer rows){
        return itemService.getEasyUIDataGridResult(page,rows);
    }

    @RequestMapping(value = "pic/upload", method = RequestMethod.POST)
    @ResponseBody
    public String picUpload(MultipartFile uploadFile){
        Map map = new HashMap();
        String extName = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().indexOf(".") + 1);
        FastDFSClient fastDFSClient = null;
        String picPath = "";
        try {
            fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            picPath = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
            map.put("error",0);
            map.put("url",picServerIp+picPath);
        } catch (Exception e) {
            map.put("error",1);
            map.put("message","上传失败");
            e.printStackTrace();
        }
        System.out.println("picPath="+picPath);
        return JsonUtils.objectToJson(map);
    }

    @RequestMapping("item/save")
    public void save(TbItem tbItem,String desc){
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        itemService.saveTbItem(tbItem);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        itemService.saveTbItemDesc(itemId,tbItemDesc);
    }
}
