package com.itheima.service;

import com.itheima.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getItemCatByParentId(int parentId);
}
