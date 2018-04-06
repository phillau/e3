package com.itheima.mapper;

import com.itheima.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatMapper {
    List<EasyUITreeNode> getItemCatByParentId(int parentId);
}
