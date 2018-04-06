package com.itheima.mapper;

import com.itheima.pojo.TbItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    public TbItem getItemById(Long itemId);

    List<TbItem> getItems(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    Integer getTotalCount();
}