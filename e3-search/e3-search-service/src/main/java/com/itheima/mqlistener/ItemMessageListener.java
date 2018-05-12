package com.itheima.mqlistener;

import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemMessageListener implements MessageListener {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            long itemId = Long.parseLong(textMessage.getText());
            SearchItem searchItem = itemMapper.findItemById(itemId);
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.addField("id",searchItem.getId());
            solrInputDocument.addField("item_title",searchItem.getTitle());
            solrInputDocument.addField("item_sell_point",searchItem.getSell_point());
            solrInputDocument.addField("item_price",searchItem.getPrice());
            solrInputDocument.addField("item_image",searchItem.getImage());
//            solrInputDocument.addField("item_desc",searchItem.getItem_desc());
            solrInputDocument.addField("item_category_name",searchItem.getCategory_name());
            solrServer.add(solrInputDocument);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
