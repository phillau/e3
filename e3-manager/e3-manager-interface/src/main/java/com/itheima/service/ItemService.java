package com.itheima.service;

import com.itheima.pojo.EasyUIDataGridResult;
import com.itheima.pojo.TbItem;

public interface ItemService {
    TbItem getItemById(Long itemId);

    EasyUIDataGridResult getEasyUIDataGridResult(Integer page,Integer rows);
}
