package com.itheima.service;

import com.itheima.jedis.JedisClient;
import com.itheima.mapper.ContentCatMapper;
import com.itheima.pojo.EasyUITreeNode;
import java.util.List;

import com.itheima.pojo.TbContent;
import com.itheima.pojo.TbContentCategory;
import com.itheima.utils.E3Result;
import com.itheima.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContentCatServiceImpl implements ContentCatService {
    @Autowired
    private ContentCatMapper contentCatMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

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
            try {
                jedisClient.hdel(CONTENT_LIST,tbContent.getCategoryId()+"");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return e3Result;
    }

    @Override
    public List<TbContent> findContentList(Integer LUNBO_ID) {
        try {
            //先从redis中查询
            String content_list = jedisClient.hget(CONTENT_LIST, LUNBO_ID + "");
            //如果不为空的话直接返回
            if(StringUtils.isNotBlank(content_list)){
                return JsonUtils.jsonToList(content_list,TbContent.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //否则从数据库中查询
        List<TbContent> contentList = contentCatMapper.findContentList(LUNBO_ID);
        //将从数据库中查询到的数据放入redis中
        try {
            jedisClient.hset(CONTENT_LIST,LUNBO_ID+"", JsonUtils.objectToJson(contentList));
        }catch (Exception e){
            e.printStackTrace();
        }
        return contentList;
    }
}
