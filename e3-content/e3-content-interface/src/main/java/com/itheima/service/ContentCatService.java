package com.itheima.service;

import com.itheima.pojo.EasyUITreeNode;
import com.itheima.pojo.TbContent;
import com.itheima.utils.E3Result;

import java.util.List;

public interface ContentCatService {
    List<EasyUITreeNode> contentCatTree(Long parentId);

    E3Result saveContentCat(Long parentId, String name);

    E3Result addContent(TbContent tbContent);

    List<TbContent> findContentList();
}
