package com.itheima.pojo;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Item extends TbItem {
    public String[] getImages(){
        if(StringUtils.isNotBlank(getImage())){
            String[] images = getImage().split(",");
            return images;
        }
        return null;
    }
    public Item(TbItem tbItem){
        this.setBarcode(tbItem.getBarcode());
        this.setCid(tbItem.getCid());
        this.setCreated(tbItem.getCreated());
        this.setId(tbItem.getId());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setStatus(tbItem.getStatus());
        this.setTitle(tbItem.getTitle());
        this.setUpdated(tbItem.getUpdated());
    }
}
