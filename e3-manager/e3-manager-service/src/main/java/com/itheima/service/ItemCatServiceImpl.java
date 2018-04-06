package com.itheima.service;

import com.itheima.mapper.ItemCatMapper;
import com.itheima.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatByParentId(int parentId) {
        List<EasyUITreeNode> easyUITreeNodeList =  itemCatMapper.getItemCatByParentId(parentId);
        System.out.println("why...");
        for (EasyUITreeNode easyUITreeNode:easyUITreeNodeList) {
            easyUITreeNode.setState("1".equals(easyUITreeNode.getState())?"closed":"open");
        }
        return easyUITreeNodeList;
    }
}
