package com.itheima.service;

import com.itheima.mapper.ContentCatMapper;
import com.itheima.pojo.EasyUITreeNode;
import java.util.List;

import com.itheima.pojo.TbContent;
import com.itheima.pojo.TbContentCategory;
import com.itheima.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentCatServiceImpl implements ContentCatService {
    @Autowired
    private ContentCatMapper contentCatMapper;

    @Override
    public List<EasyUITreeNode> contentCatTree(Long parentId) {
        List<EasyUITreeNode> easyUITreeNodeList =  contentCatMapper.contentCatTree(parentId);
        for (EasyUITreeNode easyUITreeNode:easyUITreeNodeList
             ) {
            if("1".equals(easyUITreeNode.getState())) {
                easyUITreeNode.setState("closed");
            }
            if("0".equals(easyUITreeNode.getState())) {
                easyUITreeNode.setState("open");
            }
        }
        return easyUITreeNodeList;
    }

    @Override
    public E3Result saveContentCat(Long parentId, String name) {
        E3Result e3Result = new E3Result();
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setIsParent(false);
        contentCatMapper.saveContentCat(tbContentCategory);
        EasyUITreeNode easyUITreeNode = contentCatMapper.findContentCatById(tbContentCategory.getParentId());
        if("0".equals(easyUITreeNode.getState())){
            TbContentCategory pTbContentCategory = new TbContentCategory();
            pTbContentCategory.setId(parentId);
            pTbContentCategory.setIsParent(true);
            contentCatMapper.updateContentCat(pTbContentCategory);
        }
        if(tbContentCategory.getId() != null){
            e3Result.setStatus(200);
            e3Result.setMsg("添加节点成功！");
        }
        e3Result.setData(tbContentCategory);
        return e3Result;
    }

    @Override
    public E3Result addContent(TbContent tbContent) {
        contentCatMapper.addContent(tbContent);
        E3Result e3Result = new E3Result();
        if(tbContent.getId()!=null){
            e3Result.setStatus(200);
            e3Result.setMsg("添加内容成功！");
        }
        return e3Result;
    }

    @Override
    public List<TbContent> findContentList() {
        return contentCatMapper.findContentList();
    }
}
