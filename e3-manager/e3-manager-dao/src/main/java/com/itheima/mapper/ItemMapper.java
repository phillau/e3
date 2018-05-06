package com.itheima.mapper;

import com.itheima.pojo.SearchItem;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    public TbItem getItemById(Long itemId);

    List<TbItem> getItems(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    Integer getTotalCount();

    void saveTbItem(TbItem tbItem);

    void saveTbItemDesc(TbItemDesc tbItemDesc);

    List<SearchItem> findSearchItem();
}
