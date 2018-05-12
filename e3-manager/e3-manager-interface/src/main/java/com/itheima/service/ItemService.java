package com.itheima.service;

import com.itheima.pojo.EasyUIDataGridResult;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;

public interface ItemService {
    TbItem getItemById(Long itemId);

    EasyUIDataGridResult getEasyUIDataGridResult(Integer page,Integer rows);

    void saveTbItem(TbItem tbItem);

    void saveTbItemDesc(long itemId, TbItemDesc tbItemDesc);
}
