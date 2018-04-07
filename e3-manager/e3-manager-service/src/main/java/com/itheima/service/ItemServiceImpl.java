package com.itheima.service;

import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.EasyUIDataGridResult;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public TbItem getItemById(Long itemId){
        TbItem itemById = itemMapper.getItemById(itemId);
        return itemById;
    }

    @Override
    public EasyUIDataGridResult getEasyUIDataGridResult(Integer page,Integer rows) {
        if(page==null) page = 1;
        if(rows==null) rows = 30;
        List<TbItem> tbItem = itemMapper.getItems((page*rows)-rows,rows);
        Integer total = itemMapper.getTotalCount();
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(tbItem);
        easyUIDataGridResult.setTotal(total);
        return easyUIDataGridResult;
    }

    @Override
    public void saveTbItem(TbItem tbItem) {
        itemMapper.saveTbItem(tbItem);
    }

    @Override
    public void saveTbItemDesc(TbItemDesc tbItemDesc) {
        itemMapper.saveTbItemDesc(tbItemDesc);
    }
}
