package com.itheima.mapper;

import com.itheima.pojo.EasyUITreeNode;
import com.itheima.pojo.TbContent;
import com.itheima.pojo.TbContentCategory;
import com.itheima.utils.E3Result;

import java.util.List;

public interface ContentCatMapper {
    public List<EasyUITreeNode> contentCatTree(Long parentId);

    Long saveContentCat(TbContentCategory tbContentCategory);

    EasyUITreeNode findContentCatById(Long parentId);

    void updateContentCat(TbContentCategory pTbContentCategory);

    Long addContent(TbContent tbContent);

    List<TbContent> findContentList();
}
