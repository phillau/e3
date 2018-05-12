package com.itheima.service;

import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.EasyUIDataGridResult;
import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TbItem getItemById(Long itemId){
        TbItem itemById = itemMapper.getItemById(itemId);
        return itemById;
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
