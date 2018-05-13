package com.itheima.service;

import com.itheima.jedis.JedisClient;
import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.EasyUIDataGridResult;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import com.itheima.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEM_INFO_PRE}")
    private String ITEM_INFO_PRE;
    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;

    public TbItem getItemById(Long itemId){
        //先从redis中查询，如果有则返回
        //如果没有则从数据库中查询并放入redis
        try {
            String base = jedisClient.get(ITEM_INFO_PRE+":"+itemId+":BASE");
            if(StringUtils.isNotBlank(base)){
                return JsonUtils.jsonToPojo(base,TbItem.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItem itemById = itemMapper.getItemById(itemId);
        try {
            jedisClient.set(ITEM_INFO_PRE+":"+itemId+":BASE",JsonUtils.objectToJson(itemById));
            //设置过期时间
            jedisClient.expire(ITEM_INFO_PRE + ":" + itemId + ":BASE", ITEM_INFO_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemById;
    }

    public TbItemDesc getTbItemById(Long itemId){
        try {
            String desc = jedisClient.get(ITEM_INFO_PRE+":"+itemId+":DESC");
            if(StringUtils.isNotBlank(desc)){
                return JsonUtils.jsonToPojo(desc,TbItemDesc.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemDesc itemDescById = itemMapper.getItemDescById(itemId);
        try {
            jedisClient.set(ITEM_INFO_PRE+":"+itemId+":DESC",JsonUtils.objectToJson(itemDescById));
            //设置过期时间
            jedisClient.expire(ITEM_INFO_PRE + ":" + itemId + ":DESC", ITEM_INFO_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemDescById;
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
    public void saveTbItemDesc(long itemId, TbItemDesc tbItemDesc) {
        itemMapper.saveTbItemDesc(tbItemDesc);
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                System.out.println("添加完成商品放到mq中："+itemId);
                return session.createTextMessage(itemId+"");
            }
        });
    }
}
